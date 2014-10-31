<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/pt/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="common/include.jsp"%>
<script src="${ctx}/resources/pt/js/org/unit/jquery.unit.js"></script>
<style type="text/css">
</style>
<title>经理自助平台-员工培训信息</title>
</head>
<body style="overflow-x: hidden; overflow-y: auto;">
	<form method="post" action="" name="_LDX_YGZZCX_SKCXD">
		<table width="96%" border="2" align="center">
			<tr>
				<td width="100%" colspan="3"><font color="#0000FF">员工培训信息</font></td>
				<td width="33%"><div align="right">
						<input id="btnQuery" type="button" value="查询" class="l-button">
					</div></td>
			</tr>
			<tr>
				<td width="10%">查询类型</td>
				<td width="90%" colspan="3"><input name="Surrogate_Type"
					type="hidden" value="1"><label><input id="Type01" type="radio"
						name="Type" value="02">按单个人员查询 </label><label>
						<input type="radio" name="Type" value="01" checked="checked">按部门查询
				</label></td>
			</tr>
			<tr id="ID_PT_PERNR">
				<td width="8%">员工编号</td>
				<td width="42%"><input id="PT_PERNR" name="PT_PERNR"
					value="${employeeNumber}" style="width: 30%" ></td>
				<td width="8%">员工姓名</td>
				<td width="42%"><input id="SQ_SNAME" name="SQ_SNAME"
					value="${userName}" style="width: 30%" onfocus="this.blur();"></td>
			</tr>
			<tr id="ID_OBJID" style="display: none;">
				<td width="8%">部门编号</td>
				<td width="42%"><input id="OBJID" name="OBJID" value=""
					style="width: 30%"></td>
				<td width="8%">部门名称</td>
				<td width="42%"><input id="DEPT_NAME" name="DEPT_NAME" value=""
					style="width: 36%" onfocus="this.blur();"></td>
			</tr>

			<tr>
				<td width="8%" valign="middle">年份</td>
				<td width="42%" valign="middle"><input
						id="PI_GJAHR" name="NF" value=""></td>
				<td width="8%" valign="middle">月份</td>
				<td width="42%" valign="middle"><select id="PI_MONAT" name="YF" style="width: 30%">
						<option value="01">1
						<option value="02">2
						<option value="03">3
						<option value="04">4
						<option value="05">5
						<option value="06">6
						<option value="07">7
						<option value="08">8
						<option value="09">9
						<option value="10">10
						<option value="11">11
						<option value="12">12
				</select></td>
			</tr>
		</table>
		<table style="width: 96%; height: 82%;" align="center">
			<tr valign="top">
				<td>
					<div id="ID_A_GRID" align="center"></div>
					<div id="ID_B_GRID" align="center" style="display: none"></div>
				</td>
			</tr>
		</table>

	</form>
	<script src="${ctx}/resources/app/js/indfm/indfm.js"></script>
	<script type="text/javascript">
		var grid_ygpxxx, grid_bmpxxx;

		function selectUser() {
			$.unit.open({
				title : "选择人员",
				labelField : "ygxm",
				valueField : "ygbh",
				type : "MyDept",
				multiple : false,
				afterSelect : function(retVal) {
					$("#PT_PERNR").val(retVal.employeeNumber);
					$("#SQ_SNAME").val(retVal.name);
				},
				selectType : 4
			});
		}
		function selectDept() {
			$.unit.open({
				title : "选择部门",
				type : "MyDept",
				multiple : false,
				afterSelect : function(retVal) {
					JDS.call({
						service : "departmentService.getById",
						data : retVal.id,
						async : false,
						success : function(result) {
							var data = result.data;
							if (typeof data != "undefined" && data != null) {
								$("#OBJID").val(data.externalId);
								$("#DEPT_NAME").val(data.name);
							}
						},
						error : function(result) {
							alert("旧系统不存在对应的部门编号");
						}
					});
				},
				selectType : 2
			});
		}

		$(function() {
			//$("#PI_MONAT").ligerComboBox();
			$("#PT_PERNR").click(function() {
				selectUser();
			});

			$("#SQ_SNAME").click(function() {
				selectUser();
			});

			$("#OBJID").click(function() {
				selectDept();
			});

			$("#DEPT_NAME").click(function() {
				selectDept();
			});
			$("#Type01").attr("checked", "checked");
			$('input[name="Type"]').click(function() {
				if ($(this).val() == "01") {
					$("#ID_PT_PERNR").hide();
					$("#ID_B_GRID").hide();
					$("#ID_OBJID").show();
					$("#ID_A_GRID").show();
				}
				if ($(this).val() == "02") {
					$("#ID_OBJID").hide();
					$("#ID_A_GRID").hide();
					$("#ID_PT_PERNR").show();
					$("#ID_B_GRID").show();
				}
			});
			var today = new Date().format("yyyy-MM-dd");

			$("#PI_GJAHR").val(today.substring(0, 4));

			$("#PI_MONAT").val(new Date().getMonth() + 1);

			$("#btnQuery").click(function() {
				var type = $('input[name="Type"]:checked').val();
				var PI_GJAHR = $("#PI_GJAHR").val();
				var PI_MONAT = $("#PI_MONAT").val();
				//alert(type);
				if (type == "02") {
					var PT_PERNR = $("#PT_PERNR").val();
					qYGPXXX(PT_PERNR, PI_GJAHR, PI_MONAT);
				} else if (type == "01") {
					var PT_DEPID = $("#OBJID").val();
					qBMPXXX(PT_DEPID, PI_GJAHR, PI_MONAT);
				}
			});
			var jsonObj = {};
			grid_bmpxxx = $("#ID_A_GRID").ligerGrid({
				columns : [ {
					display : '人员编号',
					name : 'PERNR',
					align : 'center',
					width : '12%'
				}, {
					display : '姓名',
					name : 'ENAME',
					width : '5%'
				}, {
					display : '培训级别',
					name : 'ZLEVEL',
					width : '16%'
				}, {
					display : '培训形式',
					name : 'ZTYPE',
					width : '16%'
				}, {
					display : '课程名称',
					name : 'ZNAME',
					align : 'center',
					width : '16%'
				}, {
					display : '开始日期',
					name : 'BEGDA',
					width : '16%'
				}, {
					display : '结束日期',
					name : 'ENDDA',
					width : '16%'
				}, {
					display : '参训课时',
					name : 'ZCOUTIME',
					width : '16%'
				}, {
					display : '考试成绩',
					name : 'ZGRADE',
					width : '16%'
				} ],
				//title : '部门考勤汇总',
				data : jsonObj,
				height : '96%',
				rownumbersColWidth : 42,
				width : '100%',
				autoWidth : true,
				usePager : false,
				rownumbers : true
			});
			grid_ygpxxx = $("#ID_B_GRID").ligerGrid({
				columns : [ {
					display : '人员编号',
					name : 'PERNR',
					align : 'center',
					width : '12%'
				}, {
					display : '姓名',
					name : 'ENAME',
					width : '5%'
				}, {
					display : '培训级别',
					name : 'ZLEVEL',
					width : '16%'
				}, {
					display : '培训形式',
					name : 'ZTYPE',
					width : '16%'
				}, {
					display : '课程名称',
					name : 'ZNAME',
					align : 'center',
					width : '16%'
				}, {
					display : '开始日期',
					name : 'BEGDA',
					width : '16%'
				}, {
					display : '结束日期',
					name : 'ENDDA',
					width : '16%'
				}, {
					display : '参训课时',
					name : 'ZCOUTIME',
					width : '16%'
				}, {
					display : '考试成绩',
					name : 'ZGRADE',
					width : '16%'
				}],
				//title : '个人考勤汇总',
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