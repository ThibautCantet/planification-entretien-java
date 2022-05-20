package com.soat.planification_entretien.domain.entretien;

import java.util.List;

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
