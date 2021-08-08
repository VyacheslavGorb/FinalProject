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
    <title><fmt:message key="company.name" bundle="${rb}"/> - <fmt:message key="page.name.teachers"
                                                                           bundle="${rb}"/></title>
</head>
<body>
<jsp:include page="parts/header.jsp" flush="true"/>

<c:if test="${requestScope.nothing_found != null}">
    <div class="d-flex justify-content-center align-items-center flex-column vh-100">
        <h1 class="display-1"><fmt:message key="teachers.not_found" bundle="${rb}"/></h1>
        <a class="mt-4 first-page-link fs-3"
           href="${pageContext.request.contextPath}/controller?command=teachers&page=1">
            <fmt:message key="teachers.go_to_first_page" bundle="${rb}"/></a>
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
                               placeholder="<fmt:message key="teachers.search.placeholder" bundle="${rb}"/>" required>
                        <input type="hidden" name="command" value="teachers">
                        <input type="hidden" name="page" value="1">
                        <button type="submit" class="btn btn-outline-success"><fmt:message key="teachers.search.btn"
                                                                                           bundle="${rb}"/></button>
                    </form>
                </div>

                <c:forEach items="${requestScope.teachers}" var="teacher">

                    <c:if test="${teacher.descriptionProvided == true}">
                        <div class="card mb-3">
                            <div class="row g-0">
                                <div class="col-md-4">
                                    <img src="${teacher.picturePath}" class="img-fluid rounded-start"
                                         alt="${teacher.name}">
                                </div>
                                <div class="col-md-8">
                                    <div class="card-body">
                                        <h5 class="card-title display-4 fs-1">${teacher.name} ${teacher.surname}</h5>
                                        <p class="card-text fs-5">${teacher.selfDescription}</p>
                                        <a href="${pageContext.request.contextPath}/controller?command=teacher_page&teacher_id=${teacher.teacherId}"
                                           class="btn btn-outline-success"><fmt:message key="teachers.see_more"
                                                                                        bundle="${rb}"/></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:if>

                    <c:if test="${teacher.descriptionProvided == false}">
                        <div class="card mb-3">
                            <div class="row g-0">
                                <div class="col-md-4">
                                    <img src="pic/slide1.jpg" class="img-fluid rounded-start"
                                         alt="${teacher.name}">
                                </div>
                                <div class="col-md-8">
                                    <div class="card-body">
                                        <h5 class="card-title display-4 fs-1">${teacher.name} ${teacher.surname}</h5>
                                        <p class="card-text fs-5"><fmt:message key="teachers.no_description_provided"
                                                                               bundle="${rb}"/></p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:if>

                </c:forEach>


                <ctg:pagestag pagesCountAttribute="${requestScope.page_count}"
                              searchLine="${requestScope.search_line}"
                              command="${requestScope.command}"/>
            </div>
        </div>
    </div>
</c:if>

<jsp:include page="parts/footer.jsp"/>
</body>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
<script src="script/pagination.js"></script>
</html>

<%--                        <nav class="mt-4" aria-label="Page navigation example"TODO remove>--%>
<%--                            <ul class="pagination flex-wrap justify-content-center">--%>
<%--                                <c:forEach begin="0" end="${requestScope.page_count - 1}" varStatus="status">--%>
<%--                                    <li class="page-item"><a class="page-link" href="${pageContext.request.contextPath}/contoller?command=courses&page=&search=">${status.count}</a></li>--%>
<%--                                </c:forEach>--%>
<%--                            </ul>--%>
<%--                        </nav>--%>
