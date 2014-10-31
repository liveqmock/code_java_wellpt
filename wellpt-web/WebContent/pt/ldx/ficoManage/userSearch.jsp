<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<title>用户选择</title>

<link type="text/css" rel="stylesheet"
	href="${ctx}/resources/validform/css/style.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/jqueryui/css/base/jquery-ui.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/jqgrid/css/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css" media="screen"
	href="${ctx}/resources/jqgrid/themes/ui.multiselect.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/pt/css/custom-admin-style.css" />
<link href="${ctx}/resources/jBox/Skins/Blue/jbox.css" rel="stylesheet"
	type="text/css" />
<style type="text/css">
html,body {
	width: 100%;
	height: 100%;
	padding: 0;
	margin: 0;
	overflow: auto; /* when page gets too small */
}

#container {
	background: #999;
	height: 100%;
	position: absolute;
	margin: 0 auto;
	width: 100%;
}

.pane {
	display: none; /* will appear when layout inits */
}

.selectedrow{
	background: #B3D9FF;
}
</style>

</head>
<body>
  <div class="div_body" style="">
    <form action="${ctx}/ficoManage/custChoose" id="userInfoForm" class="dyform">
		<input name="bukrs" id="bukrs" value="${bukrs}" type="hidden">
		<input name="kunnr" id="kunnr" value="${kunnr}" type="hidden">
    	<table>
    		<tbody>
    			<tr>
					<td class="title" colspan="5">查询</td>
				</tr>
				<tr class="field">
					<td width="80px">姓名:</td>
					<td width="120px"><input type="text" name="name" id="name" style="border:0;width:100%;height:90%;" value="${name}"/></td>
					<td width="80px">工号:</td>
					<td width="120px"><input type="text" name="code" id="code" style="border:0;width:100%;height:90%;" value="${code}"/></td>
					<td width="60px">
						 <div class="form_operate">
				          <button id="form_search" type="butoon" onclick="return false;">查询</button>
				        </div>
					</td>
				</tr>
    		</tbody>
    	</table>
    	<br/>
    	<table style="margin-top: 4px;">
    		<thead>
    			<tr class="ui-jqgrid-labels">
					<th width="110px" class="ui-state-default ui-th-column ui-th-ltr">用户名</th>
					<th width="120px" class="ui-state-default ui-th-column ui-th-ltr">工号</th>
					<th width="120px" class="ui-state-default ui-th-column ui-th-ltr">角色</th>
				</tr>
    		</thead>
    		<tbody id="userInfoBody">
    			<c:forEach var="item" items="${list}">
    				<tr id="${item[0]}_${item[1]}">
    					<td>${item[0]}</td>
    					<td>${item[1]}</td>
    					<td>${item[3]}</td>
    				</tr>
    			</c:forEach>
    		</tbody>
    	</table>
    </form>
  </div>
</body>

<script type="text/javascript">
function closeDialog(){
	window.close();
}
var receive = [];
var subFlag = false;
$(document).ready(function(){
	$('#userInfoBody tr').click(function(){
		$('#userInfoBody tr').removeClass("selectedrow");
		$(this).addClass("selectedrow");
	});
	//查询按钮事件
	$("#form_search").click(function(){
		var param = {
			'bukrs':$("#userInfoForm #bukrs").val(),
			'kunnr':$("#userInfoForm #kunnr").val(),
			'name':$("#userInfoForm #name").val(),
			'code':$("#userInfoForm #code").val()
		};
		$.ajax({
			url : "searchUserInfo",
			type : "post",
			data : param ,
			dataType : "json",
			contentType : "application/x-www-form-urlencoded; charset=utf-8"
		}).done(function(ret) {
			$("#userInfoBody").html(generateRows(ret));
			$('#userInfoBody tr').click(function(){
				$('#userInfoBody tr').removeClass("selectedrow");
				$(this).addClass("selectedrow");
			});
		});
	});
});

function generateRows(object){
	if(object===undefined || object.length==0)
		return "";
	var array = [];
	var j = 0;
	for(var i=0;i<object.length;i++){
		array[j++]="<tr id='"+object[i][0]+"_"+object[i][1]+"'>";
		array[j++]="<td>"+object[i][0]+"</td>";
		array[j++]="<td>"+object[i][1]+"</td>";
		array[j++]="<td>"+(object[i][3]==null?'':object[i][3])+"</td>";
		array[j++]="</tr>";
	}
	return array.join("");
}
</script>
</html>