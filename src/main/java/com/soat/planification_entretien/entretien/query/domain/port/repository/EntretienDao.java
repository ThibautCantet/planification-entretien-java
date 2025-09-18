package com.soat.planification_entretien.entretien.query.domain.port.repository;

import java.util.List;

import com.soat.planification_entretien.entretien.query.domain.model.Entretien;

public interface EntretienDao {

    List<Entretien> findAll();

}
