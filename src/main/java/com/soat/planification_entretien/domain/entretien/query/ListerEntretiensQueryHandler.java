package com.soat.planification_entretien.domain.entretien.query;

import java.util.List;

import com.soat.planification_entretien.cqrs.QueryHandler;
import com.soat.planification_entretien.cqrs.QueryResponse;
import com.soat.planification_entretien.domain.entretien.event.EntretiensListes;
import com.soat.planification_entretien.domain.entretien.query.dao.EntretienDAO;
import com.soat.planification_entretien.domain.entretien.query.dto.IEntretien;

public class ListerEntretiensQueryHandler implements QueryHandler<ListerEntretiensQuery, QueryResponse<List<IEntretien>>> {

    private final EntretienDAO entretienDAO;


    public ListerEntretiensQueryHandler(EntretienDAO entretienDAO) {
        this.entretienDAO = entretienDAO;
    }

    @Override
    public QueryResponse<List<IEntretien>> handle(ListerEntretiensQuery query) {
        List<IEntretien> entretiens = entretienDAO.findAll().stream()
                .map(IEntretien.class::cast)
                .toList();
        return new QueryResponse<>(entretiens, new EntretiensListes());
    }

    @Override
    public Class listenTo() {
        return ListerEntretiensQuery.class;
    }
}
