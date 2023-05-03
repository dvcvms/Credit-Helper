package ru.mts.credit_registration.serivce;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.mts.credit_registration.entity.ConfirmationTokenEntity;
import ru.mts.credit_registration.entity.RoleEntity;
import ru.mts.credit_registration.entity.UserEntity;
import ru.mts.credit_registration.model.*;
import ru.mts.credit_registration.property.ServerProperties;
import ru.mts.credit_registration.security.UserDetailsImpl;
import ru.mts.credit_registration.security.UserDetailsServiceImpl;
import ru.mts.credit_registration.serivce.impl.RoleServiceImpl;
import ru.mts.credit_registration.serivce.impl.UserServiceImpl;
import ru.mts.credit_registration.validator.ConfirmTokenValidator;
import ru.mts.credit_registration.validator.UserValidator;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final UserServiceImpl userService;
    private final RoleServiceImpl roleService;
    private final UserValidator userValidator;
    private final ConfirmTokenValidator confirmTokenValidator;
    private final UserDetailsServiceImpl userDetailsService;
    private final EmailSenderService emailSenderService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final ConfirmationTokenService confirmationTokenService;
    private final ServerProperties serverProperties;

    @Transactional
    public RegistrationResponse register(RegistrationRequest request) {

        UserEntity user = UserEntity.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        userValidator.validate(user);

        UserEntity createdUser = userService.createUser(user);
        RoleEntity role = roleService.findByName("ROLE_USER");
        UserEntity updatedUser = userService.addRole(createdUser.getId(), role);
        String jwtToken = jwtService.generateToken(new UserDetailsImpl(updatedUser));

        ConfirmationTokenEntity confirmationToken = confirmationTokenService.generateToken(jwtToken, updatedUser);
        confirmationTokenService.saveConfirmationToken(confirmationToken);

        // Send verification link
        emailSenderService.sendEmail(generateEmailBody(updatedUser, jwtToken));

        return RegistrationResponse.builder()
                .token(jwtToken)
                .build();
    }

    private EmailBody generateEmailBody(UserEntity user, String jwtToken) {
        return EmailBody.builder()
                .toEmail(user.getEmail())
                .subject("Verification")
                .body(generateLink(jwtToken))
                .build();
    }

    private String generateLink(String jwtToken) {
        return String.format("http://%s:%s/registration/confirm?token=%s",
                serverProperties.getHost(),
                serverProperties.getPort(),
                jwtToken);
    }

    @Transactional
    public DataResponse<ConfirmTokenResponse> confirmToken(String token) {
        ConfirmationTokenEntity confirmationToken =
                confirmationTokenService.getToken(token)
                        .orElseThrow(() -> new IllegalStateException("token not found")); // TODO:

        confirmTokenValidator.validate(confirmationToken);

        confirmationTokenService.setConfirmedAt(token);
        userDetailsService.enableUser(confirmationToken.getUserId());

        return new DataResponse<>(new ConfirmTokenResponse("Email was confirmed successfully!"));
    }
}
