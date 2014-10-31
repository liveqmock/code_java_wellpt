<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<%-- <%@ include file="/pt/dyform/dyform_css.jsp"%> --%>
<%-- <%@ include file="/pt/dyform/dyform_js.jsp"%> --%>
</head>
<c:if test="${openBy == 'definition'}">
	<script type="text/javascript" src="${ctx}/resources/jquery/jquery.js"></script>
</c:if>
<c:if test="${ openBy !='dytable'}">
<style>
/* Component containers
----------------------------------*/
.ui-widget { font-family: "Microsoft YaHei"; font-size: 12px; }
.ui-widget .ui-widget { font-size: 11px; }
.ui-widget input, .ui-widget select, .ui-widget textarea, .ui-widget button { font-family:"Microsoft YaHei"; font-size: 12px; }
<!--
/* 组织选择框 */
.ui-dialog {
padding:0;
}
.ui-widget-header{
background: url("/wellpt-web/resources/theme/images/v1_icon.png") repeat-x scroll 0 -53px transparent;
}
.ui-corner-all, .ui-corner-bottom, .ui-corner-right, .ui-corner-br {
border-bottom-right-radius: 0;
}
.ui-corner-all, .ui-corner-bottom, .ui-corner-left, .ui-corner-bl {
border-bottom-left-radius: 0;
}
.ui-corner-all, .ui-corner-top, .ui-corner-right, .ui-corner-tr {
border-top-right-radius: 0;
}
.ui-corner-all, .ui-corner-top, .ui-corner-left, .ui-corner-tl {
border-top-left-radius: 0;
}
.ui-resizable-handle.ui-resizable-s {
background: url("/wellpt-web/resources/theme/images/v1_panel.png") repeat-x scroll 0 -10px transparent;
}
.ui-resizable-handle.ui-resizable-e {
background: url("/wellpt-web/resources/theme/images/v1_bd.png") repeat-y scroll 100% 0 transparent;
right: -2px;
}
-->
</style>
</c:if>	
<body>
<c:if test="${loadJs != 'false' && openBy !='dytable'}">
<!-- 该条件分支原来实现点击查询后不用重复加载js -->
<script type="text/javascript" src="${ctx}/resources/utils/json2.js"></script>
<script type="text/javascript" src="${ctx}/resources/bootstrap/js/bootstrap.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/jqgrid/js/i18n/grid.locale-cn.js"></script>
<script type="text/javascript">
	$.jgrid.no_legacy_api = true;
	$.jgrid.useJSON = true;
	// 加载全局国际化资源
	I18nLoader.load("/resources/pt/js/global");
	// 加载动态表单定义模块国际化资源
	I18nLoader.load("/resources/pt/js/dyform/dyform");
</script>
 
<script src="${ctx}/resources/My97DatePicker/WdatePicker.js"
	type="text/javascript"></script>

<!-- <script type="text/javascript" -->
<%-- 		src="${ctx}/resources/pt/js/dyform/dyform_combine.js"></script>		 --%>

<script src="${ctx}/resources/jqgrid/js/jquery.jqGrid.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/timers/jquery.timers.min.js"
	type="text/javascript"></script>
<%-- <script src="${ctx}/resources/jqueryui/js/jquery-ui.js" --%>
<!-- 	type="text/javascript"></script> -->
<script src="${ctx}/resources/ztree/js/jquery.ztree.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/ztree/js/jquery.ztree.exhide-3.5.js"
	type="text/javascript"></script>

<script src="${ctx}/resources/pt/js/basicdata/serialnumber/serialform.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/pt/js/common/jquery.numberInput.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/pt/js/common/jquery.comboTree.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/pt/js/common/jquery.timeEmploy.js"
	type="text/javascript"></script>	
<script src="${ctx}/resources/pt/js/org/unit/jquery.unit.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/pt/js/common/jquery.alerts.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/form/form_body.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/pt/js/dyform/common/dyform_constant.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/pt/js/dyform/common/FormClass.js"
	type="text/javascript"></script>		
<script src="${ctx}/resources/pt/js/dyform/common/function.js"
	type="text/javascript"></script>	
<script src="${ctx}/resources/pt/js/dyform/common/formDefinitionMethod.js"
	type="text/javascript"></script>	
<script src="${ctx}/resources/pt/js/dyform/controlproperty/wFileUploadClass.js"
	type="text/javascript"></script>	
<script src="${ctx}/resources/pt/js/dyform/controlproperty/wCheckBoxClass.js"
	type="text/javascript"></script>	
<script src="${ctx}/resources/pt/js/dyform/controlproperty/wCkeditorClass.js"
	type="text/javascript"></script>	
