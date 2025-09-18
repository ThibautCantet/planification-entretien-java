package com.soat.planification_entretien.recruteur.query.application_service;

import java.util.List;

import com.soat.planification_entretien.recruteur.query.domain.model.RecruteurDetail;
import com.soat.planification_entretien.recruteur.query.domain.port.repository.RecruteurDao;
import org.springframework.stereotype.Service;

@Service
public class ListerRecruteursExperimentesQueryHandler {
    private final RecruteurDao recruteurDao;

    public ListerRecruteursExperimentesQueryHandler(RecruteurDao recruteurDao) {
        this.recruteurDao = recruteurDao;
    }

    public List<RecruteurDetail> handle() {
        return recruteurDao.find10AnsExperience();
    }
}
