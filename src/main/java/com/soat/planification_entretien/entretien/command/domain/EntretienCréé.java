package com.soat.planification_entretien.entretien.command.domain;

import com.soat.planification_entretien.common.cqrs.event.Event;

public record EntretienCréé(Integer entretienId, Integer recruteurId) implements Event {
}
