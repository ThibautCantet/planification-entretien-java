package com.soat.planification_entretien.candidat.domain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record EmailCandidat(String adresse) {

    private static final String EMAIL_REGEX = "^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$";

    static EmailCandidat of(String value) {
        if (!isEmail(value) || value.endsWith("@soat.fr")) {
            throw new IllegalArgumentException();
        }
        return new EmailCandidat(value);
    }


    private static boolean isEmail(String adresse) {
        final Pattern r = Pattern.compile(EMAIL_REGEX);
        final Matcher m = r.matcher(adresse);
        return m.matches();
    }
}
