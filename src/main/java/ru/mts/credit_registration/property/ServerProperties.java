package ru.mts.credit_registration.property;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@Configuration
@PropertySource("classpath:config/server.properties")
public class ServerProperties {

    @Value("${server.host}")
    private String host;

    @Value("${server.port}")
    private String port;

}
