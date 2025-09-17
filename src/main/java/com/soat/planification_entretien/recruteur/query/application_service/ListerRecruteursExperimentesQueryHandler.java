package com.soat.planification_entretien.recruteur.query.application_service;

import java.util.List;

import com.soat.planification_entretien.recruteur.query.domain.repository.RecruteurDao;
import com.soat.planification_entretien.recruteur.query.domain.model.Recruteur;
import org.springframework.stereotype.Service;

@Service
public class ListerRecruteursExperimentesQueryHandler {
    private final RecruteurDao recruteurDao;

    public ListerRecruteursExperimentesQueryHandler(RecruteurDao recruteurDao) {
        this.recruteurDao = recruteurDao;
    }

    public List<Recruteur> handle() {
        return recruteurDao.find10AnsExperience();
    }
}
