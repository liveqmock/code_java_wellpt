I18nLoader.load("/resources/pt/js/fileManager");
$(function() {
	$(".folder_list_head_first").live("click",function(){
		pageLock("show");
		$.ajax({
			type : "POST",
			url : contextPath+"/fileManager/folder/indexList",
			data : "id="+$("#folder_list_parentUuid").val(),
			dataType : "text",
			success : function callback(result) {
				$("#fileListIframe").html(result);
				pageLock("hide");
			}
		});
	});
	$(".folder_list_head_second").live("click",function(){
		pageLock("show");
		$.ajax({
			type : "POST",
			url : contextPath+"/fileManager/folder/indexList",
			data : "id="+"",
			dataType : "text",
			success : function callback(result) {
				$("#fileListIframe").html(result);
				pageLock("hide");
			}
		});
	});
	 $(".folder_list_head_third").live("click",function(){
		 pageLock("show");
			$.ajax({
				type : "POST",
				url : contextPath+"/fileManager/folder/indexList",
				data : "id="+$("#folder_list_tempUuid").val(),
				dataType : "text",
				success : function callback(result) {
					$("#fileListIframe").html(result);
					pageLock("hide");
				}
			});
	 });
	
	$("#move").live("click",function(){
		setUpIds();
		var currentId = $("#currentId").val();
		if(currentId.length <=0){
			oAlert(fileManager.pleaseChooseAndOperate);
			return ;
		}else{
			$("#currentOperate").val("move");
			showFolderTree();
		}
		
	});
	
	$("#delete").live("click",function(){
		setUpIds();
		oConfirm(fileManager.deleteConfirm,function(){
			JDS.call({
    			service : "folderManagerService.deleteObjects",
    			data : [ $("#currentId").val()],
    			success : function(result) {
    				oAlert(fileManager.deleteSuccess, function(){
    					pageLock("show");
    					$.ajax({
    						type : "POST",
    						url : contextPath+"/fileManager/folder/indexList",
    						data : "id="+$("#parentUuid").val(),
    						dataType : "text",
    						async:false,
    						success : function callback(result) {
    	 						$("#fileListIframe").html(result);
    						}
    					});
    					loadLeftFolderTree1();
    					pageLock("hide");
    				});
    			}
    		});
		});
	});
	
	
	
	
	$("#copy").live("click",function(){
		setUpIds();
		var currentId = $("#currentId").val();
		if(currentId.length <=0){
			oAlert(fileManager.pleaseChooseAndOperate);
			return ;
		}else{
			$("#currentOperate").val("copy");
			showFolderTree();
		}
	});
	
	$("#excelSelect").change(function(){ 
		var currentValue=$(this).children('option:selected').val();
		$("#currentTemplateId").val(currentValue);
	});
	
	$("#uploadBtn").live("click",function(){
		var templateId = $("#currentTemplateId").val();
		
		if(templateId.length <= 0){
			oAlert(fileManager.pleaseChooseTemplate);
			return ;
		}else{
			$.ajaxFileUpload({
	            url:ctx + '/fileManager/folder/uploadExcel?folderId='+$("#parentUuid").val()+"&templateId="+templateId,//链接到服务器的地址
	            secureuri:false,
	            fileElementId:'fileExcel',//文件选择框的ID属性
	            dataType: 'text',  //服务器返回的数据格式
	            success: function (data, status){
	           	  	oAlert(fileManager.fileUploadSuccess, function(){
//	           	  		window.location.href=ctx+'/fileManager/folder/indexList?id='+$("#parentUuid").val();
	           	 	$.ajax({
						type : "POST",
						url : contextPath+"/fileManager/folder/indexList",
						data : "id="+$("#parentUuid").val(),
						dataType : "text",
						success : function callback(result) {
	 						$("#fileListIframe").html(result);
						}
					});
	           	  	});
	            },
	            error: function (data, status, e){  
	           	 $.jBox.info(dymsg.fileUploadFailure,dymsg.tipTitle);//弹出对话框
	            }
			});
		}
	});
	
	 //初始化导入对话框
	 function initImportDialog(){
		 var importDialogOptions={
					autoOpen:false,
				    bgiframe: true,
				    title:fileManager.fileImport,
				    modal: true,
				    height: 220,
				    width:450,
				    open: function(event, ui) {
				    	JDS.call({
				    		service : "folderManagerService.getAllExcelImportRules",
							success : function(result) {
								var $excelSelect = $("#excelSelect");
								$excelSelect.html("");
								$excelSelect.append("<option value=''>"+fileManager.templateOptionDefaultSelect+"</option>");
								$.each(result.data, function(i){
									$excelSelect.append("<option value='" + this.id + "'>"+ this.name +"</option>");
								});
							}
						});
				    },
				    overlay: {
				        backgroundColor: '#000',
				        opacity: 0.5
				    },
				    buttons: {
				    }
				};
			
			importDialogOptions.buttons[fileManager.buttonYes]=function(){
				var templateId = $("#currentTemplateId").val();
				if(templateId.length <= 0){
					oAlert(fileManager.pleaseChooseTemplate);
					return ;
				}
				var uploadFileName = $("#fileExcel", this).val();
				if(StringUtils.isBlank(uploadFileName)){
					oAlert(fileManager.pleaseChooseFile);
					return;
				}
				$.ajaxFileUpload({
		            url:ctx + '/fileManager/folder/uploadExcel?folderId='+$("#parentUuid").val()+"&templateId="+templateId,//链接到服务器的地址
		            secureuri:false,
		            type:'post',
		            fileElementId:'fileExcel',//文件选择框的ID属性
		            dataType: 'text',  //服务器返回的数据格式
		            success: function (data, status){
		           	  	oAlert(fileManager.fileUploadSuccess, function(){
//		           	  		window.location.href=ctx+'/fileManager/folder/indexList?id='+$("#parentUuid").val();
		           	 	$.ajax({
							type : "POST",
							url : contextPath+"/fileManager/folder/indexList",
							data : "id="+$("#parentUuid").val(),
							dataType : "text",
							success : function callback(result) {
		 						$("#fileListIframe").html(result);
							}
						});
		           	 	$("#importDialog").oDialog('close');
		           	  	});
		            },
		            error: function (data, status, e){  
		            	$("#importDialog").oDialog('close');
		            }
				});
			};
			
			
			importDialogOptions.buttons[fileManager.buttonCancel]=function(){
				$("#importDialog").oDialog('close');
			}
			;
			$("#importDialog").oDialog(importDialogOptions);
	 }
	

	$("#import").live("click",function(){
		initImportDialog();
		$("#importDialog").oDialog("open");
	});
	
	$("#checkAll").live("click",function(){
		
		$("input[name='objId']").prop("checked", this.checked);
		
		
//		if($(this).attr("checked") == true){
//			$("input[name='objId']").each(function(){
//				$(this).attr("checked",true);
//			});
//		}else{
//			$("input[name='objId']").each(function(){
//				$(this).attr("checked",false);
//			});
//		}
	});

	 $("input[name='objId']").live("click",function() {
		    var $subs = $("input[name='objId']");
		    $("#checkAll").attr("checked" , $subs.length == $subs.filter(":checked").length ? true :false);
		  });
	
	 //初始化动态表单模板对话框
	function initShowTemplateDialog(){
		var showTemplateDialogOptions={
				autoOpen:false,
			    bgiframe: true,
			    modal: true,
			    title:fileManager.templateChooseTitle,
			    closeOnEscape: false,
			    open: function(event, ui) { },
			    overlay: {
			        backgroundColor: '#000',
			        opacity: 0.5
			    },
			    buttons: {
			    }
		};
		showTemplateDialogOptions.buttons[fileManager.buttonYes]=function(){
			if($("#templateList").val().length == 0){
				oAlert(fileManager.pleaseChooseTemplate);
				return;
			}else{
				$("#showTemplateDialog").oDialog('close');
				fileManagerAddFunc("",ctx + "/fileManager/file/add?folderId=" + $("#parentUuid").val()+"&dynamicFormId="+$("#templateList").val());
			}
		};
		showTemplateDialogOptions.buttons[fileManager.buttonCancel]=function(){
			$("#showTemplateDialog").oDialog('close');
		};
		$("#showTemplateDialog").oDialog(showTemplateDialogOptions);
	}
	
	$("#addFile").live("click",function(){
		var map = null;
		JDS.call({
			service : "fileManagerService.loadTemplateByFolderId",
			data : [ $("#parentUuid").val()],
			async: false,
			success : function(result) {
				map = result.data;
			}
		});
		var len = map.size;
		if(len == 0){
			oAlert(fileManager.fileCreateError);
		}
		else
		if(len == 1){
			fileManagerAddFunc("",ctx + "/fileManager/file/add?folderId=" + $("#parentUuid").val()+"&dynamicFormId="+map.ids[0]);
		}
		else{
			var selectObj = $("#templateList");
			$("#templateList").empty();
			selectObj.append("<option value=''>"+fileManager.templateOptionDefaultSelect+"</option>");
			for(var k=0; k < len ; k++){
				
				selectObj.append("<option value='"+map.ids[k]+"'>"+map.names[k]+"</option>");
				
			}
			initShowTemplateDialog();
			$("#showTemplateDialog").oDialog({autoOpen:true});
		}
	});
	var newFileUrl = "";//${ctx}/fileManager/file/add?folderId=${uuid}
	function addNewFile(url){
		newFileUrl = url;
	}
	
	
	// JQuery zTree设置
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
			beforeClick : beforeClick,
			onAsyncSuccess : choseIcon
		}
	};
	// 树结点点击处理
	function beforeClick(treeId, treeNode) {
		// 最新选择的树结点
		latestSelectedNode = treeNode;
		if (treeNode.id != null && treeNode.id != -1) {
			// 查看详细
			$("#moveToFolderId").val(treeNode.id);
		}
		return true;
	}
	// JQyery zTree加载数据字典树结点
	function loadFolderTree() {
		$.fn.zTree.init($("#folder_tree"), dataFolderSetting);
	}
	
	//初始化树形选择框
	function initTreeDialog(){
		var treeDialogOptions={
				autoOpen:false,
			    bgiframe: true,
			    modal: true,
			    title:fileManager.fileTreeTitle,
			    overlay: {
			        backgroundColor: '#000',
			        opacity: 0.5
			    },
			    buttons: {
			    }
		};
		
		treeDialogOptions.buttons[fileManager.buttonYes]=function() {
	    	oConfirm(fileManager.sureToDoList, function(){
	    		JDS.call({
	    			service : "folderManagerService.updateFolderDest",
	    			data : [ $("#currentId").val(),$("#objType").val(),$("#currentOperate").val(),$("#moveToFolderId").val()],
	    			success : function(result) {
	    				pageLock("show");
	    				oAlert(result.data.msg, function(){
	    					$.ajax({
	    						type : "POST",
	    						url : contextPath+"/fileManager/folder/indexList",
	    						data : "id="+$("#parentUuid").val(),
	    						dataType : "text",
	    						success : function callback(result) {
	    	 						$("#fileListIframe").html(result);
	    						}
	    					});
	    				});
	    				loadLeftFolderTree1();
	    				pageLock("hide");
	    			}
	    		});
	        	$("#treeDialog").oDialog('close');
	    	});
	    };
	    treeDialogOptions.buttons[fileManager.buttonCancel]=function() {
	    	$("#treeDialog").oDialog('close');
	    };
		$("#treeDialog").oDialog(treeDialogOptions);
	}
	
	function setUpIds(){
		var ids = "";
		$("input[name='objId']").each(function(){
			 
			if($(this).attr("checked") == "checked"){
				if(ids.length >0){
					ids = ids +";";
				}
				ids = ids + $(this).attr("value")+","+$(this).attr("objType");
				
			}
		});
		
		$("#currentId").val(ids);
		 
	}
	
	//显示文件夹选择树
	function showFolderTree(){
		initTreeDialog();
		$.fn.zTree.init($("#folder_tree"), dataFolderSetting);
		$("#treeDialog").oDialog({autoOpen:true});
	}
});


