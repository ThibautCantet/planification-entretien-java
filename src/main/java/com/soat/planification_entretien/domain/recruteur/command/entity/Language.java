package com.soat.planification_entretien.domain.recruteur.command.entity;

record Language(String nom) {
    static Language create(String nom) {
        if (nom.isBlank()) {
            return null;
        }
        return new Language(nom);
    }
}
