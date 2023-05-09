package ru.mts.credit_registration.property;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class ServerProperties {

    @Value("${spring.server.host}")
    private String host;

    @Value("${spring.server.port}")
    private String port;

}
