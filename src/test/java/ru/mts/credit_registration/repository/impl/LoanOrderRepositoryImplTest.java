package ru.mts.credit_registration.repository.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import ru.mts.credit_registration.config.PersistenceLayerTestConfig;
import ru.mts.credit_registration.entity.LoanOrderEntity;
import ru.mts.credit_registration.enums.LoanOrderStatus;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@TestPropertySource(properties = {
        "spring.sql.init.data-locations=classpath:data-order-tariff-test.sql"
})
@ContextConfiguration(classes = {
        PersistenceLayerTestConfig.class,
        LoanOrderRepositoryImpl.class
})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@EnableJpaRepositories(basePackages = {"ru.mts.credit_registration"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LoanOrderRepositoryImplTest {

    private static LoanOrderEntity testOrder;
    @Autowired
    private LoanOrderRepositoryImpl loanOrderRepository;

    @BeforeAll
    public void init() {
        LoanOrderEntity order = new LoanOrderEntity()
                .setOrderId(UUID.randomUUID().toString())
                .setUserId(1L)
                .setTariffId(1L)
                .setCreditRating(0.4)
                .setStatus(LoanOrderStatus.APPROVED)
                .setTimeInsert(new Timestamp(System.currentTimeMillis()));

        testOrder = loanOrderRepository.save(order);
    }

    @Test
    public void findOrdersByUserId() {
        Long userId = testOrder.getUserId();

        List<LoanOrderEntity> actualOrders = loanOrderRepository.findOrdersByUserId(userId).get();

        assertThat(actualOrders.size()).isEqualTo(1);
        assertThat(actualOrders.get(0)).isEqualTo(testOrder);
    }

    @Test
    public void save() {
        String orderId = UUID.randomUUID().toString();
        LoanOrderEntity expectedOrder = new LoanOrderEntity()
                .setOrderId(orderId)
                .setUserId(17L)
                .setTariffId(2L)
                .setCreditRating(0.62)
                .setStatus(LoanOrderStatus.REFUSED)
                .setTimeInsert(new Timestamp(System.currentTimeMillis()));

        LoanOrderEntity actualOrder = loanOrderRepository.save(expectedOrder);

        assertThat(actualOrder.getId()).isEqualTo(actualOrder.getId());
        assertThat(actualOrder.getOrderId()).isEqualTo(actualOrder.getOrderId());
        assertThat(actualOrder.getStatus()).isEqualTo(actualOrder.getStatus());
        assertThat(actualOrder.getTimeInsert()).isEqualTo(actualOrder.getTimeInsert());
    }

    @Test
    public void findStatusByOrderId() {
        String orderId = testOrder.getOrderId();
        LoanOrderStatus expectedStatus = testOrder.getStatus();

        LoanOrderStatus actualStatus = loanOrderRepository.findStatusByOrderId(orderId).get();

        assertThat(actualStatus).isEqualTo(expectedStatus);
    }

    @Test
    public void deleteByUserIdAndOrderId() {
        String orderId = UUID.randomUUID().toString();
        Long userId = 1L;
        LoanOrderEntity orderToSave = new LoanOrderEntity()
                .setOrderId(orderId)
                .setUserId(userId)
                .setTariffId(1L)
                .setCreditRating(0.4D)
                .setStatus(LoanOrderStatus.REFUSED)
                .setTimeInsert(new Timestamp(System.currentTimeMillis()));

        loanOrderRepository.save(orderToSave);
        loanOrderRepository.deleteByUserIdAndOrderId(userId, orderId);
        Optional<LoanOrderEntity> actualOrder =
                loanOrderRepository.findByUserIdAndOrderId(userId, orderId);

        assertThat(actualOrder).isEmpty();
    }

    @Test
    public void findByUserIdAndOrderId() {
        Long userId = testOrder.getUserId();
        String orderId = testOrder.getOrderId();
        Double creditRating = testOrder.getCreditRating();

        LoanOrderEntity actualOrder =
                loanOrderRepository.findByUserIdAndOrderId(userId, orderId).get();

        assertThat(actualOrder.getOrderId()).isEqualTo(orderId);
        assertThat(actualOrder.getUserId()).isEqualTo(userId);
        assertThat(actualOrder.getStatus()).isEqualTo(LoanOrderStatus.APPROVED);
        assertThat(actualOrder.getCreditRating()).isEqualTo(creditRating);
    }

    @Test
    public void findByStatus() {
        LoanOrderStatus status = LoanOrderStatus.IN_PROGRESS;

        List<LoanOrderEntity> actualOrders =
                loanOrderRepository.findByStatus(status).get();


        assertThat(actualOrders.size()).isEqualTo(1);
        assertThat(actualOrders.get(0).getStatus()).isEqualTo(status);
    }

    @Test
    public void updateStatusByOrderId() {
        String orderId = testOrder.getOrderId();
        Long userId = testOrder.getUserId();
        LoanOrderStatus expectedStatus = LoanOrderStatus.REFUSED;

        loanOrderRepository.updateStatusByOrderId(orderId, expectedStatus);
        LoanOrderStatus actualStatus =
                loanOrderRepository.findByUserIdAndOrderId(userId, orderId).get().getStatus();

        assertThat(actualStatus).isEqualTo(expectedStatus);
    }

    @Test
    public void updateTimeUpdateByOrderId() {
        String orderId = testOrder.getOrderId();
        Long userId = testOrder.getUserId();
        Timestamp timeUpdate = new Timestamp(System.currentTimeMillis());

        loanOrderRepository.updateTimeUpdateByOrderId(orderId, timeUpdate);
        Timestamp actualTimeupdate =
                loanOrderRepository.findByUserIdAndOrderId(userId, orderId).get().getTimeUpdate();

        assertThat(actualTimeupdate).isNotNull();
        assertThat(actualTimeupdate.getTime()).isEqualTo(timeUpdate.getTime());
    }
}