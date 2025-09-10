package com.soat.planification_entretien.use_case.planification;

import java.util.List;

import com.soat.planification_entretien.domain.planification.EntretienRepository;
import com.soat.planification_entretien.domain.planification.IEntretien;
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
