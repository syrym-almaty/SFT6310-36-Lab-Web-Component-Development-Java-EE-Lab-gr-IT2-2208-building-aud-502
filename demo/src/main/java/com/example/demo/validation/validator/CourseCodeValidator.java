import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CourseCodeValidator implements ConstraintValidator<ValidCourseCode, String> {
    @Override
    public void initialize(ValidCourseCode constraintAnnotation) {
        // Инициализация, если требуется
    }

    @Override
    public boolean isValid(String code, ConstraintValidatorContext context) {
        // Логика валидации: формат кода курса — 4 буквы и 4 цифры
        return code != null && code.matches("^[A-Z]{4}\\d{4}$");
    }
}
