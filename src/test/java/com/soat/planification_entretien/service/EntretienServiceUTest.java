package com.soat.planification_entretien.service;


import java.time.LocalDate;
import java.time.LocalDateTime;

import com.soat.planification_entretien.model.Candidat;
import com.soat.planification_entretien.model.Entretien;
import com.soat.planification_entretien.model.Recruteur;
import com.soat.planification_entretien.repository.EntretienRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;

class EntretienServiceUTest {
    private EntretienService entretienService;

    private final EntretienRepository entretienRepository = mock(EntretienRepository.class);
    private final EmailService emailService = mock(EmailService.class);

    @BeforeEach
    void setUp() {
        entretienService = new EntretienService(entretienRepository, emailService);
    }

    @Nested
    class Planifier {
        private final Candidat candidat = new Candidat("Java", "candidat@mail.com", 5);
        private final Recruteur recruteur = new Recruteur("Java", "recruteur@soat.fr", 10);
        private final LocalDateTime disponibiliteCandidat = LocalDateTime.of(2022, 2, 10, 12, 0, 0);
        private final LocalDate disponibiliteRecruteur = LocalDate.of(2022, 2, 10);

        @Test
        void should_save_entretien() {
            // when
            entretienService.planifier(candidat, recruteur, disponibiliteCandidat, disponibiliteRecruteur);

            // then
            verify(entretienRepository).save(new Entretien(disponibiliteCandidat, "candidat@mail.com", "recruteur@soat.fr"));
        }

        @Test
        void should_return_saved_entretien() {
            // given
            Entretien entretien = new Entretien(disponibiliteCandidat, "candidat@mail.com", "recruteur@soat.fr");
            given(entretienRepository.save(entretien)).willReturn(entretien);

            // when
            Entretien result = entretienService.planifier(candidat, recruteur, disponibiliteCandidat, disponibiliteRecruteur);

            // then
            assertThat(result).isEqualTo(entretien);
        }

        @Test
        void should_send_emails_to_candidat_and_recruteur() {
            // when
            entretienService.planifier(candidat, recruteur, disponibiliteCandidat, disponibiliteRecruteur);

            // then
            verify(emailService).sendToCandidat("candidat@mail.com");
            verify(emailService).sendToRecruteur("recruteur@soat.fr");
        }
    }
}
