package com.soat.planification_entretien.entretien.command.application.controller;

import java.util.Arrays;

public enum Status {
    PLANIFIE(0),
    CONFIRME(1),
    ANNULE(2);

    private int value;

    Status(int value) {
        this.value = value;
    }

    public static Status of(int status) {
        return Arrays.stream(values())
                .filter(v -> v.getValue() == status)
                .findFirst()
                .orElse(null);
    }

    public int getValue() {
        return value;
    }
}
