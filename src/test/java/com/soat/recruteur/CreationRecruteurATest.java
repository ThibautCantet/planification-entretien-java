package com.soat.recruteur;

import com.soat.recruteur.domain.Recruteur;
import com.soat.recruteur.domain.RecruteurRepository;
import com.soat.recruteur.infrastructure.InMemoryRecruteurRepository;
import com.soat.recruteur.use_case.CreerRecruteur;
import io.cucumber.java.fr.Alors;
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
    private UUID nouveauRecruteurId;

    @Etantdonné("un recruteur {string} \\({string}) avec {string} ans d’expériences")
    public void unRecruteurAvecAnsDExpériences(String language, String email, String experienceInYears) {
        this.language = language;
        this.email = email;
        this.experienceInYears = Integer.parseInt(experienceInYears);
    }

    @Quand("on tente de l'enregistrer")
    public void onTenteDeLEnregistrer() {
        final CreerRecruteur creerRecruteur = new CreerRecruteur(recruteurRepository);
        nouveauRecruteurId = creerRecruteur.execute(language, email, experienceInYears);
    }

    @Alors("il est correctement enregistré avec ses informations {string}, {string} et {string} ans d’expériences")
    public void ilEstCorrectementEnregistréAvecSesInformationsEtAnsDExpériences(String language, String email, String experienceInYears) {
        final Recruteur recruteur = recruteurRepository.findById(recruteurId);
        assertThat(recruteur).isEqualToComparingFieldByField(new Recruteur(nouveauRecruteurId, language, email, Integer.parseInt(experienceInYears)));
    }
}
