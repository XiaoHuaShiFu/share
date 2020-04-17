package top.xiaohuashifu.share.validator;

import top.xiaohuashifu.share.validator.annotation.Url;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 描述: Url校验器
 *  必须符合长度0~255
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
public class UrlValidator implements ConstraintValidator<Url, String> {

    private static final int MAX_URL_LENGTH = 255;

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null) {
            return true;
        }
        return s.length() <= MAX_URL_LENGTH;
    }
}
