package com.soat.planification_entretien.entretien.command;

import java.time.LocalDateTime;

import com.soat.planification_entretien.entretien.command.domain.Candidat;
import com.soat.planification_entretien.entretien.command.domain.Recruteur;

public record PlanifierEntretienCommand(Candidat candidat, Recruteur recruteur,
                                        LocalDateTime dateEtHeureDisponibiliteDuCandidat,
                                        LocalDateTime dateEtHeureDisponibiliteDuRecruteur) {
}
