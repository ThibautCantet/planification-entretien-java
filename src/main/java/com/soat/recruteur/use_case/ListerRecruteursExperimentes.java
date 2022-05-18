package com.soat.recruteur.use_case;

import java.util.List;
import java.util.UUID;

import com.soat.recruteur.domain.RecruteurDetail;
import com.soat.recruteur.domain.RecruteurRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ListerRecruteursExperimentes {
    private final RecruteurRepository recruteurRepository;

    public ListerRecruteursExperimentes(@Qualifier("recruteur") RecruteurRepository recruteurRepository) {
        this.recruteurRepository = recruteurRepository;
    }

    public List<RecruteurDetail> execute() {
        return recruteurRepository.findAll()
                .stream()
                .filter(recruteur -> recruteur.getAnneesExperience() >= 10)
                .map(recruteur -> new BasicRecruteurDetail(recruteur.getId(), recruteur.getEmail(), recruteur.getLanguage(), recruteur.getAnneesExperience()))
                .map(RecruteurDetail.class::cast)
                .toList();
    }

    class BasicRecruteurDetail implements RecruteurDetail {
        private final UUID id;
        private final String email;
        private final String competence;

        BasicRecruteurDetail(UUID id, String email, String language, Integer xp) {
            this.id = id;
            this.email = email;
            this.competence = String.format("%s %s ans XP", language, xp);
        }

        @Override
        public UUID id() {
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
