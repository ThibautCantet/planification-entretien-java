package com.soat.recruteur;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.soat.ATest;
import com.soat.recruteur.infrastructure.controller.RecruteurController;
import com.soat.recruteur.infrastructure.controller.RecruteurDetailDto;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.fr.Alors;
import io.cucumber.java.fr.Quand;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
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
        RestAssured.basePath = RecruteurController.PATH;
    }


    @Quand("on liste les tous les recruteurs de plus de 10 ans d'XP")
    public void onListeLesTousLesRecruteursDePlusDeAnsDXP() {
        //@formatter:off
        response = given()
                .contentType(ContentType.JSON)
                .when()
                .get(RecruteurController.PATH);
        //@formatter:on
    }

    @Alors("on récupères les recruteurs suivants")
    public void onRécupèresLesRecruteursSuivants(DataTable dataTable) {
        List<RecruteurDetailDto> recruteurs = dataTableTransformEntries(dataTable, this::buildRecruteur);

        RecruteurDetailDto[] detailDtos = response.then().extract()
                .as(RecruteurDetailDto[].class);
        assertThat(Arrays.stream(detailDtos).toList())
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .containsExactlyInAnyOrder(recruteurs.toArray(RecruteurDetailDto[]::new));
    }

    private RecruteurDetailDto buildRecruteur(Map<String, String> entry) {
        return new RecruteurDetailDto(
                UUID.fromString(entry.get("id")),
                entry.get("email"),
                entry.get("competence"));
    }
}
