package com.soat.planification_entretien.application.use_case.input_port;

import java.time.LocalDateTime;

public record EntretienDetailImpl(int id, String emailCandidat, String emailRecruteur, String language,
                                  LocalDateTime horaire) implements EntretienDetail {
}
