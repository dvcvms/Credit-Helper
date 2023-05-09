package ru.mts.credit_registration.serivce;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mts.credit_registration.entity.ConfirmationTokenEntity;
import ru.mts.credit_registration.entity.UserEntity;
import ru.mts.credit_registration.repository.ConfirmationTokenRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;

    public void saveConfirmationToken(ConfirmationTokenEntity token) {
        confirmationTokenRepository.save(token);
    }

    public Optional<ConfirmationTokenEntity> getToken(String token) {
        return confirmationTokenRepository.findByToken(token);
    }

    public void setConfirmedAt(String token) {
        confirmationTokenRepository.updateConfirmedAt(token, LocalDateTime.now());
    }

    public ConfirmationTokenEntity generateToken(String token, UserEntity user) {
        return ConfirmationTokenEntity.builder()
                .token(token)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .userId(user.getId())
                .build();
    }
}
