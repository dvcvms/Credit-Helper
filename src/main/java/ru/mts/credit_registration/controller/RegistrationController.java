package ru.mts.credit_registration.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mts.credit_registration.model.ConfirmTokenResponse;
import ru.mts.credit_registration.model.DataResponse;
import ru.mts.credit_registration.model.RegistrationRequest;
import ru.mts.credit_registration.model.RegistrationResponse;
import ru.mts.credit_registration.serivce.RegistrationService;

@RestController
@RequestMapping("/registration")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping
    public ResponseEntity<RegistrationResponse> register(@RequestBody RegistrationRequest request) {
        return ResponseEntity.ok(registrationService.register(request));
    }

    @GetMapping(path = "/confirm")
    public ResponseEntity<DataResponse<ConfirmTokenResponse>> confirm(@RequestParam("token") String token) {
        return ResponseEntity.ok(registrationService.confirmToken(token));
    }
}
