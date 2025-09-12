package com.soat.planification_entretien.domain.planification;

import com.soat.planification_entretien.common.domain.Event;

public record EntretienPlanifié(int recruteurId) implements Event {
}
