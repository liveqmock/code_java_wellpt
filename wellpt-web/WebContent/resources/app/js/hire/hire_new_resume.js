I18nLoader.load("/resources/app/js/hire/hireManager");



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
	if(flag == 'new_resume'){
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
		
		$(window).resize();
	}
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
		
		formData = $("#abc").dyform("collectFormData");
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

/**
 * 新增简历
 */
function new_resume(formuuid,flag){
	
	var url = ctx + "/app/hire/new_resume?formUuid=" + formuuid + '&flag=' + flag;
	window.open(url);
}

/**
 * 拉入黑名单js事件
 */
function pull_into_black_list(formuuid){
	
	
	var temp = $(".checkeds:checked").val();
	//删除的uuid数组
	var delArray = new Array();
	
	if(typeof temp ==  "undefined"){
		oAlert(hireManager.pleaseCheckedRecord);
	}else{
		//alert($(".checkeds").val());
		$(".checkeds:checked").each(function(){
			delArray.push($(this).val());
		});
		
		//拉入黑名单确认框
		oConfirm(hireManager.pullIntoBlackList, function() {
			JDS.call({
				
				data:[formuuid,delArray.toString(),10],
				service:"hireService.changeResumeStatus",
				success:function(result) {
					if (result.data == 1) {
						oAlert(hireManager.pullIntoBlackListSuccess, function() {
							afterSaveRefleshWindow("");
							// window.location.href=ctx+'/fileManager/folder/indexList?id='+$("#folderId").val();
						});
					}
				}
			});
		});
	}	
}

/**
 * 删除简历(假删除)
 */
function delete_resume(){
	var temp = $(".checkeds:checked").val();
	
	//删除的uuid数组
	var delArray = new Array();
	
	if(typeof(temp) ==  "undefined"){
		oAlert(hireManager.pleaseCheckedRecord);
	}else{
		//alert($(".checkeds").val());
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

/**
 * 描述：选入按钮函数
 */
function check_into_need(){
	//alert('ok');
	var temp = $(".checkeds:checked");
	var resumeArray = new Array();
	if(typeof(temp) ==  "undefined"){
		oAlert(hireManager.pleaseCheckedRecord);
	}else{
		
		$(".checkeds:checked").each(function(){
			//存入数组中
			resumeArray.push($(this).val());
			
			//
		});
		
		var viewUuid = '5453cc13-3bb5-467a-82fe-8aee69a6929a';
		path = "/basicdata/dyview/view_show?viewUuid="+ viewUuid +"&currentPage=1&openBy=dytable";
		
		//弹出框标题
		var title = "选择需求";
		
		//发起请求，获得视图的数据，弹出框
		$.ajax({
			async:false,
			cache:false,
			url : ctx + path,
			success:function(data){
				var json = new Object(); 
				json.content = "<div class='dnrw' style='width:99%;'>" +data+"</div>";
		        json.title = title;
		        json.height= 600;
		        json.width= 800;
		        showDialog(json);
		        
		        //$(".dataTr").unbind("dblclick");
		        $(".dataTr").die().live("dblclick",function(){
		        	
		        	var paramsObj = new Object();
		        	//点击行，返回的jsonstr
		        	var jsonstr = $(this).attr("jsonstr");
		        	var jsonObj = eval("(" + urldecode(jsonstr) + ")");
		        	
		        	oConfirm(hireManager.confirmToNeed, function() {
		        		
		        		var form_uuid = jsonObj.formUuid;//人员增补清单formUuid
		        		var uUid = jsonObj.uUID;//人员增补清单数据uUid
		        		
		        		//alert(resumeArray.toString());
		        		JDS.call({
		    				service : "hireService.personConnectionNeed",
		    				
		    				data : [resumeArray.toString(),form_uuid,uUid],
		    				success : function(result) {
		    					
		    					if(result.data == '1'){
		    						oAlert(hireManager.checkedIntoSuccess,function(){
		    							//关闭对话框
			    						closeDialog();
			    						//刷新页面
			    						afterSaveRefleshWindow("");
		    						});
		    						
		    					}else{
		    						oAlert(hireManager.checkedIntoFail,function(){
		    							//关闭对话框
			    						closeDialog();
			    						//刷新页面
			    						afterSaveRefleshWindow("");
		    						});
		    						
		    					}
		    				}
		    			});
		        	});
		        	
		        });
			}
			
		});
	}
	
}

/**
 * 描述：将进入面试的人才放入人才库
 */
function pull_into_talent_pool(){
	
	var temp = $(".checkeds:checked").val();
	//删除的uuid数组
	var delArray = new Array();
	
	if(typeof temp ==  "undefined"){
		oAlert(hireManager.pleaseCheckedRecord);
	}else{
		//alert($(".checkeds").val());
		$(".checkeds:checked").each(function(){
			delArray.push($(this).val());
		});
		
		//拉入黑名单确认框
		oConfirm(hireManager.pullIntoTalentPool, function() {
			JDS.call({
				
				data:[delArray.toString()],
				service:"hireService.pullIntoTalentPool",
				success:function(result) {
					if (result.data == 1) {
						oAlert(hireManager.pullIntoTalentPoolSuccess, function() {
							afterSaveRefleshWindow("");
							// window.location.href=ctx+'/fileManager/folder/indexList?id='+$("#folderId").val();
						});
					}
				}
			});
		});
	}	
	
}





//保存完文档点击“确定”之后调用的刷新方法
function afterSaveRefleshWindow(fileUuid){
	window.location.reload();
}
