<%--
  Created by IntelliJ IDEA.
  User: lume
  Date: 15.10.2022
  Time: 13:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<jsp:useBean id="meal" class="ru.javawebinar.topjava.model.Meal" scope="request"/>
<hr>
<h2>${meal.id == null ? "Add meal" : "Edit meal"}</h2>
<hr>
<%--форма отправляется на meals--%>
<form method="post" action="meals">
    <input type="hidden" name="id" value="${meal.id}">
    Date: <input type="datetime-local" name="dateTime" value="${meal.dateTime}">
    <hr>
    Description: <input type="text" name="description" value="${meal.description}">
    <hr>
    Calories: <input type="number" name="calories" value="${meal.calories}">
    <hr>
    <button type="submit">Save</button>
    <button type="button" onclick="window.history.back()">Cancel</button>
</form>
</body>
</html>
