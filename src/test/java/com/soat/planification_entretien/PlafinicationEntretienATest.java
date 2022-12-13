package com.soat.planification_entretien;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.soat.ATest;
import com.soat.planification_entretien.archi_hexa.application.EntretienController;
import com.soat.planification_entretien.archi_hexa.application.JsonEntretien;
import com.soat.planification_entretien.archi_hexa.infrastructure.jpa.model.JpaCandidat;
import com.soat.planification_entretien.archi_hexa.infrastructure.jpa.model.JpaEntretien;
import com.soat.planification_entretien.archi_hexa.infrastructure.jpa.model.JpaRecruteur;
import com.soat.planification_entretien.archi_hexa.infrastructure.jpa.repository.JpaEntretienRepository;
import com.soat.planification_entretien.archi_hexa.domain.port.NotificationPort;
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

    private JpaCandidat candidat;
    private LocalDateTime disponibiliteDuCandidat;
    private JpaRecruteur recruteur;
    private LocalDateTime disponibiliteDuRecruteur;

    @Autowired
    private JpaEntretienRepository jpaEntretienRepository;

    @Autowired
    private NotificationPort notificationPort;

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
        candidat = new JpaCandidat(language, email, Integer.parseInt(experienceInYears));
        entityManager.persist(candidat);
        disponibiliteDuCandidat = LocalDateTime.of(LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy")), LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm")));
    }

    @Etqu("un recruteur {string} \\({string}) qui a {string} ans d’XP qui est dispo {string} à {string}")
    public void unRecruteurQuiAAnsDXPQuiEstDispo(String language, String email, String experienceInYears, String date, String time) {
        recruteur = new JpaRecruteur(language, email, Integer.parseInt(experienceInYears));
        entityManager.persist(recruteur);
        disponibiliteDuRecruteur = LocalDateTime.of(LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy")), LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm")));
    }

    @Quand("on tente une planification d’entretien")
    public void onTenteUnePlanificationDEntretien() throws JsonProcessingException {
        JsonEntretien jsonEntretien = new JsonEntretien(candidat.getId(), recruteur.getId(), disponibiliteDuCandidat, disponibiliteDuRecruteur);
        String body = objectMapper.writeValueAsString(jsonEntretien);
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

        JpaEntretien entretien = jpaEntretienRepository.findByCandidat(candidat);
        JpaEntretien expectedEntretien = JpaEntretien.of(candidat, recruteur, disponibiliteDuCandidat);
        assertThat(entretien).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expectedEntretien);
    }

    @Et("un mail de confirmation est envoyé au candidat et au recruteur")
    public void unMailDeConfirmationEstEnvoyéAuCandidatEtAuRecruteur() {
        verify(notificationPort).envoyerUnEmailDeConfirmationAuCandidat(candidat.getEmail(), disponibiliteDuCandidat);
        verify(notificationPort).envoyerUnEmailDeConfirmationAuRecruteur(recruteur.getEmail(), disponibiliteDuCandidat);
    }

    @Alors("L’entretien n'est pas planifié")
    public void lEntretienNEstPasPlanifié() {
        response.then()
                .statusCode(HttpStatus.SC_BAD_REQUEST);

        JpaEntretien entretien = jpaEntretienRepository.findByCandidat(candidat);
        assertThat(entretien).isNull();
    }

    @Et("aucun mail de confirmation n'est envoyé au candidat ou au recruteur")
    public void aucunMailDeConfirmationNEstEnvoyéAuCandidatOuAuRecruteur() {
        verify(notificationPort, never()).envoyerUnEmailDeConfirmationAuCandidat(candidat.getEmail(), disponibiliteDuCandidat);
        verify(notificationPort, never()).envoyerUnEmailDeConfirmationAuRecruteur(recruteur.getEmail(), disponibiliteDuCandidat);
    }
}
