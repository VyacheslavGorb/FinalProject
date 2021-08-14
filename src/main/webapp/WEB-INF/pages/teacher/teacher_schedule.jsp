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
    <title><fmt:message key="company.name" bundle="${rb}"/> - <fmt:message key="teacher.tabs.teacher_schedule"
                                                                           bundle="${rb}"/></title>
</head>
<body>
<jsp:include page="../parts/header.jsp" flush="true"/>

<ul class="nav nav-tabs mb-5">
    <li class="nav-item">
        <a class="nav-link" aria-current="page"
           href="${pageContext.request.contextPath}/controller?command=teacher_lesson_schedule"><fmt:message
                key="teacher.tabs.lesson_schedule"
                bundle="${rb}"/></a>
    </li>
    <li class="nav-item">
        <a class="nav-link active"
           href="${pageContext.request.contextPath}/controller?command=teacher_schedule"><fmt:message
                key="teacher.tabs.teacher_schedule"
                bundle="${rb}"/></a>
    </li>
    <li class="nav-item">
        <a class="nav-link"
           href="${pageContext.request.contextPath}/controller?command=teacher_personal_info"><fmt:message
                key="teacher.tabs.personal_info"
                bundle="${rb}"/></a>
    </li>
</ul>


<div class="min-vh-100 d-flex flex-column justify-content-around">

    <div class="w-100 d-flex justify-content-center align-items-center flex-column">
        <div class="d-flex w-75 align-items-center justify-content-center mb-4">
            <h1 class="display-4"><fmt:message key="teacher.schedule.main" bundle="${rb}"/></h1>
        </div>
        <table class="table mb-5 w-75">
            <thead>
            <tr>
                <th scope="col"><fmt:message key="teacher.schedule.day" bundle="${rb}"/></th>
                <th scope="col"><fmt:message key="teacher.schedule.start_time" bundle="${rb}"/></th>
                <th scope="col"><fmt:message key="teacher.schedule.end_time" bundle="${rb}"/></th>
            </tr>
            </thead>
            <tbody>
            <c:if test="${requestScope.teacher_schedules.size() == 0}">
                <tr>
                    <td colspan="3"><fmt:message key="teacher.schedule.not_found" bundle="${rb}"/></td>
                </tr>
            </c:if>
            <c:forEach items="${requestScope.teacher_schedules}" var="schedule">
                <tr>
                    <td><fmt:message key="day_of_week.${schedule.dayOfWeek}" bundle="${rb}"/></td>
                    <td>${schedule.startTime}</td>
                    <td>${schedule.endTime}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>


    <div class="d-flex flex-column align-items-center justify-content-center">
        <h1 class="display-4 fs-1"><fmt:message key="teacher.schedule.alter" bundle="${rb}"/></h1>

        <div id="illegal-datetime" class="alert alert-danger align-items-center w-50" role="alert">
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor"
                 class="bi bi-exclamation-triangle-fill flex-shrink-0 me-2" viewBox="0 0 16 16" role="img"
                 aria-label="Warning:">
                <path
                        d="M8.982 1.566a1.13 1.13 0 0 0-1.96 0L.165 13.233c-.457.778.091 1.767.98 1.767h13.713c.889 0 1.438-.99.98-1.767L8.982 1.566zM8 5c.535 0 .954.462.9.995l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 5.995A.905.905 0 0 1 8 5zm.002 6a1 1 0 1 1 0 2 1 1 0 0 1 0-2z"/>
            </svg>
            <div>
                <fmt:message key="error.invalid_time" bundle="${rb}"/>
            </div>
        </div>

        <c:if test="${sessionScope.is_teacher_schedule_error != null}">
            <div class="alert alert-danger d-flex align-items-center w-50" role="alert">
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor"
                     class="bi bi-exclamation-triangle-fill flex-shrink-0 me-2" viewBox="0 0 16 16" role="img"
                     aria-label="Warning:">
                    <path
                            d="M8.982 1.566a1.13 1.13 0 0 0-1.96 0L.165 13.233c-.457.778.091 1.767.98 1.767h13.713c.889 0 1.438-.99.98-1.767L8.982 1.566zM8 5c.535 0 .954.462.9.995l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 5.995A.905.905 0 0 1 8 5zm.002 6a1 1 0 1 1 0 2 1 1 0 0 1 0-2z"/>
                </svg>
                <div>
                    <fmt:message key="error.invalid_request" bundle="${rb}"/>
                </div>
            </div>
        </c:if>

        <form method="post" class="d-flex w-50 flex-column"
              action="${pageContext.request.contextPath}/controller?command=alter_teacher_schedule">
            <label class="form-label mt-4" for="day-select"><fmt:message key="teacher.schedule.day"
                                                                         bundle="${rb}"/></label>
            <select name="day_of_week" id="day-select" class="form-select" required>
                <option value="1"><fmt:message key="day_of_week.1" bundle="${rb}"/></option>
                <option value="2"><fmt:message key="day_of_week.2" bundle="${rb}"/></option>
                <option value="3"><fmt:message key="day_of_week.3" bundle="${rb}"/></option>
                <option value="4"><fmt:message key="day_of_week.4" bundle="${rb}"/></option>
                <option value="5"><fmt:message key="day_of_week.5" bundle="${rb}"/></option>
                <option value="6"><fmt:message key="day_of_week.6" bundle="${rb}"/></option>
                <option value="7"><fmt:message key="day_of_week.7" bundle="${rb}"/></option>
            </select>
            <div class="d-flex align-items-center  mt-4">
                <label class="form-label mb-0" for="time-start-input"><fmt:message key="teacher.schedule.remove"
                                                                                   bundle="${rb}"/> &nbsp;</label>
                <input name="remove" id="remove-checkbox" class="form-check-input" type="checkbox" value="1">
            </div>
            <label class="form-label mt-4" for="time-start-input"><fmt:message key="teacher.schedule.start_time"
                                                                               bundle="${rb}"/>
                &nbsp;<fmt:message key="teacher.schedule.hours"
                                   bundle="${rb}"/></label>
            <input name=start_time id="time-start-input" class="form-control" min="0" max="23" type="number"
                   required>
            <label class="form-label mt-4" for="time-start-input"><fmt:message key="teacher.schedule.end_time"
                                                                               bundle="${rb}"/>
                &nbsp;<fmt:message key="teacher.schedule.hours"
                                   bundle="${rb}"/></label>
            <input name="end_time" id="time-end-input" class="form-control" min="0" max="23" type="number"
                   required>
            <button id="submit-btn" class="w-25 btn btn-success mt-4 mb-4" type="submit"><fmt:message
                    key="teacher.schedule.btn" bundle="${rb}"/></button>
        </form>
    </div>
</div>


<jsp:include page="../parts/footer.jsp"/>
</body>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
<script src="script/teacher_schedule.js"></script>
</html>
