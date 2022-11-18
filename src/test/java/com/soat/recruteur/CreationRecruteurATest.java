package com.soat.recruteur;


import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.soat.ATest;
import com.soat.planification_entretien.archi_hexa.application.RecruteurController;
import com.soat.planification_entretien.archi_hexa.application.RecruteurExperimenteDto;
import com.soat.planification_entretien.archi_hexa.domain.entity.EntretienDetail;
import com.soat.planification_entretien.archi_hexa.infrastructure.jpa.model.JpaRecruteur;
import com.soat.planification_entretien.archi_hexa.infrastructure.jpa.repository.RecruteurRepository;
import com.soat.planification_entretien.archi_hexa.application.RecruteurDto;
import io.cucumber.datatable.DataTable;
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
        RestAssured.basePath = RecruteurController.PATH;
    }

    @Etantdonné("un recruteur {string} \\({string}) avec {string} ans d’expériences")
    public void unRecruteurAvecAnsDExpériences(String language, String email, String experienceEnAnnees) {
        recruteurDto = new RecruteurDto(language, email, experienceEnAnnees);
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

        final JpaRecruteur recruteur = recruteurRepository.findById(recruteurId).get();
        assertThat(recruteur).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(new JpaRecruteur(language, email, Integer.parseInt(experienceEnAnnees)));
    }

    @Alors("l'enregistrement du recruteur est refusé")
    public void lEnregistrementDuRecruteurEstRefusé() {
        response.then()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Et("le recruteur n'est pas enregistré")
    public void leRecruteurNEstPasEnregistré() {
        final Optional<JpaRecruteur> recruteur = recruteurRepository.findById(recruteurId);
        assertThat(recruteur).isEmpty();
    }

    @Etantdonné("les recruteurs existants sont")
    public void lesRecruteursExistantsSont(DataTable dataTable) {
        List<JpaRecruteur> recruteurs = dataTableTransformEntries(dataTable, this::buildRecruteur);

        for (JpaRecruteur recruteur : recruteurs) {
            recruteurRepository.save(recruteur);
        }

    }

    private JpaRecruteur buildRecruteur(Map<String, String> entry) {
        return new JpaRecruteur(
                entry.get("language"),
                entry.get("email"),
                Integer.parseInt(entry.get("xp")));
    }

    @Quand("on liste les tous les recruteurs ayant plus de dix ans d'exp")
    public void onListeLesTousLesRecruteursAyantPlusDeAnsDExp() {
        initPath();
        //@formatter:off
        response = given()
                .log().all()
                .header("Content-Type", ContentType.JSON)
                .when()
                .get("/experimente");
        //@formatter:on
    }

    @Alors("on récupères les recruteurs suivants")
    public void onRécupèresLesRecruteursSuivants(DataTable datatable) {
        var recruteurs = dataTableTransformEntries(datatable, this::buildRecruteurExperimente);

        assertThat(response.statusCode()).isEqualTo(200);
        RecruteurExperimenteDto[] recruteurExperimenteDtos = response.then().extract()
                .as(RecruteurExperimenteDto[].class);
        assertThat(Arrays.stream(recruteurExperimenteDtos).toList())
                .containsExactlyInAnyOrder(recruteurs.toArray(RecruteurExperimenteDto[]::new));
    }

    private RecruteurExperimenteDto buildRecruteurExperimente(Map<String, String> entry) {
        return new RecruteurExperimenteDto(
                entry.get("email"),
                entry.get("languageXP"));
    }
}
