package com.soat.planification_entretien.entretien.command;

import java.time.LocalDateTime;

import com.soat.planification_entretien.cqrs.Command;
import com.soat.planification_entretien.entretien.command.domain.entity.Candidat;
import com.soat.planification_entretien.entretien.command.domain.entity.Recruteur;

public record PlanifierEntretienAutomatiqueCommand(Candidat candidat,
                                                   LocalDateTime dateEtHeureDisponibiliteDuCandidat) implements Command {
}
