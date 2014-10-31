<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>

<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
 
<title id="title">显示单据</title>

<script type="text/javascript"
	src="${ctx}/resources/jquery/jquery.js"></script>
<script type="text/javascript"
		src="${ctx}/resources/bootstrap/js/bootstrap.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/utils/ajaxfileupload.js"></script>
<script type="text/javascript" src="${ctx}/resources/utils/json2.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/pt/js/dyform/common/dyform_constant.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/validate/js/jquery.validate.js"></script>
<!-- 表单自定义验证方法 -->
<script type="text/javascript"
		src="${ctx}/resources/pt/js/dyform/validate/additional-methods.js"></script>
		
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/jqueryui/css/base/jquery-ui.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/jqgrid/css/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css" media="screen"
	href="${ctx}/resources/jqgrid/themes/ui.multiselect.css" />

<script type="text/javascript"
		src="${ctx}/resources/jqueryui/js/jquery-ui.js"></script>
<script src="${ctx}/resources/jqgrid/js/src/i18n/grid.locale-cn.js"
	type="text/javascript"></script>
<script type="text/javascript">
	$.jgrid.no_legacy_api = true;
	$.jgrid.useJSON = true;
</script>
 
<script src="${ctx}/resources/jBox/jquery.jBox.src.js"
	type="text/javascript"></script>
	<script src="${ctx}/resources/pt/js/common/jquery.dytableTree.js"
	type="text/javascript"></script>
	
	
<script src="${ctx}/resources/jBox/i18n/jquery.jBox-zh-CN.js"
	type="text/javascript"></script>
 
<!-- Bootstrap -->
<link rel="stylesheet"
	href="${ctx}/resources/bootstrap/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/pt/css/custom-admin-style.css" />
	
<link rel="stylesheet"
	href="${ctx}/resources/ztree/css/zTreeStyle/zTreeStyle.css"
	type="text/css" /> 
	
		<%@ include file="/pt/dyform/dyform_ctljs.jsp"%>


<link rel="stylesheet" type="text/css" media="screen"
	href="${ctx}/resources/pt/css/dyform/dyform.css" />

<style type="text/css">
body,table,input,select {
	font-size: 12px
}

#fs3 legend {
    margin-bottom: 0;
}
.ui-button { font-weight: normal; }
.ui-jqgrid .ui-icon-asc { margin-left: 1px; }
#uploadFile {
	width: 185px;
}

.be-selected{
	background: #D2E6F5;
}

</style>
</head>

<body style="font-size: 12px;">
<input type="hidden" id="flag" name="flag" value="${flag}">
<div class="div_body">
	<div class="form_header">
		<!--标题-->
		<div class="form_title">
			<h2 id="title_h2"></h2>
			<div class="form_close" title="关闭"></div>
		</div>
		<div id="toolbar" class="form_toolbar">
			<div class="form_operate">
				<button id="btn_form_save" name="btn_model_save">保存</button>  
			</div>
		</div>
	</div>
	<div class="form_content">
		<div class="tabbable" >
			<ul class="nav nav-tabs">
				<li class="active"><a href="#tab1" data-toggle="tab">基本属性</a></li>
				<li><a href="#tab2" data-toggle="tab">显示单据设计</a></li>
			</ul>
			<div class="tab-content">
				<div class="tab-pane active" id="tab1" style="height:250px;">
					<div class="dyform">
						<div class="post-sign">
							<div class="post-detail">
							
							<form id="mainForm" name="mainForm" style="margin: 0px;">
								<input type="hidden" name="uuid" id="uuid" value="${uuid}" /> 
						<table>
							<tr>
								<td class="Label" style="width:20%">参照表单</td>
								<td class="value" ><input type="text" name="referredFormName"
									id="referredFormName" value="" readonly="readonly"/>
									<input type="hidden" name="referredFormId"
									id="referredFormId" value="${referredFormId}" />
									</td>
							</tr>
							<tr class="odd">
								<td  class="Label" style="width:20%">ID</td>
								<td class="value" ><input type="text" name="modelId"
									id="modelId" value="${modelId}" /></td> 
							</tr>
							<tr >
								<td class="Label" style="width:20%">名称</td>
								<td class="value" ><input type="text" name="displayName"
									id="displayName" value="${displayName}" /></td>
								 
							</tr>
							
							<tr class="odd">
								<td class="Label" style="width:20%">预览</td>
								<td class="value" ><input type="checkbox" name="preview"
									id="preview" value="${preview}" 
									<c:if test="${preview == 'YES'}">checked</c:if>
									/>是</td>
							</tr>
							
							
							
							<!-- 	<tr class="odd">
									<td class="Label" style="width:20%">是否启用签名</td>
									<td class="value" style="width:30%">
										<span id="formSign">
											<input type='radio' name="formSign" id='formSign_1' value='1' style='margin-top: 11px;' checked='checked'></input><label for='formSign_1' style='margin-top: 8px;'>不启用</label>
											<input type='radio' name="formSign" id='formSign_2' value='2' style='margin-top: 11px;'></input><label for='formSign_2' style='margin-top: 8px;'>启用</label>
											<input type='hidden' id='isGetZhengWen' />
										</span>
									</td>
								</tr> -->
								 
					 
						</table>
					</form>
							</div>
						</div>
					</div>		
				</div>
				<div class="tab-pane" id="tab2">
					<div class="dyform"> 
						<table border="0"> 
							<tr> 	
								<td  >
									<div style="top:10px;height:440px;width:300px; overflow:auto;">
										<table id="fieldsTbl">
											<tr>
												<th>字段编码</th><th>显示名称</th>
											</tr> 
										</table>
									</div>
									<div style="padding-bottom: 5px;margin-bottom: 5px;position: relative;">
										<input id="statistic" type="button" value="统计">
									</div>
								</td>
								<td style="padding:0px;width: 850px;">
									<textarea id="moduleText"  name="moduleText"></textarea>
								</td>
							</tr>
						</table> 
					</div>
				</div> 
			</div>
		</div>
	</div>
	
  
	<div id="menuContent" class="menuContent"
		style="display: none; position: absolute; z-index: 9999; background-color: #fff; overflow: scroll; border: 1px solid #c5dbec;">
		<ul id="tableTree" class="ztree"
			style="margin-top: 0; height: 300px;"></ul>
	</div>
	<div id="menuContent2" class="menuContent"
		style="display: none; position: absolute; z-index: 9999; background-color: #fff; overflow: scroll; border: 1px solid #c5dbec;">
		<ul id="tableTree2" class="ztree"
			style="margin-top: 0; height: 300px;"></ul>
	</div>
	<!-- <textarea rows="10" cols="60" id="showJSON"></textarea> -->
	
	<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/common/jquery.comboTree.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/ckeditor4.4.3/ckeditor.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/common/jquery.alerts.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/dyform/common/FormClass.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/dyform/common/formDefinitionMethod.js"></script>
	
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/dyform/dyform_model.js"></script>
		
		
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/dyform/common/function.js"></script>
		<script type="text/javascript"
		src="${ctx}/resources/pt/js/dyform/common/ckUtils.js"></script>
	
  
	<script type="text/javascript">
	function autoWith(){
		var div_body_width = $(window).width() * 0.9;
		$(".form_header").css("width", div_body_width-5);
	 	$(".div_body").css("width", div_body_width);
		
	}
	autoWith();
	$(window).resize(function(e) {
		// 调整自适应表单宽度
		autoWith();
	});
	$("#downTemp").click(function() {
		location.href='${ctx}/dytable/downloadTemp.action';
	});
 
	</script>
	</div>
	<div class="body_foot"></div>
</body>
</html>
