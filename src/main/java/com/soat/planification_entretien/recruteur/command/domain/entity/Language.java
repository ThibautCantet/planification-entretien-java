package com.soat.planification_entretien.recruteur.command.domain.entity;

record Language(String nom) {
    static Language create(String nom) {
        if (nom.isBlank()) {
            return null;
        }
        return new Language(nom);
    }
}
