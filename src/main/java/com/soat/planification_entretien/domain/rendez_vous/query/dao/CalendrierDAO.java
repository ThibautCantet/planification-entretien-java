package com.soat.planification_entretien.domain.rendez_vous.query.dao;

import java.util.Map;
import java.util.Optional;

import com.soat.planification_entretien.infrastructure.dao.InMemoryCalendrier;
import org.springframework.stereotype.Repository;

@Repository("query")
public class CalendrierDAO {
    private final InMemoryCalendrier calendrier;

    public CalendrierDAO(InMemoryCalendrier calendrier) {
        this.calendrier = calendrier;
    }

    public Optional<String> findByRecruteur(String emailRecruteur) {
        return calendrier.getCache().entrySet()
                .stream().filter(calendrier -> calendrier.getKey().email().equals(emailRecruteur))
                .map(Map.Entry::getValue)
                .findFirst();
    }

}