//初始化树形选择框1
function initTreeDialog1(){
	var treeDialogOptions={
			autoOpen:false,
		    bgiframe: true,
		    modal: true,
		    title:fileManager.fileTreeTitle,
		    overlay: {
		        backgroundColor: '#000',
		        opacity: 0.5
		    },
		    buttons: {
		    }
	};
	
	treeDialogOptions.buttons[fileManager.buttonYes]=function() {
    	oConfirm(fileManager.sureToDoList, function(){
    		JDS.call({
    			service : "folderManagerService.updateFolderDest",
    			data : [ $("#currentId").val(),$("#objType").val(),$("#currentOperate").val(),$("#moveToFolderId").val()],
    			success : function(result) {
    				oAlert(result.data.msg, function(){
    					pageLock("show");
    					$.ajax({
    						type : "POST",
    						url : contextPath+"/fileManager/folder/indexList",
    						data : "id="+$("#parentUuid").val(),
    						dataType : "text",
    						success : function callback(result) {
    	 						$("#fileListIframe").html(result);
    						}
    					});
    					loadLeftFolderTree1();
    					pageLock("hide");
    				});
    			}
    		});
        	
        	$("#treeDialog").oDialog('close');
    	});
    };
    
    treeDialogOptions.buttons[fileManager.buttonCancel]=function() {
    	$("#treeDialog").oDialog('close');
    };
    
	
	$("#treeDialog").oDialog(treeDialogOptions);
}

