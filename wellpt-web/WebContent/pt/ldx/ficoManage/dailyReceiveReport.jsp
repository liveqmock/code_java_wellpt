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
<title>财务每日到款</title>
</head>
<body>
  <div class="div_body" style="">
    <div class="form_header" style="">
      <div class="form_title" style="">
        <h2>财务每日到款</h2>
      </div>
      <div id="toolbar" class="form_toolbar">
        <div class="form_operate">
          <button id="form_export" type="butoon">导出</button>
        </div>
      </div>
    </div>
    <form action="${ctx}/ficoManage/dailyReceive/download" method="post" id="dailyReceiveForm" class="dyform">
      <table>
        <tr>
          <td class="title" colspan="4">查询条件</td>
        </tr>
        <tr class="field">
          <td width="110px">凭证编号</td>
          <td><input type="text" name="param.zbelnr" id="zbelnr"/></td>
          <td width="110px">公司代码</td>          
          <td><input type="text" name="param.bukrs" id="bukrs"/></td>
        </tr>
        <tr class="field">
          <td width="110px">客户编码</td>
          <td><input type="text" name="param.kunnr" id="kunnr"/></td>
          <td width="110px">客户简称</td>          
          <td><input type="text" name="param.sortl" id="sortl"/></td>
        </tr>
        <tr class="field">
          <td width="110px">申报单号</td>
          <td><input type="text" name="param.zdoip" id="zdoip"/></td>
          <td width="110px">国家汇款人</td>          
          <td><input type="text" name="param.zcrem" id="zcrem"/></td>
        </tr>
        <tr class="field">
          <td width="110px">合同号</td>
          <td><input type="text" name="param.bstkd" id="bstkd"/></td>
          <td width="110px">外向交货单</td>          
          <td><input type="text" name="param.vbeln" id="vbeln"/></td>
        </tr>
        <tr class="field">
          <td width="110px">是否登记</td>
          <td>
          	<select name="param.zcheck" id="zcheck">
				<option value=""></option>
				<option value="1">是</option>
				<option value="0">否</option>
			</select>
		  </td>
          <td width="110px">是否入账</td>      
          <td>
          	<select name="param.zpost" id="zpost">
				<option value=""></option>
				<option value="1">是</option>
				<option value="0">否</option>
			</select>
		  </td>
        </tr>
        <tr class="field">
          <td width="110px">到款日期</td>
          <td><input type="text" name="param.fromBldat" id="fromBldat" onfocus="WdatePicker({dateFmt:'yyyyMMdd'})" class="Wdate" readonly="readonly"/></td>
          <td width="110px">至:</td>          
          <td><input type="text" name="param.toBldat" id="toBldat" onfocus="WdatePicker({dateFmt:'yyyyMMdd'})" class="Wdate" readonly="readonly"/></td>
        </tr>
        <tr class="field">
          <td width="110px">RSM</td>          
          <td><input type="text" name="param.zrsm" id="zrsm"/></td>
          <td width="110px">AE</td>          
          <td><input type="text" name="param.zae" id="zae"/></td>
        </tr>
        <tr class="field">
          <td width="110px">AA</td>
          <td><input type="text" name="param.zaa" id="zaa"/></td>
          <td width="110px">应收人员</td>          
          <td><input type="text" name="param.zrname" id="zrname"/></td>
        </tr>
        <tr class="field">
          <td width="110px">业务类型</td>          
          <td colspan="3">
          	<select name="jz" id="jz">
				<option value=""></option>
				<option value="A">预收</option>
				<option value="B">应收</option>
				<option value="C">其他应收款</option>
				<option value="D">样品款</option>
				<option value="E">其他</option>
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
	$('#zrsm').click(function(){
		$.unit.open({
			title : 'RSM',
			labelField : 'zrsm',
			selectType : 4
		});
	});
	$('#zae').click(function(){
		$.unit.open({
			title : 'AE',
			labelField : 'zae',
			selectType : 4
		});
	});
	$('#zaa').click(function(){
		$.unit.open({
			title : 'AA',
			labelField : 'zaa',
			selectType : 4
		});
	});
	$('#zrname').click(function(){
		$.unit.open({
			title : '应收人员',
			labelField : 'zrname',
			selectType : 4
		});
	});
	$("#form_export").click(function(){
		$('#dailyReceiveForm').submit();
	});
})

</script>
</html>