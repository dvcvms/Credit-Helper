package ru.mts.credit_registration.property;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class MailProperties {

    @Value("${spring.email.port}")
    private int port;

    @Value("${spring.email.host}")
    private String host;

    @Value("${spring.email.login}")
    private String login;

    @Value("${spring.email.password}")
    private String password;

}
