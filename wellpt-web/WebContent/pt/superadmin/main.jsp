<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<!-- <title>威尔 - 统一业务应用与开发云平台</title> -->
<title>厦门市行政审批云平台</title>
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/ligerUI/skins/Aqua/css/ligerui-all.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/ztree/css/zTreeStyle/zTreeStyle.css" />
<!-- Bootstrap -->
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/bootstrap/css/bootstrap.min.css" />

<!-- Project -->
<script type="text/javascript" src="${ctx}/resources/jquery/jquery.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/ligerUI/js/ligerui.min.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/ztree/js/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript">
	//几个布局的对象
	var tab = null;
	var accordion = null;
	var tree = null;
	$(function() {
		//布局
		$("#layout1").ligerLayout({
			leftWidth : 190,
			height : '100%',
			heightDiff : -34,
			space : 4,
			onHeightChanged : pt_heightChanged
		});

		var height = $(".l-layout-center").height();

		//Tab
		$("#framecenter").ligerTab({
			height : height
		});

		//面板
		$("#accordion1").ligerAccordion({
			height : height - 24
		});

		$(".l-link").hover(function() {
			$(this).addClass("l-link-over");
		}, function() {
			$(this).removeClass("l-link-over");
		});

		tab = $("#framecenter").ligerGetTabManager();
		accordion = $("#accordion1").ligerGetAccordionManager();
		//tree = $("#tree1").ligerGetTreeManager();
		$("#pageloading").hide();

		top.navtab = tab;
	});
	function pt_heightChanged(options) {
		if (tab) {
			tab.addHeight(options.diff);
		}
		if (accordion && options.middleHeight - 24 > 0)
			accordion.setHeight(options.middleHeight - 24);
	}
	//添加到tab或者刷新
	function addTab(tabid, text, url, icon) {
		if (tab.isTabItemExist(tabid)) {
			tab.selectTabItem(tabid);
			tab.reload(tabid);
		} else {
			tab.addTabItem({
				tabid : tabid,
				text : text,
				url : url,
				icon : icon
			});
		}
	};
</script>
<style type="text/css">
.l-link {
	display: block;
	line-height: 22px;
	height: 22px;
	padding-left: 16px;
	border: 1px solid white;
	margin: 4px;
	text-decoration: none;
}

.l-link-over {
	background: #FFEEAC;
	border: 1px solid #DB9F00;
	text-decoration: none;
}

.nav-item {
	text-align: center;
}

body {
	background-color: #EAF4FD
}
</style>
</head>
<body>
	<div class="container-fluid">
		<div id="pageloading"></div>
		<div id="header" class="row-fluid">
			<div class="span9">
				<div class="row-fluid">
<!-- 				oa	<div style="float: left;height: 65px;line-height: 60px;"> -->
<!-- 						<img style="height: 34px; margin: 5px 0px 5px 0px;" -->
<!-- 							alt="Well-Soft" -->
<%-- 							src="${ctx}/resources/pt/images/logo.png"> --%>
<!-- 					</div> -->
					<div style="float: left;height: 65px;line-height: 60px;width: 322px;">
						<img style="height: 50px;margin: 5px 0px 5px 0px;float: left;" alt="Well-Soft"
							src="${ctx}/resources/pt/img/zzffzx-logo6.png">
    <div style="font-size: 16px;
    height: 50px;
    margin-left: 55px;
    margin-top: 5px;
    text-align: left;font-family: Microsoft YaHei;">
    	<div style="height: 25px;
    line-height: 25px;font-size:19px;letter-spacing: 14px;">厦门市审批服务云</div>
	    <div style="border-top: 1px solid #000000;
    font-size: 14px;
    height: 25px;
    line-height: 25px;
    text-align: left;">厦门市行政审批（商事登记）信息管理平台</div>
    </div>
     
					</div>
