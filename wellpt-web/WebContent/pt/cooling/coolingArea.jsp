<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<%@ include file="/pt/dyform/dyform_css.jsp"%>
<%@ include file="/pt/dyform/dyform_js.jsp"%>
<%@ include file="/pt/dyform/dyform_ctljs.jsp"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>散热面积计算</title>
<style type="text/css">
body{
	font-family: "Microsoft YaHei";
	font-size: 12px;
}
</style>
</head>
<body>
<div>
<div class="div_body" style="height:680;width:1110"">
		<div class="form_header" style="">
			<div class="form_title">
				<h2>散热面积计算</h2>
			</div>
			<div id="toolbar" class="form_toolbar">
				<div class="form_operate">
					<button id="form_close">关闭</button>
				</div>
			</div>
		</div>
		<form action="" id="wlgcform" class="dyform">
	<fieldset style="margin-left:10px;float:left;width:600px;height:400px;">
		<legend style="font-size:12px;color:#7DAECC;">散热面积计算</legend>
		<div style="float:left;width:50%;height:100%;">
		   <table id="flexigrid-filter" cellpadding="3" class="fisherFilterTable" style="margin-top:5px;">
			   <tr>
					<td  style="width:40%">
						散热件类型:
					</td>
					<td  style="width:55%">
						<select id="coolType"  name="coolType">
							<option value="1">光面</option>
							<option value="2">叶面</option>
						</select>
					</td>
					<td style="width:5%"></td>
			   </tr>
			   <tr>
					<td  style="width:40%">
						散热件高度:
					</td>
					<td  style="width:55%">
						<input type="text" name="hi" id="hi" maxlength="10" style="height: 100%;border: 1;width:100%;" />
					</td>
					<td style="width:7%">mm</td>
			   </tr>
			   <tr>
					<td  style="width:40%">
						基板温度Tc:
					</td>
					<td  style="width:55%">
						<input type="text" name="tc" id="tc" maxlength="10" style="height: 100%;border: 1;width:100%;" />
					</td>
					<td style="width:5%">℃</td>
			   </tr>
			   <tr>
					<td  style="width:40%">
						输入功率:
					</td>
					<td  style="width:55%">
						<input type="text" name="wi" id="wi" maxlength="10" style="height: 100%;border: 1;width:100%;" />
					</td>
					<td style="width:5%">w</td>
			   </tr>
			   <tr>
					<td  style="width:40%">
						驱动效率:
					</td>
					<td  style="width:55%">
						<input type="text" name="yi" id="yi" maxlength="10" style="height: 100%;border: 1;width:100%;" />
					</td>
					<td style="width:5%"></td>
			   </tr>
			   <tr class="fisherFilterTr leafType" style="display:none;">
					<td  style="width:40%">
						最大外径:
					</td>
					<td  style="width:55%">
						<input type="text" name="diMax" id="diMax" maxlength="10" style="height: 100%;border: 1;width:100%;" />
					</td>
					<td style="width:7%">mm</td>
			   </tr>
			   <tr class="fisherFilterTr leafType" style="display:none;">
					<td  style="width:40%">
						最小外径:
					</td>
					<td  style="width:55%">
						<input type="text" name="diMin" id="diMin" maxlength="10" style="height: 100%;border: 1;width:100%;" />
					</td>
					<td style="width:7%">mm</td>
			   </tr>
			   <tr>
					<td style="width:40%;">
						环境温度:
					</td>
					<td style="width:55%">
						25℃
					</td>
					<td style="width:5%"></td>
			   </tr>
			   <tr>
					<td  style="width:40%">
						表面发射率:
					</td>
					<td  style="width:55%">
						0.9
					</td>
					<td style="width:5%"></td>
			   </tr>
			   <tr>
				   <td style="width:95%" colspan="2">
				   		<div id="errorDiv" style="float:left;color:red;display:none;">
				   		</div>
				   		<div style="float:right;">
							<button id="B_calculate" onclick="return false;;">计算</button>
						</div>
					</td>
					<td style="width:5%"></td>
				</tr>
				<tr>
					<td  style="width:40%">
						换热系数:
					</td>
					<td  style="width:55%">
						<input type="text" name="fcFinal" id="fcFinal" readonly="true" style="height: 100%;border: 1;width:100%;" />
					</td>
					<td style="width:5%"></td>
			   </tr>
			   <tr>
					<td  style="width:40%">
						散热面积:
					</td>
					<td  style="width:55%">
						<input type="text" name="faFinal" id="faFinal" readonly="true" style="height: 100%;border: 1;width:100%;" />
					</td>
					<td style="width:5%"></td>
			   </tr>
		   </table>
	    </div>
	    <div id="sampleImg" style="float:left;width:48%;height:100%;">
	    	<img src='${ctx}/resources/pt/images/coolModel1.png'/>
	    </div>
	    </fieldset>
	    <fieldset style="margin-left:10px;float:left;width:350px;height:400px;">
		<legend style="font-size:12px;color:#7DAECC;">寿命预估计算</legend>
		<div style="float:left;width:100%;height:100%;">
			<table id="flexigrid-filter2" cellpadding="3" class="fisherFilterTable" style="margin-top:5px;">
				<tr>
					<td  style="width:30%">
						跟踪时间:
					</td>
					<td  style="width:50%">
						<input type="text" name="ti" id="ti" maxlength="10" style="height: 100%;border: 1;width:100%;" />
					</td>
					<td style="width:20%">h</td>
			   </tr>
			   <tr>
					<td  style="width:30%">
						光通维持率:
					</td>
					<td  style="width:50%">
						<input type="text" name="gi" id="gi" maxlength="10" style="height: 100%;border: 1;width:100%;" />
					</td>
					<td style="width:20%"></td>
			   </tr>
			   <tr>
				   <td style="width:80%" colspan="2">
				   		<div id="errorDivSec" style="float:left;color:red;display:none;">
				   		</div>
				   		<div style="float:right;">
							<button id="B_calculateSec" onclick="return false;">计算</button>
						</div>
				    </td>
				    <td style="width:20%"></td>
			   </tr>
			   <tr>
					<td  style="width:30%">
						衰减系数:
					</td>
					<td  style="width:50%">
						<input type="text" name="so" id="so" readonly="true" style="height: 100%;border: 1;width:100%;" />
					</td>
					<td style="width:20%"></td>
			   </tr>
			   <tr>
					<td  style="width:30%">
						LM70时间:
					</td>
					<td  style="width:50%">
						<input type="text" name="lo" id="lo" readonly="true" style="height: 100%;border: 1;width:100%;" />
					</td>
					<td style="width:20%">万H</td>
			   </tr>
			</table>
		</div>
	</fieldset>
	</div>
	</form>
	</div>
