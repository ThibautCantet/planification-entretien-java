package com.soat.planification_entretien;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.soat.ATest;
import com.soat.planification_entretien.entretien.command.application.controller.EntretienCommandController;
import com.soat.planification_entretien.entretien.command.application.controller.Status;
import com.soat.planification_entretien.entretien.command.application.controller.StatutDto;
import com.soat.planification_entretien.entretien.query.application.controller.EntretienDetailDto;
import com.soat.planification_entretien.rendez_vous.command.domain.entity.Calendrier;
import com.soat.planification_entretien.rendez_vous.command.repository.CalendrierRepository;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.fr.Alors;
import io.cucumber.java.fr.Et;
import io.cucumber.java.fr.Quand;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;

import static com.soat.planification_entretien.entretien.command.application.controller.Status.*;
import static io.restassured.RestAssured.*;
import static org.assertj.core.api.Assertions.*;

public class StatusEntretienATest extends ATest {

    @Autowired
    private CalendrierRepository calendrierRepository;

    @Before
    @Override
    public void setUp() {
        initIntegrationTest();
    }

    @Override
    protected void initPath() {
        RestAssured.basePath = EntretienCommandController.PATH;
    }

    @Quand("on confirme l'entretien {string}")
    public void onConfirmeLEntretien(String entretienId) throws JsonProcessingException {
        StatutDto status = new StatutDto(CONFIRME);
        String body = objectMapper.writeValueAsString(status);

        initPath();
        //@formatter:off
        response = given()
                    .log().all()
                    .body(body)
                    .header("Content-Type", ContentType.JSON)
                .when()
                    .put(entretienId);
        //@formatter:on
    }

    @Quand("on annule l'entretien {string}")
    public void onAnnuleLEntretien(String entretienId) throws JsonProcessingException {
        StatutDto status = new StatutDto(ANNULE);
        String body = objectMapper.writeValueAsString(status);

        initPath();
        //@formatter:off
        response = given()
                .log().all()
                .body(body)
                .header("Content-Type", ContentType.JSON)
                .when()
                .put(entretienId);
        //@formatter:on
    }

    @Alors("son status est modifié")
    public void sonStatusEstModifié() {
        response.then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Et("on récupères les entretiens avec leur statut suivants")
    public void onRécupèresLesEntretiensAvecLeurStatutSuivants(DataTable dataTable) {
        List<EntretienDetailDto> entretiens = dataTableTransformEntries(dataTable, this::buildEntretienDetail);

        //@formatter:off
        var response = given()
                    .log().all()
                    .header("Content-Type", ContentType.JSON)
                .when()
                .get();
        //@formatter:on

        var detailDtos = response.then().extract()
                .as(EntretienDetailDto[].class);
        assertThat(Arrays.stream(detailDtos).toList())
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .containsExactlyInAnyOrder(entretiens.toArray(EntretienDetailDto[]::new));
    }

    private EntretienDetailDto buildEntretienDetail(Map<String, String> entry) {
        return new EntretienDetailDto(
                entry.get("id"),
                entry.get("candidat"),
                entry.get("recruteur"),
                entry.get("language"),
                LocalDateTime.parse(entry.get("horaire"), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                entry.get("status").toUpperCase()
        );
    }

    @Et("le rendez-vous n'existe plus")
    public void leRendezVousNExistePlus() {
        List<Calendrier> calendriers = calendrierRepository.findAll();

        assertThat(calendriers.get(0).rendezVous()).isEmpty();
    }
}
