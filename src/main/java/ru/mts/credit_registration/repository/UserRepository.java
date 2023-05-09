package ru.mts.credit_registration.repository;

import ru.mts.credit_registration.entity.RoleEntity;
import ru.mts.credit_registration.entity.UserEntity;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserRepository {

    Optional<UserEntity> findById(Long id);

    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByEmail(String email);

    List<UserEntity> findAll();

    UserEntity save(UserEntity order);

    void deleteById(Long id);

    boolean existsById(Long id);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    int enableUser(Long userId);

    void addRoles(Long userId, Set<RoleEntity> roles);
}
