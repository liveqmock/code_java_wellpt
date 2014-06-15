//加载全局国际化资源
I18nLoader.load("/resources/pt/js/global");
//加载动态表单定义模块国际化资源
I18nLoader.load("/resources/pt/js/dytable/dytable");

(function($) { 
	//初始化开始
	var isZW = "";
	var uuid = $("#formUuid").val();
	var flag = $("#flag").val();
	//当前双击表单元素对象
	var currInputObj;

	//当前表格对象
	var currTableObj;

	//主表字段信息数组(用于修改数据还原)
	var columns;

	//从表信息列表
	var subTables;
	
	if(uuid == "undefined") {
		if(flag == 1) {
			$("#formDisplay").val("编辑表单定义");
			$('#title').text("新建编辑表单");
			$('#title_h2').text("新建编辑表单");
		}else if(flag == 2) {
			$("#formDisplay").val("显示表单定义");
			$('#title').text("新建编辑表单");
			$('#title_h2').text("新建显示表单");
		}
	}
	else if(uuid != "undefined" && uuid != "") {
		$.ajax({
			url : contextPath + "/dytable/pre_edit_form.action",
			cache : false,
			async : false,
			type : "get",
			data : "uuid=" + uuid,
			dataType : "json",
			success : function(data) {
				$('#formUuid').val(data.tableInfo.uuid);
				$('#tableId').val(data.tableInfo.id);
				
				$('#mainTableEnName').val(data.tableInfo.tableName);
				$('#mainTableCnName').val(data.tableInfo.tableDesc);
				$('input[type=radio][name=formSign][value=' + data.tableInfo.formSign + ']').attr('checked',true);
				$('#tableNum').val(data.tableInfo.tableNum);
				if(data.tableInfo.formDisplay == 2) {
					$('#title').text(data.tableInfo.tableDesc+"(编辑表单)");
					$('#title_h2').text(data.tableInfo.tableDesc+"(编辑表单)");
					$('#formDisplay').val("编辑表单定义");
				}else {
					$('#title').text(data.tableInfo.tableDesc+"(显示表单)");
					$('#title_h2').text(data.tableInfo.tableDesc+"(显示表单)");
					$('#formDisplay').val("显示表单定义");
				}
//				$('input[type=radio][name=formDisplay][value=' + data.tableInfo.formDisplay + ']').attr('checked',true);
				$('#moduleId').val(data.tableInfo.moduleId);
//				var moduleId = $("#moduleId").val();
//				$("#moduleName").comboTree("initValue", moduleId);
//				$('#moduleName').val(data.tableInfo.moduleId);
//				$('#dataShow').val(data.tableInfo.dataShow);
				$('#version').val(data.tableInfo.version);
				$('#htmlPath').val(data.tableInfo.htmlPath);
				$("#applyTo2").val(data.tableInfo.applyTo);
//				var applyTo2 = $("#applyTo2").val();
//				$("#applyToName2").comboTree("initValue", applyTo2);
				$("#getPrintTemplateId").val(data.tableInfo.printTemplate);
				var printTemplate = $("#getPrintTemplateId").val();
				$("#getPrintTemplateName").val(data.tableInfo.printTemplateName);
				$("#showTableModelId").val(data.tableInfo.showTableModelId);
				$("#showTableModel").val(data.tableInfo.showTableModel);
				columns = data.tableInfo.fields;
				subTables = data.subTables;
				$('#moduleDiv').html(data.tableInfo.htmlBodyContent);
				$('#moduleText').text(data.tableInfo.htmlBodyContent);
				setTimeout(function(){CKEDITOR.instances.moduleText.setData(data.tableInfo.htmlBodyContent);},500); 
				if(flag == "1") {
					setEventAndInitFormData();
				}
			}
		});
		$('#fs2').css('display','');
	}else {
		$('#title').text("新增表单");
		$('#title_h2').text("新增表单");
	}
	//初始化开始结束
	
	//清除编辑器
	var instance = CKEDITOR.instances['moduleText'];
	if (instance) { 
		CKEDITOR.remove(instance);
	}
	 //初始化编辑器
	var editor = CKEDITOR.replace( 'moduleText', {  
			allowedContent:true,
			enterMode: CKEDITOR.ENTER_P,
			toolbarStartupExpanded:true,
			//工具栏
			toolbar: [ 
			          ['Bold','Italic','Underline'], ['Cut','Copy','Paste'], 
			          ['NumberedList','BulletedList','-'], 
			          ['JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock'],
			          ['Link','Unlink'],['Format','Font','FontSize'],['TextColor','BGColor'],['Image','Table','Maximize'],
			          ['Form'],['TextField','Textarea','Radio','Checkbox','Button','Select','fileupload'],['formfile'],['formpreview'],['Source'],
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
	
	//select,radio,checkbox，备选项来源
	$('input[name=optionDataSource]').click(function (){
		createOptionElement($(this).val(),$("#formEleType").val(),$("#colEnName").val(),getOptons());
	});
	$('input[name=optionDataSource]').next().click(function (){
		createOptionElement($(this).prev().val(),$("#formEleType").val(),$("#colEnName").val(),getOptons());
	});
	
	var setting2 = {
			async: {
				enable: true,
				url:contextPath + "/dytable/get_full_tree.action",
				autoParam:["id"]
			},
			callback: {
				onClick: onClick2,
				onAsyncSuccess:onAsyncSuccess2
			}
	};
		
	function onAsyncSuccess2(){
		var nodes = $.fn.zTree.getZTreeObj("tableTree2").getNodesByParam('id',$(currTableObj).attr('subformApplyTableId'),null);
		if(nodes && nodes.length > 0){
			$('#subformApplyTableIdText').val(nodes[0].name);
		}else{
			$('#subformApplyTableIdText').val();
		}
	}

	function onClick2(e, treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("tableTree2"),
		nodes = zTree.getSelectedNodes();
		nodes.sort(function compare(a,b){return a.id-b.id;});
		for (var i=0, l=nodes.length; i<l; i++) {
			$('#subformApplyTableId').val(nodes[i].id);
			$("#subformApplyTableIdText").val(nodes[i].name);
			changeTableSelect2(nodes[i].id);
		}
	}
	
	
	
	var setting = {
			async: {
				enable: true,
				url:contextPath + "/dytable/get_full_tree.action",
				autoParam:["id"]
			},
			callback: {
				onClick: onClick,
				onAsyncSuccess:onAsyncSuccess
			}
	};
		
	function onAsyncSuccess(){
		var nodes = $.fn.zTree.getZTreeObj("tableTree").getNodesByParam('id',$(currTableObj).attr('tableId'),null);
		if(nodes && nodes.length > 0){
			$('#tableDefinitionText').val(nodes[0].name);
		}else{
			$('#tableDefinitionText').val();
		}
	}

	function onClick(e, treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("tableTree"),
		nodes = zTree.getSelectedNodes();
		nodes.sort(function compare(a,b){return a.id-b.id;});
		for (var i=0, l=nodes.length; i<l; i++) {
			$('#tableDefinitionId').val(nodes[i].id);
			$("#tableDefinitionText").val(nodes[i].name);
			changeTableSelect(nodes[i].id);
			changeTableSelect3(nodes[i].id);
		}
	}

	function showMenu() {
		var cityObj = $("#tableDefinitionText");
		var cityOffset = $("#tableDefinitionText").offset();
		$("#menuContent").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");

		$("body").bind("mousedown", onBodyDown);
	}
	
	function showMenu2() {
		var cityObj = $("#subformApplyTableIdText");
		var cityOffset = $("#subformApplyTableIdText").offset();
		$("#menuContent2").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");

		$("body").bind("mousedown", onBodyDown2);
	}
	
	function hideMenu2() {
		$("#menuContent2").fadeOut("fast");
		$("body").unbind("mousedown", onBodyDown2);
	}
	function onBodyDown2(event) {
		if (!(event.target.id == "menuContent2" || (event.target.id && event.target.id.indexOf('tableTree2') >= 0))) {
			hideMenu2();
		}
	}
	
	function hideMenu() {
		$("#menuContent").fadeOut("fast");
		$("body").unbind("mousedown", onBodyDown);
	}
	function onBodyDown(event) {
		if (!(event.target.id == "menuContent" || (event.target.id && event.target.id.indexOf('tableTree') >= 0))) {
			hideMenu();
		}
	}

	function getOptons(){
		var options = new Array();
		if($('#dicCode').val() != undefined){
			var o = {"value":$('#dicCode').val()};
			options.push(o);
		}
		return options;
	}

	function isUniqueCheck(){
		if($('#checkRule_5').attr('checked')){
			$('#uniqueCheckRuleTr').show();
		}else{
			$('#uniqueCheckRuleTr').hide();
			$('#uniqueCheckRule').val('');
		}
	}

	function setFieldAttribute(){
		$('#mainTableEnName').attr('disabled',true);
	}

	/**************************/
	//获取主表字段配置信息对象
	function getColInfoObj(colEnName){
		if(columns != undefined){
			for(var i=0;i<columns.length;i++){
				if(columns[i].colEnName == colEnName){
					return columns[i];
				}
			}
			return undefined;
		}
		return undefined;
	}
	//获取从表配置信息(从表uuid要写到html模板中对应的table元素属性为tableId)
	function getSubTableObj(subTableId){
		if(subTables != undefined){
			for(var i=0;i<subTables.length;i++){
				if(subTableId == subTables[i].id){
					return subTables[i];
				}
			}
		}
		return undefined;
	}

	//用户自定义模板导入后,元素增加双击事件
	function setEventAndInitFormData(){
		var uuid = $("#formUuid").val();
		JDS.call({
			service:"formDefinitionService.getFieldByForm",
			data:[uuid],
			success:function(result) {
				data = result.data;
				var optionStr = "";
				for(var i=0;i<data.length;i++) {
					optionStr += "<option value='"+data[i].fieldName+"'>"+data[i].descname+"</option>";
				}
				$("#formField").html(optionStr);
			}
		});
		if($("#body_col").attr("id") == undefined) {
			$('#moduleDiv').find("table").append("<tr style='display:none;'><td><input type='hidden' id='body_col' name='body_col'></td><tr>");
		}
		if($("#signature_").attr("id") == undefined) {
			$('#moduleDiv').find("table").append("<tr style='display:none;'><td><input type='hidden' id='signature_' name='signature_'></td><tr>");
		}
		if($("#expand_field").attr("id") == undefined) {
			$('#moduleDiv').find("table").append("<tr style='display:none;'><td><input type='hidden' id='expand_field' name='expand_field'></td><tr>");
		}
		var iframeDocObj = $('#moduleDiv');
		//对input元素增加双击事件
		var inputArr = $(iframeDocObj).find("input");
		
		for(var i=0;i<inputArr.length;i++){
			$(inputArr[i]).dblclick(function (){inputDBClick(this);});
			
			//构造初使化数据
			var obj = new Object();
			//默认为"input"
			obj.formEleType = "text";
			if("expand_field" == $(inputArr[i]).attr("id")) {
				obj.formEleType = "text";
				if($(inputArr[i]).attr("id") == undefined) {
					$(inputArr[i]).attr("id","expand_field");
					obj.formEleId = $(inputArr[i]).attr("id");
				}else {
					obj.formEleId = $(inputArr[i]).attr("id");
				}
			}
			
			if("body_col" == $(inputArr[i]).attr("id")) {
				obj.formEleType = "button";
				if($(inputArr[i]).attr("id") == undefined) {
					$(inputArr[i]).attr("id","body_col");
					obj.formEleId = $(inputArr[i]).attr("id");
				}else {
					obj.formEleId = $(inputArr[i]).attr("id");
				}
				
			}
			if("signature_" == $(inputArr[i]).attr("id")) {
				obj.formEleType = "text";
				if($(inputArr[i]).attr("id") == undefined) {
					$(inputArr[i]).attr("id","signature_");
					obj.formEleId = $(inputArr[i]).attr("id");
				}else {
					obj.formEleId = $(inputArr[i]).attr("id");
				}
				
			}
			if("file" == $(inputArr[i]).attr("type")) {
				obj.formEleType = "file";
				if($(inputArr[i]).attr("id") == undefined) {
					$(inputArr[i]).attr("id","fileupload");
					obj.formEleId = $(inputArr[i]).attr("id");
				}else {
					obj.formEleId = $(inputArr[i]).attr("id");
				}
			}
			if("radio" == $(inputArr[i]).attr("type")){
				obj.formEleType = "radio";
				if($(inputArr[i]).attr("id") == undefined) {
					$(inputArr[i]).attr("id",$(inputArr[i]).attr("name")+(i+parseInt(1)));
					obj.formEleId = $(inputArr[i]).attr("id");
				}else {
					obj.formEleId = $(inputArr[i]).attr("id");
				}
			}
			if("checkbox" == $(inputArr[i]).attr("type")){
					obj.formEleType = "checkbox";
					if($(inputArr[i]).attr("id") == undefined) {
						$(inputArr[i]).attr("id",$(inputArr[i]).attr("name")+(i+parseInt(1)));
						obj.formEleId = $(inputArr[i]).attr("id");
					}else {
						obj.formEleId = $(inputArr[i]).attr("id");
					}
			}
			if($(inputArr[i]).attr("id") == undefined) {
				$(inputArr[i]).attr("id",$(inputArr[i]).attr("name"));
				obj.formEleId = $(inputArr[i]).attr("id");
			}else {
				obj.formEleId = $(inputArr[i]).attr("id");
			}
			obj.colEnName = $(inputArr[i]).attr("name");
			var colObj = getColInfoObj($(inputArr[i]).attr("name"));
			if(undefined == colObj){//新建
				if("body_col" == $(inputArr[i]).attr("id")) {
					obj.uuid = '';
					obj.colCnName =  "正文";
					obj.defaultValue =  '';
					obj.fontSize  =  '';
					obj.fontColor =  '';
					obj.jsFunction =  '';
					obj.dataShow = dyFormDataShow.directShow; //初始化为直接展示
					obj.dataLength = '';//初使化为空
					obj.floatSet = '';//初使化为空
					obj.ctrlField = '';//初使化为空
					obj.serviceName = '';//初使化为空
					obj.methodName = '';//初使化为空
					obj.data = '';//初使化为空
					obj.unEditDesignatedId = '';
					obj.unEditIsSaveDb = '';
					obj.designatedId = '';//初使化为空
					obj.designatedType = '';//初使化为空
					obj.isOverride = '';//初使化为空
					obj.isSaveDb = '';
					obj.uploadFileType = '';//初始化为空
					obj.isGetZhengWen = '';//初始化为空
					obj.applyTo = '';
					obj.inputMode = dyFormInputMode.textBody;//正文
					obj.optionDataSource = dyDataSourceType.dataConstant;//1.常量 2.取字典 ''.隐藏这个选项
					/********ruan start***********/
					obj.fontWidth = '';
					obj.setUrlOnlyRead = '';
					obj.fontHight = '';
					obj.textAlign = '';
					obj.isHide = elementShow.show;
					obj.defaultValueWay = '';
					obj.inputDataType = '';
					obj.relationDataTwoSql = '';
					obj.relationDataText = '';
					obj.relationDataValue = '';
					obj.relationDataSql = '';
					obj.relationDataShowType = '';
					obj.relationDataShowMethod = '';
//					obj.relationDataTypeTwo = '';
					obj.relationDataTextTwo = '';
					obj.relationDataValueTwo = '';
//					obj.relationDataUuid = '';
					obj.relationDataDefiantion = '';
					/********ruan end***********/
					
					var options = new Array();//radio,checkbox,select
					obj.options = options;
					var chkArr = new Array();//校验规则[value,text,rule]
					obj.checkRules = chkArr;
				}else if("signature_" == $(inputArr[i]).attr("id")) {
					obj.uuid = '';
					obj.colCnName =  $(inputArr[i]).parent().prev().html();
					obj.defaultValue =  '';
					obj.fontSize  =  '';
					obj.fontColor =  '';
					obj.jsFunction =  '';
					obj.dataShow = dyFormDataShow.directShow; //初始化为直接展示
					obj.dataLength = '';//初使化为空
					obj.floatSet = '';//初使化为空
					obj.ctrlField = '';//初使化为空
					obj.serviceName = '';//初使化为空
					obj.methodName = '';//初使化为空
					obj.data = '';//初使化为空
					obj.unEditDesignatedId = '';
					obj.unEditIsSaveDb = '';
					obj.designatedId = '';//初使化为空
					obj.designatedType = '';//初使化为空
					obj.isOverride = '';//初使化为空
					obj.isSaveDb = '';
					obj.uploadFileType = '';//初始化为空
					obj.isGetZhengWen = '';//初始化为空
					obj.applyTo = '';
					obj.inputMode = '';//正文
					obj.optionDataSource = dyDataSourceType.dataConstant;//1.常量 2.取字典 ''.隐藏这个选项
					/********ruan start***********/
					obj.fontWidth = '';
					obj.setUrlOnlyRead = '';
					obj.fontHight = '';
					obj.textAlign = '';
					obj.isHide = elementShow.show;
					obj.defaultValueWay = '';
					obj.inputDataType = '';
					obj.relationDataTwoSql = '';
					obj.relationDataText = '';
					obj.relationDataValue = '';
					obj.relationDataSql = '';
					obj.relationDataShowType = '';
					obj.relationDataShowMethod = '';
//					obj.relationDataTypeTwo = '';
					obj.relationDataTextTwo = '';
					obj.relationDataValueTwo = '';
//					obj.relationDataUuid = '';
					obj.relationDataDefiantion = '';
					/********ruan end***********/
					
					var options = new Array();//radio,checkbox,select
					obj.options = options;
					var chkArr = new Array();//校验规则[value,text,rule]
					obj.checkRules = chkArr;
				}
				else if("expand_field" == $(inputArr[i]).attr("id")) {
					obj.uuid = '';
					obj.colCnName = "拓展字段";
					obj.defaultValue =  '';
					obj.fontSize  =  '';
					obj.fontColor =  '';
					obj.jsFunction =  '';
					obj.dataShow = dyFormDataShow.directShow; //初始化为直接展示
					obj.dataLength = '';//初使化为空
					obj.floatSet = '';//初使化为空
					obj.ctrlField = '';//初使化为空
					obj.serviceName = '';//初使化为空
					obj.methodName = '';//初使化为空
					obj.data = '';//初使化为空
					obj.unEditDesignatedId = '';
					obj.unEditIsSaveDb = '';
					obj.designatedId = '';//初使化为空
					obj.designatedType = '';//初使化为空
					obj.isOverride = '';//初使化为空
					obj.isSaveDb = '';
					obj.uploadFileType = '';//初始化为空
					obj.isGetZhengWen = '';//初始化为空
					obj.applyTo = '';
					obj.inputMode = '';
					obj.optionDataSource = dyDataSourceType.dataConstant;//1.常量 2.取字典 ''.隐藏这个选项
					/********ruan start***********/
					obj.fontWidth = '';
					obj.setUrlOnlyRead = '';
					obj.fontHight = '';
					obj.textAlign = '';
					obj.isHide = elementShow.show;
					obj.defaultValueWay = '';
					obj.inputDataType = '';
					obj.relationDataTwoSql = '';
					obj.relationDataText = '';
					obj.relationDataValue = '';
					obj.relationDataSql = '';
					obj.relationDataShowType = '';
					obj.relationDataShowMethod = '';
//					obj.relationDataTypeTwo = '';
					obj.relationDataTextTwo = '';
					obj.relationDataValueTwo = '';
//					obj.relationDataUuid = '';
					obj.relationDataDefiantion = '';
					/********ruan end***********/
					
					var options = new Array();//radio,checkbox,select
					obj.options = options;
					var chkArr = new Array();//校验规则[value,text,rule]
					obj.checkRules = chkArr;
				}
				else if("file" == $(inputArr[i]).attr("type")) {
					obj.uuid = '';
					obj.colCnName =  $(inputArr[i]).parent().prev().html();
					obj.defaultValue =  '';
					obj.fontSize  =  '';
					obj.fontColor =  '';
					obj.jsFunction =  '';
					obj.dataShow = dyFormDataShow.directShow; //初始化为直接展示
					obj.dataType = dyFormInputType.text;//初使化为字符串类型
					obj.dataLength = '';//初使化为空
					obj.floatSet = '';//初使化为空
					obj.ctrlField = '';//初使化为空
					obj.serviceName = '';//初使化为空
					obj.methodName = '';//初使化为空
					obj.data = '';//初使化为空
					obj.unEditDesignatedId = '';
					obj.unEditIsSaveDb = '';
					obj.designatedId = '';//初使化为空
					obj.designatedType = '';//初使化为空
					obj.isOverride = '';//初使化为空
					obj.isSaveDb = '';
					obj.uploadFileType = '';//初始化为空
					obj.isGetZhengWen = '';//初始化为空
					obj.applyTo = '';
					obj.inputMode = dyFormInputMode.fileUpload;//附件
					obj.optionDataSource = dyDataSourceType.dataConstant;//1.常量 2.取字典 ''.隐藏这个选项
					var options = new Array();//radio,checkbox,select
					obj.options = options;
					var chkArr = new Array();//校验规则[value,text,rule]
					obj.checkRules = chkArr;
					/********ruan start***********/
					obj.fontWidth = '';
					obj.setUrlOnlyRead = '';
					obj.fontHight = '';
					obj.textAlign = '';
					obj.isHide = elementShow.show;
					obj.defaultValueWay = '';
					obj.inputDataType = '';
					obj.relationDataTwoSql = '';
					obj.relationDataText = '';
					obj.relationDataValue = '';
					obj.relationDataSql = '';
					obj.relationDataShowType = '';
					obj.relationDataShowMethod = '';
//					obj.relationDataTypeTwo = '';
					obj.relationDataTextTwo = '';
					obj.relationDataValueTwo = '';
//					obj.relationDataUuid = '';
					obj.relationDataDefiantion = '';
					/********ruan end***********/
				}
				else {
					obj.uuid = '';
					obj.colCnName =  $(inputArr[i]).parent().prev().html();
					obj.defaultValue = '';
					obj.fontSize  =  '';
					obj.fontColor =  '';
					obj.jsFunction =  '';
					obj.dataShow = dyFormDataShow.directShow; //初始化为直接展示
					obj.dataType = dyFormInputType.text;//初使化为字符串类型
					obj.dataLength = '';//初使化为空
					obj.floatSet = '';//初使化为空
					obj.ctrlField = '';//初使化为空
					obj.serviceName = '';//初使化为空
					obj.methodName = '';//初使化为空
					obj.data = '';//初使化为空
					obj.unEditDesignatedId = '';
					obj.unEditIsSaveDb = '';
					obj.designatedId = '';//初使化为空
					obj.designatedType = '';//初使化为空
					obj.isOverride = '';//初使化为空
					obj.isSaveDb = '';
					obj.uploadFileType = '';//初始化为空
					obj.isGetZhengWen = '';//初始化为空
					obj.applyTo = '';
					obj.inputMode = dyFormInputMode.text;//文本输入框
					obj.optionDataSource = dyDataSourceType.dataConstant;//1.常量 2.取字典 ''.隐藏这个选项
					var options = new Array();//radio,checkbox,select
					obj.options = options;
					var chkArr = new Array();//校验规则[value,text,rule]
					obj.checkRules = chkArr;
					/********ruan start***********/
					obj.fontWidth = '';
					obj.setUrlOnlyRead = '';
					obj.fontHight = '';
					obj.textAlign = '';
					obj.isHide = elementShow.show;
					obj.defaultValueWay = '';
					obj.inputDataType = '';
					obj.relationDataTwoSql = '';
					obj.relationDataText = '';
					obj.relationDataValue = '';
					obj.relationDataSql = '';
					obj.relationDataShowType = '';
					obj.relationDataShowMethod = '';
//					obj.relationDataTypeTwo = '';
					obj.relationDataTextTwo = '';
					obj.relationDataValueTwo = '';
//					obj.relationDataUuid = '';
					obj.relationDataDefiantion = '';
					/********ruan end***********/
				}
			}
			else{//编辑
				if("body_col" == $(inputArr[i]).attr("id")) {
					obj.uuid = colObj.uuid;
					obj.colCnName =  "正文";
					obj.defaultValue =  '';
					obj.fontSize  =  '';
					obj.fontColor =  '';
					obj.jsFunction =  '';
					obj.dataShow = dyFormDataShow.directShow; //初始化为直接展示
					obj.dataLength = '';//初使化为空
					obj.floatSet = '';//初使化为空
					obj.ctrlField = '';//初使化为空
					obj.serviceName = '';//初使化为空
					obj.methodName = '';//初使化为空
					obj.data = '';//初使化为空
					obj.unEditDesignatedId = '';
					obj.unEditIsSaveDb = '';
					obj.designatedId = '';//初使化为空
					obj.designatedType = '';//初使化为空
					obj.isOverride = '';//初使化为空
					obj.isSaveDb = '';
					obj.uploadFileType = '';//初始化为空
					obj.isGetZhengWen = '';//初始化为空
					obj.applyTo = '';
					obj.inputMode = dyFormInputMode.textBody;//正文
					obj.optionDataSource = dyDataSourceType.dataConstant;//1.常量 2.取字典 ''.隐藏这个选项
					var options = new Array();//radio,checkbox,select
					obj.options = options;
					var chkArr = new Array();//校验规则[value,text,rule]
					obj.checkRules = chkArr;
					/********ruan start***********/
					obj.fontWidth = '';
					obj.setUrlOnlyRead = '';
					obj.fontHight = '';
					obj.textAlign = '';
					obj.isHide = elementShow.show;
					obj.defaultValueWay = '';
					obj.inputDataType = '';
					obj.relationDataTwoSql = '';
					obj.relationDataText = '';
					obj.relationDataValue = '';
					obj.relationDataSql = '';
					obj.relationDataShowType = '';
					obj.relationDataShowMethod = '';
//					obj.relationDataTypeTwo = '';
					obj.relationDataTextTwo = '';
					obj.relationDataValueTwo = '';
//					obj.relationDataUuid = '';
					obj.relationDataDefiantion = '';
					/********ruan end***********/
				}else if("signature_" == $(inputArr[i]).attr("id")) {
					obj.uuid = colObj.uuid;
					obj.colCnName =  $(inputArr[i]).parent().prev().html();
					obj.defaultValue =  '';
					obj.fontSize  =  '';
					obj.fontColor =  '';
					obj.jsFunction =  '';
					obj.dataShow = dyFormDataShow.directShow; //初始化为直接展示
					obj.dataLength = '';//初使化为空
					obj.floatSet = '';//初使化为空
					obj.ctrlField = '';//初使化为空
					obj.serviceName = '';//初使化为空
					obj.methodName = '';//初使化为空
					obj.data = '';//初使化为空
					obj.unEditDesignatedId = '';
					obj.unEditIsSaveDb = '';
					obj.designatedId = '';//初使化为空
					obj.designatedType = '';//初使化为空
					obj.isOverride = '';//初使化为空
					obj.isSaveDb = '';
					obj.uploadFileType = '';//初始化为空
					obj.isGetZhengWen = '';//初始化为空
					obj.applyTo = '';
					obj.inputMode = '';
					obj.optionDataSource = dyDataSourceType.dataConstant;//1.常量 2.取字典 ''.隐藏这个选项
					/********ruan start***********/
					obj.fontWidth = '';
					obj.setUrlOnlyRead = '';
					obj.fontHight = '';
					obj.textAlign = '';
					obj.isHide = elementShow.show;
					obj.defaultValueWay = '';
					obj.inputDataType = '';
					obj.relationDataTwoSql = '';
					obj.relationDataText = '';
					obj.relationDataValue = '';
					obj.relationDataSql = '';
					obj.relationDataShowType = '';
					obj.relationDataShowMethod = '';
//					obj.relationDataTypeTwo = '';
					obj.relationDataTextTwo = '';
					obj.relationDataValueTwo = '';
//					obj.relationDataUuid = '';
					obj.relationDataDefiantion = '';
					/********ruan end***********/
				}
				else if("expand_field" == $(inputArr[i]).attr("id")) {
					obj.uuid = colObj.uuid;
					obj.colCnName =  "拓展字段";
					obj.defaultValue =  '';
					obj.fontSize  =  '';
					obj.fontColor =  '';
					obj.jsFunction =  '';
					obj.dataShow = dyFormDataShow.directShow; //初始化为直接展示
					obj.dataLength = '';//初使化为空
					obj.floatSet = '';//初使化为空
					obj.ctrlField = '';//初使化为空
					obj.serviceName = '';//初使化为空
					obj.methodName = '';//初使化为空
					obj.data = '';//初使化为空
					obj.unEditDesignatedId = '';
					obj.unEditIsSaveDb = '';
					obj.designatedId = '';//初使化为空
					obj.designatedType = '';//初使化为空
					obj.isOverride = '';//初使化为空
					obj.isSaveDb = '';
					obj.uploadFileType = '';//初始化为空
					obj.isGetZhengWen = '';//初始化为空
					obj.applyTo = '';
					obj.inputMode = '';
					obj.optionDataSource = dyDataSourceType.dataConstant;//1.常量 2.取字典 ''.隐藏这个选项
					/********ruan start***********/
					obj.fontWidth = '';
					obj.setUrlOnlyRead = '';
					obj.fontHight = '';
					obj.textAlign = '';
					obj.isHide = elementShow.show;
					obj.defaultValueWay = '';
					obj.inputDataType = '';
					obj.relationDataTwoSql = '';
					obj.relationDataText = '';
					obj.relationDataValue = '';
					obj.relationDataSql = '';
					obj.relationDataShowType = '';
					obj.relationDataShowMethod = '';
//					obj.relationDataTypeTwo = '';
					obj.relationDataTextTwo = '';
					obj.relationDataValueTwo = '';
//					obj.relationDataUuid = '';
					obj.relationDataDefiantion = '';
					/********ruan end***********/
				}
				else if("file" == $(inputArr[i]).attr("type")) {
					obj.uuid = colObj.uuid;
					obj.colCnName =  $(inputArr[i]).parent().prev().html();
					obj.defaultValue =  '';
					obj.fontSize  =  '';
					obj.fontColor =  '';
					obj.jsFunction =  '';
					obj.dataShow = dyFormDataShow.directShow; //初始化为直接展示
					obj.dataType = dyFormInputType.text;//初使化为字符串类型
					obj.dataLength = '';//初使化为空
					obj.floatSet = '';//初使化为空
					obj.ctrlField = '';//初使化为空
					obj.serviceName = '';//初使化为空
					obj.methodName = '';//初使化为空
					obj.data = '';//初使化为空
					obj.unEditDesignatedId = '';
					obj.unEditIsSaveDb = '';
					obj.designatedId = '';//初使化为空
					obj.designatedType = '';//初使化为空
					obj.isOverride = '';//初使化为空
					obj.isSaveDb = '';
					obj.uploadFileType = '';//初始化为空
					obj.isGetZhengWen = '';//初始化为空
					obj.applyTo = '';
					obj.inputMode = dyFormInputMode.fileUpload;//附件
					obj.optionDataSource = dyDataSourceType.dataConstant;//1.常量 2.取字典 ''.隐藏这个选项
					var options = new Array();//radio,checkbox,select
					obj.options = options;
					var chkArr = new Array();//校验规则[value,text,rule]
					obj.checkRules = chkArr;
					/********ruan start***********/
					obj.fontWidth = '';
					obj.setUrlOnlyRead = '';
					obj.fontHight = '';
					obj.textAlign = '';
					obj.isHide = elementShow.show;
					obj.defaultValueWay = '';
					obj.inputDataType = '';
					obj.relationDataTwoSql = '';
					obj.relationDataText = '';
					obj.relationDataValue = '';
					obj.relationDataSql = '';
					obj.relationDataShowType = '';
					obj.relationDataShowMethod = '';
//					obj.relationDataTypeTwo = '';
					obj.relationDataTextTwo = '';
					obj.relationDataValueTwo = '';
//					obj.relationDataUuid = '';
					obj.relationDataDefiantion = '';
					/********ruan end***********/
				}
				else {
					obj.uuid = colObj.uuid;
					$(inputArr[i]).parent().prev().html(colObj.colCnName);
					obj.colCnName =  $(inputArr[i]).parent().prev().html();
					obj.defaultValue = colObj.defaultValue; //初始化为直接展示
					obj.fontSize  =  colObj.fontSize;
					obj.fontColor =  colObj.fontColor;
					obj.jsFunction  =  colObj.jsFunction;
					obj.dataShow = colObj.dataShow; //初始化为直接展示
					if($(inputArr[i]).parent("table").attr("tableType") == "2") {
						obj.dataType = "*";
					}else {
						obj.dataType = colObj.dataType;//初使化为字符串类型
					}
					obj.dataLength = (colObj.dataLength == undefined ? "" : colObj.dataLength);//初使化为空
					obj.floatSet = (colObj.floatSet == undefined ? "" : colObj.floatSet);//初使化为空
					obj.ctrlField = (colObj.ctrlField == undefined ? "" : colObj.ctrlField);//初使化为空
					obj.serviceName = colObj.serviceName;
					obj.methodName = colObj.methodName;
					obj.data = colObj.data;
					obj.unEditDesignatedId = colObj.unEditDesignatedId;
					obj.unEditIsSaveDb = colObj.unEditIsSaveDb;
					obj.designatedId = colObj.designatedId;
					obj.designatedType = colObj.designatedType;
					obj.isOverride = colObj.isOverride;
					obj.isSaveDb = colObj.isSaveDb;
					obj.uploadFileType = colObj.uploadFileType;
					obj.isGetZhengWen = colObj.isGetZhengWen;
					obj.applyTo = (colObj.applyTo == undefined ? "" : colObj.applyTo);//初使化为空
					obj.inputMode = colObj.inputMode;//文本
					obj.optionDataSource = colObj.optionDataSource;//1.常量 2.取字典 ''.隐藏这个选项
					obj.options = colObj.options;
					obj.checkRules = colObj.checkRules;
					/********ruan start***********/
					obj.fontWidth = colObj.fontWidth;
					obj.setUrlOnlyRead = colObj.setUrlOnlyRead;
					obj.fontHight = colObj.fontHight;
					obj.textAlign = colObj.textAlign;
					obj.isHide = colObj.isHide;
					obj.defaultValueWay = colObj.defaultValueWay;
					obj.inputDataType = colObj.inputDataType;
					obj.relationDataTwoSql = colObj.relationDataTwoSql;
					obj.relationDataText = colObj.relationDataText;
					obj.relationDataValue = colObj.relationDataValue;
					obj.relationDataSql = colObj.relationDataSql;
					obj.relationDataShowType = colObj.relationDataShowType;
					obj.relationDataShowMethod = colObj.relationDataShowMethod;
//					obj.relationDataTypeTwo = colObj.relationDataTypeTwo;
					obj.relationDataTextTwo = colObj.relationDataTextTwo;
					obj.relationDataValueTwo = colObj.relationDataValueTwo;
//					obj.relationDataUuid = colObj.relationDataUuid;
					obj.relationDataDefiantion = colObj.relationDataDefiantion;
					/********ruan end***********/
				}
			}
			setFormData(inputArr[i],obj);
		}
		
		//对textarea增加事件
		var textareaArr = $(iframeDocObj).find("textarea");
		for(var i=0;i<textareaArr.length;i++){
			$(textareaArr[i]).dblclick(function (){inputDBClick(this);});
			//构造初使化数据
			var obj = new Object();
			//默认为"input"
			obj.formEleType = "textarea";
			if($(textareaArr[i]).attr("id") == undefined) {
				$(textareaArr[i]).attr("id",$(textareaArr[i]).attr("name"));
				obj.formEleId = $(textareaArr[i]).attr("id");
			}else {
				obj.formEleId = $(textareaArr[i]).attr("id");
			}
			obj.colEnName = $(textareaArr[i]).attr("name");
			var colObj = getColInfoObj($(textareaArr[i]).attr("name"));
			if(undefined == colObj){//新建
				obj.uuid = '';
				obj.colCnName =  $(textareaArr[i]).parent().prev().html();
				obj.defaultValue = '';
				obj.fontSize  =  '';
				obj.fontColor =  '';
				obj.jsFunction =  '';
				obj.dataShow = dyFormDataShow.directShow; //初始化为直接展示
				obj.dataType = "";//初使化为普通类型
				obj.dataLength = "";//初使化为空
				obj.floatSet = "";//初使化为空
				obj.ctrlField = "";//初使化为空
				obj.serviceName = '';//初使化为空
				obj.methodName = '';//初使化为空
				obj.data = '';//初使化为空
				obj.unEditDesignatedId = '';
				obj.unEditIsSaveDb = '';
				obj.designatedId = '';//初使化为空
				obj.designatedType = '';//初使化为空
				obj.isOverride = '';//初使化为空
				obj.isSaveDb = '';
				obj.uploadFileType = '';//初始化为空
				obj.isGetZhengWen = '';//初始化为空
				obj.applyTo = '';
				obj.inputMode = dyFormInputMode.textArea;//手动文本域输入
				obj.optionDataSource = '';//1.常量 2.取字典 ''.隐藏这个选项
				var options = new Array();//radio,checkbox,select
				obj.options = options;
				var chkArr = new Array();//校验规则[value,text,rule]
				obj.checkRules = chkArr;
				/********ruan start***********/
				obj.fontWidth = '';
				obj.setUrlOnlyRead = '';
				obj.fontHight = '';
				obj.textAlign = '';
				obj.isHide = elementShow.show;
				obj.defaultValueWay = '';
				obj.inputDataType = '';
				obj.relationDataTwoSql = '';
				obj.relationDataText = '';
				obj.relationDataValue = '';
				obj.relationDataSql = '';
				obj.relationDataShowType = '';
				obj.relationDataShowMethod = '';
//				obj.relationDataTypeTwo = '';
				obj.relationDataTextTwo = '';
				obj.relationDataValueTwo = '';
//				obj.relationDataUuid = '';
				obj.relationDataDefiantion = '';
				/********ruan end***********/
			}else{//编辑
				obj.uuid = colObj.uuid;
				$(colObj[i]).parent().prev().html(colObj.colCnName);
				obj.colCnName =  $(textareaArr[i]).parent().prev().html();
				obj.defaultValue =  colObj.defaultValue;
				obj.fontSize  =  colObj.fontSize;
				obj.fontColor =  colObj.fontColor;
				obj.jsFunction  =  colObj.jsFunction;
				obj.dataShow = colObj.dataShow; //初始化为直接展示
				obj.dataType = colObj.dataType;//初使化为普通类型
				obj.dataLength = (colObj.dataLength == undefined ? "" : colObj.dataLength);//初使化为空
				obj.floatSet = (colObj.floatSet == undefined ? "" : colObj.floatSet);//初使化为空
				obj.ctrlField = (colObj.ctrlField == undefined ? "" : colObj.ctrlField);//初使化为空
				obj.serviceName = colObj.serviceName;
				obj.methodName = colObj.methodName;
				obj.data = colObj.data;
				obj.unEditDesignatedId = colObj.unEditDesignatedId;
				obj.unEditIsSaveDb = colObj.unEditIsSaveDb;
				obj.designatedId = colObj.designatedId;
				obj.designatedType = colObj.designatedType;
				obj.isOverride = colObj.isOverride;
				obj.isSaveDb = colObj.isSaveDb;
				obj.uploadFileType = colObj.uploadFileType;
				obj.isGetZhengWen = colObj.isGetZhengWen;
				obj.applyTo = colObj.applyTo;
				obj.inputMode = colObj.inputMode;//手动输入
				obj.optionDataSource = colObj.optionDataSource;//1.常量 2.取字典 ''.隐藏这个选项
				obj.options = colObj.options;
				obj.checkRules = colObj.checkRules;
				/********ruan start***********/
				obj.fontWidth = colObj.fontWidth;
				obj.setUrlOnlyRead = colObj.setUrlOnlyRead;
				obj.fontHight = colObj.fontHight;
				obj.textAlign = colObj.textAlign;
				obj.isHide = elementShow.show;
				obj.defaultValueWay = colObj.defaultValueWay;
				obj.inputDataType = colObj.inputDataType;
				obj.relationDataTwoSql = colObj.relationDataTwoSql;
				obj.relationDataText = colObj.relationDataText;
				obj.relationDataValue = colObj.relationDataValue;
				obj.relationDataSql = colObj.relationDataSql;
				obj.relationDataShowType = colObj.relationDataShowType;
				obj.relationDataShowMethod = colObj.relationDataShowMethod;
				
//				obj.relationDataTypeTwo = '';
				obj.relationDataTextTwo = colObj.relationDataTextTwo;
				obj.relationDataValueTwo = colObj.relationDataValueTwo;
//				obj.relationDataUuid = '';
				obj.relationDataDefiantion = colObj.relationDataDefiantion;
				/********ruan end***********/
			}
			setFormData(textareaArr[i],obj);
		}
		//对Button增加事件
		var buttonArr = $(iframeDocObj).find("button");
		for(var i=0;i<buttonArr.length;i++){
			$(buttonArr[i]).dblclick(function (){inputDBClick(this);});
			//构造初使化数据
			var obj = new Object();
			//默认为"input"
			obj.formEleType = "button";
			if($(buttonArr[i]).attr("id") == undefined) {
				$(buttonArr[i]).attr("id",$(buttonArr[i]).attr("name"));
				obj.formEleId = $(buttonArr[i]).attr("id");
			}else {
				obj.formEleId = $(buttonArr[i]).attr("id");
			}
			obj.colEnName = $(buttonArr[i]).attr("name");
			var colObj = getColInfoObj($(buttonArr[i]).attr("name"));
			if(undefined == colObj){//新建
				obj.uuid = '';
				obj.colCnName =  $(buttonArr[i]).parent().prev().html();
				obj.defaultValue = '';
				obj.fontSize  =  '';
				obj.fontColor =  '';
				obj.jsFunction =  '';
				obj.dataShow = dyFormDataShow.directShow; //初始化为直接展示
				obj.dataType = dyFormInputType.text;//初使化为普通类型
				obj.dataLength = "";//初使化为空
				obj.floatSet = "";//初使化为空
				obj.ctrlField = "";//初使化为空
				obj.serviceName = '';//初使化为空
				obj.methodName = '';//初使化为空
				obj.data = '';//初使化为空
				obj.unEditDesignatedId = '';
				obj.unEditIsSaveDb = '';
				obj.designatedId = '';//初使化为空
				obj.designatedType = '';//初使化为空
				obj.isOverride = '';//初使化为空
				obj.isSaveDb = '';
				obj.uploadFileType = '';//初始化为空
				obj.isGetZhengWen = '';//初始化为空
				obj.applyTo = '';
				obj.inputMode = dyFormInputMode.fileUpload;//附件上传
				obj.optionDataSource = '';//1.常量 2.取字典 ''.隐藏这个选项
				var options = new Array();//radio,checkbox,select
				obj.options = options;
				var chkArr = new Array();//校验规则[value,text,rule]
				obj.checkRules = chkArr;
				/********ruan start***********/
				obj.fontWidth = '';
				obj.setUrlOnlyRead = '';
				obj.fontHight = '';
				obj.textAlign = '';
				obj.isHide = elementShow.show;
				obj.defaultValueWay = '';
				obj.inputDataType = '';
				obj.relationDataTwoSql = '';
				obj.relationDataText = '';
				obj.relationDataValue = '';
				obj.relationDataSql = '';
				obj.relationDataShowType = '';
				obj.relationDataShowMethod = '';
//				obj.relationDataTypeTwo = '';
				obj.relationDataTextTwo = '';
				obj.relationDataValueTwo = '';
//				obj.relationDataUuid = '';
				obj.relationDataDefiantion = '';
				/********ruan end***********/
			}else{//编辑
				obj.uuid = colObj.uuid;
				$(colObj[i]).parent().prev().html(colObj.colCnName);
				obj.colCnName =  $(buttonArr[i]).parent().prev().html();
				obj.defaultValue = colObj.defaultValue;
				obj.fontSize  =  colObj.fontSize;
				obj.fontColor =  colObj.fontColor;
				obj.jsFunction  =  colObj.jsFunction;
				obj.dataShow = colObj.dataShow; //初始化为直接展示
				obj.dataType = colObj.dataType;//初使化为普通类型
				obj.dataLength = (colObj.dataLength == undefined ? "" : colObj.dataLength);//初使化为空
				obj.floatSet = (colObj.floatSet == undefined ? "" : colObj.floatSet);//初使化为空
				obj.ctrlField = (colObj.ctrlField == undefined ? "" : colObj.ctrlField);//初使化为空
				obj.serviceName = colObj.serviceName;
				obj.methodName = colObj.methodName;
				obj.data = colObj.data;
				obj.unEditDesignatedId = colObj.unEditDesignatedId;
				obj.unEditIsSaveDb = colObj.unEditIsSaveDb;
				obj.designatedId = colObj.designatedId;
				obj.designatedType = colObj.designatedType;
				obj.isOverride = colObj.isOverride;
				obj.isSaveDb = colObj.isSaveDb;
				obj.uploadFileType = colObj.uploadFileType;
				obj.isGetZhengWen = colObj.isGetZhengWen;
				obj.applyTo = colObj.applyTo;
				obj.inputMode = colObj.inputMode;//手动输入
				obj.optionDataSource = colObj.optionDataSource;//1.常量 2.取字典 ''.隐藏这个选项
				obj.options = colObj.options;
				obj.checkRules = colObj.checkRules;
				/********ruan start***********/
				obj.fontWidth = colObj.fontWidth;
				obj.setUrlOnlyRead = colObj.setUrlOnlyRead;
				obj.fontHight = colObj.fontHight;
				obj.textAlign = colObj.textAlign;
				obj.isHide = elementShowunEditIsSaveDb.show;
				obj.defaultValueWay = colObj.defaultValueWay;
				obj.inputDataType = colObj.inputDataType;
				obj.relationDataTwoSql = colObj.relationDataTwoSql;
				obj.relationDataText = colObj.relationDataText;
				obj.relationDataValue = colObj.relationDataValue;
				obj.relationDataSql = colObj.relationDataSql;
				obj.relationDataShowType = colObj.relationDataShowType;
				obj.relationDataShowMethod = colObj.relationDataShowMethod;
				
//				obj.relationDataTypeTwo = '';
				obj.relationDataTextTwo = colObj.relationDataTextTwo;
				obj.relationDataValueTwo = colObj.relationDataValueTwo;
//				obj.relationDataUuid = '';
				obj.relationDataDefiantion = colObj.relationDataDefiantion;
				/********ruan end***********/
			}
			setFormData(buttonArr[i],obj);
		}
		
		//对select下拉单选框
		var selectArr = $(iframeDocObj).find("select");
		for(var i=0;i<selectArr.length;i++){
			$(selectArr[i]).dblclick(function (){inputDBClick(this);});
			//构造初使化数据
			var obj = new Object();
			//默认为"input"
			obj.formEleType = "select";
			if($(selectArr[i]).attr("id") == undefined) {
				$(selectArr[i]).attr("id",$(selectArr[i]).attr("name"));
				obj.formEleId = $(selectArr[i]).attr("id");
			}else {
				obj.formEleId = $(selectArr[i]).attr("id");
			}
			obj.colEnName = $(selectArr[i]).attr("name");
			var colObj = getColInfoObj($(selectArr[i]).attr("name"));
			if(undefined == colObj){//新建
				obj.uuid = '';
				obj.colCnName =  $(selectArr[i]).parent().prev().html();
				obj.defaultValue = '';
				obj.fontSize  =  '';
				obj.fontColor =  '';
				obj.jsFunction =  '';
				obj.dataShow = dyFormDataShow.directShow; //初始化为直接展示
				obj.dataType = dyFormInputType.text;//初使化为普通类型
				obj.dataLength = "";//初使化为空
				obj.floatSet = "";//初使化为空
				obj.ctrlField = "";//初使化为空
				obj.serviceName = '';//初使化为空
				obj.methodName = '';//初使化为空
				obj.data = '';//初使化为空
				obj.unEditDesignatedId = '';
				obj.unEditIsSaveDb = '';
				obj.designatedId = '';//初使化为空
				obj.designatedType = '';//初使化为空
				obj.isOverride = '';//初使化为空
				obj.isSaveDb = '';
				obj.uploadFileType = '';//初始化为空
				obj.isGetZhengWen = '';//初始化为空
				obj.applyTo = '';
				obj.inputMode = dyFormInputMode.selectMutilFase;//下拉
				obj.optionDataSource = dyDataSourceType.dataConstant;//1.常量 2.取字典 ''.隐藏这个选项
				var options = new Array();//radio,checkbox,select
				obj.options = options;
				var chkArr = new Array();//校验规则[value,text,rule]
				obj.checkRules = chkArr;
				/********ruan start***********/
				obj.fontWidth = '';
				obj.setUrlOnlyRead = '';
				obj.fontHight = '';
				obj.textAlign = '';
				obj.isHide = elementShow.show;
				obj.defaultValueWay = '';
				obj.inputDataType = '';
				obj.relationDataTwoSql = '';
				obj.relationDataText = '';
				obj.relationDataValue = '';
				obj.relationDataSql = '';
				obj.relationDataShowType = '';
				obj.relationDataShowMethod = '';
//				obj.relationDataTypeTwo = '';
				obj.relationDataTextTwo = '';
				obj.relationDataValueTwo = '';
//				obj.relationDataUuid = '';
				obj.relationDataDefiantion = '';
				/********ruan end***********/
			}else{//编辑
				obj.uuid = colObj.uuid;
				$(selectArr[i]).parent().prev().html(colObj.colCnName);
				obj.colCnName =  $(selectArr[i]).parent().prev().html();
				obj.defaultValue = colObj.defaultValue;
				obj.fontSize  =  colObj.fontSize;
				obj.fontColor =  colObj.fontColor;
				obj.jsFunction  =  colObj.jsFunction;
				obj.dataShow = colObj.dataShow; //初始化为直接展示
				obj.dataType = colObj.dataType;//初使化为普通类型
				obj.dataLength = (colObj.dataLength == undefined ? "" : colObj.dataLength);//初使化为空
				obj.floatSet = (colObj.floatSet == undefined ? "" : colObj.floatSet);//初使化为空
				obj.ctrlField = (colObj.ctrlField == undefined ? "" : colObj.ctrlField);//初使化为空
				obj.serviceName = colObj.serviceName;
				obj.methodName = colObj.methodName;
				obj.data = colObj.data;
				obj.unEditDesignatedId = colObj.unEditDesignatedId;
				obj.unEditIsSaveDb = colObj.unEditIsSaveDb;
				obj.designatedId = colObj.designatedId;
				obj.designatedType = colObj.designatedType;
				obj.isOverride = colObj.isOverride;
				obj.isSaveDb = colObj.isSaveDb;
				obj.uploadFileType = colObj.uploadFileType;
				obj.isGetZhengWen = colObj.isGetZhengWen;
				obj.applyTo = colObj.applyTo;
				obj.inputMode = colObj.inputMode;//手动输入
				obj.optionDataSource = colObj.optionDataSource;//1.常量 2.取字典 ''.隐藏这个选项
				obj.options = colObj.options;
				obj.checkRules = colObj.checkRules;
				/********ruan start***********/
				obj.fontWidth = colObj.fontWidth;
				obj.setUrlOnlyRead = colObj.setUrlOnlyRead;
				obj.fontHight = colObj.fontHight;
				obj.textAlign = colObj.textAlign;
				obj.isHide = elementShow.show;
				obj.defaultValueWay = colObj.defaultValueWay;
				obj.inputDataType = colObj.inputDataType;
				obj.relationDataTwoSql = colObj.relationDataTwoSql;
				obj.relationDataText = colObj.relationDataText;
				obj.relationDataValue = colObj.relationDataValue;
				obj.relationDataSql = colObj.relationDataSql;
				obj.relationDataShowType = colObj.relationDataShowType;
				obj.relationDataShowMethod = colObj.relationDataShowMethod;
//				obj.relationDataTypeTwo = '';
				obj.relationDataTextTwo = colObj.relationDataTextTwo;
				obj.relationDataValueTwo = colObj.relationDataValueTwo;
//				obj.relationDataUuid = '';
				obj.relationDataDefiantion = colObj.relationDataDefiantion;
				/********ruan end***********/
			}
			setFormData(selectArr[i],obj);
		}
		
		//在每个表格前面增加表格信息设置按键
		var tableArr = $(iframeDocObj).find("table");
		for(var i=0;i<tableArr.length;i++){
			//对主表id进行设置
			if(dyTableType.mainTable == $(tableArr[i]).attr("tableType")) {
				var obj = new Object();
				obj.id = ($(tableArr[i]).attr("id") == undefined ? "" : $(tableArr[i]).attr("id"));
				if($("#tableId").val() == undefined || $("#tableId").val == null) {
					$("#tableId").val(obj.id);
				}
			}
			
			//从表才要进行设置
			if(dyTableType.subtable == $(tableArr[i]).attr("tableType")){
				var obj = new Object();
				obj.tableType = ($(tableArr[i]).attr("tableType") == undefined ? dyTableType.subtable : $(tableArr[i]).attr("tableType"));
				obj.id = ($(tableArr[i]).attr("id") == undefined ? "" : $(tableArr[i]).attr("id"));
				var subTableObj = getSubTableObj(obj.id);
				if(undefined == subTableObj){//新建
					obj.tableId = ($(tableArr[i]).attr("tableId") == undefined ? "" : $(tableArr[i]).attr("tableId"));
					obj.tableTitle = ($(tableArr[i]).attr("tableTitle") == undefined ? "" : $(tableArr[i]).attr("tableTitle"));
					obj.groupShowTitle = ($(tableArr[i]).attr("groupShowTitle") == undefined ? "" : $(tableArr[i]).attr("groupShowTitle"));
					obj.subformApplyTableId = ($(tableArr[i]).attr("subformApplyTableId") == undefined ? "" : $(tableArr[i]).attr("subformApplyTableId"));
					obj.subrRelationDataDefiantion = ($(tableArr[i]).attr("subrRelationDataDefiantion") == undefined ? "" : $(tableArr[i]).attr("subrRelationDataDefiantion"));
					obj.editType = ($(tableArr[i]).attr("editType") == undefined ? dySubFormEdittype.rowEdit : $(tableArr[i]).attr("editType"));
					obj.tableOpen = ($(tableArr[i]).attr("tableOpen") == undefined ? dySubFormTableOpen.open : $(tableArr[i]).attr("tableOpen"));
					obj.isGroupShowTitle = ($(tableArr[i]).attr("isGroupShowTitle") == undefined ? dySubFormGroupShow.show : $(tableArr[i]).attr("isGroupShowTitle"));
					obj.hideButtons = ($(tableArr[i]).attr("hideButtons") == undefined ? dySubFormHideButtons.isHide : $(tableArr[i]).attr("hideButtons"));
				}else{//编辑
					obj.tableId = (subTableObj.tableId == undefined ? "" : subTableObj.tableId);
					obj.tableTitle =  subTableObj.tableTitle;
					obj.groupShowTitle =  subTableObj.groupShowTitle;
					obj.subformApplyTableId =  (subTableObj.subformApplyTableId == undefined ? "" : subTableObj.subformApplyTableId);
					obj.subrRelationDataDefiantion =  (subTableObj.subrRelationDataDefiantion == undefined ? "" : subTableObj.subrRelationDataDefiantion);
					obj.editType = subTableObj.editType;
					obj.tableOpen = subTableObj.tableOpen;
					obj.isGroupShowTitle = subTableObj.isGroupShowTitle;
					obj.hideButtons = subTableObj.hideButtons;
					//初使化字段映射信息
					var colMappingArr = subTableObj.fields;
					var thArr = $(tableArr[i]).find('th');
					for(var j=0;j<thArr.length;j++){
						for(var k=0;k<colMappingArr.length;k++){
							if($(thArr[j]).html() == colMappingArr[k].thCnName){
								$(thArr[j]).attr('columnId',colMappingArr[k].uuid);
								$(thArr[j]).attr('columnId2',colMappingArr[k].uuid2);
								$(thArr[j]).attr("fieldOrder", k);
								break;
							}
						}
					}
				}
				setTableAttributes(tableArr[i],obj);
				setTableInfoCfgBtn(tableArr[i]);
			}
		}
	}

	//打开表信息设置窗口
	function openTableInfoCfgDiv(currDiv){
		
		//读取对应表格初使化信息
		var tableObj = $(currDiv).next();
		currTableObj = tableObj;
		var tableId = $(tableObj).attr("tableId");
		var tableTitle = $(tableObj).attr("tableTitle");
		var groupShowTitle = $(tableObj).attr("groupShowTitle");
		var subformApplyTableId = $(tableObj).attr("subformApplyTableId");
		var subrRelationDataDefiantion = $(tableObj).attr("subrRelationDataDefiantion");
		//解析从表映射数据的字段
		$(".subDefinitioncontentiteam").remove();
		$("#subrRelationDataDefiantion").val(subrRelationDataDefiantion);
		if(subrRelationDataDefiantion != ""){
			var str = subrRelationDataDefiantion;
			var tempArray = str.split("|");
			for(var j=0;j<tempArray.length;j++){
				var tempObj = JSON.parse(tempArray[j]);
				var subRelationDataDefiantion = '<tr class="subDefinitioncontentiteam">';
				subRelationDataDefiantion += '<td>'+tempObj.zhubiaoTitle+'</td>';
				subRelationDataDefiantion += '<td>'+tempObj.zhubiaoField+'</td>';
				subRelationDataDefiantion += '<td>'+tempObj.formField+'</td>';
				subRelationDataDefiantion += '<td><button class="delField">删除</button></td>';
				subRelationDataDefiantion += '</tr>';
				$(".subDefinitiontrtable").append(subRelationDataDefiantion);
			}
		}
		
		var tableType = $(tableObj).attr("tableType");
		var editType = $(tableObj).attr("editType");
		var tableOpen = $(tableObj).attr("tableOpen");
		var isGroupShowTitle = $(tableObj).attr("isGroupShowTitle");
		var hideButtons = $(tableObj).attr("hideButtons");
		
		tableId = (tableId == undefined ? "" : tableId);
		tableTitle = (tableTitle == undefined ? "" : tableTitle);
		groupShowTitle = (groupShowTitle == undefined ? "" : groupShowTitle);
		subformApplyTableId = (subformApplyTableId == undefined ? "" : subformApplyTableId);
		subrRelationDataDefiantion = (subrRelationDataDefiantion == undefined ? "" : subrRelationDataDefiantion);
		tableType = (tableType == undefined ? dyTableType.subtable : tableType);
		editType = (editType == undefined ? dySubFormEdittype.rowEdit : editType);
		tableOpen = (tableOpen == undefined ? dySubFormTableOpen.open : tableOpen);
		isGroupShowTitle = (isGroupShowTitle == undefined ? dySubFormGroupShow.show : isGroupShowTitle);
		hideButtons = (hideButtons == undefined ? dySubFormHideButtons.isHide : hideButtons);
		
		var obj = new Object();
		obj.tableId = tableId;
		obj.tableTitle = tableTitle;
		obj.groupShowTitle = groupShowTitle;
		obj.subformApplyTableId = subformApplyTableId;
		obj.subrRelationDataDefiantion = subrRelationDataDefiantion;
		obj.tableType = tableType;
		obj.editType = editType;
		obj.tableOpen = tableOpen;
		obj.isGroupShowTitle = isGroupShowTitle;
		obj.hideButtons = hideButtons;
		changeTableSelect3($(currTableObj).attr("tableId"));
		initTableInfo(obj);
		
		//清空表字段 信息列表
		$("#fieldCfgTable").find("tr[name=tr-td]").remove();
		
		//添加表字段信息行
		var thObjArr = $(tableObj).find("th");
		for(var i=0;i<thObjArr.length;i++){
			$("#fieldCfgTable").append("<tr name='tr-td'><td>" + $(thObjArr[i]).html() + "</td><td name=fieldMapping></td><td name=aboutOtherTableData></td><td name=subColHidden><select name=subColHidden><option value='1'>否</option><option value='2'>是</option></select></td><td name=subColEdit><select name=subColEdit><option value='2'>是</option><option value='1'>否</option></select></td><td name=fieldWidth ><input type='text' name='fieldWidth'></td></tr>");
		}
		//初使化字段信息
		initFieldInfo(currTableObj);
		$("#tableInfoCfgDiv").dialog("open");
		$('#tableInfoCfgDiv').dialog('option',
				'buttons', 
				{ "确定": 
					function() {
					saveTableInfoCfg();
						}, 
				  "取消":
					  function() {
					  closeTableInfoCfgDiv();
				  }
});
	}

	function setTableInfoCfgBtn(tableObj){
		var $cfg = $('<div class="wubinsb" style="cursor:pointer;width:80px;">' + dybtn.subTableCfgLink + '</div>');
		if($(tableObj).prev().attr("class")&&$(tableObj).prev().attr("class")=="wubinsb"){
			$(tableObj).prev().click(function(){
				openTableInfoCfgDiv(this);
			});
		}else{
			$cfg.insertBefore(tableObj);
			$cfg.click(function(){
				openTableInfoCfgDiv(this);
			});
		}
	}

	//设置当前表相关属性
	function setTableAttributes(currTable,obj){
		$(currTable).attr("id",obj.id);
		$(currTable).attr("tableId",obj.tableId);
		$(currTable).attr("tableTitle",obj.tableTitle);
		$(currTable).attr("groupShowTitle",obj.groupShowTitle);
		$(currTable).attr("subformApplyTableId",obj.subformApplyTableId);
		$(currTable).attr("subrRelationDataDefiantion",obj.subrRelationDataDefiantion);
		$(currTable).attr("tableType",obj.tableType);
		$(currTable).attr("editType",obj.editType);
		$(currTable).attr("tableOpen",obj.tableOpen);
		$(currTable).attr("isGroupShowTitle",obj.isGroupShowTitle);
		$(currTable).attr("hideButtons",obj.hideButtons);
	}

	//初使化表相关信息
	function initTableInfo(obj){
		
		var uuid = $("#formUuid").val();
		JDS.call({
			service:"formDefinitionService.getFieldByForm",
			data:[uuid],
			success:function(result) {
				data = result.data;
				var optionStr = "";
				for(var i=0;i<data.length;i++) {
					optionStr += "<option value='"+data[i].fieldName+"'>"+data[i].descname+"</option>";
				}
				$("#subformApplyCol").html(optionStr);
			}
		});
		
		//重新加载可选表列表
		$.extend(true,setting,{async:{otherParam:{selectedId:obj.tableId}}});
		//
		$.extend(true,setting2,{async:{otherParam:{selectedId:obj.tableId}}});
		$.fn.zTree.init($("#tableTree"), setting);
		$.fn.zTree.init($("#tableTree2"), setting2);
		$('#tableDefinitionId').val(obj.tableId);
		$('#subformApplyTableId').val(obj.subformApplyTableId);
		$('#subrRelationDataDefiantion').val(obj.subrRelationDataDefiantion);
		$("#tableTitle").val(obj.tableTitle);
		$("#groupShowTitle").val(obj.groupShowTitle);
		$("input[name=editType][value=" + obj.editType + "]").attr("checked","checked");
		$("input[name=tableOpen][value=" + obj.tableOpen + "]").attr("checked","checked");
		$("input[name=isGroupShowTitle][value=" + obj.isGroupShowTitle + "]").attr("checked","checked");
		$("input[name=hideButtons][value=" + obj.hideButtons + "]").attr("checked","checked");
	}

	function inputDBClick(currObj){
		currInputObj = currObj;
		var uuid = $(currObj).attr("uuid");
		var formEleType = $(currObj).attr("formEleType");
		if(formEleType == "checkbox" || formEleType == "radio") {
			$("#ctrlFieldTr").css("display","");
		}else {
			$("#ctrlFieldTr").css("display","none");
		}
		var formEleId = $(currObj).attr("id");
		var colCnName = $(currObj).attr("colcnname");
		var colEnName = $(currObj).attr("name");
		var defaultValue = $(currObj).attr("defaultValue");
		var fontSize = $(currObj).attr("fontSize");
		var fontColor = $(currObj).attr("fontColor");
		var jsFunction  = $(currObj).attr("jsFunction");
		if(fontColor != null && fontColor != undefined) {
			$("#fontcolor").css("background-color",fontColor);
		}else {
			$("#fontcolor").css("background-color","black");
		}
		var dataShow = $(currObj).attr("dataShow");
		var dataType = $(currObj).attr("dataType");
		var dataLength = $(currObj).attr("dataLength");
		var floatSet = $(currObj).attr("floatSet");
		var ctrlField = $(currObj).attr("ctrlField");
		var tempArray = ctrlField.split(",")[1];
		$("#ctrlFieldName").val(tempArray);
		var serviceName = $(currObj).attr("serviceName");
		var methodName = $(currObj).attr("methodName");
		var data = $(currObj).attr("data");
		var unEditDesignatedId = $(currObj).attr("unEditDesignatedId");
		var unEditIsSaveDb = $(currObj).attr("unEditIsSaveDb");
		var designatedId = $(currObj).attr("designatedId");
		var designatedType = $(currObj).attr("designatedType");
		var isOverride = $(currObj).attr("isOverride");
		var isSaveDb = $(currObj).attr("isSaveDb");
		var uploadFileType = $(currObj).attr("uploadFileType");
		var isGetZhengWen = $(currObj).attr("isGetZhengWen");
		var applyTo = $(currObj).attr("applyTo");
		$("#applyToName").comboTree("initValue", applyTo);
		var inputMode = $(currObj).attr("inputMode");
		var optionDataSource = $(currObj).attr("optionDataSource");
		var options = $(currObj).attr("opts");
		var checkRules = $(currObj).attr("checkRules");
		
		//参数
		var obj = new Object();
		obj.uuid = (uuid == undefined ? "" : uuid);
		obj.formEleType = (formEleType == undefined ? "" : formEleType);
		obj.formEleId = (formEleId == undefined ? "" : formEleId);
		obj.colCnName = (colCnName == undefined ? "" : colCnName);
		obj.colEnName = (colEnName == undefined ? "" : colEnName);
		obj.defaultValue = (defaultValue == undefined ? "" : defaultValue);
		obj.fontSize = (fontSize == undefined ? "" : fontSize);
		obj.fontColor = (fontColor == undefined ? "" : fontColor);
		obj.jsFunction = (jsFunction == undefined ? "" : jsFunction);
		obj.dataShow = (dataShow == undefined ? "" : dataShow);
		obj.dataType = (dataType == undefined ? "" : dataType);
		obj.dataLength = (dataLength == undefined ? "" : dataLength);
		obj.floatSet = (floatSet == undefined ? "" : floatSet);
		obj.ctrlField = (ctrlField == undefined ? "" : ctrlField);
		obj.serviceName = (serviceName == undefined ? "" : serviceName);
		obj.methodName = (methodName == undefined ? "" : methodName);
		obj.data = (data == undefined ? "" : data);
		obj.unEditDesignatedId = (unEditDesignatedId == undefined ? "" : unEditDesignatedId);
		obj.unEditIsSaveDb = (unEditIsSaveDb == undefined ? "" : unEditIsSaveDb);
		obj.designatedId = (designatedId == undefined ? "" : designatedId);
		obj.designatedType = (designatedType == undefined ? "" : designatedType);
		obj.isOverride = (isOverride == undefined ? "" : isOverride);
		obj.isSaveDb = (isSaveDb == undefined ? "" : isSaveDb);
		obj.uploadFileType = (uploadFileType == undefined ? "" : uploadFileType);
		obj.isGetZhengWen = (isGetZhengWen == undefined ? "" : isGetZhengWen);
		obj.applyTo = (applyTo == undefined ? "" : applyTo);
		obj.inputMode = (inputMode == undefined ? "" : inputMode);
		obj.optionDataSource = (optionDataSource == undefined ? "" : optionDataSource);
		/********ruan start***********/
		var fontWidth = $(currObj).attr("fontWidth");
		obj.fontWidth = (fontWidth == undefined ? "" : fontWidth);
		var setUrlOnlyRead = $(currObj).attr("setUrlOnlyRead");
		obj.setUrlOnlyRead = (setUrlOnlyRead == undefined ? "" :setUrlOnlyRead);
		var fontHight = $(currObj).attr("fontHight");
		obj.fontHight = (fontHight == undefined ? "" : fontHight);
		var textAlign = $(currObj).attr("textAlign");
		obj.textAlign = (textAlign == undefined ? "" : textAlign);
		var isHide = $(currObj).attr("isHide");
		obj.isHide = (isHide == undefined ? "" : isHide);
//		var dataShow = $(currObj).attr("dataShow");
//		obj.dataShow = (dataShow == undefined ? "" : dataShow);
		var defaultValueWay = $(currObj).attr("defaultValueWay");
		obj.defaultValueWay = (defaultValueWay == undefined ? "" : defaultValueWay);
		var inputDataType = $(currObj).attr("inputDataType");
		obj.inputDataType = (inputDataType == undefined ? "" : inputDataType);
		var relationDataTwoSql = $(currObj).attr("relationDataTwoSql");
		obj.relationDataTwoSql = (relationDataTwoSql == undefined ? "" : relationDataTwoSql);
		var relationDataText = $(currObj).attr("relationDataText");
		obj.relationDataText = (relationDataText == undefined ? "" : relationDataText);
		var relationDataValue = $(currObj).attr("relationDataValue");
		obj.relationDataValue = (relationDataValue == undefined ? "" : relationDataValue);
		var relationDataSql = $(currObj).attr("relationDataSql");
		obj.relationDataSql = (relationDataSql == undefined ? "" : relationDataSql);
		var relationDataShowType = $(currObj).attr("relationDataShowType");
		var relationDataShowMethod = $(currObj).attr("relationDataShowMethod");
		obj.relationDataShowType = (relationDataShowType == undefined ? "" : relationDataShowType);
		obj.relationDataShowMethod = (relationDataShowMethod == undefined ? "" : relationDataShowMethod);
//		var relationDataTypeTwo = $(currObj).attr("relationDataTypeTwo");
//		obj.relationDataTypeTwo = (relationDataTypeTwo == undefined ? "" : relationDataTypeTwo);
		var relationDataTextTwo = $(currObj).attr("relationDataTextTwo");
		obj.relationDataTextTwo = (relationDataTextTwo == undefined ? "" : relationDataTextTwo);
		var relationDataValueTwo = $(currObj).attr("relationDataValueTwo");
		obj.relationDataValueTwo = (relationDataValueTwo == undefined ? "" : relationDataValueTwo);
//		var relationDataUuid = $(currObj).attr("relationDataUuid");
//		obj.relationDataUuid = (relationDataUuid == undefined ? "" : relationDataUuid);
		var relationDataDefiantion = $(currObj).attr("relationDataDefiantion");
		obj.relationDataDefiantion = (relationDataDefiantion == undefined ? "" : relationDataDefiantion);
		/********ruan end***********/
		obj.options = JSON.parse(options);
		obj.checkRules = JSON.parse(checkRules);
		//绑定初始的数据列
		if(relationDataTextTwo!="" && relationDataTextTwo!=""){
			sqlOption2(relationDataValueTwo);
		}
		initColAttributes(obj);
		$("#attrCfgDiv").dialog("open");
		$('#attrCfgDiv').dialog('option',
							'buttons', 
							{ "确定": 
								function() { 
								saveColAttributes();
									}, 
							  "取消":
								  function() {
								  closeAttrCfgWin();
							  }
		});
	}

	//初使化字段属性值
	function initColAttributes(paramObj){
//		$("#uuid").val(paramObj.uuid);
		$("#formEleType").val(paramObj.formEleType);
		$("#formEleId").val(paramObj.formEleId);
		$("#colEnName").val(paramObj.colEnName);
		$("#colCnName").val(paramObj.colCnName);
		$("#defaultValue").val(paramObj.defaultValue);
		$("#fontSize").val(paramObj.fontSize);
		$("#fontColor").val(paramObj.fontColor);
//		$("#jsFunction").val(paramObj.jsFunction);
//		$('#dataShow').val(paramObj.dataShow);
//		$('input[type=radio][name=dataShow][value=' + paramObj.dataShow + ']').attr('checked',true);
		$('#dataType').val(paramObj.dataType);
		$("#dataLength").val(paramObj.dataLength);
		$("#floatSet").val(paramObj.floatSet);
		$("#ctrlField").val(paramObj.ctrlField);
//		$("#serviceName").val(paramObj.serviceName);
//		$("#methodName").val(paramObj.methodName);
		$("#data").val(paramObj.data);
		$("#unEditDesignatedId").val(paramObj.unEditDesignatedId);
		$("#unEditIsSaveDb").val(paramObj.unEditIsSaveDb);
		$("#designatedId").val(paramObj.designatedId);
		$("#designatedType").val(paramObj.designatedType);
		$("#isOverride").val(paramObj.isOverride);
		$("#isSaveDb").val(paramObj.isSaveDb);
		$("#uploadFileType").val(paramObj.uploadFileType);
		$('input[type=radio][name=isGetZhengWen][value=' + paramObj.isGetZhengWen + ']').attr('checked',true);
		$("#applyTo").val(paramObj.applyTo);
		/**ruan start**/
		$("#fontWidth").val(paramObj.fontWidth);
		$("#setUrlOnlyRead").val(paramObj.setUrlOnlyRead);
		$("#fontHight").val(paramObj.fontHight);
		$("#textAlign").val(paramObj.textAlign);
		if(paramObj.isHide == 2) {
			$('input[type=checkbox][name=isHide]').attr('checked',true);
		}else {
			$('input[type=checkbox][name=isHide]').attr('checked',false);
		}
		if(paramObj.dataShow == 2) {
			$('input[type=checkbox][name=dataShow]').attr('checked',true);
		}else {
			$('input[type=checkbox][name=dataShow]').attr('checked',false);
		}
		$('input[type=radio][name=defaultValueWay][value=' + paramObj.defaultValueWay + ']').attr('checked',true);
		if(paramObj.defaultValueWay==dyFormInputValue.jsEquation){
			$("#defaultValue").val(paramObj.jsFunction);
		} else if(paramObj.defaultValueWay==dyFormInputValue.relationDoc){
			$(".relationClass").show();
		}
		
		$("#relationDataShowType").val(paramObj.relationDataShowType);
		$('input[type=radio][name=relationDataShowMethod][value=' + paramObj.relationDataShowMethod + ']').attr('checked',true);
		$("#inputDataType").val(paramObj.inputDataType);
		$("#relationDataText").val(paramObj.relationDataText);
		$("#relationDataValue").val(paramObj.relationDataValue);
		$("#relationDataSql").val(paramObj.relationDataSql);
		$("#relationDataTwoSql").val(paramObj.relationDataTwoSql);
//		$('input[type=radio][name=relationDataTypeTwo][value=' + paramObj.relationDataTypeTwo + ']').attr('checked',true);
		$("#relationDataTextTwo").val(paramObj.relationDataTextTwo);
		$("#relationDataValueTwo").val(paramObj.relationDataValueTwo);
//		$("#relationDataUuid").val(paramObj.relationDataUuid);
		//解析关联数据的字段
		$(".definitioncontentiteam").remove();
		$("#relationDataDefiantion").val(paramObj.relationDataDefiantion);
		if(paramObj.relationDataDefiantion!=""){
			var str = paramObj.relationDataDefiantion;
			var tempArray = str.split("|");
			for(var j=0;j<tempArray.length;j++){
				var tempObj = JSON.parse(tempArray[j]);
				var relationDataDefiantion = '<tr class="definitioncontentiteam">';
				relationDataDefiantion += '<td>'+tempObj.sqlTitle+'</td>';
				relationDataDefiantion += '<td>'+tempObj.sqlField+'</td>';
				relationDataDefiantion += '<td>'+tempObj.formTitle+'</td>';
				relationDataDefiantion += '<td  style="display: none;">'+tempObj.formField+'</td>';
				relationDataDefiantion += '<td>'+tempObj.search+'</td>';
				relationDataDefiantion += '<td><button class="delField">删除</button></td>';
				relationDataDefiantion += '</tr>';
				$(".definitiontrtable").append(relationDataDefiantion);
			}
		}
		/**ruan end**/
		//select,radio,checkbox详细信息
		createOptionElement(paramObj.optionDataSource,paramObj.formEleType,paramObj.colEnName,paramObj.options);
		//字段类型详细信息
		createDataTypeDetailElement(paramObj.dataType,paramObj.dataLength,paramObj.floatSet,paramObj.ctrlField,paramObj.serviceName,paramObj.methodName,paramObj.data,paramObj.unEditDesignatedId,paramObj.unEditIsSaveDb,paramObj.designatedId,paramObj.designatedType,paramObj.isOverride,paramObj.isSaveDb,paramObj.uploadFileType,paramObj.isGetZhengWen);
		
		createDataTypeDetailElement1(paramObj.inputDataType,paramObj.dataLength,paramObj.floatSet,paramObj.ctrlField,paramObj.serviceName,paramObj.methodName,paramObj.data,paramObj.unEditDesignatedId,paramObj.unEditIsSaveDb,paramObj.designatedId,paramObj.designatedType,paramObj.isOverride,paramObj.isSaveDb,paramObj.uploadFileType,paramObj.isGetZhengWen);
		//关联文档时
		if(paramObj.defaultValueWay==dyFormInputValue.relationDoc){
			$(".relationClass").show();
			$("#defaultValue").hide();
		}else{
			$(".relationClass").hide();
			$("#defaultValue").show();
		}
		
		//校验规则
		initCheckRule(paramObj.checkRules);
	}

	function createOptionElement(dataSource,eleType,colEnName,options){
		if('radio' == eleType || 'checkbox' == eleType || 'select' == eleType || 'button' == eleType){//select,radio,checkbox
			$('#optionDataSourceTr').show();
			$("#inputDataTypetr").hide();
			$("#definitiontr").hide();
			$('input[name=optionDataSource]').attr('checked',false);
			$('#optionsTr').hide();
			if(dataSource != undefined && '' != dataSource){
				$("input[name=optionDataSource][value=" + dataSource + "]").attr("checked",true);
				$('#optionsTr').show();
				var td1 = '';
				var td2 = '';
				if(dataSource == dyDataSourceType.dataConstant){//常量
					td1 = dylbl.opt + ':';
					if('radio' == eleType || 'checkbox' == eleType){
						td2 = $($('input[name=' + colEnName + ']')[0]).parent().html();
					}else if('button' == eleType){
						td2 = $($('button[name=' + colEnName + ']')[0]).parent().html();
					}
					else{
						td2 = $($('select[name=' + colEnName + ']')[0]).parent().html();
					}
				}else{//2.字典
					var dicCode = '';
					if(options != undefined && options.length == 1){
						dicCode = options[0].value;
					}
					td1 = dylbl.optCode + ':';
	 				td2 = '<input type="text" class="w100" name="dicCode" id="dicCode" value="'+dicCode+'"/>';
				}
				$('#optionsTd_1').html(td1);
				$('#optionsTd_2').html(td2);
			}
		}else{
			$('#optionDataSourceTr').hide();
			$('#optionsTr').hide();
			$("#inputDataTypetr").show();
			$("#definitiontr").show();
		}
	}
	
	function createDataTypeDetailElement(dataType,value,floatSet,ctrlField,serviceName,methodName,data,unEditDesignatedId,unEditIsSaveDb,designatedId,designatedType,isOverride,isSaveDb,uploadFileType,isGetZhengWen){
		if($('#dataLengthSpan')){
			$('#dataLengthSpan').remove();
		}
		if($('#floatSetSpan')){
			$("#floatSetSpan").hide();
		}
		if(dataType == dyFormInputType.text || dataType==""){//
			elementStr = '<span id="dataLengthSpan">'
					+ '<div><input style="width:37%;float:left;"name="dataLength" type="text" class="w100" id="dataLength" value="' + (value != "" ? value : '255') + '"/><span class="defineSpan" style="width:40px;">('+dylbl.length + ')</span>'
					+ '</div></span>';
			$('#dataType').parent().append(elementStr);
			$("#inputDataTypetr").show();
			$("#definitiontr").show();
		}else if(dataType == dyFormInputType.clob) {
			$("#inputDataTypetr").show();
		}
		else if(dataType == dyFormInputType.float){
//			elementStr = '<span id="floatSetSpan">'
//				+ '<div>计算结果保留<input style="width:20px; float: none;"name="floatSet" type="text" id="floatSet" value="0"/>位小数'
//				+ '</div></span>';
//			
//			$('#dataType').parent().append(elementStr);
			$("#floatSetSpan").show();
			$("#inputDataTypetr").hide();
			$("#definitiontr").hide();
		}
		else{
			$("#inputDataTypetr").hide();
			$("#definitiontr").hide();
		}
	}
	function createDataTypeDetailElement1(inputDataType,value,floatSet,ctrlField,serviceName,methodName,data,unEditDesignatedId,unEditIsSaveDb,designatedId,designatedType,isOverride,isSaveDb,uploadFileType,isGetZhengWen){
		$("#inputDataTypeValueInfo").html("");
		$("#inputDataTypeValue").hide();
		if($('#treeSpan')){
			$('#treeSpan').remove();
		}
		if($('#fileSpan')){
			$('#fileSpan').remove();
		}
		if($('#serialNumberSpan')){
			$('#serialNumberSpan').remove();
		}
		if($('#unEditSerialNumberSpan')){
			$('#unEditSerialNumberSpan').remove();
		}
		
		if(inputDataType == dyFormInputMode.treeSelect) {//树形下拉框 
			elementStr = '<span id="treeSpan" style="margin-left:10px;display: block;clear: both;">'+
				"<div><span class='defineSpan'>显示值" + ':</span><input style="float:none;width:75%;" name="serviceName" type="text" id="serviceName" value="' + (serviceName != undefined ? serviceName : '') + '"></div>'+
				"<div><span class='defineSpan'>隐藏值" + ':</span><input style="float:none;width:75%;" name="methodName" type="text"  id="methodName" value="' + (methodName != undefined ? methodName : '') + '"></div>' +
				"<input name= 'data' type='hidden' id='data' value=''>"+
				'</span>';
		$('#inputDataType').parent().append(elementStr);
		}else if(inputDataType == dyFormInputMode.unEditSerialNumber) {//不可编辑流水号
			elementStr = '<span id="unEditSerialNumberSpan" style="margin-left:10px;display: block;clear: both;">'+
			"<div><span class='defineSpan'>指定流水号id:</span>"+ '<select name="unEditDesignatedId" id="unEditDesignatedId"></select></div>'+
			"<div><span class='defineSpan'>是否保存数据库:</span>" + "<select name='unEditIsSaveDb' id='unEditIsSaveDb'>" + 
			'<option value="true">是</option><option value="false">否</option></select>'+
			"</div></span>";
			
			$('#inputDataType').parent().append(elementStr);
			$.ajax({
                url: contextPath + '/dytable/list/getSerialNumberIdList.action',
                type: "post",
                dataType: "json",
                success: function (result) {
                	$("#unEditDesignatedId").append("<option value='请选择'>请选择</option>");
                    $.each(result, function (key, data) {
                        $("#unEditDesignatedId").append("<option value=" + data + ">" + data + "</option>");
                    });
                    $('#unEditDesignatedId').find('option[value='+ unEditDesignatedId +']').attr('selected',true);
                }
            });
		}
		else if(inputDataType == dyFormInputMode.serialNumber) {//可编辑流水号
			elementStr = '<span id="serialNumberSpan" style="margin-left:10px;display: block;clear: both;">'+
			"<div><span class='defineSpan'>指定流水号id:</span>"+ '<select name="designatedId" id="designatedId"></select></div>'+
			"<div><span class='defineSpan'>指定的流水号分类:</span>"+ '<select name="designatedType" id="designatedType"></select></div>'+
			"<div><span class='defineSpan'>是否覆盖指针:</span>" + "<select name='isOverride' id='isOverride'>" + 
			'<option value="0">不覆盖</option><option value="1">新指针大于当前指针才覆盖</option><option value="2">覆盖</option></select></div>'+
			"<div><span class='defineSpan'>是否保存数据库:</span>" + "<select name='isSaveDb' id='isSaveDb'>" + 
			'<option value="0">是</option><option value="1">否</option></select>'+
			"</div></span>";
			
			$('#inputDataType').parent().append(elementStr);
				  $.ajax({
		                url: contextPath + '/dytable/list/getSerialNumberIdList.action',
		                type: "post",
		                dataType: "json",
		                success: function (result) {
		                	$("#designatedId").append("<option value='请选择'>请选择</option>");
		                    $.each(result, function (key, data) {
		                        $("#designatedId").append("<option value=" + data + ">" + data + "</option>");
		                    });
		                    $('#designatedId').find('option[value='+ designatedId +']').attr('selected',true);
		                }
		            });
				  
				  $.ajax({
		                url: contextPath + '/dytable/list/getSerialNumberTypeList.action',
		                type: "post",
		                dataType: "json",
		                success: function (result) {
		                	$("#designatedType").append("<option value='请选择'>请选择</option>");
		                    $.each(result, function (key, data) {
		                        $("#designatedType").append("<option value=" + data + ">" + data + "</option>");
		                    });
		                    $('#designatedType').find('option[value=' + designatedType + ']').attr('selected',true);
		                }
		            });
					$('#serialNumberSpan').find('option[value=' + isOverride + ']').attr('selected',true);
		}
		else if(inputDataType == dyFormInputMode.dialog){
			$("#inputDataTypeValueInfo").html("(执行的js)");
			$("#inputDataTypeValue").show();
		}
		else if(inputDataType == dyFormInputMode.xml){
			$("#inputDataTypeValueInfo").html("(xsl)");
			$("#inputDataTypeValue").show();
		}
		else if(inputDataType == dyFormInputMode.timeEmployForMeet||inputDataType == dyFormInputMode.timeEmployForCar||inputDataType == dyFormInputMode.timeEmployForDriver){
			$("#inputDataTypeValueInfo").html("(资源起始时间FieldName)");
			$("#inputDataTypeValue").show();
		}
		else if(inputDataType == dyFormInputMode.ckedit) {
			
		}
//		else if(inputDataType == dyFormInputMode.accessory1 || inputDataType == dyFormInputMode.accessory2 || inputDataType == dyFormInputMode.accessory3) {//附件上传
//					elementStr = '<span id="fileSpan">'+
//					"<div><span class='defineSpan'>是否启用附件签名:</span>" + 
//					"<input type='radio' name='isGetZhengWen' id='isGetZhengWen_1' value='1' style='margin-top: 11px;' checked='checked'></input><label for='file_laiyuan_1' style='margin-top: 8px;'>不启用</label>"+
//					"<input type='radio' name='isGetZhengWen' id='isGetZhengWen_2' value='2' style='margin-top: 11px;'></input><label for='file_laiyuan_2' style='margin-top: 8px;'>启用</label>"+
//					"<input type='hidden' id='isGetZhengWen' value=''></input>"+
//					'</div></span>';
//			$('#inputDataType').parent().append(elementStr);
//			$('#isGetZhengWen').val(isGetZhengWen);
//			$('input[type=radio][name=isGetZhengWen][value=' + isGetZhengWen + ']').attr('checked',true);
//		}
	}
	function initCheckRule(rules){
		$('input[type=checkbox][name=checkRule]').attr('checked',false);
		$('#uniqueCheckRuleTr').hide();
		$('#uniqueCheckRule').val('');
		if(rules != undefined){
			for(var i=0;i<rules.length;i++){
				var obj = rules[i];
				if(dyCheckRule.notNull == rules[i].value){//不允许为空
					$('#checkRule_1').attr('checked',true);
				}else if(dyCheckRule.url == rules[i].value){//必须是URL
					$('#checkRule_2').attr('checked',true);
				}else if(dyCheckRule.email == rules[i].value){//必须是邮箱地址
					$('#checkRule_3').attr('checked',true);
				}else if(dyCheckRule.idCard == rules[i].value){//必须是身份证
					$('#checkRule_4').attr('checked',true);
				}else if(dyCheckRule.unique == rules[i].value){//要求唯一
					$('#checkRule_5').attr('checked',true);
					$('#uniqueCheckRule').val(rules[i].rule == undefined ? '' : rules[i].rule);
					$('#uniqueCheckRuleTr').show();
				}
			}
		}
	}

	//设置字段属性值
	function setFormData(currObj,obj){
		if($(currObj).parents("table").attr("tabletype")==2&&!(obj.colEnName!="expand_field"||obj.colEnName!="signature_"||obj.colEnName!="body_col")){
		}else {
			if($(currObj).attr("type") == "radio") {
				$(currObj).attr("id",obj.id);
			}else if ($(currObj).attr("type") == "checkbox") {
				$(currObj).attr("id",obj.id);
			}else if ($(currObj).attr("type") == "button") {
				$(currObj).attr("id",obj.id);
			}else if($(currObj).attr("type") == "file") {
				$(currObj).attr("id",obj.id);
			}
			else {
				$(currObj).attr("id",obj.colEnName);
			}
			$(currObj).attr("uuid",obj.uuid);
			$(currObj).attr("name",obj.colEnName);
			$(currObj).attr("formEleType",obj.formEleType);
			$(currObj).attr("formEleId",obj.formEleId);
			$(currObj).attr("colEnName",obj.colEnName);
			$(currObj).attr("colCnName",obj.colCnName);
			$(currObj).attr("defaultValue",obj.defaultValue);
			$(currObj).attr("fontSize",obj.fontSize);
			$(currObj).attr("fontColor",obj.fontColor);
			$(currObj).attr("jsFunction",obj.jsFunction);
			$(currObj).attr("dataShow",obj.dataShow);
		    if($(currObj).parents("table").attr("tabletype") == 2){
				
			}else {
				$(currObj).attr("dataType",obj.dataType);
			}
			$(currObj).attr("dataLength",obj.dataLength);
			$(currObj).attr("floatSet",obj.floatSet);
			$(currObj).attr("ctrlField",obj.ctrlField);
			$(currObj).attr("serviceName",obj.serviceName);
			$(currObj).attr("methodName",obj.methodName);
			$(currObj).attr("data",obj.data);
			$(currObj).attr("unEditDesignatedId",obj.unEditDesignatedId);
			$(currObj).attr("unEditIsSaveDb",obj.unEditIsSaveDb);
			$(currObj).attr("designatedId",obj.designatedId);
			$(currObj).attr("designatedType",obj.designatedType);
			$(currObj).attr("isOverride",obj.isOverride);
			$(currObj).attr("isSaveDb",obj.isSaveDb);
			$(currObj).attr("uploadFileType",obj.uploadFileType);
			$(currObj).attr("isGetZhengWen",obj.isGetZhengWen);
			$(currObj).attr("applyTo",obj.applyTo);
			$(currObj).attr("inputMode",obj.inputMode);
			$(currObj).attr("optionDataSource",obj.optionDataSource);
			$(currObj).attr("opts",JSON.stringify(obj.options));
			$(currObj).attr("checkRules",JSON.stringify(obj.checkRules));
			/************ruan*********************/
			$(currObj).attr("fontWidth",obj.fontWidth);
			$(currObj).attr("setUrlOnlyRead",obj.setUrlOnlyRead);
			$(currObj).attr("fontHight",obj.fontHight);
			$(currObj).attr("textAlign",obj.textAlign);
			$(currObj).attr("isHide",obj.isHide);
			$(currObj).attr("defaultValueWay",obj.defaultValueWay);
			$(currObj).attr("inputDataType",obj.inputDataType);
//			if(obj.defaultValueWay==dyFormInputValue.relationDoc){
				$(currObj).attr("relationDataTwoSql",obj.relationDataTwoSql);
				$(currObj).attr("relationDataText",obj.relationDataText);
				$(currObj).attr("relationDataValue",obj.relationDataValue);
				$(currObj).attr("relationDataSql",obj.relationDataSql);
				$(currObj).attr("relationDataShowType",obj.relationDataShowType);
				$(currObj).attr("relationDataShowMethod",obj.relationDataShowMethod);
				
//			}
//			$(currObj).attr("relationDataTypeTwo",obj.relationDataTypeTwo);
			$(currObj).attr("relationDataTextTwo",obj.relationDataTextTwo);
			$(currObj).attr("relationDataValueTwo",obj.relationDataValueTwo);
//			$(currObj).attr("relationDataUuid",obj.relationDataUuid);
			$(currObj).attr("relationDataDefiantion",obj.relationDataDefiantion);
		}
	}

	//保存属性值
	function saveColAttributes(){
		var obj = new Object();
//		obj.uuid = $("#uuid").val();
		obj.formEleType = $("#formEleType").val();
		obj.formEleId = $("#formEleId").val();
		obj.colEnName = $("#colEnName").val();
		obj.colCnName = $("#colCnName").val();
		obj.fontSize = $("#fontSize").val();
		obj.fontColor = $("#fontColor").val();
		/**ruan start**/
		obj.fontWidth = $("#fontWidth").val();
		obj.setUrlOnlyRead = $("#setUrlOnlyRead").val();
		obj.fontHight = $("#fontHight").val();
		obj.textAlign = $("#textAlign").val();
		if($("#isHide:checked").val()=='2'){
			obj.isHide = elementShow.hidden;
		}else{
			obj.isHide = elementShow.show;
		}
		if($("#dataShow:checked").val()=='2'){
			obj.dataShow = dyFormDataShow.indirect;
		}else{
			obj.dataShow = dyFormDataShow.directShow;
		}
		obj.defaultValueWay = $('input[name=defaultValueWay]:checked').val();
		obj.inputDataType = $('#inputDataType option:selected').val();
		if(obj.defaultValueWay==dyFormInputValue.jsEquation){
			obj.jsFunction = $("#defaultValue").val();
		}
		obj.relationDataText = $("#relationDataText").val();
		obj.relationDataValue = $("#relationDataValue").val();
		obj.relationDataSql = $("#relationDataSql").val();
		obj.relationDataShowType = $("#relationDataShowType").val();
		obj.relationDataShowMethod = $('input[name=relationDataShowMethod]:checked').val();
//		obj.relationDataShowType = $('input[name=relationDataShowType]:checked').val();
//		obj.relationDataTypeTwo = $('input[name=relationDataTypeTwo]:checked').val();
		obj.relationDataTextTwo = $("#relationDataTextTwo").val();
		obj.relationDataValueTwo = $("#relationDataValueTwo").val();
//		obj.relationDataUuid = $("#relationDataUuid").val();
		obj.relationDataTwoSql = $("#relationDataTwoSql").val();
		/**ruan end**/
		obj.defaultValue = $("#defaultValue").val();
		obj.dataType = $("#dataType").val();
		obj.dataLength = $("#dataLength").val();
		obj.floatSet = $("#floatSet").val();
		obj.ctrlField = $("#ctrlField").val();
		obj.serviceName = $("#serviceName").val();
		obj.methodName = $("#methodName").val();
		obj.data = $("#data").val();
		obj.unEditDesignatedId = $("#unEditDesignatedId").val();
		obj.unEditIsSaveDb = $("#unEditIsSaveDb").val();
		obj.designatedId = $("#designatedId").val();
		obj.designatedType = $("#designatedType").val();
		obj.isOverride = $("#isOverride").val();
		obj.isSaveDb = $("#isSaveDb").val();
		obj.uploadFileType = $("#uploadFileType").val();
		obj.isGetZhengWen = $('input[name=isGetZhengWen]:checked').val();
		isZW = $("#isGetZhengWen").val();
		obj.applyTo = $("#applyTo").val();
		obj.inputMode = getInputMode(obj.formEleType,obj.dataType);
		//收集关联数据的字段
		var definitioncontentContent = new Array();
		$(".definitioncontentiteam").each(function(){
			var objTemp = new Object();
			objTemp.sqlTitle = $(this).find("td").eq(0).html();
			objTemp.sqlField = $(this).find("td").eq(1).html();
			objTemp.formTitle = $(this).find("td").eq(2).html();
			objTemp.formField = $(this).find("td").eq(3).html();
			objTemp.search = $(this).find("td").eq(4).html(); 
			definitioncontentContent.push(JSON.stringify(objTemp));
		});
		obj.relationDataDefiantion = definitioncontentContent.join("|");
		
		$(currInputObj).parent().prev().html(obj.colCnName);
		if('radio' == obj.formEleType || 'checkbox' == obj.formEleType || 'select' == obj.formEleType || 'button' == obj.formEleType){
			obj.optionDataSource = $('input[name=optionDataSource]:checked').val();
		}
		else{
			obj.optionDataSource = '';
		}
		var options = new Array();
		if(dyDataSourceType.dataDictionary == obj.optionDataSource){
			var o = {"value":$('#dicCode').val(),"label":dylbl.dicCode};
			options.push(o);
		}
		obj.options = options;
		//设置校验规则
		var checkRules = new Array();
		var ruleOpts = $('input[name=checkRule]:checked');
		if(ruleOpts && ruleOpts.length > 0){
			for(var i=0;i<ruleOpts.length;i++){
				var o = new Object();
				o.value = $(ruleOpts[i]).val();
				o.label = $(ruleOpts[i]).next().html();
				o.rule = '';
				if(dyCheckRule.unique == o.value){
					o.rule = $('#uniqueCheckRule').val();
				}
				checkRules.push(o);
			}
		}
		obj.checkRules = checkRules;
		
		//设置字段展示
//		var dataShows = "";
//		var dataOpts = $('input[name=dataShow]:checked');
//		if(dataOpts && dataOpts.length > 0){
//			for(var i=0;i<dataOpts.length;i++){
//				var o = new Object();
//				o.value = $(dataOpts[i]).val();
//				o.label = $(dataOpts[i]).next().html();
//				o.rule = '';
//				dataShows.push(o);
//			}
//			obj.dataShow = dataShows;
//			alert(dataShows);
//		}
		setFormData(currInputObj,obj);		
		//radio同步
		if("radio" == $("#formEleType").val()){
			var iframeDocObj = $('#moduleDiv');
			var radioArr = $(iframeDocObj).find('input[name=' + obj.colEnName + ']');
			for(var i=0;i<radioArr.length;i++){
				if($(radioArr[i]).attr("name") == obj.colEnName){
						$(radioArr[i]).attr('id',obj.colEnName+'_'+(i+parseInt(1)));
					obj.formEleId = $(radioArr[i]).attr('id');
					obj.id = $(radioArr[i]).attr('id');
					setFormData(radioArr[i],obj);
				}
			}
		}
		//checkbox同步
		if("checkbox" == $("#formEleType").val()){
			var iframeDocObj = $('#moduleDiv');
			var checkboxArr = $(iframeDocObj).find('input[name=' + obj.colEnName + ']');
			for(var i=0;i<checkboxArr.length;i++){
				if($(checkboxArr[i]).attr("name") == obj.colEnName){
						$(checkboxArr[i]).attr('id',obj.colEnName+'_'+(i+parseInt(1)));
					obj.formEleId = $(checkboxArr[i]).attr('id');
					obj.id = $(checkboxArr[i]).attr('id');
					setFormData(checkboxArr[i],obj);
				}
			}
		}
		
		closeAttrCfgWin();
	}

	//根据表单元素类型和数据类型获取输入模式
	function getInputMode(eleType,dataType){
		if('radio' == eleType){
			return dyFormInputMode.radio;
		}
		if('checkbox' == eleType){
			return dyFormInputMode.checkbox;
		}
		if('select' == eleType){
			return dyFormInputMode.selectMutilFase;
		}
		if('button' == eleType){
			return dyFormInputMode.fileUpload;
		}
		if('file' == eleType){
			return dyFormInputMode.fileUpload;
		}
		if('textarea' == eleType){
			if(dyFormInputType.int == dataType){//文本正数
				return dyFormInputMode.int;
			}
			if(dyFormInputType.long == dataType){//文本长正数
				return dyFormInputMode.long;
			}
			if(dyFormInputType.float == dataType){//文本浮点数
				return dyFormInputMode.float;
			}
			if(dyFormInputType.clob == dataType) {//大字段类型
				return dyFormInputMode.clob;
			}
		}
		if('text' == eleType){
			if(dyFormInputType.int == dataType){//文本正数
				return dyFormInputMode.int;
			}
			if(dyFormInputType.long == dataType){//文本长正数
				return dyFormInputMode.long;
			}
			if(dyFormInputType.float == dataType){//文本浮点数
				return dyFormInputMode.float;
			}
			if(dyFormInputType.clob == dataType) {//大字段类型
				return dyFormInputMode.clob;
			}
		}
		return dataType;
	}

	//关闭属性操作窗口
	function closeAttrCfgWin(){
		$("#attrCfgDiv").dialog("close");
	}

	//初使化字段信息
	function initFieldInfo(currTableObj){
		if($(currTableObj).attr("tableId") != undefined && $(currTableObj).attr("tableId") != ''){
			//表
			changeTableSelect($(currTableObj).attr("tableId"));
			changeTableSelect2($(currTableObj).attr("tableId"));
			
			//字段
			var selectArr = $("#fieldCfgTable").find("select[name=fieldSelect]");
			var selectArr2 = $("#fieldCfgTable").find("select[name=isGetTableData]");
			var selectArr3 = $("#fieldCfgTable").find("select[name=subColHidden]");
			var selectArr4 = $("#fieldCfgTable").find("input[name=fieldWidth]");
			var selectArr5 = $("#fieldCfgTable").find("select[name=subColEdit]");
			var thArr = $(currTableObj).find("th");
			for(var i=0;i<thArr.length;i++){
				for(var j=0;j<selectArr[i].options.length;j++){
					if(selectArr[i].options[j].value == $(thArr[i]).attr("columnId")){
						selectArr[i].options[j].selected = true;
						break;
					}
				}
				if(selectArr2[i] != undefined) {
					for(var j=0;j<selectArr2[i].options.length;j++){
						if(selectArr2[i].options[j].value == $(thArr[i]).attr("columnId2")) {
							selectArr2[i].options[j].selected = true;
							break;
						}
					}
				}
				
				for(var j=0;j<selectArr3[i].options.length;j++){
					if(selectArr3[i].options[j].value == $(thArr[i]).attr("subColHidden")) {
						selectArr3[i].options[j].selected = true;
						break;
					}
				}
				
				for(var j=0;j<selectArr5[i].options.length;j++){
					if(selectArr5[i].options[j].value == $(thArr[i]).attr("subColEdit")) {
						selectArr5[i].options[j].selected = true;
						break;
					}
				}
				//设置字段的宽度
				$(selectArr4[i]).val($(thArr[i]).attr("fieldWidth"));
			}
		}
	}

	//关闭表信息设置窗口
	function closeTableInfoCfgDiv(){
		$("#tableInfoCfgDiv").dialog("close");
	}

	//保存表信息
	function saveTableInfoCfg(){
		//保存表配置信息
		$(currTableObj).attr("tableTitle",$("#tableTitle").val());
		$(currTableObj).attr("groupShowTitle",$("#groupShowTitle").val());
		//收集关联数据的字段
		var subDefinitioncontentContent = new Array();
		$(".subDefinitioncontentiteam").each(function(){
			var objTemp = new Object();
			objTemp.zhubiaoTitle = $(this).find("td").eq(0).html();
			objTemp.zhubiaoField = $(this).find("td").eq(1).html();
			objTemp.formField = $(this).find("td").eq(2).html();
			subDefinitioncontentContent.push(JSON.stringify(objTemp));
		});
		$(currTableObj).attr("subrRelationDataDefiantion",subDefinitioncontentContent.join("|"));
		$(currTableObj).attr("tableId",$("#tableDefinitionId").val());
		$(currTableObj).attr("subformApplyTableId",$("#subformApplyTableId").val());
		$(currTableObj).attr("editType",$("input[name=editType]:checked").val());
		$(currTableObj).attr("tableOpen",$("input[name=tableOpen]:checked").val());
		$(currTableObj).attr("isGroupShowTitle",$("input[name=isGroupShowTitle]:checked").val());
		$(currTableObj).attr("hideButtons",$("input[name=hideButtons]:checked").val());
		//保存表字段配置信息
		var thArr = $(currTableObj).find("th");
		var selectArr = $("#fieldCfgTable").find("select[name=fieldSelect]");
		var selectArr2 = $("#fieldCfgTable").find("select[name=isGetTableData]");
		var selectArr3 = $("#fieldCfgTable").find("select[name=subColHidden]");
		var selectArr4 = $("#fieldCfgTable").find("input[name=fieldWidth]");
		var selectArr5 = $("#fieldCfgTable").find("select[name=subColEdit]");
		for(var i=0;i<selectArr.length;i++){
			$(thArr[i]).attr("columnId",selectArr[i].value);
			if(selectArr2.length != 0) {
				$(thArr[i]).attr("columnId2",selectArr2[i].value);
			}else {
				$(thArr[i]).attr("columnId2","");
			}
			$(thArr[i]).attr("fieldOrder", i);
			$(thArr[i]).attr("subColHidden",selectArr3[i].value);
			$(thArr[i]).attr("fieldWidth",selectArr4[i].value);
			$(thArr[i]).attr("subColEdit",selectArr5[i].value);
		}
		closeTableInfoCfgDiv();
	}

	function changeTableSelect(tableId){
		//加载数据
		$.ajaxSettings.async = false;
		$.getJSON(contextPath + "/dytable/field_list.action?tableId=" + tableId,function (data){
			var selectHTML = "<select name=fieldSelect><option value=''>" + dylbl.selectNull + "</option>";
			$.each(data,function (i,item){
				selectHTML += "<option value='" + item.value + "'>" + item.label + "</option>";
			});
			selectHTML += "</select>";
			var tdArr = $("#fieldCfgTable").find("td[name=fieldMapping]");
			for(var i=0;i<tdArr.length;i++){
				$(tdArr[i]).html(selectHTML);
			}
		});
	}
	
	function changeTableSelect2(tableId){
		//加载数据
		$.ajaxSettings.async = false;
		$.getJSON(contextPath + "/dytable/field_list2.action?tableId=" + $("#subformApplyTableId").val(),function (data){
			var selectHTML = "<select name=isGetTableData><option value=''>" + dylbl.selectNull + "</option>";
			$.each(data,function (i,item){
				selectHTML += "<option value='" + item.value + "'>" + item.label + "</option>";
			});
			selectHTML += "</select>";
			var tdArr = $("#fieldCfgTable").find("td[name=aboutOtherTableData]");
			for(var i=0;i<tdArr.length;i++){
				$(tdArr[i]).html(selectHTML);
			}
		});
	}
	
	function changeTableSelect3(tableId){
		//加载数据
		$.ajaxSettings.async = false;
		$.getJSON(contextPath + "/dytable/field_list2.action?tableId=" + tableId,function (data){
			var selectHTML = "<select name=isGetTableData><option value=''>" + dylbl.selectNull + "</option>";
			$.each(data,function (i,item){
				selectHTML += "<option value='" + item.value + "'>" + item.label + "</option>";
			});
			selectHTML += "</select>";
			$("#groupShowTitle").html(selectHTML);
		});
	}
	
	
//收集用户配置信息
function collectFormDatas(){
		var jsonStr = '';
		var uuid = $('#formUuid').val();
		var mainTableEnName = $('#mainTableEnName').val();
		var mainTableCnName = $('#mainTableCnName').val();
		var formSign = $('input[name=formSign]:checked').val();
		var tableNum = $('#tableNum').val();
		var formDisplay = "";
		if($("#formDisplay").val() == "编辑表单定义") {
			 formDisplay = 2;
		}else {
			 formDisplay = 1;
		}
		var moduleId = $('#moduleId').val();
		var moduleName = $('#moduleName').val();
		var isUp = $('#isUp').val();
		var tableId = $('#tableId').val();
		var applyTo = $('#applyTo2').val();
		var printTemplate = $("#getPrintTemplateId").val();
		var printTemplateName = $("#getPrintTemplateName").val();
		var showTableModel = $("#showTableModel").val();
		var showTableModelId = $("#showTableModelId").val();
		var version = $('#version').val();
		var tempHtmlPath = $('#tempHtmlPath').val();
		var htmlPath = $('#htmlPath').val();
		var htmlBodyContent = $('#moduleDiv').html();
		var jsonObject = new Object();
		var tableInfo = new Object();
		tableInfo.id = tableId;
		tableInfo.applyTo = applyTo;
		//htmlBodyContent
		tableInfo.htmlBodyContent = htmlBodyContent;
		tableInfo.printTemplate = printTemplate;
		tableInfo.printTemplateName = printTemplateName;
		tableInfo.showTableModel = showTableModel;
		tableInfo.showTableModelId = showTableModelId;
		tableInfo.uuid = uuid;
		tableInfo.tableName = mainTableEnName;
		tableInfo.tableDesc = mainTableCnName;
		tableInfo.formSign = formSign;
		tableInfo.tableNum = tableNum;
		tableInfo.formDisplay = formDisplay;
		tableInfo.moduleId = moduleId;
		tableInfo.moduleName = moduleName;
		tableInfo.isUp = isUp;
		tableInfo.htmlPath = htmlPath;
		tableInfo.version = version;
		tableInfo.tempHtmlPath = tempHtmlPath;
		tableInfo.isUp = isUp;
		var fields = new Array();
		
//		if(isZW == "3") {
//			var colsObject = new Object();
//			colsObject.uuid = $("#body_col").attr("uuid");
//			colsObject.colEnName = "body_col";
//			colsObject.inputMode = "17";
//			colsObject.optionDataSource = "";
//			fields.push(colsObject);
//			tableInfo.fields = fields;
//			jsonObject.tableInfo = tableInfo;
//		}
		if(formDisplay == "1") {
			var cols = ",";
			var iframeDocObj = $('#moduleDiv');
			var inputArr = $(iframeDocObj).find("input");
			//input
			for(var i=0;i<inputArr.length;i++){
					//供选项(单选或复选)
					var options = new Array();
					if($(inputArr[i]).attr("formeletype") == "radio" || $(inputArr[i]).attr("formeletype") == "checkbox"){
//						options = '';
						if(dyDataSourceType.dataConstant == $(inputArr[i]).attr('optionDataSource')){//常量
							$('#optionsTd_2').html('');
							var optArr = $('input[name=' + $(inputArr[i]).attr("colEnName") + ']');
							for(var j=0;j<optArr.length;j++){
								if(j != 0){
//									options += ',';
								}
								var optionsArr = new Object();
								optionsArr.label = $(optArr[j]).next('label').html();
								optionsArr.value = $(optArr[j]).val();
//								options += '{"label":"' + $(optArr[j]).next('label').html() + '","value":"' + $(optArr[j]).val() + '"}';
								options.push(optionsArr);
							}
						}else{//字典
							options = JSON.parse($(inputArr[i]).attr('opts'));
						}
					}
					var colsObject = new Object();
					colsObject.uuid = $(inputArr[i]).attr("uuid");
					colsObject.colEnName = $(inputArr[i]).attr("colEnName");
					colsObject.colCnName = $(inputArr[i]).attr("colCnName");
					colsObject.defaultValue = $(inputArr[i]).attr("defaultValue");
					colsObject.fontSize = $(inputArr[i]).attr("fontSize");
					colsObject.fontColor = $(inputArr[i]).attr("fontColor");
					colsObject.jsFunction = $(inputArr[i]).attr("jsFunction");
//					colsObject.dataShow = $(inputArr[i]).attr("dataShow");
					if($(inputArr[i]).parents("table").attr("tableType") == "2") {
					}else {
						colsObject.dataType = $(inputArr[i]).attr("dataType");
					}
					if(colsObject.dataType == "16") {
						colsObject.dataLength = "8000000";
					}else {
						colsObject.dataLength = $(inputArr[i]).attr("dataLength");
					}
					colsObject.floatSet = $(inputArr[i]).attr("floatSet");
					colsObject.ctrlField = $(inputArr[i]).attr("ctrlField");
					colsObject.serviceName = $(inputArr[i]).attr("serviceName");
					colsObject.methodName = $(inputArr[i]).attr("methodName");
					colsObject.data = $(inputArr[i]).attr("data");
					colsObject.unEditDesignatedId = $(inputArr[i]).attr("unEditDesignatedId");
					colsObject.unEditIsSaveDb = $(inputArr[i]).attr("unEditIsSaveDb");
					colsObject.designatedId = $(inputArr[i]).attr("designatedId");
					colsObject.designatedType = $(inputArr[i]).attr("designatedType");
					colsObject.isOverride = $(inputArr[i]).attr("isOverride");
					colsObject.isSaveDb = $(inputArr[i]).attr("isSaveDb");
					colsObject.uploadFileType = $(inputArr[i]).attr("uploadFileType");
					colsObject.isGetZhengWen = $(inputArr[i]).attr("isGetZhengWen");
					colsObject.applyTo = $(inputArr[i]).attr("applyTo");
					/**ruan**/
					colsObject.fontWidth = $(inputArr[i]).attr("fontWidth");
					colsObject.setUrlOnlyRead = $(inputArr[i]).attr("setUrlOnlyRead");
					colsObject.fontHight = $(inputArr[i]).attr("fontHight");
					colsObject.textAlign = $(inputArr[i]).attr("textAlign");
					colsObject.isHide = $(inputArr[i]).attr("isHide");
					colsObject.dataShow = $(inputArr[i]).attr("dataShow");
					colsObject.defaultValueWay = $(inputArr[i]).attr("defaultValueWay");
					colsObject.inputDataType = $(inputArr[i]).attr("inputDataType");
					colsObject.relationDataTwoSql = $(inputArr[i]).attr("relationDataTwoSql");
					colsObject.relationDataText = $(inputArr[i]).attr("relationDataText");
					colsObject.relationDataValue = $(inputArr[i]).attr("relationDataValue");
					colsObject.relationDataSql = $(inputArr[i]).attr("relationDataSql");
					colsObject.relationDataShowType = $(inputArr[i]).attr("relationDataShowType");
					colsObject.relationDataShowMethod = $(inputArr[i]).attr("relationDataShowMethod");
//					colsObject.relationDataTypeTwo = $(inputArr[i]).attr("relationDataTypeTwo");
					colsObject.relationDataTextTwo = $(inputArr[i]).attr("relationDataTextTwo");
					colsObject.relationDataValueTwo = $(inputArr[i]).attr("relationDataValueTwo");
//					colsObject.relationDataUuid = $(inputArr[i]).attr("relationDataUuid");
					colsObject.relationDataDefiantion = $(inputArr[i]).attr("relationDataDefiantion");
					/**ruan**/
					cols += $(inputArr[i]).attr("colEnName") + ",";
					if($(inputArr[i]).attr("formeletype") == "radio"){
						colsObject.inputMode = dyFormInputMode.radio;
					}else if($(inputArr[i]).attr("formeletype") == "checkbox") {
						colsObject.inputMode = dyFormInputMode.checkbox;
					}else if($(inputArr[i]).attr("formeletype") == "button"){
						colsObject.inputMode = dyFormInputMode.textBody;
					}else if($(inputArr[i]).attr("formeletype") == "file"){
						colsObject.inputMode = dyFormInputMode.fileUpload;
					}
					else {
						colsObject.inputMode = colsObject.inputDataType;
//						colsObject.inputMode = $(inputArr[i]).attr("inputMode");
					}
					colsObject.optionDataSource = $(inputArr[i]).attr("optionDataSource")== undefined ? '' : $(inputArr[i]).attr("optionDataSource");
					colsObject.options = options;
					var checkRules = new Array();
					checkRules = JSON.parse($(inputArr[i]).attr("checkRules")== undefined ? '[]' : $(inputArr[i]).attr("checkRules"));
					colsObject.checkRules = checkRules;
					fields.push(colsObject);
				tableInfo.fields = fields;
				jsonObject.tableInfo = tableInfo;
			}
			//textarea
			var textareaArr = $(iframeDocObj).find("textarea");
			for(var i=0;i<textareaArr.length;i++){
				if(cols.indexOf(","+$(textareaArr[i]).attr("colEnName")+",") < 0){
					if(cols.length > 1){
						jsonStr += ',';
					}
					cols += $(textareaArr[i]).attr("colEnName") + ",";
					var rs = $(textareaArr[i]).attr("rows") == undefined ? '2' : $(textareaArr[i]).attr("rows");
					var cs = $(textareaArr[i]).attr("cols") == undefined ? '50' : $(textareaArr[i]).attr("cols");
					var colsObject = new Object();
					colsObject.uuid = $(textareaArr[i]).attr("uuid");
					colsObject.colEnName = $(textareaArr[i]).attr("colEnName");
					colsObject.colCnName = $(textareaArr[i]).attr("colCnName");
					colsObject.defaultValue = $(textareaArr[i]).attr("defaultValue");
					colsObject.fontSize = $(textareaArr[i]).attr("fontSize");
					colsObject.fontColor = $(textareaArr[i]).attr("fontColor");
					colsObject.jsFunction = $(textareaArr[i]).attr("jsFunction");
//					colsObject.dataShow = $(textareaArr[i]).attr("dataShow");
					colsObject.dataType = $(textareaArr[i]).attr("dataType");
					if(colsObject.dataType == "16") {
						colsObject.dataLength = "8000000";
					}else {
						colsObject.dataLength = $(textareaArr[i]).attr("dataLength");
					}
					colsObject.floatSet = $(textareaArr[i]).attr("floatSet");
					colsObject.ctrlField = $(textareaArr[i]).attr("ctrlField");
					colsObject.serviceName = $(textareaArr[i]).attr("serviceName");
					colsObject.methodName = $(textareaArr[i]).attr("methodName");
					colsObject.data = $(textareaArr[i]).attr("data");
					colsObject.unEditDesignatedId = $(textareaArr[i]).attr("unEditDesignatedId");
					colsObject.unEditIsSaveDb = $(textareaArr[i]).attr("unEditIsSaveDb");
					colsObject.designatedId = $(textareaArr[i]).attr("designatedId");
					colsObject.designatedType = $(textareaArr[i]).attr("designatedType");
					colsObject.isOverride = $(textareaArr[i]).attr("isOverride");
					colsObject.isSaveDb = $(textareaArr[i]).attr("isSaveDb");
					colsObject.uploadFileType = $(textareaArr[i]).attr("uploadFileType");
					colsObject.isGetZhengWen = $(textareaArr[i]).attr("isGetZhengWen");
					colsObject.applyTo = $(textareaArr[i]).attr("applyTo");
//					colsObject.inputMode = $(textareaArr[i]).attr("inputMode");
					/**ruan**/
					colsObject.fontWidth = $(textareaArr[i]).attr("fontWidth");
					colsObject.setUrlOnlyRead = $(textareaArr[i]).attr("setUrlOnlyRead");
					colsObject.fontHight = $(textareaArr[i]).attr("fontHight");
					colsObject.textAlign = $(textareaArr[i]).attr("textAlign");
					colsObject.isHide = $(textareaArr[i]).attr("isHide");
					colsObject.dataShow = $(textareaArr[i]).attr("dataShow");
					colsObject.defaultValueWay = $(textareaArr[i]).attr("defaultValueWay");
					colsObject.inputDataType = $(textareaArr[i]).attr("inputDataType");
					colsObject.relationDataTwoSql = $(textareaArr[i]).attr("relationDataTwoSql");
					colsObject.relationDataText = $(textareaArr[i]).attr("relationDataText");
					colsObject.relationDataValue = $(textareaArr[i]).attr("relationDataValue");
					colsObject.relationDataSql = $(textareaArr[i]).attr("relationDataSql");
					colsObject.relationDataShowType = $(textareaArr[i]).attr("relationDataShowType");
					colsObject.relationDataShowMethod = $(textareaArr[i]).attr("relationDataShowMethod");
//					colsObject.relationDataTypeTwo = $(textareaArr[i]).attr("relationDataTypeTwo");
					colsObject.relationDataTextTwo = $(textareaArr[i]).attr("relationDataTextTwo");
					colsObject.relationDataValueTwo = $(textareaArr[i]).attr("relationDataValueTwo");
//					colsObject.relationDataUuid = $(textareaArr[i]).attr("relationDataUuid");
					colsObject.relationDataDefiantion = $(textareaArr[i]).attr("relationDataDefiantion");
					/**ruan**/
					colsObject.inputMode = colsObject.inputDataType;
					colsObject.optionDataSource = "";
					colsObject.options = [];
					colsObject.rows = rs;
					colsObject.cols = cs;
					var checkRules = new Array();
					checkRules = JSON.parse($(textareaArr[i]).attr('checkRules') == undefined ? '[]' : $(textareaArr[i]).attr('checkRules'));
					colsObject.checkRules = checkRules;
					
					fields.push(colsObject);
				}
				tableInfo.fields = fields;
				jsonObject.tableInfo = tableInfo;
			}
			
			//button
			var buttonArr = $(iframeDocObj).find("button");
			for(var i=0;i<buttonArr.length;i++){
				if(cols.indexOf(","+$(buttonArr[i]).attr("colEnName")+",") < 0){
					if(cols.length > 1){
						jsonStr += ',';
					}
					cols += $(buttonArr[i]).attr("colEnName") + ",";
					
					var colsObject = new Object();
					colsObject.uuid = $(buttonArr[i]).attr("uuid");
					colsObject.colEnName = $(buttonArr[i]).attr("colEnName");
					colsObject.colCnName = $(buttonArr[i]).attr("colCnName");
					colsObject.defaultValue = $(buttonArr[i]).attr("defaultValue");
					colsObject.fontSize = $(buttonArr[i]).attr("fontSize");
					colsObject.fontColor = $(buttonArr[i]).attr("fontColor");
					colsObject.jsFunction = $(buttonArr[i]).attr("jsFunction");
//					colsObject.dataShow = $(buttonArr[i]).attr("dataShow");
					colsObject.dataType = $(buttonArr[i]).attr("dataType");
					if(colsObject.dataType == "16") {
						colsObject.dataLength = "8000000";
					}else {
						colsObject.dataLength = $(buttonArr[i]).attr("dataLength");
					}
					colsObject.floatSet = $(buttonArr[i]).attr("floatSet");
					colsObject.ctrlField = $(buttonArr[i]).attr("ctrlField");
					colsObject.serviceName = $(buttonArr[i]).attr("serviceName");
					colsObject.methodName = $(buttonArr[i]).attr("methodName");
					colsObject.data = $(buttonArr[i]).attr("data");
					colsObject.unEditDesignatedId = $(buttonArr[i]).attr("unEditDesignatedId");
					colsObject.unEditIsSaveDb = $(buttonArr[i]).attr("unEditIsSaveDb");
					colsObject.designatedId = $(buttonArr[i]).attr("designatedId");
					colsObject.designatedType = $(buttonArr[i]).attr("designatedType");
					colsObject.isOverride = $(buttonArr[i]).attr("isOverride");
					colsObject.isSaveDb = $(buttonArr[i]).attr("isSaveDb");
					colsObject.uploadFileType = $(buttonArr[i]).attr("uploadFileType");
					colsObject.isGetZhengWen = $(buttonArr[i]).attr("isGetZhengWen");
//					colsObject.isGetZhengWen = $('input[name="isGetZhengWen"]:checked').val();
					colsObject.applyTo = $(buttonArr[i]).attr("applyTo");
					colsObject.inputMode = $(buttonArr[i]).attr("inputMode");
					/**ruan**/
					colsObject.fontWidth = $(buttonArr[i]).attr("fontWidth");
					colsObject.setUrlOnlyRead = $(buttonArr[i]).attr("setUrlOnlyRead");
					colsObject.fontHight = $(buttonArr[i]).attr("fontHight");
					colsObject.textAlign = $(buttonArr[i]).attr("textAlign");
					colsObject.isHide = $(buttonArr[i]).attr("isHide");
					colsObject.dataShow = $(buttonArr[i]).attr("dataShow");
					colsObject.defaultValueWay = $(buttonArr[i]).attr("defaultValueWay");
					colsObject.inputDataType = $(buttonArr[i]).attr("inputDataType");
					colsObject.relationDataTwoSql = $(buttonArr[i]).attr("relationDataTwoSql");
					colsObject.relationDataText = $(buttonArr[i]).attr("relationDataText");
					colsObject.relationDataValue = $(buttonArr[i]).attr("relationDataValue");
					colsObject.relationDataSql = $(buttonArr[i]).attr("relationDataSql");
					colsObject.relationDataShowType = $(buttonArr[i]).attr("relationDataShowType");
					colsObject.relationDataShowMethod = $(buttonArr[i]).attr("relationDataShowMethod");
//					colsObject.relationDataTypeTwo = $(buttonArr[i]).attr("relationDataTypeTwo");
					colsObject.relationDataTextTwo = $(buttonArr[i]).attr("relationDataTextTwo");
					colsObject.relationDataValueTwo = $(buttonArr[i]).attr("relationDataValueTwo");
//					colsObject.relationDataUuid = $(buttonArr[i]).attr("relationDataUuid");
					colsObject.relationDataDefiantion = $(buttonArr[i]).attr("relationDataDefiantion");
					/**ruan**/
					colsObject.optionDataSource = "";
					colsObject.options = [];
					var checkRules = new Array();
					checkRules = JSON.parse($(buttonArr[i]).attr('checkRules') == undefined ? '[]' : $(buttonArr[i]).attr('checkRules'));
					colsObject.checkRules = checkRules;
					fields.push(colsObject);
				}
				tableInfo.fields = fields;
				jsonObject.tableInfo = tableInfo;
			}
			//select
			var selectArr = $(iframeDocObj).find("select");
			for(var i=0;i<selectArr.length;i++){
				if(cols.indexOf(","+$(selectArr[i]).attr("colEnName")+",") < 0){
					if(cols.length > 1){
					}
					//供选项
					var options = new Array();
					var a = $(selectArr[i]).attr('optionDataSource');
					if(dyDataSourceType.dataConstant == $(selectArr[i]).attr('optionDataSource')){//常量
						var optArr = $(selectArr[i]).find('option');
						for(var j=0;j<optArr.length;j++){
							if(j != 0){
							}
							var optionsArr = new Object();
							optionsArr.label = optArr[j].text;
							optionsArr.value = optArr[j].value;
							options.push(optionsArr);
//							options += '{"label":"' + optArr[j].text + '","value":"' + optArr[j].value + '"}';
						}
//						options = '[' + options + ']';
					}else{//字典
						options = JSON.parse($(selectArr[i]).attr('opts'));
					}
					
					cols += $(selectArr[i]).attr("colEnName") + ",";
					
					var colsObject = new Object();
					colsObject.uuid = $(selectArr[i]).attr("uuid");
					colsObject.colEnName = $(selectArr[i]).attr("colEnName");
					colsObject.colCnName = $(selectArr[i]).attr("colCnName");
					colsObject.defaultValue = $(selectArr[i]).attr("defaultValue");
					colsObject.fontSize = $(selectArr[i]).attr("fontSize");
					colsObject.fontColor = $(selectArr[i]).attr("fontColor");
					colsObject.jsFunction = $(selectArr[i]).attr("jsFunction");
//					colsObject.dataShow = $(selectArr[i]).attr("dataShow");
					colsObject.dataType = $(selectArr[i]).attr("dataType");
					if(colsObject.dataType == "16") {
						colsObject.dataLength = "8000000";
					}else {
						colsObject.dataLength = $(selectArr[i]).attr("dataLength");
					}
					colsObject.floatSet = $(selectArr[i]).attr("floatSet");
					colsObject.ctrlField = $(selectArr[i]).attr("ctrlField");
					colsObject.serviceName = $(selectArr[i]).attr("serviceName");
					colsObject.methodName = $(selectArr[i]).attr("methodName");
					colsObject.data = $(selectArr[i]).attr("data");
					colsObject.unEditDesignatedId = $(selectArr[i]).attr("unEditDesignatedId");
					colsObject.unEditIsSaveDb = $(selectArr[i]).attr("unEditIsSaveDb");
					colsObject.designatedId = $(selectArr[i]).attr("designatedId");
					colsObject.designatedType = $(selectArr[i]).attr("designatedType");
					colsObject.isOverride = $(selectArr[i]).attr("isOverride");
					colsObject.isSaveDb = $(selectArr[i]).attr("isSaveDb");
					colsObject.uploadFileType = $(selectArr[i]).attr("uploadFileType");
					colsObject.isGetZhengWen = $(selectArr[i]).attr("isGetZhengWen");
//					colsObject.isGetZhengWen = $('input[name="isGetZhengWen"]:checked').val();
					colsObject.applyTo = $(selectArr[i]).attr("applyTo");
					/**ruan**/
					colsObject.fontWidth = $(selectArr[i]).attr("fontWidth");
					colsObject.setUrlOnlyRead = $(selectArr[i]).attr("setUrlOnlyRead");
					colsObject.fontHight = $(selectArr[i]).attr("fontHight");
					colsObject.textAlign = $(selectArr[i]).attr("textAlign");
					colsObject.isHide = $(selectArr[i]).attr("isHide");
					colsObject.dataShow = $(selectArr[i]).attr("dataShow");
					colsObject.defaultValueWay = $(selectArr[i]).attr("defaultValueWay");
					colsObject.inputDataType = $(selectArr[i]).attr("inputDataType");
					colsObject.relationDataTwoSql = $(selectArr[i]).attr("relationDataTwoSql");
					colsObject.relationDataText = $(selectArr[i]).attr("relationDataText");
					colsObject.relationDataValue = $(selectArr[i]).attr("relationDataValue");
					colsObject.relationDataSql = $(selectArr[i]).attr("relationDataSql");
					colsObject.relationDataShowType = $(selectArr[i]).attr("relationDataShowType");
					colsObject.relationDataShowMethod = $(selectArr[i]).attr("relationDataShowMethod");
//					colsObject.relationDataTypeTwo = $(selectArr[i]).attr("relationDataTypeTwo");
					colsObject.relationDataTextTwo = $(selectArr[i]).attr("relationDataTextTwo");
					colsObject.relationDataValueTwo = $(selectArr[i]).attr("relationDataValueTwo");
//					colsObject.relationDataUuid = $(selectArr[i]).attr("relationDataUuid");
					colsObject.relationDataDefiantion = $(selectArr[i]).attr("relationDataDefiantion");
					/**ruan**/
					colsObject.inputMode = $(selectArr[i]).attr("inputMode");
					colsObject.optionDataSource = $(selectArr[i]).attr("optionDataSource");
					colsObject.options = options;
					var checkRules = new Array();
					checkRules = JSON.parse($(selectArr[i]).attr('checkRules') == undefined ? '[]' : $(selectArr[i]).attr('checkRules'));
					colsObject.checkRules = checkRules;
					fields.push(colsObject);
				}
				tableInfo.fields = fields;
				jsonObject.tableInfo = tableInfo;
			}
		}else {
			var cols = ",";
			var iframeDocObj = $('#moduleDiv');
			var inputArr = $(iframeDocObj).find("input");
			
			//input
			for(var i=0;i<inputArr.length;i++){
				if(cols.indexOf(',' + $(inputArr[i]).attr("colEnName") + ',') < 0){
					if(cols.length > 1){
						jsonStr += ',';
					}
					//供选项(单选或复选)
					var options = new Array();
					if($(inputArr[i]).attr("formeletype") == "radio" || $(inputArr[i]).attr("formeletype") == "checkbox"){
//						options = '';
						if(dyDataSourceType.dataConstant == $(inputArr[i]).attr('optionDataSource')){//常量
							$('#optionsTd_2').html('');
							var optArr = $('input[name=' + $(inputArr[i]).attr("colEnName") + ']');
							for(var j=0;j<optArr.length;j++){
								if(j != 0){
//									options += ',';
								}
								var optionsArr = new Object();
								optionsArr.label = $(optArr[j]).next('label').html();
								optionsArr.value = $(optArr[j]).val();
//								options += '{"label":"' + $(optArr[j]).next('label').html() + '","value":"' + $(optArr[j]).val() + '"}';
								options.push(optionsArr);
							}
						}else{//字典
							options = JSON.parse($(inputArr[i]).attr('opts'));
						}
					}
					var colsObject = new Object();
					colsObject.uuid = $(inputArr[i]).attr("uuid");
					colsObject.colEnName = $(inputArr[i]).attr("colEnName");
					colsObject.colCnName = $(inputArr[i]).attr("colCnName");
					colsObject.defaultValue = $(inputArr[i]).attr("defaultValue");
					colsObject.fontSize = $(inputArr[i]).attr("fontSize");
					colsObject.fontColor = $(inputArr[i]).attr("fontColor");
					colsObject.jsFunction = $(inputArr[i]).attr("jsFunction");
					colsObject.dataShow = $(inputArr[i]).attr("dataShow");
					if($(inputArr[i]).parents("table").attr("tableType") == "2") {
					}else {
						colsObject.dataType = $(inputArr[i]).attr("dataType");
					}
					if(colsObject.dataType == "16") {
						colsObject.dataLength = "8000000";
					}else {
						colsObject.dataLength = $(inputArr[i]).attr("dataLength");
					}
					colsObject.floatSet = $(inputArr[i]).attr("floatSet");
					colsObject.ctrlField = $(inputArr[i]).attr("ctrlField");
					colsObject.serviceName = $(inputArr[i]).attr("serviceName");
					colsObject.methodName = $(inputArr[i]).attr("methodName");
					colsObject.data = $(inputArr[i]).attr("data");
					colsObject.unEditDesignatedId = $(inputArr[i]).attr("unEditDesignatedId");
					colsObject.unEditIsSaveDb = $(inputArr[i]).attr("unEditIsSaveDb");
					colsObject.designatedId = $(inputArr[i]).attr("designatedId");
					colsObject.designatedType = $(inputArr[i]).attr("designatedType");
					colsObject.isOverride = $(inputArr[i]).attr("isOverride");
					colsObject.isSaveDb = $(inputArr[i]).attr("isSaveDb");
					colsObject.uploadFileType = $(inputArr[i]).attr("uploadFileType");
					colsObject.isGetZhengWen = $(inputArr[i]).attr("isGetZhengWen");
//					colsObject.isGetZhengWen = $('input[name="isGetZhengWen"]:checked').val();
					colsObject.applyTo = $(inputArr[i]).attr("applyTo");
					/**ruan**/
					colsObject.fontWidth = $(inputArr[i]).attr("fontWidth");
					colsObject.setUrlOnlyRead = $(inputArr[i]).attr("setUrlOnlyRead");
					colsObject.fontHight = $(inputArr[i]).attr("fontHight");
					colsObject.textAlign = $(inputArr[i]).attr("textAlign");
					colsObject.isHide = $(inputArr[i]).attr("isHide");
					colsObject.defaultValueWay = $(inputArr[i]).attr("defaultValueWay");
					colsObject.inputDataType = $(inputArr[i]).attr("inputDataType");
					colsObject.relationDataTwoSql = $(inputArr[i]).attr("relationDataTwoSql");
					colsObject.relationDataText = $(inputArr[i]).attr("relationDataText");
					colsObject.relationDataValue = $(inputArr[i]).attr("relationDataValue");
					colsObject.relationDataSql = $(inputArr[i]).attr("relationDataSql");
					colsObject.relationDataShowType = $(inputArr[i]).attr("relationDataShowType");
					colsObject.relationDataShowMethod = $(inputArr[i]).attr("relationDataShowMethod");
//					colsObject.relationDataTypeTwo = $(inputArr[i]).attr("relationDataTypeTwo");
					colsObject.relationDataTextTwo = $(inputArr[i]).attr("relationDataTextTwo");
					colsObject.relationDataValueTwo = $(inputArr[i]).attr("relationDataValueTwo");
//					colsObject.relationDataUuid = $(inputArr[i]).attr("relationDataUuid");
					colsObject.relationDataDefiantion = $(inputArr[i]).attr("relationDataDefiantion");
					/**ruan**/
					cols += $(inputArr[i]).attr("colEnName") + ",";
					if($(inputArr[i]).attr("formeletype") == "radio"){
						colsObject.inputMode = dyFormInputMode.radio;
//						jsonStr += ',"inputMode":"' + dyFormInputMode.radio + '"';
					}else if($(inputArr[i]).attr("formeletype") == "checkbox") {
						colsObject.inputMode = dyFormInputMode.checkbox;
//						jsonStr += ',"inputMode":"' + dyFormInputMode.checkbox + '"';
					}else if($(inputArr[i]).attr("formeletype") == "button"){
						colsObject.inputMode = dyFormInputMode.textBody;
					}else if($(inputArr[i]).attr("formeletype") == "file"){
						colsObject.inputMode = dyFormInputMode.fileUpload;
					}
					else {
						colsObject.inputMode = colsObject.inputDataType;
//						colsObject.inputMode = $(inputArr[i]).attr("inputMode");
					}
					colsObject.optionDataSource = $(inputArr[i]).attr("optionDataSource")== undefined ? '' : $(inputArr[i]).attr("optionDataSource");
					colsObject.options = options;
					var checkRules = new Array();
					checkRules = JSON.parse($(inputArr[i]).attr("checkRules")== undefined ? '[]' : $(inputArr[i]).attr("checkRules"));
					colsObject.checkRules = checkRules;
					fields.push(colsObject);
				}
				tableInfo.fields = fields;
				jsonObject.tableInfo = tableInfo;
			}
			//textarea
			var textareaArr = $(iframeDocObj).find("textarea");
			for(var i=0;i<textareaArr.length;i++){
				if(cols.indexOf(","+$(textareaArr[i]).attr("colEnName")+",") < 0){
					if(cols.length > 1){
						jsonStr += ',';
					}
					cols += $(textareaArr[i]).attr("colEnName") + ",";
					var rs = $(textareaArr[i]).attr("rows") == undefined ? '2' : $(textareaArr[i]).attr("rows");
					var cs = $(textareaArr[i]).attr("cols") == undefined ? '50' : $(textareaArr[i]).attr("cols");
					var colsObject = new Object();
					colsObject.uuid = $(textareaArr[i]).attr("uuid");
					colsObject.colEnName = $(textareaArr[i]).attr("colEnName");
					colsObject.colCnName = $(textareaArr[i]).attr("colCnName");
					colsObject.defaultValue = $(textareaArr[i]).attr("defaultValue");
					colsObject.fontSize = $(textareaArr[i]).attr("fontSize");
					colsObject.fontColor = $(textareaArr[i]).attr("fontColor");
					colsObject.jsFunction = $(textareaArr[i]).attr("jsFunction");
					colsObject.dataShow = $(textareaArr[i]).attr("dataShow");
					colsObject.dataType = $(textareaArr[i]).attr("dataType");
					if(colsObject.dataType == "16") {
						colsObject.dataLength = "8000000";
					}else {
						colsObject.dataLength = $(textareaArr[i]).attr("dataLength");
					}
					colsObject.floatSet = $(textareaArr[i]).attr("floatSet");
					colsObject.ctrlField = $(textareaArr[i]).attr("ctrlField");
					colsObject.serviceName = $(textareaArr[i]).attr("serviceName");
					colsObject.methodName = $(textareaArr[i]).attr("methodName");
					colsObject.data = $(textareaArr[i]).attr("data");
					colsObject.unEditDesignatedId = $(textareaArr[i]).attr("unEditDesignatedId");
					colsObject.unEditIsSaveDb = $(textareaArr[i]).attr("unEditIsSaveDb");
					colsObject.designatedId = $(textareaArr[i]).attr("designatedId");
					colsObject.designatedType = $(textareaArr[i]).attr("designatedType");
					colsObject.isOverride = $(textareaArr[i]).attr("isOverride");
					colsObject.isSaveDb = $(textareaArr[i]).attr("isSaveDb");
					colsObject.uploadFileType = $(textareaArr[i]).attr("uploadFileType");
					colsObject.isGetZhengWen = $(textareaArr[i]).attr("isGetZhengWen");
//					colsObject.isGetZhengWen = $('input[name="isGetZhengWen"]:checked').val();
					colsObject.applyTo = $(textareaArr[i]).attr("applyTo");
					colsObject.inputMode = $(textareaArr[i]).attr("inputMode");
					/**ruan**/
					colsObject.fontWidth = $(textareaArr[i]).attr("fontWidth");
					colsObject.setUrlOnlyRead = $(textareaArr[i]).attr("setUrlOnlyRead");
					colsObject.fontHight = $(textareaArr[i]).attr("fontHight");
					colsObject.textAlign = $(textareaArr[i]).attr("textAlign");
					colsObject.isHide = $(textareaArr[i]).attr("isHide");
					colsObject.defaultValueWay = $(textareaArr[i]).attr("defaultValueWay");
					colsObject.inputDataType = $(textareaArr[i]).attr("inputDataType");
					colsObject.relationDataTwoSql = $(textareaArr[i]).attr("relationDataTwoSql");
					colsObject.relationDataText = $(textareaArr[i]).attr("relationDataText");
					colsObject.relationDataValue = $(textareaArr[i]).attr("relationDataValue");
					colsObject.relationDataSql = $(textareaArr[i]).attr("relationDataSql");
					colsObject.relationDataShowType = $(textareaArr[i]).attr("relationDataShowType");
					colsObject.relationDataShowMethod = $(textareaArr[i]).attr("relationDataShowMethod");
//					colsObject.relationDataTypeTwo = $(textareaArr[i]).attr("relationDataTypeTwo");
					colsObject.relationDataTextTwo = $(textareaArr[i]).attr("relationDataTextTwo");
					colsObject.relationDataValueTwo = $(textareaArr[i]).attr("relationDataValueTwo");
//					colsObject.relationDataUuid = $(textareaArr[i]).attr("relationDataUuid");
					colsObject.relationDataDefiantion = $(textareaArr[i]).attr("relationDataDefiantion");
					/**ruan**/
					colsObject.inputMode = colsObject.inputDataType;
					colsObject.optionDataSource = "";
					colsObject.options = [];
					colsObject.rows = rs;
					colsObject.cols = cs;
					var checkRules = new Array();
					checkRules = JSON.parse($(textareaArr[i]).attr('checkRules') == undefined ? '[]' : $(textareaArr[i]).attr('checkRules'));
					colsObject.checkRules = checkRules;
					
					fields.push(colsObject);
				}
				tableInfo.fields = fields;
				jsonObject.tableInfo = tableInfo;
			}
			
			//button
			var buttonArr = $(iframeDocObj).find("button");
			for(var i=0;i<buttonArr.length;i++){
				if(cols.indexOf(","+$(buttonArr[i]).attr("colEnName")+",") < 0){
					if(cols.length > 1){
						jsonStr += ',';
					}
					cols += $(buttonArr[i]).attr("colEnName") + ",";
					
					var colsObject = new Object();
					colsObject.uuid = $(buttonArr[i]).attr("uuid");
					colsObject.colEnName = $(buttonArr[i]).attr("colEnName");
					colsObject.colCnName = $(buttonArr[i]).attr("colCnName");
					colsObject.defaultValue = $(buttonArr[i]).attr("defaultValue");
					colsObject.fontSize = $(buttonArr[i]).attr("fontSize");
					colsObject.fontColor = $(buttonArr[i]).attr("fontColor");
					colsObject.jsFunction = $(buttonArr[i]).attr("jsFunction");
//					colsObject.dataShow = $(buttonArr[i]).attr("dataShow");
					colsObject.dataType = $(buttonArr[i]).attr("dataType");
					if(colsObject.dataType == "16") {
						colsObject.dataLength = "8000000";
					}else {
						colsObject.dataLength = $(buttonArr[i]).attr("dataLength");
					}
					colsObject.floatSet = $(buttonArr[i]).attr("floatSet");
					colsObject.ctrlField = $(buttonArr[i]).attr("ctrlField");
					colsObject.serviceName = $(buttonArr[i]).attr("serviceName");
					colsObject.methodName = $(buttonArr[i]).attr("methodName");
					colsObject.data = $(buttonArr[i]).attr("data");
					colsObject.unEditDesignatedId = $(buttonArr[i]).attr("unEditDesignatedId");
					colsObject.unEditIsSaveDb = $(buttonArr[i]).attr("unEditIsSaveDb");
					colsObject.designatedId = $(buttonArr[i]).attr("designatedId");
					colsObject.designatedType = $(buttonArr[i]).attr("designatedType");
					colsObject.isOverride = $(buttonArr[i]).attr("isOverride");
					colsObject.isSaveDb = $(buttonArr[i]).attr("isSaveDb");
					colsObject.uploadFileType = $(buttonArr[i]).attr("uploadFileType");
					colsObject.isGetZhengWen = $(buttonArr[i]).attr("isGetZhengWen");
//					colsObject.isGetZhengWen = $('input[name="isGetZhengWen"]:checked').val();
					colsObject.applyTo = $(buttonArr[i]).attr("applyTo");
					colsObject.inputMode = $(buttonArr[i]).attr("inputMode");
					/**ruan**/
					colsObject.fontWidth = $(buttonArr[i]).attr("fontWidth");
					colsObject.setUrlOnlyRead = $(buttonArr[i]).attr("setUrlOnlyRead");
					colsObject.fontHight = $(buttonArr[i]).attr("fontHight");
					colsObject.textAlign = $(buttonArr[i]).attr("textAlign");
					colsObject.isHide = $(buttonArr[i]).attr("isHide");
					colsObject.dataShow = $(buttonArr[i]).attr("dataShow");
					colsObject.defaultValueWay = $(buttonArr[i]).attr("defaultValueWay");
					colsObject.inputDataType = $(buttonArr[i]).attr("inputDataType");
					colsObject.relationDataTwoSql = $(buttonArr[i]).attr("relationDataTwoSql");
					colsObject.relationDataText = $(buttonArr[i]).attr("relationDataText");
					colsObject.relationDataValue = $(buttonArr[i]).attr("relationDataValue");
					colsObject.relationDataSql = $(buttonArr[i]).attr("relationDataSql");
					colsObject.relationDataShowType = $(buttonArr[i]).attr("relationDataShowType");
					colsObject.relationDataShowMethod = $(buttonArr[i]).attr("relationDataShowMethod");
//					colsObject.relationDataTypeTwo = $(buttonArr[i]).attr("relationDataTypeTwo");
					colsObject.relationDataTextTwo = $(buttonArr[i]).attr("relationDataTextTwo");
					colsObject.relationDataValueTwo = $(buttonArr[i]).attr("relationDataValueTwo");
//					colsObject.relationDataUuid = $(buttonArr[i]).attr("relationDataUuid");
					colsObject.relationDataDefiantion = $(buttonArr[i]).attr("relationDataDefiantion");
					/**ruan**/
					colsObject.optionDataSource = "";
					colsObject.options = [];
					var checkRules = new Array();
					checkRules = JSON.parse($(buttonArr[i]).attr('checkRules') == undefined ? '[]' : $(buttonArr[i]).attr('checkRules'));
					colsObject.checkRules = checkRules;
					fields.push(colsObject);
				}
				tableInfo.fields = fields;
				jsonObject.tableInfo = tableInfo;
			}
			//select
			var selectArr = $(iframeDocObj).find("select");
			for(var i=0;i<selectArr.length;i++){
				if(cols.indexOf(","+$(selectArr[i]).attr("colEnName")+",") < 0){
					if(cols.length > 1){
					}
					//供选项
					var options = new Array();
					var a = $(selectArr[i]).attr('optionDataSource');
					if(dyDataSourceType.dataConstant == $(selectArr[i]).attr('optionDataSource')){//常量
						var optArr = $(selectArr[i]).find('option');
						for(var j=0;j<optArr.length;j++){
							if(j != 0){
							}
							var optionsArr = new Object();
							optionsArr.label = optArr[j].text;
							optionsArr.value = optArr[j].value;
							options.push(optionsArr);
//							options += '{"label":"' + optArr[j].text + '","value":"' + optArr[j].value + '"}';
						}
//						options = '[' + options + ']';
					}else{//字典
						options = JSON.parse($(selectArr[i]).attr('opts'));
					}
					
					cols += $(selectArr[i]).attr("colEnName") + ",";
					
					var colsObject = new Object();
					colsObject.uuid = $(selectArr[i]).attr("uuid");
					colsObject.colEnName = $(selectArr[i]).attr("colEnName");
					colsObject.colCnName = $(selectArr[i]).attr("colCnName");
					colsObject.defaultValue = $(selectArr[i]).attr("defaultValue");
					colsObject.fontSize = $(selectArr[i]).attr("fontSize");
					colsObject.fontColor = $(selectArr[i]).attr("fontColor");
					colsObject.jsFunction = $(selectArr[i]).attr("jsFunction");
//					colsObject.dataShow = $(selectArr[i]).attr("dataShow");
					colsObject.dataType = $(selectArr[i]).attr("dataType");
					if(colsObject.dataType == "16") {
						colsObject.dataLength = "8000000";
					}else {
						colsObject.dataLength = $(selectArr[i]).attr("dataLength");
					}
					colsObject.floatSet = $(selectArr[i]).attr("floatSet");
					colsObject.ctrlField = $(selectArr[i]).attr("ctrlField");
					colsObject.serviceName = $(selectArr[i]).attr("serviceName");
					colsObject.methodName = $(selectArr[i]).attr("methodName");
					colsObject.data = $(selectArr[i]).attr("data");
					colsObject.unEditDesignatedId = $(selectArr[i]).attr("unEditDesignatedId");
					colsObject.unEditIsSaveDb = $(selectArr[i]).attr("unEditIsSaveDb");
					colsObject.designatedId = $(selectArr[i]).attr("designatedId");
					colsObject.designatedType = $(selectArr[i]).attr("designatedType");
					colsObject.isOverride = $(selectArr[i]).attr("isOverride");
					colsObject.isSaveDb = $(selectArr[i]).attr("isSaveDb");
					colsObject.uploadFileType = $(selectArr[i]).attr("uploadFileType");
					colsObject.isGetZhengWen = $(selectArr[i]).attr("isGetZhengWen");
//					colsObject.isGetZhengWen = $('input[name="isGetZhengWen"]:checked').val();
					colsObject.applyTo = $(selectArr[i]).attr("applyTo");
					colsObject.inputMode = $(selectArr[i]).attr("inputMode");
					/**ruan**/
					colsObject.fontWidth = $(selectArr[i]).attr("fontWidth");
					colsObject.setUrlOnlyRead = $(selectArr[i]).attr("setUrlOnlyRead");
					colsObject.fontHight = $(selectArr[i]).attr("fontHight");
					colsObject.textAlign = $(selectArr[i]).attr("textAlign");
					colsObject.isHide = $(selectArr[i]).attr("isHide");
					colsObject.dataShow = $(selectArr[i]).attr("dataShow");
					colsObject.defaultValueWay = $(selectArr[i]).attr("defaultValueWay");
					colsObject.inputDataType = $(selectArr[i]).attr("inputDataType");
					colsObject.relationDataTwoSql = $(selectArr[i]).attr("relationDataTwoSql");
					colsObject.relationDataText = $(selectArr[i]).attr("relationDataText");
					colsObject.relationDataValue = $(selectArr[i]).attr("relationDataValue");
					colsObject.relationDataSql = $(selectArr[i]).attr("relationDataSql");
					colsObject.relationDataShowType = $(selectArr[i]).attr("relationDataShowType");
					colsObject.relationDataShowMethod = $(selectArr[i]).attr("relationDataShowMethod");
//					colsObject.relationDataTypeTwo = $(selectArr[i]).attr("relationDataTypeTwo");
					colsObject.relationDataTextTwo = $(selectArr[i]).attr("relationDataTextTwo");
					colsObject.relationDataValueTwo = $(selectArr[i]).attr("relationDataValueTwo");
//					colsObject.relationDataUuid = $(selectArr[i]).attr("relationDataUuid");
					colsObject.relationDataDefiantion = $(selectArr[i]).attr("relationDataDefiantion");
					/**ruan**/
					colsObject.inputMode = $(selectArr[i]).attr("inputMode");
					colsObject.optionDataSource = $(selectArr[i]).attr("optionDataSource");
					colsObject.options = options;
					var checkRules = new Array();
					checkRules = JSON.parse($(selectArr[i]).attr('checkRules') == undefined ? '[]' : $(selectArr[i]).attr('checkRules'));
					colsObject.checkRules = checkRules;
					fields.push(colsObject);
				}
				tableInfo.fields = fields;
				jsonObject.tableInfo = tableInfo;
			}
		}
		//从表信息收集
		var subTables = new  Array();
		var firstTable = true;
		var tableArr = $(iframeDocObj).find("table");
		for(var i=0;i<tableArr.length;i++){
			//从表才要进行设置
			if("2" == $(tableArr[i]).attr("tableType")){
				if(!firstTable){
					jsonStr += ',';
				}
				var subTableInfo = new Object();
				subTableInfo.id = $(tableArr[i]).attr("id");
				subTableInfo.tableId = $(tableArr[i]).attr("tableId");
				subTableInfo.tableTitle = $(tableArr[i]).attr("tableTitle");
				subTableInfo.groupShowTitle = $(tableArr[i]).attr("groupShowTitle");
				subTableInfo.subformApplyTableId = $(tableArr[i]).attr("subformApplyTableId");
				subTableInfo.subrRelationDataDefiantion = $(tableArr[i]).attr("subrRelationDataDefiantion");
				subTableInfo.editType = $(tableArr[i]).attr("editType");
				subTableInfo.tableOpen = $(tableArr[i]).attr("tableOpen");
				subTableInfo.isGroupShowTitle = $(tableArr[i]).attr("isGroupShowTitle");
				subTableInfo.hideButtons = $(tableArr[i]).attr("hideButtons");
				//从表的字段映射信息
				var fields = new Array();
				var thArr = $(tableArr[i]).find("th");
				for(var j=0;j<thArr.length;j++){
					var thCol = new Object();
					thCol.thCnName = $(thArr[j]).html();
					thCol.uuid = $(thArr[j]).attr("columnId");
					thCol.fieldOrder = $(thArr[j]).attr("fieldOrder");
					thCol.uuid2 = $(thArr[j]).attr("columnId2");
					thCol.subColHidden = $(thArr[j]).attr("subColHidden");
					thCol.fieldWidth = $(thArr[j]).attr("fieldWidth");
					thCol.subColEdit = $(thArr[j]).attr("subColEdit");
					fields.push(thCol);
				}
				subTableInfo.fields = fields;
				subTables.push(subTableInfo);
				firstTable = false;
			}
		}
		jsonObject.subTables = subTables;
		return jsonObject;
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//保存、更新动态表单
	$("#btn_form_save").click(function(){
		$('#isUp').val('0');
		if(validator.form()){
			saveForm();
		}
	});
	$("#btn_form_save_new").click(function(){
		$('#isUp').val('1');
		if(validator.form()){
			saveForm();
		}
	});
	var saveForm = function(){
		var jsonObject = collectFormDatas();
		var url;
		if("undefined" == $('#formUuid').val()){
			url = contextPath + '/dytable/save_form_by_html.action';
		}else{
			url = contextPath + '/dytable/update_form_by_html.action';
		}
		$.ajax({
			url:url,
			type:"POST",
			data:JSON.stringify(jsonObject),
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
			}
		});
	};	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//表单字段值获得焦点时下拉宏一定选项
	$("#defaultValue").focus(function(){
		if($("input[name=defaultValueWay]:checked").val()==1||
				$("input[name=defaultValueWay]:checked").val()==2||
				$("input[name=defaultValueWay]:checked").val()==3||
				$("input[name=defaultValueWay]:checked").val()==4){
			$(".hongdy").show();
		}
	});
	//字段值为关联文档时
	$(".defaultValueWay").click(function(){
		if($(this).attr("id")=="defaultValueWay7"){
			$(".relationClass").show();
			$("#defaultValue").hide();
		}else{
			$(".relationClass").hide();
			$("#defaultValue").show();
		}
	});
	
//	function treeNodeOnClick(event, treeId, treeNode) {
//		var tableType =  $('input[name=relationDataTypeTwo]:checked').val();
//		if(tableType == "4") {
//			$("#relationDataTextTwo").val(treeNode.data.descname);
//			$("#relationDataValueTwo").val(treeNode.data.name);
//			var formuuid = treeNode.data.uuid;
//			$("#relationDataUuid").val(formuuid);
//			JDS.call({
//				service:"getViewDataService.getFieldByForm",
//				data:[formuuid],
//				success:function(result) {
//					data = result.data;
//					var optionStr = "";
//					for(var i=0;i<data.length;i++) {
//						optionStr += "<option value='"+data[i].fieldName+"'>"+data[i].descname+"</option>";
//					}
//					$("#sqlField").html(optionStr);
//				}
//			});
//		}else if(tableType == "2"){
//			$("#relationDataTextTwo").val(treeNode.data.chineseName);
//			$("#relationDataValueTwo").val(treeNode.data.tableName);
//			var formuuid = treeNode.data.uuid;
//			$("#relationDataUuid").val(formuuid);
//			JDS.call({
//				service:"getViewDataService.getSystemTableColumns",
//				data:[formuuid],
//				success:function(result) {
//					data = result.data;
//					var optionStr = "";
//					for(var i=0;i<data.length;i++) {
//						optionStr += "<option value='"+data[i].columnAliases+"'>"+data[i].chineseName+"</option>";
//					}
//					$("#sqlField").html(optionStr);
//				}
//			});
//		}else if(tableType == "3"){
//			$("#relationDataTextTwo").val(treeNode.name);
//			$("#relationDataValueTwo").val(treeNode.id);
//			var id = treeNode.id;
//			$("#relationDataUuid").val(id);
//			JDS.call({
//				service:"getViewDataService.getViewColumns",
//				data:[id],
//				success:function(result) {
//					data = result.data;
//					var optionStr = "";
//					for(var i=0;i<data.length;i++) {
//						optionStr += "<option value='"+data[i].attributeName+"'>"+data[i].columnName+"</option>";
//					}
//					$("#sqlField").html(optionStr);
//				}
//			});
//		}
//	}
	
	function treeNodeOnClick2(event, treeId, treeNode) {
		if(!treeNode.isParent){
			if(treeNode.name!=$("#relationDataTextTwo").val()){
				$(".definitioncontentiteam").remove();
			}
			$("#relationDataTextTwo").val(treeNode.name);
			$("#relationDataValueTwo").val(treeNode.id);
			JDS.call({
				service:"viewDefinitionService.getColumnDefinitions",
				data:[treeNode.id],
				async : false,
				success:function(result) { 
					data = result.data;
					var optionStr = "";
					for(var i=0;i<data.length;i++) {
						var titleName = data[i].otherName;
						if(titleName==""){
							titleName = data[i].titleName;
						}
						if(data[i].columnAliase == "" || data[i].columnAliase == null || data[i].columnAliase == undefined) {
							optionStr += "<option value='"+data[i].fieldName+"'>"+titleName+"</option>";
						}else {
							optionStr += "<option value='"+data[i].columnAliase+"'>"+titleName+"</option>";
						}
					}
					$("#sqlField").html(optionStr);
				}
			});
		}
	}
	function sqlOption2(relationDataValueTwo){
		if(relationDataValueTwo!="" && relationDataValueTwo!= undefined){
			JDS.call({
				service:"viewDefinitionService.getColumnDefinitions",
				data:[relationDataValueTwo],
				success:function(result) { 
					data = result.data;
					var optionStr = "";
					for(var i=0;i<data.length;i++) {
						var titleName = data[i].otherName;
						if(titleName==""){
							titleName = data[i].titleName;
						}
						if(data[i].columnAliase == "" && data[i].columnAliase == null) {
							optionStr += "<option value='"+data[i].fieldName+"'>"+titleName+"</option>";
						}else {
							optionStr += "<option value='"+data[i].columnAliase+"'>"+titleName+"</option>";
						}
					}
					$("#sqlField").html(optionStr);
				}
			});
		}
	}
	function sqlOption(tableType,formuuid){
		if(tableType == "4") {
			JDS.call({
				service:"getViewDataService.getFieldByForm",
				data:[formuuid],
				success:function(result) {
					data = result.data;
					var optionStr = "";
					for(var i=0;i<data.length;i++) {
						optionStr += "<option value='"+data[i].fieldName+"'>"+data[i].descname+"</option>";
					}
					$("#sqlField").html(optionStr);
				}
			});
		}else if(tableType == "2"){
			JDS.call({
				service:"getViewDataService.getSystemTableColumns",
				data:[formuuid],
				success:function(result) {
					data = result.data;
					var optionStr = "";
					for(var i=0;i<data.length;i++) {
						optionStr += "<option value='"+data[i].columnAliases+"'>"+data[i].chineseName+"</option>";
					}
					$("#sqlField").html(optionStr);
				}
			});
		}else if(tableType == "3"){
			JDS.call({
				service:"getViewDataService.getViewColumns",
				data:[formuuid],
				success:function(result) {
					data = result.data;
					var optionStr = "";
					for(var i=0;i<data.length;i++) {
						optionStr += "<option value='"+data[i].attributeName+"'>"+data[i].columnName+"</option>";
					}
					$("#sqlField").html(optionStr);
				}
			});
		}
	}
	
	var viewSetting = {
			async : {
				otherParam : {
					"serviceName" : "viewDefinitionService",
					"methodName" : "getViewAsTreeAsync",
				}
			},
			check : {
				enable : true,
				chkStyle : "radio"
			},
	};
	$("#relationDataText").comboTree({
		labelField: "relationDataText",
		valueField: "relationDataValue",
		treeSetting : viewSetting,
		width: 220,
		height: 220
	});
	var relationSettingTwo = {
			async : {
				otherParam : {
					"serviceName" : "viewDefinitionService",
					"methodName" : "getViewAsTreeAsync",
				}
			},
			check : {
				enable : false,
			},
			callback : {
				onClick: treeNodeOnClick2,
			}
	};
	$("#relationDataTextTwo").comboTree({
		labelField: "relationDataTextTwo",
		valueField: "relationDataValueTwo",
		treeSetting : relationSettingTwo,
		width: 220,
		height: 220
	});
//	$(".relationDataTypeTwo").live("click",function(){
//		var val = $(this).val();
//		$("#relationDataTextTwo").comboTree("clear");
//		$("#relationDataTextTwo").comboTree("disable");
//		if(val !=0) {
//			relationSettingTwo.async.otherParam.data = val;
//		}
//		$("#relationDataTextTwo").comboTree("enable");
//		$("#relationDataTextTwo").comboTree("setParams", {treeSetting : relationSettingTwo});
//	});
	//关联文档及关联数据选择数据源结束
	
	//选择字段值的具体时的响应动作
	$(".isChose").click(function(){
		$("#defaultValue").val($("#defaultValue").val()+"{"+$(this).html()+"}");
	});
	$(document).mousedown(function(event){
		var temp = $(event.target).attr("class");
		if(temp!="noChose"&&temp!="isChose"&&temp!="hongdy"){
			$(".hongdy").hide();
		}
	});
	//表字段类型设置
	$("#dataType").change(function(){
		createDataTypeDetailElement(this.value,"","","","","","","");
	});
	//表输入样式设置
	$("#inputDataType").change(function(){
		createDataTypeDetailElement1(this.value,"","","","","","","");
	});
	//保存字段配置
	$("#btn_field_ok").click(function(){
		saveColAttributes();
	});
	//取消字段配置
	$("#btn_field_cancel").click(function(){
		closeAttrCfgWin();
	});
	$("#tableDefinitionText").focus(function(){
		showMenu();
	});
	
	$("#subformApplyTableIdText").focus(function(){
		showMenu2();
	});
	//保存表信息
	$("#btn_cfg_ok").click(function(){
		saveTableInfoCfg();
	});
	//关闭表信息设置窗口
	$("#btn_cfg_cancel").click(function(){
		closeTableInfoCfgDiv();
	});
	$("#isUniqueCheck").click(function(){
		isUniqueCheck();
	});
	
	$(".addSubRelationField").live("click",function() {
		var subformColText = $("#subformApplyCol option:selected").text();
		var subformColValue = $("#subformApplyCol option:selected").val();
		var subRelationDataDefiantion = '<tr class="subDefinitioncontentiteam">';
		subRelationDataDefiantion += '<td>'+subformColText+'</td>';
		subRelationDataDefiantion += '<td>'+subformColValue+'</td>';
		subRelationDataDefiantion += '<td>PARENT_ID</td>';
		subRelationDataDefiantion += '<td><button class="subDelField">删除</button></td>';
		subRelationDataDefiantion += '</tr>';
		$(".subDefinitiontrtable").append(subRelationDataDefiantion);
	});
	
	$(".addRelationField").click(function(){
		var sqlFieldText = $("#sqlField option:selected").text();
		var sqlFieldValue = $("#sqlField option:selected").val();
		var formFieldText = $("#formField option:selected").text();
		var formFieldValue = $("#formField option:selected").val();
		var searchCheck = $("#searchCheck ").val();
		if($("#searchCheck:checked").val()=='yes'){
			searchCheck = "yes";
		}else{
			searchCheck = "no";
		}
		var relationDataDefiantion = '<tr class="definitioncontentiteam">';
		relationDataDefiantion += '<td>'+sqlFieldText+'</td>';
		relationDataDefiantion += '<td>'+sqlFieldValue+'</td>';
		relationDataDefiantion += '<td>'+formFieldText+'</td>';
		relationDataDefiantion += '<td  style="display: none;">'+formFieldValue+'</td>';
		relationDataDefiantion += '<td>'+searchCheck+'</td>';
		relationDataDefiantion += '<td><button class="delField">删除</button></td>';
		relationDataDefiantion += '</tr>';
		$(".definitiontrtable").append(relationDataDefiantion);
	});
	$(".subDelField").live("click",function(){
		$(this).parent().parent().remove();
	});
	
	$(".delField").live("click",function(){
		$(this).parent().parent().remove();
	});
})(jQuery);

