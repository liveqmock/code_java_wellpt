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
<title>客户未清款项金额汇总</title>
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
        <h2>客户未清款项查询</h2>
      </div>
      <div id="toolbar" class="form_toolbar">
       
      </div>
    </div>
    <form action="${ctx}/ficoManage/custAcctCompare" id="receiveAcctForm" class="dyform">
    	<table>
    		<tbody>
    			<tr>
					<td class="title" colspan="9">查询</td>
				</tr>
				<tr class="field">
					<td width="90px;">公司代码:</td>
					<td width="90px;">
						<select name="bukrs" id="bukrs" style='width: 100%;border: 0;padding: 0;margin: 0;' value='${bukrs}'>
							<option value='1000' <c:if test="${'1000' eq bukrs}">selected</c:if>>1000</option>
							<option value='7000' <c:if test="${'7000' eq bukrs}">selected</c:if>>7000</option>
							<option value='7200' <c:if test="${'7200' eq bukrs}">selected</c:if>>7200</option>
							<option value='7300' <c:if test="${'7300' eq bukrs}">selected</c:if>>7300</option>
						</select>
					</td>
					<td width="90px;">客户编号:</td>
					<td width="110px;"><input type="text" name="kunnr" id="kunnr" style="border:0;width:100%;height:90%;" value="${kunnr}"/></td>
					<td width="90px;">客户简称:</td>
					<td width="110px;"><input type="text" name="sortl" id="sortl" style="border:0;width:100%;height:90%;" value="${sortl}"/></td>
					<td width="90px;">提示:</td>
					<td width="80px;">
						<select name="tip" id="tip" style='width: 100%;border: 0;padding: 0;margin: 0;' value='${tip}'>
							<option value='' <c:if test="${empty tip}">selected</c:if>></option>
							<option value='1' <c:if test="${'1' eq tip}">selected</c:if>>1</option>
						</select>
					</td>
					<td width="80px;"> 
						<div class="form_operate">
				          <button id="form_search" type="butoon" onclick="goToPage(1)">查询</button>
				        </div>
			        </td>
				</tr>
    		</tbody>
    	</table>
    	<div style="width:100%;overflow: auto;">
	   	<table style="margin-top: 4px;width: 1200px;">
	   		<thead>
	   			<tr>
					<td class="title" colspan="13">客户未清款项</td>
				</tr>
	   			<tr class="ui-jqgrid-labels">
					<th width="80px" class="ui-state-default ui-th-column ui-th-ltr">公司代码</th>
					<th width="120px" class="ui-state-default ui-th-column ui-th-ltr">客户</th>
					<th width="100px" class="ui-state-default ui-th-column ui-th-ltr">客户简称</th>
					<th width="70px" class="ui-state-default ui-th-column ui-th-ltr">币种</th>
					<th width="70px" class="ui-state-default ui-th-column ui-th-ltr">RSM</th>
					<th width="70px" class="ui-state-default ui-th-column ui-th-ltr">AE</th>
					<th width="70px" class="ui-state-default ui-th-column ui-th-ltr">AA</th>
					<th width="70px" class="ui-state-default ui-th-column ui-th-ltr">OM</th>
					<th width="110px" class="ui-state-default ui-th-column ui-th-ltr">SAP预收未清</th>
					<th width="110px" class="ui-state-default ui-th-column ui-th-ltr">SAP应收未清</th>
					<th width="110px" class="ui-state-default ui-th-column ui-th-ltr">SAP其他未清</th>
					<th width="110px" class="ui-state-default ui-th-column ui-th-ltr">OA预收未清</th>
					<th width="70px" class="ui-state-default ui-th-column ui-th-ltr">提示</th>
				</tr>
	   		</thead>
	   		<tbody>
	   			<c:forEach var="entity" items="${list}">
 					<tr>
 						<td>${entity[0]}</td>
						<td>${entity[1]}</td>
						<td>${entity[2]}</td>
						<td>${entity[3]}</td>
						<td>${entity[4]}</td>
						<td>${entity[5]}</td>
						<td>${entity[6]}</td>
						<td>${entity[7]}</td>
						<td style='text-align:right;'>${entity[8]}</td>
						<td style='text-align:right;'>${entity[9]}</td>
						<td style='text-align:right;'>${entity[10]}</td>
						<td style='text-align:right;'>${entity[11]}</td>
						<td>${entity[12]}</td>
 					</tr>
	   			</c:forEach>
	   		</tbody>
	   	</table>
	   	</div>
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
		<table></table>
    </form>
  </div>
</body>

<script type="text/javascript">
$(function(){
	if($('#from').val()=="edit"){
		$("#bukrs").attr("disabled",true);
		$("#bukrsTemp").val($("#bukrs").val());
		$("#bukrsTemp").attr("name","bukrs");
	}
	var div_body_width = $(window).width() * 0.76;
	$(".form_header").css("width",div_body_width - 5);
	$(".body").css("width",div_body_width);
});

</script>
</html>