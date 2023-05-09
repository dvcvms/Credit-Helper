package ru.mts.credit_registration.repository.impl;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import ru.mts.credit_registration.config.PersistenceLayerTestConfig;
import ru.mts.credit_registration.entity.RoleEntity;
import ru.mts.credit_registration.entity.UserEntity;
import ru.mts.credit_registration.enums.RoleName;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@TestPropertySource(properties = {
        "spring.sql.init.data-locations=classpath:data-user-role-token-test.sql"
})
@ContextConfiguration(classes = {
        PersistenceLayerTestConfig.class,
        RoleRepositoryImpl.class,
        UserRoleRepositoryImpl.class,
        UserRepositoryImpl.class,
        PasswordEncoder.class
})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@EnableJpaRepositories(basePackages = {"ru.mts.credit_registration"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserRepositoryImplTest {

    private static final Faker FAKER = new Faker();
    private static UserEntity testUser;
    @Autowired
    private UserRepositoryImpl userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeAll
    public void init() {
        UserEntity user = new UserEntity().toBuilder()
                .username(FAKER.name().username())
                .firstname(FAKER.name().firstName())
                .lastname(FAKER.name().lastName())
                .email(FAKER.internet().emailAddress() + "_" + UUID.randomUUID())
                .password(passwordEncoder.encode(FAKER.internet().password()))
                .build();

        testUser = userRepository.save(user);
    }

    @Test
    public void findById() {
        Long id = testUser.getId();

        UserEntity actualUser = userRepository.findById(id).get();

        assertThat(actualUser.getId()).isEqualTo(id);
        assertThat(actualUser.getUsername()).isEqualTo(testUser.getUsername());
        assertThat(actualUser.getPassword()).isEqualTo(testUser.getPassword());
    }

    @Test
    public void findByUsername() {
        String username = testUser.getUsername();

        UserEntity actualUser = userRepository.findByUsername(username).get();

        assertThat(actualUser.getUsername()).isEqualTo(username);
        assertThat(actualUser.getPassword()).isEqualTo(testUser.getPassword());
        assertThat(actualUser.getLocked()).isEqualTo(testUser.getLocked());
    }

    @Test
    public void findByEmail() {
        String email = testUser.getEmail();

        UserEntity actualUser = userRepository.findByEmail(email).get();

        assertThat(actualUser.getEmail()).isEqualTo(email);
        assertThat(actualUser.getLastname()).isEqualTo(testUser.getLastname());
        assertThat(actualUser.getEnabled()).isEqualTo(testUser.getEnabled());
    }

    @Test
    public void findAll() {
        int userCount = 2; // From init db and before all method

        List<UserEntity> userEntities = userRepository.findAll();

        assertThat(userEntities.size()).isEqualTo(userCount);
    }

    @Test
    public void save() {
        UserEntity expectedUser = new UserEntity().toBuilder()
                .username(FAKER.name().username())
                .firstname(FAKER.name().firstName())
                .lastname(FAKER.name().lastName())
                .email(FAKER.internet().emailAddress() + "_" + UUID.randomUUID())
                .password(passwordEncoder.encode(FAKER.internet().password()))
                .build();

        UserEntity actualUser = userRepository.save(expectedUser);

        assertThat(actualUser.getId()).isEqualTo(expectedUser.getId());
        assertThat(actualUser.getEmail()).isEqualTo(expectedUser.getEmail());
        assertThat(actualUser.getPassword()).isEqualTo(expectedUser.getPassword());
        assertThat(actualUser.getLocked()).isEqualTo(expectedUser.getLocked());
    }

    @Test
    public void deleteById() {
        UserEntity user = new UserEntity().toBuilder()
                .username(FAKER.name().username())
                .firstname(FAKER.name().firstName())
                .lastname(FAKER.name().lastName())
                .email(FAKER.internet().emailAddress() + "_" + UUID.randomUUID())
                .password(passwordEncoder.encode(FAKER.internet().password()))
                .build();

        UserEntity savedUser = userRepository.save(user);
        userRepository.deleteById(savedUser.getId());
        Optional<UserEntity> deletedUser = userRepository.findById(savedUser.getId());

        assertThat(deletedUser).isEmpty();
    }

    @Test
    public void existsById() {
        Long id = testUser.getId();

        boolean isExists = userRepository.existsById(id);

        assertThat(isExists).isTrue();
    }

    @Test
    public void existsByIdBad() {
        Long id = -1L;

        boolean isExists = userRepository.existsById(id);

        assertThat(isExists).isFalse();
    }

    @Test
    public void existsByUsername() {
        String username = testUser.getUsername();

        boolean isExists = userRepository.existsByUsername(username);

        assertThat(isExists).isTrue();
    }

    @Test
    public void existsByUsernameBad() {
        String username = FAKER.name().username() + "_" + UUID.randomUUID();

        boolean isExists = userRepository.existsByUsername(username);

        assertThat(isExists).isFalse();
    }

    @Test
    public void existsByEmail() {
        String email = testUser.getEmail();

        boolean isExists = userRepository.existsByEmail(email);

        assertThat(isExists).isTrue();
    }

    @Test
    public void existsByEmailBad() {
        String email = FAKER.internet().emailAddress() + "_" + UUID.randomUUID() + "0123!@#";

        boolean isExists = userRepository.existsByEmail(email);

        assertThat(isExists).isFalse();
    }

    @Test
    public void enableUser() {
        Long id = testUser.getId();

        userRepository.enableUser(id);
        boolean actualEnabledState = userRepository.findById(id).get().getEnabled();

        assertThat(actualEnabledState).isTrue();
    }

    @Test
    public void enableUserNotEnabled() {
        Long id = testUser.getId();

        boolean actualEnabledState = userRepository.findById(id).get().getEnabled();

        assertThat(actualEnabledState).isFalse();
    }

    @Test
    public void addRoles() {
        Long id = testUser.getId();
        Set<RoleEntity> expectedRoles = Set.of(
                new RoleEntity().setName(RoleName.ROLE_USER),
                new RoleEntity().setName(RoleName.ROLE_ADMIN)
        );

        userRepository.addRoles(id, expectedRoles);
        Set<RoleEntity> actualRoles = userRepository.findById(id).get().getRoles();
        boolean isEqual = isSetOfRolesIsEqual(expectedRoles, actualRoles);

        assertThat(isEqual).isTrue();
    }

    private boolean isSetOfRolesIsEqual(Set<RoleEntity> roles1, Set<RoleEntity> roles2) {
        Comparator<RoleEntity> comparing = Comparator.comparing(RoleEntity::getName);

        List<RoleEntity> list1 = roles1.stream().sorted(comparing).toList();
        List<RoleEntity> list2 = roles2.stream().sorted(comparing).toList();

        boolean result = list1.size() == list2.size();
        if (result) {
            for (int i = 0; i < list2.size(); i++) {
                result = list1.get(i).getName().equals(list2.get(i).getName());
                if (!result) {
                    break;
                }
            }
        }
        return result;
    }
}
