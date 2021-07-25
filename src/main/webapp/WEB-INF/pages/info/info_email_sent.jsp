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
    <link href="style/error.css" rel="stylesheet">
    <link href="style/footer.css" rel="stylesheet">
    <title><fmt:message key="company.name" bundle="${rb}"/> - <fmt:message key="page.name.info" bundle="${rb}"/></title>
</head>
<body>
<jsp:include page="../parts/header.jsp" flush="true"/>
<div class="error_page_outer">
    <div class="error_page_inner">
        <h1 class="error_message error_message_main fs-1"><fmt:message key="info.email_sent"
                                                                       bundle="${rb}"/></h1>
        <p class="error_message fs-4 mt-3">
            <a class="home_link" href="${pageContext.request.contextPath}/controller?command=home_page"><fmt:message
                    key="error.return_to_home"
                    bundle="${rb}"/></a>
        </p>
    </div>
</div>
<jsp:include page="../parts/footer.jsp"/>
</body>
</html>