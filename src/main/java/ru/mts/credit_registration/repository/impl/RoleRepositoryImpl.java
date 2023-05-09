package ru.mts.credit_registration.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.mts.credit_registration.entity.RoleEntity;
import ru.mts.credit_registration.enums.RoleName;
import ru.mts.credit_registration.repository.RoleRepository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RoleRepositoryImpl implements RoleRepository {

    private final JdbcTemplate jdbcTemplate;

    private static final String SQL_SELECT_BY_ID = "SELECT * FROM roles WHERE id = ?";

    private static final String SQL_SELECT_BY_NAME = "SELECT * FROM roles WHERE name = ?";

    private static final String SQL_SELECT_ROLE_ID_BY_NAME = "SELECT id FROM roles WHERE name = ?";

    @Override
    public Optional<RoleEntity> findById(Long id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                            SQL_SELECT_BY_ID,
                            new BeanPropertyRowMapper<>(RoleEntity.class),
                            id
                    )
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<RoleEntity> findByName(RoleName name) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                            SQL_SELECT_BY_NAME,
                            new BeanPropertyRowMapper<>(RoleEntity.class),
                            name.toString()
                    )
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Long findRoleIdByName(RoleName name) {
        return jdbcTemplate.queryForObject(
                SQL_SELECT_ROLE_ID_BY_NAME,
                Long.class,
                name.toString()
        );
    }
}
