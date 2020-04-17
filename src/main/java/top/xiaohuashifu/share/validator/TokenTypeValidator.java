package top.xiaohuashifu.share.validator;

import top.xiaohuashifu.share.constant.TokenType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 描述: TokenType校验器
 *  必须符合是 [USER, ADMIN] 中的一个
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
public class TokenTypeValidator
        implements ConstraintValidator<top.xiaohuashifu.share.validator.annotation.TokenType, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null) {
            return true;
        }
        for (TokenType tokenType : TokenType.values()) {
            if (s.equals(tokenType.name())) {
                return true;
            }
        }
        return false;
    }
}
