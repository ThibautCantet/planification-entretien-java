package com.soat.planification_entretien.archi_hexa.infrastructure.jpa.model;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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

    private JpaEntretien(JpaCandidat candidat, JpaRecruteur recruteur, LocalDateTime horaire) {
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
}
