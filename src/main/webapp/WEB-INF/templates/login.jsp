<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page session="true" %>



<html>
<head>
    <link href="<c:url value="/css/main.css"/>" rel="stylesheet" type="text/css">

</head>
<body>
<div class="login-page">
    <div class="form">
        <form action="login" method="POST">
            <label>${error}</label>
            <input type="text" name="username" placeholder="username"/>
            <button>login</button>
        </form>
    </div>
</div>
</body>

</html>
