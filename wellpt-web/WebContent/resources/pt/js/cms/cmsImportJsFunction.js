function a() {
	JDS.call({
		async:false,
		service:"XZSPService.isGoInOldSystem",
		data:[],
		success:function(result) {
			if(result.data.userName != null ) {
				var html = "<div style='display:none;'>" 
					+"<form name='loginForm' action='http://172.22.37.198:8000/apas/login.do' method='post' >"
					+ "<table style='width: 90%;'><tr><td>用户名:</td>"
					+ "<td><input type='text' id='loginName' name='loginName' value=\""+result.data.userName+"\"/>"
					+ "</td></tr>"
					+ "<tr><td style='letter-spacing: 6px;'>密码:</td>" +
							"<td><input type='password' id='password' name='password' value='"+result.data.passWord+"'/>"
					+ "</td></tr>"
					+"<tr style='color:red;'><td>备注:</td><td>用户名和密码请输入旧建管系统的用户名和密码。</td></tr>"
					+ "</table>" 
					+"</form>"
					+	"</div>";
				$("#user_uuid").after(html);
				var userName = $("#loginName").val();
				var cookie1 = "userName=" + userName;
				var now = new Date();
				//在当前日期上加上Cookie的存活时间与路径
			    now.setDate(now.getDate() + parseInt("365"));
			    cookie1 = cookie1+";expires=" + now.toGMTString()+ ";path=/";
			    document.cookie = cookie1;
			    document.loginForm.submit();
			}else {
				var json = new Object();
				var data = "<div style='padding:20px;'>" 
					+"<form name='loginForm' action='http://172.22.37.198:8000/apas/login.do' method='post' >"
					+ "<table style='width: 90%;'><tr><td>用户名:</td>"
					+ "<td><input type='text' id='loginName' name='loginName'/>"
					+ "</td></tr>"
					+ "<tr><td style='letter-spacing: 6px;'>密码:</td>" +
							"<td><input type='password' id='password' name='password'/>"
					+ "</td></tr>"
					+"<tr style='color:red;'><td>备注:</td><td>用户名和密码请输入旧建管系统的用户名和密码。</td></tr>"
					+ "</table>" 
					+"</form>"
					+	"</div>";
					json.content = data;	
					json.title = "单点登录旧建管系统";
					json.height = 400;
					json.width = 600;
					json.buttons = {
							"确定" : function() {
								var userName = $("#loginName").val();
								var cookie1 = "userName=" + userName;
								var now = new Date();
								//在当前日期上加上Cookie的存活时间与路径
							    now.setDate(now.getDate() + parseInt("365"));
							    cookie1 = cookie1+";expires=" + now.toGMTString()+ ";path=/";
							    document.cookie = cookie1;
							    document.loginForm.submit();
							    JDS.call({
							    	async:false,
									service:"XZSPService.saveAccount",
									data:[userName,passWord],
									success:function(result) {
										
									}
							    });
							},
						};
					showDialog(json);
			}
		}
	});
}

function xzspreporting(src){
	var src = encodeURI(src);
	$("#reportFrame").attr("src",src);
}



function loadNav(cateCode) { 
	if(!$("#dialogModule").is(":hidden") && $("#dialogModule").size() > 0){//已展示时，返回，不再处理
		return;
	}
	
    $(document).mousedown(function(event){//点击其他区域时对话框隐藏
		var temp = $(event.target).attr("class");
		if(temp == "ui-dialog" ||  $(event.target).parents(".ui-dialog").size() > 0){
			return;
		}else if(temp == undefined){
			return;
		}else {
			$("#dialogModule").dialog("close");
		}
	});
	
	if(typeof cateCode == "undefined"){
		cateCode = "";
	}
	$.ajax({
		type:"post",
		async:false,
		url:ctx+"/cms/cmspage/indexNavForldx?treeName=ldx_index_nav&catCode=" +cateCode,
		success:function(result) {
			var json = 
			{
				title:"导航",
				autoOpen: true,  /*****初始化之后，是否立即显示对话框******/
				modal: false,     /*****是否模式对话框******/
				closeOnEscape: true, /***当按 Esc之后，是否应该关闭对话框****/
				draggable: true, /*****是否允许拖动******/  
				resizable: true, /*****是否可以调整对话框的大小******/  
				stack : false,   /*****对话框是否叠在其他对话框之上******/
				height: 520, /*****弹出框高度******/
				width: 1100,   /*****弹出框宽度******/
				content: result,/*****内容******/
				//open：事件,
			};
			showDialog(json);
			
			
			if(cateCode != ""){
				achorClass(cateCode);
			}
		}
	})
	
	$(".cancel").remove();
	window.scroll(0,0);
}
 
