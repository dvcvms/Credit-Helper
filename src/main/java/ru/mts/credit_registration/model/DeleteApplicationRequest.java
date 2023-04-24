package ru.mts.credit_registration.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeleteApplicationRequest {
    private Long userId;
    private String orderId;
}
