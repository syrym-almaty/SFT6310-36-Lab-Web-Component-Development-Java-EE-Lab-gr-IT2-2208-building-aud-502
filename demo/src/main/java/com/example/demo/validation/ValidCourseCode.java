import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = CourseCodeValidator.class) 
@Target({ ElementType.FIELD }) 
@Retention(RetentionPolicy.RUNTIME) 
public @interface ValidCourseCode {
    String message() default "Invalid course code"; 
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}



import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CourseCodeValidator implements ConstraintValidator<ValidCourseCode, String> {

    @Override
    public void initialize(ValidCourseCode constraintAnnotation) {
    }

    @Override
    public boolean isValid(String code, ConstraintValidatorContext context) {
        if (code == null || code.isEmpty()) {
            return true; 
        }
        return code.matches("^[A-Z]{4}\\d{4}$");
    }
}
