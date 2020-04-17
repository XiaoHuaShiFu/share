package top.xiaohuashifu.share.aspect.annotation;

import java.lang.annotation.*;

/**
 * 描述: 对返回结果的错误进行解析
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ErrorHandler {
}
