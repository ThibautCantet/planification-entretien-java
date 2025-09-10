package com.soat.planification_entretien.infrastructure.preparation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecruteurCrud extends JpaRepository<Recruteur, Integer> {
    // default List<com.soat.planification_entretien.domain.preparation.Recruteur> find10AnsExperience() {
    //     return find10AnsExperienceImpl().stream()
    //             .map(recruteur -> new com.soat.planification_entretien.domain.preparation.Recruteur(
    //                     recruteur.getId(),
    //                     recruteur.getLanguage(),
    //                     recruteur.getEmail(),
    //                     recruteur.getExperienceInYears()))
    //             .toList();
    // }

    // @Query(value = "SELECT e FROM Recruteur where e.experienceInYears >= 10")
    // List<Recruteur> find10AnsExperienceImpl();
}
