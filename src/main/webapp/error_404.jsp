<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isErrorPage="true" %>

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
    <title><fmt:message key="company.name" bundle="${rb}"/> - <fmt:message key="page.name.error"
                                                                           bundle="${rb}"/></title>
</head>
<body>
<jsp:include page="WEB-INF/pages/parts/header.jsp" flush="true"/>
<div class="error_page_outer">
    <div class="error_page_inner">
        <h1 class="error_code display-1">404</h1>
        <h2 class="error_message error_message_main display-6"><fmt:message key="error.not_found_main_message"
                                                                            bundle="${rb}"/></h2>
        <p class="error_message fs-5 mt-3">
            <fmt:message key="error.page_not_found_info" bundle="${rb}"/>
            <a class="home_link" href="${pageContext.request.contextPath}/controller?command=home_page"><fmt:message
                    key="error.return_to_home" bundle="${rb}"/></a>
        </p>
    </div>
</div>
<jsp:include page="WEB-INF/pages/parts/footer.jsp"/>
</body>
</html>