//具体文件显示文件夹树
function fileManagerShowFolderTree(){
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
				beforeClick : fileManagerBeforeClick,
				onAsyncSuccess : choseIcon
			}
		};
	initTreeDialog1();
	$.fn.zTree.init($("#folder_tree"), dataFolderSetting);
	
	$("#treeDialog").oDialog({autoOpen:true});
}


function fileManagerBeforeClick(treeId, treeNode) {
	// 最新选择的树结点
	latestSelectedNode = treeNode;
	if (treeNode.id != null && treeNode.id != -1) {
		// 查看详细
		$("#moveToFolderId").val(treeNode.id);
	}
	return true;
}

function fileManagerDel(objType,id){
	$("#currentId").val(id+","+objType);
	oConfirm(fileManager.deleteConfirm, function(){
		JDS.call({
			service : "folderManagerService.deleteObjects",
			data : [ $("#currentId").val()],
			success : function(result) {
				oAlert(fileManager.deleteSuccess, function(){
					pageLock("show");
					$.ajax({
						type : "POST",
						url : contextPath+"/fileManager/folder/indexList",
						data : "id="+$("#parentUuid").val(),
						dataType : "text",
						success : function callback(result) {
	 						$("#fileListIframe").html(result);
						}
					});
					loadLeftFolderTree1();
					pageLock("hide");
				});
			}
		});
	});
}

