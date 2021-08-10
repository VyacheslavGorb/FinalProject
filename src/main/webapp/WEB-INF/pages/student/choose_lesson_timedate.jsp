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

<c:if test="${requestScope.available_dates.size() == 0}">
    <div class="d-flex align-items-center justify-content-center min-vh-100">
        <h1 class="display-3 mt-4">No time is available. Please contact admin.</h1>
    </div>
</c:if>

<c:if test="${requestScope.available_dates.size() != 0}">
    <div class="min-vh-100 d-flex flex-column align-items-center">
        <div class="d-flex justify-content-center w-25 align-items-center flex-column">
            <h1 class="display-3 mt-4">Select date</h1>

            <form class="w-100" id="date_form" method="get" action="/controller">
                <input type="hidden" name="command" value="command_name">
                <input type="hidden" name="course_id" value="1">
                <select name="date" id="date_select" class="form-select mt-4 w-100">
                    <c:forEach items="${requestScope.available_dates}" var="date">
                        <option value="${date}">${date}</option>
                    </c:forEach>
                </select>
            </form>
        </div>

        <table class="table mb-5 w-75 mt-5">
            <thead>
            <tr>
                <th class="" scope="col">Teacher name</th>
                <th scope="col">Teacher surname</th>
                <th scope="col">Avaliable slots</th>
            </tr>
            </thead>

            <tbody>
            <c:forEach items="${requestScope.teachers}" var="teacher">
                <tr>
                    <td class="fs-4">${teacher.name}</td>
                    <td class="fs-4">${teacher.surname}</td>
                    <td class="w-75">
                        <c:forEach items="${requestScope.teachers_schedules.get(teacher)}" var="slot">
                            <a class="btn btn-outline-success m-1" href="#">${slot}</a>
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
