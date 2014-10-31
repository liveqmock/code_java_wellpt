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
<title>到款资料记账</title>
</head>
<body>
  <div class="div_body" style="">
    <div class="form_header" style="">
      <div class="form_title" style="">
        <h2>到款资料记账</h2>
      </div>
    </div>
    <form action="" id="acctInfoForm" class="dyform">
    	<table>
    		<tbody>
    			<tr>
					<td class="title" colspan="8">到账信息</td>
				</tr>
				<tr class="field">
					<td width="110px;" style="color:red">业务员:</td>
					<td id="zsname" title="双击修改业务员" width="110px;">${entity.zsname}</td>
					<td width="110px;">公司代码:</td>
					<td id="bukrs" width="110px;">${entity.bukrs}</td>
					<td width="110px;" style="color:red">客户编号:</td>
					<td id="kunnr" title="双击修改客户" width="110px;">${entity.kunnr}</td>
					<td width="110px;">客户简称:</td>
					<td id="sortl" width="110px;">${entity.sortl}</td>
				</tr>
				<tr class="field">
					<td width="110px;">凭证编号(流水号):</td>
					<td id="zbelnrNum">${entity.zbelnr}</td>
					<td width="110px;">到款主体:</td>
					<td id="recBukrs">${recBukrs}</td>
					<td width="110px;" style="color:red">国际收支申报单号:</td>
					<td id="zdoip" title="双击修改">${entity.zdoip}</td>
					<td width="110px;">收款日期:</td>
					<td>${entity.bldat}</td>
				</tr>
				<tr class="field">
					<td width="110px;" style="color:red">过账日期:</td>
					<td>
						<input type='text' style='text-align:left;' onfocus="WdatePicker({dateFmt:'yyyyMMdd'})" value='${entity.budat}' readonly="readonly" onchange="saveBudat(this)"/>
					</td>
					<td width="110px;">收款金额:</td>
					<td id="zcamount">${entity.zcamount}</td>
					<td width="110px;">收款币别:</td>
					<td id="waers">${entity.waers}</td>
					<td width="110px;">银行类科目:</td>
					<td>${entity.hkont}</td>
				</tr>
				<tr class="field">
					<td width="110px;">付款条件:</td>
					<td colspan="3">${condition}</td>
					<td width="110px;">预收状态:</td>
					<td>${entity.zdrs}
						<c:if test="${!empty entity.zdrs && 'P' eq entity.zdrs}">- 未分解</c:if>
						<c:if test="${!empty entity.zdrs && 'F' eq entity.zdrs}">- 已分解</c:if>
						<c:if test="${!empty entity.zdrs && 'C' eq entity.zdrs}">- 已确认</c:if>
					</td>
					<td width="110px;">流转状态:</td>
					<td>${entity.zcirs}
						<c:if test="${!empty entity.zcirs && 'P' eq entity.zcirs}">- 维护</c:if>
						<c:if test="${!empty entity.zcirs && 'F' eq entity.zcirs}">- 确认</c:if>
						<c:if test="${!empty entity.zcirs && 'C' eq entity.zcirs}">- 生成凭证</c:if>
					</td>
				</tr>
				<tr class="field">
					<td width="110px;">摘要:</td>
					<td colspan="7">${entity.sgtxt}</td>
				</tr>
				<tr class="field">
					<td width="110px;">其他:</td>
					<td colspan="5"><input type="text" name="ztext" id="ztext" style="border:0;width:100%;height:90%;" value="${entity.ztext}"/></td>
					<td colspan="2">
						<div class="form_operate">
							<button id="B_saveText" type="butoon" onclick="return false;">保存</button>
							<button id="B_reject" type="butoon" onclick="return false;">退回</button>
							<button id="B_confirm" type="butoon" onclick="return false;">记账</button>
						</div>
					</td>
				</tr>
    		</tbody>
    	</table>
    	<br/>
    	<table>
    		<tbody>
    			<tr>
					<td class="title" colspan="2" style="border-right: 0;" colspan="7">手工记账</td>
					<td class="title" style="border-left:0;" colspan="4">
						<div class="form_operate" style="float: right;margin-top: 5px;">
							<button id="B_manualConfirm" type="butoon" onclick="return false;">记账</button>
						</div>
					</td>
				</tr>
				<tr class="ui-jqgrid-labels">
					<td width="100px;">手工凭证1:</td>
					<td width="150px;"><input type="text" name="manualNum" id="manualNum"/></td>
					<td width="80px;">凭证2:</td>
					<td width="150px;"><input type="text" name="manualNum2" id="manualNum2"/></td>
					<td width="80px;">凭证3:</td>
					<td width="150px;"><input type="text" name="manualNum3" id="manualNum3"/></td>
				</tr>
    		</tbody>
    	</table>
    	<br/>
    	<div style="width:100%;overflow: auto;">
	    	<table style="width:2000px;">
				<thead>
					<tr>
						<td class="title" style="border-right: 0;" colspan="21">预收款分解</td>
					</tr>
					<tr>
						<th width="150px" class="ui-state-default ui-th-column ui-th-ltr">合同号</th>
						<th width="100px" class="ui-state-default ui-th-column ui-th-ltr">外向交货单</th>
						<th width="100px" class="ui-state-default ui-th-column ui-th-ltr">客户简称</th>
						<th width="60px" class="ui-state-default ui-th-column ui-th-ltr">公司代码</th>
						<th width="80px" class="ui-state-default ui-th-column ui-th-ltr">业务类型</th>
						<th width="60px" class="ui-state-default ui-th-column ui-th-ltr">样品款类型</th>
						<th width="100px" class="ui-state-default ui-th-column ui-th-ltr">应收金额</th>
						<th width="100px" class="ui-state-default ui-th-column ui-th-ltr">未清金额</th>
						<th width="100px" class="ui-state-default ui-th-column ui-th-ltr">本次收款金额</th>
						<th width="80px" class="ui-state-default ui-th-column ui-th-ltr">手续费</th>
						<th width="80px" class="ui-state-default ui-th-column ui-th-ltr">预计出口日期</th>
						<th width="100px" class="ui-state-default ui-th-column ui-th-ltr">合同金额</th>
						<th width="60px" class="ui-state-default ui-th-column ui-th-ltr">合同币种</th>
						<th width="80px" class="ui-state-default ui-th-column ui-th-ltr">记账汇率</th>
						<th width="100px" class="ui-state-default ui-th-column ui-th-ltr" style="color:red">备注</th>
						<th width="100px" class="ui-state-default ui-th-column ui-th-ltr" style="color:red">特殊总账</th>
						<th width="80px" class="ui-state-default ui-th-column ui-th-ltr">产品组</th>
						<th width="100px" class="ui-state-default ui-th-column ui-th-ltr">凭证号</th>
						<th width="100px" class="ui-state-default ui-th-column ui-th-ltr">凭证号2</th>
						<th width="100px" class="ui-state-default ui-th-column ui-th-ltr">凭证号3</th>
						<th width="100px" class="ui-state-default ui-th-column ui-th-ltr">冲销凭证</th>
					</tr>
				</thead>
	    		<tbody id="acctBody">
	    			<c:forEach var="sep" items="${sepList}">
	    				<tr id="${sep.zuonr}" class="${sep.zposnr}">
	    					<td style='white-space:nowrap;'>${sep.bstkd}</td>
							<td style='white-space:nowrap;'>${sep.vbeln}</td>
							<td style='white-space:nowrap;'>${sep.sortl}</td>
							<td style='white-space:nowrap;'>${sep.bukrs}</td>
							<td>
								<c:if test="${'A' eq sep.zrbl}">预收</c:if>
								<c:if test="${'B' eq sep.zrbl}">应收</c:if>
								<c:if test="${'C' eq sep.zrbl}">其他应收</c:if>
								<c:if test="${'D' eq sep.zrbl}">样品款</c:if>
								<c:if test="${'E' eq sep.zrbl}">其它</c:if>
							</td>
							<td>
								<c:if test="${'1' eq sep.zsmc}">报关</c:if>
								<c:if test="${'2' eq sep.zsmc}">不报关</c:if>
								<c:if test="${'3' eq sep.zsmc}">内销</c:if>
							</td>
							<td style='text-align:right;'>0</td>
							<td style='text-align:right;'>0</td>
							<td><input type='text' style='text-align:right;' value='${sep.zcamount}'/></td>
							<td><input type='text' style='text-align:right;' value='${sep.zhc}'/></td>
							<td style='white-space:nowrap;'>${sep.zpodat}</td>
							<td style='text-align:right;'>${sep.zbamt}</td>
							<td style='white-space:nowrap;'>${sep.waers}</td>
							<td style='text-align:right;'>${sep.kursf}</td>
							<td><input type='text' style='text-align:left;' onblur="autoSave(this,'zanote','${sep.zbelnr}','${sep.zposnr}')" value='${sep.zanote}'/></td>
							<td><input type='text' style='text-align:left;' onblur="autoSave(this,'zanote','${sep.zbelnr}','${sep.zposnr}')" value='${sep.umskz}'/></td>
							<td>
								<c:if test="${'D' eq sep.zrbl}">
									<select style="width: 100%;border: 0;padding: 0;margin: 0;" value="${sep.spart}" onchange="autoSave(this,'spart','${(sep.zbelnr)!}','${(sep.zposnr)!}')">
										<option value=''></option>
										<option value='00' <c:if test="${'00' eq sep.spart}">selected</c:if>>00-通用</option>
										<option value='10' <c:if test="${'10' eq sep.spart}">selected</c:if>>10-CFL</option>
										<option value='20' <c:if test="${'20' eq sep.spart}">selected</c:if>>20-LED</option>
									</select>
								</c:if>
							</td>
							<td width="100px">
								<a href="#" onclick="viewBelnr('${sep.belnr}','${recBukrs}','${entity.budat}')">
									${sep.belnr}
								</a>
							</td>
							<td width="100px">
								<a href="#" onclick="viewBelnr('${sep.belnr2}','${recBukrs}','${entity.budat}')">
									${sep.belnr2}
								</a>
							</td>
							<td width="100px">
								<a href="#" onclick="viewBelnr('${sep.belnr3}','${sep.bukrs}','${entity.budat}')">
									${sep.belnr3}
								</a>
							</td>
							<td width="100px">
								<a href="#" onclick="viewBelnr('${sep.stblg}','${entity.bukrs}','${sep.zwodat}')">
									${sep.stblg}
								</a>
							</td>
	    				</tr>
	    			</c:forEach>
	    		</tbody>
	    	</table>
    	</div>
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
	var div_body_width = $(window).width() * 0.76;
	$(".form_header").css("width",div_body_width - 5);
	$(".body").css("width",div_body_width);
	//进入页面时提示备注信息
	var noteTip = "${noteTip}";
	if(noteTip!=""){
		oAlert(noteTip);
	}
	//保存按钮事件
	$("#B_saveText").click(saveText);
	//退回按钮事件
	$("#B_reject").click(rejectAcctEdit);
	//自动记账按钮事件
	$("#B_confirm").click(confirmAcctEdit);
	//手工记账按钮事件
	$("#B_manualConfirm").click(confirmManual);
	//国际收支申报单双击事件
	$("#zdoip").dblclick(editDoip);
	//客户编号双击事件
	$("#kunnr").dblclick(openCustChooseWin);
	//业务员双击事件
	$("#zsname").dblclick(openUserChooseWin);
});

