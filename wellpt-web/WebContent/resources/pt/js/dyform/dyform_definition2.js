//加载全局国际化资源
I18nLoader.load("/resources/pt/js/global");
//加载动态表单定义模块国际化资源
I18nLoader.load("/resources/pt/js/dyform/dyform");
 
 

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
	var customJs = $("#customJs").val();
 
	var htmlBodyContent = editor.getData();
	
	//formDefinition是一个全局变量,用于保存定义
	formDefinition.outerId = tableId;
	formDefinition.applyTo = applyTo;   
	formDefinition.printTemplateId = printTemplate;
	formDefinition.printTemplateName = printTemplateName;
	formDefinition.displayFormModelName = showTableModel;
	formDefinition.displayFormModelId = showTableModelId;
	formDefinition.uuid = uuid;
	formDefinition.name = mainTableEnName.toLowerCase();//转成小写
	formDefinition.displayName = mainTableCnName;
	formDefinition.enableSignature = formSign;
	formDefinition.code = tableNum;
	//console.log(JSON.cStringify(formDefinition.fields));
	formDefinition.moduleId = moduleId;
	formDefinition.moduleName = moduleName;
	formDefinition.version = version;
	formDefinition.html = htmlBodyContent;
	formDefinition.customJs = customJs;
	console.log(JSON.cStringify(formDefinition));
	cleanUselessDefinition();//清除无效的定义
	
	return true;
}

/**
 * 清除无效的字段定义
 */
function cleanUselessDefinition(){
	 
	var $html =  $("<span>" + editor.getData() + "</span>");
	
	var fields = formDefinition.fields;
	for(var fieldName in fields){
		if($html.find(".value[name='" + fieldName + "']").size() == 0){//在模板中没有找到该字段,则删除该字段的定义
			console.log(fieldName + " 对应的占位符已被删除，故删除对应的字段定义");
			delete fields[fieldName];
		}
	}
	
	var subforms = formDefinition.subforms;
	for(var formUuid in subforms){
		if($html.find("table[formUuid='" + formUuid + "']").size() == 0){//在模板中没有找到该字段,则删除该从表 的定义
			console.log(formUuid + " 对应的占位符已被删除，故删除对应的从表定义");
			delete subforms[formUuid];
		}
	}
	
	var layouts =  formDefinition.layouts;
	 
	for(var name in layouts){
		if($html.find(".tabLayout[name='" + name + "']").size() == 0){//在模板中没有找到该字段,则删除该从表 的定义 
			console.log(name + " 对应的占位符已被删除，故删除对应的布局定义");
			delete layouts[name];
		}
	}
	
	var blocks = formDefinition.blocks;
	if(typeof blocks != "undefined"){
		for(var blockCode in blocks){
			if($html.find("td[blockCode='" + blockCode + "']").size() == 0){//在模板中没有找到该字段,则删除该区块的定义
				console.log(blockCode + " 对应的占位符已被删除，故删除对应的区块");
				delete blocks[blockCode];
			}
		}
	}
	
	if(formDefinition.defaultFormData){
		delete formDefinition.defaultFormData;
	}
	if(formDefinition.subformDefinitions){
		delete formDefinition.subformDefinitions;
	}
	
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
	
	if(!/^(uf|userform)_.*$/.test(formDefinition.name.toLowerCase())){ 
		alert("数据库表名须以字符串\"UF_\"开头");
		return false;
	}
	
	if(!formDefinition){
		throw new Error("unknown formDefinition");
	}
	
	var formDefinitionCopy = {};
	$.extend(formDefinitionCopy, formDefinition);

	var deletedFieldNames = [];
	if(formDefinitionCopy){
		if(typeof formDefinitionCopy.deletedFieldNames != "undefined"){
			deletedFieldNames = formDefinitionCopy.deletedFieldNames;
			delete formDefinitionCopy.deletedFieldNames; 
		}
	}
	
	
	formDefinitionCopy.definitionJson = JSON.cStringify(formDefinitionCopy);
	
	if(formDefinitionCopy){
		delete formDefinitionCopy.fields;
		delete formDefinitionCopy.subforms;
		delete formDefinitionCopy.html;
		if(formDefinitionCopy.layouts){
			delete formDefinitionCopy.layouts;
		}
		if( formDefinitionCopy.blocks){
			delete formDefinitionCopy.blocks;
		}
		
		if(typeof formDefinitionCopy.customJs =="string"){
			delete formDefinitionCopy.customJs;
		}
		
	}
	
	 
	
  
	$.ajax({
		url:url,
		type:"POST",
		data:  {formDefinition:JSON.cStringify(formDefinitionCopy), deletedFieldNames:JSON.cStringify(deletedFieldNames)},
		dataType:'json',
		async: false,
		timeout: 120000,
		contentType:'application/x-www-form-urlencoded',
		beforeSend: function(){
			pageLock("show");
		},
		complete:function(){
			pageLock("hide");
		},
		success:function (result){
			 if(result.success == "true" || result.success == true){
				 alert("保存成功!");
				// $("#"+$(window.opener.document.getElementById("tt")).attr("id")).trigger('reloadGrid');
				//window.opener.location.reload();//刷新父窗口页面
				//window.close();
				 window.location.href = contextPath + "/dyform/demo/openFormDefinition?uuid=" + result.data + "&flag=1";
				 
 		   }else{
 			   alert("保存失败\n" + result.data);
 		   } 
		},
		error:function(result){
			var responseText = result.responseText;
			try{
				var errorObj = eval("(" + responseText + ")");
				alert("保存失败\n" +errorObj.data);
			}catch(e){
				alert(JSON.cStringify(result));
			}
			
			 
			
			//console.log(JSON.cStringify(data));
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
				maxlength: 27, 
				regex:/^(uf|userform)_.*$/ig,
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
					remote : "该ID已存在!", 
			},
			mainTableCnName : {
				required : "表单名称不能为空!"
			},
			mainTableEnName : {
				required : "数据库名称不能为空!",
				remote : "该数据库名称已存在!",
				maxlength:  $.validator.format("数据库表名不得超过 {0}个字符."),  
				regex:"数据库表名须以 UF_ 开头"
			}
		};
	
	}else{//更新或者版本升级 
		validateRules = {
				mainTableCnName : {
					required : true
				}/*,
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
				}*/
			};
		validateMessages = {
				/*tableId:{
					required : "ID不能为空！",
						remote : "该ID已存在!", 
				},*/
			mainTableCnName : {
				required : "表单名称不能为空!"
			} 
			 
		};
	}
	validator = $("#mainForm").validate({rules: validateRules, messages: validateMessages});
}

 
 
 

