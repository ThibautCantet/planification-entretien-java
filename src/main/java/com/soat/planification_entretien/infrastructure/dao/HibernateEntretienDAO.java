package com.soat.planification_entretien.infrastructure.dao;

import com.soat.planification_entretien.domain.entretien.listener.dto.Entretien;
import com.soat.planification_entretien.domain.entretien.listener.dao.EntretienDAO;
import com.soat.planification_entretien.infrastructure.repository.EntretienCrud;
import org.springframework.stereotype.Repository;

@Repository
public class HibernateEntretienDAO implements EntretienDAO {
    private final EntretienCrud entretienCrud;

    public HibernateEntretienDAO(EntretienCrud entretienCrud) {
        this.entretienCrud = entretienCrud;
    }

    @Override
    public Entretien findById(int id) {
        return entretienCrud.findById(id).map(entretien -> new Entretien(entretien.getRecruteur().getEmail(), entretien.getCandidat().getEmail(), entretien.getHoraireEntretien()))
                .orElse(null);
    }
}
