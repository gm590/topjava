<%--
  Created by IntelliJ IDEA.
  User: lume
  Date: 14.10.2022
  Time: 15:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@page import="ru.javawebinar.topjava.util.TimeUtil" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <style>
        table {
            border: 1px solid grey;
            border-collapse: collapse;
            table-layout: auto;
        }

        th, td {
            border: 1px solid grey;
        }

        .excess {
            color: #960d0d;
        }

        .notExcess {
            color: #07ad07;
        }

    </style>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<table>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th></th>
        <th></th>
    </tr>
    <c:forEach var="meal" items="${meals}">
        <tr class="${meal.excess ? 'excess' : 'notExcess'}">
            <td>${TimeUtil.format(meal.dateTime)}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td><a href="?action=update&id=${meal.id}">update</a></td>
            <td><a href="?action=delete&id=${meal.id}">delete</a></td>
        </tr>
    </c:forEach>
</table>
<a href="?action=add">add meal</a>
</body>
</html>
