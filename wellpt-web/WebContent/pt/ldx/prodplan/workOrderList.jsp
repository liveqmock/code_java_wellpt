<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<%@ include file="/pt/dyform/dyform_css.jsp"%>
<%@ include file="/pt/dyform/dyform_js.jsp"%>
<%@ include file="/pt/dyform/dyform_ctljs.jsp"%>
<style type="text/css">
@media print{
	.noprint {
		display:none;
	}
	.showAtPrint{
		display:block;
	}
}
.plan td{
	padding: 0 0px;
}
.plan div{
	word-break: keep-all;
	overflow: hidden;
	text-overflow: ellipsis;
	white-space: nowrap;
}
</style>
<title>工单列表</title>
</head>
<body>
	<div class="div_body" style="">
		<form action="${ctx}/productionPlan/workOrder/search" id="workOrderform" class="dyform">
			<div class="form_header noprint" style="">
				<div class="form_title">
					<h2>工单列表</h2>
				</div>
				<div id="toolbar" class="form_toolbar">
					<div class="form_operate" style="float:left;">
						<table>
							<tbody>
								<tr class="field">
									<td width="60px" style="color:#2e2e2e;">生管:</td>
									<td width="80px">
										<select name="sgNum" id="sgNum">
											<c:forEach var="sg" items="${sgList}">
												<option value="${sg}"
													<c:if test="${!empty sg && sg eq sgNum}">selected</c:if>
													>${sg}</option>
											</c:forEach>
										</select>
									</td>
									<td width="60px"  style="color:#2e2e2e;">线号:</td>
									<td width="200px">
										<select name="zxh" id="zxh">
											<c:forEach var="line" items="${lineList}">
												<option value="${line}"
													<c:if test="${!empty line && line eq zxh}">selected</c:if>
													>${line}</option>
											</c:forEach>
										</select>
									</td>
									<td>
										<button id="form_search" type="button">查询</button>
										<button id="form_print" type="button" onclick="javascript:window.print();">打印</button>
										<button id="form_close" type="button">关闭</button>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
					<div class="form_operate" style="float:left;">
						
					</div>
				</div>
			</div>
			<table>
				<tr class="title">
					<td width="60px" rowspan="3">线别</td>
					<td width="60px" rowspan="3">客户</td>
					<td width="80px" rowspan="3">生产订单</td>
					<td width="80px" rowspan="3">销售订单</td>
					<td width="30px" rowspan="3">行项</td>
					<td width="80px" rowspan="3">物料ID</td>
					<td width="180px" rowspan="3">物料描述</td>
					<td width="60" rowspan="3">订单量</td>
					<td width="60" rowspan="3">交期</td>
					<td width="60" rowspan="3">MPS上线日期</td>
					<td width="80" colspan="2">标准工时</td>
					<td width="60" rowspan="3">计划量</td>
					<td width="60" rowspan="3">完成量</td>
					<td width="120" colspan="3">${days[0]}</td>
					<td width="120" colspan="3">${days[1]}</td>
					<td width="120" colspan="3">${days[2]}</td>
					<td width="200" colspan="3">备注</td>
				</tr>
				<tr class="title">
					<td width="40" rowspan="2">总装</td>
					<td width="40" rowspan="2">包装</td>
					<td width="120" colspan="3">${weeks[0]}</td>
					<td width="120" colspan="3">${weeks[1]}</td>
					<td width="120" colspan="3">${weeks[2]}</td>
					<td width="60" rowspan="2">状态</td>
					<td width="80" rowspan="2">物料状况</td>
					<td width="60" rowspan="2">其他</td>
				</tr>
				<tr class="title">
					<td width="40">A</td>
					<td width="40">B</td>
					<td width="40">C</td>
					<td width="40">A</td>
					<td width="40">B</td>
					<td width="40">C</td>
					<td width="40">A</td>
					<td width="40">B</td>
					<td width="40">C</td>
				</tr>
				<c:forEach var="plan" items="${allPlans}" >
					<tr class="plan" style="line-height: 20px;height:20px;">
						<td width="40px"><div style="width:40px">${plan.lineNo}</div></td>
						<td width="60px"><div style="width:60px">${plan.custNo}</div></td>
						<td width="80px"><div style="width:80px">${plan.productOrder}</div></td>
						<td width="80px"><div style="width:80px">${plan.saleOrder}</div></td>
						<td width="30px"><div style="width:30px">${plan.orderLineNo}</div></td>
						<td width="80px"><div style="width:80px">${plan.wlId}</div></td>
						<td width="180px"><div style="width:180px">${plan.wlDesc}</div></td>
						<td width="60px"><div style="width:60px;text-align: right;">${plan.orderAmt}</div></td>
						<td width="60px"><div style="width:60px">${plan.sdExpDate}</div></td>
						<td width="60px"><div style="width:60px">${plan.mpsDate}</div></td>
						<td width="40px"><div style="width:40px;text-align: right;">${plan.zzHours}</div></td>
						<td width="40px"><div style="width:40px;text-align: right;">${plan.bzHours}</div></td>
						<td width="60px"><div style="width:60px;text-align: right;">${plan.planAmt}</div></td>
						<td width="60px"><div style="width:60px;text-align: right;">${plan.completeAmt}</div></td>
						<td width="40px"><div style="width:40px;text-align: right;"><c:if test="${0 lt plan.day1A}">${plan.day1A}</c:if></div></td>
						<td width="40px"><div style="width:40px;text-align: right;"><c:if test="${0 lt plan.day1B}">${plan.day1B}</c:if></div></td>
						<td width="40px"><div style="width:40px;text-align: right;"><c:if test="${0 lt plan.day1C}">${plan.day1C}</c:if></div></td>
						<td width="40px"><div style="width:40px;text-align: right;"><c:if test="${0 lt plan.day2A}">${plan.day2A}</c:if></div></td>
						<td width="40px"><div style="width:40px;text-align: right;"><c:if test="${0 lt plan.day2B}">${plan.day2B}</c:if></div></td>
						<td width="40px"><div style="width:40px;text-align: right;"><c:if test="${0 lt plan.day2C}">${plan.day2C}</c:if></div></td>
						<td width="40px"><div style="width:40px;text-align: right;"><c:if test="${0 lt plan.day3A}">${plan.day3A}</c:if></div></td>
						<td width="40px"><div style="width:40px;text-align: right;"><c:if test="${0 lt plan.day3B}">${plan.day3B}</c:if></div></td>
						<td width="40px"><div style="width:40px;text-align: right;"><c:if test="${0 lt plan.day3C}">${plan.day3C}</c:if></div></td>
						<td width="60px"><div style="width:60px">${plan.status}</div></td>
						<td width="80px"><div style="width:80px">${plan.wlStatus}</div></td>
						<td width="60px"><div style="width:60px">${plan.desc}</div></td>
					</tr>
				</c:forEach>
			</table>
		<br/>
		
			<table>
				<tr class="title">
					<td width="80px">生产顺序</td>
					<td width="110px">工单号</td>
					<td width="200px">物料描述</td>
					<td width="80px">今日计划量</td>
					<td width="80px">完成数量</td>
					<td width="80px" class="noprint">实际完工数量</td>
					<td width="80px">状态</td>
					<td width="150px" title="双击修改备注信息">备注</td>
					<td width="480px;">条形码</td>
				</tr>
				<c:forEach var="wo" items="${planList}" >
					<tr class="ui-widget-content jqgrow ui-row-ltr">
						<td width="80px" style="text-align: center;">${wo.zsxh}</td>
						<td width="110px">${wo.zgdh}</td>
						<td width="200px">${wo.zgrun}</td>
						<td width="80px" style="text-align: right;"><fmt:formatNumber value="${wo.zjrjhl}" pattern="#,##0"/></td>
						<td width="80px" style="text-align: right;"><fmt:formatNumber value="${wo.zwczt}" pattern="#,##0"/></td>
						<td width="80px" style="text-align: right;" class="noprint"><fmt:formatNumber value="${wo.sjwgsl}" pattern="#,##0"/></td>
						<td width="80px">${wo.zzt}</td>
						<td width="150px" id="${wo.zgdh}" class="remark" title="双击修改备注信息">${wo.remark}</td>
						<td width="480px;" style="border-left: 0;"><img src="${ctx}/barCode?code=${wo.zgdh}&height=17&width=7" style="margin-left: 80px;"/></td>
					</tr>
				</c:forEach>
				<c:forEach var="wo" items="${changeList}" >
					<tr class="ui-widget-content jqgrow ui-row-ltr">
						<td width="80px" style="text-align: center;">${wo.zsxh}</td>
						<td width="110px">${wo.zgdh}</td>
						<td width="200px">${wo.zgrun}</td>
						<td width="80px" style="text-align: right;"><fmt:formatNumber value="${wo.zjrjhl}" pattern="#,##0"/></td>
						<td width="80px" style="text-align: right;"><fmt:formatNumber value="${wo.zwczt}" pattern="#,##0"/></td>
						<td width="80px" style="text-align: right;" class="noprint"><fmt:formatNumber value="${wo.sjwgsl}" pattern="#,##0"/></td>
						<td width="80px">${wo.zzt}</td>
						<td width="150px" id="${wo.zgdh}" class="remark" title="双击修改备注信息">${wo.remark}</td>
						<td width="480px;" style="border-left: 0;"><img src="${ctx}/barCode?code=${wo.zgdh}&height=17&width=7" style="margin-left: 80px;"/></td>
					</tr>
				</c:forEach>
			</table>
		</form>
	</div>
