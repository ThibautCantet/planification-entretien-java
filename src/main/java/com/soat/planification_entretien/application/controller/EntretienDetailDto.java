package com.soat.planification_entretien.application.controller;

import java.time.LocalDateTime;
import java.util.Objects;

import com.soat.planification_entretien.domain.entretien.IEntretien;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EntretienDetailDto that = (EntretienDetailDto) o;
        return Objects.equals(id, that.id) && Objects.equals(emailCandidat, that.emailCandidat) && Objects.equals(emailRecruteur, that.emailRecruteur) && Objects.equals(language, that.language) && Objects.equals(horaire, that.horaire);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, emailCandidat, emailRecruteur, language, horaire);
    }
}
