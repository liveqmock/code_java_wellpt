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
<title>到款资料分解</title>
</head>
<body>
  <div class="div_body" style="">
    <div class="form_header" style="">
      <div class="form_title" style="">
        <h2>到款资料分解</h2>
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
					<td id="zsname" title="双击修改业务员">${entity.zsname}</td>
					<td width="110px;">公司代码:</td>
					<td id="bukrs">${entity.bukrs}</td>
					<td width="110px;">客户编号:</td>
					<td id="kunnr">${entity.kunnr}</td>
					<td width="110px;">客户简称:</td>
					<td id="sortl">${entity.sortl}</td>
				</tr>
				<tr class="field">
					<td width="110px;">凭证编号(流水号):</td>
					<td id="zbelnrNum">${entity.zbelnr}</td>
					<td width="110px;">到款主体:</td>
					<td id="recBukrs">${recBukrs}</td>
					<td width="110px;">国际收支申报单号:</td>
					<td>${entity.zdoip}</td>
					<td width="110px;">收款日期:</td>
					<td>${entity.bldat}</td>
				</tr>
				<tr class="field">
					<td width="110px;">过账日期:</td>
					<td>${entity.budat}</td>
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
					<td colspan="6"><input type="text" name="ztext" id="ztext" style="border:0;width:100%;height:90%;" value="${entity.ztext}"/></td>
					<td>
						<div class="form_operate">
							<button id="B_saveText" type="butoon" onclick="return false;">保存</button>
							<!-- <button id="B_submAcct" type="butoon" onclick="return false;">提交</button>  -->
						</div>
					</td>
				</tr>
    		</tbody>
    	</table>
    	<br/>
    	<table>
    		<tbody>
    			<tr>
					<td class="title" colspan="2" style="border-right: 0;">应收款查询</td>
					<td class="title" style="border-left:0;" colspan="4">
						<div class="form_operate" style="float: right;margin-top: 5px;">
							<button id="searchBtn" type="butoon" onclick="return false;">查找</button>
							<button id="receiveLink" type="butoon" onclick="return false;">应收款查询</button>
							<button id="otherLink" type="butoon" onclick="return false;">其他应收查询</button>
							<button id="B_zuonrCheck" type="butoon" onclick="return false;">未开票登记</button>
						</div>
					</td>
				</tr>
				<tr class="ui-jqgrid-labels">
					<td width="80px;">合同编号:</td>
					<td width="250px;" ondblclick="getDefault()"><input type="text" name="xblnr" id="xblnr"/></td>
					<td width="100px;">外向交货单号:</td>
					<td width="250px;"><input type="text" name="xref1" id="xref1"/></td>
					<td width="60px;">备注:</td>
					<td width="250px;"><input type="text" name="sgtxt" id="sgtxt"/></td>
				</tr>
				<tr class="ui-jqgrid-labels">
			   		<td colspan="6">
						<div>
					    	<span style="font-size:12px;font-weight:bold;">预收未清合计:</span><span style="color:red;font-size:12px;font-weight:bold;">${sumDatas[0]}</span>
					    	&nbsp;<span style="font-size:12px;font-weight:bold;">应收未清合计:</span><span style="color:red;font-size:12px;font-weight:bold;">${sumDatas[1]}</span>
					    	&nbsp;<span style="font-size:12px;font-weight:bold;">其他未清合计:</span><span style="color:red;font-size:12px;font-weight:bold;">${sumDatas[2]}</span>
					    </div>
					</td>
			    <tr>
    		</tbody>
    	</table>
    	<table style="margin-top: 4px;">
    		<thead>
    			<tr class="ui-jqgrid-labels">
					<th width="110px" class="ui-state-default ui-th-column ui-th-ltr">发票号</th>
					<th width="120px" class="ui-state-default ui-th-column ui-th-ltr">合同号</th>
					<th width="120px" class="ui-state-default ui-th-column ui-th-ltr">外向交货单</th>
					<th width="100px" class="ui-state-default ui-th-column ui-th-ltr">备注</th>
					<th width="100px" class="ui-state-default ui-th-column ui-th-ltr">预收未清</th>
					<th width="100px" class="ui-state-default ui-th-column ui-th-ltr">未清金额</th>
					<th width="60px" class="ui-state-default ui-th-column ui-th-ltr">币种</th>
					<th width="70px" class="ui-state-default ui-th-column ui-th-ltr">业务类型</th>
					<th width="100px" class="ui-state-default ui-th-column ui-th-ltr">本次收款金额</th>
					<th width="80px" class="ui-state-default ui-th-column ui-th-ltr">操作</th>
				</tr>
    		</thead>
    		<tbody id="receivableBody">
    		</tbody>
    	</table>
    	<br/>
    	<div style="width:100%;overflow: auto;">
	    	<table style="width:2000px;">
				<thead>
					<tr>
						<td class="title" style="border-right: 0;" colspan="2">预收款分解</td>
						<td class="title" style="border-right: 0;border-left: 0;" colspan="6">金额合计：
							<span style="fron-size:12px;color:red;font-weight:bold;" id="amtSum">${amtSum}</span></td>
						<td class="title" style="border-left: 0;" colspan="11"></td>
					</tr>
					<tr>
						<th width="30px" class="ui-state-default ui-th-column ui-th-ltr">选择</th>
						<th width="150px" class="ui-state-default ui-th-column ui-th-ltr">合同号</th>
						<th width="80px" class="ui-state-default ui-th-column ui-th-ltr">外向交货单</th>
						<th width="100px" class="ui-state-default ui-th-column ui-th-ltr">客户简称</th>
						<th width="60px" class="ui-state-default ui-th-column ui-th-ltr">公司代码</th>
						<th width="60px" class="ui-state-default ui-th-column ui-th-ltr">行项</th>
						<th width="80px" class="ui-state-default ui-th-column ui-th-ltr">业务类型</th>
						<th width="60px" class="ui-state-default ui-th-column ui-th-ltr">样品类型</th>
						<th width="80px" class="ui-state-default ui-th-column ui-th-ltr">产品组</th>
						<th width="100px" class="ui-state-default ui-th-column ui-th-ltr">收款金额</th>
						<th width="100px" class="ui-state-default ui-th-column ui-th-ltr">冲销金额</th>
						<th width="80px" class="ui-state-default ui-th-column ui-th-ltr">手续费(${entity.waers})</th>
						<th width="80px" class="ui-state-default ui-th-column ui-th-ltr">预计出口日期</th>
						<th width="100px" class="ui-state-default ui-th-column ui-th-ltr">合同金额</th>
						<th width="60px" class="ui-state-default ui-th-column ui-th-ltr">合同币种</th>
						<th width="80px" class="ui-state-default ui-th-column ui-th-ltr">记账汇率</th>
						<th width="100px" class="ui-state-default ui-th-column ui-th-ltr">凭证号</th>
						<th width="80px" class="ui-state-default ui-th-column ui-th-ltr">单位换算</th>
						<th width="80px" class="ui-state-default ui-th-column ui-th-ltr">操作</th>
					</tr>
				</thead>
	    		<tbody id="acctBody">
	    			<c:forEach var="sep" items="${sepList}">
	    				<tr id="${sep.zuonr}" class="${sep.zposnr}">
	    				<c:if test="${'B' eq sep.zrbl || 'C' eq sep.zrbl}">
	    					<td></td>
	    					<td style='white-space:nowrap;'>${sep.bstkd}</td>
							<td style='white-space:nowrap;'>${sep.vbeln}</td>
							<td style='white-space:nowrap;'>${sep.sortl}</td>
							<td style='white-space:nowrap;'>${sep.bukrs}</td>
							<td>${sep.zposnr}</td>
							<td>
								<select disabled='true' style='width: 100%;border: 0;padding: 0;margin: 0;' value='${sep.zrbl}'>
									<option value='B' <c:if test="${'B' eq sep.zrbl}">selected</c:if>>应收</option>
									<option value='C' <c:if test="${'C' eq sep.zrbl}">selected</c:if>>其他应收款</option>
								</select>
							</td>
							<td></td>
							<td></td>
							<td style='text-align:right;'>${sep.zcamount}</td>
							<td style='text-align:right;'>${sep.zwoamt}</td>
							<td style='text-align:right;'>${sep.zhc}</td>
							<td style='white-space:nowrap;'>${sep.zpodat}</td>
							<td style='text-align:right;'>${sep.zbamt}</td>
							<td style='white-space:nowrap;'>${sep.waers}</td>
							<td><input type='text' style='text-align:right;' value='${sep.kursf}'/></td>
							<td style='white-space:nowrap;'>${sep.belnr}</td>
							<td><input type='text' style='text-align:right;' value='${sep.zpeinh}'/></td>
							<td style='white-space:nowrap;'>
							    <c:if test="${empty sep.stblg && !empty sep.zposnrSup && 'P' eq sep.zdrs}">
									<a href='#' onclick='saveAcct(this)'>保存</a>&nbsp;&nbsp;<a href='#' onclick='deleteAcct(this)'>删除</a>
								</c:if>
							</td>
	    				</c:if>
	    				<c:if test="${'A' eq sep.zrbl || 'D' eq sep.zrbl || 'E' eq sep.zrbl}">
	    					<td>
	    						<c:if test="${'A' eq sep.zrbl && 'P' eq sep.zdrs && 'C' eq sep.zcirs && empty sep.zposnrSup}">
									<input type="checkbox" style="width:15px;" class="${sep.zposnr}" id="${sep.zbelnr}"/>
								</c:if>
	    					</td>
	    					<td style='white-space:nowrap;'>${sep.bstkd}</td>
							<td><input type='text' value='${sep.vbeln}'/></td>
							<td style='white-space:nowrap;'>${sep.sortl}</td>
							<td style='white-space:nowrap;'>${sep.bukrs}</td>
	    					<td>${sep.zposnr}</td>
							<td>
								<select disabled='true' style='width: 100%;border: 0;padding: 0;margin: 0;' value='${sep.zrbl}'>
									<option value='A' <c:if test="${'A' eq sep.zrbl}">selected</c:if>>预收</option>
									<option value='D' <c:if test="${'D' eq sep.zrbl}">selected</c:if>>样品款</option>
									<option value='E' <c:if test="${'E' eq sep.zrbl}">selected</c:if>>其它</option>
								</select>
							</td>
							<td>
								<select style='width: 100%;border: 0;' value='${sep.zsmc}'>
									<option value=' '></option>
									<option value='1' <c:if test="${'1' eq sep.zsmc}">selected</c:if>>报关</option>
									<option value='2' <c:if test="${'2' eq sep.zsmc}">selected</c:if>>不报关</option>
									<option value='3' <c:if test="${'3' eq sep.zsmc}">selected</c:if>>内销</option>
								</select>
							</td>
							<td>
								<c:if test="${'D' eq sep.zrbl}">
									<select style='width: 100%;border: 0;padding: 0;margin: 0;' value='${sep.spart}'>
										<option value='00' <c:if test="${'00' eq sep.spart}">selected</c:if>>00-通用</option>
										<option value='10' <c:if test="${'10' eq sep.spart}">selected</c:if>>10-CFL</option>
										<option value='20' <c:if test="${'20' eq sep.spart}">selected</c:if>>20-LED</option>
									</select>
								</c:if>
							</td>
							<td><input type='text' style='text-align:right;' value='${sep.zcamount}'/></td>
							<td style='text-align:right;'>${sep.zwoamt}</td>
							<td style='text-align:right;'>${sep.zhc}</td>
							<td>
								<input type='text' style='text-align:left;' onfocus="WdatePicker({dateFmt:'yyyyMMdd'})" value='${sep.zpodat}'/>
							</td>
							<td><input type='text' style='text-align:right;' value='${sep.zbamt}'/></td>
							<td><input type='text' value='${sep.waers}'/></td>
							<td><input type='text' style='text-align:right;' value='${sep.kursf}'/></td>
							<td style='white-space:nowrap;'>
								<a href="#" onclick="viewBelnr('${sep.belnr}','${recBukrs}','${entity.budat}')">
									${sep.belnr}
								</a>
							</td>
							<td><input type='text' style='text-align:right;' value='${sep.zpeinh}'/></td>
							<td style='white-space:nowrap;'>
								<c:if test="${'A' eq sep.zrbl && empty sep.vbeln && empty sep.zposnrSup}">
									&nbsp;&nbsp;<a href='#' onclick='seprateAcct(this)'>分解样品款</a>
								</c:if>
								<c:if test="${'P' eq sep.zdrs && empty sep.stblg && empty sep.zposnrSup}">
									&nbsp;&nbsp;<a href='#' onclick='saveAcct(this)'>保存</a>
									&nbsp;&nbsp;<a href='#' onclick='deleteAcct(this)'>删除</a>
								</c:if>
							</td>
	    				</c:if>
	    				</tr>
	    			</c:forEach>
	    		</tbody>
	    	</table>
    	</div>
		<div style="height:20px;width:100%;float:top;margin-bottom:3px;">
			<div style="width:500px;float:left;height:20px;">
				<span style="color:#FF6060;font-size:11px;line-height:20px;font-weight:thin;">金额计算逻辑:本次收款金额100 USD=（到款金额70 EUR+手续费10 EUR）/ 清账汇率 0.8</span>
			</div>
		</div>
		<div style="height:20px;width:100%;float:top;margin-bottom:3px;">
			<div style="width:500px;float:left;height:20px;">
				<span style="color:#FF6060;font-size:11px;line-height:20px;font-weight:thin;">红色标题为可修改字段，黑色标题为不可修改字段</span>
			</div>
		</div>
		<div style="height:20px;width:100%;float:top;margin-bottom:10px;">
			<div style="width:500px;float:left;height:20px;">
				<span style="color:#FF6060;font-size:11px;line-height:20px;font-weight:thin;">当前客户AR:${arInfo}</span>
			</div>
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
	var model = "${model}";
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
	//查询按钮事件
	$("#searchBtn").click(searchReceive);
	//未开票登记事件
	$("#B_zuonrCheck").click(zuonrCheckInfoSave);
	//业务员双击事件
	$("#zsname").dblclick(openUserChooseWin);
	//应收款查询
	$("#receiveLink").click(openReceiveSearch);
	//其他应收款查询
	$("#otherLink").click(openOtherAcctSearch);
});

