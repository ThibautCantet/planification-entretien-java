package com.soat.planification_entretien.domain.entretien;

import java.util.List;

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
                )
                .map(EntretienDetail.class::cast)
                .toList();
    }

}
