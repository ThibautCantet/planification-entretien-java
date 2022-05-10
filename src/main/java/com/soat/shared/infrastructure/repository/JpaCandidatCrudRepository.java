package com.soat.shared.infrastructure.repository;

import com.soat.shared.infrastructure.repository.model.Candidat;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaCandidatCrudRepository extends CrudRepository<Candidat, Integer> {
}
