package com.soat.planification_entretien.entretien.domain.aggregate;

public record Candidat(Integer id, String langage, String email, int experienceEnAnnees) {

    public Profil profil() {
        return new Profil(langage, experienceEnAnnees);
    }
}
