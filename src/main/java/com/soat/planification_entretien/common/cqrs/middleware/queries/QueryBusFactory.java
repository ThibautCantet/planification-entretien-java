package com.soat.planification_entretien.common.cqrs.middleware.queries;

import java.util.List;

import com.soat.planification_entretien.common.cqrs.query.Query;
import com.soat.planification_entretien.common.cqrs.query.QueryHandler;
import com.soat.planification_entretien.entretien.query.application_service.ListerEntretiensQueryHandler;
import com.soat.planification_entretien.entretien.query.domain.port.repository.EntretienDao;
import com.soat.planification_entretien.recruteur.query.application_service.ListerRecruteursExperimentesQueryHandler;
import com.soat.planification_entretien.recruteur.query.domain.port.repository.RecruteurDao;
import org.springframework.stereotype.Service;

@Service
public class QueryBusFactory {

    private final EntretienDao entretienDao;
    private final RecruteurDao recruteurDao;

    public QueryBusFactory(EntretienDao entretienDao, RecruteurDao recruteurDao) {
        this.entretienDao = entretienDao;
        this.recruteurDao = recruteurDao;
    }

    protected List<QueryHandler<? extends Query, ? extends Object>> getQueryHandlers() {
        return List.of(
                new ListerEntretiensQueryHandler(entretienDao),
                new ListerRecruteursExperimentesQueryHandler(recruteurDao)
        );
    }

    public QueryBus build() {
        List<QueryHandler<? extends Query, ? extends Object>> queryHandlers = getQueryHandlers();
        final QueryBusDispatcher queryBusDispatcher = new QueryBusDispatcher(queryHandlers);

        return new QueryBusLogger(queryBusDispatcher);
    }
}