<script>
$(document).ready(function() {
	
	$("#form_close").click(function(){
		window.close();
	});
	$("#coolType").change(function(){
		var src="/resources/pt/images/coolModel"+this.value+".png";
		$("#sampleImg").html("<img src='${ctx}"+src+"'/>");
		if(this.value=="2"){
			$(".leafType").show();
		}else{
			$(".leafType").hide();
		}
		$("#fcFinal").val("");
		$("#faFinal").val("");
	});
	$("#B_calculateSec").click(calculateSec);
	$("#B_calculate").click(calculate);
});

function calculate(){
	$.ajax({
		url:"${ctx}/plm/coolingArea/coolingArea",
		type:"POST",
		dataType:"json",
		async : false,
		data:{
			"coolType":$("#coolType").val(),
			"hi":$("#hi").val(),
			"tc":$("#tc").val(),
			"wi":$("#wi").val(),
			"yi":$("#yi").val(),
			"diMax":$("#diMax").val(),
			"diMin":$("#diMin").val()
		},
		success: function(res){ 
			if(res!=null&&res.length>0){
				if("success"==res[0].result){
					$("#errorDiv").hide();
					$("#fcFinal").val(res[0].fcFinal);
					$("#faFinal").val(res[0].faFinal);
				}else{
					$("#errorDiv").show().text(res[0].error);
					$("#fcFinal").val("");
					$("#faFinal").val("");
				}
			}
	     },
		error: function(){ 
			alert("请求失败");
		}
	});
}

function calculateSec(){
	$.ajax({
		url:"${ctx}/plm/coolingArea/lifeTime",
		type:"POST",
		dataType:"json",
		async : false,
		data:{
			"ti":$("#ti").val(),
			"gi":$("#gi").val()
		},
		success: function(res){ 
			if(res!=null&&res.length>0){
				if("success"==res[0].result){
					$("#errorDivSec").hide();
					$("#so").val(res[0].so);
					$("#lo").val(res[0].lo);
				}else{
					$("#errorDivSec").show().text(res[0].error);
					$("#so").val("");
					$("#lo").val("");
				}
			}
	     },
		error: function(){ 
			alert("请求失败");
		}
	});

}

</script>
</body>
</html>