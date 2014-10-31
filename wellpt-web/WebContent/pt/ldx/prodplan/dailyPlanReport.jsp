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
<title>日滚动计划</title>
</head>
<body>
  <div class="div_body" style="">
    <div class="form_header" style="">
      <div class="form_title" style="">
        <h2>日滚动计划</h2>
      </div>
      <div id="toolbar" class="form_toolbar">
        <div class="form_operate">
          <button id="form_export" type="butoon">导出</button>
        </div>
      </div>
    </div>
    <form action="${ctx}/productionPlan/dailyPlanReport/exportExcelAll" method="post" id="planForm" class="dyform">
      <table>
        <tr>
          <td class="title" colspan="4">查询条件</td>
        </tr>
        <tr class="field">
          <td width="110px">版本号</td>
          <td>
          	<select name="version" id="version">
				<c:forEach var="version" items="${versionList}">
					<option value="${version}">${version}</option>
				</c:forEach>
			</select>
		  </td>
          <td width="110px">
          	<select name="searchType1" id="searchType1">
				<option value="zsg">生管</option>
				<option value="zxh">线别</option>
				<option value="class">课别</option>
				<option value="dwerks">厂别</option>
				<option value="department">部别</option>
				<option value="zzt">状态</option>
			</select>
		  </td>          
          <td>
          	<select name="searchValue1" id="searchValue1">
				<option value=""></option>
				<c:forEach var="sg" items="${selectSgList}">
					<option value="${sg}">${sg}</option>
				</c:forEach>
			</select>
		  </td>
        </tr>
        <tr class="field">
          <td width="110px">
          	<select name="searchType2" id="searchType2">
				<option value=""></option>
				<option value="zxh">线别</option>
				<option value="zsg">生管</option>
				<option value="class">课别</option>
				<option value="dwerks">厂别</option>
				<option value="department">部别</option>
				<option value="zzt">状态</option>
			</select>
		  </td>
          <td>
          	<select name="searchValue2" id="searchValue2">
				<option value=""></option>
			</select>
          </td>
          <td width="110px">
          	<select name="searchType3" id="searchType3">
				<option value="edatu">SD交期</option>
				<option value="gstrp">上线日期</option>
				<option value="gltrp">完工日期</option>
				<option value="scrq">生产日期</option>
				<option value="mpsdate">MPS上线日期</option>
				<option value="yjcq">预计船期</option>
			</select>
          </td>
          <td><input type="text" name="searchValue3" id="searchValue3" onfocus="WdatePicker({dateFmt:'yyyyMMdd'})" class="Wdate" readonly="readonly"/></td>
        </tr>
        <tr class="field">
          <td width="110px">生产订单</td>
          <td><input type="text" name="searchValue4" id="searchValue4"/></td>
          <td width="110px">报表类型</td>
          <td>
          	<select name="reportType" id="reportType">
	          	<option value="3">3日计划</option>
				<option value="7">7日计划</option>
				<option value="10">10日计划</option>
			</select>
          </td>
        </tr>
      </table>
    </form>
  </div>
</body>

<script type="text/javascript">
function closeDialog(){
	window.close();
}
$(document).ready(function(){
	var model = "${model}";
	var div_body_width = $(window).width() * 0.76;
	$(".form_header").css("width",div_body_width - 5);
	$(".body").css("width",div_body_width);
	$("#searchType1").change(function(){
		var width = $('#searchValue1').outerWidth();
		var parentType = "";
		var parentValue = "";
		var childType = $('#searchType1').val();
		JDS.call({
			service : "productionPlanService.getSelectItemByParent",
			data : [ '','',childType ],
			async : false,
			success : function(result) {
				if(result.data.res=="success"){
					$('#searchValue1').html(result.data.data);
					$('#searchValue1').css("width",width);
				}
			}
		});
	});
	$("#searchType2").change(function(){
		var width = $('#searchValue2').outerWidth();
		var parentType = $('#searchType1').val();
		var parentValue = "";
		var childType = $('#searchType2').val();
		JDS.call({
			service : "productionPlanService.getSelectItemByParent",
			data : [ parentType,parentValue,childType ],
			async : false,
			success : function(result) {
				if(result.data.res=="success"){
					$('#searchValue2').html(result.data.data);
					$('#searchValue2').css("width",width);
				}
			}
		});
	});
	$("#form_export").click(function(){
		$('#planForm').submit();
	});
})

</script>
</html>