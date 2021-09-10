package com.soat.planification_entretien.domain;

public record EntretienPlanifie(Candidat candidat, Recruteur recruteur, HoraireEntretien horaireEntretien) implements ResultatPlanificationEntretien {
}
