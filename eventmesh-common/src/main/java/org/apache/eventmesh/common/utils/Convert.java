package org.apache.eventmesh.common.utils;

import com.google.common.base.Splitter;
import inet.ipaddr.AddressStringException;
import inet.ipaddr.IPAddress;
import inet.ipaddr.IPAddressString;
import org.apache.eventmesh.common.config.ConfigFiled;
import org.apache.eventmesh.common.config.ConfigInfo;
import org.apache.eventmesh.common.config.NotNull;

import java.lang.reflect.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Map.Entry;

import com.google.common.base.Preconditions;

import lombok.Data;

public class Convert {

	private Map<Class<?> ,ConvertValue<?> > classToConvert = new HashMap<Class<?>, ConvertValue<?>>();

	private ConvertValue<?> convertEnum = new ConvertEnum();

	{
		this.register(new ConvertCharacter(), Character.class , char.class);
		this.register(new ConvertByte(), Byte.class , byte.class);
		this.register(new ConvertShort(), Short.class , short.class);
		this.register(new ConvertInteger(), Integer.class , int.class);
		this.register(new ConvertLong(), Long.class , long.class);
		this.register(new ConvertFloat(), Float.class , float.class);
		this.register(new ConvertDouble(), Double.class , double.class);
		this.register(new ConvertBoolean(), Boolean.class , boolean.class);
		this.register(new ConvertDate(), Date.class);
		this.register(new ConvertString(), String.class);
		this.register(new ConvertLocalDate(), LocalDate.class);
		this.register(new ConvertLocalDateTime(), LocalDateTime.class);
		this.register(new ConvertList(), List.class , ArrayList.class,LinkedList.class,Vector.class);
		this.register(new ConvertMap(), Map.class , HashMap.class,TreeMap.class,LinkedHashMap.class);
		this.register(new ConvertIPAddress(), IPAddress.class);
	}

	public Object createObject(ConfigInfo configInfo,Properties properties) {
		ConvertInfo convertInfo = new ConvertInfo();
		convertInfo.setConfigInfo(configInfo);
		convertInfo.setProperties(properties);
		convertInfo.setClazz(configInfo.getClazz());

		// 简单类型解析
		 ConvertValue<?> convertValue = classToConvert.get(configInfo.getClazz());
		 if(Objects.nonNull(convertValue)) {
			 return convertValue.convert(convertInfo);
		 }

		// 复杂类型解析，如 EventMeshHTTPConfiguration
		ConvertObject convertObject = new ConvertObject();
		return convertObject.convert(convertInfo);
	}


	public void register(ConvertValue<?> convertValue , Class<?>... clazzs) {
		for(Class<?> clazz : clazzs) {
			classToConvert.put(clazz, convertValue);
		}
	}

	// 把 ConvertInfo 转换为 T 类型
	public interface ConvertValue<T>{

		public default boolean isNotHandleNullValue() {
			return true;
		}

		public T convert(ConvertInfo convertInfo );
	}

	// 复杂类型解析，如 EventMeshHTTPConfiguration
	private class ConvertObject implements ConvertValue<Object> {

		private String prefix; // 配置键前缀，以.结尾

		private ConvertInfo convertInfo; // 要转换的信息

		private Object object; // 配置类型实例，也是最终转换的结果

		private char hump; // 驼峰连接符

		private Class<?> clazz; // 配置类 clazz

		// 初始化 ConvertObject 属性
		private void init(ConfigInfo configInfo) {
			String prefix = configInfo.getPrefix();
			if(Objects.nonNull(prefix)) {
				this.prefix = prefix.endsWith(".") ? prefix : prefix + ".";
			}
			this.hump = Objects.equals(configInfo.getHump() , ConfigInfo.HUMP_ROD)? '_':'.';
			this.clazz = convertInfo.getClazz();
			this.convertInfo.setHump(this.hump);
		}

		@Override // 配置转换方法，从 主配置文件，解析出 Object 如 EventMeshHTTPConfiguration 对象
		public Object convert(ConvertInfo convertInfo) {
			try {
				this.convertInfo = convertInfo;
				this.object = convertInfo.getClazz().newInstance(); // 配置类实例化
				this.init(convertInfo.getConfigInfo());
				this.setValue(); // 配置赋值

				// 向上赋值属性
				Class<?> superclass = convertInfo.getClazz();
				for( ; ; ) {
					superclass = superclass.getSuperclass();
					if(Objects.equals(superclass, Object.class) || Objects.isNull(superclass)) {
						break;
					}
					this.clazz = superclass;
					this.setValue();
				}

				return object;
			}catch(Exception e) {
				throw new RuntimeException(e);
			}
		}