/**
 * 退回
 */
function rejectAcctEdit(){
	oConfirm("确认将当前流程退回?", function() {
		var params = {'flowNum':$('#zbelnrNum').text(),'from':'cw'};
		$.post("reject",params).done(function(ret) {
			if("success"==ret){
				refresh();
			}else{
				oAlert(ret);
			}
		});
	});
}

/**
 * 自动生成凭证
 */
function confirmAcctEdit(){
	var params = {'flowNum':$('#zbelnrNum').text(),'from':'cw'};
	$.post("generateCertAll",params).done(function(ret) {
		if("success"==ret){
			refresh();
		}else{
			oAlert(ret);
		}
	});
}

/**
 * 手工生成凭证
 */
function confirmManual(){
	var params = {
		'manualNum':$('#manualNum').val(),
		'manualNum2':$('#manualNum2').val(),
		'manualNum3':$('#manualNum3').val(),
		'flowNum':$('#zbelnrNum').text()
	}
	if(params.manualNum==""){
		oAlert("请录入手工凭证编号");
		return;
	}
	if(params.manualNum2!=""||params.manualNum3!=""){
		if(params.manualNum2==""||params.manualNum3==""){
			oAlert("手工凭证2和手工凭证3需同时录入");
			return;
		}
	}
	oConfirm("确认手工生成凭证?", function() {
		$.ajax({
			url : "manualCert",
			type : "post",
			data : params ,
			dataType : "json",
			contentType : "application/x-www-form-urlencoded; charset=utf-8"
		}).done(function(ret) {
			if("success"==ret[0]){
				oAlert("生成凭证成功");
				refresh();
			}else{
				oAlert("生成凭证失败"+ret[1]);
			}
		});
	});
}
 
