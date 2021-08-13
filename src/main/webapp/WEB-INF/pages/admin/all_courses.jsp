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
    <title><fmt:message key="company.name" bundle="${rb}"/> - <fmt:message key="admin.tabs.courses"
                                                                           bundle="${rb}"/></title>
</head>
<body>
<jsp:include page="../parts/header.jsp" flush="true"/>

<ul class="nav nav-tabs">
    <li class="nav-item">
        <a class="nav-link" aria-current="page"
           href="${pageContext.request.contextPath}/controller?command=all_lessons_page">
            <fmt:message key="admin.tabs.lessons" bundle="${rb}"/></a>
    </li>
    <li class="nav-item">
        <a class="nav-link"
           href="${pageContext.request.contextPath}/controller?command=all_subscriptions_page">
            <fmt:message key="admin.tabs.subscriptions" bundle="${rb}"/></a>
    </li>
    <li class="nav-item">
        <a class="nav-link"
           href="${pageContext.request.contextPath}/controller?command=manage_users_page">
            <fmt:message key="admin.tabs.users" bundle="${rb}"/></a>
    </li>
    <li class="nav-item">
        <a class="nav-link active"
           href="${pageContext.request.contextPath}/controller?command=all_courses_page">
            <fmt:message key="admin.tabs.courses" bundle="${rb}"/></a>
    </li>
</ul>

<div class="w-100 d-flex align-items-center flex-column vh-100 p-4">

    <div class="d-flex align-items-center justify-content-between w-100 mt-4">
        <h1 class="display-4 fs-1"><fmt:message key="course.all.main" bundle="${rb}"/></h1>
        <a href="${pageContext.request.contextPath}/controller?command=add_course_page" class="btn btn-success"><fmt:message key="course.add" bundle="${rb}"/></a>
    </div>

    <c:if test="${requestScope.courses.size() == 0}">
        <div class="d-flex mt-5 align-items-center flex-column">
            <h1 class="display-1"><fmt:message key="course.not_found" bundle="${rb}"/></h1>
        </div>
    </c:if>

    <c:if test="${requestScope.courses.size() != 0}">
        <table class="table mb-5 w-100">
            <thead>
            <tr>
                <th scope="col"><fmt:message key="course.name"
                                             bundle="${rb}"/></th>
                <th scope="col"><fmt:message key="course.change_status" bundle="${rb}"/></th>
                <th scope="col"><fmt:message key="course.change" bundle="${rb}"/></th>
                <th scope="col"><fmt:message key="course.manage_teachers" bundle="${rb}"/></th>
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
                                <button type="submit" class="btn btn-outline-danger"><fmt:message
                                        key="course.deactivate" bundle="${rb}"/></button>
                            </form>
                        </c:if>
                        <c:if test="${!course.active}">
                            <form method="post" action="${pageContext.request.contextPath}/controller">
                                <input type="hidden" name="command" value="activate_course">
                                <input type="hidden" name="course_id" value="${course.id}">
                                <button type="submit" class="btn btn-outline-success"><fmt:message key="course.activate"
                                                                                                   bundle="${rb}"/></button>
                            </form>
                        </c:if>
                    </td>
                    <td>
                        <a class="btn btn-outline-success"
                           href="${pageContext.request.contextPath}/controller?command=change_course_page&course_id=${course.id}"><fmt:message
                                key="course.edit"
                                bundle="${rb}"/></a>
                    </td>
                    <td>
                        <a class="btn btn-outline-success"
                           href="${pageContext.request.contextPath}/controller?command=manage_teachers_course_page&course_id=${course.id}"><fmt:message
                                key="course.manage"
                                bundle="${rb}"/></a>
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
