package com.soat.planification_entretien.domain;


import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class DisponibiliteUTest {

    @Nested
    class VerfierShouldReturn {
        @Test
        void true_when_both_dates_are_the_same() {
            // given
            final LocalDateTime dateEtHeureDeDisponibilite = LocalDateTime.of(2021, 9, 10, 18, 0);
            final Disponibilite disponibilite = new Disponibilite(dateEtHeureDeDisponibilite);
            final LocalDate dateDeDisponibilite = dateEtHeureDeDisponibilite.toLocalDate();

            // when
            final boolean result = disponibilite.correspondA(dateDeDisponibilite);

            // then
            assertThat(result).isTrue();
        }

        @Test
        void false_when_both_dates_are_different() {
            // given
            final LocalDateTime dateEtHeureDeDisponibilite = LocalDateTime.of(2021, 9, 10, 18, 0);
            final Disponibilite disponibilite = new Disponibilite(dateEtHeureDeDisponibilite);
            final LocalDate dateDeDisponibiliteUnJourApres = dateEtHeureDeDisponibilite.toLocalDate().plusDays(1);

            // when
            final boolean result = disponibilite.correspondA(dateDeDisponibiliteUnJourApres);

            // then
            assertThat(result).isFalse();
        }
    }
}