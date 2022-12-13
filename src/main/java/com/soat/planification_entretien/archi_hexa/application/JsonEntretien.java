package com.soat.planification_entretien.archi_hexa.application;

import java.time.LocalDateTime;

public record JsonEntretien(int candidatId, int recruteurId, LocalDateTime disponibiliteDuCandidat,
                            LocalDateTime disponibiliteDuRecruteur) {
}
