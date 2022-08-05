package com.soat.candidat;

import java.util.Optional;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.soat.ATest;
import com.soat.planification_entretien.candidat.command.application.controller.CandidatDto;
import com.soat.planification_entretien.candidat.command.application.controller.CandidatCommandController;
import com.soat.planification_entretien.candidat.command.domain.entity.Candidat;
import com.soat.planification_entretien.candidat.command.repository.CandidatRepository;
import io.cucumber.java.Before;
import io.cucumber.java.fr.Alors;
import io.cucumber.java.fr.Et;
import io.cucumber.java.fr.Etantdonné;
import io.cucumber.java.fr.Quand;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;

import static io.restassured.RestAssured.*;
import static org.assertj.core.api.Assertions.*;

public class CreationCandidatATest extends ATest {

    @Autowired
    private CandidatRepository candidatRepository;

    private CandidatDto candidatDto;

    @Before
    @Override
    public void setUp() {
        initIntegrationTest();
    }

    @Override
    protected void initPath() {
        RestAssured.basePath = CandidatCommandController.PATH;
    }

    @Etantdonné("un candidat {string} \\({string}) avec {string} ans d’expériences")
    public void unCandidatAvecAnsDExpériences(String language, String email, String experienceEnAnnees) {
        candidatDto = new CandidatDto(language, email, experienceEnAnnees);
    }

    @Quand("on tente d'enregistrer le candidat")
    public void onTenteDEnregistrerLeCandidat() throws JsonProcessingException {
        String body = objectMapper.writeValueAsString(candidatDto);
        initPath();
        //@formatter:off
        response = given()
                .log().all()
                .header("Content-Type", ContentType.JSON)
                .body(body)
        .when()
                .post("/");
        //@formatter:on
    }

    @Alors("le candidat est correctement enregistré avec ses informations {string}, {string} et {string} ans d’expériences")
    public void leCandidatEstCorrectementEnregistréAvecSesInformationsEtAnsDExpériences(String language, String email, String experienceEnAnnees) {
        response.then()
                .statusCode(HttpStatus.SC_CREATED);

        var candidatId = response.then().extract().as(UUID.class);

        final Candidat candidat = candidatRepository.findById(candidatId).get();
        assertThat(candidat).usingRecursiveComparison()
                .isEqualTo(new Candidat(candidatId, language, email, Integer.parseInt(experienceEnAnnees)));
    }

    @Alors("l'enregistrement est refusé")
    public void lEnregistrementEstRefusé() {
        response.then()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Et("le candidat n'est pas enregistré")
    public void leCandidatNEstPasEnregistré() {
        var candidats = candidatRepository.findAll();
        assertThat(candidats).isEmpty();
    }
}
