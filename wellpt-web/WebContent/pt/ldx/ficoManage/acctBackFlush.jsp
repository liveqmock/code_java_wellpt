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
<title>到账信息回冲</title>
</head>
<body>
  <div class="div_body" style="">
    <div class="form_header" style="">
      <div class="form_title" style="">
        <h2>到账信息回冲</h2>
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
					<td>${entity.zsname}</td>
					<td width="110px;">公司代码:</td>
					<td>${entity.bukrs}</td>
					<td width="110px;">客户编号:</td>
					<td>${entity.kunnr}</td>
					<td width="110px;">客户简称:</td>
					<td>${entity.sortl}</td>
				</tr>
				<tr class="field">
					<td width="110px;">凭证编号(流水号):</td>
					<td id="zbelnr">${entity.zbelnr}</td>
					<td width="110px;">到款主体:</td>
					<td>${recBukrs}</td>
					<td width="110px;">国际收支申报单号:</td>
					<td>${entity.zdoip}</td>
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
					<td colspan="4">${entity.ztext}</td>
					<td colspan="3">
						<div class="form_operate">
							<button id="B_backBelnr" type="butoon" onclick="return false;">回冲凭证</button>
							<button id="B_backAndDel" type="butoon" onclick="return false;">回冲并退回出纳</button>
							<button id="B_backReceive" type="butoon" onclick="return false;">回冲预收</button>
						</div>
					</td>
				</tr>
    		</tbody>
    	</table>
    	<br/>
    	<div style="width:100%;overflow: auto;">
	    	<table style="width:1900px;">
				<thead>
					<tr>
						<td class="title" colspan="20">到账分解信息</td>
					</tr>
					<tr class="ui-jqgrid-labels">
						<th width="30px" class="ui-state-default ui-th-column ui-th-ltr">选择</th>
						<th width="150px" class="ui-state-default ui-th-column ui-th-ltr">合同号</th>
						<th width="120px" class="ui-state-default ui-th-column ui-th-ltr">外向交货单</th>
						<th width="100px" class="ui-state-default ui-th-column ui-th-ltr">客户简称</th>
						<th width="60px" class="ui-state-default ui-th-column ui-th-ltr">公司代码</th>
						<th width="60px" class="ui-state-default ui-th-column ui-th-ltr">业务类型</th>
						<th width="60px" class="ui-state-default ui-th-column ui-th-ltr">样品款类型</th>
						<th width="100px" class="ui-state-default ui-th-column ui-th-ltr">AE预计收款金额</th>
						<th width="100px" class="ui-state-default ui-th-column ui-th-ltr">本次收款金额</th>
						<th width="80px" class="ui-state-default ui-th-column ui-th-ltr">手续费</th>
						<th width="80px" class="ui-state-default ui-th-column ui-th-ltr">预计出口日期</th>
						<th width="100px" class="ui-state-default ui-th-column ui-th-ltr">合同金额</th>
						<th width="60px" class="ui-state-default ui-th-column ui-th-ltr">合同币种</th>
						<th width="80px" class="ui-state-default ui-th-column ui-th-ltr">清帐汇率</th>
						<th width="120px" class="ui-state-default ui-th-column ui-th-ltr">备注</th>
						<th width="100px" class="ui-state-default ui-th-column ui-th-ltr">凭证号1</th>
						<th width="100px" class="ui-state-default ui-th-column ui-th-ltr">凭证号2</th>
						<th width="100px" class="ui-state-default ui-th-column ui-th-ltr">凭证号3</th>
						<th width="100px" class="ui-state-default ui-th-column ui-th-ltr">冲销凭证</th>
						<th width="100px" class="ui-state-default ui-th-column ui-th-ltr">快递单号</th>
					</tr>
				</thead>
	    		<tbody id="entityBody">
	    			<c:forEach var="sep" items="${sepList}">
	    			  <tr class="${sep.zposnr}">
	    			  	<td>
	    			  		<c:if test="${!empty sep.stblg}">
	    			  			<input type="checkbox" style="width:15px;" class="${sep.zposnr}" id="${sep.zbelnr}"/>
	    			  		</c:if>
	    			  	</td>
	    				<td width="150px">${sep.bstkd}</td>
						<td width="120px">${sep.vbeln}</td>
						<td width="100px">${sep.sortl}</td>
						<td width="60px">${sep.bukrs}</td>
						<td width="60px">
							<c:if test="${!empty sep.zrbl && 'B' eq sep.zrbl}">应收</c:if>
							<c:if test="${!empty sep.zrbl && 'C' eq sep.zrbl}">其他应收款</c:if>
							<c:if test="${!empty sep.zrbl && 'A' eq sep.zrbl}">预收</c:if>
							<c:if test="${!empty sep.zrbl && 'D' eq sep.zrbl}">样品款</c:if>
							<c:if test="${!empty sep.zrbl && 'E' eq sep.zrbl}">其它</c:if>
						</td>
						<td width="60px">
							<c:if test="${!empty sep.zsmc && '1' eq sep.zsmc}">报关</c:if>
							<c:if test="${!empty sep.zsmc && '2' eq sep.zsmc}">不报关</c:if>
							<c:if test="${!empty sep.zsmc && '3' eq sep.zsmc}">内销</c:if>
						</td>
						<td width="100px" style='text-align:right;'>${sep.aeamt}</td>
						<td width="100px" style='text-align:right;'>${sep.zcamount}</td>
						<td width="80px" style='text-align:right;'>${sep.zhc}</td>
						<td width="80px">${sep.zpodat}</td>
						<td width="100px" style='text-align:right;'>${sep.zbamt}</td>
						<td width="60px">${sep.waers}</td>
						<td width="80px" style='text-align:right;'>${sep.kursf}</td>
						<td width="120px">${sep.zanote}</td>
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
						<td width="100px">${sep.ztnum}</td>
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
	//回冲按钮事件
	$("#B_backBelnr").click(backBelnr);
	//回冲并退回出纳事件
	$("#B_backAndDel").click(backAndDelete);
	//回冲预收事件
	$("#B_backReceive").click(backReceive);
});

