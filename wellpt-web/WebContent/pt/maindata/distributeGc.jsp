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
<title>工厂分配</title>
</head>
<body>
	<div class="div_body" style="">
		<div class="form_header" style="">
			<div class="form_title">
				<h2>分配工厂</h2>
			</div>
			<div id="toolbar" class="form_toolbar">
				<div class="form_operate">
					<button id="form_save" type="button">保存</button>
					<button id="form_close" type="button">关闭</button>
				</div>
			</div>
		</div>

		<form action="" id="wlgcform" class="dyform">
			<input name="wlids" id="wlids" value="${wlids}" type="hidden">
			<table>
				<tbody>
					<tr>
						<td class="title"><input type="checkbox" class="checkall">
						</td>
						<td class="title">工厂</td>
						<td class="title">工厂名称</td>
						<td class="title">生产仓储地点</td>
						<td class="title">外部采购仓储地点</td>
					</tr>
					<c:forEach var="factory" items="${factorys}">
						<tr>
							<td><input type="checkbox" value="${factory.factory}"
								class="checkeds"></td>
							<td>${factory.factory}</td>
							<td>${factory.name}</td>
							<td><select name="scstore" id="scstore" style="width: 200px">
									<option value=""></option>
									<c:forEach var="story" items="${factory.stores}">
										<option value="${story[0]}">${story[1]}(${story[0]})</option>
									</c:forEach>
							</select></td>
							<td><select name="wbstore" id="wbstore" style="width: 200px">
									<option value=""></option>
									<c:forEach var="story" items="${factory.stores}">
										<option value="${story[0]}">${story[1]}(${story[0]})</option>
									</c:forEach>
							</select></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</form>
	</div>
</body>
<script type="text/javascript">
	$(document).ready(
			function() {
				var div_body_width = $(window).width() * 0.76;
				$(".form_header").css("width", div_body_width - 5);
				$(".div_body").css("width", div_body_width);
				$(".checkall").live(
						"click",
						function() {
							if ($(this).attr("checked")) {
								$(this).parents("table").find("input:checkbox")
										.attr("checked", true);
							} else {
								$(this).parents("table").find("input:checkbox")
										.attr("checked", false);
							}
						});
				$("#form_close").click(function() {
					window.close();
				});
				$("#form_save").click(
						function() {
							var object = new Object();
							var arrayObj = new Array();
							$(".checkeds:checked").each(
									function(i) {
										var s = new Object();
										s['factory'] = $(this).val();
										s['scstore'] = $(this).parent()
												.parent().find(
														"select[name=scstore]")
												.val();
										s['wbstore'] = $(this).parent()
												.parent().find(
														"select[name=wbstore]")
												.val();
										arrayObj.push(s);
									});
							object['wlids'] = eval($('#wlids').val());
							object['items'] = arrayObj;
							JDS.call({
								service : "mainDataWlService.saveWlgc",
								data : [ JSON.stringify(object) ],
								async : false,
								success : function(result) {
									window.opener.reloadFileParentWindow();
									window.close();
								}
							});
						});
			});
</script>
</html>