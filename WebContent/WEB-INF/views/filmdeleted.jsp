<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Film Deleted</title>
</head>
<body>
<c:choose>
    <c:when test="${isdeleted}">
      <p>Film deleted successfully!</p>
    </c:when>
    <c:otherwise>
      <p>Error deleting the film!</p>
    </c:otherwise>
  </c:choose>
  

</body>
</html>