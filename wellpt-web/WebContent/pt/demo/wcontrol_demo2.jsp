<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>


<!DOCTYPE html>
<html lang="en">
<head>
<style>
/* Component containers
----------------------------------*/
.ui-widget { font-family: "Microsoft YaHei"; font-size: 12px; }
.ui-widget .ui-widget { font-size: 11px; }
.ui-widget input, .ui-widget select, .ui-widget textarea, .ui-widget button { font-family:"Microsoft YaHei"; font-size: 12px; }
<!--
/* 组织选择框 */
.ui-dialog {
padding:0;
}
.ui-widget-header{
background: url("/wellpt-web/resources/theme/images/v1_icon.png") repeat-x scroll 0 -53px transparent;
}
.ui-corner-all, .ui-corner-bottom, .ui-corner-right, .ui-corner-br {
border-bottom-right-radius: 0;
}
.ui-corner-all, .ui-corner-bottom, .ui-corner-left, .ui-corner-bl {
border-bottom-left-radius: 0;
}
.ui-corner-all, .ui-corner-top, .ui-corner-right, .ui-corner-tr {
border-top-right-radius: 0;
}
.ui-corner-all, .ui-corner-top, .ui-corner-left, .ui-corner-tl {
border-top-left-radius: 0;
}
.ui-resizable-handle.ui-resizable-s {
background: url("/wellpt-web/resources/theme/images/v1_panel.png") repeat-x scroll 0 -10px transparent;
}
.ui-resizable-handle.ui-resizable-e {
background: url("/wellpt-web/resources/theme/images/v1_bd.png") repeat-y scroll 100% 0 transparent;
right: -2px;
}
-->
</style>

<%-- <link rel="stylesheet" href="${ctx}/resources/theme/css/wellnewoa.css  " type="text/css" /> --%>
 <c:set var="ctx" value="${pageContext.request.contextPath}" />
<link rel="stylesheet" href="${ctx}/resources/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css" />
<link rel="stylesheet" href="${ctx}/resources/pt/css/jquery-ui-1.8.21.custom.css" type="text/css" />
<link rel="stylesheet" type="text/css" href="${ctx}/resources/jqueryui/css/base/jquery-ui.css" />
<link href="${ctx}/resources/jBox/Skins/Blue/jbox.css" rel="stylesheet" type="text/css" /> 

<link rel="stylesheet" type="text/css" media="screen"
href="${ctx}/resources/jqgrid/themes/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css" media="screen"
href="${ctx}/resources/jqgrid/themes/ui.multiselect.css" /> 
<script type="text/javascript" src="${ctx}/resources/jquery/jquery.js"></script>
<script type="text/javascript" src="${ctx}/resources/utils/json2.js"></script>
<script type="text/javascript" src="${ctx}/resources/bootstrap/js/bootstrap.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/control/common/jquery.wControlUtil.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/control/common/jquery.wabstractCtl.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/control/common/jquery.wControlInterface.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/control/common/jquery.wTextCommonMethod.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/control/subform/jquery.wSubFormMethod.js"></script>

<script type="text/javascript" src="${ctx}/resources/My97DatePicker/WdatePicker.js" ></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/dyform/common/dyform_constant.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/control/jquery.wPubTools.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/control/jquery.wTextInput.js" ></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/control/jquery.wRadio.js" ></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/control/jquery.wCheckBox.js" ></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/control/jquery.wTextArea.js" ></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/control/jquery.wComboBox.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/control/jquery.wNumberInput.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/control/jquery.wDatePicker.js"></script>

<script type="text/javascript" src="${ctx}/resources/pt/js/control/common/jquery.wControlManager.js"></script>
<script type="text/javascript" src='${ctx}/resources/pt/js/org/unit/jquery.unit.js'></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/control/jquery.wUnit.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/control/jquery.wSerialNumber.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/control/jquery.wViewDisplay.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/control/jquery.wDialog.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/control/jquery.wFileUpload4Image.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/control/jquery.wSubForm.js"></script>

<script type="text/javascript" src="${ctx}/resources/pt/js/common/jquery.alerts.js"></script>

