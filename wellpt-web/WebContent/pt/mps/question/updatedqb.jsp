<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<%@ include file="/pt/dyform/dyform_css.jsp"%>
<%@ include file="/pt/dyform/dyform_js.jsp"%>
<%@ include file="/pt/dyform/dyform_ctljs.jsp"%>
<title>更新题目</title>
<script type="text/javascript">
	function bs(){
		
		
		
		var obj="";
		var obj2="";
		
		 input = document.getElementsByTagName("input");
		 for (var i = 0; i < input.length; i++) {
	            if (input[i].type == "checkbox") {
	                
	//遍历所有的input  将数据进行变更
	                    obj+=input[i].value+"@@"; 
	                    obj2+=document.getElementById(input[i].value).value+"@@";
	                    
	                    
	                
	            }
	        }
		
		
		 
		//alert(obj2);
		document.getElementById("bs").value=obj;
		document.getElementById("bs2").value=obj2;
		//alert(document.getElementById("bs").value);
		
		
		document.getElementById('dqbform').submit();
		
		
	
	}
	function add(){
		
		
	var question_id=document.getElementById("question_id").value;	
	
	window.open( "../mps/enteradddqo?question_id=" + question_id);	
			
	}
	
	function del(){
		
		var obj="";
		if(window.confirm('你确定要删除这些数据？')){
			
			
			 input = document.getElementsByTagName("input");
			 for (var i = 0; i < input.length; i++) {
		            if (input[i].type == "checkbox") {
		                if (input[i].checked) {
		                    obj+=input[i].value+"@@"; 
		                }
		            }
		        }
    window.open( "../mps/deletedqo?optionids=" + obj);	
		}
	}

	
	
	

</script>
</head>
<body>
<%
String question_id=(String)request.getAttribute("question_id");
String level_1=(String)request.getAttribute("level_1");
String level_2=(String)request.getAttribute("level_2");
String level_3=(String)request.getAttribute("level_3");
String question_text=(String)request.getAttribute("question_text");
String question_type=(String)request.getAttribute("question_type");
String operation_seq=(String)request.getAttribute("operation_seq");
String is_key=(String)request.getAttribute("is_key");
String[][] option=(String[][])request.getAttribute("option"); 
%>
	<div class="div_body" style="">
		<div class="form_header" style="">
			<div class="form_title">
				<h2>修改题目</h2>
			</div>
			<div id="toolbar" class="form_toolbar">
				<div class="form_operate">
					<button id="form_save" type="button" onclick="bs()">保存</button>
					<button id="form_close" type="button">关闭</button>
				</div>
			</div>
		</div>
		<form action="../mps/updatedqb" id="dqbform" class="dyform" method="post">
			<input name="question_id" id="question_id"  type="hidden"  value=<%=question_id %>>
			<input type="hidden" name="bs" id="bs" />
			<input type="hidden" name="bs2" id="bs2" />
			<table>
				<tbody>
					<tr>
						<td class="title" colspan="2">修改题目</td>
					</tr>
					<tr class="field">
						<td width="110px;">题级一</td>
						<td><input name="level_1" id="level_1" value=<%=level_1 %> >
						</td>
					</tr>
					<tr class="field">
						<td width="110px;">题级二</td>
						<td><input name="level_2" id="level_2" value=<%=level_2 %>>
						</td>
					</tr>
					<tr class="field">
						<td width="110px;">题级三</td>
						<td><input name="level_3" id="level_3" value=<%=level_3 %>>
						</td>
					</tr>
					<tr class="field">
						<td width="110px;">题目</td>
						<td><input name="question_text" id="question_text" value=<%=question_text %>>
						</td>
					</tr>
					<tr class="field">
						<td width="110px;">题库类型</td>
						<td><input name="question_type" id="question_type"  value=<%=question_type %>>
						</td>
					</tr>
					<tr class="field">
						<td width="110px;">工序名</td>
						<td><input name="operation_seq" id="operation_seq"  value=<%=operation_seq %> >
						</td>
					</tr>
					<tr class="field">
						<td width="110px;">是否关键工序</td>
						<td><input name="is_key" id="is_key"  value=<%=is_key %> >
						</td>
					</tr>
				</tbody>
			</table>
			<br />
			<p></p>
		</form>
		<input type="button"  value="添加" onclick="add()"/>
		<input type="button"  value="删除" onclick="del()" />
		<form action="" id="dqbform2" class="dyform" method="post" width="50%">
			<table>
				<tbody>
					<tr>
						<td class="title" colspan="2">显示对应答案</td>
					</tr>
					<tr>
					<td>选择</td>
					<td>答案内容</td>
					</tr>
					<%for(int i=0;i<option.length;i++) {%>
				    <tr>
				    <td>
				    <input type="checkbox"  value=<%=option[i][1] %>  />
				    </td>
				    <td>
				    <input type="text" value=<%=option[i][2]%>  id=<%=option[i][1] %>  onchange='choose(this)' />
				    </td>
				    </tr>
					<%}
					%>
				</tbody>
			</table>
			<br />
			<p></p>
		</form>
	</div>
</body>
</html>