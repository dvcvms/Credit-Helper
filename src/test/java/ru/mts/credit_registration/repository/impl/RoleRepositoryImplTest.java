package ru.mts.credit_registration.repository.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import ru.mts.credit_registration.config.PersistenceLayerTestConfig;
import ru.mts.credit_registration.entity.RoleEntity;
import ru.mts.credit_registration.enums.RoleName;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@ContextConfiguration(classes = {
        PersistenceLayerTestConfig.class,
        RoleRepositoryImpl.class,
})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@EnableJpaRepositories(basePackages = {"ru.mts.credit_registration"})
public class RoleRepositoryImplTest {

    @Autowired
    private RoleRepositoryImpl roleRepository;

    @Test
    public void findById() {
        Long roleId = 1L;

        RoleEntity role = roleRepository.findById(roleId).get();

        assertThat(role.getId()).isEqualTo(roleId);
        assertThat(role.getName()).isEqualTo(RoleName.ROLE_USER);
    }

    @Test
    public void findByName() {
        RoleName expectedName = RoleName.ROLE_ADMIN;

        RoleName actualName = roleRepository.findByName(expectedName).get().getName();

        assertThat(actualName).isEqualTo(expectedName);
    }
}