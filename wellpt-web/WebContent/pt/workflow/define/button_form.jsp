<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/pt/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Right Form</title>
<%@ include file="/pt/common/meta.jsp"%>

<link href="${ctx}/resources/easyui/themes/default/easyui.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/resources/easyui/themes/icon.css" type="text/css" rel="stylesheet" />

<script src="${ctx}/resources/easyui/jquery-1.8.0.min.js" type="text/javascript"></script>
<script src="${ctx}/resources/easyui/jquery.easyui.min.js" type="text/javascript"></script>
<script src="${ctx}/resources/easyui/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>

	<script type="text/javascript">
	</script>
</head>
<body>
	<div style="padding:3px 2px;border-bottom:1px solid #ccc">流程分类</div>  
<form:form id="form" commandName="button" method="post" action="${ctx}/workflow/define/button/${action}" cssClass="cleanform">
	<c:if test="${not empty button.uuid}">
	<form:hidden path="uuid"></form:hidden>
	</c:if>
    <table>
    	 <tr>
			<td><label>名称</label></td>
        	<td><form:input path="name"></form:input></td>
        	<td><form:errors path="name" cssClass="error" /></td>
        </tr>  
        <tr>  
			<td><label>编号</label></td>
        	<td><form:input path="sn"></form:input></td>
        	<td><form:errors path="sn" cssClass="error" /></td>
        </tr>  
        <tr>
			<td><label>控制值</label></td>
        	<td><form:input path="value"></form:input></td>
        	<td><form:errors path="value" cssClass="error" /></td>
        </tr>
        <tr>
            <td></td>
            <td><input type="submit" value="保存"></input></td>  
        </tr>  
    </table>  
</form:form>
</body>
</html>