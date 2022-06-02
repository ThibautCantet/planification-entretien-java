package com.soat.planification_entretien.domain.rendez_vous;

import java.util.Optional;

import com.soat.planification_entretien.cqrs.QueryHandler;
import com.soat.planification_entretien.cqrs.QueryResponse;
import com.soat.planification_entretien.domain.entretien.CalendrierDAO;
import org.springframework.stereotype.Service;

@Service
public class ListerRendezVousRecruteurQueryHandler implements QueryHandler<ListerRendezVousRecruteurQuery, QueryResponse<String>> {
    private final CalendrierDAO calendrierDAO;

    public ListerRendezVousRecruteurQueryHandler(CalendrierDAO calendrierDAO) {
        this.calendrierDAO = calendrierDAO;
    }

    public QueryResponse<String> handle(ListerRendezVousRecruteurQuery query) {
        Optional<String> optionalRendezVous = calendrierDAO.findByRecruteur(query.email());
        if (optionalRendezVous.isPresent()) {
            return new QueryResponse<>(optionalRendezVous.get(), new RendezVousTrouves());
        }
        return new QueryResponse<>(new RendezVousNonTrouves());
    }

    @Override
    public Class listenTo() {
        return ListerRendezVousRecruteurQuery.class;
    }
}