package ru.mts.credit_registration.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.mts.credit_registration.repository.UserRoleRepository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRoleRepositoryImpl implements UserRoleRepository {

    private final JdbcTemplate jdbcTemplate;

    private static final String SQL_SELECT_BY_USER_ID =
            "SELECT role_id FROM users_roles WHERE user_id = ?";

    private static final String SQL_INSERT_USER_ROLE =
            "INSERT INTO users_roles (user_id, role_id) VALUES (?, ?)";

    @Override
    public List<Long> findRolesIdByUserId(Long userId) {
        return jdbcTemplate.queryForList(
                SQL_SELECT_BY_USER_ID,
                Long.class,
                userId
        );
    }

    @Override
    public void addRole(Long userId, Long roleId) {
        jdbcTemplate.update(
                SQL_INSERT_USER_ROLE,
                userId,
                roleId
        );
    }
}
