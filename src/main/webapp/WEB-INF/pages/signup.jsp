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
    <link href="style/signup.css" rel="stylesheet">
    <link href="style/footer.css" rel="stylesheet">
    <title>MuzShock - Sign Up</title>
</head>
<body>
<jsp:include page="parts/header.jsp" flush="true"/>

<div class="signup-outer pt-3 pb-3">
    <div class="signup-inner d-flex flex-column p-4">
        <h1 class="fs-3 mb-3"><fmt:message key="signup.message" bundle="${rb}"/></h1>

        <c:if test="${requestScope.is_error != null}">
            <div id="" class="alert alert-danger d-flex align-items-center" role="alert">
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor"
                     class="bi bi-exclamation-triangle-fill flex-shrink-0 me-2" viewBox="0 0 16 16" role="img"
                     aria-label="Warning:">
                    <path
                            d="M8.982 1.566a1.13 1.13 0 0 0-1.96 0L.165 13.233c-.457.778.091 1.767.98 1.767h13.713c.889 0 1.438-.99.98-1.767L8.982 1.566zM8 5c.535 0 .954.462.9.995l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 5.995A.905.905 0 0 1 8 5zm.002 6a1 1 0 1 1 0 2 1 1 0 0 1 0-2z"/>
                </svg>
                <div>
                    <fmt:message key="${requestScope.error_key}" bundle="${rb}"/>
                </div>
            </div>
        </c:if>


        <div id="passwords_mismatch" class="alert alert-danger align-items-center" role="alert">
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor"
                 class="bi bi-exclamation-triangle-fill flex-shrink-0 me-2" viewBox="0 0 16 16" role="img"
                 aria-label="Warning:">
                <path
                        d="M8.982 1.566a1.13 1.13 0 0 0-1.96 0L.165 13.233c-.457.778.091 1.767.98 1.767h13.713c.889 0 1.438-.99.98-1.767L8.982 1.566zM8 5c.535 0 .954.462.9.995l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 5.995A.905.905 0 0 1 8 5zm.002 6a1 1 0 1 1 0 2 1 1 0 0 1 0-2z"/>
            </svg>
            <div>
                <fmt:message key="signup.passwords_mismatch" bundle="${rb}"/>
            </div>
        </div>


        <form class="d-flex flex-column" action="${pageContext.request.contextPath}/controller?command=sign_up"
              method="post">
            <div class="mb-3">
                <label for="role_select" class="form-label"><fmt:message key="signup.role" bundle="${rb}"/></label>
                <select class="form-select" id="role_select" name="user_role">
                    <option selected value="STUDENT"><fmt:message key="signup.role.student" bundle="${rb}"/></option>
                    <option value="TEACHER"><fmt:message key="signup.role.teacher" bundle="${rb}"/></option>
                    <option value="ADMIN"><fmt:message key="signup.role.admin" bundle="${rb}"/></option>
                </select>
            </div>

            <div class="mb-3">
                <label for="login" class="form-label"><fmt:message key="signup.login" bundle="${rb}"/></label>
                <input type="text" class="form-control" id="login" pattern="[A-Za-z][0-9a-zA-Z]{2,19}"
                       title="<fmt:message key="signup.hint.login" bundle="${rb}"/>" name="login" required>
            </div>

            <div class="mb-3">
                <label for="password1" class="form-label"><fmt:message key="signup.password" bundle="${rb}"/></label>
                <input type="password" class="form-control" id="password1" required pattern="[0-9a-zA-Z]{8,20}"
                       title="<fmt:message key="signup.hint.password" bundle="${rb}"/>" name="password">
            </div>
            <div class="mb-3">
                <label for="password2" class="form-label"><fmt:message key="signup.password_repeated"
                                                                       bundle="${rb}"/></label>
                <input type="password" class="form-control" id="password2" required pattern="[0-9a-zA-Z]{8,20}"
                       title="<fmt:message key="signup.hint.password" bundle="${rb}"/>"
                       name="password_repeat">
            </div>
            <div class="mb-3">
                <label for="email" class="form-label"><fmt:message key="signup.email" bundle="${rb}"/></label>
                <input type="email" class="form-control" maxlength="60" id="email" name="email" required>
            </div>
            <div class="mb-3">
                <label for="name" class="form-label"><fmt:message key="signup.name" bundle="${rb}"/></label>
                <input type="text" class="form-control" id="name" required
                       pattern="([ЁА-Я][ёа-я]{1,20})|([A-Z][a-z]{1,20})"
                       name="name"
                       title="<fmt:message key="signup.hint.name" bundle="${rb}"/>">
            </div>
            <div class="mb-3">
                <label for="surname" class="form-label"><fmt:message key="signup.surname" bundle="${rb}"/></label>
                <input type="text" class="form-control" id="surname" required
                       pattern="([ЁА-Я][ёа-я]{1,20})|([A-Z][a-z]{1,20})"
                       name="surname"
                       title="<fmt:message key="signup.hint.name" bundle="${rb}"/>">
            </div>
            <div class="mb-3">
                <label for="patronymic" class="form-label"><fmt:message key="signup.patronymic" bundle="${rb}"/></label>
                <input type="text" class="form-control" id="patronymic" required
                       name="patronymic"
                       pattern="([ЁА-Я][ёа-я]{1,20})|([A-Z][a-z]{1,20})"
                       title="<fmt:message key="signup.hint.name" bundle="${rb}"/>">
            </div>
            <button id="signup_btn" type="submit" class="btn btn-success mt-2 mb-3"><fmt:message key="signup.btn"
                                                                                                 bundle="${rb}"/></button>
            <p class="fs-6"><fmt:message key="signup.have_account" bundle="${rb}"/> <a class="login_link"
                                                                                       href="${pageContext.request.contextPath}/controller?command=go_to_login_page"><fmt:message
                    key="login_page.login_btn" bundle="${rb}"/></a>
            </p>
        </form>
    </div>
</div>

<jsp:include page="parts/footer.jsp"/>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
<script src="script/signup.js"></script>
</body>
</html>
