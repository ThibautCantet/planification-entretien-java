package com.soat.planification_entretien.domain_service.candidat;


public record Result<Event, T>(Event event, T value)  {
    public Result(Event e) {
        this(e, null);
    }
}
