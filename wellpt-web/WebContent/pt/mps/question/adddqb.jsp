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
<title>添加题目</title>
<script type="text/javascript">
	function bs(){
		document.getElementById('dqbform').submit();
	}
</script>
</head>
<body>
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
		<form action="../mps/adddqb" id="dqbform" class="dyform" method="post">
			<input name="question_id" id="question_id"  type="hidden">
			<table>
				<tbody>
					<tr>
						<td class="title" colspan="2">添加题目</td>
					</tr>
					<tr class="field">
						<td width="110px;">题级一</td>
						<td><input name="level_1" id="level_1" />
						</td>
					</tr>
					<tr class="field">
						<td width="110px;">题级二</td>
						<td><input name="level_2" id="level_2" />
						</td>
					</tr>
					<tr class="field">
						<td width="110px;">题级三</td>
						<td><input name="level_3" id="level_3" />
						</td>
					</tr>
					<tr class="field">
						<td width="110px;">题目</td>
						<td><input name="question_text" id="question_text" />
						</td>
					</tr>
					<tr class="field">
						<td width="110px;">题库类型</td>
						<td><input name="question_type" id="question_type" />
						</td>
					</tr>
					<tr class="field">
						<td width="110px;">工序名</td>
						<td><input name="operation_seq" id="operation_seq" />
						</td>
					</tr>
					<tr class="field">
						<td width="110px;">是否关键工序</td>
						<td><input name="is_key" id="is_key" />
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	
		

	</div>
</body>
</html>