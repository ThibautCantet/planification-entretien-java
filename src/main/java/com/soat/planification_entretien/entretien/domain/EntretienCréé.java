package com.soat.planification_entretien.entretien.domain;

import com.soat.planification_entretien.common.domain.Event;

public record EntretienCréé(Integer entretienId, Integer recruteurId) implements Event {
}
