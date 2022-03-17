package com.soat.planification_entretien;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.soat.planification_entretien.model.Candidat;
import com.soat.planification_entretien.model.Disponibilite;
import com.soat.planification_entretien.model.Entretien;
import com.soat.planification_entretien.model.Recruteur;
import com.soat.planification_entretien.service.EntretienRepository;
import com.soat.planification_entretien.service.EmailService;
import com.soat.planification_entretien.service.EntretienService;
import io.cucumber.java.fr.Alors;
import io.cucumber.java.fr.Et;
import io.cucumber.java.fr.Etantdonnéque;
import io.cucumber.java.fr.Quand;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
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

    @Autowired
    protected TestEntityManager entityManager;

    private EmailService emailService = mock(EmailService.class);

    private Candidat candidat;
    private Disponibilite disponibiliteDuCandidat;
    private Recruteur recruteur;
    private Disponibilite disponibiliteDuRecruteur;
    private String emailCandidat;
    private String emailRecruteur;

    private EntretienService entretienService;

    @Autowired
    private EntretienRepository entretienRepository;

    @Etantdonnéque("candidat avec comme email {string} et avec {int} années d’XP, faisant du {string} et disponible le {string} à {string}")
    public void candidatAvecAnnéesDXPFaisantDuEtDisponibleLeÀ(String email, int nombreAnneesXP, String technologie, String dateDeDisponibilite, String heureDeDisponibilite) {
        candidat = new Candidat(email, nombreAnneesXP, technologie);
        emailCandidat = email;
        entityManager.persist(candidat);
        disponibiliteDuCandidat = new Disponibilite(LocalDateTime.of(LocalDate.parse(dateDeDisponibilite, DateTimeFormatter.ofPattern("dd/MM/yyyy")), LocalTime.parse(heureDeDisponibilite, DateTimeFormatter.ofPattern("HH:mm"))));
    }

    @Et("un recruteur avec comme email {string} et {int} années d’XP, faisant du {string} et disponible le {string} à {string}")
    public void unRecruteurAnnéesDXPFaisantDuJavaEtDisponibleLeÀ(String email, int nombreAnneesXP, String technologie, String dateDeDisponibilite, String heureDeDisponibilite) {
        recruteur = new Recruteur(email, nombreAnneesXP, technologie);
        emailRecruteur = email;
        entityManager.persist(recruteur);
        disponibiliteDuRecruteur = new Disponibilite(LocalDateTime.of(LocalDate.parse(dateDeDisponibilite, DateTimeFormatter.ofPattern("dd/MM/yyyy")), LocalTime.parse(heureDeDisponibilite, DateTimeFormatter.ofPattern("HH:mm"))));
    }

    @Quand("je planifie l’entretien")
    public void jePlanifieLEntretien() {
        entretienService = new EntretienService(emailService, entretienRepository);
        entretienService.planifier(candidat, recruteur, disponibiliteDuCandidat, disponibiliteDuRecruteur);
    }

    @Alors("le rdv est pris \\(enregistré dans la base de données)")
    public void leRdvEstPrisEnregistréDansLaBaseDeDonnées() {
        List<Entretien> entretiens = entretienRepository.findAll();
        assertThat(entretiens).extracting("candidat.email", "recruteur.email", "horaire")
                .containsExactly(tuple(emailCandidat, emailRecruteur, disponibiliteDuCandidat.dateTime()));
    }

    @Et("un email a été envoyé au candidat")
    public void unEmailAÉtéEnvoyéAuCandidat() {
        verify(emailService, times(1)).envoyerConfirmationAuCandidat(emailCandidat);
    }

    @Et("un email a été envoyé au recruteur")
    public void unEmailAÉtéEnvoyéAuRecruteur() {
        verify(emailService, times(1)).envoyerConfirmationAuRecruteur(emailRecruteur);
    }
}
