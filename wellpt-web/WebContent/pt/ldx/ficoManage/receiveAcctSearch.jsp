<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<%@ include file="/pt/dyform/dyform_css.jsp"%>
<%@ include file="/pt/dyform/dyform_js.jsp"%>
<%@ include file="/pt/dyform/dyform_ctljs.jsp"%>
<script type="text/javascript" src="${ctx}/resources/pt/js/ldx/ldxPage.js"></script>
<title>应收款查询</title>
<style type="text/css">
.smartBtn{
	background: url("../../resources/pt/images/v1_icon.png") no-repeat scroll 0 -165px transparent;
	border: 1px solid #dee1e2;
	color: #0f599c;
	display: block;
	float: left;
	font-size: 12px;
	height: 22px;
	width:36px;
	margin: 0 3px;
	padding: 0 5px;
}
</style>
</head>
<body>
  <div class="div_body" style="">
    <div class="form_header" style="">
      <div class="form_title" style="">
        <h2>应收款查询</h2>
      </div>
      <div id="toolbar" class="form_toolbar">
       
      </div>
    </div>
    <form action="${ctx}/ficoManage/receiveAcct" id="receiveAcctForm" class="dyform">
   		<input name="from" id="from" value="${from}" type="hidden">
   		<input name="bukrsTemp" id="bukrsTemp" type="hidden">
    	<table>
    		<tbody>
    			<tr>
					<td class="title" colspan="9">应收款查询</td>
				</tr>
				<tr class="field">
					<td width="90px;">*客户编号:</td>
					<td width="110px;"><input type="text" name="kunnr" id="kunnr" style="border:0;width:100%;height:90%;" value="${kunnr}"/></td>
					<td width="90px;">*公司代码:</td>
					<td width="110px;">
						<select name="bukrs" id="bukrs" style='width: 100%;border: 0;padding: 0;margin: 0;' value='${bukrs}'>
							<option value='1000' <c:if test="${'1000' eq bukrs}">selected</c:if>>1000</option>
							<option value='7000' <c:if test="${'7000' eq bukrs}">selected</c:if>>7000</option>
							<option value='7200' <c:if test="${'7200' eq bukrs}">selected</c:if>>7200</option>
							<option value='7300' <c:if test="${'7300' eq bukrs}">selected</c:if>>7300</option>
						</select>
					</td>
					<td width="90px;">合同号:</td>
					<td width="110px;"><input type="text" name="xblnr" id="xblnr" style="border:0;width:100%;height:90%;" value="${xblnr}"/></td>
					<td width="90px;">外向交货单:</td>
					<td colspan="2" width="180px;"><input type="text" name="xref1" id="xref1" style="border:0;width:100%;height:90%;" value="${xref1}"/></td>
				</tr>
				<tr class="field">
					<td width="90px;">预收未清合计:</td>
					<td>${yswqSum}</td>
					<td width="90px;">应收金额合计:</td>
					<td>${ysjeSum}</td>
					<td width="90px;">未清金额合计:</td>
					<td>${wqjeSum}</td>
					<td width="90px;">查询已结清:</td>
					<td><input type="checkBox" id="clear" name="clear" style="width:15px;height:13px;float:right;top:2px;" title="勾选同时查询已清项"/></td>
					<td> 
						<div class="form_operate">
				          <button id="form_search" type="butoon" onclick="goToPage(1)">查询</button>
				        </div>
			        </td>
				</tr>
    		</tbody>
    	</table>
	   	<table style="margin-top: 4px;">
	   		<thead>
	   			<tr>
					<td class="title" colspan="7">应收款信息</td>
				</tr>
	   			<tr class="ui-jqgrid-labels">
					<th width="110px" class="ui-state-default ui-th-column ui-th-ltr">发票号</th>
					<th width="120px" class="ui-state-default ui-th-column ui-th-ltr">合同号</th>
					<th width="120px" class="ui-state-default ui-th-column ui-th-ltr">外向交货单</th>
					<th width="100px" class="ui-state-default ui-th-column ui-th-ltr">预收未清</th>
					<th width="100px" class="ui-state-default ui-th-column ui-th-ltr">未清金额</th>
					<th width="60px" class="ui-state-default ui-th-column ui-th-ltr">币种</th>
					<th width="70px" class="ui-state-default ui-th-column ui-th-ltr">业务类型</th>
				</tr>
	   		</thead>
	   		<tbody>
	   			<c:forEach var="entity" items="${recList}">
	   				<c:choose>
	   					<c:when test="${'0' eq entity[6]}">
	   						<tr ondblclick="updateParent('${entity[1]}','${entity[2]}')">
	   							<td>${entity[0]}</td>
								<td>${entity[1]}</td>
								<td>${entity[2]}</td>
								<td style='text-align:right;'>${entity[3]}</td>
								<td style='text-align:right;'>${entity[5]}</td>
								<td>${entity[7]}</td>
								<td>应收</td>
	   						</tr>
	   					</c:when>
	   					<c:otherwise>
	   						<tr>
	   							<td style="background-color:#F19C9E;">${entity[0]}</td>
								<td style="background-color:#F19C9E;">${entity[1]}</td>
								<td style="background-color:#F19C9E;">${entity[2]}</td>
								<td style='text-align:right;background-color:#F19C9E;'>${entity[3]}</td>
								<td style='text-align:right;background-color:#F19C9E;'>${entity[5]}(已清)</td>
								<td style="background-color:#F19C9E;">${entity[7]}</td>
								<td style="background-color:#F19C9E;">应收(已清)</td>
	   						</tr>
	   					</c:otherwise>
	   				</c:choose>
	   			</c:forEach>
	   		</tbody>
	   	</table>
	   	<div style="width:100%;float:right;height:100%;">
			<div style="float:left;height: 100%;">
				<label id="paginationInfo" style="font-size:12px;">
				第${pagingInfo.currentPage}页,共${pagingInfo.totalPages}页,每页${pagingInfo.pageSize}条,共${pagingInfo.totalCount}条 
				</label>
			</div>
			<input name="currentPage" id="currentPage" value="${pagingInfo.currentPage}" type="hidden">
	   		<input name="totalPages" id="totalPage" value="${pagingInfo.totalPages}" type="hidden">
			<div style="float:right;height: 100%;">
				<span align="right" id="wwctrl_B_Previous">
					<button type="button" class="smartBtn" id="B_First" style="width:60px;" onclick="goToPage(1)">首页</button>
					<button type="button" class="smartBtn" id="B_Previous" style="width:60px;" onclick="addPage(-1)">上一页</button>
				</span>
				<span align="right" id="wwctrl_B_Next">
					<button type="button" class="smartBtn" id="B_Next" style="width:60px;" onclick="addPage(1)">下一页</button>
					<button type="button" class="smartBtn" id="B_Last" style="width:60px;" onclick="goToPage(${pagingInfo.totalPages})">末页</button>
				</span>
			</div>
		</div>
		<div style="width:100%;overflow: auto;">
	   	<table style="margin-top: 4px;width:1600px;">
	   		<thead>
	   			<tr>
					<td class="title" colspan="14">预收款信息</td>
				</tr>
	   			<tr class="ui-jqgrid-labels">
					<th width="110px" class="ui-state-default ui-th-column ui-th-ltr">流水号</th>
					<th width="100px" class="ui-state-default ui-th-column ui-th-ltr">发票号</th>
					<th width="100px" class="ui-state-default ui-th-column ui-th-ltr">合同号</th>
					<th width="120px" class="ui-state-default ui-th-column ui-th-ltr">外向交货单</th>
					<th width="80px" class="ui-state-default ui-th-column ui-th-ltr">预收款金额</th>
					<th width="80px" class="ui-state-default ui-th-column ui-th-ltr">冲销金额</th>
					<th width="60px" class="ui-state-default ui-th-column ui-th-ltr">手续费</th>
					<th width="70px" class="ui-state-default ui-th-column ui-th-ltr">预计出口日期</th>
					<th width="80px" class="ui-state-default ui-th-column ui-th-ltr">合同金额</th>
					<th width="60px" class="ui-state-default ui-th-column ui-th-ltr">币种</th>
					<th width="40px" class="ui-state-default ui-th-column ui-th-ltr">清账汇率</th>
					<th width="80px" class="ui-state-default ui-th-column ui-th-ltr">快递单号</th>
					<th width="80px" class="ui-state-default ui-th-column ui-th-ltr">备注</th>
					<th width="120px" class="ui-state-default ui-th-column ui-th-ltr">凭证号</th>
				</tr>
	   		</thead>
	   		<tbody>
	   			<c:forEach var="receive" items="${preList}">
		   			<tr>
						<td>${receive[13]}</td>
						<td>${receive[12]}</td>
						<td>${receive[0]}</td>
						<td>${receive[1]}</td>
						<td style='text-align:right;'>${receive[2]}</td>
						<td style='text-align:right;'>${receive[3]}</td>
						<td style='text-align:right;'>${receive[4]}</td>
						<td>${receive[5]}</td>
						<td style='text-align:right;'>${receive[6]}</td>
						<td>${receive[7]}</td>
						<td style='text-align:right;'>${receive[8]}</td>
						<td>${receive[9]}</td>
						<td>${receive[10]}</td>
						<td>${receive[11]}</td>
					</tr>
	   			</c:forEach>
	   		</tbody>
	   	</table>
	   	</div>
    </form>
  </div>
</body>

<script type="text/javascript">
$(function(){
	var clear = "${clear}";
	if(clear!=""){
		$("#clear").attr("checked","checked");
	}else{
		$("#clear").removeAttr("checked");
	}
	if($('#from').val()=="edit"){
		$("#bukrs").attr("disabled",true);
		$("#bukrsTemp").val($("#bukrs").val());
		$("#bukrsTemp").attr("name","bukrs");
	}
	var div_body_width = $(window).width() * 0.76;
	$(".form_header").css("width",div_body_width - 5);
	$(".body").css("width",div_body_width);
});

function updateParent(xblnr,xref1){
	if(window.opener){
		window.opener.$('#xblnr').val(xblnr);
		window.opener.$('#xref1').val(xref1);
		window.opener.$('#sgtxt').val("");
		window.opener.$('#searchBtn').click();
		window.close();
	}
}

</script>
</html>