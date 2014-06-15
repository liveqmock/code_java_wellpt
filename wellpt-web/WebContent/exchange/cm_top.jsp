<%@ page language="java" pageEncoding="UTF-8" %>
<div class="navbar">
	<div class="navbar-inner">
		<div class="container-fluid">

			<a class="brand" href="index.jsp"> <img alt="welloa logo"
				src="${ctx}/resources/wellpt/img/logo20.png" /> <span>OA</span></a>

			<!-- theme selector starts -->
			<div class="btn-group pull-right theme-container">
				<a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
					<i class="icon-tint"></i><span class="hidden-phone"> Change
						Theme / Skin</span> <span class="caret"></span>
				</a>
				<ul class="dropdown-menu" id="themes">
					<li><a data-value="classic" href="#"><i class="icon-blank"></i>
							Classic</a></li>
					<li><a data-value="cerulean" href="#"><i
							class="icon-blank"></i> Cerulean</a></li>
					<li><a data-value="cyborg" href="#"><i class="icon-blank"></i>
							Cyborg</a></li>
					<li><a data-value="redy" href="#"><i class="icon-blank"></i>
							Redy</a></li>
					<li><a data-value="journal" href="#"><i class="icon-blank"></i>
							Journal</a></li>
					<li><a data-value="simplex" href="#"><i class="icon-blank"></i>
							Simplex</a></li>
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
					<i class="icon-user"></i><span class="hidden-phone"> admin</span> <span
					class="caret"></span>
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