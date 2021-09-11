package com.soat.recruteur.event;

import java.util.UUID;

public record CreationRecruteurReussie(UUID recruteurId) implements ResultatCreationRecruteur {
}
