package ru.mts.credit_registration.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.mts.credit_registration.entity.RoleEntity;
import ru.mts.credit_registration.repository.UserRoleRepository;

import java.util.List;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class UserRoleRepositoryImpl implements UserRoleRepository {

    private final JdbcTemplate jdbcTemplate;

    private static final String SQL_INSERT_USER_ROLE =
            "INSERT INTO users_roles (user_id, role_id) VALUES (?, ?)";

    private static final String SQL_SELECT_BY_USER_ID =
            "SELECT role_id FROM users_roles WHERE user_id = ?";

    private static final String SQL_DELETE_BY_USER_ID =
            "DELETE FROM users_roles WHERE user_id = ?";

    @Override
    public List<Long> findRolesIdByUserId(Long userId) {
        return jdbcTemplate.query(SQL_SELECT_BY_USER_ID,
                new BeanPropertyRowMapper<>(Long.class),
                userId + 10
        );
    }

    @Override
    public void deleteByUserId(Long userId) {
        jdbcTemplate.update(SQL_DELETE_BY_USER_ID);
    }

    @Override
    public void addRole(Long userId, RoleEntity role) {
        jdbcTemplate.update(SQL_INSERT_USER_ROLE,
                userId,
                role.getId()
        );
    }

    @Override
    public void addRole(Long userId, Set<RoleEntity> roles) {
        for (RoleEntity role : roles) {
            addRole(userId, role);
        }
    }
}
