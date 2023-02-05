package com.soat.planification_entretien.entretien.application_service;

import java.time.LocalDateTime;

import com.soat.planification_entretien.entretien.domain.Candidat;
import com.soat.planification_entretien.entretien.domain.Recruteur;

public record PlanifierEntretienCommand(Candidat candidat, Recruteur recruteur,
                                        LocalDateTime dateEtHeureDisponibiliteDuCandidat,
                                        LocalDateTime dateEtHeureDisponibiliteDuRecruteur) {
}
