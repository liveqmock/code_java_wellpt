<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/pt/common/taglibs.jsp"%>
<html>
<head>
<title>图形化工作流 2.0</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=Quirks">
<script src="${ctx}/resources/jquery/jquery.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
<script src="${ctx}/pt/workflow/js/JSConstant.js"></script>
<script src="${ctx}/pt/workflow/js/graphics.js"></script>
<script src="${ctx}/pt/workflow/js/workflow.js"></script>
<script src="${ctx}/pt/workflow/js/property.js"></script>
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/jqueryui/css/base/jquery-ui.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/ztree/css/zTreeStyle/zTreeStyle.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/pt/css/custom-admin-style.css" />
<link href="${ctx}/pt/workflow/css/Flow.css" type="text/css" rel="stylesheet">
</head>
<body style="overflow: none">
<table border=0 cellpadding=0 cellspacing=0 width='100%' height='100%'>
<tr valign=top><td height=28 colspan="2">
	<TABLE border="0" cellPadding="0" cellSpacing="0" height=27 class=MenuBar>
	<TR valign=middle>
	<TD width=5 align=right><DIV CLASS="TBHandle"></DIV></TD>
	<TD width=3></TD>
	<TD width=10><DIV CLASS="TBHandle"></DIV></TD>
	<TD CLASS="Menu" onclick="window.close();">关闭(<span style="text-decoration:underline">X</span>)</TD>
	<TD width=1><DIV CLASS="TBSep"></DIV></TD>
	<TD CLASS="Menu" id="menu_save">保存(<span style="text-decoration:underline">S</span>)</TD>
	<TD CLASS="Menu_Big" id="menu_save_version">保存为新版本(<span style="text-decoration:underline">S</span>)</TD>
	<TD CLASS="Menu" id="menu_clear">清空(<span style="text-decoration:underline">D</span>)</TD>
	<TD CLASS="Menu" id="menu_grid">网格(<span style="text-decoration:underline">W</span>)</TD>
	<TD width=1><DIV CLASS="TBSep"></DIV></TD>
	<TD CLASS="Menu" id="menu_up">上移(<span style="text-decoration:underline">T</span>)</TD>
	<TD CLASS="Menu" id="menu_down">下移(<span style="text-decoration:underline">B</span>)</TD>
	<TD CLASS="Menu" id="menu_left">左移(<span style="text-decoration:underline">L</span>)</TD>
	<TD CLASS="Menu" id="menu_right">右移(<span style="text-decoration:underline">R</span>)</TD>
	<TD width=1 Version="Simple"><DIV CLASS="TBSep"></DIV></TD>
	<TD CLASS="Menu" onclick="window.open('${ctx}/pt/workflow/help/index.htm','_blank');" Version="Simple">帮助(<span style="text-decoration:underline">H</span>)</TD>
	</TR></Table>
</td></tr>
<tr><td width="35" valign=top height=100%>
	<TABLE border="0" cellPadding="0" cellSpacing="0" width="33" class=ToolBar>
	<TR><TD align=center>
	<TABLE border="0" cellPadding="0" cellSpacing="0" width="25">
	<TR><TD height=10></TD></TR>
	<TR><TD class=ToolBar align=center><img src="${ctx}/pt/workflow/images/ico_default.gif" title="选择项目"></TD></TR>
	<TR><TD class=ToolBar align=center><img src="${ctx}/pt/workflow/images/ico_begin.gif" title="开始环节"></TD></TR>
	<TR><TD class=ToolBar align=center><img src="${ctx}/pt/workflow/images/ico_task.gif" title="环节"></TD></TR>
	<TR Version="Simple"><TD class=ToolBar align=center><img src="${ctx}/pt/workflow/images/ico_subflow.gif" title="子流程"></TD></TR>
	<TR style="display:none"><TD class=ToolBar align=center><img src="${ctx}/pt/workflow/images/ico_freetask.gif" title="自动环节"></TD></TR>
	<TR><TD class=ToolBar align=center><img src="${ctx}/pt/workflow/images/ico_condition.gif" title="判断点"></TD></TR>
	<TR><TD class=ToolBar align=center><img src="${ctx}/pt/workflow/images/ico_end.gif" title="结束环节"></TD></TR>
	<TR><TD class=ToolBar align=center><img src="${ctx}/pt/workflow/images/ico_beeline.gif" title="创建直线流向"></TD></TR>
	<TR><TD class=ToolBar align=center><img src="${ctx}/pt/workflow/images/ico_curve.gif" title="创建曲线流向"></TD></TR>
	<TR><TD class=ToolBar align=center><img src="${ctx}/pt/workflow/images/ico_label.gif" title="创建标签"></TD></TR>
	<TR><TD height=30></TD></TR>
	</Table>
	</TD></TR>
	</Table>
	<TABLE border="0" cellPadding="0" cellSpacing="0" width="35"><TR><TD>&nbsp;</TD></TR></Table>
