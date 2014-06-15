<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/pt/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Done List</title>
<%@ include file="/pt/common/meta.jsp"%>

<link href="${ctx}/resources/easyui/themes/default/easyui.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/resources/easyui/themes/icon.css" type="text/css" rel="stylesheet" />

<script src="${ctx}/resources/easyui/jquery-1.8.0.min.js" type="text/javascript"></script>
<script src="${ctx}/resources/easyui/jquery.easyui.min.js" type="text/javascript"></script>
<script src="${ctx}/resources/easyui/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
	<script type="text/javascript">
	$().ready(function(){
		$("#view").click(function(){
			var row = $('#work_done').datagrid('getSelected');
			if (row) {
				TabUtils.openTab(row['flowInstUuid'], row['title'], ctx + '/workflow/work/view/complete?flowInstUuid=' + row['flowInstUuid']);
			} else {
				$.messager.alert('warning', '请选择工作流程', 'warning');
			}
		});
	});
	</script>
</head>
<body style="width:100%; height:100%;padding:0px; margin:0px;">
	<div id="toolbar">
		<a href="#" class="easyui-linkbutton" id="view">查看</a>
	</div>
	<table id="work_done" title="已办工作" class="easyui-datagrid" style="height:350px"
			url="${ctx}/workflow/work/over/list.action"
			toolbar="#toolbar" pagination="true"
			rownumbers="true" fitColumns="true" singleSelect="true">
		<thead>
			<tr>
				<th field="title" width="150">标题</th>
				<th field="endTime" width="100">办结时间</th>
				<th field="flowName" width="100">流程</th>
			</tr>
		</thead>
	</table>
</body>
</html>