package com.soat.planification_entretien.domain.entretien;

import com.soat.planification_entretien.infrastructure.middleware.Event;

public record EntretienPanifie(int id) implements Event {
}