</body>
<script type="text/javascript">
$(document).ready(function() {
	var div_body_width = 1600;
	$(".form_header").css("width", div_body_width - 5);
	$(".div_body").css("width", div_body_width);
	$("#form_close").click(function() {
		window.close();
	});
	$("#form_search").click(function() {
		var width = $('#zxh').outerWidth();
		if($('#zxh').val()==''){
			oAlert2("请选择线别!");
			return;
		}
		$('#workOrderform').submit();
	});
	$("#sgNum").change(function() {
		JDS.call({
			service : "productionPlanService.getSelectItemByParent",
			data : [ 'zsg',$("#sgNum").val(),'zxh' ],
			async : false,
			success : function(result) {
				if(result.data.res=="success"){
					$('#zxh').html(result.data.data);
					$('#zxh').css("width",width);
				}
			}
		});
	});
	$(".remark").dblclick(function(){
		editRemark(this);
	});
});

/**
 * 编辑备注
 */
function editRemark(obj){
	var remark = obj.innerText;
	var input = "<input type='text' id='"+obj.id+"' value='"+remark+"' title='"+remark+"' onblur='saveRemark(this)'/>";
	$(obj).html(input);
}

/**
 * 保存备注
 */
function saveRemark(obj){
	var params = {
		'value':obj.value,
		'zgdh':obj.id
	};
	$.ajax({
		url : "saveRemark",
		type : "post",
		data : params ,
		dataType : "json",
		contentType : "application/x-www-form-urlencoded; charset=utf-8"
	}).done(function(ret) {
		if("success"==ret[0]){
			$(obj).parent($("#"+obj.id).text(obj.value));
		}else{
			oAlert("保存失败:"+ret[1]);
			$(obj).parent($("#"+obj.id).text(obj.title));
		}
	});
}
</script>
</html>