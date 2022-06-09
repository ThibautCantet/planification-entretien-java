package com.soat.planification_entretien.recruteur.command.domain.entity;

import com.soat.planification_entretien.cqrs.Event;
import com.soat.planification_entretien.recruteur.event.RecruteurCree;
import com.soat.planification_entretien.recruteur.event.RecruteurNonCree;

public class Recruteur {
    private RecruteurId id;

    private Language language;
    private Email email;
    private AnneesExperience experienceInYears;
    private Event event;

    private Recruteur(String recruteurId, String language, String email, Integer experienceInYears) {
        id = new RecruteurId(recruteurId);
        this.language = Language.create(language);
        this.email = Email.create(email);
        this.experienceInYears = AnneesExperience.create(experienceInYears);

        if (isValid()) {
            event = new RecruteurCree();
        } else {
            event = new RecruteurNonCree();
        }
    }

    private boolean isValid() {
        return id != null && this.language != null && this.email != null && this.experienceInYears != null;
    }

    public static Recruteur create(String recruteurId, String language, String email, Integer experienceInYears) {
        return new Recruteur(recruteurId, language, email, experienceInYears);
    }

    public static Recruteur of(String id, Recruteur recruteur) {
        recruteur.id = new RecruteurId(id);
        return recruteur;
    }

    public String getId() {
        return id.value();
    }

    public String getLanguage() {
        return language.nom();
    }

    public String getEmail() {
        return email.address();
    }

    public Integer getExperienceInYears() {
        return experienceInYears.nombre();
    }

    public Event getEvent() {
        return event;
    }
}
