package com.soat.recruteur.infrastructure.controller;

import java.util.UUID;

import com.soat.recruteur.domain.RecruteurDetail;

public record RecruteurDetailDto(UUID id, String email, String competence) implements RecruteurDetail {
}
