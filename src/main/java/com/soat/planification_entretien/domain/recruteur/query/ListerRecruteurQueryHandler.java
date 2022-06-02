package com.soat.planification_entretien.domain.recruteur.query;

import java.util.List;

import com.soat.planification_entretien.cqrs.QueryHandler;
import com.soat.planification_entretien.cqrs.QueryResponse;
import com.soat.planification_entretien.domain.recruteur.query.dao.RecruteurDAO;
import com.soat.planification_entretien.domain.recruteur.query.dto.Recruteur;
import org.springframework.stereotype.Service;

@Service
public class ListerRecruteurQueryHandler implements QueryHandler<ListerRecruteurQuery, QueryResponse<List<Recruteur>>> {
    private final RecruteurDAO recruteurDAO;

    public ListerRecruteurQueryHandler(RecruteurDAO recruteurDAO) {
        this.recruteurDAO = recruteurDAO;
    }

    public QueryResponse<List<Recruteur>> handle(ListerRecruteurQuery query) {
        return new QueryResponse<>(recruteurDAO.find10AnsExperience(), null);
    }

    @Override
    public Class listenTo() {
        return ListerRecruteurQuery.class;
    }
}
