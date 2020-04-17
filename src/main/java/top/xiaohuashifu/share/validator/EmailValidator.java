package top.xiaohuashifu.share.validator;

import top.xiaohuashifu.share.validator.annotation.Email;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 描述: 电子邮件校验器
 *  长度必须小于100
 *  必须符合正则表达式 ^\\s*\\w+(?:\\.?[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
public class EmailValidator implements ConstraintValidator<Email, String> {

    /**
     * 电子邮箱匹配模式
     */
    private static final String REGEX_EMAIL =
            "^\\s*\\w+(?:\\.?[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";

    /**
     * 构造静态的匹配模式
     */
    private static final Pattern p = Pattern.compile(REGEX_EMAIL);

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        if (email == null) {
            return true;
        }
        if (email.length() > 100) {
            return false;
        }
        Matcher matcher = p.matcher(email);
        return matcher.matches();
    }
}
