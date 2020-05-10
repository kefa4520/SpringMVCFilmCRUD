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
  <h3>Add Film</h3>
  <form action="addFilm.do" method="POST">
    Title:
    <input type="text" name="title"/> 
    Description:
    <input type="text" name="description"/> 
    Release Year:
    <input type="number" name="releaseYear"/>
    Rating: 
    <input type="text" name="rating"/>
    
    <input type="submit" value="Add Film Data"/>
  </form>
</body>
</html>