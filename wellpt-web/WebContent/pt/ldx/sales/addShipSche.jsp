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
<title>出货排载维护</title>
</head>
<body>
	<div class="div_body" style="">
		<div class="form_header" style="">
			<div class="form_title">
				<h2>出货排载维护</h2>
			</div>
			<div id="toolbar" class="form_toolbar">
				<div class="form_operate">
					<font color="black">从外向交货单号&nbsp;</font><input name="vbeln2"
						id="vbeln2" value="">
					<button id="form_copy" type="button">复制</button>
					<button id="form_save" type="button">保存</button>
					<button id="form_close" type="button">关闭</button>
				</div>
			</div>
		</div>
		<form action="" id="wlgcform" class="dyform">
			<table>
				<tbody>
					<tr>
						<td class="title" colspan="2">出货排载维护</td>
					</tr>
					<tr class="field">
						<td width="110px;">外向交货单号</td>
						<td><input name="vbeln1" id="vbeln1" value="${entity.vbeln}"
							disabled="disabled"> <input name="vbeln" id="vbeln"
							value="${entity.vbeln}" hidden="true">
						</td>
					</tr>
					<tr class="field">
						<td width="110px;">装柜编号</td>
						<td><input name="zgbh" id="zgbh" value="${entity.zgbh}"
							disabled="disabled">
						</td>
					</tr>
					<tr class="field">
						<td width="110px;">出口单位</td>
						<td><input name="bukrs" id="bukrs" value="${entity.bukrs}"
							disabled="disabled">
						</td>
					</tr>
					<tr class="field">
						<td width="110px;">客户代码</td>
						<td><input name="sortl" id="sortl" value="${entity.sortl}"
							disabled="disabled">
						</td>
					</tr>
					<tr class="field">
						<td width="110px;">合同号</td>
						<td><input name="zbstkd" id="zbstkd" value="${entity.zbstkd}"
							disabled="disabled">
						</td>
					</tr>
					<tr class="field">
						<td width="110px;">订单交期</td>
						<td><input name="wadat" id="wadat" value="${entity.wadat}"
							disabled="disabled">
						</td>
					</tr>
					<tr class="field">
						<td width="110px;">起运港</td>
						<td><input name="z103" id="z103" value="${entity.z103}"
							disabled="disabled">
						</td>
					</tr>
					<tr class="field">
						<td width="110px;">目的港</td>
						<td><input name="z104" id="z104" value="${entity.z104}"
							disabled="disabled">
						</td>
					</tr>
					<tr class="field">
						<td width="110px;">柜型</td>
						<td><input name="z108" id="z108" value="${entity.z108}"
							disabled="disabled">
						</td>
					</tr>
					<tr class="field">
						<td width="110px;">目的国</td>
						<td><input name="land1" id="land1" value="${entity.land1}"
							disabled="disabled">
						</td>
					</tr>
					<tr class="field">
						<td width="110px;">费用（CNY）</td>
						<td><input name="cny" id="cny" value="${entity.cny}"
							disabled="disabled">
						</td>
					</tr>
					<tr class="field">
						<td width="110px;">费用（USD）</td>
						<td><input name="usd" id="usd" value="${entity.usd}"
							disabled="disabled">
						</td>
					</tr>
					<tr class="field">
						<td width="110px;">出厂日期</td>
						<td><input name="zccrq" id="zccrq" value="${entity.zccrq}"
							disabled="disabled">
						</td>
					</tr>
					<tr class="field">
						<td width="110px;">AA</td>
						<td><input name="zaa" id="zaa" value="${entity.zaa}"
							disabled="disabled">
						</td>
					</tr>
					<tr class="field">
						<td width="110px;">最后修改人</td>
						<td><input name="zggr" id="zggr" value="${entity.zggr}"
							disabled="disabled">
						</td>
					</tr>
					<tr class="field">
						<td width="110px;">最后修改时间</td>
						<td><input name="zgxsj" id="zgxsj" value="${entity.zgxsj}"
							disabled="disabled">
						</td>
					</tr>
					<tr class="field">
						<td width="110px;">排载负责人</td>
						<td><input name="zpzfzr" id="zpzfzr"
							value="${entity.zpzfzr}" class="required">
						</td>
					</tr>
					<tr class="field">
						<td width="110px;">船东提单号</td>
						<td><input name="zbl" id="zbl" value="${entity.zbl}" class="required">
						</td>
					</tr>
					<tr class="field">
						<td width="110px;">码头或仓库</td>
						<td><input name="zmt" id="zmt" value="${entity.zmt}" class="required">
						</td>
					</tr>
					<tr class="field">
						<td width="110px;">船东</td>
						<td><input name="zcd" id="zcd" value="${entity.zcd}" class="required">
						</td>
					</tr>
					<tr class="field">
						<td width="110px;">货代</td>
						<td><input name="zhd" id="zhd" value="${entity.zhd}" class="required">
						</td>
					</tr>
					<tr class="field">
						<td width="110px;">货代联系人</td>
						<td><input name="zhdc" id="zhdc" value="${entity.zhdc}" class="required">
						</td>
					</tr>
					<tr class="field">
						<td width="110px;">货代联系方式</td>
						<td><input name="zhdcw" id="zhdcw" value="${entity.zhdcw}" class="required">
						</td>
					</tr>
					<tr class="field">
						<td width="110px;">备注</td>
						<td><input name="zbz" id="zbz" value="${entity.zbz}">
						</td>
					</tr>
					<tr class="field">
						<td width="110px;">订单交期(手维)</td>
						<td><input name="zwadat" id="zwadat" value="${entity.zwadat}"
							class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});">
						</td>
					</tr>
					<tr class="field">
						<td width="110px;">预计船期</td>
						<td><input name="zyjcq" id="zyjcq" value="${entity.zyjcq}"
							class="Wdate required" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});">
						</td>
					</tr>
					<tr class="field">
						<td width="110px;">申报日期</td>
						<td><input name="zsbd" id="zsbd" value="${entity.zsbd}"
							class="Wdate required" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});">
						</td>
					</tr>
					<tr class="field">
						<td width="110px;">预计到港日期</td>
						<td><input name="zdgrq" id="zdgrq" value="${entity.zdgrq}"
							class="Wdate required" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});">
						</td>
					</tr>
					<tr class="field">
						<td width="110px;">付款申请完成</td>
						<td><select name="zappr" id="zappr" disabled="disabled">
								<option value="N"
									<c:if test="${!empty entity.zappr && 'N' eq entity.zappr}">selected</c:if>>否</option>
								<option value="Y"
									<c:if test="${!empty entity.zappr && 'Y' eq entity.zappr}">selected</c:if>>是</option>
						</select>
						</td>
					</tr>
				</tbody>
			</table>
			<br />
			<p></p>
		</form>
	</div>
