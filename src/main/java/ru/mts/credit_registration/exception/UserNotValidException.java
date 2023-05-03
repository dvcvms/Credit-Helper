package ru.mts.credit_registration.exception;

public class UserNotValidException extends CustomException {

    public UserNotValidException(String code, String message) {
        super(code, message);
    }
}
