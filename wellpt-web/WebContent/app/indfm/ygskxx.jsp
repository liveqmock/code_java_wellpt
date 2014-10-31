<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/pt/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="common/include.jsp"%>
<script src="${ctx}/resources/pt/js/org/unit/jquery.unit.js"></script>
<title>经理自助平台-员工刷卡信息</title>
</head>
<body style="overflow-x: hidden; overflow-y: auto;">
	<form method="post" action="" name="_LDX_YGZZCX_SKCXD">
		<input type="hidden" id="PT_LEADER_PERNR" value="${employeeNumber}">
		<table width="96%" border="2" align="center">
			<tr>
				<td width="67%" colspan="3"><font color="#0000FF">刷卡信息</font></td>
				<td width="33%"><div align="right">
						<input id="btnQuery" type="button" value="查询" class="l-button">
					</div></td>
			</tr>
			<tr>
				<td width="8%">员工编号:</td>
				<td width="42%"><input id="PI_PERNR" name="PI_PERNR"
					value="${employeeNumber}" style="width: 132px"></td>
				<!-- onfocus="this.blur();" -->
				<td width="8%">员工姓名:</td>
				<td width="42%"><input id="SQ_SNAME" name="SQ_SNAME"
					value="${userName}" style="width: 132px" onfocus="this.blur();"></td>
				<!--  -->
			</tr>
			<tr class="l-panel-header">
				<td width="100%" colspan="4">查询条件</td>
			</tr>
			<tr>
				<td width="8%">开始时间:</td>
				<td width="42%"><font face="宋体"> <input id="PI_BEGDA"
						name="PI_BEGDA" value=""></font></td>
				<td width="8%">结束时间:</td>
				<td width="42%"><font face="宋体"> <input id="PI_ENDDA"
						name="PI_ENDDA" value=""></font></td>
			</tr>
		</table>
		<table style="width: 96%; height: 82%;" align="center">
			<tr valign="top">
				<td>
					<div id="ID_A_GRID" align="center"></div>
				</td>
			</tr>
		</table>
	</form>
	<script src="${ctx}/resources/app/js/indfm/indfm.js"></script>
	<script type="text/javascript">
		var grid_skjl;
		function selectUser(){
			$.unit.open({
				title : "选择人员",
				labelField : "ygxm",
				valueField : "ygbh",
				type : "MyDept",
				multiple:false, 
				afterSelect : function(retVal) {
					$("#PI_PERNR").val(retVal.employeeNumber);
					$("#SQ_SNAME").val(retVal.name);						
				},
				selectType : 4	
			});
		}
		
		$(function() {
			var today = new Date().format("yyyy-MM-dd");

			var startData = $("#PI_BEGDA").ligerDateEditor({
				format : "yyyy-MM-dd",
				//label : '开始时间',
				labelWidth : 100,
				labelAlign : 'center',
				cancelable : true
			});
			startData.setValue(today.substring(0, 7) + "-01");

			var endData = $("#PI_ENDDA").ligerDateEditor({
				format : "yyyy-MM-dd",
				//label : '结束时间',
				labelWidth : 100,
				labelAlign : 'center',
				cancelable : true
			});
			endData.setValue(today);
			var jsonObj = {};
			$("#btnQuery").click(function() {
				var PI_PERNR = $("#PI_PERNR").val();
				var PI_BEGDA = $("#PI_BEGDA").val();
				var PI_ENDDA = $("#PI_ENDDA").val();
				//alert(type);
				qSKJL(PI_PERNR, PI_BEGDA, PI_ENDDA);
			});

			$("#PI_PERNR").click(function() {
				selectUser();
			});

			$("#SQ_SNAME").click(function() {
				selectUser();
			});

			grid_skjl = $("#ID_A_GRID").ligerGrid({
				columns : [ {
					display : '人员编号',
					name : 'PERNR',
					minWidth : 96,
					width : '16%'
				}, {
					display : '日期',
					name : 'LDATE',
					minWidth : 120,
					width : '26%'
				}, {
					display : '时间类型',
					name : 'DDTEXT',
					minWidth : 120,
					width : '24%'
				}, {
					display : '刷卡时间',
					name : 'LTIME',
					minWidth : 120,
					width : '26%'
				} ],
				//title : '刷卡信息',
				data : jsonObj,
				height : '96%',
				width : '100%',
				autoWidth : true,
				usePager : false,
				rownumbers : true
			});
		});
	</script>
</body>
</html>