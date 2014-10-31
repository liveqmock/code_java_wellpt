<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/pt/common/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<html>
<head>
<title></title>
<link href="${ctx}/resources/bootstrap/css/bootstrap.css" rel="stylesheet">
<script src="${ctx}/resources/jquery/jquery.js"></script>
</head>
<body>
	<link href="${ctx}/resources/theme/css/urlPage.css" rel="stylesheet">
	<div class="newoa_cate">
		<input type="hidden" value="${parent.title}" id="navtitle"/>
		<div class="clearfix fun-list catelist">
			<c:forEach items="${list }" var="l" varStatus="index">
			<c:if test="${index.index%2==0}"><div class="jo" ></c:if>
			   <div class="list-item <c:if test="${index.index%2==0}"> list-item_left</c:if><c:if test="${index.index%2==1}"> list-item_right</c:if>">
			   	<h4>
			   		<a class="openchild" newpage="${l.cc.newPage}" fullwindow="${l.cc.fullWindow}" jscontent="${l.cc.jsContent}" moduleid="${l.cc.moduleId}" opentype="${l.cc.openType}" inputurl="${l.cc.inputUrl}" pageurl="${l.cc.pageUrl}" divid="${l.cc.divId}">${l.cc.title }</a>
			   		<c:if test="${l.cc.showNum == true}"><span class="nums">${l.count}</span></c:if>
			   	</h4>
			   	<c:if test="${l.clist.size()!=0}"><span class="fold"></span></c:if>
			   <div class="list-content ">
			        <div class="hide-border"></div>
			        	<ul>
			        		<c:forEach items="${l.clist}" var="ll">
			            	<li><a class="cateherfbtn" fullwindow="${ll.fullWindow}" jscontent="${ll.jsContent}" newpage="${ll.newPage}" moduleid="${ll.moduleId}" opentype="${ll.openType}" inputurl="${ll.inputUrl}" pageurl="${ll.pageUrl}" divid="${ll.divId}">${ll.title }</a></li>
			            	</c:forEach>
			            </ul>
			     </div>
			   </div>
			   <c:if test="${index.index%2==1}"></div></c:if>
		  </c:forEach>
		</div>
	</div>
	<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
	<script>
		$(function(){
			/**页面刷新时绑定选项**/
			var search = readSearch();
			$.each(search,function(n,value) {   
		           if(n == "uuid" || n == "treeName" || n =="viewName" || n =="keyword"
		        	|| n == "keyword2" || n == "keyword3" || n == "keyword4" || n == "keywordTime1" 
		        	|| n == "doSearch" || n == "keywordTime2" 
		           ) {
		        	   
		           }else {
		        	   search.mid = n;
		        	   search.moduleid = value;
		           }
		         }); 
			if(search.moduleid){
				$(".newoa_cate").find("a").each(function(){
					var moduleid = $(this).attr("moduleid");
					var title =  $(this).text();
					if(moduleid==search.moduleid){
						$(".jh2").show();
						
						if($(".newoa_cate").find(".activite").length!=0){
							$(".newoa_cate").find(".activite").attr("class",$(".newoa_cate").find(".activite").attr("class").replace("activite",""));
						}
						$(this).attr("class",$(this).attr("class")+" activite");
						$(".list-item").css("border-color","#C5CACD");
						$(".list-item").css("background","#F1F4F5");
						$(".activite").parent().parent().css("background","#fff");
						if($(".activite").attr("class").indexOf("cateherfbtn")<0){
							$(".activite").parent().parent().css("box-shadow","2px 2px 3px #B5B5B5");
						}else{
							var elemet_a = $(".activite").parents(".list-item").find(".openchild");
							if(elemet_a.parent().next().attr("class")=="fold"){
								$(".activite").parents(".list-item").css("background","#fff");
								$(".activite").parents(".list-item").css("border-color","#1C6AA9");
								/**存在子级***/
								if($(".catelist").attr("class").indexOf("fun-expand")<0){
									$(".catelist").attr("class",$(".catelist").attr("class")+" fun-expand");
								}
								$(".jo .list-item").each(function(){
									$(this).attr("class",$(this).attr("class").replace("list-selected",""));					
								});
								$(".jo").css("height",46);
								elemet_a.parent().parent().attr("class",elemet_a.parent().parent().attr("class")+" list-selected");
								elemet_a.parent().parent().parent().css("height",parseInt(elemet_a.parent().parent().find(".list-content").css("height"))+86);
								$(".list-content").hide();
								elemet_a.parent().parent().find(".list-content").show();
							}
						}
					}
				});
			}
			/**设置title***/
			$(".newoa_cate").parents(".dnrw").find(".nav-tabs li a").html($("#navtitle").val());
			/**点击后样式的处理***/
			$(".newoa_cate").find(".list-item").click(function(){
				var elemet_a = $(this).find(".openchild");
				if($(".newoa_cate").find(".activite").length!=0){
					$(".newoa_cate").find(".activite").attr("class",$(".newoa_cate").find(".activite").attr("class").replace("activite",""));
				}
				elemet_a.attr("class",elemet_a.attr("class")+" activite");
				if($(this).find(".fold").length>0){
					$(this).css("border-color","#1C6AA9");
				}else{
					$(".list-item").css("border-color","#C5CACD");
				}
				$(".list-item").css("background","#F1F4F5");
				$(".list-item").css("box-shadow","none");
 				$(".list-item").css("border-color","#C5CACD");
				$(".activite").parent().parent().css("background","#fff");
				$(".activite").parent().parent().css("box-shadow","2px 2px 3px #B5B5B5");
 				$(".activite").parent().parent().css("border-color","#1C6AA9");
			});
			
			$(".list-item").unbind("mouseover");
			$(".list-item").mouseover(function(){
				$(this).css("background","#fff");
				$(this).css("box-shadow","2px 2px 3px #B5B5B5");
			});
			
			$(".list-item").unbind("mouseout");
			$(".list-item").mouseout(function(){
				if($(this).find(".activite").length>0){
				}else{
					$(this).css("background","#F1F4F5");
					$(this).css("box-shadow","none");
				}
			});
			/**点击相应事件的处理***/
			$(".jo .list-item").click(function(){
				var elemet_a = $(this).find(".openchild");
				$(".jh2").show();
				if(elemet_a.parent().next().attr("class")=="fold"){
					/**存在子级***/
					if($(".catelist").attr("class").indexOf("fun-expand")<0){
						$(".catelist").attr("class",$(".catelist").attr("class")+" fun-expand");
					}
					$(".jo .list-item").each(function(){
						$(this).attr("class",$(this).attr("class").replace("list-selected",""));					
					});
					$(".jo").css("height",46);
					elemet_a.parent().parent().attr("class",elemet_a.parent().parent().attr("class")+" list-selected");
					elemet_a.parent().parent().parent().css("height",parseInt(elemet_a.parent().parent().find(".list-content").css("height"))+86);
					$(".list-content").hide();
					elemet_a.parent().parent().find(".list-content").show();
				}else{
					$(".list-content").hide();
					$(".jo").css("height",46);
					if($(".list-selected").length!=0){
						$(".list-selected").attr("class",$(".list-selected").attr("class").replace("list-selected",""));
					}
					if($(".fun-expand").length!=0){
						$(".fun-expand").attr("class",$(".fun-expand").attr("class").replace("fun-expand",""));
					}
					/**不存在子级***/
					var moduleid = elemet_a.attr("moduleid");
					var newpage = elemet_a.attr("newpage");
					var opentype = elemet_a.attr("opentype");
					var inputurl = elemet_a.attr("inputurl");
					var pageurl = elemet_a.attr("pageurl");
					var divid = elemet_a.attr("divid");
					var jsContent = elemet_a.attr("jsContent");
					var fullwindow =  elemet_a.attr("fullwindow");
					var name = elemet_a.text();
					if(newpage=="dialog"){
						var json = new Object(); 
				        json.content = $(".send_message").html();
				        json.title = "发送消息";
				        json.height= 350;
				        json.width= 830;
				        var buttons = new Object(); 
				        buttons.发送 = sendMessage;
				        json.buttons = buttons;
				        showDialog(json);
					}else{
						if(opentype=="moduleId"){
							$.ajax({
								type:"post",
								async:false,
								data : {"uuid":moduleid},
								url:"${ctx}/cms/cmspage/viewcontent",
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
									/**初始化控件**/
									initCtl();
								}
							});
						}else if(opentype=="pageUrl"){
							if(newpage=="this"){
								location.href="${ctx}/"+pageurl;
							}else{
								window.open("${ctx}/"+pageurl);
							}
						}else if(opentype=="inputUrl"){
							if(newpage=="this"){
								location.href="${ctx}/"+inputurl;
							}else{
								window.open("${ctx}/"+inputurl);
							}
						}else if(opentype=="jsContent"){
							eval(jsContent); 
						}
					}
				}
				$("#update_"+viewUuid).find(".dataTr").each(function(){
					if($(this).attr("class").indexOf("readed")>-1 && $(this).find("td").eq(0).find("input").attr("class").indexOf("checkeds") ==-1){
						$(this).find("td").eq(0).html("<div class='icon_readed'></div>"+$(this).find("td").eq(0).html());
					}else if($(this).attr("class").indexOf("noread")>-1 && $(this).find("td").eq(0).find("input").attr("class").indexOf("checkeds") ==-1){
						$(this).find("td").eq(0).html("<div class='icon_noread'></div>"+$(this).find("td").eq(0).html());
					}
				});
			});	
			$(".cateherfbtn").click(function(e){
				$(".cateherfbtn").css("color","#000000");
				$(this).css("color","#FF4800");
				$(this).parents(".list-selected").css("border-color","#1C6AA9");
				$(this).parents(".list-selected").css("background","#fff");
				var moduleid = $(this).attr("moduleid");
				var newpage = $(this).attr("newpage");
				var opentype = $(this).attr("opentype");
				var inputurl = $(this).attr("inputurl");
				var pageurl = $(this).attr("pageurl");
				var divid = $(this).attr("divid");
				var fullwindow =  $(this).attr("fullwindow");
				var name = $(this).text();
				if(newpage=="dialog"){
					var json = new Object(); 
			        json.content = $(".send_message").html();
			        json.title = "发送消息";
			        json.height= 350;
			        json.width= 830;
			        var buttons = new Object(); 
			        buttons.发送 = sendMessage;
			        json.buttons = buttons;
			        showDialog(json);

				}else{
					if(opentype=="moduleId"){
						$.ajax({
							type:"post",
							async:false,
							data : {"uuid":moduleid},
							url:"${ctx}/cms/cmspage/viewcontent",
							success:function(result){
								$("#"+divid+" ul li a" ).text(name);
								$("#"+divid+" div div").attr("moduleid",moduleid);
								if(fullwindow=='yes'){
									$("#"+divid).html(result);
								}else{
									$("#"+divid+" .viewContent").parent().html(result);
									jsmod("#"+divid+" .tab-content");
								}
							}
						});
					}else if(opentype=="pageUrl"){
						if(newpage=="this"){
							location.href="${ctx}/"+pageurl;
						}else{
							window.open("${ctx}/"+pageurl);
						}
					}else if(opentype=="inputUrl"){
						if(newpage=="this"){
							location.href="${ctx}/"+inputurl;
						}else{
							window.open("${ctx}/"+inputurl);
						}
					}else if(opentype=="jsContent"){
						${parent.jsContent}
					}
				}
				e.stopPropagation();
			});	
		});
// 		$("#update_"+viewUuid).find(".dataTr").each(function(){
// 			if($(this).attr("class").indexOf("readed")>-1){
// 				$(this).find("td").eq(0).html("<div class='icon_readed'></div>"+$(this).find("td").eq(0).html());
// 			}else if($(this).attr("class").indexOf("noread")>-1){
// 				$(this).find("td").eq(0).html("<div class='icon_noread'></div>"+$(this).find("td").eq(0).html());
// 			}
// 		});
	</script>
</body>
</html>