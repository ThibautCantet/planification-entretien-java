package com.soat.planification_entretien.infrastructure.middleware.queries;

import java.util.List;

import com.soat.planification_entretien.cqrs.Query;
import com.soat.planification_entretien.cqrs.QueryHandler;
import com.soat.planification_entretien.domain.entretien.listener.dao.CalendrierDAO;
import com.soat.planification_entretien.domain.entretien.command.repository.EntretienRepository;
import com.soat.planification_entretien.domain.entretien.query.ListerEntretiensQueryHandler;
import com.soat.planification_entretien.domain.recruteur.ListerQueryHandler;
import com.soat.planification_entretien.domain.recruteur.RecruteurRepository;
import com.soat.planification_entretien.domain.rendez_vous.query.ListerRendezVousRecruteurQueryHandler;
import org.springframework.stereotype.Service;

@Service
public class QueryBusFactory {

    private final EntretienRepository entretienRepository;
    private final RecruteurRepository recruteurRepository;
    private final com.soat.planification_entretien.domain.rendez_vous.query.dao.CalendrierDAO queryCalendrierDAO;

    public QueryBusFactory(EntretienRepository entretienRepository, RecruteurRepository recruteurRepository, com.soat.planification_entretien.domain.rendez_vous.query.dao.CalendrierDAO queryCalendrierDAO) {
        this.entretienRepository = entretienRepository;
        this.recruteurRepository = recruteurRepository;
        this.queryCalendrierDAO = queryCalendrierDAO;
    }

    protected List<QueryHandler<? extends Query, ? extends Object>> getQueryHandlers() {
        return List.of(new ListerRendezVousRecruteurQueryHandler(queryCalendrierDAO),
                new ListerEntretiensQueryHandler(entretienRepository),
                new ListerQueryHandler(recruteurRepository)
        );
    }

    public QueryBus build() {
        List<QueryHandler<? extends Query, ? extends Object>> queryHandlers = getQueryHandlers();
        final QueryBusDispatcher queryBusDispatcher = new QueryBusDispatcher(queryHandlers);

        return new QueryBusLogger(queryBusDispatcher);
    }
}
