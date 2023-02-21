package com.soat.planification_entretien;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.soat.ATest;
import com.soat.planification_entretien.candidat.command.domain.CandidatRepository;
import com.soat.planification_entretien.common.application_service.MessageBus;
import com.soat.planification_entretien.common.cqrs.command.CommandResponse;
import com.soat.planification_entretien.common.cqrs.event.Event;
import com.soat.planification_entretien.common.cqrs.middleware.command.CommandBus;
import com.soat.planification_entretien.common.cqrs.middleware.command.CommandBusFactory;
import com.soat.planification_entretien.entretien.command.domain.Candidat;
import com.soat.planification_entretien.entretien.command.domain.Entretien;
import com.soat.planification_entretien.entretien.command.domain.EntretienRepository;
import com.soat.planification_entretien.entretien.command.domain.Recruteur;
import com.soat.planification_entretien.entretien.command.domain.Status;
import com.soat.planification_entretien.entretien.command.infrastructure.controller.EntretienCommandController;
import com.soat.planification_entretien.entretien.query.infrastructure.controller.EntretienDetailDto;
import com.soat.planification_entretien.recruteur.command.CreerRecruteurCommand;
import com.soat.planification_entretien.recruteur.command.CreerRecruteurCommandHandler;
import com.soat.planification_entretien.recruteur.command.domain.RecruteurCrée;
import com.soat.planification_entretien.recruteur.command.domain.RecruteurRepository;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.fr.Alors;
import io.cucumber.java.fr.Et;
import io.cucumber.java.fr.Etantdonné;
import io.cucumber.java.fr.Quand;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static io.restassured.RestAssured.*;
import static org.assertj.core.api.Assertions.*;

@Transactional
public class ListingEntretienATest extends ATest {

    private List<Candidat> savedCandidats = new ArrayList<>();
    private List<Recruteur> savedRecruteurs = new ArrayList<>();
    @Autowired
    private CandidatRepository candidatRepository;
    @Autowired
    private EntretienRepository entretienRepository;
    @Autowired
    private RecruteurRepository recruteurRepository;
    @Autowired
    private CommandBusFactory commandBusFactory;

    @Before
    @Override
    public void setUp() {
        initIntegrationTest();
    }

    @Override
    protected void initPath() {
        RestAssured.basePath = EntretienCommandController.PATH;
    }

    @Etantdonné("les recruteurs existants")
    public void lesRecruteursExistants(DataTable dataTable) {
        List<Recruteur> recruteurs = dataTableTransformEntries(dataTable, this::buildRecruteur);

        for (Recruteur recruteur : recruteurs) {

            CommandBus commandBus = commandBusFactory.build();
            CommandResponse<Event> commandResponse = commandBus.dispatch(new CreerRecruteurCommand(
                    recruteur.getLanguage(),
                    recruteur.adresseEmail(),
                    String.valueOf(recruteur.getExperienceInYears())));
            Integer id = commandResponse
                    .findFirst(RecruteurCrée.class)
                    .map(RecruteurCrée.class::cast)
                    .map(RecruteurCrée::id)
                    .orElse(-1);
            recruteur = new Recruteur(id, recruteur.getLanguage(), recruteur.adresseEmail(), recruteur.getExperienceInYears());
            savedRecruteurs.add(recruteur);
        }
    }

    private Recruteur buildRecruteur(Map<String, String> entry) {
        return new Recruteur(
                null,
                entry.get("language"),
                entry.get("email"),
                Integer.parseInt(entry.get("xp")));
    }

    @Et("les candidats existants")
    public void lesCandidatsExistants(DataTable dataTable) {
        List<Candidat> candidats = dataTableTransformEntries(dataTable, this::buildCandidat);

        for (Candidat candidat : candidats) {
            var saved = candidatRepository.save(new com.soat.planification_entretien.candidat.command.domain.Candidat(
                    candidat.id(),
                    candidat.language(),
                    candidat.adresseEmail(),
                    candidat.experienceInYears()));
            candidat = new Candidat(saved.getId(), saved.getLanguage(), saved.getAdresseEmail(), saved.getExperienceInYears());
            savedCandidats.add(candidat);
        }
    }

    private Candidat buildCandidat(Map<String, String> entry) {
        return new Candidat(
                Integer.parseInt(entry.get("id")),
                entry.get("language"),
                entry.get("email"),
                Integer.parseInt(entry.get("xp")));
    }

    @Et("les entretiens existants")
    public void lesEntretiensExistants(DataTable dataTable) {
        List<Entretien> entretiens = dataTableTransformEntries(dataTable, this::buildEntretien);

        for (Entretien entretien : entretiens) {
            //entityManager.persist(entretien);
            entretienRepository.save(entretien);
        }
    }

    private Entretien buildEntretien(Map<String, String> entry) {
        return Entretien.of(
                savedCandidats.get(0),
                savedRecruteurs.get(0),
                LocalDateTime.parse(entry.get("horaire"), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                Status.valueOf(entry.get("status")));
    }

    @Quand("on liste les tous les entretiens")
    public void onListeLesTousLesEntretiens() {
        initPath();
        //@formatter:off
        response = given()
                        .contentType(ContentType.JSON)
                .when()
                        .get();
        //@formatter:on
    }

    @Alors("on récupères les entretiens suivants")
    public void onRécupèresLesEntretiensSuivants(DataTable dataTable) {
        List<EntretienDetailDto> entretiens = dataTableTransformEntries(dataTable, this::buildEntretienDetail);

        var detailDtos = response.then().extract()
                .as(EntretienDetailDto[].class);

        assertThat(Arrays.stream(detailDtos).toList())
                .containsExactlyInAnyOrder(entretiens.toArray(EntretienDetailDto[]::new));
    }

    private EntretienDetailDto buildEntretienDetail(Map<String, String> entry) {
        return new EntretienDetailDto(
                Integer.parseInt(entry.get("id")),
                entry.get("candidat"),
                entry.get("recruteur"),
                entry.get("language"),
                LocalDateTime.parse(entry.get("horaire"), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                entry.get("status"));
    }
}
