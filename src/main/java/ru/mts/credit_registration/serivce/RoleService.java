package ru.mts.credit_registration.serivce;

import ru.mts.credit_registration.entity.RoleEntity;

public interface RoleService {

    RoleEntity findByName(String name);

}
