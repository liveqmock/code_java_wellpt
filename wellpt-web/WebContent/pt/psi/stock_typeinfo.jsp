
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
	<script type="text/javascript">
		var refleshFlag = "0";
		window.ctx = '${ctx}';
	</script>

	<input type="hidden" id="currentId" value="">
	<input type="hidden" id="objType" value="">
	<input type="hidden" id="currentOperate" value="">
	<input type="hidden" id="moveToFolderId" value="">
	<input type="hidden" id="parentUuid" value="${uuid}">

	<div class="file_list">
		<div class="file_list_h1 form_operate">
				<button type="button" id="newType" >新建分类</button>
				<button type="button" id="newName" >新建品名</button>
				<button type="button" id="delete" >删除</button>
		</div>
		<div class="file_list_h2">
			<div class="file_list_h2_h1">
					<c:if test="${ uuid ne ''}">
					<div class="file_list_h2_h1_l1 file_list_h2_h1_l">
					<input id="folder_list_parentUuid" type="hidden" value="${parentUuid}" />
						<c:if
							test="${(!empty parentFolders) && fn:length(parentFolders) > 0}">
							<span class="folder_list_head_first"><a href="#"><spring:message code="fileManager.label.backToUp" /></a></span>
							<c:forEach items="${parentFolders}" var="tempFolder">
								><span class="folder_list_head_third"><a
									href="#">${tempFolder.title}</a></span>
									<input id="folder_list_tempUuid" type="hidden" value="${tempFolder.uuid}" />
							</c:forEach>

						</c:if>
					</div>
					</c:if>
			</div>
			<div class="file_list_h2_h2">
				<table class="file-list-table">
					<thead>
						<tr>
							<th class="th1"><input id="checkAll" type="checkbox"
								class="checkAll" /></th>
							<th class="th2">物品分类</th>
							<th class="th3">编号</th>
								
								
						</tr>
					</thead>
					<tbody>
						<c:choose>
							<c:when
								test="${(!empty fmFolders) && fn:length(fmFolders) > 0}">
								<c:forEach items="${fmFolders}" var="folder">
									<tr class="folderList" objType="folder" objId="${folder.uuid}">
										<td><input name="objId" type="checkbox"
											value="${folder.uuid}" objType="folder" /></td>
										<td>
											<div style="position: relative;">
												
												<div class="file_name">
													<A href="#" class="folder-item"
														id="${folder.uuid}"
														rel=view_folder>${folder.title}</A>
												</div>

												<div class="file_operate" folderId="${folder.uuid}">
														<button type="button" class="button" id="dispose">配置</button>
												
														<button type="button" class="button" id="deleteSingle">删除</button>
												</div>

												 
											</div>

										</td>
										<td>${folder.seqNum}</td>
									</tr>
								</c:forEach>
								
							</c:when>
						</c:choose>
					</tbody>
				</table>
			</div>
		</div>

	</div>
	<script type="text/javascript">
			var refleshFlag = "0";
	</script>
	<!-- /container -->

	<script type="text/javascript"
		src="${ctx}/resources/utils/ajaxfileupload.js"></script>
