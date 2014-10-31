$(function() {
	if($("#abc").length>0){
		$("#abc").dyView({
			data:{viewUuid:$("#viewUuid").val(),
				columnDefinitions:$("#columnDefinitions").val(),
				page:$("#page").val(),
				pageCurrentPage:$("#pageCurrentPage").val(),
				pageTotalCount:$("#pageTotalCount").val(),
				pageSize:$("#pageSize").val(),
				pageDefinition:$("#pageDefinition").val(),
				conditionTypes:$("#conditionTypes").val(),
				fieldSelects:$("#fieldSelects").val(),
//				clickType:"view_show"
				}	
		});
	}
	
	
	/***点击行事件***/
	$(".dataTr").die();
	$(".dataTr").live("click",function(e){
		var temp = $(e.target).attr("type");
		var t = $(e.target);
		var class_ = $(e.target).attr("class");
		var clickjs = $(this).attr("clickjs");
		var array = new Object();
		array["moduleId"]=$(this).parents(".active").attr("moduleid");
		array["wid"]=$(this).parents(".dnrw").attr("id");
		if(temp=="checkbox"||temp=="button"){
			return;
		}else{
			if(clickjs != null && clickjs != "") {
				eval(clickjs);
			}
			if(class_&&class_.indexOf("star_")>-1){
				var s = readUrlParam($(this));
				var uuid = s["uuid"];
				setStar(t,uuid);
			}else if(class_&&class_.indexOf("view_tags_item")>-1 || class_&&class_.indexOf("closeTagSideDiv")>-1){
			}else{
				if($(this).attr("src")){
					var moduleid = $(this).parents(".active").attr("moduleid");
					var wid = $(this).parents(".dnrw").attr("id");
					if($(this).attr("src").indexOf("?")>-1){
						if(moduleid==undefined){
							moduleid = "";
						}
						window.open($(this).attr("src")+"&moduleid="+moduleid+"&wid="+wid);
					}else{
						window.open($(this).attr("src")+"?moduleid="+$(this).parents(".active").attr("moduleid")+"&wid="+$(this).parents(".dnrw").attr("id"));
					}
				}
			}
		} 
	});
	//获取url参数
	function readUrlParam(obj){
		var s = new Object(); 
		var index = obj.attr("src").indexOf("?");
		var search = obj.attr("src").substr(index);
		var searchArray = search.replace("?", "").split("&");
		for(var i=0;i<searchArray.length;i++){
			var paraArray = searchArray[i].split("=");
			var key = paraArray[0];
			var value = paraArray[1];
			s[key] = value;
		}
		return s;
	}
	//设置星标
	function setStar(obj,uuid){
		var objs=obj.attr('class');
		if(objs.indexOf('star_ star_1')>-1||objs.indexOf('star_1')>-1){
			updateStar(obj,0,uuid);
		}else{
			updateStar(obj,1,uuid);
		}
	}
	//下载数据交换批量上传的文件
	$(".exchangeUpload").click(function (){
//		alert(contextPath+'/exchangedata/client/downloadFile?id='+$(this).attr("batchid"));
		location.href=contextPath+'/exchangedata/client/downloadFile?id='+$(this).attr("batchid");
	});
	function updateStar(obj,stype,uuid){
		$.ajax({
			type : "POST",
			url : ctx+"/mail/mailbox_setstar.action",
			data : "uuid="+uuid+"&stype="+stype,
			dataType : "text",
			success : function callback(result) {
				if(stype==0){
					obj.attr("class","star_");
				}else{
					obj.attr("class","star_ star_1");
				}
				var moduleid = obj.parents(".active").attr("moduleid");
				//我的文件夹、其他邮件、标签刷新当前视图
				if(moduleid == "folder" || moduleid == "other" || moduleid == "mlabel"){
					refreshMailLive(obj);
				} else {
					refreshWindow(obj);
				}
			}
		});
	}
	/***按钮显示与隐藏***/
	$(".odd").die();
	$(".odd").live("mouseenter",function(){
		if($(this).find(".customButton1").length!=0){
			$(this).find(".customButton1").show();
		}
		if($(this).next().find(".customButton2").length!=0){
			$(this).next().find(".customButton2").show();
		}
	});
	$(".odd").live("mouseleave",function(){
		if($(this).find(".customButton1").length!=0){
			$(this).find(".customButton1").hide();
		}
		if($(this).next().find(".customButton2").length!=0){
			$(this).next().find(".customButton2").hide();
		}
	});
	$(".even").die();
	$(".even").live("mouseenter",function(){
		if($(this).find(".customButton2").length!=0){
			$(this).find(".customButton2").show();
		}
		if($(this).prev().find(".customButton1").length!=0){
			$(this).prev().find(".customButton1").hide();
		}
	});
	$(".even").live("mouseleave",function(){
		if($(this).find(".customButton2").length!=0){
			$(this).find(".customButton2").hide();
		}
		if($(this).prev().find(".customButton1").length!=0){
			$(this).prev().find(".customButton1").hide();
		}
	});
	
	/***复选框全选***/
	$(".checkall").die();
	$(".checkall").live("click",function(){
		if($(this).attr("checked")){
			$(this).parents("table").find("input:checkbox").attr("checked",true);
		}else{
			$(this).parents("table").find("input:checkbox").attr("checked",false);
		}
	});
	/****显示视图搜索条*******/
	$(".dnrw ul").live("mouseover",function(){
		if($(this).find(".indexViewSearch").length>0){
			$(this).find(".indexViewSearch").show();
		}
		
	});
	$(".dnrw ul").live("mouseout",function(){
		if($(this).find(".indexViewSearch").length>0){
			if($(this).find(".indexViewKeyWord").attr("class").indexOf("isfocus")>-1){
			}else{
				$(this).find(".indexViewSearch").hide();
			}
		}
	});
	$(".indexViewKeyWord").focus(function(){
		$(this).attr("class","indexViewKeyWord isfocus");
	});
	$(".indexViewKeyWord").blur(function(){
		$(this).attr("class","indexViewKeyWord");
		$(this).parent(".indexViewSearch").hide();
	});
	
	/********点击搜索事件**************/
	$(".indexSearchButton").live("click",function(){
		    var dnrw = $(this).parents(".dnrw");
			var moduleid = dnrw.attr("moduleid");
			var viewUuid = "";
			var keyWord = $(this).parent().find(".indexViewKeyWord").val();
//			if(keyWord==""){
////				oAlert("请输入关键字！");
//			}else{
				$.ajax({
					type:"post",
					async:false,
					data : {"uuid":moduleid},
					url:ctx+"/cms/cmspage/getViewIdByModuleId",
					success:function(result){
						viewUuid = result;
					}
				});
				var dyViewQueryInfo = new Object();
				var pageSize = $("#update_"+viewUuid).find("#pageSize").val();
				var currentPage = 1;
				dyViewQueryInfo.viewUuid = viewUuid;
				var pageInfo = new Object();
				pageInfo.currentPage = currentPage;
				pageInfo.pageSize = pageSize;
				var condSelect = new Object();
				condSelect.beginTime = "";
				condSelect.endTime = "";
				condSelect.searchField = "";
				dyViewQueryInfo.pageInfo = pageInfo;
				var keyWords = new Array();
				var keyWordMap = new Object();
				keyWordMap.all=keyWord;
				keyWords.push(keyWordMap);
				condSelect.keyWords = keyWords;
				dyViewQueryInfo.condSelect = condSelect;
				var url = ctx+'/basicdata/view/view_show_param.action';
				$.ajax({
					url:url,
					type:"POST",
					data:JSON.stringify(dyViewQueryInfo),
					dataType:'text',
					contentType:'application/json',
					success:function(result){
						$("#update_"+viewUuid).html(result);
						formDate();
					}
				});
//			}
	});
	
	/********回车搜索事件**************/
	$(".indexViewKeyWord").live("keyup",function(event){
		var code = event.keyCode;
	    if (code == 13) {
	    	var dnrw = $(this).parents(".dnrw");
			var moduleid = dnrw.attr("moduleid");
			var viewUuid = "";
			var keyWord = $(this).val();
			$.ajax({
				type:"post",
				async:false,
				data : {"uuid":moduleid},
				url:ctx+"/cms/cmspage/getViewIdByModuleId",
				success:function(result){
					viewUuid = result;
				}
			});
			var pageSize = $("#update_"+viewUuid).find("#pageSize").val();
			var currentPage = 1;
			var dyViewQueryInfo = new Object();
			dyViewQueryInfo.viewUuid = viewUuid;
			var pageInfo = new Object();
			pageInfo.currentPage = currentPage;
			pageInfo.pageSize = pageSize;
			var condSelect = new Object();
			condSelect.beginTime = "";
			condSelect.endTime = "";
			condSelect.searchField = "";
			dyViewQueryInfo.pageInfo = pageInfo;
			var keyWords = new Array();
			var keyWordMap = new Object();
			keyWordMap.all=keyWord;
			keyWords.push(keyWordMap);
			condSelect.keyWords = keyWords;
			dyViewQueryInfo.condSelect = condSelect;
			var url = ctx+'/basicdata/view/view_show_param.action';
			$.ajax({
				url:url,
				type:"POST",
				data:JSON.stringify(dyViewQueryInfo),
				dataType:'text',
				contentType:'application/json',
				success:function(result){
					$("#update_"+viewUuid).html(result);
					formDate();
				}
			});
	    }else{
	    	var keyWord = $(this).val();
			if(keyWord==""){
				if($(this).next().attr("class")=="clearText"){
					$(".clearText").remove();
				}
			}else{
				if($(this).next().attr("class")!="clearText"){
					$(this).after("<div class='clearText'></div>");
				}
			}
	    }
	});
	/**************删除关键字******************/
	$(".indexViewSearch .clearText").die().live("click",(function(){
		var dnrw = $(this).parents(".dnrw");
		var moduleid = dnrw.attr("moduleid");
		var viewUuid = "";
		var keyWord = "";
		$.ajax({
			type:"post",
			async:false,
			data : {"uuid":moduleid},
			url:ctx+"/cms/cmspage/getViewIdByModuleId",
			success:function(result){
				viewUuid = result;
			}
		});
		
		var dyViewQueryInfo = new Object();
		dyViewQueryInfo.viewUuid = viewUuid;
		var pageInfo = new Object();
		pageInfo.currentPage = 1;
		pageInfo.pageSize = 6;
		var condSelect = new Object();
		dyViewQueryInfo.pageInfo = pageInfo;
		var keyWords = new Array();
		var keyWordMap = new Object();
		keyWordMap.all=keyWord;
		keyWords.push(keyWordMap);
		condSelect.keyWords = keyWords;
		dyViewQueryInfo.condSelect = condSelect;
		
		var url = ctx+'/basicdata/view/view_show_param.action';
		$.ajax({
			url:url,
			type:"POST",
			data:JSON.stringify(dyViewQueryInfo),
			dataType:'text',
			contentType:'application/json',
			success:function(result){
				$("#update_"+viewUuid).html(result);
				formDate();
			}
		});
		
		$(".indexViewSearch .clearText").remove();
		$(".indexViewKeyWord").val("");
	}));
	/***************按钮下拉效果*****************************/
	$(".customButton_group_item").live("click",function(){
		$(this).find(".customButton_group_buttons").show();
	});
	$(".customButton_group_button").live("click",function(e){
		$(this).parents(".customButton_group_buttons").hide();
		e.stopPropagation();
	});
	/**********邮件导航树动态选项的的绑定事件***********/
	$(".liveData").live("click",function(){
		var url = $(this).attr("url");
		var title = $(this).attr("name_");
		var id_ = this.id;
		pageLock("show");
		$.ajax({
			type:"post",
			async:false,
			url:ctx+url,
			success:function(result){
//				result = result.substring(result.indexOf("<body>")+6,result.indexOf("</body>"));
				$("#"+window.dnrwId+" ul li a" ).text(title);
				$("#"+window.dnrwId+" div div").attr("moduleid",id_);
				$("#"+window.dnrwId+" div div").html(result);
				//格式化时间
				formDate();
				pageLock("hide");
			}
		});
	});
	
	/**********事项管理导航树动态选项的的绑定事件***********/
	$(".liveData_matters").live("click",function(){
		var $this = $(this);
		var url = $(this).attr("url");
		var title = $(this).attr("name_");
//		var id_ = this.id;
		pageLock("show");
		$.ajax({
			type:"post",
			async:false,
			url:ctx+url,
			success:function(result){
				$("#"+window.dnrwId+" ul li a" ).text(title);
//				$("#"+window.dnrwId+" div div").attr("moduleid",id_);
				$("#"+window.dnrwId+" div div").html(result);
				//事项管理新增按钮事件处理
				if($this.attr("folderid")!=""&&$("button[value='B014001001']").length>0){
					var onclickStr = $("button[value='B014001001']").attr("onclick");
					onclickStr = onclickStr.replace("folderid",$this.attr("folderid"));
					$("button[value='B014001001']").attr("onclick",onclickStr);
				}
				//格式化时间
				formDate();
				pageLock("hide");
			}
		});
	});
}); 

function isTopBtn(obj){
	var place = $(obj).attr("place");
	if(place == "place_top"){
		return true;
	}else{
		return false;
	}
}
//从url中获取活动窗口(需填充数据窗口)Id
function readSearch() {
	var search = window.location.search;
	var s = new Object();
	var searchArray = search.replace("?", "").split("&");
	for ( var i = 0; i < searchArray.length; i++) {
		var paraArray = searchArray[i].split("=");
		var key = paraArray[0];
		var value = paraArray[1];
		s[key] = value;
	}
	return s;
}
var searchPara = readSearch();
var mids = "";
$.each(searchPara,function(n,value) {   
    if(n == "uuid" || n == "treeName" || n =="viewName") {
 	   
    }else {
    	mids += ","+n;
    }
  });  
mids = mids.substring(1);
if(mids!=undefined){
	window.dnrwId = mids.split(",")[0];
}