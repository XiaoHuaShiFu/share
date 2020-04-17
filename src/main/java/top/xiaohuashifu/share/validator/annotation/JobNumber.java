package top.xiaohuashifu.share.validator.annotation;

import top.xiaohuashifu.share.validator.JobNumberValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 描述: 工号校验
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
@Documented
@Constraint(validatedBy = {JobNumberValidator.class})
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(JobNumber.List.class)
public @interface JobNumber {

    String message() default "INVALID_PARAMETER: The parameter of jobNumber is invalid.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        JobNumber[] value();
    }

}
