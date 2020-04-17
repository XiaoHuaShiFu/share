package top.xiaohuashifu.share.service.impl;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.xiaohuashifu.share.constant.TokenExpire;
import top.xiaohuashifu.share.constant.TokenType;
import top.xiaohuashifu.share.pojo.ao.TokenAO;
import top.xiaohuashifu.share.pojo.do0.AdminDO;
import top.xiaohuashifu.share.pojo.do0.UserDO;
import top.xiaohuashifu.share.result.ErrorCode;
import top.xiaohuashifu.share.result.Result;
import top.xiaohuashifu.share.service.AdminService;
import top.xiaohuashifu.share.service.CacheService;
import top.xiaohuashifu.share.service.TokenService;
import top.xiaohuashifu.share.service.UserService;
import top.xiaohuashifu.share.service.constant.RedisStatus;
import top.xiaohuashifu.share.util.SHA256;

import java.util.UUID;

/**
 * 描述: Token相关服务
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
@Service("tokenService")
public class TokenServiceImpl implements TokenService {

    private static final Logger logger = LoggerFactory.getLogger(TokenServiceImpl.class);

    private final CacheService cacheService;

    private final UserService userService;

    private final AdminService adminService;

    private final Gson gson;

    @Autowired
    public TokenServiceImpl(CacheService cacheService, UserService userService, AdminService adminService, Gson gson) {
        this.cacheService = cacheService;
        this.userService = userService;
        this.adminService = adminService;
        this.gson = gson;
    }

    /**
     * 在缓存里添加token，并设置过期时间
     *
     * @param tokenAO TokenAO
     * @return Result<TokenAO>
     */
    @Override
    public Result<TokenAO> saveToken(TokenAO tokenAO) {
        return saveToken(tokenAO, TokenExpire.NORMAL.getExpire());
    }

    /**
     * 在缓存里添加token，并设置过期时间
     *
     * @param tokenAO TokenAO
     * @param seconds 过期时间
     * @return Result<TokenAO>
     */
    @Override
    public Result<TokenAO> saveToken(TokenAO tokenAO, int seconds) {
        //保存token
        String code = cacheService.set(tokenAO.getToken(), gson.toJson(tokenAO));

        //保存失败
        if (!code.equals(RedisStatus.OK.name())) {
            logger.error("Failed to create token, type: {} and id: {}", tokenAO.getToken(), tokenAO.getId());
            return Result.fail(ErrorCode.INTERNAL_ERROR, "Failed to create token.");
        }

        //设置过期时间
        Long result = cacheService.expire(tokenAO.getToken(), TokenExpire.NORMAL.getExpire());
        if (result.equals(0L)) {
            cacheService.del(tokenAO.getToken());
            logger.error("Failed to set expire, type: {} and id: {}", tokenAO.getToken(), tokenAO.getId());
            return Result.fail(ErrorCode.INTERNAL_ERROR, "Failed to set expire.");
        }

        return Result.success(tokenAO);
    }

    /**
     * 获取token
     *
     * @param token token
     * @return Result<TokenAO>
     */
    @Override
    public Result<TokenAO> getToken(String token) {
        String jsonToken = cacheService.get(token);
        //token不在
        if (jsonToken == null) {
            return Result.fail(ErrorCode.UNAUTHORIZED);
        }

        return Result.success(gson.fromJson(jsonToken, TokenAO.class));
    }

    /**
     * 获取token并认证是不是可以通过认证的类型并更新过期时间
     *
     * @param token 要认证类型
     * @param tokenTypes 可以通过认证的列表
     * @param seconds 过期时间长度
     *
     * @return Result<TokenAO>
     */
    @Override
    public Result<TokenAO> getTokenAndAuthTokenTypeAndUpdateExpire(String token, TokenType[] tokenTypes, int seconds) {
        Result<TokenAO> result = getToken(token);
        if (!result.isSuccess()) {
            return Result.fail(result.getErrorCode(), result.getMessage());
        }

        //不是所需的token类型
        TokenAO tokenAO = result.getData();
        if (!authTokenType(tokenAO.getType(), tokenTypes)) {
            return Result.fail(ErrorCode.FORBIDDEN_SUB_USER);
        }

        //更新token过期时间
        Long expire = cacheService.expire(result.getData().getToken(), seconds);
        if (expire == 0) {
            logger.warn("Set redis expire fail, token: {} id: {} type: {}",
                    tokenAO.getToken(), tokenAO.getId(), tokenAO.getType());
        }

        return result;
    }

    /**
     * 创建token并保存到redis里
     *
     * @param username 用户名
     * @param password 密码
     * @param tokenType token类型
     * @return Result<TokenAO>
     */
    @Override
    public Result<TokenAO> createAndSaveToken(TokenType tokenType, String username, String password) {
        TokenAO tokenAO = new TokenAO();
        if (tokenType == TokenType.ADMIN) {
            Result<AdminDO> result = adminService.getAdminByUsername(username);
            if (!result.isSuccess()) {
                return Result.fail(result.getErrorCode(), result.getMessage());
            }
            AdminDO admin = result.getData();
            if (!admin.getPassword().equals(password)) {
                return Result.fail(ErrorCode.UNAUTHORIZED, "Wrong password.");
            }

            tokenAO.setId(admin.getId());
            tokenAO.setType(tokenType);
        } else if (tokenType == TokenType.USER) {
            Result<UserDO> result = userService.getUserByUsername(username);
            if (!result.isSuccess()) {
                return Result.fail(result.getErrorCode(), result.getMessage());
            }

            UserDO user = result.getData();
            if (!user.getPassword().equals(password)) {
                return Result.fail(ErrorCode.UNAUTHORIZED, "Wrong password.");
            }

            tokenAO.setId(user.getId());
            tokenAO.setType(tokenType);
        }


        String token = createToken();
        tokenAO.setToken(token);
        return saveToken(tokenAO);
    }

    /**
     * 认证tokenType
     *
     * @param type 要认证类型
     * @param tokenTypes 可以通过认证的列表
     * @return 是否认证成功
     */
    private boolean authTokenType(TokenType type, TokenType[] tokenTypes) {
        if (tokenTypes.length == 0) {
            return true;
        }
        for (TokenType tokenType : tokenTypes) {
            if (tokenType == type) {
                return true;
            }
        }
        return false;
    }

    /**
     * 创建token
     *
     * @return token
     */
    private String createToken() {
        return SHA256.encryption(UUID.randomUUID().toString());
    }

}