<script type="text/javascript" src="${ctx}/resources/pt/js/dytable/dytable_dialog_js.js"></script>
<script type="text/javascript" src="${ctx}/resources/jqueryui/js/jquery-ui.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/basicdata/serialnumber/serialform.js"></script>
<script type="text/javascript" src="${ctx}/resources/ckeditor4.1/ckeditor.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/control/jquery.wCkeditor.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/control/jquery.wFileUpload.js"></script>

<script type="text/javascript"src="${ctx}/resources/pt/js/fileupload/well.fileupload.constant.js"></script>
<script type="text/javascript"src="${ctx}/resources/utils/ajaxfileupload.src.js"></script>
<script type="text/javascript"src="${ctx}/resources/utils/ajaxfileupload.js"></script>
<script type="text/javascript"src="${ctx}/resources/fileupload/js/jquery.fileupload.js"></script>
<script type="text/javascript"src="${ctx}/resources/pt/js/fileupload/well.fileupload.js"></script>
<script type="text/javascript"src="${ctx}/resources/pt/js/fileupload/well.fileupload4Icon.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/control/jquery.wFileUpload4Icon.js"></script>
<script type="text/javascript"src="${ctx}/resources/pt/js/fileupload/well.fileupload4Image.js"></script>

<link rel="stylesheet" type="text/css" href="${ctx}/resources/fileupload/jquery.fileupload-ui.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/resources/fileupload/fileupload.css" />
<script type="text/javascript" src="${ctx}/resources/ztree/js/jquery.ztree.js"></script>
<script type="text/javascript" src="${ctx}/resources/ztree/js/jquery.ztree.exhide-3.5.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/control/jquery.wComboTree.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/common/jquery.comboTree.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/control/jquery.wctlproperty.js"></script>
 
 <script type="text/javascript"
src="${ctx}/resources/pt/js/dyform/common/function.js"></script> 
<script type="text/javascript"
src="${ctx}/resources/pt/js/dyform/dyform_explain.js"></script> 
<script type="text/javascript"
src="${ctx}/resources/pt/js/dyform/dyform_zh_CN.js"></script> 

<script type="text/javascript"
src="${ctx}/resources/jqgrid/js/jquery.jqGrid.js"></script> 
<script src="${ctx}/resources/jBox/jquery.jBox.src.js"
type="text/javascript"></script>
<script src="${ctx}/resources/jBox/i18n/jquery.jBox-zh-CN.js"
type="text/javascript"></script> 

<script type="text/javascript"
src="${ctx}/resources/jqgrid/js/i18n/grid.locale-cn.js"></script> 
<script src="${ctx}/resources/fileupload/js/uuid.js"></script>
<script type="text/javascript">

		var radiomap="{'1':'apple','2':'orange','3':'lemon','4':'其他1'}";
		var optionSet=eval("("+radiomap+")");//
		var checkboxdata="[{value:'1',label:'红'},"+
        "{value:'2',label:'绿'},"+
        "{value:'3',label:'蓝 '}]";
		var relationDataDefiantion='{"sqlTitle":"单位名称","sqlField":"title","formTitle":"项目建设单位名称","formField":"fieldName2001","search":"no"}|{"sqlTitle":"组织机构代码","sqlField":"reservedText5","formTitle":"项目建设单位名称","formField":"fieldName2002","search":"no"}|{"sqlTitle":"组织机构代码","sqlField":"reservedText5","formTitle":"项目建设单位名称","formField":"_inputfieldName18","search":"no"}';
        var treeservername='dataDictionaryService.getFromTypeAsTreeAsync(DY_FORM_FIELD_MAPPING)';

