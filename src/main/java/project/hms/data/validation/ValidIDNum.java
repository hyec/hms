package project.hms.data.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Size;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = IDNumValidator.class)
@Documented
@Size(min = 18, max = 18, message = "身份证号位数应为18位！")
public @interface ValidIDNum {

    String message() default "Wrong ID number.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
