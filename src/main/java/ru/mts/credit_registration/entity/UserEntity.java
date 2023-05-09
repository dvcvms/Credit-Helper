package ru.mts.credit_registration.entity;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class UserEntity {

    private Long id;
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String password;

    @Builder.Default
    private Boolean locked = false;

    @Builder.Default
    private Boolean enabled = false;

    @Singular
    private Set<RoleEntity> roles = new HashSet<>();
}
