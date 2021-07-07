<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@include file="/jsp/common/head.jsp"%>

<div class="right">
    <div class="location">
        <strong>你现在所在的位置是:</strong>
        <span>用户管理页面</span>
    </div>
    <div class="search">
        <form method="post" action="${pageContext.request.contextPath }/user/query">

            <span>用户名称：</span>
            <input id="queryUserName" name="queryUserName" type="text" value="${queryUserName}">

            <span>用户角色：</span>
            <select id="queryUserRole" name="queryUserRole" >
                <option value="">
                    <c:if test="${queryUserRole1==0}">
                        请选择
                    </c:if>
                    <c:if test="${queryUserRole1!=0}">
                        ${roleList.get(queryUserRole1-1).roleName}
                    </c:if>

                </option>
                <c:forEach var="role" items="${roleList}" varStatus="vsta">
                    <option value="${role.id}">${role.roleName}</option>
                </c:forEach>
            </select>

            <input value="查 询" type="submit" id="searchbutton">
            <a href="${pageContext.request.contextPath }/jsp/useradd.jsp">添加用户</a>
        </form>
    </div>
    <!--供应商操作表格-->
    <table class="providerTable" cellpadding="0" cellspacing="0">
        <tr class="firstTr">
            <th width="8%">用户名</th>
            <th width="15%">姓名</th>
            <th width="8%">性别</th>
            <th width="8%">年龄</th>
            <th width="8%">电话</th>
            <th width="8%">岗位</th>
            <th width="10%">用户头像</th>
            <th width="30%">操作</th>
        </tr>
        <c:forEach var="user" items="${userList }" varStatus="status">
            <tr>
                <td>
                    <span>${user.userCode }</span>
                </td>
                <td>
                    <span>${user.userName }</span>
                </td>
                <td>
                    <span>
                        <c:if test="${user.gender==1}">
                            男
                        </c:if>
                        <c:if test="${user.gender==2}">
                            女
                        </c:if>
                        <c:if test="${user.gender!=1 and user.gender!=2}">
                            未知
                        </c:if>
                    </span>
                </td>
                <td>

                    <span>
                        <fmt:formatDate value="${user.birthday}" pattern="yyyy-MM-dd"/>
                    </span>
                </td>

                <td>
                    <span>${user.phone}</span>
                </td>
                <td>
                    <span>
                        <c:forEach var="role" items="${roleList}">
                            <c:if test="${role.id==user.userRole}">
                                ${role.roleName}
                            </c:if>
                        </c:forEach>
                    </span>
                </td>
                <td>
                    <span>
                        <c:if test="${empty user.imgPath}">
                            无
                        </c:if>
                        <c:if test="${not empty user.imgPath}">
                            <img src="${user.imgPath}" style="width: 50px">
                        </c:if>

                    </span>
                </td>
                <td>
                    <span><a class="viewUser" href="javascript:;"  userid=${user.id} username=${user.userName}><img src="${pageContext.request.contextPath }/images/read.png" alt="查看" title="查看"/></a></span>
                    <span><a class="modifyUser" href="javascript:;" userid=${user.id} username=${user.userName}><img src="${pageContext.request.contextPath }/images/xiugai.png" alt="修改" title="修改"/></a></span>
                    <span><a class="deleteUser" href="javascript:;" userid=${user.id} username=${user.userName}><img src="${pageContext.request.contextPath }/images/schu.png" alt="删除" title="删除"/></a></span>
                </td>
            </tr>
        </c:forEach>
    </table>
    <div>
        <nav aria-label="Page navigation">
            <ul class="pagination">
                <c:if test="${currentPageNo!=1}">
                    <li>
                        <a href="${pageContext.request.contextPath}/user/query?queryUserName=${queryUserName}&queryUserRole=${queryUserRole}&currentPageNo=${currentPageNo-1}" aria-label="Previous">
                            <span aria-hidden="true">&laquo;</span>
                        </a>
                    </li>
                </c:if>
                <c:if test="${currentPageNo==1}">
                    <li class="disabled">
                        <a aria-label="Previous">
                            <span aria-hidden="true">&laquo;</span>
                        </a>
                    </li>
                </c:if>
                <%! Integer currentPageNo; %>
                <%! Integer totalPageCount; %>
                <%currentPageNo = (Integer) request.getAttribute("currentPageNo");
                    System.out.println(currentPageNo);%>
                <%totalPageCount = (Integer) request.getAttribute("totalPageCount");
                    System.out.println(totalPageCount);%>
                <c:if test="<%= totalPageCount/5==currentPageNo/5&&currentPageNo%5!=0%>">
                    <c:forEach begin="<%=totalPageCount/5*5+1%>" end="${totalPageCount}" var="index">
                        <c:choose>
                            <c:when test="${index==currentPageNo}">
                                <li class="active">
                                    <a href="${pageContext.request.contextPath}/user/query?queryUserName=${queryUserName}&queryUserRole=${queryUserRole}&currentPageNo=${currentPageNo}">
                                            ${index}
                                    </a>
                                </li>
                            </c:when>
                            <c:otherwise>
                                <li>
                                    <a href="${pageContext.request.contextPath}/user/query?queryUserName=${queryUserName}&queryUserRole=${queryUserRole}&currentPageNo=${index}">
                                            ${index}
                                    </a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </c:if>
                <c:if test="<%= totalPageCount/5!=currentPageNo/5||currentPageNo%5==0%>">
                    <c:forEach begin="${currentPageNo-(currentPageNo-1)%5}" end="${currentPageNo-(currentPageNo-1)%5+4}" var="index">
                        <c:choose>

                            <c:when test="${index==currentPageNo}">
                                <li class="active"><a href="">${index}</a></li>
                            </c:when>
                            <c:otherwise>
                                <li><a href="">${index}</a></li>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </c:if>
                <c:if test="${currentPageNo!=totalPageCount}">
                    <li>
                        <a href="${pageContext.request.contextPath}/user/query?queryUserName=${queryUserName}&queryUserRole=${queryUserRole}&currentPageNo=${currentPageNo+1}" aria-label="Next">
                            <span aria-hidden="true">&raquo;</span>
                        </a>
                    </li>
                </c:if>
                <c:if test="${currentPageNo==totalPageCount}">
                    <li class="disabled">
                        <a aria-label="Next">
                            <span aria-hidden="true">&raquo;</span>
                        </a>
                    </li>
                </c:if>
                <li class="myText">跳转至</li>
                <li><input type="text" id="currentPageNo" name="currentPageNo"></li>
                <li class="myText">页</li>
                <button onclick="goQuery()">go</button>
                <li class="myText">
                    共${totalCount}条记录，共${totalPageCount}页
                </li>
            </ul>
        </nav>
    </div>
</div>

</div>
</section>

<!--点击删除按钮后弹出的页面-->
<div class="zhezhao"></div>
<div class="remove" id="removeUse">
    <div class="removerChid">
        <h2>提示</h2>
        <div class="removeMain" >
            <p>你确定要删除该用户吗？</p>
            <a href="#" id="yes">确定</a>
            <a href="#" id="no">取消</a>
        </div>
    </div>
</div>

<script>
    function goQuery() {
        var queryUserName=$("#queryUserName").val();
        var currentPageNo=$("#currentPageNo").val();
        var queryUserRole=$("#queryUserRole").val();
        location.href="${pageContext.request.contextPath }/user/query?queryUserName="+queryUserName+"&currentPageNo="+currentPageNo+"&queryUserRole="+queryUserRole;
    }
</script>

<%@include file="/jsp/common/foot.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/userlist.js"></script>