</body>
<script type="text/javascript">
	function openUser(lname, lid) {
		$.unit.open({
			title : 'test',
			labelField : lname,
			valueField : lid,
			selectType : 4
		});
	}
	$(document).ready(function() {
		var div_body_width = $(window).width() * 0.76;
		$(".form_header").css("width", div_body_width - 5);
		$(".div_body").css("width", div_body_width);
		$("#form_close").click(function() {
			window.close();
		});
		$("#form_save").click(function() {
			var bean = {
				"zwadat" : null,
				"zpzfzr" : null,
				"zyjcq" : null,
				"zbl" : null,
				"zmt" : null,
				"zcd" : null,
				"zhd" : null,
				"zhdc" : null,
				"zhdcw" : null,
				"zsbd" : null,
				"zbz" : null,
				"zdgrq" : null,
				"vbeln" : null
			};
			if ($("#wlgcform").validate(Theme.validationRules).form()) {
				$("#wlgcform").form2json(bean);
				JDS.call({
					service : "shipScheService.saveShipSche",
					data : [ bean ],
					async : false,
					success : function(result) {
						window.opener.reloadFileParentWindow();
						window.close();
					}
				});
			}
		});
		$("#form_copy").click(function() {
			var vbeln2 = $("#vbeln2").val();
			if ('' != vbeln2) {
				JDS.call({
					service : "shipScheService.findShipSche",
					data : [ vbeln2 ],
					async : false,
					success : function(result) {
						var bean = result.data;
						if ("E" == bean.type) {
							alert(bean.message);
						} else {
							$("#wlgcform").json2form(bean);
						}
					}
				});
			}
		});
	});
</script>
</html>