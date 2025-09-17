package com.soat.planification_entretien.entretien.application_service.query;

import java.util.List;

import com.soat.planification_entretien.entretien.domain.port.repository.EntretienRepository;
import com.soat.planification_entretien.entretien.domain.port.client.IEntretien;
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
