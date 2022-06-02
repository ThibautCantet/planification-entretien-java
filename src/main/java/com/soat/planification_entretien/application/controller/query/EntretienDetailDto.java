package com.soat.planification_entretien.application.controller.query;

import java.time.LocalDateTime;

import com.soat.planification_entretien.domain.entretien.query.dto.IEntretien;

public class EntretienDetailDto implements IEntretien {
    private final Integer id;
    private final String emailCandidat;
    private final String emailRecruteur;
    private final String language;
    private final LocalDateTime horaire;

    public EntretienDetailDto(
            int id,
            String emailCandidat,
            String emailRecruteur,
            String language,
            LocalDateTime horaire) {
        this.id = id;
        this.emailCandidat = emailCandidat;
        this.emailRecruteur = emailRecruteur;
        this.language = language;
        this.horaire = horaire;
    }

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
}
