<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>样品跟踪维护</title>
</head>
<body>
	<%@ include file="/pt/common/taglibs.jsp"%>
	<%@ include file="/pt/common/meta.jsp"%>
	<%@ include file="/pt/dyform/dyform_css.jsp"%>
	<%@ include file="/pt/dyform/dyform_js.jsp"%>
	<%@ include file="/pt/dyform/dyform_ctljs.jsp"%>
	<div class="div_body" style="">
		<div class="form_header" style="">
			<div class="form_title">
				<h2>样品跟踪维护</h2>
			</div>
			<div id="toolbar" class="form_toolbar">
				<div class="form_operate">
					<button id="form_save" type="button" onclick="save()">保存</button>
					<button id="form_close" type="button">关闭</button>
				</div>
			</div>
		</div>
		<form:form action="" id="capacityform" class="dyform"
			modelAttribute="sampleTrack" method="post">
			<input type="hidden" name="lineitemid" value="${sample.lineitemid }" />
			<table style="font-size: 12px;">
				<tr class="field">
					<td align="right">产品ID</td>
					<td><input type="text" name="productid" id="productId"
						value="${sample.productid }" />
					</td>
				</tr>
				<tr class="field">
					<td align="right">产品名称</td>
					<td><textarea name="productname" cols="50" rows="3"
							id="productName"
							style="width: 100%; border: solid 1px B2D9F6; margin: 0;">${sample.productname }</textarea>
					</td>
				</tr>
				<tr class="field">
					<td align="right">生产方式</td>
					<td><select name="productmode" id="productMode">
							<option value=""></option>
							<option value="1"
								<c:if test="${sample.productmode=='1' }">selected</c:if>>自制</option>
							<option value="2"
								<c:if test="${sample.productmode=='2' }">selected</c:if>>外购</option>
							<option value="3"
								<c:if test="${sample.productmode=='3' }">selected</c:if>>外购&amp;自制</option>
					</select>
					</td>
				</tr>
				<tr class="field">
					<td align="right">完工日期</td>
					<td><input type="text" name="completedate" id="completeDate"
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"
						class="Wdate required" value="${sample.completedate }" />
					</td>
				</tr>
				<tr class="field">
					<td align="right">样品课及外购组回复日期</td>
					<td><input type="text" name="techoutgroupreplydate"
						id="techOutGroupReplyDate"
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"
						class="Wdate required" value="${sample.techoutgroupreplydate }" />
					</td>
				</tr>
				<tr class="field">
					<td align="right">样品交货绝期</td>
					<td><input type="text" name="sampledeliveryperiod"
						id="sampleDeliveryPeriod"
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"
						class="Wdate required" value="${sample.sampledeliveryperiod }" />
					</td>
				</tr>
				<tr class="field">
					<td align="right">客户代码</td>
					<td><input type="text" name="customerid" id="customerId"
						value="${sample.customerid }" />
					</td>
				</tr>
				<tr class="field">
					<td align="right">申请部门</td>
					<td><input type="text" name="applydept" id="applyDept"
						value="${sample.applydept }" />
					</td>
				</tr>
				<tr class="field">
					<td align="right">行项目备注说明</td>
					<td><input type="text" name="memo" id="memo"
						value="${sample.memo }" />
					</td>
				</tr>
				<tr class="field">
					<td align="right">领样说明</td>
					<td><input type="text" name="ledsamplememo" id="ledSampleMemo"
						value="${sample.ledsamplememo }" />
					</td>
				</tr>
				<tr class="field">
					<td align="right">单价(RMB)</td>
					<td><input type="text" name="unitprice" id="unitprice"
						value="${sample.unitprice }" />
					</td>
				</tr>
				<tr class="field">
					<td align="right">材料成本(RMB)</td>
					<td><input type="text" name="bomcost" id="bomcost"
						value="${sample.bomcost }" />
					</td>
				</tr>
				<tr class="field">
					<td align="right">人工成本(RMB)</td>
					<td><input type="text" name="labourcost" id="labourcost"
						value="${sample.labourcost }" />
					</td>
				</tr>
				<tr class="field">
					<td align="right">包装成本(RMB)</td>
					<td><input type="text" name="packcost" id="packcost"
						value="${sample.packcost }" />
					</td>
				</tr>
				<tr class="field">
					<td align="right">PI金额</td>
					<td><input type="text" name="pimoney" id="piMoney"
						value="${sample.pimoney }" />
					</td>
				</tr>
				<tr class="field">
					<td align="right">PI金额单位</td>
					<td><input type="text" name="pimoneyunit" id="piMoneyUnit"
						value="${sample.pimoneyunit }" />
					</td>
				</tr>
			</table>

		</form:form>
		<input type="hidden" id="viewMode" name="pageMode" value=""
			notClear="true" />
	</div>
	<script type="text/javascript">
		function save() {
			$("#capacityform").attr("action",
					"${ctx}/sampleTrack/sampleTrack/save");
			$("#capacityform").submit();
		}
	</script>
	<script type="text/javascript">
	$(document).ready(function() {
		$("#form_close").click(function(){
			window.close();
		});
	});
	</script>
</body>
</html>