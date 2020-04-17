package top.xiaohuashifu.share.constant;

/**
 * 描述: token的有效时间
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
public enum TokenExpire {

    /**
     * 正常token过期时间
     */
    NORMAL(72000);

    private final int expire;

    TokenExpire(int expire) {
        this.expire = expire;
    }

    public int getExpire() {
        return expire;
    }
}
