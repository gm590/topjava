package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;

public interface MealRepository {
    Meal add(Meal meal);

    Meal get(int id);

    Collection<Meal> getAll();

    Meal update(int id, Meal meal);

    boolean delete(int id);
}
