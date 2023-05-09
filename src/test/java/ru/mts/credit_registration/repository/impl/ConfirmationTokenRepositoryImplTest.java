package ru.mts.credit_registration.repository.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import ru.mts.credit_registration.config.PersistenceLayerTestConfig;
import ru.mts.credit_registration.entity.ConfirmationTokenEntity;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@TestPropertySource(properties = {
        "spring.sql.init.data-locations=classpath:data-user-role-token-test.sql"
})
@ContextConfiguration(classes = {
        PersistenceLayerTestConfig.class,
        ConfirmationTokenRepositoryImpl.class
})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@EnableJpaRepositories(basePackages = {"ru.mts.credit_registration"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ConfirmationTokenRepositoryImplTest {

    private static ConfirmationTokenEntity testToken;
    @Autowired
    private ConfirmationTokenRepositoryImpl confirmationTokenRepository;

    @BeforeAll
    public void init() {
        ConfirmationTokenEntity token = ConfirmationTokenEntity.builder()
                .token("random_token_" + UUID.randomUUID())
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .userId(1L)
                .build();

        testToken = confirmationTokenRepository.save(token);
    }

    @Test
    public void findByToken() {
        String token = testToken.getToken();

        ConfirmationTokenEntity confirmationTokenEntity =
                confirmationTokenRepository.findByToken(token).get();

        assertThat(confirmationTokenEntity.getId()).isEqualTo(testToken.getId());
        assertThat(confirmationTokenEntity.getToken()).isEqualTo(testToken.getToken());
        assertThat(confirmationTokenEntity.getUserId()).isEqualTo(testToken.getUserId());
    }

    @Test
    public void updateConfirmedAt() {
        LocalDateTime localDateTime = LocalDateTime.now();
        String token = testToken.getToken();

        confirmationTokenRepository.updateConfirmedAt(token, localDateTime);
        LocalDateTime confirmedAt = confirmationTokenRepository.findByToken(token).get().getConfirmedAt();

        assertThat(confirmedAt).isNotNull();
    }

    @Test
    public void save() {
        String token = "random_token_" + UUID.randomUUID();
        ConfirmationTokenEntity expectedToken = ConfirmationTokenEntity.builder()
                .token(token)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .userId(1L)
                .build();

        ConfirmationTokenEntity savedToken =
                confirmationTokenRepository.save(expectedToken);
        ConfirmationTokenEntity actualToken =
                confirmationTokenRepository.findByToken(token).get();

        assertThat(actualToken.getId()).isEqualTo(savedToken.getId());
        assertThat(actualToken.getToken()).isEqualTo(expectedToken.getToken());
        assertThat(actualToken.getUserId()).isEqualTo(expectedToken.getUserId());
    }
}