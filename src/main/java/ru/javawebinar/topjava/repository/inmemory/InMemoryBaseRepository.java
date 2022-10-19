package ru.javawebinar.topjava.repository.inmemory;

import ru.javawebinar.topjava.model.AbstractBaseEntity;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryBaseRepository<T extends AbstractBaseEntity> {
    private final AtomicInteger counter = new AtomicInteger(0);
    private final Map<Integer, T> repository = new ConcurrentHashMap<>();

    public T save(T entity) {
        if (entity.isNew()) {
            entity.setId(counter.incrementAndGet());
            repository.put(entity.getId(), entity);
            return entity;
        }
//        returns: null, if oldT == null; null and removing k, if entity == null; entity otherwise
        return repository.computeIfPresent(entity.getId(), (k, oldT) -> entity);
    }

    public boolean delete(int id) {
        // null if there was no mapping for id
        return repository.remove(id) != null;
    }

    public T get(int id) {
        // null if there was no mapping for id
        return repository.get(id);
    }

    public Collection<T> getCollection() {
        return repository.values();
    }
}
