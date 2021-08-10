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
    <title><fmt:message key="company.name" bundle="${rb}"/> - <fmt:message key="page.name.teacher"
                                                                           bundle="${rb}"/></title>
</head>
<body>
<jsp:include page="../parts/header.jsp" flush="true"/>

<ul class="nav nav-tabs mb-5">
    <li class="nav-item">
        <a class="nav-link" aria-current="page" href="#">Расписание занятий</a>
    </li>
    <li class="nav-item">
        <a class="nav-link active" href="#">Расписание преподавателя</a>
    </li>
</ul>

<c:if test="${requestScope.subscriptions.size() == 0}">
    <div class="min-vh-100 d-flex align-items-center justify-content-center">
        <p class="display-3">Ничего не найдено</p>
    </div>
</c:if>

<c:forEach items="${requestScope.subscriptions}" var="subscription">
    <div class="min-vh-100">
        <div class="d-flex justify-content-center flex-column align-items-center card m-5 bg-light">
            <div class="d-flex w-100 mt-4 flex-column justify-content-between align-items-center">
                <h1 class="display-5">Course name</h1>
                <div class="d-flex justify-content-around w-75 mt-4">
                    <p class="display-1 fs-4">Start date: ${subscription.startDate}</p>
                    <p class="display-1 fs-4">End date: ${subscription.endDate}</p>
                    <p class="display-1 fs-4">Lesson amount: ${subscription.lessonCount}</p>
                    <p class="display-1 fs-4">Status: ${subscription.status}</p>
                </div>
            </div>

            <c:if test="${subscription.status.name() == 'WAITING_FOR_APPROVE' || subscription.status.name() == 'REJECTED'}">
                <p class="mt-0 mb-0 fs-4">
                    No
                </p>
            </c:if>


            <c:if test="${subscription.status.name() == 'APPROVED' || subscription.status.name() == 'ACTIVATED'}">
                <table class="table mb-5 w-75 mt-3">
                    <thead>
                    <tr>
                        <th scope="col">Teacher name</th>
                        <th scope="col">Teacher surname</th>
                        <th scope="col">Start time</th>
                        <th scope="col">Duration (minutes)</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${requestScope.subscription_schedules.get(subscription)}" var="lessonSchedule">
                        <tr>
                            <td>${lessonSchedule.studentName}</td>
                            <td>${lessonSchedule.studentSurname}</td>
                            <td>${lessonSchedule.courseName}</td>
                            <td>${lessonSchedule.startDateTime.hour}:${lessonSchedule.startDateTime.minute}</td>
                            <td>${lessonSchedule.duration.minute}</td>
                        </tr>
                    </c:forEach>

                    </tbody>
                </table>
            </c:if>

            <c:if test="${subscription.status.name() == 'APPROVED'}">
                <div class="d-flex w-50 justify-content-around mt-2 mb-4">
                    <p class="mt-0 mb-0 fs-4">
                        Select ${subscription.lessonCount} lessons
                    </p>
                    <a class="btn btn-success fs-5">Add lesson</a>
                </div>
            </c:if>


        </div>
    </div>
</c:forEach>

<jsp:include page="../parts/footer.jsp"/>
</body>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
</html>
