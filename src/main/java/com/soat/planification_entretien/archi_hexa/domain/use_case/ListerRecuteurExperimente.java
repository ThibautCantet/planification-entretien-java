package com.soat.planification_entretien.archi_hexa.domain.use_case;

import java.util.List;
import java.util.stream.Stream;
import org.springframework.stereotype.Service;
import com.soat.planification_entretien.archi_hexa.domain.model.Recruteur;
import com.soat.planification_entretien.archi_hexa.domain.model.RecruteurExperimente;
import com.soat.planification_entretien.archi_hexa.domain.port.RecruteurPort;

@Service
public class ListerRecuteurExperimente {
   public static final int NBRE_ANNEES_EXPERIENCE = 10;

   private final RecruteurPort recruteurPort;

   public ListerRecuteurExperimente(RecruteurPort recruteurPort) {
      this.recruteurPort = recruteurPort;
   }

   public List<RecruteurExperimente> execute() {
      List<Recruteur> recruteurs = recruteurPort.getByExperience(NBRE_ANNEES_EXPERIENCE);
      return recruteurs.stream()
            .map(ListerRecuteurExperimente::toRecruteurExperimente)
            .toList();
   }

   private static RecruteurExperimente toRecruteurExperimente(Recruteur recruteur) {
      return new RecruteurExperimente(
            recruteur.id(),
            recruteur.language() + " " + recruteur.experienceInYears() + " ans XP",
            recruteur.email());
   }
}
