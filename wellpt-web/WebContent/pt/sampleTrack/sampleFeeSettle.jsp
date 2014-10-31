<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>样品费用结算</title>
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
	<%@ include file="/pt/common/taglibs.jsp"%>
	<%@ include file="/pt/common/meta.jsp"%>
	<%@ include file="/pt/dyform/dyform_css.jsp"%>
	<%@ include file="/pt/dyform/dyform_js.jsp"%>
	<%@ include file="/pt/dyform/dyform_ctljs.jsp"%>
	<div class="div_body"
		style="overflow-X: scroll; height: 700; width: 99%;">
		<div class="form_header" style="">
			<div class="form_title">
				<h2>样品费用结算</h2>
			</div>
			<div id="toolbar" class="form_toolbar">
				<div class="form_operate">
					<button id="form_export" type="button">报表导出</button>
					<button id="form_close" type="button">关闭</button>
				</div>
			</div>
		</div>
		<form action="" id="capacityform" class="dyform">
			<div>
				<table>
					<tr>
						<td>分组条件:</td>
						<td><select id="groupCondition" name="groupCondition">
								<option value="部门名称">部门名称</option>
								<option value="客户代码">客户代码</option>
						</select></td>
						<td>年份:</td>
						<td><select id="year" name="year">
								<option value="2013">2013</option>
								<option value="2014" selected="selected">2014</option>
								<option value="2015">2015</option>
						</select></td>
						<td>费用承担方:</td>
						<td><select id="sampleCharge1" name="sampleCharge1">
								<option value="我司">我司</option>
								<option value="客户">客户</option>
						</select></td>
						<td>生产方式:</td>
						<td><select id="productMode1" name="productMode1">
								<option value="1">自制</option>
								<option value="2">外购</option>
								<option value="3">外购&自制</option>
						</select></td>
						<td colspan="10">
							<button id="search" onclick="return false;" style="font-size: 12px">查找</button>
						</td>
					</tr>
				</table>
				<div style="overflow-X: scroll; height: 450px;">
					<table id="entityTable" style="width: 3000px; font-size: 12px">
						<thead>
							<tr class="field" align="center">
								<!--<c:if test="${groupCondition== '部门名称'}">-->
								<th rowspan="2" id="gC" style="font-size: 14px"></th>
								<!--</c:if>	
		    <c:if test="${groupCondition== '客户代码'}">
		    	<th rowspan="2">客户代码</th>
		    </c:if>		-->
								<th colspan="2">1月</th>
								<th colspan="2">2月</th>
								<th colspan="2">3月</th>
								<th colspan="2">4月</th>
								<th colspan="2">5月</th>
								<th colspan="2">6月</th>
								<th colspan="2">7月</th>
								<th colspan="2">8月</th>
								<th colspan="2">9月</th>
								<th colspan="2">10月</th>
								<th colspan="2">11月</th>
								<th colspan="2">12月</th>
							</tr>
							<tr class="field" align="center">
								<th>样品成本(RMB)</th>
								<th>PI金额(USD)</th>
								<th>样品成本(RMB)</th>
								<th>PI金额(USD)</th>
								<th>样品成本(RMB)</th>
								<th>PI金额(USD)</th>
								<th>样品成本(RMB)</th>
								<th>PI金额(USD)</th>
								<th>样品成本(RMB)</th>
								<th>PI金额(USD)</th>
								<th>样品成本(RMB)</th>
								<th>PI金额(USD)</th>
								<th>样品成本(RMB)</th>
								<th>PI金额(USD)</th>
								<th>样品成本(RMB)</th>
								<th>PI金额(USD)</th>
								<th>样品成本(RMB)</th>
								<th>PI金额(USD)</th>
								<th>样品成本(RMB)</th>
								<th>PI金额(USD)</th>
								<th>样品成本(RMB)</th>
								<th>PI金额(USD)</th>
								<th>样品成本(RMB)</th>
								<th>PI金额(USD)</th>
							</tr>
						</thead>
						<tbody id="tbody">
							<c:forEach var="entity" items="${list }">
								<tr class="field">
									<td>${entity[0]}</td>
									<td>${entity[1]}</td>
									<td>${entity[2]}</td>
									<td>${entity[3]}</td>
									<td>${entity[4]}</td>
									<td>${entity[5]}</td>
									<td>${entity[6]}</td>
									<td>${entity[7]}</td>
									<td>${entity[8]}</td>
									<td>${entity[9]}</td>
									<td>${entity[10]}</td>
									<td>${entity[11]}</td>
									<td>${entity[12]}</td>
									<td>${entity[13]}</td>
									<td>${entity[14]}</td>
									<td>${entity[15]}</td>
									<td>${entity[16]}</td>
									<td>${entity[17]}</td>
									<td>${entity[18]}</td>
									<td>${entity[19]}</td>
									<td>${entity[20]}</td>
									<td>${entity[21]}</td>
									<td>${entity[22]}</td>
									<td>${entity[23]}</td>
									<td>${entity[24]}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				<div>
					<label style="color: blue; font-size: 12px; line-height: 28px;">备注：(每个月费用的计算周期为：上个月的26号到本月的25号)</label>
				</div>
			</div>
		</form>
	</div>
	<script>
		function dosearch() {
			$.ajax({
				url : "${ctx}/sampleTrack/sampleFeeSettle/search",
				type : "POST",
				dataType : "json",
				async : false,
				data : {
					"groupCondition" : $("#groupCondition").val(),
					"year" : $("#year").val(),
					"sampleCharge1" : $("#sampleCharge1").val(),
					"productMode1" : $("#productMode1").val()
				},
				success : function(res) {
					var arr = eval(res);
					var h = "";
					for ( var i = 0; i < arr.length; i++) {
						var ord = arr[i];
						h += "<tr class='field'>";
						h += "<td>" + ord[0] + "</td>";
						h += "<td>" + ord[1] + "</td>";
						h += "<td>" + ord[2] + "</td>";
						h += "<td>" + ord[3] + "</td>";
						h += "<td>" + ord[4] + "</td>";
						h += "<td>" + ord[5] + "</td>";
						h += "<td>" + ord[6] + "</td>";
						h += "<td>" + ord[7] + "</td>";
						h += "<td>" + ord[8] + "</td>";
						h += "<td>" + ord[9] + "</td>";
						h += "<td>" + ord[10] + "</td>";
						h += "<td>" + ord[11] + "</td>";
						h += "<td>" + ord[12] + "</td>";
						h += "<td>" + ord[13] + "</td>";
						h += "<td>" + ord[14] + "</td>";
						h += "<td>" + ord[15] + "</td>";
						h += "<td>" + ord[16] + "</td>";
						h += "<td>" + ord[17] + "</td>";
						h += "<td>" + ord[18] + "</td>";
						h += "<td>" + ord[19] + "</td>";
						h += "<td>" + ord[20] + "</td>";
						h += "<td>" + ord[21] + "</td>";
						h += "<td>" + ord[22] + "</td>";
						h += "<td>" + ord[23] + "</td>";
						h += "<td>" + ord[24] + "</td></tr>";
					}
					if (arr.length <= 0) {
						alert("没有找到数据");
					}
					$("#tbody").html(h);
					$("#gC").html($("#groupCondition").val());
				},
				error : function() {
					alert("请求失败");
				}
			})
		}
		$(document).ready(function() {
			$("#gC").html($("#groupCondition").val());
			$("#form_close").click(function() {
				window.close();
			});
			$("#search").click(dosearch);
			$("#form_export").click(function(){
				var g = $("#groupCondition").val(),
				y = $("#year").val(),
				s = $("#sampleCharge1").val(),
				p = $("#productMode1").val();
				var url = ctx + "/qcCheckResult/sampleFeeSettle/export?groupCondition="+g+"&year="+y+"&sampleCharge1="+s+"&productMode1="+p;
				$("#capacityform").attr("action",url);
				$("#capacityform").submit();
			})
		});
	</script>
</body>
</html>