package com.soat.planification_entretien.domain.rendez_vous;

import java.util.List;
import java.util.Optional;

import com.soat.planification_entretien.domain.entretien.entities.Calendrier;

public interface CalendrierRepository {
    Optional<Calendrier> findByRecruteur(String emailRecruteur);

    void save(Calendrier calendrier);

    String findByRecruteurId(int recruteurId);

    void saveAll(List<Calendrier> calendriers);
}
