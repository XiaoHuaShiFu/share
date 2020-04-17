package top.xiaohuashifu.share.aspect.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import top.xiaohuashifu.share.constant.AdminLogType;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AdminLog {
	
	String value() default "";
	
	AdminLogType type();
	
}
