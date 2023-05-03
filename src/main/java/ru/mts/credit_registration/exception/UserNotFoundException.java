package ru.mts.credit_registration.exception;

public class UserNotFoundException extends CustomException {

    public UserNotFoundException(String code, String message) {
        super(code, message);
    }
}
