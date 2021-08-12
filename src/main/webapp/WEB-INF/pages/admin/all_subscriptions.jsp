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

    <c:if test="${requestScope.active_subscriptions.size() == 0                   && requestScope.waiting_for_approve_subscriptions.size() == 0}">
        <div class="d-flex mt-5 align-items-center flex-column">
            <h1 class="display-1"><fmt:message key="student.subscriptions.no_found" bundle="${rb}"/></h1>
        </div>
    </c:if>

    <c:if test="${requestScope.waiting_for_approve_subscriptions.size() != 0}">
        <div class="d-flex mt-5 align-items-center flex-column mt-4">
            <h1 class="display-4 fs-2"><fmt:message key="student.subscriptions.no_found" bundle="${rb}"/></h1>
        </div>
        <table class="table mb-5 w-100 mt-4">
            <thead>
            <tr>
                <th scope="col"><fmt:message key="teacher.lesson_schedule.course_name" bundle="${rb}"/></th>
                <th scope="col"><fmt:message key="teacher.lesson_schedule.student_name" bundle="${rb}"/></th>
                <th scope="col"><fmt:message key="teacher.lesson_schedule.student_surname" bundle="${rb}"/></th>
                <th scope="col"><fmt:message key="subscription.start_date" bundle="${rb}"/></th>
                <th scope="col"><fmt:message key="subscription.end_date" bundle="${rb}"/></th>
                <th scope="col"><fmt:message key="subscription.lesson_amount" bundle="${rb}"/></th>
                <th scope="col"><fmt:message key="subscription.status" bundle="${rb}"/></th>
                <th scope="col"><fmt:message key="subscription.status" bundle="${rb}"/></th>
                <th>Approve</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${requestScope.waiting_for_approve_subscriptions}" var="subscription">
                <tr>
                    <td>${subscription.courseName}</td>
                    <td>${subscription.studentName}</td>
                    <td>${subscription.studentSurname}</td>
                    <td>${subscription.startDate}</td>
                    <td>${subscription.endDate}</td>
                    <td>${subscription.lessonCount}</td>
                    <td>${requestScope.total_lesson_count.get(subscription)}</td>
                    <td>${requestScope.for_sure_lesson_count.get(subscription)}</td>
                    <td>
                        <form method="post" action="${pageContext.request.contextPath}/controller">
                            <input type="hidden" name="command" value="admin_approve_subscription">
                            <input type="hidden" name="subscription_id" value="${subscription.subscriptionId}">
                            <button type="submit" class="btn btn-outline-success">Approve</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>


    <c:if test="${requestScope.active_subscriptions.size() != 0}">
        <div class="d-flex mt-5 align-items-center flex-column mt-4">
            <h1 class="display-4 fs-2"><fmt:message key="student.subscriptions.no_found" bundle="${rb}"/></h1>
        </div>
        <table class="table mb-5 w-100 mt-4">
            <thead>
            <tr>
                <th scope="col"><fmt:message key="teacher.lesson_schedule.course_name" bundle="${rb}"/></th>
                <th scope="col"><fmt:message key="teacher.lesson_schedule.student_name" bundle="${rb}"/></th>
                <th scope="col"><fmt:message key="teacher.lesson_schedule.student_surname" bundle="${rb}"/></th>
                <th scope="col"><fmt:message key="subscription.start_date" bundle="${rb}"/></th>
                <th scope="col"><fmt:message key="subscription.end_date" bundle="${rb}"/></th>
                <th scope="col"><fmt:message key="subscription.lesson_amount" bundle="${rb}"/></th>
                <th scope="col"><fmt:message key="subscription.status" bundle="${rb}"/></th>
                <th scope="col"><fmt:message key="lesson.cancel" bundle="${rb}"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${requestScope.active_subscriptions}" var="subscription">
                <tr>
                    <td>${subscription.courseName}</td>
                    <td>${subscription.studentName}</td>
                    <td>${subscription.studentSurname}</td>
                    <td>${subscription.startDate}</td>
                    <td>${subscription.endDate}</td>
                    <td>${subscription.lessonCount}</td>
                    <td>${subscription.status}</td>
                    <td>
                        <form method="post" action="${pageContext.request.contextPath}/controller">
                            <input type="hidden" name="command" value="admin_cancel_subscription">
                            <input type="hidden" name="subscription_id" value="${subscription.subscriptionId}">
                            <button type="submit" class="btn btn-outline-danger">Cancel</button>
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
