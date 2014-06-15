<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/pt/common/header.jsp"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<link href="${ctx}/resources/jBox/Skins/Blue/jbox.css" rel="stylesheet"
	type="text/css" /> 
<body>
	<div class="row-fluid sortable">
		<div class="box span12">
			<div class="box-header well" data-original-title>
				<h2>
					<i class="icon-edit"></i> 工作日志
				</h2>
				<div class="box-icon"></div>
			</div>
			<div class="box-content">
				<form class="form-horizontal" action="${ctx }/worktask/dytable/save"
					method="post">
					<fieldset>
						<input type="hidden" value="${pojo.uuid }" name="uuid" />
						 
						<div class="control-group">
							<label class="control-label" for="typeahead">${pojo.name }: </label>
							<div class="controls">
								<select name="formUuid" id="task">
									<c:forEach var="item" items="${forms }">
										<option value="${item.uuid }"<c:if test="${item.uuid==pojo.formUuid }">selected</c:if>>${item.descname}</option>
									</c:forEach>
								</select>
							</div> 
						</div> 
						<div class="form-actions">
							<button type="submit" id="submitBtn" class="btn btn-primary">保存</button>
							<button type="reset" class="btn">取消</button>
						</div>

					</fieldset>
				</form>

			</div>
		</div>
		<!--/span-->

	</div>
	<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/jqgrid/js/i18n/grid.locale-cn.js"></script>
	<script src="${ctx}/resources/jBox/jquery.jBox.src.js"
		type="text/javascript"></script>
	<script src="${ctx}/resources/jBox/i18n/jquery.jBox-zh-CN.js"
		type="text/javascript"></script>
	<script type="text/javascript">
		$.jgrid.no_legacy_api = true;
		$.jgrid.useJSON = true;
		// 加载全局国际化资源
		I18nLoader.load("/resources/pt/js/global");
		// 加载动态表单定义模块国际化资源
		I18nLoader.load("/resources/worktask/js/daily/editpage");
	</script>
	<script src="${ctx }/resources/worktask/js/daily/workdaily.js"></script>
</body>
</html>