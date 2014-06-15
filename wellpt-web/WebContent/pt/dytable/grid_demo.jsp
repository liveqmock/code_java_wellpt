<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<%@ include file="/pt/dytable/form_css.jsp"%>

<title>GRID 示例</title>
 
</head>
<body id="explainBody">
	 
	<input type="hidden" id="text" value='请选择未完成工作' >
	<input type="hidden" id="subtable" value="001">
	<button id="save" type="button" value="保存">保存</button>
	<button id="getFileID" type="button" value="获取文件ID">获取文件ID</button>
	<button id="addRow" type="button" value="获取文件ID">增加一行</button>
	<!-- 自定义表单 -->
	<form action="">
		<label>动态表单示例</label> <input type="text" id="creator" name="creator" />
	</form>
	<!-- 动态表单 -->
	<div id="abc">
		<table id="list4"></table>
		
		<table id="rowed5"></table>
	</div>
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
<%@ include file="/pt/dytable/form_js.jsp"%>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/dytable/grid_demo.js"></script>
		<script type="text/javascript">
 //mydata2
 //colModel
 $(function(){
	 $("#getFileID").click(function(){
		 alert("查看日志中的文件信息");
		 for(var i in colModel){
			 var cm = colModel[i];
			 var fieldName = null;
			 if(cm.edittype == "fileupload"){
				 fieldName = cm.name;
			 }
			 if(fieldName != null){
				 for(var j in mydata2){
					 var data = mydata2[j];
					 console.log(data.id + "_" + fieldName + ":" + JSON.stringify(WellFileUpload.files[data.id + "_" + fieldName]));
				 }
			 }
			 
		 }
		 
	 });
	 
	 $("#addRow").click(function(){
		 $("#rowed5").jqGrid('addRowData',"1324saf", {id:"1324saf",name:"Matrix Printer",note:"note3",stock:"No", ship:"FedEx", fileupload:[{ fileID:"x1394787da8b54a9787e1869a20f687d7", fileName:"xWELL_PT_动态表单接口调用V1.0.docx"}]} ,"first");
		 
	 });
	 
	
	 
 });
 
 </script>
</body>
</html>
