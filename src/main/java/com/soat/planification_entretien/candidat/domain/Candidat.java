package com.soat.planification_entretien.candidat.domain;

public class Candidat {

    private Integer id;

    private final CompetenceCandidat competence;
    private final EmailCandidat email;

    public Candidat(String language, String email, int experienceInYears) {
        this(null, language, email, experienceInYears);
    }

    public Candidat(Integer candidatId, String language, String email, Integer experienceEnAnnees) {
        this.id = candidatId;
        this.competence = CompetenceCandidat.of(language, experienceEnAnnees);
        this.email = EmailCandidat.of(email);
    }

    public static Candidat of(Integer id, Candidat candidat) {
        candidat.id = id;
        return candidat;
    }

    public Integer getId() {
        return id;
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
}