/**
 * 应收款查询
 */
function openReceiveSearch(){
	var kunnr = $('#kunnr').text();
	var bukrs = $('#bukrs').text();
	var xblnr = $('#xblnr').val();
	var xref1 = $('#xref1').val();
	var action = ctx + "/ficoManage/receiveAcct?from=edit&kunnr="+kunnr+"&xblnr="+xblnr+"&xref1="+xref1+"&bukrs="+bukrs;
	window.open(action);
}

/**
 * 其他应收款查询
 */
function openOtherAcctSearch(){
	var kunnr = $('#kunnr').text();
	var bukrs = $('#bukrs').text();
	var sgtxt = $('#sgtxt').val();
	var action = ctx + "/ficoManage/otherAcct?from=edit&kunnr="+kunnr+"&sgtxt="+sgtxt+"&bukrs="+bukrs;
	window.open(action);
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
			url : "saveAcct",
			type : "post",
			data : params ,
			dataType : "json",
			contentType : "application/x-www-form-urlencoded; charset=utf-8"
		}).done(function(ret) {
			if("success"==ret[0]){
				oAlert("转交成功!");
				refresh();
			}else{
				oAlert("转交失败:"+ret[1]);
			}
		});
	});
}

/**
 * 刷新页面
 */
function refresh(){
	window.location.href=ctx+"/ficoManage/ficoRoute?from=yw&flowNum="+$('#zbelnrNum').text();
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
	$.ajax({
		url : "saveAcct",
		type : "post",
		data : params ,
		dataType : "json",
		contentType : "application/x-www-form-urlencoded; charset=utf-8"
	}).done(function(ret) {
		if("success"==ret[0]){
			oAlert("保存成功!");
		}else{
			oAlert("保存失败:"+ret[1]);
		}
	});
}

