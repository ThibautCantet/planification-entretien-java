package com.soat.recruteur;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.soat.ATest;
import com.soat.planification_entretien.recruteur.query.domain.model.RecruteurDetail;
import com.soat.planification_entretien.recruteur.query.infrastructure.controller.RecruteurQueryController;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.fr.Alors;
import io.cucumber.java.fr.Quand;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

import static io.restassured.RestAssured.*;
import static org.assertj.core.api.Assertions.*;

@Transactional
public class ListingRecruteurATest extends ATest {

    @Before
    @Override
    public void setUp() {
        initIntegrationTest();
    }

    @Override
    protected void initPath() {
        RestAssured.basePath = RecruteurQueryController.PATH;
    }


    @Quand("on liste les tous les recruteurs de plus de 10 ans d'XP")
    public void onListeLesTousLesRecruteursDePlusDeAnsDXP() {
        initPath();
        //@formatter:off
        response = given()
                .contentType(ContentType.JSON)
            .when()
                .get();
        //@formatter:on
    }

    @Alors("on récupères les recruteurs suivants")
    public void onRécupèresLesRecruteursSuivants(DataTable dataTable) {
        List<RecruteurDetail> recruteurs = dataTableTransformEntries(dataTable, this::buildRecruteur);

        response.then().statusCode(HttpStatus.SC_OK);

        RecruteurDetail[] detailDtos = response.then().extract()
                .as(RecruteurDetail[].class);
        assertThat(Arrays.stream(detailDtos).toList())
                .containsExactlyInAnyOrder(recruteurs.toArray(RecruteurDetail[]::new));
    }

    private RecruteurDetail buildRecruteur(Map<String, String> entry) {
        return new RecruteurDetail(
                Integer.parseInt(entry.get("id")),
                entry.get("competence"),
                entry.get("email")
        );
    }
}
