package com.soat.candidat;

import com.soat.candidat.domain.Candidat;
import com.soat.candidat.domain.CandidatRepository;
import com.soat.candidat.infrastructure.InMemoryCandidatRepository;
import com.soat.candidat.use_case.CreerCandidat;
import io.cucumber.java.fr.Alors;
import io.cucumber.java.fr.Etantdonné;
import io.cucumber.java.fr.Quand;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class CreationCandidatATest {

    private final UUID candidatId = UUID.fromString("96af3270-91fa-4915-8984-d1001f13fc6b");
    private final CandidatRepository candidatRepository = new InMemoryCandidatRepository(candidatId);

    private String language;
    private String email;
    private Integer experienceInYears;
    private UUID nouveauCandidatId;

    @Etantdonné("un candidat {string} \\({string}) avec {string} ans d’expériences")
    public void unCandidatAvecAnsDExpériences(String language, String email, String experienceInYears) {
        this.language = language;
        this.email = email;
        this.experienceInYears = Integer.parseInt(experienceInYears);
    }

    @Quand("on tente de l'enregistrer")
    public void onTenteDeLEnregistrer() {
        final CreerCandidat creerCandidat = new CreerCandidat(candidatRepository);
        nouveauCandidatId = creerCandidat.execute(language, email, experienceInYears);
    }

    @Alors("il est correctement enregistré avec ses informations {string}, {string} et {string} ans d’expériences")
    public void ilEstCorrectementEnregistréAvecSesInformationsEtAnsDExpériences(String language, String email, String experienceInYears) {
        final Candidat candidat = candidatRepository.findById(candidatId);
        assertThat(candidat).isEqualToComparingFieldByField(new Candidat(nouveauCandidatId, language, email, Integer.parseInt(experienceInYears)));
    }
}
