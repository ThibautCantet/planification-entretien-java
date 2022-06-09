package com.soat.planification_entretien.rendez_vous.query;

import com.soat.planification_entretien.cqrs.Query;

public record ListerRendezVousRecruteurQuery(String email) implements Query {
}
