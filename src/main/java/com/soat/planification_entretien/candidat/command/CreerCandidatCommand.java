package com.soat.planification_entretien.candidat.command;

import com.soat.planification_entretien.common.cqrs.command.Command;

public record CreerCandidatCommand(String language, String email, String experienceEnAnnees) implements Command {
}
