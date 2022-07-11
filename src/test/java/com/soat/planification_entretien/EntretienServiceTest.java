package com.soat.planification_entretien;

import java.util.List;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class EntretienServiceTest {

    @Test
    void planifier_should_save_entretien() {
        // given
        EntretienRepository repository = new EntretienRepository();
        EmailService emailService = new FakeEmailService();
        var candidat = new Candidat("candidat@mail.com", 2, "java", "10h00");
        var recruteur = new Recruteur("rh@soat.fr", 3, "java", "10h00");

        EntretienService entretienService = new EntretienService(repository, emailService);

        // when
        entretienService.planifier(candidat, recruteur);

        // then
        List<Entretien> entretiens = repository.findAll();

        assertThat(entretiens).hasSize(1);
        assertThat(entretiens.get(0)).extracting(Entretien::emailCandidat, Entretien::emailRH, Entretien::horaire)
                .containsExactly(candidat.email(), recruteur.email(), candidat.disponibilite());
    }

    @Test
    void planifier_should_send_email_to_candidat() {
        // given
        EntretienRepository repository = new EntretienRepository();
        FakeEmailService emailService = new FakeEmailService();
        var candidat = new Candidat("candidat@mail.com", 2, "java", "10h00");
        var recruteur = new Recruteur("rh@soat.fr", 3, "java", "10h00");

        EntretienService entretienService = new EntretienService(repository, emailService);

        // when
        entretienService.planifier(candidat, recruteur);

        // then
        assertThat(emailService.isSentToCandidat("candidat@mail.com")).isTrue();
    }

    @Test
    void planifier_should_send_email_to_RH() {
        // given
        EntretienRepository repository = new EntretienRepository();
        FakeEmailService emailService = new FakeEmailService();
        var candidat = new Candidat("candidat@mail.com", 2, "java", "10h00");
        var recruteur = new Recruteur("rh@soat.fr", 3, "java", "10h00");

        EntretienService entretienService = new EntretienService(repository, emailService);

        // when
        entretienService.planifier(candidat, recruteur);

        // then
        assertThat(emailService.isSentToRH("rh@soat.fr")).isTrue();
    }
}
