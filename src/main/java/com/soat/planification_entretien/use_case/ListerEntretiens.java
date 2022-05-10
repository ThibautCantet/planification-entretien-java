package com.soat.planification_entretien.use_case;

import java.util.List;

import com.soat.planification_entretien.domain.EntretienDetail;
import com.soat.planification_entretien.domain.EntretienRepository;
import org.springframework.stereotype.Service;

@Service
public class ListerEntretiens {

    private final EntretienRepository entretienRepository;

    public ListerEntretiens(EntretienRepository entretienRepository) {
        this.entretienRepository = entretienRepository;
    }

    public List<EntretienDetail> execute() {
        return entretienRepository.findAll().stream().map(entretien ->
                new EntretienDetail(
                        entretien.getId(),
                        entretien.getCandidat().getEmail(),
                        entretien.getRecruteur().getEmail(),
                        entretien.getRecruteur().getLanguage(),
                        entretien.getHoraireEntretien())
        ).toList();
    }
}
