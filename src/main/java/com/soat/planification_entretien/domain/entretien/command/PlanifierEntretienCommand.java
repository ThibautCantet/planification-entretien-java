package com.soat.planification_entretien.domain.entretien.command;

import java.time.LocalDateTime;

import com.soat.planification_entretien.cqrs.Command;
import com.soat.planification_entretien.domain.entretien.command.entity.Candidat;
import com.soat.planification_entretien.domain.entretien.command.entity.Recruteur;

public record PlanifierEntretienCommand(Candidat candidat, Recruteur recruteur,
                                        LocalDateTime dateEtHeureDisponibiliteDuCandidat,
                                        LocalDateTime dateEtHeureDisponibiliteDuRecruteur) implements Command {
}
