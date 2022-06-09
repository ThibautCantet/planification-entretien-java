package com.soat.planification_entretien.entretien.command;

import com.soat.planification_entretien.cqrs.Command;
import com.soat.planification_entretien.entretien.command.application.controller.Status;

public record MettreAJourStatusEntretienCommand(String id, Status status) implements Command {
}
