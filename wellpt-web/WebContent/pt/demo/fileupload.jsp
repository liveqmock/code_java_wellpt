<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/pt/common/header.jsp"%>

<body>

	<!-- topbar starts -->
	<div class="navbar">
		<div class="navbar-inner">
			<div class="container-fluid">

				<a class="brand" href="index.jsp"> <img alt="welloa logo"
					src="${ctx}/resources/pt/img/logo20.png" /> <span>OA</span></a>

				<!-- theme selector starts -->
				<div class="btn-group pull-right theme-container">
					<a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
						<i class="icon-tint"></i><span class="hidden-phone"> Change
							Theme / Skin</span> <span class="caret"></span>
					</a>
					<ul class="dropdown-menu" id="themes">
						<li><a data-value="classic" href="#"><i
								class="icon-blank"></i> Classic</a></li>
						<li><a data-value="cerulean" href="#"><i
								class="icon-blank"></i> Cerulean</a></li>
						<li><a data-value="cyborg" href="#"><i class="icon-blank"></i>
								Cyborg</a></li>
						<li><a data-value="redy" href="#"><i class="icon-blank"></i>
								Redy</a></li>
						<li><a data-value="journal" href="#"><i
								class="icon-blank"></i> Journal</a></li>
						<li><a data-value="simplex" href="#"><i
								class="icon-blank"></i> Simplex</a></li>
						<li><a data-value="slate" href="#"><i class="icon-blank"></i>
								Slate</a></li>
						<li><a data-value="spacelab" href="#"><i
								class="icon-blank"></i> Spacelab</a></li>
						<li><a data-value="united" href="#"><i class="icon-blank"></i>
								United</a></li>
					</ul>
				</div>
				<!-- theme selector ends -->

				<!-- user dropdown starts -->
				<div class="btn-group pull-right">
					<a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
						<i class="icon-user"></i><span class="hidden-phone"> admin</span>
						<span class="caret"></span>
					</a>
					<ul class="dropdown-menu">
						<li><a href="#">Profile</a></li>
						<li class="divider"></li>
						<li><a href="login.jsp">Logout</a></li>
					</ul>
				</div>
				<!-- user dropdown ends -->

				<div class="top-nav nav-collapse">
					<!-- 顶部导航 -->
					<ul class="nav">
						<li class="active"><a href="index.jsp">首页</a></li>
						<li><a href="ui.jsp">ui元素</a></li>
						<li><a href="form.jsp">表单</a></li>
						<li><a href="table.jsp">表格</a></li>
						<li>
							<form class="navbar-search pull-left">
								<input placeholder="Search" class="search-query span2"
									name="query" type="text">
							</form>
						</li>
					</ul>
				</div>
				<!--/.nav-collapse -->
			</div>
		</div>
	</div>
	<!-- topbar ends -->
	<!--內容区域 流式布局 -->
	<div class="container-fluid">
		<div class="row-fluid">
			<!-- 左侧菜单 span2-->
			<div class="span2 main-menu-span">
				<div class="well nav-collapse sidebar-nav">
					<!-- 列表导航 -->
					<ul class="nav nav-tabs nav-stacked main-menu">
						<li class="nav-header hidden-tablet">Main</li>
						<li><a class="ajax-link" href="index.jsp"><i
								class="icon-home"></i><span class="hidden-tablet"> 首页</span></a></li>
						<li><a class="ajax-link" href="ui.jsp"><i
								class="icon-eye-open"></i><span class="hidden-tablet">
									ui元素</span></a></li>
						<li><a class="ajax-link" href="form.jsp"><i
								class="icon-edit"></i><span class="hidden-tablet"> 表单</span></a></li>
						<li><a class="ajax-link" href="table.jsp"><i
								class="icon-align-justify"></i><span class="hidden-tablet">
									表格</span></a></li>
						<li><a href="jqtable.jsp"><i class="icon-ban-circle"></i><span
								class="hidden-tablet"> jqgrid表格</span></a></li>
						<li><a href="fileupload.jsp"><i class="icon-ban-circle"></i><span
								class="hidden-tablet"> 文件上传</span></a></li>
						<li><a href="login.jsp"><i class="icon-lock"></i><span
								class="hidden-tablet"> 登录</span></a></li>
					</ul>

				</div>
				<!--/.well -->
			</div>
			<!--/span2-->
			<!-- 左侧菜单 ends -->




			<div id="content" class="span10">
				<!-- content starts -->


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
									</td>	
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
					</div>
					<!--/span-->

				</div>
				<!--/row-->




				<!-- content ends -->
			</div>
			<!--/#content.span10-->
		</div>
		<!--/fluid-row-->
		

		
		<script>
			
			//var fileuuid ;
			$(document)
					.ready(
							function() {
								
							fileupload($('#attach'),$('#fine-uploader'),$('#messages'),'${ctx}');
							
							
							$('#submit').bind("click",function(){
								
								$.ajax({
									type : "post",
									url : '${ctx}' + '/repository/file/testsubmit.action',
									data : {"modulename": $('#modulename').val(),"nodename":$('#nodename').val(),"attach":$('#attach').val()},
									success : function(data) {									
										 $(data.data).each(function() {
					                            $('#filelist').append('<div  class="alert-success" style="margin: 20px 0 0"></div>')					                           											
												.append('<i class="icon-ok"></i> '
																+ '"'
																+ this['filename'].toString()
																+ '"'
																+ '<a  href="'
																+ '${ctx}'
																+ '/repository/file/download.action?filename='
																+ this['filename'].toString()
																+ '&modulename='
																+ $('#modulename').val()
																+'&nodename='
																+$('#nodename').val()
																+ '">下载</a>'																
																);					                          
					                   });
									},
									Error : function(data) {
										alert(data);
									}
								});
							});
							});
			
			
		</script>


		<%@ include file="/pt/common/footer.jsp"%>

	</div>
	<!--/.fluid-container-->


</body>
</html>