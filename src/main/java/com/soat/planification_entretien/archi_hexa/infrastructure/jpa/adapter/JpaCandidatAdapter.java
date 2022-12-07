package com.soat.planification_entretien.archi_hexa.infrastructure.jpa.adapter;

import com.soat.planification_entretien.archi_hexa.domain.model.Candidat;
import com.soat.planification_entretien.archi_hexa.domain.port.CandidatPort;
import com.soat.planification_entretien.archi_hexa.infrastructure.jpa.model.JpaCandidat;
import com.soat.planification_entretien.archi_hexa.infrastructure.jpa.repository.JpaCandidatRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JpaCandidatAdapter implements CandidatPort {

    private final JpaCandidatRepository jpaCandidatRepository;

    public JpaCandidatAdapter(JpaCandidatRepository jpaCandidatRepository) {
        this.jpaCandidatRepository = jpaCandidatRepository;
    }

    @Override
    public Optional<Candidat> findById(int id) {
        final Optional<JpaCandidat> optionalOfCandidat = jpaCandidatRepository.findById(id);
        return optionalOfCandidat.map(JpaCandidatAdapter::toCandidatDetail);
    }

    private static Candidat toCandidatDetail(JpaCandidat candidat) {
        return new Candidat(
                candidat.getId(),
                candidat.getLanguage(),
                candidat.getEmail(),
                candidat.getExperienceInYears()
        );
    }
}
