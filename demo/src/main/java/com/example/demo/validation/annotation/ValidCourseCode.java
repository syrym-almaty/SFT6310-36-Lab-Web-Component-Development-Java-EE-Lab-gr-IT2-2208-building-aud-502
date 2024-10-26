import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CourseCodeValidator.class) // Определяет класс валидатора
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCourseCode {
    String message() default "Invalid course code";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
