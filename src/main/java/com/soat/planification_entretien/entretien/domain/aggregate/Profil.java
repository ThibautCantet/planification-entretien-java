package com.soat.planification_entretien.entretien.domain.aggregate;

record Profil(String langage, int experienceEnAnnees) {

    boolean estCompatibleAvec(Profil profilCandidat) {
        return this.langage.equalsIgnoreCase(profilCandidat.langage)
                && this.experienceEnAnnees > profilCandidat.experienceEnAnnees;
    }
}
