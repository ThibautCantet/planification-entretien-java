package com.soat.planification_entretien.entretien.query.dto;

import java.time.LocalDateTime;

import com.soat.planification_entretien.entretien.command.application.controller.Status;

public interface IEntretien {
    Integer getId();

    String getEmailCandidat();

    String getEmailRecruteur();

    String getLanguage();

    LocalDateTime getHoraire();

    String getStatus();
}
