package com.soat.planification_entretien.candidat.event;

import com.soat.planification_entretien.cqrs.Event;

public record CandidatCree(java.util.UUID id) implements Event {
}
