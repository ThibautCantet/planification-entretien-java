# Controller

### Etape 8

Séparer et déplacer les `controller` dans les sous packages `command` et `query` en les intégrant au
packages `infrastructure` dans chacun de ces précédents packages.

#### Exemple

entretien :

- command
  - domain
    - repository port
    - controller
  - infrastructure
    - repository adapter
    - controller
- query
  - application
    - dao port
    - controller
  - infrastructure
    - dao adapter
    - controller

### Question

Que constatez-vous dans les nouveaux `controller` ?

### Réponse

En supprimant les use cases inutiles, on réduit les dépendances et spécialise les `controller`. Les `dto` sont bien avec
les `controller` les utilisant.
