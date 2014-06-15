var dytable = dytable || {};
var flag = true;
var flag2 = true;
var flag3 = true;
var saveflag = 1;
var delfile = new Array();
var colName = new Array();
var fileNameListArray = new Array();
var ColEnName = "";
var formSign = "";
var strArray = new Array();
dytable.afterDialogSelect = null;
(function($) {
	var formUuid = null;
	var dataUuid = null;
	var hiddenValue = null;
	var data = null;
	var fileNameList = [];
	// 表单数据数字签名信息
	var signature = {files:[]};
	//缓存对象
	var cache = {
			
	};
	/** ********************************* 表单解析开始 ********************************* */
	$.fn.dytable = function(options){
		
		var args = null;
		if(arguments.length == 2) {
			args = arguments[1];
		}
		if (typeof options == 'string') {
			if(typeof args != null){
				return dytable[options](args);
			}
			return dytable[options]();
		}
		options = $.extend({
			data: {},
			isFile2swf:null,
			setReadOnly:null,
			subDataParams:{},
			btnSubmit: 'save',
			buttons:{},
			beforeSubmit : null,
			editable:true,
			afterDialogSelect:null,
		}, options);
		data = options.data;
//		//表单中的定义的字段全部转换成大写
//		var tbName = data.form.tableInfo.tableName;
//		var subTables = data.form.subTables;
//		for(var index=0;index<subTables.length;index++) {
//			var subTableName = subTables[index].tableName;
//			var subOldDataObj = data.data[subTableName];
//			for(var i =0;i<subOldDataObj.length;i++) {
//				var newSubDataObj = {};
//				for(key in subOldDataObj[i]) {
//					newSubDataObj[key.toUpperCase()] = subOldDataObj[i][key];
//				}
//				data.data[subTableName] = newSubDataObj;
//			}
//		}
//		
//		var newDataObj = {};
//		var oldDataObj = data.data[tbName];
//		for (key in oldDataObj) {
//			newDataObj[key.toUpperCase()] = oldDataObj[key];
//		}
//		data.data[tbName] = newDataObj;
//		
		//是否支持下载，“1”表示支持，“2”表示防止下载，不设置表示默认支持下载
		supportDown = options.supportDown;
		//是否将文件转化为swf
		isFile2swf = options.isFile2swf;
		
		//是否生成文件副本
		WellFileUpload.file2swf = isFile2swf;
	 
		//是否进行数据签名
		enableSignature = options.enableSignature;
		//是否讲所有字段设置只读,true表示设置，false表示不设置
		setReadOnly = options.setReadOnly;
		//从表的数据对象
		subDataParams = options.subDataParams;
		//表单的定义uuid
		formUuid = data.formUuid;
		//表单的数据uuid
		dataUuid = data.dataUuid;
		if(dataUuid == "" || dataUuid == undefined || dataUuid == null){
			dataUuid = WellFileUpload.createFolderID();
		}
		//表单的弹出框回调方法
		dytable.afterDialogSelect = options.afterDialogSelect;
		var btnSubmit = '#' + options.btnSubmit;
		var beforeSubmit = options.beforeSubmit;
		var arr = new Array();
		formDefinitionInfo = data.form;
		var htmlBC = data.form.tableInfo.htmlBodyContent;
		var showTableModelId = data.form.tableInfo.showTableModelId;
		this.html(htmlBC);
		var $this = this;
		//如果表单有配置显示单据的定义,则显示的就是显示单据
		if(setReadOnly == true && showTableModelId != "" && showTableModelId != null) {
			JDS.call({
				async:false,
				service :"formDefinitionService.getFormDefinitionBeanByUUID",
				data : showTableModelId,
				success : function(result) {
					var htmlShow = result.data.htmlBody;
					$this.html("<div id='showHtml'></div>");
					$('#showHtml').html(htmlShow);
				}
			});
		}
		$(".wubinsb").remove();
		//主表表格对象
		var mainTable = $('table[tableType=' + dyTableType.mainTable + ']');
		//主表表格数据
		mainFormData = eval('data.data.' + data.form.tableInfo.tableName);
		mainFormData  = (mainFormData == undefined ? {} : mainFormData);
		//主表表格字段信息
		var mainColArr = data.form.tableInfo.fields;
		//是否启用表单签名
		formSign = data.form.tableInfo.formSign;
		//表单的表名
		var mainTableName = data.form.tableInfo.tableName;
		//初始化表单<即解析表单>
		initForm(mainTable,mainColArr,options.editable,mainFormData,mainTableName);
		//设置了只读跳转url的处理
		setSkipUrl(mainColArr);
		//从表的字段信息
		var tableArr = data.form.subTables;
			for(var i=0;i<tableArr.length;i++){
				var cols = new Array();
				var titleArr = new Array();
				
				//uuid列
				titleArr.push('id');
				titleArr.push('序号');
				var uuidCfg = {name:'id',index:'id',hidden:true};
				var sortCfg = {name:'sort_order',index:'sort_order',width:'25px'};
				cols.push(uuidCfg);
				cols.push(sortCfg);
				//从表是否进行分组展示
				var isGroupShowTitle = tableArr[i].isGroupShowTitle;
				//分组展示的标题名称
				if(isGroupShowTitle == "1") {
					var groupArray = new Array();
					var groupShowTitle = tableArr[i].groupShowTitle;
					groupArray.push(groupShowTitle);
				}else {
					var groupArray = new Array();
				}
				
				//从表字段信息
				var thArr = tableArr[i].fields;
				var unitStr = "";
				var treeStr = "";
				var treeStr2 = "";
				var fileStr = "";
				for(var j=0;j<thArr.length;j++){  
					if(thArr[j].inputDataType==dyFormInputMode.orChoose1||thArr[j].inputDataType==dyFormInputMode.orChoose2||thArr[j].inputDataType==dyFormInputMode.orChoose3){
						unitStr += "," + thArr[j].colEnName;
					}
					if(thArr[j].inputDataType==dyFormInputMode.treeSelect){
						treeStr += "," + thArr[j].colEnName;
						treeStr2 += "," + thArr[j].methodName;
					}
					if(thArr[j].inputMode == dyFormInputMode.accessory3) {
						fileStr += "," + thArr[j].colEnName;
					}
					var isThMapping = true;
					if(thArr[j].thCnName == null || thArr[j].thCnName == ''){
						isThMapping = false;
					}
					
					//从表表头是否有定义或者从表为弹出窗口
					if(isThMapping || dySubFormEdittype.newWin == tableArr[i].editType){
						var colCfg = new Object();
						colCfg.name = thArr[j].colEnName;
						colCfg.index = thArr[j].colEnName;
						colCfg.width = thArr[j].fieldWidth;
						//是否可编辑
						if(thArr[j].subColEdit == dySubFormFieldEdit.notEdit) {
							colCfg.editable = false;
						}else {
							colCfg.editable = true;
						}
						//是否支持点击排序
						colCfg.sortable = false;
						
						if(isThMapping){
							//是否隐藏
							if(thArr[j].subColHidden == dySubFormFieldShow.notShow) {
								titleArr.push(thArr[j].thCnName);
								colCfg.hidden = true;
							}else {
								titleArr.push(thArr[j].thCnName);
							}
						}else{
							titleArr.push(thArr[j].colCnName);
							colCfg.hidden = true;
						}
						
						//校验设置
						var ruleOpt = new Object();
						var rules = thArr[j].checkRules;
						if(!isThMapping){
							ruleOpt.edithidden = true;
						}
						for(var k=0;k<rules.length;k++){
							if(dyCheckRule.notNull == rules[k].value){//非空
								ruleOpt.required = true;
							}else if(dyCheckRule.url == rules[k].value){//url
								ruleOpt.url = true;
							}else if(dyCheckRule.email == rules[k].value){//email
								ruleOpt.email = true;
							}else if(dyCheckRule.idCard == rules[k].value){//身份证
								ruleOpt.custom = true;
								ruleOpt.custom_func = idCardNoCheck;
							}else if(dyCheckRule.unique == rules[k].value){//唯一校验
								
							}
						}
						colCfg.editrules = ruleOpt;
						
						//如果从表字段定义为附件字段,则设置为不可编辑状态
						if(dyFormInputMode.accessory3 == thArr[j].inputMode) {
							colCfg.editable = false;
						}
						else if(dyFormInputMode.radio == thArr[j].inputMode){//字段定义为单选						
							colCfg.edittype = 'custom';
							colCfg.editoptions = {custom_element:radioEl,custom_value:radioVal,data:thArr[j].options};
							colCfg.formatter = function (cellvalue, options, rowObject){
								var arr = options.colModel.editoptions.data;
								$.each(arr,function (i,obj){
									if(obj.value == cellvalue){
										cellvalue = obj.label;
										return false;
									}
								});
								return (cellvalue == undefined ? '' : cellvalue);
							};
							colCfg.unformat = function (cellvalue, options, cell){
								var arr = options.colModel.editoptions.data;
								$.each(arr,function (i,obj){
									if(obj.label == cellvalue){
										cellvalue = obj.value;
										return false;
									}
								});
								return (cellvalue == undefined ? '' : cellvalue);
							}
						}else if(dyFormInputMode.checkbox == thArr[j].inputMode){//字段定义为多选
//							colCfg.edittype = 'custom';
//							colCfg.editoptions = {custom_element:checkboxEl,custom_value:checkboxVal,data:thArr[j].options};
//							colCfg.formatter = function (cellvalue, options, rowObject){
//								var labels = '';
//								var arr = options.colModel.editoptions.data;
//								$.each(arr,function (i,obj){
//									if((',' + cellvalue).indexOf(',' + obj.value + ',') >= 0){
//										labels += obj.label + ',';
//									}
//								});
//								return labels;
//							};
//							colCfg.unformat = function (cellvalue, options, cell){
//								var values = '';
//								var arr = options.colModel.editoptions.data;
//								$.each(arr,function (i,obj){
//									if((',' + cellvalue).indexOf(',' + obj.label + ',') >= 0){
//										values += obj.value + ',';
//									}
//								});
//								return values;
//							};
							//行政审批项目中从表需要定义一个可勾选框
							colCfg.edittype = 'checkbox';
							colCfg.editoptions = {value : "1:0"};
							colCfg.formatter = 'checkbox';
							colCfg.editable = true;
							colCfg.align = "center";
						}else if(dyFormInputMode.selectMutilFase == thArr[j].inputMode){//字段定义为下拉框
							colCfg.edittype = 'select';
							colCfg.formatter = 'select';
							var arr = new Array();
							$.each(thArr[j].options,function (i,obj){
								arr.push(obj.value + ':' + obj.label);
							});
							colCfg.editoptions = {value:arr.join(';')};
						}/*else if('7' == thArr[j].colInfo.inputMode || '8' == thArr[j].colInfo.inputMode){//日期到分，到秒
							editor.type = 'datebox';
						}else if('10' == thArr[j].colInfo.inputMode || '11' == thArr[j].colInfo.inputMode){//时间到分，到秒
							editor.type = 'datebox';
						}*/else if(dyFormInputMode.textArea == thArr[j].inputMode){//文本域
							var r = (thArr[j].rows == null ? 1 : thArr[j].rows);
							var c = (thArr[j].cols == null ? 30 : thArr[j].cols);
							colCfg.edittype = 'textarea';
							colCfg.editoptions = {rows:r,cols:c};
						}
						/*else if('13' == thArr[j].colInfo.inputMode || '14' == thArr[j].colInfo.inputMode){//整数或长整数
							editor.type = 'numberbox';
						}else if('15' == thArr[j].colInfo.inputMode){//浮点数
							opt.precision = 2;
							editor.type = 'numberbox';
						}*/else{
							colCfg.edittype = 'text';
							if(dyFormInputMode.int == thArr[j].inputMode || dyFormInputMode.long == thArr[j].inputMode){//整数或长整数
								ruleOpt.integer = true;
							}
						}
						
						cols.push(colCfg);
					}
				}
				//从表数据对象
				var subData = eval('data.data.' + tableArr[i].tableName);
				if(subData == undefined) {
					subData = [];
				}
				var subFields = tableArr[i].fields;
				//对从表的数据对象做处理(行政审批)
				if(subDataParams.length != 0) {
					for(var index=0;index<subDataParams.length;index++) {
						var fmfileId = subDataParams[index].id;
						JDS.call({
							async:false,
							service : "fileManagerService.getFmFileByUuid",
							data : [fmfileId],
							success : function(result) {
								var formUuid =result.data.dynamicFormId;
								var dataUuid =result.data.dynamicDataId;
								//获得事项的ID值
								var shixiangId = result.data.reservedText1;
								//从表数据来源的表uuid
								var subformApplyTableId = tableArr[i].subformApplyTableId;
								var shixiangName = result.data.title;
								if(subformApplyTableId != null && subformApplyTableId != "") {
									//获得文档中表单的数据
									JDS.call({
										async:false,
										service : "formDataService.getSubFormData",
										data : [subformApplyTableId,dataUuid],
										success : function(result) {
											var subTableData = result.data;
											var colTempArray = new Array();
											for(var c=0;c<subFields.length;c++) {
												var uuid2 = subFields[c].uuid2;
												if(uuid2 != null && uuid2 != "") {
													colTempArray.push(uuid2);
												}
											}
											
											for(var b=0;b<subTableData.length;b++) {
												var subObj = new Object();
												//从表加入事项ID
												subObj["apply_material_shixiangid"] = shixiangId;
												//从表加入事项名称
												subObj["apply_material_shixiangname"] = shixiangName;
												subObj["id"] =  new UUID().id.toLowerCase();
												//序号
												subObj["sort_order"] = subTableData[b].sort_order;
												for(var d=0;d<colTempArray.length;d++) {
													subObj[colTempArray[d]] = subTableData[b][colTempArray[d]];
												}
												subData.push(subObj);
											} 
										}
									});
								}
							}
						});
					}
				}
				if(subData!=undefined&&subData!='undefined'){
					var unitArray = unitStr.split(",");
					var treeArray = treeStr.split(",");
					var treeArray2 = treeStr2.split(",");
					var fileArray = fileStr.split(",");
					for(var j1=0;j1<subData.length;j1++){
						for(var j2=0;j2<unitArray.length;j2++){
							if(unitArray[j2]!=""){
								if(subData[j1][unitArray[j2]] != null) {
									var tname = subData[j1][unitArray[j2]].split(",")[0];
									var tvalue = subData[j1][unitArray[j2]].split(",")[1];
									var b = {"name":tname,"value":tvalue};
									cache[subData[j1].id] = b;
									subData[j1][unitArray[j2]] = tname;
								}
							}
						}
						for(var j3=0;j3<treeArray.length;j3++){
							if(treeArray[j3]!=""){
								var zvalue = subData[j1][treeArray[j3]];
								var zname = "";
								if(treeArray2[j3].split("(")[0]!=""&&treeArray2[j3].split("(")[0]!="null"){
									JDS.call({
										async:false,
										service :  treeArray2[j3].split("(")[0],
										data : ["",zvalue],
										success : function(result) {
											zname = result.data.label;
											subData[j1][treeArray[j3]] = zname;
										}
									});
								}
								var b = {"name":zname,"value":zvalue};
								cache[subData[j1].id+"tree"] = b;
							}
						}
						for(var j4=0;j4<fileArray.length;j4++) {
							if(fileArray[j4] != "") {
								var fileName = subData[j1][fileArray[j4]];
								var fileValue = "";
								fileListId = "";
								fileValue +=  '<div class="upload_div" id="upload_div">'+
								'<span class="btn btn-success fileinput-button2">'+
								'<span class="file_icon" style=" display: block;float: left;"></span>'+
								'<span class="add_icon">添加附件</span>'+
								'<input id='+fileName+' type="file" name="files" multiple class="fileupload_css">'+
								'</span></div>';
								fileValue += '<div id="'+fileListId+'"></div>';
								var b = {"name":fileName,"value":fileValue};
								cache[subData[j1].id] = b;
							}
						}
					}
				}
				var openDiv = "<span id='openDiv'></span>";
				var notopenDiv = "<span id='notOpenDiv'></span>";
				//console.log("从表按钮");
					
				//从表操作按钮是否隐藏 
				if( dySubFormHideButtons.isHide == tableArr[i].hideButtons){
					//从表添加、编辑、删除按钮的id
					var btnAddId = "btn_" + tableArr[i].id + "_" + tableArr[i].editType + "_add";
					var btnEditId = "btn_" + tableArr[i].id + "_" + tableArr[i].editType + "_edit";
					var btnDelId = "btn_" + tableArr[i].id + "_" + tableArr[i].editType + "_del";
					//从表按钮列表外层包的div的id
					var subBtnId = 'subBtn-'+ tableArr[i].id;
					//拼出按钮的div框
					var strbtn = '<div id="'+subBtnId+'" class="subTableCss">'
						+ '<input id="' + btnAddId + '" type="button" value="' + dybtn.add + '" fields="'+tableArr[i].fields+'" editType="' + tableArr[i].editType + '" tableId="' + tableArr[i].id + '"/>'
						+ ('2' == tableArr[i].editType ? '<input id="' + btnEditId + '" type="button" value="' + dybtn.edit + '"  tableId="' + tableArr[i].id + '"/>' : '')
						+ '<input id="' + btnDelId + '" type="button" value="' + dybtn.del + '"  tableId="' + tableArr[i].id + '"/>'
						+ '</div>';
					//设置为只读的处理
					if(setReadOnly == true) {
						if(tableArr[i].tableTitle != null && tableArr[i].tableTitle != "") {
							var titleDiv = "";
							if(dySubFormTableOpen.open == tableArr[i].tableOpen) {
								 titleDiv = "<div id='titleDiv' style='background:#DBDBDB;margin-bottom: 2px;'><span id='title_span_open' style='cursor: pointer;'>"+tableArr[i].tableTitle+"</span>"+openDiv+"</div>";
								 $(titleDiv).insertBefore('#' + tableArr[i].id);
									$("#"+ tableArr[i].id).prev().css("display","");
							}else if(dySubFormTableOpen.notOpen == tableArr[i].tableOpen) {
								 titleDiv = "<div id='titleDiv' style='background:#DBDBDB;margin-bottom: 2px;'><span id='title_span_notopen' style='cursor: pointer;'>"+tableArr[i].tableTitle+"</span>"+notopenDiv+"</div>";
								 $(titleDiv).insertBefore('#' + tableArr[i].id);
								 $("#"+ tableArr[i].id).prev().css("display","");
							}
						}
						
					}else {
						//可编辑的处理
						if(tableArr[i].tableTitle != null && tableArr[i].tableTitle != "") {
							var titleDiv = "";
							if(dySubFormTableOpen.open == tableArr[i].tableOpen) {
								 titleDiv = "<div id='titleDiv' style='background:#DBDBDB;margin-bottom: 2px;'><span id='title_span_open' style='cursor: pointer;'>"+tableArr[i].tableTitle+"</span>"+openDiv+"</div>";
								 $(titleDiv).insertBefore('#' + tableArr[i].id);
							}else if(dySubFormTableOpen.notOpen == tableArr[i].tableOpen) {
								 titleDiv = "<div id='titleDiv' style='background:#DBDBDB;margin-bottom: 2px;'><span id='title_span_notopen' style='cursor: pointer;'>"+tableArr[i].tableTitle+"</span>"+notopenDiv+"</div>";
								 $(titleDiv).insertBefore('#' + tableArr[i].id);
							}
						}
						
							$(strbtn).insertBefore('#' + tableArr[i].id);
								var fields = tableArr[i].fields;
								$("#" + btnAddId).click(
									$.proxy(add, $("#" + btnAddId), fields)
									);
								$("#" + btnEditId).click(function(){mod($(this).attr("tableId"));});
								$("#" + btnDelId).click(function(){del($(this).attr("tableId"));});
							
							
							if(dySubFormTableOpen.notOpen == tableArr[i].tableOpen) {
								$("#"+ tableArr[i].id).prev().css("display","none");
							}else if(dySubFormTableOpen.open == tableArr[i].tableOpen) {
								$("#"+ tableArr[i].id).prev().css("display","");
							}
					}
					//从表添加自定义的按钮
					for(var index = 0;index<options.buttons.length;index++) {
						if(options.buttons[index] != undefined) {
							if(getIdInfo(options.buttons[index].subtableMapping) != null) {
								if(getIdInfo(options.buttons[index].subtableMapping) == tableArr[i].id){
									var btnNewId = "btn_"+index+ "_" + tableArr[i].editType + "_new";
									var strbtnNew = '<input id= "' + btnNewId + '" type="button" value="' + options.buttons[index].text + '" tableId = "' + getIdInfo(options.buttons[index].subtableMapping) +'"/>';
									if(setReadOnly == true) {
										
									}else {
										$("#"+subBtnId).append(strbtnNew);
										$("#" +btnNewId).click(options.buttons[index].method);	
									}
								}
							}
						}
					}
					
				}else if(dySubFormHideButtons.isNotHide == tableArr[i].hideButtons){
					//从表添加自定义的按钮
					for(var index = 0;index<options.buttons.length;index++) {
						var strbtn = '<div id="fsdf_'+i+'" class="subTableCss">'+ '</div>';
						$(strbtn).insertBefore('#' + tableArr[i].id);	
						if(getIdInfo(options.buttons[index].subtableMapping) == tableArr[i].id){
							var btnNewId = "btn_"+index+ "_" + tableArr[i].editType + "_new";
							var strbtnNew = '<input id= "' + btnNewId + '" type="button" value="' + options.buttons[index].text + '" tableId = "' + getIdInfo(options.buttons[index].subtableMapping) +'"/>';
							$("#fsdf_"+i).append(strbtnNew);
							$("#" +btnNewId).click(options.buttons[index].method);	
						}
					}
				}
				$('#' + tableArr[i].id).html('');
				$('#' + tableArr[i].id).attr('colInfos',JSON.stringify(thArr));
					if(dySubFormEdittype.rowEdit == tableArr[i].editType){//行内编辑
						var jqGridId = '#' + tableArr[i].id;
						if(setReadOnly == true) {
							$('#'+tableArr[i].id).jqGrid({
								beforeEditCell: function (rowid, cellname, value, iRow, iCol) {//将从表列表附件列设置为只读
//						             var rec = $(jqGridId).jqGrid('getRowData', rowid);
						             var colInfos = JSON.parse($(this).attr('colInfos'));
						             for(var index=0;index<colInfos.length;index++) {
						            	 var value = colInfos[index];
						            	 if(value.inputMode == dyFormInputMode.accessory3){
						            		 if (cellname == value.colEnName) {
								                 setTimeout(function () {
//								                	 $(jqGridId).jqGrid('setCell',rowid,cellname,'','editable-cell');
								                     $(jqGridId).jqGrid('saveCell', iRow, iCol);
								                     //===>或者设置为只读
								                 }, 1);
								                
						            	 	}
						            	 }
						             	}
						            
						             },
								datatype:'local',
								data:subData,
						        autowidth: true,  
								colNames:titleArr,
								colModel:cols,
								scrollOffset : 0,
								multiselect:true,
								cellEdit:false,
								cellsubmit:'clientArray',
								rownumbers:true,
								shrinkToFit:true,
								scrollOffset : 0,
								height : 'auto',
								grouping:true,
							   	groupingView : {
							   		groupField : groupArray,
							   		groupColumnShow : [false]
							   	},
								afterEditCell:function (id,name,val,iRow,iCol){
									var qid = this;
									var colInfos = JSON.parse($(this).attr('colInfos'));
									//获得所有的运算，并且把列标题替换为列名
									
									for(var iii=0;iii<colInfos.length;iii++) {
										var value = colInfos[iii];
										if(value.dataType == dyFormInputType.date && value.colEnName === name){//日期
											var label = iRow + "_" + name;
											$("#" + iRow + "_" + name).live('click', function() {
												WdatePicker({dateFmt:'yyyy-MM-dd',onpicked:function(){
													$(qid).jqGrid('saveCell',iRow, iCol);}
												});
											});
											return false;
										}
										else if(value.dataType == dyFormInputMode.dateTimeMin && value.colEnName === name){//日期到分
											my97Datetime(iRow+"_" + name,'yyyy-MM-dd HH:mm');
											var label = iRow + "_" + name;
											$("#" + iRow + "_" + name).live('click', function() {
												WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',onpicked:function(){
													$(qid).jqGrid('saveCell',iRow, iCol);}
												});
											});
											return false;
										}
										else if(value.dataType == dyFormInputMode.dateTimeSec && value.colEnName === name){//日期到秒
											my97Datetime(iRow+"_" + name,'yyyy-MM-dd HH:mm:ss');
											var label = iRow + "_" + name;
											$("#" + iRow + "_" + name).live('click', function() {
												WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',onpicked:function(){
													$(qid).jqGrid('saveCell',iRow, iCol);}
												});
											});
											return false;
										}
										else if(value.dataType == dyFormInputMode.timeMin && value.colEnName === name){//日期到秒
											var label = iRow + "_" + name;
											$("#" + iRow + "_" + name).live('click', function() {
												WdatePicker({dateFmt:'HH:mm',onpicked:function(){
													$(qid).jqGrid('saveCell',iRow, iCol);}
												});
											});
											return false;
										}
										else if(value.dataType == dyFormInputMode.timeSec && value.colEnName === name){//日期到秒
											var label = iRow + "_" + name;
											$("#" + iRow + "_" + name).live('click', function() {
												WdatePicker({dateFmt:'HH:mm:ss',onpicked:function(){
													$(qid).jqGrid('saveCell',iRow, iCol);}
												});
											});
											return false;
										}
										else if(value.inputDataType == dyFormInputMode.dialog && value.colEnName === name) {//弹出框
											if( value.relationDataShowType != null){
												 window[value.relationDataShowType](value,qid,id,name,val,iRow,iCol);
											}
										}
										else if(value.inputDataType == dyFormInputMode.treeSelect && value.colEnName === name) {//树形下拉框
											var servicename = value.serviceName.split(".")[0]; //服务名
											var method = value.serviceName.split(".")[1].split("(")[0]; //方法名
											var datas = value.serviceName.split(".")[1].split("(")[1].replace(")",""); //数据
											var data = datas.split(",")[0];
											data = data.replace("\'","").replace("\'","");
											var label = iRow + "_" + name;
											var setting = {
												async : {
													otherParam : {
														"serviceName" : servicename,
														"methodName" : method,
														"data" : data
													}
												},
												check : {//复选框的选择做成可配置项
													enable:false
												},
												callback : {
													onClick:function (event, treeId, treeNode) {
														$("#"+label).val(treeNode.name);
														var b = {"name":treeNode.name,"value":treeNode.data};
														cache[id+"tree"] = b;
														$(qid).jqGrid("saveCell", iRow, iCol);
													}
												}
											};
											$("#"+label).comboTree({
												labelField : label,
												treeSetting : setting,
												width: 220,
												height: 220
											});
											$("#"+label).trigger("click");
										}
										else if(value.inputDataType == dyFormInputMode.orChoose3 && value.colEnName === name) {//组织选择框(人员+部门)
											var label = iRow + "_" + name;
											$.unit.open({
												labelField:iRow + "_" + name,
												initNames: getLabelByValue(id),
												initIDs:getValuesByLabel(id),
												afterSelect:function(data) {
														var tname = "";
														var tvalue = "";
														tname = data.name;
														tvalue = data.id;
														var b = {"name":tname,"value":tvalue};
														if (cache[id] == null){
															cache[id] = b;
														}else {
															cache[id] = b;
														}
														$("#"+label).val(tname);
														$(qid).jqGrid("saveCell", iRow, iCol);
												}, 
												close : function(e) {
													$(qid).jqGrid("saveCell", iRow, iCol);
												}
											});
											return false;
										}
										else if(value.inputDataType == dyFormInputMode.orChoose2 && value.colEnName === name) {//组织选择框(仅选择组织部门)
											var label = iRow + "_" + name;
											$("#"+label).attr("hiddenValue",hiddenValue);
											$.unit.open({
												labelField:iRow + "_" + name,
												initNames: getLabelByValue(id),
												initIDs:getValuesByLabel(id),
													afterSelect:function(data) {
														var tname = "";
														var tvalue = "";
														tname = data.name;
														tvalue = data.id;
														var b = {"name":tname,"value":tvalue};
														if (cache[id] == null){
															cache[id] = b;
														}else {
															cache[id] = b;
														}
														$("#"+label).val(tname);
														$(qid).jqGrid("saveCell", iRow, iCol);
												}, 
												close : function(e) {
													$(qid).jqGrid("saveCell", iRow, iCol);
												},
												type : "Dept",
												selectType : 2,
											});
											return false;
										}
										else if(value.inputDataType == dyFormInputMode.orChoose1 && value.colEnName === name) {//组织选择框(仅选择组织部门)
											var label = iRow + "_" + name;
											$("#"+label).attr("hiddenValue",hiddenValue);
											$.unit.open({
												labelField:iRow + "_" + name,
												initNames: getLabelByValue(id),
												initIDs:getValuesByLabel(id),
													afterSelect:function(data) {
														var tname = "";
														var tvalue = "";
														tname = data.name;
														tvalue = data.id;
														var b = {"name":tname,"value":tvalue};
														if (cache[id] == null){
															cache[id] = b;
														}else {
															cache[id] = b;
														}
														$("#"+label).val(tname);
														$(qid).jqGrid("saveCell", iRow, iCol);
												}, 
												close : function(e) {
													$(qid).jqGrid("saveCell", iRow, iCol);
												},
												selectType : 4,
											});
											return false;
										}
										else if(value.colEnName === name&&value.relationDataTextTwo!=""&&value.relationDataTextTwo!=null&&value.relationDataTextTwo!=undefined){
												var label = iRow + "_" + name;
												//获得要查询的字段
												var str = "";
												var tempArray = value.relationDataDefiantion.split("|");
												for(var j=0;j<tempArray.length;j++){
													var tempObj = JSON.parse(tempArray[j]);
													str += ','+tempObj.sqlField;
												}
												var path = "/basicdata/dyview/view_show?viewUuid="+value.relationDataValueTwo+"&currentPage=1&openBy=dytable&relationDataDefiantion="+str;
												var parmArray = new Array();
												var relationDataTwoSql2 = value.relationDataTwoSql;
												while(relationDataTwoSql2.indexOf("${")>-1){
													var s1=relationDataTwoSql2.match("\\${.*?\\}")+"";
													parmArray.push(s1.replace("${", "").replace("}", ""));
													relationDataTwoSql2 = relationDataTwoSql2.replace(s1,"");
												}
												path += "&"+value.relationDataTwoSql;
												for(var jt=0;jt<parmArray.length;jt++){
													if(eval('formData.'+parmArray[jt])!=undefined&&eval('formData.'+parmArray[jt])!=""&&eval('formData.'+parmArray[jt])!="undefined"){
														path = path.replace("${"+parmArray[jt]+"}",eval('formData.'+parmArray[jt])) ;
													}
												}
												if(path.indexOf("${")>-1){
													var json = new Object(); 
													json.content = "没有相应条件的数据";
											        json.title = "相关数据源";
											        json.height= 600;
											        json.width= 800;
											        showDialog(json);
												}else{
													$.ajax({
														async:false,
														url : ctx + path,
														success : function(data) {
															var json = new Object(); 
													        json.content = "<div class='dnrw'>" +data+"</div>";
													        json.title = "相关数据源";
													        json.height= 600;
													        json.width= 800;
													        showDialog(json);
													        $(".dataTr").live("dblclick",function(){
													        	var valStr = $(this).attr("jsonstr").replace("{","").replace("}","").split(",");
													        	for(var ai1=0;ai1<valStr.length;ai1++){
																	for(var j=0;j<tempArray.length;j++){
																		var tempObj = JSON.parse(tempArray[j]);
																		if(tempObj.sqlField.replace("_","").toUpperCase()==valStr[ai1].split("=")[0].toUpperCase().replace(" ","")){
																			if(tempObj.formField==value.colEnName){
																				$("#"+label).val(valStr[ai1].split("=")[1]);
																			}else{
																				$(qid).jqGrid("setCell",id,tempObj.formField,valStr[ai1].split("=")[1]);
																			}
																		}
																	}
													        	}
													        	$(qid).jqGrid("saveCell", iRow, iCol);
													        	$("#dialogModule").dialog( "close" );
															});
														}
													});
												}
										}
										else if(value.colEnName === name){
											$("#" + iRow + "_" + name, qid).one('blur', function() {
												$(qid).jqGrid("saveCell", iRow, iCol);
											});
										}
										if(value.jsFunction!=""){
												var jsFunction_ = value.jsFunction;
												if(jsFunction_ != "" && jsFunction_ != null) {
													var colmunArray = jsFunction_.replace(/\+/g,"@")
													.replace(/\-/g,"@")
													.replace(/\*/g,"@")
													.replace(/\//g,"@").split("@");
													for(var i=0;i<colmunArray.length;i++){
														for(var j=0;j<titleArr.length;j++){
															if(titleArr[j]==colmunArray[i]){
																var colName = cols[j].name;
																jsFunction_ = jsFunction_.replace(titleArr[j],colName);
																break;
															}
														}
													}
													var flag = 1;
													//算法，字段名
													var jsFunction1 = jsFunction_;
													for(var ii=0;ii<colmunArray.length;ii++){
														if(flag == 1){
															for(var jj=0;jj<titleArr.length;jj++){
																if(titleArr[jj]==colmunArray[ii]&&cols[jj].name!=name){
																	var colVal = $(qid).jqGrid("getCell",id,jj+1);
																	if(colVal==""){
																		flag = 0;
																		break;
																	}else{
																		jsFunction_ = jsFunction_.replace(cols[jj].name,colVal);
																	}
																}
															}
														}else{
															break;
														}
													}
													$("#" + iRow + "_" + name, qid).one('blur', function() {
														if(flag==1&&jsFunction1.indexOf(name)>-1){
															var colVal_ = $(qid).jqGrid("getCell",id,iCol);
															if(colVal_!=""){
																jsFunction_ = jsFunction_.replace(name,colVal_);
															}
															var val = eval(jsFunction_);
															$(qid).jqGrid("setCell",id,value.colEnName,val);
															$(qid).jqGrid('saveCell',iRow, iCol);
														}
													});
												}
												
											}
									
									}
									
								},
								loadComplete: function(xhr){
								var tblName = $(this).attr('id');
									var colInfos = JSON.parse($(this).attr('colInfos'));
									var ids = $(jqGridId).jqGrid('getDataIDs');
									 for ( var i=0; i<ids.length;i++ ){//遍历从表所有的数据
										 var idValue = ids[i];
										 for(var ia=0;ia<colInfos.length;ia++) {//遍历一列数据的所有列字段
											 var value = colInfos[ia];
											 if(value.inputMode == dyFormInputMode.accessory3) {//判断字段类型为列表附件的字段
												 var val = $(jqGridId).jqGrid('getCell',ids[i],value.colEnName); //取得附件字段的值
												 
												 var fieldName = value.colEnName;//附件字段的名字
												 
													 
												 var uploadButton = "<div id='attachContainer_" + ids[i] + fieldName + "'></div>"
										         $(jqGridId).jqGrid('setCell',ids[i],value.colEnName,uploadButton);
													 
													var $attachContainer = $("#attachContainer_" + ids[i] + fieldName) //上传控件要存放的位置,为jquery对象 
												   
													
													 //创建上传控件
													 var elementID = WellFileUpload.getCtlID4Dytable("", fieldName, idValue);
													 
													 var fileupload = new WellFileUpload(elementID);

													//初始化上传控件
													fileupload.initWithLoadFilesFromFileSystem(false,  $attachContainer,  formSign == "2", true, idValue, fieldName);
												
											 }
										 }
									}										 
							    }  
							});
						}else {
							$('#'+tableArr[i].id).jqGrid({
								beforeEditCell: function (rowid, cellname, value, iRow, iCol) {//将从表列表附件列设置为只读
//						             var rec = $(jqGridId).jqGrid('getRowData', rowid);
						             var colInfos = JSON.parse($(this).attr('colInfos'));
						             for(var index=0;index<colInfos.length;index++) {
						            	 var value = colInfos[index];
						            	 if(value.inputMode == dyFormInputMode.accessory3){
						            		 if (cellname == value.colEnName) {
						            			 var id = $(this).attr("id")
								                 setTimeout(function () {
//								                	 $(jqGridId).jqGrid('setCell',rowid,cellname,'','editable-cell');
								                     $("#"+id).jqGrid('saveCell', iRow, iCol);
								                     //===>或者设置为只读
								                 }, 1);
								                
						            	 	}
						            	 }
						             	}
						            
						             },
								datatype:'local',
								data:subData,
						        autowidth: true,  
								colNames:titleArr,
								colModel:cols,
								scrollOffset : 0,
								multiselect:true,
								rownumbers :true,
								cellEdit:true,
								cellsubmit:'clientArray',
								shrinkToFit:true,
								scrollOffset : 0,
								height : 'auto',
								grouping:true,
							   	groupingView : {
							   		groupField : groupArray,
							   		groupColumnShow : [false]
							   	},
								afterEditCell:function (id,name,val,iRow,iCol){
									var qid = this;
									var colInfos = JSON.parse($(this).attr('colInfos'));
									//获得所有的行数据id
									var ids = $(jqGridId).jqGrid('getDataIDs');
									
									
									for(var iii=0;iii<colInfos.length;iii++) {
										var value = colInfos[iii];
										if(value.dataType == dyFormInputType.date && value.colEnName === name){//日期
											var label = iRow + "_" + name;
											$("#" + iRow + "_" + name).live('click', function() {
												WdatePicker({dateFmt:'yyyy-MM-dd',onpicked:function(){
													$(qid).jqGrid('saveCell',iRow, iCol);}
												});
											});
											return false;
										}
										else if(value.dataType == dyFormInputMode.dateTimeMin && value.colEnName === name){//日期到分
											my97Datetime(iRow+"_" + name,'yyyy-MM-dd HH:mm');
											var label = iRow + "_" + name;
											$("#" + iRow + "_" + name).live('click', function() {
												WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',onpicked:function(){
													$(qid).jqGrid('saveCell',iRow, iCol);}
												});
											});
											return false;
										}
										else if(value.dataType == dyFormInputMode.dateTimeSec && value.colEnName === name){//日期到秒
											my97Datetime(iRow+"_" + name,'yyyy-MM-dd HH:mm:ss');
											var label = iRow + "_" + name;
											$("#" + iRow + "_" + name).live('click', function() {
												WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',onpicked:function(){
													$(qid).jqGrid('saveCell',iRow, iCol);}
												});
											});
											return false;
										}
										else if(value.dataType == dyFormInputMode.timeMin && value.colEnName === name){//日期到秒
											var label = iRow + "_" + name;
											$("#" + iRow + "_" + name).live('click', function() {
												WdatePicker({dateFmt:'HH:mm',onpicked:function(){
													$(qid).jqGrid('saveCell',iRow, iCol);}
												});
											});
											return false;
										}
										else if(value.dataType == dyFormInputMode.timeSec && value.colEnName === name){//日期到秒
											var label = iRow + "_" + name;
											$("#" + iRow + "_" + name).live('click', function() {
												WdatePicker({dateFmt:'HH:mm:ss',onpicked:function(){
													$(qid).jqGrid('saveCell',iRow, iCol);}
												});
											});
											return false;
										}
										else if(value.inputDataType == dyFormInputMode.dialog && value.colEnName === name) {//弹出框
											if( value.relationDataShowType != null){
												 window[value.relationDataShowType](value,qid,id,name,val,iRow,iCol);
											}
										}
										else if(value.inputDataType == dyFormInputMode.accessory3) {//列表显示（不含正文）
 
										}
										else if(value.inputDataType == dyFormInputMode.treeSelect && value.colEnName === name) {//树形下拉框
											var servicename = value.serviceName.split(".")[0]; //服务名
											var method = value.serviceName.split(".")[1].split("(")[0]; //方法名
											var datas = value.serviceName.split(".")[1].split("(")[1].replace(")",""); //数据
											var data = datas.split(",")[0];
											data = data.replace("\'","").replace("\'","");
											var label = iRow + "_" + name;
											var setting = {
												async : {
													otherParam : {
														"serviceName" : servicename,
														"methodName" : method,
														"data" : data
													}
												},
												check : {//复选框的选择做成可配置项
													enable:false
												},
												callback : {
													onClick:function (event, treeId, treeNode) {
														$("#"+label).val(treeNode.name);
														var b = {"name":treeNode.name,"value":treeNode.data};
														cache[id+"tree"] = b;
														$(qid).jqGrid("saveCell", iRow, iCol);
													}
												}
											};
											$("#"+label).comboTree({
												labelField : label,
												treeSetting : setting,
												width: 140,
												height: 140
											});
											$("#"+label).trigger("click");
										}
										else if(value.inputDataType == dyFormInputMode.orChoose3 && value.colEnName === name) {//组织选择框(人员+部门)
											var label = iRow + "_" + name;
											$.unit.open({
												labelField:iRow + "_" + name,
												initNames: getLabelByValue(id),
												initIDs:getValuesByLabel(id),
												afterSelect:function(data) {
														var tname = "";
														var tvalue = "";
														tname = data.name;
														tvalue = data.id;
														var b = {"name":tname,"value":tvalue};
														if (cache[id] == null){
															cache[id] = b;
														}else {
															cache[id] = b;
														}
														$("#"+label).val(tname);
														$(qid).jqGrid("saveCell", iRow, iCol);
												}, 
												close : function(e) {
													$(qid).jqGrid("saveCell", iRow, iCol);
												}
											});
											return false;
										}
										else if(value.inputDataType == dyFormInputMode.orChoose2 && value.colEnName === name) {//组织选择框(仅选择组织部门)
											var label = iRow + "_" + name;
											$("#"+label).attr("hiddenValue",hiddenValue);
											$.unit.open({
												labelField:iRow + "_" + name,
												initNames: getLabelByValue(id),
												initIDs:getValuesByLabel(id),
													afterSelect:function(data) {
														var tname = "";
														var tvalue = "";
														tname = data.name;
														tvalue = data.id;
														var b = {"name":tname,"value":tvalue};
														if (cache[id] == null){
															cache[id] = b;
														}else {
															cache[id] = b;
														}
														$("#"+label).val(tname);
														$(qid).jqGrid("saveCell", iRow, iCol);
												}, 
												close : function(e) {
													$(qid).jqGrid("saveCell", iRow, iCol);
												},
												type : "Dept",
												selectType : 2,
											});
											return false;
										}
										else if(value.inputDataType == dyFormInputMode.orChoose1 && value.colEnName === name) {//组织选择框(仅选择组织部门)
											var label = iRow + "_" + name;
											$("#"+label).attr("hiddenValue",hiddenValue);
											$.unit.open({
												labelField:iRow + "_" + name,
												initNames: getLabelByValue(id),
												initIDs:getValuesByLabel(id),
													afterSelect:function(data) {
														var tname = "";
														var tvalue = "";
														tname = data.name;
														tvalue = data.id;
														var b = {"name":tname,"value":tvalue};
														if (cache[id] == null){
															cache[id] = b;
														}else {
															cache[id] = b;
														}
														$("#"+label).val(tname);
														$(qid).jqGrid("saveCell", iRow, iCol);
												}, 
												close : function(e) {
													$(qid).jqGrid("saveCell", iRow, iCol);
												},
												selectType : 4,
											});
											return false;
										}
										else if(value.colEnName === name&&value.relationDataTextTwo!=""&&value.relationDataTextTwo!=null&&value.relationDataTextTwo!=undefined){
											var label = iRow + "_" + name;
												//获得要查询的字段
												var str = "";
												var tempArray = value.relationDataDefiantion.split("|");
												for(var j=0;j<tempArray.length;j++){
													var tempObj = JSON.parse(tempArray[j]);
													str += ','+tempObj.sqlField;
												}
												var path = "/basicdata/dyview/view_show?viewUuid="+value.relationDataValueTwo+"&currentPage=1&openBy=dytable&relationDataDefiantion="+str;
												var parmArray = new Array();
												var relationDataTwoSql2 = value.relationDataTwoSql;
												while(relationDataTwoSql2.indexOf("${")>-1){
													var s1=relationDataTwoSql2.match("\\${.*?\\}")+"";
													parmArray.push(s1.replace("${", "").replace("}", ""));
													relationDataTwoSql2 = relationDataTwoSql2.replace(s1,"");
												}
												path += "&"+value.relationDataTwoSql;
												for(var jt=0;jt<parmArray.length;jt++){
													if(eval('formData.'+parmArray[jt])!=undefined&&eval('formData.'+parmArray[jt])!=""&&eval('formData.'+parmArray[jt])!="undefined"){
														path = path.replace("${"+parmArray[jt]+"}",eval('formData.'+parmArray[jt])) ;
													}
												}
												if(path.indexOf("${")>-1){
													var json = new Object(); 
													json.content = "没有相应条件的数据";
											        json.title = "相关数据源";
											        json.height= 600;
											        json.width= 800;
											        showDialog(json);
												}else{
													$.ajax({
														async:false,
														url : ctx + path,
														success : function(data) {
															var json = new Object(); 
													        json.content = "<div class='dnrw'>" +data+"</div>";
													        json.title = "相关数据源";
													        json.height= 600;
													        json.width= 800;
													        showDialog(json);
													        $(".dataTr").live("dblclick",function(){
													        	var valStr = $(this).attr("jsonstr").replace("{","").replace("}","").split(",");
													        	for(var ai1=0;ai1<valStr.length;ai1++){
																	for(var j=0;j<tempArray.length;j++){
																		var tempObj = JSON.parse(tempArray[j]);
																		if(tempObj.sqlField.replace("_","").toUpperCase()==valStr[ai1].split("=")[0].toUpperCase().replace(" ","")){
																			if(tempObj.formField==value.colEnName){
																				$("#"+label).val(valStr[ai1].split("=")[1]);
																			}else{
																				$(qid).jqGrid("setCell",id,tempObj.formField,valStr[ai1].split("=")[1]);
																			}
																		}
																	}
													        	}
													        	$(qid).jqGrid("saveCell", iRow, iCol);
													        	$("#dialogModule").dialog( "close" );
															});
														}
													});
												}
										}
										else if(value.colEnName === name){
											$("#" + iRow + "_" + name, qid).die().live('blur', function() {
												$(qid).jqGrid("saveCell", iRow, iCol);
											});
										}
										if(value.jsFunction!=""){
												var jsFunction_ = value.jsFunction;
												if(jsFunction_ != "" && jsFunction_ != null) {
													var colmunArray = jsFunction_.replace(/\+/g,"@")
													.replace(/\-/g,"@")
													.replace(/\*/g,"@")
													.replace(/\//g,"@").split("@");
													for(var i=0;i<colmunArray.length;i++){
														for(var j=0;j<titleArr.length;j++){
															if(titleArr[j]==colmunArray[i]){
																var colName = cols[j].name;
																jsFunction_ = jsFunction_.replace(titleArr[j],colName);
																break;
															}
														}
													}
													var flag = 1;
													//算法，字段名
													var jsFunction1 = jsFunction_;
													for(var ii=0;ii<colmunArray.length;ii++){
														if(flag == 1){
															for(var jj=0;jj<titleArr.length;jj++){
																if(titleArr[jj]==colmunArray[ii]&&cols[jj].name!=name){
																	var colVal = $(qid).jqGrid("getCell",id,jj+1);
																	if(colVal==""){
																		flag = 0;
																		break;
																	}else{
																		jsFunction_ = jsFunction_.replace(cols[jj].name,colVal);
																	}
																}
															}
														}else{
															break;
														}
													}
													$("#" + iRow + "_" + name, qid).one('blur', function() {
														if(flag==1&&jsFunction1.indexOf(name)>-1){
															var colVal_ = $(qid).jqGrid("getCell",id,iCol);
															if(colVal_!=""){
																jsFunction_ = jsFunction_.replace(name,colVal_);
															}
															var val = eval(jsFunction_);
															$(qid).jqGrid("setCell",id,value.colEnName,val);
															$(qid).jqGrid('saveCell',iRow, iCol);
														}
													});
												}
												
											}
										}
								},
								loadComplete: function(xhr){
									var tblName = $(this).attr('id');
									var colInfos = JSON.parse($(this).attr('colInfos'));
									var ids = $(jqGridId).jqGrid('getDataIDs');
									 for ( var i=0; i<ids.length;i++ ){//遍历从表所有的数据
										 var idValue = ids[i];
										 for(var ia=0;ia<colInfos.length;ia++) {//遍历一列数据的所有列字段
											 var value = colInfos[ia];
											 if(value.inputMode == dyFormInputMode.accessory3) {//判断字段类型为列表附件的字段
												  var val = $(jqGridId).jqGrid('getCell',ids[i],value.colEnName); //取得附件字段的值
												 
												 var fieldName = value.colEnName;//附件字段的名字
												  
													 
												 var uploadButton = "<div id='attachContainer_" +  ids[i] + fieldName + "'></div>"
										         $(jqGridId).jqGrid('setCell',ids[i],value.colEnName,uploadButton);
												 
												  var $attachContainer = $("#attachContainer_" +  ids[i] + fieldName) //上传控件要存放的位置,为jquery对象  
												   
													 //创建上传控件
													 var elementID = WellFileUpload.getCtlID4Dytable("", fieldName, idValue);
													 
													 var fileupload = new WellFileUpload(elementID);

													//初始化上传控件
													fileupload.initWithLoadFilesFromFileSystem(false,  $attachContainer,  formSign == "2", true, idValue, fieldName);
															
												}
												 
											 }
										 }	
							    },  
							});
						}
						if(tableArr[i].tableTitle != null && tableArr[i].tableTitle != "") {
							if(dySubFormTableOpen.open == tableArr[i].tableOpen) {
								$('#gbox_'+ tableArr[i].id).css("display","");
							}else if(dySubFormTableOpen.notOpen == tableArr[i].tableOpen) {
								 $('#gbox_'+ tableArr[i].id).css("display","none");
							}
						}
						
						//从表的表单表头打开
						$("#openDiv").die().live("click",function() {
							var $this = $(this);
							var titleDiv = $this.parents("#titleDiv");
							var nextId = titleDiv.next();
							var subBtnId2 = "subBtn-" + nextId.attr("id").split("-")[1];

							if(nextId.attr("id") == subBtnId2) {
								nextId.css("display","none");
								nextId.next().css("display","none");
								$this.css("background-position","-75px 0");
							}else {
								nextId.css("display","none");
								$this.css("background-position","-75px 0");
							}
							$this.attr("id","notOpenDiv");
						});
						
						$("#title_span_open").die().live("click",function() {
							var $this = $(this);
							var titleDiv = $this.parents("#titleDiv");
							var nextId = titleDiv.next();
							var subBtnId2 = "subBtn-" + nextId.attr("id").split("-")[1];
							if(nextId.attr("id") == subBtnId2) {
								nextId.css("display","none");
								nextId.next().css("display","none");
							}else {
								nextId.css("display","none");
							}
							$this.attr("id","title_span_notopen");
							$this.next().attr("id","notOpenDiv");
						});
						
						//从表的表单表头关闭
						$("#notOpenDiv").die().live("click",function() {
							var $this = $(this);
							var titleDiv = $this.parents("#titleDiv");
							var nextId = titleDiv.next();
							var subBtnId2 = "subBtn-" + nextId.attr("id").split("-")[1];
							if(nextId.attr("id") == subBtnId2) {
								nextId.css("display","");
								nextId.next().css("display","");
								$this.css("background-position","-95px 0");
							}else {
								nextId.css("display","");
								$this.css("background-position","-95px 0");
							}
							$this.attr("id","openDiv");
						});
						
						$("#title_span_notopen").die().live("click",function() {
							var $this = $(this);
							var titleDiv = $this.parents("#titleDiv");
							var nextId = titleDiv.next();
							var subBtnId2 = "subBtn-" + nextId.attr("id").split("-")[1];
							if(nextId.attr("id") == subBtnId2) {
								nextId.css("display","");
								nextId.next().css("display","");
							}else {
								nextId.css("display","");
							}
							$this.attr("id","title_span_open");
							$this.next().attr("id","openDiv");
						});
						
						
						
