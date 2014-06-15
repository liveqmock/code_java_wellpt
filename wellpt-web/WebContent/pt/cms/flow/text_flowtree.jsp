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
	<style type="text/css">
	.gl_list1 ul,li{ cursor: pointer; list-style:none; list-style-type:none; margin:0px; padding:0px; background: none repeat scroll 0 0 transparent;}
	.gl_con1{ color:#666666; font-size:12px; font-family:"宋体";height:754px; margin:0px auto; width:238px; border:1px solid #a4c8fe; border-top:0px; background-color:#e0f1ff;}
	.gl_tit2{ width:210px; background-color:#abd7fb; height:27px; line-height:27px; padding-left:28px; font-weight:bold;}
	.gl_list1{ width:206px; margin:0px auto; margin-top:12px; clear:both;}
	.gl_list1 ul li{ width:90px; float:left; padding-left:13px; line-height:30px; height:30px;}
	.gl_list1 ul li:hover{color:#4b9ee0;}
	.li_bar{color:#4b9ee0;}
	</style>
	<div class="gl_con1">
			<div class="gl_tit2 tblue1" style="background: url(${ctx}${parent.icon}) no-repeat scroll 16px 12px #ABD7FB;">${parent.title}</div>
			<div class="gl_list1">
				<ul>
					<c:forEach items="${list }" var="l">
						<li newpage="${l.cc.newPage}" moduleid="${l.cc.moduleId}" opentype="${l.cc.openType}" inputurl="${l.cc.inputUrl}" pageurl="${l.cc.pageUrl}" divid="${l.cc.divId}" jscontent="${parent.jsContent}" style="background:url(${ctx}${l.cc.icon}) no-repeat left center;" class="btn_open">${l.cc.title }</li>
					</c:forEach>
					<div class="clear"></div>
				</ul>
			</div>
	</div>
	<script>
		$(function(){
			$(".btn_open").click(function(){
				$(".btn_open").attr("class","btn_open");
				$(this).attr("class","btn_open li_bar");
				var moduleid = $(this).attr("moduleid");
				var newpage = $(this).attr("newpage");
				var opentype = $(this).attr("opentype");
				var inputurl = $(this).attr("inputurl");
				var pageurl = $(this).attr("pageurl");
				var divid = $(this).attr("divid");
				var name = $(this).text();
				if(opentype=="divId"){
					$.ajax({
						type:"post",
						async:false,
						data : {"uuid":moduleid},
						url:"${ctx}/cms/cmspage/viewcontent",
						success:function(result){
							$("#"+divid+" ul li a" ).text(name);
							$("#"+divid+" div div").html(result);
						}
					});
				}else if(opentype=="pageUrl"){
					if(newpage=="false"){
						location.href="${ctx}/"+pageurl;
					}else{
						window.open("${ctx}/"+pageurl);
					}
				}else if(opentype=="inputUrl"){
					if(newpage=="false"){
						location.href=inputurl;
					}else{
						window.open(inputurl);
					}
				}else if(opentype=="jsContent"){
					${parent.jsContent}
				}
			});	
		});
	</script>
</body>
</html>