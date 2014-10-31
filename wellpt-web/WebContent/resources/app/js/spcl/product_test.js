I18nLoader.load("/resources/app/js/hire/hireManager");



$(function() {
	
	var dataUuid = $("#dataUuid").val();
	var formUuid = $("#formUuid").val();
	var oldFormUuid = $("#oldFormUuid").val();
	// 得到表单定义及数据
	var formDatas = loadFormDefinitionData(formUuid, dataUuid);
	var oldFormData = loadFormDefinitionData(oldFormUuid,dataUuid).formDatas;
	// 初始化表单数据
	var data = formDatas.formDatas;
	// 表单数据是否新增 
	var isNew = false;
	
	if(typeof data.uuid=='undefined'||data.uuid==''){
		isNew =true;
	}
	
	if(typeof formDatas == "string"){
		formDatas = (eval("(" + formDatas +  ")"));
	}
	
	//ie8不兼容
	var titleElem=document.getElementsByTagName('title').item(0); 
	var text =  eval("(" + formDatas.formDefinition +  ")").displayName +"(解析)";
	try{
		$(titleElem).text(text);
	}catch(e){
		
	}
	
	//是新增跳转进来还是行点击跳转进来
	var flag = $("#flag").val();
	$("#spclform").dyform(
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

	//$("#spclform").dyform("showAsLabel", true);
	
	var formData = undefined;
	
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
		var flag = $("#flag").val();
		
		var url = ctx + "/app/hire/saveFormData";
		
		formData = $("#spclform").dyform("collectFormData");
		//console.log( JSON.stringify(formData));
		formData.flag = flag;
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
					  var url = ctx + '/app/hire/new_resume?formUuid=' + formUuid + "&uuid=" + dataUuid + "&flag=" + flag; 
				  }else{
					  var url = ctx + '/app/hire/rowClick?form_uuid=' + formUuid + "&uuid=" + dataUuid + "&flag=" + flag + "&isEdit=" + isEdit; 
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
	
	/**
	 * 编辑按钮点击事件
	 */
	$("#edit").die().live( 'click', function(e) {
//		if(!checkCAKey()) {
//			return false;
//		}
		var resumeStatus = $("#resumeStatus").val();
		
		$("#save").show();
		var url = ctx + '/app/hire/rowClick?form_uuid=' + formUuid + "&uuid=" + dataUuid + '&isEdit=true' + '&status=' +resumeStatus;
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
	 * 已阅按钮js事件
	 */
	$("#hadRead").die().live('click',function(e){
		oConfirm(hireManager.setHadRead, function() {
			
			var dataUuid = $("#dataUuid").val();
			var formUuid = $("#formUuid").val();
			var resumeStatus = $("#resumeStatus").val();
			JDS.call({
				data : [formUuid,dataUuid,resumeStatus],
				service : "hireService.changeResumeStatus",
				//$("#uuid").val()
				success : function(result) {
					if(result.data == '1'){
						oAlert("设置成功!");
					}else{
						oAlert("设置失败");
					}
				}
			});
		});
		
		
	});
	
	/**
	 * 已选按钮js事件
	 */
	$("#hadSelected").die().live('click',function(e){
		oConfirm(hireManager.intoHadSelected, function() {
			var dataUuid = $("#dataUuid").val();
			var formUuid = $("#formUuid").val();
			var resumeStatus = $("#resumeStatus").val();
			JDS.call({
				data : [formUuid,dataUuid,resumeStatus],
				service : "hireService.changeResumeStatus",
				//$("#uuid").val()
				success : function(result) {
					if(result.data == '1'){
						oAlert("设置成功!");
					}else{
						oAlert("设置失败");
					}
				}
			});
		});
	});
	
	
});

// 调整自适应表单宽度
function adjustWidthToForm() {
	var div_body_width = $(window).width() * 0.95;
	$(".form_header").css("width", div_body_width - 5);
	$(".div_body").css("width", div_body_width);
}

//保存完文档点击“确定”之后调用的刷新方法
function afterSaveRefleshWindow(fileUuid){
	window.location.reload();
}
