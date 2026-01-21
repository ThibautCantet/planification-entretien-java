package com.soat.planification_entretien.entretien.domain.aggregate;

public record RecruteurPlanifié(Integer id, String langage, String email, int experienceEnAnnees, boolean estDisponible) {

    public boolean estCompatibleAvec(Candidat candidat) {
        return new Profil(langage, experienceEnAnnees).estCompatibleAvec(candidat.profil());
    }

}
