package com.soat.planification_entretien.profil.use_case;

import java.util.UUID;

import com.soat.planification_entretien.profil.domain.Prospect;
import com.soat.planification_entretien.profil.domain.ProspectRepository;
import org.springframework.stereotype.Service;

@Service
public class CreerProspect {

    private final ProspectRepository prospectRepository;

    public CreerProspect(ProspectRepository prospectRepository) {
        this.prospectRepository = prospectRepository;
    }

    public UUID execute(String language, String email, String experienceEnAnnees) {
        try {
            Prospect prospect = new Prospect(prospectRepository.next(), language, email, Integer.parseInt(experienceEnAnnees));

            Prospect savedProspect = prospectRepository.save(prospect);

            return savedProspect.getId();
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

}