function fileManagerCopy(objType,id){
	$("#currentId").val(id+","+objType);
	
	$("#currentOperate").val("copy");
	fileManagerShowFolderTree();
}

function fileManagerMove(objType,id){
	$("#currentId").val(id+","+objType);
	$("#currentOperate").val("move");
	fileManagerShowFolderTree();
	
}
var isSearchAuth = false;
function setFunctionShow(obj){
	if(isSearchAuth){
		return;
	}
	isSearchAuth = true;
	 
	var folderId = $(obj).attr("objId");
	 
	var haveGet = $(obj).attr("haveGet");
	if("1" == haveGet){
		isSearchAuth = false;
		return;
	}
	else{
		$(obj).attr("haveGet","1");
		JDS.call({
			service : "folderManagerService.haveDelLib",
			data : [ folderId],
			mask : false,
			success : function(result) {
				if(result.data == '1'){
					
					$(obj).find("div[class='file_operate']").append("<button type='button' class='button' onclick=\"fileManagerEditFunc('','','"+ctx+"/fileManager/folder/edit?uuid="+folderId+"');\">配置</button>" +
							"<button type='button' class='button' onclick=\"fileManagerDel('folder','"+folderId+"');\">删除</button>");
					
				}
				
				isSearchAuth = false;
			}
		});
	}
}

function fileManagerAddFunc(name,url){
	var uuid = "fileManager-add-tap";
	window.open(url);
}

