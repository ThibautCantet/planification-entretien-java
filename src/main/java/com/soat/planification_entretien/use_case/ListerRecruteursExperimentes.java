package com.soat.planification_entretien.use_case;

import java.util.List;

import com.soat.planification_entretien.domain.RecruteurDetail;
import com.soat.planification_entretien.domain.RecruteurRepository;
import org.springframework.stereotype.Service;

@Service
public class ListerRecruteursExperimentes {
    private final RecruteurRepository recruteurRepository;

    public ListerRecruteursExperimentes(RecruteurRepository recruteurRepository) {
        this.recruteurRepository = recruteurRepository;
    }

    public List<RecruteurDetail> execute() {
        return recruteurRepository.findAll()
                .stream()
                .filter(recruteur -> recruteur.getExperienceInYears() >= 10)
                .map(recruteur -> new BasicRecruteurDetail(recruteur.getId(), recruteur.getEmail(), recruteur.getLanguage(), recruteur.getExperienceInYears()))
                .map(RecruteurDetail.class::cast)
                .toList();
    }

    class BasicRecruteurDetail implements RecruteurDetail {
        private final Integer id;
        private final String email;
        private final String competence;

        BasicRecruteurDetail(Integer id, String email, String language, Integer xp) {
            this.id = id;
            this.email = email;
            this.competence = String.format("%s %s ans XP", language, xp);
        }

        @Override
        public Integer id() {
            return id;
        }

        @Override
        public String email() {
            return email;
        }

        @Override
        public String competence() {
            return competence;
        }
    }
}