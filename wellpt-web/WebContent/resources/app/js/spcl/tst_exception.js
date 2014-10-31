I18nLoader.load("/resources/app/js/hire/hireManager");

$(function() {
	var dataUuid = $("#dataUuid").val();
	var formUuid = $("#formUuid").val();
	var formDatas = loadFormDefinitionData(formUuid, dataUuid);
	
	if(typeof formDatas == "string"){
		formDatas = (eval("(" + formDatas +  ")"));
	}
	var titleElem=document.getElementsByTagName('title').item(0); 
	 
	var text =  eval("(" + formDatas.formDefinition +  ")").displayName +"(解析)";
	
	try{
		$(titleElem).text(text);//ie8不兼容
	}catch(e){
		
	}
	
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
	
	var formData = undefined;
	$("#save").click(function(){
		
		var url = ctx + "/special/dyform/saveFormData";
		
		formData = $("#spclform").dyform("collectFormData");
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
		var resumeStatus = $("#resumeStatus").val();
		
		$("#save").show();
		var url = ctx + '/app/hire/rowClick?form_uuid=' + formUuid + "&uuid=" + dataUuid + '&isEdit=true' + '&status=' +resumeStatus;
		location.href = url;
	});

	/**
	 * 关闭按钮Js
	 */
	$(".form_close").die().live('click',function(e){
		window.opener.location.reload();   
		window.close();
	});
});

//调整自适应表单宽度
function adjustWidthToForm() {
	var div_body_width = $(window).width() * 0.95;
	$(".form_header").css("width", div_body_width - 5);
	$(".div_body").css("width", div_body_width);
}

function editTstException(formId,dataUuid){
	var url = ctx + "/special/dyform/tst_exception?formId=" + formId +"&dataUuid=" + dataUuid;
	window.open(url);
}

function deleteTstException(){
	var checkedRec = $(".checkeds:checked").val();
	
	//删除的uuid数组
	var delArray = new Array();
	
	if(typeof(checkedRec) ==  "undefined"){
		oAlert("请选择数据删除！");
	}else{
		$(".checkeds:checked").each(function(){
			delArray.push($(this).val());
		});
		
		//删除确认框
		oConfirm(hireManager.deleteConfirm, function() {
			JDS.call({
				data : [delArray.toString()],
				service : "hireService.deleteResume",
				//$("#uuid").val()
				success : function(result) {
					if (result.data == 1) {
						oAlert(hireManager.deleteSuccess, function() {
							afterSaveRefleshWindow("");
							// window.location.href=ctx+'/fileManager/folder/indexList?id='+$("#folderId").val();
						});
					}
				}
			});
		});
	}
	
}
