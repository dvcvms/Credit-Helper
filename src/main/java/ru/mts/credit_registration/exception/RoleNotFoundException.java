package ru.mts.credit_registration.exception;

public class RoleNotFoundException extends CustomException {

    public RoleNotFoundException(String code, String message) {
        super(code, message);
    }
}
