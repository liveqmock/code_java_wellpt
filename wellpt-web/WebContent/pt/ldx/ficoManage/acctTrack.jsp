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
<title>到款资料跟踪</title>
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
        <h2>到款资料跟踪</h2>
      </div>
    </div>
    <form action="${ctx}/ficoManage/acctTrack" id="receiveAcctForm" class="dyform">
   		<input name="from" id="from" value="${from}" type="hidden">
   		<input name="bukrsTemp" id="bukrsTemp" type="hidden">
    	<table>
    		<tbody id="condBody">
    			<tr>
					<td class="title" colspan="10">到款资料跟踪</td>
				</tr>
				<tr class="field">
					<td width="90px;">业务员:</td>
					<td width="110px;"><input type="text" name="zsname" id="zsname" style="border:0;width:100%;height:90%;" value="${zsname}"/></td>
					<td width="90px;">公司代码:</td>
					<td width="90px;">
						<select name="bukrs" id="bukrs" style='width: 100%;border: 0;padding: 0;margin: 0;' value='${bukrs}'>
							<option value='' <c:if test="${'' eq bukrs}">selected</c:if>></option>
							<option value='1000' <c:if test="${'1000' eq bukrs}">selected</c:if>>1000</option>
							<option value='7000' <c:if test="${'7000' eq bukrs}">selected</c:if>>7000</option>
							<option value='7200' <c:if test="${'7200' eq bukrs}">selected</c:if>>7200</option>
							<option value='7300' <c:if test="${'7300' eq bukrs}">selected</c:if>>7300</option>
						</select>
					</td>
					<td width="110px;">凭证编号(流水号):</td>
					<td width="110px;"><input type="text" name="zbelnr" id="zbelnr" style="border:0;width:100%;height:90%;" value="${zbelnr}"/></td>
					<td width="90px;">客户编号:</td>
					<td width="100px;"><input type="text" name="kunnr" id="kunnr" style="border:0;width:100%;height:90%;" value="${kunnr}"/></td>
					<td width="90px;">客户简称:</td>
					<td width="100px;"><input type="text" name="sortl" id="sortl" style="border:0;width:100%;height:90%;" value="${sortl}"/></td>
				</tr>
				<tr class="field">
					<td width="90px;">提醒状态:</td>
					<td width="90px;">
						<select name="zremind" id="zremind" style='width: 100%;border: 0;padding: 0;margin: 0;' value='${zremind}'>
							<option value='' <c:if test="${empty zremind}">selected</c:if>></option>
							<option value='Y' <c:if test="${'Y' eq zremind}">selected</c:if>>Y</option>
						</select>
					</td>
					<td width="90px;">预收状态:</td>
					<td width="90px;">
						<select name="zdrs" id="zdrs" style='width: 100%;border: 0;padding: 0;margin: 0;' value='${zdrs}'>
							<option value='' <c:if test="${empty zdrs}">selected</c:if>></option>
							<option value='P' <c:if test="${'P' eq zdrs}">selected</c:if>>P</option>
							<option value='F' <c:if test="${'F' eq zdrs}">selected</c:if>>F</option>
							<option value='C' <c:if test="${'C' eq zdrs}">selected</c:if>>C</option>
						</select>
					</td>
					<td width="90px;">流转状态:</td>
					<td width="90px;">
						<select name="zcirs" id="zcirs" style='width: 100%;border: 0;padding: 0;margin: 0;' value='${zcirs}'>
							<option value='' <c:if test="${empty zcirs}">selected</c:if>></option>
							<option value='P' <c:if test="${'P' eq zcirs}">selected</c:if>>P</option>
							<option value='F' <c:if test="${'F' eq zcirs}">selected</c:if>>F</option>
							<option value='C' <c:if test="${'C' eq zcirs}">selected</c:if>>C</option>
						</select>
					</td>
					<td width="90px;">FC状态查询:</td>
					<td width="90px;">
						<select name="sepStatus" id="sepStatus" style='width: 100%;border: 0;padding: 0;margin: 0;' value='${sepStatus}'>
							<option value='' <c:if test="${empty sepStatus}">selected</c:if>></option>
							<option value='FC' <c:if test="${'FC' eq sepStatus}">selected</c:if>>FC</option>
						</select>
					</td>
					
					<td colspan="2"> 
						<div class="form_operate">
				          <button id="B_search" type="butoon" onclick="goToPage(1)">查询</button>
				          <button id="B_send" type="butoon" onclick="return false;">发邮件</button>
				          <button id="B_remind" type="butoon" onclick="return false;">提醒</button>
				        </div>
			        </td>
				</tr>
				<tr class="field">
					<td colspan="2">邮件抄送(多个邮件用,分隔):</td>
					<td colspan="8"><input type="text" name="mailcc" id="mailcc" style="border:0;width:100%;height:90%;"/></td>
				</tr>
    		</tbody>
    	</table>
    	<div style="width:100%;overflow: auto;">
	   	<table style="margin-top: 4px;width:2300px;">
	   		<thead>
	   			<tr>
					<td class="title" colspan="19">到款资料列表</td>
				</tr>
	   			<tr class="ui-jqgrid-labels">
					<th width="30px" class="ui-state-default ui-th-column ui-th-ltr">选择</th>
					<th width="70px" class="ui-state-default ui-th-column ui-th-ltr" style="color:red">业务员</th>
					<th width="120px" class="ui-state-default ui-th-column ui-th-ltr">Email</th>
					<th width="60px" class="ui-state-default ui-th-column ui-th-ltr">提醒</th>
					<th width="120px" class="ui-state-default ui-th-column ui-th-ltr">最早通知时间</th>
					<th width="120px" class="ui-state-default ui-th-column ui-th-ltr">回复时间</th>
					<th width="110px" class="ui-state-default ui-th-column ui-th-ltr">凭证编号(流水号)</th>
					<th width="100px" class="ui-state-default ui-th-column ui-th-ltr">国际收支申报单号</th>
					<th width="60px" class="ui-state-default ui-th-column ui-th-ltr">公司代码</th>
					<th width="120px" class="ui-state-default ui-th-column ui-th-ltr" style="color:red">客户编号</th>
					<th width="90px" class="ui-state-default ui-th-column ui-th-ltr">客户简称</th>
					<th width="90px" class="ui-state-default ui-th-column ui-th-ltr">收款日期</th>
					<th width="100px" class="ui-state-default ui-th-column ui-th-ltr">收款金额</th>
					<th width="60px" class="ui-state-default ui-th-column ui-th-ltr">收款币别</th>
					<th width="120px" class="ui-state-default ui-th-column ui-th-ltr">业务对象（摘要）</th>
					<th width="110px" class="ui-state-default ui-th-column ui-th-ltr">银行类科目</th>
					<th width="70px" class="ui-state-default ui-th-column ui-th-ltr">预收状态</th>
					<th width="70px" class="ui-state-default ui-th-column ui-th-ltr">流转状态</th>
					<th width="160px" class="ui-state-default ui-th-column ui-th-ltr">操作</th>
				</tr>
	   		</thead>
	   		<tbody id="listBody">
	   			<c:forEach var="entity" items="${list}">
 					<tr id="tr_${entity.zbelnr}" class="${entity.zbelnr}">
 						<td style="width:23px;"><input type="checkbox" id="${entity.zbelnr}" style="width:15px;"/></td>
						<td style='white-space:nowrap;' ondblclick="openUserChooseWin('${entity.zbelnr}','${entity.bukrs}','${entity.kunnr}')" 
							title="双击修改业务员" id="${entity.zbelnr}" class="zsname">
							${entity.zsname}
						</td>
						<td style='white-space:nowrap;' id="${entity.zbelnr}" class="mail">${entity.mail}</td>
						<td style='white-space:nowrap;'>${entity.zremind}</td>
						<td style='white-space:nowrap;'>${entity.zetime}</td>
						<td style='white-space:nowrap;'>${entity.zrdate}</td>
						<td style='white-space:nowrap;'>
							<a href="${ctx}/ficoManage/ficoRoute?flowNum=${entity.zbelnr}&from=cw" target="到账信息">
							${entity.zbelnr}
							</a>
						</td>
						<td style='white-space:nowrap;'>${entity.zdoip}</td>
						<td style='white-space:nowrap;' id="${entity.zbelnr}" class="bukrs">${entity.bukrs}</td>
						<td title="双击修改客户编号" ondblclick="openCustChooseWin('${entity.zbelnr}','${entity.bukrs}','${entity.kunnr}')"
							id="${entity.zbelnr}" class="kunnr">
							${entity.kunnr}
						</td>
						<td style='white-space:nowrap;' id="${entity.zbelnr}" class="sortl">${entity.sortl}</td>
						<td style="text-align: right;">${entity.budat}</td>
						<td style="text-align: right;">${entity.zcamount}</td>
						<td>${entity.waers}</td>
						<td style='white-space:nowrap;'>${entity.sgtxt}</td>
						<td>${entity.hkont}</td>
						<td style='white-space:nowrap;'>${entity.zdrs}
							<c:if test="${'P' eq entity.zdrs}">- 未分解</c:if>
							<c:if test="${'F' eq entity.zdrs}">- 已分解</c:if>
							<c:if test="${'C' eq entity.zdrs}">- 已确认</c:if>
						</td>
						<td style='white-space:nowrap;'>${entity.zcirs}
							<c:if test="${'P' eq entity.zcirs}">- 维护</c:if>
							<c:if test="${'F' eq entity.zcirs}">- 确认</c:if>
							<c:if test="${'C' eq entity.zcirs}">- 生成凭证</c:if>
						</td>
						<td style='white-space:nowrap;'>
							<c:if test="${'C' eq entity.zcirs}">
							&nbsp;<a href="${ctx}/ficoManage/acctBackFlush?flowNum=${entity.zbelnr}" target="回冲">回冲到账信息</a>
							</c:if>
							<c:if test="${'P' eq entity.zcirs && 'P' eq entity.zdrs}">
								&nbsp;<a href='#' onclick="deleteAcct('${entity.zbelnr}')">删除</a>
							</c:if>
						</td>
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
	var div_body_width = $(window).width() * 0.95;
	$(".form_header").css("width",div_body_width - 5);
	$(".div_body").css("width",div_body_width);
	$("#B_send").click(sendEmail);
	$("#B_remind").click(matchRemind);
});

