# Value object

### Etape 2

Extraire des `value objects` pour encapsuler les règles métiers liées aux emails et aux années d'expérience.

### Question 1 :

Où se trouve ces règles pour le moment ?

### Réponse 1 :

Dans les entités `Candidat` et `Recruteur`

### Question 2 :

Où se trouve ces règles avec les `value objects` ?

### Réponse 2 :

Dans les `value objects` `CandidatEmail` et `Experience` dans le sous-domaine `candidat` et dans
les `value objects` `RecruteurEmail` et `Experience` dans le sous-domaine `recruteur`.
