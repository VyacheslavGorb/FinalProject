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
    <title><fmt:message key="company.name" bundle="${rb}"/> - <fmt:message key="student.tabs.personal_subscriptions"
                                                                           bundle="${rb}"/></title>
</head>
<body>
<jsp:include page="../parts/header.jsp" flush="true"/>

<ul class="nav nav-tabs mb-5">
    <li class="nav-item">
        <a class="nav-link" aria-current="page"
           href="${pageContext.request.contextPath}/controller?command=student_lesson_schedule">
            <fmt:message key="student.tabs.lesson_schedule" bundle="${rb}"/></a>
    </li>
    <li class="nav-item">
        <a class="nav-link active"
           href="${pageContext.request.contextPath}/controller?command=personal_subscriptions">
            <fmt:message key="student.tabs.personal_subscriptions" bundle="${rb}"/></a>
    </li>
</ul>

<c:if test="${requestScope.subscriptions.size() == 0}">
    <div class="min-vh-100 d-flex align-items-center justify-content-center">
        <p class="display-3"><fmt:message key="student.subscriptions.no_found" bundle="${rb}"/></p>
    </div>
</c:if>

<div class="min-vh-100">
    <c:forEach items="${requestScope.subscriptions}" var="subscription">
        <div class="d-flex justify-content-center flex-column align-items-center card m-5 bg-light">
            <div class="d-flex w-100 mt-4 flex-column justify-content-between align-items-center">
                <h1 class="display-5">${subscription.courseId}</h1>
                <div class="d-flex justify-content-around w-75 mt-4">
                    <p class="display-1 fs-4"><fmt:message key="subscription.start_date"
                                                           bundle="${rb}"/>: ${subscription.startDate}</p>
                    <p class="display-1 fs-4"><fmt:message key="subscription.end_date"
                                                           bundle="${rb}"/>: ${subscription.endDate}</p>
                    <p class="display-1 fs-4"><fmt:message key="subscription.lesson_amount"
                                                           bundle="${rb}"/>: ${subscription.lessonCount}</p>
                    <p class="display-1 fs-4"><fmt:message key="subscription.status" bundle="${rb}"/>: <fmt:message
                            key="subscription.status.${subscription.status}" bundle="${rb}"/></p>
                </div>
            </div>

            <c:if test="${subscription.status.name() == 'WAITING_FOR_APPROVE' }">
                <p class="mt-0 mb-0 fs-4">
                    <fmt:message key="subscription.waiting.message" bundle="${rb}"/>
                </p>
            </c:if>

            <c:if test="${subscription.status.name() == 'REJECTED'}">
                <p class="mt-0 mb-0 fs-4">
                    <fmt:message key="subscription.rejected.message" bundle="${rb}"/>
                </p>
            </c:if>


            <c:if test="${subscription.status.name() == 'APPROVED' || subscription.status.name() == 'ACTIVATED'}">
                <table class="table mb-5 w-75 mt-3">
                    <thead>
                    <tr>
                        <th scope="col"><fmt:message key="teacher.schedule.teacher_name" bundle="${rb}"/></th>
                        <th scope="col"><fmt:message key="teacher.schedule.teacher_surname" bundle="${rb}"/></th>
                        <th scope="col"><fmt:message key="teacher.schedule.start_time" bundle="${rb}"/></th>
                        <th scope="col"><fmt:message key="teacher.lesson_schedule.duration" bundle="${rb}"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${requestScope.subscription_schedules.get(subscription)}" var="lessonSchedule">
                        <tr>
                            <td>${lessonSchedule.teacherName}</td>
                            <td>${lessonSchedule.teacherSurname}</td>
                            <td>${lessonSchedule.startDateTime.toLocalTime()}</td>
                            <td>${lessonSchedule.duration.minute}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:if>

            <c:if test="${subscription.status.name() == 'APPROVED'}">
                <div class="d-flex w-50 justify-content-around mt-2 mb-4">
                    <p class="mt-0 mb-0 fs-4">
                        <fmt:message key="subscription.select_lessons" bundle="${rb}"/>: ${subscription.lessonCount}
                    </p>
                    <a class="btn btn-success fs-5"><fmt:message key="subscription.add_lesson" bundle="${rb}"/></a>
                </div>
            </c:if>

        </div>
    </c:forEach>
</div>

<jsp:include page="../parts/footer.jsp"/>
</body>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
</html>
