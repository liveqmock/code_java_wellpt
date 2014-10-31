<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/pt/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="common/include.jsp"%>
<title>员工自助平台-刷卡信息</title>
</head>
<body style="overflow: hidden;">
	<form method="post" action="" name="_LDX_YGZZCX_SKCXD">
		<table border="2" width="96%" align="center">
			<tr>
				<td width="67%" colspan="3"><font color="#0000FF">刷卡信息查询</font></td>
				<td width="33%"><div align="right">
						<input id="btnQuery" type="button" value="查询" class="l-button">
					</div></td>
			</tr>
			<tr>
				<td width="8%">员工编号</td>
				<td width="42%"><font face="宋体"> <input id="PI_PERNR"
						name="PI_PERNR" value="${employeeNumber}" onfocus="this.blur();"
						style="width: 132px"></font></td>
				<td width="8%">查询类型</td>
				<td width="42%"><label> <input type="radio" name="Type"
						value="01" checked="checked">刷卡记录查询
				</label><label> <input type="radio" name="Type" value="02">考勤异常查询
				</label></td>
			</tr>
			<tr class="l-panel-header">
				<td width="100%" colspan="4">查询条件</td>
			</tr>
			<tr>
				<td width="8%">开始时间</td>
				<td width="42%"><font face="宋体"> <input id="PI_BEGDA"
						name="PI_BEGDA" value="20141001"></font></td>
				<td width="8%">结束时间</td>
				<td width="42%"><font face="宋体"> <input id="PI_ENDDA"
						name="PI_ENDDA" value="20141020"></font></td>
			</tr>
		</table>
		<table width="96%" height="82%" align="center">
			<tr valign="top">
				<td>
					<div id="ID_B" style="display: none;"></div>
					<div id="ID_A"></div>
				</td>
			</tr>
		</table>

	</form>
	<script src="${ctx}/resources/app/js/indfm/indfm.js"></script>
	<script type="text/javascript">
		var grid_skjl, grid_kqyc;
		$(function() {
			$('input[name="Type"]').click(function() {
				if ($(this).val() == "01") {
					$("#ID_B").hide();
					$("#ID_A").show();
				}
				if ($(this).val() == "02") {
					$("#ID_A").hide();
					$("#ID_B").show();
				}
			});

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
				var type = $('input[name="Type"]:checked').val();
				//alert(type);
				var PI_PERNR = $("#PI_PERNR").val();
				var PI_BEGDA = $("#PI_BEGDA").val();
				var PI_ENDDA = $("#PI_ENDDA").val();
				if (type == "01") {
					qSKJL(PI_PERNR, PI_BEGDA, PI_ENDDA);
				} else if (type == "02") {
					qKQYC(PI_PERNR, PI_BEGDA, PI_ENDDA);
				}
			});

			grid_skjl = $("#ID_A").ligerGrid({
				columns : [ {
					display : '人员编号',
					name : 'PERNR',
					width : '16%'
				}, {
					display : '时间类型',
					name : 'DDTEXT',
					width : '24%'
				}, {
					display : '刷卡时间',
					name : 'LTIME',
					width : '26%'
				}, {
					display : '刷卡日期',
					name : 'LDATE',
					width : '26%'
				} ],
				//title : '刷卡记录查询',
				data : jsonObj,
				height : '96%',
				width : '100%',
				usePager : false,
				rownumbers : true
			});

			grid_kqyc = $("#ID_B").ligerGrid({
				columns : [ {
					display : '异常类型',
					name : 'ERROR',
					align : 'center',
					width : '16%'
				}, {
					display : '异常原因',
					name : 'ETEXT',
					width : '26%'
				}, {
					display : '星期',
					name : 'KURZT',
					width : '20%'
				}, {
					display : '异常日期',
					name : 'LDATE',
					width : '26%'
				} ],
				//title : '考勤异常查询',
				data : jsonObj,
				height : '96%',
				width : '100%',
				usePager : false,
				rownumbers : true
			});
		});
	</script>
</body>
</html>