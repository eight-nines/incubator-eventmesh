package org.apache.eventmesh.common.config;

import java.lang.reflect.Field;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConfigInfo {
	
	public static final String HUMP_SPOT = "spot";

	public static final String HUMP_ROD = "rod";

	private String path;
	
	private String field;
	
	private String prefix;

	private Class<?> clazz; // 特指配置类的 clazz ，而无关字段或对象

	private Object object;

	private String filePath;

	private boolean removePrefix;

	private boolean monitor;

	private String hump;
	
	Field objectField;
	
	Object instance;
}
