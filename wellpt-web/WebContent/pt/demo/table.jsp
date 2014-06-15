<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/pt/common/header.jsp"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.wellsoft.pt.org.entity.User"%>
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
						<li><a href="table.jsp">表格</a></li>
					</ul>
				</div>

				<div class="row-fluid sortable">
					<%
						ArrayList users= new ArrayList();
						User user1 =new User();
						user1.setLoginName("David R");
						user1.setSex("男");
						
						User user2 =new User();
						user2.setLoginName("li lin");
						user2.setSex("女");
						users.add(user1);
						users.add(user2);
						request.setAttribute("users",users);
					%>
					<div class="box span12">
						<div class="box-header well" data-original-title>
							<h2>
								<i class="icon-user"></i> 用户测试
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
							<table class="table table-striped  datatable">
								<thead>
									<tr >
										<th>用户名</th>
										<th>性别</th>
										
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${users}" var="user">
									<!-- 选中打开新页面  -->
										<tr onclick="window.open('update/${user.loginName}')">
											<td>${user.loginName}</td>
											<td>${user.sex}</td>
																					
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
					<!--/span-->

				</div>
				<!--/row-->
	          <div class="row-fluid sortable">
				<div class="box span12">
					<div class="box-header well" data-original-title>
						<h2>
							<i class="icon-user"></i> Members
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
						<table class="table table-striped table-bordered  datatable">
							<thead>
								<tr>
									<th>Username</th>
									<th>Date registered</th>
									<th>Role</th>
									<th>Status</th>
									<th>Actions</th>
								</tr>
							</thead>
							<tbody>


								<tr>
									<td>David R</td>
									<td class="center">2012/01/01</td>
									<td class="center">Member</td>
									<td class="center"><span class="label label-success">Active</span>
									</td>
									<td class="center"><a class="btn btn-success" href="#">
											<i class="icon-zoom-in icon-white"></i> View
									</a> <a class="btn btn-info" href="#"> <i
											class="icon-edit icon-white"></i> Edit
									</a> <a class="btn btn-danger" href="#"> <i
											class="icon-trash icon-white"></i> Delete
									</a></td>
								</tr>
								<tr>
									<td>Chris Jack</td>
									<td class="center">2012/01/01</td>
									<td class="center">Member</td>
									<td class="center"><span class="label label-success">Active</span>
									</td>
									<td class="center"><a class="btn btn-success" href="#">
											<i class="icon-zoom-in icon-white"></i> View
									</a> <a class="btn btn-info" href="#"> <i
											class="icon-edit icon-white"></i> Edit
									</a> <a class="btn btn-danger" href="#"> <i
											class="icon-trash icon-white"></i> Delete
									</a></td>
								</tr>
								<tr>
									<td>Jack Chris</td>
									<td class="center">2012/01/01</td>
									<td class="center">Member</td>
									<td class="center"><span class="label label-success">Active</span>
									</td>
									<td class="center"><a class="btn btn-success" href="#">
											<i class="icon-zoom-in icon-white"></i> View
									</a> <a class="btn btn-info" href="#"> <i
											class="icon-edit icon-white"></i> Edit
									</a> <a class="btn btn-danger" href="#"> <i
											class="icon-trash icon-white"></i> Delete
									</a></td>
								</tr>
								<tr>
									<td>Muhammad Usman</td>
									<td class="center">2012/01/01</td>
									<td class="center">Member</td>
									<td class="center"><span class="label label-success">Active</span>
									</td>
									<td class="center"><a class="btn btn-success" href="#">
											<i class="icon-zoom-in icon-white"></i> View
									</a> <a class="btn btn-info" href="#"> <i
											class="icon-edit icon-white"></i> Edit
									</a> <a class="btn btn-danger" href="#"> <i
											class="icon-trash icon-white"></i> Delete
									</a></td>
								</tr>
								<tr>
									<td>Sheikh Heera</td>
									<td class="center">2012/02/01</td>
									<td class="center">Staff</td>
									<td class="center"><span class="label label-important">Banned</span>
									</td>
									<td class="center"><a class="btn btn-success" href="#">
											<i class="icon-zoom-in icon-white"></i> View
									</a> <a class="btn btn-info" href="#"> <i
											class="icon-edit icon-white"></i> Edit
									</a> <a class="btn btn-danger" href="#"> <i
											class="icon-trash icon-white"></i> Delete
									</a></td>
								</tr>
								<tr>
									<td>Helen Garner</td>
									<td class="center">2012/02/01</td>
									<td class="center">Staff</td>
									<td class="center"><span class="label label-important">Banned</span>
									</td>
									<td class="center"><a class="btn btn-success" href="#">
											<i class="icon-zoom-in icon-white"></i> View
									</a> <a class="btn btn-info" href="#"> <i
											class="icon-edit icon-white"></i> Edit
									</a> <a class="btn btn-danger" href="#"> <i
											class="icon-trash icon-white"></i> Delete
									</a></td>
								</tr>
								<tr>
									<td>Saruar Ahmed</td>
									<td class="center">2012/03/01</td>
									<td class="center">Member</td>
									<td class="center"><span class="label label-warning">Pending</span>
									</td>
									<td class="center"><a class="btn btn-success" href="#">
											<i class="icon-zoom-in icon-white"></i> View
									</a> <a class="btn btn-info" href="#"> <i
											class="icon-edit icon-white"></i> Edit
									</a> <a class="btn btn-danger" href="#"> <i
											class="icon-trash icon-white"></i> Delete
									</a></td>
								</tr>
								<tr>
									<td>Ahemd Saruar</td>
									<td class="center">2012/03/01</td>
									<td class="center">Member</td>
									<td class="center"><span class="label label-warning">Pending</span>
									</td>
									<td class="center"><a class="btn btn-success" href="#">
											<i class="icon-zoom-in icon-white"></i> View
									</a> <a class="btn btn-info" href="#"> <i
											class="icon-edit icon-white"></i> Edit
									</a> <a class="btn btn-danger" href="#"> <i
											class="icon-trash icon-white"></i> Delete
									</a></td>
								</tr>
								<tr>
									<td>Habib Rizwan</td>
									<td class="center">2012/01/21</td>
									<td class="center">Staff</td>
									<td class="center"><span class="label label-success">Active</span>
									</td>
									<td class="center"><a class="btn btn-success" href="#">
											<i class="icon-zoom-in icon-white"></i> View
									</a> <a class="btn btn-info" href="#"> <i
											class="icon-edit icon-white"></i> Edit
									</a> <a class="btn btn-danger" href="#"> <i
											class="icon-trash icon-white"></i> Delete
									</a></td>
								</tr>
								<tr>
									<td>Rizwan Habib</td>
									<td class="center">2012/01/21</td>
									<td class="center">Staff</td>
									<td class="center"><span class="label label-success">Active</span>
									</td>
									<td class="center"><a class="btn btn-success" href="#">
											<i class="icon-zoom-in icon-white"></i> View
									</a> <a class="btn btn-info" href="#"> <i
											class="icon-edit icon-white"></i> Edit
									</a> <a class="btn btn-danger" href="#"> <i
											class="icon-trash icon-white"></i> Delete
									</a></td>
								</tr>
								<tr>
									<td>Amrin Sana</td>
									<td class="center">2012/08/23</td>
									<td class="center">Staff</td>
									<td class="center"><span class="label label-important">Banned</span>
									</td>
									<td class="center"><a class="btn btn-success" href="#">
											<i class="icon-zoom-in icon-white"></i> View
									</a> <a class="btn btn-info" href="#"> <i
											class="icon-edit icon-white"></i> Edit
									</a> <a class="btn btn-danger" href="#"> <i
											class="icon-trash icon-white"></i> Delete
									</a></td>
								</tr>
								<tr>
									<td>Sana Amrin</td>
									<td class="center">2012/08/23</td>
									<td class="center">Staff</td>
									<td class="center"><span class="label label-important">Banned</span>
									</td>
									<td class="center"><a class="btn btn-success" href="#">
											<i class="icon-zoom-in icon-white"></i> View
									</a> <a class="btn btn-info" href="#"> <i
											class="icon-edit icon-white"></i> Edit
									</a> <a class="btn btn-danger" href="#"> <i
											class="icon-trash icon-white"></i> Delete
									</a></td>
								</tr>
								<tr>
									<td>Ifrah Jannat</td>
									<td class="center">2012/06/01</td>
									<td class="center">Admin</td>
									<td class="center"><span class="label">Inactive</span></td>
									<td class="center"><a class="btn btn-success" href="#">
											<i class="icon-zoom-in icon-white"></i> View
									</a> <a class="btn btn-info" href="#"> <i
											class="icon-edit icon-white"></i> Edit
									</a> <a class="btn btn-danger" href="#"> <i
											class="icon-trash icon-white"></i> Delete
									</a></td>
								</tr>
								<tr>
									<td>Jannat Ifrah</td>
									<td class="center">2012/06/01</td>
									<td class="center">Admin</td>
									<td class="center"><span class="label">Inactive</span></td>
									<td class="center"><a class="btn btn-success" href="#">
											<i class="icon-zoom-in icon-white"></i> View
									</a> <a class="btn btn-info" href="#"> <i
											class="icon-edit icon-white"></i> Edit
									</a> <a class="btn btn-danger" href="#"> <i
											class="icon-trash icon-white"></i> Delete
									</a></td>
								</tr>
								<tr>
									<td>Robert</td>
									<td class="center">2012/03/01</td>
									<td class="center">Member</td>
									<td class="center"><span class="label label-warning">Pending</span>
									</td>
									<td class="center"><a class="btn btn-success" href="#">
											<i class="icon-zoom-in icon-white"></i> View
									</a> <a class="btn btn-info" href="#"> <i
											class="icon-edit icon-white"></i> Edit
									</a> <a class="btn btn-danger" href="#"> <i
											class="icon-trash icon-white"></i> Delete
									</a></td>
								</tr>
								<tr>
									<td>Dave Robert</td>
									<td class="center">2012/03/01</td>
									<td class="center">Member</td>
									<td class="center"><span class="label label-warning">Pending</span>
									</td>
									<td class="center"><a class="btn btn-success" href="#">
											<i class="icon-zoom-in icon-white"></i> View
									</a> <a class="btn btn-info" href="#"> <i
											class="icon-edit icon-white"></i> Edit
									</a> <a class="btn btn-danger" href="#"> <i
											class="icon-trash icon-white"></i> Delete
									</a></td>
								</tr>
								<tr>
									<td>Brown Robert</td>
									<td class="center">2012/03/01</td>
									<td class="center">Member</td>
									<td class="center"><span class="label label-warning">Pending</span>
									</td>
									<td class="center"><a class="btn btn-success" href="#">
											<i class="icon-zoom-in icon-white"></i> View
									</a> <a class="btn btn-info" href="#"> <i
											class="icon-edit icon-white"></i> Edit
									</a> <a class="btn btn-danger" href="#"> <i
											class="icon-trash icon-white"></i> Delete
									</a></td>
								</tr>
								<tr>
									<td>Usman Muhammad</td>
									<td class="center">2012/01/01</td>
									<td class="center">Member</td>
									<td class="center"><span class="label label-success">Active</span>
									</td>
									<td class="center"><a class="btn btn-success" href="#">
											<i class="icon-zoom-in icon-white"></i> View
									</a> <a class="btn btn-info" href="#"> <i
											class="icon-edit icon-white"></i> Edit
									</a> <a class="btn btn-danger" href="#"> <i
											class="icon-trash icon-white"></i> Delete
									</a></td>
								</tr>
								<tr>
									<td>Abdullah</td>
									<td class="center">2012/02/01</td>
									<td class="center">Staff</td>
									<td class="center"><span class="label label-important">Banned</span>
									</td>
									<td class="center"><a class="btn btn-success" href="#">
											<i class="icon-zoom-in icon-white"></i> View
									</a> <a class="btn btn-info" href="#"> <i
											class="icon-edit icon-white"></i> Edit
									</a> <a class="btn btn-danger" href="#"> <i
											class="icon-trash icon-white"></i> Delete
									</a></td>
								</tr>
								<tr>
									<td>Dow John</td>
									<td class="center">2012/02/01</td>
									<td class="center">Admin</td>
									<td class="center"><span class="label">Inactive</span></td>
									<td class="center"><a class="btn btn-success" href="#">
											<i class="icon-zoom-in icon-white"></i> View
									</a> <a class="btn btn-info" href="#"> <i
											class="icon-edit icon-white"></i> Edit
									</a> <a class="btn btn-danger" href="#"> <i
											class="icon-trash icon-white"></i> Delete
									</a></td>
								</tr>
								<tr>
									<td>John R</td>
									<td class="center">2012/02/01</td>
									<td class="center">Admin</td>
									<td class="center"><span class="label">Inactive</span></td>
									<td class="center"><a class="btn btn-success" href="#">
											<i class="icon-zoom-in icon-white"></i> View
									</a> <a class="btn btn-info" href="#"> <i
											class="icon-edit icon-white"></i> Edit
									</a> <a class="btn btn-danger" href="#"> <i
											class="icon-trash icon-white"></i> Delete
									</a></td>
								</tr>
								<tr>
									<td>Paul Wilson</td>
									<td class="center">2012/03/01</td>
									<td class="center">Member</td>
									<td class="center"><span class="label label-warning">Pending</span>
									</td>
									<td class="center"><a class="btn btn-success" href="#">
											<i class="icon-zoom-in icon-white"></i> View
									</a> <a class="btn btn-info" href="#"> <i
											class="icon-edit icon-white"></i> Edit
									</a> <a class="btn btn-danger" href="#"> <i
											class="icon-trash icon-white"></i> Delete
									</a></td>
								</tr>
								<tr>
									<td>Wilson Paul</td>
									<td class="center">2012/03/01</td>
									<td class="center">Member</td>
									<td class="center"><span class="label label-warning">Pending</span>
									</td>
									<td class="center"><a class="btn btn-success" href="#">
											<i class="icon-zoom-in icon-white"></i> View
									</a> <a class="btn btn-info" href="#"> <i
											class="icon-edit icon-white"></i> Edit
									</a> <a class="btn btn-danger" href="#"> <i
											class="icon-trash icon-white"></i> Delete
									</a></td>
								</tr>
								<tr>
									<td>Heera Sheikh</td>
									<td class="center">2012/01/21</td>
									<td class="center">Staff</td>
									<td class="center"><span class="label label-success">Active</span>
									</td>
									<td class="center"><a class="btn btn-success" href="#">
											<i class="icon-zoom-in icon-white"></i> View
									</a> <a class="btn btn-info" href="#"> <i
											class="icon-edit icon-white"></i> Edit
									</a> <a class="btn btn-danger" href="#"> <i
											class="icon-trash icon-white"></i> Delete
									</a></td>
								</tr>
								<tr>
									<td>Sheikh Heera</td>
									<td class="center">2012/01/21</td>
									<td class="center">Staff</td>
									<td class="center"><span class="label label-success">Active</span>
									</td>
									<td class="center"><a class="btn btn-success" href="#">
											<i class="icon-zoom-in icon-white"></i> View
									</a> <a class="btn btn-info" href="#"> <i
											class="icon-edit icon-white"></i> Edit
									</a> <a class="btn btn-danger" href="#"> <i
											class="icon-trash icon-white"></i> Delete
									</a></td>
								</tr>
								<tr>
									<td>Christopher</td>
									<td class="center">2012/08/23</td>
									<td class="center">Staff</td>
									<td class="center"><span class="label label-important">Banned</span>
									</td>
									<td class="center"><a class="btn btn-success" href="#">
											<i class="icon-zoom-in icon-white"></i> View
									</a> <a class="btn btn-info" href="#"> <i
											class="icon-edit icon-white"></i> Edit
									</a> <a class="btn btn-danger" href="#"> <i
											class="icon-trash icon-white"></i> Delete
									</a></td>
								</tr>
								<tr>
									<td>Andro Christopher</td>
									<td class="center">2012/08/23</td>
									<td class="center">Staff</td>
									<td class="center"><span class="label label-important">Banned</span>
									</td>
									<td class="center"><a class="btn btn-success" href="#">
											<i class="icon-zoom-in icon-white"></i> View
									</a> <a class="btn btn-info" href="#"> <i
											class="icon-edit icon-white"></i> Edit
									</a> <a class="btn btn-danger" href="#"> <i
											class="icon-trash icon-white"></i> Delete
									</a></td>
								</tr>
								<tr>
									<td>Jhon Doe</td>
									<td class="center">2012/06/01</td>
									<td class="center">Admin</td>
									<td class="center"><span class="label">Inactive</span></td>
									<td class="center"><a class="btn btn-success" href="#">
											<i class="icon-zoom-in icon-white"></i> View
									</a> <a class="btn btn-info" href="#"> <i
											class="icon-edit icon-white"></i> Edit
									</a> <a class="btn btn-danger" href="#"> <i
											class="icon-trash icon-white"></i> Delete
									</a></td>
								</tr>
								<tr>
									<td>Lorem Ipsum</td>
									<td class="center">2012/03/01</td>
									<td class="center">Member</td>
									<td class="center"><span class="label label-warning">Pending</span>
									</td>
									<td class="center"><a class="btn btn-success" href="#">
											<i class="icon-zoom-in icon-white"></i> View
									</a> <a class="btn btn-info" href="#"> <i
											class="icon-edit icon-white"></i> Edit
									</a> <a class="btn btn-danger" href="#"> <i
											class="icon-trash icon-white"></i> Delete
									</a></td>
								</tr>
								<tr>
									<td>Abraham</td>
									<td class="center">2012/03/01</td>
									<td class="center">Member</td>
									<td class="center"><span class="label label-warning">Pending</span>
									</td>
									<td class="center"><a class="btn btn-success" href="#">
											<i class="icon-zoom-in icon-white"></i> View
									</a> <a class="btn btn-info" href="#"> <i
											class="icon-edit icon-white"></i> Edit
									</a> <a class="btn btn-danger" href="#"> <i
											class="icon-trash icon-white"></i> Delete
									</a></td>
								</tr>
								<tr>
									<td>Brown Blue</td>
									<td class="center">2012/03/01</td>
									<td class="center">Member</td>
									<td class="center"><span class="label label-warning">Pending</span>
									</td>
									<td class="center"><a class="btn btn-success" href="#">
											<i class="icon-zoom-in icon-white"></i> View
									</a> <a class="btn btn-info" href="#"> <i
											class="icon-edit icon-white"></i> Edit
									</a> <a class="btn btn-danger" href="#"> <i
											class="icon-trash icon-white"></i> Delete
									</a></td>
								</tr>
								<tr>
									<td>Worth Name</td>
									<td class="center">2012/03/01</td>
									<td class="center">Member</td>
									<td class="center"><span class="label label-warning">Pending</span>
									</td>
									<td class="center"><a class="btn btn-success" href="#">
											<i class="icon-zoom-in icon-white"></i> View
									</a> <a class="btn btn-info" href="#"> <i
											class="icon-edit icon-white"></i> Edit
									</a> <a class="btn btn-danger" href="#"> <i
											class="icon-trash icon-white"></i> Delete
									</a></td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				<!--/span-->

			</div>
			<!--/row-->

			<div class="row-fluid sortable">
				<div class="box span6">
					<div class="box-header well" data-original-title>
						<h2>Simple Table</h2>
						<div class="box-icon">
							<a href="#" class="btn btn-setting btn-round"><i
								class="icon-cog"></i></a> <a href="#"
								class="btn btn-minimize btn-round"><i
								class="icon-chevron-up"></i></a> <a href="#"
								class="btn btn-close btn-round"><i class="icon-remove"></i></a>
						</div>
					</div>
					<div class="box-content">
						<table class="table">
							<thead>
								<tr>
									<th>Username</th>
									<th>Date registered</th>
									<th>Role</th>
									<th>Status</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>Muhammad Usman</td>
									<td class="center">2012/01/01</td>
									<td class="center">Member</td>
									<td class="center"><span class="label label-success">Active</span>
									</td>
								</tr>
								<tr>
									<td>White Horse</td>
									<td class="center">2012/02/01</td>
									<td class="center">Staff</td>
									<td class="center"><span class="label label-important">Banned</span>
									</td>
								</tr>
								<tr>
									<td>Sheikh Heera</td>
									<td class="center">2012/02/01</td>
									<td class="center">Admin</td>
									<td class="center"><span class="label">Inactive</span></td>
								</tr>
								<tr>
									<td>Saruar</td>
									<td class="center">2012/03/01</td>
									<td class="center">Member</td>
									<td class="center"><span class="label label-warning">Pending</span>
									</td>
								</tr>
								<tr>
									<td>Sana Amrin</td>
									<td class="center">2012/01/21</td>
									<td class="center">Staff</td>
									<td class="center"><span class="label label-success">Active</span>
									</td>
								</tr>
							</tbody>
						</table>
						<div class="pagination pagination-centered">
							<ul>
								<li><a href="#">Prev</a></li>
								<li class="active"><a href="#">1</a></li>
								<li><a href="#">2</a></li>
								<li><a href="#">3</a></li>
								<li><a href="#">4</a></li>
								<li><a href="#">Next</a></li>
							</ul>
						</div>
					</div>
				</div>
				<!--/span-->

				<div class="box span6">
					<div class="box-header well" data-original-title>
						<h2>Striped Table</h2>
						<div class="box-icon">
							<a href="#" class="btn btn-setting btn-round"><i
								class="icon-cog"></i></a> <a href="#"
								class="btn btn-minimize btn-round"><i
								class="icon-chevron-up"></i></a> <a href="#"
								class="btn btn-close btn-round"><i class="icon-remove"></i></a>
						</div>
					</div>
					<div class="box-content">
						<table class="table table-striped">
							<thead>
								<tr>
									<th>Username</th>
									<th>Date registered</th>
									<th>Role</th>
									<th>Status</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>Muhammad Usman</td>
									<td class="center">2012/01/01</td>
									<td class="center">Member</td>
									<td class="center"><span class="label label-success">Active</span>
									</td>
								</tr>
								<tr>
									<td>White Horse</td>
									<td class="center">2012/02/01</td>
									<td class="center">Staff</td>
									<td class="center"><span class="label label-important">Banned</span>
									</td>
								</tr>
								<tr>
									<td>Sheikh Heera</td>
									<td class="center">2012/02/01</td>
									<td class="center">Admin</td>
									<td class="center"><span class="label">Inactive</span></td>
								</tr>
								<tr>
									<td>Saruar</td>
									<td class="center">2012/03/01</td>
									<td class="center">Member</td>
									<td class="center"><span class="label label-warning">Pending</span>
									</td>
								</tr>
								<tr>
									<td>Sana Amrin</td>
									<td class="center">2012/01/21</td>
									<td class="center">Staff</td>
									<td class="center"><span class="label label-success">Active</span>
									</td>
								</tr>
							</tbody>
						</table>
						<div class="pagination pagination-centered">
							<ul>
								<li><a href="#">Prev</a></li>
								<li class="active"><a href="#">1</a></li>
								<li><a href="#">2</a></li>
								<li><a href="#">3</a></li>
								<li><a href="#">4</a></li>
								<li><a href="#">Next</a></li>
							</ul>
						</div>
					</div>
				</div>
				<!--/span-->
			</div>
			<!--/row-->

			<div class="row-fluid sortable">
				<div class="box span6">
					<div class="box-header well" data-original-title>
						<h2>Bordered Table</h2>
						<div class="box-icon">
							<a href="#" class="btn btn-setting btn-round"><i
								class="icon-cog"></i></a> <a href="#"
								class="btn btn-minimize btn-round"><i
								class="icon-chevron-up"></i></a> <a href="#"
								class="btn btn-close btn-round"><i class="icon-remove"></i></a>
						</div>
					</div>
					<div class="box-content">
						<table class="table table-bordered">
							<thead>
								<tr>
									<th>Username</th>
									<th>Date registered</th>
									<th>Role</th>
									<th>Status</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>Muhammad Usman</td>
									<td class="center">2012/01/01</td>
									<td class="center">Member</td>
									<td class="center"><span class="label label-success">Active</span>
									</td>
								</tr>
								<tr>
									<td>White Horse</td>
									<td class="center">2012/02/01</td>
									<td class="center">Staff</td>
									<td class="center"><span class="label label-important">Banned</span>
									</td>
								</tr>
								<tr>
									<td>Sheikh Heera</td>
									<td class="center">2012/02/01</td>
									<td class="center">Admin</td>
									<td class="center"><span class="label">Inactive</span></td>
								</tr>
								<tr>
									<td>Saruar</td>
									<td class="center">2012/03/01</td>
									<td class="center">Member</td>
									<td class="center"><span class="label label-warning">Pending</span>
									</td>
								</tr>
								<tr>
									<td>Sana Amrin</td>
									<td class="center">2012/01/21</td>
									<td class="center">Staff</td>
									<td class="center"><span class="label label-success">Active</span>
									</td>
								</tr>
							</tbody>
						</table>
						<div class="pagination pagination-centered">
							<ul>
								<li><a href="#">Prev</a></li>
								<li class="active"><a href="#">1</a></li>
								<li><a href="#">2</a></li>
								<li><a href="#">3</a></li>
								<li><a href="#">4</a></li>
								<li><a href="#">Next</a></li>
							</ul>
						</div>
					</div>
				</div>
				<!--/span-->

				<div class="box span6">
					<div class="box-header well" data-original-title>
						<h2>Condensed Table</h2>
						<div class="box-icon">
							<a href="#" class="btn btn-setting btn-round"><i
								class="icon-cog"></i></a> <a href="#"
								class="btn btn-minimize btn-round"><i
								class="icon-chevron-up"></i></a> <a href="#"
								class="btn btn-close btn-round"><i class="icon-remove"></i></a>
						</div>
					</div>
					<div class="box-content">
						<table class="table table-condensed">
							<thead>
								<tr>
									<th>Username</th>
									<th>Date registered</th>
									<th>Role</th>
									<th>Status</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>Muhammad Usman</td>
									<td class="center">2012/01/01</td>
									<td class="center">Member</td>
									<td class="center"><span class="label label-success">Active</span>
									</td>
								</tr>
								<tr>
									<td>White Horse</td>
									<td class="center">2012/02/01</td>
									<td class="center">Staff</td>
									<td class="center"><span class="label label-important">Banned</span>
									</td>
								</tr>
								<tr>
									<td>Sheikh Heera</td>
									<td class="center">2012/02/01</td>
									<td class="center">Admin</td>
									<td class="center"><span class="label">Inactive</span></td>
								</tr>
								<tr>
									<td>Saruar</td>
									<td class="center">2012/03/01</td>
									<td class="center">Member</td>
									<td class="center"><span class="label label-warning">Pending</span>
									</td>
								</tr>
								<tr>
									<td>Sana Amrin</td>
									<td class="center">2012/01/21</td>
									<td class="center">Staff</td>
									<td class="center"><span class="label label-success">Active</span>
									</td>
								</tr>
							</tbody>
						</table>
						<div class="pagination pagination-centered">
							<ul>
								<li><a href="#">Prev</a></li>
								<li class="active"><a href="#">1</a></li>
								<li><a href="#">2</a></li>
								<li><a href="#">3</a></li>
								<li><a href="#">4</a></li>
								<li><a href="#">Next</a></li>
							</ul>
						</div>
					</div>
				</div>
				<!--/span-->

			</div>
			<!--/row-->

			<div class="row-fluid sortable">
				<div class="box span12">
					<div class="box-header well" data-original-title>
						<h2>Combined All</h2>
						<div class="box-icon">
							<a href="#" class="btn btn-setting btn-round"><i
								class="icon-cog"></i></a> <a href="#"
								class="btn btn-minimize btn-round"><i
								class="icon-chevron-up"></i></a> <a href="#"
								class="btn btn-close btn-round"><i class="icon-remove"></i></a>
						</div>
					</div>
					<div class="box-content">
						<table class="table table-bordered table-striped table-condensed">
							<thead>
								<tr>
									<th>Username</th>
									<th>Date registered</th>
									<th>Role</th>
									<th>Status</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>Muhammad Usman</td>
									<td class="center">2012/01/01</td>
									<td class="center">Member</td>
									<td class="center"><span class="label label-success">Active</span>
									</td>
								</tr>
								<tr>
									<td>White Horse</td>
									<td class="center">2012/02/01</td>
									<td class="center">Staff</td>
									<td class="center"><span class="label label-important">Banned</span>
									</td>
								</tr>
								<tr>
									<td>Sheikh Heera</td>
									<td class="center">2012/02/01</td>
									<td class="center">Admin</td>
									<td class="center"><span class="label">Inactive</span></td>
								</tr>
								<tr>
									<td>Saruar</td>
									<td class="center">2012/03/01</td>
									<td class="center">Member</td>
									<td class="center"><span class="label label-warning">Pending</span>
									</td>
								</tr>
								<tr>
									<td>Sana Amrin</td>
									<td class="center">2012/01/21</td>
									<td class="center">Staff</td>
									<td class="center"><span class="label label-success">Active</span>
									</td>
								</tr>
							</tbody>
						</table>
						<div class="pagination pagination-centered">
							<ul>
								<li><a href="#">Prev</a></li>
								<li class="active"><a href="#">1</a></li>
								<li><a href="#">2</a></li>
								<li><a href="#">3</a></li>
								<li><a href="#">4</a></li>
								<li><a href="#">Next</a></li>
							</ul>
						</div>
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
		$(document).ready(function() {
			$('.btn-setting').click(function(e) {
				e.preventDefault();
				$('#myModal').modal('show');
			});

		})
	</script>
</body>
</html>