var columnjson={
		          fields: [{
		            	displayName:'字段1',
		            	name:'fieldName1',
		            	defaultValue:'fieldName1',
		            	inputMode:'20'
		            },
		            {
		            	displayName:'字段2',
		            	name:'fieldName2',
		            	defaultValue:'1,2',
		            	inputMode:'18',
		            	optionSet:optionSet
		            },
		            {
		            	displayName:'字段3',
		            	name:'fieldName3',
		            	defaultValue:'3',
		            	inputMode:'18',
		            	optionSet:optionSet,
		            	fontColor:'red',
		            	textAlign:'left',
		            	fontSize:'30',
		            	ctlWidth:'50',
		            	showType:'1',
		            	inputMode:'17',
		            	optionSet:optionSet
		            },{
		            	displayName:'字段4',
		            	name:'fieldName4',
		            	defaultValue:'2',
		            	fontColor:'red',
		            	inputMode:'19',
		            	optionSet:optionSet,
		            	dictCode:'XZSP_BJ_TYPE',
		            	optionDataSource:'2'
		            },
		            {
		            	displayName:'字段5',
		            	name:'fieldName5',
		            	defaultValue:'fieldName5',
		            	inputMode:'18',
		            	optionSet:optionSet
		            },
		            {
		            	displayName:'字段6',
	            		name:'fieldName6',
	            		defaultValue:'文本控件',
	            		inputMode:'20',
	            		textareaRow:'5',
	            		textareaCol:'30'
		            },
		            {
		            	displayName:'字段7',
			             name:'fieldName7',
			             defaultValue:123,
			             decimal:'3',
			             fontcolor:'blue',
			             inputMode:'31',
			             dbDataType:'15',
			             negative:false
		             },
		             {
		            	 displayName:'字段8',
		            	 name:'fieldName8',
		            	 showType:'1',
		            	 onlyreadUrl:'directto',
		            	 defaultValue:'dy_work_task_delay_begin_time',
		            	 inputMode:'16',
		            	 fontColor:'red',
		            	 textAlign:'center',
		            	 fontSize:12,
		            	 serviceName:treeservername},
		            	  {
		            		 displayName:'字段9',
		            		 name:'fieldName9',
		            		 defaultValue:'fieldName9',
		            		 inputMode:'1',
		            		 showType:'2'},
			            
		            {
	            		 displayName:'字段10',
	            		 name:'fieldName10',
	            		 defaultValue:'fieldName101',
	            		 inputMode:'20'},
		            {
            			 displayName:'字段12',
            			 name:'fieldName12',
            			 defaultValue:'2',
            			 optionSet:optionSet,
            			 inputMode:'19'
		            },
		            {
		            	displayName:'字段13',
		            	name:'fieldName13',
		            	defaultValue:'请选择',
		            	designatedId:'test_liushui',
		            	designatedType:'test_流水',
		            	isOverride:'0',
		            	showType:'1',
		            	isSaveDb:'1',
		            	fontColor:'red',
		            	inputMode:'7'
		            },
		            {
		            	displayName:'字段14',
		            	name:'fieldName14',
		            	designatedId:'test_liushui',
		            	isSaveDb:'true',
		            	showType:'1',
		            	fontColor:'red',
		            	inputMode:'29'},
		            {
		            		displayName:'字段15',
		            		name:'fieldName15',
		            		defaultValue:'',
		            		showType:'1',
		            		fontColor:'blue',
		            		inputMode:'30',
		            		dbDataType:'8'
		            },
		            {
		            	displayName:'字段16',
		            	name:'fieldName16',
		            	showType:'1',
		            	fontColor:'red',
		            	inputMode:'11'
		            },
		            	{displayName:'字段17',
		            	name:'fieldName17',
		            	showType:'1',
		            	inputMode:'32',
		            	relationDataText:'邮件模块/邮件收件箱',
		            	relationDataValue:'5e1422bc-152c-4f49-8e2b-230ae663b445'
		            },
		            {
		            	displayName:'字段18',
		            	name:'fieldName18',
		            	showType:'1',
		            	inputMode:'26',
		            	relationDataValueTwo:'f2a6385f-7177-4a7d-8aff-8e1b4ca114ee',
		            	relationDataDefiantion:relationDataDefiantion
		            	},   
		            	{
			            	displayName:'字段19',
			            	name:'fieldName19',
			            	showType:'1',
			            	inputMode:'6',
			            	},
		            	{
			            	displayName:'字段21',
			            	name:'fieldName21',
			            	showType:'1',
			            	inputMode:'33',
			            	ctlWidth:300,//宽度
			    			ctlHight:300//高度
			            	},
		            	{
		            		displayName:'字段22',
		            		name:'fieldName22',
		            		defaultValue:'富文本框控件',
		            		showType:'1',
		            		fontColor:'red',
		            		inputMode:'2',
		            		length:'200',
		            		dbDataType:'1'}
				            	
		]
		
};



	$(document).ready(function() {
		var columns=columnjson.fields;
		var formDefinition={};
		//控件初始化
		$("#btn_init_json").click(function(){
			var inputArr = $("body").find('input');
			for(var i=0;i<inputArr.length;i++){
				var fieldname = $(inputArr[i]).attr("name");//容器或inputname
				for(var j=0;j<columns.length;j++){
					if(fieldname==columns[j].name){
						 	$.ControlManager.createControl(columns[j].name,columns[j],formDefinition);
					}
				}
			} 
		});
		
		//从表控件初始化
		//
		var formDefinition = loadFormDefinition('a204cdc1-e699-4d7f-b385-8aa7f7c34b94');
		$("#fieldName25").wsubForm({
			$paranentelement:$("#abc"),
			formDefinition:formDefinition
		});
		
		//返回控件对象，并显示其属性.
		$("#btn_getvalue").click(function(){
			 var fieldname=$("#inputvalue").val();
			if(fieldname==""){
				alert("请输入字段名!");
				return;
			}
			
			   $("#dialog").dialog({ height: 'auto',width:'auto',
				   position:['top'],
				   buttons:{                     
                       "确认":function(){
                    	   $(this).dialog("close");
                       },     
                      
                       "关闭": function (){
                           $(this).dialog("close");
                       }}
			   });
			
			 var ctr=getCtr(); //返回实例
			 var html='<p><table border="1" cellpadding="0" cellspacing="0"><tr><td>属性</td><td>值</td></tr>'
			 + '<tr><td>字段属性列表</td></tr>'
			 + '<tr><td>columnName</td><td>'+ctr.getAllOptions().columnProperty.columnName+'</td></tr>'
			 + '<tr><td>displayName</td><td>'+ctr.getAllOptions().columnProperty.displayName+'</td></tr>'
			 + '<tr><td>dbDataType</td><td>'+ctr.getAllOptions().columnProperty.dbDataType+'</td></tr>'
			 + '<tr><td>length</td><td>'+ctr.getAllOptions().columnProperty.length+'</td></tr>'
			 
			 + '<tr><td>showType</td><td>'+ctr.getAllOptions().columnProperty.showType+'</td></tr>'
			 + '<tr><td>defaultValue</td><td>'+ctr.getAllOptions().columnProperty.defaultValue+'</td></tr>'
			 + '<tr><td>valueCreateMethod</td><td>'+ctr.getAllOptions().columnProperty.valueCreateMethod+'</td></tr>'
			 + '<tr><td>公共属性列表</td></tr>'
			 + '<tr><td>inputMode</td><td>'+ctr.getAllOptions().commonProperty.inputMode+'</td></tr>'
			 + '<tr><td>fieldCheckRules</td><td>'+ctr.getAllOptions().commonProperty.fieldCheckRules+'</td></tr>'
			 + '<tr><td>fontSize</td><td>'+ctr.getAllOptions().commonProperty.fontSize+'</td></tr>'
			 + '<tr><td>fontColor</td><td>'+ctr.getAllOptions().commonProperty.fontColor+'</td></tr>'
			 + '<tr><td>fontWidth</td><td>'+ctr.getAllOptions().commonProperty.fontWidth+'</td></tr>'
			 + '<tr><td>fontHight</td><td>'+ctr.getAllOptions().commonProperty.fontHight+'</td></tr>'
			 + '<tr><td>textAlign</td><td>'+ctr.getAllOptions().commonProperty.textAlign+'</td></tr>'
			 + '<tr><td>---</td></tr>'
			 + '<tr><td>disabled</td><td>'+ctr.getAllOptions().disabled+'</td></tr>'
			 + '<tr><td>readOnly</td><td>'+ctr.getAllOptions().readOnly+'</td></tr>'
			 + '<tr><td>isHide</td><td>'+ctr.getAllOptions().isHide+'</td></tr>'
			 + '<tr><td>isShowAsLabel</td><td>'+ctr.getAllOptions().isShowAsLabel+'</td></tr>'
			 + '<tr><td>get属性列表</td></tr>'
			 + '<tr><td>控件name</td><td>'+ctr.getCtlName()+'</td></tr>'
			 + '<tr><td>isedit</td><td>'+ctr.isEditable()+'</td></tr>'
			 + '<tr><td>getRule</td><td>'+ctr.getRule()+'</td></tr>'
			 + '<tr><td>getMessage</td><td>'+ctr.getMessage()+'</td></tr>'
			 + '<tr><td>isRequired</td><td>'+ctr.isRequired()+'</td></tr>'
			 + '<tr><td>getValue</td><td>'+ctr.getValue()+'</td></tr>'
			 + '<tr><td>isVisible</td><td>'+ctr.isVisible()+'</td></tr>'
			 + '<tr><td>isEnable</td><td>'+ctr.isEnable()+'</td></tr>';
			 if(ctr.isValueMap()){
				 html=html+ '<tr><td>getDisplayValue</td><td>'+ctr.getDisplayValue()+'</td></tr>';
				 html=html+ '<tr><td>getValueMap</td><td>'+JSON.stringify(ctr.getValueMap())+'</td></tr>';
			}
			 if(ctr.getAllOptions().commonProperty.inputMode==dyFormInputMode.number){
				 html=html+ '<tr><td>decimal</td><td>'+ctr.getAllOptions().decimal+'</td></tr>';
				 html=html+ '<tr><td>negative</td><td>'+ctr.getAllOptions().negative+'</td></tr>';
			}
			 if(ctr.getAllOptions().commonProperty.inputMode==dyFormInputMode.ckedit){
				 html=html+ '<tr><td>ckText</td><td>'+ctr.getCkText()+'</td></tr>';
				 html=html+ '<tr><td>ckHtml</td><td>'+ctr.getCkHtml()+'</td></tr>';
			}
			 html=html+'</table>';
			 $("#dialog").html(html);

		});
		
		$("#btn_showlabel").click(function(){
			getCtr().setDisplayAsLabel();
		});
		
		$("#btn_cancellabel").click(function(){
			getCtr().setDisplayAsCtl();
		});
		
		
		$("#btn_setEnable").click(function(){
			getCtr().setEnable(false);
		});
		
		$("#btn_setedit").click(function(){
			getCtr().setEditable();
			document.getElementsByName(getCtr().getCtlName()).item(0).select();
		});
		
		$("#btn_setrequire").click(function(){
			getCtr().setRequired(true);

		});
		
		$("#btn_setnorequire").click(function(){
			getCtr().setRequired(false);

		});
		
		$("#btn_setmapvalue").click(function(){
			getCtr().setValueByMap('{"DY_EXCHANGE_FIELD_COPY_UNIT":"公文交换/抄送单位"}');

		});
		$("#btn_setmapvalue1").click(function(){
			getCtr().setValueByMap('{"2":"orange"}');
//
		});
		
		$("#btn_setvalue").click(function(){
			getCtr().setValue("dy_work_task_assign_begintime");
		});
		
		$("#btn_setselect").click(function(){
			getCtr().setValue("3");

		});
		
		$("#btn_showfile").click(function(){
			alert(JSON.stringify(getCtr().getValue()));

		});
		
		$("#btn_setValue").click(function(){
			getCtr().setValue($("#value").val());

		});
		
		$("#btn_setDisplayValue").click(function(){
			getCtr().setDisplayValue($("#value").val());

		});
		
		$("#btn_setHide").click(function(){
			getCtr().setVisible(false);

		});
		
		$("#btn_cancelHide").click(function(){
			getCtr().setVisible(true);

		});
		
		$("#btn_subformcollect").click(function(){
			var data=$("#fieldName25").wsubForm("getObject").collectSubformData('1660c076-82a7-4e73-8d44-b0b94708e1ac');
		    alert(JSON.stringify(data));
		});
		
		
		
		
		
		$("#btn_bind").click(function(){
			ctr=getCtr();
			ctr.bind('focus',function(){
				
			}).bind('keydown',function(){
				alert("btnkeydown事件触发1");
			}).bind('blur',function(){
				ctr.setDisplayAsLabel();
			});
		});
		
		function getCtr(){
			var value=$("#inputvalue").val();
			return $.ControlManager.getControl(value.replace(/No/, "fieldName")); 
		}
		
		$("#btn_jds").click(function(){
			var dbFiles = [];
			JDS.call({
	       	 service:"dataDictionaryService.getDataDictionariesByType",
	       	 data:["XZSP_BJ_TYPE"],
	       	 async: false,
				success:function(result){
					var datas = result.data;
					for(var i in datas){
						var data=datas[i]
					   console.log(data.name);
					   console.log(data.code);
					}
				},
				error:function(jqXHR){
					 
				}
			});
			return dbFiles;
		});
		
	});
