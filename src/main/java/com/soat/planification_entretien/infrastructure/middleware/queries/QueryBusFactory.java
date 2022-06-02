package com.soat.planification_entretien.infrastructure.middleware.queries;

import java.util.List;

import com.soat.planification_entretien.cqrs.Query;
import com.soat.planification_entretien.cqrs.QueryHandler;
import com.soat.planification_entretien.domain.entretien.CalendrierDAO;
import com.soat.planification_entretien.domain.entretien.EntretienRepository;
import com.soat.planification_entretien.domain.entretien.ListerEntretiensQueryHandler;
import com.soat.planification_entretien.domain.recruteur.ListerQueryHandler;
import com.soat.planification_entretien.domain.recruteur.RecruteurRepository;
import com.soat.planification_entretien.domain.rendez_vous.ListerRendezVousRecruteurQueryHandler;
import org.springframework.stereotype.Service;

@Service
public class QueryBusFactory {

    private final CalendrierDAO calendrierDAO;
    private final EntretienRepository entretienRepository;
    private final RecruteurRepository recruteurRepository;

    public QueryBusFactory(CalendrierDAO calendrierDAO, EntretienRepository entretienRepository, RecruteurRepository recruteurRepository) {
        this.calendrierDAO = calendrierDAO;
        this.entretienRepository = entretienRepository;
        this.recruteurRepository = recruteurRepository;
    }

    protected List<QueryHandler<? extends Query, ? extends Object>> getQueryHandlers() {
        return List.of(new ListerRendezVousRecruteurQueryHandler(calendrierDAO),
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
