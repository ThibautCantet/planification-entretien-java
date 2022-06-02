package com.soat.planification_entretien.domain.entretien.listener.dao;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soat.planification_entretien.domain.entretien.command.entity.Calendrier;
import com.soat.planification_entretien.infrastructure.dao.InMemoryCalendrier;
import org.springframework.stereotype.Repository;

@Repository("listener")
public class CalendrierDAO {
    private final InMemoryCalendrier calendrier;
    private final ObjectMapper objectMapper;

    public CalendrierDAO(InMemoryCalendrier calendrier, ObjectMapper objectMapper) {
        this.calendrier = calendrier;
        this.objectMapper = objectMapper;
    }

    public void save(Calendrier calendrier) throws JsonProcessingException {
        if (calendrier.id() == null) {
            Integer newId = this.calendrier.getCache().size() + 1;
            calendrier = new Calendrier(newId, calendrier.emailRecruteur(), calendrier.rendezVous());
        }
        String json = objectMapper.writeValueAsString(calendrier.rendezVous());
        this.calendrier.getCache().put(new InMemoryCalendrier.Key(calendrier.id(), calendrier.emailRecruteur()), json);
    }

    public void saveAll(List<Calendrier> calendriers) throws JsonProcessingException {
        for (Calendrier calendrier : calendriers) {
            save(calendrier);
        }
    }
}
