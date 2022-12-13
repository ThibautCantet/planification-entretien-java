package com.soat.planification_entretien.archi_hexa.domain.use_case;

import org.springframework.stereotype.Service;
import com.soat.planification_entretien.archi_hexa.domain.model.Recruteur;
import com.soat.planification_entretien.archi_hexa.domain.port.RecruteurPort;

@Service
public class CreerRecruteur {

   private final RecruteurPort recruteurPort;

   public CreerRecruteur(RecruteurPort recruteurPort) {
      this.recruteurPort = recruteurPort;
   }

   public Integer execute(Recruteur recruteur) {
      return recruteurPort.save(recruteur);
   }
}
