package com.soat.planification_entretien.profil.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface ProspectRepository {
    UUID next();
    Optional<Prospect> findById(UUID candidatId);

    Prospect save(Prospect prospect);

    List<Prospect> findAll();
}
