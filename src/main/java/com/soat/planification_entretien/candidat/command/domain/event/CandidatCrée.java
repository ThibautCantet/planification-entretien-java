package com.soat.planification_entretien.candidat.command.domain.event;

import com.soat.planification_entretien.common.domain.Event;

public record CandidatCrée(Integer value) implements Event {
}
