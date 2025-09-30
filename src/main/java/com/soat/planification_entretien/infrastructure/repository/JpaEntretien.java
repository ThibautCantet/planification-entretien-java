package com.soat.planification_entretien.infrastructure.repository;

import java.time.LocalDateTime;

import com.soat.planification_entretien.domain.model.Entretien;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class JpaEntretien {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "candidat_id")
    private JpaCandidat candidat;

    @Column
    private LocalDateTime horaireEntretien;

    @ManyToOne
    @JoinColumn(name = "recruteur_id")
    private JpaRecruteur recruteur;

    public JpaEntretien(JpaCandidat candidat, JpaRecruteur recruteur, LocalDateTime horaire) {
        this.candidat = candidat;
        this.recruteur = recruteur;
        this.horaireEntretien = horaire;
    }

    public JpaEntretien() {

    }

    public static JpaEntretien of(JpaCandidat candidat, JpaRecruteur recruteur, LocalDateTime horaire) {
        return new JpaEntretien(candidat, recruteur, horaire);
    }

    public JpaCandidat getCandidat() {
        return candidat;
    }

    public JpaRecruteur getRecruteur() {
        return recruteur;
    }

    public LocalDateTime getHoraireEntretien() {
        return horaireEntretien;
    }

    public Integer getId() {
        return id;
    }

    public Entretien toDomain() {
        return new Entretien(id, candidat.toDomain(), recruteur.toDomain(), horaireEntretien);
    }
}
