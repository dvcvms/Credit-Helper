package ru.mts.credit_registration.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import ru.mts.credit_registration.enums.LoanOrderStatus;

import java.sql.Timestamp;

@Data
@Accessors(chain = true)
public class LoanOrderEntity {
    private Long id;
    private String orderId;
    private Long userId;
    private Long tariffId;
    private Double creditRating;
    private LoanOrderStatus status;
    private Timestamp timeInsert;
    private Timestamp timeUpdate;
}
