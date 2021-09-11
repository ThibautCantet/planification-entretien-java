package com.soat.candidat.event;

import java.util.UUID;

public record CreationCandidatEchouee(UUID candidatId, String motif) implements ResultatCreationCandidat {
}
