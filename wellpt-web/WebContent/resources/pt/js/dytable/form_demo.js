$(function() {
	var a=[]; 
	var a={}; 
	//保存数据时错误代码
	var SaveDataErrorCode = {
			SaveDataException:"SaveDataException"//保存数据错误  
	};
	// 初使化
	JDS.call({
		service : 'formDataService.getFormData',
		data : [$("#formUuid").val(), $("#dataUuid").val(), null],
		success : function(result) {
			// init('abc', data, 'save', submit);
			$("#abc").dytable({
				data : result.data,
				isFile2swf:true,
				enableSignature:true,//是否签名
				setReadOnly:false,//是否设置所有字段只读，true表示设置,false表示不设置
				supportDown:"2",//2表示防止下载 1表示支持下载 不设置表示默认支持下载
				btnSubmit : 'save',
				beforeSubmit : submit,
				buttons:[{"text": "选择完成任务","method":function(){alert(1);},"subtableMapping":"dy_form_id_report_evaluate"},
				         {"text": "选择未完成任务","method":function(){alert(2);},"subtableMapping":"dy_form_id_report_evaluate"},
				         {"text": "选择未成任务","method":function(){alert(3);},"subtableMapping":"dy_form_id_report_plan"},
				         {"text": "选择未完务","method":function(){alert(4);},"subtableMapping":"dy_form_id_report_plan"},
				         ],
				open: function(){
				},
				afterDialogSelect: function(params) {
				}
			});
			
			$("#abc").dytable("updateRowData", {
				tableId:"userform_xzsp_subbjsqd_cad",
				data : {"schedule_name":"123","original_schedule_name":""},
				rowid:"9937595f4b994ab1bdb2a8d967cf9f44"
			});

			
//			$("#abc").dytable("setSubFieldProperty", {
//				tableId:"userform_xzsp_subbjsqd_cad",
//				field:"schedule_name",
//			});
//			$("#abc").dytable("showSubFormField", {
//				tableId:"userform_xzsp_subbjsq",
//				field:"bujian_reason",
//				type:"1"
//			});
			// 事项名称
//			$("#abc").dytable("setFieldValue", {
//				mappingName : "matters_name",
//				value : "12321312"
//			});
			
//			$("#abc").dytable("setFieldValue", {
//				mappingName : "promise_transact_deadline",
//				value : "123个工作日",
//				type : "label",
//			});
			
//			$("#abc").dytable("setFieldValue",{
//				mappingName:"approval_type",
//				value:"015005001,015005002",
//				type:"checkbox"
//			})
			
//			$("#abc").dytable("hideSubForm",{
//				tableId : "001",
//			});
			
//			$("#abc").dytable("addRowData",{
//				tableId : "001",
//				data : {"apply_material_file":"fileC60F4B2084400001C2EF16D01A881DDF"}
//			});
//			
//			$("#abc").dytable("getAllRowData",{
//				tableId:"001"
//		});
//			$("#abc").dytable("delRowData",{
//				tableId:"001",
//				rowid:""
//			});
		},
		error : function(xhr, textStatus, errorThrown) {
		}
	});

	function submit() {
		//		$("#abc").dytable("setFieldValue",{mappingName:"dy_meeting_summary_detail_name",value:"111"});
//		$("#abc").dytable("getFieldForFormData",{formuuid:$("#formUuid").val(),fieldMappingName:"File_ZRZ"});
//		$("#abc").dytable("fileUpload",{modulename:"abc",uploadplace:"1"});
//		$("#abc").dytable("getFieldForFormData",{formuuid:"",mappingname:""});
//		$("#abc").dytable("addRowData", {tableId:"", data:{}});
//		$("#abc").dytable("getSubFieldInfo",{uuid:"26315cf3-8903-400b-86dd-a8d469393f43",fieldMappingName:"Plan_ZRZ"});
//		$("#abc").dytable("setAllFieldRead");
		var rootFormData = $("#abc").dytable("formData");
		var customformData = {};
		customformData.creator = $("#creator").val();
		customformData.rootFormDataBean = rootFormData;
		JDS.call({
			service : 'formDataService.save',
			data : rootFormData,
			success : function(result) {
					//设置数据UUID
					$("#dataUuid").val(result.data.uuid);
					var url = $("#current_form_url_prefix").val() + "?formUid=" + $("#formUuid").val() + "&dataUid=" + $("#dataUuid").val();
					$("#current_form_url").attr("href", url).show();
					$.jBox.info(dymsg.saveOk, dymsg.tipTitle);
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