package com.soat.planification_entretien;

import java.util.List;

import com.soat.ATest;
import com.soat.planification_entretien.entretien.command.infrastructure.controller.EntretienCommandController;
import com.soat.planification_entretien.entretien.query.domain.model.Entretien;
import com.soat.planification_entretien.entretien.query.domain.port.repository.EntretienDao;
import com.soat.planification_entretien.entretien.query.infrastructure.controller.EntretienDetailDto;
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
    private EntretienDao entretienDao;

    @Before
    @Override
    public void setUp() {
        initIntegrationTest();
    }

    @Override
    protected void initPath() {
        RestAssured.basePath = EntretienCommandController.PATH;
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

    @Alors("on récupères les entretiens après validation")
    public void onRécupèresLesEntretiensSuivants(DataTable dataTable) {
        List<EntretienDetailDto> entretiens = dataTableTransformEntries(dataTable, ListingEntretienATest::buildEntretienDetail);

        var detailDtos = entretienDao.findAll().stream()
                .map(entretien -> new EntretienDetailDto(
                        entretien.id(),
                        entretien.emailCandidat(),
                        entretien.emailRecruteur(),
                        entretien.language(),
                        entretien.horaire(),
                        entretien.status()))
                .toList();

        assertThat(detailDtos.toArray())
                .containsExactlyInAnyOrder(entretiens.toArray(EntretienDetailDto[]::new));
    }
}
