<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<%@ include file="/pt/dyform/dyform_css.jsp"%>
<title>动态表单解释</title>

<script type="text/javascript">
	//全局变量
	var formUid = '${formUuid}';
	var dataUid = '${dataUuid}';
</script>
</head>
<body id="explainBody">
	<input type="hidden" id='formUuid' value='${formUuid}'>
	<input type="hidden" id='dataUuid' value='${dataUuid}'>
	<input type="hidden" id="text" value='请选择未完成工作' >
	<input type="hidden" id="subtable" value="001"> 
	<input id="validate" type="button" value="校验"></input>
	<!--   <button id="validateElement" type="button" value="校验元素">校验元素</button>   -->
	<input id="collectFormData" type="button" value="收集数据"></input>
	<input id="save" type="button" value="保存"></input>

	<div >
	demo专区
	<div id="demoZone">
		<button id="addRowData" type="button" value="为从表添加数据">为从表添加数据</button>
	</div>
	<div id="addRowDatadialog" title="为从表添加数据" style="display: none;">
		<button id="addRowData_addField" type="button" value="添加字段">添加字段</button>
		<button id="addRowData_ok" type="button" value="确定">确定</button>
	</div>
	</div>
	
	
	<!-- 自定义表单 -->
	<form action=""  id="abc" >
		<label>动态表单示例</label> <input type="text" id="creator" name="creator" />
	</form>
	<!-- 动态表单 -->
	
		<input id="addSubform" type="button" value="添加子节点"></input>
		<input id="querySubform" type="button" value="查询子节点"></input>
		<input id="addSubformOfsub" type="button" value="添加子节点的子节点"></input>
	<table id="cde">
		 
	</table>
	<div>
		<input id="current_form_url_prefix" type="hidden"
			value="${ctx}/dytable/demo"> <a
			id="current_form_url" href="" target="_blank" style="display: none">查看当前动态表单</a>
	</div>
				<!-- content starts -->

					<!-- 
				<div>
					<ul class="breadcrumb">
						<li><a href="index.jsp">系统</a> <span class="divider">/</span>
						</li>
						<li><a href="fileupload.jsp">文件上传</a></li>
					</ul>
				</div>

				<div class="row-fluid sortable">
					<div class="box span12">
						<div class="box-header well" data-original-title>
							<h2>
								<i class="icon-plus"></i> 文件上传
							</h2>
							<div class="box-icon">
								<a href="#" class="btn btn-setting btn-round"><i
									class="icon-cog"></i></a> <a href="#"
									class="btn btn-minimize btn-round"><i
									class="icon-chevron-up"></i></a> <a href="#"
									class="btn btn-close btn-round"><i class="icon-remove"></i></a>
							</div>
						</div>
					
						<div class="box-content">
							<table class="table table-bordered table-striped">
								<tr>
									<td><h3>文件上传</h3></td>
									<td>
										<div id="fine-uploader" class="btn btn-success">
											<i class="icon-upload icon-white"></i> 选择文件
										</div>
										<div id="messages"></div> <input type="hidden" id="attach"
										value="">
									<td></td>

								</tr>
								<tr>
									<td><h3>文件提交</h3></td>
									<td><h5>模块名<h5><input type="text" name="modulename" id="modulename"
										class="input" />
										<h5>节点名(UUID)</h5><input type="text" name="nodename" id="nodename"
										class="input" /></td>

									<td><button class="btn btn-small" id="submit">提交</button></td>

								</tr>
								<tr>
								<td><h3>已提交文件</h3></td>
								<td>
								<div id="filelist"></div>
								</td>
								<td></td>
								</tr>
							</table>
						</div>
						 -->
					<!--/span-->

				<!--/row-->
				<!-- content ends -->
	<!-- Project -->
	<%@ include file="/pt/dyform/dyform_js.jsp"%>
	<%@ include file="/pt/dyform/dyform_ctljs.jsp"%>
	
	
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/dyform/dyform_demo.js"></script>
</body>
</html>
