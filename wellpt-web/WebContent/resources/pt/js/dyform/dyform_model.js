//加载全局国际化资源
I18nLoader.load("/resources/pt/js/global");
//加载动态表单定义模块国际化资源
I18nLoader.load("/resources/pt/js/dytable/dytable");

var formDefinition =  null;//用于保存定义数据

var   Model = function(){
		this.displayName="";
		
		//表单属性id
		this.outerId="";

		this.remark="";

		//html body内容  
		this.html ="";

		this.referredFormId="";
		     
};

var model = new Model();

function loadFormDefinitionByFormUuid(formUuid){ 
	var formDatas = loadFormDefinitionData(formUuid, "");
	formDefinition = ( formDatas.formDefinition );
	formDefinition = JSON.parse(formDefinition);
	fillFields(formDefinition);
	return formDefinition;
}

/**
 * 将字段信息填充到设计器左侧的字段列表中
 * @param formDef
 * @returns
 */
function fillFields(formDef){
	var fields = formDef.fields;
	var fieldList = [];
	
	
	for(var fieldName in fields){
		var field = fields[fieldName];
		fieldList.push(field);
	}
	
	var i = 0;
	var sortedFieldList = fieldList;
	sortedFieldList = sortByKey(fieldList, "name");
	$("#fieldsTbl").find("tr[name]").remove();
	for(var index = 0; index < sortedFieldList.length; index ++){
		var field = sortedFieldList[index];
		var fieldName = field.name;
		var displayName = field.displayName;
		$("#fieldsTbl").append("<tr name='" + fieldName + "'><td>" + fieldName + "</td><td>" + displayName +  "</td></tr>");
		i ++;
	}
	
	$('#fieldsTbl tr').hover(function(){
		
		$(".odd").removeClass("odd");
		 $(this).addClass("odd");
		/* var fieldName = $(this).attr("name");
		 * if( editor.document){
			 var fieldElems = editor.document.find(".model_field[name]");
			 for(var index = 0; index  < fieldElems.count(); index ++){
				 var fieldElem = fieldElems.getItem(index);
				 var name = fieldElem.getAttribute("name");
				 if(name == fieldName){
					 fieldElem.setAttribute("style", "background:red;");
				 }else{
					 fieldElem.setAttribute("style", "background:red;");
				 }
			 }
			 
		 }
		*/
	});
	

	$('#fieldsTbl tr:gt(0)').dblclick(function(){
		var fieldName = $(this).find("td:first").html(); 
		var displayName =  $(this).find("td:eq(1)").html();
		displayName = "${" + displayName + "}";
		//var ctlHtml = "<input  name='" + fieldName + "' title='" + displayName +"' />";
		//var ctlHtml = "<img class=\"value\" name=\"" + fieldName  +"\" src=\"resources/ckeditor4.4.2/plugins/control4text/images/textctl.jpg\" title=\"" + displayName + "\" />";
		 
		var ctlHtml = "<span class='model_field' name='" + fieldName + "'>" + displayName+ "</span>";
		var element = CKEDITOR.dom.element.createFromHtml( ctlHtml );
		editor.insertElement( element ); 
		//$(".cke_dialog_ui_button").trigger("click");
	});
	
	//" + ((i%2 != 0)?"":"class='odd'" ) +  "
	
}

/*var myList = [
              {"id": 1, "sortOrder": 4},
              {"id": 2, "sortOrder": 2},
              {"id": 3, "sortOrder": 3},
              {"id": 4, "sortOrder": 1}
      ];
*/
//myList = sortByKey(myList, "sortOrder");

function sortByKey(array, key) {
  return array.sort(function(a, b) {
      var x = a[key]; var y = b[key];
      return ((x < y) ? -1 : ((x > y) ? 1 : 0));
  });
}



/**
 * 加载所有的表单
 */
