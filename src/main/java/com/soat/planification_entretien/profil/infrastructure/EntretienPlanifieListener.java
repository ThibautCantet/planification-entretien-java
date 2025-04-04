package com.soat.planification_entretien.profil.infrastructure;

import com.soat.planification_entretien.common.Listener;
import com.soat.planification_entretien.common.MessageBus;
import com.soat.planification_entretien.entretien.domain.EntretienPlanifié;
import com.soat.planification_entretien.profil.use_case.RendreIndisponibleRecruteur;
import org.springframework.stereotype.Service;

@Service
public class EntretienPlanifieListener implements Listener<EntretienPlanifié> {
    private final MessageBus messageBus;
    private RendreIndisponibleRecruteur rendreIndisponibleRecruteur;

    public EntretienPlanifieListener(MessageBus messageBus, RendreIndisponibleRecruteur rendreIndisponibleRecruteur) {
        this.messageBus = messageBus;
        this.rendreIndisponibleRecruteur = rendreIndisponibleRecruteur;
        this.messageBus.subscribe(this);
    }

    @Override
    public void onMessage(EntretienPlanifié entretienPlanifié) {
        rendreIndisponibleRecruteur.execute(entretienPlanifié.recruteurId());
    }
}
