<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<title>办理过程</title>
<%@ include file="/pt/dytable/form_css.jsp"%>
<style type="text/css">
.wf_opinion {
	width: 300px;
	margin: 0 auto;
}

.btn-sign-opinion {
	margin-top: 2px;
	margin-left: 7px;
	float: left;
	height: 22px;
	width: 22px;
	background: url("../../resources/pt/images/workflow/sign_opinion_0.png")
		no-repeat;
	margin-left: 7px;
}

.open .btn-sign-opinion {
	background: url("../../resources/pt/images/workflow/sign_opinion_1.png")
		no-repeat;
}

.wf_opinion .accordion {
	border: none;
	margin: 0px;
	padding: 0px;
	border-top: 1px solid #DDD6D4;
	border-bottom: 1px solid #D9D5D8;
	border-top: 1px solid #DDD6D4;
}

.wf_opinion .accordion-group {
	margin-bottom: 1px;
	border: none;
}

.wf_opinion .accordion-body {
	background-color: #FFF;
	border: none;
}

.wf_opinion .accordion-inner {
	border: 1px solid #C3C3C3;
	padding: 0px;
}

.wf_opinion .accordion-inner ul {
	max-height: 185px;
	overflow: auto;
	list-style: none;
	margin: 0px;
	padding: 0px;
	list-style: none;
}

.wf_opinion .accordion-inner ul li {
	margin: 0px;
	padding: 8px 15px;
	border-bottom: 1px solid #D9D9D9;
}

.wf_opinion .accordion-inner ul li:hover {
	background: none;
	background-color: #3399FF;
	color: #FFF;
	cursor: pointer;
}

.wf_opinion .accordion-inner ul a {
	padding: 0px;
	margin: 0px;
}

.wf_opinion .accordion-inner ul a:hover {
	background: none;
	background-color: #3399FF;
	color: #FFF;
	cursor: pointer;
	background-color: #3399FF;
	background-color: #3399FF;
}

.wf_opinion .dropdown-custom {
	border-radius: 0 0 0 0 !important;
	border: 1px solid #A1A1A1;
	padding-bottom: 1px;
	background: #EFEFEF;
	min-width: 262px;
}

.wf_opinion .dropdown-custom>li>a {
	padding: 0px;
	margin: 0px;
}

.wf_opinion .nav-header {
	text-align: right;
}

.wf_opinion .nav-header .icon-cog-blue {
	width: 15px;
	height: 15px;
	display: inline-block;
	background: url('../../resources/pt/images/workflow/process.png')
		no-repeat scroll -102px -97px transparent;
}

.wf_opinion .nav-header span {
	cursor: pointer;
	color: #174C92;
}

.wf_opinion .accordion {
	margin-bottom: 0px;
}

.wf_opinion .navbar,.navbar-inner {
	width: 40px;
	padding: 0px;
	margin: 0px;
	border: none;
	padding: 0px;
}

.wf_opinion .navbar .nav {
	width: 36px;
	height: 28px;
}

.wf_opinion .nav>li>a {
	padding: 0px;
}

.wf_opinion .dropdown>.dropdown-menu.pull-right:before,.dropdown-menu.pull-right.dropdown-caret:before
	{
	left: auto;
	right: 9px;
}

.wf_opinion .dropdown>.dropdown-menu:before,.dropdown-menu.dropdown-caret:before
	{
	border-bottom: 7px solid rgba(0, 0, 0, 0.2);
	border-left: 7px solid transparent;
	border-right: 7px solid transparent;
	display: inline-block;
	left: 9px;
	position: absolute;
	top: -7px;
}

.wf_opinion .accordion-heading {
	background-color: #0F5A9C;
	position: relative;
}

.wf_opinion .accordion-heading .accordion-toggle:after {
	color: #4C8FBD;
	display: inline-block;
	font-family: FontAwesome;
	font-size: 16px;
	line-height: 18px;
	position: absolute;
	right: 6px;
	text-align: center;
	width: 14px;
}

.wf_opinion .accordion-heading .accordion-toggle {
	color: #F8FFFF;
	position: relative;
	color: #F8FFFF;
}

.wf_opinion .accordion-heading .accordion-toggle-icon {
	bottom: 11px;
	right: 11px;
	width: 20px;
	height: 20px;
	display: inline-block;
	position: absolute;
	background: url('../../resources/pt/images/workflow/process.png')
		no-repeat scroll -69px -91px transparent;
}

.wf_opinion .accordion-heading .accordion-toggle-icon.collapsed {
	background: url('../../resources/pt/images/workflow/process.png')
		no-repeat scroll -69px -121px transparent;
}

.wf_opinion .accordion-heading a {
	text-decoration: none;
}

