package com.soat.planification_entretien.infrastructure.repository;

import com.soat.planification_entretien.domain.candidat.Candidat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import static org.assertj.core.api.AssertionsForClassTypes.*;


@AutoConfigureDataJpa
@EnableJpaRepositories
@SpringBootTest
class HibernateCandidatRepositoryITest {

    @Autowired
    private CandidatCrud candidatCrud;

    @Autowired
    private HibernateCandidatRepository hibernateCandidatRepository;

    @Test
    void name() {
        Candidat candidat = hibernateCandidatRepository.save(new Candidat("Java", "candidat@mail.com", 3));

        assertThat(candidat)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(new Candidat("Java", "candidat@mail.com", 3));
    }
}
