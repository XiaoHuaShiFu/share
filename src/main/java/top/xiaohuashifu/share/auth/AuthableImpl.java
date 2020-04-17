package top.xiaohuashifu.share.auth;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 描述: 认证能力实现类
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
@Component
public class AuthableImpl implements Authable {

    @Override
    public HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

}
