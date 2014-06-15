<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/pt/common/taglibs.jsp"%>
<div class="tabs ui-widget ui-widget-content" style="border: none;">
	<form id="fieldSelectForm" name="fieldSelectForm">
		<table style="width: 100%;">
			<tr>
				<td><label></label></td>
				<td>
					<div>
						<div style="float: left; width: 85%">
							<label>主流程表单字段</label> <select id="fields1" name="fields1"
								style="width: 108px;">
								<option value="-1">--请选择字段--</option>
							</select>&nbsp;<label>子流程表单字段</label><select id="fields2" name="fields2"
								style="width: 108px;">
								<option value="-1">--请选择字段--</option>
							</select>
						</div>
						<div style="float: right; width: 4em;">
							<button id="fieldAdd" type="button" class="btn">增加</button>
						</div>
						<div style="clear: both;"></div>
					</div>
					<div style="height: 10px;"></div>
					<div>
						<div style="float: left; width: 85%">
							<select id="selectFields" name="selectFields" size="12"
								class="full-width">
							</select>
						</div>
						<div style="float: right; width: 4em;">
							<button id="selectFieldDelete" type="button" class="btn">删除</button>
						</div>
						<div style="clear: both;"></div>
					</div>
				</td>
				<td></td>
			</tr>
		</table>
	</form>
</div>
