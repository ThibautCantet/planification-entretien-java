package com.soat.planification_entretien.candidat.domain;

public class CandidatProspect {

    private CandidatId id;

    private final CompetenceCandidat competence;
    private final EmailCandidat email;

    public CandidatProspect(String language, String email, int experienceInYears) {
        this(null, language, email, experienceInYears);
    }

    public CandidatProspect(Integer candidatId, String language, String email, Integer experienceEnAnnees) {
        this.id = new CandidatId(candidatId);
        this.competence = CompetenceCandidat.of(language, experienceEnAnnees);
        this.email = EmailCandidat.of(email);
    }

    public static CandidatProspect of(Integer id, CandidatProspect candidat) {
        candidat.id = new CandidatId(id);
        return candidat;
    }

    public Integer getId() {
        return Integer.valueOf(id.value());
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
