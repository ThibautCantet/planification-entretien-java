package com.soat.planification_entretien.entretien.query.application_service;

import com.soat.planification_entretien.common.cqrs.query.Query;
import com.soat.planification_entretien.common.cqrs.query.QueryHandler;
import com.soat.planification_entretien.common.cqrs.query.QueryResponse;
import com.soat.planification_entretien.entretien.query.domain.port.repository.EntretienAnnuleDao;

public class CompterEntretiensAnnulésQueryHandler implements QueryHandler<CompterEntretiensAnnulésQueryHandler.CompterEntretiensAnnulésQuery, QueryResponse<Integer>> {

    private final EntretienAnnuleDao entretienAnnuleDao;

    public CompterEntretiensAnnulésQueryHandler(EntretienAnnuleDao entretienAnnuleDao) {
        this.entretienAnnuleDao = entretienAnnuleDao;
    }

    @Override
    public QueryResponse<Integer> handle(CompterEntretiensAnnulésQuery query) {
        return new QueryResponse<>(entretienAnnuleDao.getNbEntretiensAnnules(), null);
    }

    @Override
    public Class listenTo() {
        return CompterEntretiensAnnulésQuery.class;
    }

    public record CompterEntretiensAnnulésQuery() implements Query {
    }
}
