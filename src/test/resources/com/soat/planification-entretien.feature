# language: fr
Fonctionnalité: Planifier un entretien de recrutement chez Soat

  Scénario: Recruteur peut tester le candidat
    Etant donné un candidat "Java" ("candidat@email.com") avec "5" ans d’expériences qui est disponible "15/04/2019" à "15:00"
    Et les recruteurs existants
      | id | email                          | language | xp | disponible |
      | 1  | recruteurC@soat.fr             | C        | 10 | true       |
      | 2  | recruteurJunior@soat.fr        | Java     | 3  | true       |
      | 3  | recruteurPasDispo@soat.fr      | Java     | 10 | false      |
      | 4  | recruteur@soat.fr              | Java     | 10 | true       |
      | 5  | recruteurNoSelectionne@soat.fr | Java     | 8  | true       |
    Quand on tente une planification d’entretien
    Alors L’entretien est planifié avec un status "planifie"
    Et un mail de confirmation est envoyé au candidat et au recruteur
    Et le recruteur "recruteur@soat.fr" n'est plus disponible

  Scénario: Recruteur ne peut pas tester le candidat car les dates ne correspondent pas
    Etant donné un candidat "Java" ("candidat@email.com") avec "2" ans d’expériences qui est disponible "16/04/2019" à "15:00"
    Et qu'un recruteur "Java" ("recruteur@soat.fr") qui a "6" ans d’XP qui est dispo "15/04/2019" à "15:00"
    Quand on tente une planification d’entretien
    Alors L’entretien n'est pas planifié
    Et aucun mail de confirmation n'est envoyé au candidat ou au recruteur

  Scénario: Recruteur ne peut pas tester le candidat car les techno ne correspondent pas
    Etant donné un candidat "C#" ("candidat@email.com") avec "2" ans d’expériences qui est disponible "15/04/2019" à "15:00"
    Et qu'un recruteur "Java" ("recruteur@soat.fr") qui a "6" ans d’XP qui est dispo "15/04/2019" à "15:00"
    Quand on tente une planification d’entretien
    Alors L’entretien n'est pas planifié
    Et aucun mail de confirmation n'est envoyé au candidat ou au recruteur

  Scénario: Recruteur ne peut pas tester le candidat car le recruteur est moins expérimenté
    Etant donné un candidat "Java" ("candidat@email.com") avec "7" ans d’expériences qui est disponible "15/04/2019" à "15:00"
    Et qu'un recruteur "Java" ("recruteur@soat.fr") qui a "6" ans d’XP qui est dispo "15/04/2019" à "15:00"
    Quand on tente une planification d’entretien
    Alors L’entretien n'est pas planifié
    Et aucun mail de confirmation n'est envoyé au candidat ou au recruteur
