$(function() {
	var bean = {
		"uuid": null,	
		"name": null,
		"id": null,	
		"code": null,
		"fromId": null,
		"toType":null,
		"toId" : null,
		"toField" : null,
		"transformId" : null,
		"retransmissionNum" : null,
		"interval" : null,
		"restrain": null,
	};
	
	JDS.call({
		service : "exchangeDataTypeService.getExDataTypeList",
		async : false,
		success: function(result) {
			$("#fromTypeId").empty();
			var option_ = "";
			$.each(result.data, function(i) {
				option_ += "<option value=\"" + result.data[i].id + "\">" + result.data[i].name + "</option>/n/r";
     		});
			$("#fromTypeId").html(option_);
		}
	});
	
	$("#list").jqGrid(
		$.extend($.common.jqGrid.settings, {
			url :  ctx +"/common/jqgrid/query?queryType=exchangeRoute",
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
			rowNum : 20,
			rownumbers : true,
			rowList : [ 10, 20, 50, 100, 200 ],
			rowId : "uuid",
			pager : "#pager",
			sortname : "code",
			recordpos : "right",
			viewrecords : true,
			sortable : true,
			sortorder : "asc",
			multiselect : true,
			autowidth : true,
			height : 450,
			scrollOffset : 0,
			jsonReader : {
				root : "dataList",
				total : "totalPages",
				page : "currentPage",
				records : "totalRows",
				repeatitems : false
			},// 行选择事件
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
		var exchangeRoute = {};
		exchangeRoute.uuid = uuid;
		JDS.call({
			service : "exchangeRouteService.getBeanByUuid",
			data : [ uuid ],
			success : function(result) {
				bean = result.data;
				showDelButton();
				if(bean.toId==null||bean.toId==undefined){
					bean.toId = "";
				}
				if(bean.toId.indexOf(";") != -1) {
					var units = init(bean.toId, "toName");
					if(units != "-1")
						bean.toId = units.substring(1, units.length); 
				} 
				
				if(bean.transformId==""){
					$("#transformId").val("");
					$("#transformName").val("");
				} else {
					var tran = init(bean.transformId, "transformName");
					if(tran != "-1")
						bean.transformId = tran.substring(1, tran.length);
				}
				$("#module_form").json2form(bean);
				
				if(bean.toType){
					if(bean.toType=="type1"){
						$(".toIdClass").show();
						$(".toFieldClass").hide();
					}else if(bean.toType=="type2"){
						$(".toIdClass").hide();
						$(".toFieldClass").show();
					}else if(bean.toType=="type3"){
						$(".toIdClass").hide();
						$(".toFieldClass").hide();
					}
				}
				
				JDS.call({
					async : false,
					service : "exchangeRouteService.getToFieldsOption",
					data : [ $("#fromTypeId").val() ],
					success : function(result) {
						$("#toField").html(result.data);
					}
				});
				$("#toField").val(bean.toField);
				
				forbiddenComboTree('transformName');
				initComboTree('fromTypeId', 'transformName');
				forbiddenComboTree('toSysName');
			}
		});
		
	}
	$(".toType").click(function(){
		if($(this).val()=="type1"){
			$(".toIdClass").show();
			$(".toFieldClass").hide();
		}else if($(this).val()=="type2"){
			$(".toIdClass").hide();
			$(".toFieldClass").show();
		}else if($(this).val()=="type3"){
			$(".toIdClass").hide();
			$(".toFieldClass").hide();
		}
	});
	$("#fromTypeId").change(function(){
		JDS.call({
			service : "exchangeRouteService.getToFieldsOption",
			data : [ $(this).val() ],
			success : function(result) {
				$("#toField").html(result.data);
			}
		});
	});
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
		$("#"+name).val(tnames.substring(1,tnames.length));
		return tids;
	}
	$("#toName").click(function(){
		$.unit.open({
			title : "选择目的单位",
			labelField : "toName",
			valueField : "toId",
			type : "Unit",
			commonType : 1,
			close : function(e){
				$("#toSysName").comboTree("clear");
				if(StringUtils.isNotBlank(getValueById('toId'))) {
					forbiddenComboTree('toSysName');
				} else {
					forbiddenComboTree('toSysName');
				}
			}
		});
	});

	var setting = {
			async : {
				otherParam : {
					"serviceName" : "exchangeDataTypeService",
					"methodName" : "getViewAsTreeAsync",
					"data" : [getValueById('fromTypeId')]
				}
			}
	};
	$("#transformName").comboTree({
		labelField : "transformName",
		valueField : "transformId",
		treeSetting : setting,
		autoInitValue : false,
		//initServiceParam:["DY_FORM_ID_MAPPING"]
		width: 220,
		height: 220
	});
	
	function forbiddenComboTree(name) {
		//$("#"+name).comboTree("clear");
		$("#"+name).comboTree("disable");
	}
	
	function initComboTree(id,name) {
		if(StringUtils.isNotBlank(getValueById(id))) {
			setting.async.otherParam.data = getValueById(id);
			$("#"+name).comboTree("enable");
			$("#"+name).comboTree("setParams", {treeSetting : setting});
		}
	}
	
	// JQuery UI按钮
	$("input[type=submit], a, button", $(".btn-group")).button();
	// JQuery UI页签
	$("#tabs").tabs();

	// 新增
	$("#btn_add").click(function() {
		$("#module_form").clearForm(true);
		hideDelButton();
		forbiddenComboTree('transformName');
		forbiddenComboTree('toSysName');
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
		/*$("#cateName").val($("#cateUuid").find("option:selected").text());
		if(!($("#defaultWidth").val().match(/^\+?[1-9][0-9]*$/)||$("#defaultHeight").val().match(/^\+?[1-9][0-9]*$/))){
			alert("宽高请输入合法的数字");
			return false;
		}*/
		
		//收集表单数据
		
		$("#module_form").form2json(bean);
		if($("#transformName").val()==""){
			bean.transformId="";
		}
		JDS.call({
			service : "exchangeRouteService.saveBean",
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
				service : "exchangeRouteService.remove",
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
				service:"exchangeRouteService.deleteAllByIds",
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
	
	function getValueById(id){
		return $("#"+id).val();
	}
	
	
});
