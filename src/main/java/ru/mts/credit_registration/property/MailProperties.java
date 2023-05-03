package ru.mts.credit_registration.property;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@Configuration
@PropertySource("classpath:config/mail.properties")
public class MailProperties {

    @Value("${port}")
    private int port;

    @Value("${host}")
    private String host;

    @Value("${login}")
    private String login;

    @Value("${password}")
    private String password;

}