</td><td width=100%>
<div style="width:100%;height: 100%" class="layout">
<div class="ui-layout-west">
<iframe ID="eWorkFlow" src="work" MARGINHEIGHT="0" MARGINWIDTH="0" width="100%" height="100%" scrolling="yes"></iframe>
</div>
<div class="ui-layout-center" style="background-color: #fff;font-size: 12px;">
<div id="eProperty" style="font-size: 12px;"></div>
<div id="dlg_select_sub_flow"></div>
<div id="dlg_select_user"></div>
<div id="dlg_select_button"></div>
<div id="dlg_checkbox_select"></div>
<!-- <iframe ID="eProperty" src="flow2" MARGINHEIGHT="0" MARGINWIDTH="0" width="100%" height="100%" style="background-color:#fff;" scrolling="yes"></iframe> -->
</div>
<div style="clear: both;"></div>
</div>
</td></tr>
<tr><td height="25" colspan="2">
	<TABLE border="0" cellPadding="0" cellSpacing="0" width="100%" height=25 class=StatusBar>
	<TR valign=bottom>
		<td width="35"></td>
		<td align=right>
		<table border=0 cellpadding=1 cellspacing=0 height=20 width="100%"><tr>
		<td class=StatusBar id="ID_MESSAGE" nowrap></td>
		<td class=StatusBar></td>
		<td class=StatusBar width=90>WellFlow 2.0</td>
		<td class=StatusBar width=90 ID="ID_Position">0px,0px</td>
		<td class=StatusBar width="90">
<script language='JavaScript'>
	today=new Date();
	document.write(today.getFullYear()+"年"+((today.getMonth()<9)?"0"+(today.getMonth()+1):today.getMonth()+1)+"月"+((today.getDate()<10)?"0"+today.getDate():today.getDate())+"日");
</script>
		</td>
		</tr></table>
		</td>
	</TR></Table>
</td></tr>
<tr style="display:none"><td colspan="2"><textarea id="id_flowxml" style="width:100%"></textarea></td></tr>
</table>
<script type="text/javascript">
<!--
$(function(){
	attachEvent();
});
//-->
</script>
<font ID="ID_PromptDoing" style="display:none"></font>
<span style="display:none;">

<A onClick="eWorkFlow.setFlowProperty();return false;" ID="ID_MENU_FLOW">流程属性</A>
<A ID="ID_MENU_FLOW" imgName=HR></A>
<A onClick="eWorkFlow.showLineName(true);return false;" ID="ID_MENU_FLOW">显示全部流向名称</A>
<A onClick="eWorkFlow.showLineName(false);return false;" ID="ID_MENU_FLOW">隐藏全部流向名称</A>
<A ID="ID_MENU_FLOW" imgName=HR></A>
<A onClick="eWorkFlow.showLabel(true);return false;" ID="ID_MENU_FLOW">显示注释标签</A>
<A onClick="eWorkFlow.showLabel(false);return false;" ID="ID_MENU_FLOW">隐藏注释标签</A>
<A ID="ID_MENU_FLOW" imgName=HR></A>
<A onClick="eWorkFlow.checkWorkFlow();return false;" ID="ID_MENU_FLOW">错误检查</A>
<A ID="ID_MENU_FLOW" imgName=HR></A>
<A onClick="eWorkFlow.saveWorkFlow();return false;" ID="ID_MENU_FLOW">保存并覆盖当前版本</A>
<A onClick="eWorkFlow.saveWorkFlow(true);return false;" ID="ID_MENU_FLOW">保存为新版本</A>
<A ID="ID_MENU_FLOW" imgName=HR></A>
<A onClick="eWorkFlow.bDealObject('paste');return false;" ID="ID_MENU_FLOW">粘贴</A>

