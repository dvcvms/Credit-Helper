package ru.mts.credit_registration.serivce;

import ru.mts.credit_registration.entity.RoleEntity;
import ru.mts.credit_registration.entity.UserEntity;

import java.util.List;

public interface UserService {

    UserEntity getUserById(Long id);

    UserEntity getUserByUsername(String username);

    UserEntity getUserByEmail(String email);

    List<UserEntity> getAllUsers();

    UserEntity createUser(UserEntity userDto);

    void deleteUser(Long id);

    boolean existsById(Long id);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    int enableUser(Long userId);

    UserEntity addRole(Long userId, RoleEntity role);

}
