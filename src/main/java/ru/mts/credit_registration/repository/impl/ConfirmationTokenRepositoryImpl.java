package ru.mts.credit_registration.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.mts.credit_registration.entity.ConfirmationTokenEntity;
import ru.mts.credit_registration.repository.ConfirmationTokenRepository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ConfirmationTokenRepositoryImpl implements ConfirmationTokenRepository {

    private final JdbcTemplate jdbcTemplate;

    private static final String SQL_SELECT_BY_TOKEN = "SELECT * FROM confirmation_token WHERE token = ?";

    private static final String SQL_UPDATE_CONFIRMED = "UPDATE confirmation_token SET confirmedAt = ? WHERE token = ?";

    private static final String SQL_SAVE_CONFIRMATION_TOKEN =
            "INSERT INTO confirmation_token (token, createdAt, expiresAt, confirmedAt, user_id) VALUES (?, ?, ?, ?, ?)";

    @Override
    public Optional<ConfirmationTokenEntity> findByToken(String token) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_SELECT_BY_TOKEN,
                            new BeanPropertyRowMapper<>(ConfirmationTokenEntity.class),
                            token
                    )
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void updateConfirmedAt(String token, LocalDateTime confirmedAt) {
        jdbcTemplate.update(
                SQL_UPDATE_CONFIRMED,
                confirmedAt, token
        );
    }

    @Override
    public ConfirmationTokenEntity save(ConfirmationTokenEntity token) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps =
                    connection.prepareStatement(SQL_SAVE_CONFIRMATION_TOKEN,
                            Statement.RETURN_GENERATED_KEYS
                    );

            ps.setString(1, token.getToken());
            ps.setTimestamp(2, Timestamp.valueOf(token.getCreatedAt()));
            ps.setTimestamp(3, Timestamp.valueOf(token.getExpiresAt()));

            LocalDateTime confirmedAt = token.getConfirmedAt();
            ps.setTimestamp(4, confirmedAt == null ? null : Timestamp.valueOf(confirmedAt));

            ps.setLong(5, token.getUserId());

            return ps;
        }, keyHolder);

        token.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return token;
    }
}
