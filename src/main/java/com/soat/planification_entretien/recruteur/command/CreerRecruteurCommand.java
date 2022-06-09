package com.soat.planification_entretien.recruteur.command;

import com.soat.planification_entretien.cqrs.Command;

public record CreerRecruteurCommand(String language, String email, Integer experienceEnAnnees) implements Command {
}
