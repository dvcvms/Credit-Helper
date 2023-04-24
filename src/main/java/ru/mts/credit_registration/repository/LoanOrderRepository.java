package ru.mts.credit_registration.repository;

import ru.mts.credit_registration.entity.LoanOrderEntity;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface LoanOrderRepository {

    String save(LoanOrderEntity order);

    Optional<List<LoanOrderEntity>> findOrdersByUserId(Long userId);

    Optional<List<LoanOrderEntity>> findByStatus(String status);

    Optional<String> findStatusByOrderId(String orderId);

    Optional<LoanOrderEntity> findByUserIdAndOrderId(Long userId, String orderId);

    void updateTimeUpdateByOrderId(String orderId, Timestamp timeUpdate);

    void updateStatusByOrderId(String orderId, String status);

    void deleteByUserIdAndOrderId(Long userId, String orderId);

}
