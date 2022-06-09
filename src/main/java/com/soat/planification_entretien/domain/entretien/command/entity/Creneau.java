package com.soat.planification_entretien.domain.entretien.command.entity;

import java.time.LocalDateTime;

record Creneau(LocalDateTime debut, Integer duree) {

    private static final int DUREE = 1;

    Creneau(LocalDateTime debut) {
        this(debut, DUREE);
    }
}
