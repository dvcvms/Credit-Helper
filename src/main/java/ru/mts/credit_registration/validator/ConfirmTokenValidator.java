package ru.mts.credit_registration.validator;

import org.springframework.stereotype.Service;
import ru.mts.credit_registration.entity.ConfirmationTokenEntity;
import ru.mts.credit_registration.exception.ConfirmationTokenNotValidException;

import java.time.LocalDateTime;

@Service
public class ConfirmTokenValidator {

    public void validate(ConfirmationTokenEntity confirmationToken) {

        if (confirmationToken.getConfirmedAt() != null) {
            throw new ConfirmationTokenNotValidException("EMAIL_ALREADY_CONFIRMED",
                    "Электронная почта уже подтверждена"
            );
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new ConfirmationTokenNotValidException("TOKEN_EXPIRED",
                    "Срок действия токена истёк"
            );
        }
    }
}
