package com.soat.planification_entretien.domain.recruteur.command.entity;

record AnneesExperience(int nombre) {
    static AnneesExperience create(Integer experienceEnAnnees) {
        if (experienceEnAnnees == null || experienceEnAnnees < 0) {
            return null;
        }
        return new AnneesExperience(experienceEnAnnees);
    }
}
