package com.soat.planification_entretien.entretien.command.domain.entity;

import java.time.LocalDateTime;

public record RendezVous(String emailCandidat, LocalDateTime horaire) {
    public boolean estCompatible(LocalDateTime horairePropose) {
        Creneau creneau = new Creneau(horaire);
        var finHorairePropose = horairePropose.plusHours(creneau.duree());
        return (!creneau.debut().equals(horairePropose) &&
                !(horairePropose.isAfter(creneau.debut()) && horairePropose.isBefore(creneau.fin())))
                &&
                (!creneau.debut().equals(finHorairePropose) &&
                        !(finHorairePropose.isAfter(creneau.debut()) && finHorairePropose.isBefore(creneau.fin())));
    }
}
