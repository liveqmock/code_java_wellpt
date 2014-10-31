<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/pt/common/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<html>
<head>
<title></title>
<script src="${ctx}/resources/jquery/jquery.js"></script>
<script type="text/javascript"
		src="${ctx}/resources/pt/js/global.js"></script>
</head>
<body>
<style>
	
	.ldxnav .fun-list {
	    border: 1px solid;
	    position: absolute;
	    top: 90px;
	    width: 97%;
	}
	.fold {
		cursor: pointer;
    	float: right;
	}
	
	.ldx_list {
		float: left;
		cursor: pointer;
		margin: 18px 0 0 20px;
		position: relative;
/* 		background:url('${ctx}/resources/pt/images/ldx/ldx_index_icon.jpg'); */
/* 		height:50px; */
/* 		width:50px; */
	}
	.ldx_list .ldx_workflow{
	background:url('${ctx}/resources/pt/images/ldx/ldx_workflow.png');
		height:65px;
		width:65px;
	}
	.ldx_list .ldx_file{
	background:url('${ctx}/resources/pt/images/ldx/ldx_file.png');
		height:65px;
		width:65px;
	}
	.ldx_list .ldx_function{
	background:url('${ctx}/resources/pt/images/ldx/ldx_function.png');
		height:65px;
		width:65px;
	}
	.ldx_list .ldx_report{
	background:url('${ctx}/resources/pt/images/ldx/ldx_report.png');
		height:65px;
		width:65px;
	}
	.ldxnav .hClass {
		text-align:center;text-overflow: ellipsis;width: 70px;line-height: 15px;overflow: hidden;height: 50px;
	}
</style>
<script src="${ctx}/resources/app/js/xzsp/xzsp_cmsbtn.js"></script>
<link href="${ctx}/resources/theme/css/urlPage.css" rel="stylesheet">
<div class="index_dff" style="height:734px;">
		<div class="search-box" style="margin-left: 600px;padding: 13px 0 27px;"><input class="text" id="keyword" type="text" /><button style="width:50px;" id="searchbtn">搜索</button></div>
   	  	<div class="ldxnav fun-list" style="height:88%;border: 1px solid gray;">
            <c:forEach items="${list }" var="l">
                	<div class="ldx_list">
                	<c:if test="${l.cc.cateName eq '分类'}">
                		<div class="ldx_file"></div>
                	</c:if>
                	<c:if test="${l.cc.cateName eq '流程' }">
                		<div class="ldx_workflow"></div>
                	</c:if>
                	<c:if test="${l.cc.cateName eq '功能' }">
                		<div class="ldx_function"></div>
                	</c:if>
                	<c:if test="${l.cc.cateName eq '报表' }">
                		<div class="ldx_report"></div>
                	</c:if>
	                    <h1 class='hClass'>
	                    	<a class="module_btn" cateName="${l.cc.cateName}" cateUuid="${l.cc.cateUuid}" code="${l.cc.code}" newpage="${l.cc.newPage}" moduleid="${l.cc.moduleId}" opentype="${l.cc.openType}" inputurl="${l.cc.inputUrl}" pageurl="${l.cc.pageUrl}" divid="${l.cc.divId}">
	                    		${l.cc.title }
	                    		<c:if test="${l.cc.showNum == true}"><span class="nums">${l.count}</span></c:if>
	                    	</a>
	                    </h1>
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
			/***************点击块打开子级别导航列表************************/
			$(".ldx_list").unbind("click");
			$(".ldx_list").live("click",function(e){
				if($(this).attr("class").indexOf("list-selected")==-1){
					var class_ = $(e.target).attr("class");
					if(StringUtils.isNotBlank(class_) &&(class_=="a_tag"||class_.indexOf("commonModuleKeyWord")>-1||class_=="commonSearchButton"||class_=="clearText"||class_=="checkall"||class_=="checkeds")){
					}
// 					else if(class_!=undefined&&class_.indexOf("module_btn2")>-1){//列表标题跳转
// 						var inputurl = $(e.target).attr("inputurl");
// 						window.location.href = ctx+inputurl;
// 					}
					else{
						var temp = $(this).find(".module_btn");
						var moduleid = temp.attr("moduleid");
						var newpage = temp.attr("newpage");
						var opentype = temp.attr("opentype");
						var code = temp.attr("code");
						var divid = temp.attr("divid");
						var fullwindow = temp.attr("fullwindow");
						var inputurl = temp.attr("inputurl");
						var pageurl = temp.attr("inputurl");
						var title = temp.html();
						$.ajax({
							type:"post",
							async:false,
							contentType:'application/json',
							url:"${ctx}/cms/cmspage/getChildCategoryHtml?code="+code,
							success:function(result) {
								if($(".ldx_title").size() != 0 ) {
									$(".ldx_title").remove();
								}
								var ccHtml = "<div class='ldx_title' style='left: 50px;position: absolute;top: 25px;font-size: 20px;'>"+title+"</div>";
									ccHtml += "<div class='ldxnav fun-list' style='background: none repeat scroll 0 0 gainsboro;border: 1px solid;position: absolute;top: 55px;width: 98%;height:89%;'>";
								for (var index=0;index<result.length;index++) {
									var ccList = result[index].cc;
									if(ccList.cateName == '分类') {
										ccHtml += "<div class='ldx_list'><div class='ldx_file'></div>";
									}else if(ccList.cateName == '功能') {
										ccHtml += "<div class='ldx_list'><div class='ldx_function'></div>";
									}else if(ccList.cateName == '报表') {
										ccHtml += "<div class='ldx_list'><div class='ldx_report'></div>";
									}else if(ccList.cateName == '流程') {
										ccHtml += "<div class='ldx_list'><div class='ldx_workflow'></div>";
									}
										ccHtml += "<h1 class='hClass'><a class='module_btn' fullwindow='"+ccList.fullwindow+"' code='"+ccList.code+"' newpage='"+ccList.newPage+"' moduleid='"+ccList.moduleId+"' opentype='"+ccList.openType+"' inputurl='"+ccList.inputUrl+"' pageurl='"+ccList.pageUrl+"'  divid='"+ccList.divId+"'>"
			                    		+ccList.title+"</a></h1></div>";
								}
								ccHtml += "<span class='fold'>关闭</span></div>";
								if(result.length !=0) {
									temp.parent().parent().parent().parent().append(ccHtml);
								}else {
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
							}
						})
					};
				}else if($(this).attr("class").indexOf("list-selected")!=-1) {
					var class_ = $(e.target).attr("class");
					if(StringUtils.isNotBlank(class_) &&class_.indexOf("module_btn2")>-1){//列表标题跳转
						var inputurl = $(e.target).attr("inputurl");
						window.location.href = ctx+inputurl;
					}
				}
			});
			$(".fold").unbind("click");
			$(".fold").live("click",function(e){
				$(this).parent().remove();
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
// 			$(".index_dff").live("click",function(e){
// 				var class_ = $(e.target).attr("class");
// 				alert(class_);
// 			});
			
// 			$(".ldxnav").live("click",function(e){
// 				var temp = $(e.target).attr("type");
// 				var t = $(e.target);
// 				var class_ = $(e.target).attr("class");
// 			}

		});
	</script>
</body>
</html>