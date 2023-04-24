package ru.mts.credit_registration.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExceptionResponse<T> {
    private T error;
}
