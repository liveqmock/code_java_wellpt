<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<%@ include file="/pt/dyform/dyform_css.jsp"%>
<title>工作委托</title>
<link type="text/css" rel="stylesheet"
	href="${ctx}/resources/chosen/chosen.min.css" />
</head>
<script type="text/javascript">
	//全局变量
	var formUid = '${formUuid}';
</script>
<body>
<input type="hidden" id='uuid' value='${uuid}'/>
	<div class="div_body">
		<div class="div_body_content">
			<div class="form_header">
				<!--标题-->
				<div class="form_title">
					<h2>工作委托</h2>
					<div class="form_close" title="关闭"></div>
				</div>
				<div id="toolbar" class="form_toolbar"
					style="padding: 8px 0 10px 0px;">
					<div class="form_operate wf_operate">
						<button id="btn_save" class="btn">保存</button>
					</div>
				</div>	
			</div>
		<form id="duty_agent_form" class="dyform" action="">
			<table>
				<tbody>
					<tr>
						<td colspan="4" class="title">委托信息</td>
					</tr>
					<tr class="field">
						<td width="110">委托人</td>
						<td width="110" rowspan="1" colspan="3">
							<input type="text" class="editableClass" name="CONSIGNOR_NAME" id="CONSIGNOR_NAME">
						</td>
					</tr>
					<tr class="field" style="display:none;">
						<td width="110">委托人ID</td>
						<td width="110" rowspan="1" colspan="3">
							<input type="text" class="editableClass" name="CONSIGNOR" id="CONSIGNOR">
						</td>
					</tr>
					<tr class="field">
						<td width="110">受托人</td>
						<td width="110" rowspan="1" colspan="3">
							<input type="text" class="editableClass" name="TRUSTEE_NAME" id="TRUSTEE_NAME">
						</td>
					</tr>
					<tr class="field" style="display:none;">
						<td width="110">受托人ID</td>
						<td width="110" rowspan="1" colspan="3">
							<input type="text" class="editableClass" name="TRUSTEE" id="TRUSTEE">
						</td>
					</tr>
					<tr class="field">
						<td width="110">编号</td>
						<td width="110" rowspan="1" colspan="3">
							<input type="text" class="editableClass" name="CODE" id="CODE">
						</td>
					</tr>
					<tr class="field">
						<td width="110">业务类型</td>
						<td width="110" rowspan="1" colspan="3">
							<input type="text" class="editableClass" name="BUSINESS_NAME" id="BUSINESS_NAME">
							<input type="hidden" class="editableClass" name="BUSINESS_TYPE" id="BUSINESS_TYPE">
						</td>
					</tr>
					<tr class="field">
						<td width="110">委托内容</td>
						<td width="110" rowspan="1" colspan="3">
							<input type="text" class="editableClass" name="DISPLAY_CONTENT" id="DISPLAY_CONTENT">
						</td>
					</tr>
					<tr class="field" style="display:none;">
						<td width="110">委托内容真实值</td>
						<td width="110" rowspan="1" colspan="3">
							<input type="text" class="editableClass" name="CONTENT" id="CONTENT">
						</td>
					</tr>
					<tr class="field" style="display:none;">
						<td width="110">委托条件</td>
						<td width="110" rowspan="1" colspan="3">
							<input type="text" class="editableClass" name="CONDITION" id="CONDITION">
						</td>
					</tr>
					<tr class="field">
						<td width="110">状态</td>
						<td width="110" rowspan="1" colspan="3">
							<input id="status_active" name="STATUS" type="radio"
									value="1" />
									<label for="status_active">激活</label>
							<input id="status_deactive" name="STATUS" type="radio"
									value="0" />
									<label for="status_deactive">终止</label>
						</td>
					</tr>
					<tr class="field">
						<td width="110">开始时间</td>
						<td width="110" rowspan="1" colspan="3">
							<input type="text" class="editableClass" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" name="FROM_TIME" id="FROM_TIME">
						</td>
					</tr>
					<tr class="field">
						<td width="110">结束时间</td>
						<td width="110" rowspan="1" colspan="3">
							<input type="text" class="editableClass" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" name="TO_TIME" id="TO_TIME">
						</td>
					</tr>
				</tbody>
			</table>	
		</form>			
			<!-- Project -->
			<%@ include file="/pt/dyform/dyform_js.jsp"%>
	 		<script type="text/javascript"
				src="${ctx}/resources/pt/js/basicdata/view/view_org_entrust.js"></script>
			<script type="text/javascript"
				src="${ctx}/resources/chosen/chosen.jquery.min.js"></script>
			<script type="text/javascript"
				src="${ctx}/resources/pt/js/common/jquery.cmsWindown.js"></script>
		</div>
	</div>
	<div class="body_foot"></div>
</body>
</html>