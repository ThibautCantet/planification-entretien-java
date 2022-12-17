package com.soat.planification_entretien.domain.entretien;

import com.soat.planification_entretien.domain.Event;

public record EntretienCréé<Integer>(String name, Integer value) implements Event {
    public EntretienCréé(Integer value) {
        this("Entretien créé", value);
    }
}
