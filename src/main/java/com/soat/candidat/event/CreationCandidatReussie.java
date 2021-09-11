package com.soat.candidat.event;

import java.util.UUID;

public record CreationCandidatReussie(UUID candidatId) implements ResultatCreationCandidat {
}
