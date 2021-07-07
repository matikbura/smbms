<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/jsp/common/head.jsp"%>

<div class="right" style="height: 700px">
        <div class="location">
            <strong>你现在所在的位置是:</strong>
            <span>供应商管理页面</span>
        </div>
        <div class="search">
        	<form method="get" action="${pageContext.request.contextPath }/provider/query">
				<input name="method" value="query" type="hidden">
				<span>供应商编码：</span>
				<input name="queryProCode" id="queryProCode" type="text" value="${queryProCode }">
				
				<span>供应商名称：</span>
				<input name="queryProName" id="queryProName" type="text" value="${queryProName }">
				
				<input value="查 询" type="submit" id="searchbutton">
				<a href="${pageContext.request.contextPath }/providerHsz/query">回收站</a>
				<a href="${pageContext.request.contextPath }/jsp/provideradd.jsp">添加供应商</a>
			</form>
        </div>
        <!--供应商操作表格-->
        <table class="providerTable" cellpadding="0" cellspacing="0">
            <tr class="firstTr">
                <th width="10%">供应商编码</th>
                <th width="20%">供应商名称</th>
                <th width="10%">联系人</th>
                <th width="10%">联系电话</th>
                <th width="10%">传真</th>
                <th width="10%">创建时间</th>
                <th width="30%">操作</th>
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
					<fmt:formatDate value="${provider.creationDate}" pattern="yyyy-MM-dd"/>
					</span>
					</td>
					<td>
					<span><a class="viewProvider" href="javascript:;" proid=${provider.id } proname=${provider.proName }><img src="${pageContext.request.contextPath }/images/read.png" alt="查看" title="查看"/></a></span>
					<span><a class="modifyProvider" href="javascript:;" proid=${provider.id } proname=${provider.proName }><img src="${pageContext.request.contextPath }/images/xiugai.png" alt="修改" title="修改"/></a></span>
					<span><a class="deleteProvider" href="javascript:;" proid=${provider.id } proname=${provider.proName }><img src="${pageContext.request.contextPath }/images/schu.png" alt="删除" title="删除"/></a></span>
					</td>
				</tr>
			</c:forEach>
        </table>

	<div>
		<nav aria-label="Page navigation">
			<ul class="pagination">
				<c:if test="${currentPageNo>1}">
					<li>
						<a href="${pageContext.request.contextPath }/provider/query?queryProName=${queryProName}&queryProCode=${queryProCode}&currentPageNo=${currentPageNo-1}" aria-label="Previous">
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
					System.out.println(currentPageNo);%>
				<%totalPageCount = (Integer) request.getAttribute("totalPageCount");
					System.out.println(totalPageCount);%>
				<c:if test="<%= totalPageCount/5==currentPageNo/5&&currentPageNo%5!=0&&totalPageCount!=0%>">
					<c:forEach begin="<%=totalPageCount/5*5+1%>" end="${totalPageCount}" var="index">
						<c:choose>
							<c:when test="${index==currentPageNo}">
								<li class="active">
									<a href="${pageContext.request.contextPath }/provider/query?queryProName=${queryProName}&queryProCode=${queryProCode}&currentPageNo=${currentPageNo}">
											${index}
									</a>
								</li>
							</c:when>
							<c:otherwise>
								<li>
									<a href="${pageContext.request.contextPath }/provider/query?queryProName=${queryProName}&queryProCode=${queryProCode}&currentPageNo=${index}">
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
				<li class="active"><a href="${pageContext.request.contextPath }/provider/query?queryProName=${queryProName}&queryProCode=${queryProCode}&currentPageNo=${currentPageNo}">${index}</a>
					</c:when>
					<c:otherwise>
				<li><a href="${pageContext.request.contextPath }/provider/query?queryProName=${queryProName}&queryProCode=${queryProCode}&currentPageNo=${index}">${index}</a>
					</c:otherwise>
					</c:choose>
					</c:forEach>
					</c:if>
					<c:if test="${currentPageNo!=totalPageCount&&totalPageCount!=0}">
				<li>
					<a href="${pageContext.request.contextPath }/provider/query?queryProName=${queryProName}&queryProCode=${queryProCode}&currentPageNo=${currentPageNo+1}" aria-label="Next">
						<span aria-hidden="true">&raquo;</span>
					</a>
				</li>
				</c:if>
				<c:if test="${currentPageNo==totalPageCount||totalPageCount==0}">
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
<div class="remove" id="removeProv">
   <div class="removerChid">
       <h2>提示</h2>
       <div class="removeMain" >
           <p>你确定要删除该供应商吗？</p>
           <a href="#" id="yes">确定</a>
           <a href="#" id="no">取消</a>
       </div>
   </div>
</div>


<script>
	function goQuery() {
		var currentPageNo=$("#currentPageNo").val();
		var queryProCode=$("#queryProCode").val();
		var queryProName=$("#queryProName").val();
		location.href="${pageContext.request.contextPath }/provider/query?queryProName="+queryProName+"&queryProCode="+queryProCode+"&currentPageNo="+currentPageNo;
    }
</script>
<%@include file="/jsp/common/foot.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/providerlist.js"></script>