/**
 * 自动计算金额合计值
 */
function getAmtSum(){
	var flowNum = $('#zbelnrNum').text();
	var params = {
		'flowNum':flowNum
	};
	console.dir(params);
	$("#amtSum").load("getSum",params);
}

/**
 * 双击合同号,默认当前日期
 */
function getDefault(){
	var cur = new Date();
	var year = cur.getFullYear();
	var month = (cur.getMonth()+1)<10?("0"+(cur.getMonth()+1)):(""+(cur.getMonth()+1));
	var date = (cur.getDate())<10?("0"+cur.getDate()):(""+cur.getDate());
	$('#xblnr').val(year+""+month+""+date);
}

/**
 * 查找应收款信息
 */
function searchReceive(){
	if($('#xblnr').val()==""&&$('#xref1').val()==""&&$('#sgtxt').val()==""){
		return;
	}
	$("#receivableBody").html("");
	$.ajax({
		url : "findReceivableAccts",
		type : "post",
		data : {
			'xblnr' : $('#xblnr').val(),
			'xref1' : $('#xref1').val(),
			'kunnr' : $('#kunnr').text(),
			'bukrs' : $('#bukrs').text(),
			'sgtxt' : $('#sgtxt').val()
		},
		dataType : "json",
		contentType : "application/x-www-form-urlencoded; charset=utf-8"
	}).done(function(ret) {
		loadReceiveTable(ret);
	});
}

