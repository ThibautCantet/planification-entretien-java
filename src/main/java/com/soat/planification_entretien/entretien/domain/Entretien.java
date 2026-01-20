package com.soat.planification_entretien.entretien.domain;

import java.time.LocalDateTime;

import com.soat.planification_entretien.candidat.domain.CandidatProspect;
import com.soat.planification_entretien.recruteur.domain.Recruteur;

public class Entretien implements IEntretien {
    private EntretienId id;

    private CandidatProspect candidat;

    private LocalDateTime horaireEntretien;

    private Recruteur recruteur;

    public Entretien(Integer id, CandidatProspect candidat, Recruteur recruteur, LocalDateTime horaire) {
        this.id = new EntretienId(id);
        this.candidat = candidat;
        this.recruteur = recruteur;
        this.horaireEntretien = horaire;
    }

    private Entretien(CandidatProspect candidat, Recruteur recruteur, LocalDateTime horaire) {
        this.candidat = candidat;
        this.recruteur = recruteur;
        this.horaireEntretien = horaire;
    }

    public static Entretien of(Integer id, CandidatProspect candidat, Recruteur recruteur, LocalDateTime horaire) {
        return new Entretien(id, candidat, recruteur, horaire);
    }

    public static Entretien of(CandidatProspect candidat, Recruteur recruteur, LocalDateTime dateEtHeureDisponibiliteDuRecruteur) {
        return new Entretien(candidat, recruteur, dateEtHeureDisponibiliteDuRecruteur);
    }

    public static Entretien of(Integer newId, Entretien entretien) {
        entretien.id = new EntretienId(newId);
        return entretien;
    }

    public CandidatProspect getCandidat() {
        return candidat;
    }

    public Recruteur getRecruteur() {
        return recruteur;
    }

    public LocalDateTime getHoraireEntretien() {
        return horaireEntretien;
    }

    public Integer getId() {
        return Integer.valueOf(id.value());
    }

    @Override
    public String getEmailCandidat() {
        return candidat.getEmail();
    }

    @Override
    public String getEmailRecruteur() {
        return recruteur.getEmail();
    }

    @Override
    public String getLanguage() {
        return recruteur.getLanguage();
    }

    @Override
    public LocalDateTime getHoraire() {
        return horaireEntretien;
    }
}
