package com.soat.planification_entretien.entretien.query;

import java.util.List;

import com.soat.planification_entretien.entretien.command.domain.EntretienRepository;
import com.soat.planification_entretien.entretien.query.application.IEntretien;
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
