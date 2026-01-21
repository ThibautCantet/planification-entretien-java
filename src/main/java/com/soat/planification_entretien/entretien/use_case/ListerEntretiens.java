package com.soat.planification_entretien.entretien.use_case;

import java.util.List;

import com.soat.planification_entretien.entretien.domain.aggregate.EntretienRepository;
import com.soat.planification_entretien.entretien.domain.aggregate.IEntretien;
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
