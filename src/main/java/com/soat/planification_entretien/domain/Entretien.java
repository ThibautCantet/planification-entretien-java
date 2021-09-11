package com.soat.planification_entretien.domain;

import com.soat.planification_entretien.event.EntretienEchouee;
import com.soat.planification_entretien.event.EntretienPlanifie;
import com.soat.planification_entretien.event.ResultatPlanificationEntretien;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static java.util.Optional.ofNullable;

public class Entretien {
    private final EntretienId id;
    private UUID candidatId;
    private UUID recruteurId;
    private HoraireEntretien horaireEntretien;

    public Entretien(UUID id) {
        this.id = new EntretienId(id);
    }

    private Entretien(UUID id, UUID candidatId, UUID recruteurId, LocalDateTime horaire) {
        this.id = new EntretienId(id);
        this.candidatId = candidatId;
        this.recruteurId = recruteurId;
        this.horaireEntretien = ofNullable(horaire)
                .map(HoraireEntretien::new)
                .orElse(null);
    }

    public static Entretien of(UUID entretienId, UUID candidatId, UUID recruteurId, LocalDateTime horaire) {
        return new Entretien(entretienId, candidatId, recruteurId, horaire);
    }

    public UUID getId() {
        return id.id();
    }

    public ResultatPlanificationEntretien planifier(Candidat candidat, Recruteur recruteur, LocalDateTime disponibiliteDuCandidatDateTime, LocalDate dateDeDisponibiliteDuRecruteur) {
        if (recruteur.peutEvaluer(candidat)) {
            if (entretienEstPlanifiable(disponibiliteDuCandidatDateTime, dateDeDisponibiliteDuRecruteur)) {
                this.horaireEntretien = new HoraireEntretien(disponibiliteDuCandidatDateTime);
                this.candidatId = candidat.id();
                this.recruteurId = recruteur.id();

                return new EntretienPlanifie(id.id(), candidat.id(), recruteur.id(), disponibiliteDuCandidatDateTime);
            } else {
                return new EntretienEchouee(id.id(), candidat.id(), recruteur.id(), disponibiliteDuCandidatDateTime);
            }
        } else {
            return new EntretienEchouee(id.id(), candidat.id(), recruteur.id(), disponibiliteDuCandidatDateTime);
        }
    }

    private boolean entretienEstPlanifiable(LocalDateTime disponibiliteDuCandidatDateTime, LocalDate dateDeDisponibiliteDuRecruteur) {
        final Disponibilite disponibiliteDuCandidat = new Disponibilite(disponibiliteDuCandidatDateTime);
        return disponibiliteDuCandidat.correspondA(dateDeDisponibiliteDuRecruteur);
    }
}
