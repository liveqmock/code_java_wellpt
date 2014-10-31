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
<title>预收款冲销</title>
</head>
<body>
  <div class="div_body" style="">
    <div class="form_header" style="">
      <div class="form_title" style="">
        <h2>预收款冲销</h2>
      </div>
    </div>
    <form action="" id="acctInfoForm" class="dyform">
    	<table>
    		<tbody>
    			<tr>
					<td class="title" colspan="8">到账信息</td>
				</tr>
				<tr class="field">
					<td width="110px;">业务员:</td>
					<td width="110px;">${entity.zsname}</td>
					<td width="110px;">公司代码:</td>
					<td id="bukrs" width="110px;">${entity.bukrs}</td>
					<td width="110px;">客户编号:</td>
					<td id="kunnr" width="110px;">${entity.kunnr}</td>
					<td width="110px;">客户简称:</td>
					<td id="sortl" width="110px;">${entity.sortl}</td>
				</tr>
				<tr class="field">
					<td width="110px;">凭证编号(流水号):</td>
					<td id="zbelnrNum">${entity.zbelnr}</td>
					<td width="110px;">到款主体:</td>
					<td id="recBukrs">${recBukrs}</td>
					<td width="110px;">国际收支申报单号:</td>
					<td id="zdoip">${entity.zdoip}</td>
					<td width="110px;">收款日期:</td>
					<td>${entity.bldat}</td>
				</tr>
				<tr class="field">
					<td width="110px;">过账日期:</td>
					<td>${entity.budat}</td>
					<td width="110px;">收款金额:</td>
					<td>${entity.zcamount}</td>
					<td width="110px;">收款币别:</td>
					<td>${entity.waers}</td>
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
					<td class="title" colspan="6" style="border-right: 0;">手工冲销</td>
				</tr>
				<tr class="ui-jqgrid-labels">
					<td width="150px;">手工冲销凭证编号:</td>
					<td width="150px;"><input type="text" name="manualNum" id="manualNum"/></td>
					<td width="180px;">
						<div class="form_operate">
							<button id="B_manualConfirm" type="butoon" onclick="return false;">冲销</button>
							<button id="B_sepBack" type="butoon" onclick="return false;">分解退回</button>
						</div>
					</td>
				</tr>
    		</tbody>
    	</table>
    	<br/>
    	<div style="width:100%;overflow: auto;">
	    	<table style="width:2000px;">
				<thead>
					<tr>
						<td class="title" style="border-right: 0;" colspan="23">预收款分解</td>
					</tr>
					<tr>
						<th width="25px" class="ui-state-default ui-th-column ui-th-ltr">选择</th>
						<th width="150px" class="ui-state-default ui-th-column ui-th-ltr">发票号</th>
						<th width="150px" class="ui-state-default ui-th-column ui-th-ltr">合同号</th>
						<th width="100px" class="ui-state-default ui-th-column ui-th-ltr">外向交货单</th>
						<th width="100px" class="ui-state-default ui-th-column ui-th-ltr">客户简称</th>
						<th width="60px" class="ui-state-default ui-th-column ui-th-ltr">公司代码</th>
						<th width="60px" class="ui-state-default ui-th-column ui-th-ltr">行项</th>
						<th width="80px" class="ui-state-default ui-th-column ui-th-ltr">业务类型</th>
						<th width="100px" class="ui-state-default ui-th-column ui-th-ltr">未清金额</th>
						<th width="100px" class="ui-state-default ui-th-column ui-th-ltr">预收款金额</th>
						<th width="100px" class="ui-state-default ui-th-column ui-th-ltr">冲销金额</th>
						<th width="80px" class="ui-state-default ui-th-column ui-th-ltr">手续费</th>
						<th width="110px" class="ui-state-default ui-th-column ui-th-ltr">预计出口日期</th>
						<th width="110px" class="ui-state-default ui-th-column ui-th-ltr" style="color:red">冲销日期</th>
						<th width="100px" class="ui-state-default ui-th-column ui-th-ltr">合同金额</th>
						<th width="60px" class="ui-state-default ui-th-column ui-th-ltr">合同币种</th>
						<th width="60px" class="ui-state-default ui-th-column ui-th-ltr">记账汇率</th>
						<th width="120px" class="ui-state-default ui-th-column ui-th-ltr" style="color:red">备注</th>
						<th width="80px" class="ui-state-default ui-th-column ui-th-ltr">特殊总账</th>
						<th width="100px" class="ui-state-default ui-th-column ui-th-ltr">凭证1</th>
						<th width="100px" class="ui-state-default ui-th-column ui-th-ltr">凭证2</th>
						<th width="100px" class="ui-state-default ui-th-column ui-th-ltr">凭证3</th>
						<th width="100px" class="ui-state-default ui-th-column ui-th-ltr">冲销凭证</th>
					</tr>
				</thead>
	    		<tbody id="entityBody">
	    			<c:forEach var="sep" items="${sepList}">
	    				<tr id="${sep.zuonr}" class="${sep.zposnr}">
	    					<td>
	    						<c:if test="${'F' eq sep.zdrs && 'C' eq sep.zcirs}">
		    						<c:if test="${empty sep.zposnrSup && 'A' eq sep.zrbl && !empty sep.vbeln && empty sep.stblg}">
										<input type="checkbox" style="width:15px;" class="${sep.zposnr}" id="${sep.zbelnr}"/>
									</c:if>
									<c:if test="${!empty sep.zposnrSup && 'B' eq sep.zrbl && empty sep.stblg}">
										<input type="checkbox" style="width:15px;" class="${sep.zposnr}" id="${sep.zbelnr}"/>
									</c:if>
	    						</c:if>
	    					</td>
	    					<td style='white-space:nowrap;'>${sep.zuonr}</td>
	    					<td style='white-space:nowrap;'>${sep.bstkd}</td>
							<td style='white-space:nowrap;'>${sep.vbeln}</td>
							<td style='white-space:nowrap;'>${sep.sortl}</td>
							<td style='white-space:nowrap;'>${sep.bukrs}</td>
							<td style='white-space:nowrap;'>${sep.zposnr}</td>
							<td>
								<c:if test="${'A' eq sep.zrbl}">预收</c:if>
								<c:if test="${'B' eq sep.zrbl}">应收</c:if>
								<c:if test="${'C' eq sep.zrbl}">其他应收</c:if>
								<c:if test="${'D' eq sep.zrbl}">样品款</c:if>
								<c:if test="${'E' eq sep.zrbl}">其它</c:if>
							</td>
							<td style='text-align:right;'>${sep.wqamt}</td>
							<td style='text-align:right;'>${sep.zcamount}</td>
							<td style='text-align:right;'>${sep.zwoamt}</td>
							<td style='text-align:right;'>${sep.zhc}</td>
							<td>${sep.zpodat}</td>
							<td>
								<c:choose>
									<c:when test="${((!empty sep.zposnrSup && !empty sep.stblg && !' ' eq sep.stblg)||(empty sep.zposnrSup && 'A' eq sep.zrbl)) && 'F' eq sep.zdrs && 'C' eq sep.zcirs}">
										<input type='text' style='text-align:left;' onfocus="WdatePicker({dateFmt:'yyyyMMdd'})" onchange="autoSave(this,'zwodat','${sep.zbelnr}','${sep.zposnr}')" value='${sep.zwodat}'/>
									</c:when>
									<c:otherwise>
										${sep.zwodat}
									</c:otherwise>
								</c:choose>
							</td>
							<td style='text-align:right;'>${sep.zbamt}</td>
							<td style='white-space:nowrap;'>${sep.waers}</td>
							<td style='text-align:right;'>${sep.kursf}</td>
							<td><input type='text' style='text-align:left;' onblur="autoSave(this,'zanote','${sep.zbelnr}','${sep.zposnr}')" value='${sep.zanote}'/></td>
							<td>${sep.umskz}</td>
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
							<td width="100px" class="stblg">
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
	//记账按钮事件
	$("#B_confirm").click(confirmAcctEdit);
	//手工冲销按钮事件
	$("#B_manualConfirm").click(confirmManual);
	//分解退回按钮事件
	$("#B_sepBack").click(sepPushBack);
});