		private void setValue() throws Exception {
			Boolean needReload = Boolean.FALSE; // 配置类 clazz

			// 遍历 配置类 的属性，一一赋值
			for(Field field : this.clazz.getDeclaredFields()) {
				// static 字段不管理
				if(Modifier.isStatic(field.getModifiers())) {
					continue;
				}
				field.setAccessible(true);
				// 复用单例
				ConvertInfo convertInfo = this.convertInfo;
				String key;
				ConfigFiled configFiled = field.getAnnotation(ConfigFiled.class);
				if(configFiled==null || configFiled.field().equals("")){
					key = this.getKey(field.getName(), hump);
				}else {
					key = configFiled.field();
				}
				if(!needReload && configFiled!=null && configFiled.reload()){
					needReload = Boolean.TRUE;
				}
				Class<?> clazz = field.getType();
				ConvertValue<?> convertValue = classToConvert.get(clazz);
				if(clazz.isEnum()) {
					String value = convertInfo.getProperties().getProperty(key);
					convertInfo.setValue(value);
					convertValue = convertEnum;
				}else if(Objects.isNull(convertValue)) {
					if(Objects.equals("ConfigurationWrapper", clazz.getSimpleName())) {
						continue;
					}
					convertValue = new ConvertObject();
					convertInfo = new ConvertInfo();
					if(clazz.isMemberClass()) {
						convertInfo.setClazz(Class.forName(clazz.getName()));
					}else {
						convertInfo.setClazz(field.getType());
					}
					convertInfo.setProperties(this.convertInfo.getProperties());
					convertInfo.setConfigInfo(this.convertInfo.getConfigInfo());
				}else {
					String value = convertInfo.getProperties().getProperty(key);
					if(Objects.isNull(value) && convertValue.isNotHandleNullValue()) {
						NotNull notNull = field.getAnnotation(NotNull.class);
						if(Objects.nonNull(notNull)) {
							Preconditions.checkState(true, key + " is invalidated");
						}
						continue;
					}
					convertInfo.setValue(value);
				}
				convertInfo.setField(field);
				convertInfo.setKey(key);
				Object value = convertValue.convert(convertInfo);

				if(Objects.isNull(value)) {
					NotNull notNull = field.getAnnotation(NotNull.class);
					if(Objects.nonNull(notNull)) {
						Preconditions.checkState(true, key + " is invalidated");
					}
					continue;
				}
				field.set(object,  value); // 回写 object
			}

			if(!needReload) return;
			Method method = this.clazz.getDeclaredMethod("reload", null);
			method.setAccessible(true);
			method.invoke(this.object, null);
		}

		// 拼接配置文件中的 键，就是 字段名 -> 配置文件的键 如 httpServerPort -> http.server.port
		// 连着两个大写，或最后一个大写 拼接为大写 如 ttp.server.P.port
		public String getKey(String fieldName , char spot) {
			// todo 1. key 应该不属于竞争资源，不需要同步吧？   2，删除分支一一个多的 append
			// todo 专有名词的解析， eventMesh 不能切分，IDC 这种全大写缩写，也不能切分
			StringBuilder key = new StringBuilder(Objects.isNull(prefix)?"":prefix);

			boolean currency = false; // 当前字符是否是大写，属性首字符小写 false
			int length = fieldName.length();
			for(int i = 0 ; i< length ; i++) {
				char c = fieldName.charAt(i);
				boolean b = i<length-1 && fieldName.charAt(i + 1) > 96;
				// i<length-1 不是最后一个 ； 后一个字母是小写
				if(currency) {
					if(b) {
						key.append(spot); // 后面有小写，先加 . 再加小写
						key.append((char)(c + 32));
						currency = false;
					}else { // 连着两个大写，或最后一个大写 加大写？
						key.append(c);
					}
				}else {
					if(c >96) { // 小写直接加
						key.append(c);
					}else {
						key.append(spot); // 大写先加 .
						if(b) {
							key.append((char)(c + 32)); // 后面有小写，加小写
						}else {
							key.append(c); // 连着两个大写，或最后一个大写 加大写？
							currency = true;
						}

					}
				}
			}
			if(fieldName.startsWith("eventMesh")){
				key.replace(0,10,"eventMesh");
			}
			return key.toString().toLowerCase(Locale.ROOT);
		}


	}

	private class ConvertCharacter implements ConvertValue<Character>{

		@Override
		public Character convert(ConvertInfo convertInfo) {
			return convertInfo.getValue().charAt(0);
		}
	}

	private class ConvertBoolean implements ConvertValue<Boolean>{

		@Override
		public Boolean convert(ConvertInfo convertInfo) {
			if(Objects.equals(convertInfo.getValue().length(), 1)) {
				return Objects.equals(convertInfo.getValue(), "1")?Boolean.TRUE:Boolean.FALSE;
			}
			return Boolean.valueOf(convertInfo.getValue());
		}
	}

	private class ConvertByte implements ConvertValue<Byte>{

		@Override
		public Byte convert(ConvertInfo convertInfo) {
			return Byte.valueOf(convertInfo.getValue());
		}
	}

	private class ConvertShort implements ConvertValue<Short>{

