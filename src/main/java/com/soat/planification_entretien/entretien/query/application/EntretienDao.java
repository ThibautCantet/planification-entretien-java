package com.soat.planification_entretien.entretien.query.application;

import java.util.List;

public interface EntretienDao {
    List<IEntretien> findAll();
}
