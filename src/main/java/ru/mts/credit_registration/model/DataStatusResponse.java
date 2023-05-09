package ru.mts.credit_registration.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.mts.credit_registration.enums.LoanOrderStatus;

@Data
@AllArgsConstructor
public class DataStatusResponse {
    private LoanOrderStatus orderStatus;
}
