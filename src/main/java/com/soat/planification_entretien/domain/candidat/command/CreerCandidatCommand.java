package com.soat.planification_entretien.domain.candidat.command;

import com.soat.planification_entretien.cqrs.Command;
import com.soat.planification_entretien.cqrs.Event;

public record CreerCandidatCommand(String language, String email, String experienceEnAnnees) implements Event, Command {
}
