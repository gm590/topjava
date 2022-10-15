package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.MealRepositoryInMem;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;


public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);
    private MealRepository mealRepository = new MealRepositoryInMem();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        log.info("action = {}", action);

        if (action == null) {
            log.info("All meals");
            req.setAttribute("meals",
                    MealsUtil.filteredByStreams(new ArrayList<>(mealRepository.getAll()),
                            LocalTime.MIN, LocalTime.MAX, MealsUtil.CALORIES_PER_DAY));
            req.getRequestDispatcher("meals.jsp").forward(req, resp);
        } else if (action.equals("delete")) {
            log.info("Delete meal with id = {}", req.getParameter("id"));
            mealRepository.delete(Integer.parseInt(req.getParameter("id")));
            resp.sendRedirect("meals");
        } else if (action.equals("add")) {
            log.info("Add meal");
            req.getRequestDispatcher("createMeal.jsp").forward(req, resp);
        } else if (action.equals("update")) {
            log.info("Update meal with id = {}", req.getParameter("id"));
            req.setAttribute("meal", mealRepository.get(Integer.parseInt(req.getParameter("id"))));
            req.getRequestDispatcher("createMeal.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Integer id = (req.getParameter("id").equals("") ? null : Integer.parseInt(req.getParameter("id")));
        Meal meal = new Meal(id, LocalDateTime.parse(req.getParameter("dateTime")),
                req.getParameter("description"), Integer.parseInt(req.getParameter("calories")));

        if (id == null) {
            mealRepository.add(meal);
            log.info("Meal added");
        } else {
            mealRepository.update(id, meal);
            log.info("Meal with id = {} updated", id);
        }
        resp.sendRedirect("meals");
    }
}
