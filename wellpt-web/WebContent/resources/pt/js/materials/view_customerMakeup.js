I18nLoader.load("/resources/app/js/hire/hireManager");
$(function() {
	
	console.log("dyform_demo from here");
	var time1 = (new Date()).getTime();
	var dataUuid = $("#dataUuid").val();
	var formUuid = $("#formUuid").val();
	
	if (typeof dataUuid != "undefined" && typeof formUuid != "formUuid") {

		var formDatas = loadFormDefinitionData(formUuid, dataUuid);
		
		var time2 = (new Date()).getTime();
		console.log("loadFormDefinitionData:" + (time2 - time1) / 1000.0 + "s");

		if (typeof formDatas == "string") {
			formDatas = (eval("(" + formDatas + ")"));
		}
		var titleElem = document.getElementsByTagName('title').item(0);

		var text = eval("(" + formDatas.formDefinition + ")").displayName
				+ "(解析)";

		try {
			$(titleElem).text(text);// ie8不兼容
		} catch (e) {

		}
		var time3 = (new Date()).getTime();

		console.log("获取数据:" + (time3 - time1) / 1000.0 + "s");
		
			$("#customerMakeupForm").dyform({
				definition : formDatas.formDefinition,
				data : formDatas.formDatas,
				displayAsLabel : false,// 显示为文本
				displayAsFormModel : false,// displayAsLabel为true的前提下该参数才有效,默认为true
				// false:表示不用显示单据,true:使用显示单据,这时该若找不到对应的显示单据，则直接以该表单的模板做为显示单据
				success : function() {
					console.log("表单解析完毕");
					adjustWidthToForm();
				},
				error : function(msg) {
					oAlert(msg);
				}
			});


	}

	// 调整自适应表单宽度
	function adjustWidthToForm() {
		var div_body_width = $(window).width() * 0.95;
		$(".form_header").css("width", div_body_width - 5);
		$(".div_body").css("width", div_body_width);
	}
	var formData = undefined;
	
	/**
	 * 保存按钮点击事件
	 */
	$("#form_save").click(function() {
		/*
		 * if(validateForm == undefined){ alert("请先验证数据");
		 * return; }else if(validateForm == false){
		 * alert("验证失败"); return; }else if(formData ==
		 * undefined){ alert("请先收集数据"); return; }
		 */
		//var flag = $("#flag").val();

		var url = ctx + "/materials/addCLBL";

		formData = $("#customerMakeupForm").dyform("collectFormData");
		console.log( JSON.stringify(formData));
		//formData.flag = flag;
		$.ajax({
					url : url,
					type : "POST",
					data : JSON.stringify(formData),
					dataType : 'json',
					contentType : 'application/json',
					success : function(result) {
						if (result.success == "true"
								|| result.success == true) {
							alert("数据保存成功dataUuid="
									+ result.data);
							
						
						} else {
							alert("数据保存失败");
						}
					},
					error : function(data) {
						alert("数据保存失败");
					}
				});

	});

});
