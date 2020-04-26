package top.xiaohuashifu.share.aspect.annotation;

import java.lang.annotation.*;

import top.xiaohuashifu.share.constant.AdminLogType;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AdminLog {

	AdminLogType type();

}
