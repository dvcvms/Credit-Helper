package ru.mts.credit_registration.repository;

import ru.mts.credit_registration.entity.TariffEntity;

import java.util.List;
import java.util.Optional;

public interface TariffRepository {

    Optional<List<TariffEntity>> findAll();

    Optional<Boolean> existsById(Long tariffId);

}
