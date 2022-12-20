package com.soat.planification_entretien.domain.entretien;

public enum Status {
    PLANIFIE(0);

    private int value;

    Status(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
