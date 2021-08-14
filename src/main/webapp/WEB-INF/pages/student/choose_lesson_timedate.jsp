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
    <title><fmt:message key="company.name" bundle="${rb}"/> - <fmt:message key="page.name.choose_lesson"
                                                                           bundle="${rb}"/></title>
</head>
<body>
<jsp:include page="../parts/header.jsp" flush="true"/>

<c:if test="${requestScope.available_dates.size() == 0}">
    <div class="d-flex align-items-center justify-content-center min-vh-100">
        <h1 class="display-3 mt-4"><fmt:message key="lesson.not_available" bundle="${rb}"/></h1>
    </div>
</c:if>


<c:if test="${sessionScope.is_subscription_time_error != null}">
    <div class="alert alert-danger d-flex align-items-center w-100" role="alert">
        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor"
             class="bi bi-exclamation-triangle-fill flex-shrink-0 me-2" viewBox="0 0 16 16" role="img"
             aria-label="Warning:">
            <path
                    d="M8.982 1.566a1.13 1.13 0 0 0-1.96 0L.165 13.233c-.457.778.091 1.767.98 1.767h13.713c.889 0 1.438-.99.98-1.767L8.982 1.566zM8 5c.535 0 .954.462.9.995l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 5.995A.905.905 0 0 1 8 5zm.002 6a1 1 0 1 1 0 2 1 1 0 0 1 0-2z"/>
        </svg>
        <div>
            <fmt:message key="error.time_not_available" bundle="${rb}"/>
        </div>
    </div>
</c:if>

<c:if test="${requestScope.available_dates.size() != 0}">
    <div class="min-vh-100 d-flex flex-column align-items-center">
        <div class="d-flex justify-content-center w-25 align-items-center flex-column">
            <h1 class="display-3 mt-4"><fmt:message key="lesson.choose_date" bundle="${rb}"/></h1>

            <form class="w-100" id="date_form" method="get" action="${pageContext.request.contextPath}/controller">
                <input type="hidden" name="command" value="choose_lesson_datetime_page">
                <input type="hidden" name="subscription_id" value="${requestScope.subscription.id}">
                <select name="date" id="date_select" class="form-select mt-4 w-100">
                    <c:forEach items="${requestScope.available_dates}" var="date">
                        <c:if test="${requestScope.date.isEqual(date)}">
                            <option selected value="${date}">${date}</option>
                        </c:if>
                        <c:if test="${!requestScope.date.isEqual(date)}">
                            <option value="${date}">${date}</option>
                        </c:if>
                    </c:forEach>
                </select>
            </form>
        </div>

        <table class="table mb-5 w-75 mt-5">
            <thead>
            <tr>
                <th class="" scope="col"><fmt:message key="teacher.schedule.teacher_name" bundle="${rb}"/></th>
                <th scope="col"><fmt:message key="teacher.schedule.teacher_surname" bundle="${rb}"/></th>
                <th scope="col"><fmt:message key="lesson.available_slots" bundle="${rb}"/></th>
            </tr>
            </thead>

            <tbody>
            <c:forEach items="${requestScope.teachers}" var="teacher">
                <tr>
                    <td class="fs-4">${teacher.name}</td>
                    <td class="fs-4">${teacher.surname}</td>
                    <td class="w-75">
                        <c:forEach items="${requestScope.teachers_schedules.get(teacher)}" var="slot">
                            <form style="display: inline;" action="${pageContext.request.contextPath}/controller">
                                <input name="command" value="choose_lesson_datetime" type="hidden">
                                <input type="hidden" name="subscription_id" value="${requestScope.subscription.id}">
                                <input type="hidden" name="teacher_id" value="${teacher.id}">
                                <input type="hidden" name="time" value="${slot}">
                                <input type="hidden" name="date" value="${requestScope.date}">
                                <button type="submit" class="btn btn-outline-success m-1">${slot}</button>
                            </form>
                        </c:forEach>
                    </td>
                </tr>
            </c:forEach>

            </tbody>
        </table>
    </div>
</c:if>

<jsp:include page="../parts/footer.jsp"/>
</body>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
<script src="script/choose_lesson_timedate.js"></script>

</html>
