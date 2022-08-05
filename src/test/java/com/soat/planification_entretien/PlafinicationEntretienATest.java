package com.soat.planification_entretien;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.soat.ATest;
import com.soat.planification_entretien.entretien.command.application.controller.EntretienAutomatiqueDto;
import com.soat.planification_entretien.entretien.command.application.controller.EntretienCommandController;
import com.soat.planification_entretien.entretien.command.application.controller.EntretienDto;
import com.soat.planification_entretien.entretien.command.application.controller.Status;
import com.soat.planification_entretien.rendez_vous.query.application.controller.RendezVousDto;
import com.soat.planification_entretien.entretien.command.domain.entity.Candidat;
import com.soat.planification_entretien.candidat.command.repository.CandidatRepository;
import com.soat.planification_entretien.rendez_vous.command.domain.entity.Calendrier;
import com.soat.planification_entretien.recruteur.command.domain.entity.Recruteur;
import com.soat.planification_entretien.rendez_vous.command.repository.CalendrierRepository;
import com.soat.planification_entretien.entretien.listener.service.EmailService;
import com.soat.planification_entretien.entretien.command.domain.entity.Entretien;
import com.soat.planification_entretien.entretien.command.domain.repository.EntretienRepository;
import com.soat.planification_entretien.rendez_vous.command.domain.entity.RendezVous;
import com.soat.planification_entretien.recruteur.command.domain.repository.RecruteurRepository;
import com.soat.planification_entretien.rendez_vous.query.dao.CalendrierDAO;
import io.cucumber.java.Before;
import io.cucumber.java.fr.Alors;
import io.cucumber.java.fr.Et;
import io.cucumber.java.fr.Etantdonné;
import io.cucumber.java.fr.Etqu;
import io.cucumber.java.fr.Quand;
import io.cucumber.spring.CucumberContextConfiguration;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static io.restassured.RestAssured.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED)
@Transactional
@AutoConfigureCache
@AutoConfigureDataJpa
@EnableJpaRepositories
@AutoConfigureTestEntityManager
@SpringBootTest(webEnvironment = RANDOM_PORT)
@DirtiesContext
@CucumberContextConfiguration
@ActiveProfiles("AcceptanceTest")
public class PlafinicationEntretienATest extends ATest {

    private Candidat candidat;
    private LocalDateTime disponibiliteDuCandidat;
    private com.soat.planification_entretien.entretien.command.domain.entity.Recruteur recruteur;

    @Autowired
    private EntretienRepository entretienRepository;
    @Autowired
    private CandidatRepository candidatRepository;
    @Autowired
    private RecruteurRepository recruteurRepository;
    @Autowired
    private CalendrierRepository calendrierRepository;

    @Autowired
    private CalendrierDAO calendrierDAO;

    @Autowired
    private EmailService emailService;

    @Before
    @Override
    public void setUp() {
        initIntegrationTest();
    }

    @Override
    protected void initPath() {
        RestAssured.basePath = EntretienCommandController.PATH;
    }

    @Etantdonné("un candidat {string} \\({string}) avec {string} ans d’expériences qui est disponible {string} à {string}")
    public void unCandidatAvecAnsDExpériencesQuiEstDisponibleÀ(String language, String email, String experienceInYears, String date, String time) {
        candidat = new Candidat(UUID.randomUUID(), language, email, Integer.parseInt(experienceInYears));
        candidatRepository.save(new com.soat.planification_entretien.candidat.command.domain.entity.Candidat(
                candidat.getId(),
                candidat.getLanguage(),
                candidat.getEmail(),
                candidat.getExperienceInYears()
        ));
        disponibiliteDuCandidat = parseDateTime(date, time);
    }

    @Etqu("un recruteur {string} \\({string}) qui a {string} ans d’XP")
    public void unRecruteurQuiAAnsDXP(String language, String email, String experienceInYears) {
        recruteur = new com.soat.planification_entretien.entretien.command.domain.entity.Recruteur("1", language, email, Integer.parseInt(experienceInYears), new ArrayList<>());
        recruteurRepository.save(Recruteur.create(
                "1",
                recruteur.getLanguage(),
                recruteur.getEmail(),
                recruteur.getExperienceInYears()));
    }

    @Et("qui a déjà un rendez-vous le {string} à {string}")
    public void quiADéjàUnRendezVousLeÀ(String date, String time) {
        List<RendezVous> rdv = new ArrayList<>();
        var rendezVous = new RendezVous("autre@candidat.fr", parseDateTime(date, time));
        rdv.add(rendezVous);
        calendrierRepository.save(new Calendrier(1, recruteur.getEmail(), rdv));
    }

