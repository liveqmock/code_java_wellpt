//加载全局国际化资源
I18nLoader.load("/resources/pt/js/global");
//加载动态表单定义模块国际化资源
I18nLoader.load("/resources/pt/js/dytable/dytable");
 
 

 var formDefinition =  new MainFormClass();//用于保存定义数据
  
  
//收集用户配置信息
function collectFormDatas(){ 
	var uuid = $('#formUuid').val();
	var mainTableEnName = $('#mainTableEnName').val();
	var mainTableCnName = $('#mainTableCnName').val();
	var formSign = $('input[name=formSign]:checked').val();
	var tableNum = $('#tableNum').val();
	var moduleId = $('#moduleId').val();
	var moduleName = $('#moduleName').val(); 
	var tableId = $('#tableId').val();
	var applyTo = $('#applyTo2').val();
	
	var printTemplate = $("#getPrintTemplateId").val();
	 
	var printTemplateName = $("#getPrintTemplateName").val();
	var showTableModel = $("#showTableModel").val();
	var showTableModelId = $("#showTableModelId").val();
	var version = $('#version').val(); 
	
	var htmlBodyContent = editor.getData();
	
	 
	 
	
	//formDefinition是一个全局变量,用于保存定义
	formDefinition.outerId = tableId;
	formDefinition.applyTo = applyTo;   
	formDefinition.printTemplateId = printTemplate;
	formDefinition.printTemplateName = printTemplateName;
	formDefinition.displayFormModelName = showTableModel;
	formDefinition.displayFormModelId = showTableModelId;
	formDefinition.uuid = uuid;
	formDefinition.name = mainTableEnName;
	formDefinition.displayName = mainTableCnName;
	formDefinition.enableSignature = formSign;
	formDefinition.code = tableNum;
	 
	formDefinition.moduleId = moduleId;
	formDefinition.moduleName = moduleName;  
	formDefinition.version = version; 
	formDefinition.html = htmlBodyContent;
	
	
	//设置状态字段
	/*var fieldStatus = formDefinition.fields["status"];
	if(typeof fieldStatus == "undefined"){
		fieldStatus = new MainFormFieldClass();
		fieldStatus.name = "status";
		fieldStatus.displayName = "状态";
		fieldStatus.length = "10";
		fieldStatus.showType = dyshowType.hide;//默认为隐藏
		fieldStatus.sysType = dyFieldSysType.system;//系统字段
		var optionSet = {"DYFORM_DATA_STATUS_DEFAULT":"表单数据默认状态", "DYFORM_DATA_STATUS_DEL":"数据已删除状态"}; 
		for(var i in optionSet){//默认为第一个元素
			fieldStatus.defaultValue = i;
			break;
		}
		
		fieldStatus.valueCreateMethod = dyFormInputValue.userImport;
		fieldStatus.dbDataType = dyFormDataType.string;
		fieldStatus.inputMode = dyFormInputMode.radio;
		optionSet.optionSet= optionSet;
		optionSet.data=[{value:"DYFORM_DATA_STATUS_DEFAULT", label:"表单数据默认状态"},{value: "DYFORM_DATA_STATUS_DEL",label:"数据已删除状态"}]; 
		formDefinition.fields["status"] = fieldStatus;
	}*/
	
	//去掉没用的字段
	var fields = formDefinition.fields;
	for(var i in fields){
		
	}
	
	
	//去掉没用的从表
	
  
}


var validateAndSaveForm = function(evenSource){
	$(".nav-tabs li a").each(function(index) {//如果第一个页签被隐藏，目前的验证框架没对其进行验证，所以在这里把第一个面签调整出来
		if(index == 0){
			$(this).trigger("click");
		}
	});
	 
	window.setTimeout(function(){
		var isValid = validator.form(); 
		if(!isValid){//验证不通过  
			return;
		}
		 
		saveForm(evenSource);
	}, 30);
};
 
