<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/pt/common/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<html>
<head>
<title></title>
<link rel="stylesheet" type="text/css" href="${ctx}/resources/bootstrap/css/bootstrap.css" />
<script src="${ctx}/resources/jquery/jquery.js"></script>
</head>
<body>
<script src="${ctx}/resources/app/js/xzsp/xzsp_cmsbtn.js"></script>
<link href="${ctx}/resources/theme/css/urlPage.css" rel="stylesheet">
<style>
	.mail_write {
		background: none repeat scroll 0 0 black;
	    float: right;
	    font-size: 12px;
	   
	    opacity: 0.5;
	    width: 54px;
	    z-index: 100;
	}
</style>

<div class="index_dff">
	 <c:if test="${showButton == 1}">
		<div  style="float: none;">
			<button id="buttonOne" style="height: 34px;border: 0 none;margin-left: 157px;width: 141px;background: url('${ctx}/resources/theme/images/internet1.png');"><span id="internetSpan">审批事项申报</span></button>
			<button id="buttonTwo" onclick="File.addFileWithTitleAndMethodNameAndScriptUrl.call(this,'f3dd070a-999c-4859-b9f7-3c65cf3557c2','新增项目','','/resources/app/js/xzsp/xzsp_file.js');" style="height: 34px;border: 0 none;width: 141px;background: url('${ctx}/resources/theme/images/internet1.png');"><span id="internetSpan">新项目申请</span></button>
		</div>
	 </c:if>
   	  <div class="clearfix fun-list" onmouseover="$('#mail_write', this).show()">
            <c:forEach items="${list }" var="l" varStatus="loop">
                	<div class="list-item common1" 
                	<c:if test="${loop.index == 0}">
                		 style="background-color:#2597cd;"
                		 bc = "#2597cd"
                		 
                	</c:if>
                	<c:if test="${loop.index == 1}">
                		 style="background-color:#ea8840;"
                		 bc = "#ea8840"
                	</c:if>
                	<c:if test="${loop.index == 2}">
                		 style="background-color:#88bb00;"
                		 bc = "#88bb00"
                	</c:if>
                	<c:if test="${loop.index == 3}">
                		 style="background-color:#ea5e61;"
                		 bc = "#ea5e61"
                	</c:if>
                	<c:if test="${loop.index == 4}">
                		 style="background-color:#965dc3;"
                		 bc = "#965dc3"
                	</c:if>
                	<c:if test="${loop.index == 5}">
                		 style="background-color:#c49c4e;"
                		 bc = "#c49c4e"
                	</c:if>
                	 
                	>
                	 
                    <h4><a class="module_btn" newpage="${l.cc.newPage}" moduleid="${l.cc.moduleId}" opentype="${l.cc.openType}" inputurl="${l.cc.inputUrl}" pageurl="${l.cc.pageUrl}" divid="${l.cc.divId}"  jsContent="${l.cc.jsContent}">
                    		<c:choose>
                    			<c:when test="${l.cc.title eq '待办'}">
                    				<img class='img_tile' src="${ctx}/resources/pt/images/daiban.png">
                    			</c:when>
                    			<c:when test="${l.cc.title eq '待阅'}">
                    				<img  class='img_tile' src="${ctx}/resources/pt/images/daiyue.png">
                    			</c:when>
                    			<c:when test="${l.cc.title eq 'PDM'}">
                    				<img class='img_tile' src="${ctx}/resources/pt/images/pdm.png">
                    			</c:when>
                    			<c:when test="${l.cc.title eq '消息'}">
                    				<img class='img_tile' src="${ctx}/resources/pt/images/xiaoxi.png">
                    			</c:when>
                    			<c:when test="${l.cc.title eq 'CRM'}">
                    				<img class='img_tile' src="${ctx}/resources/pt/images/CRM.png">
                    			</c:when>
                    			<c:when test="${l.cc.title eq '报表'}">
                    				<img class='img_tile' src="${ctx}/resources/pt/images/daiyue.png">
                    			</c:when>
                    			<c:when test="${l.cc.title eq 'PM'}">
                    				<img class='img_tile' src="${ctx}/resources/pt/images/PM.png">
                    			</c:when>
                    		</c:choose>
                    		${l.cc.title }
                    		
                    		<c:if test="${l.cc.showNum == true}"><span class="nums" style="padding-left: 0px;">${l.count}</span></c:if>
                    	</a> 
                    </h4>
                    			<p class="operate">
                    			<c:forEach items="${l.buttons}" var="buttons">
                    			<div class="mail_write a_tag"  style="width: 76px;" onclick="${buttons.function }">
                    				<img class="a_tag" src="${ctx}/resources/pt/images/ldx/ldx_mail_write.png" />
	                    			<a style="font-size:12px;" class="a_tag" value="${buttons.code}">${buttons.name}</a>
                    			</div>
                    			</c:forEach>
                    			<c:if test="${l.cc.title eq '邮件'}">
                    				<div class="mail_write" >
                    				<img class="a_tag" src="${ctx}/resources/pt/images/ldx/ldx_mail_write.png" />
                    				<a class="a_tag" onclick="window.open('http://mail-test.leedarson.com/?_task=mail&_action=compose&from=lcp');" value="" style="font-size:12px;">撰写</a>
                    				</div>
                    			</c:if>
                    			</p>
                    			<c:if test="${l.cc.openSearch == true}">
	                    			<div class="commonSearchModule">
	                    			<input class="commonModuleKeyWord" type="text">
									<a class="commonSearchButton">搜索</a>
	                    			</div>
								</c:if>
                    			<p class="operate2" style="display: none;">
