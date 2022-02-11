package com.soat.planification_entretien.model;

public record Recruteur(String language, String email, int xp) {
    public boolean peutEvaluer(Candidat candidat) {
        return isSameLanguage(candidat) && isRecruteurMoreExperienced(candidat);
    }

    private boolean isSameLanguage(Candidat candidat) {
        return candidat.language().equals(this.language());
    }

    private boolean isRecruteurMoreExperienced(Candidat candidat) {
        return this.xp() > candidat.xp();
    }
}
