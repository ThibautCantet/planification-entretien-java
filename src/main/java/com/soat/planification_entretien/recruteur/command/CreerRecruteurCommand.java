package com.soat.planification_entretien.recruteur.command;

import com.soat.planification_entretien.common.cqrs.command.Command;

public record CreerRecruteurCommand(String language, String email, String experienceEnAnnees) implements Command {
}
