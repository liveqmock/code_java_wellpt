
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="/pt/common/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title><spring:message code="fileManager.label.fileManager" /></title>

<style type="text/css">
</style>
<%@ include file="/pt/common/meta.jsp"%>
</head>
<body>
	<div class="viewContent">
	<link rel="stylesheet" type="text/css"
		href="${ctx}/resources/ztree/css/zTreeStyle/zTreeStyle.css" />
	<link rel="stylesheet" href="${ctx}/resources/pt/css/fileManager.css" />
	<link rel="stylesheet" type="text/css" media="screen"
		href="${ctx}/resources/pt/css/form.css" />	
	<script type="text/javascript" src="${ctx}/resources/utils/json2.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/ztree/js/jquery.ztree.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/file/list.js"></script>
	<script type="text/javascript"
		src='${ctx}/resources/pt/js/common/jquery.comboTree.js'></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/common/jquery.folderTree.js"></script>
	<script type="text/javascript">
		var refleshFlag = "0";
		window.ctx = '${ctx}';
		//初始化选择模版的选项
		JDS.call({
    		service : "folderManagerService.getAllExcelImportRules",
			success : function(result) {
				var $excelSelect = $("#excelSelect");
				$excelSelect.html("");
				$excelSelect.append("<option value=''>"+fileManager.templateOptionDefaultSelect+"</option>");
				$.each(result.data, function(i){
					$excelSelect.append("<option value='" + this.id + "'>"+ this.name +"</option>");
				});
			}
		});
	</script>

	<input type="hidden" id="currentId" value="">
	<input type="hidden" id="objType" value="">
	<input type="hidden" id="currentOperate" value="">

<div class="dytable_form">
  <div class="post-sign">
	<div  class="post-detail" id="importDialog" style="background:none;">
		<table>
					<tr>
						<td><spring:message code="fileManager.label.selectTemplate" />：</td>
						<td><select class="excelSelect" id="excelSelect"></select></td>
					</tr>
					<tr>
						<td>选择品名</td>
						<td><input type="text" id="showId" >
							<input type="hidden" id="parentUuid" >
						</td>
					</tr>
					<tr>
						<td><spring:message code="fileManager.label.selectFile" />：</td>
						<td><input type="file" id="fileExcel" name="fileExcel" />
						<!--  <input type="button" id="uploadBtn" value="上传"/>--></td>
					</tr>
		</table>
				<input type="hidden" name="currentTemplateId" id="currentTemplateId" />
	</div>
	<div class="form_operate" style="margin-left: 290px;margin-top: 10px;">
		<button type="button" id="importSave" >保存</button>
	</div>
  </div>
</div>
	<script type="text/javascript">
			var refleshFlag = "0";
	</script>
	<!-- /container -->

	<script type="text/javascript"
		src="${ctx}/resources/utils/ajaxfileupload.src.js"></script>
<!-- 	<script type="text/javascript" -->
<%-- 		src="${ctx}/resources/pt/js/common/jquery.excelimportrule.js"></script> --%>
	<script>
	var setting = {
			async : {
				otherParam : {
					"serviceName" : "folderManagerService",
					"methodName" : "getSpecificFolderTreeWithoutFile",
					"data" : "GOOD_MANAGE"
				}
			},
			check : {//复选框的选择做成可配置项
				enable:false
			},
			callback : {
				onClick:function (event, treeId, treeNode) {
					$("#showId").val(treeNode.name);
					$("#parentUuid").val(treeNode.id);
				}
			}
		};
	$("#showId").comboTree({
		labelField : "showId",
		valueField : "parentUuid",
		treeSetting : setting
	});
		
	
		
	
		$("#importSave").die().live("click",function() {
			var templateId = $("#currentTemplateId").val();
			if(templateId.length <= 0){
				oAlert(fileManager.pleaseChooseTemplate);
				return ;
			}
// 			var uploadFileName = $("#fileExcel", this).val();
// 			alert(uploadFileName);
// 			if(StringUtils.isBlank(uploadFileName)){
// 				oAlert(fileManager.pleaseChooseFile);
// 				return;
// 			}
			$.ajaxFileUpload({
	            url:ctx + '/fileManager/folder/uploadExcel?folderId='+$("#parentUuid").val()+"&templateId="+templateId,//链接到服务器的地址
	            secureuri:false,
	            fileElementId:'fileExcel',//文件选择框的ID属性
	            dataType: 'text',  //服务器返回的数据格式
	            success: function (data, status){
	           	  	oAlert(fileManager.fileUploadSuccess, function(){
	           	  		refreshWindow($("#importDialog"));
	           	  	});
	            },
	            error: function (data, status, e){  
	            	oAlert("上传失败");
	            }
			});
		});
		
		$(".file_list tbody tr").mouseover(function(){
			$(this).find(".file_operate").show();
			
			<c:if test="${uuid eq ''}">
				//setFunctionShow(this);
			</c:if>
		});
		$(".file_list tbody tr").mouseout(function(){
			$(this).find(".file_operate").hide();
		});
</script>
</div>
</body>
</html>