/**
 * 发送邮件
 */
function sendEmail(){
	if($("#listBody input:checked").length<1){
		oAlert("请选择需要发送的到账信息!");
		return;
	}
	var zbelnrs="";
	var error="";
	$("#listBody input:checked").each(function() {
		zbelnrs+=this.id+",";
		console.dir($("#"+this.id+".zsname").text());
		console.dir($("#"+this.id+".mail").text());
		if($("#"+this.id+".zsname").text()==""||$("#"+this.id+".mail").text()==""){
			error += this.id+",";
		}
	});
	if(error!=""){
		oAlert("凭证号:"+error+" 没有对应的业务员或者没有Email,请手工维护!");
		return;
	}
	$.ajax({
		url : "sendEmail",
		type : "post",
		data : {
			'zbelnrs':zbelnrs,
			'emailcc':$("#mailcc").val()
		} ,
		dataType : "json",
		contentType : "application/x-www-form-urlencoded; charset=utf-8"
	}).done(function(ret) {
		if("success"==ret[0]){
			oAlert("发送成功");
		}else{
			oAlert("发送失败"+ret[1]);
		}
	});
}

/**
 * 删除到账信息
 */
function deleteAcct(zbelnr){
	if(!confirm("确认删除当前到账信息?"))
		return;
	$.get("deleteReceive",{
		'zbelnr':zbelnr
	}).done(function(ret) {
		if("success"==ret){
			oAlert("删除成功");
			$("#B_search").click();
		}else{
			oAlert("删除失败："+ret);
		}
	});
}

