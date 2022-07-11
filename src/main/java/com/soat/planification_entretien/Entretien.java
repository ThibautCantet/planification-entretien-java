package com.soat.planification_entretien;

public record Entretien(Recruteur recruteur, Candidat candidat) {

    public boolean planifier() {
        if (candidat.xp() < recruteur.xp() &&
                candidat.techno().equals(recruteur.techno())
                && candidat.disponibilite().equals(recruteur.disponibilite())) {
            return true;
        }
        return false;
    }

    public String emailCandidat() {
        return candidat.email();
    }

    public String emailRH() {
        return recruteur.email();
    }

    public String horaire() {
        return candidat.disponibilite();
    }
}
