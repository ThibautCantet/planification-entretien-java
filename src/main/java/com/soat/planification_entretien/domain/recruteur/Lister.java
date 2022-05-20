package com.soat.planification_entretien.domain.recruteur;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class Lister {
    private final RecruteurRepository recruteurRepository;

    public Lister(RecruteurRepository recruteurRepository) {
        this.recruteurRepository = recruteurRepository;
    }

    public List<Recruteur> execute() {
        return recruteurRepository.find10AnsExperience();
    }
}
