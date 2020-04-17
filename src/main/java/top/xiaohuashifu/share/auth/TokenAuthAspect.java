package top.xiaohuashifu.share.auth;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import top.xiaohuashifu.share.constant.TokenExpire;
import top.xiaohuashifu.share.constant.TokenType;
import top.xiaohuashifu.share.pojo.ao.TokenAO;
import top.xiaohuashifu.share.result.ErrorCode;
import top.xiaohuashifu.share.result.ErrorResponse;
import top.xiaohuashifu.share.result.Result;
import top.xiaohuashifu.share.service.TokenService;

import javax.servlet.http.HttpServletRequest;


/**
 * 描述: 身份认证切面
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
@Aspect
@Component("tokenAuthAspect")
public class TokenAuthAspect {

    private final TokenService tokenService;

    @Autowired
    public TokenAuthAspect(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    /**
     * 认证token令牌
     *
     * @param joinPoint ProceedingJoinPoint
     * @return Object
     * 会把TokenAO设置在request.attribute.tokenAO里
     *
     * @throws Throwable .
     *
     * @errors:
     * UNAUTHORIZED
     * UNAUTHORIZED_TOKEN_IS_NULL
     * FORBIDDEN_SUB_USER
     */
    @Around(value = "@annotation(top.xiaohuashifu.share.auth.TokenAuth) && @annotation(tokenAuth) && args(..)")
    public Object authToken(ProceedingJoinPoint joinPoint, TokenAuth tokenAuth) throws Throwable {
        Authable authable = (Authable) joinPoint.getThis();
        HttpServletRequest request = authable.getRequest();

        String token = request.getHeader("authorization");
        // token不在头部
        if (token == null) {
            ErrorResponse errorResponse = new ErrorResponse(ErrorCode.UNAUTHORIZED_TOKEN_IS_NULL.getError(),
                    ErrorCode.UNAUTHORIZED_TOKEN_IS_NULL.getMessage());
            return new ResponseEntity<>(errorResponse, ErrorCode.UNAUTHORIZED_TOKEN_IS_NULL.getHttpStatus());
        }

        TokenType[] tokenTypes = tokenAuth.tokenType();
        Result<TokenAO> result = tokenService.getTokenAndAuthTokenTypeAndUpdateExpire(token, tokenTypes,
                TokenExpire.NORMAL.getExpire());
        if (!result.isSuccess()) {
            ErrorResponse errorResponse = new ErrorResponse(result.getErrorCode().getError(),
                    result.getErrorCode().getMessage());
            return new ResponseEntity<>(errorResponse, result.getErrorCode().getHttpStatus());
        }

        // 把此tokenAO传递给控制器，如果该控制器带有TokenAO类型的参数的话
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof TokenAO) {
                TokenAO tokenAO = result.getData();
                ((TokenAO) arg).setId(tokenAO.getId());
                ((TokenAO) arg).setToken(tokenAO.getToken());
                ((TokenAO) arg).setType(tokenAO.getType());
                break;
            }
        }
        return joinPoint.proceed();
    }

}