function loadFormTree(){
	var referredFormId = $("#referredFormId").val(); 
	var focusedFormUuid = "";
	if(formDefinition == null && referredFormId.length > 0){
		var formDatas = loadFormDefinitionDataByFormId(referredFormId, "");
		formDefinition = ( formDatas.formDefinition );
		formDefinition = JSON.parse(formDefinition); 
		fillFields(formDefinition);
		$("#referredFormName").val(formDefinition.displayName); 
		focusedFormUuid = formDefinition.uuid;
	}
	
	//封装配置参数
	var setting = {
			async : {
				otherParam : {
					"serviceName" : "dyFormDefinitionService",
					"methodName" : "getFormOutlineOfAllVersionTree",
					"data":[focusedFormUuid]//设置该参数指定的表单ID,在树显示时为选中状态
				}
			},
			check : {
				enable : false
			},
			callback : {
				onClick: function(event, treeId, treeNode) { 
					//console.log(JSON.cStringify(treeNode));
					$("#referredFormName").val(treeNode.name); 
					formDefinition = loadFormDefinitionByFormUuid(treeNode.id);
					$("#displayName").val(formDefinition.displayName);
					$("#modelId").val(formDefinition.outerId);
					$("#referredFormName").comboTree("hide");
					$("#formUuid").val(formDefinition.uuid);
					$("#referredFormId").val(formDefinition.outerId);
				},
				onAsyncSuccess:function(event, treeId, treeNode, msg) {
					
				}
			}
		}; 
	//加载表单树 
	$("#referredFormName").comboTree({
		treeSetting : setting
	});
}


/**
 * 设置校验规则
 * @param uuid
 */
function setValidateOptions( ){
	var validateRules = {};
	var validateMessages = {};
		//新增 
		validateRules = {
				modelId : {
				required : true,
				remote : {
					url : ctx + "/common/validate/check/exists",
					type : "POST",
					async: false,
					data : {
					 uuid:function() {
						 var uuid = $('#uuid').val(); 
						return uuid;
					}, 
					checkType : "dyFormDisplayModel",
					fieldName : "outerId",
					fieldValue : function() {
									var outerId = $('#modelId').val(); 
									return outerId;
								}
					   }
					}
			},
			referredFormName:{
				required : true, 
			}
		};
		validateMessages = {
				modelId:{
				required : "ID不能为空！",
					remote : "该ID已存在!", 
			},
			referredFormName:{
				required : "请选择参照表单", 
			}
		};
	 
	validator = $("#mainForm").validate({rules: validateRules, messages: validateMessages});
}

function setTitle(titleVal){
	//var title=document.getElementsByTagName('title').item(0); 
	//title.innertHTML = titleVal;
	//alert(title.innertHTML);
	try{
		$("#title_h2").html(titleVal);
		$("#title").html(titleVal);
	}catch(e){
		
	}
	
	 
}


var   Model = function(){
		this.uuid = "";
		this.displayName="";
		
		//表单属性id
		this.outerId="";

		this.remark="";

		//html body内容  
		this.html ="";

		this.referredFormId="";
};

var validateAndSaveForm = function(){
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
		 
		saveModelData();
	}, 30);
};


