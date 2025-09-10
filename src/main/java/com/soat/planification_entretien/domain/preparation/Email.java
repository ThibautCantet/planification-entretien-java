package com.soat.planification_entretien.domain.preparation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record Email(String adresse) {

    private static final String EMAIL_REGEX = "^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$";

    public static Email of(String email) {
        if (!isEmail(email)) {
            throw new IllegalArgumentException();
        }
        return new Email(email);
    }

    private static boolean isEmail(String adresse) {
        final Pattern r = Pattern.compile(EMAIL_REGEX);
        final Matcher m = r.matcher(adresse);
        return m.matches();
    }
}
