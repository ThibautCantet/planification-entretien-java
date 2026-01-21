package com.soat.planification_entretien.entretien.domain;

import com.soat.planification_entretien.common.domain.Event;

public record EntretienPlanifie(int recruteurId) implements Event {}
