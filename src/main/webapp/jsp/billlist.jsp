<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/jsp/common/head.jsp"%>

<%----%>
<script src="${pageContext.request.contextPath}/js/layui.js" type="text/javascript"></script>
<script>

</script>
<div class="right">
       <div class="location">
           <strong>你现在所在的位置是:</strong>
           <span>订单管理页面</span>
       </div>
       <div class="search">
       <form method="get" action="${pageContext.request.contextPath }/bill/queryAllBill">
			<input name="method" value="query" class="input-text" type="hidden">
			<span>商品名称：</span>
		   <input id="queryProductName" list="proName" name="queryProductName" type="text" value="${queryProductName }">
		   <datalist id="proName">
		   </datalist>
			<span>供应商：</span>
			<select id="queryProviderId" name="queryProviderId">
				<c:if test="${providerList != null }">
				   <option value="0">--请选择--</option>
				   <c:forEach var="provider" items="${providerList}">
				   		<option <c:if test="${provider.id == queryProviderId }">selected="selected"</c:if>
				   		value="${provider.id}">${provider.proName}</option>
				   </c:forEach>
				</c:if>
       		</select>
			 
			<span>是否付款：</span>
			<select id="queryIsPayment" name="queryIsPayment">
				<option value="0">--请选择--</option>
				<option value="1" ${queryIsPayment == 1 ? "selected=\"selected\"":"" }>未付款</option>
				<option value="2" ${queryIsPayment == 2 ? "selected=\"selected\"":"" }>已付款</option>
       		</select>
			 <input	value="查 询" type="submit" id="searchbutton">
			 <a href="${pageContext.request.contextPath }/jsp/billadd.jsp">添加订单</a>
			   <a href="javascript:void(0);"  id="excelFile">
				   导入Excel
			   </a>
			   <a href="javascript:void(0);" onclick="getExcelFile()"  id="outExcelFile">
				   导出Excel
			   </a>
		</form>
       </div>
       <!--账单表格 样式和供应商公用-->
      <table class="providerTable" cellpadding="0" cellspacing="0">
          <tr class="firstTr">
              <th width="10%">订单编码</th>
              <th width="20%">商品名称</th>
              <th width="10%">供应商</th>
              <th width="10%">订单金额</th>
              <th width="10%">是否付款</th>
              <th width="10%">创建时间</th>
              <th width="30%">操作</th>
          </tr>
          <c:forEach var="bill" items="${billList }" varStatus="status">
				<tr>
					<td>
					<span>${bill.billCode }</span>
					</td>
					<td>
					<span>${bill.productName }</span>
					</td>
					<td>
					<span>
						<c:forEach items="${providerList}" var="provider">
							<c:if test="${provider.id==bill.providerId}">
								${provider.proName}
							</c:if>
						</c:forEach>
					</span>
					</td>
					<td>
					<span>${bill.totalPrice}</span>
					</td>
					<td>
					<span>
						<c:if test="${bill.isPayment == 1}">未付款</c:if>
						<c:if test="${bill.isPayment == 2}">已付款</c:if>
					</span>
					</td>
					<td>
					<span>
					<fmt:formatDate value="${bill.creationDate}" pattern="yyyy-MM-dd"/>
					</span>
					</td>
					<td>
					<span><a class="viewBill" href="javascript:;" billid=${bill.id } billcc=${bill.billCode }><img src="${pageContext.request.contextPath }/images/read.png" alt="查看" title="查看"/></a></span>
					<span><a class="modifyBill" href="javascript:;" billid=${bill.id } billcc=${bill.billCode }><img src="${pageContext.request.contextPath }/images/xiugai.png" alt="修改" title="修改"/></a></span>
					<span><a class="deleteBill" href="javascript:;" billid=${bill.id } billcc=${bill.billCode }><img src="${pageContext.request.contextPath }/images/schu.png" alt="删除" title="删除"/></a></span>
					</td>
				</tr>
			</c:forEach>
      </table>
	<%--<%@include file="/jsp/rollpage.jsp" %>--%>
	<div>
		<nav aria-label="Page navigation">
			<ul class="pagination">
				<c:if test="${currentPageNo>1}">
					<li>
						<a href="${pageContext.request.contextPath }/bill/queryAllBill?queryProductName=${queryProductName}&queryProviderId=${queryProviderId}&queryIsPayment=${queryIsPayment}&currentPageNo=${currentPageNo-1}" aria-label="Previous">
							<span aria-hidden="true">&laquo;</span>
						</a>
					</li>
				</c:if>
				<c:if test="${currentPageNo<=1}">
					<li class="disabled">
						<a aria-label="Previous">
							<span aria-hidden="true">&laquo;</span>
						</a>
					</li>
				</c:if>
				<%! Integer currentPageNo; %>
				<%! Integer totalPageCount; %>
				<%currentPageNo = (Integer) request.getAttribute("currentPageNo");
				%>
				<%totalPageCount = (Integer) request.getAttribute("totalPageCount");
				%>
				<c:if test="<%= totalPageCount/5==currentPageNo/5&&currentPageNo%5!=0&&totalPageCount!=0%>">
					<c:forEach begin="<%=totalPageCount/5*5+1%>" end="${totalPageCount}" var="index">
						<c:choose>
							<c:when test="${index==currentPageNo}">
								<li class="active">
									<a href="${pageContext.request.contextPath }/bill/queryAllBill?queryProductName=${queryProductName}&queryProviderId=${queryProviderId}&queryIsPayment=${queryIsPayment}&currentPageNo=${currentPageNo}">
											${index}
									</a>
								</li>
							</c:when>
							<c:otherwise>
								<li>
									<a href="${pageContext.request.contextPath }/bill/queryAllBill?queryProductName=${queryProductName}&queryProviderId=${queryProviderId}&queryIsPayment=${queryIsPayment}&currentPageNo=${index}">
											${index}
									</a>
								</li>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</c:if>
				<c:if test="<%= totalPageCount/5!=currentPageNo/5||currentPageNo%5==0&&totalPageCount!=0%>">
				<c:forEach begin="${currentPageNo-(currentPageNo-1)%5}" end="${currentPageNo-(currentPageNo-1)%5+4}" var="index">
				<c:choose>

				<c:when test="${index==currentPageNo}">
				<li class="active"><a href="${pageContext.request.contextPath }/bill/queryAllBill?queryProductName=${queryProductName}&queryProviderId=${queryProviderId}&queryIsPayment=${queryIsPayment}&currentPageNo=${currentPageNo}">${index}</a>
					</c:when>
					<c:otherwise>
				<li><a href="${pageContext.request.contextPath }/bill/queryAllBill?queryProductName=${queryProductName}&queryProviderId=${queryProviderId}&queryIsPayment=${queryIsPayment}&currentPageNo=${index}">${index}</a>
					</c:otherwise>
					</c:choose>
					</c:forEach>
					</c:if>
					<c:if test="${currentPageNo!=totalPageCount}">
				<li>
					<a href="${pageContext.request.contextPath }/bill/queryAllBill?queryProductName=${queryProductName}&queryProviderId=${queryProviderId}&queryIsPayment=${queryIsPayment}&currentPageNo=${currentPageNo+1}" aria-label="Next">
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

