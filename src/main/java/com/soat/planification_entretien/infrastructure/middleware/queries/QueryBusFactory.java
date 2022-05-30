package com.soat.planification_entretien.infrastructure.middleware.queries;

import java.util.List;

import com.soat.planification_entretien.cqrs.Query;
import com.soat.planification_entretien.cqrs.QueryHandler;
import com.soat.planification_entretien.domain.entretien.EntretienRepository;
import com.soat.planification_entretien.domain.entretien.ListerEntretiensQueryHandler;
import org.springframework.stereotype.Service;

@Service
public class QueryBusFactory {

    private final EntretienRepository entretienRepository;

    public QueryBusFactory(EntretienRepository entretienRepository) {
        this.entretienRepository = entretienRepository;
    }

    protected List<QueryHandler<? extends Query, ? extends Object>> getQueryHandlers() {
        return List.of(new ListerEntretiensQueryHandler(entretienRepository));
    }

    public QueryBus build() {
        List<QueryHandler<? extends Query, ? extends Object>> queryHandlers = getQueryHandlers();
        final QueryBusDispatcher queryBusDispatcher = new QueryBusDispatcher(queryHandlers);

        return new QueryBusLogger(queryBusDispatcher);
    }
}
