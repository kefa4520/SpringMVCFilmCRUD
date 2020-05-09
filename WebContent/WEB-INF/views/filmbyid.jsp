<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Films</title>
</head>
<body>

  <h3>Find Film by ID</h3>
  <form action="filmById.do" method="GET">
    Enter Film ID:
    <input type="number" name="id"/> 
  
    <input type="submit" value="Submit Film ID"/>
  </form>
</body>
</html>