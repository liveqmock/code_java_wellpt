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
						<li><a href="jtable.jsp"><i class="icon-ban-circle"></i><span
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
					<li>
						<a href="index.jsp">系统</a> <span class="divider">/</span>
					</li>
					<li>
						<a href="ui.jsp">ui元素</a>
					</li>
				</ul>
			</div>
			
			<div class="row-fluid sortable">	
				<div class="box span12">
					<div class="box-header well" data-original-title>
						<h2><i class="icon-plus"></i> 扩展元素</h2>
						<div class="box-icon">
							<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
							<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a>
						</div>
					</div>
					<div class="box-content">
						<table class="table table-bordered table-striped">
							<tr>
								<td><h3>文件上传</h3></td>
								<td>
									<input data-no-uniform="true" type="file" name="file_upload" id="file_upload" />
								</td>
								<td></td>
							</tr>
							<tr>
								<td><h3>评分</h3></td>
								<td>
									<div class="raty"></div>
								</td>
								<td><code>&lt;div class="raty"&gt;&lt;/div&gt;</code></td>
							</tr>
							<tr>
								<td><h3>开关</h3></td>
								<td>
									<input data-no-uniform="true" checked type="checkbox" class="iphone-toggle">
								</td>
								<td><code>&lt;input data-no-uniform="true" type="checkbox" class="iphone-toggle"&gt;</code></td>
							</tr>
							<tr>
								<td><h3>文本输入</h3></td>
								<td>
									<textarea class="autogrow">Press enter here, it will grow automatically.</textarea>
								</td>
								<td><code>&lt;textarea class="autogrow"&gt;&lt;/textarea&gt;</code></td>
							</tr>
							<tr>
								<td><h3>弹出</h3></td>
								<td>
									<a href="#" class="btn btn-danger" data-rel="popover" data-content="And here's some amazing content. It's very engaging. right?" title="A Title">Hover for popover</a>
								</td>
								<td><code>&lt;a href="#" class="btn btn-danger" data-rel="popover" data-content="And here's some amazing content. It's very engaging. right?" title="A Title"&gt;hover for popover&lt;/a&gt;</code></td>
							</tr>
							<tr>
								<td><h3>滑动</h3></td>
								<td>
									<div class="slider"></div>
								</td>
								<td><code>&lt;div class="slider"&gt;&lt;/div&gt;</code></td>
							</tr>
							<tr>
								<td><h3>对话框</h3></td>
								<td>
									<a href="#" class="btn btn-info btn-setting">Click for dialog</a>
								</td>
								<td></td>
							</tr>
							<tr>
								<td><h3>提示</h3></td>
								<td>
									<a href="#" title="Tooltip, you can change the position." data-rel="tooltip" class="btn btn-warning">Hover for tooltip</a>
								</td>
								<td><code>&lt;a href="#" title="Tooltip, you can change the position." data-rel="tooltip" class="btn btn-warning"&gt;Hover for tooltip&lt;/a&gt;</code></td>
							</tr>
						</table>
					</div>	
				</div><!--/span-->
				
			</div><!--/row-->

			<div class="row-fluid sortable">
				<div class="box span6">
					<div class="box-header well" data-original-title>
						<h2><i class="icon-tasks"></i> Progress Bars</h2>
						<div class="box-icon">
							<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
							<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a>
						</div>
					</div>
					<div class="box-content">
						<h3>Basic</h3>
						<div class="progress">
							<div class="bar" style="width: 70%;"></div>
						</div>
						<h3>Striped</h3>
						<div class="progress progress-striped">
							<div class="bar" style="width: 30%;"></div>
						</div>
						<h3>Animated</h3>
						<div class="progress progress-striped progress-success active">
							<div class="bar" style="width: 50%;"></div>
						</div>
						<h3>Additional Colors</h3>
						<div class="progress progress-info progress-striped" style="margin-bottom: 9px;">
							<div class="bar" style="width: 20%"></div>
						</div>
						<div class="progress progress-success" style="margin-bottom: 9px;">
							<div class="bar" style="width: 40%"></div>
						</div>
						<div class="progress progress-warning progress-striped active" style="margin-bottom: 9px;">
							<div class="bar" style="width: 60%"></div>
						</div>
						<div class="progress progress-danger progress-striped" style="margin-bottom: 9px;">
							<div class="bar" style="width: 80%"></div>
						</div>
					</div>
				</div><!--/span-->
				
				<div class="box span6">
					<div class="box-header well">
						<h2><i class="icon-eye-open"></i> Labels and Annotations</h2>
						<div class="box-icon">
							<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
							<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a>
						</div>
					</div>
					<div class="box-content">
						<table class="table table-bordered table-striped">
							<thead>
							  <tr>
								<th>Labels</th>
								<th>Markup</th>
							  </tr>
							</thead>
							<tbody>
							  <tr>
								<td>
								  <span class="label">Default</span>
								</td>
								<td>
								  <code>&lt;span class="label"&gt;Default&lt;/span&gt;</code>
								</td>
							  </tr>
							  <tr>
								<td>
								  <span class="label label-success">Success</span>
								</td>
								<td>
								  <code>&lt;span class="label label-success"&gt;Success&lt;/span&gt;</code>
								</td>
							  </tr>
							  <tr>
								<td>
								  <span class="label label-warning">Warning</span>
								</td>
								<td>
								  <code>&lt;span class="label label-warning"&gt;Warning&lt;/span&gt;</code>
								</td>
							  </tr>
							  <tr>
								<td>
								  <span class="label label-important">Important</span>
								</td>
								<td>
								  <code>&lt;span class="label label-important"&gt;Important&lt;/span&gt;</code>
								</td>
							  </tr>
							  <tr>
								<td>
								  <span class="label label-info">Info</span>
								</td>
								<td>
								  <code>&lt;span class="label label-info"&gt;Info&lt;/span&gt;</code>
								</td>
							  </tr>
							  <tr>
								<td>
								  <span class="label label-inverse">Inverse</span>
								</td>
								<td>
								  <code>&lt;span class="label label-inverse"&gt;Inverse&lt;/span&gt;</code>
								</td>
							  </tr>
							</tbody>
						  </table>
					</div>
				</div><!--/span-->
				
			</div><!--/row-->
			<div class="row-fluid sortable">
				<div class="box span5">
					<div class="box-header well">
						<h2><i class="icon-bullhorn"></i> Alerts</h2>
						<div class="box-icon">
							<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
							<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a>
						</div>
					</div>
					<div class="box-content alerts">
						<div class="alert alert-error">
							<button type="button" class="close" data-dismiss="alert">×</button>
							<strong>Oh snap!</strong> Change a few things up and try submitting again.
						</div>
						<div class="alert alert-success">
							<button type="button" class="close" data-dismiss="alert">×</button>
							<strong>Well done!</strong> You successfully read this important alert message.
						</div>
						<div class="alert alert-info">
							<button type="button" class="close" data-dismiss="alert">×</button>
							<strong>Heads up!</strong> This alert needs your attention, but it's not super important.
						</div>
						<div class="alert alert-block ">
							<button type="button" class="close" data-dismiss="alert">×</button>
							<h4 class="alert-heading">Warning!</h4>
							<p>Best check yo self, you're not looking too good. Nulla vitae elit libero, a pharetra augue. Praesent commodo cursus magna, vel scelerisque nisl consectetur et.</p>
						</div>
					</div>	
				</div><!--/span-->
				
				<div class="box span7">
					<div class="box-header well" data-original-title>
						<h2><i class="icon-bell"></i> Notifications</h2>
						<div class="box-icon">
							<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
							<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a>
						</div>
					</div>
					<div class="box-content">
						<div class="alert alert-info">
							Click buttons below to see Pop Notifications.
						</div>
						<p class="center">
							<button class="btn btn-primary noty" data-noty-options='{"text":"This is a success notification","layout":"topLeft","type":"success"}'><i class="icon-bell icon-white"></i> Top Left</button>
							<button class="btn btn-primary noty" data-noty-options='{"text":"This is an alert notification with fade effect","layout":"topCenter","type":"alert","animateOpen": {"opacity": "show"}}'><i class="icon-bell icon-white"></i> Top Center (fade)</button>
							<button class="btn btn-primary noty" data-noty-options='{"text":"This is an error notification","layout":"topRight","type":"error"}'><i class="icon-bell icon-white"></i> Top Right</button>
							<button class="btn btn-primary noty" data-noty-options='{"text":"This is a success information","layout":"top","type":"information"}'><i class="icon-bell icon-white"></i> Top Full Width</button>
						</p>
						<p class="center">
							<button class="btn btn-primary noty" data-noty-options='{"text":"This is an alert notification with fade effect","layout":"center","type":"alert","animateOpen": {"opacity": "show"}}'><i class="icon-bell icon-white"></i> Center (fade)</button>
							<button class="btn btn-primary noty" data-noty-options='{"text":"This is an error notification","layout":"center","type":"error"}'><i class="icon-bell icon-white"></i> Center</button>
						</p>
						
						<p class="center">
							<button class="btn btn-primary noty" data-noty-options='{"text":"This is an error notification","layout":"bottomLeft","type":"error"}'><i class="icon-bell icon-white"></i> Bottom Left</button>
							<button class="btn btn-primary noty" data-noty-options='{"text":"This is an alert notification with fade effect","layout":"bottomRight","type":"alert","animateOpen": {"opacity": "show"}}'><i class="icon-bell icon-white"></i> Bottom Right (fade)</button>
						</p>

						<p class="center">
							<button class="btn btn-primary noty" data-noty-options='{"text":"This is an alert","layout":"bottom","type":"alert","closeButton":"true"}'><i class="icon-bell icon-white"></i> Bottom Full Width with Close Button</button>
						</p>
					</div>	
				</div><!--/span-->
				
				<div class="box span7">
					<div class="box-header well" data-original-title>
						<h2><i class="icon-refresh"></i> Ajax Loaders</h2>
						<div class="box-icon">
							<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
							<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a>
						</div>
					</div>
					<div class="box-content">
						<ul class="ajax-loaders">
														<li><img src="${ctx}/resources/pt/img/ajax-loaders/ajax-loader-1.gif" title="img/ajax-loaders/ajax-loader-1.gif" ></li>
														<li><img src="${ctx}/resources/pt/img/ajax-loaders/ajax-loader-2.gif" title="img/ajax-loaders/ajax-loader-2.gif" ></li>
														<li><img src="${ctx}/resources/pt/img/ajax-loaders/ajax-loader-3.gif" title="img/ajax-loaders/ajax-loader-3.gif" ></li>
														<li><img src="${ctx}/resources/pt/img/ajax-loaders/ajax-loader-4.gif" title="img/ajax-loaders/ajax-loader-4.gif" ></li>
														<li><img src="${ctx}/resources/pt/img/ajax-loaders/ajax-loader-5.gif" title="img/ajax-loaders/ajax-loader-5.gif" ></li>
														<li><img src="${ctx}/resources/pt/img/ajax-loaders/ajax-loader-6.gif" title="img/ajax-loaders/ajax-loader-6.gif" ></li>
														<li><img src="${ctx}/resources/pt/img/ajax-loaders/ajax-loader-7.gif" title="img/ajax-loaders/ajax-loader-7.gif" ></li>
														<li><img src="${ctx}/resources/pt/img/ajax-loaders/ajax-loader-8.gif" title="img/ajax-loaders/ajax-loader-8.gif" ></li>
												</ul>
						
					</div>	
				</div><!--/span-->
			</div><!--/row-->
			
		
					<!-- content ends -->
			</div><!--/#content.span10-->
				</div><!--/fluid-row-->
				
		<hr>

		<div class="modal hide fade" id="myModal">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">×</button>
				<h3>Settings</h3>
			</div>
			<div class="modal-body">
				<p>Here settings can be configured...</p>
			</div>
			<div class="modal-footer">
				<a href="#" class="btn" data-dismiss="modal">Close</a>
				<a href="#" class="btn btn-primary">Save changes</a>
			</div>
		</div>

		<%@ include file="/pt/common/footer.jsp" %>
		
	</div><!--/.fluid-container-->
	

</body>
</html>