<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false"%>
<html>
<head>
<title>Home</title>
</head>
<body>
	<h1>WEL COME TO CRB SYSTEM</h1>

	<form:form method="GET" action="process">
		<table>
			<tr>
				<td><input type="submit" value="Process" /></td>
			</tr>
		</table>
	</form:form>

</body>
</html>
