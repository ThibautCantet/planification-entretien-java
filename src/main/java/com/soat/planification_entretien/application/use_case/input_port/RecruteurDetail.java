package com.soat.planification_entretien.application.use_case.input_port;

public record RecruteurDetail(
    Integer id,
    String email,
    String competence) {

    public RecruteurDetail(Integer id, String email, String competence, Integer experienceInYears) {
        this(id, email, String.format("%s %d ans XP", competence, experienceInYears));
    }
}
