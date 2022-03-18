package com.soat.planification_entretien.service;


import java.time.LocalDateTime;

import com.soat.planification_entretien.model.Candidat;
import com.soat.planification_entretien.model.Disponibilite;
import com.soat.planification_entretien.model.Entretien;
import com.soat.planification_entretien.model.Recruteur;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

class EntretienServiceUTest {
    private EntretienService entretienService;

    private final EntretienRepository entretienRepository = mock(EntretienRepository.class);
    private final EmailService emailService = mock(EmailService.class);

    @BeforeEach
    void setUp() {
        entretienService = new EntretienService(emailService, entretienRepository);
    }

    @Nested
    class Planifier {
        private final Candidat candidat = new Candidat("candidat@mail.com", 5, "Java");
        private final Recruteur recruteur = new Recruteur("recruteur@soat.fr", 10, "Java");
        private final Disponibilite disponibiliteCandidat = new Disponibilite(LocalDateTime.of(2022, 2, 10, 12, 0, 0));
        private final Disponibilite disponibiliteRecruteur = new Disponibilite(LocalDateTime.of(2022, 2, 10, 12, 0, 0));

        @Test
        void should_save_entretien() {
            // when
            entretienService.planifier(candidat, recruteur, disponibiliteCandidat, disponibiliteRecruteur);

            // then
            ArgumentCaptor<Entretien> entretienArgumentCaptor = ArgumentCaptor.forClass(Entretien.class);
            verify(entretienRepository).save(entretienArgumentCaptor.capture());
            assertThat(entretienArgumentCaptor.getValue()).usingRecursiveComparison().isEqualTo(new Entretien(candidat, recruteur, disponibiliteCandidat));
        }

        @Test
        void should_send_emails_to_candidat_and_recruteur() {
            // when
            entretienService.planifier(candidat, recruteur, disponibiliteCandidat, disponibiliteRecruteur);

            // then
            verify(emailService).envoyerConfirmationAuCandidat("candidat@mail.com");
            verify(emailService).envoyerConfirmationAuRecruteur("recruteur@soat.fr");
        }
    }

    @Nested
    class PlanifierWhenCandidateIsMoreExperienceThanRecruiter {
        private final Candidat candidat = new Candidat("candidat@mail.com", 5, "Java");
        private final Recruteur recruteur = new Recruteur("recruteur@soat.fr", 5, "Java");
        private final Disponibilite disponibiliteCommune = new Disponibilite(LocalDateTime.of(2022, 2, 10, 12, 0, 0));

        @Test
        void should_not_save_entretien() {
            // when
            entretienService.planifier(candidat, recruteur, disponibiliteCommune, disponibiliteCommune);

            // then
            verify(entretienRepository, never()).save(any(Entretien.class));
        }

        @Test
        void should_not_send_emails_to_candidat_and_recruteur() {
            // when
            entretienService.planifier(candidat, recruteur, disponibiliteCommune, disponibiliteCommune);

            // then
            verify(emailService, never()).envoyerConfirmationAuCandidat(anyString());
            verify(emailService, never()).envoyerConfirmationAuRecruteur(anyString());
        }
    }

    @Nested
    class PlanifierWhenDisponibiliteNotMatching {
        private final Candidat candidat = new Candidat("candidat@mail.com", 5, "Java");
        private final Recruteur recruteur = new Recruteur("recruteur@soat.fr", 10, "Java");
        private final Disponibilite disponibiliteCandidat = new Disponibilite(LocalDateTime.of(2022, 2, 10, 12, 0, 0));
        private final Disponibilite disponibiliteRecruteur = new Disponibilite(LocalDateTime.of(2022, 2, 11, 12, 0, 0));

        @Test
        void should_not_save_entretien() {
            // when
            entretienService.planifier(candidat, recruteur, disponibiliteCandidat, disponibiliteRecruteur);

            // then
            verify(entretienRepository, never()).save(any(Entretien.class));
        }

        @Test
        void should_not_send_emails_to_candidat_and_recruteur() {
            // when
            entretienService.planifier(candidat, recruteur, disponibiliteCandidat, disponibiliteRecruteur);

            // then
            verify(emailService, never()).envoyerConfirmationAuCandidat(anyString());
            verify(emailService, never()).envoyerConfirmationAuRecruteur(anyString());
        }
    }

}