.wf_opinion .accordion-heading .accordion-toggle.collapsed:after {
	color: #667799;
}

.wf_opinion .accordion-heading .accordion-toggle:after {
	color: #4C8FBD;
	display: inline-block;
	font-family: FontAwesome;
	font-size: 16px;
	line-height: 18px;
	position: absolute;
	right: 6px;
	text-align: center;
	width: 14px;
}

.wf_opinion .accordion-heading .accordion-toggle.collapsed {
	color: #F8FFFF;
	color: #F8FFFF;
	font-weight: normal;
	color: #F8FFFF;
}

.btn-sign-opinion {
	margin-top: 2px;
	margin-left: 7px;
	float: left;
	height: 22px;
	width: 22px;
	background: url("../../resources/pt/images/workflow/sign_opinion_0.png")
		no-repeat;
	margin-left: 7px;
}

.open>.btn-sign-opinion {
	background: url("../../resources/pt/images/workflow/sign_opinion_1.png")
		no-repeat;
}
</style>
</head>
<body style="background: #fff;">
	<div class="wf_opinion">
		<div class="sign-opinion-input">
			<div class="navbar" style="padding: 0px; margin: 0px; border: none;">
				<div class="navbar-inner"
					style="padding: 0px; margin: 0px; border: none;">
					<ul class="nav pull-right">
						<li class="open"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown"><span class="btn-sign-opinion"></span>
						</a>
							<ul
								class="pull-right dropdown-navbar navbar-pink dropdown-menu dropdown-caret dropdown-closer dropdown-custom">
								<li class="nav-header"><span><i
										class="icon-cog-blue"></i>管理</span></li>
								<li><a href="#">
										<div class="clearfix" style="margin: 0 2px; border: none;">
											<div class="accordion" id="accordion">
												<div class="accordion-group">
													<div class="accordion-heading">
														<a class="accordion-toggle collapsed" href="#">意见分类1 </a>
														<span class="accordion-toggle-icon collapsed"></span>
													</div>

													<div class="accordion-body collapse in">
														<div class="accordion-inner">
															<ul>
																<li class="opinion-menu-item"><a href="#"
																	tabindex="-1">同意1同意1同意1同意1同意1同意1同意1</a></li>
																<li class="opinion-menu-item"><a href="#"
																	tabindex="-1">不同意1</a></li>
																<li class="opinion-menu-item"><a href="#"
																	tabindex="-1">同意1</a></li>
																<li class="opinion-menu-item"><a href="#"
																	tabindex="-1">同意1</a></li>
																<li class="opinion-menu-item"><a href="#"
																	tabindex="-1">同意同意同意同意同意同意不同意不同意fffffffff</a></li>
															</ul>
														</div>
													</div>
												</div>

												<div class="accordion-group">
													<div class="accordion-heading">
														<a class="accordion-toggle" href="#">意见分类2</a> <span
															class="accordion-toggle-icon"></span>
													</div>

													<div class="accordion-body collapse">
														<div class="accordion-inner">
															<ul>
																<li>同意2</li>
																<li>不同意2</li>
															</ul>
														</div>
													</div>
												</div>

												<div class="accordion-group">
													<div class="accordion-heading">
														<a class="accordion-toggle" href="#">意见分类3</a> <span
															class="accordion-toggle-icon"></span>
													</div>

													<div class="accordion-body collapse">
														<div class="accordion-inner">
															<ul>
																<li>同意3</li>
																<li>不同意3</li>
															</ul>
														</div>
													</div>
												</div>
											</div>
										</div>
								</a></li>
							</ul></li>
					</ul>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="/pt/dytable/form_js.jsp"%>
	<script type="text/javascript">
		$(function() {
			$(".accordion-heading").click(
					function(e) {
						if ($(this).next().hasClass("in")) {
							$(this).next().removeClass("in");
							$(".accordion-heading").find("a").filter(
									".accordion-toggle").each(function() {
								$(this).removeClass("collapsed");
							});
							$(".accordion-heading").find("span").filter(
									".accordion-toggle-icon").each(function() {
								$(this).removeClass("collapsed");
							});
						} else {
							$(".accordion-body").filter(".in")
									.removeClass("in");
							$(this).next().addClass("in");
							$(".accordion-heading").find("a").filter(
									".accordion-toggle").each(function() {
								$(this).removeClass("collapsed");
							});
							$(this).find("a").filter(".accordion-toggle")
									.addClass("collapsed");

							$(".accordion-heading").find("span").filter(
									".accordion-toggle-icon").each(function() {
								$(this).removeClass("collapsed");
							});
							$(this).find("span").filter(
									".accordion-toggle-icon").addClass(
									"collapsed");
						}
						e.stopPropagation();
					});
		});
	</script>
</body>
</html>