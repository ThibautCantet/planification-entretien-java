package com.soat.planification_entretien;

import com.soat.candidat.domain.CandidatRepository;
import com.soat.planification_entretien.domain.*;
import com.soat.planification_entretien.event.EntretienEchouee;
import com.soat.planification_entretien.event.EntretienPlanifie;
import com.soat.planification_entretien.event.ResultatPlanificationEntretien;
import com.soat.planification_entretien.infrastructure.FakeEmailService;
import com.soat.candidat.infrastructure.InMemoryCandidatRepository;
import com.soat.planification_entretien.infrastructure.InMemoryEntretienRepository;
import com.soat.recruteur.infrastructure.InMemoryRecruteurRepository;
import com.soat.candidat.use_case.CreerCandidat;
import com.soat.planification_entretien.use_case.PlanifierEntretien;
import com.soat.planification_entretien.use_case.PlanifierEntretienCommand;
import com.soat.recruteur.domain.RecruteurRepository;
import com.soat.recruteur.use_case.CreerRecruteur;
import io.cucumber.java.fr.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class PlafinicationEntretienATest {

    private LocalDateTime disponibiliteDuCandidat;
    private LocalDate dateDeDisponibiliteDuRecruteur;

    private final UUID entretienId = UUID.fromString("e7c87dad-380f-4552-98f9-2b52f54e9284");
    private final EntretienRepository entretienRepository = new InMemoryEntretienRepository(entretienId);

    private final UUID candidatId = UUID.fromString("96af3270-91fa-4915-8984-d1001f13fc6b");
    private final CandidatRepository candidatRepository = new InMemoryCandidatRepository(candidatId);

    private final UUID recruteurId = UUID.fromString("f9505d05-df67-43b5-9ac2-3e958a2a23da");
    private final RecruteurRepository recruteurRepository = new InMemoryRecruteurRepository(recruteurId);

    private final EmailService emailService = new FakeEmailService();
    private ResultatPlanificationEntretien resultatPlanificationEntretien;
    private final com.soat.planification_entretien.domain.CandidatRepository candidatRepository2 = new com.soat.planification_entretien.infrastructure.InMemoryCandidatRepository((InMemoryCandidatRepository) candidatRepository);
    private final com.soat.planification_entretien.domain.RecruteurRepository recruteurRepository2 = new com.soat.planification_entretien.infrastructure.InMemoryRecruteurRepository((InMemoryRecruteurRepository) recruteurRepository);

    @Etantdonné("un candidat {string} \\({string}) avec {string} ans d’expériences qui est disponible {string} à {string}")
    public void unCandidatAvecAnsDExpériencesQuiEstDisponibleÀ(String language, String email, String experienceEnAnnees, String date, String time) {
        final CreerCandidat creerCandidat = new CreerCandidat(candidatRepository);
        creerCandidat.execute(language, email, Integer.parseInt(experienceEnAnnees));
        disponibiliteDuCandidat = LocalDateTime.of(LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy")), LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm")));
    }

    @Etqu("un recruteur {string} \\({string}) qui a {string} ans d’XP qui est dispo {string}")
    public void unRecruteurQuiAAnsDXPQuiEstDispo(String language, String email, String experienceEnAnnees, String date) {
        final CreerRecruteur creerRecruteur = new CreerRecruteur(recruteurRepository);
        creerRecruteur.execute(language, email, Integer.parseInt(experienceEnAnnees));
        dateDeDisponibiliteDuRecruteur = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    @Quand("on tente une planification d’entretien")
    public void onTenteUnePlanificationDEntretien() {
        PlanifierEntretien planifierEntretien = new PlanifierEntretien(entretienRepository,
                emailService,
                candidatRepository2,
                recruteurRepository2);
        final PlanifierEntretienCommand planifierEntretienCommand = new PlanifierEntretienCommand(candidatId, disponibiliteDuCandidat, recruteurId, dateDeDisponibiliteDuRecruteur);
        resultatPlanificationEntretien = planifierEntretien.execute(planifierEntretienCommand);
    }

    @Alors("L’entretien est planifié")
    public void lEntretienEstPlanifié() {
        assertThat(resultatPlanificationEntretien).isEqualToComparingFieldByField(new EntretienPlanifie(entretienId, candidatId, recruteurId, disponibiliteDuCandidat));

        Entretien entretien = entretienRepository.findById(entretienId);
        Entretien expectedEntretien = Entretien.of(entretienId, candidatId, recruteurId, disponibiliteDuCandidat);
        assertThat(entretien).isEqualToComparingFieldByField(expectedEntretien);
    }

    @Et("un mail de confirmation est envoyé au candidat et au recruteur")
    public void unMailDeConfirmationEstEnvoyéAuCandidatEtAuRecruteur() {
        assertThat(((FakeEmailService) emailService).unEmailDeConfirmationAEteEnvoyerAuCandidat()).isTrue();
        assertThat(((FakeEmailService) emailService).unEmailDeConfirmationAEteEnvoyerAuRecruteur()).isTrue();
    }

    @Alors("L’entretien n'est pas planifié")
    public void lEntretienNEstPasPlanifié() {
        assertThat(resultatPlanificationEntretien).isEqualTo(new EntretienEchouee(resultatPlanificationEntretien.id(), candidatId, recruteurId, disponibiliteDuCandidat));

        Entretien entretien = entretienRepository.findById(resultatPlanificationEntretien.id());
        assertThat(entretien).isNull();
    }

    @Et("aucun mail de confirmation n'est envoyé au candidat ou au recruteur")
    public void aucunMailDeConfirmationNEstEnvoyéAuCandidatOuAuRecruteur() {
        assertThat(((FakeEmailService) emailService).unEmailDeConfirmationAEteEnvoyerAuCandidat()).isFalse();
        assertThat(((FakeEmailService) emailService).unEmailDeConfirmationAEteEnvoyerAuRecruteur()).isFalse();
    }
}
