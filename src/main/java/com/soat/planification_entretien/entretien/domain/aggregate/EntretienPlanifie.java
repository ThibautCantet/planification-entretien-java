package com.soat.planification_entretien.entretien.domain.aggregate;

import com.soat.planification_entretien.common.domain.Event;

public record EntretienPlanifie(int recruteurId) implements Event {}
