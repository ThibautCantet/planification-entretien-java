package com.soat.recruteur.domain;

import java.util.UUID;

public interface RecruteurRepository {
    UUID next();

    void save(Recruteur recruteur);
}
