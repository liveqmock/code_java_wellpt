<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<title>文件管理</title>













<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<!-- Bootstrap -->
<link rel="stylesheet" href="/wellpt-web/resources/pt/css/fileManager.css" />
<link rel="stylesheet"
	href="/wellpt-web/resources/bootstrap/css/bootstrap.css" />
<link rel="stylesheet" type="text/css"
	href="/wellpt-web/resources/jqueryui/css/base/jquery-ui.css" />
<link rel="stylesheet" type="text/css" media="screen"
	href="/wellpt-web/resources/pt/css/form.css" />
<link rel="stylesheet" type="text/css"
	href="/wellpt-web/resources/pt/css/custom-admin-style.css" />
</head>
<body>
	<div class="container-fluid">
		<div class="form_header">
			<!--标题-->
			<div class="form_title">
				<h2>公告管理</h2>
			</div>
			<div id="toolbar" class="form_toolbar">
				<div class="form_operate">
					
						<button type="button" id="libButton" class="btn btn-primary">保存</button>
						
							<button type="button" id="deleteButton" class="btn btn-primary">删除</button>
						
					
					<button type="button" class="btn" onclick="window.close();">关闭</button>
				</div>
			</div>
		</div>
		<div class="form_content">
			<iframe id="lib_iframe" name="lib_iframe"
				style="width: 1px; height: 0px;"></iframe>
			<form class="form-horizontal" id="libForm" name="libForm"
				action="/wellpt-web/folder/update" method="post" target="lib_iframe">
				<input name="uuid" id="uuid" type="hidden" value="7cce8364-16df-4fc4-94c8-d6ac6e1662a9" />
				<input name="parentUuid" id="parentUuid" type="hidden"
					value="" />
				<fieldset>
					<div class="control-group">
						<label class="control-label">标题</label>
						<div class="controls">
							<input name="title" id="title" size="16" value="公告管理"
								type="text">
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">ID</label>
						<div class="controls">
							
							
												NOTICE
											
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">序号</label>
						<div class="controls">
							<input id="seqNum" name="seqNum" value="1"
								size="6" type="text">
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">分类</label>
						<div class="controls"><select name='type' id='type'><option value='private' selected>私有库</option><option value='public'>公有库</option></select></div>
					</div>
					<div class="control-group">
						<label class="control-label">启动版本管理</label>
						<div class="controls">
							<input type="checkbox" id="versionManagement"
								name="versionManagement" value="1"
								
								id="versionManagement" />
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">保存提醒</label>
						<div class="controls">
							<input type="checkbox" name="saveNoticeReader" value="1"
								checked="checked"
								id="saveNoticeReader" />
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">允许打印</label>
						<div class="controls">
							<input type="checkbox" name="canPrint" value="1"
								checked="checked"
								id="canPrint" />
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">允许下载</label>
						<div class="controls">
							<input type="checkbox" name="canDownload" value="1"
								
								id="canDownload" />
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">管理者</label>
						<div class="controls">
							<input type="text" id="managerNames" name="managerNames"
								value="oa_dev" /><input id="libManager"
								name="libManager" type="hidden" value="U0000000001" />
							<div id="sourcelibManagerValue" style="display: none;">U0000000001</div>
							<input type="hidden" id="libManagerChange"
								name="libManagerChange" value="0">
							<!-- 人员是否发生变更 -->
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">可以编辑所有文档的人</label>
						<div class="controls">
							<input type="text" id="editAllFileNames" name="editAllFileNames"
								value="oa_dev" /><input id="editAllFile"
								name="editAllFile" type="hidden" value="U0000000001" />
							<div id="sourceEditAllFileValue" style="display: none;">U0000000001</div>
							<input type="hidden" id="editAllFileChange"
								name="editAllFileChange" value="0">
							<!-- 人员是否发生变更 -->
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">可以创建文档的人</label>
						<div class="controls">
							<input type="text" id="createAllFileNames"
								name="createAllFileNames" value="oa_dev;oa_demo12" /><input
								id="createAllFile" name="createAllFile" type="hidden"
								value="U0000000001;U0000000006" />
							<div id="sourceCreateAllFileValue" style="display: none;">U0000000001;U0000000006</div>
							<input type="hidden" id="createAllFileChange"
								name="createAllFileChange" value="0">
							<!-- 人员是否发生变更 -->
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">可以阅读所有文档的人</label>
						<div class="controls">
							<input type="text" id="readAllFileNames" name="readAllFileNames"
								value="oa_dev" /><input id="readAllFile"
								name="readAllFile" type="hidden" value="U0000000001" />
							<div id="sourceReadAllFileValue" style="display: none;">U0000000001</div>
							<input type="hidden" id="readAllFileChange"
								name="readAllFileChange" value="0">
							<!-- 人员是否发生变更 -->
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">可以阅读所有文档（含有限定阅读人的文档）的人</label>
						<div class="controls">
							<input type="text" id="superReadAllFileNames"
								name="superReadAllFileNames" value="oa_dev" /><input
								id="superReadAllFile" name="superReadAllFile" type="hidden"
								value="U0000000001" />
							<div id="sourceSuperReadAllFileValue" style="display: none;">U0000000001</div>
							<input type="hidden" id="superReadAllFileChange"
								name="superReadAllFileChange" value="0">
							<!-- 人员是否发生变更 -->
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">文件动态表单</label>
						<div class="controls">
							<input type="text" id="templateNames" name="templateNames"
								value="公告2" /> <input type="hidden"
								id="templateIds" name="templateIds" value="8a79f61e-b151-48d7-82bb-042b786aad25" />
							<input type="hidden" id="templateRemarks" name="templateRemarks"
								value="null" />
							<div id="sourceTemplateValue" style="display: none;">8a79f61e-b151-48d7-82bb-042b786aad25</div>
							<input id="templateChange" name="templateChange" type="hidden"
								value="0">
							<!-- 表单模板是否发生变更 -->
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">流程模板</label>
						<div class="controls">
							<input type="text" id="flowName" name="flowName"
								value="" /> <input type="hidden" id="flowId"
								name="flowId" value="" />
						</div>
					</div>
				</fieldset>
			</form>
		</div>
	</div>
	<!-- /container -->
	<script type="text/javascript" src="/wellpt-web/resources/jquery/jquery.js"></script>
	<script type="text/javascript"
		src="/wellpt-web/resources/jqueryui/js/jquery-ui.js"></script>
	<script type="text/javascript"
		src='/wellpt-web/resources/pt/js/org/unit/jquery.unit.js'></script>
	<script type="text/javascript"
		src="/wellpt-web/resources/bootstrap/js/bootstrap.js"></script>
	<script type="text/javascript" src="/wellpt-web/resources/utils/json2.js"></script>
	<script type="text/javascript" src="/wellpt-web/resources/pt/js/global.js"></script>
	<script type="text/javascript"
		src="/wellpt-web/resources/ztree/js/jquery.ztree.js"></script>
	<script type="text/javascript"
		src='/wellpt-web/resources/pt/js/common/jquery.comboTree.js'></script>
	<script type="text/javascript"
		src='/wellpt-web/resources/pt/js/common/jquery.dytableTree.js'></script>
	<script type="text/javascript"
		src='/wellpt-web/resources/pt/js/common/jquery.workflowComboTree.js'></script>
	<script type="text/javascript"
		src='/wellpt-web/resources/pt/js/file/folder.js'></script>
	<script type="text/javascript">
	$(function(){
		$("#lib_iframe").load(function(e) {
			alert("保存成功!");
			window.close();
		});
	});
	</script>
</body>
</html>