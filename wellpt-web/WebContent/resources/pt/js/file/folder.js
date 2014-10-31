I18nLoader.load("/resources/pt/js/fileManager");
$(function() {

	
	//选择所有文件夹树形下拉框
	$.folderTree.open({
		labelField:"showFolderName", //须要弹出下拉框的元素ID
		valueField:	"reservedText4",
		width: 220,
		height: 220
	});
	
	isCheckUpperLimits();
	isCheckLowerLimits();
	isCheckSingleUpperLimits();
	isCheckSingleLowerLimits();
	
	
	function isCheckUpperLimits(){
		if ($("#reservedText5").is(":checked")) {
			$(".upperLimits_set").css("display", "block");
		}
		else {
			$(".upperLimits_set").css("display", "none");
		}
	}
	function isCheckLowerLimits(){
		if ($("#reservedText7").is(":checked")) {
			$(".lowerLimits_set").css("display", "block");
		}
		else {
			$(".lowerLimits_set").css("display", "none");
		}
	}
	function isCheckSingleUpperLimits(){
		if ($("#reservedText9").is(":checked")) {
			$(".singleUpperLimits_set").css("display", "block");
		}
		else {
			$(".singleUpperLimits_set").css("display", "none");
		}
	}
	function isCheckSingleLowerLimits(){
		 if ($("#reservedText11").is(":checked")) {
	         $(".singleLowerLimits_set").css("display", "block");
	     }
	     else {
	         $(".singleLowerLimits_set").css("display", "none");
	     }
	}
	
	
	
	$("#reservedText5").click(function () {
		isCheckUpperLimits();
	});
	$("#reservedText7").click(function () {
		isCheckLowerLimits();
	});
	$("#reservedText9").click(function () {
		isCheckSingleUpperLimits();
	});
	 $("#reservedText11").click(function () {
		 isCheckSingleLowerLimits();
     });
	
	
	
/**
 * 页面点击角色时弹窗事件
 * @param distInputName
 * @param distInputIds
 */
function roleClick(distInputName,distInputIds){
	 	 
//		$.unit.open({
//			labelName: distInputName,
//			valueField : distInputIds,
//			initNames:$("#"+distInputName).val(),
//			initIds:$("#"+distInputIds).val(),
//			selectType : 4
//		});
	}

$("#viewName").click(function(){
	var options = new Object();
	options.labelField = "viewName"; //显示值视图名称
	options.valueField = "viewId";     //真实值视图uuid
	options.width = 250;
	options.height = 220;
	$.dyviewTree.open(options);
});
 


$("#libButton").click(function(){
	
	 
	if($("#title").val().length <=0){
		oAlert(fileManager.titleNotBeNull);
		$("#title").focus();
		return false;
	}
//	if($("#seqNum").val().length <=0){
//		oAlert(fileManager.numberNotBeNull);
//		$("#seqNum").focus();
//		return false;
//	}
	
//	 if((/^(\+|-)?\d+$/.test($("#seqNum").val())) ){  
//         
//	    }else{  
//	    	oAlert(fileManager.numberShouldBePositiveInteger);  
//	        $("#seqNum").focus(); 
//	        return false;  
//	    }  
	
	if($("#libManager").val().length <= 0){
		oAlert(fileManager.libAdminRequired);
		$("#libManagerNames").focus();
		return false;
	}
 
	var id = "";
	if($("#uuid").val().length == 0){
		id = $("#folderId").val();
	}
	 
	JDS.call({
		service : 'folderManagerService.isUniqueIdAndSeq',
		data : [$("#uuid").val(),$("#seqNum").val(),id,$("#parentUuid").val()],
		success : function(result) {
			 
			var dataFile = result.data;
			if(dataFile.length == 0){
				checkInputValueEq("libManager","sourcelibManagerValue","libManagerChange");
				checkInputValueEq("editAllFile","sourceEditAllFileValue","editAllFileChange");
				checkInputValueEq("createAllFile","sourceCreateAllFileValue","createAllFileChange");
				checkInputValueEq("readAllFile","sourceReadAllFileValue","readAllFileChange");
				checkInputValueEq("superReadAllFile","sourceSuperReadAllFileValue","superReadAllFileChange");
				checkInputValueEq("templateIds","sourceTemplateValue","templateChange");
				$("#libForm").submit();
			}
			else{
				oAlert(dataFile);
			}

			//$("#showDifDialog").oDialog("open");
		},
		error : function(xhr, textStatus, errorThrown) {}
	});
	
	
	
	
	 
});
 


$("#libManagerNames").click(function() {
	$.unit.open({
		title : fileManager.chooseUser,
		labelField : "libManagerNames",
		valueField : "libManager",
		initNames:$("#libManagerNames").val(),
		initIds:$("#libManager").val(),
		selectType : 1
	});
});

$("#editAllFileNames").click(function() {
	$.unit.open({
		title : fileManager.chooseUser,
		labelField : "editAllFileNames",
		valueField : "editAllFile",
		initNames:$("#editAllFileNames").val(),
		initIds:$("#editAllFile").val(),
		selectType : 1
	});
});

$("#createAllFileNames").click(function() {
	$.unit.open({
		title : fileManager.chooseUser,
		labelField : "createAllFileNames",
		valueField : "createAllFile",
		initNames:$("#createAllFileNames").val(),
		initIds:$("#createAllFile").val(),
		selectType : 1
	});
});


$("#readAllFileNames").click(function() {
	$.unit.open({
		title : fileManager.chooseUser,
		labelField : "readAllFileNames",
		valueField : "readAllFile",
		initNames:$("#readAllFileNames").val(),
		initIds:$("#readAllFile").val(),
		selectType : 1
	});
});

$("#superReadAllFileNames").click(function() {
	$.unit.open({
		title : fileManager.chooseUser,
		labelField : "superReadAllFileNames",
		valueField : "superReadAllFile",
		initNames:$("#superReadAllFileNames").val(),
		initIds:$("#superReadAllFile").val(),
		selectType : 1
	});
});


$("#folderManagerNames").click(function() {
	$.unit.open({
		title : fileManager.chooseUser,
		labelField : "folderManagerNames",
		valueField : "folderManager",
		initNames:$("#folderManagerNames").val(),
		initIds:$("#folderManager").val(),
		selectType : 1
	});
});


$("#editFolderFileNames").click(function() {
	$.unit.open({
		title : fileManager.chooseUser,
		labelField : "editFolderFileNames",
		valueField : "editFolderFile",
		initNames:$("#editFolderFileNames").val(),
		initIds:$("#editFolderFile").val(),
		selectType : 1
	});
});

$("#createFolderFileNames").click(function() {
	$.unit.open({
		title : fileManager.chooseUser,
		labelField : "createFolderFileNames",
		valueField : "createFolderFile",
		initNames:$("#createFolderFileNames").val(),
		initIds:$("#createFolderFile").val(),
		selectType : 1
	});
});


$("#readFolderFileNames").click(function() {
	$.unit.open({
		title : fileManager.chooseUser,
		labelField : "readFolderFileNames",
		valueField : "readFolderFile",
		initNames:$("#readFolderFileNames").val(),
		initIds:$("#readFolderFile").val(),
		selectType : 1
	});
});

$("#upperLimitsRemindPersons").click(function() {
	$.unit.open({
		title : fileManager.chooseUser,
		labelField : "upperLimitsRemindPersons",
		valueField : "reservedText6",
		initNames:$("#upperLimitsRemindPersons").val(),
		initIds:$("#reservedText6").val(),
		selectType : 1
	});
});
$("#lowerLimitsRemindPersons").click(function() {
	$.unit.open({
		title : fileManager.chooseUser,
		labelField : "lowerLimitsRemindPersons",
		valueField : "reservedText8",
		initNames:$("#lowerLimitsRemindPersons").val(),
		initIds:$("#reservedText8").val(),
		selectType : 1
	});
});
$("#singleUpperLimitsRemindPersons").click(function() {
	$.unit.open({
		title : fileManager.chooseUser,
		labelField : "singleUpperLimitsRemindPersons",
		valueField : "reservedText10",
		initNames:$("#singleUpperLimitsRemindPersons").val(),
		initIds:$("#reservedText10").val(),
		selectType : 1
	});
});
$("#singleLowerLimitsRemindPersons").click(function() {
	$.unit.open({
		title : fileManager.chooseUser,
		labelField : "singleLowerLimitsRemindPersons",
		valueField : "reservedText12",
		initNames:$("#singleLowerLimitsRemindPersons").val(),
		initIds:$("#reservedText12").val(),
		selectType : 1
	});
});



$("#fileCreateMsgFormatName").click(function() {
	$.messageTemplateTree.open({
		labelField:"fileCreateMsgFormatName", //消息格式名
		valueField:	"fileCreateMsgFormatId",//消息格式值
		category:"文件管理", //消息分类
		width: 300,
		height: 500
	});
});

$("#fileEditorMsgFormatName").click(function() {
	$.messageTemplateTree.open({
		labelField:"fileEditorMsgFormatName", //消息格式名
		valueField:	"fileEditorMsgFormatId",//消息格式值
		category:"文件管理", //消息分类
		width: 300,
		height: 500
	});
});

$("#templateNames").click(function() {
	/*
	$.unit.open({
		title : "选择模板",
		labelField : "templateNames",
		valueField : "templateIds",
		initNames:$("#templateNames").val(),
		initIds:$("#templateIds").val(),
		selectType : 4
	});*/
	
	$.dytableTree.open({
		labelField:"templateNames", //须要弹出下拉框的元素ID
		valueField:	"templateIds",//元素的value
		moduleId:"FILE" //要调用的模块ID，不传默认为全部
	});

	
});
$("#templateNames").trigger("click");
 
$("#flowName").click(function(){
	$("#flowName").workflowComboTree({
		labelField : "flowName",	//须要弹出下拉框的元素ID(流程名称)
			valueField : "flowId"	//元素的value(流程ID)
		});

});
$("#flowName").trigger("click");



$("#folderButton").click(function(){
	if($("#title").val().length <=0){
		oAlert(fileManager.titleNotBeNull);
		$("#title").focus();
		return false;
	}
	var seqNum = 0;
	seqNum = $("#seqNum").val();
	if($("#seqNum").val().length <=0){
		oAlert(fileManager.numberNotBeNull);
		$("#seqNum").focus();
		return false;
	}

	 if((/^(\+|-)?\d+$/.test(seqNum)) ){  
	         
	    }else{  
	    	oAlert(fileManager.numberShouldBePositiveInteger);  
	        $("#seqNum").focus(); 
	        return false;  
	    }  
 
	 JDS.call({
			service : 'folderManagerService.isUniqueIdAndSeq',
			data : [$("#uuid").val(),seqNum,"",$("#parentUuid").val()],
			success : function(result) {
				 
				var dataFile = result.data;
				if(dataFile.length == 0){
					checkInputValueEq("folderManager","sourceFolderManagerValue","folderManagerChange");
					checkInputValueEq("editFolderFile","sourceEditFolderFileValue","editFolderFileChange");
					checkInputValueEq("createFolderFile","sourceCreateFolderFileValue","createFolderFileChange");
					checkInputValueEq("readFolderFile","sourceReadFolderFileValue","readFolderFileChange");
					checkInputValueEq("templateIds","sourceTemplateValue","templateChange");
					
//					checkInputValueEq("reservedText6","sourceReservedText6","upperLimitsRemindPersonsChange");
//					checkInputValueEq("reservedText8","sourceReservedText8","lowerLimitsRemindPersonsChange");
//					checkInputValueEq("reservedText10","sourceReservedText10","singleUpperLimitsRemindPersonsChange");
//					checkInputValueEq("reservedText12","sourceReservedText12","singleLowerLimitsRemindPersonsChange");
					 
					$("#folderForm").submit();
				}
				else{
					oAlert(dataFile);
				}

				//$("#showDifDialog").oDialog("open");
			},
			error : function(xhr, textStatus, errorThrown) {}
		});
	 
	
	
});


var dataFolderSetting = {
		async : {
			enable : true,
			contentType : "application/json",
			url : ctx + "/json/data/services",
			otherParam : {
				"serviceName" : "folderManagerService",
				"methodName" : "getFolderTree"
			},
			type : "POST"
		},
		callback : {
			beforeClick : fileManagerBeforeClick
		}
	};

function fileManagerBeforeClick(treeId, treeNode) {
	// 最新选择的树结点
	latestSelectedNode = treeNode;
	 
	return true;
}

var showTreeDialogOptions = {
		autoOpen:false,
	    bgiframe: true,
	    modal: true,
	    title: fileManager.fileTreeTitle,
	    overlay: {
	        backgroundColor: '#000',
	        opacity: 0.5
	    },
		buttons : {}
	};

		showTreeDialogOptions.buttons[fileManager.buttonYes] = function() {

			if (latestSelectedNode.id != null && latestSelectedNode.id != -1) {
	    		// 查看详细
	    		$("#viewId").val(latestSelectedNode.id);
	    	}
	        $("#treeDialog").oDialog('close');
		};

		showTreeDialogOptions.buttons[fileManager.buttonCancel] = function() {
			$("#treeDialog").oDialog('close');
		};



$("#deleteButton").click(function(){
	oConfirm(fileManager.deleteConfirm, function(){
		JDS.call({
			service : "folderManagerService.deleteFolder",
			data : [ $("#uuid").val()],
			success : function(result) {
				if(result.data == 1){
	           	  	oAlert(fileManager.deleteSuccess, function(){
	           	  		refleshParentWinByFolder($("#parentUuid").val());
	           	  	});
				}
			}
		});
	});
});

function refleshParentWin(){
	window.opener.location.reload(); 
	window.close();
}

                            
/**
 * 校验文件夹表单的信息
 * @returns {Boolean} 
 */
/*
function checkFolderForm(){
	if($("#title").val().length <=0){
		oAlert("对不起，标题不能为空");
		$("#title").focus();
		return false;
	}
	if($("#seqNum").val().length <=0){
		oAlert("对不起，序号不能为空");
		$("#seqNum").focus();
		return false;
	}

	checkInputValueEq("folderManager","sourceFolderManagerValue","folderManagerChange");
	checkInputValueEq("editFolderFile","sourceEditFolderFileValue","editFolderFileChange");
	checkInputValueEq("createFolderFile","sourceCreateFolderFileValue","createFolderFileChange");
	checkInputValueEq("readFolderFile","sourceReadFolderFileValue","readFolderFileChange");

	return true;
}*/


/*
判断 inputIds元素的值，是否与divIds中的元素值一致
*/
function checkInputValueEq(inputIds,divIds,setValue){
	var  sourceValue = $("#"+inputIds).val();

	var  destValue = $("#"+divIds).html();
 
	var sourceArr = sourceValue.split(";");
 
	var destArr = destValue.split(";");
 
	if(sourceArr.sort().toString() == destArr.sort().toString()){
		
		$("#"+setValue).val("0");
	 
	}
	else{
		 
		$("#"+setValue).val("1");
		}
}

//动态表单单据关闭
$(".form_header h2").after(
		$("<div class='form_close' title='"+fileManager.btnClose+"'></div>"));
$(".form_header .form_close").click(function(e) {
	window.close();
});
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
$(window).trigger("resize");
});
//刷新父页面的窗口
function refleshParentWinByFolder(parentUuid) {
	pageLock("show");
	window.opener.refleshParentWinByFolder(parentUuid);//父窗口右侧刷新
	window.opener.loadLeftFolderTree1();//父窗口导航树刷新
	window.close();
	pageLock("hide");
}

