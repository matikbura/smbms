<%--
  Created by IntelliJ IDEA.
  User: 60108
  Date: 2021/6/23
  Time: 1:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <form action="${pageContext.request.contextPath}/bill/addBillByExcelFile" method="post" enctype="multipart/form-data">
        <input type="file" name="excelFile" value="选择Excel文件">
        <input type="submit" value="提交">
    </form>
</body>
</html>
