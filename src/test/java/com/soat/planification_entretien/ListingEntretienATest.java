package com.soat.planification_entretien;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.soat.ATest;
import com.soat.planification_entretien.candidat.command.domain.entity.Candidat;
import com.soat.planification_entretien.candidat.command.repository.CandidatRepository;
import com.soat.planification_entretien.entretien.command.application.controller.EntretienCommandController;
import com.soat.planification_entretien.entretien.command.application.controller.Status;
import com.soat.planification_entretien.entretien.command.domain.entity.Entretien;
import com.soat.planification_entretien.entretien.command.domain.entity.RendezVous;
import com.soat.planification_entretien.entretien.command.domain.repository.EntretienRepository;
import com.soat.planification_entretien.entretien.query.application.controller.EntretienDetailDto;
import com.soat.planification_entretien.recruteur.command.domain.entity.Recruteur;
import com.soat.planification_entretien.recruteur.command.domain.repository.RecruteurRepository;
import com.soat.planification_entretien.rendez_vous.command.domain.entity.Calendrier;
import com.soat.planification_entretien.rendez_vous.command.repository.CalendrierRepository;
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
    private RecruteurRepository recruteurRepository;
    @Autowired
    private EntretienRepository entretienRepository;
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

    @Etantdonné("les recruteurs existants")
    public void lesRecruteursExistants(DataTable dataTable) {
        List<Recruteur> recruteurs = dataTableTransformEntries(dataTable, this::buildRecruteur);

        for (Recruteur recruteur : recruteurs) {
            //Recruteur saved = entityManager.persist(recruteur);
            recruteurRepository.save(recruteur);
            savedRecruteurs.add(recruteur);
        }
    }

    private Recruteur buildRecruteur(Map<String, String> entry) {
        return Recruteur.create(
                entry.get("id"),
                entry.get("language"),
                entry.get("email"),
                Integer.parseInt(entry.get("xp")));
    }

    @Et("les candidats existants")
    public void lesCandidatsExistants(DataTable dataTable) {
        List<Candidat> candidats = dataTableTransformEntries(dataTable, this::buildCandidat);

        for (Candidat candidat : candidats) {
            //Candidat saved = entityManager.persist(candidat);
            candidatRepository.save(candidat);
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

            if (entretien.getStatus().equals(Status.PLANIFIE.name()) || entretien.getStatus().equals(Status.CONFIRME.name())) {
                var rendezVous = new ArrayList<com.soat.planification_entretien.rendez_vous.command.domain.entity.RendezVous>();
                rendezVous.add(new com.soat.planification_entretien.rendez_vous.command.domain.entity.RendezVous(entretien.getEmailCandidat(), entretien.getHoraire()));
                Calendrier calendrier = new Calendrier(1, entretien.getEmailRecruteur(), rendezVous);
                calendrierRepository.save(calendrier);
            }
        }
    }

    private Entretien buildEntretien(Map<String, String> entry) {
        Candidat candidat = savedCandidats.get(0);
        Recruteur recruteur = savedRecruteurs.get(0);

        LocalDateTime horaire = LocalDateTime.parse(entry.get("horaire"), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        List<RendezVous> rendezVous = new ArrayList<>();
        rendezVous.add(new RendezVous(candidat.getEmail(), horaire));

        if (entry.get("status") == null) {
            return Entretien.of(
                    entry.get(("id")),
                    new com.soat.planification_entretien.entretien.command.domain.entity.Candidat(candidat.getId(), candidat.getLanguage(), candidat.getEmail(), candidat.getExperienceInYears()),
                    new com.soat.planification_entretien.entretien.command.domain.entity.Recruteur(recruteur.getId(), recruteur.getLanguage(), recruteur.getEmail(), recruteur.getExperienceInYears(), rendezVous),
                    horaire);
        }
        return Entretien.of(
                entry.get(("id")),
                new com.soat.planification_entretien.entretien.command.domain.entity.Candidat(candidat.getId(), candidat.getLanguage(), candidat.getEmail(), candidat.getExperienceInYears()),
                new com.soat.planification_entretien.entretien.command.domain.entity.Recruteur(recruteur.getId(), recruteur.getLanguage(), recruteur.getEmail(), recruteur.getExperienceInYears(), rendezVous),
                horaire,
                Enum.valueOf(Status.class, entry.get("status")));
    }

    @Quand("on liste les tous les entretiens")
    public void onListeLesTousLesEntretiens() {
        //@formatter:off
        response = given()
                        .contentType(ContentType.JSON)
                .when()
                        .get(EntretienCommandController.PATH);
        //@formatter:on

    }

    @Alors("on récupères les entretiens suivants")
    public void onRécupèresLesEntretiensSuivants(DataTable dataTable) {
        List<EntretienDetailDto> entretiens = dataTableTransformEntries(dataTable, this::buildEntretienDetail);

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
                LocalDateTime.parse(entry.get("horaire"), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
    }
}
