package com.soat.planification_entretien.domain.rendez_vous.query;

import com.soat.planification_entretien.cqrs.Query;

public record ListerRendezVousRecruteurQuery(String email) implements Query {
}
