package org.apache.eventmesh.common.config;

import java.lang.reflect.Field;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConfigInfo {

	public static final String HUMP_SPOT = "spot";
	public static final String HUMP_ROD = "rod";
	private String path; // 对应 Config.path ->  文件路径
	private String field; // 对应 Config.field
	private String prefix; // 对应 Config.prefix
	private String hump; // 对应 Config.hump
	private boolean monitor; // 对应 Config.monitor
	private boolean removePrefix; // 对应 Config.removePrefix

	private Class<?> clazz;
	private Object object;
	private String filePath; // 提取后的文件路径

	Field objectField;
	Object instance;
}
