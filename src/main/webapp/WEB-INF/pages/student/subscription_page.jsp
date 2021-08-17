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
    <title><fmt:message key="company.name" bundle="${rb}"/> - <fmt:message key="page.name.subscription"
                                                                           bundle="${rb}"/></title>
</head>
<body>
<jsp:include page="../parts/header.jsp" flush="true"/>

<div class="d-flex justify-content-center mt-4 min-vh-100">

    <c:if test="${requestScope.max_available_lesson_count == 0}">
        <div class="d-flex align-items-center justify-content-center flex-column">
            <h1 class="display-3 text-center"><fmt:message key="subscription.not_found_later" bundle="${rb}"/></h1>
            <a class="mt-4 display-3 fs-3 link"
               href="${pageContext.request.contextPath}/controller?command=courses&page=1"><fmt:message
                    key="subscription.go_to_courses" bundle="${rb}"/></a>
        </div>
    </c:if>


    <c:if test="${requestScope.max_available_lesson_count != 0}">
        <div class="w-50">
            <div>
                <h1 class="display-3">Select lesson amount for month</h1>
            </div>
            <c:if test="${sessionScope.is_subscription_error != null}">
                <div class="alert alert-danger d-flex align-items-center w-100" role="alert">
                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor"
                         class="bi bi-exclamation-triangle-fill flex-shrink-0 me-2" viewBox="0 0 16 16" role="img"
                         aria-label="Warning:">
                        <path
                                d="M8.982 1.566a1.13 1.13 0 0 0-1.96 0L.165 13.233c-.457.778.091 1.767.98 1.767h13.713c.889 0 1.438-.99.98-1.767L8.982 1.566zM8 5c.535 0 .954.462.9.995l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 5.995A.905.905 0 0 1 8 5zm.002 6a1 1 0 1 1 0 2 1 1 0 0 1 0-2z"/>
                    </svg>
                    <div>
                        <fmt:message key="error.subscription_not_available" bundle="${rb}"/>
                    </div>
                </div>
            </c:if>
            <div>
                <form method="post" class="mt-4"
                      action="${pageContext.request.contextPath}/controller">
                    <input type="hidden" name="command" value="send_subscription_request">
                    <input type="hidden" name="course_id" value="${requestScope.course}">
                    <label class="fs-5 form-label">Lesson amount (max
                        available: ${requestScope.max_available_lesson_count})</label>
                    <input name="lesson_count" class="form-control" type="number" min="1"
                           max=${requestScope.max_available_lesson_count}>
                    <button class="btn btn-success w-25 mt-4" type="submit"><fmt:message key="subscription.select"
                                                                                         bundle="${rb}"/></button>
                </form>
            </div>
        </div>
    </c:if>
</div>

<jsp:include page="../parts/footer.jsp"/>
</body>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
</html>
