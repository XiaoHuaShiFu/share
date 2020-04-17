package top.xiaohuashifu.share.service;

import top.xiaohuashifu.share.constant.TokenType;
import top.xiaohuashifu.share.pojo.ao.TokenAO;
import top.xiaohuashifu.share.result.Result;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
public interface TokenService {

    Result<TokenAO> saveToken(TokenAO tokenAO);

    Result<TokenAO> saveToken(TokenAO tokenAO, int seconds);

    Result<TokenAO> createAndSaveToken(TokenType tokenType, String username, String password);

    Result<TokenAO> getToken(String token);

    Result<TokenAO> getTokenAndAuthTokenTypeAndUpdateExpire(String token, TokenType[] tokenTypes, int seconds);

}
