package com.soat.planification_entretien;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.soat.ATest;
import com.soat.planification_entretien.archi_hexa.application.EntretienController;
import com.soat.planification_entretien.archi_hexa.domain.model.EntretienDetail;
import com.soat.planification_entretien.archi_hexa.infrastructure.jpa.model.JpaCandidat;
import com.soat.planification_entretien.archi_hexa.infrastructure.jpa.model.JpaEntretien;
import com.soat.planification_entretien.archi_hexa.infrastructure.jpa.model.JpaRecruteur;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.fr.Alors;
import io.cucumber.java.fr.Et;
import io.cucumber.java.fr.Etantdonné;
import io.cucumber.java.fr.Quand;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.springframework.transaction.annotation.Transactional;

import static io.restassured.RestAssured.*;
import static org.assertj.core.api.Assertions.*;

@Transactional
public class ListingEntretienATest extends ATest {

    private List<JpaCandidat> savedCandidats = new ArrayList<>();
    private List<JpaRecruteur> savedRecruteurs = new ArrayList<>();

    @Before
    @Override
    public void setUp() {
        initIntegrationTest();
    }

    @Override
    protected void initPath() {
        RestAssured.basePath = EntretienController.PATH;
    }

    @Etantdonné("les recruteurs existants")
    public void lesRecruteursExistants(DataTable dataTable) {
        List<JpaRecruteur> recruteurs = dataTableTransformEntries(dataTable, this::buildRecruteur);

        for (JpaRecruteur recruteur : recruteurs) {
            JpaRecruteur saved = entityManager.persist(recruteur);
            savedRecruteurs.add(saved);
        }
    }

    private JpaRecruteur buildRecruteur(Map<String, String> entry) {
        return new JpaRecruteur(
                entry.get("language"),
                entry.get("email"),
                Integer.parseInt(entry.get("xp")));
    }

    @Et("les candidats existants")
    public void lesCandidatsExistants(DataTable dataTable) {
        List<JpaCandidat> candidats = dataTableTransformEntries(dataTable, this::buildCandidat);

        for (JpaCandidat candidat : candidats) {
            JpaCandidat saved = entityManager.persist(candidat);
            savedCandidats.add(saved);
        }
    }

    private JpaCandidat buildCandidat(Map<String, String> entry) {
        return new JpaCandidat(
                entry.get("language"),
                entry.get("email"),
                Integer.parseInt(entry.get("xp")));
    }

    @Et("les entretiens existants")
    public void lesEntretiensExistants(DataTable dataTable) {
        List<JpaEntretien> entretiens = dataTableTransformEntries(dataTable, this::buildEntretien);

        for (JpaEntretien entretien : entretiens) {
            entityManager.persist(entretien);
        }
    }

    private JpaEntretien buildEntretien(Map<String, String> entry) {
        return JpaEntretien.of(
                savedCandidats.get(0),
                savedRecruteurs.get(0),
                LocalDateTime.parse(entry.get("horaire"), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
    }

    @Quand("on liste les tous les entretiens")
    public void onListeLesTousLesEntretiens() {
        //@formatter:off
        response = given()
                        .contentType(ContentType.JSON)
                .when()
                        .get(EntretienController.PATH);
        //@formatter:on

    }

    @Alors("on récupères les entretiens suivants")
    public void onRécupèresLesEntretiensSuivants(DataTable dataTable) {
        List<EntretienDetail> entretiens = dataTableTransformEntries(dataTable, this::buildEntretienDetail);

        EntretienDetail[] detailDtos = response.then().extract()
                .as(EntretienDetail[].class);
        assertThat(Arrays.stream(detailDtos).toList())
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .containsExactlyInAnyOrder(entretiens.toArray(EntretienDetail[]::new));
    }

    private EntretienDetail buildEntretienDetail(Map<String, String> entry) {
        return new EntretienDetail(
                Integer.parseInt(entry.get("id")),
                entry.get("candidat"),
                entry.get("recruteur"),
                entry.get("language"),
                LocalDateTime.parse(entry.get("horaire"), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
    }
}
