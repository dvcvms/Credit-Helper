package ru.mts.credit_registration.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.mts.credit_registration.entity.RoleEntity;
import ru.mts.credit_registration.entity.UserEntity;
import ru.mts.credit_registration.exception.RoleNotFoundException;
import ru.mts.credit_registration.repository.UserRepository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.*;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RoleRepositoryImpl roleRepository;
    private final UserRoleRepositoryImpl userRoleRepository;

    private static final String SQL_SELECT_BY_ID = "SELECT * FROM users WHERE id = ?";

    private static final String SQL_SELECT_BY_USERNAME = "SELECT * FROM users WHERE username = ?";

    private static final String SQL_SELECT_BY_EMAIL = "SELECT * FROM users WHERE email = ?";

    private static final String SQL_SELECT_ALL = "SELECT * FROM users";

    private static final String SQL_SAVE_USER =
            "INSERT INTO users (username, firstname, lastname, email, password, locked, enabled) VALUES (?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_DELETE_BY_ID = "DELETE FROM users WHERE id = ?";

    private static final String SQL_EXISTS_BY_ID = "SELECT EXISTS (SELECT * FROM users WHERE id = ?)";

    private static final String SQL_EXISTS_BY_USERNAME = "SELECT EXISTS (SELECT * FROM users WHERE username = ?)";

    private static final String SQL_EXISTS_BY_EMAIL = "SELECT EXISTS (SELECT * FROM users WHERE email = ?)";

    private static final String SQL_UPDATE_ENABLED = "UPDATE users SET enabled = true WHERE id = ?";

    @Override
    public Optional<UserEntity> findById(Long id) {
        try {
            UserEntity user = jdbcTemplate.queryForObject(
                    SQL_SELECT_BY_ID,
                    new BeanPropertyRowMapper<>(UserEntity.class),
                    id
            );
            return setRolesIntoUser(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<UserEntity> findByUsername(String username) {
        try {
            UserEntity user = jdbcTemplate.queryForObject(
                    SQL_SELECT_BY_USERNAME,
                    new BeanPropertyRowMapper<>(UserEntity.class),
                    username
            );
            return setRolesIntoUser(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        try {
            UserEntity user = jdbcTemplate.queryForObject(
                    SQL_SELECT_BY_EMAIL,
                    new BeanPropertyRowMapper<>(UserEntity.class),
                    email
            );
            return setRolesIntoUser(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public List<UserEntity> findAll() {
        List<UserEntity> query = jdbcTemplate.query(
                SQL_SELECT_ALL,
                new BeanPropertyRowMapper<>(UserEntity.class)
        );

        return query.stream().map(user -> setRolesIntoUser(user).get()).collect(Collectors.toList());
    }

    private Optional<UserEntity> setRolesIntoUser(UserEntity userEntity) {
        if (userEntity == null) {
            return Optional.empty();
        }

        Set<RoleEntity> userRoles = findRolesByUserId(userEntity.getId());
        userEntity.setRoles(userRoles);
        return Optional.of(userEntity);
    }

    private Set<RoleEntity> findRolesByUserId(Long id) {
        List<Long> rolesId = userRoleRepository.findRolesIdByUserId(id);

        Set<RoleEntity> roles = new HashSet<>();
        for (Long roleId : rolesId) {
            Optional<RoleEntity> role = roleRepository.findById(roleId);

            if (role.isEmpty()) {
                throw new RoleNotFoundException("ROLE_NOT_FOUND",
                        String.format("Роль по id=`%d`не существует", roleId)
                );
            }

            roles.add(role.get());
        }
        return roles;
    }

    @Override
    @Transactional
    public UserEntity save(UserEntity user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(SQL_SAVE_USER, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getFirstname());
            ps.setString(3, user.getLastname());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getPassword());
            ps.setBoolean(6, user.getLocked());
            ps.setBoolean(7, user.getEnabled());

            return ps;
        }, keyHolder);

        user.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        addRoles(user.getId(), user.getRoles());
        return user;
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update(
                SQL_DELETE_BY_ID,
                id
        );
    }

    @Override
    public boolean existsById(Long id) {
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(
                        SQL_EXISTS_BY_ID,
                        Boolean.class,
                        id
                )
        );
    }

    @Override
    public boolean existsByUsername(String username) {
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(
                        SQL_EXISTS_BY_USERNAME,
                        Boolean.class,
                        username
                )
        );
    }

    @Override
    public boolean existsByEmail(String email) {
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(
                        SQL_EXISTS_BY_EMAIL,
                        Boolean.class,
                        email
                )
        );
    }

    @Override
    public int enableUser(Long userId) {
        return jdbcTemplate.update(SQL_UPDATE_ENABLED,
                userId
        );
    }

    @Override
    @Transactional
    public void addRoles(Long userId, Set<RoleEntity> roles) {
        for (RoleEntity role : roles) {
            Long roleId = roleRepository.findRoleIdByName(role.getName());
            userRoleRepository.addRole(userId, roleId);
        }
    }
}
