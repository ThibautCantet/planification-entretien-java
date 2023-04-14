package com.soat.planification_entretien.common.cqrs.middleware.queries;

import java.util.List;

import com.soat.planification_entretien.common.cqrs.query.Query;
import com.soat.planification_entretien.common.cqrs.query.QueryHandler;
import com.soat.planification_entretien.entretien.query.CompterEntretiensAnnulesQueryHandler;
import com.soat.planification_entretien.entretien.query.ListerEntretiensQueryHandler;
import com.soat.planification_entretien.entretien.query.application.EntretienDao;
import com.soat.planification_entretien.entretien.query.application.EntretienProjectionDao;
import com.soat.planification_entretien.recruteur.query.ListerRecruteursExperimentesQueryHandler;
import com.soat.planification_entretien.recruteur.query.application.RecruteurDao;
import org.springframework.stereotype.Service;

@Service
public class QueryBusFactory {

    private final EntretienDao entretienDao;
    private final RecruteurDao recruteurDao;
    private final EntretienProjectionDao entretienProjectionDao;

    public QueryBusFactory(EntretienDao entretienDao, RecruteurDao recruteurDao, EntretienProjectionDao entretienProjectionDao) {
        this.entretienDao = entretienDao;
        this.recruteurDao = recruteurDao;
        this.entretienProjectionDao = entretienProjectionDao;
    }

    protected List<QueryHandler<? extends Query, ? extends Object>> getQueryHandlers() {
        return List.of(
                new ListerEntretiensQueryHandler(entretienDao),
                new ListerRecruteursExperimentesQueryHandler(recruteurDao),
                new CompterEntretiensAnnulesQueryHandler(entretienProjectionDao)
        );
    }

    public QueryBus build() {
        List<QueryHandler<? extends Query, ? extends Object>> queryHandlers = getQueryHandlers();
        final QueryBusDispatcher queryBusDispatcher = new QueryBusDispatcher(queryHandlers);

        return new QueryBusLogger(queryBusDispatcher);
    }
}
