# Message bus et Listener

### Etape 6.1 : tuyauterie pour faire transiter les `Event`

Rajouter les classes `Listener` et `MessageBus` dans le package `application_service` :

```java
public interface Listener<E extends Event> {
    void onMessage(E msg);
}
@Service
public class MessageBus {
    private List<Listener> subs = new ArrayList<>();

    public void subscribe(Listener l) {
        this.subs.add(l);
    }

    public void send(Event msg) {
        for (Listener l : subs) {
            l.onMessage(msg);
        }
    }
}
```

### Etape 6.2 : Publication d'un domain event

Créer un nouveau `Event` `EntretienCréé` :

```java
public record EntretienCréé(Integer entretienId, Integer recruteurId) implements Event {
}
```

Dans le use case `PlanifierEntretien`, ajouter en dépendance un `MessageBus` et publier un `Event` `EntretienCréé` :

```java
messageBus.send(new EntretienCréé(entretien.getId(),recruteur.getId()));
```

### Etape 6.3 : Ajout d'une propriété disponible pour un recruteur

Rajouter une propriété `disponible` pour un recruteur qui sera modifiée via une nouvelle méthode.

### Etape 6.4 : Interception de l'`Event` `EntretienCréé` et mise à jour du recruteur

Créer un `Listener` `EntretienCreeListener` dans le package `application_service.recruteur` qui va s'occuper de rendre
indisponible le recruteur :

```java
@Service
public class EntretienCreeListener implements Listener<EntretienCréé> {
    private final MessageBus messageBus;

    public EntretienCreeListener(MessageBus messageBus) {
        this.messageBus = messageBus;
        this.messageBus.subscribe(this);
    }

    @Override
    public void onMessage(EntretienCréé entretienCréé) {
        // TODO
    }
}
```

### Question

Quels sont les intérêts de passer par des domain events ?

### Réponse

- Découplage
- Open Close Principle