/**
 * 加载应收款列表
 * 
 * @param obj
 */
function loadReceiveTable(obj){
	receive = obj;
	if(obj.length>0){
		// 有查询结果则显示
		var array = [];
		var j = 0;
		for(var i=0,len=obj.length;i<len;i++){
			array[j++]="<tr ondblclick=\"reloadQuery('"+obj[i][1]+"','"+obj[i][2]+"')\">";
			array[j++]="<td>"+obj[i][0]+"</td>";
			array[j++]="<td>"+obj[i][1]+"</td>";
			array[j++]="<td>"+obj[i][2]+"</td>";
			array[j++]="<td>"+obj[i][8]+"</td>";
			array[j++]="<td style='text-align: right;'>"+obj[i][3]+"</td>";
			// array[j++]="<td style='text-align: right;'>"+obj[i][4]+"</td>";
			array[j++]="<td style='text-align: right;' ondblclick='assignReceiveAmt("+i+",this)'>"+obj[i][5]+"</td>";
			array[j++]="<td>"+obj[i][7]+"</td>";
			if(obj[i][6]==0){
				array[j++]="<td>应收款</td>";
			}else{
				array[j++]="<td>其他应收款</td>";
			}
			array[j++]="<td><input type='text' id='bcskje"+i+"' ondblclick='this.value=0' maxlength='20' style='width: 100%;height: 100%;border: 0;text-align: right;' value='0'/></td>";
			array[j++]="<td style='text-align: center;'><a href='#' onclick='insertAcctFromReceive("+i+")'>确认</a></td>";
			array[j++]="</tr>";
		}
		$("#receivableBody").html(array.join(""));
	}else{
		// 没有查询结果则查看是否已经清账或未开票
		if($('#xref1').val()!=""){
			var param = {
				'xblnr' : $('#xblnr').val(),
				'xref1' : $('#xref1').val(),
				'kunnr' : $('#kunnr').text(),
				'bukrs' : $('#bukrs').text(),
				'sgtxt' : $('#sgtxt').val()
			};
			$.get("checkZounrClear",param).done(function(ret){
				if(ret!=""){
					oAlert(ret);
				}
			});
		}
		
	}
}

