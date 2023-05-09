package ru.mts.credit_registration.repository;

import ru.mts.credit_registration.entity.RoleEntity;
import ru.mts.credit_registration.enums.RoleName;

import java.util.Optional;

public interface RoleRepository {

    Optional<RoleEntity> findById(Long id);

    Optional<RoleEntity> findByName(RoleName name);

    Long findRoleIdByName(RoleName name);

}
