package com.soat.planification_entretien.entretien.query.application_service;

import java.util.List;

import com.soat.planification_entretien.entretien.domain.port.repository.EntretienRepository;
import com.soat.planification_entretien.entretien.query.domain.port.client.IEntretien;
import org.springframework.stereotype.Service;

@Service
public class ListerEntretiensQueryHandler {

    private final EntretienRepository entretienRepository;

    public ListerEntretiensQueryHandler(EntretienRepository entretienRepository) {
        this.entretienRepository = entretienRepository;
    }

    public List<IEntretien> handle() {
        return entretienRepository.findAll().stream()
                .map(IEntretien.class::cast)
                .toList();
    }

}
