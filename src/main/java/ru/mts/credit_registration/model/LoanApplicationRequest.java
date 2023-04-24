package ru.mts.credit_registration.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoanApplicationRequest {
    private Long userId;
    private Long tariffId;
}
