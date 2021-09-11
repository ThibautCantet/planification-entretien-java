package com.soat.recruteur;

import com.soat.recruteur.domain.Recruteur;
import com.soat.recruteur.domain.RecruteurRepository;
import com.soat.recruteur.event.CreationRecruteurEchouee;
import com.soat.recruteur.event.CreationRecruteurReussie;
import com.soat.recruteur.event.ResultatCreationRecruteur;
import com.soat.recruteur.infrastructure.InMemoryRecruteurRepository;
import com.soat.recruteur.use_case.CreerRecruteur;
import io.cucumber.java.fr.Alors;
import io.cucumber.java.fr.Et;
import io.cucumber.java.fr.Etantdonné;
import io.cucumber.java.fr.Quand;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class CreationRecruteurATest {

    private final UUID recruteurId = UUID.fromString("96af3270-91fa-4915-8984-d1001f13fc6b");
    private final RecruteurRepository recruteurRepository = new InMemoryRecruteurRepository(recruteurId);

    private String language;
    private String email;
    private Integer experienceInYears;
    private ResultatCreationRecruteur resultatCreationRecruteur;

    @Etantdonné("un recruteur {string} \\({string}) avec {string} ans d’expériences")
    public void unRecruteurAvecAnsDExpériences(String language, String email, String experienceInYears) {
        this.language = language;
        this.email = email;
        this.experienceInYears = experienceInYears.isBlank() ? null : Integer.parseInt(experienceInYears);
    }

    @Quand("on tente de l'enregistrer")
    public void onTenteDeLEnregistrer() {
        final CreerRecruteur creerRecruteur = new CreerRecruteur(recruteurRepository);
        resultatCreationRecruteur = creerRecruteur.execute(language, email, experienceInYears);
    }

    @Alors("il est correctement enregistré avec ses informations {string}, {string} et {string} ans d’expériences")
    public void ilEstCorrectementEnregistréAvecSesInformationsEtAnsDExpériences(String language, String email, String experienceInYears) {
        assertThat(resultatCreationRecruteur).isEqualTo(new CreationRecruteurReussie(recruteurId));

        final Recruteur recruteur = recruteurRepository.findById(recruteurId);
        assertThat(recruteur).isEqualToComparingFieldByField(new Recruteur(resultatCreationRecruteur.recruteurId(), language, email, Integer.parseInt(experienceInYears)));
    }

    @Alors("l'enregistrement du recruteur est refusé pour le motif {string}")
    public void lEnregistrementDuRecruteurEstRefuséPourLeMotif(String motif) {
        assertThat(resultatCreationRecruteur).isEqualTo(new CreationRecruteurEchouee(recruteurId, motif));
    }

    @Et("le recruteur n'est pas enregistré")
    public void leRecruteurNEstPasEnregistré() {
        final Recruteur recruteur = recruteurRepository.findById(recruteurId);
        assertThat(recruteur).isNull();
    }
}
