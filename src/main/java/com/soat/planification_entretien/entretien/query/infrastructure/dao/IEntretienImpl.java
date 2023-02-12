package com.soat.planification_entretien.entretien.query.infrastructure.dao;

import java.time.LocalDateTime;

import com.soat.planification_entretien.entretien.command.domain.Status;
import com.soat.planification_entretien.entretien.query.application.IEntretien;

public record IEntretienImpl(Integer id, String emailCandidat, String emailRecruteur,
                             String language,
                             LocalDateTime horaireEntretien,
                             Status value) implements IEntretien {
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
        return horaireEntretien;
    }

    @Override
    public String getStatus() {
        return value.name();
    }
}
