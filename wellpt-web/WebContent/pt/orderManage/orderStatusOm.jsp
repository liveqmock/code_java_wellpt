<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<%@ include file="/pt/dyform/dyform_css.jsp"%>
<%@ include file="/pt/dyform/dyform_js.jsp"%>
<%@ include file="/pt/dyform/dyform_ctljs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>OM订单状态汇总查询</title>
<style type="text/css">
table {
	width: 100%;
	border-color: gray;
	color: #2E2E2E;
	font-family: "Microsoft YaHei";
	font-size: 12px;
	text-align: center;
}

table tr {
	white-space: nowrap;
}

table th {
	font-family: "Microsoft YaHei";
	font-size: 12px;
	text-align: center;
	border: 1px solid #C9C9C9;
}

table td {
	border: medium none;
	font-family: "Microsoft YaHei";
	font-size: 12px;
	height: 20px;
	border: 1px solid #C9C9C9;
}
</style>
</head>
<body>
	<div class="div_body" style="">
		<div class="form_header" style="">
			<div class="form_title">
				<h2>OM订单状态汇总查询</h2>
			</div>
			<div id="toolbar" class="form_toolbar">
				<div class="form_operate">
					<button id="form_close" type="button">关闭</button>
				</div>
			</div>
		</div>
		<form action="${ctx }/orderManage/omStatus/search" id="capacityform"
			class="dyform">
			<table>
					<tr>
						<td class="title" colspan="5" align="left">客户：<input
							type="text" name="sortl" id="sortl" style="width: 200px" />
							<button type="button" id="doSearch">搜索</button>
						</td>
					</tr>
				</table>
			<div style="height: 600px;overflow:scroll;">
				<table>
					<thead>
					<tr>
						<th>客户</th>
						<th>待包装</th>
						<th>待包装—预警</th>
						<th>待包装—delay</th>
						<th>待出货</th>
						<th>待出货—预警</th>
						<th>待出货—delay</th>
						<th>尾数待出货</th>
						<th>待订舱</th>
						<th>待订舱—预警</th>
						<th>待订舱—delay</th>
						<th>已完成出货</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>合计：</td>
							<td>${dbz }</td>
							<td>${dbzyj }</td>
							<td>${dbzd }</td>
							<td>${dch }</td>
							<td>${dchyj }</td>
							<td>${dchd }</td>
							<td>${wsdch }</td>
							<td>${ddc }</td>
							<td>${ddcyj }</td>
							<td>${ddcd }</td>
							<td>${yhbhe }</td>
						</tr>
						<c:forEach var="entity" items="${orderList }">
							<tr>
								<td><c:if test="${entity[0]!='0' }">${entity[0]}</c:if></td>
								<td><c:if test="${entity[1]!='0' }">${entity[1]}</c:if></td>
								<td><c:if test="${entity[2]!='0' }">${entity[2]}</c:if></td>
								<td><c:if test="${entity[3]!='0' }">${entity[3]}</c:if></td>
								<td><c:if test="${entity[4]!='0' }">${entity[4]}</c:if></td>
								<td><c:if test="${entity[5]!='0' }">${entity[5]}</c:if></td>
								<td><c:if test="${entity[6]!='0' }">${entity[6]}</c:if></td>
								<td><c:if test="${entity[7]!='0' }">${entity[7]}</c:if></td>
								<td><c:if test="${entity[8]!='0' }">${entity[8]}</c:if></td>
								<td><c:if test="${entity[9]!='0' }">${entity[9]}</c:if></td>
								<td><c:if test="${entity[10]!='0' }">${entity[10]}</c:if></td>
								<td><c:if test="${entity[11]!='0' }">${entity[11]}</c:if></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</form>
	</div>
</body>
<script>
	$(document).ready(function() {

		$("#form_close").click(function() {
			window.close();
		});
		$("#doSearch").click(function() {
			$("#capacityform").submit();
		});
	});
</script>
</html>