var saveForm = function(evenSource){ 
	 
	formDefinition.isUp = evenSource;//是否表单版本升级1:为升级;0为保存或更新
	
	collectFormDatas();
	
	var url;
	var uuid = $('#formUuid').val();
	if("undefined" == uuid || uuid == "" || uuid == null){
		url = contextPath + '/dyform/save_form_by_html';
		
	}else{
		url = contextPath + '/dyform/update_form_by_html';
		if(formDefinition.isUp == "0" && !confirm("确定要更新表单定义吗?")){
			return false;
		}
	}
	
	if(!/^userform_.*$/.test(formDefinition.name.toLowerCase())){ 
		alert("数据库表名须以字符串\"USERFORM_\"开头");
		return false;
	}
	
	formDefinition.definitionJson = JSON.stringify(formDefinition);
	
	if(formDefinition){
		delete formDefinition.fields;
		delete formDefinition.subforms;
		delete formDefinition.html;
	}
	
	 
	//var json = JSON.stringify(formDefinition.formDisplay);
	 
	 
	$.ajax({
		url:url,
		type:"POST",
		data:  JSON.stringify(formDefinition),
		dataType:'json',
		contentType:'application/json',
		success:function (data){
			if(dyResult.success == data){
				$('#moduleDiv').html("");
				alert("保存成功!");
				$("#"+$(window.opener.document.getElementById("tt")).attr("id")).trigger('reloadGrid');
				window.opener.location.reload();//刷新父窗口页面
				window.close();
			}
		},
		error:function(data){
			console.log(JSON.stringify(data));
		}
	});
};	

/**
 * 设置校验规则
 * @param uuid
 */
function setValidateOptions(uuid){ 
	var validateRules = {};
	var validateMessages = {};
	if(typeof uuid== "undefined" ||  uuid== "undefined" ||  uuid== null ||  uuid== ""){
		//新增 
		validateRules = {
			tableId : {
				required : true,
				remote : {
					url : ctx + "/common/validate/check/exists",
					type : "POST",
					async: false,
					data : {
					  uuid:function() {
						return $('#formUuid').val();
					}, 
					checkType : "dyFormDefinition",
					fieldName : "outerId",
					fieldValue : function() {
									return $('#tableId').val();
								}
					   }
					}
			},
			mainTableCnName : {
				required : true
			},
			
			mainTableEnName : {
				required : true,
				remote : {
				url : ctx + "/common/validate/check/exists",
				type : "POST",
				async: false,
				data : {
				uuid:function() {
					return $('#formUuid').val();
				},
				checkType : "dyFormDefinition",
				fieldName : "name",
				fieldValue : function() {
								return $('#mainTableEnName').val();
							}
				   }
				}
			}
		};
		validateMessages = {
			tableId:{
				required : "ID不能为空！",
					remote : "该ID已存在!"
			},
			mainTableCnName : {
				required : "表单名称不能为空!"
			},
			mainTableEnName : {
				required : "数据库名称不能为空!",
				remote : "该数据库名称已存在!"
			}
		};
	
	}else{//更新或者版本升级 
		validateRules = {
				mainTableCnName : {
					required : true
				}
			};
		validateMessages = {
			 
			mainTableCnName : {
				required : "表单名称不能为空!"
			} 
			 
		};
	}
	validator = $("#mainForm").validate({rules: validateRules, messages: validateMessages});
}

 
 
 

var fillFormDesignTab = function(defintionObj){ 
	 
	setTimeout(function(){CKEDITOR.instances.moduleText.setData(defintionObj.html);},500); 
};


 
var fillBasicPropertyTab = function(defintionObj){
	
	
	
	$('#tableId').val(defintionObj.outerId);//该表对外暴露出去的id
	$('#tableId').attr("readonly", true);//编辑时,表名只读
	
	$('#mainTableEnName').val(defintionObj.name);
	
	$('#mainTableEnName').attr("readonly", true);//编辑时,表名只读
	
	
	
	$('#mainTableCnName').val(defintionObj.displayName);
	$('input[type=radio][name=formSign][value=' + defintionObj.enableSignature + ']').attr('checked',true);
	$('#tableNum').val(defintionObj.code);
	
	
	
	$('#moduleId').val(defintionObj.moduleId);
	
	$('#version').val(defintionObj.version);
	//$('#htmlPath').val(data.tableInfo.htmlPath);
	$("#applyTo2").val(defintionObj.applyTo);
	 
	$("#getPrintTemplateId").val(defintionObj.printTemplateId);
	 
	$("#getPrintTemplateName").val(defintionObj.printTemplateName);
	
	$("#showTableModelId").val(defintionObj.displayFormModelId);
	$("#showTableModel").val(defintionObj.displayFormModelName);
};


