package com.soat.planification_entretien.entretien.query.application.controller;

import java.time.LocalDateTime;

import com.soat.planification_entretien.entretien.command.application.controller.Status;
import com.soat.planification_entretien.entretien.query.dto.IEntretien;

public class EntretienDetailDto implements IEntretien {
    private String id;
    private String emailCandidat;
    private String emailRecruteur;
    private String language;
    private LocalDateTime horaire;
    private String status;

    public EntretienDetailDto(String id, String emailCandidat, String emailRecruteur, String language, LocalDateTime horaire, String status) {
        this.id = id;
        this.emailCandidat = emailCandidat;
        this.emailRecruteur = emailRecruteur;
        this.language = language;
        this.horaire = horaire;
        this.status = status;
    }

    public EntretienDetailDto(
            String id,
            String emailCandidat,
            String emailRecruteur,
            String language,
            LocalDateTime horaire) {
        this(id, emailCandidat, emailRecruteur, language, horaire, Status.PLANIFIE.name());
    }

    public EntretienDetailDto() {
    }

    @Override
    public String getId() {
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
