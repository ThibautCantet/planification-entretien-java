package com.soat.planification_entretien.domain;

import com.soat.planification_entretien.event.EntretienEchouee;
import com.soat.planification_entretien.event.EntretienPlanifie;
import com.soat.planification_entretien.event.ResultatPlanificationEntretien;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class Entretien {
    private final EntretienId id;
    private final UUID candidatId;
    private final UUID recruteurId;
    private HoraireEntretien horaireEntretien;

    public Entretien(UUID id, UUID candidatId, UUID recruteurId) {
        this.id = new EntretienId(id);
        this.candidatId = candidatId;
        this.recruteurId = recruteurId;
    }

    private Entretien(UUID id, UUID candidatId, UUID recruteurId, LocalDateTime horaire) {
        this.id = new EntretienId(id);
        this.candidatId = candidatId;
        this.recruteurId = recruteurId;
        this.horaireEntretien = new HoraireEntretien(horaire);
    }

    public static Entretien of(UUID entretienId, UUID candidatId, UUID recruteurId, LocalDateTime horaire) {
        return new Entretien(entretienId, candidatId, recruteurId, horaire);
    }

    public UUID getId() {
        return id.id();
    }

    public ResultatPlanificationEntretien planifier(Candidat candidat, Recruteur recruteur, LocalDateTime disponibiliteDuCandidatDateTime, LocalDate dateDeDisponibiliteDuRecruteur) {
        if (recruteur.peutEvaluer(candidat)) {
            final Disponibilite disponibiliteDuCandidat = new Disponibilite(disponibiliteDuCandidatDateTime);
            this.horaireEntretien = new HoraireEntretien(disponibiliteDuCandidat.horaire());
            if (disponibiliteDuCandidat.verifier(dateDeDisponibiliteDuRecruteur)) {
                return new EntretienPlanifie(id.id(), candidatId, recruteurId, horaireEntretien.horaire());
            } else {
                return new EntretienEchouee(id.id(), candidatId, recruteurId, horaireEntretien.horaire());
            }
        } else {
            return new EntretienEchouee(id.id(), candidatId, recruteurId, disponibiliteDuCandidatDateTime);
        }
    }
}
