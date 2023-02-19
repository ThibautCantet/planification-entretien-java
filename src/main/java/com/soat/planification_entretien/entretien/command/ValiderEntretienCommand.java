package com.soat.planification_entretien.entretien.command;

import com.soat.planification_entretien.common.cqrs.command.Command;

public record ValiderEntretienCommand(int entretienId) implements Command {
}
