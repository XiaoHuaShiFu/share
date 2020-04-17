package top.xiaohuashifu.share.auth;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.DeclareParents;
import org.springframework.stereotype.Component;

/**
 * 描述: 认证能力切面
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
@Aspect
@Component
public class AuthableAspect {

    @DeclareParents(value = "top.xiaohuashifu.share.controller.v1.api.*", defaultImpl = AuthableImpl.class)
    public Authable authable;

}
