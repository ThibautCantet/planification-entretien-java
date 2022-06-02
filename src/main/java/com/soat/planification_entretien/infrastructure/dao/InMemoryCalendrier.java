package com.soat.planification_entretien.infrastructure.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class InMemoryCalendrier {
    private final Map<Key, String> cache = new HashMap<>();

    public Map<Key, String> getCache() {
        return cache;
    }

    public record Key(Integer id, String email) {
    }
}
