package com.soat.planification_entretien.entretien.infrastructure.controller;

import java.time.LocalDateTime;

import com.soat.planification_entretien.entretien.query.application.IEntretien;

public record EntretienDetailDto(
        Integer id,
        String emailCandidat,
        String emailRecruteur,
        String language,
        LocalDateTime horaire,
        String status) implements IEntretien {

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public String getEmailCandidat() {
        return emailCandidat;
    }

    @Override
    public String getEmailRecruteur() {
        return emailRecruteur;
    }

    @Override
    public String getLanguage() {
        return language;
    }

    @Override
    public LocalDateTime getHoraire() {
        return horaire;
    }

    @Override
    public String getStatus() {
        return status;
    }
}
