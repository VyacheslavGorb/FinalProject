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
    <title><fmt:message key="company.name" bundle="${rb}"/> - <fmt:message key="teacher.tabs.lesson_schedule"
                                                                           bundle="${rb}"/></title>
</head>
<body>
<jsp:include page="../parts/header.jsp" flush="true"/>

<div class="w-100 d-flex align-items-center flex-column vh-100 p-4">

    <c:if test="${requestScope.courses.size() == 0}">
        <div class="d-flex mt-5 align-items-center flex-column">
            <h1 class="display-1"><fmt:message key="teacher.lesson_schedule.not_found" bundle="${rb}"/></h1>
        </div>
    </c:if>

    <c:if test="${requestScope.courses.size() != 0}">
        <div class="d-flex align-items-center justify-content-between w-100 mt-4">
            <h1 class="display-4 fs-1"><fmt:message key="student.subscriptions.no_found" bundle="${rb}"/></h1>
            <a href="${pageContext.request.contextPath}/controller?command=add_course_page" class="btn btn-success">Add
                course</a>
        </div>
        <table class="table mb-5 w-100">
            <thead>
            <tr>
                <th class="w-75" scope="col"><fmt:message key="teacher.lesson_schedule.student_name"
                                                          bundle="${rb}"/></th>
                <th scope="col"><fmt:message key="teacher.lesson_schedule.student_surname" bundle="${rb}"/></th>
                <th scope="col"><fmt:message key="teacher.schedule.teacher_name" bundle="${rb}"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${requestScope.courses}" var="course">
                <tr>
                    <td>${course.name}</td>
                    <td>
                        <c:if test="${course.active}">
                            <form method="post" action="${pageContext.request.contextPath}/controller">
                                <input type="hidden" name="command" value="deactivate_course">
                                <input type="hidden" name="course_id" value="${course.id}">
                                <button type="submit" class="btn btn-outline-danger">Deactivate</button>
                            </form>
                        </c:if>
                        <c:if test="${!course.active}">
                            <form method="post" action="${pageContext.request.contextPath}/controller">
                                <input type="hidden" name="command" value="activate_course">
                                <input type="hidden" name="course_id" value="${course.id}">
                                <button type="submit" class="btn btn-outline-success">Activate</button>
                            </form>
                        </c:if>
                    </td>
                    <td>
                        <form method="post" action="${pageContext.request.contextPath}/controller">
                            <input type="hidden" name="command" value="">
                            <input type="hidden" name="course" value="${course.id}">
                            <button type="submit" class="btn btn-outline-success">Manage</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
</div>

<jsp:include page="../parts/footer.jsp"/>
</body>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
</html>
