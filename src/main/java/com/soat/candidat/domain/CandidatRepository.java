package com.soat.candidat.domain;

import java.util.UUID;

import org.springframework.stereotype.Repository;

@Repository
public interface CandidatRepository {
    UUID next();

    Candidat save(Candidat candidat);
}
