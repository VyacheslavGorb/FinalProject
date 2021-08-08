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
    <link href="style/teacher_page.css" rel="stylesheet">
    <link href="style/footer.css" rel="stylesheet">
    <title><fmt:message key="company.name" bundle="${rb}"/> - <fmt:message key="page.name.teacher"
                                                                           bundle="${rb}"/></title>
</head>
<body>
<jsp:include page="parts/header.jsp" flush="true"/>

<div class="w-100 d-flex align-items-center justify-content-center">
    <div class="d-flex flex-column  align-items-center w-75 vh-100">
        <div class="d-flex align-items-center justify-content-between w-100">
            <h1 class="display-3 mt-4">${requestScope.teacher.name} ${requestScope.teacher.surname}</h1>
            <span class="display-1 fs-2 mt-4"><fmt:message key="teacher.work_experience"
                                                           bundle="${rb}"/> ${requestScope.teacher.experienceYears}</span>
        </div>
        <div class="w-100 d-flex">
            <img class="course_page_image w-25 mt-4 mb-4" src="${requestScope.teacher.picturePath}">
            <p class="m-4 fs-4 text-start">${requestScope.teacher.selfDescription}</p>
        </div>
    </div>
</div>

<%--<script src="https://cdn.jsdelivr.net/npm/marked/marked.min.js"></script>TODO--%>
<%--<script>--%>
<%--    document.getElementById('content').innerHTML =--%>
<%--        marked('${requestScope.teacher.selfDescription}');--%>
<%--</script>--%>

<jsp:include page="parts/footer.jsp"/>
</body>
</html>