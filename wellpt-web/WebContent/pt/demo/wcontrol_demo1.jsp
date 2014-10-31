<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>

<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<%@ include file="/pt/dyform/dyform_css.jsp"%>
<%@ include file="/pt/dyform/dyform_js.jsp"%>
<%@ include file="/pt/dyform/dyform_ctljs.jsp"%>

<script type="text/javascript">
	//构造控件参数
	var radiomap="{'1':'apple','2':'orange','3':'lemon','4':'其他1'}";
	var optionSet=eval("("+radiomap+")");//
		var columnjson={
		          fields: [{
		            	displayName:'字段1',
		            	name:'text',
		            	defaultValue:'text',
		            	inputMode:'1'
		            },
		            {
		            	displayName:'字段2',
		            	name:'checkbox',
		            	defaultValue:'1,2',
		            	inputMode:'18',
		            	optionSet:optionSet
		            },
		            {
		            	displayName:'字段3',
		            	name:'select',
		            	defaultValue:'1',
		            	inputMode:'19',
		            	optionSet:optionSet
		            }]
		};
		
	
	//创建控件
	$(document).ready(function() {
 		var columns=columnjson.fields;
		var formDefinition={};
		//控件初始化
			var inputArr = $("body").find('input');
			for(var i=0;i<inputArr.length;i++){
				var fieldname = $(inputArr[i]).attr("name");//容器或inputname
				for(var j=0;j<columns.length;j++){
					if(fieldname==columns[j].name){
						 	$.ControlManager.createControl(columns[j].name,columns[j],formDefinition);
					}
				}
			} 
	       //使用控件
			$("#setValue").click(function(){
				if(getCtr().getAllOptions().commonProperty.inputMode=='1'){
					getCtr().setValue("text11111");
				}else if(getCtr().getAllOptions().commonProperty.inputMode=='18'){
					getCtr().setValue("1,3");
				}else if(getCtr().getAllOptions().commonProperty.inputMode=='19'){
					getCtr().setValue("2");
				}
			});
			
	
			$("#getValue").click(function(){
				alert(getCtr().getValue());
			});
			
			$("#btn_setedit").click(function(){
				getCtr().setEditable();
			});
			
			$("#btn_setnoedit").click(function(){
				getCtr().setEnable(false);
			});
			
			$("#btn_showlabel").click(function(){
				getCtr().setDisplayAsLabel();
			});
			
			$("#btn_bind").click(function(){
				getCtr().bind('blur',function(){
					alert("'blur'事件绑定");
				});
			});
			
			$("#btn_unbind").click(function(){
				getCtr().unbind('blur');
			});
			
			
			
			
			
	
			function getCtr(){
				var ctlname=$("#ctlname").val();
				return $.ControlManager.getControl(ctlname); 
			}
	});
	

</script>

<body>
	<form id="abc" class="dyform" action="">
		<table>
			<tbody>
				<tr>
					<td class="title" colspan="2">控件Demo</td>
				</tr>
				<tr class="field">
					<td width="110px;">文本(字段名：text)</td>
					<td><input name="text" ></td>
				</tr>
				<tr class="field">
					<td width="110px;">复选(字段名：checkbox)</td>
					<td><input name="checkbox" ></td>
				</tr>
					<tr class="field">
					<td width="110px;">下拉框(字段名：select)</td>
					<td><input name="select" ></td>
				</tr>
				<tr>
					<td width="110px;">
					<input type="button" id='setValue' value="设置值" />
					<input type="button" id='getValue' value="获得值" />
					<input type="button" id='btn_setnoedit' value="禁用" />
					<input type="button" id='btn_setedit' value="可编辑" />
					<input type="button" id='btn_showlabel' value="显示为label" />
					<input type="button" id='btn_bind' value="事件绑定" />
					<input type="button" id='btn_unbind' value="事件解绑" />
					</td>
					<td>
					请输入字段名:
					<input name="ctlname" id="ctlname"></input>
					</td>
				</tr>
			</tbody>
		</table>
		<p></p>
	</form>


</body>
</html>