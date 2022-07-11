package com.soat.planification_entretien;

import java.util.List;

import io.cucumber.java.fr.*;

import static org.assertj.core.api.Assertions.*;

public class PlafinicationEntretienATest {

    private Candidat candidat;
    private Recruteur recruteur;
    private EntretienRepository entretienRepository = new EntretienRepository();
    private EmailService emailService = new FakeEmailService();
    private Boolean resultatPlanification;

    @Etantdonné("un candidat {string} avec une expérience de {int} ans d XP en {string} avec une disponibilité à {string}")
    public void unCandidatAvecUneExpérienceDeAnsDXPEnAvecUneDisponibilitéÀ(String email, int xp, String techno, String disponibilite) {
        candidat = new Candidat(email, xp, techno, disponibilite);
    }

    @Et("un RH {string} avec {int} ans d XP sur {string} et une disponibilité à {string}")
    public void unRHAvecAnsDXPSurEtUneDisponibilitéÀ(String email, int xp, String techno, String disponibilite) {
        recruteur = new Recruteur(email, xp, techno, disponibilite);
    }

    @Quand("on planifie l entretien")
    public void onPlanifieLEntretien() {
        EntretienService entretienService = new EntretienService(entretienRepository, emailService);
        resultatPlanification = entretienService.planifier(candidat, recruteur);
    }

    @Alors("un entretien est enregistré dans l'application")
    public void unEntretienEstEnregistréDansLApplication() {
        List<Entretien> entretiens = entretienRepository.findAll();

        assertThat(entretiens).hasSize(1);
        assertThat(entretiens.get(0)).extracting(Entretien::emailCandidat, Entretien::emailRH, Entretien::horaire)
                .containsExactly(candidat.email(), recruteur.email(), candidat.disponibilite());
    }

    @Et("un email est envoyé au candidat {string}")
    public void unEmailEstEnvoyéAuCandidat(String emailCandidat) {
        //verify(emailService).sendEmailToCandidat(emailCandidat);
        assertThat(((FakeEmailService) emailService).isSentToCandidat(candidat.email())).isTrue();
    }

    @Et("un email est envoyé au RH {string}")
    public void unEmailEstEnvoyéAuRH(String emailRH) {
        //verify(emailService).sendEmailToRH(emailRH);
        assertThat(((FakeEmailService) emailService).isSentToRH(recruteur.email())).isTrue();
    }

    @Alors("un entretien n est pas enregistré dans l'application")
    public void unEntretienNEstPasEnregistréDansLApplication() {
        List<Entretien> entretiens = entretienRepository.findAll();

        assertThat(entretiens).isEmpty();
    }

    @Et("une erreur est retournée")
    public void uneErreurEstRetournée() {
        assertThat(resultatPlanification).isFalse();
    }

    @Et("un email aucun est envoyé au candidat")
    public void unEmailAucunEstEnvoyéAuCandidat() {
        //verify(emailService, never()).sendEmailToCandidat(anyString());
        assertThat(((FakeEmailService) emailService).isSentToCandidat(candidat.email())).isFalse();
    }

    @Et("un email aucun est envoyé au RH")
    public void unEmailAucunEstEnvoyéAuRH() {
        //verify(emailService, never()).sendEmailToRH(anyString());
        assertThat(((FakeEmailService) emailService).isSentToRH(recruteur.email())).isFalse();
    }
}
