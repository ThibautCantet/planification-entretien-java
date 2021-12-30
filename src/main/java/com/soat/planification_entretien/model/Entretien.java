package com.soat.planification_entretien.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Entretien {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "candidat_id")
    private Candidat candidat;

    @Column
    private LocalDateTime horaireEntretien;

    @ManyToOne
    @JoinColumn(name = "recruteur_id")
    private Recruteur recruteur;

    public Entretien(Candidat candidat, Recruteur recruteur) {
        this.candidat = candidat;
        this.recruteur = recruteur;
    }

    private Entretien(Candidat candidat, Recruteur recruteur, HoraireEntretien horaire) {
        this.candidat = candidat;
        this.recruteur = recruteur;
        this.horaireEntretien = horaire.horaire();
    }

    public Entretien() {

    }

    public static Entretien of(Candidat candidat, Recruteur recruteur, HoraireEntretien horaire) {
        return new Entretien(candidat, recruteur, horaire);
    }

    public Candidat getCandidat() {
        return candidat;
    }

    public ResultatPlanificationEntretien planifier(Disponibilite disponibiliteDuCandidat, LocalDate dateDeDisponibiliteDuRecruteur) {
        this.horaireEntretien = disponibiliteDuCandidat.horaire();
        if (disponibiliteDuCandidat.verifier(dateDeDisponibiliteDuRecruteur)) {
            return new EntretienPlanifie(candidat, recruteur, new HoraireEntretien(horaireEntretien));
        } else {
            return new EntretienEchouee(candidat, recruteur, new HoraireEntretien(horaireEntretien));
        }
    }
}
