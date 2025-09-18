package com.soat.recruteur.query.infrastucture.repository;

import com.soat.planification_entretien.recruteur.query.domain.model.RecruteurDetail;
import com.soat.planification_entretien.recruteur.query.infrastructure.repository.HibernateRecruteurDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;


class HibernateRecruteurDaoTest {

    private HibernateRecruteurDao hibernateRecruteurDao;

    @BeforeEach
    void setUp() {
        hibernateRecruteurDao = new HibernateRecruteurDao(null);
        HibernateRecruteurDao.getRecruteursExperimentes().clear();
    }

    @Test
    void should_add_Experimentes_to_recruteurs_Experimentes() {
        // given
        var recruteursExerimentes = HibernateRecruteurDao.getRecruteursExperimentes();
        var recruteurDetail = new RecruteurDetail(1, "Java", 10, "recruteur@soat.fr");

        // when
        hibernateRecruteurDao.addExperimente(recruteurDetail);

        // then
        assertThat(recruteursExerimentes).containsExactly(recruteurDetail);
    }

}
