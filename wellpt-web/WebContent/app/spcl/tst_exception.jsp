<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@ include file="/pt/common/taglibs.jsp"%>
	<%@ include file="/pt/common/meta.jsp"%>
	<%@ include file="/pt/dyform/dyform_css.jsp"%>
	<title id="title">成品例行实验&RoHS测试异常单</title>
	
</head>
<body>
	<div class="div_body">
		<input type="hidden" id="formUuid" value="${formUuid }" />
		<input type="hidden" id="dataUuid" value="${dataUuid }" />
		<div class="form_header">
			<div class="form_title">
				<div class="form_close" title="关闭"></div>
			</div>
			<div id="toolbar" class="form_toolbar">
				<div class="form_operate">
					<button id="save" type="button" >保存</button>
					<button id="edit" type="button" >编辑</button>
				</div>
				<!-- 自定义表单 -->
			</div>
		</div>
		<form action=""  id="spclform" >
		</form>
	</div>
	<!-- Project -->
	<%@ include file="/pt/dyform/dyform_js.jsp"%>
	<script type="text/javascript" src="${ctx}/resources/pt/js/common/jquery.cmsWindown.js"></script>
	<script type="text/javascript" src='${ctx}/resources/app/js/spcl/tst_exception.js'></script>
</body>
</html>