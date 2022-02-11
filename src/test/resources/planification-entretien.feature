# language: fr
Fonctionnalité: Planifier un entretien de recrutement chez Soat

  Scénario: Recruteur peut tester le candidat
    Etant donné un candidat "Java" ("candidat@email.com") avec 2 ans d’expériences qui est disponible "15/04/2019" à "15:00"
    Et qu'un recruteur "Java" ("recruteur@soat.fr") qui a 6 ans d’XP qui est dispo "15/04/2019"
    Quand on tente une planification d’entretien
    Alors L’entretien est planifié : "candidat@email.com", "recruteur@soat.fr" à "15/04/2019" à "15:00"
    Et un mail de confirmation est envoyé au candidat ("candidat@email.com") et au recruteur ("recruteur@soat.fr")

  Scénario: Recruteur ne peut pas tester le candidat car les dates ne correspondent pas
    Etant donné un candidat "Java" ("candidat@email.com") avec 2 ans d’expériences qui est disponible "16/04/2019" à "15:00"
    Et qu'un recruteur "Java" ("recruteur@soat.fr") qui a 6 ans d’XP qui est dispo "15/04/2019"
    Quand on tente une planification d’entretien
    Alors L’entretien n'est pas planifié
    Et aucun mail de confirmation est envoyé au candidat ou au recruteur

  Scénario: Recruteur ne peut pas tester le candidat car leur techno sont differentes
    Etant donné un candidat "C#" ("candidat@email.com") avec 2 ans d’expériences qui est disponible "15/04/2019" à "15:00"
    Et qu'un recruteur "Java" ("recruteur@soat.fr") qui a 6 ans d’XP qui est dispo "15/04/2019"
    Quand on tente une planification d’entretien
    Alors L’entretien n'est pas planifié
    Et aucun mail de confirmation est envoyé au candidat ou au recruteur

  Scénario: Recruteur ne peut pas tester le candidat quand ce dernier est strictement plus expérimenté que le premier
    Etant donné un candidat "Java" ("candidat@email.com") avec 7 ans d’expériences qui est disponible "15/04/2019" à "15:00"
    Et qu'un recruteur "Java" ("recruteur@soat.fr") qui a 6 ans d’XP qui est dispo "15/04/2019"
    Quand on tente une planification d’entretien
    Alors L’entretien n'est pas planifié
    Et aucun mail de confirmation est envoyé au candidat ou au recruteur
