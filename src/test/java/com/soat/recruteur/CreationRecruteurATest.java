package com.soat.recruteur;


import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.soat.ATest;
import com.soat.planification_entretien.recruteur.command.application.controller.RecruteurCommandController;
import com.soat.planification_entretien.recruteur.command.domain.entity.Recruteur;
import com.soat.planification_entretien.recruteur.command.domain.repository.RecruteurRepository;
import com.soat.planification_entretien.recruteur.command.application.controller.RecruteurDto;
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

        String newId = response.then().extract()
                .as(String.class);

        final Recruteur recruteur = recruteurRepository.findById(newId).get();
        assertThat(recruteur).usingRecursiveComparison()
                .isEqualTo(Recruteur.create(newId, language, email, Integer.parseInt(experienceEnAnnees)));
    }

    @Alors("l'enregistrement du recruteur est refusé")
    public void lEnregistrementDuRecruteurEstRefusé() {
        response.then()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Et("le recruteur n'est pas enregistré")
    public void leRecruteurNEstPasEnregistré() {
        final List<Recruteur> recruteurs = recruteurRepository.findAll();
        assertThat(recruteurs).isEmpty();
    }
}
