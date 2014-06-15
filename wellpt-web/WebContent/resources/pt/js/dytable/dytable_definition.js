//加载全局国际化资源
I18nLoader.load("/resources/pt/js/global");
//加载动态表单定义模块国际化资源
I18nLoader.load("/resources/pt/js/dytable/dytable");

$(function() {
	$("#btn_open").click(function(){
		var id = $('#tt').jqGrid('getGridParam','selrow');//获取选中行的ID
		if (id && id.length > 0) {
			var select = $('#tt').jqGrid('getRowData',id);//根据ID 获取选中行的数据
			var url = ctx + '/dytable/demo?formUid=' + select.uuid;
			TabUtils.openTab(select.uuid, select.name, url);
		}
	});
	
	//调整浏览器窗口大小
	$(window).resize(function(){
		if(Browser.isChrome()){
			$("#tt").jqGrid("setGridWidth", $(window).width() - 30);
			$("#tt").jqGrid("setGridHeight", $(window).height() - 114);
		}else if(Browser.isIE()) {
			$("#tt").jqGrid("setGridWidth", $(window).width() - 22);
			$("#tt").jqGrid("setGridHeight", $(window).height() - 110);
		}else if(Browser.isMozila()) {
			$("#tt").jqGrid("setGridWidth", $(window).width() - 19);
			$("#tt").jqGrid("setGridHeight", $(window).height() - 114);
		}
		else{
			$("#tt").jqGrid("setGridWidth", $(window).width());
			$("#tt").jqGrid("setGridHeight", $(window).height());
		}
	});
	$(window).trigger("resize");
	
function openNewPage_formDefinition(flag,uuid) {
	var url = ctx + "/dytable/demo/openFormDefinition?uuid="+uuid+"&flag="+flag+"";
	window.open(url);
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//新增动态表单
$("#btn_form_add").click(function(){
	var flag = $('#flag').val();
	openNewPage_formDefinition(flag);
	$('#mainForm').find('input').val('');
	$('#moduleDiv').html('');
	$('#moduleText').text('');
	$('#fs3').css('display','');
});
//取消新增、编辑动态表单
$("#btn_form_cancel").click(function(){
	$('#moduleDiv').html("");
	$('#dlg_form_add').dialog('close');
});
//编辑动态表单
$("#btn_form_edit").click(function(){
	var uuid;
	var id = $('#tt').jqGrid('getGridParam','selrow');
	if (id && id.length > 0) {
		var select = $('#tt').jqGrid('getRowData',id);
		uuid = select.uuid;
		$('#uuid').val(select.uuid);
		var flag = $('#flag').val();
		openNewPage_formDefinition(flag,uuid);
	} else {
		$.jBox.info(dymsg.selectRecordMod,dymsg.tipTitle);
		
	}
});

//删除动态表单
$("#btn_form_del").click(function(){
	var id = $('#tt').jqGrid('getGridParam','selrow');
	if (id && id.length > 0) {
		$.jBox.confirm(dymsg.delConfirm,dymsg.tipTitle,function (v,h,f){
			if(v){
				uuid = id;
				$.ajax({
					type : "POST",
					url : contextPath + "/dytable/delete_form.action",
					data : "uuid=" + uuid,
					dataType : "json",
					success : function callback(data) {
						if(data == dyResult.success){
							$.jBox.info(dymsg.delSuccess,dymsg.tipTitle);
							$('#tt').trigger('reloadGrid');
						}
					}
				});
			}
		},{buttons:{'是':true,'否':false}});
	} else {
		$.jBox.info(dymsg.selectRecordDel, dymsg.tipTitle);
	}
});

//列表查询
$("#query_keyWord").keypress(function(e) {
	if (e.keyCode == 13) {
		$("#btn_query").trigger("click");
	}
});

//视图查询方法
$("#btn_form_query").click(function() {
	var queryValue = $("#query_keyWord").val();
	$("#tt").jqGrid("setGridParam", {
		postData : {
			"queryPrefix" : "query",
			"queryOr" : true,
			"query_LIKES_descname_OR_name_OR_version_OR_moduleName_OR_id" : queryValue,
		},
		page : 1
	}).trigger("reloadGrid");
});
});
