package com.soat.planification_entretien.entretien.infrastructure.repository;

import java.util.List;

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
                .map(HibernateEntretienRepository::toIEntretien)
                .toList();
    }
}
