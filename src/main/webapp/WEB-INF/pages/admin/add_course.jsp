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
    <title><fmt:message key="company.name" bundle="${rb}"/> - <fmt:message key="page.name.add_course"
                                                                           bundle="${rb}"/></title>
</head>
<body>
<jsp:include page="../parts/header.jsp" flush="true"/>

<div class="w-100 d-flex flex-column align-items-center min-vh-100">
    <p class="display-4 fs-1 mt-4"><fmt:message key="course.add_course.main" bundle="${rb}"/></p>

    <c:if test="${sessionScope.is_add_course_error != null}">
        <div class="alert alert-danger d-flex align-items-center w-75" role="alert">
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


    <div id="illegal-file-type" class="alert alert-danger align-items-center w-75" role="alert">
        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor"
             class="bi bi-exclamation-triangle-fill flex-shrink-0 me-2" viewBox="0 0 16 16" role="img"
             aria-label="Warning:">
            <path
                    d="M8.982 1.566a1.13 1.13 0 0 0-1.96 0L.165 13.233c-.457.778.091 1.767.98 1.767h13.713c.889 0 1.438-.99.98-1.767L8.982 1.566zM8 5c.535 0 .954.462.9.995l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 5.995A.905.905 0 0 1 8 5zm.002 6a1 1 0 1 1 0 2 1 1 0 0 1 0-2z"/>
        </svg>
        <div>
            <fmt:message key="error.image_type" bundle="${rb}"/>
        </div>
    </div>

    <form id="teacher-init-form" method="post" class="w-75" action="${pageContext.request.contextPath}/controller"
          enctype='multipart/form-data'>
        <input type="hidden" name="command" value="add_course">
        <label class="form-label mt-4"><fmt:message key="course.label.name" bundle="${rb}"/></label>
        <input name="course_name" class="form-control" maxlength="40" required>

        <label class="form-label mt-4"><fmt:message key="course.label.description" bundle="${rb}"/></label>
        <textarea name="description" class="form-control" rows="5" maxlength="1000" required></textarea>

        <label class="form-label mt-4"><fmt:message key="course.label.price" bundle="${rb}"/></label>
        <input name="price" class="form-control" type="number" step="0.01" min="0" max="9999" required>

        <label class="form-label mt-4"><fmt:message key="course.label.image" bundle="${rb}"/></label>
        <input id="fileInput" name="image" type="file" class="form-control" accept="image/jpeg" required>

        <button id="submit-btn" type="submit" class="btn btn-success mt-4 mb-4 w-25"><fmt:message
                key="teacher_init.btn" bundle="${rb}"/></button>
    </form>
</div>

<jsp:include page="../parts/footer.jsp"/>
</body>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
<script src="script/add_course_page.js"></script>
</html>
