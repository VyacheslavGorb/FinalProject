<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="edu.gorb.musicstudio.entity.UserRole" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent" var="rb"/>

<header class="navbar navbar-expand-lg navbar-dark bg-dark sticky-top">
    <div class="container-fluid">
        <a class="navbar-brand fw-bold"
           href="${pageContext.request.contextPath}/controller?command=home_page"><fmt:message key="company.name"
                                                                                               bundle="${ rb }"/></a>
        <div class="d-flex justify-content-around">
            <div class="navbar-nav">
                <a class="nav-link active" aria-current="page"
                   href="${pageContext.request.contextPath}/controller?command=home_page"><fmt:message key="header.home"
                                                                                                       bundle="${ rb }"/></a>
                <a class="nav-link " aria-current="page"
                   href="${pageContext.request.contextPath}/static_pages/aboutus.jsp"><fmt:message key="header.aboutus"
                                                                                                   bundle="${ rb }"/></a>
                <a class="nav-link " aria-current="page"
                   href="${pageContext.request.contextPath}/controller?command=courses&page=1"><fmt:message
                        key="header.courses"
                        bundle="${ rb }"/></a>
                <a class="nav-link " aria-current="page"
                   href="${pageContext.request.contextPath}/controller?command=teachers&page=1"><fmt:message
                        key="header.teachers"
                        bundle="${ rb }"/></a>
                <c:choose>
                    <c:when test="${sessionScope.user == null}">
                        <a class="nav-link " aria-current="page"
                           href="${pageContext.request.contextPath}/controller?command=go_to_sign_up_page"><fmt:message
                                key="header.signup"
                                bundle="${ rb }"/></a>
                        <a href="${pageContext.request.contextPath}/controller?command=go_to_login_page" type="button"
                           class="btn btn-success"><fmt:message key="header.login" bundle="${ rb }"/></a>
                    </c:when>
                    <c:when test="${sessionScope.user.role eq UserRole.STUDENT}">
                        <a class="nav-link " aria-current="page"
                           href="${pageContext.request.contextPath}/controller?command=student_lesson_schedule"><fmt:message
                                key="header.profile"
                                bundle="${ rb }"/></a>
                    </c:when>
                    <c:when test="${sessionScope.user.role eq UserRole.TEACHER}">
                        <a class="nav-link " aria-current="page"
                           href="${pageContext.request.contextPath}/controller?command=teacher_lesson_schedule">
                            <fmt:message key="header.profile" bundle="${ rb }"/></a>
                    </c:when>
                    <c:when test="${sessionScope.user.role eq UserRole.ADMIN}">
                        <a class="nav-link " aria-current="page"
                           href="${pageContext.request.contextPath}/controller?command=all_lessons_page"><fmt:message
                                key="header.profile"
                                bundle="${ rb }"/></a>
                    </c:when>
                </c:choose>
                <c:if test="${sessionScope.user != null}">
                    <a href="${pageContext.request.contextPath}/controller?command=logout" type="button"
                       class="btn btn-secondary"><fmt:message key="header.logout" bundle="${ rb }"/></a>
                </c:if>
            </div>
        </div>
    </div>
</header>
