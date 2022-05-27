package com.soat.planification_entretien.domain.rendez_vous;

import java.time.LocalDateTime;

public record RendezVous(String emailCandidat, LocalDateTime horaire) {
}
