# language: fr
Fonctionnalité: Planifier un entretien de recrutement chez Soat

  Scénario: Recruteur peut tester le candidat
    Etant donné un candidat "Java" ("candidat@email.com") avec "4" ans d’expériences qui est disponible "15/04/2019" à "15:00"
    Et les recruteurs existants
      | id | email               | language | xp | disponible |
      | 1  | recruteur10@soat.fr | Rust     | 10 | true       |
      | 2  | recruteur11@soat.fr | Java     | 3  | true       |
      | 3  | recruteur9@soat.fr  | C++      | 9  | true       |
      | 4  | recruteur8@soat.fr  | Java     | 9  | false      |
      | 5  | recruteur7@soat.fr  | Java     | 10 | true       |
      | 6  | recruteur6@soat.fr  | Java     | 9  | true       |
    Quand on tente une planification d’entretien
    Alors L’entretien est planifié
    Et un mail de confirmation est envoyé au candidat et au recruteur "recruteur7@soat.fr"
    Et le recruteur n'est plus disponible

  Scénario: Recruteur ne peut pas tester le candidat car les dates ne correspondent pas
    Etant donné un candidat "Java" ("candidat@email.com") avec "2" ans d’expériences qui est disponible "16/04/2019" à "15:00"
    Et les recruteurs existants
      | id | email               | language | xp | disponible |
      | 1  | recruteur10@soat.fr | Java     | 10 | false       |
    Quand on tente une planification d’entretien
    Alors L’entretien n'est pas planifié
    Et aucun mail de confirmation n'est envoyé au candidat ou au recruteur

  Scénario: Recruteur ne peut pas tester le candidat car les techno ne correspondent pas
    Etant donné un candidat "C#" ("candidat@email.com") avec "2" ans d’expériences qui est disponible "15/04/2019" à "15:00"
    Et les recruteurs existants
      | id | email               | language | xp | disponible |
      | 1  | recruteur10@soat.fr | Rust     | 10 | true       |
    Quand on tente une planification d’entretien
    Alors L’entretien n'est pas planifié
    Et aucun mail de confirmation n'est envoyé au candidat ou au recruteur

  Scénario: Recruteur ne peut pas tester le candidat car le recruteur est moins expérimenté
    Etant donné un candidat "Java" ("candidat@email.com") avec "7" ans d’expériences qui est disponible "15/04/2019" à "15:00"
    Et les recruteurs existants
      | id | email               | language | xp | disponible |
      | 1  | recruteur10@soat.fr | Rust     | 5 | true       |
    Quand on tente une planification d’entretien
    Alors L’entretien n'est pas planifié
    Et aucun mail de confirmation n'est envoyé au candidat ou au recruteur
