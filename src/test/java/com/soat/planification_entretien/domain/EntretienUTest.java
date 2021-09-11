package com.soat.planification_entretien.domain;

import com.soat.planification_entretien.event.EntretienEchouee;
import com.soat.planification_entretien.event.EntretienPlanifie;
import com.soat.planification_entretien.event.ResultatPlanificationEntretien;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class EntretienUTest {

    public static final UUID ENTRETIEN_ID = UUID.randomUUID();
    public static final UUID CANDIDAT_ID = UUID.randomUUID();
    public static final UUID RECRUTEUR_ID = UUID.randomUUID();
    public static final LocalDateTime DISPONIBILITE_DU_CANDIDAT_DATE_TIME = LocalDateTime.of(2021, 9, 10, 12, 0);
    public static final LocalDate DATE_DE_DISPONIBILITE_DU_RECRUTEUR = LocalDate.of(2021, 9, 10);

    private static final String JAVA = "Java";
    private static final String EMAIL_CANDIDAT = "candidat@email.com";
    private static final String EMAIL_RECRUTEUR = "recruteur@email.com";
    private static final Integer ANNEES_XP = 5;

    private Entretien entretien;
    private Candidat candidat;
    private Recruteur recruteur;

    @BeforeEach
    void setUp() {
        entretien = new Entretien(ENTRETIEN_ID);
    }

    @Nested
    class Planifier {

        @Nested
        class WhenCanPlanInterviewShould {

            @BeforeEach
            void setUp() {
                candidat = new Candidat(CANDIDAT_ID, JAVA, EMAIL_CANDIDAT, ANNEES_XP);
                recruteur = new Recruteur(RECRUTEUR_ID, JAVA, EMAIL_RECRUTEUR, ANNEES_XP);
            }

            @Test
            void return_EntretienPlanifie() {
                //when
                final ResultatPlanificationEntretien resultatPlanificationEntretien = entretien.planifier(candidat, recruteur, DISPONIBILITE_DU_CANDIDAT_DATE_TIME, DATE_DE_DISPONIBILITE_DU_RECRUTEUR);

                // then
                assertThat(resultatPlanificationEntretien).isEqualTo(new EntretienPlanifie(ENTRETIEN_ID, CANDIDAT_ID, RECRUTEUR_ID, DISPONIBILITE_DU_CANDIDAT_DATE_TIME));
            }

            @Test
            void udapte_entretien() {
                //when
                entretien.planifier(candidat, recruteur, DISPONIBILITE_DU_CANDIDAT_DATE_TIME, DATE_DE_DISPONIBILITE_DU_RECRUTEUR);

                // then
                assertThat(entretien).isEqualToComparingFieldByField(Entretien.of(ENTRETIEN_ID, CANDIDAT_ID, RECRUTEUR_ID, DISPONIBILITE_DU_CANDIDAT_DATE_TIME));
            }
        }

        @Nested
        class WhenRecruteurHasLessExperienceThanTheCandidatShould {

            @BeforeEach
            void setUp() {
                candidat = new Candidat(CANDIDAT_ID, JAVA, EMAIL_CANDIDAT, ANNEES_XP);
                recruteur = new Recruteur(RECRUTEUR_ID, JAVA, EMAIL_RECRUTEUR, ANNEES_XP - 1);
            }

            @Test
            void return_EntretienEchoue() {
                //when
                final ResultatPlanificationEntretien resultatPlanificationEntretien = entretien.planifier(candidat, recruteur, DISPONIBILITE_DU_CANDIDAT_DATE_TIME, DATE_DE_DISPONIBILITE_DU_RECRUTEUR);

                // then
                assertThat(resultatPlanificationEntretien).isEqualTo(new EntretienEchouee(ENTRETIEN_ID, CANDIDAT_ID, RECRUTEUR_ID, DISPONIBILITE_DU_CANDIDAT_DATE_TIME));
            }

            @Test
            void not_udapte_entretien() {
                //when
                entretien.planifier(candidat, recruteur, DISPONIBILITE_DU_CANDIDAT_DATE_TIME, DATE_DE_DISPONIBILITE_DU_RECRUTEUR);

                // then
                assertThat(entretien).isEqualToComparingFieldByField(Entretien.of(ENTRETIEN_ID, null, null, null));
            }
        }

        @Nested
        class WhenCannotPlanInterviewShould {

            private static final LocalDate DATE_DE_DISPONIBILITE_DU_RECRUTEUR_LE_LENDEMAIN = DATE_DE_DISPONIBILITE_DU_RECRUTEUR.plusDays(1);

            @BeforeEach
            void setUp() {
                candidat = new Candidat(CANDIDAT_ID, JAVA, EMAIL_CANDIDAT, ANNEES_XP);
                recruteur = new Recruteur(RECRUTEUR_ID, JAVA, EMAIL_RECRUTEUR, ANNEES_XP);
            }

            @Test
            void return_EntretienEchoue() {
                //when
                final ResultatPlanificationEntretien resultatPlanificationEntretien = entretien.planifier(candidat, recruteur, DISPONIBILITE_DU_CANDIDAT_DATE_TIME, DATE_DE_DISPONIBILITE_DU_RECRUTEUR_LE_LENDEMAIN);

                // then
                assertThat(resultatPlanificationEntretien).isEqualTo(new EntretienEchouee(ENTRETIEN_ID, CANDIDAT_ID, RECRUTEUR_ID, DISPONIBILITE_DU_CANDIDAT_DATE_TIME));
            }

            @Test
            void not_udapte_entretien() {
                //when
                entretien.planifier(candidat, recruteur, DISPONIBILITE_DU_CANDIDAT_DATE_TIME, DATE_DE_DISPONIBILITE_DU_RECRUTEUR_LE_LENDEMAIN);

                // then
                assertThat(entretien).isEqualToComparingFieldByField(Entretien.of(ENTRETIEN_ID, null, null, null));
            }
        }
    }
}
