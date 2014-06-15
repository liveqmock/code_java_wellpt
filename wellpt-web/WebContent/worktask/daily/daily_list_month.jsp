 
<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/pt/common/header.jsp"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.wellsoft.pt.org.entity.User"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script>
	function openwin(uuid) {
		if (uuid != '') {
			window.open('${ctx}/worktask/daily/editpage.action?uuid=' + uuid);
		} else {
			window.open('${ctx}/worktask/daily/editpage.action');
		}
	}
	$() .ready( function() {
		$('#date01')
				.change(
						function() {

							window.location.href = '${ctx}/worktask/daily/list_month.action?day='
									+ $('#date01').val();
						});
	});
</script>
<body>
	<div class="form-actions"> 
		<button type="button" class="btn btn-primary" onclick="openwin('');"
			id="submitbtn">新建</button>
<a href="${ctx }/worktask/daily/list_this_week">按周查看</a>	<input type="text" class="input-xlarge datepicker"
												id="date01" value="<fmt:formatDate value="${today }" pattern="MM/dd/yyyy"/>">
	</div>

	<table class="table table-bordered table-striped table-condensed">
		<thead>
			<tr>

				<th width="14%">星期天</th>
				<th width="14%">星期一</th>
				<th width="14%">星期二</th>
				<th width="14%">星期三</th>
				<th width="14%">星期四</th>
				<th width="14%">星期五</th>
				<th width="14%">星期六</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${dailys}" var="item" varStatus="i">
				<c:if test="${i.index%7==0 }">
					<tr>
				</c:if>

				<td style="height:80px;margin: 0px;padding: 0px; " >
				 <div style=" width: 100%;height: 100%;margin: 0px;padding: 0px;">
				 	<span style="display:block;<c:if test="${item.today=='true' }">color:red;</c:if>">
				 	<c:if test="${item.no_month=='yes' }">
				 	<fmt:formatDate value="${item.date }" pattern="MM-dd"/>
				 	</c:if>
				 	<c:if test="${empty item.no_month }">
				 	<fmt:formatDate value="${item.date }" pattern="dd"/>
				 	</c:if><c:if test="${item.today=='true' }">今天</c:if>
				 	</span>
				 	&nbsp;&nbsp;
				<c:forEach items="${item.dailys }" var="subitem">
						<a href="#nogo" onclick="openwin('${subitem.uuid}')" >  
							我的日志<br />
						</a>
					</c:forEach>
				 </div>
				
				</td>

				<c:if test="${i.index%7==6 }">
					</tr>
				</c:if>
			</c:forEach>
		</tbody>
	</table>
	<div id="iframe_fix" class="clearfix block lines"
		style="height: 0px; width: 0px; visibility: hidden; clear: both;"></div>
</body>
</html>