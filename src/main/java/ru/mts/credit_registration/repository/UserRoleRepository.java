package ru.mts.credit_registration.repository;

import java.util.List;

public interface UserRoleRepository {

    List<Long> findRolesIdByUserId(Long userId);

    void addRole(Long userId, Long role);

}
