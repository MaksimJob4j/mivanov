package ru.job4j.threads;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

public class ModelsStorage {
    ConcurrentHashMap<String, Model> models;

    public ModelsStorage() {
        this.models = new ConcurrentHashMap<>();
    }

    public void add(Model model) {
        models.putIfAbsent(model.id, model);
    }

    public void update(Model model) throws OplimisticException {
        if (model.version == models.get(model.id).version) {
            model.version++;
            models.computeIfPresent(model.id, (s, m) -> model);
        } else {
            throw new OplimisticException();
        }
    }

    public void delete(Model model) {
        models.remove(model.id);
    }

}
