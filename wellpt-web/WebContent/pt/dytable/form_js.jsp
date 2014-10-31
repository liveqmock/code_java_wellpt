<%@page import="com.wellsoft.pt.basicdata.ca.service.FJCAAppsService"%>
<%@page import="com.wellsoft.pt.core.resource.Config"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript" src="${ctx}/resources/utils/json2.js"></script>
<script type="text/javascript" src="${ctx}/resources/jquery/jquery.js"></script>
<script type="text/javascript" src="${ctx}/resources/bootstrap/js/bootstrap.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/jqgrid/js/i18n/grid.locale-cn.js"></script>
<script type="text/javascript">
	$.jgrid.no_legacy_api = true;
	$.jgrid.useJSON = true;
	// 加载全局国际化资源
	I18nLoader.load("/resources/pt/js/global");
	// 加载动态表单定义模块国际化资源
	I18nLoader.load("/resources/pt/js/dytable/dytable");
</script>
<script type="text/javascript"
	src="${ctx}/resources/jqgrid/js/jquery.jqGrid.js"></script>
<script type="text/javascript" src="${ctx}/resources/timers/jquery.timers.min.js"></script>
<script src="${ctx}/resources/My97DatePicker/WdatePicker.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/jBox/jquery.jBox.src.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/jBox/i18n/jquery.jBox-zh-CN.js"
	type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/pt/js/common/jquery.fileuploader.js"></script>
<%-- <script src="${ctx}/resources/fileupload/js/header.js"></script> --%>
<%-- <script src="${ctx}/resources/fileupload/js/util.js"></script> --%>
<%-- <script src="${ctx}/resources/fileupload/js/button.js"></script> --%>
<%-- <script src="${ctx}/resources/fileupload/js/ajax.requester.js"></script> --%>
<!-- <script -->
<%-- 	src="${ctx}/resources/fileupload/js/deletefile.ajax.requester.js"></script> --%>
<%-- <script src="${ctx}/resources/fileupload/js/handler.base.js"></script> --%>
<%-- <script src="${ctx}/resources/fileupload/js/window.receive.message.js"></script> --%>
<%-- <script src="${ctx}/resources/fileupload/js/handler.form.js"></script> --%>
<%-- <script src="${ctx}/resources/fileupload/js/handler.xhr.js"></script> --%>
<%-- <script src="${ctx}/resources/fileupload/js/uploader.basic.js"></script> --%>
<%-- <script src="${ctx}/resources/fileupload/js/dnd.js"></script> --%>
<%-- <script src="${ctx}/resources/fileupload/js/uploader.js"></script> --%>
<%-- <script src="${ctx}/resources/fileupload/js/jquery-plugin.js"></script> --%>

<script src="${ctx}/resources/fileupload/js/uuid.js"></script>
<script src="${ctx}/resources/form/form_body.js"></script>

<script type="text/javascript"
	src="${ctx}/resources/jqueryui/js/jquery-ui.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/pt/js/basicdata/serialnumber/serialform.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/validform/js/Validform_v5.2.1.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/pt/js/dytable/dytable_constant.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/pt/js/common/jquery.numberInput.js"></script>
	

<!-------------------- 我是上传控件开始 ------------------------->
<script type="text/javascript"
	src="${ctx}/resources/pt/js/fileupload/well.fileupload.constant.js"></script>
	
<script type="text/javascript"
	src="${ctx}/resources/pt/js/fileupload/well.fileupload.js"></script>
	
<script type="text/javascript"
	src="${ctx}/resources/pt/js/fileupload/well.fileupload4Icon.js"></script>
	
 
<!-------------------- 我是上传控件结束 ------------------------->	
	
	
<script type="text/javascript"
	src="${ctx}/resources/utils/ajaxfileupload.src.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/utils/ajaxfileupload.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/fileupload/js/jquery.fileupload.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/fileupload/js/jquery.iframe-transport.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/fileupload/js/jquery.fileupload-process.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/fileupload/js/jquery.fileupload-fp.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/fileupload/js/jquery.fileupload-ui.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/ckeditor4.1/ckeditor.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/ztree/js/jquery.ztree.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/ztree/js/jquery.ztree.exhide-3.5.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/pt/js/common/jquery.comboTree.js"></script>
<script type="text/javascript"
	src='${ctx}/resources/pt/js/org/unit/jquery.unit.js'></script>
<script type="text/javascript"
	src="${ctx}/resources/pt/js/common/jquery.alerts.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/pt/js/dytable/dytable_dialog_js.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/pt/js/dytable/dytable_explain.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/pt/js/calendar/calendar_timeEmploy.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/basicdata/dyview/dyview_explain.js"></script>

 

<%
	// 加入附件签名控件
	if(FJCAAppsService.ENABLE.equals(Config.getValue(FJCAAppsService.KEY_ENABLE))) {
		String fjcaControlContextPath = request.getContextPath();
		if("/".equals(fjcaControlContextPath)) {
			fjcaControlContextPath = "";
		}
		String fjcaWs = "<object id=\"fjcaWs\" name=\"SBFjCAEnAndSign\" classid=\"CLSID:506038C2-52A5-4EA5-8F7D-F39B10265709\" codebase=\"" + fjcaControlContextPath + "/resources/pt/js/security/SBFjCAEnAndSign.ocx\"></object>";
		String fjcaControl = "<object id=\"fjcaControl\" classid=\"clsid:414C56EC-7370-48F1-9FB4-AF4A40526463\" codebase=\"" + fjcaControlContextPath + "/resources/pt/js/security/fjcaControl.ocx\" ></object>";
		out.print(fjcaWs);
		out.print(fjcaControl);
	}
%>