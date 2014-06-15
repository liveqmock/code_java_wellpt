<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/pt/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Draft Mail List</title>
<%@ include file="/pt/common/meta.jsp"%>

<link href="${ctx}/resources/easyui/themes/default/easyui.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/resources/easyui/themes/icon.css" type="text/css" rel="stylesheet" />

<script src="${ctx}/resources/easyui/jquery-1.8.0.min.js" type="text/javascript"></script>
<script src="${ctx}/resources/easyui/jquery.easyui.min.js" type="text/javascript"></script>
<script src="${ctx}/resources/easyui/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
	<script type="text/javascript">
	function workNew(){
		var row = $('#work_new').datagrid('getSelected');
		if (row) {
			TabUtils.openTab(row['taskUid'], row['name'],ctx + '/workflow/work/new?flowDefUid=' + row['uuid']);
		} else {
			$.messager.alert('warning', '请选择工作流程', 'warning');
		}
	}
	</script>
</head>
<body style="width:100%; height:100%;padding:0px; margin:0px;">
	<div id="toolbar">
		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="workNew(parent)">新建工作</a>
	</div>
	<table id="work_new" title="新建工作" class="easyui-datagrid" style="height:350px"
			url="${ctx}/workflow/work/flow/define/list.action"
			toolbar="#toolbar" pagination="true"
			rownumbers="true" fitColumns="true" singleSelect="true">
		<thead>
			<tr>
				<th field="name" width="150">名称</th>
				<th field="id" width="150">ID</th>
				<th field="version" width="150">版本</th>
			</tr>
		</thead>
	</table>
</body>
</html>