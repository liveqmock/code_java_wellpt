$(function() {
	
	/***************************************************************************************可编辑流水号对外调用接口****************************************************************************************************/
		var html = "<div id='dialog_form'><div class='dialog_form_content'><table width='100%'><tbody><tr class='odd'><td class='Label'>流水号名称</td><td class='value'><div class='td_class'><select name='serialName' id='serialName' style='' ><option value='' >--请选择--</option></select></div></td></tr><tr><td class='Label'>可选择流水号</td><td class='value'><div class='td_class'><select name='serialMaintain' id='serialMaintain' style='width=90%' ><option value='' >--请选择--</option></select></div></td></tr><tr class='odd'></tr><tr class='odd'><td class='Label'>流水号</td><td class='value'><div class='td_class'><div class='serilNumPosition' style='float:left;padding-top:3px;'  id='serialNum_head_part'></div>&nbsp;&nbsp;&nbsp;<input class='serilNumPosition' style='width:30px;margin:0px 5px;float:left;' type='text' name='serialNum_pointer'  id='serialNum_pointer' value=''/>&nbsp;&nbsp;&nbsp;<div class='serilNumPosition' style='float:left;padding-top:3px;' id='serialNum_last_part'></div><input style='display:none;' type='text' name='serialNum'  id='serialNum' value=''/></div></td></tr></tbody></table></div></div>";
		var headPart = "";
		var lastPart = "";
		var pointer = "";
		var serialVal = "";
		//加载时判断可编辑框是否被选中
		 $("#iseditor").focus(function(){  
			document.getElementById("serialName").options.length=1;
	        $(".hide").show();  
	     });
		 
		//获取可编辑的流水号
		 function getAllEditableSerialNumber(designatedId,designatedType,isOverride,formUuid,projection){
			 document.getElementById("serialName").options.length=1;
			//获取指定id可编辑的流水号
		 	if(designatedId != "" && designatedId != "请选择"){
		 		 JDS.call({
					 service : "serialNumberService.getByDesignatedId",
					 data : [ true,designatedId ],
					 success : function(result) {
						 $.each(result.data, function(i) {
							 //添加名称option选项
							 $("#serialName").append("<option value=\"" + result.data[i].name + "\">" + result.data[i].name + "</option>/n/r");
							 incremental = result.data[i].incremental;
						 });
					 },
				 });
		 	}else if(designatedType != "" && designatedType!="请选择"){//获取指定类型可编辑的流水号
		 		 JDS.call({
					 service : "serialNumberService.getByDesignatedType",
					 data : [ true,designatedType ],
					 success : function(result) {
						 $.each(result.data, function(i) {
							 //添加名称option选项
							 $("#serialName").append("<option value=\"" + result.data[i].name + "\">" + result.data[i].name + "</option>/n/r");
							 incremental = result.data[i].incremental;
						 });
					 },
				 });
		 	}else {//获取所有可编辑的流水号
		 		JDS.call({
					 service : "serialNumberService.getByIsEditor",
					 data : [ true ],
					 success : function(result) {
						 $.each(result.data, function(i) {
							 //添加名称option选项
							 $("#serialName").append("<option value=\"" + result.data[i].name + "\">" + result.data[i].name + "</option>/n/r");
							 incremental = result.data[i].incremental;
						 });
					 },
				 });
		 	}
		 }
		 
		//名称改变时将值赋给流水号的option选项
		 function getEditableSerialNumberValue(){
			$("#serialName").change(function(){
				 //每次改变选项前都需要清除原来内容
			  document.getElementById("serialMaintain").options.length=1;
			  $("#serialName option").each(function(i,o){
					  if($(this).attr("selected")){
						  getDifferentKey($(this).val());
				      }
			  });
			});
		 }
		 
	 	
		
		//判断所选择的流水号定义在维护中是否有不同的关键字
		function getDifferentKey(name) {
			JDS.call({
				service : "serialNumberMaintainService.getByName",
					data : [ name ],
					success : function(result) {
						 if( result.data.length > 0 ){
								$.each(result.data, function(i) {
									$("#serialMaintain").append("<option value=\"" + result.data[i].keyPart + "\">" + result.data[i].headPart+result.data[i].pointer+result.data[i].lastPart + "</option>/n/r");
									headPart = result.data[i].headPart;
									lastPart = result.data[i].lastPart;
									pointer = result.data[i].pointer;
									serialVal = result.data[i].keyPart;
								});
						 } else {
								JDS.call({
									service : "serialNumberService.getByName",
										data : [ name ],
										success : function(result) {
											serialVal = result.data.keyPart;
//												oAlert2(serialVal);
											$("#serialNum").val(result.data.headPart+result.data.initialValue+result.data.lastPart);
										},
										error : function(result){}
									});
						 }
					},
				});
		}
		
		//流水号的option选项改变时将值赋给流水号
		 function getFinalEditableSerialNumber(){
				$("#serialMaintain").change(function(){
					  $("#serialMaintain option").each(function(i,o){
						  if($(this).attr("selected")){
							  $("#serialNum_head_part").text(headPart);
							  $("#serialNum_pointer").val(pointer);
							  $("#serialNum_last_part").text(lastPart);
							  $("#serialNum").val($(this).text());
							  serialVal = $(this).val();
					      }
					  });
				});
		 }
		 
		//提交、取消时清除数据
		function clearAll(){
			document.getElementById("serialName").options.length=1;
			document.getElementById("serialMaintain").options.length=1;
			$("#serialNum").val(""); 
			$("#serialNum_head_part").text(""); 
			$("#serialNum_pointer").val(""); 
			$("#serialNum_last_part").text(""); 
			 $(".hide").hide();  
		}
	 
		
	//接收参数，可编辑流水号(指定的流水号id,指定的流水号分类，是否覆盖指针(0:强制不覆盖，1:新指针大于当前指针才覆盖；2：强制覆盖),表单uuid,存放流水号列名)
	 getEditableSerialNumber = function(designatedId,designatedType,isOverride,formUuid,projection,element){
		 var json = new Object(); 
	        json.content = html;
	        json.title = "可编辑流水号";
	        json.height= 400;
	        json.width= 700;
	        var buttons = new Object(); 
	        buttons.确定 = makeSure;
	        json.buttons = buttons;
	        showDialog(json);

		getAllEditableSerialNumber(designatedId,designatedType,isOverride,formUuid,projection);//获取所有可编辑
		getEditableSerialNumberValue();//名称改变时将值赋给流水号的option选项
		getFinalEditableSerialNumber();//流水号的option选项改变时将值赋给流水号
		var lastSerialNumber = "";
		
		function makeSure(){
			//将流水号加到可编辑框里
			var serial =  $("#serialNum").val();
			var newPointer =$("#serialNum_pointer").val();
			 lastSerialNumber = headPart + newPointer + lastPart;
			
			 //检测当前流水号是否被占用
			var checkResult = checkIsOccupied(formUuid,projection,lastSerialNumber);
			if(checkResult){
				oAlert2 ("该流水号已经被占用!",closeDialog,"");
				return ;
			} else{
//				oAlert2("该流水号未被占用!");
				//不覆盖
				if(isOverride==0){
//					 oAlert2("流水号："+lastSerialNumber+"强制不更新");
					 clearAll();
					 $("#editableSerialNumber").dialog("close");
					 closeDialog();

					 $("#iseditor").val(lastSerialNumber); 
				 } 
				//大于当前指针才覆盖
				if(isOverride == 1){
					var currentPointer = pointer-incremental;
//					 oAlert2("新指针："+newPointer);
					 if(newPointer > currentPointer){
//						 oAlert2("流水号："+lastSerialNumber+"大于当前，更新");
						 savePointer(lastSerialNumber,headPart,serialVal,isOverride,newPointer);
						 closeDialog();
						 $("#iseditor").val(lastSerialNumber); 
					 }else {
//						 oAlert2("流水号："+lastSerialNumber+"小于或等于当前， 不更新",closeDialog,"");
						 closeDialog();
						 $("#iseditor").val(lastSerialNumber); 
					 }
				 }
				//强制覆盖
				if(isOverride==2){
//					 oAlert2("新指针newPointer："+newPointer);
					 savePointer(lastSerialNumber,headPart,serialVal,isOverride,newPointer);
//					 oAlert2("流水号："+lastSerialNumber+"强制更新");
					 closeDialog();
					 $("#iseditor").val(lastSerialNumber); 
				 } 
			}
//			alert(element);
			$("#"+element).val(lastSerialNumber);
		}
		
		//保存指针
		function savePointer(lastSerialNumber,headPart,serialVal,isOverride,newPointer){
			JDS.call({
				service : "serialNumberMaintainService.savePointer",
					data : [ lastSerialNumber,headPart,serialVal,isOverride,newPointer],
					async: false,
					success : function(result) {
//						oAlert2("保存成功！",closeDialog,"");
						 clearAll();
					},
			});
		}
		//检测当前流水号是否已被占用
		function checkIsOccupied(formUuid,projection,lastSerialNumber){
			var checkResult;
			JDS.call({
				service : "serialNumberMaintainService.checkIsOccupied",
					data : [ formUuid,projection,lastSerialNumber],
					async: false,
					success : function(result) {
						checkResult = result.data;
					},
			});
			return checkResult;
		}
	};
	/******************************************************************************************************************************************************************************************************************/
	
	
	
	
	/**************************************************************************不可编辑流水号对外调用接口***********************************************************************************************************/
	//不可编辑流水号调用方法
	//serialNumberId：流水号id
	//isOccupied:         判断该流水号是否强制保留进数据库,不保存则为临时流水号，true：保存；false：不保存）
	//formUuid:           该表单的uuid
	//field:				    存放流水号的列名
	getNotEditableSerialNumberForDytable = function(serialNumberId,isOccupied, formUuid,field){
		var NotEditableSerialNumber;
		JDS.call({
			service : "serialNumberService.getNotEditorSerialNumberForDytable",
				data : [serialNumberId,isOccupied, formUuid,field],
				async: false,
				success : function(result) {
					NotEditableSerialNumber = result.data;
					return result.data;
				},
		});
		return NotEditableSerialNumber;
	 };
	/******************************************************************************************************************************************************************************************************************/
});
	
	