//						//调整浏览器窗口大小
//						var a = tableArr[i].id;
//						$(window).resize(function(){
//							$('#' + a).jqGrid("setGridWidth", $(this).width() - 10);
//						});
					}else{//弹出窗口编辑
						$('#' + tableArr[i].id).jqGrid({
							datatype:'local',
							data:subData,
							scrollOffset : 0,
							autowidth: true,
							colNames:titleArr,
							colModel:cols,
							multiselect:true,
							mtype:'POST',
							editurl:contextPath + '/dytable/submit_form.action'
						});
						//调整浏览器窗口大小
						var b = tableArr[i].id;
						$(window).resize(function(){
							$('#' + b).jqGrid("setGridWidth", $(this).width() - 10);
							$('#' + b).jqGrid("setGridHeight", $(this).height() - 100);
						});
						//从表操作弹出窗口
						var dialogId = '_dialog_' + tableArr[i].id;
						var id = tableArr[i].id;
						var dialog = '<div id=' + dialogId + ' title="操作窗口">' + tableArr[i].htmlBodyContent + '</div>';
						$(dialog).insertAfter('#' + tableArr[i].id);
						initForm($( "#" + dialogId),thArr,false,{},mainTableName);
						$( "#" + dialogId).dialog({
							open: function() {
								var dialogWidth = $("#" + id).width();
							},
							autoOpen: false,
							height: $(window).height() * 2/3, //650,
							width: $("#" + id, this).width(),//800,
							modal: true,
							buttons: {
								"取消": function() {
									$( this ).dialog( "close" );
								},
								"确定": function() {
								}

							},
							close: function() {
								//allFields.val( "" ).removeClass( "ui-state-error" );
							}
						});
						
					}
			}
			// 调整浏览器窗口大小
			$(window).resize(function(){
				var tableArr = data.form.subTables;
				var formWidth = $(".post-detail").width();
				var formWidth2 =$("#showHtml").find(".post-detail").width();
				
				for(var i = 0; i < tableArr.length; i++){
					if(dySubFormEdittype.rowEdit == tableArr[i].editType){
						if(formWidth == 0) {
							$('#' + tableArr[i].id).jqGrid("setGridWidth", formWidth2);
						}else {
							$('#' + tableArr[i].id).jqGrid("setGridWidth", formWidth);
						}
					}
				}
			});
			
			//主表表单校验
			initValid(this.parent(),btnSubmit,beforeSubmit);
			//从表弹出校验
			initValid('div.ui-dialog',$('div button:last-child'),saveSub);
			
			// 编辑表单且启用数字签名
			if(!(setReadOnly == true) && formSign == "2") {
				if(data.signature && data.signature != null){
					signature = data.signature;
				}
				var enableSignUploadFile = false;
				try {
					enableSignUploadFile = fjcaWs.OpenFJCAUSBKey();
				} catch (e) {
					try {
						fjcaWs.CloseUSBKey();
					} catch (e) {}
				}
				if(!enableSignUploadFile) {
					$(btnSubmit).unbind("click");
					$(btnSubmit).click(function(e){
						if(Browser.isIE()){
							oAlert("无法对表单数据进行签名，请插入USBKey数字证书!");
						}else{
							oAlert("当前浏览器无法对表单数据进行签名，请使用IE浏览器编辑表单!");
						}
						return false;
					});
					if(Browser.isIE()){
						oAlert("无法对表单数据进行签名，请插入USBKey数字证书!");
					}else{
						oAlert("当前浏览器无法对表单数据进行签名，请使用IE浏览器编辑表单!");
					}
				} else{
					fjcaWs.CloseUSBKey();
				}
			}
			
			if(options.open !=null ){
				options.open();
			}
			if($("#drId").length>0){
				var drId = $("#drId").val();
//				$("#"+drId+" .tab-content").jScrollPane();
				$("#"+drId+" .tab-content").each(function(){
					if($(this).height()<$(this).children().eq(0).height()){
						$(this).jScrollPane();
					}
				});
			}
			if(setReadOnly == true && showTableModelId != "" && showTableModelId != null) {
				//将数据显示在单据上
				var dytableId = $(".dytable_form").parent().attr("id");
				//列表附件的处理
				$.each($("#"+dytableId).find(".filename"),function (i,obj){
					var fileId = $(this).parents("td").children().attr("id"); 
					var fileHtml = $(this).parents("td").children().html();
					if($("#showHtml").find("#"+fileId).length != 0) {
						$("#showHtml").find("#"+fileId).html(fileHtml);	
					}
				});
				
				//底层附件的处理
				$.each($("#"+dytableId).find("#files"),function (i,obj){
					var fileId = $(this).attr("id"); 
					var fileHtml = $(this).parents("td").children().html();
					if($("#showHtml").find("#"+fileId).length != 0) {
						$("#showHtml").find("#"+fileId).html(fileHtml);	
					}
				});
				//从表的处理
				$.each($("#"+dytableId).find("table"),function (i,obj){
					if($(this).attr("id") != undefined) {
						var jqId = $(this).attr("id");
						if($(this).parents("#gbox_"+jqId).length != 0) {
							if($("#"+jqId).length>0){
								$("#gbox_"+jqId).css("display","");
								$("#gbox_"+jqId).prev().insertBefore($("#showHtml").find("#"+jqId));
								$("#showHtml").find("#"+jqId).after($("#gbox_"+jqId));
								$("#gbox_"+jqId).hide();
								$("#showHtml").find("#"+jqId).remove();
							}
						}
					}
				});
				//$this.css("display","none");
			}
			
	};
