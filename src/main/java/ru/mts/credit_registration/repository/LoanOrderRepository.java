package ru.mts.credit_registration.repository;

import ru.mts.credit_registration.entity.LoanOrderEntity;
import ru.mts.credit_registration.enums.LoanOrderStatus;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface LoanOrderRepository {

    LoanOrderEntity save(LoanOrderEntity order);

    Optional<List<LoanOrderEntity>> findOrdersByUserId(Long userId);

    Optional<List<LoanOrderEntity>> findByStatus(LoanOrderStatus status);

    Optional<LoanOrderStatus> findStatusByOrderId(String orderId);

    Optional<LoanOrderEntity> findByUserIdAndOrderId(Long userId, String orderId);

    void updateTimeUpdateByOrderId(String orderId, Timestamp timeUpdate);

    void updateStatusByOrderId(String orderId, LoanOrderStatus status);

    void deleteByUserIdAndOrderId(Long userId, String orderId);

}
