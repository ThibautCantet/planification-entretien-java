package com.soat.planification_entretien.infrastructure.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soat.planification_entretien.domain.entretien.Calendrier;
import com.soat.planification_entretien.domain.entretien.Entretien;
import com.soat.planification_entretien.domain.rendez_vous.CalendrierRepository;
import org.springframework.stereotype.Repository;

import static java.util.Optional.*;

@Repository
public class InMemoryCalendrierRepository implements CalendrierRepository {
    private final Map<Integer, Calendrier> cache = new HashMap<>();
    private final ObjectMapper objectMapper;

    public InMemoryCalendrierRepository(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Optional<Calendrier> findByRecruteur(String emailRecruteur) {
        return cache.values()
                .stream().filter(calendrier -> calendrier.emailRecruteur().equals(emailRecruteur))
                .findFirst();
    }

    @Override
    public void save(Calendrier calendrier) {
        if (calendrier.id() == null) {
            Integer newId = cache.size() + 1;
            calendrier = new Calendrier(newId, calendrier.emailRecruteur(), calendrier.rendezVous());
        }
        cache.put(calendrier.id(), calendrier);
    }

    @Override
    public String findByRecruteurId(int recruteurId) {
        return null;
    }

    @Override
    public void saveAll(List<Calendrier> calendriers) {
        calendriers.forEach(this::save);
    }
}
