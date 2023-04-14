# language: fr
Fonctionnalité: Workflow d'un entretien de recrutement chez Soat

  Scénario: Valider un entretien planifié
    Etant donné les recruteurs existants
      | id | email             | language | xp |
      | 1  | recruteur@soat.fr | Java     | 10 |
    Et les candidats existants
      | id | email             | language | xp |
      | 1  | candidat@mail.com | Java     | 5  |
    Et les entretiens existants
      | id | recruteur | candidat | horaire          | status   |
      | 1  | 1         | 1        | 16/04/2019 15:00 | PLANIFIE |
    Quand on valide l'entretien 1
    Alors on récupères les entretiens suivants en base
      | id | recruteur         | candidat          | language | horaire          | status |
      | 1  | recruteur@soat.fr | candidat@mail.com | Java     | 16/04/2019 15:00 | VALIDE |

  Scénario: Annuler un entretien validé
    Etant donné les recruteurs existants
      | id | email             | language | xp |
      | 1  | recruteur@soat.fr | Java     | 10 |
    Et les candidats existants
      | id | email             | language | xp |
      | 1  | candidat@mail.com | Java     | 5  |
    Et les entretiens existants
      | id | recruteur | candidat | horaire          | status |
      | 1  | 1         | 1        | 16/04/2019 15:00 | VALIDE |
    Quand on annule l'entretien 1
    Alors on récupères les entretiens suivants en base
      | id | recruteur         | candidat          | language | horaire          | status |
      | 1  | recruteur@soat.fr | candidat@mail.com | Java     | 16/04/2019 15:00 | ANNULE |
    Et le nombre d'entretiens annulé est incrémenté à 1
