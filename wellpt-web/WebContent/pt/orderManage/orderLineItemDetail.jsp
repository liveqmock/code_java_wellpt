<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<title>订单行项目明细查询</title>
<style type="text/css">
html,body {
	width: 100%;
	height: 100%;
	padding: 0;
	margin: 0;
	overflow: auto; /* when page gets too small */
}

#container {
	background: #999;
	height: 100%;
	position: absolute;
	margin: 0 auto;
	width: 100%;
}

.pane {
	display: none; /* will appear when layout inits */
}

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
	<%@ include file="/pt/common/taglibs.jsp"%>
	<%@ include file="/pt/common/meta.jsp"%>
	<%@ include file="/pt/dyform/dyform_css.jsp"%>
	<%@ include file="/pt/dyform/dyform_js.jsp"%>
	<%@ include file="/pt/dyform/dyform_ctljs.jsp"%>
	<div class="div_body" style="width: 80%">
		<div class="form_header" style="">
			<div class="form_title">
				<h2>订单行项目明细查询</h2>
			</div>
			<div id="toolbar" class="form_toolbar">
				<div class="form_operate">
					<button id="form_save" type="button">保存</button>
					<button id="form_close" type="button">关闭</button>
				</div>
			</div>
		</div>
		<form action="" id="capacityform" class="dyform">
			<table>
				<tr align="left">
					<td class="title">sap订单号：<input type="text"
						name="vbeln" id="vbeln" style="width: 200px" />
						<button type="button" id="doSearch" style="font-size: 12px" onclick="clickDo()">搜索</button>
					</td>
				</tr>
			</table>
			<div style="overflow-X: scroll; height: 450px;">
			<table name="orderTable" id="orderTable" >
				<thead>
					<tr class="field" align="center">
						<th>SAP订单号</th>
						<th>行项目号</th>
						<th>物料</th>
						<th>物料描述</th>
						<th>数量</th>
					</tr>
				</thead>
				<tbody id="tbody">

				</tbody>
			</table>
			</div>
		</form>
	</div>
	<script type="text/javascript">
		function clickDo() {
			$.ajax({
						url : "${ctx}/orderManage/orderLineItemDetail/search",
						type : "POST",
						dataType : "json",
						data : {
							"vbeln" : $("#vbeln").val()
						},
						success : function(res) {
							var arr = eval(res);
							var h = "";
							for ( var i = 0; i < arr.length; i++) {
								var ord = arr[i];
								h += "<tr src='${ctx}/orderLineItemDetail/update?vbeln="+ord[1]+"&posnr=${"+ord[2]+" }'>";
								h += "<td>"+ord[1]+"</td>";
								h += "<td>"+ord[2]+"</td>";
								h += "<td>"+ord[3]+"</td>";
								h += "<td>"+ord[4]+"</td>";
								h += "<td>"+ord[5]+"</td></tr>";
							}
							$("#tbody").html(h);
						},
						error : function() {
							alert("请求失败");
						}
					});
		}
		function clickTr(vbe, pos) {

		}
		$(document).ready(function() {
			//window.location.reload();

			$("#form_close").click(function() {
				window.close();
			});
		});
	</script>
</body>
</html>