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
<title>客户对应表</title>
</head>
<body>
  <div class="div_body" style="">
    <div class="form_header" style="">
      <div class="form_title" style="">
        <h2>客户对应表维护</h2>
      </div>
      <div id="toolbar" class="form_toolbar">
        <div class="form_operate">
          <button id="form_save" type="butoon">保存</button>
          <button id="form_close" type="button">关闭</button>
        </div>
      </div>
    </div>
    <form action="" id="customerForm" class="dyform">
      <table>
        <tr>
          <td class="title" colspan="2">客户对应表</td>   
        </tr>
        <tr class="field">
          <td width="110px">公司代码</td>          
          <td><input type="text" name="bukrs" id="bukrs" value="${customer.bukrs}" class="required"/></td>
        </tr>
        <tr class="field">
          <td width="110px">客户编号</td>          
          <td><input type="text" name="kunnr" id="kunnr" value="${customer.kunnr}" class="required"/></td>
        </tr>
        <tr class="field">
          <td width="110px">客户简称</td>          
          <td><input type="text" name="sortl" id="sortl" value="${customer.sortl}"/></td>
        </tr>
        <tr class="field">
          <td width="110px">放宽期限</td>          
          <td><input type="text" name="zday" id="zday" value="${customer.zday}" class="required"/></td>
        </tr>
        <tr class="field">
          <td width="110px">RSM</td>          
          <td><input type="text" name="zrsmName" id="zrsmName" value="${customer.zrsmName}" readonly="readonly"/>
          	<input type="text" name="zrsm" id="zrsm" value="${customer.zrsm}" style="display: none;"/></td>
        </tr>
        <tr class="field">
          <td width="110px">OM</td>          
          <td><input type="text" name="zomName" id="zomName" value="${customer.zomName}" readonly="readonly"/>
          	<input type="text" name="zom" id="zom" value="${customer.zom}" style="display: none;"/></td>
        </tr>
        <tr class="field">
          <td width="110px">AE</td>          
          <td><input type="text" name="zaeName" id="zaeName" value="${customer.zaeName}" readonly="readonly"/>
          	<input type="text" name="zae" id="zae" value="${customer.zae}" style="display: none;"/></td>
        </tr>
        <tr class="field">
          <td width="110px">AA</td>          
          <td><input type="text" name="zaaName" id="zaaName" value="${customer.zaaName}" readonly="readonly"/>
          	<input type="text" name="zaa" id="zaa" value="${customer.zaa}" style="display: none;"/></td>
        </tr>
        <tr class="field">
          <td width="110px">应收人员</td>          
          <td><input type="text" name="zrName" id="zrName" value="${customer.zrName}" readonly="readonly"/>
          	<input type="text" name="zr" id="zr" value="${customer.zr}" style="display: none;"/></td>
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
	$('#zrsmName').click(function(){
		$.unit.open({
			title : 'RSM',
			labelField : 'zrsmName',
			valueField : 'zrsm',
			selectType : 4
		});
	});
	$('#zomName').click(function(){
		$.unit.open({
			title : 'OM',
			labelField : 'zomName',
			valueField : 'zom',
			selectType : 4
		});
	});
	$('#zaeName').click(function(){
		$.unit.open({
			title : 'AE',
			labelField : 'zaeName',
			valueField : 'zae',
			selectType : 4
		});
	});
	$('#zaaName').click(function(){
		$.unit.open({
			title : 'AA',
			labelField : 'zaaName',
			valueField : 'zaa',
			selectType : 4
		});
	});
	$('#zrName').click(function(){
		$.unit.open({
			title : '应收人员',
			labelField : 'zrName',
			valueField : 'zr',
			selectType : 4
		});
	});
	var bean = {
			"bukrs" : null,
			"kunnr" : null,
			"sortl" : null,
			"zday" : null,
			"zrsmName" : null,
			"zrsm" : null,
			"zomName" : null,
			"zom" : null,
			"zaeName" : null,
			"zae" : null,
			"zaaName" : null,
			"zaa" : null,
			"zrName" : null,
			"zr" : null
		};
	
	if (model == "modify"){
		$("#kunnr").attr("readonly",true);
		$("#bukrs").attr("readonly",true);
		$("#form_save").click(function(){
			if ($("#customerForm").validate(Theme.validationRules).form()) {
				$("#customerForm").form2json(bean);
				JDS.call({
					service : "customerTableService.updateCustomer",
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
		$("#kunnr").blur(function(){
			JDS.call({
				service : "defaultBaoeDataService.getSortl",
				data : [ $("#kunnr").val()],
				async : false,
				success : function(result) {
					if (result.data.mess != null){
						oAlert2(result.data.mess);
					}else{
						$("#sortl").val(result.data.sortl);					
					}
				}
			});
		});
		$("#form_save").click(function(){
			if ($("#customerForm").validate(Theme.validationRules).form()) {
				$("#customerForm").form2json(bean);
				JDS.call({
					service : "customerTableService.saveCustomer",
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