package com.soat.planification_entretien.domain.entretien;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;

@Repository
public class CalendrierDAO {
    private final Map<Key, String> cache = new HashMap<>();
    private final ObjectMapper objectMapper;

    public CalendrierDAO(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public Optional<String> findByRecruteur(String emailRecruteur) {
        return cache.entrySet()
                .stream().filter(calendrier -> calendrier.getKey().email.equals(emailRecruteur))
                .map(Map.Entry::getValue)
                .findFirst();
    }

    public void save(Calendrier calendrier) throws JsonProcessingException {
        if (calendrier.id() == null) {
            Integer newId = cache.size() + 1;
            calendrier = new Calendrier(newId, calendrier.emailRecruteur(), calendrier.rendezVous());
        }
        String json = objectMapper.writeValueAsString(calendrier.rendezVous());
        cache.put(new Key(calendrier.id(), calendrier.emailRecruteur()), json);
    }

    public void saveAll(List<Calendrier> calendriers) throws JsonProcessingException {
        for (Calendrier calendrier : calendriers) {
            save(calendrier);
        }
    }

    record Key(Integer id, String email) {
    }
}
