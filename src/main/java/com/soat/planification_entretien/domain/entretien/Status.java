package com.soat.planification_entretien.domain.entretien;

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