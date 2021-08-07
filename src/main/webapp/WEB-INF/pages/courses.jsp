<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="customtag" %>
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
    <link href="style/footer.css" rel="stylesheet">
    <link href="style/listing.css" rel="stylesheet">
    <title><fmt:message key="company.name" bundle="${rb}"/> - <fmt:message key="page.name.courses"
                                                                           bundle="${rb}"/></title>
</head>
<body>
<jsp:include page="parts/header.jsp" flush="true"/>

<c:if test="${requestScope.nothing_found != null}">
    <div class="d-flex justify-content-center align-items-center flex-column vh-100">
        <h1 class="display-1"><fmt:message key="courses.not_found" bundle="${rb}"/></h1>
        <a class="mt-4 first-page-link fs-3"
           href="${pageContext.request.contextPath}/controller?command=courses&page=1">
            <fmt:message key="courses.go_to_first_page" bundle="${rb}"/></a>
    </div>
</c:if>

<c:if test="${requestScope.nothing_found == null}">
    <div class="outer d-flex d-flex  justify-content-center">
        <div class="courses-main p-4">
            <div class="d-flex flex-column align-items-center">
                <div class="d-flex justify-content-end w-100">
                    <form class="search-courses d-flex"
                          action="${pageContext.request.contextPath}/controller"
                          method="get">
                        <input class="form-control me-2" name="search"
                               placeholder="<fmt:message key="courses.search.placeholder" bundle="${rb}"/>" required>
                        <input type="hidden" name="command" value="courses">
                        <input type="hidden" name="page" value="1">
                        <button type="submit" class="btn btn-outline-success"><fmt:message key="courses.search.btn"
                                                                                           bundle="${rb}"/></button>
                    </form>
                </div>

                <c:forEach items="${requestScope.courses}" var="course">
                    <div class="card ">
                        <img src="${course.picturePath}" class="card-img-top" alt="${course.name}">
                        <div class="card-body">
                            <h5 class="card-title display-4">${course.name}</h5>
                            <p class="card-text fs-5">${course.description}</p>
                            <a href="${pageContext.request.contextPath}/controller?command=course_page&course_id=${course.id}"
                               class="fs-4 btn btn-outline-success"><fmt:message
                                    key="courses.learn_more" bundle="${rb}"/></a>
                        </div>
                    </div>
                </c:forEach>
                <ctg:pagestag pagesCountAttribute="${requestScope.page_count}"
                              searchLine="${requestScope.search_line}"/>
            </div>
        </div>
    </div>
</c:if>

<jsp:include page="parts/footer.jsp"/>
</body>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
<script src="script/courses.js"></script>
</html>

<%--                        <nav class="mt-4" aria-label="Page navigation example"TODO remove>--%>
<%--                            <ul class="pagination flex-wrap justify-content-center">--%>
<%--                                <c:forEach begin="0" end="${requestScope.page_count - 1}" varStatus="status">--%>
<%--                                    <li class="page-item"><a class="page-link" href="${pageContext.request.contextPath}/contoller?command=courses&page=&search=">${status.count}</a></li>--%>
<%--                                </c:forEach>--%>
<%--                            </ul>--%>
<%--                        </nav>--%>
