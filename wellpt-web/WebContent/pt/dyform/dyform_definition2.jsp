<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>

<title id="title"></title>
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
<script src="${ctx}/resources/jqgrid/js/jquery.jqGrid.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/jBox/jquery.jBox.src.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/jBox/i18n/jquery.jBox-zh-CN.js"
	type="text/javascript"></script>
 
 <%@ include file="/pt/dyform/dyform_js.jsp"%> 
<%@ include file="/pt/dyform/dyform_ctljs.jsp"%>
<%@ include file="/pt/dyform/dyform_preservedjs.jsp"%>


<!-- Bootstrap -->
	 <link rel="stylesheet"
	href="${ctx}/resources/bootstrap/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/pt/css/custom-admin-style.css" />
	
<link rel="stylesheet"
	href="${ctx}/resources/ztree/css/zTreeStyle/zTreeStyle.css"
	type="text/css" />
 
	
	

	
	
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
				<button id="btn_form_save" name="btn_form_save">保存</button>
				<button id="btn_form_save_new" name="btn_form_save_new">保存新版本</button>
				
			</div>
		</div>
	</div>
	<div class="form_content">
		<div class="tabbable" >
			<ul class="nav nav-tabs">
				<li class="active"><a href="#tab1" data-toggle="tab">基本属性</a></li>
				<li><a href="#tab2" data-toggle="tab">表单设计</a></li>
				<li><a href="#tab3" data-toggle="tab">自定义JS</a></li>
				<li><a href="#tab4" data-toggle="tab">帮助</a></li>
			</ul>
			<div class="tab-content">
				<div class="tab-pane active" id="tab1" style="height:250px;">
					<div class="dyform">
						<div class="post-sign">
							<div class="post-detail">
							<form id="mainForm" name="mainForm" style="margin: 0px;">
								<input type="hidden" name="formUuid" id="formUuid" value="${uuid}" />
								<input type="hidden" name="version" id="version" value="" />
								<input type="hidden" name="htmlPath" id="htmlPath" value="" /> 
								<input type="hidden" name="tempHtmlPath" id="tempHtmlPath" value="" />
						<table>
							<tr class="odd">
								<td class="Label" style="width:20%">名称</td>
								<td class="value" style="width:30%"><input type="text" name="mainTableCnName"
									id="mainTableCnName" value="" /></td>
								<td class="Label" style="width:20%">所属模块</td>
								<td class="value" style="width:30%"><input type="text" name="moduleName"
									id="moduleName" value="" />
									<input type="hidden" class="w100"
						name="moduleId" id="moduleId" value="" />
								</td>
							</tr>
							<tr>
								<td class="Label" style="width:20%">ID</td>
								<td class="value" style="width:30%"><input type="text" name="tableId"
									id="tableId" value="" /></td>
								<td class="Label" style="width:20%">应用于</td>
								<td class="value" style="width:30%"><input type="text" class="w100" name="applyToName2"
									id="applyToName2" value="" />
									<input type="hidden" class="w100"
						name="applyTo2" id="applyTo2" value="" /></td>
									
