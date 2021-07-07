<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@include file="/jsp/common/head.jsp"%>

<div class="right">
    <div class="location">
        <strong>你现在所在的位置是:</strong>
        <span>回收站</span>
    </div>
    <div class="search">
        <form method="get" action="${pageContext.request.contextPath }/providerHsz/query">
            <input name="method" value="query" type="hidden">
            <span>供应商编码：</span>
            <input id="queryProCode" name="queryProCode" type="text" value="${queryProCode }" style="width: 175px">

            <span>供应商名称：</span>
            <input id="queryProName" name="queryProName" type="text" value="${queryProName }" style="width: 175px">

            <span>开始时间：</span>
            <input id="queryMinDateStr" name="queryMinDateStr" type="date" value="<fmt:formatDate value='${queryMinDate}' pattern='yyyy-MM-dd'/>" style="height: 30px">

            <span>结束时间：</span>
            <input id="queryMaxDateStr" name="queryMaxDateStr" type="date" value="<fmt:formatDate value='${queryMaxDate}' pattern='yyyy-MM-dd'/>" style="height: 30px">

            <input value="查 询" type="submit" id="searchbutton" style="width: 160px">
        </form>
    </div>
    <!--供应商操作表格-->
    <table class="providerTable" cellpadding="0" cellspacing="0">
        <tr class="firstTr">
            <th width="15%">供应商编码</th>
            <th width="20%">供应商名称</th>
            <th width="10%">联系人</th>
            <th width="15%">联系电话</th>
            <th width="10%">传真</th>
            <th width="15%">删除时间</th>
            <th width="15%">操作</th>
        </tr>
        <c:forEach var="provider" items="${providerList }" varStatus="status">
            <tr>
                <td>
                    <span>${provider.proCode }</span>
                </td>
                <td>
                    <span>${provider.proName }</span>
                </td>
                <td>
                    <span>${provider.proContact}</span>
                </td>
                <td>
                    <span>${provider.proPhone}</span>
                </td>
                <td>
                    <span>${provider.proFax}</span>
                </td>
                <td>
					<span>
					<fmt:formatDate value="${provider.deletetime}" pattern="yyyy-MM-dd"/>
					</span>
                </td>
                <td>
                    <span><a class="deleteProvider" proid=${provider.id } proname=${provider.proName }>恢复数据</a></span>
                </td>
            </tr>
        </c:forEach>
    </table>
    <div>
        <nav aria-label="Page navigation">
            <ul class="pagination">
                <c:if test="${currentPageNo>1}">
                <li>
                    <a href="${pageContext.request.contextPath }/providerHsz/query?queryProName=${queryProName}&queryProCode=${queryProCode}&queryMinDateStr=<fmt:formatDate value='${queryMinDate}' pattern='yyyy-MM-dd'/>&queryMaxDateStr=<fmt:formatDate value='${queryMaxDate}' pattern='yyyy-MM-dd'/>&currentPageNo=${currentPageNo-1}" aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                    </c:if>
                    <c:if test="${currentPageNo<=1}">
                <li class="disabled">
                    <a aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                    </c:if>
                        <%! Integer currentPageNo; %>
                        <%! Integer totalPageCount; %>
                        <%currentPageNo = (Integer) request.getAttribute("currentPageNo");
                    System.out.println(currentPageNo);%>
                        <%totalPageCount = (Integer) request.getAttribute("totalPageCount");
                    System.out.println(totalPageCount);%>
                    <c:if test="<%= totalPageCount/5==currentPageNo/5&&currentPageNo%5!=0&&totalPageCount!=0%>">
                    <c:forEach begin="<%=totalPageCount/5*5+1%>" end="${totalPageCount}" var="index">
                    <c:choose>
                    <c:when test="${index==currentPageNo}">
                <li class="active">
                    <a href="${pageContext.request.contextPath }/providerHsz/query?queryProName=${queryProName}&queryProCode=${queryProCode}&queryMinDateStr=<fmt:formatDate value='${queryMinDate}' pattern='yyyy-MM-dd'/>&queryMaxDateStr=<fmt:formatDate value='${queryMaxDate}' pattern='yyyy-MM-dd'/>&currentPageNo=${index}">
                            ${index}
                    </a>
                    </c:when>
                    <c:otherwise>
                <li>
                    <a href="${pageContext.request.contextPath }/providerHsz/query?queryProName=${queryProName}&queryProCode=${queryProCode}&queryMinDateStr=<fmt:formatDate value='${queryMinDate}' pattern='yyyy-MM-dd'/>&queryMaxDateStr=<fmt:formatDate value='${queryMaxDate}' pattern='yyyy-MM-dd'/>&currentPageNo=${index}">
                            ${index}
                    </a>
                    </c:otherwise>
                    </c:choose>
                    </c:forEach>
                    </c:if>
                    <c:if test="<%= totalPageCount/5!=currentPageNo/5||currentPageNo%5==0&&totalPageCount!=0%>">
                    <c:forEach begin="${currentPageNo-(currentPageNo-1)%5}" end="${currentPageNo-(currentPageNo-1)%5+4}" var="index">
                    <c:choose>

                    <c:when test="${index==currentPageNo}">
                <li class="active"><a href="${pageContext.request.contextPath }/providerHsz/query?queryProName=${queryProName}&queryProCode=${queryProCode}&queryMinDateStr=<fmt:formatDate value='${queryMinDate}' pattern='yyyy-MM-dd'/>&queryMaxDateStr=<fmt:formatDate value='${queryMaxDate}' pattern='yyyy-MM-dd'/>&currentPageNo=${currentPageNo}">${index}</a><>
                    </c:when>
                    <c:otherwise>
                <li><a href="${pageContext.request.contextPath }/providerHsz/query?queryProName=${queryProName}&queryProCode=${queryProCode}&queryMinDateStr=<fmt:formatDate value='${queryMinDate}' pattern='yyyy-MM-dd'/>&queryMaxDateStr=<fmt:formatDate value='${queryMaxDate}' pattern='yyyy-MM-dd'/>&currentPageNo=${index}">${index}</a><>
                    </c:otherwise>
                    </c:choose>
                    </c:forEach>
                    </c:if>
                    <c:if test="${currentPageNo!=totalPageCount}">
                <li>
                    <a href="${pageContext.request.contextPath }/providerHsz/query?queryProName=${queryProName}&queryProCode=${queryProCode}&queryMinDateStr=<fmt:formatDate value='${queryMinDate}' pattern='yyyy-MM-dd'/>&queryMaxDateStr=<fmt:formatDate value='${queryMaxDate}' pattern='yyyy-MM-dd'/>&currentPageNo=${currentPageNo+1}" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                    </c:if>
                    <c:if test="${currentPageNo==totalPageCount||totalPageCount==0}">
                <li class="disabled">
                    <a aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                    </c:if>
                    <li class="myText">跳转至</li>
                    <li><input type="text" id="currentPageNo" name="currentPageNo"></li>
                    <li class="myText">页</li>
                    <button onclick="goQuery()">go</button>
                    <li class="myText">
                        共<span id="totalCountId">${totalCount}</span>条记录，共${totalPageCount}页
                    </li>
                </span>
            </ul>
        </nav>
    </div>
    <div style="width: 80%">
        <form method="get" action="${pageContext.request.contextPath }/provider/query">
            <input type="submit" value="返回供应商管理页面">
        </form>
    </div>
