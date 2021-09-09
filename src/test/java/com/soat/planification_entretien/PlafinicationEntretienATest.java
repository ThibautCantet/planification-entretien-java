package com.soat.planification_entretien;

import io.cucumber.java.fr.*;

public class PlafinicationEntretienATest {

    @Etantdonné("un candidat {string} \\({string}) avec {string} ans d’expériences qui est disponible {string} à {string}")
    public void unCandidatAvecAnsDExpériencesQuiEstDisponibleÀ(String language, String email, String experienceInYears, String date, String time) {

    }

    @Etqu("un recruteur {string} \\({string}) qui a {string} ans d’XP qui est dispo {string}")
    public void unRecruteurQuiAAnsDXPQuiEstDispo(String language, String email, String experienceInYears, String date) {
    }

    @Quand("on tente une planification d’entretien")
    public void onTenteUnePlanificationDEntretien() {
    }

    @Alors("L’entretien est planifié")
    public void lEntretienEstPlanifié() {
    }

    @Et("un mail de confirmation est envoyé au candidat et le recruteur")
    public void unMailDeConfirmationEstEnvoyéAuCandidatEtLeRecruteur() {
    }
}
