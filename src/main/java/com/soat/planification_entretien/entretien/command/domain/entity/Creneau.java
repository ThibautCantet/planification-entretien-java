package com.soat.planification_entretien.entretien.command.domain.entity;

import java.time.LocalDateTime;

record Creneau(LocalDateTime debut, Integer duree) {

    private static final int DUREE = 1;

    Creneau(LocalDateTime debut) {
        this(debut, DUREE);
    }

    LocalDateTime fin() {
        return debut.plusHours(DUREE);
    }

}
