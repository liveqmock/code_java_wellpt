$(function() {
	var bean = {
		"uuid": null,	
		"name": null,
		"id": null,	
		"code": null,
		"formId": null,
		"unitId": null,
		"retain": null,
		"tableName" : null,
		"text" : null,
		"businessTypeId" : null,
		"showToUnit" : null,
		"businessId" : null,
		"reportLimit" : null,
		"receiveLimit" : null
	};
	
	JDS.call({
		service : "exchangeDataTypeService.getBusinessTypeList",
		async : false,
		success: function(result) {
			$("#businessTypeId").empty();
			var option_ = "";
			$.each(result.data, function(i) {
				option_ += "<option value=\"" + result.data[i].id + "\">" + result.data[i].name + "</option>/n/r";
	 		});
		    $("#businessTypeId").html(option_);
		}
	});
	
	$("#list").jqGrid(
		$.extend($.common.jqGrid.settings, {
			url :  ctx +"/common/jqgrid/query?queryType=exchangeDataType",
			datatype : 'json',
			mtype : "POST",
			colNames : [ "uuid", "名称", "ID", "编号" ],
			colModel : [ {
				name : "uuid",
				index : "uuid",
				width : "180",
				hidden : true,
				key : true
			}, {
				name : "name",
				index : "name",
				width : "40"
			},{
				name : "id",
				index : "id",
				width : "30"
			},{
				name : "code",
				index : "code",
				width : "30"
			} ],
			
			onSelectRow : function(id) {
				getModuleById(id);
	//			var rowData = $(this).getRowData(id);
	//			getModuleById(rowData.uuid);
			},
			loadComplete : function(data) {
				$("#list").setSelection($("#list").getDataIDs()[0]);
			}
		}));

	// 根据模块ID获取模块信息
	function getModuleById(uuid) {
		$("#module_form").clearForm(true);
		var exchangeDataType = {};
		exchangeDataType.uuid = uuid;
		JDS.call({
			service : "exchangeDataTypeService.getBeanByUuid",
			data : [ uuid ],
			success : function(result) {
				bean = result.data;
				if(bean.unitId.indexOf(";") != -1) {
					var units = init(bean.unitId, "unitName");
					if(units != "-1")
						bean.unitId = units.substring(1, units.length); 
				} 
				
				var temp1 = bean.formId.split(";")[0];
				var temp2 = bean.formId.split(";")[1];
				bean.formId = temp1;
				$("#formName").val(temp2);
				showDelButton();
				$(".fileDownLoad").text(bean.name+".xml");
				$("#attach").show();
				$("#module_form").json2form(bean);
				$("#businessId").val(bean.businessId.split(":")[0]);
				$("#businessName").val(bean.businessId.split(":")[1]);
			}
		});
		
	}
	function init(value, name) {
		if(value.indexOf(";") == -1) {
			return "-1";
		}
		var temp = value.split(","); //1;11
		var tids ="";
		var tnames ="";
		for (var i=0; i<temp.length; i++) {
			var idname = temp[i];
			var temp1 = idname.split(";")[0];
			var temp2 = idname.split(";")[1];
			tids += ";" + temp1;
			tnames += ";" + temp2;
		}
		//value = tids.substring(1,tids.length);
		$("#"+name).val(tnames.substring(1,tnames.length));
		return tids;
	}
	// JQuery UI按钮
	$("input[type=submit], a, button", $(".btn-group")).button();
	// JQuery UI页签
	$("#tabs").tabs();

	// 新增
	$("#btn_add").click(function() {
		$("#module_form").clearForm(true);
		hideDelButton();
		$("#attach").hide();
	});
	function clear() {
		$("#module_form").clearForm(true);
		// 清空JSON
		$.common.json.clearJson(bean);
	}
	
	//隐藏删除按钮
	function hideDelButton(){
		$("#btn_del").hide();
	}
	//显示删除按钮
	function showDelButton(){
		$("#btn_del").show();
	}

	// 保存
	$("#btn_save").click(function() {
		//$("#cateName").val($("#cateUuid").find("option:selected").text());
		if($("#reportLimit").val()==''){
			
		}  else if(!($("#reportLimit").val().match("^[0-9]*[1-9][0-9]*$"))){
			alert("上报时限请输入合法的数字");
			return false;
		}
		
		if($("#receiveLimit").val()=='') {
			
		} else if(!$("#receiveLimit").val().match("^[0-9]*[1-9][0-9]*$")) {
			alert("接收时限请输入合法的数字");
			return false;
		}
		if($("#formName").val()== ""){
			alert("请选择动态表单");
			return false;
		}
		
		
		//收集表单数据
		var business = $("#businessId").val()+":"+$("#businessName").val();
		$("#module_form").form2json(bean);
		bean.businessId = business;
		JDS.call({
			service : "exchangeDataTypeService.saveBean",
			data : [ bean ],
			async:false,
			validate : true,
			success : function(result) {
				// 保存成功刷新列表
				$("#list").trigger("reloadGrid");
				showDelButton();
				alert("保存成功！");
			}
		});
	});

	// 删除
	$("#btn_del").click(function() {
		if (bean.uuid == null || bean.uuid == "") {
			alert("请选择记录！");
			return true;
		}
		var name = bean.name;
		if (confirm("确定要删除页面元素[" + name + "]吗？")) {
			JDS.call({
				service : "exchangeDataTypeService.remove",
				data : [ bean.uuid ],
				success : function(result) {
					clear();
					// 删除成功刷新列表
					$("#list").trigger("reloadGrid");
				}
			});
		}
	});
	// 批量删除
	$("#btn_delAll").click(function() {
		var rowids = $("#list").jqGrid('getGridParam', 'selarrrow');
		if (rowids.length == 0) {
			alert("请选择记录!");
			return;
		}
		if (confirm("确定要删除所选资源？")) {
			JDS.call({
				service:"exchangeDataTypeService.deleteAllByIds",
				data:[rowids],
				success:function(result) {
					alert("删除成功!");
					//删除成功刷新列表
					clear();
					$("#list").trigger("reloadGrid");
				}
			});
		}
	});
	//查询
	$("#btn_query").click(function() {
		var queryValue = $("#query_keyWord").val();
		$("#list").jqGrid("setGridParam",{
			postData : {
				"queryPrefix" : "query",
				"queryOr" : true,
				"query_LIKES_name_OR_id_OR_code" : queryValue,
			},
			page : 1
		}).trigger("reloadGrid");
	});
	//回车查询
	$("#query_keyWord").keypress(function(e) {
		if (e.keyCode == 13) {
			$("#btn_query").trigger("click");
		}
	});
	Layout.layout({
		west__size : 480
	});
	var setting = {
			async : {
				otherParam : {
					"serviceName" : "getViewDataService",
					"methodName" : "getForms",
					"data":"4"
				}
			},
			check : {
				enable : true,
				chkStyle : "radio"
			}
	};
	$("#formName").comboTree({
		labelField: "formName",
		valueField: "formId",
		treeSetting : setting,
		width: 220,
		height: 220
	});
	$(".fileDownLoad").click(function(){
		if($('#uuid').val()==""){
			alert("请先保存数据");
		}else{
//			alert(contextPath+'/exchangedata/dataconfig/downLoadXml?uuid='+$('#uuid').val());
			location.href=contextPath+'/exchangedata/dataconfig/downLoadXml?uuid='+$('#uuid').val();
		}
	});
	
	$("#unitName").click(function(){
		$.unit.open({
			title : "选择目的单位",
			labelField : "unitName",
			valueField : "unitId",
			type : "Unit",
			commonType : 1,
			close : function(e){
				if(StringUtils.isNotBlank($("#"+id).val())) {
					forbiddenComboTree('unitName');
				} else {
					forbiddenComboTree('unitName');
				}
			}
		});
	});
	
	var setting2 = {
			async : {
				otherParam : {
					"serviceName" : "exchangeDataTypeService",
					"methodName" : "getBusinessHandleList",
				}
			},
			check : {
				enable : true,
				chkStyle : "radio"
			}
	};
	$("#businessName").comboTree({
		labelField: "businessName",
		valueField: "businessId",
		treeSetting : setting2,
		width: 220,
		height: 220
	});
});
