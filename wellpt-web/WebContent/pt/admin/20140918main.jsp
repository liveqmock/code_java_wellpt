<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.wellsoft.pt.cms.facade.CmsApiFacade"%>
<%@page import="com.wellsoft.pt.core.context.ApplicationContextHolder"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<!-- <title>威尔 - 统一业务应用与开发云平台</title> -->
<title>厦门威尔软件</title>
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
<%-- <script src='${ctx}/dwr/engine.js' type='text/javascript'></script> --%>
<%-- <script src='${ctx}/dwr/util.js' type='text/javascript'></script> --%>
<%-- <script src='${ctx}/dwr/interface/directController.js' --%>
<!-- 	type='text/javascript'></script> -->
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
// 	通过该方法与后台交互，确保推送时能找到指定用户  
// 	function onPageLoad() {
// 		directController.onPageLoad(function(data) {
// 			$(".noreadmsgcount").html(data);
// 		});
// 	}
// 	推送信息  
// 	function showMessage(noReadMessageCount) {
// 		$(".noreadmsgcount").html(noReadMessageCount);
// 	}
</script>
<style type="text/css">
*{font-family: "Microsoft YaHei";}
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
.mouse_hand {
	cursor:pointer;
}
body{background-color:#EAF4FD}
</style>
</head>
<body>
	<div class="container-fluid">
		<div id="pageloading"></div>
		<div id="header" class="row-fluid">
			<div class="span9">
				<div class="row-fluid">
 			<!-- OA后台页面log -->  
<!--  					<div style="float: left;height: 65px;line-height: 60px;"> -->
<!--  						<img style="height: 34px;margin: 5px 0px 5px 0px;" alt="Well-Soft"  -->
<%--  							src="${ctx}/resources/pt/images/logo.png"> --%>
<!--  					</div>  -->
 					<div style="float: left;height: 65px;line-height: 60px;width: 322px;">
						<img style="height: 50px;margin: 5px 0px 5px 0px;float: left;" alt="Well-Soft"
							src="${ctx}/resources/pt/img/zzffzx-logo6.png">
    <div style="font-size: 16px;
    height: 50px;
    margin-left: 55px;
    margin-top: 5px;
    text-align: left;font-family: Microsoft YaHei;">
    	<div style="height: 25px;
    line-height: 25px;font-size:19px;letter-spacing: 1px;margin-top: 14px;">厦门市行政审批信息管理平台</div>
	   
    </div>
     
					</div>
			<%--		<div style="float: left;height: 65px;line-height: 60px;width: 322px;">
					
						<img style="height: 50px;margin: 5px 0px 5px 0px;float: left;" alt="Well-Soft"
							src="${ctx}/resources/pt/img/zzffzx-logo6.png">
    <div style="font-size: 16px;
    height: 50px;
    margin-left: 55px;
    margin-top: 5px;
    text-align: left;">
    	<div style="height: 25px;
    line-height: 25px;font-size:18px;letter-spacing: 15px;">厦门市审批服务云</div>
	    <div style="border-top: 1px solid #000000;
    font-size: 14px;
    height: 25px;
    line-height: 25px;
    text-align: left;">厦门市行政审批（商事登记）信息管理平台</div>
    </div>
					</div>
     --%>
<!-- 					<div style="float: left; height: 50px"> -->
<!-- 						<h3 -->
<!-- 							style="vertical-align: bottom; height: 45px; line-height: 45px; font-family: '黑体', 'Helvetica Neue', Helvetica, Arial, sans-serif;"> -->
<!-- 							<i>&nbsp;统一业务应用与开发云平台</i> -->
<!-- 						</h3> -->
<!-- 					</div> -->
				</div>
			</div>
			<div class="span3" style="text-align: right">
			<%
				CmsApiFacade cmsApiFacade = ApplicationContextHolder
						.getBean(CmsApiFacade.class);
				String homePage = null;
				try{
					homePage = cmsApiFacade.getIndexCmsPage();
				}catch (Exception e){
					e.printStackTrace();
					homePage = request.getRequestURI().replace(request.getContextPath(), "");
				}
			%>
				<security:authentication property="principal.username" />,欢迎您!
				<a href="${ctx}<%=homePage%>" target="_blank">我的主页</a>
				<a href="${ctx}/security_logout">退出登录</a>
			</div>
		</div>
		<!-- header end -->
		<div id="layout1" class="row-fluid">
			<div position="left" title="导航菜单" id="accordion1">
				<privilege:access ifGranted="010">
				<div title="软件管理" style="overflow: auto;">
					<privilege:access ifGranted="010001">
					<div class="nav-item">
						<a
							href="javascript:addTab('app_system','软件','${ctx}/system/app/system')">
							<img src="${ctx}/resources/pt/images/kdmconfig.png"></img><br />
							<span>软件</span>
						</a>
					</div>
					</privilege:access>
				</div>
				</privilege:access>
				<privilege:access ifGranted="007">
				<div title="模块管理" style="overflow: auto;">
					<privilege:access ifGranted="007001">
					<div class="nav-item">
						<a
							href="javascript:addTab('app_module','模块','${ctx}/system/app/module')">
							<img src="${ctx}/resources/pt/images/kdmconfig.png"></img><br />
							<span>模块</span>
						</a>
					</div>
					</privilege:access>
				</div>
				</privilege:access>
				<privilege:access ifGranted="001">
				<div title="用户管理" style="overflow: auto;">
					<privilege:access ifGranted="001001">
					<div class="nav-item">
						<a
							href="javascript:addTab('org_department_user','用户','${ctx}/org/department/user')">
							<img src="${ctx}/resources/pt/images/kdmconfig.png"></img><br /> <span>用户</span>
						</a>
					</div>
					</privilege:access>
					<privilege:access ifGranted="001002">
					<div class="nav-item">
						<a
							href="javascript:addTab('org_department','部门','${ctx}/org/department/list')">
							<img src="${ctx}/resources/pt/images/package.png"></img><br /> <span>部门</span>
						</a>
					</div>
					</privilege:access>
					
						<privilege:access ifGranted="001009">
					<div class="nav-item">
						<a
							href="javascript:addTab('org_department_job','职位','${ctx}/org/department/job')">
							<img src="${ctx}/resources/pt/images/kontact.png"></img><br />
							<span>职位</span>
						</a>
					</div>
					</privilege:access>
					
					<privilege:access ifGranted="001003">
					<div class="nav-item">
						<a href="javascript:addTab('org_group','群组','${ctx}/org/group/list')">
							<img src="${ctx}/resources/pt/images/kontact.png"></img><br /> <span>群组</span>
						</a>
					</div>
					</privilege:access>
					<privilege:access ifGranted="001005">
					<div class="nav-item">
						<a
							href="javascript:addTab('org_option','组织选择项','${ctx}/org/option/list')">
							<img src="${ctx}/resources/pt/images/kappfinder.png"></img><br />
							<span>组织选择项</span>
						</a>
					</div>
					</privilege:access>
					<privilege:access ifGranted="001006">
					<div class="nav-item">
						<a
							href="javascript:addTab('org_duty_agent','职务代理人','${ctx}/org/duty/agent/list')">
							<img src="${ctx}/resources/pt/images/print_class.png"></img><br />
							<span>职务代理人</span>
						</a>
					</div>
					</privilege:access>
					<privilege:access ifGranted="001007">
					<div class="nav-item">
						<a
							href="javascript:addTab('business_unit_tree','业务单位通讯录','${ctx}/org/business/unit/business_type')">
							<img src="${ctx}/resources/pt/images/print_class.png"></img><br />
							<span>业务单位通讯录</span>
						</a>
					</div>
					</privilege:access>
					<privilege:access ifGranted="001008">
					<div class="nav-item">
						<a
							href="javascript:addTab('business_manage','业务管理','${ctx}/org/business/unit/business_manage')">
							<img src="${ctx}/resources/pt/images/kontact.png"></img><br />
							<span>业务管理</span>
						</a>
					</div>
					</privilege:access>
				</div>
				</privilege:access>
				<privilege:access ifGranted="004">
				<div title="流程管理" style="overflow: auto;">
					<privilege:access ifGranted="004001">
					<div class="nav-item">
						<a
							href="javascript:addTab('flow_define','流程定义','${ctx}/workflow/define/flow')">
							<img src="${ctx}/resources/pt/images/print_class.png"></img><br />
							<span>流程定义</span>
						</a>
					</div>
					</privilege:access>
					<privilege:access ifGranted="004002">
					<div class="nav-item">
						<a
							href="javascript:addTab('define_category','流程分类','${ctx}/workflow/define/category')">
							<img src="${ctx}/resources/pt/images/print_class.png"></img><br />
							<span>流程分类</span>
						</a>
					</div>
					</privilege:access>
					<privilege:access ifGranted="004003">
					<div class="nav-item">
						<a
							href="javascript:addTab('define_format','信息格式','${ctx}/workflow/define/format')">
							<img src="${ctx}/resources/pt/images/print_class.png"></img><br />
							<span>信息格式</span>
						</a>
					</div>
					</privilege:access>
				</div>
				</privilege:access>
				<privilege:access ifGranted="005">
				<div title="表单管理" style="overflow: auto;">
					<privilege:access ifGranted="005001">
					<div class="nav-item">
						<a
							href="javascript:addTab('form_definition','编辑表单','${ctx}/dytable/editable/form/definition/list?flag=1')">
							<img src="${ctx}/resources/pt/images/kdmconfig.png"></img><br />
							<span>编辑表单</span>
						</a>
					</div>
					</privilege:access>
					<privilege:access ifGranted="005002">
					<div class="nav-item">
						<a
							href="javascript:addTab('form_definition2','显示表单','${ctx}/dytable/display/form/definition/list?flag=2')">
							<img src="${ctx}/resources/pt/images/kdmconfig.png"></img><br />
							<span>显示表单</span>
						</a>
					</div>
					</privilege:access>
					<privilege:access ifGranted="005003">
					<div class="nav-item">
						<a
							href="javascript:addTab('form_definition3','编辑表单(新)','${ctx}/dyform/editable/form/definition/list?flag=1')">
							<img src="${ctx}/resources/pt/images/kdmconfig.png"></img><br />
							<span>编辑表单(新)</span>
						</a>
					</div>
					</privilege:access>
					<privilege:access ifGranted="005004">
					<div class="nav-item">
						<a
							href="javascript:addTab('form_definition4','显示表单(新)','${ctx}/dyformmodel/list/list')">
							<img src="${ctx}/resources/pt/images/kdmconfig.png"></img><br />
							<span>显示表单(新)</span>
						</a>
					</div>
					</privilege:access>
				</div>
				
				
				 
				
				
				
				</privilege:access>
				<privilege:access ifGranted="002">
				<div title="权限管理" style="overflow: auto;">
					<privilege:access ifGranted="002001">
					<div class="nav-item">
						<a
							href="javascript:addTab('security_role','角色','${ctx}/security/role')">
							<img src="${ctx}/resources/pt/images/kontact.png"></img><br /> <span>角色</span>
						</a>
					</div>
					</privilege:access>
					<privilege:access ifGranted="002002">
					<div class="nav-item">
						<a
							href="javascript:addTab('authority','权限','${ctx}/security/privilege')">
							<img src="${ctx}/resources/pt/images/print_class.png"></img><br />
							<span>权限</span>
						</a>
					</div>
					</privilege:access>
					<privilege:access ifGranted="002003">
					<div class="nav-item">
						<a
							href="javascript:addTab('resource','资源','${ctx}/security/resource')">
							<img src="${ctx}/resources/pt/images/kappfinder.png"></img><br />
							<span>资源</span>
						</a>
					</div>
					</privilege:access>
				</div>
				</privilege:access>
				<privilege:access ifGranted="006">
				<div title="界面管理" style="overflow: auto;">
					<privilege:access ifGranted="006001">
					<div class="nav-item">
						<a
							href="javascript:addTab('cms_pageconfig','页面','${ctx}/cms/cmspage/define')">
							<img src="${ctx}/resources/pt/images/kontact.png"></img><br /> <span>页面</span>
						</a>
					</div>
					</privilege:access>
					<privilege:access ifGranted="006007">
					<div class="nav-item">
						<a
							href="javascript:addTab('cms_category','导航','${ctx}/cms/category')">
							<img src="${ctx}/resources/pt/images/kappfinder.png"></img><br />
							<span>导航</span>
						</a>
					</div>
					</privilege:access>
					<privilege:access ifGranted="006003">
					<div class="nav-item">
						<a
							href="javascript:addTab('cms_moduleconfig','页面元素','${ctx}/cms/module')">
							<img src="${ctx}/resources/pt/images/kappfinder.png"></img><br />
							<span>页面元素</span>
						</a>
					</div>
					</privilege:access>
					<privilege:access ifGranted="006004">
					<div class="nav-item">
						<a
							href="javascript:addTab('dyview','视图','${ctx}/basicdata/dyview')">
							<img src="${ctx}/resources/pt/images/kappfinder.png"></img><br />
							<span>视图</span>
						</a>
					</div>
					</privilege:access>
					<privilege:access ifGranted="006008">
					<div class="nav-item">
						<a
							href="javascript:addTab('view','视图','${ctx}/basicdata/view')">
							<img src="${ctx}/resources/pt/images/kappfinder.png"></img><br />
							<span>视图（新）</span>
						</a>
					</div>
					</privilege:access>
				</div>
				</privilege:access>
				<div title="多语言管理" style="overflow: auto;"></div>
				<privilege:access ifGranted="003">
				<div title="基础数据管理" style="overflow: auto;">
					<privilege:access ifGranted="003001">
					<div class="nav-item">
						<a
							href="javascript:addTab('data_dic','数据字典','${ctx}/basicdata/datadict')">
							<img src="${ctx}/resources/pt/images/kontact.png"></img><br /> <span>数据字典</span>
						</a>
					</div>
					</privilege:access>
					<privilege:access ifGranted="003003">
					<div class="nav-item">
						<a
							href="javascript:addTab('message_template','消息格式','${ctx}/message/template')">
							<img src="${ctx}/resources/pt/images/kappfinder.png"></img><br />
							<span>消息格式</span>
						</a>
					</div>
					</privilege:access>
					<privilege:access ifGranted="003004">
					<div class="nav-item">
						<a
							href="javascript:addTab('print_template','打印模版','${ctx}/basicdata/printtemplate')">
							<img src="${ctx}/resources/pt/images/kappfinder.png"></img><br />
							<span>打印模版</span>
						</a>
					</div>
					</privilege:access>
					<privilege:access ifGranted="003005">
					<div class="nav-item">
						<a
							href="javascript:addTab('serial_number','流水号定义','${ctx}/basicdata/serialnumber')">
							<img src="${ctx}/resources/pt/images/kappfinder.png"></img><br />
							<span>流水号定义</span>
						</a>
					</div>
					</privilege:access>
					<privilege:access ifGranted="003002">
					<div class="nav-item">
						<a
							href="javascript:addTab('serial_number_maintain','流水号维护','${ctx}/basicdata/serialnumberMaintain')">
							<img src="${ctx}/resources/pt/images/kappfinder.png"></img><br />
							<span>流水号维护</span>
						</a>
					</div>
					</privilege:access>
					<privilege:access ifGranted="003007">
					<div class="nav-item">
						<a
							href="javascript:addTab('system_table','系统表结构','${ctx}/basicdata/systemtable')">
							<img src="${ctx}/resources/pt/images/kappfinder.png"></img><br />
							<span>系统表结构</span>
						</a>
					</div>
					</privilege:access>
					<privilege:access ifGranted="003008">
					<div class="nav-item">
						<a
							href="javascript:addTab('excel_import_rule','数据导入规则','${ctx}/basicdata/excelimportrule')">
							<img src="${ctx}/resources/pt/images/kappfinder.png"></img><br />
							<span>数据导入规则</span>
						</a>
					</div>
					</privilege:access>
					<privilege:access ifGranted="003009">
					<div class="nav-item">
						<a
							href="javascript:addTab('excel_export_rule','excel导出规则','${ctx}/basicdata/excelexportrule')">
							<img src="${ctx}/resources/pt/images/kappfinder.png"></img><br />
							<span>数据导出规则</span>
						</a>
					</div>
					</privilege:access>
					<privilege:access ifGranted="003010">
					<div class="nav-item">
						<a
							href="javascript:addTab('data_source','数据源','${ctx}/basicdata/datasource')">
							<img src="${ctx}/resources/pt/images/kappfinder.png"></img><br />
							<span>数据源</span>
						</a>
					</div>
					</privilege:access>
					<privilege:access ifGranted="003010">
					<div class="nav-item">
						<a
							href="javascript:addTab('data_source_profile','外部数据源配置信息','${ctx}/basicdata/datasource/profile')">
							<img src="${ctx}/resources/pt/images/kappfinder.png"></img><br />
							<span>外部数据源配置信息</span>
						</a>
					</div>
					</privilege:access>
				</div>
				</privilege:access>
				<privilege:access ifGranted="008">
				<div title="系统设置" style="overflow: auto;">
					<privilege:access ifGranted="008001">
					<div class="nav-item">
						<a
							href="javascript:addTab('work_hour','工作时间','${ctx}/basicdata/workhour')">
							<img src="${ctx}/resources/pt/images/kappfinder.png"></img><br />
							<span>工作时间</span>
						</a>
					</div>
					</privilege:access>
					<privilege:access ifGranted="008002">
					<div class="nav-item">
						<a
							href="javascript:addTab('ip_config','IP安全设置','${ctx}/security/ip/config')">
							<img src="${ctx}/resources/pt/images/kontact.png"></img><br /> <span>IP安全设置</span>
						</a>
					</div>
					</privilege:access>
					<privilege:access ifGranted="008003">
					<div class="nav-item">
						<a
							href="javascript:addTab('rtx_config','RTX设置','${ctx}/basicdata/rtx')">
							<img src="${ctx}/resources/pt/images/kontact.png"></img><br /> <span>RTX设置</span>
						</a>
					</div>
					</privilege:access>
					<privilege:access ifGranted="008004">
					<div class="nav-item">
						<a class="mouse_hand"
							onclick="window.open('${ctx}/report/design')">
							<img src="${ctx}/resources/pt/images/kontact.png"></img><br /> <span>报表设计</span>
						</a>
					</div>
					</privilege:access>
					<privilege:access ifGranted="008005">
					<div class="nav-item">
						<a
							href="javascript:addTab('job_details','任务管理','${ctx}/task/job/details')">
							<img src="${ctx}/resources/pt/images/kontact.png"></img><br /> <span>任务管理</span>
						</a>
					</div>
					</privilege:access>
					<privilege:access ifGranted="008006">
					<div class="nav-item">
						<a
							href="javascript:addTab('job_details_12','补丁升级服务','http://fbd.xm.gov.cn/')">
							<img src="${ctx}/resources/pt/images/kontact.png"></img><br /> <span>补丁升级服务</span>
						</a>
					</div>
					</privilege:access>
					<privilege:access ifGranted="008007">
					<div class="nav-item">
						<a
							href="javascript:addTab('mas_config','MAS设置','${ctx}/message/content/mas')">
							<img src="${ctx}/resources/pt/images/kontact.png"></img><br /> <span>MAS设置</span>
						</a>
					</div>
					</privilege:access>
				</div>
				</privilege:access>
				<privilege:access ifGranted="009">
				<div title="模块设置" style="overflow: auto;">
					<privilege:access ifGranted="009001">
					<div class="nav-item">
						<a
							href="javascript:addTab('sysset_shcedule','日程设置', '${ctx}/schedule/schedule_secsetlist')">
							<img src="${ctx}/resources/pt/images/package_settings.png"></img><br />
							<span>日程设置</span>
						</a>
					</div>
					</privilege:access>
					<privilege:access ifGranted="009002">
					<div class="nav-item">
						<a
							href="javascript:addTab('mail_config','邮件设置', '${ctx}/mail/mail_config')">
							<img src="${ctx}/resources/pt/images/package_settings.png"></img><br />
							<span>邮件设置</span>
						</a>
					</div>
					</privilege:access>
					<privilege:access ifGranted="009003">
					<div class="nav-item">
						<a
							href="javascript:addTab('exchange_config','商改设置', '${ctx}/exchangedata/dataconfig/exchange_sg_set')">
							<img src="${ctx}/resources/pt/images/package_settings.png"></img><br />
							<span>商改设置</span>
						</a>
					</div>
					</privilege:access>
				</div>
				</privilege:access>
				<privilege:access ifGranted="011">
				<div title="日志管理" style="overflow: auto;">
					<privilege:access ifGranted="011001">
					<div class="nav-item">
						<a
							href="javascript:addTab('system_log','系统日志','${ctx}/log/system')">
							<img src="${ctx}/resources/pt/images/kontact.png"></img><br /> <span>系统日志</span>
						</a>
					</div>
					</privilege:access>
					<privilege:access ifGranted="011002">
					<div class="nav-item">
						<a
							href="javascript:addTab('manager_log','管理操作日志','${ctx}/log/user/operation')">
							<img src="${ctx}/resources/pt/images/kontact.png"></img><br /> <span>管理操作日志</span>
						</a>
					</div>
					</privilege:access>
					<privilege:access ifGranted="011003">
					<div class="nav-item">
						<a
							href="javascript:addTab('user_log','用户登录日志','${ctx}/passport/user/loginlog')">
							<img src="${ctx}/resources/pt/images/kontact.png"></img><br /> <span>用户登录日志</span>
						</a>
					</div>
					</privilege:access>
					<privilege:access ifGranted="011004">
					<div class="nav-item">
						<a
							href="javascript:addTab('module_log','用户操作日志','${ctx}/log/user/operation')">
							<img src="${ctx}/resources/pt/images/kontact.png"></img><br /> <span>用户操作日志</span>
						</a>
					</div>
					</privilege:access>
					<privilege:access ifGranted="011005">
					<div class="nav-item">
						<a
							href="javascript:addTab('showmsg_log','短信收发日志','${ctx}/message/content/lookmsg')">
							<img src="${ctx}/resources/pt/images/kontact.png"></img><br /> <span>短信收发日志</span>
						</a>
					</div>
					</privilege:access>
				</div>
				</privilege:access>
				<privilege:access ifGranted="012">
				<div title="数据交换" style="overflow: auto;">
					<privilege:access ifGranted="012001">
					<div class="nav-item">
						<a
							href="javascript:addTab('exchange_system','接入系统设置','${ctx}/exchangedata/dataconfig/exchange_systemlist')">
							<img src="${ctx}/resources/pt/images/package_settings.png"></img><br />
							<span>接入系统</span>
						</a>
					</div>
					</privilege:access>
					<privilege:access ifGranted="012002">
					<div class="nav-item">
						<a
							href="javascript:addTab('exchange_data_type','数据类型设置','${ctx}/exchangedata/dataconfig/exchange_data_typelist')">
							<img src="${ctx}/resources/pt/images/package_settings.png"></img><br />
							<span>数据类型</span>
						</a>
					</div>
					</privilege:access>
					<privilege:access ifGranted="012003">
					<div class="nav-item">
						<a
							href="javascript:addTab('exchange_data_transform','数据转换设置','${ctx}/exchangedata/dataconfig/exchange_data_transformlist')">
							<img src="${ctx}/resources/pt/images/package_settings.png"></img><br />
							<span>数据转换</span>
						</a>
					</div>
					</privilege:access>
					<privilege:access ifGranted="012004">
					<div class="nav-item">
						<a
							href="javascript:addTab('exchange_route','路由规则设置','${ctx}/exchangedata/dataconfig/exchange_routelist')">
							<img src="${ctx}/resources/pt/images/package_settings.png"></img><br />
							<span>路由规则</span>
						</a>
					</div>
					</privilege:access>
					<privilege:access ifGranted="012006">
					<div class="nav-item">
						<a
							href="javascript:addTab('exchange_log','日志跟踪','${ctx}/exchangedata/dataconfig/exchange_loglist')">
							<img src="${ctx}/resources/pt/images/kontact.png"></img><br />
							<span>日志跟踪</span>
						</a>
					</div>
					</privilege:access>
					<privilege:access ifGranted="012008">
					<div class="nav-item">
						<a
							href="javascript:addTab('synchronous_source','同步数据','${ctx}/exchangedata/dataconfig/synchronous_source')">
							<img src="${ctx}/resources/pt/images/package_settings.png"></img><br />
							<span>同步数据</span>
						</a>
					</div>
					</privilege:access>
<%-- 					<privilege:access ifGranted="012009"> --%>
<!-- 					<div class="nav-item"> -->
<!-- 						<a -->
<%-- 							href="javascript:addTab('synchronous_rules','同步规则','${ctx}/exchangedata/dataconfig/synchronous_rules')"> --%>
<%-- 							<img src="${ctx}/resources/pt/images/package_settings.png"></img><br /> --%>
<!-- 							<span>同步规则</span> -->
<!-- 						</a> -->
<!-- 					</div> -->
<%-- 					</privilege:access> --%>
					<privilege:access ifGranted="012005">
					<div class="nav-item">
						<a
							href="javascript:addTab('system_pro','系统参数设置','${ctx}/exchangedata/dataconfig/sys_properties')">
							<img src="${ctx}/resources/pt/images/package_settings.png"></img><br />
							<span>系统参数设置</span>
						</a>
					</div>
					</privilege:access>
				</div>
				</privilege:access>
			</div>
			<div position="center" id="framecenter">
				<div tabid="home" title="主页">
					<privilege:access ifGranted="010003">
					<iframe frameborder="0" name="home"
						src="${ctx}/passport/admin/home"></iframe>
					</privilege:access>
				</div>
			</div>
		</div>
		<!-- layout1 end -->
		<div id="footer" class="row-fluid">
			<div class="span4 offset4" style="height: 30px; line-height: 30px; text-align: center; vertical-align: middle;">
				Copyright &copy; <a>厦门威尔软件</a>
			</div>
		</div>
		<!-- footer end -->
	</div>
	<!-- Bootstrap -->
	<script src="${ctx}/resources/bootstrap/js/bootstrap.js"></script>
	<script src="${ctx}/resources/pt/js/global.js"></script>
</body>
</html>