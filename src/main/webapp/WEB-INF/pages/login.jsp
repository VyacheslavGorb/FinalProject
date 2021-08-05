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
    <link href="style/error.css" rel="stylesheet">
    <link href="style/form_page.css" rel="stylesheet">
    <link href="style/footer.css" rel="stylesheet">
    <title><fmt:message key="company.name" bundle="${rb}"/> - <fmt:message key="page.name.login"
                                                                           bundle="${rb}"/></title>
</head>
<body>
<jsp:include page="parts/header.jsp" flush="true"/>
<div class="login-outer ">
    <div class="login-inner d-flex flex-column p-4">
        <h1 class="fs-3 mb-3"><fmt:message key="login_page.message" bundle="${rb}"/></h1>


        <c:if test="${sessionScope.is_login_error != null}">
            <div class="alert alert-danger d-flex align-items-center" role="alert">
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor"
                     class="bi bi-exclamation-triangle-fill flex-shrink-0 me-2" viewBox="0 0 16 16" role="img"
                     aria-label="Warning:">
                    <path d="M8.982 1.566a1.13 1.13 0 0 0-1.96 0L.165 13.233c-.457.778.091 1.767.98 1.767h13.713c.889 0 1.438-.99.98-1.767L8.982 1.566zM8 5c.535 0 .954.462.9.995l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 5.995A.905.905 0 0 1 8 5zm.002 6a1 1 0 1 1 0 2 1 1 0 0 1 0-2z"/>
                </svg>
                <div>
                    <fmt:message key="${sessionScope.error_key}" bundle="${rb}"/>
                </div>
            </div>
        </c:if>

        <c:if test="${sessionScope.email_not_confirmed != null}">
            <div class="alert alert-danger d-flex align-items-center" role="alert">
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor"
                     class="bi bi-exclamation-triangle-fill flex-shrink-0 me-2" viewBox="0 0 16 16" role="img"
                     aria-label="Warning:">
                    <path d="M8.982 1.566a1.13 1.13 0 0 0-1.96 0L.165 13.233c-.457.778.091 1.767.98 1.767h13.713c.889 0 1.438-.99.98-1.767L8.982 1.566zM8 5c.535 0 .954.462.9.995l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 5.995A.905.905 0 0 1 8 5zm.002 6a1 1 0 1 1 0 2 1 1 0 0 1 0-2z"/>
                </svg>
                <div>
                    <fmt:message key="login_page.email_error" bundle="${rb}"/>
                    <br>
                    <a class="send_link"
                       href="${pageContext.request.contextPath}/controller?command=go_to_send_email_again_page">
                        <fmt:message key="error.email_not_confirmed.send_again" bundle="${rb}"/></a>
                </div>
            </div>
        </c:if>

        <form class="d-flex flex-column" action="${pageContext.request.contextPath}/controller?command=login"
              method="post">
            <div class="mb-3">
                <label for="login" class="form-label"><fmt:message key="login_page.login" bundle="${rb}"/></label>
                <input type="text" class="form-control" id="login" name="login" pattern="[A-Za-z][0-9a-zA-Z]{2,19}"
                       title="<fmt:message key="login_page.login_hint" bundle='${rb}'/>" required>
            </div>
            <div class="mb-3">
                <label for="password" class="form-label"><fmt:message key="login_page.password" bundle='${rb}'/></label>
                <input type="password" class="form-control" id="password" name="password" required
                       pattern="[0-9a-zA-Z]{8,20}"
                       title="<fmt:message key="login_page.password_hint" bundle='${rb}'/>">
            </div>
            <button type="submit" class="btn btn-success mt-2"><fmt:message key="login_page.login_btn"
                                                                            bundle='${rb}'/></button>
            <a href="${pageContext.request.contextPath}/controller?command=go_to_sign_up_page" type="button"
               class="btn btn-outline-secondary mt-3"><fmt:message key="login_page.signup_btn"
                                                                   bundle='${rb}'/></a>
        </form>
    </div>
</div>
<jsp:include page="parts/footer.jsp"/>
</body>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
</html>
