package com.soat.recruteur.event;

import java.util.UUID;

public record CreationRecruteurEchouee(UUID recruteurId, String motif) implements ResultatCreationRecruteur {
}
