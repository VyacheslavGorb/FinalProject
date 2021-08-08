<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent" var="rb"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link href="style/form_page.css" rel="stylesheet">
    <link href="style/footer.css" rel="stylesheet">
    <title><fmt:message key="company.name" bundle="${rb}"/> - <fmt:message key="page.name.login"
                                                                           bundle="${rb}"/></title>
</head>
<body>
<jsp:include page="../parts/header.jsp" flush="true"/>

<ul class="nav nav-tabs mb-5">
    <li class="nav-item">
        <a class="nav-link active" aria-current="page" href="#">Расписание занятий</a>
    </li>
    <li class="nav-item">
        <a class="nav-link" href="#">Расписание преподавателя</a>
    </li>
    <li class="nav-item">
        <a class="nav-link" href="#">Персональная информация</a>
    </li>
</ul>


<div class="w-100 d-flex align-items-center flex-column vh-100">

    <c:forEach items="${requestScope.lesson_schedule_dates}" var="date">
        <div class="d-flex w-75">
            <p class="fs-2 display-1">${date}</p>
        </div>
        <c:forEach items="${requestScope.lesson_schedule_map.get(date)}" var="lessonSchedule">
            <table class="table mb-5 w-75">
                <thead>
                <tr>
                    <th scope="col">User name</th>
                    <th scope="col">User surname</th>
                    <th scope="col">Course name</th>
                    <th scope="col">Start time</th>
                    <th scope="col">Duration (minutes)</th>
                    <th scope="col">Status</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>${lessonSchedule.studentName}</td>
                    <td>${lessonSchedule.studentSurname}</td>
                    <td>${lessonSchedule.courseName}</td>
                    <td>${lessonSchedule.startDateTime.hour}:${lessonSchedule.startDateTime.minute}</td>
                    <td>${lessonSchedule.duration.minute}</td>
                    <td>${lessonSchedule.status}</td>
                </tr>
                </tbody>
            </table>
        </c:forEach>
    </c:forEach>
</div>

<jsp:include page="../parts/footer.jsp"/>
</body>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
</html>
