# language: fr
Fonctionnalité: Création d'un recruteur

  Scénario: Un recruteur est crée quand toutes ses informations sont complètes
    Etant donné un recruteur "Java" ("recruteur@email.com") avec "2" ans d’expériences
    Quand on tente d'enregistrer le recruteur
    Alors le recruteur est correctement enregistré avec ses informations "Java", "recruteur@email.com" et "2" ans d’expériences

  Scénario: Un recruteur n'est pas crée quand sa techno principale est vide
    Etant donné un recruteur "" ("recruteur@email.com") avec "2" ans d’expériences
    Quand on tente d'enregistrer le recruteur
    Alors l'enregistrement du recruteur est refusé
    Et le recruteur n'est pas enregistré

  Scénario: Un recruteur n'est pas crée quand son nombre d'années d'expérience est vide
    Etant donné un recruteur "Java" ("recruteur@email.com") avec "" ans d’expériences
    Quand on tente d'enregistrer le recruteur
    Alors l'enregistrement du recruteur est refusé
    Et le recruteur n'est pas enregistré

  Scénario: Un recruteur n'est pas crée quand son nombre d'années d'expérience est négatif
    Etant donné un recruteur "Java" ("recruteur@email.com") avec "-1" ans d’expériences
    Quand on tente d'enregistrer le recruteur
    Alors l'enregistrement du recruteur est refusé
    Et le recruteur n'est pas enregistré

  Scénario: Un recruteur n'est pas crée quand son email est vide
    Etant donné un recruteur "Java" ("") avec "2" ans d’expériences
    Quand on tente d'enregistrer le recruteur
    Alors l'enregistrement du recruteur est refusé
    Et le recruteur n'est pas enregistré

  Scénario: Un recruteur n'est pas crée quand son email est incorrect
    Etant donné un recruteur "Java" ("recruteur@email") avec "2" ans d’expériences
    Quand on tente d'enregistrer le recruteur
    Alors l'enregistrement du recruteur est refusé
    Et le recruteur n'est pas enregistré

  Scénario: Lister les recuteurs ayant au moins 10 ans d'experiences
    Etant donné les recruteurs existants dans la base
      | id | email              | language | xp |
      | 1  | recruteur1@soat.fr | Java     | 10 |
      | 2  | recruteur2@soat.fr | Java     | 5  |
      | 3  | recruteur3@soat.fr | C#       | 12 |
    Quand on cherche les recuteurs ayant au moins 10 d'experience
    Alors on récupères les recuteurs suivants
      | id | email              | competence     |
      | 1  | recruteur1@soat.fr | Java 10 ans XP |
      | 3  | recruteur3@soat.fr | C# 12 ans XP   |
