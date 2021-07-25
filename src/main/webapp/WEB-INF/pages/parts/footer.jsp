<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="edu.gorb.musicstudio.entity.UserRole" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent" var="rb"/>

<footer class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container-fluid d-flex justify-content-between">
        <div id="header_navbar" class="d-flex justify-content-between">
            <div class="navbar-nav">
                <a class="nav-link " aria-current="page" href="#"><fmt:message key="header.home" bundle="${ rb }"/></a>
                <a class="nav-link " aria-current="page" href="#"><fmt:message key="header.aboutus"
                                                                               bundle="${ rb }"/></a>
                <a class="nav-link " aria-current="page" href="#"><fmt:message key="header.courses"
                                                                               bundle="${ rb }"/></a>
                <a class="nav-link " aria-current="page" href="#"><fmt:message key="header.teachers"
                                                                               bundle="${ rb }"/></a>
                <c:choose>
                    <c:when test="${sessionScope.user == null}">
                        <a class="nav-link " aria-current="page" href="#"><fmt:message key="header.signup"
                                                                                       bundle="${ rb }"/></a>
                    </c:when>
                    <c:when test="${sessionScope.user.role eq UserRole.STUDENT}">
                        <a class="nav-link " aria-current="page" href="#"><fmt:message key="header.profile"
                                                                                       bundle="${ rb }"/></a>
                    </c:when>
                    <c:when test="${sessionScope.user.role eq UserRole.TEACHER}">
                        <a class="nav-link " aria-current="page" href="#"><fmt:message key="header.profile"
                                                                                       bundle="${ rb }"/></a>
                    </c:when>
                    <c:when test="${sessionScope.user.role eq UserRole.ADMIN}">
                        <a class="nav-link " aria-current="page" href="#"><fmt:message key="header.profile"
                                                                                       bundle="${ rb }"/></a>
                    </c:when>
                </c:choose>
            </div>
        </div>
        <div class="navbar-nav">
            <a class="nav-link fw-bold" aria-current="page"
               href="${pageContext.request.contextPath}/controller?command=change_language&lang=en">En</a>
            <span class="nav-link disabled"> | </span>
            <a class="nav-link ml-5 fw-bold" aria-current="page"
               href="${pageContext.request.contextPath}/controller?command=change_language&lang=ru">Рус</a>
        </div>
        <div>
            <span class="copyright"> &#169 2021 Copyright: MuzShock.com </span>
        </div>
    </div>
</footer>