<!-- 					<div style="float: left; height: 50px"> -->
<!-- 						<h3 -->
<!-- 							style="vertical-align: bottom; height: 45px; line-height: 45px; font-family: '黑体', 'Helvetica Neue', Helvetica, Arial, sans-serif;"> -->
<!-- 							<i>&nbsp;统一业务应用与开发云平台</i> -->
<!-- 						</h3> -->
<!-- 					</div> -->
				</div>
			</div>
			<div class="span3" style="text-align: right">
				<security:authentication property="principal.username" />
				,欢迎您!
				<!-- 				&nbsp;&nbsp;<span -->
				<%-- 					onclick="window.open('${ctx}/basicdata/dyview/view_show?viewUuid=236cf2d0-d4f2-4736-9701-597c685b3db0&currentPage=1')" --%>
				<!-- 					style="cursor: pointer;">在线消息(<span class="noreadmsgcount">0</span>) -->
				<!-- 				</span>  -->
				&nbsp;&nbsp; <a href="${ctx}/security_logout?redirectUrl=/superadmin/login">退出登录</a>
			</div>
		</div>
		<!-- header end -->
		<div id="layout1" class="row-fluid">
			<!-- 			<div position="left" title="导航菜单" id="accordion1"> -->
			<div position="left" title="导航菜单" id="accordion1">
				<div title="租户管理" style="overflow: auto;">
				<div class="nav-item">
					<a
						href="javascript:addTab('tenant_review','待审核租户','${ctx}/superadmin/tenant/review.action')">
						<img src="${ctx}/resources/pt/images/kappfinder.png"></img><br />
						<span>待审核租户</span>
					</a>
				</div>
				<div class="nav-item">
					<a
						href="javascript:addTab('tenant_active','活动租户','${ctx}/superadmin/tenant/active.action')">
						<img src="${ctx}/resources/pt/images/print_class.png"></img><br />
						<span>活动租户</span>
					</a>
				</div>
				<div class="nav-item">
					<a
						href="javascript:addTab('tenant_deactive','禁用租户','${ctx}/superadmin/tenant/deactive.action')">
						<img src="${ctx}/resources/pt/images/print_class.png"></img><br />
						<span>禁用租户</span>
					</a>
				</div>
				<div class="nav-item">
					<a
						href="javascript:addTab('tenant_reject','审核失败租户','${ctx}/superadmin/tenant/reject.action')">
						<img src="${ctx}/resources/pt/images/print_class.png"></img><br />
						<span>审核失败租户</span>
					</a>
				</div>
				<div class="nav-item">
					<a
						href="javascript:addTab('database_config','数据库配置','${ctx}/superadmin/database/config')">
						<img src="${ctx}/resources/pt/images/kontact.png"></img><br /> <span>数据库配置</span>
					</a>
				</div>
			</div>
			<div title="通讯录管理" style="overflow: auto;">
				<!-- <div class="nav-item">
					<a
						href="javascript:addTab('common_unit','单位管理','${ctx}/superadmin/unit/commonUnit')">
						<img src="${ctx}/resources/pt/images/print_class.png"></img><br />
						<span>单位管理</span>
					</a>
				</div> -->
				<div class="nav-item">
					<a
						href="javascript:addTab('unit_tree','单位通讯录','${ctx}/superadmin/unit/commonUnitTree/config')">
						<img src="${ctx}/resources/pt/images/kdmconfig.png"></img><br />
						<span>单位通讯录</span>
					</a>
				</div>
				<div class="nav-item">
					<a
						href="javascript:addTab('business_type','业务类别管理','${ctx}/superadmin/unit/businessType/config')">
						<img src="${ctx}/resources/pt/images/print_class.png"></img><br />
						<span>业务类别管理</span>
					</a>
				</div>
			</div>
			
			<div title="数据交换管理" style="overflow: auto;">
				<div class="nav-item">
					<a
						href="javascript:addTab('exchange_log','数据交换日志','${ctx}/exchangedata/dataconfig/dx_exchange_loglist')">
						<img src="${ctx}/resources/pt/images/kdmconfig.png"></img><br />
						<span>数据交换日志</span>
					</a>
				</div>
			</div>
			
		</div>
			<div position="center" id="framecenter">
				<div tabid="tenant_active" title="活动租户">
					<iframe frameborder="0" name="home"
						src="${ctx}/superadmin/tenant/active.action"></iframe>
				</div>
			</div>
		</div>
		<!-- layout1 end -->
		<div id="footer" class="row-fluid">
			<div class="span4 offset4"
				style="height: 30px; line-height: 30px; text-align: center; vertical-align: middle;">
				Copyright &copy; <a>厦门市行政服务中心管理委员会</a>
			</div>
		</div>
		<!-- footer end -->
	</div>
	<!-- Bootstrap -->
	<script src="${ctx}/resources/bootstrap/js/bootstrap.js"></script>
	<script src="${ctx}/resources/pt/js/global.js"></script>
</body>
</html>