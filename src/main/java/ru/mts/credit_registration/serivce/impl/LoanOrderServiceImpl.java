package ru.mts.credit_registration.serivce.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mts.credit_registration.entity.LoanOrderEntity;
import ru.mts.credit_registration.enums.LoanOrderStatus;
import ru.mts.credit_registration.exception.CustomException;
import ru.mts.credit_registration.model.*;
import ru.mts.credit_registration.repository.LoanOrderRepository;
import ru.mts.credit_registration.serivce.LoanOrderService;

import java.sql.Timestamp;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LoanOrderServiceImpl implements LoanOrderService {

    private final TariffServiceImpl tariffService;
    private final LoanOrderRepository orderRepository;

    public DataResponse<DataLoanOrderResponse> createOrder(LoanApplicationRequest request) {

        if (!tariffService.existsById(request.getTariffId())) {
            throw new CustomException("TARIFF_NOT_FOUND", "Тариф не найден");
        }

        List<LoanOrderEntity> orders =
                orderRepository.findOrdersByUserId(request.getUserId()).orElseThrow();

        for (LoanOrderEntity order : orders) {
            if (order.getTariffId().equals(request.getTariffId())) {
                LoanOrderStatus status = order.getStatus();

                switch (status) {
                    case IN_PROGRESS ->
                            throw new CustomException("LOAN_CONSIDERATION", "Заявка на кредит находится на рассмотрении");
                    case APPROVED ->
                            throw new CustomException("LOAN_ALREADY_APPROVED", "Заявка на кредит уже утверждена");
                    case REFUSED -> {
                        Timestamp now = new Timestamp(System.currentTimeMillis());
                        Timestamp prev = order.getTimeUpdate();

                        if ((now.getTime() - prev.getTime()) < 2 * 60 * 1000) {
                            throw new CustomException("TRY_LATER", "Попробуйте позже");
                        }
                    }
                }
            }
        }

        LoanOrderEntity newOrder = new LoanOrderEntity()
                .setOrderId(UUID.randomUUID().toString())
                .setUserId(request.getUserId())
                .setTariffId(request.getTariffId())
                .setCreditRating(generateRandomRating())
                .setStatus(LoanOrderStatus.IN_PROGRESS)
                .setTimeInsert(new Timestamp(System.currentTimeMillis()));

        return new DataResponse<>(new DataLoanOrderResponse(orderRepository.save(newOrder)));
    }

    public DataResponse<DataStatusResponse> getStatusByOrderId(String orderId) {
        return new DataResponse<>(
                new DataStatusResponse(
                        orderRepository.findStatusByOrderId(orderId).orElseThrow(() -> new RuntimeException("ORDER_NOT_FOUND"))
                )
        );
    }

    public void deleteOrder(DeleteApplicationRequest request) {
        LoanOrderEntity order =
                orderRepository.findByUserIdAndOrderId(request.getUserId(), request.getOrderId())
                        .orElseThrow(() -> new RuntimeException("ORDER_NOT_FOUND"));

        if (!(order.getStatus() == LoanOrderStatus.IN_PROGRESS)) {
            throw new RuntimeException("ORDER_IMPOSSIBLE_TO_DELETE");
        }

        orderRepository.deleteByUserIdAndOrderId(request.getUserId(), request.getOrderId());
    }

    @Override
    public List<LoanOrderEntity> getByStatus(String status) {
        return orderRepository.findByStatus(status).orElseThrow();
    }

    @Override
    public void setStatusByOrderId(String orderId, String status) {
        orderRepository.updateStatusByOrderId(orderId, status);
    }

    @Override
    public void setTimeUpdateByOrderId(String orderId, Timestamp timeUpdate) {
        orderRepository.updateTimeUpdateByOrderId(orderId, timeUpdate);
    }

    private double generateRandomRating() {
        Random random = new Random();
        return 0.1 + random.nextDouble() * 0.8;
    }
}
