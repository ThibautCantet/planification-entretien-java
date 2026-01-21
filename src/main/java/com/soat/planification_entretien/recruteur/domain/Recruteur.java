package com.soat.planification_entretien.recruteur.domain;

public class Recruteur {

    private boolean estDisponible;
    private RecruteurId id;

    private final CompetenceRecruteur competence;
    private final EmailRecruteur email;

    public Recruteur(String language, String email, int experienceInYears) {
        this(null, language, email, experienceInYears, true);
    }

    public Recruteur(Integer id, String language, String email, Integer experienceInYears, boolean estDisponible) {
        this.id = new RecruteurId(id);
        this.competence = CompetenceRecruteur.of(language, experienceInYears);
        this.email = EmailRecruteur.of(email);
        this.estDisponible = estDisponible;
    }

    public static Recruteur of(Integer id, Recruteur recruteur) {
        recruteur.id = new RecruteurId(id);
        return recruteur;
    }

    public Integer getId() {
        return this.id != null && id.value() != null && !id.value().isEmpty() ? Integer.valueOf(id.value()) : null;
    }

    public String getLanguage() {
        return competence.langage();
    }

    public String getEmail() {
        return email.adresse();
    }

    public Integer getExperienceInYears() {
        return competence.experienceEnAnnees();
    }

    public boolean estDisponible() {
        return estDisponible;
    }

    public void rendreIndisponible() {
        this.estDisponible = false;
    }
}