function saveModelData(){
	  model.referredFormId = $("#referredFormId").val();
	  model.outerId = $("#modelId").val();
	  model.displayName = $("#displayName").val(); 
	  model.html = editor.getData();
	  model.uuid = $("#uuid").val(); 
	  var previewSize =  $("#preview:checked").size();
	  model.preview =  previewSize == 0 ? "NO":"YES";
	  var url = contextPath + "/dyformmodel/saveDisplayModel";
	  
	$.ajax({
		url:url,
		type:"POST",
		data:  {model:JSON.cStringify(model)},
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
				 window.location.href = contextPath + "/dyformmodel/openDisplayModel?uuid=" + result.data + ""; 
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
}

function loadDisplayModel(){
	var uuid =  $("#uuid").val();
	if($.trim(uuid).length == 0){return;}
	var model = loadDisplayModelDefintion(uuid);
	if(model == null){
		return  ;
	}
	 $("#referredFormId").val(  model.referredFormId);
	 $("#modelId").val(model.outerId);
	 $("#displayName").val(model.displayName); 
	 if(model.preview == "YES"){
		 $("#preview").attr("checked",true);
	 }else{
		 $("#preview").attr("checked",false);
	 }
	 $("#preview").val(model.preview); 
	 editor.setData(model.html );  
	 
	 setTitle(model.displayName);
	 
}

function collectFormDatas(){
	if(formDefinition != null){
		formDefinition.displayAsFormModel = true;
		formDefinition.displayFormModelId = undefined;
		formDefinition.html = editor.getData();
		return true;
	}else{
	 var msg = "未知的表单定义, 请选择参照表单";
	  alert(msg);
	 return false;
	}
}

var editor = null;

function initEditor(){
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
			          ['Undo','Redo'],
			          ['Bold','Italic','Underline'], 
			          ['Cut','Copy','Paste'], 
			         // ['NumberedList','BulletedList','-'], 
			          ['JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock'],
			          ['Link','Unlink'],
			          ['Format','Font','FontSize'],
			         ['TextColor','BGColor'],
			        // ['control4btn'],
			          [//'Image',
			           'Table','Maximize'], 
			           [ "modelField", "modelSubform"],
			         ["block"],
			          //["control4label","control4text",
			          // 		"control4textarea","control4ckeditor","control4radio",
			         //  		"control4checkbox","control4combobox"],
			         // [ "control4number", "control4date","control4treeselect","control4serialnumber",
			         //   "control4unit","control4viewdisplay","control4dialog","control4fileupload","control4fileupload4icon",
			         //   "control4fileupload4image", "table"],['dyformpreview'],
			        //  ['propertiesDialog'],
			          ['Source']
//			          ,['titleClass','titleClass2','titleClass3']
			          ],
		
			 on: {
			        instanceReady: function( ev ) {
			           $(this.document.find("body").getItem(0)).attr("class", "dyform");
			        	var _this = this;
			            this.dataProcessor.writer.setRules( 'p', {
			                indent: true,
			                breakBeforeOpen: false,
			                breakAfterOpen: false,
			                breakBeforeClose: false,
			                breakAfterClose: false
			            });
			        },
					key:function(evt){
					},
					change:function(evt){
						 
					},
					doubleclick:function(evt){
						//this.removeHintInDesigner();
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
	
}
$(function(){
	initEditor();//初始化ckeditor
	
	
	setValidateOptions();//设置校验规则 
	
	//var modelId = $("#modelId").val();
	//var displayName = $("#displayName").val();
	
	
	loadDisplayModel();
	
	
	
	
	
	//setTitle(formDefinition.displayName);
	
	
	loadFormTree();
	
	
	//清除编辑器
	var instance = CKEDITOR.instances['moduleText'];
	if (instance) {
		CKEDITOR.remove(instance);
	}
	 
	
	
	$("#btn_form_save").click(function(){ 
		validateAndSaveForm();//收集显示单据的数据 
	});
	
	 if($.trim($("#uuid")).length > 0){
			window.setTimeout(function(){ 
				$(".nav-tabs li a").each(function(index) {
					if(index == 1){
						$(this).trigger("click");
					}
				}); 
			}, 50);
	 }
	 
	 $("#statistic").click(function(){
		 var $html =  $(editor.getData());
		 if(formDefinition == null){
			 return;
		 }
		 var fields = formDefinition.fields; 
			for(var fieldName in fields){
				var $tr =  $("#fieldsTbl tr[name='" + fieldName + "']");
				 if($html.find(".model_field[name='" + fieldName + "']").size() > 0){
					 $tr.addClass("be-selected");
				 }else{
					 $tr.removeClass("be-selected"); 
				 }
			} 
	 });
	 $("#statistic").trigger("click");
});


 

