package com.soat.candidat;

import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.soat.ATest;
import com.soat.planification_entretien.profil.domain.Prospect;
import com.soat.planification_entretien.profil.domain.ProspectRepository;
import com.soat.planification_entretien.profil.infrastructure.controller.CandidatController;
import com.soat.planification_entretien.profil.infrastructure.controller.CandidatDto;
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
    private ProspectRepository prospectRepository;

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
        var newProspectId = response.as(UUID.class);

        final Prospect prospect = prospectRepository.findById(newProspectId).get();
        assertThat(prospect)
                .extracting(Prospect::getLanguage, Prospect::getEmail, Prospect::getExperienceInYears)
                .containsExactly(language, email, Integer.parseInt(experienceEnAnnees));
        assertThat(prospect.getId()).isNotNull();
    }

    @Alors("l'enregistrement est refusé")
    public void lEnregistrementEstRefusé() {
        response.then()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Et("le candidat n'est pas enregistré")
    public void leCandidatNEstPasEnregistré() {
        final var prospects = prospectRepository.findAll();
        assertThat(prospects).isEmpty();
    }
}
