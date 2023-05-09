package ru.mts.credit_registration.serivce.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mts.credit_registration.entity.RoleEntity;
import ru.mts.credit_registration.enums.RoleName;
import ru.mts.credit_registration.exception.RoleNotFoundException;
import ru.mts.credit_registration.repository.impl.RoleRepositoryImpl;
import ru.mts.credit_registration.serivce.RoleService;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepositoryImpl roleRepository;

    @Override
    public RoleEntity findByName(RoleName name) {
        return roleRepository.findByName(name).orElseThrow(() -> new RoleNotFoundException(
                        "ROLE_NOT_FOUND",
                        String.format("Роль `%s` не найдена", name)
                )
        );
    }
}
