package ru.mts.credit_registration.repository;

import ru.mts.credit_registration.entity.RoleEntity;

import java.util.List;
import java.util.Set;

public interface UserRoleRepository {

    List<Long> findRolesIdByUserId(Long userId);

    void deleteByUserId(Long userId);

    void addRole(Long userId, RoleEntity role);

    void addRole(Long userId, Set<RoleEntity> roles);

}