		@Override
		public Short convert(ConvertInfo convertInfo) {
			return Short.valueOf(convertInfo.getValue());
		}
	}

	private class ConvertInteger implements ConvertValue<Integer>{

		@Override
		public Integer convert(ConvertInfo convertInfo) {
			return Integer.valueOf(convertInfo.getValue());
		}
	}

	private class ConvertLong implements ConvertValue<Long>{

		@Override
		public Long convert(ConvertInfo convertInfo) {
			return Long.valueOf(convertInfo.getValue());
		}
	}

	private class ConvertFloat implements ConvertValue<Float>{

		@Override
		public Float convert(ConvertInfo convertInfo) {
			return Float.valueOf(convertInfo.getValue());
		}
	}

	private class ConvertDouble implements ConvertValue<Double>{

		@Override
		public Double convert(ConvertInfo convertInfo) {
			return Double.valueOf(convertInfo.getValue());
		}
	}

	private class ConvertString implements ConvertValue<String>{

		@Override
		public String convert(ConvertInfo convertInfo) {
			return convertInfo.getValue();
		}
	}

	private class ConvertDate implements ConvertValue<Date>{

		@Override
		public Date convert(ConvertInfo convertInfo) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				return  sdf.parse(convertInfo.getValue());
			} catch (ParseException e) {
				throw new RuntimeException(e);
			}
		}
	}

	private class ConvertLocalDate implements ConvertValue<LocalDate>{

		@Override
		public LocalDate convert(ConvertInfo convertInfo) {
			return LocalDate.parse(convertInfo.getValue(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		}

	}

	private class ConvertLocalDateTime implements ConvertValue<LocalDateTime>{

		@Override
		public LocalDateTime convert(ConvertInfo convertInfo) {
			return LocalDateTime.parse(convertInfo.getValue(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		}

	}

	private class ConvertEnum implements ConvertValue<Enum<?>>{

		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
		public Enum<?> convert(ConvertInfo convertInfo) {
			return Enum.valueOf((Class<Enum>) convertInfo.getField().getType(), convertInfo.getValue());
		}

	}

	private class ConvertList implements ConvertValue<List<Object>>{

		public boolean isNotHandleNullValue() {
			return false;
		}

		@SuppressWarnings("unchecked")
		@Override
		public List<Object> convert(ConvertInfo convertInfo) {
			try {
				if(convertInfo.getValue()==null ){
					return new ArrayList<>();
				}
				List<String> values = Splitter.on(",").omitEmptyStrings()
						.trimResults().splitToList(convertInfo.getValue());
				List<Object> list;
				if(Objects.equals(convertInfo.getField().getType(), List.class)) {
					list = new ArrayList<>();
				}else {
					list = (List<Object>) convertInfo.getField().getType().newInstance();
				}

				Type parameterizedType =  ((ParameterizedType)convertInfo.getField().getGenericType()).getActualTypeArguments()[0];
				ConvertValue<?> convert = classToConvert.get(parameterizedType);
				if(Objects.isNull(convert)) {
					throw new RuntimeException("convert is null");
				}

				for(String value : values) {
					convertInfo.setValue(value);
					list.add(convert.convert(convertInfo));
				}

				return list;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	private class ConvertMap implements ConvertValue<Map<String,Object>>{

		public boolean isNotHandleNullValue() {
			return false;
		}

		@SuppressWarnings("unchecked")
		@Override
		public Map<String,Object> convert(ConvertInfo convertInfo) {
			try {
				String key = convertInfo.getKey() + convertInfo.getHump();
				Map<String,Object> map;
				if(Objects.equals(Map.class, convertInfo.getField().getType())) {
					map = new HashMap<>();
				}else {
					 map = (Map<String,Object>) convertInfo.getField().getType().newInstance();
				}
				Type parameterizedType =  ((ParameterizedType)convertInfo.getField().getGenericType()).getActualTypeArguments()[1];
				ConvertValue<?> convert = classToConvert.get(parameterizedType);
				if(Objects.isNull(convert)) {
					throw new RuntimeException("convert is null");
				}
				for(Entry<Object, Object> entry : convertInfo.getProperties().entrySet()) {
					String propertiesKey = entry.getKey().toString();
					if(propertiesKey.startsWith(key)) {
						String value = entry.getValue().toString();
						convertInfo.setValue(value);
						map.put(propertiesKey.replace(key, "") ,convert.convert(convertInfo));
					}
				}
				return map;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	private class ConvertIPAddress implements ConvertValue<IPAddress>{

		@Override
		public IPAddress convert(ConvertInfo convertInfo) {
			try {
				return new IPAddressString(convertInfo.getValue()).toAddress();
			} catch (AddressStringException e) {
				throw new RuntimeException(e);
			}
		}
	}


	@Data // 代表一个要被转换的 字段
	class ConvertInfo{
		Class<?>  clazz ;
		String value;
		String key;
		Properties properties;
		Field field;
		ConfigInfo configInfo;
		char hump;
	}
}
