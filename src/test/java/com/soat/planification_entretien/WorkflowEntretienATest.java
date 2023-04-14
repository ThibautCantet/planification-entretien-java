package com.soat.planification_entretien;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import com.soat.ATest;
import com.soat.planification_entretien.entretien.command.domain.Status;
import com.soat.planification_entretien.entretien.command.infrastructure.controller.EntretienCommandController;
import com.soat.planification_entretien.entretien.query.application.EntretienProjectionDao;
import com.soat.planification_entretien.entretien.query.infrastructure.dao.IEntretienImpl;
import com.soat.planification_entretien.entretien.query.application.EntretienDao;
import com.soat.planification_entretien.entretien.query.application.IEntretien;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.fr.Alors;
import io.cucumber.java.fr.Et;
import io.cucumber.java.fr.Quand;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.springframework.beans.factory.annotation.Autowired;

import static io.restassured.RestAssured.*;
import static org.assertj.core.api.Assertions.*;

public class WorkflowEntretienATest extends ATest {

    @Autowired
    private EntretienDao entretienDao;

    @Autowired
    private EntretienProjectionDao entretienProjectionDao;

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

    @Alors("on récupères les entretiens suivants en base")
    public void onRécupèresLesEntretiensSuivantsEnBase(DataTable dataTable) {
        List<IEntretien> entretiens = dataTableTransformEntries(dataTable, this::buildIEntretien);

        List<IEntretien> savedEntretiens = entretienDao.findAll();

        assertThat(savedEntretiens)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("candidat.id",
                        "candidat.experienceInYears",
                        "candidat.profil",
                        "recruteur.id",
                        "recruteur.experienceInYears",
                        "recruteur.profil")
                .containsExactlyInAnyOrder(entretiens.toArray(IEntretien[]::new));
    }

    private IEntretien buildIEntretien(Map<String, String> entry) {
        return new IEntretienImpl(
                Integer.parseInt(entry.get("id")),
                entry.get("candidat"),
                entry.get("recruteur"),
                entry.get("language"),
                LocalDateTime.parse(entry.get("horaire"), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                Status.valueOf(entry.get("status")));
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

    @Quand("on récupère le nombre d'entretiens annulés")
    public void onRécupèreLeNombreDEntretiensAnnulés() {
        initPath();
        //@formatter:off
        response = given()
                .contentType(ContentType.JSON)
                .when()
                .get("/annule");
        //@formatter:on
    }

    @Et("le nombre d'entretiens annulé est égal à {int}")
    public void leNombreDEntretiensAnnuléEstEgalÀ(int count) {
        Integer result = response.then().extract().as(Integer.class);
        assertThat(result).isEqualTo(count);

        int entretiensAnnules = entretienProjectionDao.entretiensAnnules();
        assertThat(entretiensAnnules).isEqualTo(count);
    }
}
