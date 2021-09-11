package com.soat.planification_entretien.domain;

public record Recruteur(String language, String email, Integer experienceEnAnnees) {
    public boolean peutEvaluer(Candidat candidat) {
        return aPlusDExperienceQueLe(candidat) && aLaMemeExpertiseQueLe(candidat);
    }

    private boolean aPlusDExperienceQueLe(Candidat candidat) {
        return experienceEnAnnees >= candidat.experienceEnAnnees();
    }

    private boolean aLaMemeExpertiseQueLe(Candidat candidat) {
        return language.equals(candidat.language());
    }
}
