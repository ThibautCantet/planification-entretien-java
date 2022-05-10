package com.soat.candidat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.soat.ATest;
import com.soat.candidat.domain.Candidat;
import com.soat.candidat.infrastructure.controller.CandidatController;
import com.soat.candidat.infrastructure.controller.CandidatDto;
import com.soat.candidat.domain.CandidatRepository;
import com.soat.shared.infrastructure.repository.JpaCandidatCrudRepository;
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

    @Autowired
    private JpaCandidatCrudRepository jpaCandidatCrudRepository;

    private CandidatDto candidatDto;
    private Integer candidatId = 1;

    @Before
    @Override
    public void setUp() {
        initIntegrationTest();
    }

    @Override
    protected void initPath() {
        RestAssured.basePath = CandidatController.PATH;
    }

    @Etantdonné("un candidat {string} \\({string}) avec {int} ans d’expériences")
    public void unCandidatAvecAnsDExpériences(String language, String email, Integer experienceEnAnnees) {
        candidatDto = new CandidatDto(language, email, experienceEnAnnees);
    }

    @Etantdonné("un candidat {string} \\({string})")
    public void unCandidatAvecAnsDExpériences(String language, String email) {
        candidatDto = new CandidatDto(language, email, null);
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

    @Alors("le candidat est correctement enregistré avec ses informations {string}, {string} et {int} ans d’expériences")
    public void leCandidatEstCorrectementEnregistréAvecSesInformationsEtAnsDExpériences(String language, String email, Integer experienceEnAnnees) {
        response.then()
                .statusCode(HttpStatus.SC_CREATED);

        final var candidat = jpaCandidatCrudRepository.findById(candidatId).get();
        assertThat(candidat).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(Candidat.of(language, email, experienceEnAnnees));
    }

    @Alors("l'enregistrement est refusé")
    public void lEnregistrementEstRefusé() {
        response.then()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Et("le candidat n'est pas enregistré")
    public void leCandidatNEstPasEnregistré() {
        final var candidat = jpaCandidatCrudRepository.findById(candidatId);
        assertThat(candidat).isEmpty();
    }
}
