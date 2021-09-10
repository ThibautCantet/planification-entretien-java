package com.soat.candidat.domain;

import java.util.UUID;

public interface CandidatRepository {
    UUID next();

    void save(Candidat candidat);
}
