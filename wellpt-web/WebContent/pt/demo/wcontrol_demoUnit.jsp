<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/pt/common/taglibs.jsp"%>
<!DOCTYPE html>
<html><head><meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>jOrgChart - A jQuery OrgChart Plugin</title>
    
    
  <link rel="stylesheet" type="text/css"
	href="${ctx}/resources/jqueryui/css/base/jquery-ui.css" />
	<script type="text/javascript" src="${ctx}/resources/jquery/jquery.js"></script>
    <script type="text/javascript"
		src="${ctx}/resources/jqueryui/js/jquery-ui.js"></script>
			<script type="text/javascript"
		src="${ctx}/resources/pt/js/org/test/jquery.unit1.js"></script>
  

    <script>
    $(document).ready(function(){ 
	    $("#departmentName").click(function() {
			$.unit.open({
				title : "选择部门",
				labelField : "departmentName",
				valueField : "departmentId",
				type : "Dept",
				selectType : 2
			});
		});
    });

    </script>
  </head>

<body>
<tr>
		<td style="width: 65px;"><label for="departmentName">所属部门</label></td>
		<td><input type="text" id="departmentName"
			name="departmentName" class="full-width" /></td>
		<td><input type="hidden" id="departmentId"
			name="departmentId" />
			<input type="hidden" id="departmentUuid"
			name="departmentUuid" /></td>
	</tr>	

</body>
</html>