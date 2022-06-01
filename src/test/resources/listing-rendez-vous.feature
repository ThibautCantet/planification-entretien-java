# language: fr
Fonctionnalité: Lister les rendez-vous

  Scénario: Lister les rendez-vous des entretiens déjà planifiés pour un recruteur
    Etant donné les recruteurs existants
      | id | email              | language | xp |
      | 1  | recruteur@soat.fr  | Java     | 10 |
      | 2  | recruteur2@soat.fr | Java     | 10 |
    Et les candidats existants
      | id | email              | language | xp |
      | 1  | candidat@mail.com  | Java     | 5  |
      | 2  | candidat2@mail.com | Java     | 6  |
      | 3  | candidat3@mail.com | Java     | 7  |
    Et les entretiens existants
      | id | recruteur | candidat | horaire          |
      | 1  | 1         | 1        | 11/04/2019 15:00 |
      | 2  | 1         | 2        | 12/04/2019 15:00 |
      | 3  | 2         | 3        | 13/04/2019 15:00 |
    Et les rendez-vous suivants
      | id | recruteur          | rendez-vous                                                            |
      | 1  | recruteur@soat.fr  | candidat@mail.com,11/04/2019 15:00;candidat2@mail.com,12/04/2019 15:00 |
      | 3  | recruteur2@soat.fr | candidat3@mail.com,13/04/2019 15:00                                    |
    Quand on liste les rendez-vous du recruteur "recruteur@soat.fr"
    Alors on récupères les rendez-vous suivants
      | id | candidat           | horaire          |
      | 1  | candidat@mail.com  | 11/04/2019 15:00 |
      | 2  | candidat2@mail.com | 12/04/2019 15:00 |