<script src="${ctx}/resources/pt/js/dyform/controlproperty/wComboBoxClass.js"
	type="text/javascript"></script>	
<script src="${ctx}/resources/pt/js/dyform/controlproperty/wComboTreeClass.js"
	type="text/javascript"></script>	
<script src="${ctx}/resources/pt/js/dyform/controlproperty/wDatePickerClass.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/pt/js/dyform/controlproperty/wFileUpload4IconClass.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/pt/js/dyform/controlproperty/wFileUploadClass.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/pt/js/dyform/controlproperty/wNumberInputClass.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/pt/js/dyform/controlproperty/wRadioClass.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/pt/js/dyform/controlproperty/wSerialNumberClass.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/pt/js/dyform/controlproperty/wTextAreaClass.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/pt/js/dyform/controlproperty/wTextInputClass.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/pt/js/dyform/controlproperty/wUnitClass.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/pt/js/dyform/controlproperty/wViewDisplayClass.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/pt/js/dyform/controlproperty/wDialogClass.js"
	type="text/javascript"></script>	
<script src="${ctx}/resources/pt/js/dyform/controlproperty/wTimeEmployClass.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/pt/js/control2/common/jquery.wControlUtil.js"
	type="text/javascript"></script>	
<script src="${ctx}/resources/pt/js/control2/common/jquery.wControlConfigUtil.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/pt/js/control2/common/jquery.wControlManager.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/pt/js/control2/common/jquery.wControlInterface.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/pt/js/control2/common/jquery.wTextCommonMethod.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/pt/js/control2/common/jquery.wRadioCheckBoxCommonMethod.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/pt/js/control2/fileupload/jquery.wFileUploadMethod.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/pt/js/control2/jquery.wDatePicker.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/pt/js/control2/jquery.wNumberInput.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/pt/js/control2/jquery.wRadio.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/pt/js/control2/jquery.wTextInput.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/pt/js/control2/jquery.wTextArea.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/pt/js/control2/jquery.wCkeditor.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/pt/js/control2/jquery.wCheckBox.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/pt/js/control2/jquery.wComboBox.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/pt/js/control2/jquery.wSerialNumber.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/pt/js/control2/jquery.wUnit.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/pt/js/control2/jquery.wTimeEmploy.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/pt/js/control2/jquery.wViewDisplay.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/pt/js/control2/jquery.wDialog.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/pt/js/control2/subform/jquery.wSubFormMethod.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/pt/js/control2/subform/jquery.wSubForm.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/pt/js/control2/subform/jquery.wSubForm4Group.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/pt/js/control2/fileupload/jquery.wFileUpload.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/pt/js/control2/fileupload/jquery.wFileUpload4Image.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/pt/js/control2/fileupload/jquery.wFileUpload4Icon.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/pt/js/control2/jquery.wComboTree.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/pt/js/control2/jquery.wEmbedded.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/pt/js/control2/leedarson/jquery.wJobSelect.js"
	type="text/javascript"></script>


	
<script type="text/javascript"
		src="${ctx}/resources/ckeditor4.4.3/ckeditor.js"></script>	
<script type="text/javascript" src="${ctx}/resources/pt/js/dyform/common/dyform_constant.js"></script>
</c:if>		
<title>视图解析</title>
<c:if test="${openBy == 'definition'}">
	<link rel="stylesheet" type="text/css" href="${ctx}/resources/bootstrap/css/bootstrap.css" />
	<link href="${ctx}/resources/My97DatePicker/skin/WdatePicker.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="${ctx}/resources/pt/js/basicdata/view/view_demo.js"></script>
