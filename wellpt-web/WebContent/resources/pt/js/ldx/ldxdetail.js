var temp = "";

$(function() {
	var btn_save = "B02600101";
	var btn_edit = "B026002002";
	var btn_del = "B026002001";
	var btn_save_add = "B02600102";
	var btn_cancel = "B02600103";
	var btn_save_new = "B02600104";
	
	var setReadOnly = true;
	if($("#readOnly").val()=="false"){
		setReadOnly = false;
	}
	// 初使化
	JDS.call({
		service : 'formDataService.getFormData',
		data : [$("#formUid").val(), $("#dataUid").val(), null],
		success : function(result) {
			$("#dyform").dytable({
				data : result.data,
				isFile2swf:false,
				setReadOnly:setReadOnly,//是否设置所有字段只读，true表示设置,false表示不设置
				supportDown:"1",//2表示防止下载 1表示支持下载 不设置表示默认支持下载
				btnSubmit : btn_save,
				beforeSubmit : submit
			});
		},
		error : function(xhr, textStatus, errorThrown) {
		}
	});
	adjustWidthToForm();
	$(window).resize(function(e) {
		// 调整自适应表单宽度
		adjustWidthToForm();
	});
	// 调整自适应表单宽度
	function adjustWidthToForm() {
		var div_body_width = $(window).width() * 0.95;
		$(".form_header").css("width", div_body_width - 5);
		$(".div_body").css("width", div_body_width);
	}
	
	// 发送处理
	function submit() {
			var rootFormData = $("#dyform").dytable("formData");
			//console.log(JSON.stringify(rootFormData));
			JDS.call({
				service : "ldxProductService.saveLdxProduct",
				data : [rootFormData],
				success : function(result) {
					if(result.data="Success"){
						var dataUuid = result.data.uuid;
						if(temp == "btn_save_add") {
							alert(JSON.stringify(result));
							oAlert("保存成功",function (){
								location.href = ctx + "/ldx/product?formUid=b9581420-cb41-40c7-a729-0d26233aee9f&dataUid="+dataUuid+"&showSubTable=true&readOnly=true&moduleid=d775a2cb-6bc5-4792-9ddc-3cd5ea8c1b74&wid=201441213014582";
								});
						}else if(temp == "btn_save_new") {
							oAlert("保存成功",function (){
								location.href = ctx + "/ldx/new_product?formUid=b9581420-cb41-40c7-a729-0d26233aee9f&moduleid=d775a2cb-6bc5-4792-9ddc-3cd5ea8c1b74&wid=201441213014582";
								});
						}else {
							oAlert("保存成功",function (){returnWindow();window.close();});
						}
					}else{
						oAlert("保存失败");
					}
				},
				error : function(xhr, textStatus, errorThrown) {
					oAlert("保存失败");
				}
			});
	}
	
	$("#"+ btn_edit).click(function() {
		var formUid = $("#formUid").val();
		var dataUid = $("#dataUid").val();
		var url = ctx+"/ldx/new_product?formUid="+formUid+"&dataUid="+dataUid+"&showSubTable=true";
		location.href = url;
	});
	
	$("#"+ btn_del).click(function() {
		var formUid = $("#formUid").val();
		var dataUid = $("#dataUid").val();
		JDS.call({
			async:false,
			service : "formDataService.delete",
			data : [formUid,dataUid],
			success : function(result) {
				oAlert("删除成功",function (){returnWindow();window.close();});
			}
		});
	});
	
	$("#"+ btn_save_add).click(function() {
		temp = "btn_save_add";
		submit(); 
	}) 
	
	$("#"+ btn_save_new).click(function() {
		temp = "btn_save_new";
		submit();
	}) 
	
	$("#"+btn_cancel).click(function() {
		window.close();
	})
	
});