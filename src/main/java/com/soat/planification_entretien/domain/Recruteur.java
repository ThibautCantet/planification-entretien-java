package com.soat.planification_entretien.domain;

public record Recruteur(String email, Integer experienceEnAnnees) {
    public boolean peutEvaluer(Candidat candidat) {
        return experienceEnAnnees >= candidat.experienceEnAnnees();
    }
}
