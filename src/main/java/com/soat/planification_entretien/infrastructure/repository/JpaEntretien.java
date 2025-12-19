package com.soat.planification_entretien.infrastructure.repository;

import com.soat.planification_entretien.domain.Candidat;
import com.soat.planification_entretien.domain.Entretien;
import com.soat.planification_entretien.domain.Recruteur;
import jakarta.persistence.*;
import java.time.LocalDateTime;

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

    public JpaEntretien() {}

    public Integer getId() { return id; }
    public JpaCandidat getCandidat() { return candidat; }
    public JpaRecruteur getRecruteur() { return recruteur; }
    public LocalDateTime getHoraireEntretien() { return horaireEntretien; }

    public Entretien toEntretien() {
        return Entretien.of(
                id,
                new Candidat(
                        candidat.getId(),
                        candidat.getLanguage(),
                        candidat.getEmail(),
                        candidat.getExperienceInYears()
                ),
                new Recruteur(
                        recruteur.getId(),
                        recruteur.getLanguage(),
                        recruteur.getEmail(),
                        recruteur.getExperienceInYears()
                ),
                horaireEntretien
        );
    }
}

