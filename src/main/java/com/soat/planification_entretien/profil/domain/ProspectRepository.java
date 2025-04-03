package com.soat.planification_entretien.profil.domain;

import java.util.Optional;


public interface ProspectRepository {
    Optional<Prospect> findById(int candidatId);

    Prospect save(Prospect prospect);
}
