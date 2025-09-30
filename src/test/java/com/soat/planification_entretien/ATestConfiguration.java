package com.soat.planification_entretien;

import com.soat.planification_entretien.application.use_case.output_port.EmailServicePort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import static org.mockito.Mockito.*;

@Configuration
@Profile("AcceptanceTest")
public class ATestConfiguration {

    @Bean("EmailServiceATest")
    @Primary
    public EmailServicePort emailService() {
        return mock(EmailServicePort.class);
    }
}
