package com.soat.planification_entretien.infrastructure.controller;

import com.soat.planification_entretien.domain.RecruteurDetail;

public record RecruteurDetailDto(Integer id, String email, String competence) implements RecruteurDetail {
}
