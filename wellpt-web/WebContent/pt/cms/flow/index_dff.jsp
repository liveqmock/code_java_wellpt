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
<link href="${ctx}/resources/theme/css/urlPage.css" rel="stylesheet">
<div class="index_dff">
   	  <div class="clearfix fun-list">
            <c:forEach items="${list }" var="l">
                	<div class="list-item common1">
                    <h4><a class="module_btn" newpage="${l.cc.newPage}" moduleid="${l.cc.moduleId}" opentype="${l.cc.openType}" inputurl="${l.cc.inputUrl}" pageurl="${l.cc.pageUrl}" divid="${l.cc.divId}">
                    		${l.cc.title }<c:if test="${l.cc.showNum == true}"><span class="nums">${l.count}</span></c:if>
                    	</a></h4>
                    			<p class="operate">
                    			<c:forEach items="${l.buttons}" var="buttons">
                    			<a class="a_tag" onclick="${buttons.function }" value="${buttons.code}">${buttons.name}</a>
                    			</c:forEach>
                    			</p>
                    			<c:if test="${l.cc.openSearch == true}">
	                    			<div class="commonSearchModule">
	                    			<input class="commonModuleKeyWord" type="text">
									<a class="commonSearchButton">搜索</a>
	                    			</div>
								</c:if>
                    			<p class="operate2" style="display: none;">
                    			<c:forEach items="${l.buttons}" var="buttons">
                    			<a class="a_tag" onclick="${buttons.function }" value="${buttons.code}">${buttons.name}</a>
                    			</c:forEach>
                    			</p>
                    			
                    	 <div class="list-content modult-list">
                    	</div>
                    <span class="fold"></span>
                    </div>
           </c:forEach>
     </div>
</div>
<script>
		$(function(){
			/*****************行选择的效果*********************************/
			$(".page_index .newoatitle .dataTr").live("mouseover",function(){
				$(".dataTr").css("background-position","0 -300px");
				$(".dataTr").css("background-color","#F7F7F7");
				$(".operate .dataTr").css("background-color","#F1F4F5");
				$(this).css("background-color","#fff");
				$(this).next().css("background-position","0 -1292px");
			});
			/***************点击块打开搜索列表************************/
			$(".list-item").unbind("click");
			$(".list-item").click(function(e){
				var class_ = $(e.target).attr("class");
				if(class_!=undefined&&(class_=="a_tag"||class_.indexOf("commonModuleKeyWord")>-1||class_=="commonSearchButton"||class_=="clearText"||class_=="checkall"||class_=="checkeds")){
				}else if(class_!=undefined&&class_.indexOf("module_btn2")>-1){//列表标题跳转
					var inputurl = $(e.target).attr("inputurl");
					window.location.href = ctx+inputurl;
				}else{
					var temp = $(this).find(".module_btn");
					var moduleid = temp.attr("moduleid");
					$.ajax({
						type:"post",
						async:false,
						data : {"uuid":moduleid,"count":6},
						url:"${ctx}/cms/cmspage/viewcontent",
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
							/***添加已阅未阅的图标***/
							$(".page_index .operate .dataTr").each(function(){
								if($(this).attr("class").indexOf("readed")>-1){
									$(this).find("td").eq(0).html("<div class='icon_readed'></div>"+$(this).find("td").eq(0).html());
								}else if($(this).attr("class").indexOf("noread")>-1){
									$(this).find("td").eq(0).html("<div class='icon_noread'></div>"+$(this).find("td").eq(0).html());
								}
							});
							
							temp.parent().parent().find(".view_tool2").remove();
							var searchStr = '<input class="commonModuleKeyWord" type="text"><a class="commonSearchButton">搜索</a>';
							var viewButton = temp.parent().parent().find(".operate2").html();
							var tempStr = '<div class="common2">'+searchStr+viewButton+'</div>';
							temp.parent().append(tempStr);
							temp.parent().parent().show();
							
							$(".list-item .module_btn").each(function(){
								$(this).attr("class","module_btn");
							});
							temp.attr("class","module_btn module_btn2");
							/***格式化时间***/
							formDate();
						}
					});
				};
			});
			
			$(".fold").unbind("click");
			$(".fold").on("click",function(e){
				refreshWindow($(this));
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
				if($(this).find(".commonModuleKeyWord").attr("class").indexOf("isfocus")>-1){
				}else{
					$(this).find(".commonSearchModule").hide();
				}
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
							temp.parent().parent().find(".operate #footBar").hide();
							/***添加已阅未阅的图标***/
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
							/***格式化时间***/
							formDate();
							
						}
					});
			});
			/********回车搜索事件**************/
			$(".commonModuleKeyWord").live("keyup",function(event){
				var code = event.keyCode;
	    	    if (code == 13) {
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