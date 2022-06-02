package com.soat.recruteur;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.soat.ATest;
import com.soat.planification_entretien.application.controller.command.RecruteurCommandController;
import com.soat.planification_entretien.application.controller.query.RecruteurDetailDto;
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
        RestAssured.basePath = RecruteurCommandController.PATH;
    }


    @Quand("on liste les tous les recruteurs de plus de 10 ans d'XP")
    public void onListeLesTousLesRecruteursDePlusDeAnsDXP() {
        //@formatter:off
        response = given()
                .contentType(ContentType.JSON)
                .when()
                .get(RecruteurCommandController.PATH);
        //@formatter:on
    }

    @Alors("on récupères les recruteurs suivants")
    public void onRécupèresLesRecruteursSuivants(DataTable dataTable) {
        List<RecruteurDetailDto> recruteurs = dataTableTransformEntries(dataTable, this::buildRecruteur);

        response.then().statusCode(HttpStatus.SC_OK);

        RecruteurDetailDto[] detailDtos = response.then().extract()
                .as(RecruteurDetailDto[].class);
        assertThat(Arrays.stream(detailDtos).toList())
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .containsExactlyInAnyOrder(recruteurs.toArray(RecruteurDetailDto[]::new));
    }

    private RecruteurDetailDto buildRecruteur(Map<String, String> entry) {
        return new RecruteurDetailDto(
                Integer.parseInt(entry.get("id")),
                entry.get("competence"),
                entry.get("email")
        );
    }
}