/**
 * 自动记账
 */
function confirmAcctEdit(){
	if($("#entityBody input:checked").length==0){
		oAlert("请选择一笔预收款进行冲销!");
	}else{
		var success = true;
		//校验
		$("#entityBody input:checked").each(function(){
			var vbeln = this.parentNode.parentNode.children.item(3).innerText;
			var zrbl = this.parentNode.parentNode.children.item(7).innerText;
			var wqAmt = this.parentNode.parentNode.children.item(8).innerText;
			var ysAmt = this.parentNode.parentNode.children.item(9).innerText;
			if(zrbl=="C"||zrbl=="D"||zrbl=="E"){
				oAlert("勾选记录不是应收款或预收款,请手工冲销!");
				success = false;
				return;
			}
			if(zrbl=="A"&&(vbeln==""||vbeln==" ")){
				oAlert("预收款的外向交货单为空,请手工冲销!");
				success = false;
				return;
			}
			if(eval(wqAmt)<eval(ysAmt)){
				oAlert("预收金额大于未清金额,请手工冲销!");
				success = false;
				return;
			}
		});
		if(!success){
			return;
		}
		//生成凭证
		$("#entityBody input:checked").each(function(){
			var flowNum = this.id;
			var zposnr = this.className;
			var bukrs = $('#bukrs').text();
			var params = {
				'zposnr':zposnr,
				'flowNum':flowNum
			};
			var zrbl = this.parentNode.parentNode.children.item(7).innerText;
			var wqAmt = this.parentNode.parentNode.children.item(8).innerText;
			var ysAmt = this.parentNode.parentNode.children.item(9).innerText;
			if(zrbl=="C"||zrbl=="D"||zrbl=="E"){
				oAlert("勾选记录不是应收款或预收款,请手工冲销!");
				return;
			}
			if(eval(wqAmt)<eval(ysAmt)){
				oAlert("预收金额大于未清金额,请手工冲销!");
				return;
			}
			$.ajax({
				url : "generateCertSingle",
				type : "post",
				data : params ,
				dataType : "json",
				contentType : "application/x-www-form-urlencoded; charset=utf-8"
			}).done(function(ret) {
				if("success"==ret[0]){
					var html = "<a href='#' onclick=\"viewBelnr('"+ret[1]+"','"+bukrs+"','"+ret[2]+"')\">"+ret[1]+"</a>";
					$("#entityBody ."+zposnr+" .stblg").html(html);
				}else{
					success = false;
					oAlert("冲销失败"+ret[1]);
					refresh();
					return;
				}
			});
		});
	}
}
 

