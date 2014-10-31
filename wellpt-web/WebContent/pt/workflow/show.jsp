<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/pt/common/taglibs.jsp"%>
<html>
<head>
<title>图形化工作流 2.0</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=Quirks">
<script src="${ctx}/resources/jquery/jquery.js"></script>
<script type="text/javascript" src="${ctx}/resources/utils/json2.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
<script src="${ctx}/pt/workflow/js/JSConstant.js"></script>
<script src="${ctx}/pt/workflow/js/workflow.js"></script>
<script src="${ctx}/pt/workflow/js/property.js"></script>
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
	<TD CLASS="Menu" id="menu_grid">网格(<span style="text-decoration:underline">W</span>)</TD>
	<TD width=1><DIV CLASS="TBSep"></DIV></TD>
	<TD CLASS="Menu" id="menu_up">上移(<span style="text-decoration:underline">T</span>)</TD>
	<TD CLASS="Menu" id="menu_down">下移(<span style="text-decoration:underline">B</span>)</TD>
	<TD CLASS="Menu" id="menu_left">左移(<span style="text-decoration:underline">L</span>)</TD>
	<TD CLASS="Menu" id="menu_right">右移(<span style="text-decoration:underline">R</span>)</TD>
	</TR></Table>
</td></tr>
<tr><td width="35" valign=top height=100%>
	<TABLE border="0" cellPadding="0" cellSpacing="0" width="35"><TR><TD>&nbsp;</TD></TR></Table>
</td><td width=100%>
<iframe ID="eWorkFlow" src="work" MARGINHEIGHT="0" MARGINWIDTH="0" width="100%" height="100%" scrolling="yes"></iframe>
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
</table>
<script type="text/javascript">
<!--
$(function(){
	attachEvent();
	// IE滚轮事件处理(刷新流程图)
	if(eWorkFlow && eWorkFlow.document){
		var scrollFunc = function(event){
			// document.location.reload();
			return false;
		};
		eWorkFlow.document.attachEvent('onmousewheel',scrollFunc); 
	}
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
<A onClick="eWorkFlow.checkWorkFlow();return false;" ID="ID_MENU_FLOW" disabled>错误检查</A>
<A ID="ID_MENU_FLOW" imgName=HR></A>
<A onClick="eWorkFlow.saveWorkFlow();return false;" ID="ID_MENU_FLOW" disabled>保存</A>
<A ID="ID_MENU_FLOW" imgName=HR></A>
<A onClick="eWorkFlow.bDealObject('paste');return false;" ID="ID_MENU_FLOW" disabled>粘贴</A>

<A onClick="eWorkFlow.setTaskProperty();return false;" ID="ID_MENU_TASK">环节属性</A>
<A ID="ID_MENU_TASK" imgName=HR></A>
<A onClick="eWorkFlow.showTasks('before')";return false;" ID="ID_MENU_TASK">显示前导节点</A>
<A onClick="eWorkFlow.showTasks('after');return false;" ID="ID_MENU_TASK">显示后续节点</A>
<A ID="ID_MENU_TASK" imgName=HR></A>
<A onClick="eWorkFlow.bDealObject('cut');return false;" ID="ID_MENU_TASK" disabled>剪切</A>
<A onClick="eWorkFlow.bDealObject('copy');return false;" ID="ID_MENU_TASK" disabled>拷贝</A>
<A onClick="eWorkFlow.bDealObject('delete');return false;" ID="ID_MENU_TASK" disabled>删除</A>

<A onClick="eWorkFlow.setDirectionProperty();return false;" ID="ID_MENU_DIRECTION">流向属性</A>
<A ID="ID_MENU_DIRECTION" imgName=HR></A>
<A onClick="eWorkFlow.bSetDirection('arrow');return false;" ID="ID_MENU_DIRECTION" status="disable">反向</A>
<A onClick="eWorkFlow.bSetDirection('curve');return false;" ID="ID_MENU_DIRECTION" status="disable">曲线</A>
<A onClick="eWorkFlow.bSetDirection('beeline');return false;" ID="ID_MENU_DIRECTION" status="disable">直线</A>
<A ID="ID_MENU_DIRECTION" imgName=HR></A>
<A onClick="eWorkFlow.showLineName(true,true);return false;" ID="ID_MENU_DIRECTION">显示流向名称</A>
<A onClick="eWorkFlow.showLineName(false,true);return false;" ID="ID_MENU_DIRECTION">隐藏流向名称</A>
<A ID="ID_MENU_DIRECTION" imgName=HR></A>
<A onClick="eWorkFlow.bDealObject('delete');return false;" ID="ID_MENU_DIRECTION" status="disable">删除</A>

<A onClick="eWorkFlow.bDealObject('cut');return false;" ID="ID_MENU_LABEL" disabled>剪切</A>
<A onClick="eWorkFlow.bDealObject('copy');return false;" ID="ID_MENU_LABEL" disabled>拷贝</A>
<A onClick="eWorkFlow.bDealObject('delete');return false;" ID="ID_MENU_LABEL" disabled>删除</A>

</span>
<script type="text/javascript" src="${ctx}/resources/artDialog/jquery.artDialog.js?skin=default"></script>
<script type="text/javascript" src="${ctx}/resources/artDialog/plugins/iframeTools.js"></script>
<script src="${ctx}/pt/workflow/js/jquery.workflow.js"></script>
</body>
</html>