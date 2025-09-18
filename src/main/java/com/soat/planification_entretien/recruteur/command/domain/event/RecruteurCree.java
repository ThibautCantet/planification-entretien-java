package com.soat.planification_entretien.recruteur.command.domain.event;

import com.soat.planification_entretien.common.domain.Event;

public record RecruteurCree(Integer id, String language, Integer experiencesInYears, String email) implements Event {
}