<!-- 								<td width="15%" class="label">选择模板</td> -->
<!-- 								<td width="35%"><input type="file" name="uploadFile" -->
<!-- 									id="uploadFile" /> <a class="easyui-linkbutton" -->
<!-- 									iconCls="icon-ok" href="javascript:void(0)" id="uploadBtn" -->
<!-- 									data-options="plain:true">上传</a></td> -->
							</tr>
							<tr class="odd">
								<td class="Label" style="width:20%">数据库表名</td>
								<td class="value" style="width:30%"><input type="text" name="mainTableEnName"
									id="mainTableEnName" value="" /></td>
								<td class="Label" style="width:20%">打印模板</td>
								<td class="value" style="width:30%"><input type="text" class="w100" name="getPrintTemplateName"
									id="getPrintTemplateName" value="" />
									<input type="hidden" class="w100"
						name="getPrintTemplateId" id="getPrintTemplateId" value="" /></td>
							</tr>
							<tr>
								<td class="Label" style="width:20%">编号</td>
								<td class="value" style="width:30%"><input type="text" name="tableNum"
									id="tableNum" value="" /></td>
								<c:if test="${flag == 1}" >
								<td class="Label" style="width:20%">显示单据模板</td>
								<td class="value" style="width:30%"><input type="text" name="showTableModel"
									id="showTableModel" value="" />
									<input type="hidden" name="showTableModelId"
									id="showTableModelId" value="" />
								</td>
								</c:if>
								<c:if test="${flag == 2}" >
								<td class="Label" style="width:20%">编辑单据模板</td>
								<td class="value" style="width:30%"><input type="text" name="showTableModel"
									id="showTableModel" value="" />
									<input type="hidden" name="showTableModelId"
									id="showTableModelId" value="" />
								</td>
								</c:if>
								<tr class="odd">
									<td class="Label" style="width:20%">是否启用签名</td>
									<td class="value" style="width:30%">
										<span id="formSign">
											<input type='radio' name="formSign" id='formSign_1' value='1' style='margin-top: 11px;' checked='checked'></input><label for='formSign_1' style='margin-top: 8px;'>不启用</label>
											<input type='radio' name="formSign" id='formSign_2' value='2' style='margin-top: 11px;'></input><label for='formSign_2' style='margin-top: 8px;'>启用</label>
											<input type='hidden' id='isGetZhengWen' />
										</span>
									</td>
								</tr>
									<input type="hidden" name="formDisplay"
									id="formDisplay" value="" />
<!-- 									<input type="radio" name="formDisplay" id="formDisplay" value="1"><label for="formDisplay_1">显示表单定义</label></input> -->
<!-- 									<input type="radio" name="formDisplay" id="formDisplay" value="2"><label for="formDisplay_2">编辑表单定义</label></input> -->
<!-- 									<select name="formDisplay" id="formDisplay"> -->
<!-- 										<option value="1">显示表单定义</option> -->
<!-- 										<option value="2">编辑表单定义</option> -->
<!-- 									</select> -->
							</tr>
