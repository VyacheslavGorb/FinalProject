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
    <link href="style/login.css" rel="stylesheet">
    <link href="style/footer.css" rel="stylesheet">
    <title>MuzShock - Home</title>
</head>
<body>
<jsp:include page="parts/header.jsp" flush="true"/>
<div class="login-outer ">
    <div class="login-inner d-flex flex-column p-4">
        <h1 class="fs-3 mb-3"><fmt:message key="send_again_page.message" bundle="${rb}"/></h1>
        <form class="d-flex flex-column" action="${pageContext.request.contextPath}/controller?command=send_email_again"
              method="post">
            <div class="mb-3">
                <label for="login" class="form-label"><fmt:message key="login_page.login" bundle="${rb}"/></label>
                <input type="text" class="form-control" id="login" name="login" pattern="[A-Za-z][0-9a-zA-Z]{2,19}"
                       title="<fmt:message key="login_page.login_hint" bundle='${rb}'/>" required>
            </div>

            <button type="submit" class="btn btn-success mt-2"><fmt:message key="send_again_page.btn"
                                                                            bundle='${rb}'/></button>
        </form>
    </div>
</div>
<jsp:include page="parts/footer.jsp"/>
</body>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
</html>