//加载全局国际化资源
I18nLoader.load("/resources/pt/js/global");
//加载动态表单定义模块国际化资源
I18nLoader.load("/resources/pt/js/dytable/dytable");


var formDefinitionInfo;

function getLabelByValue(rowid) {
	if (rowid == null) {
		return "";
	}
	var label = "";
	if(cache[rowid] != undefined) {
		label = cache[rowid].name;
	}
	return label;
}

function getValuesByLabel(rowid) {
	if (rowid == null) {
		return "";
	}
	var value = "";
	if(cache[rowid] != undefined) {
		value = cache[rowid].value;
	}
	return value;
}


//根据传入的BUTTON映射名来查询对应的表id
function getIdInfo(fieldMappingName) {
	var id = null;
	var tableArr = data.form.subTables; //从表信息
	for(var index=0;index<tableArr.length;index++) {
		var applyTo = tableArr[index].applyTo;
			if(applyTo != null && applyTo != "") {
			var applyToArray = applyTo.split(";");
			for(var i=0;i<applyToArray.length;i++) {
				if (fieldMappingName == applyToArray[i]) {
					id =  tableArr[index].id;
				}
			}
		}
	}
	return id;
}

//给主表某个字段赋值
function setFieldValue(params) {
	////console.log("------------------:");
	//console.log(JSON.stringify(params));
	var fieldMappingName = params.mappingName;
	var key = params.key;
	var value = "";
	if(key != null && key != "") {
		value = key + ":" + params.value;	
	}else {
		value = params.value;	
	}
	//设置的字段的类型
	var type = params.type;
	var options = params.options;
	if(type == "select") {
		var info = getFieldInfo(fieldMappingName);
		var colEnName = "";
		for(var i=0;i<info.length;i++) {
			colEnName = info[1];
//			$("#"+colEnName).val(params.value);
		}
		var selectHTML = "";
		if(options != undefined) {
			for(var index=0;index<options.length;index++) {
				if(options.length<2) {
					selectHTML = "<select name='"+colEnName+"' id='selectId' ><option value='" + options[index].value + "'>" + options[index].label + "</option>";
				}else {
					selectHTML = "<select name='"+colEnName+"' id='selectId' ><option value=''>" + dylbl.selectNull + "</option>";
				}
			}
			if(options.length >1) {
				$.each(options,function (i,item){
					selectHTML += "<option value='"+item.value+"'>" + item.label + "</option>";
				});
			}
			selectHTML += "</select>";
			$("#"+colEnName).after(selectHTML);
			$("#"+colEnName).hide();
			if(value != null && value != "") {
				$("#selectId").find('option[value=' + value + ']').attr('selected',true);
			}
			$("#selectId").change(function() {
				$("#"+colEnName).val($(this).children('option:selected').val());
			});
		}else {
			$("#" + colEnName).find('option[value=' + value + ']').attr('selected',true);
		}
		
		
	}else if(type=="label") {
		var info = getFieldInfo(fieldMappingName);
		var colEnName = "";
		for(var i=0;i<info.length;i++) {
			colEnName = info[1];
		}
		$("#"+colEnName).next().remove();
		$("#"+colEnName).after("<span>"+value+"</span>");
	}
	else if(type == "checkbox") {
		var checkValue = params.value;
		checkValue = checkValue.split(",");
		var info = getFieldInfo(fieldMappingName);
		for(var i=0;i<info.length;i++) {
			var colEnName = info[1];
			for(var j=0;j<checkValue.length;j++) {
				$('input[type=checkbox][name=' + colEnName + '][value=' + checkValue[j]+ ']').attr('checked',true);
			}
		}
	}else if(type == "radio") {
		var info = getFieldInfo(fieldMappingName);
		for(var i=0;i<info.length;i++) {
			var colEnName = info[1];
			$('input[type=radio][name=' + colEnName + '][value=' + params.value + ']').attr('checked',true);
		}
	}
	else {
		var maincol = data.form.tableInfo.fields;
		if(fieldMappingName == "File_parent_id") {
			for(var j=0;j<maincol.length;j++){
				var inputMode,serviceName,methodName,data2;
				inputMode = maincol[j].inputMode;
				serviceName = maincol[j].serviceName;
				methodName = maincol[j].methodName;
				data2 = maincol[j].data;
				if(dyFormInputMode.treeSelect == inputMode) {
					var sN = serviceName.split(".")[0];
					var mN = methodName.split(".")[1];
					var info = getFieldInfo(fieldMappingName);
					for(var i=0;i<info.length;i++) { 
						var colEnName = info[1];
						if(mN != undefined) {
							var Method = mN.split("(")[0];
						var initService = sN+ "."+Method;
						
						JDS.call({
							service : initService,
							data : [data2,params.value],
							success : function(result) {
								var data = result.data;
								$("#"+colEnName).val(data.label);
								$("#_"+colEnName).val(params.value);
							}
						});
						}
					}
				}
			}
		}else {
			var info = getFieldInfo(fieldMappingName);
			var colEnName = "";
			var inputMode_ = "";
			 
			colEnName = info[1];
			inputMode_ = info[3];
			 
			if(dyFormInputMode.orChoose == inputMode_||dyFormInputMode.orChoose == inputMode_||
					dyFormInputMode.orChoose2 == inputMode_||dyFormInputMode.orChoose3 == inputMode_||dyFormInputMode.orChoose4 == inputMode_){
				$("#" +colEnName).attr("hiddenValue",value.split(",")[1]);
				//						$("#_"+colEnName).val(value.split(",")[1]);
				$("#" +colEnName).val(value.split(",")[0]);
				
			}else if((dyFormInputMode.accessory1 == inputMode_ 
			|| dyFormInputMode.accessory2 == inputMode_ 
			|| dyFormInputMode.accessory3 == inputMode_)){//附件//hunt 2014.5.5
				//表单的表名
				var mainTableName = data.form.tableInfo.tableName;
				var ctlId = WellFileUpload.getCtlID4Dytable(mainTableName, colEnName, 0);
				//console.log(WellFileUpload.get);
				var fileupload = WellFileUpload.get(ctlId);  
				//console.log(fileupload);
				fileupload.addFiles(value, true); 
			}
			
			else{
				$("#"+colEnName).val(value);
			}
		}
	}
}


//根据传入的映射名(或者字段名)查询字段信息
function getFieldInfo(fieldMappingName) {
	var mainTableArr = data.form.tableInfo; //主表信息
	var mainArr = mainTableArr.fields;
	var info = new Array();
	var tableName = mainTableArr.tableName;
	info.push(tableName);
	for(var i=0;i<mainArr.length;i++) {
		var colenName = mainArr[i].colEnName;
		var colcnName = mainArr[i].colCnName;
		if(fieldMappingName == colenName) {
			info.push(colenName);
			info.push(colcnName);
			info.push(mainArr[i].inputMode);
		}else {
			var applyTo = mainArr[i].applyTo;
			if(applyTo !=null) {
				var applyToArray = applyTo.split(";");
				for(var j=0;j<applyToArray.length;j++) {
					if(fieldMappingName == applyToArray[j]) {
						var colEnName = mainArr[i].colEnName;
						var colCnName = mainArr[i].colCnName;
						info.push(colEnName);
						info.push(colCnName);
					};
				}
			}
		}
	}
	return info;
}

//根据传入的主表uuid和映射名查询字段信息
function getSubFieldInfo(params) {
	var subTableArr = data.form.subTables;//从表的信息
	var info = new  Array();
	for(var index=0;index<subTableArr.length;index++) {
		var subArr = subTableArr[index].fields;
		for(var i=0;i<subArr.length;i++) {
			var applyTo = subArr[i].applyTo;
			if(applyTo != null) {
				var applyToArray = applyTo.split(";");
				for(var j=0;j<applyToArray.length;j++) {
					if(params.fieldMappingName == applyToArray[j]) {
						var colEnName = subArr[i].colEnName;
						var colCnName = subArr[i].colCnName;
						info.push(colEnName);
						info.push(colCnName);
					}
				}
			}
			
		}
	};
	return info;
}

function idCardNoCheck(value, colname){
	var rs = true;
	var len = value.length, re;
	if (len == 15){
		re = new RegExp(/^(\d{6})()?(\d{2})(\d{2})(\d{2})(\d{3})$/);
	}else if (len == 18){
		re = new RegExp(/^(\d{6})()?(\d{4})(\d{2})(\d{2})(\d{3})(\d)$/);
	}else{
		rs = false;
	}
	var a = value.match(re);
	if (rs && a != null){
		if (len==15){
			var D = new Date("19"+a[3]+"/"+a[4]+"/"+a[5]);
			var B = D.getYear()==a[3]&&(D.getMonth()+1)==a[4]&&D.getDate()==a[5];
		}else{
			var D = new Date(a[3]+"/"+a[4]+"/"+a[5]);
			var B = D.getFullYear()==a[3]&&(D.getMonth()+1)==a[4]&&D.getDate()==a[5];
		}
		if (!B) {
			//alert("输入的身份证号 "+ a[0] +" 里出生日期不对！"); 
			rs = false;
		}
	}
    var s = '';
    if(!rs){
    	s = dymsg.idCardNo;
    }
    return [rs,s];
}



function radioEl (value, options) {
	var datas = options.data;
	var span = document.createElement("span");
	var s = '';
	for(var i=0;i<datas.length;i++){
		var name = 'R_' + options.name;
		var id = name + '_' + i;
		s = s + '<input name=' + name + ' id=' + id + ' type=radio value=' + datas[i].value + (value == datas[i].value ? ' checked=true' : '') + '>' + datas[i].label;
	}
	$(span).append(s);

	return span;
}
function radioVal(el) {
	var val = $(el).find("input[type=radio]:checked").val();
	return (val == undefined ? '' : val);
}

function checkboxEl (value, options) {
	var datas = options.data;
	var span = document.createElement("span");
	var s = '';
	for(var i=0;i<datas.length;i++){
		var name = 'R_' + options.name;
		var id = name + '_' + i;
		s = s + '<input name=' + name + ' id=' + id + ' type=checkbox value=' + datas[i].value + ((',' + value).indexOf(',' + datas[i].value + ',') >=0 ? ' checked=true' : '') + '>' + datas[i].label;
	}
	$(span).append(s);
	
	return span;
}
function checkboxVal(el) {
	var val = '';
	$.each($(el).find("input[type=checkbox]:checked"),function (i,obj){
		val += $(obj).val() + ',';
	});
	return val;
}

function saveSub(){
	var colInfoArr;
	var tableName ;
	$.each(formDefinitionInfo.subTables,function (i,obj){
		if(obj.id == currTab[0].id){
			colInfoArr = obj.fields;
			tableName = obj.tableName;
		}
	});
	var data = getFormData(tableName,colInfoArr,dyTableType.subtable,"","");
	var arr = new Array();
	$.each(data,function (i,obj){
		arr.push('"' + obj.colEnName + '":"' + obj.value + '"');
	});
	var row = '{"id":"' + uuid + '",' + arr.join(',') + '}';
	if(isAdd){
		$(currTab).jqGrid('addRowData',uuid,JSON.parse(row));
	}else{
		$(currTab).jqGrid('setRowData',uuid,JSON.parse(row));
	}
	//$.jBox.info('Hello jBox', 'jBox');
	$.jBox.info(dymsg.saveOk,dymsg.tipTitle);
	$('#_dialog_' + $(currTab)[0].id).dialog('close');
}

/**
 * 主表行数据校验规则
 */
function initValid(forms,btnSubmit,beforeSubmitFun){
	$(forms).Validform({
//		tiptype:3,
		tiptype:function(msg,o,cssctl){
			//msg：提示信息;
			//o:{obj:*,type:*,curform:*}, obj指向的是当前验证的表单元素（或表单对象），type指示提示的状态，值为1、2、3、4， 1：正在检测/提交数据，2：通过验证，3：验证失败，4：提示ignore状态, curform为当前form对象;
			//cssctl:内置的提示信息样式控制函数，该函数需传入两个参数：显示提示信息的对象 和 当前提示的状态（既形参o中的type）;
			
			if(!o.obj.is("form")){//验证表单元素时o.obj为该表单元素，全部验证通过提交表单时o.obj为该表单对象;
				var objtip=o.obj.parents("td").find(".Validform_checktip");
				cssctl(objtip,o.type);
				objtip.text(msg);
				var infoObj=o.obj.parents("td").find(".info");
				if(o.type==2){
					infoObj.fadeOut(200);
				}else if(o.type==4) {
					infoObj.fadeOut(200);
				}
				else{
					if(infoObj.is(":visible")){return;}
					var left=o.obj.offset().left,
						top=o.obj.offset().top;
					var height = o.obj.css("height");
					infoObj.css({
						left:left,
						top:top+parseInt(height)+parseInt(10)
					}).show().animate({
						top:top+parseInt(height)+parseInt(10)
					},200);
				}
			}	
		},
		btnSubmit:btnSubmit,
		beforeSubmit:function (curform){
			if(flag2 == false) {
				if($("#fileupload").length != 0) {
					return;
				}
			}else {
				if(!ValidSubTable()) {
					return;
				}
					beforeSubmitFun();
			}
			return false;
		},
		
		usePlugin:{
			datepicker:{
				format:"yyyy-mm-dd",//指定输出的时间格式;
				firstDayOfWeek:0,//指定每周开始的日期，0、1-6 对应 周日、周一到周六;
				callback:function(date,obj){//指定选择日期后的回调函数;
					//date:选中的日期;
					//obj:当前表单元素;
					//$("#msgdemo2").text(date+" is selected");
				},
				//以上三个参数是在Validform插件内调用Datepicker时可传入的参数;
				//下面几个参数是Datepicker插件本身初始化时可接收的参数，还有更多请查看页面说明;
				clickInput:true,
				startDate:"1970-00-00",
				createButton:false
			}
		},
		datatype:{
			"idcard":function(gets,obj,curform,datatype){
				//该方法由佚名网友提供;
				var Wi = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1 ];// 加权因子;
				var ValideCode = [ 1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2 ];// 身份证验证位值，10代表X;
				if (gets.length == 15) {
					return isValidityBrithBy15IdCard(gets);
				}else if (gets.length == 18){
					var a_idCard = gets.split("");//得到身份证数组 
					if (isValidityBrithBy18IdCard(gets)&&isTrueValidateCodeBy18IdCard(a_idCard)) {
						return true;   
					}
					return false;
				}
				return false;
				
				function isTrueValidateCodeBy18IdCard(a_idCard) {
					var sum = 0; // 声明加权求和变量 
					if (a_idCard[17].toLowerCase() == 'x') {
						a_idCard[17] = 10;// 将最后位为x的验证码替换为10方便后续操作 
					}
					for ( var i = 0; i < 17; i++) {
						sum += Wi[i] * a_idCard[i];// 加权求和 
					}
					valCodePosition = sum % 11;// 得到验证码所位置   
					if (a_idCard[17] == ValideCode[valCodePosition]) {
						return true;
					}
					return false;
				}
				
				function isValidityBrithBy18IdCard(idCard18){
					var year = idCard18.substring(6,10);
					var month = idCard18.substring(10,12);
					var day = idCard18.substring(12,14);   
					var temp_date = new Date(year,parseFloat(month)-1,parseFloat(day));
					// 这里用getFullYear()获取年份，避免千年虫问题  
					if(temp_date.getFullYear()!=parseFloat(year) || temp_date.getMonth()!=parseFloat(month)-1 || temp_date.getDate()!=parseFloat(day)){ 
						return false;
					}
					return true;
				}
				
				function isValidityBrithBy15IdCard(idCard15){
					var year =  idCard15.substring(6,8);
					var month = idCard15.substring(8,10);
					var day = idCard15.substring(10,12);
					var temp_date = new Date(year,parseFloat(month)-1,parseFloat(day));
					// 对于老身份证中的你年龄则不需考虑千年虫问题而使用getYear()方法 
					if(temp_date.getYear()!=parseFloat(year) || temp_date.getMonth()!=parseFloat(month)-1 || temp_date.getDate()!=parseFloat(day)){
						return false;
					}
					return true;
				}
			},
			//文字长度验证规则添加
			"maxLength":function(gets,obj,curform,datatype) {
				var datalength = obj.attr("datalength");
				if(gets.length<=datalength) {
					return true;
				}else {
					return false;
				}
			},
			//唯一性验证规则添加
			"unique":function(gets,obj,curform,datatype) {
				var fieldName = obj.attr("id");
				var fieldNameArray = new Array();
				var selectionArgs = new Array();
//				fieldNameArray.push(fieldName);
				//字段的数据
				var fieldValue = obj.val();
				//通过ajax访问是否有存在于数据库 
				var selection = fieldName + " = '" + fieldValue+"'";
				var flag = true;
				JDS.call({
					async:false,
					service : "formDataService.query2",
					data : [formUuid,fieldNameArray,selection,selectionArgs,"","","",0,0],
					success : function(result) {
						var data = result.data;
						
						if(data.length==0) {
							flag =  true;
						}else if(data.length !=0) {
							for(var index=0;index<data.length;index++) {
								var uuid = data[index].uUID;
								if(uuid == dataUuid) {
									flag = true;
								}else {
									flag = false;
								}
							}
						}
					}
				});
				return flag;
			}
		}
		
	});
}

/**
 * 从表行数据校验规则
 */
function ValidSubTable() {
	var subTabArr = data.form.subTables;
	var subArrInfo = {
	};
	
	for(var i = 0;i<subTabArr.length;i++) {
		
		var fields = subTabArr[i].fields;
		for(var j=0;j<fields.length;j++) {
			var infoValue = {};
			if(fields[j].checkRules.length != 0 ) {
				 infoValue.CnName = fields[j].colCnName;
				 infoValue.checkRules = fields[j].checkRules;
				subArrInfo[fields[j].colEnName] = infoValue;
			}
		}
		$('#' + subTabArr[i].id).Validform();
		var ids = $('#' + subTabArr[i].id).jqGrid('getGridParam','selarrrow'); 
		var rowDatas = $('#' + subTabArr[i].id).jqGrid('getRowData',ids[0]);
		
		
			for(var property in subArrInfo){
				var checkRules = subArrInfo[property].checkRules;
				var CnName = subArrInfo[property].CnName;
				for(var k=0;k<checkRules.length;k++) {
					for(var i=0;i<rowDatas.length;i++) {
						var rowData = rowDatas[i];
					var fieldValue = rowData[property];
					if(dyCheckRule.notNull == checkRules[k].value) {
						if(fieldValue.length ==0) {
							oAlert(CnName + "不能为空！");
							return false;
						}
					}else if(dyCheckRule.url == checkRules[k].value) {	
						//必须是URL
						var result = /^(https?|s?ftp):\/\/(((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:)*@)?(((\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5]))|((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?)(:\d*)?)(\/((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)+(\/(([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)*)*)?)?(\?((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|[\uE000-\uF8FF]|\/|\?)*)?(#((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|\/|\?)*)?$/i.test(fieldValue);
						if(result == false) {
							oAlert(CnName + "必须是URL！");
							return result;
						}
					}else if(dyCheckRule.email == checkRules[k].value) {
						//必须是邮箱地址
						var result = /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))$/i.test(fieldValue);
						if(result == false) {
							oAlert(CnName + "必须是邮箱地址！");
							return result;
						}
					}else if(dyCheckRule.idCard == checkRules[k].value) {
						//必须是身份证
						//该方法由佚名网友提供;
						var Wi = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1 ];// 加权因子;
						var ValideCode = [ 1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2 ];// 身份证验证位值，10代表X;
						if (fieldValue.length == 15) {
							return isValidityBrithBy15IdCard(gets);
						}else if (fieldValue.length == 18){
							var a_idCard = fieldValue.split("");//得到身份证数组 
							if (isValidityBrithBy18IdCard(fieldValue)&&isTrueValidateCodeBy18IdCard(a_idCard)) {
								return true;   
							}
							oAlert(CnName + "不对！");
							return false;
						}
						oAlert(CnName + "不对！");
						return false;
						
						function isTrueValidateCodeBy18IdCard(a_idCard) {
							var sum = 0; // 声明加权求和变量 
							if (a_idCard[17].toLowerCase() == 'x') {
								a_idCard[17] = 10;// 将最后位为x的验证码替换为10方便后续操作 
							}
							for ( var i = 0; i < 17; i++) {
								sum += Wi[i] * a_idCard[i];// 加权求和 
							}
							valCodePosition = sum % 11;// 得到验证码所位置   
							if (a_idCard[17] == ValideCode[valCodePosition]) {
								return true;
							}
							oAlert(CnName + "不对！");
							return false;
						}
						
						function isValidityBrithBy18IdCard(idCard18){
							var year = idCard18.substring(6,10);
							var month = idCard18.substring(10,12);
							var day = idCard18.substring(12,14);   
							var temp_date = new Date(year,parseFloat(month)-1,parseFloat(day));
							// 这里用getFullYear()获取年份，避免千年虫问题  
							if(temp_date.getFullYear()!=parseFloat(year) || temp_date.getMonth()!=parseFloat(month)-1 || temp_date.getDate()!=parseFloat(day)){ 
								return false;
							}
							return true;
						}
						
						function isValidityBrithBy15IdCard(idCard15){
							var year =  idCard15.substring(6,8);
							var month = idCard15.substring(8,10);
							var day = idCard15.substring(10,12);
							var temp_date = new Date(year,parseFloat(month)-1,parseFloat(day));
							// 对于老身份证中的你年龄则不需考虑千年虫问题而使用getYear()方法 
							if(temp_date.getYear()!=parseFloat(year) || temp_date.getMonth()!=parseFloat(month)-1 || temp_date.getDate()!=parseFloat(day)){
								oAlert(CnName + "不对！");
								return false;
							}
							return true;
						}
					}else if(dyCheckRule.unique == checkRules[k].value) {
						//要求唯一
					
					}
				}
			}
		}
	}
	return true;
}

/**
 * 移除字段上的属性
 */
function removeInputArr(a) {
	$(a).removeAttr("datashow");
	$(a).removeAttr("formeletype");
	$(a).removeAttr("formeleid");
	$(a).removeAttr("uuid");
	$(a).removeAttr("colenname");
	$(a).removeAttr("colcnname");
	$(a).removeAttr("datatype");
	$(a).removeAttr("datalength");
	$(a).removeAttr("applyto");
	$(a).removeAttr("inputmode");
	$(a).removeAttr("optiondatasource");
	$(a).removeAttr("opts");
	$(a).removeAttr("checkrules");
}

/**
 * 时间的格式化
 */
Date.prototype.pattern=function(fmt) {        
    var o = {        
    "M+" : this.getMonth()+1, //月份        
    "d+" : this.getDate(), //日        
    "h+" : this.getHours()%12 == 0 ? 12 : this.getHours()%12, //小时        
    "H+" : this.getHours(), //小时        
    "m+" : this.getMinutes(), //分        
    "s+" : this.getSeconds(), //秒        
    "q+" : Math.floor((this.getMonth()+3)/3), //季度        
    "S" : this.getMilliseconds() //毫秒        
    };        
    var week = {        
    "0" : "\u65e5",        
    "1" : "\u4e00",        
    "2" : "\u4e8c",        
    "3" : "\u4e09",        
    "4" : "\u56db",        
    "5" : "\u4e94",        
    "6" : "\u516d"       
    };        
    if(/(y+)/.test(fmt)){        
        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));        
    }        
    if(/(E+)/.test(fmt)){        
        fmt=fmt.replace(RegExp.$1, ((RegExp.$1.length>1) ? (RegExp.$1.length>2 ? "\u661f\u671f" : "\u5468") : "")+week[this.getDay()+""]);        
    }        
    for(var k in o){        
        if(new RegExp("("+ k +")").test(fmt)){        
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));        
        }        
    }        
    return fmt;        
};

/**
 * 设置了只读跳转url的处理
 */
function setSkipUrl(columns) {
	for(var j=0;j<columns.length;j++){
		var setUrlOnlyRead = columns[j].setUrlOnlyRead;
		var colEnName = columns[j].colEnName;
		if(setUrlOnlyRead != null && setUrlOnlyRead != ""){
			var expandFieldValue = $("#expand_field").val();
			$("#"+colEnName).next("span").css("cursor","pointer");
			$("#"+colEnName).next("span").css("color","#0000FF");
			$("#"+colEnName).next("span").bind("click",{a:setUrlOnlyRead,b:expandFieldValue},function(event) {
				var url = event.data.a;
				var tempValue = event.data.b;
				var tempValueArray = tempValue.split(",");
				for(var k=0;k<tempValueArray.length;k++) {
					var tempValues = tempValueArray[k].split(":");
					if(url.indexOf(tempValues[0]) > -1 ) {
						url = url.replace("${", "").replace("}", "").replace(tempValues[0],tempValues[1]);
					}
				}
				window.open(ctx+url);
			});
		}
	}
}

/**
 * 设置只读状态下的span的样式
 */
function setSpanStyle(elments,textAlign,fontSize,fontColor,fontWidth,fontHight,val) {
	var spanTemp = "style = 'display:block;";
	if(textAlign == "center") {
		spanTemp += "text-align:center;";
	}else if(textAlign == "left") {
		spanTemp += "text-align:left;";
	}else if(textAlign == "right") {
		spanTemp += "text-align:right;";
	}
	
	if(fontSize != null && fontSize != "") {
		spanTemp += "font-size:"+ fontSize+"px;";
	}
	if(fontColor != null && fontColor != "") {
		spanTemp += "color:"+ fontColor+";";
	}
	if(fontWidth != null && fontWidth != "") {
		spanTemp += "width:"+ fontWidth+"px;";
	}
	if(fontHight != null && fontHight != "") {
		spanTemp += "height:"+ fontHight+"px;";
	}
	spanTemp += "'";
	elments.after("<span "+spanTemp+">"+val+"</span>");
}

/**
 * 表单的解析
 */
