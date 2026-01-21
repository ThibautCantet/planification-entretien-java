package com.soat.planification_entretien;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.soat.ATest;
import com.soat.planification_entretien.entretien.domain.Candidat;
import com.soat.planification_entretien.entretien.domain.Entretien;
import com.soat.planification_entretien.entretien.domain.EntretienRepository;
import com.soat.planification_entretien.entretien.domain.RecruteurPlanifié;
import com.soat.planification_entretien.entretien.domain.StatusEntretien;
import com.soat.planification_entretien.entretien.infrastructure.controller.EntretienController;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.fr.Alors;
import io.cucumber.java.fr.Quand;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.springframework.beans.factory.annotation.Autowired;

import static io.restassured.RestAssured.*;
import static org.assertj.core.api.Assertions.*;

public class WorkflowEntretienATest extends ATest {

    @Autowired
    private EntretienRepository entretienRepository;

    @Before
    @Override
    public void setUp() {
        initIntegrationTest();
    }

    @Override
    protected void initPath() {
        RestAssured.basePath = EntretienController.PATH;
    }

    @Quand("on valide l'entretien {int}")
    public void onValideLEntretien(int entretienId) {
        initPath();
        //@formatter:off
        response = given()
                .log().all()
                .header("Content-Type", ContentType.JSON)
                .when()
                .patch(entretienId + "/valider");
        //@formatter:on
    }

    @Quand("on annule l'entretien {int}")
    public void onAnnuleLEntretien(int entretienId) {
        initPath();
        //@formatter:off
        response = given()
                .log().all()
                .header("Content-Type", ContentType.JSON)
                .when()
                .patch(entretienId + "/annuler");
        //@formatter:on
    }

    @Alors("on récupères les entretiens suivants en base")
    public void onRécupèresLesEntretiensSuivantsEnBase(DataTable dataTable) {
        assertThat(response.statusCode()).isEqualTo(204);
        List<Entretien> entretiens = dataTableTransformEntries(dataTable, this::buildEntretien);

        List<Entretien> savedEntretiens = entretienRepository.findAll();

        assertThat(savedEntretiens)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("candidat.id",
                        "candidat.experienceEnAnnees",
                        "candidat.profil",
                        "recruteur.id",
                        "recruteur.experienceEnAnnees",
                        "recruteur.profil")
                .containsExactlyInAnyOrder(entretiens.toArray(Entretien[]::new));
    }

    private Entretien buildEntretien(Map<String, String> entry) {
        return Entretien.of(
                Integer.parseInt(entry.get("id")),
                new Candidat(null, entry.get("language"), entry.get("candidat"), 0),
                new RecruteurPlanifié(null, entry.get("language"), entry.get("recruteur"), 0),
                LocalDateTime.parse(entry.get("horaire"), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                entry.get("status"));
    }
}
