package ru.mts.credit_registration.serivce;

import ru.mts.credit_registration.entity.RoleEntity;
import ru.mts.credit_registration.enums.RoleName;

public interface RoleService {

    RoleEntity findByName(RoleName name);

}
