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


			<!-- 核心内容 span10-->
			<div id="content" class="span10">
				<div>
					<!-- 面包屑导航 -->
					<ul class="breadcrumb">
						<li><a href="#">系统</a> <span class="divider">/</span></li>
						<li><a href="#">首页</a></li>
					</ul>
				</div>
				<!--工具提示 sortable可排序-->
				<div class="sortable row-fluid">
					<a data-rel="tooltip" title="6 new members."
						class="well span3 top-block" href="#"> <span
						class="icon32 icon-red icon-user"></span>
						<div>Total Members</div>
						<div>507</div> <span class="notification">6</span>
					</a> 
					<a data-rel="tooltip" title="4 new pro members."
						class="well span3 top-block" href="#"> <span
						class="icon32 icon-color icon-star-on"></span>
						<div>Pro Members</div>
						<div>228</div> <span class="notification green">4</span>
					</a> 
					<a data-rel="tooltip" title="$34 new sales."
						class="well span3 top-block" href="#"> <span
						class="icon32 icon-color icon-cart"></span>
						<div>Sales</div>
						<div>$13320</div> <span class="notification yellow">$34</span>
					</a> 
					<a data-rel="tooltip" title="12 new messages."
						class="well span3 top-block" href="#"> <span
						class="icon32 icon-color icon-envelope-closed"></span>
						<div>Messages</div>
						<div>25</div> <span class="notification red">12</span>
					</a>
				</div>
                <!--工具提示 结束-->
                
                <!--box区域开始  -->
				<div class="row-fluid">
				    <!--box开始  -->
					<div class="box span12">
						<div class="box-header well">
							<h2>
								<i class="icon-info-sign"></i> Introduction
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
							<h1>
								OA <small>free, premium quality, responsive,
									multiple skin admin template.</small>
							</h1>
							<p>Its a live demo of the template. I have created Charisma
								to ease the repeat work I have to do on my projects. Now I
								re-use Charisma as a base for my admin panel work and I am
								sharing it with you :)</p>
							<p>
								<b>All pages in the menu are functional, take a look at all,
									please share this with your followers.</b>
							</p>

						
							<div class="clearfix"></div>
						</div>
					</div>
				     <!--box结束  -->
				</div>

				<div class="row-fluid sortable">
					<div class="box span4">
						<div class="box-header well">
							<h2>
								<i class="icon-th"></i> Tabs
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
							<ul class="nav nav-tabs" id="myTab">
								<li class="active"><a href="#info">Info</a></li>
								<li><a href="#custom">Custom</a></li>
								<li><a href="#messages">Messages</a></li>
							</ul>

							<div id="myTabContent" class="tab-content">
								<div class="tab-pane active" id="info">
									<h3>
										Charisma <small>a fully featued template</small>
									</h3>
									<p>Its a fully featured, responsive template for your admin
										panel. Its optimized for tablet and mobile phones. Scan the QR
										code below to view it in your mobile device.</p>
									<img alt="QR Code" class="charisma_qr center"
										src="${ctx}/resources/pt/img/qrcode136.png" />
								</div>
								<div class="tab-pane" id="custom">
									<h3>
										Custom <small>small text</small>
									</h3>
									<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit.
										Curabitur bibendum ornare dolor.</p>
									<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit.
										Curabitur bibendum ornare dolor, quis ullamcorper ligula
										sodales at. Nulla tellus elit, varius non commodo eget, mattis
										vel eros. In sed ornare nulla. Donec consectetur, velit a
										pharetra ultricies, diam lorem lacinia risus, ac commodo orci
										erat eu massa. Sed sit amet nulla ipsum. Donec felis mauris,
										vulputate sed tempor at, aliquam a ligula. Pellentesque non
										pulvinar nisi.</p>
								</div>
								<div class="tab-pane" id="messages">
									<h3>
										Messages <small>small text</small>
									</h3>
									<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit.
										Curabitur bibendum ornare dolor, quis ullamcorper ligula
										sodales at. Nulla tellus elit, varius non commodo eget, mattis
										vel eros. In sed ornare nulla. Donec consectetur, velit a
										pharetra ultricies, diam lorem lacinia risus, ac commodo orci
										erat eu massa. Sed sit amet nulla ipsum. Donec felis mauris,
										vulputate sed tempor at, aliquam a ligula. Pellentesque non
										pulvinar nisi.</p>
									<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit.
										Curabitur bibendum ornare dolor.</p>
								</div>
							</div>
						</div>
					</div>
					<!--/span-->

					<div class="box span4">
						<div class="box-header well" data-original-title>
							<h2>
								<i class="icon-user"></i> Member Activity
							</h2>
							<div class="box-icon">
								<a href="#" class="btn btn-minimize btn-round"><i
									class="icon-chevron-up"></i></a> <a href="#"
									class="btn btn-close btn-round"><i class="icon-remove"></i></a>
							</div>
						</div>
						<div class="box-content">
							<div class="box-content">
								<ul class="dashboard-list">
									<li><a href="#"> <img class="dashboard-avatar"
											alt=user1
											src=""></a>
										<strong>Name:</strong> <a href="#">user1 </a><br> <strong>Since:</strong>
										17/05/2012<br> <strong>Status:</strong> <span
										class="label label-success">Approved</span></li>
									<li><a href="#"> <img class="dashboard-avatar"
											alt="user2"
											src=""></a>
										<strong>Name:</strong> <a href="#">user2 </a><br>
										<strong>Since:</strong> 17/05/2012<br> <strong>Status:</strong>
										<span class="label label-warning">Pending</span></li>
									<li><a href="#"> <img class="dashboard-avatar"
											alt="user3"
											src=""></a>
										<strong>Name:</strong> <a href="#">user3 </a><br> <strong>Since:</strong>
										25/05/2012<br> <strong>Status:</strong> <span
										class="label label-important">Banned</span></li>
									<li><a href="#"> <img class="dashboard-avatar"
											alt="user4"
											src=""></a>
										<strong>Name:</strong> <a href="#">user4 </a><br>
										<strong>Since:</strong> 17/05/2012<br> <strong>Status:</strong>
										<span class="label label-info">Updates</span></li>
								</ul>
							</div>
						</div>
					</div>
					<!--/span-->

					<div class="box span4">
						<div class="box-header well" data-original-title>
							<h2>
								<i class="icon-list-alt"></i> Realtime Traffic
							</h2>
							<div class="box-icon">
								<a href="#" class="btn btn-minimize btn-round"><i
									class="icon-chevron-up"></i></a> <a href="#"
									class="btn btn-close btn-round"><i class="icon-remove"></i></a>
							</div>
						</div>
						<div class="box-content">
							
							<p class="clearfix">You can update a chart periodically to
								get a real-time effect by using a timer to insert the new data
								in the plot and redraw it.</p>
							<p>
								Time between updates: <input id="updateInterval" type="text"
									value="" style="text-align: right; width: 5em">
								milliseconds
							</p>
						</div>
					</div>
					<!--/span-->
				</div>
				<!--/row-->

				<div class="row-fluid sortable">
					<div class="box span4">
						<div class="box-header well" data-original-title>
							<h2>
								<i class="icon-list"></i> Buttons
							</h2>
							<div class="box-icon">
								<a href="#" class="btn btn-setting btn-round"><i
									class="icon-cog"></i></a> <a href="#"
									class="btn btn-minimize btn-round"><i
									class="icon-chevron-up"></i></a> <a href="#"
									class="btn btn-close btn-round"><i class="icon-remove"></i></a>
							</div>
						</div>
						<div class="box-content buttons">
							<p class="btn-group">
								<button class="btn">Left</button>
								<button class="btn">Middle</button>
								<button class="btn">Right</button>
							</p>
							<p>
								<button class="btn btn-small">
									<i class="icon-star"></i> Icon button
								</button>
								<button class="btn btn-small btn-primary">Small button</button>
								<button class="btn btn-small btn-danger">Small button</button>
							</p>
							<p>
								<button class="btn btn-small btn-warning">Small button</button>
								<button class="btn btn-small btn-success">Small button</button>
								<button class="btn btn-small btn-info">Small button</button>
							</p>
							<p>
								<button class="btn btn-small btn-inverse">Small button</button>
								<button class="btn btn-large btn-primary btn-round">Round
									button</button>
								<button class="btn btn-large btn-round">
									<i class="icon-ok"></i>
								</button>
								<button class="btn btn-primary">
									<i class="icon-edit icon-white"></i>
								</button>
							</p>
							<p>
								<button class="btn btn-mini">Mini button</button>
								<button class="btn btn-mini btn-primary">Mini button</button>
								<button class="btn btn-mini btn-danger">Mini button</button>
								<button class="btn btn-mini btn-warning">Mini button</button>
							</p>
							<p>
								<button class="btn btn-mini btn-info">Mini button</button>
								<button class="btn btn-mini btn-success">Mini button</button>
								<button class="btn btn-mini btn-inverse">Mini button</button>
							</p>
						</div>
					</div>
					<!--/span-->

					<div class="box span4">
						<div class="box-header well" data-original-title>
							<h2>
								<i class="icon-list"></i> Buttons
							</h2>
							<div class="box-icon">
								<a href="#" class="btn btn-setting btn-round"><i
									class="icon-cog"></i></a> <a href="#"
									class="btn btn-minimize btn-round"><i
									class="icon-chevron-up"></i></a> <a href="#"
									class="btn btn-close btn-round"><i class="icon-remove"></i></a>
							</div>
						</div>
						<div class="box-content  buttons">
							<p>
								<button class="btn btn-large">Large button</button>
								<button class="btn btn-large btn-primary">Large button</button>
							</p>
							<p>
								<button class="btn btn-large btn-danger">Large button</button>
								<button class="btn btn-large btn-warning">Large button</button>
							</p>
							<p>
								<button class="btn btn-large btn-success">Large button</button>
								<button class="btn btn-large btn-info">Large button</button>
							</p>
							<p>
								<button class="btn btn-large btn-inverse">Large button</button>
							</p>
							<div class="btn-group">
								<button class="btn btn-large">Large Dropdown</button>
								<button class="btn btn-large dropdown-toggle"
									data-toggle="dropdown">
									<span class="caret"></span>
								</button>
								<ul class="dropdown-menu">
									<li><a href="#"><i class="icon-star"></i> Action</a></li>
									<li><a href="#"><i class="icon-tag"></i> Another
											action</a></li>
									<li><a href="#"><i class="icon-download-alt"></i>
											Something else here</a></li>
									<li class="divider"></li>
									<li><a href="#"><i class="icon-tint"></i> Separated
											link</a></li>
								</ul>
							</div>

						</div>
					</div>
					<!--/span-->

					<div class="box span4">
						<div class="box-header well" data-original-title>
							<h2>
								<i class="icon-list"></i> Weekly Stat
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
							<ul class="dashboard-list">
								<li><a href="#"> <i class="icon-arrow-up"></i> <span
										class="green">92</span> New Comments
								</a></li>
								<li><a href="#"> <i class="icon-arrow-down"></i> <span
										class="red">15</span> New Registrations
								</a></li>
								<li><a href="#"> <i class="icon-minus"></i> <span
										class="blue">36</span> New Articles
								</a></li>
								<li><a href="#"> <i class="icon-comment"></i> <span
										class="yellow">45</span> User reviews
								</a></li>
								<li><a href="#"> <i class="icon-arrow-up"></i> <span
										class="green">112</span> New Comments
								</a></li>
								<li><a href="#"> <i class="icon-arrow-down"></i> <span
										class="red">31</span> New Registrations
								</a></li>
								<li><a href="#"> <i class="icon-minus"></i> <span
										class="blue">93</span> New Articles
								</a></li>
								<li><a href="#"> <i class="icon-comment"></i> <span
										class="yellow">254</span> User reviews
								</a></li>
							</ul>
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
				<a href="#" class="btn" data-dismiss="modal">Close</a> <a href="#"
					class="btn btn-primary">Save changes</a>
			</div>
		</div>

		<%@ include file="/pt/common/footer.jsp"%>

	</div>
	<!--/.fluid-container-->
	

	
	<script>
	$(document).ready(function(){ 
		$('.btn-setting').click(function(e){
		e.preventDefault();
		$('#myModal').modal('show');
	});
	})
	</script>
</body>
</html>