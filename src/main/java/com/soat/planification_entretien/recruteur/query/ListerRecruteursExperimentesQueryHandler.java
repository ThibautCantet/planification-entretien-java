package com.soat.planification_entretien.recruteur.query;

import java.util.List;

import com.soat.planification_entretien.recruteur.query.application.IRecruteurDetail;
import com.soat.planification_entretien.recruteur.query.application.RecruteurDao;
import org.springframework.stereotype.Service;

@Service
public class ListerRecruteursExperimentesQueryHandler {
    private final RecruteurDao recruteurDao;

    public ListerRecruteursExperimentesQueryHandler(RecruteurDao recruteurDao) {
        this.recruteurDao = recruteurDao;
    }

    public List<IRecruteurDetail> handle() {
        return recruteurDao.find10AnsExperience();
    }
}
