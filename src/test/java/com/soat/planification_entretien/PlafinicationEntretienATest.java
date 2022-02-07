package com.soat.planification_entretien;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.soat.ATest;
import com.soat.planification_entretien.controller.EntretienController;
import com.soat.planification_entretien.controller.EntretienDto;
import com.soat.planification_entretien.model.Candidat;
import com.soat.planification_entretien.model.Entretien;
import com.soat.planification_entretien.model.Recruteur;
import com.soat.planification_entretien.repository.EntretienRepository;
import com.soat.planification_entretien.service.EmailService;
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
@AutoConfigureTestEntityManager
@SpringBootTest(webEnvironment = RANDOM_PORT)
@DirtiesContext
@CucumberContextConfiguration
@ActiveProfiles("AcceptanceTest")
public class PlafinicationEntretienATest extends ATest {

    private Candidat candidat;
    private LocalDateTime disponibiliteDuCandidat;
    private Recruteur recruteur;
    private LocalDateTime disponibiliteDuRecruteur;

    @Autowired
    private EntretienRepository entretienRepository;

    @Autowired
    private EmailService emailService;

    @Before
    @Override
    public void setUp() {
        initIntegrationTest();
    }

    @Override
    protected void initPath() {
        RestAssured.basePath = EntretienController.PATH;
    }

    @Etantdonné("un candidat {string} \\({string}) avec {string} ans d’expériences qui est disponible {string} à {string}")
    public void unCandidatAvecAnsDExpériencesQuiEstDisponibleÀ(String language, String email, String experienceInYears, String date, String time) {
        candidat = new Candidat(language, email, Integer.parseInt(experienceInYears));
        entityManager.persist(candidat);
        disponibiliteDuCandidat = LocalDateTime.of(LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy")), LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm")));
    }

    @Etqu("un recruteur {string} \\({string}) qui a {string} ans d’XP qui est dispo {string} à {string}")
    public void unRecruteurQuiAAnsDXPQuiEstDispo(String language, String email, String experienceInYears, String date, String time) {
        recruteur = new Recruteur(language, email, Integer.parseInt(experienceInYears));
        entityManager.persist(recruteur);
        disponibiliteDuRecruteur = LocalDateTime.of(LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy")), LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm")));
    }

    @Quand("on tente une planification d’entretien")
    public void onTenteUnePlanificationDEntretien() throws JsonProcessingException {
        EntretienDto entretienDto = new EntretienDto(candidat.getId(), recruteur.getId(), disponibiliteDuCandidat, disponibiliteDuRecruteur);
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
        Entretien expectedEntretien = Entretien.of(candidat, recruteur, disponibiliteDuCandidat);
        assertThat(entretien).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expectedEntretien);
    }

    @Et("un mail de confirmation est envoyé au candidat et au recruteur")
    public void unMailDeConfirmationEstEnvoyéAuCandidatEtAuRecruteur() {
        verify(emailService).envoyerUnEmailDeConfirmationAuCandidat(candidat.getEmail(), disponibiliteDuCandidat);
        verify(emailService).envoyerUnEmailDeConfirmationAuRecruteur(recruteur.getEmail(), disponibiliteDuCandidat);
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
}