function setPageAndDialogTile(uuid){
	var flag = $("#flag").val();//根据该标签来决定该页面的功能:1为显示单据;2为表单 
	if(uuid == "undefined") {
		if(flag == 1) { 
			$('#title').text("新建编辑表单");
			$('#title_h2').text("新建编辑表单");
		}else if(flag == 2) {
			
			$('#title').text("新建显示表单");
			$('#title_h2').text("新建显示表单");
		}
	} else{
		if(flag == 1) { 
			$('#title').text("编辑表单定义");
			$('#title_h2').text("编辑表单定义(" + formDefinition.name + ")");
		}else if(flag == 2) {
			$('#title').text("编辑显示表单");
			$('#title_h2').text("编辑显示表单(" + formDefinition.name   + ")");
		}
	}
	
	if(flag == 1){//编辑表单
		formDefinition.formDisplay = "2";
	}else{//显示单据
		formDefinition.formDisplay = "1";
	}
	
}





(function($) {
	 
	//初始化开始
	 
	
	var uuid = $("#formUuid").val(); 
	
	//清除编辑器
	var instance = CKEDITOR.instances['moduleText'];
	if (instance) { 
		CKEDITOR.remove(instance);
	}
 
	 //初始化编辑器
	editor = CKEDITOR.replace( 'moduleText', {  
			allowedContent:true,
			enterMode: CKEDITOR.ENTER_P,
			toolbarStartupExpanded:true,
			bodyId:"testest",
			customConfig:"./dyform_config.js",
			//工具栏 
			toolbar: [
			          ['Undo','Redo'],['Bold','Italic','Underline'], ['Cut','Copy','Paste'], 
			          ['NumberedList','BulletedList','-'], 
			          ['JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock'],
			          ['Link','Unlink'],['Format','Font','FontSize'],['TextColor','BGColor'],['Image','Table','Maximize'],
			          ["dyform","dysubform"],["control4label","control4text","control4textarea","control4ckeditor","control4radio","control4checkbox","control4combobox",'Button'],[ "control4number", "control4date","control4treeselect","control4serialnumber","control4unit","control4viewdisplay","control4dialog","control4fileupload","control4fileupload4icon", "table"],['dyformpreview'],['Source'],
//			          ,['titleClass','titleClass2','titleClass3']
			          ],
		
			 on: {
			        instanceReady: function( ev ) {
			            this.dataProcessor.writer.setRules( 'p', {
			                indent: true,
			                breakBeforeOpen: false,
			                breakAfterOpen: false,
			                breakBeforeClose: false,
			                breakAfterClose: false
			            });
			        }
			    }
		 });
	
	/*  editor.on("key", function(evt){
	 
		if(evt.data.keyCode == '8' ||evt.data.keyCode == '13' || evt.data.keyCode == '46'){
			return true;
		}
		return false;
	});  */
	
	//初始化编辑器结束
	
	//上传按钮响应事件
	$("#uploadBtn").click(function (){
		$.ajaxFileUpload({
             url:contextPath + '/dytable/upload_html_file.action',//链接到服务器的地址
             secureuri:false,
             fileElementId:'uploadFile',//文件选择框的ID属性
             dataType: 'text',  //服务器返回的数据格式
             success: function (data1, status){
            	 var data1 = eval("("+data1+")");
            	 if(data1 != undefined && dyResult.success == data1.result){
            		 $('#fs2').css('display','');
            		 var htmlPath = data1.htmlPath;
            		 $('#htmlPath').val(htmlPath);
		         	 $.ajax({
		         		 url : contextPath + "/dytable/get_html_body.action",
		         		 cache : false,
		         		 type : "post",
		         		 data : "tempHtmlPath=" + data1.filePath,
		         		 dataType : "json",
		         		 success : function(obj) {
		         			 $('#tempHtmlPath').val(data1.filePath);
		         			 
		         			 $('#moduleDiv').html(obj.htmlContent);
		         			 var iframeDocObj = $('#moduleDiv');
		         			var inputArr = $(iframeDocObj).find("input");
		         			for(var i=0;i<inputArr.length;i++){
		         					if("text" == $(inputArr[i]).attr("type")){
			         					if($(inputArr[i]).attr("name") == undefined) {
			         						$(inputArr[i]).attr("name","text_col"+"_"+i);
			         					}
			         				}
			         				if("radio" == $(inputArr[i]).attr("type")){
			         					if($(inputArr[i]).attr("name") == undefined) {
			         						$(inputArr[i]).attr("name","radio_col");
			         					}
			         				}
			         				if("checkbox" == $(inputArr[i]).attr("type")){
			         					if($(inputArr[i]).attr("name") == undefined) {
			         						$(inputArr[i]).attr("name","checkbox_col");
			         					}
			         				}
			         				
			         				if("button" == $(inputArr[i]).attr("type")) {
			         					if($(inputArr[i]).attr("name") == undefined) {
			         						$(inputArr[i]).attr("name","body_col");
			         					}
			         				}
		         			}
		         			var textareaArr = $(iframeDocObj).find("textarea");
		         			for(var i=0;i<textareaArr.length;i++){
		         				if($(textareaArr[i]).attr("name") == undefined) {
		         					$(textareaArr[i]).attr("name","textarea_col"+"_"+i);
		         				}
		         			}
		         			
		         			var buttonArr = $(iframeDocObj).find("button");
		         			for(var i=0;i<buttonArr.length;i++){ 
		         				if($(buttonArr[i]).attr("name") == undefined) {
		         					$(buttonArr[i]).attr("name","file_upload");
		         				}
		         			}
		         			
		         			var selectArr = $(iframeDocObj).find("select");
		         			for(var i=0;i<selectArr.length;i++){ 
		         				if($(selectArr[i]).attr("name") == undefined) {
		         					$(selectArr[i]).attr("name","select_col");
		         				}
		         			}
		         			 if($('#formUuid').val() == ''){
		         				columns = undefined;
		         				subTables = undefined;
		         			 }
		         			 setEventAndInitFormData();
		         			oAlert("上传成功");
		         		 },
		         		 error:function (){
		         		 }
		         	 });
            	 }else{
            		 oAlert("上传失败");
            	 }
             },
             error: function (data, status, e){
            	 oAlert("上传失败");
             }
		});
	});
	//上传按钮响应事件结束
	
 
	

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//保存、更新动态表单
 
	$("#btn_form_save").click(function(){ 
		
		validateAndSaveForm( "0");//用于表示用户点击的是"保存"按钮 
	});
	
	$("#btn_form_save_new").click(function(){ 
		validateAndSaveForm("1"); //用于表示用户点击的是"版本升级"按钮 
	});
	
	  
	 
	
	//初始化开始结束
	
	//为已经初始化完成的各页面元素填充数据 
	
	if(uuid != "undefined" && uuid != "") {   
		 
		formDefinition = loadFormDefinition(uuid);//加载表单定义 
		
		
		fillBasicPropertyTab(formDefinition);//初始化页面中的"基本属性"tab
		
		fillFormDesignTab(formDefinition );//初始化页面中的"表单设计"tab  
		
		setValidateOptions(uuid);//设置校验规则
		 
	}else{
		$("#btn_form_save_new").hide(); 
		 setValidateOptions();//设置校验规则
	}
	
	
	
	
		
	setPageAndDialogTile( uuid);
	
	
	
/*	window.setTimeout(function(){
	 
		$(".nav-tabs li a").each(function(index) {
			 
			if(index == 1){
				$(this).trigger("click");
			}
		}); 
	}, 50);*/
})(jQuery);

