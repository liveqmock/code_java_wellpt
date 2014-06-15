<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/pt/common/header.jsp"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.wellsoft.pt.org.entity.User"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script>
	function openwin(uuid) {
		 
	}
	$().ready(function(){
		$('#date01').change(function(){
			
			window.location.href='${ctx}/worktask/daily/list_share.action?day='+$('#date01').val();
		});
	});
</script>
<body>
	<div class="form-actions">  
		<a href="${ctx }/worktask/daily/list_month_share">按月查看</a>
		<input type="text" class="input-xlarge datepicker"
												id="date01" value="<fmt:formatDate value="${today }" pattern="MM/dd/yyyy"/>">
	</div>

	<c:forEach var="item" items="${dailys }" varStatus="i">
		<div class="row-fluid sortable" style="width: 100%">
			<div class="box span6" style="width: 100%">
				<div class="box-header well" data-original-title>
					<h2>
						<c:if test="${i.index==0 }">星期一</c:if>
						<c:if test="${i.index==1 }">星期二</c:if>
						<c:if test="${i.index==2 }">星期三</c:if>
						<c:if test="${i.index==3 }">星期四</c:if>
						<c:if test="${i.index==4 }">星期五</c:if>
						<c:if test="${i.index==5}">星期六</c:if>
						<c:if test="${i.index==6 }">星期天</c:if>
						<fmt:formatDate value="${item.date }" /><c:if test="${item.today=='true' }">今天</c:if>
					</h2>
					<div class="box-icon"></div>
				</div>
				<div class="box-content">
					<table class="table">

						<tr>
							<c:if test="${item.dailys_length==0 }">
								<td>没有安排</td>

							</c:if>
							<c:if test="${item.dailys_length>0 }">
								<td><c:forEach items="${item.dailys }" var="subitem">
										<a href="#nogo" onclick="openwin('${subitem.uuid}')"> <fmt:formatDate
												value="${subitem.createTime }" pattern="yyyy-MM-dd HH:mm:ss" />
											&nbsp;&nbsp;${subitem.dutyManName }<br/>
										</a>
									</c:forEach></td>

							</c:if>
						</tr>

					</table>

				</div>
			</div>
		</div>
	</c:forEach>
	<div id="iframe_fix" class="clearfix block lines"
		style="height: 0px; width: 0px; visibility: hidden; clear: both;"></div>
</body>
</html>