/**
 * 从应收列表加载当前行到信息登记列表
 * 
 * @param index
 */
function insertAcctFromReceive(index){
	if($("#acctBody input:checked").length!=1){
		oAlert("请选择单条预收信息进行分解");
		return;
	}
	if($('#bcskje'+index).val()=='0'){
		oAlert("本次收款金额不能为0");
		return;
	}
	var tr=$("#acctBody input:checked")[0].parentNode.parentNode;
	var params = getParams(tr);
	$.ajax({
		url : "cloneAcctSerprate",
		type : "post",
		data : params ,
		dataType : "json",
		contentType : "application/x-www-form-urlencoded; charset=utf-8"
	}).done(function(ret) {
		if("success"==ret[0]){
			params.zposnr = ret[1];
			params.zwoamt='0';
			params.zhc='0';
			params.bstkd =receive[index][1];
			params.vbeln =receive[index][2];
			var zrbl=receive[index][6];
			if(zrbl==1){
				params.zrbl='C';
				params.zanote=receive[index][8];
			}else{
				params.zrbl='B';
			}
			params.zcamount=$('#bcskje'+index).val();
			params.waers=receive[index][7];
			params.zuonr=receive[index][0];
			var html = insertRow(params,1);
			$(tr).after(html);
		}else{
			oAlert("分解失败!"+ret[1]);
		}
	});
}

