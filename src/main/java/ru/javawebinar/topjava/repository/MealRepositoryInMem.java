package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealRepositoryInMem implements MealRepository {
    private AtomicInteger idCounter = new AtomicInteger(0);
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();

    {
        MealsUtil.meals.forEach(this::add);
    }

    @Override
    public Meal add(Meal meal) {
        meal.setId(idCounter.getAndIncrement());
        repository.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public Meal get(int id) {
        return repository.get(id);
    }

    @Override
    public Collection<Meal> getAll() {
        return repository.values();
    }

    @Override
    public Meal update(int id, Meal meal) {
        return repository.replace(id, meal);
    }

    @Override
    public boolean delete(int id) {
        return repository.remove(id) != null;
    }
}
