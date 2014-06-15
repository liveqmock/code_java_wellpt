(function() {
	
	/**************************/
	//当前双击表单元素对象
	var currInputObj;

	//当前表格对象
	var currTableObj;

	//主表字段信息数组(用于修改数据还原)
	var columns;
	//从表信息列表
	var subTables;
	var uuid = $("#formUuid").val();
	if(uuid != "undefined" && uuid != "") {
		$.ajax({
			url : contextPath + "/dytable/pre_edit_form.action",
			cache : false,
			type : "get",
			data : "uuid=" + uuid,
			dataType : "json",
			success : function(data) {
				columns = data.tableInfo.fields;
			}
		});
	}
	
	//关闭属性操作窗口
	function closeAttrCfgWin(){
		$("#attrCfgDiv").dialog("close");
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
			if(dyFormDataType.int == dataType){//文本正数
				return dyFormInputMode.int;
			}
			if(dyFormDataType.long == dataType){//文本长正数
				return dyFormInputMode.long;
			}
			if(dyFormDataType.float == dataType){//文本浮点数
				return dyFormInputMode.float;
			}
		}
		if('text' == eleType){
			if(dyFormDataType.int == dataType){//文本正数
				return dyFormInputMode.int;
			}
			if(dyFormDataType.long == dataType){//文本长正数
				return dyFormInputMode.long;
			}
			if(dyFormDataType.float == dataType){//文本浮点数
				return dyFormInputMode.float;
			}
		}
		return dataType;
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
		obj.serviceName = $("#serviceName").val();
		obj.methodName = $("#methodName").val();
		obj.data = $("#data").val();
		obj.designatedId = $("#designatedId").val();
		obj.designatedType = $("#designatedType").val();
		obj.isOverride = $("#isOverride").val();
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
	
	function createDataTypeDetailElement(dataType,value,serviceName,methodName,data,designatedId,designatedType,isOverride,uploadFileType,isGetZhengWen){
		if($('#dataLengthSpan')){
			$('#dataLengthSpan').remove();
		}
		if(dataType == dyFormInputType.text || dataType==""){//字符串
			elementStr = '<span id="dataLengthSpan">'
					+ '<div><input style="width:37%;float:left;"name="dataLength" type="text" class="w100" id="dataLength" value="' + (value != "" ? value : '255') + '"/><span class="defineSpan" style="width:40px;">('+dylbl.length + ')</span>'
					+ '</div></span>';
			$('#dataType').parent().append(elementStr);
			$("#inputDataTypetr").show();
			$("#definitiontr").show();
		}else{
			$("#inputDataTypetr").hide();
			$("#definitiontr").hide();
		}
	}
	function createDataTypeDetailElement1(inputMode,value,serviceName,methodName,data,designatedId,designatedType,isOverride,uploadFileType,isGetZhengWen){
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
		if(inputDataType == dyFormInputMode.treeSelect) {//树形下拉框 
			elementStr = '<span id="treeSpan" style="margin-left:10px;display: block;clear: both;">'+
				"<div><span class='defineSpan'>显示值" + ':</span><input style="float:none;width:75%;" name="serviceName" type="text" id="serviceName" value="' + (serviceName != undefined ? serviceName : '') + '"></div>'+
				"<div><span class='defineSpan'>隐藏值" + ':</span><input style="float:none;width:75%;" name="methodName" type="text"  id="methodName" value="' + (methodName != undefined ? methodName : '') + '"></div>' +
				"<input name= 'data' type='hidden' id='data' value=''>"+
				'</span>';
		$('#inputDataType').parent().append(elementStr);
		}else if(inputDataType == dyFormInputMode.serialNumber) {//可编辑流水号
			elementStr = '<span id="serialNumberSpan" style="margin-left:10px;display: block;clear: both;">'+
			"<div><span class='defineSpan'>指定流水号id:</span>"+ '<select name="designatedId" id="designatedId"></select></div>'+
			"<div><span class='defineSpan'>指定的流水号分类:</span>"+ '<select name="designatedType" id="designatedType"></select></div>'+
			"<div><span class='defineSpan'>是否覆盖指针:</span>" + "<select name='isOverride' id='isOverride'>" + 
			'<option value="0">不覆盖</option><option value="1">新指针大于当前指针才覆盖</option><option value="2">覆盖</option></select></div></span>';
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
			$("#inputDataTypeValueInfo").html("(弹出框URL)");
			$("#inputDataTypeValue").show();
		}
		else if(inputDataType == dyFormInputMode.timeEmployForMeet||inputDataType == dyFormInputMode.timeEmployForCar||inputDataType == dyFormInputMode.timeEmployForDriver){
			$("#inputDataTypeValueInfo").html("(资源起始时间FieldName)");
			$("#inputDataTypeValue").show();
		}
//		else if(inputMode == dyFormInputMode.fileUpload) {//附件上传
//					elementStr = '<span id="fileSpan" style="margin-left:10px;">'+
//					"<div><span class='defineSpan'>选择附件样式:</span>"+ "<select name='uploadFileType' id='uploadFileType'>"+
//					'<option value="1">标准样式</option><option value="2">简单样式</option></select></div>'+
//					"<div><span class='defineSpan'>是否启用正文:</span>" + 
//					"<input type='radio' name='isGetZhengWen' id='isGetZhengWen_1' value='4'></input><label for='file_laiyuan_1'>不启用</label>"+
//					"<input type='radio' name='isGetZhengWen' id='isGetZhengWen_2' value='3'></input><label for='file_laiyuan_2'>启用</label>"+
//					"<input type='hidden' id='isGetZhengWen' value=''></input>"+
//					'</div></span>';
//			$('#inputDataType').parent().append(elementStr);
//			$('#fileSpan').find('option[value=' + uploadFileType + ']').attr('selected',true);
//			$('#isGetZhengWen').val(isGetZhengWen);
//			$('input[type=radio][name=isGetZhengWen][value=' + isGetZhengWen + ']').attr('checked',true);
//		}
	}
	
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
//		$("#serviceName").val(paramObj.serviceName);
//		$("#methodName").val(paramObj.methodName);
		$("#data").val(paramObj.data);
		$("#designatedId").val(paramObj.designatedId);
		$("#designatedType").val(paramObj.designatedType);
		$("#isOverride").val(paramObj.isOverride);
		$("#uploadFileType").val(paramObj.uploadFileType);
		$('input[type=radio][name=isGetZhengWen][value=' + paramObj.isGetZhengWen + ']').attr('checked',true);
		$("#applyTo").val(paramObj.applyTo);
		/**ruan start**/
		$("#fontWidth").val(paramObj.fontWidth);
		$("#fontHight").val(paramObj.fontHight);
		$("#textAlign").val(paramObj.textAlign);
		$('input[type=checkbox][name=isHide][value=' + paramObj.isHide + ']').attr('checked',true);
		$('input[type=checkbox][name=dataShow][value=' + paramObj.dataShow + ']').attr('checked',true);
		$('input[type=radio][name=defaultValueWay][value=' + paramObj.defaultValueWay + ']').attr('checked',true);
		if(paramObj.defaultValueWay==dyFormInputValue.jsEquation){
			$("#defaultValue").val(paramObj.jsFunction);
		} else if(paramObj.defaultValueWay==dyFormInputValue.relationDoc){
			$(".relationClass").show();
		}
		$("#relationDataShowType").val(paramObj.relationDataShowType);
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
		createDataTypeDetailElement(paramObj.dataType,paramObj.dataLength,paramObj.serviceName,paramObj.methodName,paramObj.data,paramObj.designatedId,paramObj.designatedType,paramObj.isOverride,paramObj.uploadFileType,paramObj.isGetZhengWen);
		createDataTypeDetailElement1(paramObj.inputDataType,paramObj.dataLength,paramObj.serviceName,paramObj.methodName,paramObj.data,paramObj.designatedId,paramObj.designatedType,paramObj.isOverride,paramObj.uploadFileType,paramObj.isGetZhengWen);
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
	
	function inputDBClick(currObj){
		currInputObj = currObj;
		var uuid = $(currObj).attr("uuid");
		var formEleType = $(currObj).attr("formEleType");
		var formEleId = $(currObj).attr("id");
		var colCnName = $(currObj).attr("colcnname");
		var colEnName = $(currObj).attr("name");
		var defaultValue = $(currObj).attr("defaultValue");
		var fontSize = $(currObj).attr("fontSize");
		var fontColor = $(currObj).attr("fontColor");
		var jsFunction  = $(currObj).attr("jsFunction");
		if(fontColor != null) {
			$("#fontcolor").css("background-color",fontColor);
		}
		var dataShow = $(currObj).attr("dataShow");
		var dataType = $(currObj).attr("dataType");
		var dataLength = $(currObj).attr("dataLength");
		var serviceName = $(currObj).attr("serviceName");
		var methodName = $(currObj).attr("methodName");
		var data = $(currObj).attr("data");
		var designatedId = $(currObj).attr("designatedId");
		var designatedType = $(currObj).attr("designatedType");
		var isOverride = $(currObj).attr("isOverride");
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
		obj.serviceName = (serviceName == undefined ? "" : serviceName);
		obj.methodName = (methodName == undefined ? "" : methodName);
		obj.data = (data == undefined ? "" : data);
		obj.designatedId = (designatedId == undefined ? "" : designatedId);
		obj.designatedType = (designatedType == undefined ? "" : designatedType);
		obj.isOverride = (isOverride == undefined ? "" : isOverride);
		obj.uploadFileType = (uploadFileType == undefined ? "" : uploadFileType);
		obj.isGetZhengWen = (isGetZhengWen == undefined ? "" : isGetZhengWen);
		obj.applyTo = (applyTo == undefined ? "" : applyTo);
		obj.inputMode = (inputMode == undefined ? "" : inputMode);
		obj.optionDataSource = (optionDataSource == undefined ? "" : optionDataSource);
		/********ruan start***********/
		var fontWidth = $(currObj).attr("fontWidth");
		obj.fontWidth = (fontWidth == undefined ? "" : fontWidth);
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
		obj.relationDataShowType = (relationDataShowType == undefined ? "" : relationDataShowType);
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
		var iframeDocObj = $('#moduleDiv');
		//对input元素增加双击事件
		var inputArr = $(iframeDocObj).find("input");
		
		for(var i=0;i<inputArr.length;i++){
			$(inputArr[i]).dblclick(function (){inputDBClick(this);});
			
			//构造初使化数据
			var obj = new Object();
			//默认为"input"
			obj.formEleType = "text";
			if("body_col" == $(inputArr[i]).attr("id")) {
				obj.formEleType = "button";
				if($(inputArr[i]).attr("id") == undefined) {
					$(inputArr[i]).attr("id","body_col");
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
					obj.colCnName =  $(inputArr[i]).parent().prev().html();
					obj.defaultValue =  '';
					obj.fontSize  =  '';
					obj.fontColor =  '';
					obj.jsFunction =  '';
					obj.dataShow = dyFormDataShow.directShow; //初始化为直接展示
					obj.dataType = dyFormInputType.text;//初使化为字符串类型
					obj.dataLength = '';//初使化为空
					obj.serviceName = '';//初使化为空
					obj.methodName = '';//初使化为空
					obj.data = '';//初使化为空
					obj.designatedId = '';//初使化为空
					obj.designatedType = '';//初使化为空
					obj.isOverride = '';//初使化为空
					obj.uploadFileType = '';//初始化为空
					obj.isGetZhengWen = '';//初始化为空
					obj.applyTo = '';
					obj.inputMode = dyFormInputMode.textBody;//正文
					obj.optionDataSource = dyDataSourceType.dataConstant;//1.常量 2.取字典 ''.隐藏这个选项
					/********ruan start***********/
					obj.fontWidth = '';
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
				}else if("expand_field" == $(inputArr[i]).attr("id")) {
					obj.uuid = '';
					obj.colCnName = "拓展字段";
					obj.defaultValue =  '';
					obj.fontSize  =  '';
					obj.fontColor =  '';
					obj.jsFunction =  '';
					obj.dataShow = dyFormDataShow.directShow; //初始化为直接展示
					obj.dataType = "*";//初使化为字符串类型
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
					obj.inputMode = colObj.inputMode;
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
					obj.serviceName = '';//初使化为空
					obj.methodName = '';//初使化为空
					obj.data = '';//初使化为空
					obj.designatedId = '';//初使化为空
					obj.designatedType = '';//初使化为空
					obj.isOverride = '';//初使化为空
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
					obj.serviceName = '';//初使化为空
					obj.methodName = '';//初使化为空
					obj.data = '';//初使化为空
					obj.designatedId = '';//初使化为空
					obj.designatedType = '';//初使化为空
					obj.isOverride = '';//初使化为空
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
					obj.colCnName =  $(inputArr[i]).parent().prev().html();
					obj.defaultValue =  '';
					obj.fontSize  =  '';
					obj.fontColor =  '';
					obj.jsFunction =  '';
					obj.dataShow = dyFormDataShow.directShow; //初始化为直接展示
					obj.dataType = dyFormInputType.text;//初使化为字符串类型
					obj.dataLength = '';//初使化为空
					obj.serviceName = '';//初使化为空
					obj.methodName = '';//初使化为空
					obj.data = '';//初使化为空
					obj.designatedId = '';//初使化为空
					obj.designatedType = '';//初使化为空
					obj.isOverride = '';//初使化为空
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
//					obj.relationDataTypeTwo = '';
					obj.relationDataTextTwo = '';
					obj.relationDataValueTwo = '';
//					obj.relationDataUuid = '';
					obj.relationDataDefiantion = '';
					/********ruan end***********/
				}else if("expand_field" == $(inputArr[i]).attr("id")) {
					obj.uuid = colObj.uuid;
					obj.colCnName =  "拓展字段";
					obj.defaultValue =  '';
					obj.fontSize  =  '';
					obj.fontColor =  '';
					obj.jsFunction =  '';
					obj.dataShow = dyFormDataShow.directShow; //初始化为直接展示
					obj.dataType = "*";//初使化为字符串类型
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
					obj.inputMode = colObj.inputMode;
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
					obj.serviceName = '';//初使化为空
					obj.methodName = '';//初使化为空
					obj.data = '';//初使化为空
					obj.designatedId = '';//初使化为空
					obj.designatedType = '';//初使化为空
					obj.isOverride = '';//初使化为空
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
					obj.dataType = colObj.dataType;//初使化为字符串类型
					obj.dataLength = (colObj.dataLength == undefined ? "" : colObj.dataLength);//初使化为空
					obj.serviceName = colObj.serviceName;
					obj.methodName = colObj.methodName;
					obj.data = colObj.data;
					obj.designatedId = colObj.designatedId;
					obj.designatedType = colObj.designatedType;
					obj.isOverride = colObj.isOverride;
					obj.uploadFileType = colObj.uploadFileType;
					obj.isGetZhengWen = colObj.isGetZhengWen;
					obj.applyTo = (colObj.applyTo == undefined ? "" : colObj.applyTo);//初使化为空
					obj.inputMode = colObj.inputMode;//文本
					obj.optionDataSource = colObj.optionDataSource;//1.常量 2.取字典 ''.隐藏这个选项
					obj.options = colObj.options;
					obj.checkRules = colObj.checkRules;
					/********ruan start***********/
					obj.fontWidth = colObj.fontWidth;
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
				obj.dataType = dyFormInputType.text;//初使化为普通类型
				obj.dataLength = "";//初使化为空
				obj.serviceName = '';//初使化为空
				obj.methodName = '';//初使化为空
				obj.data = '';//初使化为空
				obj.designatedId = '';//初使化为空
				obj.designatedType = '';//初使化为空
				obj.isOverride = '';//初使化为空
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
				obj.serviceName = colObj.serviceName;
				obj.methodName = colObj.methodName;
				obj.data = colObj.data;
				obj.designatedId = colObj.designatedId;
				obj.designatedType = colObj.designatedType;
				obj.isOverride = colObj.isOverride;
				obj.uploadFileType = colObj.uploadFileType;
				obj.isGetZhengWen = colObj.isGetZhengWen;
				obj.applyTo = colObj.applyTo;
				obj.inputMode = colObj.inputMode;//手动输入
				obj.optionDataSource = colObj.optionDataSource;//1.常量 2.取字典 ''.隐藏这个选项
				obj.options = colObj.options;
				obj.checkRules = colObj.checkRules;
				/********ruan start***********/
				obj.fontWidth = '';
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
//				obj.relationDataTypeTwo = '';
				obj.relationDataTextTwo = '';
				obj.relationDataValueTwo = '';
//				obj.relationDataUuid = '';
				obj.relationDataDefiantion = '';
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
				obj.serviceName = '';//初使化为空
				obj.methodName = '';//初使化为空
				obj.data = '';//初使化为空
				obj.designatedId = '';//初使化为空
				obj.designatedType = '';//初使化为空
				obj.isOverride = '';//初使化为空
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
				obj.serviceName = colObj.serviceName;
				obj.methodName = colObj.methodName;
				obj.data = colObj.data;
				obj.designatedId = colObj.designatedId;
				obj.designatedType = colObj.designatedType;
				obj.isOverride = colObj.isOverride;
				obj.uploadFileType = colObj.uploadFileType;
				obj.isGetZhengWen = colObj.isGetZhengWen;
				obj.applyTo = colObj.applyTo;
				obj.inputMode = colObj.inputMode;//手动输入
				obj.optionDataSource = colObj.optionDataSource;//1.常量 2.取字典 ''.隐藏这个选项
				obj.options = colObj.options;
				obj.checkRules = colObj.checkRules;
				/********ruan start***********/
				obj.fontWidth = '';
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
//				obj.relationDataTypeTwo = '';
				obj.relationDataTextTwo = '';
				obj.relationDataValueTwo = '';
//				obj.relationDataUuid = '';
				obj.relationDataDefiantion = '';
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
				obj.serviceName = '';//初使化为空
				obj.methodName = '';//初使化为空
				obj.data = '';//初使化为空
				obj.designatedId = '';//初使化为空
				obj.designatedType = '';//初使化为空
				obj.isOverride = '';//初使化为空
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
				obj.serviceName = colObj.serviceName;
				obj.methodName = colObj.methodName;
				obj.data = colObj.data;
				obj.designatedId = colObj.designatedId;
				obj.designatedType = colObj.designatedType;
				obj.isOverride = colObj.isOverride;
				obj.uploadFileType = colObj.uploadFileType;
				obj.isGetZhengWen = colObj.isGetZhengWen;
				obj.applyTo = colObj.applyTo;
				obj.inputMode = colObj.inputMode;//手动输入
				obj.optionDataSource = colObj.optionDataSource;//1.常量 2.取字典 ''.隐藏这个选项
				obj.options = colObj.options;
				obj.checkRules = colObj.checkRules;
				/********ruan start***********/
				obj.fontWidth = '';
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
//				obj.relationDataTypeTwo = '';
				obj.relationDataTextTwo = '';
				obj.relationDataValueTwo = '';
//				obj.relationDataUuid = '';
				obj.relationDataDefiantion = '';
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
					obj.editType = ($(tableArr[i]).attr("editType") == undefined ? dySubFormEdittype.rowEdit : $(tableArr[i]).attr("editType"));
					obj.hideButtons = ($(tableArr[i]).attr("hideButtons") == undefined ? dySubFormHideButtons.isHide : $(tableArr[i]).attr("hideButtons"));
				}else{//编辑
					obj.tableId = (subTableObj.tableId == undefined ? "" : subTableObj.tableId);
					obj.editType = subTableObj.editType;
					obj.hideButtons = subTableObj.hideButtons;
					//初使化字段映射信息
					var colMappingArr = subTableObj.fields;
					var thArr = $(tableArr[i]).find('th');
					for(var j=0;j<thArr.length;j++){
						for(var k=0;k<colMappingArr.length;k++){
							if($(thArr[j]).html() == colMappingArr[k].thCnName){
								$(thArr[j]).attr('columnId',colMappingArr[k].uuid);
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
	
	function setTableAttributes(currTable,obj){
//		$(currTable).attr("id",obj.id);
		$(currTable).attr("tableId",obj.tableId);
		$(currTable).attr("tableType",obj.tableType);
		$(currTable).attr("editType",obj.editType);
		$(currTable).attr("hideButtons",obj.hideButtons);
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
		 
		if(subrRelationDataDefiantion != "" && subrRelationDataDefiantion != undefined){
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
	
	//保存表信息
	function saveTableInfoCfg(){
		//保存表配置信息
		$(currTableObj).attr("tableId",$("#tableDefinitionId").val());
		$(currTableObj).attr("editType",$("input[name=editType]:checked").val());
		$(currTableObj).attr("hideButtons",$("input[name=hideButtons]:checked").val());
		//保存表字段配置信息
		var thArr = $(currTableObj).find("th");
		var selectArr = $("#fieldCfgTable").find("select[name=fieldSelect]");
		for(var i=0;i<selectArr.length;i++){
			$(thArr[i]).attr("columnId",selectArr[i].value);
			$(thArr[i]).attr("fieldOrder", i);
		}
		closeTableInfoCfgDiv();
	}
	
	//关闭表信息设置窗口
	function closeTableInfoCfgDiv(){
		$("#tableInfoCfgDiv").dialog("close");
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
	
	
	//初使化表相关信息
	function initTableInfo(obj){
		//重新加载可选表列表
		$.extend(true,setting,{async:{otherParam:{selectedId:obj.tableId}}});
		$.fn.zTree.init($("#tableTree"), setting);
		 
		//
		$.extend(true,setting2,{async:{otherParam:{selectedId:obj.tableId}}});
	 
		$.fn.zTree.init($("#tableTree2"), setting2);
		$('#tableDefinitionId').val(obj.tableId);
		$("input[name=editType][value=" + obj.editType + "]").attr("checked","checked");
		$("input[name=hideButtons][value=" + obj.hideButtons + "]").attr("checked","checked");
	}
	
	//初使化字段信息
	function initFieldInfo(currTableObj){
		if($(currTableObj).attr("tableId") != undefined && $(currTableObj).attr("tableId") != ''){
			//表
			changeTableSelect($(currTableObj).attr("tableId"));
			//字段
			var selectArr = $("#fieldCfgTable").find("select[name=fieldSelect]");
			var thArr = $(currTableObj).find("th");
			for(var i=0;i<thArr.length;i++){
				for(var j=0;j<selectArr[i].options.length;j++){
					if(selectArr[i].options[j].value == $(thArr[i]).attr("columnId")){
						selectArr[i].options[j].selected = true;
						break;
					}
				}
			}
		}
	}
	
	function changeTableSelect(tableId){
		//加载数据
		$.ajaxSettings.async = false;
		$.getJSON(contextPath + "/dytable/field_list.action?tableId=" + tableId,function (data){
			var selectHTML = "<select name=fieldSelect style='width:100%;'><option value=''>" + dylbl.selectNull + "</option>";
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
	
	//设置字段属性值
	function setFormData(currObj,obj){
		if($(currObj).parents("table").attr("tabletype")==2){
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
			$(currObj).attr("serviceName",obj.serviceName);
			$(currObj).attr("methodName",obj.methodName);
			$(currObj).attr("data",obj.data);
			$(currObj).attr("designatedId",obj.designatedId);
			$(currObj).attr("designatedType",obj.designatedType);
			$(currObj).attr("isOverride",obj.isOverride);
			$(currObj).attr("uploadFileType",obj.uploadFileType);
			$(currObj).attr("isGetZhengWen",obj.isGetZhengWen);
			$(currObj).attr("applyTo",obj.applyTo);
			$(currObj).attr("inputMode",obj.inputMode);
			$(currObj).attr("optionDataSource",obj.optionDataSource);
			$(currObj).attr("opts",JSON.stringify(obj.options));
			$(currObj).attr("checkRules",JSON.stringify(obj.checkRules));
			/************ruan*********************/
			$(currObj).attr("fontWidth",obj.fontWidth);
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
//			}
//			$(currObj).attr("relationDataTypeTwo",obj.relationDataTypeTwo);
			$(currObj).attr("relationDataTextTwo",obj.relationDataTextTwo);
			$(currObj).attr("relationDataValueTwo",obj.relationDataValueTwo);
//			$(currObj).attr("relationDataUuid",obj.relationDataUuid);
			$(currObj).attr("relationDataDefiantion",obj.relationDataDefiantion);
		}
		
	}
	
	var formpreview = {
			 exec:function(editor){
		    		$('#fs2').css('display','');
//		    		alert($('#moduleDiv').html());
//		    		alert(CKEDITOR.instances.moduleText.getData());
		    		$("#moduleText").val(CKEDITOR.instances.moduleText.getData());
		    		$("#moduleDiv").html($("#moduleText").val());
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
		    		$(".nav-tabs").find("li").each(function() {
		    			var li_class = $(this).attr("class");
		    			if(li_class == "active") {
		    				$(this).attr("class","");
		    				$(this).next().attr("class","active");
		    				$("#tab2").attr("class","tab-pane");
		    				$("#tab3").attr("class","tab-pane active");
		    				return false;
		    			}
		    		});
		    	
	}};
	
    CKEDITOR.plugins.add("formpreview", {
        init: function(a) {
            a.addCommand("formpreview", formpreview);
            a.ui.addButton("formpreview", {
                label: "预览",//调用dialog时显示的名称
                icon: this.path + "images/preview.png",//在toolbar中的图标
                command: "formpreview"
            });
        }
    });
})();