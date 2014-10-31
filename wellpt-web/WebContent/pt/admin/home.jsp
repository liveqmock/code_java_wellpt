<%@page import="com.wellsoft.pt.core.context.ApplicationContextHolder"%>
<%@page import="com.wellsoft.pt.cms.facade.CmsApiFacade"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<title>首页</title>
<style type="text/css">.xct {
		font-family: "Microsoft YaHei";
}
.xct {
    background: none repeat scroll 0 0 #FFFFFF;
    margin: auto;
}
.xct .fun-list {
    height: 100%;
    margin: auto;
    width: 80%;
}

.xct .fun-list .list-item {
	background: none repeat scroll 0 0 #EAF4FD;
    border: 1px solid #C5CACD;
    cursor: pointer;
    float: left;
    height: 30%;
    margin: 1%;
    position: relative;
    width: 22%;
}
.xct .fun-list .list-item:hover{
	box-shadow: 2px 2px 3px #B5B5B5;
}
.xct .fun-list .list-item h4 {
    font-weight: normal;
    margin: 0;
    text-align: center;
    height: 100%;
}

.xct .fun-list .list-item h4 a{
    color: #183152;
    text-decoration: none;
    font-size:16px;
}
.xct .fun-list .list-item h4 a:hover{
	text-decoration: underline;
}
.xct .fun-list .list-item h4 .num {
    right: 5px;
    top: 8px;
    background: url("/wellpt-web/resources/theme/images/v1_icon.png") no-repeat scroll -56px -142px transparent;
    color: #FFFFFF;
    font-size: 14px;
    height: 19px;
    line-height: 19px;
    padding: 0 8px 0 15px;
    position: absolute;
}
</style>
</head>
<body>
	<div class="xct">
	<div class="clearfix fun-list">
	<div class="list-item xct_div">
	<h4><a tabid="org_department_user" tabtext="用户" url="${ctx}/org/department/user">用户管理</a></h4>
	</div>
	
	<div class="list-item xct_div">
	<h4><a tabid="flow_define" tabtext="流程定义" url="${ctx}/workflow/define/flow">流程管理</a></h4>
	</div>
	
	<div class="list-item xct_div">
	<h4><a tabid='form_definition' tabtext='编辑表单（新）' url='${ctx}/dyform/editable/form/definition/list?flag=1'>表单管理（新）</a></h4>
	</div>
	
	<div class="list-item xct_div">
	<h4><a tabid='cms_pageconfig' tabtext='界面' url='${ctx}/cms/cmspage/define'>界面管理</a></h4>
	</div>
	
	<div class="list-item xct_div">
	<h4><a tabid='dyview' tabtext='报表管理' url='#' url_="${ctx}/report">报表管理</a></h4>
	</div>
	
	<div class="list-item xct_div">
	<h4><a tabid='print_template' tabtext='打印模版' url='${ctx}/basicdata/printtemplate'>模版管理</a></h4>
	</div>
	
	<div class="list-item xct_div">
	<h4><a tabid='cms_category' tabtext='数据字典' url='${ctx}/basicdata/datadict'>数据字典</a></h4>
	</div>
	
	<div class="list-item xct_div">
	<h4><a tabid='org_unit' tabtext='时间设置' url='${ctx}/basicdata/workhour'>系统设置</a></h4>
	</div>
	
	</div>
	</div>
</body>
<script type="text/javascript" src="${ctx}/resources/jquery/jquery.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
<script>
$(window.parent.document).ready(function(){
	var height = $(".l-tab-content", window.parent.document).height();
	height = parseInt(height)-30;
	$(".xct").css("height",height);
	var itenHeight = $(".xct_div").css("height");
	$(".xct_div").css("height",itenHeight);
	$(".xct_div").css("line-height",itenHeight);
	
	$(".xct_div").click(function(){
		var tabid = $(this).find("a").attr("tabid");
		var tabtext = $(this).find("a").attr("tabtext");
		var url = $(this).find("a").attr("url");
		if(url!="#"){
			TabUtils.openTab(tabid, tabtext, url);
		}else{
			window.open($(this).find("a").attr("url_"));
		}
// 		$(".xct_div").css("box-shadow","none");
// 		$(this).css("box-shadow","2px 2px 3px #B5B5B5");
	});
});
</script>
</html>