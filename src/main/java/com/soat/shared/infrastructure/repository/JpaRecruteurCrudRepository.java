package com.soat.shared.infrastructure.repository;

import java.util.List;
import java.util.UUID;

import com.soat.shared.infrastructure.repository.model.Recruteur;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaRecruteurCrudRepository extends CrudRepository<Recruteur, UUID> {
    List<Recruteur> findAll();
}
