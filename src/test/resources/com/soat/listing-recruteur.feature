# language: fr
Fonctionnalité: Listing des recruteurs expérimentés

  Scénario: Lister les recruteurs de plus de 10 ans d'XP
    Etant donné un recruteur "Java" ("recruteur3@soat.fr") avec "3" ans d’expériences
    Et on tente d'enregistrer le recruteur

    Etant donné un recruteur "Java" ("recruteur10@soat.fr") avec "10" ans d’expériences
    Et on tente d'enregistrer le recruteur

    Quand on liste les tous les recruteurs de plus de 10 ans d'XP
    Alors on récupères les recruteurs suivants
      | id | email               | competence     |
      | 2  | recruteur10@soat.fr | Java 10 ans XP |