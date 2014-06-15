<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>事项管理左侧导航</title>
<%@ include file="/pt/common/meta.jsp"%>
<link href="${ctx}/resources/bootstrap/css/bootstrap.css" rel="stylesheet"></link>
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/jqueryui/css/base/jquery-ui.css" />
</head>
<script type="text/javascript">

</script>
<body>
<div class="mail_div">
<style type="text/css">
.mail_div .left1 ul li{padding-left: 15px;margin: 1px 2px;list-style-type:none;font-size: 15px;font-weight:bold;line-height:30px;height: 30px;cursor: pointer;}
.mail_div .left2 ul li{font-size: 12px;padding-left: 15px;margin: 1px 2px;list-style-type:none;line-height:25px;height: 25px;cursor: pointer;}
.mail_div .left3 ul li{font-size: 12px;margin: 1px 2px;list-style-type:none;line-height:25px;height: 25px;cursor: pointer;}
.mail_div  ul li:hover{background-color:#4b9ad2;}
/* .mail_div  ul #mytag:hover,.mail_div  ul #othermail:hover{ */
/* 	background: none; */
/* } */
.mail_div .active{background-color:#4b9ad2;}
.mail_div .left3 ul .other,.mail_div .left3 ul .folder {
    margin-left: 20px;
}
.mail_div {
    background: none repeat scroll 0 0 #F7F7F7;
    margin: 10px auto 0;
    width: 90%;
    font-family: "Microsoft YaHei";
    color: #000000;
}
.mail_div ul{
	margin: 0;
}
.writeMail_icon{
	background: url("${ctx}/resources/theme/images/v1_icon4.png") no-repeat scroll 0 -1px transparent;
	width: 20px;
	height: 20px;
	margin: 5px 5px 0 0;
	float: left;
}
.receiveMail_icon{
	background: url("${ctx}/resources/theme/images/v1_icon4.png") no-repeat scroll 0 -32px transparent;
	width: 20px;
	height: 20px;
	margin: 5px 5px 0 0;
	float: left;
}
.xingbiaoIcon{
	background: url("/wellpt-web/resources/theme/images/v1_icon4.png") no-repeat scroll -48px -176px transparent;
    float: left;
    height: 15px;
    margin: 5px 5px 0;
    width: 15px;
}
.mail_div .left1 ul .xiahuaxian {
    height: 1px;
    margin: 0;
    padding: 0;
}
.mail_div .left1 ul .xiahuaxian div{
	border-bottom: 1px solid #DDDDDD;
    height: 1px;
    margin-left: 10px;
    width: 40%;;
}
</style>
       <div class="left3" align="left" style="heigth:100px;OVERFLOW-Y: auto; OVERFLOW-X:hidden;">
          	<ul >
          	
           	<c:if test="${folderList!= null && fn:length(folderList) > 0}">
           		<c:forEach items="${folderList}" var="frow">
           			<li id="folder" class="folder liveData_matters"  name_="${frow.title }" url="/basicdata/dyview/view_show?viewUuid=caa1ff4c-a987-41c7-ab00-4fcd107fdafd&status_=${frow.uuid }" folderid="${frow.uuid }" foldername="${frow.title }">${frow.title }</li>
           		</c:forEach>
           	</c:if> 

          	</ul>
        </div>

<script src="${ctx}/resources/jquery/jquery.js"></script>
<!-- jQuery UI -->
<script src="${ctx}/resources/jqueryui/js/jquery-ui.js"></script>

<script src="${ctx}/pt/mail/js/jquery-powerFloat-min.js" type="text/javascript"></script>

<script type="text/javascript" src="${ctx}/resources/pt/mail/global_zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>

<script type="text/javascript" src="${ctx}/resources/timers/jquery.timers.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/matters/loadmatters.js"></script>
<script type="text/javascript">
$(function(){
	//点击左侧导航事件
	$(".mail_div  ul li").click(function(){
		$(".mail_div  ul li").removeClass('active');
		if($(this).attr("id")!="mytag"&&$(this).attr("id")!="othermail"&&$(this).attr("id")!="myfile"){
			$(this).addClass('active');
		}
	});
});
</script>
</div>
</body>
</html>