function loadChirldNav(code){
	$.ajax({
		type:"post",
		async:true,
		contentType:'application/json',
		url:getContextPath() + "/cms/cmspage/getChildCategoryHtml?code=" + code,
		success:function(result) {
				 //$(".fieldset[code='${l.cc.code}']").append("<div class='gl_tit4'>abc</div>");
				 for (var index=0;index<result.length;index++) {
					
					 var ccList = result[index].cc;
					
						if(ccList.cateName == '分类') {//二级分类
							
							 //debugger;
							 $(".gl_con_r[code='" + code +  "'] > .clear").first().before("<div class='gl_tit4' code='" + ccList.code + "'>" + ccList.title + "</div>");
							 var classItem = "<div class='gl_con2'><div class='gl_con2_l'> ";
							 classItem += "<ul code='" + ccList.code + "'></ul> </div> <div class='clear'></div> </div> ";
							 $(".gl_tit4[code='" + ccList.code + "']").after( classItem);
							 loadChirldNav( ccList.code);
						}else{
							// $(".gl_con_r[code='${l.cc.code}']").append("<div class='gl_tit4'>abc</div>");
							if($("ul[code='" + code + "']").size() > 0){//三级节点
								//$("ul[code='" + code + "']").append("");
								$("<li class='flowItem'><a>" +  ccList.title + "</a></li>").appendTo($("ul[code='" + code + "']")).css("width", "210px");
							}else{//二级节点
								 
								 var classItem = "<div class='gl_con2'><div class='gl_con2_l'> ";
								 classItem += "<ul code='" + code + "'></ul> </div> <div class='clear'></div> </div> ";
								 $("span[code='" + code +  "']").first().after( classItem);
								   
									
								 $("<li class='flowItem'><a>" +  ccList.title + "</a></li>").appendTo($("ul[code='" + code + "']")).css("width", "210px");
								  
							}
							
							(function(ccParam){
								 var moduleid =ccParam["moduleId"];
									var newpage = ccParam["newPage"];
									var opentype = ccParam["openType"];
									var divid = ccParam["divId"];
									var fullwindow = ccParam["fullwindow"];
									var inputurl = ccParam["inputUrl"];
								 
									var pageurl = ccParam["pageUrl"]; 
									if(typeof pageurl != "undefined" && pageurl !=null && pageurl.indexOf("://") != -1){
										
									}else{
										pageurl = ctx  + pageurl;
									}
									if(typeof inputurl != "undefined" && inputurl !=null && inputurl.indexOf("://") != -1){
										
									}else{
										inputurl = ctx + inputurl;
									}
									
									 //debugger;
									if(typeof lili != "undefined"){
										//console.log(lili == $("ul[code='" + code + "']").find("li").last());
									}
									lili = $("ul[code='" + code + "']").find("li").last();
									
									$("ul[code='" + code + "']").find("li").last().click(function(){
									
									
									 if(newpage=="dialog"){
											var json = new Object(); 
									        json.content = $(".send_message").html();
									        json.title = "发送消息";
									        json.height= 350;
									        json.width= 830;
									        var buttons = new Object(); 
									        buttons["发送"] = sendMessage;
									        json.buttons = buttons;
									        $("#dialogModule").dialog( "close" );
									        showDialog(json);
									       
										}else{
											 var code = ccParam["code"];
											if(opentype=="moduleId"){
												$("#dialogModule").dialog( "close" );
												$.ajax({
													type:"post",
													async:false,
													data : {"uuid":moduleid},
													url: ctx + "/cms/cmspage/viewcontent",
													success:function(result){
														//设置模块名称
														$("#"+divid+" ul li a" ).text(name);
														$("#"+divid+" div div").attr("moduleid",moduleid);
														if(fullwindow=='yes'){
															$("#"+divid).html(result);
															jsmod(".dnrw .tab-content");
														}else{
															$("#"+divid+" .viewContent").parent().html(result);
															jsmod(".dnrw .tab-content");
														}
														 /**格式化时间***/ 
														formDate();
													}
												});
												 
											}else if(opentype=="pageUrl"){
												if(newpage=="this"){
													location.href= pageurl;
												}else{
													window.open( pageurl);
												}
												 $("#dialogModule").dialog( "close" );
											}else if(opentype=="inputUrl"){
												if(newpage=="this"){
													location.href= ctx+inputurl;
												}else{
													window.open( ctx+inputurl);
												}
												 $("#dialogModule").dialog( "close" );
											}else if(opentype=="jsContent"){
												
												eval(jsContent); 
												 $("#dialogModule").dialog( "close" );
											} 
										}
								 }) ;  
							})(ccList);
						 
							
							
						}
				 }
			 // document.write();
		}
  	});
}

function achorClass(code, _this){
	removeSearchResultFlag(); //删除搜索结果中的选中效果
	 if(_this){
		 $(".openchild").removeClass("activite");
		 $(_this).find("a").addClass("activite");
	 }
	$(".navfieldset").each(function(){
		if($(this).attr("code") == code){
			$(this).show();
			$(this).next().show();
			$(this).next().next().show();
			showNavItem($(this).attr("code"));
		}else if(typeof code == "undefined" || code == "" || code == null){ 
			$(this).show();
			$(this).next().show();
			$(this).next().next().show();
			showNavItem($(this).attr("code"));
		}else{
			$(this).hide();
			$(this).next().hide();
			$(this).next().next().hide();
		}
	});
}
function removeSearchResultFlag(){
	$(".navfieldset").find("legend").removeClass("searchResult");
	$(".flowItem").removeClass("searchResult");
	$(".gl_tit4").removeClass("searchResult"); 
}

function showNavItem(code){
	var $fieldsetelem = $(".navfieldset[code='" + code + "']");
	$fieldsetelem.show();
	$fieldsetelem.next(".clear").hide();
	$(".flowItem", $fieldsetelem[0]).show();
	$(".gl_tit4", $fieldsetelem[0]).show();
}

 
 