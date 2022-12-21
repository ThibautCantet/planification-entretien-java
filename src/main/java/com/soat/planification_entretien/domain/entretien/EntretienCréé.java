package com.soat.planification_entretien.domain.entretien;

import com.soat.planification_entretien.domain.Event;

public record EntretienCréé(Integer entretienId, Integer recruteurId) implements Event {
}
