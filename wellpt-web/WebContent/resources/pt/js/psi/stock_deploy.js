//物品管理模块配置页面调用的js
$(function() {
	//保存数据时错误代码
	var SaveDataErrorCode = {
			SaveDataException:"SaveDataException"//保存数据错误  
	};
	var dytableSelector = "#abc";
	// 初使化
	JDS.call({
		service : 'formDataService.getFormData',
		data : [$("#formUuid").val(), $("#dataUuid").val(),null],
		success : function(result) {
			$("#abc").dytable({
				data : result.data,
				isFile2swf:false,
				setReadOnly:null,//是否设置所有字段只读，true表示设置,false表示不设置
				supportDown:"1",//2表示防止下载 1表示支持下载 不设置表示默认支持下载
				btnSubmit : 'save',
				beforeSubmit : submit,
				buttons:[],
				open: function(){
					onDyformOpen();
				}
			});
		},
		error : function(xhr, textStatus, errorThrown) {
		}
	});
	// 动态表单初始化后回调处理
	function onDyformOpen() {
		// 调整自适应表单宽度
		adjustWidthToForm();
	}
	
	// 调整自适应表单宽度
	function adjustWidthToForm() {
		// 获取动态表单宽度
		var formWidth = $(dytableSelector + " .post-detail").width();
		$(".form_toolbar").css("width", formWidth + parseInt(35));
	}
	
	function submit() {
		var rootFormData = $("#abc").dytable("formData");
		var customformData = {};
		customformData.creator = $("#creator").val();
		customformData.rootFormDataBean = rootFormData;
		JDS.call({
			service : 'formDataService.save',
			data : rootFormData,
			success : function(result) {
				var formUuid =	$('#formUuid').val();
				var flag = $('#flag').val();
				var status = $('#status').val();
				if(flag == 1) {
					JDS.call({
						service:'stockService.saveTopFolder',
						data:["GOOD_MANAGE",result.data.uuid,formUuid],
						success :function(result) {
							//设置数据UUID
							$("#dataUuid").val(result.data);
							oAlert("保存成功！",function refresh() {
								refreshWindow($("#abc"));
							});
						}
					});
				}else if(flag == 2 && status == 1) {
					//新建分类
					JDS.call({
						service:'stockService.createNewFolder',
						data:[formUuid,result.data.uuid,status],
						success :function(result) {
							//设置数据UUID
							oAlert("保存成功！",function refresh() {
								window.opener.location.reload(); 
								window.close();
							});
						}
					});
				}else if(flag == 2 && status == 2) {
					//新建品名
					JDS.call({
						service:'stockService.createNewFolder',
						data:[formUuid,result.data.uuid,status],
						success :function(result) {
							//设置数据UUID
							oAlert("保存成功！",function refresh() {
								window.opener.location.reload(); 
								window.close();
							});
						}
					});
				}else if(flag == 2 && status == 3) {
					//新建物品规格
					JDS.call({
						service:'stockService.createNewFile',
						data:[formUuid,result.data.uuid],
						success :function(result) {
							//设置数据UUID
							oAlert("保存成功！",function refresh() {
								window.opener.location.reload(); 
								window.close();
							});
						}
					});
				}
					
			},
			error : function(xhr, textStatus, errorThrown) {
				var msg = JSON.parse(xhr.responseText);
				if(SaveDataErrorCode.SaveDataException === msg.errorCode) {
					var id = "abc";
					$("#"+id).dytable("showCompareData", {data:msg.data,id:id});
				}
			}
		});
	}
});