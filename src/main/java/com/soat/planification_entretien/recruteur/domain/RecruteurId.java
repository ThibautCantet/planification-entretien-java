package com.soat.planification_entretien.recruteur.domain;

public record RecruteurId(String value) {
    public RecruteurId(Integer id) {
        this(id == null ? null : id.toString());
    }
}
