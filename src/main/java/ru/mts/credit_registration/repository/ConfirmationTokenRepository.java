package ru.mts.credit_registration.repository;

import ru.mts.credit_registration.entity.ConfirmationTokenEntity;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ConfirmationTokenRepository {

    Optional<ConfirmationTokenEntity> findByToken(String token);

    void updateConfirmedAt(String token, LocalDateTime confirmedAt);

    ConfirmationTokenEntity save(ConfirmationTokenEntity token);

}
