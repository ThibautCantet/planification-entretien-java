package com.soat.planification_entretien.candidat.command.domain;

import com.soat.planification_entretien.common.cqrs.event.Event;

public record CandidatCrée(Integer value) implements Event {
}
