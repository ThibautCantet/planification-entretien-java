package com.soat.planification_entretien.recruteur.domain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record RecruteurEmail(String adresse) {
    private static final String EMAIL_REGEX = "^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$";

    public RecruteurEmail(String adresse) {
        if (!isEmail(adresse) || !adresse.endsWith("soat.fr")) {
            throw new IllegalArgumentException();
        }
        this.adresse = adresse;
    }

    private static boolean isEmail(String adresse) {
        final Pattern r = Pattern.compile(EMAIL_REGEX);
        final Matcher m = r.matcher(adresse);
        return m.matches();
    }
}
