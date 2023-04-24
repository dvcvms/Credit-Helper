package ru.mts.credit_registration.entity;

import lombok.Data;

@Data
public class TariffEntity {
    private Long id;
    private String type;
    private String interestRate;
}
