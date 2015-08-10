<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>Status</h1>

	<form:form action="book" method="post">

		${availabeRoomList } <br /> Room number : <input type="text"
			name="roomId" value="${roomnumber }" readonly>
		<br /> floor
		number : <input type="text" name="floor" value="${floorNumber }"
			readonly>
		<br /> enter employee id : <input type="text" name="empId">
		<br /> enter comments : <input type="text" name="comment">
		<br />
		<input type="Date" id="date" name="date">
		<table>


			<tr>
				<td>FROM TIME</td>
				<td>TO TIME</td>
			</tr>
			<tr>
				<td><select name="fromTime">
						<option>10 am</option>
						<option>11 am</option>
						<option>12 pm</option>
						<option>1 pm</option>
						<option>2 pm</option>
						<option>3 pm</option>
						<option>4 pm</option>
				</select></td>


				<td><select name="toTime">
						<option>11 am</option>
						<option>12 pm</option>
						<option>1 pm</option>
						<option>2 pm</option>
						<option>3 pm</option>
						<option>4 pm</option>
				</select></td>
			</tr>



			<tr>
				<td><input type="submit" value="book"></td>
			</tr>
		</table>
	</form:form>

</body>
</html>