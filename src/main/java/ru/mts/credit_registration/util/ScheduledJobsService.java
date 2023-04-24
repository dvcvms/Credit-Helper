package ru.mts.credit_registration.util;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mts.credit_registration.entity.LoanOrderEntity;
import ru.mts.credit_registration.serivce.LoanOrderService;

import java.sql.Timestamp;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
@EnableAsync
public class ScheduledJobsService {

    private final Random random = new Random();

    private final LoanOrderService orderService;

    @Async
    @Scheduled(initialDelay = 1000 * 5, fixedRate = 1000 * 3) // TODO: do param`s in properties file
    @Transactional
    public void considerationApplicationJob() {
        List<LoanOrderEntity> inProgress = orderService.getByStatus("IN_PROGRESS");

        for (LoanOrderEntity order : inProgress) {
            String currentStatus = nextBoolean() ? "APPROVED" : "REFUSED";

            orderService.setStatusByOrderId(order.getOrderId(), currentStatus);
            orderService.setTimeUpdateByOrderId(order.getOrderId(), new Timestamp(System.currentTimeMillis()));
        }
    }

    private boolean nextBoolean() {
        return random.nextBoolean();
    }
}
