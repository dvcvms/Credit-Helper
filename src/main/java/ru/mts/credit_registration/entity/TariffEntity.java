package ru.mts.credit_registration.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TariffEntity {
    private Long id;
    private String type;
    private String interestRate;
}
