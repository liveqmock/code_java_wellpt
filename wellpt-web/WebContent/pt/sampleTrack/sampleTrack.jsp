<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>样品跟踪</title>
<style type="text/css">
.table1 {
	width: 100%;
	border-color: gray;
	color: #2E2E2E;
	font-family: "Microsoft YaHei";
	font-size: 12px;
	text-align: center;
}

.table1 tr {
	white-space: nowrap;
}

.table1 th {
	font-family: "Microsoft YaHei";
	font-size: 12px;
	text-align: center;
	border: 1px solid #C9C9C9;
	border-right: 0px solid #C9C9C9;
}

.table1 td {
	border: medium none;
	font-family: "Microsoft YaHei";
	font-size: 12px;
	height: 20px;
	text-align: center;
	border-bottom: 1px solid #C9C9C9;
	border-left: 1px solid #C9C9C9;
	border-right: 0px solid #C9C9C9;
}
</style>
</head>
<body>
	<%@ include file="/pt/common/taglibs.jsp"%>
	<%@ include file="/pt/common/meta.jsp"%>
	<%@ include file="/pt/dyform/dyform_css.jsp"%>
	<%@ include file="/pt/dyform/dyform_js.jsp"%>
	<%@ include file="/pt/dyform/dyform_ctljs.jsp"%>
	<div class="div_body"
		style="overflow-X: scroll; height: 550px; width: 1100px">
		<div class="form_header" style="">
			<div class="form_title">
				<h2>样品跟踪</h2>
			</div>
			<div id="toolbar" class="form_toolbar">
				<div class="form_operate">
					<button id="form_search" type="button">查询条件</button>
					<button id="form_export" type="button" onclick="return false;">报表导出</button>
					<button id="form_close" type="button">关闭</button>
				</div>
			</div>
		</div>
		<div id="searchDiv" style="float: left; width: 75%;">
			<div style="display: none;" id="searchDIV">
				<form:form name="form1" modelAttribute="sample" id="form1"
					action="${ctx}/sampleTrack/sampleTrack" method="post">
					<table style="font-size: 12px; height: 150px;" border="0">
						<inpute type="hidden" name="power" value="${power}" />
						<inpute type="hidden" name="id" value="${id}" />
						<tr class="field">
							<td>样品单号:</td>
							<td><input type="text" name="formSampleOrderId"
								style="height: 16px; width: 115px;"></td>
							<td>到</td>
							<td><input type="text" name="toSampleOrderId"
								style="height: 16px; width: 115px;"></td>
							<td>行项目:</td>
							<td><input type="text" name="sampleTypeNo"
								style="height: 16px; width: 115px;"></td>
							<td>项目状态:</td>
							<td><select name="projectStatus" id="projectStatus"
								style="font-size: 12px; height: 25px">
									<option value=""></option>
									<option value="0">待EP定型</option>
									<option value="1">已EP定型</option>
							</select></td>
						</tr>
						<tr class="field">
							<td>单支样品序号:</td>
							<td><input type="text" name="formSampleId"
								style="height: 16px; width: 115px;"></td>
							<td class="fisherFilterTypeTd2">到</td>
							<td><input type="text" name="toSampleId"
								style="height: 16px; width: 115px;"></td>
							<td>申请人员:</td>
							<td><input type="text" name="applyUser"
								style="height: 16px; width: 115px;"></td>
							<td>申请部门:</td>
							<td><input type="text" name="applyDept"
								style="height: 16px; width: 115px;"></td>


						</tr>
						<tr class="field">
							<td>申请日期:</td>
							<td><input type="text" name="fromApplyDate"
								style="height: 16px; width: 115px;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"
					class="Wdate required"></td>
							<td class="fisherFilterTypeTd2">到</td>
							<td><input type="text" name="toApplyDate"
								style="height: 16px; width: 115px;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"
					class="Wdate required"></td>
							<td>产品名称:</td>
							<td><input type="text" name="productName"
								style="height: 16px; width: 115px;"></td>
							<td>交期Week:</td>
							<td><input type="text" name="samplePredictDDateWeek"
								style="height: 16px; width: 115px;"></td>
						</tr>
						<tr>
							<td>完工日期:</td>
							<td><input type="text" name="formCompleteDate"
								style="height: 16px; width: 115px;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"
					class="Wdate required"></td>
							<td>到</td>
							<td><input type="text" name="toCompleteDate"
								style="height: 16px; width: 115px;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"
					class="Wdate required"></td>
							<td>产品ID:</td>
							<td><input type="text" name="productId"
								style="height: 16px; width: 115px;"></td>
							<td>客户代码:</td>
							<td><input type="text" name="customerId"
								style="height: 16px; width: 115px;"></td>
						</tr>
						<tr>
							<td>生产方式:</td>
							<td colspan="3"><input type="checkbox"
								name="productModeList" id="productModeList" value="1" />&nbsp;自制&nbsp;&nbsp;
								<input type="checkbox" name="productModeList"
								id="productModeList" value="2" />&nbsp;外购&nbsp;&nbsp; <input
								type="checkbox" name="productModeList" id="productModeList"
								value="3" />&nbsp;外购&自制</td>

							<td>样品单状态:</td>
							<td colspan="3"><input type="checkbox"
								name="sampleOrderStatusList" id="sampleOrderStatusList"
								value="1" />&nbsp;待制作&nbsp;&nbsp; <input type="checkbox"
								name="sampleOrderStatusList" id="sampleOrderStatusList"
								value="2" />&nbsp;待邮寄&nbsp;&nbsp; <input type="checkbox"
								name="sampleOrderStatusList" id="sampleOrderStatusList"
								value="3" />&nbsp;已完成&nbsp;&nbsp; <input type="checkbox"
								name="sampleOrderStatusList" id="sampleOrderStatusList"
								value="4" />&nbsp;已取消</td>
						</tr>
						<tr>
							<td>距离交期天数:</td>
							<td><input type="text" name="formDay"
								style="height: 16px; width: 115px;"></td>
							<td>到</td>
							<td><input type="text" name="toDay"
								style="height: 16px; width: 115px;"></td>
							<td>客户反馈结果:</td>
							<td><select name="customerResult" id="customerResult"
								style="font-size: 12px; height: 25px">
									<option value=""></option>
									<option value="1">已维护</option>
									<option value="0">待维护</option>
							</select></td>
							<td>材料成本:</td>
							<td><input type="text" name="bomCost"
								style="height: 16px; width: 115px;"></td>
						</tr>
						<tr>
							<td>品质异常:</td>
							<td><select id="qcExceptStatus" name="qcExceptStatus"
								style="font-size: 12px; height: 25px">
									<option value=""></option>
									<option value="0">正常</option>
									<option value="1">异常</option>
							</select></td>
							<td>交期异常:</td>
							<td><select id="prodExceptStatus" name="prodExceptStatus"
								style="font-size: 12px; height: 25px">
									<option value=""></option>
									<option value="0">正常</option>
									<option value="1">异常</option>
							</select></td>
							<td class="" colspan="6"><input type="submit" id="search"
								value="查找"></td>
						</tr>

					</table>
				</form:form>
			</div>
			<div
				style="overflow-X: scroll; height: 380px; width: 1050px; position: relative;">
				<table class="table1"
					style="font-size: 12px; width: 5500px; border: solid 2px B2D9F6;">
					<thead>
						<tr style="outline: medium none;" class="table1">
							<th>样品单状态</th>
							<th>项目状态</th>
							<th>样品类型</th>
							<th>样品单号</th>
							<th>行项目</th>
							<th>单支样品序号</th>
							<th>产品ID</th>
							<th>产品名称</th>
							<th>样品数量</th>
							<th>申请部门</th>
							<th>申请人员</th>
							<th>跟单人员</th>
							<th>客户代码</th>
							<th>客户潜力级别</th>
							<th>是否报关</th>
							<th>是否开票</th>
							<th>样品费承担方</th>
							<th>运费承担方</th>
							<th>模具费承担方</th>
							<th>付款方式</th>
							<th>送样目的</th>
							<th>技术要求</th>
							<th>测试类型</th>
							<th>测试标准</th>
							<th>生产方式</th>
							<th>包装方式</th>
							<th>标签要求</th>
							<th>&nbsp;&nbsp;&nbsp;申请日期&nbsp;&nbsp;&nbsp;</th>
							<th>RSM审批日期</th>
							<th>PM审批日期</th>
							<th>CS审批日期</th>
							<th>样品课及外购组回复日期</th>
							<th>AE确认交期</th>
							<th>相关人员确认日期</th>
							<th>签发Week</th>
							<th>&nbsp;&nbsp;&nbsp;目标交期&nbsp;&nbsp;&nbsp;</th>
							<th>样品预计交期</th>
							<th>交期Week</th>
							<th>样品交货绝期</th>
							<th>&nbsp;&nbsp;&nbsp;完工日期&nbsp;&nbsp;&nbsp;</th>

							<th>生产时间</th>
							<th>生产状态</th>
							<th>生产状态备注说明</th>

							<th>领样Week</th>
							<th>预计送样目的地</th>
							<th>实际送样目的地</th>
							<th>&nbsp;&nbsp;&nbsp;送样时间&nbsp;&nbsp;&nbsp;</th>
							<th>快递单号</th>
							<th>品保判定结果</th>
							<th>品保二次判定结果</th>
							<th>检验人员</th>
							<th>品保检验项目</th>
							<th>品保检验描述</th>
							<th>品质异常描述</th>
							<th>品保检验时间</th>
							<th>客户反馈结果</th>
							<th>责任部门反馈（品质异常）</th>
							<th>责任部门反馈（交期异常）</th>
							<th>库存数量</th>
							<th>行项目备注说明</th>
							<th>抬头备注信息</th>
							<th>领样说明</th>
							<th>单价(RMB)</th>
							<th>材料成本(RMB)</th>
							<th>人工成本(RMB)</th>
							<th>包装成本(RMB)</th>
							<th>样品成本(RMB)</th>
							<th>PI金额</th>
							<th>PI金额单位</th>
							<th style="border-right: 1px solid #C9C9C9;">距离交期天数</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="entity" items="${sample}">
							<tr class="table1" onclick="showAndUpdate(${entity.lineitemid})"
								onmouseover="" onmouseout="" style="cursor: pointer;">
								<td style="width: 65px;"><c:if
										test="${entity.sampleorderstatus=='1'}">待制作</c:if> <c:if
										test="${entity.sampleorderstatus=='2'}">待邮寄</c:if> <c:if
										test="${entity.sampleorderstatus=='3'}">已完成</c:if> <c:if
										test="${entity.sampleorderstatus=='4'}">已取消</c:if></td>
								<td style="width: 65px"><c:if
										test="${entity.projectstatus=='0'}">待EP定型</c:if> <c:if
										test="${entity.projectstatus=='1'}">已EP定型
								</c:if></td>
								<td style="width: 65px"><c:if
										test="${entity.sampleorderid=='8'}">CFL</c:if> <c:if
										test="${entity.sampleorderid=='9'}">LED</c:if> <c:if
										test="${entity.sampleorderid=='7'}">LF
							</c:if></td>
								<td style="width: 65px">${(entity.sampleorderid)}</td>
								<td style="width: 65px">${fn:substring(entity.sampleid,0,7)}</td>
								<td style="width: 65px">${(entity.sampleid)}</td>
								<td style="width: 65px">${(entity.productid)}</td>
								<td style="width: 50px">${(entity.productname)}</td>
								<td style="width: 65px">${(entity.samplenum)}</td>
								<td style="width: 65px">${(entity.applydept)}</td>
								<td style="width: 65px">${(entity.applyuser)}</td>
								<td style="width: 65px">${(entity.documentaryuser)}</td>
								<td style="width: 65px">${(entity.customerid)}</td>
								<td style="width: 65px">${(entity.customerpotentialrank)}</td>
								<td style="width: 65px"><c:if
										test="${entity.iscustoms=='0'}">否</c:if> <c:if
										test="${entity.iscustoms=='1'}">是
								</c:if></td>
								<td style="width: 65px"><c:if
										test="${entity.isinvoice=='0'}">否</c:if> <c:if
										test="${entity.isinvoice=='1'}">是</c:if></td>
								<td style="width: 65px">${(entity.samplecharge)}</td>
								<td style="width: 65px">${(entity.freight)}</td>
								<td style="width: 65px">${(entity.mouldcharge)}</td>
								<td style="width: 65px">${(entity.paymode)}</td>
								<td style="width: 65px">${(entity.sampleaim)}</td>
								<td style="width: 65px">${(entity.techrequire)}</td>
								<td style="width: 65px">${(entity.testmode)}</td>
								<td style="width: 65px">${(entity.teststandard)}</td>
								<td style="width: 65px"><c:if
										test="${entity.productmode=='1'}">自制</c:if> <c:if
										test="${entity.productmode=='2'}">外购</c:if> <c:if
										test="${entity.productmode=='3'}">外购&自制</c:if></td>
								<td style="width: 65px">${(entity.packmode)}</td>
								<td style="width: 65px">${(entity.labelrequire)}</td>
								<td style="width: 65px">${(entity.applydate)}</td>
								<td style="width: 65px">${(entity.rsmauditingdate)}</td>
								<td style="width: 65px">${(entity.pmauditingdate)}</td>
								<td style="width: 65px">${(entity.customergroupauditingdate)}</td>
								<td style="width: 65px">${(entity.techoutgroupreplydate)}</td>
								<td style="width: 65px">${(entity.aesuredelivery)}</td>
								<td style="width: 65px">${(entity.aesuredate)}</td>
								<td style="width: 65px">${(entity.signweek)}</td>
								<td style="width: 65px">${(entity.aimdate)}</td>
								<td style="width: 65px">${(entity.samplepredictddate)}</td>
								<td style="width: 65px">${(entity.samplepredictddateweek)}</td>
								<td style="width: 65px">${(entity.sampledeliveryperiod)}</td>
								<td style="width: 65px">${(entity.completedate)}</td>
								<td style="width: 65px"><c:if
										test="${!empty entity.prodcosttime }">
								${entity.prodcosttime}小时
							</c:if></td>
								<td style="width: 65px"><c:if
										test="${entity.prodstatus=='1'}">物料调拨</c:if> <c:if
										test="${entity.prodstatus=='2'}">物料采购</c:if> <c:if
										test="${entity.prodstatus=='3'}">生产中</c:if> <c:if
										test="${entity.prodstatus=='4'}">已完成</c:if></td>
								<td style="width: 65px">${(entity.prodstatusmemo)}</td>
								<td style="width: 65px">${(entity.week)}</td>
								<td style="width: 65px">${(entity.presampledestination)}</td>
								<td style="width: 65px">${(entity.sampledestination)}</td>
								<td style="width: 65px">${(entity.sampledate)}</td>
								<td style="width: 65px">${(entity.expressagenum)}</td>
								<td style="width: 65px"><c:if
										test="${entity.qccheckresult=='1'}">OK</c:if> <c:if
										test="${entity.qccheckresult=='2'}">NG</c:if> <c:if
										test="${entity.qccheckresult=='3'}">放行</c:if> <c:if
										test="${entity.qccheckresult=='4'}">特采出货</c:if></td>
								<td style="width: 65px"><c:if
										test="${entity.qcsecondresult=='1'}">OK</c:if> <c:if
										test="${entity.qcsecondresult=='2'}">NG</c:if> <c:if
										test="${entity.qcsecondresult=='3'}">放行</c:if> <c:if
										test="${entity.qcsecondresult=='4'}">特采出货
						        </c:if></td>
								<td style="width: 65px">${(entity.qcname)}</td>
								<td style="width: 65px">${(entity.qccheckitem)}</td>
								<td style="width: 65px">${(entity.qccheckmemo)}</td>
								<td style="width: 65px">${(entity.qcexceptcause)}</td>
								<td style="width: 65px">${(entity.qccosttime)}小时</td>
								<td style="width: 65px">${(entity.customerresult)}</td>
								<td style="width: 65px">${(entity.qcexceptreply)}</td>
								<td style="width: 65px">${(entity.prodexceptreply)}</td>
								<td style="width: 65px">${(entity.stocknubmer)}</td>
								<td style="width: 65px">${(entity.memo)}</td>
								<td style="width: 65px">${(entity.topmemo)}</td>
								<td style="width: 65px">${(entity.ledsamplememo)}</td>
								<td style="width: 65px">${(entity.unitprice)}</td>
								<td style="width: 65px">${(entity.bomcost)}</td>
								<td style="width: 65px">${(entity.labourcost)}</td>
								<td style="width: 65px">${(entity.packcost)}</td>
								<td style="width: 65px">${(entity.samplecost)}</td>
								<td style="width: 65px">${(entity.pimoney)}</td>
								<td style="width: 65px">${(entity.pimoneyunit)}</td>
								<td style="width: 65px; border-right: 1px solid #C9C9C9;">${(entity.day)}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div style="position: relative;">
				<font color="blue" size="2px">待EP定型数量:</font><font color="red"
					size="3px">&nbsp;${dep}</font> <font color="blue" size="2px">已EP定型数量:</font><font
					color="red" size="3px">&nbsp;${yep}</font> <font color="blue"
					size="2px">待制作数量:</font><font color="red" size="3px">&nbsp;${dzz}</font>
				<font color="blue" size="2px">待邮寄数量:</font><font color="red"
					size="3px">&nbsp;${sumStockNumber}</font> <font color="blue"
					size="2px">样品单完结数量(已寄出):</font><font color="red" size="3px">&nbsp;${ywc}</font>
				<font color="blue" size="2px">已取消数量:</font><font color="red"
					size="3px">&nbsp;${yqx}</font>
			</div>
			<div style="position: relative;">
				<font color="blue" size="2px">样品成本总和(RMB):</font><font color="red"
					size="3px">&nbsp;${sampleCostTemp}</font> <font color="blue"
					size="2px">PI金额总和:</font><font color="red" size="3px">&nbsp;${PIMoneyUSDTemp}(USD)+${PIMoneyRMBTemp}(RMB)</font>
			</div>
		</div>

	</div>
	<script type="text/javascript">
function showAndUpdate(id){
	window.open(ctx + "/sampleTrack/sampleTrack/update?lineItemId="+id);
}
function exportData(){
	/* $.ajax({
		url:"${ctx}/sampleTrack/sampleTrack/export",
		type:"POST",
		dataType:"text",
		async: false,
		data:{},
		success: function(res){ 
			alert(res.text());
		},
		error: function() {
			alert('error');
		}
	}) */
	var url = ctx + "/sampleTrack/sampleTrack/export";
	$("#form1").attr("action",url);
	$("#form1").submit();
}
$(document).ready(function(){
	/* $('#form1').on('submit', function(e) {
        e.preventDefault(); // <-- important
        $(this).ajaxSubmit({
            target: '#output'
        });
    }); */
	$("#form_close").click(function(){
		window.close();
	});
	$("#form_search").click(function(){
		$("#searchDIV").toggle(1000);
	});
	$("#form_export").click(exportData);
});
</script>
</body>
</html>