package com.soat.rendezVous;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.soat.ATest;
import com.soat.planification_entretien.application.controller.RendezVousController;
import com.soat.planification_entretien.domain.entretien.entities.Calendrier;
import com.soat.planification_entretien.domain.rendez_vous.CalendrierRepository;
import com.soat.planification_entretien.domain.rendez_vous.RendezVous;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.fr.Alors;
import io.cucumber.java.fr.Et;
import io.cucumber.java.fr.Quand;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static io.restassured.RestAssured.*;
import static org.assertj.core.api.Assertions.*;

@Transactional
public class ListerRendezVousATest extends ATest {

    @Autowired
    private CalendrierRepository calendrierRepository;

    @Override
    protected void setUp() {
        initIntegrationTest();
    }

    @Override
    protected void initPath() {
        RestAssured.basePath = RendezVousController.PATH;
    }

    @Quand("on liste les rendez-vous du recruteur {int}")
    public void onListeLesRendezVousDuRecruteur(Integer recruteurId) {
        //@formatter:off
        response = given()
                .contentType(ContentType.JSON)
                .when()
                .get(RendezVousController.PATH + "/recruteur/" + recruteurId);
        //@formatter:on
    }

    @Alors("on récupères les rendez-vous suivants")
    public void onRécupèresLesRendezVousSuivants(DataTable dataTable) {
        List<RendezVousDto> rendezVousDtos = dataTableTransformEntries(dataTable, this::buildRendezVous);

        var rendezVous = response.then().extract()
                .as(RendezVousDto[].class);
        assertThat(Arrays.stream(rendezVous).toList())
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .containsExactlyInAnyOrder(rendezVousDtos.toArray(RendezVousDto[]::new));
    }

    private RendezVousDto buildRendezVous(Map<String, String> entry) {
        return new RendezVousDto(
                entry.get("candidat"),
                LocalDateTime.parse(entry.get("horaire"), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
    }

    @Et("les rendez-vous suivants")
    public void lesRendezVousSuivants(DataTable dataTable) {
        List<Calendrier> calendriers = dataTableTransformEntries(dataTable, this::buildCalendrier);

        calendrierRepository.saveAll(calendriers);
    }

    private Calendrier buildCalendrier(Map<String, String> entry) {
        List<RendezVous> rendezVous = Arrays.stream(entry.get("rendez-vous").split(";"))
                .map(String::trim)
                .map(this::toRendezVous)
                .toList();
        return new Calendrier(
                Integer.parseInt(entry.get("id")),
                entry.get("recruteur"),
                rendezVous
        );
    }

    private RendezVous toRendezVous(String rdv) {
        String[] rendezVousSplit = rdv.split(",");
        return new RendezVous(
                rendezVousSplit[0],
                LocalDateTime.parse(rendezVousSplit[1], DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
    }
}