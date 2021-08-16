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
    <title><fmt:message key="company.name" bundle="${rb}"/> - <fmt:message key="course.manage_teachers"
                                                                           bundle="${rb}"/></title>
</head>
<body>
<jsp:include page="../parts/header.jsp" flush="true"/>

<div class="w-100 d-flex align-items-center flex-column vh-100 p-4">

    <c:if test="${requestScope.on_course_teachers.size() == 0 && requestScope.not_on_course_teachers.size() == 0}">
        <div class="d-flex mt-5 align-items-center flex-column">
            <h1 class="display-1"><fmt:message key="teachers.not_found" bundle="${rb}"/></h1>
        </div>
    </c:if>

    <c:if test="${requestScope.on_course_teachers.size() != 0}">
        <div class="d-flex mt-5 align-items-center flex-column mt-4">
            <h1 class="display-4 fs-2"><fmt:message key="course.manage_teachers" bundle="${rb}"/></h1>
        </div>
        <table class="table mb-5 w-100 mt-4">
            <thead>
            <tr>
                <th scope="col"><fmt:message key="teacher.lesson_schedule.course_name" bundle="${rb}"/></th>
                <th scope="col"><fmt:message key="teacher.lesson_schedule.student_name" bundle="${rb}"/></th>
                <th><fmt:message key="course.add_to_course" bundle="${rb}"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${requestScope.on_course_teachers}" var="teacher">
                <tr>
                    <td>${teacher.name}</td>
                    <td>${teacher.surname}</td>
                    <td>
                        <form method="post" action="${pageContext.request.contextPath}/controller">
                            <input type="hidden" name="command" value="remove_teacher_from_course">
                            <input type="hidden" name="course_id" value="${requestScope.course.id}">
                            <input type="hidden" name="teacher_id" value="${teacher.id}">
                            <button type="submit" class="btn btn-outline-danger"><fmt:message key="course.remove"
                                                                                              bundle="${rb}"/></button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>


    <c:if test="${requestScope.not_on_course_teachers.size() != 0}">
        <div class="d-flex mt-5 align-items-center flex-column mt-4">
            <h1 class="display-4 fs-2"><fmt:message key="page.name.teachers" bundle="${rb}"/></h1>
        </div>
        <table class="table mb-5 w-100 mt-4">
            <thead>
            <tr>
                <th scope="col"><fmt:message key="teacher.name" bundle="${rb}"/></th>
                <th scope="col"><fmt:message key="teacher.surname" bundle="${rb}"/></th>
                <th><fmt:message key="subscription.approve.btn" bundle="${rb}"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${requestScope.not_on_course_teachers}" var="teacher">
                <tr>
                    <td>${teacher.name}</td>
                    <td>${teacher.surname}</td>
                    <td>
                        <form method="post" action="${pageContext.request.contextPath}/controller">
                            <input type="hidden" name="command" value="add_teacher_to_course">
                            <input type="hidden" name="course_id" value="${requestScope.course.id}">
                            <input type="hidden" name="teacher_id" value="${teacher.id}">
                            <button type="submit" class="btn btn-outline-success"><fmt:message key="add"
                                                                                               bundle="${rb}"/></button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
    <a class="btn btn-success"
       href="${pageContext.request.contextPath}/controller?command=all_courses_page"><fmt:message
            key="course.go_to_page" bundle="${rb}"/></a>
</div>

<jsp:include page="../parts/footer.jsp"/>
</body>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
</html>