var fillFormDesigner = function(html){
	CKEDITOR.instances.moduleText.setData(html);
	//setTimeout(function(){CKEDITOR.instances.moduleText.setData(html);},300); 
};


 
var fillBasicPropertyTab = function(defintionObj){ 
	
	$('#tableId').val(defintionObj.outerId);//该表对外暴露出去的id
	//$('#tableId').attr("readonly", true);//编辑时,表名只读
	
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
	$("#customJs").text(defintionObj.customJs);
};


function setPageAndDialogTile(uuid){
	var flag = $("#flag").val();//根据该标签来决定该页面的功能:1为显示单据;2为表单 
	if(uuid == "undefined") {
		if(flag == 1) { 
			$('#title').html("新建编辑表单");
			$('#title_h2').html("新建编辑表单");
		}else if(flag == 2) {
			
			$('#title').html("新建显示表单");
			$('#title_h2').html("新建显示表单");
		}
	} else{
		if(flag == 1) { 
			$('#title').html("(" + formDefinition.displayName + ")编辑");
			$('#title_h2').html("(" + formDefinition.displayName + ")编辑表单定义");
		}else if(flag == 2) {
			$('#title').html("(" + formDefinition.displayName   + ")编辑");
			$('#title_h2').html("(" + formDefinition.displayName   + ")编辑显示表单");
		}
	}
	
	if(flag == 1){//编辑表单
		formDefinition.formDisplay = "2";
	}else{//显示单据
		formDefinition.formDisplay = "1";
	}
	
}


