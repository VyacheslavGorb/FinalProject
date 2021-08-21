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
                           class="nav-link"><fmt:message key="header.login" bundle="${ rb }"/></a>
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
                       class="nav-link"><fmt:message key="header.logout" bundle="${ rb }"/></a>
                </c:if>
            </div>
        </div>
        <div class="navbar-nav">
            <form method="post" action="${pageContext.request.contextPath}/controller">
                <input type="hidden" name="command" value="change_language">
                <input type="hidden" name="lang" value="en">
                <button class="btn nav-link fw-bold" type="submit">En</button>
            </form>
            <span class="nav-link disabled"> | </span>
            <form method="post" action="${pageContext.request.contextPath}/controller">
                <input type="hidden" name="command" value="change_language">
                <input type="hidden" name="lang" value="ru">
                <button class="btn nav-link fw-bold" type="submit">Рус</button>
            </form>
        </div>
        <div>
            <span class="copyright"> &#169 2021 Copyright: MuzShock.com </span>
        </div>
    </div>
</footer>