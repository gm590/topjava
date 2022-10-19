package ru.javawebinar.topjava.repository.inmemory;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.Util;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.repository.inmemory.InMemoryUserRepository.ADMIN_ID;
import static ru.javawebinar.topjava.repository.inmemory.InMemoryUserRepository.USER_ID;

public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, InMemoryBaseRepository<Meal>> repository = new ConcurrentHashMap<>();

    {
        MealsUtil.meals.forEach(meal -> save(meal, USER_ID));
        save(new Meal(LocalDateTime.of(2022, Month.OCTOBER, 18, 13, 30), "lunch", 1200), ADMIN_ID);
        save(new Meal(LocalDateTime.of(2022, Month.OCTOBER, 18, 18, 30), "dinner", 700), ADMIN_ID);
    }

    @Override
    public Meal save(Meal meal, int userId) {
        InMemoryBaseRepository<Meal> mealsByUser = repository.computeIfAbsent(userId, K -> new InMemoryBaseRepository<>());
        return mealsByUser.save(meal);
    }

    @Override
    public boolean delete(int mealId, int userId) {
        InMemoryBaseRepository<Meal> mealsByUser = repository.get(userId);
        return mealsByUser != null && mealsByUser.delete(mealId);
    }

    @Override
    public Meal get(int mealId, int userId) {
        InMemoryBaseRepository<Meal> mealsByUser = repository.get(userId);
        return mealsByUser == null ? null : mealsByUser.get(mealId);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return filterByPredicate(userId, meal -> true);
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime start, LocalDateTime end, int userId) {
        return filterByPredicate(userId, meal -> Util.isBetweenHalfOpen(meal.getDateTime(), start, end));
    }

    private List<Meal> filterByPredicate(int userId, Predicate<Meal> filter) {
        InMemoryBaseRepository<Meal> mealsByUser = repository.get(userId);
        return mealsByUser == null ? Collections.emptyList() :
                mealsByUser.getCollection().stream()
                        .filter(filter)
                        .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                        .collect(Collectors.toList());
    }
}

