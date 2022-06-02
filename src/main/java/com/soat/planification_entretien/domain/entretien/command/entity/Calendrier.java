package com.soat.planification_entretien.domain.entretien.command.entity;

import java.util.List;

public record Calendrier(Integer id, String emailRecruteur, List<RendezVous> rendezVous) {
    public void add(RendezVous rendezVous) {
        this.rendezVous.add(rendezVous);
    }

    public Calendrier updateId(Integer id) {
        return new Calendrier(id, emailRecruteur, rendezVous);
    }
}
