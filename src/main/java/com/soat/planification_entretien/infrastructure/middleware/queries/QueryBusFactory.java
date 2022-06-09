package com.soat.planification_entretien.infrastructure.middleware.queries;

import java.util.List;

import com.soat.planification_entretien.cqrs.Query;
import com.soat.planification_entretien.cqrs.QueryHandler;
import com.soat.planification_entretien.entretien.query.ListerEntretiensQueryHandler;
import com.soat.planification_entretien.entretien.query.dao.EntretienDAO;
import com.soat.planification_entretien.recruteur.query.ListerRecruteurQueryHandler;
import com.soat.planification_entretien.recruteur.query.dao.RecruteurDAO;
import com.soat.planification_entretien.rendez_vous.query.ListerRendezVousRecruteurQueryHandler;
import com.soat.planification_entretien.rendez_vous.query.dao.CalendrierDAO;
import org.springframework.stereotype.Service;

@Service
public class QueryBusFactory {

    private final EntretienDAO entretienDAO;
    private final RecruteurDAO recruteurDAO;
    private final CalendrierDAO queryCalendrierDAO;

    public QueryBusFactory(EntretienDAO entretienDAO, RecruteurDAO recruteurDAO, CalendrierDAO queryCalendrierDAO) {
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
