package com.soat.planification_entretien.entretien.domain;


public record ConsultantRecruteur(int id, String language, String email, int experienceInYears, boolean disponible) {

    public boolean estCompatible(Candidat prospect) {
        return this.profil().estCompatibe(prospect.profil());
    }

    private Profil profil() {
        return new Profil(experienceInYears, language);
    }
}
