package com.soat.planification_entretien.recruteur.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecruteurCrud extends JpaRepository<Recruteur, Integer> {
    // default List<com.soat.planification_entretien.recruteur.domain.Recruteur> find10AnsExperience() {
    //     return find10AnsExperienceImpl().stream()
    //             .map(recruteur -> new com.soat.planification_entretien.recruteur.domain.Recruteur(
    //                     recruteur.getId(),
    //                     recruteur.getLanguage(),
    //                     recruteur.getEmail(),
    //                     recruteur.getExperienceInYears()))
    //             .toList();
    // }

    // @Query(value = "SELECT e FROM Recruteur where e.experienceInYears >= 10")
    // List<Recruteur> find10AnsExperienceImpl();
}
