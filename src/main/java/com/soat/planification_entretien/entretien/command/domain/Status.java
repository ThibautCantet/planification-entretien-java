package com.soat.planification_entretien.entretien.command.domain;

public enum Status {
    PLANIFIE(0),
    VALIDE(1);

    private int value;

    Status(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
