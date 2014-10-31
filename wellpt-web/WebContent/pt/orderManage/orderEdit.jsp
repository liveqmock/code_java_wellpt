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
<title>订单信息维护</title>
</head>
<body>
<div class="div_body" style="">
		<div class="form_header" style="">
			<div class="form_title">
				<h2>订单信息维护</h2>
			</div>
			<div id="toolbar" class="form_toolbar">
				<div class="form_operate">
					<button id="form_save" type="button">保存</button>
					<button id="form_close" type="button">关闭</button>
				</div>
			</div>
		</div>
		<form action="" id="capacityform" class="dyform">
		<table>
					<tr>
						<td>订单号</td>
						<td style="white-space:nowrap;">
							<input type="text" name="vbeln" id="vbeln" maxlength="10" value="${vbeln}" readonly="true" style="height: 100%;border: 0;width:80%;"/>
						</td>
					</tr>
					<tr>
						<td>PONO</td>
						<td style="white-space:nowrap;">
							<input type="text" name="zpono" id="zpono" maxlength="10" value="${zpono}" style="height: 100%;border: 0;width:80%;"/>
						</td>
					</tr>
					<tr>
						<td>散件包装提供日期</td>
						<td style="white-space:nowrap;">
							<input type="text" name="xmdyb" id="xmdyb" maxlength="10" value="${xmdyb}" readonly="true" style="height: 100%;border: 0;width:80%;" />
							<input type="checkBox" id="update_xmdyb" style="width:15px;height:13px;float:right;" checked="true" title="同时更新订单行项"/>
						</td>
					</tr>
					<tr class="fisherFilterTr">
						<td >回复客人交期</td>
						<td  style="white-space:nowrap;">
							<input type="text" name="zhfkr" id="zhfkr" maxlength="10" value="${zhfkr}" readonly="true" style="height: 100%;border: 0;width:80%;" />
							<input type="checkBox" id="update_zhfkr" style="width:15px;height:13px;float:right;" checked="true" title="同时更新订单行项"/>
						</td>
					</tr>
					<tr>
						<td>客人批次号</td>
						<td style="white-space:nowrap;">
							<input type="text" name="krpch" id="krpch" maxlength="10" value="${krpch}" style="height: 100%;border: 0;width:80%;"/>
						</td>
					</tr>
					<tr>
						<td>塑件提供日期</td>
						<td style="white-space:nowrap;">
							<input type="text" name="zsjtg" id="zsjtg" maxlength="10" value="${zsjtg}" style="height: 100%;border: 0;width:80%;"/>
						</td>
					</tr>
					<tr>
						<td>包装指导提供日期</td>
						<td style="white-space:nowrap;">
							<input type="text" name="zbzzd" id="zbzzd" maxlength="10" value="${zbzzd}" style="height: 100%;border: 0;width:80%;"/>
						</td>
					</tr>
					<tr>
						<td >验货日期</td>
						<td  style="white-space:nowrap;">
							<input type="text" name="zyhrq" id="zyhrq" maxlength="10" value="${zyhrq}" readonly="true" style="height: 100%;border: 0;width:80%;" />
							<input type="checkBox" id="update_zyhrq" style="width:15px;height:13px;float:right;" checked="true" title="同时更新订单行项"/>
						</td>
					</tr>
					<tr>
						<td >验货结果</td>
						<td  style="white-space:nowrap;">
							<select name="zyhjg" id="zyhjg" >
								<option value=""> </option>
								<option value="合格">合格</option>
								<option value="不合格">不合格</option>
							</select>
							<input type="checkBox" id="update_zyhjg" style="width:15px;height:13px;float:right;" checked="true" title="同时更新订单行项"/>
						</td>
					</tr>
					<tr>
						<td>验货备注</td>
						<td style="white-space:nowrap;">
							<textarea rows="2" cols="50" name="zyhbz" id="zyhbz">${zyhbz}</textarea>
						</td>
					</tr>
					<tr>
						<td>备注</td>
						<td style="white-space:nowrap;">
							<textarea rows="2" cols="50" name="zbz" id="zbz">${zbz}</textarea>
						</td>
					</tr>
				</table>
				<table>
					<tr >
						<td >报关单</td>
						<td  style="white-space:nowrap;">
							<input type="text" name="zzbgd" id="zzbgd" maxlength="10" value="${zzbgd}" style="height: 100%;border: 0;width:80%;" />
						</td>
					</tr>
					<tr >
						<td >尾数预计出货数量</td>
						<td  style="white-space:nowrap;">
							<input type="text" name="zchsl" id="zchsl" maxlength="10" value="${zchsl}" style="height: 100%;border: 0;width:80%;" />
						</td>
					</tr>
					<tr >
						<td>尾数预计出货日期</td>
						<td style="white-space:nowrap;">
							<input type="text" name="zchrq" id="zchrq" maxlength="10" value="${zchrq}" style="height: 100%;border: 0;width:80%;" />
						</td>
					</tr>
					<tr>
						<td>出货尾数备注</td>
						<td style="white-space:nowrap;">
							<input type="text" name="chwsbz" id="chwsbz" maxlength="10" value="${chwsbz}" style="height: 100%;border: 0;width:80%;" />
						</td>
					</tr>
					<tr>
						<td>OM出货计划提供日期</td>
						<td style="white-space:nowrap;">
							<input type="text" name="omchjh" id="omchjh" maxlength="10" value="${omchjh}" readonly="true" style="height: 100%;border: 0;width:80%;" />
							<input type="checkBox" id="update_omchjh" style="width:15px;height:13px;float:right;" checked="true" title="同时更新订单行项"/>
						</td>
					</tr>
					<tr>
						<td>逾期责任部门</td>
						<td>
						<select id="zyqbm" name="zyqbm">
							<option value=" "> </option>
							<option value="LED-光源应用开发部">LED-光源应用开发部</option>
							<option value="LED-智能系统开发部">LED-智能系统开发部</option>
							<option value="LED-灯具应用开发部">LED-灯具应用开发部</option>
							<option value="CFL产品线/品技部">CFL产品线/品技部</option>
							<option value="客户">客户</option>
							<option value="销售部">销售部</option>
							<option value="包装课">包装课</option>
							<option value="船务课">船务课</option>
							<option value="LED产品线/制造一部">LED产品线/制造一部</option>
							<option value="LED产品线/制造三部">LED产品线/制造三部</option>
							<option value="LED产品线/制造四部">LED产品线/制造四部</option>
							<option value="LED产品线/制造五部">LED产品线/制造五部</option>
							<option value="LED产品线/注塑部">LED产品线/注塑部</option>
							<option value="LED产品线/电感部">LED产品线/电感部</option>
							<option value="LED产品线/包材厂">LED产品线/包材厂</option>
							<option value="CFL产品线/联恺">CFL产品线/联恺</option>
							<option value="CFL产品线/制造三部">CFL产品线/制造三部</option>
							<option value="LED PMC">LED PMC</option>
							<option value="CFL PMC">CFL PMC</option>
							<option value="采购部">采购部</option>
							<option value="仓储部">仓储部</option>
							<option value="品保部">品保部</option>
							<option value="供应商管理部">供应商管理部</option>
							<option value="技转部">技转部</option>
							<option value="系统">系统</option>
							<option value="项目管理部">项目管理部</option>
						</select>
						<input type="checkBox" id="update_zyqbm" style="width:15px;height:13px;float:right;" checked="true" title="同时更新订单行项"/>							
						</td>
					</tr>
					<tr>
						<td>逾期异常大类</td>
						<td>
							<select id="zycdl" name="zycdl">
								<option value=" "> </option>
							</select>
							<input type="checkBox" id="update_zycdl" style="width:15px;height:13px;float:right;" checked="true" title="同时更新订单行项"/>							
						</td>
					</tr>
					<tr>
						<td>逾期备注</td>
						<td>
							<textarea rows="4" cols="50"id="zyqbz" name="zyqbz" >${zyqbz }</textarea>
							<input type="checkBox" id="update_zyqbz" style="width:15px;height:13px;float:right;top:2px;" checked="true" title="同时更新订单行项"/>
						</td>
					</tr>
				</table>
		</form>
</div>
</body>
<script type="text/javascript">
$(document).ready(function() {
	
	$("#form_close").click(function(){
		window.close();
	});
	$("#form_save").click(function() {
		JDS.call({
			service : "orderSearchService.update",
			data : [ $("#vbeln").val(), $("#zpono").val(), $("#xmdyb").val(),
					$("#zhfkr").val(), $("#krpch").val(), $("#zsjtg").val(),
					$("#zbzzd").val(), $("#zyhrq").val(), $("#zyhjg").val(),
					$("#zyhbz").val(), $("#zbz").val(), $("#zzbgd").val(), $("#zchsl").val(), $("#zchrq").val()
					,$("#chwsbz").val(), $("#omchjh").val(), $("#zyqbm").val(), $("#zycdl").val(), $("#zyqbz").val()],
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
	});
});
</script>
</html>