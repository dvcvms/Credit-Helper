package ru.mts.credit_registration.util;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.mts.credit_registration.entity.LoanOrderEntity;
import ru.mts.credit_registration.enums.LoanOrderStatus;
import ru.mts.credit_registration.serivce.LoanOrderService;

import java.sql.Timestamp;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
@EnableAsync
public class ScheduledJobsService {

    private final Random random = new Random();

    private final LoanOrderService orderService;

    @Async
    @Scheduled(
            initialDelayString = "${spring.scheduler.initDelay}",
            fixedRateString = "${spring.scheduler.fixedRate}"
    )
    @Transactional
    public void considerationApplicationJob() {
        List<LoanOrderEntity> inProgress = orderService.getByStatus(LoanOrderStatus.IN_PROGRESS);

        for (LoanOrderEntity order : inProgress) {
            LoanOrderStatus currentStatus = nextBoolean()
                    ? LoanOrderStatus.APPROVED
                    : LoanOrderStatus.REFUSED;

            orderService.setStatusByOrderId(order.getOrderId(), currentStatus);
            orderService.setTimeUpdateByOrderId(order.getOrderId(), new Timestamp(System.currentTimeMillis()));
        }
    }

    private boolean nextBoolean() {
        return random.nextBoolean();
    }
}
