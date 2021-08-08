<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="edu.gorb.musicstudio.entity.UserRole" %>
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
    <link href="style/course_page.css" rel="stylesheet">
    <link href="style/footer.css" rel="stylesheet">
    <title><fmt:message key="company.name" bundle="${rb}"/> - <fmt:message key="page.name.course"
                                                                           bundle="${rb}"/></title>
</head>
<body>
<jsp:include page="parts/header.jsp" flush="true"/>

<div class="w-100 d-flex align-items-center justify-content-center">
    <div class="d-flex flex-column justify-content-center align-items-center w-75">
        <div class="d-flex align-items-center justify-content-between w-100">
            <h1 class="display-3 mt-4">${requestScope.course.name}</h1>
            <span class="display-1 fs-2 mt-4">${requestScope.course.pricePerHour} BYN / <fmt:message
                    key="course_page.per_hour"
                    bundle="${rb}"/></span>
        </div>
        <img class="course_page_image w-100 mt-4 mb-4"
             src="${pageContext.request.contextPath}/image?path=${requestScope.course.picturePath}"
             alt="${requestScope.course.name}">

        <p id="content" class="m-4 fs-4 text-start">${requestScope.course.description}</p>

        <c:if test="${sessionScope.user == null || sessionScope.user.role == UserRole.STUDENT}">
            <div class="d-flex align-items-center justify-content-center w-100 mt-4 mb-4">
                <a class="fs-4 display-1 w-25 btn btn-success ms-4 me-4 mb-4"><fmt:message
                        key="course_page.enroll"
                        bundle="${rb}"/></a>
            </div>
        </c:if>
    </div>
</div>


<div class="d-flex flex-column justify-content-center align-items-center">
    <h2 class="display-4 mt-4">Comments</h2>

    <c:if test="${sessionScope.user == null}">
        <p class="display-4 fs-1 mt-4 mb-5">Login to leave a comment</p>
    </c:if>

    <c:if test="${sessionScope.user != null && sessionScope.user.role == UserRole.STUDENT}">
        <form class="w-75 mb-4 mt-4" method="post" action="${pageContext.request.contextPath}/controller">
            <input type="hidden" name="command" value="post_comment">
            <input type="hidden" name="course_id" value="${requestScope.course.id}">
            <div class="mb-3">
                <textarea placeholder="Enter your comment" class="form-control" name="content" rows="5"></textarea>
            </div>
            <button type="submit" class="btn btn-outline-success w-25">Post a comment</button>
        </form>
    </c:if>

    <c:if test="${requestScope.comments.size() == 0}">
        <p class="display-4 fs-3 mt-5 mb-4">No comments yet</p>
    </c:if>

    <c:if test="${requestScope.comments.size() != 0}">
        <c:forEach items="${requestScope.comments}" var="comment">
            <div class="comment d-flex w-75 flex-column mt-4 mb-4 p-4">
                <div class="d-flex justify-content-between align-items-center">
                    <p class="fs-4">${comment.studentName} ${comment.studentSurname}</p>
                    <p class="fs-6">${comment.dateTime}</p>
                </div>
                <p class="comment-text display-1 fs-2">${comment.content}</p>
            </div>
        </c:forEach>
    </c:if>
</div>

<%--<script src="https://cdn.jsdelivr.net/npm/marked/marked.min.js"></script>--%>
<%--<script>--%>
<%--    document.getElementById('content').innerHTML =--%>
<%--        marked('${requestScope.course.description}');--%>
<%--</script>TODO--%>

<jsp:include page="parts/footer.jsp"/>
</body>
</html>