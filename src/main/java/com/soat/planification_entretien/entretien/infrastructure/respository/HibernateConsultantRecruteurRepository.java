package com.soat.planification_entretien.entretien.infrastructure.respository;

import java.util.List;
import java.util.Optional;

import com.soat.planification_entretien.entretien.domain.ConsultantRecruteur;
import com.soat.planification_entretien.entretien.domain.ConsultantRecruteurRepository;
import com.soat.planification_entretien.profil.domain.RecruteurRepository;
import com.soat.planification_entretien.profil.infrastructure.repository.RecruteurCrud;
import org.springframework.stereotype.Repository;

@Repository
public class HibernateConsultantRecruteurRepository implements ConsultantRecruteurRepository {
    private final RecruteurRepository recruteurRepository;
    private final RecruteurCrud recruteurCrud;

    public HibernateConsultantRecruteurRepository(RecruteurRepository recruteurRepository, RecruteurCrud recruteurCrud) {
        this.recruteurRepository = recruteurRepository;
        this.recruteurCrud = recruteurCrud;
    }

    @Override
    public Optional<ConsultantRecruteur> findById(int id) {
        return recruteurRepository.findById(id)
                .map(recruteur -> new ConsultantRecruteur(recruteur.getId(),
                        recruteur.getLanguage(),
                        recruteur.getEmail(),
                        recruteur.getExperienceInYears(),
                        recruteur.isDisponible()));

    }

    @Override
    public List<ConsultantRecruteur> findAll() {
        return recruteurCrud.findAll().stream()
                .map(recruteur -> new ConsultantRecruteur(recruteur.getId(),
                        recruteur.getLanguage(),
                        recruteur.getEmail(),
                        recruteur.getExperienceInYears(),
                        recruteur.isDisponible()))
                .toList();
    }
}
