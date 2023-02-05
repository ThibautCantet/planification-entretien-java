# Sous domaines

### Etape 4

On ne veut plus avoir de package `domain` à la racine des sous-domaines `candidat`, `entretien` et `recruteur`.

In fine, nous ne voulons avoir dans les sous-domaines `candidat`, `entretien` et `recruteur` que 2 packages `command`
et `query`.

Déplacer les packages `domain` pour les mettre dans les packages `command` et déplacer les modèles utilisés seulement
pour la lecture dans les packages `application` :

`command` :

- `domain`
- `domain_service`

`query` :

- `application`

### Etape 4.1 candidat

Déplacer `domain` dans le package `command`.

### Etape 4.2 entretien

Déplacer `domain` dans le package `command` et déplacer l'interface `IEntretien` dans le package `query.application`.

### Etape 4.3 recruteur

Déplacer `domain` dans le package `command`.

### Question

Que remarquez-vous dans les `QueryHandler` ?

### Réponse

Il y a des références au package `command` pour les modèles et les `repository`
