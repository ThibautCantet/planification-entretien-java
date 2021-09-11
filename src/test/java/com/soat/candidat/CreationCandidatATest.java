package com.soat.candidat;

import com.soat.candidat.domain.Candidat;
import com.soat.candidat.domain.CandidatRepository;
import com.soat.candidat.event.CreationCandidatEchouee;
import com.soat.candidat.event.CreationCandidatReussie;
import com.soat.candidat.event.ResultatCreationCandidat;
import com.soat.candidat.infrastructure.InMemoryCandidatRepository;
import com.soat.candidat.use_case.CreerCandidat;
import io.cucumber.java.fr.Alors;
import io.cucumber.java.fr.Et;
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
    private ResultatCreationCandidat resultatCreationCandidat;

    @Etantdonné("un candidat {string} \\({string}) avec {string} ans d’expériences")
    public void unCandidatAvecAnsDExpériences(String language, String email, String experienceInYears) {
        this.language = language;
        this.email = email;
        this.experienceInYears = Integer.parseInt(experienceInYears);
    }

    @Quand("on tente de l'enregistrer")
    public void onTenteDeLEnregistrer() {
        final CreerCandidat creerCandidat = new CreerCandidat(candidatRepository);
        resultatCreationCandidat = creerCandidat.execute(language, email, experienceInYears);
    }

    @Alors("il est correctement enregistré avec ses informations {string}, {string} et {string} ans d’expériences")
    public void ilEstCorrectementEnregistréAvecSesInformationsEtAnsDExpériences(String language, String email, String experienceInYears) {
        assertThat(resultatCreationCandidat).isEqualTo(new CreationCandidatReussie(candidatId));

        final Candidat candidat = candidatRepository.findById(candidatId);
        assertThat(candidat).isEqualToComparingFieldByField(new Candidat(resultatCreationCandidat.candidatId(), language, email, Integer.parseInt(experienceInYears)));
    }

    @Alors("l'enregistrement est refusé pour le motif {string}")
    public void lEnregistrementEstRefuséPourLeMotif(String motif) {
        assertThat(resultatCreationCandidat).isEqualTo(new CreationCandidatEchouee(candidatId, motif));
    }

    @Et("le candidat n'est pas enregistré")
    public void leCandidatNEstPasEnregistré() {
        final Candidat candidat = candidatRepository.findById(candidatId);
        assertThat(candidat).isNull();
    }
}
