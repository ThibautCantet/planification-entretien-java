package com.soat.planification_entretien.entretien.query.infrastructure.dao;

import java.util.List;

import com.soat.planification_entretien.entretien.command.domain.Status;
import com.soat.planification_entretien.entretien.infrastructure.repository.Entretien;
import com.soat.planification_entretien.entretien.infrastructure.repository.EntretienCrud;
import com.soat.planification_entretien.entretien.query.application.EntretienDao;
import com.soat.planification_entretien.entretien.query.application.IEntretien;
import org.springframework.stereotype.Repository;


@Repository
public class HibernateEntretienDao implements EntretienDao {

    private final EntretienCrud entretienCrud;

    public HibernateEntretienDao(EntretienCrud entretienCrud) {
        this.entretienCrud = entretienCrud;
    }

    @Override
    public List<IEntretien> findAll() {
        return entretienCrud.findAll().stream()
                .map(HibernateEntretienDao::toIEntretien)
                .toList();
    }

    public static IEntretien toIEntretien(Entretien jpaEntretien) {
        return new IEntretienImpl(
                jpaEntretien.getId(),
                jpaEntretien.getCandidat().getEmail(),
                jpaEntretien.getRecruteur().getEmail(),
                jpaEntretien.getCandidat().getLanguage(),
                jpaEntretien.getHoraireEntretien(),
                Status.values()[jpaEntretien.getStatus()]);
    }
}
