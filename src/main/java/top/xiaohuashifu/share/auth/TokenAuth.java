package top.xiaohuashifu.share.auth;


import top.xiaohuashifu.share.constant.TokenType;

import java.lang.annotation.*;

/**
 * 描述: 进行token身份认证
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TokenAuth {

    TokenType[] tokenType() default {};

}
