package top.xiaohuashifu.share.validator;

import top.xiaohuashifu.share.validator.annotation.Password;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 描述: password校验器
 *  密码长度必须在6~20位之间
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
public class PasswordValidator implements ConstraintValidator<Password, String> {

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        if (password == null) {
            return true;
        }
        return password.length() >= 6 && password.length() <= 20;
    }
}
