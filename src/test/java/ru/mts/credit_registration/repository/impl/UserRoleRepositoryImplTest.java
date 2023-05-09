package ru.mts.credit_registration.repository.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import ru.mts.credit_registration.config.PersistenceLayerTestConfig;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@TestPropertySource(properties = {
        "spring.sql.init.data-locations=classpath:data-user-role-token-test.sql"
})
@ContextConfiguration(classes = {
        PersistenceLayerTestConfig.class,
        UserRoleRepositoryImpl.class
})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@EnableJpaRepositories(basePackages = {"ru.mts.credit_registration"})
public class UserRoleRepositoryImplTest {

    @Autowired
    private UserRoleRepositoryImpl userRoleRepository;

    @Test
    public void findRolesIdByUserId() {
        Long userId = 1L;

        List<Long> rolesIdByUserId =
                userRoleRepository.findRolesIdByUserId(userId);

        assertThat(rolesIdByUserId.size()).isEqualTo(2);
    }

    @Test
    public void addRole() {
        Long userId = 1L;
        Long roleId = 3L;

        userRoleRepository.addRole(userId, roleId);
        List<Long> actualRoles =
                userRoleRepository.findRolesIdByUserId(userId);

        assertThat(actualRoles.size()).isEqualTo(3);
    }
}