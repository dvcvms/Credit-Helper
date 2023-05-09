package ru.mts.credit_registration.repository;

import ru.mts.credit_registration.entity.TariffEntity;

import java.util.List;

public interface TariffRepository {

    List<TariffEntity> findAll();

    boolean existsById(Long tariffId);

}