/**
 * 手工冲销
 */
function confirmManual(){
	if($("#entityBody input:checked").length!=1){
		oAlert("请选择单笔预收款进行手工冲销!");
	}else{
    	var zposnr = $("#entityBody input:checked")[0].className;
		var params = {
			'manualNum':$('#manualNum').val(),
			'flowNum':$('#zbelnrNum').text(),
			'zposnr':zposnr
		}
		if(params.manualNum==""){
			oAlert("请录入手工冲销凭证编号");
			return;
		}
		if(!confirm("确认对当前预收款手工冲销?")){
			return;
		}
		$.ajax({
			url : "manualFlush",
			type : "post",
			data : params ,
			dataType : "json",
			contentType : "application/x-www-form-urlencoded; charset=utf-8"
		}).done(function(ret) {
			if("success"==ret[0]){
				alert("手工冲销成功");
				refresh();
			}else{
				alert("手工冲销失败"+ret[1]);
			}
		});
	}
}

/**
 * 分解退回
 */
function sepPushBack(){
	var params = {
		'flowNum':$('#zbelnrNum').text()
	};
	if(!confirm("确认对预收信息进行分解退回操作?")){
		return;
	}
	$.ajax({
		url : "sepratePushBack",
		type : "post",
		data : params ,
		dataType : "json",
		contentType : "application/x-www-form-urlencoded; charset=utf-8"
	}).done(function(ret) {
		if("success"==ret[0]){
			oAlert("分解退回成功");
			refresh();
		}else{
			oAlert("分解退回失败"+ret[1]);
		}
	});
}

/**
 * 刷新页面
 */
function refresh(){
	window.location.href=ctx+"/ficoManage/ficoRoute?from=cw&flowNum="+$('#zbelnrNum').text();
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

/**
 * 保存应收信息
 */
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
</script>
</html>