/**
 * 编辑国际收支申报单号
 */
function editDoip(){
	var zdoip = $("#zdoip").text();
	var input = "<input type='text' id='zdoipEdit' value='"+zdoip+"' onblur='saveDoip(this)'/>";
	$('#zdoip').html(input);
}

/**
 * 保存国际收支申报单号
 * @param zdoip
 */
function saveDoip(obj){
	var params = {
		'type':'zdoip',
		'value':obj.value,
		'zbelnr':$('#zbelnrNum').text()
	};
	saveAcct(params,saveDoipCallBack);
}

function saveDoipCallBack(){
	$('#zdoip').text($('#zdoipEdit').val());
}

/**
 * 修改过账日期
 * @param obj
 */
function saveBudat(obj){
	var params = {
		'type':'budat',
		'value':obj.value,
		'zbelnr':$('#zbelnrNum').text()
	};
	saveAcct(params);
}

/**
 * 刷新页面
 */
function refresh(){
	window.location.href=ctx+"/ficoManage/ficoRoute?from=cw&flowNum="+$('#zbelnrNum').text();
}

/**
 * 弹出客户编号选择窗口
 * @param bukrs
 */
function popCustSearch() {
	var bukrs = $("#bukrs").text();
	var recBukrs = $("#recBukrs").text();
	var action = ctx + "/ficomanage/custChoose?bukrs="+bukrs+"&modify=false&recBukrs="+recBukrs;
	$popWin(action, {
		width : 800,
		height : 530
	});
}

