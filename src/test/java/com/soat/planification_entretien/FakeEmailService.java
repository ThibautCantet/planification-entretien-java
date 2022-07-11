package com.soat.planification_entretien;

public class FakeEmailService implements EmailService {
    private String emailCandidat;
    private String emailRH;

    @Override
    public void sendEmailToCandidat(String emailCandidat) {
        this.emailCandidat = emailCandidat;
    }

    @Override
    public void sendEmailToRH(String emailRH) {
        this.emailRH = emailRH;
    }

    public boolean isSentToCandidat(String emailCandidat) {
        return emailCandidat.equals(this.emailCandidat);
    }

    public boolean isSentToRH(String emailRH) {
        return emailRH.equals(this.emailRH);
    }
}
