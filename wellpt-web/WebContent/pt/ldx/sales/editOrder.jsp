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
<title>订单修改</title>
</head>
<body>
	<div class="div_body" style="">
		<div class="form_header" style="">
			<div class="form_title">
				<h2>订单修改</h2>
			</div>
			<div id="toolbar" class="form_toolbar">
				<div class="form_operate">
					<button id="form_save" type="button">保存</button>
					<button id="form_close" type="button">关闭</button>
				</div>
			</div>
		</div>
		<form action="" id="wlgcform" class="dyform">
			<input name="id" id="id" value="${entity.id}" type="hidden">
			<table>
				<tbody>
					<tr>
						<td class="title" colspan="4">订单修改</td>
					</tr>
					<tr class="field">
						<td width="110px;">订单编号</td>
						<td><input name="ddbh" id="ddbh" value="${entity.ddbh}"
							disabled="disabled"></td>
						<td width="110px;">订单类型</td>
						<td><input name="ddlx" id="ddlx" value="${entity.ddlx}">
						</td>
					</tr>
					<tr class="field">
						<td width="110px;">销售组织</td>
						<td><input name="sszz" id="sszz" value="${entity.sszz}">
						</td>
						<td width="110px;">分销渠道</td>
						<td><input name="fxqd" id="fxqd" value="${entity.fxqd}">
						</td>
					</tr>
					<tr class="field">
						<td width="110px;">产品组</td>
						<td><input name="cpz" id="cpz" value="${entity.cpz}">
						</td>
						<td width="110px;">售达方</td>
						<td><input name="soudf" id="soudf" value="${entity.soudf}">
						</td>
					</tr>
					<tr class="field">
						<td width="110px;">送达方</td>
						<td><input name="songdf" id="songdf" value="${entity.songdf}">
						</td>
						<td width="110px;">付款方</td>
						<td><input name="fkf" id="fkf" value="${entity.fkf}">
						</td>
					</tr>
					<tr class="field">
						<td width="110px;">交货日期</td>
						<td><input name="jhrq1" id="jhrq1" value="${entity.jhrq}"
							class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});">
						</td>
						<td width="110px;">货币类型</td>
						<td><input name="hblx" id="hblx" value="${entity.hblx}">
						</td>
					</tr>
					<tr class="field">
						<td width="110px;">客户组3</td>
						<td><select name="khz" id="khz">
								<option value=""></option>
								<option value="A1"
									<c:if test="${!empty entity.khz && 'A1' eq entity.khz}">selected</c:if>>A1
									返利</option>
								<option value="A2"
									<c:if test="${!empty entity.khz && 'A2' eq entity.khz}">selected</c:if>>A2
									不返利</option>
						</select></td>
						<td width="110px;">付款条件</td>
						<td><select name="fktj" id="fktj">
								<c:forEach var="object" items="${zterms}">
									<option value="${object[0]}"
										<c:if test="${!empty entity.fktj && object[0] eq entity.fktj}">selected</c:if>>${object[1]}(${object[0]})</option>
								</c:forEach>
						</select></td>
					</tr>
					<tr class="field">
						<td width="110px;">采购订单编号</td>
						<td colspan="3"><input name="bstkd" id="bstkd"
							value="${entity.bstkd}"></td>
					</tr>
					<tr class="field">
						<td width="110px;">审核人</td>
						<td><input name="auditname" id="auditname"
							value="${entity.auditname}" disabled="disabled"></td>
						<td width="110px;">审核时间</td>
						<td><input name="auditdate" id="auditdate"
							value="${entity.auditdate}" disabled="disabled"></td>
					</tr>
					<tr class="field">
						<td width="110px;">审核描述</td>
						<td colspan="3"><textarea id="remark" name="remark"
								textalign="left" fontsize="12" fontcolor="black"
								style="text-align: left; font-size: 12px; color: rgb(0, 0, 0); width: 285px; height: 52px;">${entity.remark}</textarea>
					</tr>
					<tr class="field">
						<td colspan="4"><p>
								<button id="delBtn">删除&nbsp;&nbsp;&nbsp;&nbsp;</button>
							</p>
							<table width="95%" class="ui-jqgrid-htable" id="nonEditTable">
								<thead>
									<tr class="ui-jqgrid-labels">
										<th width="10%"
											class="ui-state-default ui-th-column ui-th-ltr">物料ID</th>
										<th width="20%"
											class="ui-state-default ui-th-column ui-th-ltr">物料描述</th>
										<th width="10%"
											class="ui-state-default ui-th-column ui-th-ltr">数量</th>
										<th width="8%" class="ui-state-default ui-th-column ui-th-ltr">单价</th>
										<th width="8%" class="ui-state-default ui-th-column ui-th-ltr">价格单位</th>
										<th width="14%"
											class="ui-state-default ui-th-column ui-th-ltr">行项目交货日期</th>
										<th width="14%"
											class="ui-state-default ui-th-column ui-th-ltr">销售凭证项目类别</th>
										<th width="8%" class="ui-state-default ui-th-column ui-th-ltr">货币</th>
										<th width="8%" class="ui-state-default ui-th-column ui-th-ltr">条件类型</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="object" items="${entity.orderlines}"
										varStatus="status">
										<tr
											class="ui-widget-content jqgrow ui-row-ltr <c:if test="${0 eq status.index}">ui-state-highlight</c:if>"
											<c:if test="${0 eq status.index}">aria-selected='true'</c:if>>
											<td width="10%">${object.product.matnr.substring(8)}<input
												name="lineid" id="lineid" value="${object.id}" type="hidden">
											</td>
											<td width="20%">${object.product.stext}</td>
											<td width="10%">${object.sl}</td>
											<td width="8%"><input name="linedj" id="linedj"
												value="${object.dj}"></td>
											<td width="8%"><input name="linejgdw" id="linejgdw"
												value="${object.jgdw}"></td>
											<td width="14%"><input name="linejhrq1" id="linejhrq1"
												value="${object.jhrq}" class="Wdate"
												onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"></td>
											<td width="14%"><input name="linexmlx" id="linexmlx"
												value="${object.xmlx}"></td>
											<td width="8%">${object.hblx}</td>
											<td width="8%">${object.tjlx}</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
							<p></p></td>
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
	function mainPlanClick() {
		$('#nonEditTable tbody tr').each(
				function() {
					$(this).removeClass("ui-state-highlight").attr(
							"aria-selected", "false");
				});
		$(this).addClass("ui-state-highlight").attr("aria-selected", "true");
	}
	function collectChildrenDatadicList(bean) {
		var changes = [];
		$('#nonEditTable tbody tr').each(
				function() {
					var model = {};
					$(this).find("input")
							.each(
									function() {
										model[$(this).attr("name").replace(
												'line', '')] = $(this).val();
									});
					changes.push(model);
				});
		bean["orderlines"] = changes;
	}
	$(document).ready(function() {
		var div_body_width = $(window).width() * 0.76;
		$(".form_header").css("width", div_body_width - 5);
		$(".div_body").css("width", div_body_width);
		var bean = {
			"id" : null,
			"ddlx" : null,
			"sszz" : null,
			"fxqd" : null,
			"cpz" : null,
			"soudf" : null,
			"songdf" : null,
			"fkf" : null,
			"jhrq1" : null,
			"hblx" : null,
			"khz" : null,
			"fktj" : null,
			"bstkd" : null,
			"remark" : null,
			"orderlines" : []
		};
		$("#form_save").click(function() {
			if ($("#wlgcform").validate(Theme.validationRules).form()) {
				$("#wlgcform").form2json(bean);
				collectChildrenDatadicList(bean);
				JDS.call({
					service : "dmsOrderService.saveObject",
					data : [ bean ],
					async : false,
					success : function(result) {
						var b = result.data;
						if ("E" == b.type) {
							alert(bean.message);
						} else {
							window.opener.reloadFileParentWindow();
							window.close();
						}
					}
				});
			}
		});
		$("#form_close").click(function() {
			window.close();
		});
		$("#delBtn").click(function() {
			$('#nonEditTable tbody tr').each(function() {
				if ("true" == $(this).attr("aria-selected")) {
					$(this).remove();
				}
			});
			return false;
		});
		$('#nonEditTable tbody tr').click(mainPlanClick);
	});
</script>
</html>