<!-- 	<script type="text/javascript" -->
<%-- 		src="${ctx}/resources/pt/js/common/jquery.excelimportrule.js"></script> --%>
	<script>
	$(function(){
		
		$("#checkAll").die().live("click",function(){
			$("input[name='objId']").prop("checked", this.checked);
		});
		
		//点击库、夹的标题或图标的页面跳转
		$(".folder-item").each(function(){
			var $this = $(this);
			$(this).click(function(){
				var folderUuid = $(this).attr("id");
				pageLock("show");
				$.ajax({
					type : "POST",
					async:false,
					url : ctx+"/psi/type_info",
					data : "id="+folderUuid,
					dataType : "text",
					success : function callback(result) {
						$this.parents(".active").html(result);
						pageLock("hide");
					}
				});
			});
		});
		
		$(".folder_list_head_first").die().live("click",function(){
			var $this = $(this);
			pageLock("show");
			$.ajax({
				type : "POST",
				url : ctx+"/psi/type_info",
				data : "id="+$("#folder_list_parentUuid").val(),
				dataType : "text",
				success : function callback(result) {
					$this.parents(".active").html(result);
					pageLock("hide");
				}
			});
		});
		
		 $(".folder_list_head_third").die().live("click",function(){
			 var $this = $(this);
			 pageLock("show");
				$.ajax({
					type : "POST",
					url : ctx+"/psi/type_info",
					data : "id="+$("#folder_list_tempUuid").val(),
					dataType : "text",
					success : function callback(result) {
						$this.parents(".active").html(result);
						pageLock("hide");
					}
				});
		 });
		
		//新建分类
		$("#newType").die().live("click",function() {
			$.ajax({
				type : "POST",
				url : ctx+"/psi/new_type",
				data:"libId=GOOD_MANAGE",
				dataType : "text",
				success : function (data) {
					var url = ctx+"/psi/open?formUuid="+data+"&status=1";
					window.open(url);
				}
			});
		});
		
		//新建品名
		$("#newName").die().live("click",function() {
			$.ajax({
				type : "POST",
				url : ctx+"/psi/new_name",
				data:"libId=GOOD_MANAGE",
				dataType : "text",
				success : function (data) {
					var url = ctx+"/psi/open?formUuid="+data+"&status=2";
					window.open(url);
				}
			});
		});
		 
		//删除(支持批量删除)
		$("#delete").die().live("click",function() {
			alert($("#currentId").val());
			oConfirm("确定要删除吗？",function(){
				JDS.call({
	    			service : "stockService.deleteObjects",
	    			data : [ $("#currentId").val()],
	    			success : function(result) {
	    				oAlert("删除成功", function(){
	    					pageLock("show");
	    					refreshWindow($("#file_list"));
	    					 var $this = $("#delete");
// 	    					$.ajax({
// 	    						type : "POST",
// 	    						url : contextPath+"/psi/type_info",
// 	    						data : "id="+$("#parentUuid").val(),
// 	    						dataType : "text",
// 	    						async:false,
// 	    						success : function callback(result) {
// 	    							$this.parents(".active").html(result);
// 	    							refreshWindow($("#abc"));
// 	    							pageLock("hide");
// 	    						}
// 	    					});
	    					pageLock("hide");
	    				});
	    			}
	    		});
			});
		
		});
		
		//配置
		$("#dispose").die().live("click",function() {
			var $this = $(this);
				JDS.call({
	    			service : "stockService.openObjects",
	    			data : [$this.parent().attr('folderid')],
	    			success : function(result) {
	    				var formUuid = result.data.formUuid;
	    				var dataUuid = result.data.dataUuid;
	    				var isHideFolder = result.data.isHideFolder;
	    				var url = ctx+"/psi/open?formUuid="+formUuid+"&dataUuid="+dataUuid+"&isHideFolder="+isHideFolder;
	    				window.open(url);
	    			}
	    		});
		});
		
		//删除(单个删除)
		$("#deleteSingle").die().live("click",function() {
			var $this = $(this);
			oConfirm("确定要删除吗？",function(){
				JDS.call({
	    			service : "stockService.deleteObjects",
	    			data : [$this.parent().attr('folderid')],
	    			success : function(result) {
	    				oAlert("删除成功", function(){
	    					pageLock("show");
	    					refreshWindow($(".file_list"));
	    					pageLock("hide");
	    				});
	    			}
	    		});
			});
		
		});
		
		 
		
		function setUpIds(){
			var ids = "";
			$("input[name='objId']").each(function(){
				 
				if($(this).attr("checked") == "checked"){
					if(ids.length >0){
						ids = ids +";";
					}
					ids = ids + $(this).attr("value");
					
				}
			});
			
			$("#currentId").val(ids);
		}
		
		
		$(".file_list tbody tr").mouseover(function(){
			$(this).find(".file_operate").show();
			
			<c:if test="${uuid eq ''}">
				//setFunctionShow(this);
			</c:if>
		});
		$(".file_list tbody tr").mouseout(function(){
			$(this).find(".file_operate").hide();
		});
	});
 
	
</script>
</div>
</body>
</html>