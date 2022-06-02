package com.soat.planification_entretien.domain.recruteur;

import com.soat.planification_entretien.cqrs.Command;

public record CreerRecruteurCommand(String language, String email, String experienceEnAnnees) implements Command {
}