</c:if>
	<div class = "templateDiv">
			${htmlParmStr}
			${selectTemplate}
			${buttonTemplate}
	</div>
	<div class="viewContent" viewnum="${queryItemCount}" id="update_${viewAndDataBean.viewUuid}">
		<div>
		<!-- 视图 -->
		<div id="abc" class="abc${viewAndDataBean.viewUuid}">
			<!-- 视图列特殊计算需要的参数开始 -->
			<input type="hidden" id="specialField" name="specialField" value="${viewDefinitionBean.specialField}">
			<input type="hidden" id="specialFieldMethod" name="specialFieldMethod" value="${viewDefinitionBean.specialFieldMethod}">
			<input type="hidden" id="requestParamName" name="requestParamName" value="${viewDefinitionBean.requestParamName}">
			<input type="hidden" id="requestParamId" name="requestParamId" value="${viewDefinitionBean.requestParamId}">
			<input type="hidden" id="responseParamName" name="responseParamName" value="${viewDefinitionBean.responseParamName}">
			<input type="hidden" id="responseParamId" name="responseParamId" value="${viewDefinitionBean.responseParamId}">
			<!-- 视图列特殊计算需要的参数结束 -->
			<input type="hidden" id="viewUuid" name="viewUuid" value="${viewAndDataBean.viewUuid}" />
			<input type="hidden" id="columnDefinitions" name="columnDefinitions" value="${columnDefinitions }" />
			<input type="hidden" id="fieldSelects" name="fieldSelects" value="${fieldSelects}"/>
			<input type="hidden" id="conditionTypes" name="conditionTypes" value="${conditionTypes}">
			<input type="hidden" id="parmStr" name="parmStr" value="${parmStr}">
			
			<input type="hidden" id="page" name="page" value="${page}" />
			<input value="${mark}" type="hidden" id="mark" name="mark"/>
			<input type="hidden" id="pageDefinition" name="pageDefinition" value="${pageDefinition}" />
			<input type="hidden" id="pageCurrentPage" name="pageCurrentPage" value="${page.currentPage}"/>
			<input type="hidden" id="pageTotalCount" name="pageTotalCount" value="${page.totalCount}"/>
			<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}"/>
			<input type="hidden" id="totalPages" name="totalPages" value="${page.totalPages}"/>
			<!-- 原来view_update页面的参数 -->
			<c:set var="ctx" value="${pageContext.request.contextPath}"/>
			<input type="hidden" id="title" name="title" value="${title}"/>
			<input type="hidden" id="orderbyArr" name="orderbyArr" value="${orderbyArr}"/>
			
			<table class="table">
					<thead>
					${titleSource}
					</thead>
					<tbody id="template" style="clear: both;">
					${template} 
					</tbody>
			</table>
			<c:if test="${viewDefinitionBean.pageAble==true}">
			<%@ include file="/pt/basicdata/view/page_new.jsp"%>
			</c:if>
			${buttonTemplate2}
		</div>
		</div>
	</div>
	<c:if test="${openBy!= 'dytable'}">
	<script type="text/javascript" src="${ctx}/resources/pt/js/basicdata/view/view_explain.js"></script>
	</c:if>
	<c:if test="${openBy== 'dytable'}">
		<script type="text/javascript" src="${ctx}/resources/pt/js/basicdata/view/view_demo.js"></script>
	</c:if>
	<c:forEach items="${srcList}" var="src">
		<script type="text/javascript" src="${ctx}${src.src}"></script>
	</c:forEach>

