package com.soat.rendezVous;

import java.time.LocalDateTime;

public class RendezVousDto {
    private final String emailCandidat;
    private final LocalDateTime horaire;

    public RendezVousDto(String emailCandidat, LocalDateTime horaire) {
        this.emailCandidat = emailCandidat;
        this.horaire = horaire;
    }

    public String getEmailCandidat() {
        return emailCandidat;
    }

    public LocalDateTime getHoraire() {
        return horaire;
    }
}
