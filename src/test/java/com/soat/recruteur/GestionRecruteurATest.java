package com.soat.recruteur;


import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.fr.Alors;
import io.cucumber.java.fr.Et;
import io.cucumber.java.fr.Etantdonné;
import io.cucumber.java.fr.Quand;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.soat.ATest;
import com.soat.planification_entretien.archi_hexa.application.RecruteurController;
import com.soat.planification_entretien.archi_hexa.application.JsonRecruteur;
import com.soat.planification_entretien.archi_hexa.domain.model.RecruteurExperimente;
import com.soat.planification_entretien.archi_hexa.infrastructure.jpa.model.JpaRecruteur;
import com.soat.planification_entretien.archi_hexa.infrastructure.jpa.repository.JpaRecruteurRepository;

public class GestionRecruteurATest extends ATest {

    @Autowired
    private JpaRecruteurRepository jpaRecruteurRepository;

    private JsonRecruteur jsonRecruteur;
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
        jsonRecruteur = new JsonRecruteur(language, email, experienceEnAnnees);
    }

    @Quand("on tente d'enregistrer le recruteur")
    public void onTenteDEnregistrerLeRecruteur() throws JsonProcessingException {
        String body = objectMapper.writeValueAsString(jsonRecruteur);
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

        final JpaRecruteur recruteur = jpaRecruteurRepository.findById(recruteurId).get();
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
        final Optional<JpaRecruteur> recruteur = jpaRecruteurRepository.findById(recruteurId);
        assertThat(recruteur).isEmpty();
    }

   @Etantdonné("les recruteurs existants dans la base")
   public void lesRecruteursExistantsDansLaBase(DataTable dataTable) {
       List<JpaRecruteur> recruteurs = dataTableTransformEntries(dataTable, this::buildRecruteur);

       for (JpaRecruteur recruteur : recruteurs) {
           jpaRecruteurRepository.save(recruteur);
       }
   }

    @Quand("on cherche les recuteurs ayant au moins 10 d'experience")
    public void onChercheLesRecuteursAyantAuMoinsDExperience() {
       response = given()
             .contentType(ContentType.JSON)
             .when()
             .get(RecruteurController.PATH +"/experimente");
    }

    @Alors("on récupères les recuteurs suivants")
    public void onRécupèresLesRecuteursSuivants(DataTable dataTable) {
       response.then().statusCode(HttpStatus.SC_OK);
       RecruteurExperimente[] recuteurResponses = response.then().extract()
             .as(RecruteurExperimente[].class);

       List<RecruteurExperimente> expectedRecruteurExperimente = dataTableTransformEntries(dataTable, this::buildRecruteurExperimente);
       assertThat(Arrays.stream(recuteurResponses).toList())
             .containsExactlyInAnyOrder(expectedRecruteurExperimente.toArray(RecruteurExperimente[]::new));
    }

   private RecruteurExperimente buildRecruteurExperimente(Map<String, String> entry) {
      return new RecruteurExperimente(
            Integer.parseInt(entry.get("id")),
            entry.get("competence"),
            entry.get("email"));
   }

    private JpaRecruteur buildRecruteur(Map<String, String> entry) {
        return new JpaRecruteur(
              entry.get("language"),
              entry.get("email"),
              Integer.parseInt(entry.get("xp")));
    }
}
