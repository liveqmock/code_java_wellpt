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
<title>未收汇手工资料</title>
</head>
<body>
  <div class="div_body" style="">
    <div class="form_header" style="">
      <div class="form_title" style="">
        <h2>未收汇手工资料</h2>
      </div>
      <div id="toolbar" class="form_toolbar">
        <div class="form_operate">
          <button id="form_save" type="butoon">保存</button>
          <button id="form_close" type="button">关闭</button>
        </div>
      </div>
    </div>
    <form action="" id="nonReceiveForm" class="dyform">
      <table>
        <tr>
          <td class="title" colspan="2">客户对应表</td>   
        </tr>
        <tr class="field">
          <td width="110px">外向交货单</td>          
          <td><input type="text" name="vbeln" id="vbeln" value="${doc.vbeln}" class="required"/></td>
        </tr>
        <tr class="field">
          <td width="110px">报关单号</td>          
          <td><input type="text" name="vbelv" id="vbelv" value="${doc.vbelv}"></td>
        </tr>
        <tr class="field">
          <td width="110px">报关数量</td>          
          <td><input type="text" name="lfimg" id="lfimg" value="${doc.lfimg}"/></td>
        </tr>
        <tr class="field">
          <td width="110px">报关金额</td>          
          <td><input type="text" name="zeamt" id="zeamt" value="${doc.zeamt}"></td>
        </tr>
        <tr class="field">
          <td width="110px">申报日期</td>          
          <td><input type="text" name="zddate" id="zddate" value="${doc.zddate}" onfocus="WdatePicker({dateFmt:'yyyyMMdd'})" class="Wdate" readonly="readonly"/></td>
        </tr>
        <tr class="field">
          <td width="110px">折算数量</td>          
          <td><input type="text" name="zclfimg" id="zclfimg" value="${doc.zclfimg}"/></td>
        </tr>
        <tr class="field">
          <td width="110px">报关海运费</td>          
          <td><input type="text" name="zcost" id="zcost" value="${doc.zcost}"/></td>
        </tr>
        <tr class="field">
          <td width="110px">柜型</td>          
          <td><input type="text" name="zmodel" id="zmodel" value="${doc.zmodel}"/></td>
        </tr>
        <tr class="field">
          <td width="110px">付款条件代码</td>          
          <td><input type="text" name="zterm" id="zterm" value="${doc.zterm}"/></td>
        </tr>
        <tr class="field">
          <td width="110px">扣款类型</td>          
          <td>
          	<select name="zvtext" id="zvtext">
				<option value="扣款">扣款</option>
				<option value="调整尾差">调整尾差</option>
				<option value="补应收款">补应收款</option>
				<option value="佣金抵扣">佣金抵扣</option>
			</select>
          </td>
        </tr>
        <tr class="field">
          <td width="110px">扣款金额</td>          
          <td><input type="text" name="zcamt" id="zcamt" value="${doc.zcamt}"/></td>
        </tr>
        <tr class="field">
          <td width="110px">客户编号</td>          
          <td><input type="text" name="kunnr" id="kunnr" value="${doc.kunnr}"/></td>
        </tr>
        <tr class="field">
          <td width="110px">备注</td>          
          <td><input type="text" name="znote" id="znote" value="${doc.znote}"/></td>
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
	$("#form_close").click(function(){
		window.close();
	});
	var bean = {
		"vbeln" : null,
		"vbelv" : null,
		"lfimg" : null,
		"zeamt" : null,
		"zddate" : null,
		"zclfimg" : null,
		"zcost" : null,
		"zmodel" : null,
		"zterm" : null,
		"zvtext" : null,
		"zcamt" : null,
		"znote" : null,
		"kunnr" : null
	};
	
	if (model == "modify"){
		$("#vbeln").attr("readonly",true);
		$("#form_save").click(function(){
			if ($("#nonReceiveForm").validate(Theme.validationRules).form()) {
				$("#nonReceiveForm").form2json(bean);
				JDS.call({
					service : "nonReceiveService.updateDoc",
					data : [ bean ],
					async : false,
					success : function(result) {
						if(result.data.res=="success"){
							window.opener.reloadFileParentWindow();
							oAlert2(result.data.msg,closeDialog);
						}else{
							oAlert2(result.data.msg);
						}
					}
				});
			}
		});
	}else{
		$("#form_save").click(function(){
			if ($("#nonReceiveForm").validate(Theme.validationRules).form()) {
				$("#nonReceiveForm").form2json(bean);
				JDS.call({
					service : "nonReceiveService.saveDoc",
					data : [ bean ],
					async : false,
					success : function(result) {
						if(result.data.res=="success"){
							window.opener.reloadFileParentWindow();
							window.close();
						}else{
							oAlert2(result.data.msg);
						}
					}
				});
			}
		});
	}	
})

</script>
</html>