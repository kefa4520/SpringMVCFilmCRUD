<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Film</title>
</head>
<body>
  <c:choose>
    <c:when test="${! empty film}">
      <ul>
      	<li>ID: ${film.id }</li>
        <li>TITLE: ${film.title}</li>
        <li>DESCRIPTION: ${film.description}</li>
        <li>RELEASE YEAR: ${film.releaseYear}</li>
        <li>RATING: ${film.rating}</li>
        <li>LANGUAGE: ${languageString}</li>
      </ul>
    </c:when>
    <c:otherwise>
      <p>No film created</p>
    </c:otherwise>
  </c:choose>
      <form action="updateFilm.do" method="POST">
  	Change the film?:
  	<br>
  	<input type="hidden" name="id" value="${film.id}"/>
  	<input type="hidden" name="languageId" value="1"/>
    Title:
    <input type="text" name="title" value="${film.title}"/> 
    Description:
    <input type="text" name="description" value="${film.description}"/> 
    Release Year:
    <input type="number" name="releaseYear" value="${film.releaseYear}"/>
    Rating: ${film.rating}
    
    <select id="rating" name="rating">
    	<option value="G">General Audiences</option>
    	<option value="PG">Parental Guidance Suggested</option>
    	<option value="PG13">Parents Strongly Cautioned</option>
    	<option value="R">Restricted</option>
    	<option value="NC17">Adults Only</option>
    </select>
    <!-- Language:
    <select id="languageId" name="languageId">
    	<option value="1">English</option>
    	<option value="2">Italian</option>
    	<option value="3">Japanese</option>
    	<option value="4">Mandarin</option>
    	<option value="5">French</option>
    	<option value="6">German</option>
    </select> -->
    <input type="submit" value="Update Film Data"/>
  </form>

</body>
</html>