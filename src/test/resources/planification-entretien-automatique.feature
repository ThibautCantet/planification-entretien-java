# language: fr
Fonctionnalité: Planifier un entretien de recrutement chez Soat avec le matching automatique d'un recruteur

  Scénario: Recruteur peut tester le candidat
    Etant donné un candidat "Java" ("candidat@email.com") avec "2" ans d’expériences qui est disponible "15/04/2019" à "15:00"
    Et les recruteurs existants
      | id | email              | language | xp |
      | 1  | recruteur1@soat.fr | C#       | 10 |
      | 2  | recruteur2@soat.fr | Java     | 11 |
      | 3  | recruteur3@soat.fr | Java     | 9  |
    Et les rendez-vous suivants
      | id | recruteur          | rendez-vous                                                            |
      | 1  | recruteur2@soat.fr | candidat@mail.com,15/04/2019 15:30;candidat2@mail.com,12/04/2019 15:00 |
      | 3  | recruteur3@soat.fr | candidat3@mail.com,13/04/2019 15:00                                    |
    Quand on tente une planification automatique d’entretien
    Alors L’entretien est planifié pour le recruteur "3"
    Et un mail de confirmation est envoyé au candidat et au recruteur "recruteur3@soat.fr"
    Et ajouter un rendez-vous pour le recruteur "recruteur3@soat.fr" avec "candidat@email.com" pour le "15/04/2019" à "15:00"
