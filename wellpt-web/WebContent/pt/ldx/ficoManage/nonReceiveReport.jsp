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
<title>未收汇报表</title>
</head>
<body>
  <div class="div_body" style="">
    <div class="form_header" style="">
      <div class="form_title" style="">
        <h2>未收汇报表</h2>
      </div>
      <div id="toolbar" class="form_toolbar">
        <div class="form_operate">
          <button id="form_export" type="butoon">导出</button>
          <button id="form_syn" type="button">同步</button>
        </div>
      </div>
    </div>
    <form action="${ctx}/ficoManage/nonReceive/download" method="post" id="nonReceiveForm" class="dyform">
      <table>
        <tr>
          <td class="title" colspan="4">查询条件</td>
        </tr>
        <tr class="field">
          <td width="110px">RSM</td>          
          <td><input type="text" name="zrsmName" id="zrsmName"/></td>
          <td width="110px">AE</td>          
          <td><input type="text" name="zaeName" id="zaeName"/></td>
        </tr>
        <tr class="field">
          <td width="110px">AA</td>
          <td><input type="text" name="zaaName" id="zaaName"/></td>
          <td width="110px">应收人员</td>          
          <td><input type="text" name="zarName" id="zarName"/></td>
        </tr>
        <tr class="field">
          <td width="110px">外向交货单</td>
          <td><input type="text" name="vbeln" id="vbeln"/></td>
          <td width="110px">客户简称</td>          
          <td><input type="text" name="sortl" id="sortl"/></td>
        </tr>
        <tr class="field">
          <td width="110px">出运日期</td>
          <td><input type="text" name="cyDateBegin" id="cyDateBegin" onfocus="WdatePicker({dateFmt:'yyyyMMdd'})" class="Wdate" readonly="readonly"/></td>
          <td width="110px">至:</td>          
          <td><input type="text" name="cyDateEnd" id="cyDateEnd" onfocus="WdatePicker({dateFmt:'yyyyMMdd'})" class="Wdate" readonly="readonly"/></td>
        </tr>
        <tr class="field">
          <td width="110px">接单主体</td>
          <td><input type="text" name="bukrs" id="bukrs"/></td>
          <td width="110px">是否未清</td>          
          <td>
          	<select name="wq" id="wq">
				<option value=""></option>
				<option value="1">是</option>
				<option value="0">否</option>
			</select>
		  </td>
        </tr>
        <tr class="field">
          <td width="110px">是否逾期</td>
          <td>
          	<select name="yq" id="yq">
				<option value=""></option>
				<option value="1">是</option>
				<option value="0">否</option>
			</select>
		  </td>
          <td width="110px">未清金额不符</td>          
          <td>
          	<select name="bf" id="bf">
				<option value=""></option>
				<option value="1">是</option>
				<option value="0">否</option>
			</select>
		  </td>
        </tr>
        <tr class="field">
          <td width="110px">报关单号</td>
          <td><input type="text" name="bgnum" id="bgnum"/></td>
          <td width="110px">是否已开票</td>          
          <td>
          	<select name="kp" id="kp">
				<option value="1">是</option>
				<option value="">全部</option>
			</select>
		  </td>
        </tr>
        <tr class="field">
          <td width="110px">是否已记账</td>          
          <td colspan="3">
          	<select name="jz" id="jz">
				<option value="1">是</option>
				<option value="">全部</option>
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
	$('#zrsmName').click(function(){
		$.unit.open({
			title : 'RSM',
			labelField : 'zrsmName',
			selectType : 4
		});
	});
	$('#zaeName').click(function(){
		$.unit.open({
			title : 'AE',
			labelField : 'zaeName',
			selectType : 4
		});
	});
	$('#zaaName').click(function(){
		$.unit.open({
			title : 'AA',
			labelField : 'zaaName',
			selectType : 4
		});
	});
	$('#zarName').click(function(){
		$.unit.open({
			title : '应收人员',
			labelField : 'zarName',
			selectType : 4
		});
	});
	$("#form_export").click(function(){
		$('#nonReceiveForm').submit();
	});
})

</script>
</html>