<%--                     			<c:forEach items="${l.buttons}" var="buttons"> --%>
<%--                     			<a class="a_tag" onclick="${buttons.function }" value="${buttons.code}">${buttons.name}</a> --%>
<%--                     			</c:forEach> --%>
                    			</p>
                    			
                    	 <span class="fold"></span>
                    </div>
           </c:forEach>
     </div>
</div>
<script>
		$(function(){
			//我要申报按钮点击事件
			$("#buttonOne").click(function () {
				XZSP_BTNS.receive2(null);
			});
			
			/*****************行选择的效果*********************************/
			$(".page_index .newoatitle .dataTr").live("mouseover",function(){
				$(".dataTr").css("background-position","0 -300px");
				$(".dataTr").css("background-color","#F7F7F7");
				$(".operate .dataTr").css("background-color","#F1F4F5");
				$(this).css("background-color","#fff");
				$(this).next().css("background-position","0 -1292px");
			});
			/***************点击块打开搜索列表************************/
			<c:forEach items="${list}" var="list">
			var openType = "<c:out value = '${list.cc.openType}' />";
			var jsContent = "<c:out value = '${list.cc.jsContent}' />";
	$(".list-item").unbind("click");
	$(".list-item").click(function(e){

		if($(e.target).attr("class")!="fold"){
			openType = $(this).find(".module_btn").attr("opentype"); 
			jsContent = $(this).find(".module_btn").attr("jsContent");
			newpage = $(this).find(".module_btn").attr("newpage");
			inputurl = $(this).find(".module_btn").attr("inputurl");
				if($(this).attr("class").indexOf("list-selected")==-1){
					var class_ = $(e.target).attr("class");
					if(StringUtils.isNotBlank(class_) &&(class_.indexOf("a_tag")>-1||class_.indexOf("commonModuleKeyWord")>-1||class_=="commonSearchButton"||class_=="clearText"||class_=="checkall"||class_=="checkeds")){
					}
//						else if(class_!=undefined&&class_.indexOf("module_btn2")>-1){//列表标题跳转
//							var inputurl = $(e.target).attr("inputurl");
//							window.location.href = ctx+inputurl;
//						}
					else{
						var temp = $(this).find(".module_btn");
						var moduleid = temp.attr("moduleid");
						if(openType == "jsContent") {
							eval(jsContent);
						}else if(openType == "inputUrl" && moduleid == ""){
							if(newpage=="this"){
								location.href="http://"+inputurl;
							}else{
								window.open("http://"+inputurl);
							}
						}else{
							$.ajax({
								type:"post",
								async:false,
								data : {"uuid":moduleid,"count":6},
								url:"${ctx}/cms/cmspage/viewcontent",
								success:function(result){
									$(".mail_write").css("display","none");
									$(".index_dff").attr("class","index_dff common-top");
									$(".fun-list").attr("class","clearfix fun-list fun-expand");
									$(".fun-list").find(".list-item").attr("class","list-item");
									$(".list-content").html("");
									$(".common2").remove();
									$(".commonSearchModule").remove();
									temp.parent().parent().hide();
									temp.parent().parent().attr("class","list-item list-selected");
									temp.parent().parent().find(".operate").html(result);
									if(moduleid == "f88650c9-b961-4dc1-ab86-897f8f54b970") {
										temp.parent().parent().find(".operate").after("<button type='button' id='messageHiddenId'/ value='123' style='display:none;'></button>");
									}
									/***添加已阅未阅的图标***/
									$(".page_index .operate .dataTr").each(function(){
										if($(this).attr("class").indexOf("readed")>-1){
											$(this).find("td").eq(0).html("<div class='icon_readed'></div>"+$(this).find("td").eq(0).html());
										}else if($(this).attr("class").indexOf("noread")>-1){
											$(this).find("td").eq(0).html("<div class='icon_noread'></div>"+$(this).find("td").eq(0).html());
										}
									});
									
									temp.parent().parent().find(".view_tool2").remove();
									var searchStr = '<input class="commonModuleKeyWord" type="text"><span class="commonSearchButton" style="font-size:12px;">搜索</span>';
									var viewButton = temp.parent().parent().find(".operate2").html();
									var tempStr = '<div class="common2">'+searchStr+viewButton+'</div>';
									temp.parent().append(tempStr);
									temp.parent().parent().show();
									var backgroundColor = $(".list-selected").attr("bc");
									 
									$(".list-selected").find("h4").css("background",backgroundColor);	
									$(".list-selected").css("background","");
									$(".list-item .module_btn").each(function(){
										$(this).attr("class","module_btn");
										$(this).find(".img_tile").hide();
//	 									$(this).find(".nums").hide();
									});
									temp.attr("class","module_btn module_btn2");
									/***格式化时间***/
									formDate();
								}
							});
						}
					};
				}else if($(this).attr("class").indexOf("list-selected")!=-1) {
					var class_ = $(e.target).attr("class");
					if(StringUtils.isNotBlank(class_) &&class_.indexOf("module_btn2")>-1){//列表标题跳转
						var inputurl = $(e.target).attr("inputurl");
						window.location.href = ctx+inputurl;
					}
				}
		}
		
	});
			</c:forEach>
			$(".fold").unbind("click");
			$(".fold").on("click",function(e){
				var this_drnw = $(e.target).parents(".dnrw");
				var moduleid = this_drnw.attr("moduleid");
				pageLock("show");
				$.ajax({
					type:"post",
					async:false,
					data : {"uuid":moduleid},
					url:ctx+"/cms/cmspage/viewcontent",
					success:function(result){
						this_drnw.html(result);
						pageLock("hide");
						jsmod(".dnrw .tab-content",true);
					}
				});
			});
			/****显示视图搜索条*******/
			$(".common1").live("mouseover",function(){
				var buttons = $(this).find(".operate").html().replace(" ","");
				if(buttons==""){
					$(this).find(".commonSearchModule").css("right","8px");	
				}
				$(this).find(".commonSearchModule").show();
			});
			$(".common1").live("mouseout",function(){
// 				if($(this).attr("class").indexOf("isfocus")>-1){
// 				}else{
// 					$(this).find(".commonSearchModule").hide();
// 				}
			});
			$(".commonModuleKeyWord").focus(function(){
				$(this).attr("class","commonModuleKeyWord isfocus");
			})
			$(".commonModuleKeyWord").blur(function(){
				$(this).attr("class","commonModuleKeyWord");
				$(this).parent(".commonSearchModule").hide();
			})
			/********点击搜索事件**************/
			$(".commonSearchButton").unbind("click");
			$(".commonSearchButton").live("click",function(){
					var temp = $(this).parents(".list-item").find(".module_btn");
					var moduleid = temp.attr("moduleid");
					var viewUuid = "";
					var keyWord = $(this).parent().find(".commonModuleKeyWord").val();
					$.ajax({
						type:"post",
						async:false,
						data : {"uuid":moduleid},
						url:"${ctx}/cms/cmspage/getViewIdByModuleId",
						success:function(result){
							viewUuid = result;
						}
					})
					
					var dyViewQueryInfo = new Object();
					dyViewQueryInfo.viewUuid = viewUuid;
					var pageInfo = new Object();
					pageInfo.currentPage = 1;
					pageInfo.pageSize = 6;
					var condSelect = new Array();
					dyViewQueryInfo.pageInfo = pageInfo;
					var keyWords = new Array();
					var keyWordMap = new Object();
					keyWordMap.all=keyWord;
					keyWords.push(keyWordMap);
					condSelect.push({"keyWords":keyWords});
					dyViewQueryInfo.condSelectList = condSelect;
					var url = '${ctx}/basicdata/view/view_show_param.action';
					$.ajax({
						url:url,
						type:"POST",
						data:JSON.stringify(dyViewQueryInfo),
						dataType:'text',
						contentType:'application/json',
						success:function(result){
							$(".index_dff").attr("class","index_dff common-top");
							$(".fun-list").attr("class","clearfix fun-list fun-expand");
							$(".fun-list").find(".list-item").attr("class","list-item");
							$(".list-content").html("");
							$(".common2").remove();
							$(".commonSearchModule").remove();
							temp.parent().parent().hide();
							 
							temp.parent().parent().attr("class","list-item list-selected");
							temp.parent().parent().find(".view_tool2").remove();
							temp.parent().parent().find(".operate").html(result);
							temp.parent().parent().find(".operate #footBar").hide();
							/***添加已阅未阅的图标***/
							$(".page_index .operate .dataTr").each(function(){
								if($(this).attr("class").indexOf("readed")>-1){
									$(this).find("td").eq(0).html("<div class='icon_readed'></div>"+$(this).find("td").eq(0).html());
								}else if($(this).attr("class").indexOf("noread")>-1){
									$(this).find("td").eq(0).html("<div class='icon_noread'></div>"+$(this).find("td").eq(0).html());
								}
							});
							
							
							var searchStr = '<input class="commonModuleKeyWord" type="text" value="'+keyWord+'" ><span class="commonSearchButton" style="font-size:12px;">搜索</span>';
							var viewButton = temp.parent().parent().find(".operate2").html();
							var tempStr = '<div class="common2">'+searchStr+viewButton+'</div>';
							temp.parent().append(tempStr);
							temp.parent().parent().show();
							$(".list-item .module_btn").each(function(){
								$(this).attr("class","module_btn");
							});
							temp.attr("class","module_btn module_btn2");
							
							temp.parent().parent().first().css("background", "");
							$(".commonModuleKeyWord").after("<div class='clearText'></div>");
							if($(".common2").find("a").length==2){
								$(".common2").find(".clearText").css("right","90px");
							}
							/***格式化时间***/
							formDate();
							
						}
					});
			});
			
			$(".commonModuleKeyWord").live("keyup",function(event){
				var code = event.keyCode;
				if (code == 13) {
					var temp = $(this).parents(".list-item").find(".module_btn");
					var moduleid = temp.attr("moduleid");
					var viewUuid = "";
					var keyWord = $(this).parent().find(".commonModuleKeyWord").val();
					$.ajax({
						type:"post",
						async:false,
						data : {"uuid":moduleid},
						url:"${ctx}/cms/cmspage/getViewIdByModuleId",
						success:function(result){
							viewUuid = result;
						}
					})
					
					var dyViewQueryInfo = new Object();
					dyViewQueryInfo.viewUuid = viewUuid;
					var pageInfo = new Object();
					pageInfo.currentPage = 1;
					pageInfo.pageSize = 6;
					var condSelect = new Array();
					dyViewQueryInfo.pageInfo = pageInfo;
					var keyWords = new Array();
					var keyWordMap = new Object();
					keyWordMap.all=keyWord;
					keyWords.push(keyWordMap);
					condSelect.push({"keyWords":keyWords});
					dyViewQueryInfo.condSelectList = condSelect;
					var url = '${ctx}/basicdata/view/view_show_param.action';
					$.ajax({
						url:url,
						type:"POST",
						data:JSON.stringify(dyViewQueryInfo),
						dataType:'text',
						contentType:'application/json',
						success:function(result){
							$(".index_dff").attr("class","index_dff common-top");
							$(".fun-list").attr("class","clearfix fun-list fun-expand");
							$(".fun-list").find(".list-item").attr("class","list-item");
							$(".list-content").html("");
							$(".common2").remove();
							$(".commonSearchModule").remove();
							temp.parent().parent().hide();
							 
							temp.parent().parent().attr("class","list-item list-selected");
							temp.parent().parent().find(".view_tool2").remove();
							temp.parent().parent().find(".operate").html(result);
							temp.parent().parent().find(".operate #footBar").hide();
							/***添加已阅未阅的图标***/
							$(".page_index .operate .dataTr").each(function(){
								if($(this).attr("class").indexOf("readed")>-1){
									$(this).find("td").eq(0).html("<div class='icon_readed'></div>"+$(this).find("td").eq(0).html());
								}else if($(this).attr("class").indexOf("noread")>-1){
									$(this).find("td").eq(0).html("<div class='icon_noread'></div>"+$(this).find("td").eq(0).html());
								}
							});
							
							
							var searchStr = '<input class="commonModuleKeyWord" type="text" value="'+keyWord+'" ><span class="commonSearchButton" style="font-size:12px;">搜索</span>';
							var viewButton = temp.parent().parent().find(".operate2").html();
							var tempStr = '<div class="common2">'+searchStr+viewButton+'</div>';
							temp.parent().append(tempStr);
							temp.parent().parent().show();
							$(".list-item .module_btn").each(function(){
								$(this).attr("class","module_btn");
							});
							temp.attr("class","module_btn module_btn2");
							
							temp.parent().parent().first().css("background", "");
							$(".commonModuleKeyWord").after("<div class='clearText'></div>");
							if($(".common2").find("a").length==2){
								$(".common2").find(".clearText").css("right","90px");
							}
							/***格式化时间***/
							formDate();
							
						}
					});
				}
			});
			function stopDefault(e) {
		        //如果提供了事件对象，则这是一个非IE浏览器 
		        if(e && e.preventDefault) {
		        　　//阻止默认浏览器动作(W3C)
		        　　e.preventDefault();
		        } else {
		        　　//IE中阻止函数器默认动作的方式 
		        　　window.event.returnValue = false; 
		        }
		        return false;
		    }
			
			/********回车搜索事件**************/
			$(".commonModuleKeyWord").live("keydown",function(event){
				var code = event.keyCode;
	    	    if (code == 13) {
	    	    	stopDefault(event);
	    	    	var temp = $(this).parents(".list-item").find(".module_btn");
					var moduleid = temp.attr("moduleid");
					var viewUuid = "";
					var keyWord = $(this).val();
					$.ajax({
						type:"post",
						async:false,
						data : {"uuid":moduleid},
						url:"${ctx}/cms/cmspage/getViewIdByModuleId",
						success:function(result){
							viewUuid = result;
						}
					})
					
					var dyViewQueryInfo = new Object();
					dyViewQueryInfo.viewUuid = viewUuid;
					var pageInfo = new Object();
					pageInfo.currentPage = 1;
					pageInfo.pageSize = 6;
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
					
					var url = '${ctx}/basicdata/dyview/view_show_param.action';
					$.ajax({
						url:url,
						type:"POST",
						data:JSON.stringify(dyViewQueryInfo),
						dataType:'text',
						contentType:'application/json',
						success:function(result){
							$(".index_dff").attr("class","index_dff common-top");
							$(".fun-list").attr("class","clearfix fun-list fun-expand");
							$(".fun-list").find(".list-item").attr("class","list-item");
							$(".list-content").html("");
							$(".common2").remove();
							$(".commonSearchModule").remove();
							temp.parent().parent().hide();
							temp.parent().parent().attr("class","list-item list-selected");
							temp.parent().parent().find(".operate").html(result);
							temp.parent().parent().find(".operate #footBar").hide();
							/*****添加已阅未阅的图标********/
							$(".page_index .operate .dataTr").each(function(){
								if($(this).attr("class").indexOf("readed")>-1){
									$(this).find("td").eq(0).html("<div class='icon_readed'></div>"+$(this).find("td").eq(0).html());
								}else if($(this).attr("class").indexOf("noread")>-1){
									$(this).find("td").eq(0).html("<div class='icon_noread'></div>"+$(this).find("td").eq(0).html());
								}
							});
							
							temp.parent().parent().find(".view_tool2").remove();
							var searchStr = '<input class="commonModuleKeyWord" type="text" value="'+keyWord+'"><a class="commonSearchButton">搜索</a>';
							var viewButton = temp.parent().parent().find(".operate2").html();
							var tempStr = '<div class="common2">'+searchStr+viewButton+'</div>';
							temp.parent().append(tempStr);
							temp.parent().parent().show();
							
							$(".list-item .module_btn").each(function(){
								$(this).attr("class","module_btn");
							});
							temp.attr("class","module_btn module_btn2");
							
							$(".commonModuleKeyWord").after("<div class='clearText'></div>");
							if($(".common2").find("a").length==2){
								$(".common2").find(".clearText").css("right","90px");
							}
							/**格式化时间**/
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
						if($(".common2").find("a").length==2){
							$(".common2").find(".clearText").css("right","90px");
						}
					}
	    	    }
			});
			/**************删除关键字******************/
			$(".commonSearchModule .clearText").die().live("click",(function(){
				$(".commonSearchModule .clearText").remove();
				$(".commonModuleKeyWord").val("");
			}));
			$(".common2 .clearText").die().live("click",(function(){
				var temp = $(this).parents(".list-item").find(".module_btn");
				var moduleid = temp.attr("moduleid");
				var viewUuid = "";
				var keyWord = $(this).val();
				$.ajax({
					type:"post",
					async:false,
					data : {"uuid":moduleid},
					url:"${ctx}/cms/cmspage/getViewIdByModuleId",
					success:function(result){
						viewUuid = result;
					}
				})
				
				var dyViewQueryInfo = new Object();
				dyViewQueryInfo.viewUuid = viewUuid;
				var pageInfo = new Object();
				pageInfo.currentPage = 1;
				pageInfo.pageSize = 6;
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
				
				var url = '${ctx}/basicdata/dyview/view_show_param.action';
				$.ajax({
					url:url,
					type:"POST",
					data:JSON.stringify(params),
					dataType:'text',
					contentType:'application/json',
					success:function(result){
						$(".index_dff").attr("class","index_dff common-top");
						$(".fun-list").attr("class","clearfix fun-list fun-expand");
						$(".fun-list").find(".list-item").attr("class","list-item");
						$(".list-content").html("");
						$(".common2").remove();
						$(".commonSearchModule").remove();
						temp.parent().parent().hide();
						temp.parent().parent().attr("class","list-item list-selected");
						temp.parent().parent().find(".operate").html(result);
						/*******添加已阅未阅的图标***********/
						$(".page_index .operate .dataTr").each(function(){
							if($(this).attr("class").indexOf("readed")>-1){
								$(this).find("td").eq(0).html("<div class='icon_readed'></div>"+$(this).find("td").eq(0).html());
							}else if($(this).attr("class").indexOf("noread")>-1){
								$(this).find("td").eq(0).html("<div class='icon_noread'></div>"+$(this).find("td").eq(0).html());
							}
						});
						
						temp.parent().parent().find(".view_tool2").remove();
						var searchStr = '<input class="commonModuleKeyWord" type="text" value="'+keyWord+'"><a class="commonSearchButton">搜索</a>';
						var viewButton = temp.parent().parent().find(".operate2").html();
						var tempStr = '<div class="common2">'+searchStr+viewButton+'</div>';
						temp.parent().append(tempStr);
						temp.parent().parent().show();
						
						$(".list-item .module_btn").each(function(){
							$(this).attr("class","module_btn");
						});
						temp.attr("class","module_btn module_btn2");
						
						/********格式化时间*******/
						formDate();
					}
				});
				
				$("this").remove();
				$(".commonModuleKeyWord").val("");
			}));
		});
	</script>
</body>
</html>