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
<title>经理自助平台-员工考勤信息</title>
</head>
<body style="overflow-x: hidden; overflow-y: auto;">
	<form method="post" action="" name="_LDX_YGZZCX_SKCXD">
		<table width="96%" border="2" align="center">
			<tr>
				<td width="100%" colspan="3"><font color="#0000FF">考勤信息</font></td>
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
		var grid_ygkqhz, grid_bmkqhz;

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
					qYGKQHZ(PT_PERNR, PI_GJAHR, PI_MONAT);
				} else if (type == "01") {
					var PT_DEPID = $("#OBJID").val();
					qBMKQHZ(PT_DEPID, PI_GJAHR, PI_MONAT);
				}
			});
			var jsonObj = {};
			grid_bmkqhz = $("#ID_A_GRID").ligerGrid({
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
				//title : '部门考勤汇总',
				data : jsonObj,
				height : '96%',
				rownumbersColWidth : 42,
				width : '100%',
				autoWidth : true,
				usePager : false,
				rownumbers : true
			});
			grid_ygkqhz = $("#ID_B_GRID").ligerGrid({
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