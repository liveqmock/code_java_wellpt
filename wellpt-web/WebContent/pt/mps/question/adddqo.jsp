<%@ page import="java.text.SimpleDateFormat,java.util.*" language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<%@ include file="/pt/dyform/dyform_css.jsp"%>
<%@ include file="/pt/dyform/dyform_js.jsp"%>
<%@ include file="/pt/dyform/dyform_ctljs.jsp"%>
<title>添加答案</title>
<script type="text/javascript">
	function bs(){
		document.getElementById('dqoform').submit();
	}
</script>
</head>
<body>
<%String question_id=(String)request.getAttribute("question_id"); %>
	<div class="div_body" style="">
		<div class="form_header" style="">
			<div class="form_title">
				<h2>添加题目</h2>
			</div>
			<div id="toolbar" class="form_toolbar">
				<div class="form_operate">
					<button id="form_save" type="button" onclick="bs()">保存</button>
					<button id="form_close" type="button">关闭</button>
				</div>
			</div>
		</div>
		<form action="../mps/adddqo" id="dqoform" class="dyform" method="post">
			<input name="question_id" id="question_id"  type="hidden" value=<%=question_id%> >
			<table>
				<tbody>
					<tr>
						 <td class="title" colspan="2">添加答案</td>
					</tr>
					<tr class="field">
						<td width="110px;">答案内容</td>
						<td><input name="option_text" id="option_text" /></td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
</body>
</html>