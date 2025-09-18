package com.soat.planification_entretien.entretien.query.infrastructure.repository;

import java.util.List;

import com.soat.planification_entretien.entretien.command.domain.model.Status;
import com.soat.planification_entretien.entretien.infrastructure.repository.HibernateEntretien;
import com.soat.planification_entretien.entretien.query.domain.model.Entretien;
import com.soat.planification_entretien.entretien.query.domain.port.repository.EntretienDao;
import org.springframework.stereotype.Repository;

@Repository
public class HibernateEntretienDao implements EntretienDao {
    private final EntretienRead entretienCrud;

    public HibernateEntretienDao(EntretienRead entretienCrud) {
        this.entretienCrud = entretienCrud;
    }

    @Override
    public List<Entretien> findAll() {
        return entretienCrud.findAll().stream()
                .map(HibernateEntretienDao::toEntretien)
                .toList();
    }

    public static Entretien toEntretien(HibernateEntretien jpaEntretien) {
        return new Entretien(
                jpaEntretien.getId(),
                jpaEntretien.getCandidat().getEmail(),
                jpaEntretien.getRecruteur().getEmail(),
                jpaEntretien.getRecruteur().getLanguage(),
                jpaEntretien.getHoraireEntretien(),
                Status.values()[jpaEntretien.getStatus()].name());
    }
}