</script>
<body>
    <font size=5 align='center'>动态表单控件操作DEMO</font>
    <p>
    <div id="dialog" title="控件属性列表"></div>
    <br>
         <input type="button" id="btn_init_json" value="表单初始化" />
    <br>
	请输入字段名:<input type="text" id="inputvalue" value="No1" />
	<input type="button" id="btn_getvalue" value="控件属性" /><input type="button" id="btn_setedit" value="可编辑" /><input type="button" id="btn_setEnable" value="禁用" /><input type="button" id="btn_setrequire" value="设置必输" />
	<input type="button" id="btn_setnorequire" value="取消必输" /><input type="button" id="btn_showlabel" value="显示为label." /><input type="button" id="btn_cancellabel" value="取消label显示." /><input type="button" id="btn_setselect" value="设置选中" /><input type="button" id="btn_bind" value="bind事件" />
	请输入:<input type="text" id="value" value="TEXTVALUE" /><input type="button" id="btn_setValue" value="设置值" />
	<input type="button" id="btn_setDisplayValue" value="设置显示值" /><input type="button" id="btn_setHide" value="设置隐藏" /><input type="button" id="btn_cancelHide" value="取消隐藏" />
	<div id='getvalue'></div>
	
	<br>
      <table >
     <tr>
     <td>文本:No1:</td><td><input id='fieldName1'  name="fieldName1"  /><td>
     <td>复选框:No2:</td><td><input id='fieldName2'  name="fieldName2"  /><input type="button" id="btn_setmapvalue1" value="根据valuemap设置值" /><td>
     <td>单选:No3:</td><td><input id='fieldName3'  name="fieldName3"  /><td>
     </tr>
     
    <tr>
    <td>No4:</td><td><input  name="fieldName4" /><td>
    <td>No5:</td><td><input  name="fieldName5" /><td>
    </tr>
    
    <br>
    <tr>
    
     <td>数字输入框:No7:</td><td><input  name="fieldName7"   /><td>
     <td><font color='red'>树型下拉控件</font>No8:</td><td><input  name="fieldName8"  class="input-search" /><input type="button" id="btn_setmapvalue" value="根据valuemap设置值" /><input type="button" id="btn_setvalue" value="设置值" /><td>
     <td>测试9:No9:</td><td><input  id="fieldName9" name="fieldName9"   /></tr>
     
     <tr>
     <td>下拉框No12:</td><td><input  name="fieldName12"  /><td>
     <td>可编辑流水号控件No13:</td><td><input  name="fieldName13"/><td>
     <td><font color='red'>不可编辑流水号</font>No14 </td><td><input  name="fieldName14"  /><td>
     </tr>
     <tr>
     <td>日期选择框No15:</td><td><input  name="fieldName15"  /><td>
     <td><font color='red'>单位选择框</font>No16:</td><td><input  name="fieldName16"  /><td>

    
     </tr>
     
      <tr>
       <td><font color='red'>弹出对话框</font>No18</td><td><input  name="fieldName18"  /><td>
     <td>selectname:</td><td><input  id="fieldName2001"  /><td>
     <td>selectcode:</td><td><input  id="fieldName2002"  /><td>
     </tr>
     
      <tr>
     <td><font color='red'>上传控件1</font>No19:</td><td><input  name="fieldName19"  /><td>
     <td><font color='red'>上传控件2</font>No20:</td><td><input  name="fieldName20"  /><td>
     <td><font color='red'>上传控件3</font>No21:</td><td><input  name="fieldName21"  /><td>
     </tr>
    </table>
    <br>
    <font color='red'>富文本框初始化</font>No22:<input  name="fieldName22"  />
	<br>
<input type="button" id="btn_jds" value="JDS" />
<input type="button" id="btn_showfile" value="获得控件文件" />
 <font color='red'>视图展示框</font>No17:</td><td><input  name="fieldName17"  />


<input type="button" id="btn_subformcollect" value="从表数据收集" />
<form id="abc" action="">
<input  id="fieldName25" />
<table  border="1" formuuid="1660c076-82a7-4e73-8d44-b0b94708e1ac" style="border-collapse:collapse;" title="userform_hunt_test47">
</table>

<a href="http://localhost:38080/client2CAS/user.jsp"/>cas测试

</form>
</body>
</html>
