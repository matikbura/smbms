<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>	
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>超市订单管理系统</title>
    <script src="${pageContext.request.contextPath}/js/jquery-3.2.1.min.js"></script>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/css/style.css" />
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/css/public.css" />
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/css/page.css" />
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/css/layui.css" />
</head>

<body>
<!--头部-->
    <header class="publicHeader">
        <h1>超市订单管理系统</h1>
        <div class="publicHeaderR">
            <p><span>下午好！</span><span style="color: #fff21b"> ${userSession.userName}</span> , 欢迎你！</p>
            <a href="${pageContext.request.contextPath }/user/logout">退出</a>
        </div>
    </header>
<!--时间-->
    <section class="publicTime">
        <span id="time">2015年1月1日 11:11  星期一</span>
        <a href="#">温馨提示：为了能正常浏览，请使用高版本浏览器！（IE10+）</a>
    </section>
 <!--主体内容-->
 <section class="publicMian ">
     <div class="left">
         <h2 class="leftH2"><span class="span1"></span>功能列表 <span></span></h2>
         <script>
             $(function (){
                 $.get("${pageContext.request.contextPath}/user/getUserSession",function (data){
                     console.log("---"+data);
                     if (data.imgPath==null){
                         $("#haedImg").attr("src","${pageContext.request.contextPath}/images/schu.png");
                     }else {
                         $("#haedImg").attr("src",data.imgPath);
                     }
                 },"json");
             });
         </script>
         <nav>
             <img  id="haedImg" alt="..." style="width: 100px;display: block;margin: 0 auto;border-radius: 5px;box-shadow: 0px 0px 10px black"></img>
             <ul class="list">
               <li ><a href="${pageContext.request.contextPath }/bill/queryAllBill">订单管理</a></li>
              <li><a href="${pageContext.request.contextPath }/provider/query">供应商管理</a></li>
              <li><a href="${pageContext.request.contextPath }/user/query">用户管理</a></li>
              <li><a href="${pageContext.request.contextPath }/jsp/pwdmodify.jsp">密码修改</a></li>
                 <li><a href="${pageContext.request.contextPath }/jsp/pictureUpload.jsp">头像上传</a></li>
              <li><a href="${pageContext.request.contextPath }/user/logout">退出系统</a></li>
             </ul>
         </nav>
     </div>
     <input type="hidden" id="path" name="path" value="${pageContext.request.contextPath }"/>
     <input type="hidden" id="referer" name="referer" value="<%=request.getHeader("Referer")%>"/>