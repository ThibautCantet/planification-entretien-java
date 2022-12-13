package com.soat.planification_entretien.archi_hexa.domain.use_case;

import org.springframework.stereotype.Service;
import com.soat.planification_entretien.archi_hexa.domain.model.Candidat;
import com.soat.planification_entretien.archi_hexa.domain.port.CandidatPort;

@Service
public class CreerCandidat {

   private final CandidatPort candidatPort;

   public CreerCandidat(CandidatPort candidatPort) {
      this.candidatPort = candidatPort;
   }


   public Integer execute(Candidat candidat) {
      return candidatPort.save(candidat);
   }
}
