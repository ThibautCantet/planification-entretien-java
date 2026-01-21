package com.soat.planification_entretien.entretien.domain.aggregate;

public record EntretienId(String value) {
    public EntretienId(Integer id) {
        this(id == null ? null : id.toString());
    }
}
