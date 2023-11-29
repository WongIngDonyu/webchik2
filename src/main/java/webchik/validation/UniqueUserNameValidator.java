package webchik.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import webchik.repositories.UserRepository;

public class UniqueUserNameValidator implements ConstraintValidator<UniqueUserName, String> {
    private final UserRepository userRepository;

    public UniqueUserNameValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(String string, ConstraintValidatorContext constraintValidatorContext) {
        return userRepository.findByUsername(string).isEmpty();
    }
}
