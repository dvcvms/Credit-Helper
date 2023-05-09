package ru.mts.credit_registration.repository.impl;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import ru.mts.credit_registration.config.PersistenceLayerTestConfig;
import ru.mts.credit_registration.entity.TariffEntity;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@TestPropertySource(properties = {
        "spring.sql.init.data-locations=classpath:data-order-tariff-test.sql"
})
@ContextConfiguration(classes = {
        PersistenceLayerTestConfig.class,
        TariffRepositoryImpl.class,
})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@EnableJpaRepositories(basePackages = {"ru.mts.credit_registration"})
public class TariffRepositoryImplTest {

    @Autowired
    private TariffRepositoryImpl tariffRepository;

    @Test
    public void findAll() {
        List<TariffEntity> actualTariffs = tariffRepository.findAll().get();

        assertThat(actualTariffs.size()).isEqualTo(3);
    }

    @Test
    public void willReturnTrueWhenExistById() {
        Long tariffId = 1L;

        Boolean isExists = tariffRepository.existsById(tariffId).get();

        assertThat(isExists).isTrue();
    }

    @Test
    public void willReturnFalseWhenExistById() {
        Long falseId = -1L;

        Boolean isExists = tariffRepository.existsById(falseId).get();

        Assertions.assertThat(isExists).isFalse();
    }
}