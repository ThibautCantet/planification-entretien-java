package com.soat.planification_entretien.entretien.command.domain.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Recruteur {

    private final String id;
    private final Profil profil;
    private final String email;
    private final List<RendezVous> rendezVous;

    public Recruteur(String id,
                     String language,
                     String email,
                     Integer experienceInYears,
                     List<RendezVous> rendezVous) {
        this.id = id;
        this.rendezVous = rendezVous;
        this.profil = new Profil(language, experienceInYears);
        this.email = email;
    }

    public Recruteur(String id,
                     String language,
                     String email,
                     Integer experienceInYears) {
        this(id, language, email, experienceInYears, new ArrayList<>());
    }

    public boolean isCompatible(Candidat candidat) {
        return profil.estCompatible(candidat.getProfil());
    }

    public boolean estDisponible(LocalDateTime dateEtHeureDisponibiliteDuCandidat) {
        if (rendezVous.isEmpty()) {
            return true;
        }
        return rendezVous.stream()
                .allMatch(rdv -> rdv.estCompatible(dateEtHeureDisponibiliteDuCandidat));
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getLanguage() {
        return profil.language();
    }

    public Integer getExperienceInYears() {
        return profil.experienceInYears();
    }
}
