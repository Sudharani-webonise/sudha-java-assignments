<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
     <h2>Select the Room</h2>

        <form action="status.action" method="post">
        <select name="roomN" title="RoomId">
						<c:forEach var="roomN" items="${requestScope.roomList}">
									<option value="${roomN.roomId}">room ${roomN.roomId} floor ${roomN.floorNo}
									</option>
						</c:forEach>
		</select>
		<input type="submit" value="select">
		</form>
		
</body>
</html>