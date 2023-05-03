package ru.mts.credit_registration.serivce;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.mts.credit_registration.model.EmailBody;

@Service
@RequiredArgsConstructor
public class EmailSenderService {

    private final JavaMailSender javaMailSender;

    public void sendEmail(EmailBody emailBody) { // TODO: send emails in a separate thread | async
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(emailBody.getToEmail());
        message.setText(emailBody.getBody());
        message.setSubject(emailBody.getSubject());

        javaMailSender.send(message);
    }
}