    static LocalDateTime parseDateTime(String date, String time) {
        return LocalDateTime.of(LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy")), LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm")));
    }

    @Quand("on tente une planification d’entretien")
    public void onTenteUnePlanificationDEntretien() throws JsonProcessingException {
        EntretienDto entretienDto = new EntretienDto(candidat.getId(), recruteur.getId(), disponibiliteDuCandidat);
        String body = objectMapper.writeValueAsString(entretienDto);
        initPath();
        //@formatter:off
        response = given()
                .log().all()
                .header("Content-Type", ContentType.JSON)
                .body(body)
        .when()
                .post("planifier");
        //@formatter:on
    }

    @Quand("on tente une planification automatique d’entretien")
    public void onTenteUnePlanificationAutomatiqueDEntretien() throws JsonProcessingException {
        var entretienDto = new EntretienAutomatiqueDto(candidat.getId(), disponibiliteDuCandidat);
        String body = objectMapper.writeValueAsString(entretienDto);
        initPath();
        //@formatter:off
        response = given()
                .log().all()
                .header("Content-Type", ContentType.JSON)
                .body(body)
                .when()
                .post("planifier-automatique");
        //@formatter:on
    }

    @Alors("L’entretien est planifié")
    public void lEntretienEstPlanifié() {
        response.then()
                .statusCode(HttpStatus.SC_CREATED);

        Entretien entretien = entretienRepository.findByCandidat(candidat);
        var expectedRecruteur = new com.soat.planification_entretien.entretien.command.domain.entity.Recruteur(recruteur.getId(),
                recruteur.getLanguage(),
                recruteur.getEmail(),
                recruteur.getExperienceInYears(),
                List.of(new com.soat.planification_entretien.entretien.command.domain.entity.RendezVous(candidat.getEmail(), disponibiliteDuCandidat)));
        Entretien expectedEntretien = Entretien.of(candidat, expectedRecruteur, disponibiliteDuCandidat);
        assertThat(entretien).usingRecursiveComparison()
                .ignoringFields("id", "candidat.id", "recruteur.id")
                .isEqualTo(expectedEntretien);
    }

    @Alors("L’entretien est planifié pour le recruteur {string}")
    public void lEntretienEstPlanifiéPourLeRecruteur(String recruteurId) {
        response.then()
                .statusCode(HttpStatus.SC_CREATED);

        Entretien entretien = entretienRepository.findByCandidat(candidat);

        assertThat(entretien.getRecruteur().getId()).isEqualTo(recruteurId);
        assertThat(entretien.getCandidat().getId()).isEqualTo(candidat.getId());
        assertThat(entretien.getStatus()).isEqualTo(Status.PLANIFIE.name());
        assertThat(entretien.getHoraire()).isEqualTo(disponibiliteDuCandidat);

    }

    @Et("un mail de confirmation est envoyé au candidat et au recruteur")
    public void unMailDeConfirmationEstEnvoyéAuCandidatEtAuRecruteur() {
        verify(emailService).envoyerUnEmailDeConfirmationAuCandidat(candidat.getEmail(), disponibiliteDuCandidat);
        verify(emailService).envoyerUnEmailDeConfirmationAuRecruteur(recruteur.getEmail(), disponibiliteDuCandidat);
    }

    @Et("un mail de confirmation est envoyé au candidat et au recruteur {string}")
    public void unMailDeConfirmationEstEnvoyéAuCandidatEtAuRecruteurEmail(String recruteurRmail) {
        verify(emailService).envoyerUnEmailDeConfirmationAuCandidat(candidat.getEmail(), disponibiliteDuCandidat);
        verify(emailService).envoyerUnEmailDeConfirmationAuRecruteur(recruteurRmail, disponibiliteDuCandidat);
    }

    @Alors("L’entretien n'est pas planifié")
    public void lEntretienNEstPasPlanifié() {
        response.then()
                .statusCode(HttpStatus.SC_BAD_REQUEST);

        Entretien entretien = entretienRepository.findByCandidat(candidat);
        assertThat(entretien).isNull();
    }

    @Et("aucun mail de confirmation n'est envoyé au candidat ou au recruteur")
    public void aucunMailDeConfirmationNEstEnvoyéAuCandidatOuAuRecruteur() {
        verify(emailService, never()).envoyerUnEmailDeConfirmationAuCandidat(candidat.getEmail(), disponibiliteDuCandidat);
        verify(emailService, never()).envoyerUnEmailDeConfirmationAuRecruteur(recruteur.getEmail(), disponibiliteDuCandidat);
    }

    @Et("ajouter un rendez-vous pour le recruteur {string} avec {string} pour le {string} à {string}")
    public void ajouterUnRendezVousPourLeRecruteurAvecPourLeÀ(String emailRecruteur, String emailCandidat, String date, String time) throws JsonProcessingException {
        var horaireEntretien = parseDateTime(date, time);

        RendezVous expectedRendezVous = new RendezVous(emailCandidat, horaireEntretien);
        Calendrier calendrier = calendrierRepository.findByRecruteur(emailRecruteur).orElse(null);
        assertThat(calendrier).isNotNull();
        assertThat(calendrier.emailRecruteur()).contains(emailRecruteur);
        assertThat(calendrier.rendezVous()).contains(expectedRendezVous);

        Optional<String> rendezVousJson = calendrierDAO.findByRecruteur(emailRecruteur);
        assertThat(rendezVousJson).isNotEmpty();

        var mapType = new TypeReference<List<RendezVousDto>>() {
        };
        var rdv = objectMapper.readValue(rendezVousJson.get(), mapType);
        assertThat(rdv).contains(new RendezVousDto(emailCandidat, horaireEntretien));
    }

    @Et("aucun rendez-vous n'est ajouté au recruteur")
    public void aucunRendezVousNEstAjoutéAuRecruteur() {
        Calendrier calendrier = calendrierRepository.findByRecruteur(recruteur.getEmail())
                .orElse(new Calendrier(0, "", List.of()));
        assertThat(calendrier.rendezVous()).doesNotContain(new RendezVous(candidat.getEmail(), disponibiliteDuCandidat));

        Optional<String> rendezVousJson = calendrierDAO.findByRecruteur(recruteur.getEmail());
        assertThat(rendezVousJson).isEmpty();
    }
}
