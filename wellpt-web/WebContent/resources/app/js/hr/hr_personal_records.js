I18nLoader.load("/resources/app/js/hr/hrManager");
/**
 * 新增人事档案
 */
function new_personal_records(formuuid,flag){
	var url = ctx + "/app/hr/new_personal_records?formUuid=" + formuuid + '&flag=' + flag;
	window.open(url);
}

/**
 * 删除简历(假删除)
 */
function delete_personal_records(){
	var temp = $(".checkeds:checked").val();
	if(typeof(temp) ==  "undefined"){
		oAlert(hrManager.pleaseCheckedRecord);
	}else{
		//alert($(".checkeds").val());
		JDS.call({
			data : [$(".checkeds:checked").val()],
			service : "hrService.deletePersonalRecords",
			//$("#uuid").val()
			success : function(result) {
				if (result.data == 1) {
					oAlert(hrManager.deleteSuccess, function() {
						afterSaveRefleshWindow("");
						// window.location.href=ctx+'/fileManager/folder/indexList?id='+$("#folderId").val();
					});
				}
			}
		});
	}
}

//保存完文档点击“确定”之后调用的刷新方法
function afterSaveRefleshWindow(fileUuid){
	window.location.reload();
}

$(function() {
	
	console.log("dyform_demo from here");
	var time1 =(new Date()).getTime();
	var dataUuid = $("#dataUuid").val();
	var formUuid = $("#formUuid").val();
	var formDatas = loadFormDefinitionData(formUuid, dataUuid);
	var time2 =(new Date()).getTime();
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
	
	//是新增跳转进来还是行点击跳转进来
	var flag = $("#flag").val();
	if(flag == 'new_personal_records'){
		$("#abc").dyform(
				{
					definition:  formDatas.formDefinition ,
					data:formDatas.formDatas,
					displayAsLabel:false,//显示为文本
					displayAsFormModel:false,//displayAsLabel为true的前提下该参数才有效,默认为true
										//false:表示不用显示单据,true:使用显示单据,这时该若找不到对应的显示单据，则直接以该表单的模板做为显示单据
					success:function(){
						console.log("表单解析完毕");
						adjustWidthToForm();
					},
					error:function(msg){ 
						oAlert(msg);
					}
				} 
		);
	}else if(flag == 'rowclick'){
		$("#abc").dyform(
				{
					definition:  formDatas.formDefinition ,
					data:formDatas.formDatas,
					displayAsLabel:false,//显示为文本
					displayAsFormModel:false,//displayAsLabel为true的前提下该参数才有效,默认为true
										//false:表示不用显示单据,true:使用显示单据,这时该若找不到对应的显示单据，则直接以该表单的模板做为显示单据
					success:function(){
						console.log("表单解析完毕");
						adjustWidthToForm();
					},
					error:function(msg){ 
						oAlert(msg);
					}
				} 
		);
		
		$("#save").hide();
		if($("#isEdit").val() == 'true'){
			$("#save").show();
			$("#abc").dyform("setEditable", true);
		}else{
			$("#abc").dyform("showAsLabel", true);
		}
	}
	
	// 调整自适应表单宽度
	function adjustWidthToForm() {
		var div_body_width = $(window).width() * 0.95;
		$(".form_header").css("width", div_body_width - 5);
		$(".div_body").css("width", div_body_width);
	}
	
	/**
	 * 编辑按钮点击事件
	 */
	$("#edit").die().live( 'click', function(e) {
//		if(!checkCAKey()) {
//			return false;
//		}
		$("#save").show();
		var url = ctx + '/app/hr/rowClick?form_uuid=' + formUuid + "&uuid=" + dataUuid + '&isEdit=true';
		location.href = url;
	});
	
	/**
	 * 关闭按钮Js
	 * @param fileUuid
	 */
	$(".form_close").die().live('click',function(e){
		//alert('ok');
		//var window_opener = window.opener;
		//alert(window.opener);
		window.opener.location.reload();   
		window.close();
		
	});
	
	/**
	 * 保存按钮点击事件
	 */
	$("#save").click(function(){
		/*
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
		*/
		formData = $("#abc").dyform("collectFormData");
		var url = ctx + "/dyformdata/saveFormData";
		//console.log( JSON.stringify(formData));
	 
		$.ajax({
			url:url,
			type:"POST",
			data:  JSON.stringify(formData),
			dataType:'json',
			contentType:'application/json',
			success:function (result){
			  if(result.success == "true" || result.success == true){
				  alert("数据保存成功dataUuid=" + result.data);
				  var dataUuid =  result.data;
				  var formUuid = $("#formUuid").val();
				  var flag = $("#flag").val();
				  var isEdit = $("#isEdit").val();
				  if(isEdit != 'true'){
					  var url = ctx + '/app/hr/new_personal_records?formUuid=' + formUuid + "&uuid=" + dataUuid + "&flag=" + flag; 
				  }else{
					  var url = ctx + '/app/hr/rowClick?form_uuid=' + formUuid + "&uuid=" + dataUuid + "&flag=" + flag + "&isEdit=" + isEdit; 
					  $("#save").hide();
				  }
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
	
});