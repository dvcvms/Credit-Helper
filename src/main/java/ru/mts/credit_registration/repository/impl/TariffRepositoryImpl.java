package ru.mts.credit_registration.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.mts.credit_registration.entity.TariffEntity;
import ru.mts.credit_registration.repository.TariffRepository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TariffRepositoryImpl implements TariffRepository {

    private final JdbcTemplate jdbcTemplate;

    private static final String SQL_SELECT_ALL = "SELECT * FROM tariff";

    private static final String SQL_EXISTS_BY_ID = "SELECT EXISTS (SELECT * FROM tariff WHERE id = ?)";

    @Override
    public List<TariffEntity> findAll() {
        return jdbcTemplate.query(
                SQL_SELECT_ALL,
                new BeanPropertyRowMapper<>(TariffEntity.class)
        );
    }

    @Override
    public boolean existsById(Long tariffId) {
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(
                SQL_EXISTS_BY_ID,
                Boolean.class,
                tariffId
        ));
    }
}
