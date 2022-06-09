package com.soat.planification_entretien.rendez_vous.command.repository;

import java.util.List;
import java.util.Optional;

import com.soat.planification_entretien.rendez_vous.command.domain.entity.Calendrier;

public interface CalendrierRepository {
    Optional<Calendrier> findByRecruteur(String emailRecruteur);

    Integer save(Calendrier calendrier);

    void saveAll(List<Calendrier> calendriers);
}
