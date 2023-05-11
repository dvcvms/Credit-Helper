package ru.mts.credit_registration.serivce;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.mts.credit_registration.model.EmailBody;
import ru.mts.credit_registration.property.MailProperties;

@Service
@RequiredArgsConstructor
public class EmailSenderService {

    private final JavaMailSender javaMailSender;
    private final MailProperties mailProperties;

    public void sendEmail(EmailBody emailBody) { // TODO: send emails in a separate thread | async
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(mailProperties.getLogin());
        message.setTo(emailBody.getToEmail());
        message.setText(emailBody.getBody());
        message.setSubject(emailBody.getSubject());

        javaMailSender.send(message);
    }
}
