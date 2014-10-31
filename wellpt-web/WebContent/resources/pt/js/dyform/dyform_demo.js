var _ExcelImp4MainForm = excelImp4MainForm;
var	_ExcelExp4MainForm = excelExp4MainForm;
var	_ExcelExp4MainFormEx = excelExp4MainFormEx;

window.excelImp4MainForm = function(formEl){
	//_ExcelImp4MainForm("abc");
	_ExcelImp4MainForm(formEl);
};
window.excelExp4MainForm = function(formEl,formId){
	//_ExcelExp4MainForm("abc",formId);
	_ExcelExp4MainForm(formEl,formId);
};
window.excelExp4MainFormEx = function (formEl,formId){
	//_ExcelExp4MainFormEx("abc",formId);
	_ExcelExp4MainFormEx(formEl,formId);
};
$(function() {
	/**
	var dyform_excel =  function (){
		var FORM_UUID = "formUuid";
		var FORM_DATAS = "formDatas";
		var DELETED_FORM_DATAS = "deletedFormDatas";
		getFormUuid:function(formData){
			return formData[FORM_UUID];
		};
		getFormDatas:function(formData){
			return formData[FORM_DATAS];
		};
		getMainFormDatas:function(formData){
			return getFormDatas(formData)[getFormUuid(formData)];
		};
	};
	*/
	var FORM_UUID = "formUuid";
	var FORM_DATAS = "formDatas";
	var DELETED_FORM_DATAS = "deletedFormDatas";

	function getFormUuid(formData){
		return formData[FORM_UUID];
	};
	function getFormDatas(formData){
		return formData[FORM_DATAS];
	};
	function getMainFormDatas(formData){
		return getFormDatas(formData)[getFormUuid(formData)];
	};
	function getMainFormData(formData){
		return getMainFormDatas(formData)[0];
	};
	
	//表单定义页面中的预览功能
	if(typeof formDefinitionFromDefinitionModule != "undefined" && formDefinitionFromDefinitionModule.length > 0){
		//表单定义预览功能
		var f = urldecode(formDefinitionFromDefinitionModule);
		f = (urldecode(f));
		var fo = eval("("+urldecode(f)+")");
		fo.enableSignature = signature.disable;
		try{
			$("#title").text(fo.displayName +"(解析)"); 
		}catch(e){
			
		}
		
		$("#abc").dyform(
				{
					definition:fo ,
					data:{},
					displayAsFormModel:fo.displayAsFormModel,
					optional:{
						isFirst:true
					},
					success:function(){
						console.log("表单解析完毕");
					},
					error:function(msg){
						 
						 
						oAlert(msg);
					}
				} 
		);
		
		$("#demoBtn").hide();
		return;
	}
	 
	
	
	console.log("dyform_demo from here");
	var time1 =( new Date()).getTime();
 	 var dataUuid = $("#dataUuid").val();
	var formuuid = $("#formUuid").val();
	var formDatas = loadFormDefinitionData(formuuid, dataUuid);
	var time2 =( new Date()).getTime();
	console.log("loadFormDefinitionData:" + (time2 - time1)/1000.0 + "s");
	 
	if(typeof formDatas == "string"){
		formDatas = (eval("(" + formDatas +  ")"));
	}
	var titleElem=document.getElementsByTagName('title').item(0); 
	 
	var text =  eval("(" + formDatas.formDefinition +  ")").displayName +"(解析)";
	 
	 
	try{
		$(titleElem).text(text);//ie8不兼容
	}catch(e){
		
	}
	var time3 =( new Date()).getTime();
	console.log("获取数据:" + (time3 - time1)/1000.0 + "s");
	
	$("#abc").dyform(
			{
				definition:  formDatas.formDefinition ,
				data:formDatas.formDatas,
				 displayAsLabel:false,//显示为文本
					optional:{
						isFirst:true
					},
				 displayAsFormModel:false,//displayAsLabel为true的前提下该参数才有效,默认为true
									//false:表示不用显示单据,true:使用显示单据,这时该若找不到对应的显示单据，则直接以该表单的模板做为显示单据
				
				success:function(){
					console.log("表单解析完毕");
				},
				error:function(msg){ 
					oAlert(msg);
				}
			} 
	); 
	//$("#abc").dyform("addRowEmptyDataByDefaultRowCount", "uf_test_01");
	
	
	
	//$("#abc").dyform("setColumnCtl", "ky", "wdjs1");
	//setColumnCtl:function(formId, mappingName, controlable)
	//$("#abc").dyform("setColumnCtl", formId, mappingName);
	
	 
	 
	
	
	//在这里直接用parseForm接口代码 
 
	//console.log("开始填充数据");
	/*var dataUuid = $("#dataUuid").val();
	if(typeof dataUuid != "undefinition" && dataUuid.length > 0){
		formDatas = loadFormData($("#formUuid").val(), dataUuid); 
		$("#abc").dyform("fillFormData", formDatas, function(){
			console.log("数据填充完成");
		});
	}
	*/
	
	
	
 
	 
	var validateForm = undefined;
	$("#validate").bind("click", function(){
		validateForm   = 	$("#abc").dyform("validateForm");
		console.log("valid:" + validateForm); 
	});
	
	
	
	
	var formData = undefined;
	var formDisplayData = undefined;
	
	$("#collectFormData").click(function(){
		if(validateForm ==  undefined){
			alert("请先验证数据");
			return;
		}else if(validateForm == false){
			alert("验证失败");
			return;
		}
		
		formData = $("#abc").dyform("collectFormData");
		formDisplayData = $("#abc").dyform("collectFormDisplayData");
		if(typeof formData != "undefined"){
			console.log(JSON.cStringify(formData));
		}
		if(typeof formDisplayData != "undefined"){
			console.log(JSON.cStringify(formDisplayData));
		}
	});
	
	$("#save").click(function(){
		if(validateForm ==  undefined){
			alert("请先验证数据");
			return;
		}else if(validateForm == false){
			alert("验证失败");
			return;
		}else if(formData ==  undefined){
			alert("请先收集数据");
			return;
		}
		var url = ctx + "/dyformdata/saveFormData";
		//console.log( JSON.cStringify(formData));

		$.ajax({
			url:url,
			type:"POST",
			data:  JSON.cStringify(formData),
			dataType:'json',
			contentType:'application/json',
			success:function (result){
				 if(result.success == "true" || result.success == true){
					  alert("数据保存成功dataUuid=" + result.data);
					  var dataUuid =  result.data;
					  var formUuid = $("#formUuid").val();
					  var url = ctx + '/dyform/demo?formUuid=' + formUuid + "&dataUuid=" + dataUuid; 
					  window.location.href = url;
       		   }else{
       			   alert("数据保存失败");
       		   }
			},
			error:function(data){
				 alert("数据保存失败");
			}
		});
		
	});

	//
	$("#excelImp").click(function(){
		excelImp4MainForm("abc");
		return false;
		
		$("#uploadFileDiv").dialog({
			autoOpen : true,
			height : 200,
			width : 400,
			modal : true,
			buttons : {
				"确定" : function() {
					var file = $("#uploadfile").val();
					if (file == '') {
						alert("请选择xls文件");
						return;
					}
					if (file.indexOf(".") < 0) {
						return;
					}
					var fileType = file.substring(file.lastIndexOf("."), file.length);
					if (fileType == ".xls" || fileType == ".xlsx") {
						$.ajaxFileUpload({
							url : ctx + "/basicdata/excel/parseJSONS",// 链接到服务器的地址
							secureuri : false,
							fileElementId : 'uploadfile',// 文件选择框的ID属性
							// dataType : 'json', // 服务器返回的数据格式(不设置会自动处理)
							success : function(data, status) {
								if (data != undefined) {
									data = jQuery.parseJSON(jQuery(data).text());
									//alert(JSON.cStringify(data));
									//var formDatas = data.formDatas;
									//$("#abc").dyform("fillFormDatas",formDatas);
									//$("#abc").dyform("fillFormDataOfMainform",getMainFormData(data));//主表单独设置
									//$("#abc").dyform("fillFormDatas",getFormDatas(data));
									
									// var formId = $("#abc").dyform("getFormId");
									var formId = data.formUuid;
									$("#abc").dyform("fillFormDisplayDatas",data.formDatas,formId);
								};
							},
							error :function(data){
								alert("导入失败");
							}
						});
					} else {
						alert("请选择xls文件");
						return;
					};
				},
				"取消" : function() {
					$(this).dialog("close");
				},
			}
		});
	});
	
	$("#excelExp").click(function(){
		excelExp4MainFormEx("abc");
		return false;
		if(validateForm ==  undefined){
			alert("请先验证数据");
			return;
		}else if(validateForm == false){
			alert("验证失败");
			return;
		}else if(formDisplayData ==  undefined){
			alert("请先收集数据");
			return;
		}
		$("#uploadFileDiv").dialog({
			autoOpen : true,
			height : 200,
			width : 400,
			modal : true,
			buttons : {
				"确定" : function() {
					var file = $("#uploadfile").val();
					if (file == '') {
						alert("请选择xls文件");
						return;
					}
					if (file.indexOf(".") < 0) {
						return;
					}
					var fileType = file.substring(file.lastIndexOf("."), file.length);
					if (fileType == ".xls" || fileType == ".xlsx") {
						$.ajaxFileUpload({
							url : ctx + "/basicdata/excel/parseExcel",// 链接到服务器的地址
							type : "post",
							secureuri : false,
							fileElementId : 'uploadfile',// 文件选择框的ID属性
							data : {paramObject:JSON.cStringify(formDisplayData)},
							dataType : 'json', // 服务器返回的数据格式
							success : function(data) {
								// data = jQuery.parseJSON(jQuery(data).text());
								alert(jQuery(data).text());
							},
							error : function(data, status, e) {
								alert("导入失败");
							}
						});
					} else {
						alert("请选择xls文件");
						return;
					}
				},
				"取消" : function() {
					$(this).dialog("close");
				}
			}
		});
	});
	
	
	
  
	$("#showAsLabel").click(function(){
		$("#abc").dyform("showAsLabel");
	});
	$("#setEditable").click(function(){
		$("#abc").dyform("setEditable");
	});
	$("#setReadOnly").click(function(){
		$("#abc").dyform("setReadOnly");
	});
	$("#setTextFile2SWF").click(function(){
		var val = $(this).attr("flag");
		if(typeof val == "undefined" ||  val == "false"){
			$(this).attr("flag","true"); 
			$("#abc").dyform("setTextFile2SWF", true);
		}else{
			$(this).attr("flag","false"); 
			$("#abc").dyform("setTextFile2SWF", false);
		} 
	});
	$("#enableSignature").click(function(){
		var val = $(this).attr("flag");
		//console.log("ttt-->" + val);
		if(typeof val == "undefined" ||  val == "false"){
			$(this).attr("flag","true"); 
			$("#abc").dyform("enableSignature", true);
		}else{
			$(this).attr("flag","false"); 
			$("#abc").dyform("enableSignature", false);
		}
	});
	
	$("#addRowData").click(function(){
		$("#abc").dyform("getSubformControl","bfa0c271-0412-4c89-ba61-884d5c6f3fa6" ).addRowData({});
		//$("#abc").dyform("getSubformControl","cf00247d-fed2-451c-bbeb-eb5e67c4a94d" ).addRowData({});
		//$("#abc").dyform("addRowData", "userform_receipt", {});
		//$("#abc").dyform("hideSubform", "userform_receipt");
		
	});
	
	$("#hideSubform").click(function(){
		//$("#abc").dyform("getSubformControl","cf00247d-fed2-451c-bbeb-eb5e67c4a94d" ).addRowData({});
		//$("#abc").dyform("getSubformControl","cf00247d-fed2-451c-bbeb-eb5e67c4a94d" ).addRowData({});
		//$("#abc").dyform("addRowData", "userform_receipt", {});
		$("#abc").dyform("hideSubFormByFormUuid", "cf00247d-fed2-451c-bbeb-eb5e67c4a94d");
	
		$("#abc").dyform("hideSubForm", "userform_receipt"); 
		
	});
	
	$("#setFieldAsHide").click(function(){
		$("#abc").dyform("setFieldAsHide", "dy_work_task_apply_name");
	});
	
	$("#setFieldAsShow").click(function(){ 
		$("#abc").dyform("setFieldAsShow", "dy_work_task_apply_name");
	});
	
	
	$("#setFieldReadOnly").click(function(){ 
		$("#abc").dyform("setFieldReadOnly", "dy_work_task_apply_name");
	});
	
	$("#setFieldEditable").click(function(){ 
		$("#abc").dyform("setFieldEditable", "dy_work_task_apply_name");
	});
	
	$("#setFieldAsLabel").click(function(){ 
		$("#abc").dyform("setFieldAsLabel", "dy_work_task_apply_name");
	});
	
	$("#groupSubform").click(function(){ 
		$("#abc").dyform("group", "uf_test_20140717");
	});
	
	
	
	 $("#clickEvent").click(function(){
			$("#abc").dyform("setFieldValue", "FILE_APPROVAL_OPINION", "这里有点击事件");
		$("#abc").dyform("bind2Control", {
			type:"click",
			mappingName:"FILE_APPROVAL_OPINION",
			//dataUuid: ""
			callback:function(){
				alert("FILE_APPROVAL_OPINION被点击了");
			}
		});
	});
	 
	 $("#afterSelect").click(function(){ 
		$("#abc").dyform("bind2Control", {
			type:"afterDialogSelect",
			mappingName:"dy_work_task_apply_begin_time",
			//dataUuid: ""
			callback:function(paramsId , paramsObj){
				 console.log( "paramsId=" + JSON.cStringify(paramsId));
				 console.log( "paramsObj=" + JSON.cStringify(paramsObj));
			}
		});
	});
	 
	 
	 $("#hideColumn").click(function(){ 
		 $("#abc").dyform("hideColumn","uf_xzsp_material_def", "File_title");
		});
	 
	 $("#showColumn").click(function(){ 
			$("#abc").dyform("showColumn","uf_xzsp_material_def", "File_title");
	  });
	 
	 
	 $("#columnReadonly").click(function(){
			$("#abc").dyform("setColumnReadOnly","uf_xzsp_material_def", "File_title");
	 });
	 
	 $("#columnEditable").click(function(){
			$("#abc").dyform("setColumnEditable","uf_xzsp_material_def", "File_title");
	 });
	/* 
	 $("#customBtn").click(function(){
		 $("#abc").dyform("addCustomBtn", {
			 btnId:"testbtn",
			 formId:"uf_test_20140717",
			 title: "选择未完务",
			 click:function(event, selectedRowId从表被选中的行id  ){
				 
			 }
			 
			 
			 
		 });
	 });*/
	 
	 
	 
	 
	 
	 
	 
	 
	 
});
 

