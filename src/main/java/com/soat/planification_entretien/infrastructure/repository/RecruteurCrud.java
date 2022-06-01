package com.soat.planification_entretien.infrastructure.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RecruteurCrud extends JpaRepository<Recruteur, Integer> {
    Optional<Recruteur> findByEmail(String email);
    // default List<com.soat.planification_entretien.domain.recruteur.Recruteur> find10AnsExperience() {
    //     return find10AnsExperienceImpl().stream()
    //             .map(recruteur -> new com.soat.planification_entretien.domain.recruteur.Recruteur(
    //                     recruteur.getId(),
    //                     recruteur.getLanguage(),
    //                     recruteur.getEmail(),
    //                     recruteur.getExperienceInYears()))
    //             .toList();
    // }

    // @Query(value = "SELECT e FROM Recruteur where e.experienceInYears >= 10")
    // List<Recruteur> find10AnsExperienceImpl();
}
