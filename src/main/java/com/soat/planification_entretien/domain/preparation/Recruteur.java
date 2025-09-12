package com.soat.planification_entretien.domain.preparation;

public class Recruteur {

    private static final int MINIMUM_XP_REQUISE = 3;

    private Integer id;

    private String language;
    private String email;
    private Integer experienceInYears;
    private EtatRecruteur etat;

    public Recruteur(String language, String email, int experienceInYears, boolean disponible) {
        this(null, language, email, experienceInYears, disponible);
    }

    public Recruteur(Integer recruteurId, String language, String email, Integer anneesExperience, boolean disponible) {
        this.id = new RecruteurId(recruteurId).value();
        this.language = new Langage(language).value();
        this.email = Email.of(email).adresse();
        var annéeExperience = new AnnéeExperience(anneesExperience);
        if (annéeExperience.estInférieur(MINIMUM_XP_REQUISE)) {
            throw new IllegalArgumentException();
        }
        this.experienceInYears = annéeExperience.value();
        etat = disponible ? EtatRecruteur.DISPONIBLE : EtatRecruteur.INDISPONIBLE;
    }

    public static Recruteur of(Integer id, Recruteur recruteur) {
        recruteur.id = id;
        return recruteur;
    }

    public static Recruteur of(String language, String email, int anneesExperience) {
        return new Recruteur(language, email, anneesExperience, true);
    }

    public Integer getId() {
        return id;
    }

    public String getLanguage() {
        return language;
    }

    public String getEmail() {
        return email;
    }

    public Integer getExperienceInYears() {
        return experienceInYears;
    }

    public boolean estCompatible(Candidat candidat) {
        return language.equals(candidat.getLanguage())
               && experienceInYears > candidat.getExperienceInYears();
    }

    public EtatRecruteur getEtat() {
        return etat;
    }

    public void rendreDisponible() {
        this.etat = EtatRecruteur.DISPONIBLE;
    }

    public void rendreIndisponible() {
        this.etat = EtatRecruteur.INDISPONIBLE;
    }

    public boolean estDisponible() {
        return this.etat == EtatRecruteur.DISPONIBLE;
    }
}
