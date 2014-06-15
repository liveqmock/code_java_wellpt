<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<title>Duty Agent List</title>

<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/jqueryui/css/base/jquery-ui.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/jqgrid/css/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/layout/jquery.layout.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/ztree/css/zTreeStyle/zTreeStyle.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/pt/css/custom-admin-style.css" />
</head>
<body>
	<div class="ui-layout-west">
		<div>
			<div class="btn-group btn-group-top">
				<div class="query-fields">
					<input id="query_duty_agent" name="query_duty_agent" />
					<button id="btn_query" type="button" class="btn">查询</button>
				</div>
				<button id="btn_add" type="button" class="btn">新 增</button>
				<button id="btn_del_all" type="button" class="btn">删除</button>
			</div>
			<table id="list"></table>
			<div id="pager"></div>
		</div>
	</div>
	<div class="ui-layout-center">
		<div>
			<form:form commandName="dutyAgent" action="" id="duty_agent_form">
				<div class="tabs">
					<ul>
						<li><a href="#tabs-1">基本信息</a></li>
					</ul>
					<input type="hidden" id="uuid" name="uuid" />
					<div id="tabs-1">
						<table>
							<tr>
								<td style="width: 65px;"><label>委托人</label></td>
								<td><input id="consignorName" name="consignorName" value=""
									type="text" class="full-width" /> <input id="consignor"
									name="consignor" type="hidden" /></td>
								<td></td>
							</tr>
							<tr>
								<td><label>受托人</label></td>
								<td><input id="trusteeName" name="trusteeName" type="text"
									class="full-width" /> <input id="trustee" name="trustee"
									type="hidden" /></td>
								<td></td>
							</tr>
							<tr>
								<td><label>编号</label></td>
								<td><input id="code" name="code" type="text"
									class="full-width" /></td>
								<td></td>
							</tr>
							<tr>
								<td><label>业务类型</label></td>
								<td><form:select path="businessType"
										items="${businessTypes}" class="full-width"></form:select></td>
							</tr>
							<tr>
								<td class="align-top"><label>委托内容</label></td>
								<td><textarea id="contentName" name="contentName"
										class="full-width"></textarea> <input id="content"
									name="content" type="hidden" /></td>
							</tr>
							<tr>
								<td><label>委托条件</label></td>
								<td><input id="condition" name="condition" type="text"
									class="full-width"></td>
								<td></td>
							</tr>
							<tr>
								<td><label>状态</label></td>
								<td><input id="status_active" name="status" type="radio"
									value="1" checked="checked" /><label for="status_active">激活</label>
									<input id="status_deactive" name="status" type="radio"
									value="0" /><label for="status_deactive">终止</label></td>
								<td></td>
							</tr>
							<tr>
								<td><label>开始时间</label></td>
								<td><input id="formatedFromTime" name="formatedFromTime"
									type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})"
									class="Wdate full-width" /></td>
								<td></td>
							</tr>
							<tr>
								<td><label>结束时间</label></td>
								<td><input id="formatedToTime" name="formatedToTime"
									type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})"
									class="Wdate full-width" /></td>
								<td></td>
							</tr>
						</table>
					</div>
				</div>
				<div class="btn-group btn-group-bottom">
					<button id="btn_save" type="button" class="btn">保存</button>
					<button id="btn_del" type="button" class="btn">删除</button>
				</div>
			</form:form>
		</div>
	</div>

	<!-- Project -->
	<script type="text/javascript" src="${ctx}/resources/jquery/jquery.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/jqueryui/js/jquery-ui.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/jqgrid/js/i18n/grid.locale-cn.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/jqgrid/js/jquery.jqGrid.js"></script>
	<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/layout/jquery.layout.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/form/jquery.form.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/org/unit/jquery.unit.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/ztree/js/jquery.ztree.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/common/jquery.comboTree.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/system_admin.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/org/duty_agent.js"></script>
</body>
</html>