/**
 *	保存备注信息
 */
function saveText(){
	var ztext = $('#ztext').val();
	var params = {
		'type':'ztext',
		'value':ztext,
		'zbelnr':$('#zbelnrNum').text()
	};
	saveAcct(params);
}

function saveAcct(params,callback){
	$.ajax({
		url : "saveAcctCw",
		type : "post",
		data : params ,
		dataType : "json",
		contentType : "application/x-www-form-urlencoded; charset=utf-8"
	}).done(function(ret) {
		if("success"==ret[0]){
			if(callback){
				oAlert2 ("保存成功",callback,"");
			}else{
				oAlert("保存成功!");
			}
		}else{
			oAlert("保存失败:"+ret[1]);
		}
	});
}


/**
 * 总账查询
 */
function viewBelnr(belnr,bukrs,data){
	var url = ctx +"/ficoManage/belnrSearch?belnr="+belnr+"&bukrs="+bukrs+"&gjahr="+data.substring(0,4);
	window.open(url,"总账查询");
}

/**
 * 打开客户选择窗口
 */
function openCustChooseWin(){
	var url = ctx+"/ficoManage/custSearch?bukrs="+$('#bukrs').text()+"&recBukrs"+$('#recBukrs').text();
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
					updateKunnr(arr[0],arr[1],arr[2]);
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
function updateKunnr(bukrs,kunnr,sortl){
	oConfirm("确定将当前客户修改为"+sortl+"?", function() {
		var params={
			'bukrs':bukrs,
			'kunnr':kunnr,
			'flowNum':$('#zbelnrNum').text()
		};
		$.post("updateKunnr",params).done(function(ret) {
			refresh();
		});
	});
}

/**
 * 打开用户选择窗口
 */
function openUserChooseWin(){
	var url = ctx+"/ficoManage/userSearch?bukrs="+$('#bukrs').text()+"&kunnr="+$('#kunnr').text();
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
					updateZsname(arr[0],arr[1]);
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
function updateZsname(id,name){
	oConfirm("确定将当前到款流程移交给"+name+"?", function() {
		var params = {
			'type':'zsname',
			'value':id,
			'zbelnr':$('#zbelnrNum').text()
		};
		$.ajax({
			url : "saveAcctCw",
			type : "post",
			data : params ,
			dataType : "json",
			contentType : "application/x-www-form-urlencoded; charset=utf-8"
		}).done(function(ret) {
			if("success"==ret[0]){
				oAlert("移交成功!");
				refresh();
			}else{
				oAlert("移交失败:"+ret[1]);
			}
		});
	});
}
</script>
</html>