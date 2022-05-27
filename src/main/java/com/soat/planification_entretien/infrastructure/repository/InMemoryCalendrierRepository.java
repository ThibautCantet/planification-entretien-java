package com.soat.planification_entretien.infrastructure.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soat.planification_entretien.domain.entretien.entities.Calendrier;
import com.soat.planification_entretien.domain.rendez_vous.CalendrierRepository;
import org.springframework.stereotype.Repository;

import static java.util.Optional.*;

@Repository
public class InMemoryCalendrierRepository implements CalendrierRepository {
    private final Map<Integer, InMemoryCalendrier> cache = new HashMap<>();
    private final ObjectMapper objectMapper;

    public InMemoryCalendrierRepository(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Optional<Calendrier> findByRecruteur(String emailRecruteur) {
        return cache.values()
                .stream()
                .filter(calendrier -> calendrier.emailRecruteur().equals(emailRecruteur))
                .map(InMemoryCalendrier::calendrier)
                .findFirst();
    }

    @Override
    public void save(Calendrier calendrier) {
        Integer id = ofNullable(calendrier.id())
                .orElse(cache.size() + 1);
        calendrier = calendrier.updateId(id);
        String json;
        try {
            json = objectMapper.writeValueAsString(calendrier.rendezVous());
        } catch (JsonProcessingException e) {
            json = "empty";
        }
        cache.put(id, new InMemoryCalendrier(id, calendrier.emailRecruteur(), calendrier, json));
    }

    @Override
    public String findByRecruteurId(int recruteurId) {
        return cache.get(recruteurId).rendezVousAsJson();
    }

    @Override
    public void saveAll(List<Calendrier> calendriers) {
        calendriers.forEach(this::save);
    }

    private record InMemoryCalendrier(Integer id, String emailRecruteur, Calendrier calendrier,
                                      String rendezVousAsJson) {
    }
}