<A onClick="eWorkFlow.setTaskProperty();return false;" ID="ID_MENU_TASK">环节属性</A>
<A ID="ID_MENU_TASK" imgName=HR></A>
<A onClick="eWorkFlow.showTasks('before')";return false;" ID="ID_MENU_TASK">显示前导节点</A>
<A onClick="eWorkFlow.showTasks('after');return false;" ID="ID_MENU_TASK">显示后续节点</A>
<A ID="ID_MENU_TASK" imgName=HR></A>
<A onClick="eWorkFlow.bDealObject('cut');return false;" ID="ID_MENU_TASK">剪切</A>
<A onClick="eWorkFlow.bDealObject('copy');return false;" ID="ID_MENU_TASK">拷贝</A>
<A onClick="eWorkFlow.bDealObject('delete');return false;" ID="ID_MENU_TASK">删除</A>

<A onClick="eWorkFlow.setDirectionProperty();return false;" ID="ID_MENU_DIRECTION">流向属性</A>
<A ID="ID_MENU_DIRECTION" imgName=HR></A>
<A onClick="eWorkFlow.bSetDirection('arrow');return false;" ID="ID_MENU_DIRECTION">反向</A>
<A onClick="eWorkFlow.bSetDirection('curve');return false;" ID="ID_MENU_DIRECTION">曲线</A>
<A onClick="eWorkFlow.bSetDirection('beeline');return false;" ID="ID_MENU_DIRECTION">直线</A>
<A ID="ID_MENU_DIRECTION" imgName=HR></A>
<A onClick="eWorkFlow.showLineName(true,true);return false;" ID="ID_MENU_DIRECTION">显示流向名称</A>
<A onClick="eWorkFlow.showLineName(false,true);return false;" ID="ID_MENU_DIRECTION">隐藏流向名称</A>
<A ID="ID_MENU_DIRECTION" imgName=HR></A>
<A onClick="eWorkFlow.bDealObject('delete');return false;" ID="ID_MENU_DIRECTION">删除</A>

<A onClick="eWorkFlow.bDealObject('cut');return false;" ID="ID_MENU_LABEL">剪切</A>
<A onClick="eWorkFlow.bDealObject('copy');return false;" ID="ID_MENU_LABEL">拷贝</A>
<A onClick="eWorkFlow.bDealObject('delete');return false;" ID="ID_MENU_LABEL">删除</A>

</span>
<script type="text/javascript" src="${ctx}/resources/jquery/jquery.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/jqueryui/js/jquery-ui.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/ztree/js/jquery.ztree.js"></script>
	<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/common/jquery.popupTreeWindow.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/common/jquery.comboTree.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/org/unit/jquery.unit.js"></script>
<script src="${ctx}/pt/workflow/js/jquery.workflow.js"></script>
<script type="text/javascript"
		src="${ctx}/resources/layout/jquery.layout.js"></script>
<script type="text/javascript"
		src="${ctx}/resources/pt/js/system_admin.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	// 页面布局
	Layout.layout({
		//maskContents:			"#eProperty"		// true = add DIV-mask over-or-inside this pane so can 'drag' over IFRAMES
		//,	maskObjects:			"#eWorkFlow"		// true = add IFRAME-mask over-or-inside this pane to cover objects/applets - content-mask will overlay this mask
		//,	maskZindex:	999,
		west__size:700, layout_selector: ".layout"});
});
</script>
</body>
</html>