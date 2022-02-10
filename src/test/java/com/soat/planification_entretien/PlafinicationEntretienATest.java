package com.soat.planification_entretien;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.soat.planification_entretien.model.Candidat;
import com.soat.planification_entretien.model.Entretien;
import com.soat.planification_entretien.model.Recruteur;
import com.soat.planification_entretien.repository.EntretienRepository;
import com.soat.planification_entretien.service.EmailService;
import com.soat.planification_entretien.service.EntretienService;
import io.cucumber.java.fr.Alors;
import io.cucumber.java.fr.Et;
import io.cucumber.java.fr.Etantdonné;
import io.cucumber.java.fr.Etqu;
import io.cucumber.java.fr.Quand;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED)
@Transactional
@AutoConfigureCache
@AutoConfigureDataJpa
@AutoConfigureTestEntityManager
@SpringBootTest(webEnvironment = RANDOM_PORT)
@DirtiesContext
@CucumberContextConfiguration
@ActiveProfiles("AcceptanceTest")
public class PlafinicationEntretienATest {

    private Candidat candidat;
    private Recruteur recruteur;
    private EntretienService entretienService;
    private LocalDateTime disponibiliteCandidat;
    private LocalDate disponibiliteRecruteur;
    private Entretien entretien;
    private final EmailService emailService = mock(EmailService.class);
    private final EntretienRepository entretienRepository = mock(EntretienRepository.class);

    @Etantdonné("un candidat {string} \\({string}) avec {int} ans d’expériences qui est disponible {string} à {string}")
    public void unCandidatAvecAnsDExpériencesQuiEstDisponibleÀ(String language, String email, int xp, String date, String heure) {
        candidat = new Candidat(language, email, xp);
        disponibiliteCandidat = LocalDateTime.of(LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy")), LocalTime.parse(heure, DateTimeFormatter.ofPattern("HH:mm")));
    }

    @Etqu("un recruteur {string} \\({string}) qui a {int} ans d’XP qui est dispo {string}")
    public void unRecruteurQuiAAnsDXPQuiEstDispo(String language, String email, int xp, String date) {
        recruteur = new Recruteur(language, email, xp);
        disponibiliteRecruteur = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    @Quand("on tente une planification d’entretien")
    public void onTenteUnePlanificationDEntretien() {
        Entretien expectedEntretien = new Entretien(LocalDateTime.of(2022, 2, 10, 12, 0, 0), "candidat@email.com", "recruteur@mail.com");
        given(entretienRepository.save(any())).willReturn(expectedEntretien);

        entretienService = new EntretienService(entretienRepository, emailService);
        entretien = entretienService.planifier(candidat, recruteur, disponibiliteCandidat, disponibiliteRecruteur);
    }

    @Alors("L’entretien est planifié : {string}, {string} à {string} à {string}")
    public void lEntretienEstPlanifiéÀÀ(String emailCandidat, String emailRecurteur, String date, String heure) {
        LocalDateTime dateEtHeure = LocalDateTime.of(LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy")), LocalTime.parse(heure, DateTimeFormatter.ofPattern("HH:mm")));
        Entretien expectedEntretien = new Entretien(dateEtHeure, emailCandidat, emailRecurteur);

        verify(entretienRepository).save(expectedEntretien);
        assertThat(this.entretien).isNotNull();
    }

    @Et("un mail de confirmation est envoyé au candidat \\({string}) et au recruteur \\({string})")
    public void unMailDeConfirmationEstEnvoyéAuCandidatEtAuRecruteur(String emailCandidat, String emailRecruteur) {
        verify(emailService).sendToCandidat(emailCandidat);
        verify(emailService).sendToRecruteur(emailRecruteur);
    }

    @Alors("L’entretien n'est pas planifié")
    public void lEntretienNEstPasPlanifié() {
        verify(entretienRepository, never()).save(any(Entretien.class));
        assertThat(this.entretien).isNull();
    }

    @Et("aucun mail de confirmation est envoyé au candidat ou au recruteur")
    public void aucunMailDeConfirmationEstEnvoyéAuCandidatOuAuRecruteur() {
        verify(emailService, never()).sendToCandidat(anyString());
        verify(emailService, never()).sendToRecruteur(anyString());
    }
}
