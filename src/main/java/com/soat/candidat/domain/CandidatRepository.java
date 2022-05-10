package com.soat.candidat.domain;

import org.springframework.stereotype.Repository;

@Repository
public interface CandidatRepository {
    Candidat save(Candidat candidat);
}