<script type="text/javascript" src="${ctx}/resources/pt/js/basicdata/view/view_constant.js"></script>

	
	<script type="text/javascript">
	if($("#abc").length>0){
		$(function() {
			
			$("#abc").dyView({
				data:{viewUuid:$("#viewUuid").val(),
					columnDefinitions:$("#columnDefinitions").val(),
					page:$("#page").val(),
					pageCurrentPage:$("#pageCurrentPage").val(),
					pageTotalCount:$("#pageTotalCount").val(),
					pageSize:$("#pageSize").val(),
					pageDefinition:$("#pageDefinition").val(),
					conditionTypes:$("#conditionTypes").val(),
					fieldSelects:$("#fieldSelects").val(),
					clickType:"view_show"
					}	
			});
			//初始化控件
			initCtl();
		});
	}
	
	$(".view_search").hide();
	
	var viewUuid = $("#viewUuid").val();
	var pageSize = $("#pageSize").val();
	$("#update_"+viewUuid).find("#pageSizeSelect").append("<option value='"+pageSize+"'>"+pageSize+"</option>");
	$("#update_"+viewUuid).find("#pageSizeSelect").children("option[value='"+pageSize+"']").attr("selected","selected");
	//特殊字段的二次请求数据处理
	if($("#specialField").val() == "true") {
		var viewUuid = $("#viewUuid").val();
		var viewDataUuid = new Array();
		var viewDataArray = new Array();
		 $(".checkeds").each(function(){
				var $this = $(this);
				var dataUuid = $this.val();
				var viewDataNew = new Object();
				var viewData = $this.parents("tr").attr("jsonstr");
				viewData = viewData.replace("{","");
				viewData = viewData.replace("}","");
				var viewDatas = viewData.split(",");
				for(var index=0;index<viewDatas.length;index++) {
					var viewDataMap = viewDatas[index].split("=");
					var viewDataMapFirst = viewDataMap[0].replace(" ","");
					viewDataNew[viewDataMapFirst] = viewDataMap[1];
				}
				viewDataArray.push(viewDataNew);
				viewDataUuid.push(dataUuid);
			});
// 			alert("viewDataArray " + JSON.stringify(viewDataArray));
			JDS.call({
			async:false,
				service:"getViewDataService.getSpecialFieldValues",
				data:[viewUuid,viewDataUuid,viewDataArray],
				success:function(result) {
					var data = result.data;
					for(var i=0;i<data.length;i++){
						var $nowTr = $("#template").find("tr").eq(i);
						var tempData = data[i];
						for(var key in tempData){
							$nowTr.find("td[field='"+key+"']").text(tempData[key]);
						}
					}
				}
			});
	}
	
	
	function replaceReg(reg,str){ 
		str = str.toUpperCase(); 
		return str.replace(reg,function(m){return m.toLowerCase()}) 
		} 
	
	
	function initCtl(){
		<c:forEach items="${fieldSelects}" var="field" varStatus="status"> 
		{	

			var index = "<c:out value = '${status.index}' />";
			var fieldName = "<c:out value = '${field.field}' />";
		  var selectTypeId = "<c:out value = '${field.selectTypeId}' />";
		  if(selectTypeId == selectFieldTypeId.text) {//文本
			
		  }
		  else if(selectTypeId == selectFieldTypeId.date) {//日期
				//日期控件的类型
				var dateContentFormat = "<c:out value = '${field.contentFormat}' />"; 
				//日期控件的属性
				var columnPropertyForDateBegin={
						controlName:fieldName+"_"+"begin_"+index,
				};
				var columnPropertyForDateEnd={
						controlName:fieldName+"_"+"end_"+index,
				};
				
				var commonPropertyForDate = {
						
				}
 				$("#"+fieldName+"_begin").wdatePicker({
 					columnProperty:columnPropertyForDateBegin,
 			    	commonProperty:commonPropertyForDate,
 			    	contentFormat:dateContentFormat
 					});
				
 				$("#"+fieldName+"_end").wdatePicker({
 					columnProperty:columnPropertyForDateEnd,
 			    	commonProperty:commonPropertyForDate,
 			    	contentFormat:dateContentFormat
 					});
		  }else if(selectTypeId == selectFieldTypeId.org) {//组织选择框
			//组织选择框控件的类型
			var orgInputMode = "<c:out value = '${field.orgInputMode}' />";
			var columnPropertyForOrg={
					controlName:fieldName+"_"+index,
			};
			
			var commonPropertyForOrg = {
					inputMode:orgInputMode,
					
			}
			$("#"+fieldName).wunit({
				columnProperty:columnPropertyForOrg,
		    	commonProperty:commonPropertyForOrg
				});
		  }else if(selectTypeId == selectFieldTypeId.select) {//下拉框
			  var columnPropertyForSelect = {
					  controlName:fieldName,
		  		};
		  	 var commonPropertyForSelect = {
				  
		  		}
		  	var optionDataSource = "<c:out value = '${field.optionDataSource}' />";
		  	var optdata = "<c:out value = '${field.optdata}' />";
		  	var dictCode = "<c:out value = '${field.dictCode}' />";
		  	
		  	var dataSourceId = "<c:out value = '${field.dataSourceId}' />";
		  	var selectNameColumn = "<c:out value = '${field.selectNameColumn}' />";
		  	var selectValueColumn = "<c:out value = '${field.selectValueColumn}' />";
		  	var optdata = "<c:out value = '${field.optdata}' />";
		  	var datas = new Object();
		  	if(dictCode != "") {
		  		JDS.call({
			       	 service:"dataDictionaryService.getDataDictionariesByType",
			       	 data:[dictCode.split(":")[0]],
			       	 async: false,
						success:function(result){
							datas = result.data;
						},
						error:function(jqXHR){
						}
					});
		  	}else if(dataSourceId != "") {
		  		JDS.call({
			       	 service:"dataSourceDataService.dataSourceInterpreter",
			       	 data:[dataSourceId],
			       	 async: false,
						success:function(result){
							datas = result.data;
						},
						error:function(jqXHR){
						}
					});
		  	}else {
		  		 datas = eval("(" + urldecode(optdata) + ")");
		  	}
			  $("#"+fieldName).wcomboBox({
					columnProperty:columnPropertyForSelect,
			    	commonProperty:commonPropertyForSelect,
					optionDataSource:parseInt(optionDataSource+1), //备选项来源1:常量,2:字段
					optionSet:datas,
					dictCode:dictCode
				});
		  }else if(selectTypeId == selectFieldTypeId.radio) {//单选框
				  var columnPropertyForRadio = {
						  controlName:fieldName,
			  		};
			  	 var commonPropertyForRadio = {
			  			inputMode:dyFormInputMode.radio
			  		}
			  	var optionDataSource = "<c:out value = '${field.optionDataSource}' />";
			  	var optdata = "<c:out value = '${field.optdata}' />";
			  	var dictCode = "<c:out value = '${field.dictCode}' />";

			  	var dataSourceId = "<c:out value = '${field.dataSourceId}' />";
			  	var selectNameColumn = "<c:out value = '${field.selectNameColumn}' />";
			  	var selectValueColumn = "<c:out value = '${field.selectValueColumn}' />";
			  	var datas = new Object();
			  	if(dictCode != "") {
			  		JDS.call({
				       	 service:"dataDictionaryService.getDataDictionariesByType",
				       	 data:[dictCode.split(":")[0]],
				       	 async: false,
							success:function(result){
								datas = result.data;
							},
							error:function(jqXHR){
							}
						});
			  	}else if(dataSourceId != "") {
			  		JDS.call({
				       	 service:"dataSourceDataService.dataSourceInterpreter",
				       	 data:[dataSourceId],
				       	 async: false,
							success:function(result){
								datas = result.data;
							},
							error:function(jqXHR){
							}
						});
			  	}else {
			  		 datas = eval("(" + urldecode(optdata) + ")");
			  	}
			  	
			  	
				  $("#"+fieldName).wradio({
					columnProperty:columnPropertyForRadio,
				    	commonProperty:commonPropertyForRadio,
						optionDataSource:parseInt(optionDataSource+1), //备选项来源1:常量,2:字段
						optionSet:datas,
						dictCode:dictCode
				});
		  }else if(selectTypeId == selectFieldTypeId.checkbox) {//复选框
			  var columnPropertyForCheckBox = {
					  controlName:fieldName,
					  showType:"1"
		  		};
		  	 var commonPropertyForCheckBox = {
		  			inputMode:dyFormInputMode.checkbox
		  		}
		  	 
		  	var optionDataSource = "<c:out value = '${field.optionDataSource}' />";
		  	var optdata = "<c:out value = '${field.optdata}' />";
		  	var dictCode = "<c:out value = '${field.dictCode}' />";

		  	var dataSourceId = "<c:out value = '${field.dataSourceId}' />";
		  	var selectNameColumn = "<c:out value = '${field.selectNameColumn}' />";
		  	var selectValueColumn = "<c:out value = '${field.selectValueColumn}' />";	 
		  	var optionSet = [];
		  	JDS.call({
		       	 service:"dataDictionaryService.getDataDictionariesByType",
		       	 data:[dictCode.split(":")[0]],
		       	 async: false,
					success:function(result){
						optionSet = result.data;
					},
					error:function(jqXHR){
					}
				});
		  	if(dictCode != "") {
		  		JDS.call({
			       	 service:"dataDictionaryService.getDataDictionariesByType",
			       	 data:[dictCode.split(":")[0]],
			       	 async: false,
						success:function(result){
							datas = result.data;
						},
						error:function(jqXHR){
						}
					});
		  	}else if(dataSourceId != "") {
		  		
		  		JDS.call({
			       	 service:"dataSourceDataService.dataSourceInterpreter",
			       	 data:[dataSourceId],
			       	 async: false,
						success:function(result){ 
							 var datas = result.data;
							 var reg = /\b(\w)|\s(\w)/g; 
							 selectNameColumn = replaceReg(reg,selectNameColumn);
							 selectValueColumn = replaceReg(reg,selectValueColumn);
							for(var index=0;index<datas.length;index++){
								optionSet.push({"code": datas[index][selectValueColumn],"name": datas[index][selectNameColumn]});
							}
						},
						error:function(jqXHR){
						}
					});
		  	}else {
		  		 datas = eval("(" + urldecode(optdata) + ")");
		  	}
			  $("#"+fieldName).wcheckBox({
					columnProperty:columnPropertyForCheckBox,
			    	commonProperty:commonPropertyForCheckBox,
			    	ctrlField:"",
					optionDataSource:parseInt(optionDataSource+1), //备选项来源1:常量,2:字典
					optionSet:optionSet,
					dictCode:"",
					selectMode:"2",//选择模式，单选1，多选2
					singleCheckContent :"",//单选 选中内容
					singleUnCheckContent:""//单选 取消选中内容
					
				});
		  }else if(selectTypeId == selectFieldTypeId.dialog) {//弹出框
			  
		  }						  
		}
	</c:forEach>
		
	}
	</script>	
</body>
</html>

