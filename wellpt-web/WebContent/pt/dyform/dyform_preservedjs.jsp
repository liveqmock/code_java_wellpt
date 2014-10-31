<%@page import="com.wellsoft.pt.dyform.support.enums.EnumRelationTblSystemField"%>
<%@page import="com.wellsoft.pt.dyform.support.enums.EnumSystemField"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">
<!--//处理系统预留字段
	var sysFiels = [];//系统字段
	<%
	{
		EnumSystemField[] sysFields = EnumSystemField.values();
		for(EnumSystemField sysField: sysFields){
	%>
		sysFiels.push({elementType:"<%=sysField.getElementType()%>", name:"<%=sysField.getName()%>", dataType:"<%=sysField.getDataType()%>", column:"<%=sysField.getColumn()%>", length:<%=sysField.getLength()%>});
	<%
		}
	}
	%>
	
	var relationFiels = [];//数据关系表字段
	<%
	{
		EnumRelationTblSystemField[] sysFields = EnumRelationTblSystemField.values();
		for(EnumRelationTblSystemField sysField: sysFields){
	%>
		relationFiels.push({elementType:"<%=sysField.getElementType()%>", name:"<%=sysField.getName()%>", dataType:"<%=sysField.getDataType()%>", column:"<%=sysField.getColumn()%>", length:<%=sysField.getLength()%>});
	<%
		}
	}
	%>
	
	var preservedFields = [];//预留字段
	preservedFields = preservedFields.concat(sysFiels);
	preservedFields = preservedFields.concat(relationFiels);
	
	//数据预留字段
	preservedFields =  preservedFields.concat([
	                                           {name:"user"},
	                                           {name:"select"},
	                                           {name:"from"},
	                                           {name:"alter"},
	                                           {name:"table"},
	                                           {name:"add"},
	                                           {name:"CHAR"},
	                                           {name:"create"},
	                                           {name:"TIMESTAMP"},
	                                           {name:"primary"},
	                                           {name:"key"},
	                                           {name:"using"},
	                                           {name:"index"},
	                                           {name:"NUMBER"},
	                                           {name:"VARCHAR2"},
	                                           {name:"clob"},
	                                           {name:"column"},
	                                           {name:"join"},
	                                           {name:"left"},
	                                           {name:"right"},
	                                           {name:"update"},
	                                           {name:"insert"},
	                                           {name:"as"},
	                                           {name:"on"},
	                                           {name:"where"},
	                                           {name:"and"},
	                                           {name:"in"},
	                                           {name:"not"},
	                                           {name:"like"},
	                                           {name:"if"}
	                                           ]);
	preservedFields.is = function(fieldName){
		for(var index = 0; index < this.length; index ++){
			if(fieldName.toLowerCase() == this[index].name.toLowerCase() ){
				return true;
			}
		}
		return false;
	};
//-->
</script>

</head>
<body>
	
</body>
</html>