function fileManagerEditFunc(name,id,url){
	window.open(url);
}
function choseIcon(){
	var closeFCount = 1;
	var openFCount = 1;
	var fileCount = 1;
	$(".node_folder_ico_close").each(function(){
		if(closeFCount%6==1){
			$(this).parent().prev().css("background",'url("'+ctx+'/resources/theme/images/v1_file_icon.png") no-repeat scroll -7px -4px transparent');
		}else if(closeFCount%6==2){
			$(this).parent().prev().css("background",'url("'+ctx+'/resources/theme/images/v1_file_icon.png") no-repeat scroll -7px -26px transparent');
		}else if(closeFCount%6==3){
			$(this).parent().prev().css("background",'url("'+ctx+'/resources/theme/images/v1_file_icon.png") no-repeat scroll -7px -47px transparent');
		}else if(closeFCount%6==4){
			$(this).parent().prev().css("background",'url("'+ctx+'/resources/theme/images/v1_file_icon.png") no-repeat scroll -7px -70px transparent');
		}else if(closeFCount%6==5){
			$(this).parent().prev().css("background",'url("'+ctx+'/resources/theme/images/v1_file_icon.png") no-repeat scroll -7px -93px transparent');
		}else if(closeFCount%6==0){
			$(this).parent().prev().css("background",'url("'+ctx+'/resources/theme/images/v1_file_icon.png") no-repeat scroll -7px -112px transparent');
		}
		closeFCount++;
	});
	$(".node_folder_ico_open").each(function(){
		if(openFCount%6==0){
			$(this).parent().prev().css("background",'url("'+ctx+'/resources/theme/images/v1_file_icon.png") no-repeat scroll -41px -4px transparent');
		}else if(openFCount%6==5){
			$(this).parent().prev().css("background",'url("'+ctx+'/resources/theme/images/v1_file_icon.png") no-repeat scroll -41px -26px transparent');
		}else if(openFCount%6==4){
			$(this).parent().prev().css("background",'url("'+ctx+'/resources/theme/images/v1_file_icon.png") no-repeat scroll -41px -47px transparent');
		}else if(openFCount%6==3){
			$(this).parent().prev().css("background",'url("'+ctx+'/resources/theme/images/v1_file_icon.png") no-repeat scroll -41px -70px transparent');
		}else if(openFCount%6==2){
			$(this).parent().prev().css("background",'url("'+ctx+'/resources/theme/images/v1_file_icon.png") no-repeat scroll -41px -93px transparent');
		}else if(openFCount%6==1){
			$(this).parent().prev().css("background",'url("'+ctx+'/resources/theme/images/v1_file_icon.png") no-repeat scroll -41px -112px transparent');
		}
		openFCount++;
	});
	$(".node_file_ico_docu").each(function(){
		if(fileCount%6==1){
			$(this).parent().prev().css("background",'url("'+ctx+'/resources/theme/images/v1_file_icon.png") no-repeat scroll -71px -4px transparent');
		}else if(fileCount%6==2){
			$(this).parent().prev().css("background",'url("'+ctx+'/resources/theme/images/v1_file_icon.png") no-repeat scroll -71px -26px transparent');
		}else if(fileCount%6==3){
			$(this).parent().prev().css("background",'url("'+ctx+'/resources/theme/images/v1_file_icon.png") no-repeat scroll -71px -47px transparent');
		}else if(fileCount%6==4){
			$(this).parent().prev().css("background",'url("'+ctx+'/resources/theme/images/v1_file_icon.png") no-repeat scroll -71px -70px transparent');
		}else if(fileCount%6==5){
			$(this).parent().prev().css("background",'url("'+ctx+'/resources/theme/images/v1_file_icon.png") no-repeat scroll -71px -93px transparent');
		}else if(fileCount%6==0){
			$(this).parent().prev().css("background",'url("'+ctx+'/resources/theme/images/v1_file_icon.png") no-repeat scroll -71px -112px transparent');
		}
		fileCount++;
	});
}

var dataLeftFolderSetting = {
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
			beforeClick : beforeLeftClick,
			onAsyncSuccess : choseIcon,
			onCollapse : choseIcon,
			onExpand : choseIcon
		}
	};

var showModel = 1;// 1、正常列表模式；2、视图模式
var refreshLeft = true;
// 树结点点击处理
function beforeLeftClick(treeId, treeNode) {
	// 最新选择的树结点
	latestSelectedNode = treeNode;
	if (treeNode.id != null && treeNode.id != -1) {
		
		refreshLeft = false;
		if(showModel == 2){
			var viewUuid = $("#viewUuid").val();
			var folderUuid = treeNode.id;
			if(StringUtils.isNotBlank(viewUuid)){
				pageLock("show");
				$.ajax({
					type : "POST",
					url : contextPath+"/basicdata/dyview/view_show",
					data : "viewUuid="+viewUuid+ "&currentPage=1&folderUuid=" + folderUuid,
					dataType : "text",
					success : function callback(result) {
						$("#fileListIframe").html(result);
						addBrowseByFolderButtonInDyView();
						browseByFolder();
						pageLock("hide");
					}
				});
			}else{
				// 默认视图ID FILE_DEFAULT_VIEW
				pageLock("show");
				$.ajax({
					type : "POST",
					url : contextPath+"/basicdata/dyview/view/FILE_DEFAULT_VIEW",
					data : "currentPage=1&folderUuid=" + folderUuid,
					dataType : "text",
					success : function callback(result) {
						$("#fileListIframe").html(result);
						addBrowseByFolderButtonInDyView();
						browseByFolder();
						pageLock("hide");
					}
				});
			}
		}else{
			pageLock("show");
			$.ajax({
				type : "POST",
				url : contextPath+"/fileManager/folder/indexList",
				data : "id="+treeNode.id,
				dataType : "text",
				success : function callback(result) {
					$("#fileListIframe").html(result);
					pageLock("hide");
				}
			});
		}
	}
	return true;
}





