package com.soat.planification_entretien.infrastructure.planification.repository;

import java.time.LocalDateTime;

import com.soat.planification_entretien.infrastructure.preparation.repository.Candidat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Entretien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column()
    private Integer candidatId;

    @Column
    private LocalDateTime horaireEntretien;

    @Column
    private Integer recruteurId;

    @Column
    private String etatEntretien;

    private Entretien(Integer id, Integer candidatId, Integer recruteurId, LocalDateTime horaire, String etatEntretien) {
        this.id = id;
        this.candidatId = candidatId;
        this.recruteurId = recruteurId;
        this.horaireEntretien = horaire;
        this.etatEntretien = etatEntretien;
    }

    public Entretien() {

    }

    public static Entretien of(Integer id, Integer candidatId, Integer recruteurId, LocalDateTime horaire, String etatEntretien) {
        return new Entretien(id, candidatId, recruteurId, horaire, etatEntretien);
    }

    public Integer getCandidatId() {
        return candidatId;
    }

    public Integer getRecruteurId() {
        return recruteurId;
    }


    public LocalDateTime getHoraireEntretien() {
        return horaireEntretien;
    }

    public Integer getId() {
        return id;
    }

    public String getEtatEntretien() {
        return etatEntretien;
    }

}
