package ru.mts.credit_registration.property;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@Configuration
@PropertySource("classpath:config/cors.properties")
public class CorsProperties {

    @Value("${from}")
    private String from;
}