</section>

<!--点击删除按钮后弹出的页面-->
<div class="zhezhao"></div>
<div class="remove" id="removeBi">
    <div class="removerChid">
        <h2>提示</h2>
        <div class="removeMain">
            <p>你确定要删除该订单吗？</p>
            <a href="#" id="yes">确定</a>
            <a href="#" id="no">取消</a>
        </div>
    </div>
</div>
<script>
    function goQuery() {
        var currentPageNo=$("#currentPageNo").val();
        var queryProviderId=$("#queryProviderId").val();
        var queryProductName=$("#queryProductName").val();
        var queryIsPayment=$("#queryIsPayment").val();
        location.href="${pageContext.request.contextPath }/bill/queryAllBill?queryProductName="+queryProductName+"&queryProviderId="+queryProviderId+"&queryIsPayment="+queryIsPayment+"&currentPageNo="+currentPageNo;
    }
</script>

<script>
		layui.use(['upload', 'element', 'layer'], function() {
			var $ = layui.jquery
					, upload = layui.upload
					, element = layui.element
					, layer = layui.layer;
			upload.render({
				elem: '#excelFile'
				, url: '${pageContext.request.contextPath }/bill/addBillByExcelFile' //改成您自己的上传接口
				, accept: 'file' //普通文件
				,field:'hello'
				, done: function (res) {
					layer.msg('上传成功');
					console.log(res);
				}
			});
		});
		$(function (){
			$("#queryProductName").keyup(function (){
				var queryProductName=$("#queryProductName").val();
				if (queryProductName!=null&&queryProductName!==""){
					$.get("${pageContext.request.contextPath}/bill/queryAllName",{queryProductName:queryProductName},function (billList) {
						var item=""
						$.each(billList,function (){
							item+="<option value='"+this.productName+"'>"+this.productName+"</option>"
						});
						console.log(item);
						$("#proName").html(item);
						$("#queryProductName").focus();
					},"json");
				}
			});
		});
		function getExcelFile(){
			location.href="${pageContext.request.contextPath}/bill/getExcelFileByBill";
		}
</script>

<%@include file="/jsp/common/foot.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/billlist.js"></script>