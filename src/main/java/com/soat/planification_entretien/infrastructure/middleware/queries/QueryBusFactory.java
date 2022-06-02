package com.soat.planification_entretien.infrastructure.middleware.queries;

import java.util.List;

import com.soat.planification_entretien.cqrs.Query;
import com.soat.planification_entretien.cqrs.QueryHandler;
import com.soat.planification_entretien.domain.entretien.query.ListerEntretiensQueryHandler;
import com.soat.planification_entretien.domain.entretien.query.dao.EntretienDAO;
import com.soat.planification_entretien.domain.recruteur.query.ListerRecruteurQueryHandler;
import com.soat.planification_entretien.domain.recruteur.query.dao.RecruteurDAO;
import com.soat.planification_entretien.domain.rendez_vous.query.ListerRendezVousRecruteurQueryHandler;
import org.springframework.stereotype.Service;

@Service
public class QueryBusFactory {

    private final EntretienDAO entretienDAO;
    private final RecruteurDAO recruteurDAO;
    private final com.soat.planification_entretien.domain.rendez_vous.query.dao.CalendrierDAO queryCalendrierDAO;

    public QueryBusFactory(EntretienDAO entretienDAO, RecruteurDAO recruteurDAO, com.soat.planification_entretien.domain.rendez_vous.query.dao.CalendrierDAO queryCalendrierDAO) {
        this.entretienDAO = entretienDAO;
        this.recruteurDAO = recruteurDAO;
        this.queryCalendrierDAO = queryCalendrierDAO;
    }

    protected List<QueryHandler<? extends Query, ? extends Object>> getQueryHandlers() {
        return List.of(new ListerRendezVousRecruteurQueryHandler(queryCalendrierDAO),
                new ListerEntretiensQueryHandler(entretienDAO),
                new ListerRecruteurQueryHandler(recruteurDAO)
        );
    }

    public QueryBus build() {
        List<QueryHandler<? extends Query, ? extends Object>> queryHandlers = getQueryHandlers();
        final QueryBusDispatcher queryBusDispatcher = new QueryBusDispatcher(queryHandlers);

        return new QueryBusLogger(queryBusDispatcher);
    }
}
