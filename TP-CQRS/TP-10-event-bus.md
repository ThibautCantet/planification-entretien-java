# Event bus

### Etape 11

De nouvelles classes et interfaces ont été ajoutées dans `common.cqrs`

Faire la même chose avec les `query` que ce qui vient d'être fait avec les `command`.

### Etape 11.1 classes `xxxListener`

Remplacer l'implémentation de l'interface`Listener` par un héritage de `EventHandlerVoid<yEvent>` et adapter la
classe `xxxListener`.

### Etape 11.2 `CommandBusFactory`

Ajouter les classes implémentant `EventHandlerVoid` dans la méthode `getEventListeners()` de la
classe `CommandBusFactory` :

```java
protected List<EventHandler<?extends Event>>getEventHandlers(){
        return List.of(
        //TODO
        );
        }
```

Supprimer les annotations `@Service` inutiles.

### Etape 11.3

Supprimer les interfaces et classes non utilisées `Listener` et `MessageBus`.
