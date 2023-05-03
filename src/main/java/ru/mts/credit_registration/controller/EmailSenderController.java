package ru.mts.credit_registration.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mts.credit_registration.model.EmailBody;
import ru.mts.credit_registration.serivce.EmailSenderService;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class EmailSenderController {

    private final EmailSenderService emailSenderService;

    @PostMapping("/send")
    public void sendEmail(@RequestBody EmailBody emailBody) {
        emailSenderService.sendEmail(emailBody);
    }
}