/**
 * 新增一行到账分解信息
 * @param param
 * @returns
 */
function insertRow(param,type){
	var res='0';
	var array = [];
	var j = 0;
	if(type==0){
		//从预收信息分解,类型为样品款或其他类型
		var zrblOps = "<option value='D' select>样品款</option><option value='E'>其它</option>";
		var zsmcOps = "<option value=' ' select></option><option value='1'>报关</option><option value='2'>不报关</option><option value='3'>内销</option>";
		var spartOps ="<option value=' '></option><option value='00'>00-通用</option><option value='10'>10-CFL</option><option value='20' selected>20-LED</option>"
		array[j++]="<tr class='"+param.zposnr+"' id=''>";
		array[j++]="<td></td>";
		array[j++]="<td class='bstkd'><input type='text' value='"+param.bstkd+"'/></td>";
		array[j++]="<td class='vbeln'><input type='text' value='"+param.vbeln+"'/></td>";
		array[j++]="<td style='white-space:nowrap;'>"+param.sortl+"</td>";
		array[j++]="<td style='white-space:nowrap;'>"+param.bukrs+"</td>";
		array[j++]="<td class='zposnr' style='white-space:nowrap;'>"+param.zposnr+"</td>";
		array[j++]="<td class='zrbl' style='white-space:nowrap;'><select style='width: 100%;border: 0;padding: 0;margin: 0;'  value='"+param.zrbl+"'>"+zrblOps+"</select></td>";
		array[j++]="<td class='zsmc' style='white-space:nowrap;'><select style='width: 100%;border: 0;' value=' '>"+zsmcOps+"</select></td>";
		array[j++]="<td class='spart'><select style='width: 100%;border: 0;' value='20'>"+spartOps+"</select></td>";
		array[j++]="<td class='zcamount'><input type='text' style='text-align:right;' value='"+res+"'/></td>";
		array[j++]="<td class='zwoamt' style='text-align:right;'>0</td>";
		array[j++]="<td class='zhc' style='text-align:right;'>0</td>";
		array[j++]="<td class='zpodat'><input type='text' readonly='true' onfocus=\"WdatePicker({dateFmt:'yyyyMMdd'})\" value='"+param.zpodat+"'/></td>";
		array[j++]="<td class='zbamt'><input type='text' style='text-align:right;' value='"+param.zbamt+"'/></td>";
		array[j++]="<td class='waers'><input type='text' style='text-align:left;' value='"+param.waers+"'/></td>";
		array[j++]="<td class='kursf'><input type='text' style='text-align:right;' value='"+param.kursf+"'/></td>";
		array[j++]="<td class='belnr' style='text-align:left;'>"+param.belnr+"</td>";
		array[j++]="<td class='zpeinh'><input type='text' style='text-align:right;' value='"+param.zpeinh+"'/></td>";
		array[j++]="<td style='white-space:nowrap;'>&nbsp;&nbsp;<a href='#' onclick='saveAcct(this)'>保存</a>&nbsp;&nbsp;<a href='#' onclick='deleteAcct(this)'>删除</a></td>";
		array[j++]="</tr>";
	}else{
		//从应收列表查询,类型为应收或其他应收款
		var zrblOps = "<option value='B' select>应收款</option>";
		if(param.zrbl=='C'){
			zrblOps = "<option value='C' select>其他应收款</option>";
		}
		array[j++]="<tr class='"+param.zposnr+"' id='"+param.zuonr+"'>";
		array[j++]="<td></td>";
		array[j++]="<td class='bstkd'>"+param.bstkd+"</td>";
		array[j++]="<td class='vbeln'>"+param.vbeln+"</td>";
		array[j++]="<td style='white-space:nowrap;'>"+param.sortl+"</td>";
		array[j++]="<td style='white-space:nowrap;'>"+param.bukrs+"</td>";
		array[j++]="<td class='zposnr' style='white-space:nowrap;'>"+param.zposnr+"</td>";
		array[j++]="<td class='zrbl' style='white-space:nowrap;'><select disabled='true' style='width: 100%;border: 0;padding: 0;margin: 0;'  value='"+param.zrbl+"'>"+zrblOps+"</select></td>";
		array[j++]="<td></td>";
		array[j++]="<td></td>";
		array[j++]="<td class='zcamount'><input type='text' style='text-align:right;' value='"+param.zcamount+"'/></td>";
		array[j++]="<td class='zwoamt' style='text-align:right;'>0</td>";
		array[j++]="<td class='zhc' style='text-align:right;'>0</td>";
		array[j++]="<td class='zpodat'><input type='text' readonly='true' readonly='true' onfocus=\"WdatePicker({dateFmt:'yyyyMMdd'})\" value='"+param.zpodat+"'/></td>";
		array[j++]="<td class='zbamt'><input type='text' style='text-align:right;' value='"+param.zbamt+"'/></td>";
		array[j++]="<td class='waers'><input type='text' style='text-align:left;' value='"+param.waers+"'/></td>";
		array[j++]="<td class='kursf'><input type='text' style='text-align:right;' value='"+param.kursf+"'/></td>";
		array[j++]="<td class='belnr' style='text-align:left;'>"+param.belnr+"</td>";
		array[j++]="<td class='zpeinh'><input type='text' style='text-align:right;' value='"+param.zpeinh+"'/></td>";
		array[j++]="<td style='white-space:nowrap;'>&nbsp;&nbsp;<a href='#' onclick='saveAcct(this)'>保存</a>&nbsp;&nbsp;<a href='#' onclick='deleteAcct(this)'>删除</a></td>";
		array[j++]="</tr>";
	}
	return array.join("");
}

