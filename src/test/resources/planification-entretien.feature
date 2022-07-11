# language: fr
Fonctionnalité: Planifier un entretien de recrutement chez Soat

  Scénario: cas passant
    Etant donné un candidat "candidat@mail.com" avec une expérience de 2 ans d XP en "Java" avec une disponibilité à "10h00"
    Et un RH "rh@soat.fr" avec 3 ans d XP sur "Java" et une disponibilité à "10h00"
    Quand on planifie l entretien
    Alors un entretien est enregistré dans l'application
    Et un email est envoyé au candidat "candidat@mail.com"
    Et un email est envoyé au RH "rh@soat.fr"

  Scénario: non cas passant xp
    Etant donné un candidat "candidat@mail.com" avec une expérience de 2 ans d XP en "Java" avec une disponibilité à "10h00"
    Et un RH "rh@soat.fr" avec 1 ans d XP sur "Java" et une disponibilité à "10h00"
    Quand on planifie l entretien
    Alors un entretien n est pas enregistré dans l'application
    Et une erreur est retournée
    Et un email aucun est envoyé au candidat
    Et un email aucun est envoyé au RH

  Scénario: cas passant techno
    Etant donné un candidat "candidat@mail.com" avec une expérience de 2 ans d XP en "Python" avec une disponibilité à "10h00"
    Et un RH "rh@soat.fr" avec 3 ans d XP sur "Java" et une disponibilité à "10h00"
    Quand on planifie l entretien
    Alors un entretien n est pas enregistré dans l'application
    Et une erreur est retournée
    Et un email aucun est envoyé au candidat
    Et un email aucun est envoyé au RH

  Scénario: cas passant dispo
    Etant donné un candidat "candidat@mail.com" avec une expérience de 2 ans d XP en "Java" avec une disponibilité à "10h00"
    Et un RH "rh@soat.fr" avec 3 ans d XP sur "Java" et une disponibilité à "09h00"
    Quand on planifie l entretien
    Alors un entretien n est pas enregistré dans l'application
    Et une erreur est retournée
    Et un email aucun est envoyé au candidat
    Et un email aucun est envoyé au RH
