package org.apache.eventmesh.common.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.FIELD})
public @interface ConfigFiled {

    /**
     * @return 配置文件的键名
     */
    String field() default "";

    /**
     * Note : reload 为 true 时，所在类必须有 reload 方法
     * @return 是否需要重新加载，应用于关联其他字段的场景
     */
    boolean reload() default false;
}