/**
 * 自动赋值给本次收款金额
 */
function assignReceiveAmt(index,obj){
	$('#bcskje'+index).val(obj.innerText);
}

/**
 * 双击应收信息时重置查询条件 
 */
function reloadQuery(xblnr,xref1){
	$('#xblnr').val(xblnr);
	$('#xref1').val(xref1);
}

/**
 * 保存到账分解信息
 * @param obj
 */
function saveAcct(obj){
	var tr = obj.parentNode.parentNode;
	var params = getParams(tr);
	if(params.zrbl=="A" && params.bstkd==""){
		oAlert("合同号必填!");
		return;
	}
	if(params.zrbl=="A" && params.vbeln!=""){
		oAlert("预收款不能填写外向交货单!");
		return;
	}
	if(params.zrbl=="A" && params.zpodat=="00000000"){
		oAlert("预计出口日期必填!");
		return;
	}
	if(params.zrbl=="D" && params.zsmc==" "){
		oAlert("请选择样品款类型!");
		return;
	}
	if(params.zcamount==""||eval(params.zcamount)==0){
		oAlert("请填写收款金额!");
		return;
	}
	$.ajax({
		url : "saveAcctSepSubmit",
		type : "post",
		data : params ,
		dataType : "json",
		contentType : "application/x-www-form-urlencoded; charset=utf-8"
	}).done(function(ret) {
		if("success"==ret[0]){
			oAlert("保存成功");
			tr.className = ret[1];
			refresh();
		}else{
			oAlert("保存失败!"+ret[1]);
		}
	});
}

/**
 * 删除当前账款信息
 * 
 * @param obj
 */
function deleteAcct(obj){
	oConfirm("确认删除?", function() {
		var tr = obj.parentNode.parentNode;
		if(tr.className == "tempAcct"){
			tr.parentNode.removeChild(tr);
		}else{
			var params = getParams(tr);
			$.post("deleteAcctSeprate", params).done(function(ret) {
				if("success"==ret){
					oAlert("删除成功");
					tr.parentNode.removeChild(tr);
					getAmtSum();
				}else{
					oAlert("删除失败!");
				}
			})
		}
	});
}

/**
 * 获取当前操作行参数
 * @param index
 */
