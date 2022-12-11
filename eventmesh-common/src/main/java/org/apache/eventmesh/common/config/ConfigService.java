package org.apache.eventmesh.common.config;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.lang.reflect.Field;
import java.util.*;


public class ConfigService {
	
	private static final ConfigService INSTANCE = new ConfigService();

	private Properties properties = new Properties();

	private ConfigMonitorService configMonitorService = new ConfigMonitorService();
	
	private String configPath;
	
	
	public static final ConfigService getInstance() {
		return INSTANCE;
	}
	
	public ConfigService() {}

	// 配置文件在主机上的绝对路径
	public ConfigService setConfigPath(String configPath) {
		this.configPath = configPath;
		return this;
	}
	
	public ConfigService setRootConfig(String path) throws Exception {
		ConfigInfo configInfo = new ConfigInfo();
		configInfo.setPath(path);
		properties = this.getConfig(configInfo);
//		properties = transformLowerCase(properties);
		return this;
	}

	public void getConfig(Object object, Class<?> clazz) throws Exception {
		Config[] configArray = clazz.getAnnotationsByType(Config.class);
		if (configArray.length == 0) {
			return;
		}
		for (Config config : configArray) {
			ConfigInfo configInfo = new ConfigInfo();
			configInfo.setField(config.field());
			configInfo.setPath(config.path());
			configInfo.setPrefix(config.prefix());
			configInfo.setHump(config.hump());
			configInfo.setMonitor(config.monitor());

			Field field = clazz.getDeclaredField(configInfo.getField());
			configInfo.setClazz(field.getType());
			Object configObject = this.getConfig(configInfo);
			field.setAccessible(true);
			field.set(object, configObject);
			if (configInfo.isMonitor()) {
				configInfo.setObjectField(field);
				configInfo.setInstance(object);
				configInfo.setObject(configObject);
				configMonitorService.monitor(configInfo);
			}
		}

	}

	public void getConfig(Object object) throws Exception {
		this.getConfig(object, object.getClass());
	}
	
	public <T> T getConfig(Class<?> clazz) {
		try {
			return this.getConfig(ConfigInfo.builder().clazz(clazz).hump(ConfigInfo.HUMP_SPOT).build());
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
	} 

	@SuppressWarnings("unchecked")
	public <T> T getConfig(ConfigInfo configInfo) throws Exception {
		Object object;
		if (Objects.isNull(configInfo.getPath()) || StringUtils.isEmpty(configInfo.getPath().trim())) {
			object = FileLoad.getPropertiesFileLoad().getConfig(properties, configInfo); // 从主配置文件中解析
		} else {
			String path = configInfo.getPath();
			String filePath;
			if (path.startsWith("classPath://")) {				
				filePath = ConfigService.class.getResource("/"+path.substring(12)).getPath();
			} else if (path.startsWith("file://")) {
				filePath = path.substring(7);
			} else {
				filePath = this.configPath + path;
			}
			File file = new File(filePath);
			if (!file.exists()) {
				throw new RuntimeException("fie is not exists");
			}
			String suffix = path.substring(path.lastIndexOf('.')+1);
			configInfo.setFilePath(filePath);
			object = FileLoad.getFileLoad(suffix).getConfig(configInfo);
		}
		return (T) object;
	}

	// 将map值全部转换为小写
	public static Properties transformLowerCase(Properties orgMap) {
		Properties properties = new Properties();
		if (orgMap == null || orgMap.isEmpty()) {
			return properties;
		}
		Set<Object> keySet = orgMap.keySet();
		for (Object key : keySet) {
			String key1 = (String) key;
			String newKey = key1.toLowerCase(Locale.ROOT);
			properties.put(newKey, orgMap.get(key));
		}
		return properties;
	}
}
