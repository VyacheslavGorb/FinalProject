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
    <link href="style/footer.css" rel="stylesheet">
    <title><fmt:message key="company.name" bundle="${rb}"/> - <fmt:message key="page.name.course"
                                                                           bundle="${rb}"/></title>
</head>
<body>
<jsp:include page="../parts/header.jsp" flush="true"/>
<c:if test="${sessionScope.is_manage_error != null}">
    <div class="d-flex align-items-center justify-content-center">
        <div id="illegal-datetime" class="d-flex alert alert-danger align-items-center w-75 mt-4" role="alert">
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor"
                 class="bi bi-exclamation-triangle-fill flex-shrink-0 me-2" viewBox="0 0 16 16" role="img"
                 aria-label="Warning:">
                <path
                        d="M8.982 1.566a1.13 1.13 0 0 0-1.96 0L.165 13.233c-.457.778.091 1.767.98 1.767h13.713c.889 0 1.438-.99.98-1.767L8.982 1.566zM8 5c.535 0 .954.462.9.995l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 5.995A.905.905 0 0 1 8 5zm.002 6a1 1 0 1 1 0 2 1 1 0 0 1 0-2z"/>
            </svg>
            <div>
                <fmt:message key="${sessionScope.error_key}" bundle="${rb}"/>
            </div>
        </div>
    </div>
</c:if>

<c:forEach items="${requestScope.user_map}" var="userEntry">
    <div class="w-100 d-flex justify-content-center align-items-center flex-column">
        <div class="d-flex w-75">
            <p class="fs-2 display-1">${userEntry.key}</p>
        </div>
        <table class="table mb-5 w-75">
            <thead>
            <tr>
                <th scope="col">User login</th>
                <th scope="col">User name</th>
                <th scope="col">User surname</th>
                <th scope="col">Email</th>
                <th scope="col">Role</th>
                <th scope="col">Status</th>
                <th scope="col">Change status</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${userEntry.value}" var="user">
                <tr>
                    <td>${user.login}</td>
                    <td>${user.name}</td>
                    <td>${user.surname}</td>
                    <td>${user.email}</td>
                    <td>${user.role}</td>
                    <td>${user.status}</td>
                    <td>
                        <c:if test="${user.status.name() == 'INACTIVE'}">
                            <form method="POST" action="${pageContext.request.contextPath}/controller">
                                <input type="hidden" name="command" value="activate_user">
                                <input type="hidden" name="user_id" value="${user.id}">
                                <button class="btn btn-outline-success" type="submit">Activate</button>
                            </form>
                        </c:if>
                        <c:if test="${user.status.name() != 'INACTIVE'}">
                            <form method="POST" action="${pageContext.request.contextPath}/controller">
                                <input type="hidden" name="command" value="deactivate_user">
                                <input type="hidden" name="user_id" value="${user.id}">
                                <button class="btn btn-outline-danger" type="submit">Deactivate</button>
                            </form>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

</c:forEach>

<jsp:include page="../parts/footer.jsp"/>
</body>
</html>