<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<title>威尔 - 统一业务应用与开发平台</title>
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/ligerUI/skins/Aqua/css/ligerui-all.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/ztree/css/zTreeStyle/zTreeStyle.css" />
<!-- Bootstrap -->
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/bootstrap/css/bootstrap.min.css" />

<!-- Project -->
<script type="text/javascript"
	src="${ctx}/resources/jquery/jquery.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/ligerUI/js/ligerui.min.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/ztree/js/jquery.ztree.all-3.5.min.js"></script>
<script src='${ctx}/dwr/engine.js' type='text/javascript'></script>
<script src='${ctx}/dwr/util.js' type='text/javascript'></script>
<script src='${ctx}/dwr/interface/directController.js'
	type='text/javascript'></script>
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
	//通过该方法与后台交互，确保推送时能找到指定用户  
	function onPageLoad() {
		directController.onPageLoad(function(data) {
			$(".noreadmsgcount").html(data);
		});
	}
	//推送信息  
	function showMessage(noReadMessageCount) {
		$(".noreadmsgcount").html(noReadMessageCount);
	}
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
body{background-color:#EAF4FD}
</style>
</head>
<body
	onload="onPageLoad();dwr.engine.setActiveReverseAjax(true);dwr.engine.setNotifyServerOnPageUnload(true);">
	<div class="container-fluid">
		<div id="pageloading"></div>
		<div id="header" class="row-fluid">
			<div class="span9">
				<div class="row-fluid">
					<div style="float: left;">
						<img style="height: 50px;margin: 5px 0px 5px 0px;" alt="Well-Soft" height="50"
							src="${ctx}/resources/pt/images/well-soft.gif">
					</div>
					<div style="float: left; height: 50px">
						<h3
							style="vertical-align: bottom; height: 45px; line-height: 45px; font-family: '黑体', 'Helvetica Neue', Helvetica, Arial, sans-serif;">
							<i>&nbsp;统一业务应用与开发平台</i>
						</h3>
					</div>
				</div>
			</div>
			<div class="span3" style="text-align: right">
				<security:authentication property="principal.username" />,欢迎您!
<!-- 				&nbsp;&nbsp;<span -->
<%-- 					onclick="window.open('${ctx}/basicdata/dyview/view_show?viewUuid=236cf2d0-d4f2-4736-9701-597c685b3db0&currentPage=1')" --%>
<!-- 					style="cursor: pointer;">在线消息(<span class="noreadmsgcount">0</span>) -->
<!-- 				</span>&nbsp;&nbsp;  -->
				<a href="${ctx}/passport/admin/home" target="_blank">我的主页</a>
				<a href="${ctx}/security_logout">退出登录</a>
			</div>
		</div>
		<!-- header end -->
		<div id="layout1" class="row-fluid">
			<div position="left" title="导航菜单" id="accordion1">
				
				
				
				<div title="流程管理" style="overflow: auto;">
					<div class="nav-item">
						<a
							href="javascript:addTab('flow_define','流程定义','${ctx}/workflow/flow/define')">
							<img src="${ctx}/resources/pt/images/print_class.png"></img><br />
							<span>流程定义</span>
						</a>
					</div>
					<div class="nav-item">
						<a
							href="javascript:addTab('define_category','流程分类','${ctx}/workflow/define/category')">
							<img src="${ctx}/resources/pt/images/print_class.png"></img><br />
							<span>流程分类</span>
						</a>
					</div>
					<!-- 					<div class="nav-item"> -->
					<!-- 						<a -->
					<%-- 							href="javascript:addTab('define_right','环节权限','${ctx}/workflow/define/right')"> --%>
					<%-- 							<img src="${ctx}/resources/pt/images/print_class.png"></img><br /> --%>
					<!-- 							<span>环节权限</span> -->
					<!-- 						</a> -->
					<!-- 					</div> -->

					<!-- 					<div class="nav-item"> -->
					<!-- 						<a -->
					<%-- 							href="javascript:addTab('define_button','环节操作','${ctx}/workflow/define/button')"> --%>
					<%-- 							<img src="${ctx}/resources/pt/images/print_class.png"></img><br /> --%>
					<!-- 							<span>环节操作</span> -->
					<!-- 						</a> -->
					<!-- 					</div> -->
					<div class="nav-item">
						<a
							href="javascript:addTab('define_format','信息格式','${ctx}/workflow/define/format')">
							<img src="${ctx}/resources/pt/images/print_class.png"></img><br />
							<span>信息格式</span>
						</a>
					</div>
				</div>
				<div title="表单管理" style="overflow: auto;">
					<div class="nav-item">
						<a
							href="javascript:addTab('form_definition','动态表单','${ctx}/dytable/form_definition_index.action')">
							<img src="${ctx}/resources/pt/images/kdmconfig.png"></img><br />
							<span>动态表单</span>
						</a>
					</div>
				</div>
				<div title="权限管理" style="overflow: auto;">
					<div class="nav-item">
						<a
							href="javascript:addTab('security_role','角色','${ctx}/security/role.action')">
							<img src="${ctx}/resources/pt/images/kontact.png"></img><br /> <span>角色</span>
						</a>
					</div>
					<div class="nav-item">
						<a
							href="javascript:addTab('authority','权限','${ctx}/security/privilege.action')">
							<img src="${ctx}/resources/pt/images/print_class.png"></img><br />
							<span>权限</span>
						</a>
					</div>
					<div class="nav-item">
						<a
							href="javascript:addTab('resource','资源','${ctx}/security/resource.action')">
							<img src="${ctx}/resources/pt/images/kappfinder.png"></img><br />
							<span>资源</span>
						</a>
					</div>
				</div>
				<div title="界面管理" style="overflow: auto;">
					<div class="nav-item">
						<a
							href="javascript:addTab('cms_pageconfig','页面','${ctx}/cms/cmspage.action')">
							<img src="${ctx}/resources/pt/images/kontact.png"></img><br /> <span>页面</span>
						</a>
					</div>
					<div class="nav-item">
						<a
							href="javascript:addTab('cms_category','导航','${ctx}/cms/category.action')">
							<img src="${ctx}/resources/pt/images/kappfinder.png"></img><br />
							<span>导航</span>
						</a>
					</div>
					<div class="nav-item">
						<a
							href="javascript:addTab('cms_moduleconfig','页面元素','${ctx}/cms/module.action')">
							<img src="${ctx}/resources/pt/images/kappfinder.png"></img><br />
							<span>页面元素</span>
						</a>
					</div>
					<div class="nav-item">
						<a
							href="javascript:addTab('dyview','视图','${ctx}/basicdata/dyview')">
							<img src="${ctx}/resources/pt/images/kappfinder.png"></img><br />
							<span>视图</span>
						</a>
					</div>
				</div>
				
				<div title="基础数据管理" style="overflow: auto;">
					<div class="nav-item">
						<a
							href="javascript:addTab('data_dic','数据字典','${ctx}/basicdata/datadict')">
							<img src="${ctx}/resources/pt/images/kontact.png"></img><br /> <span>数据字典</span>
						</a>
					</div>
					<div class="nav-item">
						<a
							href="javascript:addTab('message_template','消息格式','${ctx}/message/template.action')">
							<img src="${ctx}/resources/pt/images/kappfinder.png"></img><br />
							<span>消息格式</span>
						</a>
					</div>
					<div class="nav-item">
						<a
							href="javascript:addTab('print_template','打印模版','${ctx}/basicdata/printtemplate')">
							<img src="${ctx}/resources/pt/images/kappfinder.png"></img><br />
							<span>打印模版</span>
						</a>
					</div>
					<div class="nav-item">
						<a
							href="javascript:addTab('serial_number','流水号定义','${ctx}/basicdata/serialnumber')">
							<img src="${ctx}/resources/pt/images/kappfinder.png"></img><br />
							<span>流水号定义</span>
						</a>
					</div>
					<div class="nav-item">
						<a
							href="javascript:addTab('serial_number_maintain','流水号维护','${ctx}/basicdata/serialnumberMaintain')">
							<img src="${ctx}/resources/pt/images/kappfinder.png"></img><br />
							<span>流水号维护</span>
						</a>
					</div>
					<!-- 					<div class="nav-item"> -->
					<!-- 						<a -->
					<%-- 							href="javascript:addTab('serial_number_form','可编辑表单','${ctx}/basicdata/serialform')"> --%>
					<%-- 							<img src="${ctx}/resources/pt/images/kappfinder.png"></img><br /> --%>
					<!-- 							<span>可编辑表单</span> -->
					<!-- 						</a> -->
					<!-- 					</div> -->
					<div class="nav-item">
						<a
							href="javascript:addTab('system_table','系统表结构','${ctx}/basicdata/systemtable')">
							<img src="${ctx}/resources/pt/images/kappfinder.png"></img><br />
							<span>系统表结构</span>
						</a>
					</div>
					<div class="nav-item">
						<a
							href="javascript:addTab('excel_import_rule','数据导入规则','${ctx}/basicdata/excelimportrule')">
							<img src="${ctx}/resources/pt/images/kappfinder.png"></img><br />
							<span>数据导入规则</span>
						</a>
					</div>
				</div>
				<div title="我的消息" style="overflow: auto;">
					<div class="nav-item">
						<a
							href="javascript:addTab('message_send','发送消息','${ctx}/message/content/sendmessage')">
							<img src="${ctx}/resources/pt/images/kontact.png"></img><br /> <span>发送消息</span>
						</a>
					</div>
					<div class="nav-item">
						<a
							href="javascript:addTab('message_read','收件箱','${ctx}/basicdata/dyview/view_show?viewUuid=e1f323e6-392e-430e-aa05-b52e7c4ec6b8&currentPage=1')">
							<img src="${ctx}/resources/pt/images/kontact.png"></img><br /> <span>收件箱</span>
						</a>
					</div>
					<div class="nav-item">
						<a
							href="javascript:addTab('message_write','发件箱','${ctx}/basicdata/dyview/view_show?viewUuid=eb073d91-03be-452c-b824-629429117eef&currentPage=1')">
							<img src="${ctx}/resources/pt/images/kontact.png"></img><br /> <span>发件箱</span>
						</a>
					</div>
				</div>
				<div title="工作流程" style="overflow: auto;">
					<div class="nav-item">
						<a
							href="javascript:addTab('flow_list','新建工作','${ctx}/workflow/work/flow/list')">
							<img src="${ctx}/resources/pt/images/print_class.png"></img><br />
							<span>新建工作</span>
						</a>
					</div>
					<div class="nav-item">
						<a
							href="javascript:addTab('work_todo','待办工作','${ctx}/workflow/work/todo')">
							<img src="${ctx}/resources/pt/images/print_class.png"></img><br />
							<span>待办工作</span>
						</a>
					</div>
					<div class="nav-item">
						<a
							href="javascript:addTab('work_done','已办工作','${ctx}/workflow/work/done')">
							<img src="${ctx}/resources/pt/images/print_class.png"></img><br />
							<span>已办工作</span>
						</a>
					</div>
					<div class="nav-item">
						<a
							href="javascript:addTab('work_over','已办工作','${ctx}/workflow/work/over')">
							<img src="${ctx}/resources/pt/images/print_class.png"></img><br />
							<span>办结工作</span>
						</a>
					</div>
					<div class="nav-item">
						<a
							href="javascript:addTab('work_draft','工作草稿','${ctx}/workflow/work/draft')">
							<img src="${ctx}/resources/pt/images/print_class.png"></img><br />
							<span>工作草稿</span>
						</a>
					</div>
					<div class="nav-item">
						<a
							href="javascript:addTab('work_unread','未阅工作','${ctx}/workflow/work/unread')">
							<img src="${ctx}/resources/pt/images/print_class.png"></img><br />
							<span>未阅工作</span>
						</a>
					</div>
					<div class="nav-item">
						<a
							href="javascript:addTab('work_read','已阅工作','${ctx}/workflow/work/read')">
							<img src="${ctx}/resources/pt/images/print_class.png"></img><br />
							<span>已阅工作</span>
						</a>
					</div>
					<div class="nav-item">
						<a
							href="javascript:addTab('work_attention','关注工作','${ctx}/workflow/work/attention')">
							<img src="${ctx}/resources/pt/images/print_class.png"></img><br />
							<span>我关注</span>
						</a>
					</div>
					<div class="nav-item">
						<a
							href="javascript:addTab('work_supervise','工作督办','${ctx}/workflow/work/supervise')">
							<img src="${ctx}/resources/pt/images/print_class.png"></img><br />
							<span>工作督办</span>
						</a>
					</div>
					<div class="nav-item">
						<a
							href="javascript:addTab('work_monitor','工作监控','${ctx}/workflow/work/monitor')">
							<img src="${ctx}/resources/pt/images/print_class.png"></img><br />
							<span>工作监控</span>
						</a>
					</div>
					<!-- 					<div class="nav-item"> -->
					<!-- 						<a href="javascript:addTab('work_3','工作委托','device/index')"> <img -->
					<%-- 							src="${ctx}/resources/pt/images/print_class.png"></img><br /> --%>
					<!-- 							<span>工作委托</span> -->
					<!-- 						</a> -->
					<!-- 					</div> -->
				</div>
				<div title="领导日程" style="overflow: auto;">
					<div class="nav-item">
						<a
							href="javascript:addTab('lead_schedule','领导日程','${ctx}/schedule/leader_schedule.action')">
							<img src="${ctx}/resources/pt/images/package_settings.png"></img><br />
							<span>领导日程</span>
						</a>
					</div>
					<div class="nav-item">
						<a
							href="javascript:addTab('mylead_shedule','我创建的领导日程','${ctx}/schedule/leader_schedule.action?mtype=1')">
							<img src="${ctx}/resources/pt/images/package_settings.png"></img><br />
							<span>我创建的领导日程</span>
						</a>
					</div>
					<div class="nav-item">
						<a
							href="javascript:addTab('sysset_shcedule','系统设置', '${ctx}/schedule/schedule_secsetlist.action')">
							<img src="${ctx}/resources/pt/images/package_settings.png"></img><br />
							<span>系统设置</span>
						</a>
					</div>
				</div>
				<div title="个人日程" style="overflow: auto;">
					<div class="nav-item">
						<a
							href="javascript:addTab('normal_shedule','个人日程','${ctx}/schedule/person_schedule.action')">
							<img src="${ctx}/resources/pt/images/package_settings.png"></img><br />
							<span>个人日程</span>
						</a>
					</div>
					<div class="nav-item">
						<a
							href="javascript:addTab('normal_noshedule','我起草不参与的日程','${ctx}/schedule/person_schedule.action?mtype=1')">
							<img src="${ctx}/resources/pt/images/package_settings.png"></img><br />
							<span>我起草不参与的日程</span>
						</a>
					</div>
					<div class="nav-item">
						<a
							href="javascript:addTab('normal_gshedule','共享的日程','${ctx}/schedule/person_schedule.action?mtype=2')">
							<img src="${ctx}/resources/pt/images/package_settings.png"></img><br />
							<span>共享的日程</span>
						</a>
					</div>
					<div class="nav-item">
						<a
							href="javascript:addTab('many_schedule','群组日程','${ctx}/schedule/group_schedule.action')">
							<img src="${ctx}/resources/pt/images/package_settings.png"></img><br />
							<span>群组日程</span>
						</a>
					</div>
				</div>
				<div title="邮件" style="overflow: auto;">
					<div class="nav-item">
						<a
							href="javascript:addTab('write_mail','写邮件','${ctx}/mail/writeMail.action')">
							<img src="${ctx}/resources/pt/images/package_settings.png"></img><br />
							<span>写邮件</span>
						</a>
					</div>
					<div class="nav-item">
						<a
							href="javascript:addTab('inbox_mail','收件箱','${ctx}/mail/mail_box_list.action?rel=0&pageNo=1&mtype=0')">
							<img src="${ctx}/resources/pt/images/package_settings.png"></img><br />
							<span>收件箱</span>
						</a>
					</div>
					<div class="nav-item">
						<a
							href="javascript:addTab('star_mail','星标邮件','${ctx}/mail/mail_box_list.action?rel=9&pageNo=1&mtype=0')">
							<img src="${ctx}/resources/pt/images/package_settings.png"></img><br />
							<span>星标邮件</span>
						</a>
					</div>
					<div class="nav-item">
						<a
							href="javascript:addTab('inboxg_mail','可跟踪邮件列表','${ctx}/mail/mail_box_list.action?rel=4&pageNo=1&mtype=0')">
							<img src="${ctx}/resources/pt/images/package_settings.png"></img><br />
							<span>可跟踪邮件列表</span>
						</a>
					</div>
					<div class="nav-item">
						<a
							href="javascript:addTab('sent_mail','已发送邮件','${ctx}/mail/mail_box_list.action?rel=1&pageNo=1&mtype=0')">
							<img src="${ctx}/resources/pt/images/package_settings.png"></img><br />
							<span>已发送邮件</span>
						</a>
					</div>
					<div class="nav-item">
						<a
							href="javascript:addTab('draft_mail','草稿箱','${ctx}/mail/mail_box_list.action?rel=2&pageNo=1&mtype=0')">
							<img src="${ctx}/resources/pt/images/package_settings.png"></img><br />
							<span>草稿箱</span>
						</a>
					</div>
					<div class="nav-item">
						<a
							href="javascript:addTab('delete_mail','删件箱','${ctx}/mail/mail_box_list.action?rel=3&pageNo=1&mtype=0')">
							<img src="${ctx}/resources/pt/images/package_settings.png"></img><br />
							<span>删件箱</span>
						</a>
					</div>
					<div class="nav-item">
						<a
							href="javascript:addTab('mail_folder','我的文件夹','${ctx}/mail/mail_box_list.action?rel=8&pageNo=1&mtype=0')">
							<img src="${ctx}/resources/pt/images/package_settings.png"></img><br />
							<span>我的文件夹</span>
						</a>
					</div>
					<div class="nav-item">
						<a
							href="javascript:addTab('other_mail','其他邮件','${ctx}/mail/mail_other.action')">
							<img src="${ctx}/resources/pt/images/package_settings.png"></img><br />
							<span>其他邮件</span>
						</a>
					</div>
					<div class="nav-item">
						<a
							href="javascript:addTab('file_mail','附件夹','${ctx}/mail/file_list.action?curPage=0')">
							<img src="${ctx}/resources/pt/images/package_settings.png"></img><br />
							<span>附件夹</span>
						</a>
					</div>
					<div class="nav-item">
						<a
							href="javascript:addTab('mail_pconfig','个人邮件配置', '${ctx}/mail/mail_pconfig.action')">
							<img src="${ctx}/resources/pt/images/package_settings.png"></img><br />
							<span>个人邮箱设置</span>
						</a>
					</div>
					<div class="nav-item">
						<a
							href="javascript:addTab('mail_config','系统邮件配置', '${ctx}/mail/mail_config.action')">
							<img src="${ctx}/resources/pt/images/package_settings.png"></img><br />
							<span>系统邮件设置</span>
						</a>
					</div>
				</div>
				<%-- <div title="公文交换" style="overflow: auto;">
					<div class="nav-item">
						<a
							href="javascript:addTab('exchange_inbox','收件箱','${ctx}/exchange/inbox')">
							<img src="${ctx}/resources/pt/images/package.png"></img><br /> <span>收件箱</span>
						</a>
					</div>
					<div class="nav-item">
						<a
							href="javascript:addTab('exchange_outbox','发件箱','${ctx}/exchange/outbox')">
							<img src="${ctx}/resources/pt/images/package.png"></img><br /> <span>发件箱</span>
						</a>
					</div>
					<div class="nav-item">
						<a
							href="javascript:addTab('exchange_draft','草稿箱','${ctx}/exchange/draft')">
							<img src="${ctx}/resources/pt/images/package.png"></img><br /> <span>草稿箱</span>
						</a>
					</div>
					<div class="nav-item">
						<a
							href="javascript:addTab('exchange_trash','废件箱','${ctx}/exchange/trash')">
							<img src="${ctx}/resources/pt/images/package.png"></img><br /> <span>废件箱</span>
						</a>
					</div>
					<div class="nav-item">
						<a
							href="javascript:addTab('exchange_supervise','督办箱','${ctx}/exchange/supervise')">
							<img src="${ctx}/resources/pt/images/package.png"></img><br /> <span>督办箱</span>
						</a>
					</div>
					<div class="nav-item">
						<a
							href="javascript:addTab('exchange_config','模块配置','${ctx}/exchange/config')">
							<img src="${ctx}/resources/pt/images/package.png"></img><br /> <span>模块配置</span>
						</a>
					</div>
				</div> --%>
				<!-- 				<div title="工作计划" style="overflow: auto;"> -->
				<!-- 					<div class="nav-item"> -->
				<!-- 						<a -->
				<%-- 							href="javascript:addTab('work_task','页面管理','${ctx}/worktask/task/main.action')"> --%>
				<%-- 							<img src="${ctx}/resources/pt/images/kontact.png"></img><br /> <span>工作管理</span> --%>
				<!-- 						</a> -->
				<!-- 					</div> -->
				<!-- 				</div> -->
				<div title="文件管理" style="overflow: auto;">
					<div class="nav-item">
						<a
							href="javascript:addTab('file_manager','文件管理','${ctx}/folder/index')">
							<img src="${ctx}/resources/pt/images/kontact.png"></img><br /> <span>文件管理</span>
						</a>
					</div>
				</div>
			</div>
			<div position="center" id="framecenter">
				<div tabid="home" title="我的主页">
					<iframe frameborder="0" name="home"
						src="${ctx}/passport/admin/home"></iframe>
				</div>
			</div>
		</div>
		<!-- layout1 end -->
		<div id="footer" class="row-fluid">
			<div class="span4 offset4"
				style="height: 30px; line-height: 30px; text-align: center; vertical-align: middle;">
				Copyright &copy; <a href="http://www.well-soft.com" target="_blank">威尔软件</a>
			</div>
		</div>
		<!-- footer end -->
	</div>
	<!-- Bootstrap -->
	<script src="${ctx}/resources/bootstrap/js/bootstrap.js"></script>
	<script src="${ctx}/resources/pt/js/global.js"></script>
</body>
</html>