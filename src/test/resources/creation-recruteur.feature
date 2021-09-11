# language: fr
Fonctionnalité: Création d'un recruteur

  Scénario: Un recruteur est crée quand toutes ses informations sont complètes
    Etant donné un recruteur "Java" ("recruteur@email.com") avec "2" ans d’expériences
    Quand on tente de l'enregistrer
    Alors il est correctement enregistré avec ses informations "Java", "recruteur@email.com" et "2" ans d’expériences