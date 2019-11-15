<%--
  Created by IntelliJ IDEA.
  User: XT
  Date: 2019/11/14
  Time: 18:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>员工列表</title>
</head>
<body>
    <c:if test="${emps == null}">暂无员工信息</c:if>
    <c:if test="${emps != null}">
        <table border="1" cellspacing="0" cellpadding="0">
            <thead>
            <tr>
                <td>ID</td>
                <td>LastName</td>
                <td>Gender</td>
                <td>Email</td>
                <td>Department</td>
            </tr>
            </thead>
            <tbody>
                <c:forEach items="${emps}" var="emp">
                    <tr>
                        <td>${emp.id}</td>
                        <td>${emp.lastName}</td>
                        <td>${emp.gender}</td>
                        <td>${emp.email}</td>
                        <td>${emp.department.departmentName}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>
</body>
</html>
