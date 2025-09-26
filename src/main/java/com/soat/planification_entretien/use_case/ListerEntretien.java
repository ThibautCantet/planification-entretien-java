package com.soat.planification_entretien.use_case;

import java.util.List;

import com.soat.planification_entretien.controller.EntretienDetailDto;
import com.soat.planification_entretien.repository.EntretienRepository;
import org.springframework.stereotype.Service;

@Service
public class ListerEntretien {
    private final EntretienRepository entretienRepository;

    public ListerEntretien(EntretienRepository entretienRepository) {
        this.entretienRepository = entretienRepository;
    }

    public List<EntretienDetailDto> execute() {
        return entretienRepository.findAll().stream().map(entretien ->
                new EntretienDetailDto(
                        entretien.getId(),
                        entretien.getCandidat().getEmail(),
                        entretien.getRecruteur().getEmail(),
                        entretien.getRecruteur().getLanguage(),
                        entretien.getHoraireEntretien())
        ).toList();
    }
}
