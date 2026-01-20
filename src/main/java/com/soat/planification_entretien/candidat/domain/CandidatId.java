package com.soat.planification_entretien.candidat.domain;

public record CandidatId(String value) {
    public CandidatId(Integer id) {
        this(id == null ? null : id.toString());
    }
}
