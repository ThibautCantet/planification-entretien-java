package com.soat.recruteur;


import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.soat.ATest;
import com.soat.planification_entretien.application.controller.command.RecruteurCommandController;
import com.soat.planification_entretien.application.controller.query.EntretienDetailDto;
import com.soat.planification_entretien.domain.recruteur.command.entity.Recruteur;
import com.soat.planification_entretien.domain.recruteur.command.repository.RecruteurRepository;
import com.soat.planification_entretien.application.controller.command.RecruteurDto;
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

public class CreationRecruteurATest extends ATest {

    @Autowired
    private RecruteurRepository recruteurRepository;

    private RecruteurDto recruteurDto;
    private Integer recruteurId = 1;

    @Before
    @Override
    public void setUp() {
        initIntegrationTest();
    }

    @Override
    protected void initPath() {
        RestAssured.basePath = RecruteurCommandController.PATH;
    }

    @Etantdonné("un recruteur {string} \\({string}) avec {string} ans d’expériences")
    public void unRecruteurAvecAnsDExpériences(String language, String email, String experienceEnAnnees) {
        recruteurDto = new RecruteurDto(language, email, !experienceEnAnnees.isBlank() ? Integer.parseInt(experienceEnAnnees) : null);
    }

    @Quand("on tente d'enregistrer le recruteur")
    public void onTenteDEnregistrerLeRecruteur() throws JsonProcessingException {
        String body = objectMapper.writeValueAsString(recruteurDto);
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

    @Alors("le recruteur est correctement enregistré avec ses informations {string}, {string} et {string} ans d’expériences")
    public void leRecruteurEstCorrectementEnregistréAvecSesInformationsEtAnsDExpériences(String language, String email, String experienceEnAnnees) {
        response.then()
                .statusCode(HttpStatus.SC_CREATED);

        Integer newId = response.then().extract()
                .as(Integer.class);

        final Recruteur recruteur = recruteurRepository.findById(newId).get();
        assertThat(recruteur).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(Recruteur.create(language, email, Integer.parseInt(experienceEnAnnees)));
    }

    @Alors("l'enregistrement du recruteur est refusé")
    public void lEnregistrementDuRecruteurEstRefusé() {
        response.then()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Et("le recruteur n'est pas enregistré")
    public void leRecruteurNEstPasEnregistré() {
        final Optional<Recruteur> recruteur = recruteurRepository.findById(recruteurId);
        assertThat(recruteur).isEmpty();
    }
}
