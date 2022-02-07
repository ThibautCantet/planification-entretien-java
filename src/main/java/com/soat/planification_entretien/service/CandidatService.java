package com.soat.planification_entretien.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.soat.planification_entretien.model.Candidat;
import com.soat.planification_entretien.repository.CandidatRepository;
import org.springframework.stereotype.Service;

@Service
public class CandidatService {
    private static final String EMAIL_REGEX = "^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$";

    private final CandidatRepository candidatRepository;

    public CandidatService(CandidatRepository candidatRepository) {
        this.candidatRepository = candidatRepository;
    }


    public Integer creer(String language, String email, Integer experienceEnAnnees) {
        if (language.isBlank() || !isEmail(email) || experienceEnAnnees == null || experienceEnAnnees < 0) {
            return null;
        }
        Candidat candidat = new Candidat(language, email, experienceEnAnnees);
        Candidat savedCandidat = candidatRepository.save(candidat);

        return savedCandidat.getId();
    }

    private static boolean isEmail(String adresse) {
        final Pattern r = Pattern.compile(EMAIL_REGEX);
        final Matcher m = r.matcher(adresse);
        return m.matches();
    }
}
