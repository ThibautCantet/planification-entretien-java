package com.soat.planification_entretien.application_service.entretien;

import java.util.List;

import com.soat.planification_entretien.domain.entretien.EntretienRepository;
import com.soat.planification_entretien.domain.entretien.IEntretien;
import org.springframework.stereotype.Service;

@Service
public class ListerEntretiens {

    private final EntretienRepository entretienRepository;

    public ListerEntretiens(EntretienRepository entretienRepository) {
        this.entretienRepository = entretienRepository;
    }

    public List<IEntretien> execute() {
        return entretienRepository.findAll().stream()
                .map(IEntretien.class::cast)
                .toList();
    }

}
