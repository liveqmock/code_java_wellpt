$(function() {
	$("#abc").dyView({
		data:{viewUuid:$("#viewUuid").val(),
			columnDefinitions:$("#columnDefinitions").val(),
			page:$("#page").val(),
			pageCurrentPage:$("#pageCurrentPage").val(),
			pageTotalCount:$("#pageTotalCount").val(),
			pageSize:$("#pageSize").val(),
			pageDefinition:$("#pageDefinition").val(),
			conditionTypes:$("#conditionTypes").val(),
			clickType:"view_show"
			}	
	}
	);
	/***点击行事件***/
	$(".index_dff .dataTr").unbind("click");
	$(".index_dff .dataTr").on("click",function(e){
		var temp = $(e.target).attr("type");
		var t = $(e.target);
		var class_ = $(e.target).attr("class");
		var array = new Object();
		array["moduleId"]=$(this).parents(".active").attr("moduleid");
		array["wid"]=$(this).parents(".dnrw").attr("id");
		if(temp!="checkbox"){
			if(class_&&class_.indexOf("star_")>-1){
				var s = readUrlParam($(this));
				var uuid = s["uuid"];
				setStar(t,uuid);
			}else{
				if($(this).attr("src")){
					if($(this).attr("src").indexOf("?")>-1){
						window.open($(this).attr("src")+"&moduleid="+$(this).parents(".active").attr("moduleid")+"&wid="+$(this).parents(".dnrw").attr("id"));
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
	function updateStar(obj,stype,uuid){
		$.ajax({
			type : "POST",
			url : ctx+"/mail/mailbox_setstar.action",
			data : "uuid="+uuid+"&stype="+stype,
			dataType : "text",
			success : function callback(result) {
				if(stype==0){
					obj.attr("class","star_");
					refreshWindow(obj);
				}else{
					obj.attr("class","star_ star_1");
					refreshWindow(obj);
				}
			}
		});
	}
	/***按钮显示与隐藏***/
	$(".odd").mouseover(function(){
		if($(this).find(".customButton").length!=0){
			$(this).find(".customButton").show();
		}else{
			$(this).next().find(".customButton").show();
		}
		
	});
	$(".odd").mouseout(function(){
		if($(this).find(".customButton").length!=0){
			$(this).find(".customButton").hide();
		}else{
			$(this).next().find(".customButton").hide();
		}
	});
	$(".even").mouseout(function(){
		if($(this).find(".customButton").length!=0){
			$(this).find(".customButton").hide();
		}else{
			$(this).prev().find(".customButton").hide();
		}
	});
	$(".even").mouseover(function(){
		if($(this).find(".customButton").length!=0){
			$(this).find(".customButton").show();
		}else{
			$(this).prev().find(".customButton").hide();
		}
	});
	/***复选框全选***/
	$(".checkall").click(function(){
		if($(this).attr("checked")){
			$(this).parents("table").find("input:checkbox").attr("checked",true);
		}else{
			$(this).parents("table").find("input:checkbox").attr("checked",false);
		}
	});
}); 