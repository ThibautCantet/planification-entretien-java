package com.soat.planification_entretien.profil.domain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

record EmailProspect(String addresse) {
    private static final String EMAIL_REGEX = "^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$";

    public EmailProspect {
        if (!isEmail(addresse) || addresse.endsWith("soat.fr")) {
            throw new IllegalArgumentException("Invalid email address");
        }
    }

    private static boolean isEmail(String adresse) {
        final Pattern r = Pattern.compile(EMAIL_REGEX);
        final Matcher m = r.matcher(adresse);
        return m.matches();
    }

}
