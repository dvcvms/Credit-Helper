package ru.mts.credit_registration.serivce;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import ru.mts.credit_registration.entity.UserEntity;
import ru.mts.credit_registration.model.AuthenticationRequest;
import ru.mts.credit_registration.model.AuthenticationResponse;
import ru.mts.credit_registration.security.UserDetailsImpl;
import ru.mts.credit_registration.serivce.impl.UserServiceImpl;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final JwtService jwtService;
    private final UserServiceImpl userService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        UserEntity user = userService.getUserByEmail(request.getEmail());
        String jwtToken = jwtService.generateToken(new UserDetailsImpl(user));

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