/**
 * 修改业务员
 */
function openUserChooseWin(zbelnr,bukrs,kunnr){
	var url = ctx+"/ficoManage/userSearch?bukrs="+bukrs+"&kunnr="+kunnr;
	$.ajax({
		async : false,
		url : url,
		success : function(data) {
			var json = new Object();
			json.content = data;
			json.title = "选择用户";
			json.height = 400;
			json.width = 900;
			json.buttons = {
				"选择" : function sure() {
					var id = $("#userInfoBody .selectedrow").attr("id");
					var arr = id.split("_");
					updateZsname(zbelnr,arr[0],arr[1]);
					$(this).dialog("close");
				}
			};
			json.close = function() {
				$(this).dialog("close");
			};
			showDialog(json);
		}
	});
}

/**
 * 用户选择回调函数
 */
function updateZsname(zbelnr,userCode,name){
	oConfirm("确定将当前到款流程移交给"+name+"?", function() {
		var params = {
			'type':'zsname',
			'value':userCode,
			'zbelnr':zbelnr
		};
		$.ajax({
			url : "saveAcctCw",
			type : "post",
			data : params ,
			dataType : "json",
			contentType : "application/x-www-form-urlencoded; charset=utf-8"
		}).done(function(ret) {
			if("success"==ret[0]){
				oAlert("转交成功!");
				$("#"+zbelnr+".zsname").text(name);
			}else{
				oAlert("转交失败:"+ret[1]);
			}
		});
	});
}

/**
 * 修改客户编号
 */
function openCustChooseWin(zbelnr,bukrs,kunnr){
	var url = ctx+"/ficoManage/custSearch?bukrs="+bukrs;
	$.ajax({
		async : false,
		url : url,
		success : function(data) {
			var json = new Object();
			json.content = data;
			json.title = "选择客户";
			json.height = 600;
			json.width = 900;
			json.buttons = {
				"选择" : function sure() {
					var id = $("#custInfoBody .selectedrow").attr("id");
					var arr = id.split("_");
					updateKunnr(zbelnr,arr[0],arr[1],arr[2]);
					$(this).dialog("close");
				}
			};
			json.close = function() {
				$(this).dialog("close");
			};
			showDialog(json);
		}
	});
}

/**
 * 修改客户编号
 */
function updateKunnr(zbelnr,bukrs,kunnr,sortl){
	oConfirm("确定将当前客户修改为"+sortl+"?", function() {
		var params={
			'bukrs':bukrs,
			'kunnr':kunnr,
			'flowNum':zbelnr,
			'from':'cw'
		};
		$.post("updateKunnr",params).done(function(ret) {
			$("#"+zbelnr+".bukrs").text(bukrs);
			$("#"+zbelnr+".kunnr").text(kunnr);
			$("#"+zbelnr+".sortl").text(sortl);
		});
	});
}

/**
 * 提醒
 */
function matchRemind(){
	$.ajax({
		url : "remind",
		type : "get",
		data : {} ,
		dataType : "json",
		contentType : "application/x-www-form-urlencoded; charset=utf-8"
	}).done(function(ret) {
		if("success"==ret[0]){
			oAlert("提醒成功!");
			$("#condBody input,select").val("");//清空查询条件
			$("#sepStatus").val("FC");//查询FC状态
			$("#B_search").click();
		}else{
			oAlert("提醒失败："+ret[1]);
		}
	});
}
</script>
</html>