/**
 * 回冲预收
 */
function backReceive(){
	if($("#entityBody input:checked").length!=1){
		oAlert("请选择单笔笔预收款进行回冲预收!");
		return;
	}
	if(!confirm("确认对当前到账信息进行回冲预收凭证?")){
		return;
	}
	var zposnr = $("#entityBody input:checked")[0].className;
	var params = {
		'zposnr':zposnr,
		'zbelnr':$('#zbelnr').text()
	};
	$.post("backFlushReceive",params).done(function(ret) {
		if ("success" == ret) {
			alert("操作成功");
			window.location.reload();
		} else {
			oAlert("操作失败:" + ret);
		}
	});
}

/**
 * 回冲凭证
 */
function backBelnr(){
	if(!confirm("确认对当前到账信息回冲凭证?")){
		return;
	}
	var params = {
		'zbelnr':$('#zbelnr').text()
	};
	$.post("backFlushBelnr",params).done(function(ret) {
		if ("success" == ret) {
			oAlert("操作成功");
			window.location.reload();
		} else {
			oAlert("操作失败:" + ret);
		}
	});
}

/**
 * 回冲并退回出纳
 */
function backAndDelete(){
	if(!confirm("确认回冲并退回给出纳?此操作将删除当前已保存的到账信息!")){
		return;
	}
	var params = {
		'zbelnr':$('#zbelnr').text()
	};
	$.post("backFlushAndDelete",params).done(function(ret) {
		if ("success" == ret) {
			oAlert("操作成功");
			window.close();
		} else {
			oAlert("操作失败:" + ret);
		}
	});
}

function viewBelnr(belnr,bukrs,data){
	var url = ctx +"/ficoManage/belnrSearch?belnr="+belnr+"&bukrs="+bukrs+"&gjahr="+data.substring(0,4);
	window.open(url,"总帐查询");
}

</script>
</html>