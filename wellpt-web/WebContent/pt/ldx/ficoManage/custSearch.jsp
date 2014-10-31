<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<title>客户选择</title>

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
    <form action="${ctx}/ficoManage/custChoose" id="custInfoForm" class="dyform">
		<input name="recBukrs" id="recBukrs" value="${recBukrs}" type="hidden">
    	<table>
    		<tbody>
    			<tr>
					<td class="title" colspan="7">查询</td>
				</tr>
				<tr class="field">
					<td width="80px">公司代码:</td>
					<td width="80px"><input type="text" name="bukrs" id="bukrs" style="border:0;width:100%;height:90%;" value="${bukrs}" readonly="true"/></td>
					<td width="80px">客户编号:</td>
					<td width="100px"><input type="text" name="kunnr" id="kunnr" style="border:0;width:100%;height:90%;" value="${kunnr}"/></td>
					<td width="80px">客户简称:</td>
					<td width="100px"><input type="text" name="sortl" id="sortl" style="border:0;width:100%;height:90%;" value="${sortl}"/></td>
					<td width="60px">
						 <div class="form_operate">
				          <button id="cust_search" type="butoon" onclick="return false;">查询</button>
				        </div>
					</td>
				</tr>
    		</tbody>
    	</table>
    	<br/>
    	<table style="margin-top: 4px;">
    		<thead>
    			<tr class="ui-jqgrid-labels">
					<th width="110px" class="ui-state-default ui-th-column ui-th-ltr">公司代码</th>
					<th width="120px" class="ui-state-default ui-th-column ui-th-ltr">客户编号</th>
					<th width="120px" class="ui-state-default ui-th-column ui-th-ltr">客户简称</th>
					<th width="100px" class="ui-state-default ui-th-column ui-th-ltr">OM</th>
					<th width="100px" class="ui-state-default ui-th-column ui-th-ltr">AE</th>
					<th width="100px" class="ui-state-default ui-th-column ui-th-ltr">AA</th>
					<th width="60px" class="ui-state-default ui-th-column ui-th-ltr">RSM</th>
					<th width="70px" class="ui-state-default ui-th-column ui-th-ltr">应收会计</th>
				</tr>
    		</thead>
    		<tbody id="custInfoBody">
    			<c:forEach var="item" items="${list}">
    				<tr id="${item[1]}">
    					<td>${item[0]}</td>
    					<td>${item[1]}</td>
    					<td>${item[2]}</td>
    					<td>${item[3]}</td>
    					<td>${item[4]}</td>
    					<td>${item[5]}</td>
    					<td>${item[6]}</td>
    					<td>${item[7]}</td>
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
	var modify="${modify}";
	var recBukrs = "${recBukrs}";
	if(modify == "true"||recBukrs=="9999"||recBukrs=="7300"){
		$("#custInfoForm #bukrs").removeAttr("readonly");
	}
	//查询按钮事件
	$("#cust_search").click(function(){
		var param = {
			'bukrs':$("#custInfoForm #bukrs").val(),
			'kunnr':$("#custInfoForm #kunnr").val(),
			'sortl':$("#custInfoForm #sortl").val(),
			'recBukrs':$("#custInfoForm #recBukrs").val()
		};
		$.ajax({
			url : "searchCustInfo",
			type : "post",
			data : param ,
			dataType : "json",
			contentType : "application/x-www-form-urlencoded; charset=utf-8"
		}).done(function(ret) {
			$("#custInfoBody").html(generateRows(ret));
			$('#custInfoBody tr').click(function(){
				$('#custInfoBody tr').removeClass("selectedrow");
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
		array[j++]="<tr id='"+object[i][0]+"_"+object[i][1]+"_"+object[i][2]+"'>";
		array[j++]="<td>"+object[i][0]+"</td>";
		array[j++]="<td>"+object[i][1]+"</td>";
		array[j++]="<td>"+object[i][2]+"</td>";
		array[j++]="<td>"+object[i][3]+"</td>";
		array[j++]="<td>"+object[i][4]+"</td>";
		array[j++]="<td>"+object[i][5]+"</td>";
		array[j++]="<td>"+object[i][6]+"</td>";
		array[j++]="<td>"+object[i][7]+"</td>";
		array[j++]="</tr>";
	}
	return array.join("");
}
</script>
</html>