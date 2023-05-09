package ru.mts.credit_registration.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.mts.credit_registration.entity.LoanOrderEntity;
import ru.mts.credit_registration.enums.LoanOrderStatus;
import ru.mts.credit_registration.repository.LoanOrderRepository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LoanOrderRepositoryImpl implements LoanOrderRepository {

    private final JdbcTemplate jdbcTemplate;

    private static final String SQL_GET_ORDERS_BY_USER_ID = "SELECT * FROM loan_order WHERE user_id = ?";

    private static final String SQL_SAVE_ORDER =
            "INSERT INTO loan_order (order_id, user_id, tariff_id, credit_rating, status, time_insert) VALUES (?, ?, ?, ?, ?, ?)";

    private static final String SQL_SELECT_STATUS_BY_ORDER_ID = "SELECT STATUS FROM loan_order WHERE order_id = ?";

    private static final String SQL_DELETE_ORDER_BY_USER_ID_AND_ORDER_ID = "DELETE FROM loan_order WHERE user_id = ? AND order_id = ?";

    private static final String SQL_SELECT_BY_USER_ID_AND_ORDER_ID = "SELECT * FROM loan_order WHERE user_id = ? AND order_id = ?";

    private static final String SQL_SELECT_BY_STATUS = "SELECT * FROM loan_order WHERE status = ?";

    private static final String SQL_UPDATE_STATUS_BY_ORDER_ID = "UPDATE loan_order SET status = ? WHERE order_id = ?";

    private static final String SQL_UPDATE_TIME_UPDATE_BY_ORDER_ID = "UPDATE loan_order SET time_update = ? where order_id = ?";

    @Override
    public Optional<List<LoanOrderEntity>> findOrdersByUserId(Long userId) {
        return Optional.of(jdbcTemplate.query(SQL_GET_ORDERS_BY_USER_ID,
                new BeanPropertyRowMapper<>(LoanOrderEntity.class),
                userId)
        );
    }

    @Override
    public LoanOrderEntity save(LoanOrderEntity order) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(SQL_SAVE_ORDER, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, order.getOrderId());
            ps.setLong(2, order.getUserId());
            ps.setLong(3, order.getTariffId());
            ps.setDouble(4, order.getCreditRating());
            ps.setString(5, order.getStatus().toString());
            ps.setTimestamp(6, order.getTimeInsert());

            return ps;
        }, keyHolder);

        order.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return order;
    }

    @Override
    public Optional<LoanOrderStatus> findStatusByOrderId(String orderId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                            SQL_SELECT_STATUS_BY_ORDER_ID,
                            LoanOrderStatus.class,
                            orderId
                    )
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void deleteByUserIdAndOrderId(Long userId, String orderId) {
        jdbcTemplate.update(
                SQL_DELETE_ORDER_BY_USER_ID_AND_ORDER_ID,
                userId,
                orderId
        );
    }

    @Override
    public Optional<LoanOrderEntity> findByUserIdAndOrderId(Long userId, String orderId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                            SQL_SELECT_BY_USER_ID_AND_ORDER_ID,
                            new BeanPropertyRowMapper<>(LoanOrderEntity.class),
                            userId,
                            orderId
                    )
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<List<LoanOrderEntity>> findByStatus(LoanOrderStatus status) {
        List<LoanOrderEntity> query = jdbcTemplate.query(
                SQL_SELECT_BY_STATUS,
                new BeanPropertyRowMapper<>(LoanOrderEntity.class),
                status.toString()
        );
        return Optional.of(query);
    }

    @Override
    public void updateStatusByOrderId(String orderId, LoanOrderStatus status) {
        jdbcTemplate.update(
                SQL_UPDATE_STATUS_BY_ORDER_ID,
                status.toString(),
                orderId
        );
    }

    @Override
    public void updateTimeUpdateByOrderId(String orderId, Timestamp timeUpdate) {
        jdbcTemplate.update(
                SQL_UPDATE_TIME_UPDATE_BY_ORDER_ID,
                timeUpdate,
                orderId
        );
    }
}
