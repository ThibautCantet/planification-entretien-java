package com.soat.planification_entretien.infrastructure.repository;

import com.soat.planification_entretien.candidat.domain.CandidatProspect;
import com.soat.planification_entretien.candidat.infrastructure.repository.CandidatCrud;
import com.soat.planification_entretien.candidat.infrastructure.repository.HibernateCandidatRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import static org.assertj.core.api.AssertionsForClassTypes.*;


@AutoConfigureDataJpa
@EnableJpaRepositories
@SpringBootTest
class HibernateCandidatProspectRepositoryITest {

    @Autowired
    private CandidatCrud candidatCrud;

    @Autowired
    private HibernateCandidatRepository hibernateCandidatRepository;

    @Test
    void name() {
        CandidatProspect candidat = hibernateCandidatRepository.save(new CandidatProspect("Java", "candidat@mail.com", 3));

        assertThat(candidat)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(new CandidatProspect("Java", "candidat@mail.com", 3));
    }
}