</div>
</section>

<%@include file="/jsp/common/foot.jsp" %>
<script>
    $(function(){
        $(".deleteProvider").on("click",function(){
            var obj = $(this);
            if(confirm("你确定要恢复供应商【"+obj.attr("proname")+"】吗？")){
                $.ajax({
                    type:"POST",
                    url:path+"/providerHsz/delete",
                    data:{id:obj.attr("proid")},
                    dataType:"json",
                    success:function(data){
                        if(data.delResult == "true"){ //删除成功：移除删除行
                            alert("恢复数据成功");
                            obj.parents("tr").remove();
                            //拿记录数 String类型,转成Number类型-1,重新设值
                            $("#totalCountId").text(Number($("#totalCountId").text())-1);
                        }else if(data.delResult == "false"){ //删除失败
                            alert("对不起，删除供应商【"+obj.attr("proname")+"】失败");
                        }else if(data.delResult == "notexist"){
                            alert("对不起，供应商【"+obj.attr("proname")+"】不存在");
                        }
                    },
                    error:function(data){
                        alert("对不起，恢复失败");
                    }
                });
            }
        })
    });
</script>

<script>
    function goQuery() {
        var currentPageNo=$("#currentPageNo").val();
        var queryProCode=$("#queryProCode").val();
        var queryProName=$("#queryProName").val();
        var queryMinDateStr=$("#queryMinDateStr").val();
        var queryMaxDateStr=$("#queryMaxDateStr").val();
        console.log(currentPageNo);
        location.href="${pageContext.request.contextPath }/providerHsz/query?queryProName="+queryProName+"&queryProCode="+queryProCode+"&currentPageNo="+currentPageNo+"&queryMinDateStr="+queryMinDateStr+"&queryMaxDateStr="+queryMaxDateStr;
    }
</script>
