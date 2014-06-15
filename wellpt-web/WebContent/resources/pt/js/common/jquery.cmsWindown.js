//$(function(){
//	window.onbeforeunload = onbeforeunload_handler;  
//	function onbeforeunload_handler(){  
//		var para = window.location.search;
//		var moduleid = "";
//		var wid = "";
//		var paraArray = para.replace("?","").split("&");
//		for(var i=0;i<paraArray.length;i++){
//			if(paraArray[i].indexOf("moduleid")>-1){
//				moduleid = paraArray[i].split("=")[1];
//			}else if(paraArray[i].indexOf("wid")>-1){
//				wid = paraArray[i].split("=")[1];
//			}
//		}
//		if(moduleid != null && $.trim(moduleid) != ""){
//			$.ajax({
//				type:"post",
//				async:false,
//				data : {"uuid":moduleid},
//				url:ctx+"/cms/cmspage/viewcontent",
//				success:function(result){
//					$(window.opener.document.getElementById(wid)).find("div .active").html(result);
//				}
//			});
//		}
//	}
//});
function returnWindow(){
	
	var para = window.location.search;
	var moduleid = "";
	var wid = "";
	var para = readSearch();
	$.each(para,function(n,value) {   
           if(n == "uuid" || n == "treeName" || n =="viewName") {
        	   
           }else {
        	   para.mid = n;
        	   para.moduleid = value;
           }
         }); 
//	var paraArray = para.replace("?","").split("&");
//	for(var i=0;i<paraArray.length;i++){
//		if(paraArray[i].indexOf("moduleid")>-1){
//			moduleid = paraArray[i].split("=")[1];
//		}else if(paraArray[i].indexOf("wid")>-1){
//			wid = paraArray[i].split("=")[1];
//		}
//	}
	if(moduleid != null && $.trim(moduleid) != ""){
		$.ajax({
			type:"post",
			async:false,
			data : {"uuid":moduleid},
			url:ctx+"/cms/cmspage/viewcontent",
			success:function(result){
//				var temp = $(window.opener.document.getElementById(wid)).find(".active");
				var temp = $(window.opener.document.getElementById(wid)).find(".viewContent").parent();
				temp.each(function(){
					var class_ = $(this).attr("class");
					if(class_=="active"){
					}else{
						$(this).html(result);
						window.opener.jsmod(".dnrw .tab-content");
					}
				});
			}
		});
	}else{
		window.opener.location.href=window.opener.location.href;
	}
}

function refreshWindow(element){
//	var moduleid = element.parents(".active").attr("moduleid");
	var moduleid = element.parents(".viewContent").parent().attr("moduleid");
	var flag = 0;
	//内容不是直接放在viewContent父级的情况
	if(moduleid==undefined){
		moduleid = element.parents(".active").attr("moduleid");
		flag = 1;
	}
	pageLock("show");
	$.ajax({
		type:"post",
		async:false,
		data : {"uuid":moduleid},
		url:ctx+"/cms/cmspage/viewcontent",
		success:function(result){
//			element.parents(".active").html(result);
			if(flag = 1){
				element.parents(".active").html(result);
			}else{
				element.parents(".viewContent").parent().html(result);
			}
			pageLock("hide");
			jsmod(".dnrw .tab-content");
		}
	});
}
