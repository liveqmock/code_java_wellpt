<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/pt/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="common/include.jsp"%>
<title>员工自助平台-考勤信息</title>
</head>
<body style="overflow: hidden;">
	<form method="post" action="" name="_LDX_YGZZCX_SKCXD">
		<table width="96%" border="2" align="center">
			<tr>
				<td width="67%" colspan="3"><font color="#0000FF">考勤信息查询</font></td>
				<td width="33%"><div align="right">
						<input id="btnQuery" type="button" value="查询" class="l-button"><!--   -->
					</div></td>
			</tr>
			<tr>
				<td width="8%">员工编号</td>
				<td width="42%"><input name="PI_PERNR" id="PI_PERNR"
					value="${employeeNumber}" onfocus="this.blur();"></td>
				<td width="8%">查询类型</td>
				<td width="42%"><input type="radio" id="Type01" name="Type"
					value="01" checked="checked">调休定额</label><label> <input
						type="radio" name="Type" value="02">请假信息
				</label><label> <input type="radio" name="Type" value="03">外出出差信息
				</label><label> <input type="radio" name="Type" value="04">月度考勤汇总
				</label></td>
			</tr>
			<tr class="l-panel-header">
				<td width="96%" colspan="4" id="qTitle">调休定额查询</td>
			</tr>
			<tr id="ID_A">
				<td width="8%">可调休小时数</td>
				<td width="42%"><input id="RESULT" name="RESULT" value=""></td>
			</tr>
			<tr id="ID_QUERY" style="display: none;">
				<td width="8%">年度</td>
				<td width="42%"><input id="PI_GJAHR" name="PI_GJAHR"
					value="2014" style="width: 30%"></td>
				<td width="8%">月份</td>
				<td width="42%"><select id="PI_MONAT" name="PI_MONAT"
					style="width: 30%">
						<option value="1">1
						<option value="2">2
						<option value="3">3
						<option value="4">4
						<option value="5">5
						<option value="6">6
						<option value="7">7
						<option value="8">8
						<option value="9">9
						<option value="10">10
						<option value="11">11
						<option value="12">12
				</select></td>
			</tr>			
		</table>
		<!-- 
		<table id="ID_A" style="width: 96%;" border="2" align="center">
		</table>
		<table id="ID_QUERY" style="width: 96%; display: none;" border="2"
			align="center">
			<tr>
				<td width="100%" colspan="4">请假信息查询</td>
			</tr>
		</table>
		 -->
		<table style="width: 96%; height: 82%;" border="2" align="center">
			<tr valign="top">
				<td>
					<div id="ID_B_GRID" style="display: none;"></div>
					<div id="ID_C_GRID" style="display: none;"></div>
					<div id="ID_D_GRID" style="display: none;"></div>
			</tr>
			</td>
		</table>

	</form>
	<script src="${ctx}/resources/app/js/indfm/indfm.js"></script>
	<script type="text/javascript">
		var grid_qjxx, grid_wcccxx, grid_ygkqhz;
		$(function() {
			$("#Type01").attr("checked", "checked");
			$('input[name="Type"]').click(function() {
				if ($(this).val() == "01") {
					$("#ID_C_GRID").hide();
					$("#ID_D_GRID").hide();
					$("#ID_B_GRID").hide();
					$("#ID_QUERY").hide();
					$("#ID_A").show();
					$("#qTitle").html("调休定额查询");
				}
				if ($(this).val() == "02") {
					$("#ID_C_GRID").hide();
					$("#ID_D_GRID").hide();
					$("#ID_A").hide();
					$("#ID_QUERY").show();
					$("#ID_B_GRID").show();
					$("#qTitle").html("请假信息查询");
				}
				if ($(this).val() == "03") {
					$("#ID_D_GRID").hide();
					$("#ID_B_GRID").hide();
					$("#ID_A").hide();
					$("#ID_QUERY").show();
					$("#ID_C_GRID").show();
					$("#qTitle").html("外出\出差信息查询");
				}
				if ($(this).val() == "04") {
					$("#ID_C_GRID").hide();
					$("#ID_B_GRID").hide();
					$("#ID_A").hide();
					$("#ID_QUERY").show();
					$("#ID_D_GRID").show();
					$("#qTitle").html("月度考勤汇总");
				}
			});

			var today = new Date().format("yyyy-MM-dd");

			$("#PI_GJAHR").val(today.substring(0, 4));

			$("#PI_MONAT").val(new Date().getMonth() + 1);

			$("#btnQuery").click(function() {
				var type = $('input[name="Type"]:checked').val();
				var PI_PERNR = $("#PI_PERNR").val();
				var PI_GJAHR = $("#PI_GJAHR").val();
				var PI_MONAT = $("#PI_MONAT").val();
				//alert(type);
				if (type == "01") {
					qTXDE(PI_PERNR);
				} else if (type == "02") {
					qQJXX(PI_PERNR, PI_GJAHR, PI_MONAT);
				} else if (type == "03") {
					qWCCCXX(PI_PERNR, PI_GJAHR, PI_MONAT);
				} else if (type == "04") {
					qYGKQHZ(PI_PERNR, PI_GJAHR, PI_MONAT);
				}
			});
			var jsonObj = {};
			grid_qjxx = $("#ID_B_GRID").ligerGrid({
				columns : [ {
					display : '人员编号',
					name : 'PERNR',
					minWidth : 96,
					width : '8%'
				}, {
					display : '假期类别',
					name : 'ATEXT',
					minWidth : 96,
					width : '12%'
				}, {
					display : '开始日期',
					name : 'BEGDA',
					minWidth : 96,
					width : '12%'
				}, {
					display : '开始时间',
					name : 'BEGUZ',
					minWidth : 96,
					width : '12%'
				}, {
					display : '结束日期',
					name : 'ENDDA',
					minWidth : 96,
					width : '12%'
				}, {
					display : '结束时间',
					name : 'ENDUZ',
					minWidth : 96,
					width : '12%'
				}, {
					display : '请假原因',
					name : 'SATZA',
					minWidth : 96,
					width : '12%'
				}, {
					display : '合计天数',
					name : 'ABWTG',//HJTS
					minWidth : 96,
					width : '8%'
				} ],
				//title : '请假信息查询',
				data : jsonObj,
				height : '96%',
				width : '100%',
				usePager : false,
				rownumbers : true
			});

			grid_wcccxx = $("#ID_C_GRID").ligerGrid({
				columns : [ {
					display : '员工编号',
					name : 'PERNR',
					align : 'center',
					width : '8%'
				}, {
					display : '类别',
					name : 'ATEXT',
					width : '8%'
				}, {
					display : '开始日期',
					name : 'BEGDA',
					width : '12%'
				}, {
					display : '开始时间',
					name : 'LDATE',
					width : '12%'
				}, {
					display : '结束日期',
					name : 'ENDDA',
					width : '12%'
				}, {
					display : '结束时间',
					name : 'ENDUZ',
					width : '12%'
				}, {
					display : '事由',
					name : 'SATZA',
					width : '12%'
				}, {
					display : '合计天数',
					name : 'ABWTG',
					width : '12%'
				} ],
				//title : '外出\出差信息查询',
				data : jsonObj,
				height : '96%',
				width : '100%',
				usePager : false,
				rownumbers : true
			});

			grid_ygkqhz = $("#ID_D_GRID").ligerGrid({
				columns : [ {
					display : '一级部门',
					name : 'STEXT1',
					align : 'center',
					width : '8%'
				}, {
					display : '二级部门',
					name : 'STEXT2',
					width : '5%'
				}, {
					display : '三级部门',
					name : 'STEXT3',
					width : '5%'
				}, {
					display : '四级部门',
					name : 'STEXT4',
					width : '5%'
				}, {
					display : '岗位',
					name : 'STEXT5',
					align : 'center',
					width : '5%'
				}, {
					display : '人员编号',
					name : 'PERNR',
					width : '5%'
				}, {
					display : '姓名',
					name : 'ENAME',
					width : '5%'
				}, {
					display : '病假',
					name : 'Z008',
					width : '5%'
				}, {
					display : '事假',
					name : 'Z010',
					width : '5%'
				}, {
					display : '年休假',
					name : 'Z110',
					width : '5%'
				}, {
					display : '调休假',
					name : 'Z021',
					width : '5%'
				}, {
					display : '婚假',
					name : 'Z112',
					align : 'center',
					width : '5%'
				}, {
					display : '丧假',
					name : 'Z114',
					width : '5%'
				}, {
					display : '产假',
					name : 'Z116',
					width : '5%'
				}, {
					display : '陪护假',
					name : 'Z118',
					width : '5%'
				}, {
					display : '路程假',
					name : 'Z120',
					width : '5%'
				}, {
					display : '哺乳假',
					name : 'Z122',
					width : '5%'
				}, {
					display : '产检假',
					name : 'Z124',
					width : '5%'
				}, {
					display : '工伤假',
					name : 'Z126',
					width : '5%'
				}, {
					display : '旷工',
					name : 'Z103',
					width : '5%'
				}, {
					display : '迟到（次）',
					name : 'Z013',
					width : '5%'
				}, {
					display : '早退（次）',
					name : 'Z014',
					align : 'center',
					width : '5%'
				}, {
					display : '补卡次数',
					name : 'Z106',
					width : '5%'
				}, {
					display : '平时加班',
					name : 'Z134',
					width : '5%'
				}, {
					display : '周末加班',
					name : 'Z135',
					width : '5%'
				}, {
					display : '节假日加班',
					name : 'Z136',
					align : 'center',
					width : '5%'
				}, {
					display : '外出办事',
					name : 'Z128',
					width : '5%'
				}, {
					display : '外出培训',
					name : 'Z130',
					width : '5%'
				}, {
					display : '出差',
					name : 'Z132',
					width : '5%'
				} ],
				//title : '月度考勤汇总',
				data : jsonObj,
				height : '96%',
				rownumbersColWidth : 42,
				width : '100%',
				autoWidth : true,
				usePager : false,
				rownumbers : true
			});
		});
	</script>
</body>
</html>