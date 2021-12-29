package com.soat.planification_entretien;

import com.soat.planification_entretien.model.*;
import com.soat.planification_entretien.repository.FakeEmailService;
import com.soat.planification_entretien.repository.InMemoryEntretienRepository;
import com.soat.planification_entretien.use_case.EntretienService;
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
    private EntretienService entretienService;
    private EntretienRepository entretienRepository = new InMemoryEntretienRepository();

    private EmailService emailService = new FakeEmailService();
    private ResultatPlanificationEntretien resultatPlanificationEntretien;

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
        entretienService = new EntretienService(entretienRepository, emailService);
        resultatPlanificationEntretien = entretienService.planifier(candidat, disponibiliteDuCandidat, recruteur, dateDeDisponibiliteDuRecruteur);
    }

    @Alors("L’entretien est planifié")
    public void lEntretienEstPlanifié() {
        assertThat(resultatPlanificationEntretien).isEqualTo(new EntretienPlanifie(candidat, recruteur, new HoraireEntretien(disponibiliteDuCandidat.horaire())));

        Entretien entretien = entretienRepository.findByCandidat(candidat);
        HoraireEntretien horaire = new HoraireEntretien(disponibiliteDuCandidat.horaire());
        Entretien expectedEntretien = Entretien.of(candidat, recruteur, horaire);
        assertThat(entretien).isEqualToComparingFieldByField(expectedEntretien);
    }

    @Et("un mail de confirmation est envoyé au candidat et au recruteur")
    public void unMailDeConfirmationEstEnvoyéAuCandidatEtAuRecruteur() {
        HoraireEntretien horaire = new HoraireEntretien(disponibiliteDuCandidat.horaire());
        assertThat(((FakeEmailService) emailService).unEmailDeConfirmationAEteEnvoyerAuCandidat(candidat.email(), horaire));
        assertThat(((FakeEmailService) emailService).unEmailDeConfirmationAEteEnvoyerAuRecruteur(recruteur.email(), horaire));
    }

    @Alors("L’entretien n'est pas planifié")
    public void lEntretienNEstPasPlanifié() {
        assertThat(resultatPlanificationEntretien).isEqualTo(new EntretienEchouee(candidat, recruteur, new HoraireEntretien(disponibiliteDuCandidat.horaire())));

        Entretien entretien = entretienRepository.findByCandidat(candidat);
        assertThat(entretien).isNull();
    }

    @Et("aucun mail de confirmation n'est envoyé au candidat ou au recruteur")
    public void aucunMailDeConfirmationNEstEnvoyéAuCandidatOuAuRecruteur() {
        HoraireEntretien horaire = new HoraireEntretien(disponibiliteDuCandidat.horaire());
        assertThat(((FakeEmailService) emailService).unEmailDeConfirmationAEteEnvoyerAuCandidat(candidat.email(), horaire)).isFalse();
        assertThat(((FakeEmailService) emailService).unEmailDeConfirmationAEteEnvoyerAuRecruteur(recruteur.email(), horaire)).isFalse();
    }
}
