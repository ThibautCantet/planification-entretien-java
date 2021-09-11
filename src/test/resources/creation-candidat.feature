# language: fr
Fonctionnalité: Création d'un candidat

  Scénario: Un candidat est crée quand toutes ses informations sont complètes
    Etant donné un candidat "Java" ("candidat@email.com") avec "2" ans d’expériences
    Quand on tente de l'enregistrer
    Alors il est correctement enregistré avec ses informations "Java", "candidat@email.com" et "2" ans d’expériences