function removeHintInDesigner(editor){ 
	 var _hintelement = editor.document.find("#user__hint");
	 if(_hintelement.count() > 0){
		 _hintelement.getItem(0).remove();
	 }
}

function initUploadDesignTemplate(){
	 var iframe = false; 
		if($.browser.msie  && $.browser.version < 10){  
			iframe = true; 
		}
	var url = contextPath + '/dyform/uploadDesignTemplate';
	/* $('#uploadFile').fileupload({
		url: url,
		//forceIframeTransport: forceIframeTransport,
		 iframe: iframe,
		dataType: 'text/html',
		//datatype: dataType,
		autoUpload: true,
		//sequentialUploads : true,
		//formData: {signUploadFile: _this.signature},
		maxFileSize: 5000000, // 5 MB
		previewMaxWidth: 100,
		previewMaxHeight: 100,
		previewCrop: true
	}).on('fileuploadadd', function (e, data) {
		 
		pageLock("show");
	}).on('fileuploaddone', function (e, data) {
		pageLock("hide"); 
		if((typeof data) == "undefined"){
			oAlert("不支持上传该格式的文件");
		}else{
			alert(data);
		}
		 
	}).on('fileuploadfail', function (e, data) {
		pageLock("hide");
		if((typeof data.result) == "undefined"){
			oAlert("可能您上传的文件格式不被支持!!!"); 
		}else{
			alert("上传失败");
		}
		
	}); */
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
			bodyClass:"dyform",
			customConfig:"./dyform_config.js",
			//工具栏 
			toolbar: [
			          //['Undo','Redo'],
			          ['Bold','Italic','Underline'], 
			          //['Cut','Copy','Paste'], 
			         // ['NumberedList','BulletedList','-'], 
			          ['JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock'],
			          //['Link','Unlink'],
			          ['Format','Font','FontSize'],
			          ['TextColor','BGColor'],
			         ['control4btn'],
			          [//'Image',
			           'Table','Maximize'], 
			         ["block","dysubform"],
			          ["control4label","control4text",
			           		"control4textarea","control4ckeditor","control4radio",
			           		"control4checkbox","control4combobox"],
			          [ "control4number", "control4date","control4treeselect","control4serialnumber",
			            "control4unit","control4viewdisplay","control4dialog","control4timeEmploy","control4fileupload","control4fileupload4icon",
			            "control4fileupload4image", "table"],['dyformpreview', 'control4embedded', 'control4jobs'],
			          ['propertiesDialog'],//['copyForm'],
			          ['Source']
//			          ,['titleClass','titleClass2','titleClass3']
			          ],
		
			 on: {
				 	
			        instanceReady: function( ev ) {
			           $(this.document.find("body").getItem(0)).attr("class", "dyform");
			        	
			        	var _this = this;
			        	 
			        	 this.document.on("click", function(){
			        		 _this.removeHintInDesigner();
			        	}); 
			            this.dataProcessor.writer.setRules( 'p', {
			                indent: true,
			                breakBeforeOpen: false,
			                breakAfterOpen: false,
			                breakBeforeClose: false,
			                breakAfterClose: false
			            });
			      
			          //从表设置为不可编辑
						 var subforms = this.document.find("table[formUuid]");
						for(var i =0 ; i < subforms.count(); i ++){
							subforms.getItem(i).unselectable(); 
							subforms.getItem(i).setState(CKEDITOR.TRISTATE_OFF);
							
						} 
						
						
						//自定义表格删除事件
						var createDef =  function ( def ) {
				    			return CKEDITOR.tools.extend( def || {}, {
				    				contextSensitive: 1,
				    				refresh: function( editor, path ) {
				    					this.setState( path.contains( 'table', 1 ) ? CKEDITOR.TRISTATE_OFF : CKEDITOR.TRISTATE_DISABLED );
				    				}
				    			});
				    		};

				    		 
						  _this.addCommand( 'tableDelete', createDef({//重新定义表格删除事件,主表不得删除
				    			exec: function( editor ) {
				    			 
				    				var path = editor.elementPath();
				    				var	table = path.contains( 'table', 1 );
				    				
				    				if ( !table )
				    					return;
				    				

				    				// If the table's parent has only one child remove it as well (unless it's the body or a table cell) (#5416, #6289)
				    				var parent = table.getParent();
				    				if ( parent.getChildCount() == 1 && !parent.is( 'body', 'td', 'th' ) )
				    					table = parent;
				    				/*var clazzOfTbl = $(table).attr("class");
				    				if(typeof clazzOfTbl != "undefined" && clazzOfTbl.indexOf("mainform") != -1){
				    					alert("主表不得删除");
				    					return;
				    				}*/
				    				
				    				
				    				
				    				var range = editor.createRange();
				    				range.moveToPosition( table, CKEDITOR.POSITION_BEFORE_START ); 
				    				table.remove();
				    				range.select();
				    				var formUuid = $(table).attr("formUuid");
				    				if(typeof formUuid != "undefined"){//从表
				    					//从JSON定义中删除从表的定义信息
				    					console.log("delete subform " + formUuid);
				    					formDefinition.deleteSubform(formUuid);
				    				}
				    			}
				    		}));
			        },
					key:function(evt){ 
					   
						 if(!(evt.data.keyCode == '8' ||evt.data.keyCode == '13' || evt.data.keyCode == '46'|| evt.data.keyCode == 10000
								 ||   (  evt.data.keyCode == '1114202')
						 )){
							 
							return true;
						} 
						
						//元素删除事件 
						if( evt.data.keyCode == 10000){
							if(evt.data.type == "field"){
								console.log("delete field");
								var elem = evt.data.element;
								var name = elem.getAttribute("name");
								console.log("delete field " + name);
							 
								elem.remove(); 
								formDefinition.deleteField(name);
							} 
						}else if(evt.data.keyCode == '8' ||  evt.data.keyCode == '46' ){
							 var selection = editor.getSelection();
							 if(selection == null){
								 return true;
							 }
								var elem = selection.getStartElement();
								
								
								var clazz = elem.getAttribute("class"); 
								var name = elem.getAttribute("name");
								if(typeof name != "undefined" && typeof clazz != "undefined" && clazz == "value"  ){//占位符删除事件
									if(typeof formDefinition.fields[name] != "undefined"){
										formDefinition.deleteField(name);  
									}
								}
						}
						
						return true;
					
					},
					change:function(evt){
						 
					},
					doubleclick:function(evt){
						this.removeHintInDesigner();
						//var element = evt.data.element;  //element是CKEDITOR.dom.node类的对象 
						//var pluginContainerDomElement = CKEDITOR.getPluginContainerDomElement(pluginName, element); 
						//return this.doubleClick(evt);
					},
					resize:function(evt){
						console.log("resize");
					}
			    }
		 });
	 
	$.extend(editor, ckUtils);
	
	 
 
	

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
		
		fillFormDesigner(formDefinition.html );//初始化页面中的"表单设计"tab  
		
		setValidateOptions(uuid);//设置校验规则
		 
		window.setTimeout(function(){
			 
			$(".nav-tabs li a").each(function(index) {
				 
				if(index == 1){
					$(this).trigger("click");
				}
			}); 
		}, 50);
	}else{
		$("#btn_form_save_new").hide(); 
		 setValidateOptions();//设置校验规则
		//var html = "<div class=\"dyform\">" ;
		 var html = "";
		html +=	"<div id='user__hint' style='color:gray;height:30px;line-height:30px;text-align:center'>请在白色背景区域编辑,以免造成样式混乱</div>";
		html += "<p>&nbsp;</p>";
			 
		//html += "</div>";
		  //alert(21234);
		 fillFormDesigner(html );//初始化页面中的"表单设计"tab  
	}
	
	
	$.extend(formDefinition, formDefinitionMethod);
	
	try{
		setPageAndDialogTile( uuid);//设置标题,ie8兼容性问题
	}catch (e) {
		// TODO: handle exception
	}
	
	
	initUploadDesignTemplate();//初始化上传模板功能
	
	 
	
		
})(jQuery);

