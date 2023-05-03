package ru.mts.credit_registration.exception;

public class ConfirmationTokenNotValidException extends CustomException {

    public ConfirmationTokenNotValidException(String code, String message) {
        super(code, message);
    }
}
