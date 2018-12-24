<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="user" class="ua.nure.kn.gromak.usermanagement.User" scope="session"/>
<html>
<head><title>User management/Edit</title></head>
<body>
<form action="/edit" method="post">
    <input type="hidden" name="id" value="${user.id}">
    First name <input name="firstName" value="${user.firstName}"><br>
    Last name <input name="lastName" value="${user.lastName}"><br>
    Date of birth <input name="date"
                         value="<fmt:formatDate value="${user.dateOfBirth}" type="date" dateStyle="medium"/>"><br>
    <input type="submit" name="okayButton" value="Okay">
    <input type="submit" name="cancelButton" value="Cancel">
</form>
<c:if test="${requestScope.error != null}">
    <script>
        alert('${requestScope.error}')
    </script>
</c:if>
</body>
</html>