function initForm(table,columns,editable,formData,mainTableName){
	if(mainTableName == "userform_meeting_rescouce" || mainTableName == "userform_car_info" || mainTableName == "userform_car_info") {
		JDS.call({
			service : "timeEmployService.getTimeEmployList",
			data:[],
			success : function(result) {
				var url1 = "";
				var url2 = "";
					if(mainTableName == "userform_meeting_rescouce") {
						url1 = "<tr class='title'><td class='Label' colspan='6'>会议室占用情况</td></tr>";
					    url2 = "<tr><td class='Label'>会议名称</td><td>会议占用的时间</td></tr>";
					}else if(mainTableName == "userform_car_info") {
						url1 = "<tr class='title'><td class='Label' colspan='6'>车辆占用情况</td></tr>";
					    url2 = "<tr><td class='Label'>驾车人</td><td>车辆占用的时间</td></tr>";
					}else if(mainTableName == "userform_car_info") {
						url1 = "<tr class='title'><td class='Label' colspan='6'>司机占用情况</td></tr>";
					    url2 = "<tr><td class='Label'>司机名</td><td>司机占用的时间</td></tr>";
				}
				var data = result.data;
				$(".dytable_form").find("table")
				.append(url1)
				.append(url2);
				for(var index=0;index<data.length;index++) {
					var appendHtml = "";
					if(index%2 ==0) {
						appendHtml += "<tr style='cursor: pointer;' class='odd employCss' flowUuid='"+data[index].flowUuid+"' taskUuid='"+data[index].taskUuid+"'><td class = 'Label'>"+data[index].employWork+"</td>";
						appendHtml += "<td>"+data[index].beginTime+"--"+data[index].endTime+"</td></tr>";
					}else {
						appendHtml += "<tr style='cursor: pointer;' class='employCss' flowUuid='"+data[index].flowUuid+"' taskUuid='"+data[index].taskUuid+"'><td class = 'Label'>"+data[index].employWork+"</td>";
						appendHtml += "<td>"+data[index].beginTime+"--"+data[index].endTime+"</td></tr>";
					}
					if($("#resource_name").val() == data[index].employName) {
						$(".dytable_form").find("table")
						.append(appendHtml);
					}
					else if($('#vehicle_name').val() == data[index].employName) {
						$(".dytable_form").find("table")
						.append(appendHtml);
					}
				}
				$(".employCss").click(function() {
					var flowUuid = $(this).attr("flowUuid");
					var taskUuid = $(this).attr("taskUuid");
					var url = ctx + "/workflow/work/view/todo?taskUuid="+taskUuid+"&flowInstUuid="+flowUuid+"";
					window.open(url);
				});
			}
		});
	}
	
	//时间占用查看空闲
//	var resourceName;
//	$(".look_free").click(function() {
//		resourceName = $(this).attr("resourceName");
//		getRecords($(this),"","today","",resourceName,"");
//		
//	});
	
	$("#prevWeek").live("click",function(){
		var week = $(this).attr("week");
		var resourceName = $(this).attr("resourceName");
		var field = $(this).attr("field");
		getRecords($(this),week,"notoday","",resourceName,field);
	});
	
	$("#nextWeek").live("click",function(){
		var week = $(this).attr("week");
		var resourceName = $(this).attr("resourceName");
		var field = $(this).attr("field");
		getRecords($(this),week,"notoday","",resourceName,field);
	});
	//日期输入框调用日期控件
	$("#s_today").live("focus",function(){
		var resourceName = $(this).attr("resourceName");
		var field = $(this).attr("field");
		WdatePicker({startDate:'%y-%M-01 00:00:00',
			dateFmt:"yyyy-MM-dd",alwaysUseStartDate:false,
			onpicking:function(dp){
					getRecords($("#s_today"),"","notoday",dp.cal.getNewDateStr(),resourceName,field);
				}
		});
	});
	/**
	 * 计算出占用的时间块(具体到半个小时)
	 */
	function getTimes(begintime,endtime) {
		var begintimes =  begintime.split(" ");
		var bts = begintimes[1].split(":");
		var time1 = bts[1];
		var endtimes =  endtime.split(" ");
		var eds = endtimes[1].split(":");
		var time2 = eds[1];
		
		var a=0,b=0;
		if(parseInt(time1) >=30) {
			a = parseInt(bts[0]) * 2 + parseInt(2);
		}else {
			a = parseInt(bts[0]) * 2 + parseInt(1);
		}
		
		if(parseInt(time2) >=30) {
			b = parseInt(eds[0]) * 2 + parseInt(2);
		}else {
			b = parseInt(eds[0]) * 2 + parseInt(1);
		}
		
		var strs = "";
		var c = parseInt(b) - parseInt(a);
		for(var index = 0;index<c;index++) {
			var x = parseInt(a) + parseInt(index);
			strs += ","+ x;
		}
		return strs.substring(1);
	}
	
	/**
	 * 计算出占用的时间块(具体到分钟)
	 */
	function getTime2s(begintime,endtime) {
		var begintimes =  begintime.split(" ");
		var bts = begintimes[1].split(":");
		var time1 = bts[1];
		var endtimes =  endtime.split(" ");
		var eds = endtimes[1].split(":");
		var time2 = eds[1];
		
		var a=0,b=0,A=0,B=0;
		if(parseInt(time1) == 00) {
			a = parseInt(bts[0]) * 2 + parseInt(1);
			A = parseInt(30);
		}else if(parseInt(time1) == 30){
			a = parseInt(bts[0]) * 2 + parseInt(2);
			A = parseInt(30);
		}else {
			a = parseInt(bts[0]) * 2 + parseInt(1);
			A = parseInt(30) - parseInt(time1);
		}
		
		if(parseInt(time2) == 00) {
			b = parseInt(eds[0]) * 2 + parseInt(1);
			B = parseInt(30);
		}else if(parseInt(time2) == 30) {
			b = parseInt(eds[0]) * 2 + 2;
			B = parseInt(30);
		}else {
			b = parseInt(eds[0]) * 2 + parseInt(1);
			B = parseInt(30) - parseInt(time2);
		}
		
		var strs = "";
		var c = parseInt(b) - parseInt(a);
		for(var index = 0;index<c;index++) {
			var x = parseInt(a) + parseInt(index);
			if(index == 0) {
				strs += ","+ x+"_"+A;
			}else if(parseInt(index) < (parseInt(c)-parseInt(1)) && index>0 ) {
				strs += ","+ x + "_" + "30";
			}else if(parseInt(index) == (parseInt(c)-parseInt(1))) {
				strs += ","+x + "_" + B;
			}
			
		}
		return strs.substring(1);
	}
	
	
	/**
	 * 参数说明：element:调用方法的元素
	 * week:第几周
	 * timeType：时间类型
	 * appointDate： 指定日期
	 * type:资源类型(数据字典获取)
	 */
	function getRecords(element,week,timeType,appointDate,type,field){
		var url = "";
		if(type == "MEET_RESOURCE") {
			url = ctx + '/calendar/calendar_show.action?weekCount='+week+'&appointDate='+appointDate+'&resourceName='+type;
		}else if(type == "CAR_RESOURCE") {
			url = ctx + '/calendar/calendar_show.action?weekCount='+week+'&appointDate='+appointDate+'&resourceName='+type;
		}else if(type == "DRIVER_RESOURCE") {
			url = ctx + '/calendar/calendar_show.action?weekCount='+week+'&appointDate='+appointDate+'&resourceName='+type;
		}
		var x = new Array;
		x = ["changeDiv","changeDiv1","changeDiv2","changeDiv3","changeDiv4","changeDiv5","changeDiv6","changeDiv7","changeDiv8","changeDiv9"];
		$.ajax({
			type : "post",
			url : url,
			success : function(data) {
				var html = "";
				var weekCount = data["weekCount"];
				var today = data["today"];
				var weekList = data["weekList"];
				var weekSplitBg = weekList[0][1].split("-");
				var weekBg = weekSplitBg[1] + "月" + weekSplitBg[2] + "日";//一周的周一的日期
				var weekSplitEd = weekList[6][1].split("-");
				var weekEd = weekSplitEd[1] + "月" + weekSplitEd[2] + "日";//一周的周日的日期
				html += '<div class="toolbar"><table><tr><td align="left"><a class="s_prev_day" href="#" mtype="0" id="prevWeek" resourceName ="'+type+'"  field="'+field+'"  week="'+(weekCount-parseInt(1))+'"><</a><input  type="text"  resourceName ="'+type+'"  field="'+field+'" id="s_today" class="s_today" value="'+today+'" size="7"><a class="s_next_day" href="#" mtype="0"  resourceName ="'+type+'" field="'+field+'" id="nextWeek" week="'+(parseInt(weekCount)+parseInt(1))+'">></a><span class="fromandto">'+weekBg+'至'+weekEd+'</span></td><td></td></tr></table></div>';
				var queryItem = data["queryItem"];
				var tmList = data["tmList"];
				for(var i=0;i<weekList.length;i++) {
					var date = weekList[i][1];
					var dates = date.split("-");
					var dateNew = dates[1]+"月"+dates[2]+"日";
					html += "<table class='weekList_table'>";
					if(i%2 != 0) {
						html += "<tr><td class='calendar_weekList_2'><div class='weekDay'>"+weekList[i][0]+"</div><div class='weedDay2'>"+dateNew+"</div></td>";
					}else {
						html += "<tr><td class='calendar_weekList'><div class='weekDay'>"+weekList[i][0]+"</div><div class='weedDay2'>"+dateNew+"</div></td>";
					}
					if(type == "MEET_RESOURCE") {
						html += "<td class='calendar_rightTd'><table class='title_table'><tr class='calendar_table'><td style='width:80px' class='title_td' ><span class='title_text'>会议室</span></td>";
					}else if(type == "CAR_RESOURCE") {
						html += "<td class='calendar_rightTd'><table class='title_table'><tr class='calendar_table'><td style='width:80px' class='title_td' ><span class='title_text'>车辆类型</span></td>";
					}else if(type == "DRIVER_RESOURCE") {
						html += "<td class='calendar_rightTd'><table class='title_table'><tr class='calendar_table'><td style='width:80px' class='title_td' ><span class='title_text'>司机</span></td>";
					}
					for(var index=1;index<49;index++) {
						var str = "";
						if(parseInt(index)<17) {
							if(index%4 == 0 ){
									str = index/2-2+":00";
									var starstr = (index/2)*2-3;
									var endstr = (index/2)*2;
									html += "<td colspan=4 timestamp='"+starstr+","+endstr+"'>"+str+"</td>";
							}
						}else if(parseInt(index)>=17 && parseInt(index)<=37) {
							if(index%2 == 0 ){
								str = index/2-1 +":00";
								var starstr = index-1;
								var endstr = index;
								html += "<td colspan=2 timestamp='"+starstr+","+endstr+"'>"+str+"</td>";
							}
						}else if(parseInt(index)>37 && parseInt(index)<=49) {
							if((index-parseInt(2))%4 == 0 ){
									var starstr = index-1;
									var endstr = index+2;
									str = (index-parseInt(2))/2-2+parseInt(2)+":00";
										html += "<td colspan=4 timestamp='"+starstr+","+endstr+"'>"+str+"</td>";
							}
						}
					}
					html += "</tr>";
					for(var j=0;j<queryItem.length;j++) {
							x.sort(function(){return Math.random()-0.5});
							if(type == "MEET_RESOURCE") {
								html += "<tr><td class='basic' width='80px' title='"+queryItem[j].title+"'><div class='autocut'>"+queryItem[j].title+"</div></td>";
							}else if(type == "CAR_RESOURCE") {
								html += "<tr><td class='basic' width='80px' title='"+queryItem[j].title+"'><div class='autocut'>"+queryItem[j].title+"</div></td>";
							}else if(type == "DRIVER_RESOURCE") {
								html += "<tr><td class='basic' width='80px' title='"+queryItem[j].title+"'><div class='autocut'>"+queryItem[j].title+"</div></td>";
							}
							window.flag2 = "default";
							for(var k=1;k<49;k++) {
								var flag = 0;
								var k2 = ","+k + ",";
								for(var l=0;l<tmList.length;l++) {
									var xNew = x[0];
									var borderColor = "";
									if(xNew == "changeDiv") {
										borderColor = "#FD6F00";
									}else if(xNew == "changeDiv1") {
										borderColor = "#0B78AD";
									}else if(xNew == "changeDiv2") {
										borderColor = "#D2CB80";
									}else if(xNew == "changeDiv3") {
										borderColor = "#AE9EA6";
									}else if(xNew == "changeDiv4") {
										borderColor = "#3E9448";
									}else if(xNew == "changeDiv5") {
										borderColor = "#89A7B6";
									}else if(xNew == "changeDiv6") {
										borderColor = "#946DD5";
									}else if(xNew == "changeDiv7") {
										borderColor = "#D56D79";
									}else if(xNew == "changeDiv8") {
										borderColor = "#4D3235";
									}else if(xNew == "changeDiv9") {
										borderColor = "#E1C8B9";
									}
									var dateBg = date+" 00:00";
									var dateEnd = date+" 23:59";
									var dateBgDate = new Date(dates[0],dates[1],dates[2],00,00);
									var dateEndDate = new Date(dates[0],dates[1],dates[2],23,59);
//									alert(dateBgDate+"$$$"+dateEndDate);
									var flowUuid = tmList[l].flowUuid;
									var taskUuid = tmList[l].taskUuid
									var tmBg = tmList[l].beginTime;
									var tmBgs = tmBg.split(" ");
									var tmBgs2 = tmBgs[0].split("-");
									var tmBgs3 = tmBgs[1].split(":");
									var tmBgDate = new Date(tmBgs2[0],tmBgs2[1],tmBgs2[2],tmBgs3[0],tmBgs3[1]);
									var tmEnd = tmList[l].endTime;
									var tmEnds = tmEnd.split(" ");
									var tmEnds2 = tmEnds[0].split("-");
									var tmEnds3 = tmEnds[1].split(":");
									var tmEndDate = new Date(tmEnds2[0],tmEnds2[1],tmEnds2[2],tmEnds3[0],tmEnds3[1]);
//									alert(tmBgDate+"###"+tmEndDate);
									//对tmBg..做时间格式化处理？
									var time = "";
									var time2 = "";
									if(dateBgDate<tmBgDate && dateEndDate< tmBgDate)  {//当天时间在占用时间记录段的左侧
										//无处理
									}else if(dateBgDate>tmEndDate && dateEndDate>tmEndDate) {//当天时间在占用时间记录段的右侧
										//无处理
									}else if(dateBgDate<=tmBgDate && dateEndDate>=tmBgDate && dateEndDate<=tmEndDate) {
										//tmBg-dateEnd;
										time = getTimes(tmBg,dateEnd);
										time2 = getTime2s(tmBg,dateEnd);
									}else if(dateBgDate<tmBgDate && dateEndDate>tmEndDate) {
//										var time = tmBg-tmEnd;
										time = getTimes(tmBg,tmEnd);
										time2 = getTime2s(tmBg,tmEnd);
									}else if(dateBgDate>=tmBgDate && dateEndDate<=tmEndDate) {
//										var time = dateBg-dateEnd
										time = getTimes(dateBg,dateEnd);
										time2 = getTime2s(dateBg,dateEnd);
									}else if(dateBgDate>=tmBgDate && dateEndDate>tmEndDate) {
//										var time = dateBg-tmEnd;
										time = getTimes(dateBg,tmEnd);
										time2 = getTime2s(dateBg,tmEnd);
									}
									if(time != "") {
									
									time = ","+time+",";
//									alert("time " +time);
//									var time2 = tmList[l].time2;
									var times2 = time2.split(",");
									var beginTime = tmList[l].beginTime;
									var begintime = beginTime.split(" ");
									var endTime = tmList[l].endTime;
									var endtime = endTime.split(" ");
									var employWork = tmList[l].employWork;
									 var employName = tmList[l].employName;
//									 alert("employWork " + employWork + " employName " + employName);
									var title = employWork + " " + begintime[1] + "-" + endtime[1];
									for(var m=0;m<times2.length;m++) {
										var kAdd = parseInt(k)+parseInt(1);
										var kAdd2 = parseInt(k)+parseInt(3);
										var time2_0 = times2[m].split("_")[0];
//										alert(tmList[l].employWork+"$$$"+queryItem[j].title);
										if(time2_0 == k && tmList[l].employName == queryItem[j].title) {
											if(k>17 && k<=36) {
												if(k%2 == 1) {
													var time2_1 =  times2[m].split("_")[1];//30
													if(m == 0) {
														if(time.indexOf(k2)>=0) {
																html +="<td colspan='2' class='basic change1 left'"+k+"' right'"+kAdd+"'' title='"+title+"' timestamp='"+k+","+kAdd+"'><div class='basicDiv' style='width:50%;float:left;'><div flowUuid='"+flowUuid+"' taskUuid='"+taskUuid+"' class='"+xNew+"' title='"+title+"' style='width:"+time2_1/30*100+"%;float:right;'></div></div></td>";
																flag = 1;
																window.flag2 = "odd";
															}
													}else if(m == (times2.length-1)) {
														if(time.indexOf(k2)>=0) {
																html += "<td colspan='2' class='basic change1 left' style='border-left:1px solid "+borderColor+"' title='"+title+"' timestamp='"+k+","+kAdd+"'><div class='basicDiv' style='width:50%;'><div flowUuid='"+flowUuid+"' taskUuid='"+taskUuid+"' class='"+xNew+"' title='"+title+"' style='width:"+time2_1/30*100+"%;float:left;'></div></div></td>";
																flag = 1;
																window.flag2 = "odd";
															}
													}
													else {
														if(time.indexOf(k2)>=0) {
																html += "<td colspan='2' class='basic change1' style='border-right:1px solid "+borderColor+";border-left:1px solid "+borderColor+"' title='"+title+"' timestamp='"+k+","+kAdd+"'><div class='basicDiv' style='width:50%;float: left;'><div flowUuid='"+flowUuid+"' taskUuid='"+taskUuid+"' class='"+xNew+"' title='"+title+"' style='width:100%;'></div></div></td>";
																flag = 1;
															}
													}
												}else {
													var time2_1 =  times2[m].split("_")[1];//30
													if(m == 0) {
														if(time.indexOf(k2)>=0) {
															html = html.substring(0,html.length-7);
															html += " change1 right' style='border-right:1px solid "+borderColor+"' title='"+title+"'><div class='basicDiv' style='width:50%;float: right;'><div flowUuid='"+flowUuid+"' taskUuid='"+taskUuid+"' class='"+xNew+"' title='"+title+"' style='width:"+time2_1/30*100+"%;float:right;'></div></div></td>";
																flag = 1;
															}
													}else if(m == (times2.length-1)) {
														if(time.indexOf(k2)>=0) {
															html.replace("left"+k,"left");
															html.replace("right"+kAdd,"right");
															html = html.substring(0,html.length-5);
															html += "<div class='basicDiv' style='width:50%;float: right;'><div flowUuid='"+flowUuid+"' taskUuid='"+taskUuid+"' class='"+xNew+"' title='"+title+"' style='width:"+time2_1/30*100+"%;float:left;'></div></div></td>";
															flag = 1;
															window.flag2 = "default";
														}
													}else {
														if(time.indexOf(k2)>=0) {
															html = html.substring(0,html.length-5);
															html += "<div class='basicDiv' style='width:50%;float:right;'><div flowUuid='"+flowUuid+"' taskUuid='"+taskUuid+"' class='"+xNew+"' title='"+title+"' style='width:100%;'></div></div></td>";
															flag = 1;
															window.flag2 = "default";
														}
													}
												}
											}else{
												if(k%4 == 1) {
													var time2_1 =  times2[m].split("_")[1];//30
													if(m == 0) {
														if(time.indexOf(k2)>=0) {
																html +="<td colspan='4' class='basic change1 left'"+k+"' right'"+kAdd2+"''' title='"+title+"' timestamp='"+k+","+kAdd2+"'><div class='basicDiv ol"+k%4+"' style='width:25%;float:left;'><div flowUuid='"+flowUuid+"' taskUuid='"+taskUuid+"' class='"+xNew+"' title='"+title+"' style='width:"+time2_1/30*100+"%;float:right;'></div></div></td>";
																flag = 1;
																window.flag2 = "odd";
															}
													}else if(m == (times2.length-1)) {
														if(time.indexOf(k2)>=0) {
																html += "<td colspan='4' class='basic change1 left' style='border-left:1px solid "+borderColor+"' title='"+title+"' timestamp='"+k+","+kAdd2+"'><div class='basicDiv ol"+k%4+"' style='width:25%;float:left;'><div flowUuid='"+flowUuid+"' taskUuid='"+taskUuid+"' class='"+xNew+"' title='"+title+"' style='width:"+time2_1/30*100+"%;float:left;'></div></div></td>";
																flag = 1;
																window.flag2 = "odd";
															}
													}
													else {
														if(time.indexOf(k2)>=0) {
																html += "<td colspan='4' class='basic change1 middle' style='border-right:1px solid "+borderColor+";border-left:1px solid "+borderColor+"' title='"+title+"' timestamp='"+k+","+kAdd2+"'>" +
																		"<div class='basicDiv ol"+k%4+"' style='width:25%;float: left;'><div flowUuid='"+flowUuid+"' taskUuid='"+taskUuid+"' class='"+xNew+"' title='"+title+"' style='width:100%;'></div></div></td>";
																flag = 1;
															}
													}
												}
												else{
													
													var time2_1 =  times2[m].split("_")[1];//30
													if(m == 0) {
														if(time.indexOf(k2)>=0) {
															html = html.substring(0,html.length-5);
															html += "<div class='basicDiv ol"+k%4+"' style='width:25%;float: left;'><div flowUuid='"+flowUuid+"' taskUuid='"+taskUuid+"' class='"+xNew+"' title='"+title+"' style='width:"+time2_1/30*100+"%;float:right;'></div></div></td>";
																flag = 1;
															}
													}else if(m == (times2.length-1)) {
														if(time.indexOf(k2)>=0) {
															/*html.replace("left"+k,"left");
															html.replace("right"+kAdd,"right");*/
															html = html.replace("middle","left");
															html = html.substring(0,html.length-5);
															html += "<div class='basicDiv ol"+k%4+"' style='width:25%;float: left;'><div flowUuid='"+flowUuid+"' taskUuid='"+taskUuid+"' class='"+xNew+"' title='"+title+"' style='width:"+time2_1/30*100+"%;float:left;'></div></div></td>";
															flag = 1;
															window.flag2 = "default";
														}
													}else {
														if(time.indexOf(k2)>=0) {
															html.replace("left"+k,"left");
															html.replace("right"+kAdd2,"right");
															html = html.substring(0,html.length-5);
															html += "<div class='basicDiv ol"+k%4+"' style='width:25%;float:left;'><div flowUuid='"+flowUuid+"' taskUuid='"+taskUuid+"' class='"+xNew+"' title='"+title+"' style='width:100%;'></div></div></td>";
															flag = 1;
															window.flag2 = "default";
														}
													}
												
												}
											}
											
										}
//										
										}	
									}
								}
								if(flag==0){
									if(k<17) {
										if(k%4==1){
											var starstr = (k/2)*2;
											var endstr = (k/2)*2+3;
											html +="<td colspan=4  timestamp='"+starstr+","+endstr+"' date='"+date+"' meetroomName='"+queryItem[j].title+"' class='basic' ><div class='basicDiv o1' style='width:25%;float:left;'></div></td>";
											window.flag2 = "default";
										}else if(k%4 ==2){
											html = html.substring(0,html.length-5);
											html += "<div class='basicDiv o2' style='width:25%;float:left;'></div></td>";
										}else if(k%4 ==3) {
											html = html.substring(0,html.length-5);
											html += "<div class='basicDiv o3' style='width:25%;float:left;'></div></td>";
										}
										else if(k%4 ==0){
											html = html.substring(0,html.length-5);
											html += "<div class='basicDiv o4' style='width:25%;float:left;'></div></td>";
										}
									}else if(k>36) {
										if(k%4==1){
											var starstr = k;
											var endstr = k+3;
											html +="<td colspan=4 timestamp='"+starstr+","+endstr+"' class='basic' date='"+date+"' meetroomName='"+queryItem[j].title+"'></td>";
											window.flag2 = "default";
										}
									}else {
										if(k%2==1){
											var starstr = k;
											var endstr = k+1;
											html +="<td colspan=2 timestamp='"+starstr+","+endstr+"' class='basic' date='"+date+"' meetroomName='"+queryItem[j].title+"'></td>";
											window.flag2 = "default";
										}
									}
								}
							}
							html += "</tr>";
					}
					html +="</table></td></tr>";
					html +="</table>";
				}
				if(timeType=="today"){
					var json = "";
					if(type == "MEET_RESOURCE") {
						json = {title:"会议室占用情况",closeOnEscape: true,draggable: true,resizable: true,height: 950,width: 1200,content: html,defaultBtnName:"关闭"};
					}else if(type == "CAR_RESOURCE") {
						json = {title:"车辆占用情况",closeOnEscape: true,draggable: true,resizable: true,height: 950,width: 1200,content: html,defaultBtnName:"关闭"};
					}else if(type == "DRIVER_RESOURCE") {
						json = {title:"司机占用情况",closeOnEscape: true,draggable: true,resizable: true,height: 950,width: 1200,content: html,defaultBtnName:"关闭"};
					}
					showDialog(json);
				}else{
					element.parents(".dialogcontent").html(html);
				}
			},
			Error : function(data) {
				oAlert(data);
			}
			
			
		});
		
		$(".basic").die().live("click",function(e) {
			var class_ = $(e.target).attr("class");
			var begin = "";
			var end = "";
			var timestamp = $(this).attr("timestamp");
			var date = $(this).attr("date");
			if(timestamp == "1,4") {
				begin = date+" 00:00";
				end = date+" 02:00";
			}
			else if(timestamp == "5,8") {
				begin = date+" 02:00";
				end = date+" 04:00";
			}
			else if(timestamp == "9,12") {
				begin = date+" 04:00";
				end = date+" 06:00";
			}
			else if(timestamp == "13,16") {
				begin = date+" 06:00";
				end = date+" 08:00";
			}
			else if(timestamp == "17,18") {
				begin = date+" 08:00";
				end = date+" 09:00";
			}
			else if(timestamp == "19,20") {
				begin = date+" 09:00";
				end = date+" 10:00";
			}
			else if(timestamp == "21,22") {
				begin = date+" 10:00";
				end = date+" 11:00";
			}
			else if(timestamp == "23,24") {
				begin = date+" 11:00";
				end = date+" 12:00";
			}
			else if(timestamp == "25,26") {
				begin = date+" 12:00";
				end = date+" 13:00";
			}
			else if(timestamp == "27,28") {
				begin = date+" 13:00";
				end = date+" 14:00";
			}
			else if(timestamp == "29,30") {
				begin = date+" 14:00";
				end = date+" 15:00";
			}
			else if(timestamp == "31,32") {
				begin = date+" 15:00";
				end = date+" 16:00";
			}
			else if(timestamp == "33,34") {
				begin = date+" 16:00";
				end = date+" 17:00";
			}
			else if(timestamp == "35,36") {
				begin = date+" 17:00";
				end = date+" 18:00";
			}
			else if(timestamp == "37,40") {
				begin = date+" 18:00";
				end = date+" 20:00";
			}
			else if(timestamp == "41,44") {
				begin = date+" 20:00";
				end = date+" 22:00";
			}
			else if(timestamp == "45,48") {
				begin = date+" 22:00";
				end = date+" 23:59";
			}
			if(class_.indexOf("changeDiv")>=0) {
				
			}else if(type == "MEET_RESOURCE"){
				var html = '<div class="newAppend" style="width:100%;"><div class="changeDiv4"  style="width:100%;float:left;"></div></div>';
				 $(this).children().remove();
				$(this).append(html);
				
				var meetroomname = $(this).attr("meetroomname");
				$(".newAppend").click(function() {
					if(confirm("确定增加一笔记录?")){
//						$("#meeting_begintime").val(begin);
//						$("#meeting_endtime").val(end);
						element.val(meetroomname);
						element.next().val(type+";"+meetroomname);
						$("#"+field.split(",")[0]).val(begin);
						$("#"+field.split(",")[1]).val(end);
						closeDialog();
					}
				});
			}else if(type == "CAR_RESOURCE") {
				var html = '<div class="newAppend" style="width:100%;"><div class="changeDiv4"  style="width:100%;float:left;"></div></div>';
				 $(this).children().remove();
					$(this).append(html);
					var meetroomname = $(this).attr("meetroomname");
					$(".newAppend").click(function() {
						if(confirm("确定增加一笔记录?")){
//							$("#datetime_begin").val(begin);
//							$("#datetime_end").val(end);
							$("#"+field.split(",")[0]).val(begin);
							$("#"+field.split(",")[1]).val(end);
							element.val(meetroomname);
							element.next().val(type+";"+meetroomname);
							closeDialog();
						}
					});
			}else if(type == "DRIVER_RESOURCE") {
				var html = '<div class="newAppend" style="width:100%;"><div class="changeDiv4"  style="width:100%;float:left;"></div></div>'
					 $(this).children().remove();
					$(this).append(html);
					var meetroomname = $(this).attr("meetroomname");
					$(".newAppend").click(function() {
						if(confirm("确定增加一笔记录?")){
//							$("#datetime_begin").val(begin);
//							$("#datetime_end").val(end);
							$("#"+field.split(",")[0]).val(begin);
							$("#"+field.split(",")[1]).val(end);
							element.val(meetroomname);
							element.next().val(type+";"+meetroomname);
							closeDialog();
						}
					});
			}
		});
	}
	
	//点击占用的时间块打开一笔时间占用的记录
	$(".basic .basicDiv div").live("click",function() {
		var flowUuid = $(this).attr("flowUuid");
		var taskUuid = $(this).attr("taskUuid");
		var url = ctx + "/workflow/work/view/todo?taskUuid="+taskUuid+"&flowInstUuid="+flowUuid+"";
		window.open(url);
	});
	
	var isMod = editable;//(dataUid != '' ? true : false);
	var cols = ',';
	var inputArr = $(table).find('input,textarea,select,button');
	//主表表单元素替换成easyui(日期)
	for(var i=0;i<inputArr.length;i++){
		for(var j=0;j<columns.length;j++){
			var colEnName,colCnName,inputMode,rules,options,dataType,colHidden,colEdit,dataShow,serviceName,methodName,data,designatedId,designatedType,isOverride,unEditDesignatedId,unEditIsSaveDb,defaultValue,fontWidth,fontHight,textAlign,isHide,defaultValueWay,inputDataType,
			relationDataTwoSql,relationDataText,relationDataValue,relationDataSql,relationDataShowType,relationDataShowMethod,relationDataTextTwo,relationDataValueTwo,relationDataDefiantion;
			dataLength = columns[j].dataLength;
			floatSet = columns[j].floatSet;
			dataShow = columns[j].dataShow;
			defaultValue = columns[j].defaultValue;
			fontSize = columns[j].fontSize;
			fontColor = columns[j].fontColor;
			setUrlOnlyRead = columns[j].setUrlOnlyRead;
			jsFunction = columns[j].jsFunction;//JS函数自定义
			ctrlField = columns[j].ctrlField;
			/***ruan start****/
			fontWidth = columns[j].fontWidth;
			fontHight = columns[j].fontHight;
			textAlign = columns[j].textAlign;
			isHide = columns[j].isHide;
			dataType = columns[j].dataType;
			defaultValueWay = columns[j].defaultValueWay;
			inputDataType = columns[j].inputDataType;
			relationDataTwoSql = columns[j].relationDataTwoSql;
			relationDataText = columns[j].relationDataText;
			relationDataValue = columns[j].relationDataValue;
			relationDataSql = columns[j].relationDataSql;
			relationDataShowType = columns[j].relationDataShowType;//xml的解析方法
			relationDataShowMethod = columns[j].relationDataShowMethod;
			relationDataTextTwo = columns[j].relationDataTextTwo;
			relationDataValueTwo = columns[j].relationDataValueTwo;
			relationDataDefiantion = columns[j].relationDataDefiantion;
			
			/***ruan end****/
			if(setReadOnly == true) {
				dataShow = dyFormDataShow.indirect;
			}else if(setReadOnly == false) {
				if(dataShow == "2") {
					dataShow = columns[j].dataShow;
				}else {
					dataShow = dyFormDataShow.directShow;
				}
			}else if(setReadOnly == null) {
				dataShow = columns[j].dataShow;
			}
			else {
				dataShow = dyFormDataShow.directShow;
			}
			colEnName = columns[j].colEnName;
			
			var nodeName = inputArr[i].name;
			if(nodeName=='expand_field'){
				var tempi = i;
				
			}
			colCnName = columns[j].colCnName;
			inputMode = columns[j].inputMode;
			serviceName = columns[j].serviceName;
			methodName = columns[j].methodName;
			data = columns[j].data;
			designatedId = columns[j].designatedId;
			designatedType = columns[j].designatedType;
			isOverride = columns[j].isOverride;
			unEditDesignatedId = columns[j].unEditDesignatedId;
			unEditIsSaveDb = columns[j].unEditIsSaveDb;
			rules = columns[j].checkRules;
			options = columns[j].options;
			colHidden = columns[j].hidden;
			colEdit = columns[j].edit;
			uploadFileType = columns[j].uploadFileType;
			isGetZhengWen = columns[j].isGetZhengWen;
			var val = eval('formData.' + colEnName);
//			alert("colEnName " + colEnName + " val " + val);
			if($("#signature_") != undefined) {
				var signatureValue = eval('formData.signature_');
				$("#signature_").val(signatureValue);
			}
			
			if(inputArr[i].name == colEnName){
				
				if(textAlign == "center") {
					$(inputArr[i]).css("text-align","center");
				}else if(textAlign == "left") {
					$(inputArr[i]).css("text-align","left");
				}else if(textAlign == "right") {
					$(inputArr[i]).css("text-align","right");
				}
				
				if(fontSize != null && fontSize != "") {
					$(inputArr[i]).css("font-size",fontSize+"px");
				}
				if(fontColor != null && fontColor != "") {
					$(inputArr[i]).css("color",fontColor);
				}
				/***ruan 增加的样式定义***/
				if(fontWidth != null && fontWidth != "") {
					$(inputArr[i]).css("width",fontWidth+"px");
				}
				if(fontHight != null && fontHight != "") {
					$(inputArr[i]).css("height",fontHight+"px");
				}
				if(isHide != null && isHide != "" && isHide==elementShow.hidden) {
					var tdLength = $(inputArr[i]).parents("tr").find("td").length;
					if(tdLength >2) {
						$(inputArr[i]).parents("td").prev().css("display","none");
						$(inputArr[i]).parents("td").css("display","none");
						$(inputArr[i]).parents("tr").find(".value").eq(0).attr("colspan",parseInt(tdLength+1));
					}else {
						$(inputArr[i]).parents("tr").css("display","none");
					}
				}
				/***ruan 增加的样式定义***/
				
				//验证的脚本
				var isFirst = false;
				if(cols.indexOf(',' + colEnName + ',') < 0){
					isFirst = true;
					cols += ',' + colEnName + ',';
					if(colEnName != "files" && colEnName != "body_col" && colEnName != "expand_field") {
					$(inputArr[i]).parent().append('<div class="info"><span class="Validform_checktip"></span><span class="dec"><s class="dec1">&#9670;</s><s class="dec2">&#9670;</s></span></div>');
					}    
				}
			 
				 
				
				if(defaultValueWay== dyFormInputValue.jsEquation&&jsFunction != null && jsFunction != "") {
					var colEn = colEnName;
					var jsF = "";
					var jsFunction_ = jsFunction;
					//替换js函数中的所有运算符号
					jsF = jsFunction.replace(/\+/g,"@").replace(/\-/g,"@").replace(/\*/g,"@").replace(/\//g,"@");
					var jsFs = jsF.split("@");
					for(var i1=0;i1<jsFs.length;i1++){
						$(".Label").each(function() {
							if(jsFs[i1] == $(this).text()) {
								var id = $(this).next().children().attr("id");
								$("#"+id).attr("class","jsFunction");
								jsFunction_ = jsFunction_.replace(jsFs[i1],id);
								jsF = jsF.replace(jsFs[i1],id);
							}
						});
					}
					$(".jsFunction").unbind("blur");
					$(".jsFunction").live("blur",function() {
						var jsFunction_2 = jsFunction_;
						var flag = 1;
						$(".jsFunction").each(function() {
							if($(this).val() =="" && flag == 1) {
								flag = 0;
							}
						});
						if(flag == 1) {
							var jsfNew = jsF.split("@");
							for(var i2=0;i2<jsfNew.length;i2++) {
								var a = $("#"+jsfNew[i2]).val();
								jsFunction_2 = jsFunction_2.replace(jsfNew[i2],a);
							}
							$("#"+colEn).val(parseInt(eval(jsFunction_2)));
						}
					});
				}
				else if (defaultValueWay == dyFormInputValue.relationDoc){//关联文档
					var path = "/basicdata/dyview/view_show?viewUuid="+relationDataValue+"&currentPage=1&openBy=dytable";
					var parmArray = new Array();
					var relationDataSql2 = $(inputArr[i]).attr("relationdatasql");
					while(relationDataSql2.indexOf("${")>-1){
						var s1=relationDataSql2.match("\\${.*?\\}")+"";
						parmArray.push(s1.replace("${", "").replace("}", ""));
						relationDataSql2 = relationDataSql2.replace(s1,"");
					}
					path += "&"+$(inputArr[i]).attr("relationdatasql");
					for(var jt=0;jt<parmArray.length;jt++){
						if(eval('formData.'+parmArray[jt])!=undefined&&eval('formData.'+parmArray[jt])!=""&&eval('formData.'+parmArray[jt])!="undefined"){
							path = path.replace("${"+parmArray[jt]+"}",eval('formData.'+parmArray[jt])) ;
						}
					}
					if(path.indexOf("${")>-1){
						$(inputArr[i]).after("<span>没有相应记录</span>");
						$(inputArr[i]).hide();
					}else{
						$.ajax({
							async:false,
							url : ctx + path,
							success : function(data) {
								$(inputArr[i]).after(data);
								$(inputArr[i]).hide();
							}
						});
					}
				}else if (defaultValueWay== dyFormInputValue.creatOperation){
					
				}else if (defaultValueWay== dyFormInputValue.showOperation){
					
				}else if (defaultValueWay== dyFormInputValue.twoDimensionCode){
	
				}else if (defaultValueWay== dyFormInputValue.shapeCod){
	
				}
				if(dyFormInputType.date == dataType){//日期替换
//					alert("日期替换");
					removeInputArr(inputArr[i]);
					$(inputArr[i]).val(defaultValue);
					if(dataShow == dyFormDataShow.indirect){
						$(inputArr[i]).hide();
						if(val == undefined) {
							if(defaultValue == "" || defaultValue == null) {
								$(inputArr[i]).after("<span></span>");
							}else {
								setSpanStyle($(inputArr[i]),textAlign,fontSize,fontColor,fontWidth,fontHight,defaultValue);
							}
						}else {
							setSpanStyle($(inputArr[i]),textAlign,fontSize,fontColor,fontWidth,fontHight,val);
								$(inputArr[i]).val(val);
						}
					}else {
						my97Datetime($(inputArr[i]).attr('id'),'yyyy-MM-dd');
						if(isMod){
							$(inputArr[i]).attr('value',val);
						}else {
							$(inputArr[i]).val(defaultValue);
						}
					}
				}
				else if(dyFormInputType.dateTimeMin == dataType || dyFormInputType.dateTimeSec == dataType){//日期到分,到秒
//					alert("日期到分,到秒");
					removeInputArr(inputArr[i]);
					if(dataShow == dyFormDataShow.indirect){
						$(inputArr[i]).hide();
						if(val == undefined) {
							if(defaultValue == "" || defaultValue == null) {
								$(inputArr[i]).after("<span></span>");
							}else {
								setSpanStyle($(inputArr[i]),textAlign,fontSize,fontColor,fontWidth,fontHight,defaultValue);
								$(inputArr[i]).val(defaultValue);
							}
						}else {
							setSpanStyle($(inputArr[i]),textAlign,fontSize,fontColor,fontWidth,fontHight,val);
								$(inputArr[i]).val(val);
						}
					}else {
						if(dyFormInputType.dateTimeMin == dataType){
							my97Datetime($(inputArr[i]).attr('id'),'yyyy-MM-dd HH:mm');
						}else{
							my97Datetime($(inputArr[i]).attr('id'),'yyyy-MM-dd HH:mm:ss');
						}
						if(isMod){
							$(inputArr[i]).attr('value',val);
						}
					}
				}else if(dyFormInputType.timeMin == dataType || dyFormInputType.timeSec == dataType){//时间到分,到秒
//					alert("时间到分,到秒");
					removeInputArr(inputArr[i]);
					if(dataShow == dyFormDataShow.indirect){
						$(inputArr[i]).hide();
						if(val == undefined) {
							if(defaultValue == "" || defaultValue == null) {
								$(inputArr[i]).after("<span></span>");
							}else {
								setSpanStyle($(inputArr[i]),textAlign,fontSize,fontColor,fontWidth,fontHight,defaultValue);
							}
						}else {
							setSpanStyle($(inputArr[i]),textAlign,fontSize,fontColor,fontWidth,fontHight,val);
								$(inputArr[i]).val(val);
						}
					}else {
						if(dyFormInputType.timeMin == dataType){
							my97Datetime($(inputArr[i]).attr('id'),'HH:mm');
						}else{
							my97Datetime($(inputArr[i]).attr('id'),'HH:mm:ss');
						}
						if(isMod){
							$(inputArr[i]).attr('value',val);
						}
					}
				}
				else if(dyFormInputMode.treeSelect == inputDataType) {//树形下拉框
//					alert("树形下拉框");
					removeInputArr(inputArr[i]);
					var showId = $(inputArr[i]).attr('id');
					var servicename = serviceName.split(".")[0]; //服务名
					var method = serviceName.split(".")[1].split("(")[0]; //方法名
					var datas = serviceName.split(".")[1].split("(")[1].replace(")",""); //数据
					var data = datas.split(",")[0];
					data = data.replace("\'","").replace("\'","");
					var data2 = datas.split(",")[1];
					var data3 = "";
					if(data2 != "" && data2 != null) {
						data3 = data2;
					}else {
						data3 = false;
					}
					$(inputArr[i]).after("<input type='hidden' id='_"+colEnName+"' name='_"+colEnName+"' value=''>");
					var data1 = data.replace(/\|+/g,"");
					if($('#data').val() == undefined) {
						$(inputArr[i]).after("<input type='hidden' id='"+data1+"'  value='"+data.replace(/\|+/g,",")+"'>");
					}else {
						$(inputArr[i]).after("<input type='hidden' id='"+data1+"'  value='"+data.replace(/\|+/g,",")+"'>");
					}
					var flag = true;
					if(method[0] == 'getFormDataAsTree') {
						 flag = false;
					}else {
						 flag = true;
					}
					var dataArrays = new Array();
					if($("#"+data1).val().split(",").length >1) {
						var dataArray = $("#"+data1).val().split(",");
						for(var da = 0; da<dataArray.length;da++) {
							dataArrays.push(dataArray[da]);
						}
					}else if($("#"+data1).val().indexOf("${data}") > 0){
						dataArrays.push($("#data").val());
					}else {
						dataArrays.push($("#"+data1).val());
					}
					if(isMod){
						if(val != undefined && methodName != "" && methodName != null) {
							var method = methodName.split(".");
							var servicename = method[0];
							var Method = method[1].split("(")[0]; //方法名
							var datas = method[1].split("(")[1].replace(")",""); //数据
							var data = datas.split(",")[0];
							data = data.replace("\'","").replace("\'","");
							var initService = servicename+ "."+Method;
							var VAL = val;
							JDS.call({
								async:false,
								service : initService,
								data : [data,val],
								success : function(result) {
									var data = result.data;
									$("#"+showId).attr('value',data.label);
								}
							});
						}else {
							if(val != undefined && val.indexOf(";")>0) {
								var _val = val.split(";");
								$(inputArr[i]).attr('value',_val[1]);
							}else {
								$(inputArr[i]).val(val);
								if(methodName == "" || methodName == null) {
									JDS.call({
										async:false,
										service : servicename+"."+method,
										data : ["-1",$("#"+data1).val()],
										success : function(result) {
											var data = result.data;
											for(var l=0;l<data.length;l++) {
												if(val == data[l].data) {
													$(inputArr[i]).val(data[l].name);
												}
											}
										}
									});
								}
							}
							
						}
					}
					if(dataShow == dyFormDataShow.indirect){
						$(inputArr[i]).hide();
						if(val != undefined) {
							setSpanStyle($(inputArr[i]),textAlign,fontSize,fontColor,fontWidth,fontHight,$("#"+showId).attr('value'));
						}else {
							if(defaultValue == "" || defaultValue == null) {
								$(inputArr[i]).after("<span></span>");
							}else {
								setSpanStyle($(inputArr[i]),textAlign,fontSize,fontColor,fontWidth,fontHight,defaultValue);
							}
						}
					}else {
						var setting = {
								async : {
									otherParam : {
										"serviceName" : servicename,
										"methodName" : method,
										"data" : dataArrays
									}
								},
								check : {//复选框的选择做成可配置项
									enable:data3
								},
								callback : {
									onClick:function (event, treeId, treeNode) {
										var inputId = treeId.replace("_ztree","");
										if(typeof(treeNode.data) == "object") {
											$("#"+inputId).val(treeNode.name);
											$("#_"+inputId).val(treeNode.data.reservedText1+";"+treeNode.name);
										}else {
											$("#"+inputId).val(treeNode.name);
											$("#_"+inputId).val(treeNode.data);
										}
									}
								}
							};
						$(inputArr[i]).comboTree({
							labelField : colEnName,
							valueField : "_"+colEnName,
							treeSetting : setting
						});
						$("#_"+colEnName).val(val);
					}
				}
				else if(dyFormInputMode.ckedit == inputDataType) {//html编辑器
//					alert("html编辑器");
					removeInputArr(inputArr[i]);
					var id = $(inputArr[i]).attr('id');
					if(dataShow == dyFormDataShow.indirect){
						//清除编辑器
						var instance = CKEDITOR.instances[id];
						if (instance) { 
							CKEDITOR.remove(instance);
						}
						$(inputArr[i]).hide();
						if(val != null && val != "") {
							setSpanStyle($(inputArr[i]),textAlign,fontSize,fontColor,fontWidth,fontHight,val);
						}else {
							$(inputArr[i]).after("<span></span>");
						}
					}else {
						if(colEdit == true) {
							//清除编辑器
							var instance = CKEDITOR.instances[id];
							if (instance) { 
								CKEDITOR.remove(instance);
							}
							 //初始化编辑器
							var editor = CKEDITOR.replace( id, {  
									allowedContent:true,
									enterMode: CKEDITOR.ENTER_P,
									//工具栏
									toolbar: [ 
									          ['Bold','Italic','Underline'], ['Cut','Copy','Paste'], 
									          ['NumberedList','BulletedList','-'], 
									          ['JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock'],
									          ['Link','Unlink'],['Format','Font','FontSize'],['TextColor','BGColor'],
									          ['Image','Table','Smiley'],['Source','Maximize'],['formfile']
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
							editor.setData(val);
						}else if(colEdit == false){
							$('#'+id).attr("disabled","disabled");
							$('#'+id).html(val);
						}
					}
				}
				else if(dyFormInputMode.serialNumber == inputDataType) {//可编辑流水号
//					alert("可编辑流水号");
					removeInputArr(inputArr[i]);
					var id = $(inputArr[i]).attr('id');
					if(dataShow == dyFormDataShow.indirect){
						$(inputArr[i]).hide();
						if(isMod){
							if(val) {
								$(inputArr[i]).val(val);
									$(inputArr[i]).after("<span>"+val+"</span>");
							}else {
								if(defaultValue == "" || defaultValue == null) {
									$(inputArr[i]).after("<span></span>");
								}else {
									setSpanStyle($(inputArr[i]),textAlign,fontSize,fontColor,fontWidth,fontHight,defaultValue);
								}
							}
						}
					
					}else {
						$(inputArr[i]).val(val);
						var designatedId_ = designatedId;
						var designatedType_ = designatedType;
						var isOverride_ = isOverride;
						$("#"+id).live("click",function() {
							getEditableSerialNumber(designatedId_,designatedType_,parseInt(isOverride_),formUuid,$(this).attr("id"),$(this).attr("id"));
						});
					}
				}
				else if(dyFormInputMode.unEditSerialNumber == inputDataType) {//不可编辑流水号
//					alert("不可编辑流水号");
					removeInputArr(inputArr[i]);
					var id = $(inputArr[i]).attr('id');
					if(dataShow == dyFormDataShow.indirect){
						$(inputArr[i]).hide();
						if(isMod){
							if(val) {
								setSpanStyle($(inputArr[i]),textAlign,fontSize,fontColor,fontWidth,fontHight,val);
							}else {
								if(defaultValue == "" || defaultValue == null) {
									$(inputArr[i]).after("<span></span>");
								}else {
									setSpanStyle($(inputArr[i]),textAlign,fontSize,fontColor,fontWidth,fontHight,defaultValue);
								}
							}
						}
					
					}else {
						var unEditDesignatedId = unEditDesignatedId;
						var unEditIsSaveDb = unEditIsSaveDb;
						var isOverride_ = isOverride;
						if(val != undefined) {
							$(inputArr[i]).val(val);
						}else {
							var snValue = getNotEditableSerialNumberForDytable(unEditDesignatedId,unEditIsSaveDb,formUuid,$(inputArr[i]).attr("id"));
							$(inputArr[i]).val(snValue);
						}
					}
				}
				//附件 accessory1图标显示,accessory2图标显示（含正文）
				else if(//isFirst //该段代码放在一个两层循环里，这里是为了防止该段代码被重复执行两次
					//&&
					(dyFormInputMode.accessory1 == inputDataType || dyFormInputMode.accessory2 == inputDataType)) { 
					 
//					alert("附件 accessory1图标显示,accessory2图标显示（含正文)");
					removeInputArr(inputArr[i]); 
					var id = $(inputArr[i]).attr('id'); 
					var $attachtr = $(inputArr[i]).parent().parent();
					$attachtr.removeAttr("class");
					$attachtr.show();
					//console.log($attachtr.attr("style"));
					var colspan = 0; 
					$attachtr.find("td").each(function(){
						 var colspanAttr = $(this).attr("colspan");
						 if(typeof(colspanAttr) == "undefined"){
							colspan += 1;
						 }else{
							colspan += parseInt(colspanAttr);
						 }
					});
					$attachtr.html("<td colspan='" + colspan + "'></td>");
					var downable = (supportDown != '2');//是否有下载权限
					var uploadable = (dataShow != 2);//是否有上传的权限
					var signature = (formSign == "2");//是否需要签名
					  
					var $attachContainer = $attachtr.find("td");
				 
				 
					
					 
					//创建上传控件
					////console.log( "1:" + dataUuid + ":" +  id);
						 //创建上传控件
					 var elementID = WellFileUpload.getCtlID4Dytable(mainTableName, id, 0);
					 
					var fileupload = new WellFileUpload4Icon(elementID);
					  
					//初始化上传控件
					fileupload.initWithLoadFilesFromFileSystem(uploadable,//是否则有上传的权限 
					downable,//是否具有下载的权限 
					$attachContainer,//存放该附件的容器 
					signature,//是否签名
					dataUuid,
					id
					);	
				}
				//附件 accessory3显示（不含正文）
				else if( isFirst //该段代码放在一个两层循环里，这里是为了防止该段代码被重复执行两次
					&& dyFormInputMode.accessory3 == inputDataType) {
					 
//					  //在这里处理 列表式的附件
					var id = $(inputArr[i]).attr('id');//字段名称
					var $attachContainer = $(inputArr[i]).parent(); //上传控件要存放的位置,为jquery对象 
					  
					 //创建上传控件
					 var elementID = WellFileUpload.getCtlID4Dytable(mainTableName, id, 0);
					 
					 var fileupload = new WellFileUpload(elementID);

					//初始化上传控件
					fileupload.initWithLoadFilesFromFileSystem(setReadOnly,  $attachContainer,  formSign == "2", true, dataUuid , id);


				}
				else if(dyFormInputMode.textBody == inputMode) {
					  
				}else if(dyFormInputMode.orChoose4 == inputDataType) {//单位通讯录
//					alert("单位通讯录");
					removeInputArr(inputArr[i]);
					var id = $(inputArr[i]).attr('id');
					var name = $(inputArr[i]).attr('name');
					if(dataShow == dyFormDataShow.indirect){
						$(inputArr[i]).hide();
						if(isMod){
							if(val) {
								var valStr = new Array();
								valStr = val.split(",");
								for(var k=0;k<valStr.length;k++) {
									$(inputArr[i]).attr('value',valStr[0]);
									$(inputArr[i]).attr('hiddenValue',valStr[1]);
								}
								setSpanStyle($(inputArr[i]),textAlign,fontSize,fontColor,fontWidth,fontHight,valStr[0]);
							}else {
								if(defaultValue == "" || defaultValue == null) {
									$(inputArr[i]).after("<span></span>");
								}else {
									setSpanStyle($(inputArr[i]),textAlign,fontSize,fontColor,fontWidth,fontHight,defaultValue);
								}
							}
						}
					
					}else {
						if(name == "fawen_send" || name == "fawen_copy") {
							$("#" + id).mousedown(function(){
								var labelField = $(this).attr("id");
								$.unit.open({
									labelField:labelField, 
									type : "Unit",
									commonType : 2,
									close:function(){
										$("#"+labelField).focus();
										$("#"+labelField).blur();
									}
								}
								);
							});
						}else {
							$("#" + id).mousedown(function(){
								var labelField = $(this).attr("id");
								$.unit.open({
									labelField:labelField, 
									type : "Unit",
									commonType : 2,
									close:function(){
										$("#"+labelField).focus();
										$("#"+labelField).blur();
									}
								}
								);
							});
						}
						if(isMod){
							if(val) {
								var valStr = new Array();
								valStr = val.split(",");
								for(var k=0;k<valStr.length;k++) {
									$(inputArr[i]).attr('value',valStr[0]);
									$(inputArr[i]).attr('hiddenValue',valStr[1]);
								}
							}
						}
					}
				}
				else if(dyFormInputMode.orChoose3 == inputDataType) {//组织选择框(人员+部门)
//					alert("组织选择框(人员+部门)");
					removeInputArr(inputArr[i]);
					var id = $(inputArr[i]).attr('id');
					var name = $(inputArr[i]).attr('name');
					if(dataShow == dyFormDataShow.indirect){
						$(inputArr[i]).hide();
						if(isMod){
							if(val) {
								var valStr = new Array();
								valStr = val.split(",");
								for(var k=0;k<valStr.length;k++) {
									$(inputArr[i]).attr('value',valStr[0]);
									$(inputArr[i]).attr('hiddenValue',valStr[1]);
								}
								setSpanStyle($(inputArr[i]),textAlign,fontSize,fontColor,fontWidth,fontHight,valStr[0]);
							}else {
								if(defaultValue == "" || defaultValue == null) {
									$(inputArr[i]).after("<span></span>");
								}else {
									setSpanStyle($(inputArr[i]),textAlign,fontSize,fontColor,fontWidth,fontHight,defaultValue);
								}
							}
						}
					
					}else {
						if(name == "fawen_send" || name == "fawen_copy") {
							$("#" + id).mousedown(function(){
								var labelField = $(this).attr("id");
								$.unit.open({
									labelField:labelField, 
									close:function(){
										$("#"+labelField).focus();
										$("#"+labelField).blur();
									}
								}
								);
							});
						}else {
							$("#" + id).mousedown(function(){
								var labelField = $(this).attr("id");
								$.unit.open({
									labelField:labelField, 
									close:function(){
										$("#"+labelField).focus();
										$("#"+labelField).blur();
									}
								}
								);
							});
						}
						if(isMod){
							if(val) {
								var valStr = new Array();
								valStr = val.split(",");
								for(var k=0;k<valStr.length;k++) {
									$(inputArr[i]).attr('value',valStr[0]);
									$(inputArr[i]).attr('hiddenValue',valStr[1]);
								}
							}
						}
					}
				}else if(dyFormInputMode.orChoose2 == inputDataType) {//组织选择框(仅选择组织部门)
//					alert("组织选择框(仅选择组织部门)");
					removeInputArr(inputArr[i]);
					var id = $(inputArr[i]).attr('id');
					var name = $(inputArr[i]).attr('name');
					if(dataShow == dyFormDataShow.indirect){
						$(inputArr[i]).hide();
						if(isMod){
							if(val) {
								var valStr = new Array();
								valStr = val.split(",");
								for(var k=0;k<valStr.length;k++) {
									$(inputArr[i]).attr('value',valStr[0]);
									$(inputArr[i]).attr('hiddenValue',valStr[1]);
								}
								setSpanStyle($(inputArr[i]),textAlign,fontSize,fontColor,fontWidth,fontHight,valStr[0]);
							}else {
								if(defaultValue == "" || defaultValue == null) {
									$(inputArr[i]).after("<span></span>");
								}else {
									setSpanStyle($(inputArr[i]),textAlign,fontSize,fontColor,fontWidth,fontHight,defaultValue);
									$(inputArr[i]).val(defaultValue);
								}
							}
						}
					
					}else {
						if(name == "fawen_send" || name == "fawen_copy") {
							$("#" + id).mousedown(function(){
								var labelField = $(this).attr("id");
								$.unit.open({
									labelField:labelField, 
									type : "Dept",
									selectType : 2,
									close:function(){
										$("#"+labelField).focus();
										$("#"+labelField).blur();
									}
								}
								);
							});
						}else {
							$("#" + id).mousedown(function(){
								var labelField = $(this).attr("id");
								$.unit.open({
									labelField:labelField, 
									type : "Dept",
									selectType : 2,
									close:function(){
										$("#"+labelField).focus();
										$("#"+labelField).blur();
									}
								}
								);
							});
						}
						if(isMod){
							if(val) {
								var valStr = new Array();
								valStr = val.split(",");
								for(var k=0;k<valStr.length;k++) {
									$(inputArr[i]).attr('value',valStr[0]);
									$(inputArr[i]).attr('hiddenValue',valStr[1]);
								}
							}
						}
					}
				}
				else if(dyFormInputMode.orChoose1 == inputDataType) {//组织选择框(人员)
//					alert("组织选择框(人员)");
					removeInputArr(inputArr[i]);
					var id = $(inputArr[i]).attr('id');
					var name = $(inputArr[i]).attr('name');
					if(dataShow == dyFormDataShow.indirect){
						$(inputArr[i]).hide();
						if(isMod){
							if(val) {
								var valStr = new Array();
								valStr = val.split(",");
								for(var k=0;k<valStr.length;k++) {
									$(inputArr[i]).attr('value',valStr[0]);
									$(inputArr[i]).attr('hiddenValue',valStr[1]);
								}
								setSpanStyle($(inputArr[i]),textAlign,fontSize,fontColor,fontWidth,fontHight,valStr[0]);
							}else {
								if(defaultValue == "" || defaultValue == null) {
									$(inputArr[i]).after("<span></span>");
								}else {
									setSpanStyle($(inputArr[i]),textAlign,fontSize,fontColor,fontWidth,fontHight,defaultValue);
								}
							}
						}
					
					}else {
						if(name == "fawen_send" || name == "fawen_copy") {
							$("#" + id).mousedown(function(){
								var labelField = $(this).attr("id");
								$.unit.open({
									labelField:labelField, 
									selectType : 4,
									close:function(){
										$("#"+labelField).focus();
										$("#"+labelField).blur();
									}
								}
								);
							});
						}else {
							$("#" + id).mousedown(function(){
								var labelField = $(this).attr("id");
								$.unit.open({
									labelField:labelField, 
									selectType : 4,
									close:function(){
										$("#"+labelField).focus();
										$("#"+labelField).blur();
									}
								}
								);
							});
						}
						if(isMod){
							if(val) {
								var valStr = new Array();
								valStr = val.split(",");
								for(var k=0;k<valStr.length;k++) {
									$(inputArr[i]).attr('value',valStr[0]);
									$(inputArr[i]).attr('hiddenValue',valStr[1]);
								}
							}
						}
					}
				}
				else if(dyFormInputMode.timeEmployForMeet == inputDataType) {//会议
//					alert("会议");
					removeInputArr(inputArr[i]);
					if(dataShow == dyFormDataShow.indirect){
						$(inputArr[i]).hide();
						$(inputArr[i]).next().hide();
						if(val == undefined) {
							if(defaultValue == "" || defaultValue == null) {
								$(inputArr[i]).after("<span></span>");
							}else {
								setSpanStyle($(inputArr[i]),textAlign,fontSize,fontColor,fontWidth,fontHight,defaultValue);
							}
						}else {
							setSpanStyle($(inputArr[i]),textAlign,fontSize,fontColor,fontWidth,fontHight,val);
								$(inputArr[i]).val(val);
						}
					}else {
						$(inputArr[i]).after("<input type='hidden' id='_"+colEnName+"' name='_"+colEnName+"' value=''/>");
						//时间占用查看空闲
//						var resourceName;
						$(inputArr[i]).click(function() {
//							resourceName = $(this).attr("resourceName");
							getRecords($(this),"","today","","MEET_RESOURCE",$(this).attr("relationDataShowType"));
							
						});
						if(isMod){
							if(val != undefined) {
								$(inputArr[i]).val(val);
							}else {
								 $(inputArr[i]).val(defaultValue);
							}
						}
					}
					
				}
				else if(dyFormInputMode.timeEmployForCar == inputDataType) {//车辆
//					alert("车辆");
					removeInputArr(inputArr[i]);
					if(dataShow == dyFormDataShow.indirect){
						$(inputArr[i]).hide();
						$(inputArr[i]).next().hide();
						if(val == undefined) {
							if(defaultValue == "" || defaultValue == null) {
								$(inputArr[i]).after("<span></span>");
							}else {
								setSpanStyle($(inputArr[i]),textAlign,fontSize,fontColor,fontWidth,fontHight,defaultValue);
							}
						}else {
							setSpanStyle($(inputArr[i]),textAlign,fontSize,fontColor,fontWidth,fontHight,val);
								$(inputArr[i]).val(val);
						}
					}else {
						//时间占用查看空闲
//						var resourceName;
						$(inputArr[i]).click(function() {
//							resourceName = $(this).attr("resourceName");
							getRecords($(this),"","today","","CAR_RESOURCE",$(this).attr("relationDataShowType"));
							
						});
						if(isMod){
							if(val != undefined) {
								$(inputArr[i]).val(val);
							}else {
								 $(inputArr[i]).val(defaultValue);
							}
						}
					}
				}
				else if(dyFormInputMode.timeEmployForDriver == inputDataType) {//司机
//					alert("司机");
					removeInputArr(inputArr[i]);
					if(dataShow == dyFormDataShow.indirect){
						$(inputArr[i]).hide();
						$(inputArr[i]).next().hide();
						if(val == undefined) {
							if(defaultValue == "" || defaultValue == null) {
								$(inputArr[i]).after("<span></span>");
							}else {
								setSpanStyle($(inputArr[i]),textAlign,fontSize,fontColor,fontWidth,fontHight,defaultValue);
							}
						}else {
							setSpanStyle($(inputArr[i]),textAlign,fontSize,fontColor,fontWidth,fontHight,val);
								$(inputArr[i]).val(val);
						}
					}else {
						//时间占用查看空闲
//						var resourceName;
						$(inputArr[i]).click(function() {
//							resourceName = $(this).attr("resourceName");
							getRecords($(this),"","today","","DRIVER_RESOURCE",$(this).attr("relationDataShowType"));
							
						});
						if(isMod){
							if(val != undefined) {
								$(inputArr[i]).val(val);
							}else {
								 $(inputArr[i]).val(defaultValue);
							}
						}
					}
				}
				else if(dyFormInputMode.dialog == inputDataType) {//弹出框
					$(inputArr[i]).click(function(){
						$.ajax({
							async:false,
							url : ctx + $(this).attr("relationDataShowType"),
							success : function(data) {
								var json = new Object(); 
						        json.content = data;
						        json.title = "自定义弹出框";
						        json.height= 600;
						        json.width= 800;
						        showDialog(json);
							}
						});
					});
				}
				else if(dyFormInputMode.radio == inputMode){//单选框
//					alert("单选框");
					removeInputArr(inputArr[i]);
					if(dataShow == dyFormDataShow.indirect){
						$(inputArr[i]).hide();
						$(inputArr[i]).next().hide();
						if(val == undefined) {
							var opt = new Array();
							$.each(options,function (i,obj){
								var s = '<input type=radio name=' + colEnName + ' id=' + colEnName + '_' + i + ' value=' + obj.value + '>' +'<label for=' +colEnName + '_' + i+'>'+obj.label+'</label>';
								opt.push(s);
							});
							$(inputArr[i]).parent().html(opt.join('') + '<div class="info"><span class="Validform_checktip"></span><span class="dec"><s class="dec1">&#9670;</s><s class="dec2">&#9670;</s></span></div>');
							$('input[type=radio][name=' + colEnName + ']').attr("disabled","disabled");
						}else {
							
							var opt = new Array();
							$.each(options,function (i,obj){
								var s = '<input type=radio name=' + colEnName + ' id=' + colEnName + '_' + i + ' value=' + obj.value + '>' +'<label for=' +colEnName + '_' + i+'>'+obj.label+'</label>';
								opt.push(s);
							});
							$(inputArr[i]).parent().html(opt.join('') + '<div class="info"><span class="Validform_checktip"></span><span class="dec"><s class="dec1">&#9670;</s><s class="dec2">&#9670;</s></span></div>');
							$('input[type=radio][name=' + colEnName + ']').attr("disabled","disabled");
							$('input[type=radio][name=' + colEnName + '][value=' + val + ']').attr('checked',true);
						}
					}else {
						if(isFirst){
							var opt = new Array();
							$.each(options,function (i,obj){
								var s = '<input type=radio name=' + colEnName + ' id=' + colEnName + '_' + i + ' value=' + obj.value + '>' +'<label for=' +colEnName + '_' + i+'>'+obj.label+'</label>';
								opt.push(s);
							});
							$(inputArr[i]).parent().html(opt.join('') + '<div class="info"><span class="Validform_checktip"></span><span class="dec"><s class="dec1">&#9670;</s><s class="dec2">&#9670;</s></span></div>');
//							inputArr[i] = $(table).find('#' + colEnName + '_0');
							$('input[type=radio][name=' + colEnName + '][value=' + val + ']').attr('checked',true);
						}
					}
				}else if(dyFormInputMode.checkbox == inputMode){//复选框
					removeInputArr(inputArr[i]);
					//隐藏或打开字段的处理
					if(ctrlField != "" && ctrlField != null) {
							var temps = ctrlField.split(",")[0];
							var temp = temps.split(";");
							var id = $(inputArr[i]).attr("id");
							$.each(temp,function (i,obj){
								$("#"+obj).parent().prev().hide();
								$("#"+obj).parent().hide();
								$("#"+id).die().live("click",function() {
									if($("#"+obj).parent().css("display") == "none") {
										$("#"+obj).parent().prev().show();
										$("#"+obj).parent().show();
									}else {
										$("#"+obj).parent().prev().hide();
										$("#"+obj).parent().hide();
									}
								});
							});
					}
					
					
					
					if(dataShow == dyFormDataShow.indirect){
						$(inputArr[i]).hide();
						$(inputArr[i]).next().hide();						
						if(val == undefined) {
							var opt = new Array();
							$.each(options,function (i,obj){
								var s = '<input type=checkbox name=' + colEnName + ' id=' + colEnName + '_' + parseInt(i+1) + ' value=' + obj.value + '>' + '<label for=' +colEnName + '_' + parseInt(i+1)+'>'+obj.label+'</label>';
								opt.push(s);
							});
							$('input[type=checkbox][name=' + colEnName + ']').parent().html(opt.join('') + '<div class="info"><span class="Validform_checktip"></span><span class="dec"><s class="dec1">&#9670;</s><s class="dec2">&#9670;</s></span></div>');
							$('input[type=checkbox][name=' + colEnName + ']').attr("disabled","disabled");
						}else {
							var opt = new Array();
							$.each(options,function (i,obj){
								var s = '<input type=checkbox name=' + colEnName + ' id=' + colEnName + '_' + parseInt(i+1) + ' value=' + obj.value + '>' + '<label for=' +colEnName + '_' + parseInt(i+1)+'>'+obj.label+'</label>';
								opt.push(s);
							});
							$('input[type=checkbox][name=' + colEnName + ']').parent().html(opt.join('') + '<div class="info"><span class="Validform_checktip"></span><span class="dec"><s class="dec1">&#9670;</s><s class="dec2">&#9670;</s></span></div>');
							$('input[type=checkbox][name=' + colEnName + ']').attr("disabled","disabled");
							val = val+"";
							var vals = val.split(",");
							for(var p=0;p<vals.length;p++) {
								var checkboxValue = vals[p];
								if(vals.length==1&&checkboxValue==1){
									$('input[type=checkbox][name=' + colEnName + ']').val(1);
								}
								$('input[type=checkbox][name=' + colEnName + '][value=' + vals[p] + ']').attr('checked',true);
							}
						}
					}else {
						if(isFirst){
							var opt = new Array();
							$.each(options,function (i,obj){
								var s = '<input type=checkbox name=' + colEnName + ' id=' + colEnName + '_' + parseInt(i+1) + ' value=' + obj.value + '>' + '<label for=' +colEnName + '_' + parseInt(i+1)+'>'+obj.label+'</label>';
								opt.push(s);
							});
							$('input[type=checkbox][name=' + colEnName + ']').parent().html(opt.join('') + '<div class="info"><span class="Validform_checktip"></span><span class="dec"><s class="dec1">&#9670;</s><s class="dec2">&#9670;</s></span></div>');
							inputArr[i] = $(table).find('#' + colEnName + '_0');
							if(val){
								if(ctrlField != "") {
									$("#"+temp).parent().prev().show();
									$("#"+temp).parent().show();
								}
								val = val+"";
								var vals = val.split(",");
								for(var k=0;k<vals.length;k++){
									var checkboxValue = vals[k];
									if(vals.length==1&&checkboxValue==1){
										$('input[type=checkbox][name=' + colEnName + ']').val(1);
									}
									$('input[type=checkbox][name=' + colEnName + '][value="' + vals[k] + '"]').attr('checked',true);
								}
							}
						}
					}
				}
				else if(dyFormInputMode.selectMutilFase == inputMode){//下拉选择
//					alert("下拉选择");
					if(dataShow == dyFormDataShow.indirect){
//						$(inputArr[i]).hide();
						if(val == undefined) {
							if(defaultValue == "" || defaultValue == null) {
								$(inputArr[i]).after("<span></span>");
							}else {
								setSpanStyle($(inputArr[i]),textAlign,fontSize,fontColor,fontWidth,fontHight,defaultValue);
							}
						}else {
							removeInputArr(inputArr[i]);
							var opt = new Array();
							$.each(options,function (i,obj){
								var s = '<option value=' + obj.value + '>' + obj.label + '</option>';
								opt.push(s);
							});
							$('select[name=' + colEnName + ']').removeAttr("datatype");
							$('select[name=' + colEnName + ']').html(opt.join(''));
							 $('select[name=' + colEnName + ']').prop("disabled", true);
							$(inputArr[i]).find('option[value=' + val + ']').attr('selected',true);
						}
					}else {
						if(isFirst){
							removeInputArr(inputArr[i]);
							var opt = new Array();
							$.each(options,function (i,obj){
								var s = '<option value=' + obj.value + '>' + obj.label + '</option>';
								opt.push(s);
							});
							$('select[name=' + colEnName + ']').html(opt.join('') + '<div class="info"><span class="Validform_checktip"></span><span class="dec"><s class="dec1">&#9670;</s><s class="dec2">&#9670;</s></span></div>');
							$(inputArr[i]).find('option[value=' + val + ']').attr('selected',true);
						}
					}
				}else if(dyFormInputMode.xml == inputMode) {
					removeInputArr(inputArr[i]);
					if(isMod){
						if(val != undefined) {
							JDS.call({
								async:false,
								service :  "formDataService.getXml",
								data : [val,relationDataShowType],
								success : function(result) {
									var okButton = '<div class="form_operate" ><button id="update_xml" type="button" value="修改xml">修改xml</button></div>';
									var temp = "<table><tr><td>"+result.data+"</td></tr><tr><td>"+okButton+"</td></tr></table>";
									$(inputArr[i]).parent().html(temp);
									ColEnName = colEnName;
									var textareaHtml = '<textarea type="text" id="'+ColEnName+'" value="'+val+'">'+val+'</textarea>';
									$("#update_xml").after(textareaHtml);
									$("#"+ColEnName).hide();
								}
							});
						}
						$('#update_xml').die().live("click",function() {
							$("#"+ColEnName).show();
						});
					}
				}
				else if(dyFormInputType.int == dataType){//整数
//					alert("整数");
					removeInputArr(inputArr[i]);
					if(dataShow == dyFormDataShow.indirect){
						$(inputArr[i]).hide();
						if(val == undefined) {
							if(defaultValue == "" || defaultValue == null) {
								$(inputArr[i]).after("<span></span>");
							}else {
								setSpanStyle($(inputArr[i]),textAlign,fontSize,fontColor,fontWidth,fontHight,defaultValue);
							}
						}else {
							setSpanStyle($(inputArr[i]),textAlign,fontSize,fontColor,fontWidth,fontHight,val);
								$(inputArr[i]).val(val);
						}
					}else {
						if(isMod){
							$(inputArr[i]).val(val);
						}
						$(inputArr[i]).numberInput({dataType: "int"});
					}
				}else if(dyFormInputType.long == dataType){//长整数
//					alert("长整数");
					removeInputArr(inputArr[i]);
					var html = $.fileuploaders.fileHtml(); 
					$(inputArr[i]).parent().append(html);
				}else if(dyFormInputType.float == dataType){//浮点数
				//					alert("浮点数");
					removeInputArr(inputArr[i]);
					if(dataShow == dyFormDataShow.indirect){
						$(inputArr[i]).hide();
						if(val == undefined) {
							if(defaultValue == "" || defaultValue == null) {
								$(inputArr[i]).after("<span></span>");
							}else {
								setSpanStyle($(inputArr[i]),textAlign,fontSize,fontColor,fontWidth,fontHight,defaultValue);
							}
						}else {
							setSpanStyle($(inputArr[i]),textAlign,fontSize,fontColor,fontWidth,fontHight,val);
								$(inputArr[i]).val(val);
						}
					}else {
						if(isMod){
							if(val == "undefined") {
								if(floatSet > 0) {
									$(inputArr[i]).val(val.toFixed(floatSet));
								}else {
									$(inputArr[i]).val(val);
								}
							}
						}
						$(inputArr[i]).numberInput({dataType: "double"});
					}
				}else if(dyFormInputType.clob == dataType) {
					removeInputArr(inputArr[i]);
					if(dataShow == dyFormDataShow.indirect){
						$(inputArr[i]).hide();
						if(val == undefined && val == null) {
							$(inputArr[i]).val(defaultValue);
							if(defaultValue == "" || defaultValue == null) {
								$(inputArr[i]).after("<span></span>");
							}else {
								setSpanStyle($(inputArr[i]),textAlign,fontSize,fontColor,fontWidth,fontHight,defaultValue);
							}
						}else {
							setSpanStyle($(inputArr[i]),textAlign,fontSize,fontColor,fontWidth,fontHight,val);
								$(inputArr[i]).val(val);
						}
					}else {
						$(inputArr[i]).val(val);
					}
				}
				else{//文本或文本域
//					alert(val);
					removeInputArr(inputArr[i]);
					if(dataShow == dyFormDataShow.indirect){
						$(inputArr[i]).hide();
						if(val == undefined) {
							$(inputArr[i]).val(defaultValue);
							if(defaultValue == "" || defaultValue == null) {
								$(inputArr[i]).after("<span></span>");
							}else {
								setSpanStyle($(inputArr[i]),textAlign,fontSize,fontColor,fontWidth,fontHight,defaultValue);
							}
						}else {
								if($(inputArr[i]).attr("id") == "expand_field") {
								}else {
									setSpanStyle($(inputArr[i]),textAlign,fontSize,fontColor,fontWidth,fontHight,val);
								}
							$(inputArr[i]).val(val);
							
						}
					}else {
						if(isMod){
							
							if(val != undefined) {
								$(inputArr[i]).val(val);
							}else {
								if(defaultValueWay== dyFormInputValue.jsEquation){
									$(inputArr[i]).val("");
								}else{
									$(inputArr[i]).val(defaultValue);
								}
							}
							if(relationDataTextTwo!="" && relationDataTextTwo!= null && relationDataTextTwo!= undefined){
								if(relationDataShowMethod == "direct") {
									//获得要查询的字段
									var str = "";
									var tempArray = $(inputArr[i]).attr("relationDataDefiantion").split("|");
									for(var ja=0;ja<tempArray.length;ja++){
										if(tempArray[ja] != "") {
											var tempObj = JSON.parse(tempArray[ja]);
											str += ','+tempObj.sqlField;
										}
									}
									
									var path = "";
									if($(inputArr[i]).attr("relationDataValueTwo") != undefined) {
										path = "/basicdata/dyview/view_show_singledata?viewUuid="+$(inputArr[i]).attr("relationDataValueTwo")+"&currentPage=1&openBy=dytable&relationDataDefiantion="+str;
									}
									var parmArray = new Array();
									var relationDataTwoSql2 = $(inputArr[i]).attr("relationDataTwoSql");
									if(relationDataTwoSql2!="" && relationDataTwoSql2!=null && relationDataTwoSql2!=undefined){
										while(relationDataTwoSql2.indexOf("${")>-1){
											var s1=relationDataTwoSql2.match("\\${.*?\\}")+"";
											parmArray.push(s1.replace("${", "").replace("}", ""));
											relationDataTwoSql2 = relationDataTwoSql2.replace(s1,"");
										}
									}
									if($(inputArr[i]).attr("relationDataTwoSql")!=undefined&&$(inputArr[i]).attr("relationDataTwoSql")!=""){
										path += "&"+$(inputArr[i]).attr("relationDataTwoSql");
									}
									for(var jt=0;jt<parmArray.length;jt++){
										if(eval('formData.'+parmArray[jt])!=undefined&&eval('formData.'+parmArray[jt])!=""&&eval('formData.'+parmArray[jt])!="undefined"){
											path = path.replace("${"+parmArray[jt]+"}",eval('formData.'+parmArray[jt])) ;
										}
									}
									if(path.indexOf("${")>-1){
										var json = new Object(); 
										json.content = "没有相应条件的数据";
								        json.title = "相关数据源";
								        json.height= 600;
								        json.width= 800;
								        showDialog(json);
									}else{
										var viewUuid = $(inputArr[i]).attr("relationDataValueTwo");
										if(subDataParams.length != 0) {
											var fmfileIds = "";
											for(var jb=0;jb<subDataParams.length;jb++) {
												fmfileIds += subDataParams[0].id;
											}
										}
										JDS.call({
											async:false,
											service :"viewDefinitionService.getViewSingleData",
											data : [viewUuid,str,fmfileIds],
											success : function(result) {
												var data = result.data;
													for(var jc=0;jc<tempArray.length;jc++){
														for(var ma=0;ma<data.length;ma++) {
														var tempObj = JSON.parse(tempArray[jc]);
														$("#"+tempObj.formField).val(data[ma][tempObj.sqlField.replace("_","")]);
													}
												}
											}
										});
								
									}
									
								}else {
									$(inputArr[i]).unbind("click");
									$(inputArr[i]).click(function(){
										var paramsId = $(this).attr("id");
										//获得要查询的字段
										var str = "";
										var tempArray = $(this).attr("relationDataDefiantion").split("|");
										for(var j=0;j<tempArray.length;j++){
											if(tempArray[j] != "") {
												var tempObj = JSON.parse(tempArray[j]);
												str += ','+tempObj.sqlField;
											}
										}
										strArray = tempArray;
										var path = "";
										if($(this).attr("relationDataValueTwo") != undefined) {
											path = "/basicdata/dyview/view_show?viewUuid="+$(this).attr("relationDataValueTwo")+"&currentPage=1&openBy=dytable&relationDataDefiantion="+str;
										}
										var parmArray = new Array();
										var relationDataTwoSql2 = $(this).attr("relationDataTwoSql");
										if(relationDataTwoSql2!="" && relationDataTwoSql2!=null && relationDataTwoSql2!=undefined){
											while(relationDataTwoSql2.indexOf("${")>-1){
												var s1=relationDataTwoSql2.match("\\${.*?\\}")+"";
												parmArray.push(s1.replace("${", "").replace("}", ""));
												relationDataTwoSql2 = relationDataTwoSql2.replace(s1,"");
											}
										}
										if($(this).attr("relationDataTwoSql")!=undefined&&$(this).attr("relationDataTwoSql")!=""){
											path += "&"+$(this).attr("relationDataTwoSql");
										}
										for(var jt=0;jt<parmArray.length;jt++){
											if(eval('formData.'+parmArray[jt])!=undefined&&eval('formData.'+parmArray[jt])!=""&&eval('formData.'+parmArray[jt])!="undefined"){
												path = path.replace("${"+parmArray[jt]+"}",eval('formData.'+parmArray[jt])) ;
											}
										}
										if(path.indexOf("${")>-1){
											var json = new Object(); 
											json.content = "没有相应条件的数据";
									        json.title = "相关数据源";
									        json.height= 600;
									        json.width= 800;
									        showDialog(json);
										}else{
											$.ajax({
												async:false,
												url : ctx + path,
												success : function(data) {
													var json = new Object(); 
													json.content = "<div class='dnrw' style='width:99%;'>" +data+"</div>";
											        json.title = "相关数据源";
											        json.height= 600;
											        json.width= 800;
											        showDialog(json);
											        var afterDialogSelect = dytable.afterDialogSelect;
											        $(".dataTr").unbind("dblclick");
											        $(".dataTr").live("dblclick",function(){
											        	var paramsObj = new Object();
											        	var valStr = $(this).attr("jsonstr").replace("{","").replace("}","").split(",");
											        	for(var ai1=0;ai1<valStr.length;ai1++){
															for(var j=0;j<tempArray.length;j++){
																var tempObj = JSON.parse(tempArray[j]);
																if(tempObj.sqlField.replace("_","").toUpperCase()==valStr[ai1].split("=")[0].toUpperCase().replace(" ","")){
																	if(tempObj.formField == "expand_field"){
																		var expandValue = "";
																		expandValue = paramsId + ":" + valStr[ai1].split("=")[1]
																		if($("#"+tempObj.formField).val() != null && $("#"+tempObj.formField).val() != "") {
																			var oldValue = $("#"+tempObj.formField).val();
																			$("#"+tempObj.formField).val(oldValue+","+expandValue);
																			paramsObj[tempObj.formField] =  oldValue+","+expandValue;
																		}else {
																			$("#"+tempObj.formField).val(expandValue);
																			paramsObj[tempObj.formField] =  expandValue;
																		}
																	}else{
																		$("#"+tempObj.formField).val(valStr[ai1].split("=")[1]);
																		paramsObj[tempObj.formField] =  valStr[ai1].split("=")[1];
																	}
																	
																}
																if(valStr[ai1].split("=")[0].toUpperCase().replace(" ","")=="MATTERID"){
																	$("#matterId").val(valStr[ai1].split("=")[1]);
																}
															}
											        	}
											        	$("#dialogModule").dialog( "close" );
											        	if(afterDialogSelect){
											        		afterDialogSelect.call(this, paramsId, paramsObj);
														}
													});
												}
											});
										}
									});
								}
							}
						}
					}
				}
				//设置
				if(isFirst){
					for(var k=0;k<rules.length;k++){
						if(isHide != elementShow.hidden && dataShow != dyFormDataShow.indirect) {
							if(dyCheckRule.notNull == rules[k].value){//必填
								$(inputArr[i]).attr('datatype','*,maxLength').attr('nullmsg',dymsg.required).attr('errormsg',dymsg.length);
								$(inputArr[i]).attr('datalength',dataLength);
								$(inputArr[i]).parent().prev().text($(inputArr[i]).parent().prev().text()+" *");
							}else if(dyCheckRule.url == rules[k].value){//url
								$(inputArr[i]).attr('datatype','url,maxLength').attr('errormsg',dymsg.url);
								$(inputArr[i]).attr('datalength',dataLength);
							}else if(dyCheckRule.email == rules[k].value){
								$(inputArr[i]).attr('datatype','e,maxLength').attr('errormsg',dymsg.email);
								$(inputArr[i]).attr('datalength',dataLength);
							}else if(dyCheckRule.idCard == rules[k].value){//身份证
								$(inputArr[i]).attr('datatype','idcard,maxLength').attr('errormsg',dymsg.idCardNo);
								$(inputArr[i]).attr('datalength',dataLength);
							}else if(dyCheckRule.unique == rules[k].value){//唯一性
								$(inputArr[i]).attr('datatype','unique,maxLength').attr('errormsg',dymsg.notAlone);
								$(inputArr[i]).attr('datalength',dataLength);
							}
						}
					}
					if($(inputArr[i]).attr('datatype') == undefined || $(inputArr[i]).attr('datatype') == ""){
						if(dataLength != null) {
							$(inputArr[i]).attr('datalength',dataLength);
							$(inputArr[i]).attr('datatype','maxLength').attr('errormsg',dymsg.length);
						}
					}
					if($(inputArr[i]).attr('nullmsg') == undefined){
						$(inputArr[i]).attr('ignore','ignore');
					}
				}
				if(colHidden){
					$(inputArr[i]).css('display','none');
					$(inputArr[i]).parent().prev().html('');
				}else {
					if(!colEdit){
						$(inputArr[i]).attr('readonly',true);
					}
				}
			}
		}
	}
}


/**
 * 调用日期控件的方法 
 */
function my97Datetime(id,format){
	$('#' + id).focus(function (){
		WdatePicker({startDate:'%y-%M-01 00:00:00',
			dateFmt:format,alwaysUseStartDate:false,
		});
	});
}

//form编辑
var currTab;
var uuid;
var isAdd;
var properties = new Object();
properties.closeOnEscape = true;
properties.closeAfterAdd = true;
properties.closeAfterEdit = true;
properties.reloadAfterSubmit = false;
properties.closeOnEscape = true;
properties.recreateForm = true;
properties.afterShowForm = function (formid){
	var colInfos = JSON.parse($(currTab).attr('colInfos'));
	$.each(colInfos,function (i,value){
		if(value.inputMode == dyFormInputMode.date){//日期
			my97Datetime(value.colEnName,'yyyy-MM-dd');
		}
		if(value.inputMode == dyFormInputMode.dateTimeMin){//日期到分
			my97Datetime(value.colEnName,'yyyy-MM-dd HH:mm');
		}
		if(value.inputMode == dyFormInputMode.dateTimeSec){//日期到秒
			my97Datetime(value.colEnName,'yyyy-MM-dd HH:mm:ss');
		}
	});
};

/**
 * 接收发送过来的json数据并插入从表
 */
function addRowData(param) {
	
	var subTableArr = formDefinitionInfo.subTables;
	var trueFieldName = "";
	for(var k=0;k<subTableArr.length;k++) {
		var subTableFields = subTableArr[k].fields;
		var data = param.data;
		for(var index=0;index<subTableFields.length;index++) {
			var fieldName = subTableFields[index].colEnName;
			var inputMode = subTableFields[index].inputMode;
			if(inputMode == dyFormInputMode.accessory3 || inputMode == dyFormInputMode.accessory2 || inputMode == dyFormInputMode.accessory1) {
				var dataValue = data[fieldName];
				if(dataValue != null && dataValue != "") {
					//附件字段不为空的情况
					trueFieldName = fieldName;
				}
			}
			
		}
	}
		var tableId = param.tableId;
		var $dg = $('#' + tableId);
		var newDate = new UUID().id.toLowerCase(); 
		data["id"] = newDate;
		$dg.jqGrid('addRowData',newDate,data,"first");
 
		 var fieldName = trueFieldName;//附件字段的名字
		 var files = data[fieldName]; //取得附件字段的值
		 
		 
		 var uploadButton = "<div id='attachContainer_" + newDate + fieldName +"'></div>";
		 
		 $dg.jqGrid('setCell',newDate,fieldName,uploadButton);
         var $attachContainer = $("#attachContainer_" + newDate + fieldName) //上传控件要存放的位置,为jquery对象 
		 
		 //创建上传控件
		 var elementID = WellFileUpload.getCtlID4Dytable("", fieldName, newDate);
		 var fileupload = new WellFileUpload(elementID);
		 
		  //初始化上传控件
		fileupload.init(false,  $attachContainer,  formSign == "2", true, files);
		 
		
	 }

/**
 * 从表按钮添加行数据方法
 */
function add(fields){
	var tableId = $(this).attr("tableId");
	var editType =  $(this).attr("editType")
	var $dg = $('#' + tableId);
		if(dySubFormEdittype.rowEdit == editType){//行内编辑
			var newDate = WellFileUpload.createFolderID();
			$dg.jqGrid('addRowData', newDate,{});
			for(var index=0;index<fields.length;index++) {
				if(dyFormInputMode.accessory3 == fields[index].inputMode) {
					//列表显示（不含正文） 
					var fieldName = fields[index].colEnName;
					
					 
					var data_1 = new Object();
					data_1[fieldName] = "<div id='attachContainer_" + newDate  + fieldName+ "'></div>";
					$dg.jqGrid('setRowData',newDate,data_1);
					 
					var $attachContainer = $("#attachContainer_" + newDate + fieldName) //上传控件要存放的位置,为jquery对象 
					 
					//创建上传控件
					 var elementID = WellFileUpload.getCtlID4Dytable("", fieldName, newDate);
					 var fileupload = new WellFileUpload(elementID);
					//初始化上传控件
					fileupload.init(false,  $attachContainer,  formSign == "2", true, []);
					  
				}
			}
			
		}else{//弹出窗口
			currTab = $dg;
			uuid = new Date().getTime();
			isAdd = true;
			var dialogId = '_dialog_' + tableId;
			var colinfos = JSON.parse($dg.attr('colinfos'));
			$.each(colinfos,function (i,obj){
				$('#' + dialogId).find('span.Validform_checktip').html('').removeClass().addClass('Validform_checktip');
				switch(obj.inputMode){
					//单选
					case dyFormInputMode.radio:
						$('#' + dialogId).find('input[type=radio][name=' + obj.colEnName + ']').attr('checked',false);
						break;
					//复选
					case dyFormInputMode.checkbox:
						$('#' + dialogId).find('input[type=checkbox][name=' + obj.colEnName + ']').attr('checked',false);
						break;
					//下拉
					case dyFormInputMode.selectMutilFase:
						$('#' + dialogId).find('select option[name=' + obj.colEnName + ']').attr('selected',false);
						break;
					default:
						$('#' + obj.colEnName).val('');
						break;
				}
			});
			$('#' + dialogId).dialog( "open" );
		}
}

/**
 * 从表编辑按钮的方法
 */
function mod(tableId){
	tableId = '' + tableId;
	var $dg = $('#' + tableId);
	var ids = $dg.jqGrid('getGridParam','selarrrow');
	if(ids && ids.length > 0){
		if(ids.length > 1){
			$.jBox.info(dymsg.selectRecordModErr,dymsg.tipTitle);
		}else{
			currTab = $dg;
			var dialogId = '_dialog_' + tableId;
			isAdd = false;
			var rowData = $dg.jqGrid('getRowData',ids[0]);
			uuid = rowData.id;
			var colinfos = JSON.parse($dg.attr('colinfos'));
			$.each(colinfos,function (i,obj){
				$.each(rowData,function (key,value){
					if(obj.colEnName == key){
						$('#' + dialogId).find('span.Validform_checktip').html('').removeClass().addClass('Validform_checktip');
						switch(obj.inputMode){
							//单选
							case dyFormInputMode.radio:
								$('#' + dialogId).find('input[type=radio][name=' + key + ']').attr('checked',false);
								$('#' + dialogId).find('input[type=radio][name=' + key + '][value=' + value + ']').attr('checked',true);
								break;
							//复选
							case dyFormInputMode.checkbox:
								$('#' + dialogId).find('input[type=checkbox][name=' + key + ']').attr('checked',false);
								if(value){
									$.each(value.split(','),function (i,v){
										$('#' + dialogId).find('input[type=checkbox][name=' + key + '][value=' + v + ']').attr('checked',true);
									});
								}
								break;
							//下拉
							case dyFormInputMode.selectMutilFase:
								$('#' + dialogId).find('select option[name=' + key + ']').attr('selected',false);
								if(value){
									$.each(value.split(','),function (i,v){
										$('#' + dialogId).find('select option[name=' + key + '][value=' + v + ']').attr('selected',true);
									});
								}
								break;
							default:
								$('#' + key).val(value);
								break;
						}
					}
				});
			});
			$('#' + dialogId).dialog( "open" );
		}
	}else{
		$.jBox.info(dymsg.selectRecordMod,dymsg.tipTitle);
	}
}

/**
 * 从表删除按钮的方法
 */
function del(tableId){
	tableId = '' + tableId;
	var $dg = $('#' + tableId);
	var ids = $dg.jqGrid('getGridParam','selarrrow');
	if(ids.length > 0){
		var a = '';
		$.jBox.confirm(dymsg.delConfirm,dymsg.tipTitle,function (v,h,f){
			if(v){
				for(var i=(ids.length-1);i>=0;i--){
					$dg.jqGrid('delRowData',ids[i]);
				}
				$.jBox.info(dymsg.delSuccess,dymsg.tipTitle);
			}
		},{buttons:{'是':true,'否':false}});
	}else{
		$.jBox.info(dymsg.selectRecordDel,dymsg.tipTitle);
	}
}

/**
 * 获取表单数据
 */
function getFormData(tblName, columns,tableType,isUploadBodyFile,temp){
	var colArr = new Array();
	for(var i=0;i<columns.length;i++){
		var colEnName,dataType,inputMode,inputDataType;
		colEnName = columns[i].colEnName;
		dataType = columns[i].dataType;
		inputMode = columns[i].inputMode;
		colCnName = columns[i].colCnName;
		inputDataType = columns[i].inputDataType;
		dataType = columns[i].dataType;
		dataShow = columns[i].dataShow;
		if(colEnName != ''){
			var colInfo = new Object();
			colInfo.colEnName = colEnName;
			colInfo.dataType = dataType;
			colInfo.inputMode = inputMode;
			colInfo.descName = colCnName;
			if(dyFormInputType.date == dataType||dyFormInputType.dateTimeMin == dataType || dyFormInputType.dateTimeSec == dataType||dyFormInputType.timeMin == dataType || dyFormInputType.timeSec == dataType){//日期
				colInfo.value = $('input[name=' + colEnName + ']').val();
//				colInfo.descName = $('input[name=' + colEnName + ']').html();
			}else if(dyFormInputMode.radio == inputMode){//单选
				colInfo.value = $('input[type=radio][name=' + colEnName + ']:checked').val();
//				colInfo.descName = $('input[type=radio][name=' + colEnName + ']:checked').next().text();
			}else if(dyFormInputMode.checkbox == inputMode){//复选
				var chkArr = $('input[type=checkbox][name=' + colEnName + ']:checked');
				var v = '';
				var d = '';
				for(var j=0;j<chkArr.length;j++){
					if(j<1) {
						v += $(chkArr[j]).val();
						d += $(chkArr[j]).next().text();
					}else {
						v += ',' + $(chkArr[j]).val();
						d += ',' + $(chkArr[j]).next().text();
					}
				}
				colInfo.value = v;
//				colInfo.descName = d;
			}else if(dyFormInputMode.selectMutilFase == inputMode){//下拉单选框
				var selectArr = $("#"+colEnName).find('option:selected');
				colInfo.value = selectArr.val();
//				colInfo.descName = selectArr.text();
			} 
			else if(dyFormInputMode.accessory1 == inputDataType || dyFormInputMode.accessory2 == inputDataType  || dyFormInputMode.accessory3 == inputDataType ) {
				var elementID = WellFileUpload.getCtlID4Dytable(tblName  ,colEnName , 0);
				var files = WellFileUpload.files[elementID];
				 if(files){
					colInfo.value = files;
				 }else{
					colInfo.value = [];
				 }
//				console.log(elementID + ":" + colInfo.value);
				//console.log("总上传文件数目 "+ colInfo.value.length);
			}
			else if(dyFormInputMode.textBody == inputMode) {//正文
				if(typeof MSOffice != "undefined"){
					if(MSOffice.GetLocalFile && temp != "getFieldForFormData" && $("#drId").val() == null){
						if(MSOffice.GetLocalFile()!="")//表示有正文则上传
							SaveBody(MSOffice,true,'0');
					}
				}
				
				colInfo.value = "";
			}
			else if(dyFormInputMode.ckedit == inputDataType) {//html编辑器
				var a = colEnName;
				if(CKEDITOR.instances[a] != undefined) {
					var value = CKEDITOR.instances[a].getData();
					colInfo.value = value;
				}
			}
			else if(dyFormInputMode.orChoose3 == inputDataType||dyFormInputMode.orChoose2 == inputDataType||dyFormInputMode.orChoose1 == inputDataType||dyFormInputMode.orChoose4 == inputDataType) {//组织
				if($('#' + colEnName).val() !="") {
					colInfo.value = $('#' + colEnName).val() ;
					var hiddenValue = $('#'+ colEnName).attr("hiddenValue");
					if(hiddenValue !="undefined" && hiddenValue != null && hiddenValue != "null" && hiddenValue != undefined) {
						colInfo.value = $('#'+ colEnName).attr("hiddenValue");
					}
				}
			}
//			else if(dyFormInputMode.orChoose_ == inputMode) {//组织选择框(仅选择组织部门)
//				if($('#' + colEnName).val() !="") {
//					colInfo.value = $('#' + colEnName).val() ;
//					if($('#'+ colEnName).attr("hiddenValue") !="undefined") {
//						colInfo.value = $('#'+ colEnName).attr("hiddenValue");
//					}
//				}
//			}
			else if(dyFormInputMode.timeEmployForMeet == inputDataType || dyFormInputMode.timeEmployForCar == inputDataType || dyFormInputMode.timeEmployForDriver == inputDataType) {//资源（会议、车辆、司机）
				colInfo.value = $('#' + colEnName+"_").val();
			}
			else if(dyFormInputMode.treeSelect == inputDataType) {//树形下拉框
				if($('#_' + colEnName).val()==""&&$('#' + colEnName).val()!=""){
					colInfo.value = $('#' + colEnName).val();
				}else{
					colInfo.value = $('#_' + colEnName).val();
				}
			}

			else{//文本
//				if(colInfo.colEnName == "signature_") {
//					colInfo.value = $("#signature_").val();
//				}else {
					colInfo.value = $('#' + colEnName).val();
//				}
			}
			colArr.push(colInfo);
		}
	}
	return colArr;
}

/**
 * 根据传入的模块名和显示位置进行判断是否增加文件上传功能（当所传对象为空时说明动态表单不添加上传功能）
 */
function fileUpload(params) {
	var html = $.fileuploaders.appendButton({});
	if(params.uploadplace == "1") {
		$("#abc").before(html);
	}
	if(params.uploadplace == "2") {
		$("#abc").after(html);
	}
	//$.fileuploader.upload();
}

/**
 * 设置所有的字段为只读状态 传入表单名
 */
function setAllFieldRead() {
	flag3 = false;
}

/**
 * 根据字段映射名(mappingname)和表单定义UUID(formuuid)获取尚未保存到数据库中的动态表单主表某个字段的值
 */
function getFieldForFormData(params) {
	var info;
	var rootFormData = formData(false,"getFieldForFormData");
	var formDatas = rootFormData.formDatas;//获取表数据
	var mainTableArr = data.form.tableInfo; //主表信息
	var mainArr = mainTableArr.fields;
	for(var index=0;index<formDatas.length;index++) {
		var recordList = formDatas[index].recordList;
		if(params.formuuid == formDatas[index].formDefinitionUuid) {
			for(var i=0;i<mainArr.length;i++) {
				var applyTo = mainArr[i].applyTo;
				if(applyTo != null) {
					var applyToArray = applyTo.split(";");
					for(var j=0;j<applyToArray.length;j++) {
						if(params.fieldMappingName == applyToArray[j]) {
							var fieldName = mainArr[i].colEnName;
							for(var k=0;k<recordList.length;k++){
								var colValList = recordList[k].colValList;
								for(var l=0;l<colValList.length;l++) {
									if(fieldName == colValList[l].colEnName) {
										info = colValList[l].value;
									}
								}
							}
						};
					}
				}
			};
		}
	}
	return info;
}

/**
 * 文件是否转为swf
 */
function getFile2swf(param) {
	if(param == "1") {
		formData();
		return true;
	}else if(param == "2"){
		return false;
	}
}

/**
 * 防止下载
 */
function unDownLoad(param) {
	if(param == "true") {
		supportDown = "1";
	}else if(param == "false") {
		supportDown = "2";
	}
}

/**
 * 新旧表单数据的比较
 */
function showCompareData(params) {
	var id = params.id;
	var newDatas = params.data.serviceData;
	var oldData = params.data.localData;
	$("#"+id).append("<div id='"+id+"d'></div>");
	$("#"+id+"d").dialog({
		title:"版本比较",
		autoOpen:true,
		height: 600,
		width: 800,
		modal: true,
		open:function() {
			var modify_time =  newDatas['modify_time'];
			$("#"+id).append("<input type='hidden' id='modify_time' value='"+modify_time+"'>");
			var newHtml = "<style>.bg:hover{background-color: #4B9AD2;cursor: pointer;}" +
					".bar{background-color: #4B9AD2;}" +
					"</style>" +
					"<div class='dytable_form'>"+
					"<div class='post-sign'>"+
					"<div class='post-detail' style='margin: 0;'>"+
					"<div style='float:left;width:45%;'>" +
					"<div>服务器数据</div>" +
					"<table id='newHtml'>";
			for(var i=0;i<oldData.length;i++) {
				newHtml += "<th class='title' colspan='2'><span class='title_th'>"+oldData[i].descTableName+"</span></th>";
				var recordList = oldData[i].recordList;
				for(var j=0;j<recordList.length;j++) {
					var colValList = recordList[j].colValList;
					for(var k=0;k<colValList.length;k++) {
						var colEnName = colValList[k].colEnName;
						var value = colValList[k].value;
						var dataType = colValList[k].dataType;
						var inputMode = colValList[k].inputMode;
						var descName = colValList[k].descName;
						if(newDatas[colEnName] != value) {
							if(newDatas[colEnName].indexOf(",")>0) {
								if(newDatas[colEnName].split(",")[1] !=value) {
									if(colEnName == 'body_col') {
										newHtml += "<tr val='"+newDatas[colEnName].split(",")[1]+"' col='"+colEnName+"' inputMode='"+inputMode+"' descName='"+descName+"' class='bg odd'><td>正文</td>";
									}else {
										newHtml += "<tr val='"+newDatas[colEnName].split(",")[1]+"' col='"+colEnName+"' inputMode='"+inputMode+"' descName='"+descName+"' class='bg odd'><td>"+descName+"</td>";
									}
								}
							}else {
								if(colEnName == 'body_col') {
									newHtml += "<tr val='"+newDatas[colEnName]+"' col='"+colEnName+"' inputMode='"+inputMode+"' descName='"+descName+"' class='bg odd'><td>正文</td>";
								}else {
									newHtml += "<tr val='"+newDatas[colEnName]+"' col='"+colEnName+"' inputMode='"+inputMode+"' descName='"+descName+"' class='bg odd'><td>"+descName+"</td>";
								}
							}
						}
							if(dyFormInputMode.radio == inputMode) {
								if(newDatas[colEnName] != value) {
									if(newDatas[colEnName] == null) {
										newHtml += "<td></td></tr>";
									}else {
										newHtml += "<td>"+$('input[type=radio][name=' + colEnName + '][value=' + newDatas[colEnName] + ']').next().text()+"</td></tr>";
									}
								}
							}else if (dyFormInputMode.checkbox == inputMode) {
								if(newDatas[colEnName] != value) {
									if(newDatas[colEnName] == null) {
										newHtml += "<td></td></tr>";
									}else {
										var checkDatas = "";
										for(var k=0;k<newDatas[colEnName].split(',').length;k++){
											checkDatas += $('input[type=checkbox][name=' + colEnName + '][value="' + newDatas[colEnName].split(',')[k] + '"]').next().text();
										}
										newHtml += "<td>"+checkDatas+"</td></tr>";
									}
								}
							}else if (dyFormInputMode.orChoose == inputMode) {
								if(newDatas[colEnName] != null) {
									if(newDatas[colEnName].split(",")[1] != value) {
										if(newDatas[colEnName] == null) {
											newHtml += "<td></td></tr>";
										}else {
											newHtml += "<td>"+newDatas[colEnName].split(",")[0]+"</td></tr>";
										}
									}
								}
							}else if (dyFormInputMode.orChoose_ == inputMode) {
								if(newDatas[colEnName] != null) {
									if(newDatas[colEnName].split(",")[1] != value) {
										if(newDatas[colEnName] == null) {
											newHtml += "<td></td></tr>";
										}else {
											newHtml += "<td>"+newDatas[colEnName].split(",")[0]+"</td></tr>";
										}
									}
								}
							}
							else if (dyFormInputMode.selectMutilFase == inputMode) {
								if(newDatas[colEnName] != value) {
									if(newDatas[colEnName] == null) {
										newHtml += "<td></td></tr>";
									}else {
										newHtml += "<td>"+$('select[name=' + colEnName + ']').find('option[value=' + newDatas[colEnName] + ']').text()+"</td></tr>";
									}
								}
							}else if (dyFormInputMode.fileUpload == inputMode) {//附件
								var fileNames = new Array();
								var edittimes = new Array();
								$(".attach-list").find("li").each(function() {
									var fileName = $(this).attr("filename");
									fileNames.push(fileName);
									var editTime = $(this).attr("edittime");
									edittimes.push(editTime);
								});
								$.ajax({
									type : "post",
									async:false,
									dataType:"json",
									url : ctx + '/repository/file/downLoadFiles.action',
									data : {"attach": newDatas[colEnName]},
									success : function(data) {
										var fileList = data;
										if(fileNames.length != fileList.length) {
											newHtml += "<tr val='"+newDatas[colEnName]+"' col='"+colEnName+"' inputMode='"+inputMode+"' descName='"+descName+"' class='bg odd'><td>附件</td><td val='"+newDatas[colEnName]+"' inputMode='"+inputMode+"' id='"+colEnName+"_trL'><div class='filelist' id='service_filelist'><ul class='attach-list'>";
											for(var index=0;index<fileList.length;index++) {
												var fileName = fileList[index].split(";")[0];
												var edittime = fileList[index].split(";")[1];
														newHtml += "<li class='fileList_li' filename='"+fileName+"' edittime='"+edittime+"' id='file-"+index+"'>";
														if(fileName.indexOf("txt") > -1) {
															newHtml += '<img src="'+ctx+'/resources/form/file_txt.png" alt="" /><span class="fileName_span">'+fileName+'</span><span class="edittime_span">最后编辑时间:'+edittime+'</span></li>';
														}else if(fileName.indexOf("doc") > -1) {
															newHtml += '<img src="'+ctx+'/resources/form/file_doc.png" alt="" /><span class="fileName_span">'+fileName+'</span><span class="edittime_span">最后编辑时间:'+edittime+'</span></li>';
														}else if(fileName.indexOf("rar") > -1 || fileName.indexOf("zip") > -1) {
															newHtml += '<img src="'+ctx+'/resources/form/rar.png" alt="" /><span class="fileName_span">'+fileName+'</span><span class="edittime_span">最后编辑时间:'+edittime+'</span></li>';
														 }else if(fileName.indexOf("png") > -1 || fileName.indexOf("jpg") > -1) {
															 newHtml += '<img src="'+ctx+'/resources/form/jpg.png" alt="" /><span class="fileName_span">'+fileName+'</span><span class="edittime_span">最后编辑时间:'+edittime+'</span></li>';
														 }else{
															 newHtml += '<img src="'+ctx+'/resources/form/file_doc.png" alt="" /><span class="fileName_span">'+fileName+'</span><span class="edittime_span">最后编辑时间:'+edittime+'</span></li>';
													}
											}
											newHtml += "</ul></div></td></tr>";
										}else {
											for(var i = 0;i<fileList.length;i++) {
												var filename = fileList[i].split(";")[0];
												var edittime = fileList[i].split(";")[1];
												if(edittimes[i] != edittime) {
													newHtml += "<tr val='"+newDatas[colEnName]+"' col='"+colEnName+"' inputMode='"+inputMode+"' descName='"+descName+"' class='bg odd'><td>附件</td><td val='"+value+"' inputMode='"+inputMode+"' id='"+colEnName+"_trL'><div class='filelist' id='service_filelist'><ul class='attach-list'>";
														newHtml += "<li class='fileList_li' filename='"+filename+"' edittime='"+edittime+"' id='file-"+i+"'>";
													if(filename.indexOf("txt") > -1) {
														newHtml += '<img src="'+ctx+'/resources/form/file_txt.png" alt="" /><span class="fileName_span">'+filename+'</span><span class="edittime_span">最后编辑时间:'+edittime+'</span></li>';
													}else if(filename.indexOf("doc") > -1) {
														newHtml += '<img src="'+ctx+'/resources/form/file_doc.png" alt="" /><span class="fileName_span">'+filename+'</span><span class="edittime_span">最后编辑时间:'+edittime+'</span></li>';
													}else if(filename.indexOf("rar") > -1 || filename.indexOf("zip") > -1) {
														newHtml += '<img src="'+ctx+'/resources/form/rar.png" alt="" /><span class="fileName_span">'+filename+'</span><span class="edittime_span">最后编辑时间:'+edittime+'</span></li>';
													 }else if(filename.indexOf("png") > -1 || filename.indexOf("jpg") > -1) {
														 newHtml += '<img src="'+ctx+'/resources/form/jpg.png" alt="" /><span class="fileName_span">'+filename+'</span><span class="edittime_span">最后编辑时间:'+edittime+'</span></li>';
													 }else{
														 newHtml += '<img src="'+ctx+'/resources/form/file_doc.png" alt="" /><span class="fileName_span">'+filename+'</span><span class="edittime_span">最后编辑时间:'+edittime+'</span></li>';
												}
												}
											}
											newHtml += "</ul></div></td></tr>";
										}
									},
									Error : function(data) {
										alert(data);
									}
							});
							}
							else if(dyFormInputMode.textBody == inputMode) {
								if($('#edittime2').val() != undefined) {
									newHtml += '<tr class="bg odd"><td>正文</td><td><ul class="attach-list2" ><li class="fileList_li"><img src="'+ctx+'/resources/form/file_doc.png" alt="" /><span class="fileName_span">正文.doc</span><span class="edittime_span">'+$('#edittime').val()+'</span></li></ul></td>';
								}
							}
							else {
								if(newDatas[colEnName] != value) {
									if(newDatas[colEnName] == null) {
										newHtml += "<td></td></tr>";
									}else {
										newHtml += "<td>"+newDatas[colEnName]+"</td></tr>";
									}
								}
							}
					}
				}
			}

			newHtml += "</table></div></div></div></div>";
			var button = "<div class='replace_buttonDiv'><button id='buttonClick' class='replace_button'>替换</button></div>";
				var localHtml =
					"<div class='dytable_form'>"+
					"<div class='post-sign'>"+
					"<div class='post-detail' style='margin: 0;'>"+
					"<div style='float:left;width:45%;'>" +
					"<div>本地数据</div>" +
					"<table id='localHtml'>";
				for(var i=0;i<oldData.length;i++) {
					localHtml += "<th class='title' colspan='2'><span class='title_th'>"+oldData[i].descTableName+"</span></th>";
					var recordList = oldData[i].recordList;
					for(var j=0;j<recordList.length;j++) {
						var colValList = recordList[j].colValList;
						for(var k=0;k<colValList.length;k++) {
							var colEnName = colValList[k].colEnName;
							var value = colValList[k].value;
							var dataType = colValList[k].dataType;
							var inputMode = colValList[k].inputMode;
							var descName = colValList[k].descName;
							
							if(newDatas[colEnName] != value) {
								if(newDatas[colEnName].indexOf(",")>0) {
									if(newDatas[colEnName].split(",")[1] !=value) {
										if(colEnName == 'body_col') {
											localHtml += "<tr val='"+value+"' col='"+colEnName+"' inputMode='"+inputMode+"' descName='"+descName+"' class='bg odd'><td>正文</td>";
										}else {
											localHtml += "<tr val='"+value+"' col='"+colEnName+"' inputMode='"+inputMode+"' descName='"+descName+"' class='bg odd'><td>"+descName+"</td>";
										}
									}
								}else {
									if(colEnName == 'body_col') {
										localHtml += "<tr val='"+value+"' col='"+colEnName+"' inputMode='"+inputMode+"' descName='"+descName+"' class='bg odd'><td>正文</td>";
									}else {
										localHtml += "<tr val='"+value+"' col='"+colEnName+"' inputMode='"+inputMode+"' descName='"+descName+"' class='bg odd'><td>"+descName+"</td>";
									}
								}
							}
							
								if(dyFormInputMode.radio == inputMode) {
									if(newDatas[colEnName] != value) {
										if(value == null) {
											localHtml += "<td id='"+colEnName+"_trL'></td></tr>";
										}else {
											localHtml += "<td id='"+colEnName+"_trL' value='"+value+"' inputMode='"+inputMode+"'>"+$('input[type=radio][name=' + colEnName + '][value=' + value + ']').next().text()+"</td></tr>";
										}
									}
								}else if (dyFormInputMode.checkbox == inputMode) {
									if(newDatas[colEnName] != value) {
										if(value == null) {
											localHtml += "<td id='"+colEnName+"_trL'></td></tr>";
										}else {
											var checkDatas = "";
											for(var k=0;k<value.split(',').length;k++){
												checkDatas += $('input[type=checkbox][name=' + colEnName + '][value="' + value.split(',')[k] + '"]').next().text();
											}
											localHtml += "<td id='"+colEnName+"_trL' value='"+value+"' inputMode='"+inputMode+"'>"+checkDatas+"</td></tr>";
										}
									}
								}else if (dyFormInputMode.orChoose == inputMode) {
									if(newDatas[colEnName] != null) {
										if(newDatas[colEnName].split(",")[1] != value) {
											if(value == null) {
												localHtml += "<td id='"+colEnName+"_trL'></td></tr>";
											}else {
												localHtml += "<td id='"+colEnName+"_trL' value='"+value+"' inputMode='"+inputMode+"'>"+$('#'+colEnName).val()+"</td></tr>";
											}
										}
									}
								}else if (dyFormInputMode.orChoose_ == inputMode) {
									if(newDatas[colEnName] != null) {
										if(newDatas[colEnName].split(",")[1] != value) {
											if(value == null) {
												localHtml += "<td id='"+colEnName+"_trL'></td></tr>";
											}else {
												localHtml += "<td id='"+colEnName+"_trL' value='"+value+"' inputMode='"+inputMode+"'>"+$('#'+colEnName).val()+"</td></tr>";
											}
										}
									}
								}
								else if (dyFormInputMode.selectMutilFase == inputMode) {
									if(newDatas[colEnName] != value) {
										if(value == null) {
											localHtml += "<td id='"+colEnName+"_trL'></td></tr>";
										}else {
											localHtml += "<td id='"+colEnName+"_trL' value='"+value+"' inputMode='"+inputMode+"'>"+$('select[name=' + colEnName + ']').find('option[value=' + value + ']').text()+"</td></tr>";
										}
									}
								}else if (dyFormInputMode.fileUpload == inputMode) {
									var fileNames = new Array();
									var edittimes = new Array();
									$(".attach-list").find("li").each(function() {
										var fileName = $(this).attr("filename");
										var newFile = $(this).attr("newFile");
										if(newFile != undefined) {
											fileName = fileName+newFile;
										}
										fileNames.push(fileName);
										var editTime = $(this).attr("edittime");
										edittimes.push(editTime);
									});
									$.ajax({
										type : "post",
										async:false,
										dataType:"json",
										url : ctx + '/repository/file/downLoadFiles.action',
										data : {"attach": value},
										success : function(data) {
											var fileList = data;
											if(fileNames.length != fileList.length) {//文件的长度不相等的情况
												localHtml += "<tr val='"+newDatas[colEnName]+"' col='"+colEnName+"' inputMode='"+inputMode+"' descName='"+descName+"' class='bg odd'><td>附件</td><td val='"+value+"' inputMode='"+inputMode+"' id='"+colEnName+"_trL'><div class='filelist' id='local_filelist'><ul class='attach-list'>";
												for(var index=0;index<fileNames.length;index++) {
														if(fileNames[index].indexOf("*")>-1) {
															localHtml += "<li class='fileList_li' filename='"+fileNames[index]+"' edittime='"+edittimes[index]+"' newFile='*' id='file-"+index+"'>";
														}else {
															localHtml += "<li class='fileList_li' filename='"+fileNames[index]+"' edittime='"+edittimes[index]+"' id='file-"+index+"'>";
														}
															if(fileNames[index].indexOf("txt") > -1) {
																localHtml += '<img src="'+ctx+'/resources/form/file_txt.png" alt="" /><span class="fileName_span">'+fileNames[index]+'</span><span class="edittime_span">最后编辑时间:'+edittimes[index]+'</span></li>';
															}else if(fileNames[index].indexOf("doc") > -1) {
																localHtml += '<img src="'+ctx+'/resources/form/file_doc.png" alt="" /><span class="fileName_span">'+fileNames[index]+'</span><span class="edittime_span">最后编辑时间:'+edittimes[index]+'</span></li>';
															}else if(fileNames[index].indexOf("rar") > -1 || fileNames[index].indexOf("zip") > -1) {
																localHtml += '<img src="'+ctx+'/resources/form/rar.png" alt="" /><span class="fileName_span">'+fileNames[index]+'</span><span class="edittime_span">最后编辑时间:'+edittimes[index]+'</span></li>';
															 }else if(fileNames[index].indexOf("png") > -1 || fileNames[index].indexOf("jpg") > -1) {
																 localHtml += '<img src="'+ctx+'/resources/form/jpg.png" alt="" /><span class="fileName_span">'+fileNames[index]+'</span><span class="edittime_span">最后编辑时间:'+edittimes[index]+'</span></li>';
															 }else{
																 localHtml += '<img src="'+ctx+'/resources/form/file_doc.png" alt="" /><span class="fileName_span">'+fileNames[index]+'</span><span class="edittime_span">最后编辑时间:'+edittimes[index]+'</span></li>';
														}
												}
												localHtml += "</ul></div></td></tr>";
											}else {//文件长度相等，判断每个文件的最后修改时间
												for(var i = 0;i<fileList.length;i++) {
													var filename = fileList[i].split(";")[0];
													if(edittimes[i] != fileList[i].split(";")[1]) {
														localHtml += "<tr val='"+newDatas[colEnName]+"' col='"+colEnName+"' inputMode='"+inputMode+"' descName='"+descName+"' class='bg odd'><td>附件</td><td val='"+value+"' inputMode='"+inputMode+"' id='"+colEnName+"_trL'><div class='filelist' id='local_filelist'><ul class='attach-list'>";
															localHtml += "<li class='fileList_li' filename='"+filename+"' edittime='"+edittimes[i]+"' id='file-"+i+"'>";
														if(filename.indexOf("txt") > -1) {
															localHtml += '<img src="'+ctx+'/resources/form/file_txt.png" alt="" /><span class="fileName_span">'+filename+'</span><span class="edittime_span">最后编辑时间:'+edittimes[i]+'</span></li>';
														}else if(filename.indexOf("doc") > -1) {
															localHtml += '<img src="'+ctx+'/resources/form/file_doc.png" alt="" /><span class="fileName_span">'+filename+'</span><span class="edittime_span">最后编辑时间:'+edittimes[i]+'</span></li>';
														}else if(filename.indexOf("rar") > -1 || filename.indexOf("zip") > -1) {
															localHtml += '<img src="'+ctx+'/resources/form/rar.png" alt="" /><span class="fileName_span">'+filename+'</span><span class="edittime_span">最后编辑时间:'+edittimes[i]+'</span></li>';
														 }else if(filename.indexOf("png") > -1 || filename.indexOf("jpg") > -1) {
															 localHtml += '<img src="'+ctx+'/resources/form/jpg.png" alt="" /><span class="fileName_span">'+filename+'</span><span class="edittime_span">最后编辑时间:'+edittimes[i]+'</span></li>';
														 }else{
															 localHtml += '<img src="'+ctx+'/resources/form/file_doc.png" alt="" /><span class="fileName_span">'+filename+'</span><span class="edittime_span">最后编辑时间:'+edittimes[i]+'</span></li>';
													}
													}
												}
												localHtml += "</ul></div></td></tr>";
											}
											
											
										},
										Error : function(data) {
											alert(data);
										}
								});
								}else if(dyFormInputMode.textBody == inputMode) {
									if($('#edittime2').val() != undefined) {
										localHtml += '<tr class="bg odd"><td>正文</td><td><ul class="attach-list2" ><li class="fileList_li"><img src="'+ctx+'/resources/form/file_doc.png" alt="" /><span class="fileName_span">正文.doc</span><span class="edittime_span">最后编辑时间:'+$('#edittime2').val()+'</span></li></ul></td>';
									}
								}
								else {
									if(newDatas[colEnName] != value) {
										if(value == null) {
											localHtml += "<td id='"+colEnName+"_trL'></td></tr>";
										}else {
											localHtml += "<td id='"+colEnName+"_trL' value='"+value+"' inputMode='"+inputMode+"'>"+value+"</td></tr>";
										}
									}
								}
						}
					}
				}
				localHtml += "</table></div></div></div>";
					var jscontent = "<script type='text/javascript'>" +
					"$(function(){" +
					"$('.bg').live('click',function(){" +
					"if($(this).attr('class') == 'bg odd'){$(this).attr('class','bg bar');}"+
					"else if($(this).attr('class') == 'bg bar'){$(this).attr('class','bg odd');}"+
					"});" +
					"});" +
					"$('#buttonClick').click(function(){" +
					"var value= $('tr').filter('.bg').filter('.bar').attr('val');" +
					"var text = $('tr').filter('.bg').filter('.bar').children().last().text();"+
					"var inputMode = $('tr').filter('.bg').filter('.bar').attr('inputMode');"+
					"if(inputMode == '16') {" +
					"delfile = [];"+
					"$('#local_filelist').find('li').each(function(index){" +
					"var filename = $(this).attr('filename');"+
					"if(filename.indexOf('*')>-1) {" +
					"filename = filename.replace('*','');"+
					"$.ajax({"+
					"type:'post',"+
					"url:ctx+'/repository/file/delete.action',"+
					"data:{'filename':filename,'attach':value},"+
					"success:function(data) {" +
					"alert('del');"+
					"}"+
					"});"+
					"}"+
					"});"+
					"$('#local_filelist').html('');"+
					"$('#local_filelist').html($('#service_filelist').html());"+
					"}else {" +
					"var descName = $('tr').filter('.bg').filter('.bar').attr('descName');"+
					"var col = $('tr').filter('.bg').filter('.bar').attr('col');" +
					"$('#'+col+'_trL').attr('value',value);"+
					"$('#'+col+'_trL').attr('inputMode',inputMode);"+
					"$('#'+col+'_trL').html(text);"+
					"}"+
					"});" +
					"$('.attach-list').find('li').unbind('dblclick');"+
					'$(".attach-list").find("li").dblclick(function(){'+
					'var file = $(this).attr("filename");'+
					'var newFile = $(this).attr("newFile");'+
					'var nodename = $("#fileupload_trL").attr("val");'+
					'if(newFile != undefined){'+
					'var filename = file.replace("*","");'+
					"location.href = ctx + '/repository/file/downloadtemp.action?filename='+ filename + '&attach='+nodename;"+
					'}else{'+
					"location.href = ctx + '/repository/file/download.action?filename=' + file+ '&modulename=DY_TABLE_FORM'+'&nodename='+nodename;}"+
					"});"+
					"</script> ";
			 $("#"+id+"d").append(newHtml);
			 $("#"+id+"d").append(button);
			 $("#"+id+"d").append(localHtml);
			 $("#"+id+"d").append(jscontent);
		},
		buttons: {
			"取消": function() {
				$("#modify_time").remove();
				$( this ).dialog( "close" );
			},
			"保存": function() {
				for(var i=0;i<oldData.length;i++) {
					var recordList = oldData[i].recordList;
					for(var j=0;j<recordList.length;j++) {
						var colValList = recordList[j].colValList;
						for(var k=0;k<colValList.length;k++) {
							var colEnName = colValList[k].colEnName;
							var value = $("#"+colEnName+"_trL").attr("value");
//							var valueb = $("#"+colEnName).val();
							var inputMode = $("#"+colEnName+"_trL").attr("inputMode");
							var text = $("#"+colEnName+"_trL").html(); 
							if(dyFormInputMode.radio == inputMode) {
								$('input[type=radio][name=' + colEnName + ']').attr('checked',false);
								$('input[type=radio][name=' + colEnName + '][value=' + value + ']').attr('checked',true);
							}else if(dyFormInputMode.checkbox == inputMode) {
								$('input[type=checkbox][name=' + colEnName + ']').attr('checked',false);
								if(value){
									for(var m=0;m<value.split(',').length;m++){
										$('input[type=checkbox][name=' + colEnName + '][value=' + value.split(',')[m] + ']').attr('checked',true);
									}
								}
							}else if(dyFormInputMode.textBody == inputMode) {
								
							}else if(dyFormInputMode.selectMutilFase == inputMode) {
								$("#"+colEnName).find('option[value=' + value + ']').attr('selected',true);
							}else if(dyFormInputMode.fileUpload == inputMode) {
								$('#files').html("");
								$('#files').append("<div class='filelist'><ul class='attach-list'>");
								$('#local_filelist').find("li").each(function(index) {
									var filename = $(this).attr("filename");
									if(filename.indexOf("*")>-1) {
										filename = filename.replace("*","");
									}
									var edittime = $(this).attr("edittime");
									 $('#files').find("ul").append('<li filename="'+filename+'" edittime="'+edittime+'" id="file-' + index + '"></li>');
									 fileNameList.push(filename);
									 var filename2 = filename.split(".");
									 filename2 = filename2.toString();
									 if(filename2.indexOf("txt") > -1) {
										 $('#file-' + index)
											.html('<img src="'+ctx+'/resources/form/file_txt.png" alt="" /><span>'+filename+'</span>'
													);
									 }else if(filename2.indexOf("doc") > -1 || filename2.indexOf("docx") > -1 ) {
										 $('#file-' + index)
											.html('<img src="'+ctx+'/resources/form/file_doc.png" alt="" /><span>'+filename+'</span>'
													);
									 }else if(filename2.indexOf("rar") > -1 || filename2.indexOf("zip") > -1) {
										 $('#file-' + index)
											.html('<img src="'+ctx+'/resources/form/rar.png" alt="" /><span>'+filename+'</span>'
													);
									 }else if(filename2.indexOf("png") > -1 || filename2.indexOf("jpg") > -1) {
										 $('#file-' + index)
											.html('<img src="'+ctx+'/resources/form/jpg.png" alt="" /><span>'+filename+'</span>'
													);
									 }else{
										 $('#file-' + index)
											.html('<img src="'+ctx+'/resources/form/file_doc.png" alt="" /><span>'+filename+'</span>'
								);}
								});
							}else if(dyFormInputMode.orChoose == inputMode) {
								if(value == undefined ) {
									//
								}else {
									$("#"+colEnName).val(text);
									$("#"+colEnName).attr("hiddenvalue",value);
								}
							}else if(dyFormInputMode.orChoose_ == inputMode) {
								if(value == undefined ) {
									//
								}else {
									$("#"+colEnName).val(text);
									$("#"+colEnName).attr("hiddenvalue",value);
								}
							}
							else {
								if(value == undefined && text == undefined) {
									//
								}else {
									$("#"+colEnName).html(text);
									$("#"+colEnName).val(text);
								}
							}
						}
					}
				}			
				$( this ).dialog( "close" );
			}
		},
		
		close: function() {
		}
	}
	); 
}

/**
 * 设置表单的数据uuid
 */
function setDataUuid(value) {
	dataUuid = value;
}
 
/**
 * 获取动态表单所有未保存的数据
 */
function formData(isUploadBodyFile,temp){
	var mainData  =  eval('data.data.' + data.form.tableInfo.tableName);
	var mainFormData = new Object();
	mainFormData.formDefinitionUuid = formDefinitionInfo.tableInfo.uuid;
	mainFormData.tableName = formDefinitionInfo.tableInfo.tableName;
	mainFormData.descTableName = formDefinitionInfo.tableInfo.tableDesc;
	mainFormData.tableType = dyTableType.mainTable;
	if(mainData != undefined) {
		if($("#modify_time").val() != undefined) {
			mainFormData.modifyTime = $("#modify_time").val();
			$("#modify_time").remove();
		}else {
			mainFormData.modifyTime = mainData.modify_time;
		}
		 
	}
	mainFormData.dataUuid = dataUuid;
	//主表字段信息列表
	var mainColInfoArr = formDefinitionInfo.tableInfo.fields;
	var mainColArr = getFormData(mainFormData.tableName, mainColInfoArr,dyTableType.mainTable,isUploadBodyFile,temp);
	var mainRecord = new Object();
	mainRecord.uuid = dataUuid;
	mainRecord.colValList = mainColArr;
	mainRecord.sortorder = 0;
	var mainRecordList = new Array();
	mainRecordList.push(mainRecord);
	mainFormData.recordList = mainRecordList;
	//表单数据列表
	var formDataList = new Array();
	formDataList.push(mainFormData);
	
	var rootFormData = new Object();
	rootFormData.formDatas = formDataList;
	rootFormData.file2swf = isFile2swf;
	rootFormData.setReadOnly = setReadOnly;
	 
	//从表
	var subTableArr = formDefinitionInfo.subTables;
	for(var i=0;i<subTableArr.length;i++){
		var subTable = $('#' + subTableArr[i].id);
//		var rows = $(subTable).datagrid('getChanges','inserted');
		var rows = $(subTable).jqGrid('getRowData');
		var ids = $(subTable).jqGrid('getDataIDs');
//		alert(JSON.stringify(rows));
//		alert(ids);
//		var rows =  $(subTable).jqGrid('getChangedCells','all');
//		alert("rows " + rows);
		var subFormData = new Object();
		var tblName = subTableArr[i].tableName;
		var subRecordList = new Array();
		for(var j=0;j<rows.length;j++){
			var subRecord = new Object();
			subRecord.uuid = ids[j];
			subRecord.sortorder = j+1;
			var subColArr = new Array();
			var colMappings = subTableArr[i].fields;
			
			//console.log("===>" + tblName);
			for(var k=0;k<colMappings.length;k++){
				var colInfo = new Object();
				if(dyFormInputType.date == colMappings[k].dataType||dyFormInputType.dateTimeMin == colMappings[k].dataType || dyFormInputType.dateTimeSec == colMappings[k].dataType||dyFormInputType.timeMin == colMappings[k].dataType || dyFormInputType.timeSec == colMappings[k].dataType){//日期
					colInfo.colEnName = colMappings[k].colEnName;
//					colInfo.dataType = dyFormInputType.date;
					colInfo.dataType = colMappings[k].dataType;
					colInfo.inputMode = colMappings[k].inputMode;
					colInfo.value = rows[j][colMappings[k].colEnName];
					colInfo.descName = rows[j][colMappings[k].colEnName]; 
				}
				else if(dyFormInputMode.accessory1 == colMappings[k].inputDataType || dyFormInputMode.accessory2 == colMappings[k].inputDataType|| dyFormInputMode.accessory3 == colMappings[k].inputDataType) {//附件上传
					colInfo.colEnName = colMappings[k].colEnName;
//					colInfo.dataType = dyFormInputType.text;
					colInfo.dataType = colMappings[k].dataType;
					colInfo.inputMode = colMappings[k].inputMode;
					//colInfo.value = $("#attach"+colInfo.colEnName+"_"+ids[j]).val();
					var ctlID = WellFileUpload.getCtlID4Dytable(""  ,colInfo.colEnName , ids[j]); 
					
					colInfo.value =  WellFileUpload.files[ctlID]
					 
					 
				}
				else if(dyFormInputMode.orChoose3 == colMappings[k].inputDataType||dyFormInputMode.orChoose2 == colMappings[k].inputDataType||dyFormInputMode.orChoose1 == colMappings[k].inputDataType) {//组织
					colInfo.colEnName = colMappings[k].colEnName;
//					colInfo.dataType = dyFormDataType.dataType;
					colInfo.dataType = colMappings[k].dataType;
					colInfo.inputMode = colMappings[k].inputMode;
					if(cache[rows[j].id]==undefined){
						colInfo.value = "";
						colInfo.descName = "";
					}else{
						colInfo.value = cache[rows[j].id].value;
						colInfo.descName = cache[rows[j].id].name;
					}
				}
				else if(dyFormInputMode.treeSelect == colMappings[k].inputDataType) {//树形下拉框
					colInfo.colEnName = colMappings[k].colEnName;
//					colInfo.dataType = dyFormDataType.dataType;
					colInfo.dataType = colMappings[k].dataType;
					colInfo.inputMode = colMappings[k].inputMode;
					if(cache[rows[j].id+"tree"]==undefined){
						colInfo.value = "";
						colInfo.descName = "";
					}else{
						colInfo.value = cache[rows[j].id+"tree"].value;
						colInfo.descName = cache[rows[j].id+"tree"].name;
					}
//					alert("colInfo.value "+ colInfo.value + " colInfo.colEnName " + colInfo.colEnName);
					
				}
				else{//文本
					colInfo.colEnName = colMappings[k].colEnName;
					colInfo.dataType = colMappings[k].dataType;
//					colInfo.dataType = dyFormDataType.string;
					colInfo.inputMode = colMappings[k].inputMode;
					colInfo.value = rows[j][colMappings[k].colEnName];
					colInfo.descName = rows[j][colMappings[k].colEnName];
				}
				subColArr.push(colInfo);
			}
			subRecord.colValList = subColArr;
			subRecordList.push(subRecord);
		}
		
		subFormData.formDefinitionUuid = subTableArr[i].tableId;
		subFormData.tableName = subTableArr[i].tableName;
		subFormData.descTableName = subTableArr[i].descTableName;
		subFormData.tableType = dyTableType.subtable;//从表
		subFormData.recordList = subRecordList;
		formDataList.push(subFormData);
	}
	
	// 表单数据表单数据签名
	if(formSign === "2"){
		rootFormData.enableSignature = true;
		var jsonString = JSON.stringify(rootFormData);
		JDS.call({service:"formDataService.getDigestValue", data:[jsonString], async: false,
			success:function(result){
				var digest = result.data;
				var b = fjcaWs.OpenFJCAUSBKey();
				fjcaWs.ReadCertFromKey();
				var cert = fjcaWs.GetCertData();
				fjcaWs.SignDataWithKey(digest.digestValue);
				var signData = fjcaWs.GetSignData();
				fjcaWs.CloseUSBKey();
				signature.formData = jsonString;
				signature.digestValue = digest.digestValue;
				signature.certificate = cert;
				signature.signatureValue = signData;
				signature.status = 1;
				signature.digestAlgorithm = digest.digestAlgorithm;
				rootFormData.signature = signature;
				//console.log(JSON.stringify(signature));
			},
			error:function(jqXHR){
				// 数字签名失败
				var faultData = JSON.parse(jqXHR.responseText);
				signature.status = -1;
				signature.remark = faultData.msg;
				rootFormData.signature = signature;
				
			}
		});
	}
	return rootFormData;
}

/**
 * 删除指定行的从表数据
 */
function delRowData(param) {
	var tableId = param.tableId;
	var $dg = $('#' + tableId);
	var rowid = param.rowid;
	$dg.jqGrid("delRowData", rowid);
}

/**
 * 获取制定的行数据
 */
function getRowData(param) {
	var tableId = param.tableId;
	var $dg = $('#' + tableId);
	var rowid = param.rowid;
	var data = $dg.jqGrid("getRowData", rowid);
	return data;
}

/**
 * 清空数据
 */
function clearGridData(param) {
	var tableId = param.tableId;
	var $dg = $('#' + tableId);
	$dg.jqGrid("clearGridData");
}

/**
 * 获取所有行数据
 */
function getAllRowData(param) {
	var tableId = param.tableId;
	var $dg = $('#' + tableId);
	var data  = $dg.jqGrid("getRowData");
	return data;
}

/**
 * 设置从表的行数据
 */
function setRowData(param) {
	var tableId = param.tableId;
	var rowid = param.rowid;
	var data = param.data;
	var $dg = $('#' + tableId);
	var result = $dg.jqGrid("setRowData",rowid,data);
	//成功返回true 失败返回false
	return result;
}

/**
 * 更新从表的行数据
 */
function updateRowData(param) {
	var data = param.data;
	var type = param.type;
	 
	var subTableArr = formDefinitionInfo.subTables;
	var tableId = param.tableId;
	var $dg = $('#' + tableId);
	var rowid = param.rowid;
	var result = $dg.jqGrid("setRowData",rowid,data);
	 var idValue = rowid;
	 
	for(var k=0;k<subTableArr.length;k++) {
		var subTableFields = subTableArr[k].fields;
		var data = param.data;
		for(var index=0;index<subTableFields.length;index++) {
			var fieldName = subTableFields[index].colEnName;
			var inputMode = subTableFields[index].inputMode;
			if(inputMode == dyFormInputMode.accessory3 
				|| inputMode == dyFormInputMode.accessory2 
				|| inputMode == dyFormInputMode.accessory1) {
					 var uploadButton = "<div id='attachContainer_" + idValue  + fieldName + "'></div>";
					 
					 $dg.jqGrid('setCell',idValue,fieldName,uploadButton);
			         var $attachContainer = $("#attachContainer_"  + idValue  + fieldName) //上传控件要存放的位置,为jquery对象 
					 var files = data[fieldName];
					
					//创建上传控件
					 var ctlID = WellFileUpload.getCtlID4Dytable("", fieldName, idValue);
					 var fileupload = WellFileUpload.get(ctlID);  
					 if(fileupload == undefined || fileupload == null){
						fileupload = new WellFileUpload(ctlID);
						fileupload.init(false,  $attachContainer,  formSign == "2", true, []);
					 }
					 fileupload.addFiles(files, true);
					 
					
					//初始化上传控件
					//fileupload.initWithLoadFilesFromFileSystem(false,  $attachContainer,  formSign == "2", true, idValue, fieldName);
					
			}
			
		}
	}
}

/**
 * 设置从表的分组表头信息
 */
function setGroupData(param) {
	var tableId = param.tableId;
	var $dg = $('#' + tableId);
	$dg.jqGrid('groupingGroupBy', "apply_material_shixiangname");
//	$dg.trigger('reloadGrid');
}

/**
 * 从表隐藏功能，传入从表的id
 */
function hideSubForm(param) {
	var tableId = param.tableId;
	var hideSubId = "subBtn-" + tableId;
	if($("#"+hideSubId).attr("id") != undefined) {
		$("#"+hideSubId).css("display","none");
		$("#"+hideSubId).next().css("display","none");
	}else {
		$('#gbox_'+ tableId).css("display","none");
	}
}
/**
 * 从表字段展现或隐藏功能
 * tableId:传入从表的id
 * field:要隐藏的字段
 */
function showSubFormField(param) {
	var tableId = param.tableId;
	var field = param.field;
	var fieldArray = new Array();
	fieldArray = field.split(",");
	var type = param.type;
	if(type == dySubFormFieldShow.show) {//展示
		for(var i=0;i<fieldArray.length;i++) {
			$("#"+tableId).jqGrid('showCol',[fieldArray[i]]);
		}
	}else if(type == dySubFormFieldShow.notShow) {//隐藏
		for(var i=0;i<fieldArray.length;i++) {
			$("#"+tableId).jqGrid('hideCol',[fieldArray[i]]);
		}
	}

	var tableArr = data.form.subTables;
	var formWidth = $(".post-detail").width();
	var formWidth2 =$("#showHtml").find(".post-detail").width();
	
	for(var i = 0; i < tableArr.length; i++){
		if(dySubFormEdittype.rowEdit == tableArr[i].editType){
			if(formWidth == 0) {
				$('#' + tableArr[i].id).jqGrid("setGridWidth", formWidth2);
			}else {
				$('#' + tableArr[i].id).jqGrid("setGridWidth", formWidth);
			}
		}
	}

	$(window).resize(function(){
		var tableArr = data.form.subTables;
		var formWidth = $(".post-detail").width();
		var formWidth2 =$("#showHtml").find(".post-detail").width();
		
		for(var i = 0; i < tableArr.length; i++){
			if(dySubFormEdittype.rowEdit == tableArr[i].editType){
				if(formWidth == 0) {
					$('#' + tableArr[i].id).jqGrid("setGridWidth", formWidth2);
				}else {
					$('#' + tableArr[i].id).jqGrid("setGridWidth", formWidth);
				}
			}
		}
	});
}

///**
// * 从表添加附件类字段
// * tableId:从表的表id
// * colEnName:附件字段的名字
// */
//function addFileSubField (param) {
//	var tableId = param.tableId;
//	var $dg = $('#' + tableId);
//	var idValue = param.rowid; //添加字段的行Id
//	var fieldName = param.colEnName;//附件字段的名字
//	 var uploadButton = "<div id='attachContainer_" + idValue + fieldName + "'></div>"
//	 $dg.jqGrid('setCell',idValue,fieldName,uploadButton);
//	 
//	 var $attachContainer = $("#attachContainer_" + idValue + fieldName) //上传控件要存放的位置,为jquery对象 
//	  
//	 //console.log( "4:" + idValue); 
//	 
//	 //创建上传控件
//	 var elementID = WellFileUpload.getCtlID4Dytable(tableId, fieldName, idValue);
//	 
//	 var fileupload = new WellFileUpload(elementID);
//
//	//初始化上传控件
//	fileupload.init(false,  $attachContainer,  formSign == "2", true, []);
//}

/**
 * 设置从表的附件字段为不可编辑
 * 传入field
 */
function setSubFileField(param) {
	var fieldName = param.field;
	var tableId = param.tableId;
	var ids = $("#"+tableId).jqGrid('getDataIDs');
	for(var i=0;i<ids.length;i++) {
		$(".allfiles_"+ids[i]).find("#upload_div").css("display","none");
		$("#filelist"+fieldName+"_"+ids[i]).find(".delete_Div").css("display","none");
	}
}

/**
 * 设置从表字段为不可编辑
 */

var fieldNames = new Array();
var tableIds = new Array();
function setSubFieldProperty(param) {
	var fieldName = param.field;
	fieldNames.push(fieldName);
	var tableId = param.tableId;
	tableIds.push(tableId);
	for(var i=0;i<tableIds.length;i++) {
		$("#" + tableIds[i]).jqGrid("setGridParam", {
			afterEditCell:function (id,name,val,iRow,iCol){
				for(var index =0;index<fieldNames.length;index++) {
					if(name == fieldNames[index]) {
						$(this).jqGrid('saveCell',iRow, iCol);
					}
				}
			}
		});
	}
}

dytable.setSubFieldProperty = setSubFieldProperty;
dytable.setSubFileField = setSubFileField;
//dytable.addFileSubField = addFileSubField;
dytable.showSubFormField = showSubFormField;
dytable.setDataUuid = setDataUuid;
dytable.setAllFieldRead = setAllFieldRead;
dytable.showCompareData = showCompareData;
dytable.unDownLoad = unDownLoad;
dytable.getFile2swf = getFile2swf;
dytable.setFieldValue = setFieldValue;
dytable.getFieldForFormData = getFieldForFormData;
dytable.addRowData = addRowData;
dytable.delRowData = delRowData;
dytable.getRowData = getRowData;
dytable.clearGridData = clearGridData;
dytable.getAllRowData = getAllRowData;
dytable.setRowData = setRowData;
dytable.updateRowData = updateRowData;
dytable.setGroupData = setGroupData;
dytable.formData = formData;
dytable.getFieldInfo = getFieldInfo;
dytable.getSubFieldInfo = getSubFieldInfo;
dytable.getIdInfo = getIdInfo;
dytable.hideSubForm = hideSubForm;
// 动态表单单据关闭
$(".form_header h2").after($("<div class='form_close' title='关闭'></div>"));
$(".form_header .form_close").click(function(e){window.close();});

})(jQuery);

/**
 * 文件管理弹窗的回调方法
 * @param fileUuid
 */
function afterSaveSuccess(fileUuid){
	JDS.call({
		async:false,
		service : "fileManagerService.getFmFileByUuid",
		data : [fileUuid],
		success : function(result) {
			var fileData = result.data;
			for(var j=0;j<strArray.length;j++){
				var tempObj = JSON.parse(strArray[j]);
				var sqlField = tempObj.sqlField;
				$("#"+tempObj.formField).val(fileData[sqlField]);
			}
			$("#dialogModule").dialog( "close" );
		}
	});
}


