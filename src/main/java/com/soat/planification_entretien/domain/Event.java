package com.soat.planification_entretien.domain;

public interface Event<T> {
    String name();

    T value();
}
