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
<title>公告编辑</title>
</head>
<body>
	<div class="div_body" style="">
		<div class="form_header" style="">
			<div class="form_title">
				<h2>公告编辑</h2>
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
						<td class="title" colspan="2">公告编辑</td>
					</tr>
					<tr class="field">
						<td width="110px;">标题</td>
						<td><input name="name" id="name" value="${entity.name}"
							class="required"></td>
					</tr>
					<tr class="field">
						<td colspan="2"><textarea id="content" name="content"
								class="ckeditor" style="width: 100%;">${entity.content}</textarea>
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
				"id" : null,
				"name" : null,
				"content" : null
			};
			if ($("#wlgcform").validate(Theme.validationRules).form()) {
				$("#wlgcform").form2json(bean);
				var oEditor = CKEDITOR.instances['content'];
				bean.content = oEditor.document.getBody().getHtml();
				JDS.call({
					service : "dmsBulletinService.saveBulletin",
					data : [ bean ],
					async : false,
					success : function(result) {
						window.opener.reloadFileParentWindow();
						window.close();
					}
				});
			}
		});
	});
</script>
</html>