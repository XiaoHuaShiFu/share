package top.xiaohuashifu.share.validator;

import top.xiaohuashifu.share.validator.annotation.Username;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 描述: 用户名校验器
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
public class UsernameValidator implements ConstraintValidator<Username, String> {

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        if (username == null) {
            return true;
        }

        return username.length() >= 4 && username.length() <= 30;
    }
}
