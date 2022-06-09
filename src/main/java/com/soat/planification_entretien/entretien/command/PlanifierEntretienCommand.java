package com.soat.planification_entretien.entretien.command;

import java.time.LocalDateTime;

import com.soat.planification_entretien.cqrs.Command;
import com.soat.planification_entretien.entretien.command.domain.entity.Candidat;
import com.soat.planification_entretien.entretien.command.domain.entity.Recruteur;

public record PlanifierEntretienCommand(Candidat candidat, Recruteur recruteur,
                                        LocalDateTime dateEtHeureDisponibiliteDuCandidat
) implements Command {
}
