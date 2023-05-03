package ru.mts.credit_registration.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mts.credit_registration.entity.UserEntity;
import ru.mts.credit_registration.exception.UserNotValidException;
import ru.mts.credit_registration.serivce.impl.UserServiceImpl;

@Service
@RequiredArgsConstructor
public class UserValidator {

    private final UserServiceImpl userService;

    public void validate(UserEntity user) {
        checkEmail(user.getEmail());
        checkUsername(user.getUsername());
    }

    private void checkEmail(String email) {
        if (userService.existsByEmail(email)) {
            throw new UserNotValidException("USERNAME_IS_ALREADY_IN_USE",
                    String.format("Пользователь с почтой `%s` уже существует", email)
            );
        }
    }

    private void checkUsername(String username) {
        if (userService.existsByUsername(username)) {
            throw new UserNotValidException("EMAIL_IS_ALREADY_IN_USE",
                    String.format("Пользователь с ником `%s` уже существует", username)
            );
        }
    }
}
