package com.soat.planification_entretien;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.soat.ATest;
import com.soat.planification_entretien.entretien.domain.Candidat;
import com.soat.planification_entretien.entretien.domain.ConsultantRecruteur;
import com.soat.planification_entretien.entretien.domain.EmailService;
import com.soat.planification_entretien.entretien.domain.Entretien;
import com.soat.planification_entretien.entretien.domain.EntretienRepository;
import com.soat.planification_entretien.entretien.infrastructure.controller.EntretienController;
import com.soat.planification_entretien.entretien.infrastructure.controller.EntretienDto;
import com.soat.planification_entretien.profil.domain.Prospect;
import com.soat.planification_entretien.profil.domain.ProspectRepository;
import com.soat.planification_entretien.profil.domain.Recruteur;
import com.soat.planification_entretien.profil.domain.RecruteurRepository;
import io.cucumber.java.Before;
import io.cucumber.java.fr.Alors;
import io.cucumber.java.fr.Et;
import io.cucumber.java.fr.Etantdonné;
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

import static com.soat.planification_entretien.entretien.domain.Status.*;
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

    private Prospect prospect;
    private LocalDateTime disponibiliteDuCandidat;
    private LocalDateTime disponibiliteDuRecruteur;

    @Autowired
    private EntretienRepository entretienRepository;
    @Autowired
    private ProspectRepository prospectRepository;
    @Autowired
    private RecruteurRepository recruteurRepository;

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
        prospect = new Prospect(UUID.randomUUID(), language, email, Integer.parseInt(experienceInYears));
        //entityManager.persist(candidat);
        prospectRepository.save(prospect);
        disponibiliteDuCandidat = LocalDateTime.of(LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy")), LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm")));
    }

    @Quand("on tente une planification d’entretien")
    public void onTenteUnePlanificationDEntretien() throws JsonProcessingException {
        EntretienDto entretienDto = new EntretienDto(prospect.getId(), disponibiliteDuCandidat);
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

        Entretien entretien = entretienRepository.findByEmail(prospect.getEmail());
        Entretien expectedEntretien = Entretien.of(0,
                new Candidat(prospect.getId(), prospect.getLanguage(), prospect.getEmail(), prospect.getExperienceInYears()),
                new ConsultantRecruteur(5, "Java", "recruteur7@soat.fr", 10, false),
                disponibiliteDuCandidat,
                PLANIFIE);
        assertThat(entretien).usingRecursiveComparison()
                .ignoringFields("id", "candidat.id", "recruteur.id")
                .isEqualTo(expectedEntretien);
    }

    @Alors("L’entretien n'est pas planifié")
    public void lEntretienNEstPasPlanifié() {
        response.then()
                .statusCode(HttpStatus.SC_BAD_REQUEST);

        Entretien entretien = entretienRepository.findByEmail(prospect.getEmail());
        assertThat(entretien).isNull();
    }

    @Et("aucun mail de confirmation n'est envoyé au candidat ou au recruteur")
    public void aucunMailDeConfirmationNEstEnvoyéAuCandidatOuAuRecruteur() {
        verify(emailService, never()).envoyerUnEmailDeConfirmationAuCandidat(prospect.getEmail(), disponibiliteDuCandidat);
        verify(emailService, never()).envoyerUnEmailDeConfirmationAuRecruteur(any(), any());
    }

    @Et("le recruteur n'est plus disponible")
    public void leRecruteurNEstPlusDisponible() {
        Recruteur recruteur = recruteurRepository.findById(5).get();
        assertThat(recruteur.isDisponible()).isFalse();
    }

    @Et("un mail de confirmation est envoyé au candidat et au recruteur {string}")
    public void unMailDeConfirmationEstEnvoyéAuCandidatEtAuRecruteur(String recruteurEmail) {

        verify(emailService).envoyerUnEmailDeConfirmationAuCandidat(prospect.getEmail(), disponibiliteDuCandidat);
        verify(emailService).envoyerUnEmailDeConfirmationAuRecruteur(recruteurEmail, disponibiliteDuCandidat);
    }
}
