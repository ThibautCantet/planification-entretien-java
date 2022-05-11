package com.soat.recruteur.infrastructure.controller;

import com.soat.recruteur.domain.RecruteurDetail;

public record RecruteurDetailDto(Integer id, String email, String competence) implements RecruteurDetail {
}