<!-- 							<tr class="odd"> -->
<!-- 								<td class="Label" style="width:20%">关联表单</td> -->
<!-- 								<td class="value" style="width:30%"> -->
<!-- 									<input type="text" name="relationFormNames" id="relationFormNames" /> -->
<!-- 									<input type="text" name="relationFormIds" id="relationFormIds" style="display: none;"/> -->
<!-- 								</td> -->
<!-- 								<td class="Label" style="width:20%">是否展示详细</td> -->
<!-- 								<td class="value" style="width:30%"> -->
<!-- 									<input type="radio" name="showDetail" class="showDetail" id="showDetail_true" value=true/>是 -->
<!-- 									<input type="radio" name="showDetail" class="showDetail" id="showDetail_false" value=false checked="checked"/>否 -->
<!-- 								</td> -->
<!-- 							</tr> -->
						</table>
					</form>
							</div>
						</div>
					</div>		
				</div>
				<div class="tab-pane" id="tab2">
					<div class="dyform">
						<div class="post-sign">
							<div class="post-detail">
					<table>
					 
					</table>
					<fieldset id="fs3" >
						<legend></legend>
						<textarea id="moduleText"  name="moduleText"></textarea>
					</fieldset>
							</div>
						</div>
					</div>
				</div>
				<!-- <div class="tab-pane" id="tab3">
					<fieldset id="fs2" display: none;>
						<div id="moduleDiv" style="width: 500px;" title="表单预览"></div>
					</fieldset>
				</div> -->
				<div class="tab-pane" id="tab3" style="height:700px;padding-left: 20px;padding-right: 20px;">
					<textarea  id="customJs" style="width: 100%;height: 80%;"></textarea>
				</div>
				<div class="tab-pane" id="tab4" style="height:250px;">
					<div style="margin-left: 50px;">&nbsp;&nbsp;本地表单模版编辑使用说明：<br>
					1、用户可以先在界面布局上传本地html模板中下载一份样例模板；<br>
					2、参考模板中的表单进行表单设计；<br>
				    3、设置表单域时，遵循标题+输入域的输入方式；
					</div>
					<div style="margin-left: 50px;"> &nbsp;&nbsp; Web表单编辑使用说明:<br>
					1、界面布局提供了web编辑方式，用户可以在编辑器中编辑自己需要的表单；<br>
					2、使用编辑器的工具栏直接插入表单、复选框、单选框、单行文本、多行文本、按钮等表单元素，编辑器也支持源码插入表单；<br>
					3、可以使用编辑器插入从表；<br>
					4、编辑完成表单后，点击编辑器工具栏上的预览按钮，就可以到字段设置页面进行字段设置；<br>
					</div>
					<div style="margin-left: 50px;">&nbsp;&nbsp;备注:如果一个表单域需要设置成附件格式，则该域的id必须设置成fileupload</div>
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
	
	 
	
	 
 	
		
		 
	
 
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/dyform/dyform_definition2.js"></script>
	
	<script type="text/javascript">
	function autoWith(){
		var div_body_width = $(window).width() * 0.95;
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
	
	 
	 
	 
		
		
		var setting2 = {
				async : {
					otherParam : {
						"serviceName" : "dataDictionaryService",
						"methodName" : "getFromTypeAsTreeAsync",
						"data" : "DY_FORM_ID_MAPPING"
					}
				}
			};
			$("#applyToName2").comboTree({
				labelField : "applyToName2",
				valueField : "applyTo2",
				width:200,
				height:250,
				treeSetting : setting2,
				initServiceParam:["DY_FORM_ID_MAPPING"]
			});
			
			var setting3 = {
					async : {
						otherParam : {
							"serviceName" : "dataDictionaryService",
							"methodName" : "getFromTypeAsTreeAsync",
							"data" : "MODULE_ID"
						}
					},
					check : {
						enable : false
					},
					callback : {
						onClick: treeNodeOnClick,
					}
			
				};
				$("#moduleName").comboTree({
					labelField : "moduleName",
					valueField : "moduleId",
					width:150,
					height:250,
					treeSetting : setting3,
					initServiceParam:["MODULE_ID"]
				});	
				
				function treeNodeOnClick(event, treeId, treeNode) {
					$("#moduleName").val(treeNode.name);
					$("#moduleId").val(treeNode.data);
					$("#moduleName").comboTree("hide");
				}
				
				var setting4 = {
						async : {
							otherParam : {
								"serviceName" : "formDefinitionService",
								"methodName" : "getPrintTemplates",
							}
						},
						callback : {
							onClick:treeNodeOnClick2,
							onCheck:treeNodeOnCheck2
						}
					};
					$("#getPrintTemplateName").comboTree({
						autoInitValue:false,
						labelField : "getPrintTemplateName",
						valueField : "getPrintTemplateId",
						treeSetting : setting4,
					});	
				
				function treeNodeOnClick2(event, treeId, treeNode) {
					$("#getPrintTemplateName").val(treeNode.name);
					$("#getPrintTemplateId").val(treeNode.id);
				}
				function treeNodeOnCheck2(event, treeId, treeNode) {
					// 设置值
					var zTree = $.fn.zTree.getZTreeObj(treeId);
					var checkNodes = zTree.getCheckedNodes(true);
					var path = "";
					var value = "";
					for ( var index = 0; index < checkNodes.length; index++) {
						var checkNode = checkNodes[index];
							if (path == "") {
								path = getAbsolutePath(checkNode);
							} else {
								path = path + ";" + getAbsolutePath(checkNode);
							}
							if (value == "") {
								value = checkNode.data.id;
							} else {
								value = value + ";" + checkNode.data.id;
							}
					}
					$("#getPrintTemplateName").val(path);
					
					$("#getPrintTemplateId").val(value);
				}
					var setting5 = {
							async : {
								otherParam : {
									"serviceName" : "dyFormDisplayModelService",
									"methodName" : "getModels",
									"data":[]//$("#showTableModelId").val()
								}
							},
							check : {
								enable : true
							},
							callback : {
								onClick:treeNodeOnClick5,
								onCheck:treeNodeOnCheck5
							}
						};
						$("#showTableModel").comboTree({
							autoInitValue:false,
							labelField : "showTableModel",
							valueField : "showTableModelId",
							treeSetting : setting5,
						});
			
				function treeNodeOnClick5(event, treeId, treeNode) {
					//$("#showTableModel").val(treeNode.name);
					//$("#showTableModelId").val(treeNode.id);
					//$("#showTableModel").comboTree("hide");
				}
				
				function treeNodeOnCheck5(event, treeId, treeNode) {
				    
					// 设置值
					var zTree = $.fn.zTree.getZTreeObj(treeId);
					var checkNodes = zTree.getCheckedNodes(true);
					var path = "";
					var value = "";
					for ( var index = 0; index < checkNodes.length; index++) {//设置为单选
						var checkNode = checkNodes[index]; 
							if(treeNode.id != checkNode.id){ 
								zTree.checkNode(checkNode, false);
							}else{ 
								path = getAbsolutePath(checkNode);
								value = checkNode.id;
							}
						 
							 
					}
					$("#showTableModel").val(path);
					$("#showTableModelId").val(value); 
				
				}
				
				// 获取树结点的绝对路径
				function getAbsolutePath(treeNode) {
					var path = treeNode.name;
					var parentNode = treeNode.getParentNode();
					while (parentNode != null) {
						path = parentNode.name + "/" + path;
						parentNode = parentNode.getParentNode();
					}
					return path;
				}
				// 动态表单单据关闭
				$(".form_header .form_close").click(function(e){window.close();});
				$(".nav li").eq(1).click(function(){
					$(".cke_contents").css("height","700px");
				});
				
				$('#subcolor').live("click",function(){
					if($(".colors").css("display")=="block"){
						$(".colors").css("display","none");
					}else if($(".colors").css("display")=="none"){
						$(".colors").css("display","block");
					}
					
				});
				function selColor(color){
					 $("#scolor").val(color);
				     $("#subject").css("color",color);
				     $("#fontcolor").css("background-color",color);
				     $("#fontColor").val(color);
				}
				
				
				var ctrlFieldSetting = {
						async : {
							otherParam : {
								"serviceName" : "formDefinitionService",
								"methodName" : "getFieldByFormUuid",
								"data":$("#formUuid").val()
							}
						},
						callback : {
							onClick:treeNodeOnClickForctrlFieldSetting,
							onCheck:treeNodeOnCheckForctrlFieldSetting
						}
				};
					$("#ctrlFieldName").comboTree({
						labelField : "ctrlFieldName",
						valueField : "ctrlField",
						treeSetting : ctrlFieldSetting,
						width: 220,
						height: 220
					});
					
					function treeNodeOnClickForctrlFieldSetting(event, treeId, treeNode) {
						$("#ctrlFieldName").val(treeNode.name);
						$("#ctrlField").val(treeNode.id + ","+ treeNode.name);
					}
					
					function treeNodeOnCheckForctrlFieldSetting(event, treeId, treeNode) {
						// 设置值
						var zTree = $.fn.zTree.getZTreeObj(treeId);
						var checkNodes = zTree.getCheckedNodes(true);
						var path = "";
						var value = "";
						for ( var index = 0; index < checkNodes.length; index++) {
							var checkNode = checkNodes[index];
								if (path == "") {
									path = getAbsolutePath(checkNode);
								} else {
									path = path + ";" + getAbsolutePath(checkNode);
								}
								if (value == "") {
									value = checkNode.id;
								} else {
									value = value + ";" + checkNode.id;
								}
						}
						$("#ctrlFieldName").val(path);
						$("#ctrlField").val(value + "," + path);
					}
					
					// 获取树结点的绝对路径
					function getAbsolutePath(treeNode) {
						var path = treeNode.name;
						var parentNode = treeNode.getParentNode();
						while (parentNode != null) {
							path = parentNode.name + "/" + path;
							parentNode = parentNode.getParentNode();
						}
						return path;
					}
	</script>
	</div>
	<div class="body_foot"></div>
</body>
</html>
