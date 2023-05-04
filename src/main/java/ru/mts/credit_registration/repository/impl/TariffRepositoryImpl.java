package ru.mts.credit_registration.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.mts.credit_registration.entity.TariffEntity;
import ru.mts.credit_registration.repository.TariffRepository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TariffRepositoryImpl implements TariffRepository {

    private final JdbcTemplate jdbcTemplate;

    private static final String SQL_SELECT_ALL = "SELECT * FROM tariff";

    private static final String SQL_EXISTS_BY_ID = "SELECT EXISTS (SELECT * FROM tariff WHERE id = ?)";

    @Override
    public Optional<List<TariffEntity>> findAll() {
        return Optional.of(jdbcTemplate.query(SQL_SELECT_ALL, new BeanPropertyRowMapper<>(TariffEntity.class)));
    }

    @Override
    public Optional<Boolean> existsById(Long tariffId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_EXISTS_BY_ID, Boolean.class, tariffId));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
