package com.soat.planification_entretien.recruteur.command.domain;

import com.soat.planification_entretien.common.cqrs.event.Event;

public record RecruteurCrée(Integer id, String language, Integer experienceInYears, String email) implements Event {
}
