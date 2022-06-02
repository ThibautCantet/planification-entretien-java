package com.soat.planification_entretien.domain.rendez_vous.command.repository;

import java.util.List;
import java.util.Optional;

import com.soat.planification_entretien.domain.entretien.command.entity.Calendrier;

public interface CalendrierRepository {
    Optional<Calendrier> findByRecruteur(String emailRecruteur);

    Integer save(Calendrier calendrier);

    Calendrier findByRecruteurId(int recruteurId);

    void saveAll(List<Calendrier> calendriers);
}
