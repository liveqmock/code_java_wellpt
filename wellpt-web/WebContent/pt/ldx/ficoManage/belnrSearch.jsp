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
<title>总账查询</title>
</head>
<body>
  <div class="div_body" style="">
    <div class="form_header" style="">
      <div class="form_title" style="">
        <h2>总账查询</h2>
      </div>
    </div>
    <form action="${ctx}/ficoManage/belnrSearch" id="belnrInfoForm" class="dyform">
    	<table>
    		<tbody>
    			<tr>
					<td class="title" colspan="7">总账查询</td>
				</tr>
				<tr class="field">
					<td width="110px;">*凭证编号:</td>
					<td><input type="text" name="belnr" id="belnr" style="border:0;width:100%;height:90%;" value="${belnr}"/></td>
					<td width="110px;">*公司代码:</td>
					<td>
						<select name="bukrs" style='width: 100%;border: 0;padding: 0;margin: 0;' value='${bukrs}'>
							<option value='1000' <c:if test="${'1000' eq bukrs}">selected</c:if>>1000</option>
							<option value='2000' <c:if test="${'2000' eq bukrs}">selected</c:if>>2000</option>
							<option value='3000' <c:if test="${'3000' eq bukrs}">selected</c:if>>3000</option>
							<option value='4000' <c:if test="${'4000' eq bukrs}">selected</c:if>>4000</option>
							<option value='5000' <c:if test="${'5000' eq bukrs}">selected</c:if>>5000</option>
							<option value='6000' <c:if test="${'6000' eq bukrs}">selected</c:if>>6000</option>
							<option value='7000' <c:if test="${'7000' eq bukrs}">selected</c:if>>7000</option>
							<option value='7200' <c:if test="${'7200' eq bukrs}">selected</c:if>>7200</option>
							<option value='7300' <c:if test="${'7300' eq bukrs}">selected</c:if>>7300</option>
						</select>
					</td>
					<td width="110px;">*会计年度:</td>
					<td width="80px;"><input type="text" name="gjahr" id="gjahr" style="border:0;width:100%;height:90%;" value="${gjahr}"/></td>
					<td width="80px;"><div class="form_operate">
				          <button id="form_search" type="butoon">查询</button>
				        </div>
				    </td>
				</tr>
				<tr class="field">
					<td width="110px;">凭证日期:</td>
					<td>${head[4]}</td>
					<td width="110px;">过账日期:</td>
					<td>${head[5]}</td>
					<td width="110px;">会计期间:</td>
					<td colspan="2">${head[6]}</td>
				</tr>
				<tr class="field">
					<td width="110px;">参考凭证号:</td>
					<td>${head[7]}</td>
					<td width="110px;">货币码:</td>
					<td>${head[8]}</td>
					<td width="110px;">凭证类型:</td>
					<td colspan="2">${head[3]}</td>
				</tr>
    		</tbody>
    	</table>
    	<br/>
    	<div style="width:100%;overflow: auto;">
    	
    	<table style="margin-top: 4px;width:1800px;">
    		<thead>
    			<tr>
					<td class="title" colspan="15">总账明细</td>
				</tr>
    			<tr class="ui-jqgrid-labels">
					<th width="110px" class="ui-state-default ui-th-column ui-th-ltr">行项</th>
					<th width="120px" class="ui-state-default ui-th-column ui-th-ltr">记账代码</th>
					<th width="120px" class="ui-state-default ui-th-column ui-th-ltr">科目</th>
					<th width="100px" class="ui-state-default ui-th-column ui-th-ltr">说明</th>
					<th width="100px" class="ui-state-default ui-th-column ui-th-ltr">金额</th>
					<th width="100px" class="ui-state-default ui-th-column ui-th-ltr">货币</th>
					<th width="60px" class="ui-state-default ui-th-column ui-th-ltr">本位币金额</th>
					<th width="70px" class="ui-state-default ui-th-column ui-th-ltr">本位币</th>
					<th width="100px" class="ui-state-default ui-th-column ui-th-ltr">起息日</th>
					<th width="80px" class="ui-state-default ui-th-column ui-th-ltr">文本</th>
					<th width="80px" class="ui-state-default ui-th-column ui-th-ltr">发票号</th>
					<th width="80px" class="ui-state-default ui-th-column ui-th-ltr">参考1</th>
					<th width="80px" class="ui-state-default ui-th-column ui-th-ltr">参考2</th>
					<th width="80px" class="ui-state-default ui-th-column ui-th-ltr">参考3</th>
				</tr>
    		</thead>
    		<tbody>
    			<c:forEach var="item" items="${item}">
    				<tr>
    					<td style='text-align:right;'>${item[3]}</td>
						<td style='text-align:center;'>${item[4]}</td>
						<td style='white-space:nowrap;'>${item[10]}</td>
						<td style='white-space:nowrap;'>${item[11]}</td>
						<td style='text-align:right;white-space:nowrap;'>
							<c:if test="${'10' eq item[4]||'11' eq item[4]||'12' eq item[4]||'13' eq item[4]||'14' eq item[4]||'15' eq item[4]
								||'16' eq item[4]||'17' eq item[4]||'18' eq item[4]||'18' eq item[4]||'19' eq item[4]}">
							-
							</c:if>
							${item[5]}
						</td>
						<td style='white-space:nowrap;'>${item[15]}</td>
						<td style='text-align:right;white-space:nowrap;'>
							<c:if test="${'10' eq item[4]||'11' eq item[4]||'12' eq item[4]||'13' eq item[4]||'14' eq item[4]||'15' eq item[4]
								||'16' eq item[4]||'17' eq item[4]||'18' eq item[4]||'18' eq item[4]||'19' eq item[4]}">
							-
							</c:if>
							${item[6]}
						</td>
						<td style='white-space:nowrap;'>${item[16]}</td>
						<td style='text-align:center;white-space:nowrap;'>${item[7]}</td>
						<td style='white-space:nowrap;'>${item[9]}</td>
						<td style='white-space:nowrap;'>${item[8]}</td>
						<td style='white-space:nowrap;'>${item[12]}</td>
						<td style='white-space:nowrap;'>${item[13]}</td>
						<td style='white-space:nowrap;'>${item[14]}</td>
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
	var model = "${model}";
	var div_body_width = $(window).width() * 0.76;
	$(".form_header").css("width",div_body_width - 5);
	$(".body").css("width",div_body_width);
	//查询按钮事件
	$("#form_search").click(function(){
		$("#belnrInfoForm").submit();
	});
});
</script>
</html>