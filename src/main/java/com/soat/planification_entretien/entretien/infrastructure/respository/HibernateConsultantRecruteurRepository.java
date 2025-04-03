package com.soat.planification_entretien.entretien.infrastructure.respository;

import java.util.Optional;

import com.soat.planification_entretien.entretien.domain.ConsultantRecruteur;
import com.soat.planification_entretien.entretien.domain.ConsultantRecruteurRepository;
import com.soat.planification_entretien.profil.domain.RecruteurRepository;
import org.springframework.stereotype.Repository;

@Repository
public class HibernateConsultantRecruteurRepository implements ConsultantRecruteurRepository {
    private final RecruteurRepository recruteurRepository;

    public HibernateConsultantRecruteurRepository(RecruteurRepository recruteurRepository) {
        this.recruteurRepository = recruteurRepository;
    }

    @Override
    public Optional<ConsultantRecruteur> findById(int id) {
        return recruteurRepository.findById(id)
                .map(recruteur -> new ConsultantRecruteur(recruteur.getId(),
                        recruteur.getLanguage(),
                        recruteur.getEmail(),
                        recruteur.getExperienceInYears()));
    }
}
