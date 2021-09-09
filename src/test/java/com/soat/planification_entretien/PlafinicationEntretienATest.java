package com.soat.planification_entretien;

import com.soat.planification_entretien.domain.*;
import com.soat.planification_entretien.infrastructure.FakeEmailService;
import com.soat.planification_entretien.infrastructure.InMemoryEntretienRepository;
import com.soat.planification_entretien.use_case.PlanifierEntretien;
import io.cucumber.java.fr.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

public class PlafinicationEntretienATest {

    private Candidat candidat;
    private Disponibilite disponibiliteDuCandidat;
    private Recruteur recruteur;
    private LocalDate dateDeDisponibiliteDuRecruteur;
    private PlanifierEntretien planifierEntretien;
    private EntretienRepository entretienRepository = new InMemoryEntretienRepository();

    private EmailService emailService = new FakeEmailService();

    @Etantdonné("un candidat {string} \\({string}) avec {string} ans d’expériences qui est disponible {string} à {string}")
    public void unCandidatAvecAnsDExpériencesQuiEstDisponibleÀ(String language, String email, String experienceInYears, String date, String time) {
        candidat = new Candidat(language, email, Integer.parseInt(experienceInYears));
        disponibiliteDuCandidat = new Disponibilite(LocalDateTime.of(LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy")), LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"))));
    }

    @Etqu("un recruteur {string} \\({string}) qui a {string} ans d’XP qui est dispo {string}")
    public void unRecruteurQuiAAnsDXPQuiEstDispo(String language, String email, String experienceInYears, String date) {
        recruteur = new Recruteur(language, email, Integer.parseInt(experienceInYears));
        dateDeDisponibiliteDuRecruteur = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    @Quand("on tente une planification d’entretien")
    public void onTenteUnePlanificationDEntretien() {
        planifierEntretien = new PlanifierEntretien(entretienRepository, emailService);
        planifierEntretien.execute(candidat, disponibiliteDuCandidat, recruteur, dateDeDisponibiliteDuRecruteur);
    }

    @Alors("L’entretien est planifié")
    public void lEntretienEstPlanifié() {
        Entretien entretien = entretienRepository.findByCandidat(candidat);
        HoraireEntretien horaire = new HoraireEntretien(disponibiliteDuCandidat.horaire());
        Entretien expectedEntretien = new Entretien(candidat, recruteur, horaire);
        assertThat(entretien).isEqualTo(expectedEntretien);
    }

    @Et("un mail de confirmation est envoyé au candidat et au recruteur")
    public void unMailDeConfirmationEstEnvoyéAuCandidatEtAuRecruteur() {
        HoraireEntretien horaire = new HoraireEntretien(disponibiliteDuCandidat.horaire());
        assertThat(((FakeEmailService) emailService).unEmailDeConfirmationAEteEnvoyerAuCandidat(candidat.email(), horaire));
        assertThat(((FakeEmailService) emailService).unEmailDeConfirmationAEteEnvoyerAuRecruteur(recruteur.email(), horaire));
    }
}
