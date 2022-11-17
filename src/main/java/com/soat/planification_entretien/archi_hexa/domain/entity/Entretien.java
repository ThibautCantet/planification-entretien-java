package com.soat.planification_entretien.archi_hexa.domain.entity;

import java.time.LocalDateTime;

public record Entretien(Candidat candidat, Recruteur recruteur, LocalDateTime dateEtHeureDisponibiliteDuRecruteur) {
}
