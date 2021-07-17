<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale == null ? 'ru_RU' : sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent" var="rb"/>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link href="style/error.css" rel="stylesheet">
    <link href="style/footer.css" rel="stylesheet">
    <title>MuzShock - Home</title>
</head>
<body>
<jsp:include page="../parts/header.jsp" flush="true"/>
<div class="error_page_outer">
    <div class="error_page_inner">
        <h1 class="error_code display-1">404</h1>
        <h2 class="error_message error_message_main display-6">Oops! Page not found.</h2>
        <p class="error_message fs-5 mt-3">
            The page you are looking for might have been removed had its name changed or is temporarily unavailable.
            <a class="home_link" href="${pageContext.request.contextPath}/controller?command=home_page">Return to homepage</a>
        </p>
    </div>
</div>
<jsp:include page="../parts/footer.jsp"/>
</body>
</html>
