package com.soat.planification_entretien.entretien.command.infrastructure.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.soat.planification_entretien.entretien.command.domain.Entretien;
import com.soat.planification_entretien.entretien.command.domain.EntretienRepository;
import com.soat.planification_entretien.entretien.command.domain.Status;
import com.soat.planification_entretien.entretien.query.application.EntretienDao;
import com.soat.planification_entretien.entretien.query.application.IEntretien;
import com.soat.planification_entretien.entretien.query.infrastructure.dao.IEntretienImpl;

//@Repository
public class InMemoryEntretienRepository implements EntretienRepository, EntretienDao {

    private final Map<Integer, Entretien> cache = new HashMap<>();

    @Override
    public void save(Entretien entretien) {
        Integer newId = cache.size() + 1;
        entretien = Entretien.of(newId, entretien);
        cache.put(entretien.getId(), entretien);
    }

    @Override
    public List<IEntretien> findAll() {
        return cache.values().stream()
                .map(e -> new IEntretienImpl(
                        e.getId(),
                        e.getEmailCandidat(),
                        e.getEmailRecruteur(),
                        e.getLanguage(),
                        e.getHoraireEntretien(),
                        Status.valueOf(e.getStatus()))
                )
                .map(e -> (IEntretien) e)
                .toList();
    }

    @Override
    public Entretien findByCandidatId(int candidatId) {
        return cache.values().stream()
                .filter(entretien -> entretien.getCandidat().id().equals(candidatId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Optional<Entretien> findById(int entretienId) {
        return Optional.ofNullable(cache.get(entretienId));
    }
}