//JQyery zTree加载数据字典树结点
function loadLeftFolderTree1() {
	$.fn.zTree.init($("#list_folder_tree"), dataLeftFolderSetting);
	
//	 var document = this;
//	var $ztree = $("#list_folder_tree", document);
//	if(refreshLeft === true){
//		$.fn.zTree.init($ztree, dataLeftFolderSetting);
//	}
}


$("#show_view").live("click",function(e){
	showModel = 2;
	var viewUuid = $("#viewUuid").val();
	var folderUuid = $("#folderUuid").val();
	if(StringUtils.isNotBlank(viewUuid)){
		pageLock("show");
		$.ajax({
			type : "POST",
			url : contextPath+"/basicdata/dyview/view_show",
			data : "viewUuid="+viewUuid+"&currentPage=1&folderUuid=" + folderUuid,
			dataType : "text",
			success : function callback(result) {
				$("#fileListIframe").html(result);
				addBrowseByFolderButtonInDyView();
				browseByFolder();
				pageLock("hide");
			}
		});
	}else{
		// 默认视图ID FILE_DEFAULT_VIEW
		pageLock("show");
		$.ajax({
			type : "POST",
			url : contextPath+"/basicdata/dyview/view/FILE_DEFAULT_VIEW",
			data : "currentPage=1&folderUuid=" + folderUuid,
			dataType : "text",
			success : function callback(result) {
				$("#fileListIframe").html(result);
				addBrowseByFolderButtonInDyView();
				browseByFolder();
				pageLock("hide");
			}
		});
	}
});

//按夹浏览
function browseByFolder(){
	$("#btn_show_list").click(function(e){
		showModel = 1;
		var folderUuid = $("#view_param_folderUuid").val();
		pageLock("show");
		$.ajax({
			type : "POST",
			url : contextPath+"/fileManager/folder/indexList",
			data : "id="+folderUuid,
			dataType : "text",
			success : function callback(result) {
				$("#fileListIframe").html(result);
				pageLock("hide");
			}
		});
	}); 
}
//在视图中加入按夹浏览按钮
function addBrowseByFolderButtonInDyView(){
	var show_list_btn = "<button id='btn_show_list' type='button' place='place_top'>按夹浏览</button>";
	if($(".view_tool2 .customButton").length != 0){
		$(".view_tool2 .customButton").prepend(show_list_btn);
	}else{
		show_list_btn = '<div class="customButton customButton_top">' + show_list_btn + '</div>';
		if($(".view_tool2").length != 0){
			$(".view_tool2").prepend(show_list_btn);
		}else{
			show_list_btn = '<div class="view_tool2">' + show_list_btn + '</div>';
			$(".viewContent").before(show_list_btn);
		}
	}
}

function refreshZtree(){
	var treeObj = $.fn.zTree.getZTreeObj("list_folder_tree");
	treeObj.reAsyncChildNodes(null, "refresh");
}

//子页面需要调用的ajax刷新父页面的窗口（新建文档）
function refleshParentWin(folderUuid) {
	$.ajax({
		type : "POST",
		url : contextPath+"/fileManager/folder/indexList",
		data : "id="+folderUuid,
		dataType : "text",
		async:false,
		success : function callback(result) {
			$("#fileListIframe").html(result);
		}
	});
}

//子页面需要调用的ajax刷新父页面的窗口（新建或保存夹）
function refleshParentWinByFolder(parentUuid) {
	$.ajax({
		type : "POST",
		url : contextPath+"/fileManager/folder/indexList",
		data : "id="+parentUuid,
		dataType : "text",
		async:false,
		success : function callback(result) {
			$("#fileListIframe").html(result);
		}
	});
}

