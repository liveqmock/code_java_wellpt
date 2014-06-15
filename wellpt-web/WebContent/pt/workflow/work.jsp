<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/pt/common/taglibs.jsp"%>
<html>
<head>
<title>WELLFLOW 2.0</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="${ctx}/resources/jquery/jquery.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
<link href="${ctx}/pt/workflow/css/Flow.css" type="text/css" rel="stylesheet">
<script src="${ctx}/pt/workflow/js/jquery.workflow.js"></script>
<script src="${ctx}/pt/workflow/js/graphics.js"></script>
<script src="${ctx}/pt/workflow/js/workflow.js"></script>
<script type="text/javascript">
	$(function(){
		loadEvent();
	});
</script>
</head>
<body class="WORKBODY">
<script src="${ctx}/pt/workflow/js/dragdrop.js"></script>

<Div style="POSITION: absolute;width:100;height:40;display:none" id="ID_LABELVALUEDIV"><TextArea style='width:100%;height:100%' id="ID_LABELVALUE" onclick="event.cancelBubble=true;" ondblClick="event.cancelBubble=true;"></TextArea></Div>

<Div style="POSITION: relative;width:3200;height:2400;" id="ID_WORKAROUND">
<IMG src="${ctx}/pt/workflow/images/wl_begin.gif" name="img_begin" width="40" height="40">
<IMG src="${ctx}/pt/workflow/images/wl_condition.gif" name="img_condition" width="105" height="55">
<IMG src="${ctx}/pt/workflow/images/wl_end.gif" name="img_end" width="40" height="40">
<IMG src="${ctx}/pt/workflow/images/wl_freetask.gif" name="img_freetask" width="80" height="40">
<IMG src="${ctx}/pt/workflow/images/wl_subflow.gif" name="img_subflow" width="80" height="40">
<IMG src="${ctx}/pt/workflow/images/wl_task.gif" name="img_task" width="80" height="40">
<IMG src="${ctx}/pt/workflow/images/wl_label.gif" name="img_label" width="118" height="40">
<IMG src="${ctx}/pt/workflow/images/wl_line.gif" name="img_line" width="160" height="20">
</Div>
<div class="contextMenu" id="ID_MENU_FLOW">
	<ul>
		<li id="flowProperty">流程属性</li>
		<li id="showLineName">显示全部流向名称</li>
		<li id="hideLineName">隐藏全部流向名称</li>
		<li id="showLabel">显示注释标签</li>
		<li id="hideLabel">隐藏注释标签</li>
		<li id="checkWorkFlow">错误检查</li>
		<li id="saveWorkFlow">保存并覆盖当前版本</li>
		<li id="saveNewWorkFlow">保存为新版本</li>
		<li id="paste">粘贴</li>
	</ul>
</div>
<div class="contextMenu" id="ID_MENU_TASK">
	<ul>
		<li id="taskProperty">环节属性</li>
		<li id="showBeforeTasks">显示前导节点</li>
		<li id="showAfterTasks">显示后续节点</li>
		<li id="cutTask">剪切</li>
		<li id="copyTask">拷贝</li>
		<li id="deleteTask">删除</li>
	</ul>
</div>
<div class="contextMenu" id="ID_MENU_DIRECTION">
	<ul>
		<li id="directionProperty">流向属性</li>
		<li id="arrow">反向</li>
		<li id="curve">曲线</li>
		<li id="beeline">直线</li>
		<li id="showDLineName">显示流向名称</li>
		<li id="hideDLineName">隐藏流向名称</li>
		<li id="deleteDirection">删除</li>
	</ul>
</div>
<div class="contextMenu" id="ID_MENU_LABEL">
	<ul>
		<li id="cutLabel">剪切</li>
		<li id="copyLabel">拷贝</li>
		<li id="deleteLabel">删除</li>
	</ul>
</div>
<script type="text/javascript">
<!--
SET_DHTML(CURSOR_MOVE,RESIZABLE,"img_begin","img_condition","img_end","img_freetask","img_subflow","img_task","img_label","img_line");

for (var i=0;i<dd.elements.length;i++){dd.elements[i].hide();}


//document.oncontextmenu = contextMenu;
//$(document).bind("contextmenu", contextMenu);
$(document).bind("mousemove", mouseMove);
$(document).bind("mouseover", mouseOver);
$(document).bind("mouseout", mouseOut);
$(document).bind("click", mouseClick);
$(document).bind("mousedown", mouseDown);
$(document).bind("mouseup", mouseUp);
$(document).bind("dblclick", mouseDblClick);
document.onselectstart=Function("return false;");
//-->
</script>
<script type="text/javascript"
	src="${ctx}/resources/contextmenu/jquery.contextmenu.r2.js"></script>
<script type="text/javascript">
$("body").contextMenu('ID_MENU_FLOW', {
    bindings: {
		'flowProperty': function(t) {
			setFlowProperty();
		},
		'showLineName': function(t) {
			showLineName(true);
		},
		'hideLineName': function(t) {
			showLineName(false);
		},
		'showLabel': function(t) {
			showLabel(true);
		},
		'hideLabel': function(t) {
			showLabel(false);
		},
		'checkWorkFlow': function(t) {
			checkWorkFlow();
		},
		'saveWorkFlow': function(t) {
			saveWorkFlow();
		},
		'saveNewWorkFlow': function(t) {
			saveWorkFlow(true);
		},
		'paste': function(t) {
			bDealObject('paste');
		}
	},menuStyle : {
		width: "150px"
	}, onKeyDown: function(e){
	}, onShowMenu: function(e, menu){
		// 右键点击切换到鼠标画图状态
		if(parent.gaTools){
			for(var i = 0; i < parent.gaTools.length; i++){
				//if(i != 0 && parent.gaTools[i].className === "ToolInset"){
					$(parent.gaTools[0], parent).trigger("mousedown");
				//}
			}
		}
		if(goWorkFlow.copyObject == null){
			$("#paste", menu).remove();
		}
		return menu;
	}
  });
</script>
</body>
</html>