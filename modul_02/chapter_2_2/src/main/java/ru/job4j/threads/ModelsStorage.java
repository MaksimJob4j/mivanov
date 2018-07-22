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
        models.computeIfPresent(model.id, (key, value) -> {
            if (model.version == value.version) {
                model.version++;
                return model;
            }
            throw new OplimisticException();
        });
    }

    public void delete(Model model) {
        models.remove(model.id);
    }

}
