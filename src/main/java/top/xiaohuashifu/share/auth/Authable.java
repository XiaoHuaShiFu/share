package top.xiaohuashifu.share.auth;

import javax.servlet.http.HttpServletRequest;

/**
 * 描述: 认证能力声明接口
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
public interface Authable {

    HttpServletRequest getRequest();

}
