package com.soat.planification_entretien.entretien.use_case;

import com.soat.planification_entretien.common.Listener;
import com.soat.planification_entretien.common.MessageBus;
import com.soat.planification_entretien.entretien.domain.EntretienPlanifié;
import com.soat.planification_entretien.profil.domain.RecruteurRepository;
import org.springframework.stereotype.Service;

@Service
public class EntretienPlanifieListener implements Listener<EntretienPlanifié> {
    private final MessageBus messageBus;
    private RecruteurRepository recruteurRepository;

    public EntretienPlanifieListener(MessageBus messageBus, RecruteurRepository recruteurRepository) {
        this.messageBus = messageBus;
        this.messageBus.subscribe(this);
        this.recruteurRepository = recruteurRepository;
    }

    @Override
    public void onMessage(EntretienPlanifié entretienPlanifié) {
        var recruteur = recruteurRepository.findById(entretienPlanifié.recruteurId()).get();
        recruteur.rendreIndisponible();
        recruteurRepository.save(recruteur);
    }
}