function getParams(tr){
	var param = {
		'zposnr':tr.className,
		'zuonr':tr.id,
		'zbelnr':$('#zbelnrNum').text(),
		'bstkd':tr.children.item(1).children.length>0?tr.children.item(1).children.item(0).value:tr.children.item(1).innerText,
		'vbeln':tr.children.item(2).children.length>0?tr.children.item(2).children.item(0).value:tr.children.item(2).innerText,
		'sortl':tr.children.item(3).children.length>0?tr.children.item(3).children.item(0).value:tr.children.item(3).innerText,
		'bukrs':tr.children.item(4).children.length>0?tr.children.item(4).children.item(0).value:tr.children.item(4).innerText,
		'zrbl':tr.children.item(6).children.length>0?tr.children.item(6).children.item(0).value:tr.children.item(6).innerText,
		'zsmc':tr.children.item(7).children.length>0?tr.children.item(7).children.item(0).value:tr.children.item(7).innerText,
		'spart':tr.children.item(8).children.length>0?tr.children.item(8).children.item(0).value:tr.children.item(8).innerText,
		'zcamount':tr.children.item(9).children.length>0?tr.children.item(9).children.item(0).value:tr.children.item(9).innerText,
		'zwoamt':tr.children.item(10).children.length>0?tr.children.item(10).children.item(0).value:tr.children.item(10).innerText,
		'zhc':tr.children.item(11).children.length>0?tr.children.item(11).children.item(0).value:tr.children.item(11).innerText,
		'zpodat':tr.children.item(12).children.length>0?tr.children.item(12).children.item(0).value:tr.children.item(12).innerText,
		'zbamt':tr.children.item(13).children.length>0?tr.children.item(13).children.item(0).value:tr.children.item(13).innerText,
		'waers':tr.children.item(14).children.length>0?tr.children.item(14).children.item(0).value:tr.children.item(14).innerText,
		'kursf':tr.children.item(15).children.length>0?tr.children.item(15).children.item(0).value:tr.children.item(15).innerText,
		'belnr':tr.children.item(16).children.length>0?tr.children.item(16).children.item(0).innerText:tr.children.item(16).innerText,
		'zpeinh':tr.children.item(17).children.length>0?tr.children.item(17).children.item(0).value:tr.children.item(17).innerText
	};
	return param;
}

/**
 * 参数校验
 * 
 * @param param
 * 如果保存的是预收款,校验相关信息必输
 */
function checkParams(param){
	if(param.zrbl=="D"){
		if(param.zsmc==" "){
			return "请选择样品类型!";
		}
	}
	if(param.zrbl=="A"){
		if(param.zpodat==""||param.zpodat=="00000000"){
			return "请填写预计出口日期!";
		}
		if(param.zbamt==""){
			return "请填写合同金额!";
		}
		if(param.zcamount==0){
			return "请填写本次收款金额!";
		}
	}
}

/**
 * 发票跟踪数据校验
 */
function zuonrCheckInfoSave(){
	var param = {
		'zbelnr':$('#zbelnrNum').text(),
		'del'   :"del",
		'vbeln' :$('#xref1').val()
	};
	if(param.vbeln==""||param.vbeln==" "){
		oAlert("外向交货单必填!");
		$('#xref1').focus();
		return;
	}
	$.post("saveZounrCheckInfo",param).done(function(ret){
		oAlert(ret);
	});
}

/**
 * 预收款分解
 * @param index
 */
function seprateAcct(obj){
	var tr = obj.parentNode.parentNode;
	var param = getParams(tr);
	$.ajax({
		url : "cloneAcctSerprate",
		type : "post",
		data : param ,
		dataType : "json",
		contentType : "application/x-www-form-urlencoded; charset=utf-8"
	}).done(function(ret) {
		if("success"==ret[0]){
			param.zposnr = ret[1];
			if(param.zbelnr.substring(0,1)=="Z"){
				param.bstkd="";
			}
			var html = insertRow(param,0);
			$(tr).after(html);
		}else{
			alert("分解失败!"+ret[1]);
		}
	});
}

/**
 * 总账查询
 */
function viewBelnr(belnr,bukrs,data){
	var url = ctx +"/ficoManage/belnrSearch?belnr="+belnr+"&bukrs="+bukrs+"&gjahr="+data.substring(0,4);
	window.open(url,"总帐查询");
}

window.onbeforeunload = function()
{
	 var n = window.event.screenX - window.screenLeft;    
     var b = n > document.documentElement.scrollWidth-20;   
	 if(!subFlag){
		 if(b && window.event.clientY < 0 || window.event.altKey){
		 	return "您尚未提交当前到账信息数据，确认退出？";
		 }
	 }
}; 
</script>
</html>