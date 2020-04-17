package top.xiaohuashifu.share.pojo.ao;

import top.xiaohuashifu.share.constant.TokenType;

/**
 * 描述: Token对象
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
public class TokenAO {

    private Integer id;

    private TokenType type;

    private String token;

    public TokenAO() {
    }

    public TokenAO(Integer id, TokenType type, String token) {
        this.id = id;
        this.type = type;
        this.token = token;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TokenType getType() {
        return type;
    }

    public void setType(TokenType type) {
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "TokenAO{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
