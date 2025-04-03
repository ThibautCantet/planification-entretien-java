package com.soat.planification_entretien.infrastructure.repository;

import java.util.UUID;

import com.soat.planification_entretien.profil.domain.Prospect;
import com.soat.planification_entretien.profil.infrastructure.repository.CandidatCrud;
import com.soat.planification_entretien.profil.infrastructure.repository.HibernateProspectRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import static org.assertj.core.api.AssertionsForClassTypes.*;


@AutoConfigureDataJpa
@EnableJpaRepositories
@SpringBootTest
class HibernateProspectRepositoryITest {

    @Autowired
    private CandidatCrud candidatCrud;

    @Autowired
    private HibernateProspectRepository hibernateCandidatRepository;

    @Test
    void name() {
        UUID id = UUID.randomUUID();
        Prospect prospect = hibernateCandidatRepository.save(new Prospect(id, "Java", "candidat@mail.com", 3));

        assertThat(prospect)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(new Prospect(id, "Java", "candidat@mail.com", 3));
    }
}
