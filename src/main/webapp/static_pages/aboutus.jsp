<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
    <link href="../style/aboutus.css" rel="stylesheet">
    <link href="../style/footer.css" rel="stylesheet">
    <title><fmt:message key="company.name" bundle="${rb}"/> - <fmt:message key="page.name.error"
                                                                           bundle="${rb}"/></title>
</head>
<body>
<jsp:include page="../WEB-INF/pages/parts/header.jsp" flush="true"/>
<div>
    <div class="d-flex w-100 align-items-center justify-content-center flex-column">
        <h1 class="display-3 text-center mt-4 mb-4 w-75"><fmt:message key="aboutus.main" bundle="${rb}"/></h1>
        <img class="about_img" alt="welcome" src="../pic/slide1.jpg">
        <p class="display-4 mt-4"><fmt:message key="aboutus.message" bundle="${rb}"/></p>
        <div class="d-flex w-100 justify-content-around mb-4 mt-4">
            <div class="w-25">
                <h3 class="fs-2"><fmt:message key="aboutus.1.header" bundle="${rb}"/></h3>
                <p class="text-wrap fs-5"><fmt:message key="aboutus.1.content" bundle="${rb}"/></p>
            </div>
            <div class="w-25">
                <h3 class="fs-2"><fmt:message key="aboutus.2.header" bundle="${rb}"/></h3>
                <p class="text-wrap fs-5"><fmt:message key="aboutus.2.content" bundle="${rb}"/></p>
            </div>
            <div class="w-25">
                <h3 class="fs-2"><fmt:message key="aboutus.3.header" bundle="${rb}"/></h3>
                <p class="text-wrap fs-5"><fmt:message key="aboutus.3.content" bundle="${rb}"/></p>
            </div>
        </div>

        <a href="${pageContext.request.contextPath}/controller?command=home_page"
           class="btn btn-outline-success w-25 mb-4 display-1 fs-4">На главную</a>
    </div>
</div>
<jsp:include page="../WEB-INF/pages/parts/footer.jsp"/>
</body>
</html>