package com.soat.planification_entretien.domain.rendez_vous;

import java.util.List;
import java.util.Optional;

import com.soat.planification_entretien.domain.entretien.Calendrier;

public interface CalendrierRepository {
    Optional<Calendrier> findByRecruteur(String emailRecruteur);

    void save(Calendrier calendrier);

    Calendrier findByRecruteurId(int recruteurId);

    void saveAll(List<Calendrier> calendriers);
}
