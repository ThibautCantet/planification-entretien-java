package com.soat.planification_entretien.entretien.command.domain;

import com.soat.planification_entretien.common.cqrs.event.Event;

public record EntretienAnnulé(Integer id) implements Event {
}
