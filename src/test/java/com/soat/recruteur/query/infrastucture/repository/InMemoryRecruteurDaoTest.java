package com.soat.recruteur.query.infrastucture.repository;

import com.soat.planification_entretien.recruteur.query.domain.model.RecruteurDetail;
import com.soat.planification_entretien.recruteur.query.infrastructure.repository.InMemoryRecruteurDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;


class InMemoryRecruteurDaoTest {

    private InMemoryRecruteurDao inMemoryRecruteurDao;

    @BeforeEach
    void setUp() {
        inMemoryRecruteurDao = new InMemoryRecruteurDao();
        inMemoryRecruteurDao.find10AnsExperience().clear();
    }

    @Test
    void should_add_Experimentes_to_recruteurs_Experimentes() {
        // given
        var recruteursExerimentes = inMemoryRecruteurDao.find10AnsExperience();
        var recruteurDetail = new RecruteurDetail(1, "Java", 10, "recruteur@soat.fr");

        // when
        inMemoryRecruteurDao.addExperimente(recruteurDetail);

        // then
        assertThat(recruteursExerimentes).containsExactly(recruteurDetail);
    }

}
