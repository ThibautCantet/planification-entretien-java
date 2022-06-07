package com.soat.planification_entretien;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.soat.ATest;
import com.soat.planification_entretien.application.controller.command.EntretienCommandController;
import com.soat.planification_entretien.application.controller.command.EntretienDto;
import com.soat.planification_entretien.application.controller.query.RendezVousDto;
import com.soat.planification_entretien.domain.candidat.repository.CandidatRepository;
import com.soat.planification_entretien.domain.entretien.command.entity.Calendrier;
import com.soat.planification_entretien.domain.entretien.command.entity.Candidat;
import com.soat.planification_entretien.domain.entretien.command.entity.Entretien;
import com.soat.planification_entretien.domain.entretien.command.entity.Recruteur;
import com.soat.planification_entretien.domain.entretien.command.entity.RendezVous;
import com.soat.planification_entretien.domain.entretien.command.repository.EntretienRepository;
import com.soat.planification_entretien.domain.entretien.listener.service.EmailService;
import com.soat.planification_entretien.domain.recruteur.command.repository.RecruteurRepository;
import com.soat.planification_entretien.domain.rendez_vous.command.repository.CalendrierRepository;
import com.soat.planification_entretien.domain.rendez_vous.query.dao.CalendrierDAO;
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
    private Recruteur recruteur;
    private RendezVous rendezVousExistant;

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
        var candidat = new com.soat.planification_entretien.domain.candidat.entity.Candidat(1, language, email, Integer.parseInt(experienceInYears));
        this.candidat = new Candidat(1, language, email, Integer.parseInt(experienceInYears));
        //entityManager.persist(candidat);
        var savedCandidat = candidatRepository.save(candidat);
        this.candidat = new Candidat(savedCandidat.getId(), savedCandidat.getLanguage(), savedCandidat.getEmail(), savedCandidat.getExperienceInYears());
        disponibiliteDuCandidat = LocalDateTime.of(LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy")), LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm")));
    }

    @Etqu("un recruteur {string} \\({string}) qui a {string} ans d’XP")
    public void unRecruteurQuiAAnsDXP(String language, String email, String experienceInYears) {
        var recruteur = new com.soat.planification_entretien.domain.recruteur.command.entity.Recruteur(1, language, email, Integer.parseInt(experienceInYears), new ArrayList<>());
        this.recruteur = new Recruteur(1, language, email, Integer.parseInt(experienceInYears), new ArrayList<>());
        //entityManager.persist(recruteur);
        var savedRecruteur = recruteurRepository.save(recruteur);
        this.recruteur = new Recruteur(savedRecruteur.getId(), savedRecruteur.getLanguage(), savedRecruteur.getEmail(), savedRecruteur.getExperienceInYears(), savedRecruteur.getRendezVous());
    }

    @Et("qui est occupé le {string} à {string}")
    public void quiEstOccupéLeÀ(String date, String time) {
        var horaireRendezVous = LocalDateTime.of(LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy")), LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm")));
        var rendezVous = new ArrayList<RendezVous>();
        rendezVousExistant = new RendezVous("autre@candidat.fr", horaireRendezVous);
        rendezVous.add(rendezVousExistant);
        calendrierRepository.save(new Calendrier(1, recruteur.email(), rendezVous));
    }

    @Quand("on tente une planification d’entretien")
    public void onTenteUnePlanificationDEntretien() throws JsonProcessingException {
        EntretienDto entretienDto = new EntretienDto(candidat.id(), recruteur.id(), disponibiliteDuCandidat);
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

    @Alors("L’entretien est planifié")
    public void lEntretienEstPlanifié() {
        response.then()
                .statusCode(HttpStatus.SC_CREATED);

        Entretien entretien = entretienRepository.findByCandidat(candidat);
        var expectedRecruteur = new Recruteur(recruteur.id(), recruteur.language(), recruteur.email(), recruteur.experienceInYears(), List.of(new RendezVous(candidat.email(), disponibiliteDuCandidat)));
        Entretien expectedEntretien = Entretien.of(candidat, expectedRecruteur, disponibiliteDuCandidat);
        assertThat(entretien).usingRecursiveComparison()
                .ignoringFields("id", "candidat.id", "recruteur.id")
                .isEqualTo(expectedEntretien);
    }

    @Et("un mail de confirmation est envoyé au candidat et au recruteur")
    public void unMailDeConfirmationEstEnvoyéAuCandidatEtAuRecruteur() {
        verify(emailService).envoyerUnEmailDeConfirmationAuCandidat(candidat.email(), disponibiliteDuCandidat);
        verify(emailService).envoyerUnEmailDeConfirmationAuRecruteur(recruteur.email(), disponibiliteDuCandidat);
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
        verify(emailService, never()).envoyerUnEmailDeConfirmationAuCandidat(candidat.email(), disponibiliteDuCandidat);
        verify(emailService, never()).envoyerUnEmailDeConfirmationAuRecruteur(recruteur.email(), disponibiliteDuCandidat);
    }

    @Et("ajouter un rendez-vous pour le recruteur {string} avec {string} pour le {string} à {string}")
    public void ajouterUnRendezVousPourLeRecruteurAvecPourLeÀ(String emailRecruteur, String emailCandidat, String date, String time) throws JsonProcessingException {
        var horaireEntretien = LocalDateTime.of(LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy")), LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm")));

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
        assertThat(rdv).containsExactly(new RendezVousDto(emailCandidat, horaireEntretien));
    }

    @Et("aucun rendez-vous n'est ajouté au recruteur")
    public void aucunRendezVousNEstAjoutéAuRecruteur() {
        Optional<Calendrier> calendrier = calendrierRepository.findByRecruteur(recruteur.email());
        assertThat(calendrier.get().rendezVous()).containsExactly(rendezVousExistant);

        Optional<String> rendezVousJson = calendrierDAO.findByRecruteur(recruteur.email());
        assertThat(rendezVousJson).isEmpty();
    }
}
