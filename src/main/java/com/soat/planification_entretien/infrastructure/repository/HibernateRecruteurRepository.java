package com.soat.planification_entretien.infrastructure.repository;

import java.util.List;
import java.util.Optional;

import com.soat.planification_entretien.domain.entretien.command.entity.Calendrier;
import com.soat.planification_entretien.domain.entretien.command.entity.RendezVous;
import com.soat.planification_entretien.domain.recruteur.command.repository.RecruteurRepository;
import com.soat.planification_entretien.domain.recruteur.query.dao.RecruteurDAO;
import com.soat.planification_entretien.domain.rendez_vous.command.repository.CalendrierRepository;
import org.springframework.stereotype.Repository;

@Repository
public class HibernateRecruteurRepository implements RecruteurRepository, RecruteurDAO {
    private final RecruteurCrud recruteurCrud;
    public final CalendrierRepository calendrierRepository;

    public HibernateRecruteurRepository(RecruteurCrud recruteurCrud, CalendrierRepository calendrierRepository) {
        this.recruteurCrud = recruteurCrud;
        this.calendrierRepository = calendrierRepository;
    }

    @Override
    public Optional<com.soat.planification_entretien.domain.recruteur.command.entity.Recruteur> findById(int recruteurId) {

        Optional<Recruteur> optionalRecruteur = recruteurCrud.findById(recruteurId);
        if (optionalRecruteur.isPresent()) {

            List<RendezVous> rendezVous = calendrierRepository.findByRecruteur(optionalRecruteur.get().getEmail())
                    .map(Calendrier::rendezVous)
                    .orElse(List.of());
            return optionalRecruteur.map(
                    recruteur -> new com.soat.planification_entretien.domain.recruteur.command.entity.Recruteur(
                            recruteurId,
                            recruteur.getLanguage(),
                            recruteur.getEmail(),
                            recruteur.getExperienceInYears(),
                            rendezVous
                    )
            );
        }
        return Optional.empty();
    }

    @Override
    public com.soat.planification_entretien.domain.recruteur.command.entity.Recruteur save(com.soat.planification_entretien.domain.recruteur.command.entity.Recruteur recruteur) {
        com.soat.planification_entretien.infrastructure.repository.Recruteur toSave = new com.soat.planification_entretien.infrastructure.repository.Recruteur(recruteur.getLanguage(), recruteur.getEmail(), recruteur.getExperienceInYears());
        com.soat.planification_entretien.infrastructure.repository.Recruteur saved = recruteurCrud.save(toSave);
        return com.soat.planification_entretien.domain.recruteur.command.entity.Recruteur.of(saved.getId(), recruteur);
    }

    @Override
    public List<com.soat.planification_entretien.domain.recruteur.query.dto.Recruteur> find10AnsExperience() {
        return recruteurCrud.findAll()
                .stream().filter(r -> r.getExperienceInYears() >= 10)
                .map(recruteur -> new com.soat.planification_entretien.domain.recruteur.query.dto.Recruteur(
                        recruteur.getId(),
                        recruteur.getLanguage(),
                        recruteur.getEmail(),
                        recruteur.getExperienceInYears()))
                .toList();
    }
}
