package com.soat.planification_entretien.infrastructure.middleware.queries;

import java.util.List;

import com.soat.planification_entretien.cqrs.Query;
import com.soat.planification_entretien.cqrs.QueryHandler;
import com.soat.planification_entretien.domain.entretien.CalendrierDAO;
import com.soat.planification_entretien.domain.rendez_vous.ListerRendezVousRecruteurQueryHandler;
import org.springframework.stereotype.Service;

@Service
public class QueryBusFactory {

    private final CalendrierDAO calendrierDAO;

    public QueryBusFactory(CalendrierDAO calendrierDAO) {
        this.calendrierDAO = calendrierDAO;
    }

    protected List<QueryHandler<? extends Query, ? extends Object>> getQueryHandlers() {
        return List.of(new ListerRendezVousRecruteurQueryHandler(calendrierDAO));
    }

    public QueryBus build() {
        List<QueryHandler<? extends Query, ? extends Object>> queryHandlers = getQueryHandlers();
        final QueryBusDispatcher queryBusDispatcher = new QueryBusDispatcher(queryHandlers);

        return new QueryBusLogger(queryBusDispatcher);
    }
}
