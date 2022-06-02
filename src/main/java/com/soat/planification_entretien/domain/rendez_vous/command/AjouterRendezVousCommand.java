package com.soat.planification_entretien.domain.rendez_vous.command;

import java.time.LocalDateTime;

import com.soat.planification_entretien.cqrs.Command;

public record AjouterRendezVousCommand(String emailRecruteur, String emailCandidat,
                                       LocalDateTime horaire) implements Command {
}
