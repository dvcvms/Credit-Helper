package ru.mts.credit_registration.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import ru.mts.credit_registration.enums.RoleName;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class RoleEntity {
    private Long id;
    private RoleName name;
}
