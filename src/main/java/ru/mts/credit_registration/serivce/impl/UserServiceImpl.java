package ru.mts.credit_registration.serivce.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mts.credit_registration.entity.RoleEntity;
import ru.mts.credit_registration.entity.UserEntity;
import ru.mts.credit_registration.exception.UserNotFoundException;
import ru.mts.credit_registration.repository.impl.UserRepositoryImpl;
import ru.mts.credit_registration.serivce.UserService;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepositoryImpl userRepository;

    @Override
    public UserEntity getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("USER_NOT_FOUND",
                                String.format("Пользователь с id=`%d` не найден", id)
                        )
                );
    }

    @Override
    public UserEntity getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("USER_NOT_FOUND_BY_USERNAME",
                                String.format("Пользователь с ником - `%s` не найден", username)
                        )
                );
    }

    @Override
    public UserEntity getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("USER_NOT_FOUND_BY_EMAIL",
                                String.format("Пользователь с почтой `%s` не найден", email)
                        )
                );
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll().orElseThrow(); // TODO: repo check
    }

    @Override
    public UserEntity createUser(UserEntity user) {
        return userRepository.save(user);
    }

    @Override
    public UserEntity updateUser(UserEntity user) {
        UserEntity userEntity = userRepository.findByEmail(user.getEmail()).orElseThrow();

        userEntity.setUsername(user.getUsername());
        userEntity.setFirstname(user.getFirstname());
        userEntity.setLastname(user.getLastname());
        userEntity.setEmail(user.getEmail());

        return userRepository.save(userEntity); // TODO: update method create
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("USER_NOT_FOUND",
                    String.format("Пользователь с id=`%d` не найден", id)
            );
        }
        userRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public int enableUser(Long userId) {
        return userRepository.enableUser(userId);
    }

    @Override
    @Transactional
    public UserEntity addRole(Long userId, RoleEntity role) {
        userRepository.addRoles(userId, Set.of(role));
        return userRepository.findById(userId).orElseThrow();
    }
}
