package com.soat.planification_entretien.entretien.command;

import com.soat.planification_entretien.common.cqrs.command.Command;

public record AnnulerEntretienCommand(int entretienId) implements Command {
}
