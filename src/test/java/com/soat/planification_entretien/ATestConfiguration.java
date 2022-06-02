package com.soat.planification_entretien;

import com.soat.planification_entretien.domain.entretien.listener.EmailService;
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
    public EmailService emailService() {
        return mock(EmailService.class);
    }
}
