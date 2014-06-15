<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Inbox Mail List</title>
<%@ include file="/pt/common/meta.jsp"%>
<link href="${ctx}/resources/bootstrap/css/bootstrap.css" rel="stylesheet"></link>
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/jqueryui/css/base/jquery-ui.css" />
<%-- <link href="${ctx}/pt/mail/js/powerFloat.css" type="text/css" rel="stylesheet" /> --%>
</head>
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
 		<div class="left1" align="left" style="padding-bottom: 5px;height:100%;OVERFLOW-Y: hidden; margin-bottom: 5px;OVERFLOW-X:hidden;border-bottom: 1px solid #DDDDDD;" >  
              <ul >
              	<li>
              	<div class="writeMail"><div class="writeMail_icon"></div>写信</div>
              	</li>
              	<li class="xiahuaxian"> <div></div> </li>
              	<li onclick="ajaxMailContent('MAIL007','收件箱');" id="shou">
				<div class="receiveMail"><div class="receiveMail_icon"></div>收信</div>
				</li>
<!--              	<li ><img src="${ctx }/resources/pt/img/qq.gif"/>联系人</li>-->
              </ul>
        </div>
        
        <div class="left2"  align="left" style="padding-bottom: 5px;height:100%;OVERFLOW-Y: hidden;margin-bottom: 5px; OVERFLOW-X:hidden;border-bottom:1px solid #DDDDDD;" >  
              	<ul >
              	<li onclick="ajaxMailContent('MAIL007','收件箱');"><div class="left2_item_name"><spring:message code="mail.label.receiveMailBox"/>${receiveBox }</div></li>
              	<li onclick="ajaxMailContent('MAIL008','星标邮件');"><div class="left2_item_name"><div style="float: left;"><spring:message code="mail.label.xingbiao"/></div><div class="xingbiaoIcon"></div></div></li>
              	<li onclick="ajaxMailContent('MAIL006','可跟踪邮件');"><div class="left2_item_name">可跟踪邮件</div></li>
              	<li onclick="ajaxMailContent('MAIL013','草稿箱');"><div class="left2_item_name"><spring:message code="mail.label.caogaoBox"/>${caogaoBox }</div></li>
              	<li onclick="ajaxMailContent('MAIL015','已发送邮件');"><div class="left2_item_name"><spring:message code="mail.label.hasSendBox"/></div></li>
              	<li onclick="ajaxMailContent('MAIL014','已删除邮件');"><div class="left2_item_name"><spring:message code="mail.label.deleteBox"/></div></li>
              	<li onclick="ajaxMailContent('MAIL004','附件夹');"><div class="left2_item_name"><spring:message code="mail.label.fileFolder"/></div></li>
             	<li onclick="ajaxMailContent('MAIL009','设置');"><div class="left2_item_name"><spring:message code="mail.label.set"/></div></li>
<!--              	<li onclick="mailLink('write_mail','写邮件','${ctx}/mail/writeMail.action');">垃圾箱</li>-->
              	</ul>
        </div>
        
        
        <div class="left3" align="left" style="heigth:100px;OVERFLOW-Y: auto; OVERFLOW-X:hidden;">
              	<ul >
              	
				<%-- 	<!-- 其他邮件 -->
	              	<c:if test="${otherList!= null && fn:length(otherList) > 0}">
	              	<li id="othermail" onclick="changePlus('other');"><img id="otherImg" src="${ctx }/resources/ckfinder/skins/kama/images/ckfplus.gif"/><spring:message code="mail.label.otherMail"/>${otherBox }</li>
	              	</c:if>
	              	<c:if test="${otherList== null || fn:length(otherList) == 0}">
	              	<li id="othermail" onclick="changePlus('other');"><spring:message code="mail.label.otherMail"/>${otherBox }</li>
	              	</c:if>
	              	<c:forEach items="${otherList }" var="orow">
	              	<li id="other" class="other liveData" style="display:none;"  url="/basicdata/dyview/view_show?viewUuid=2a201893-761b-4bfd-b2bd-61bc5e1317a0&status_=${orow.username }" otherid="${orow.uuid }" othername="${orow.username }">${orow.username}${orow.noread }</li>
	              	</c:forEach> --%>
	              	
	              	<!-- 文件夹 -->
	              	<c:if test="${folderList!= null && fn:length(folderList) > 0}">
	              	<li id="myfile" onclick="changePlus('folder');"><img id="folderImg" src="${ctx }/resources/ckfinder/skins/kama/images/ckfplus.gif"/><spring:message code="mail.label.myfolder"/>${folderBox }</li>
	              	<c:forEach items="${folderList }" var="frow">
	              	<li id="folder" class="folder liveData" style="display:none;" name_="${frow.fname }" url="/basicdata/dyview/view_show?viewUuid=1cb04818-9156-4ccf-aee9-0af292065563&status_=${frow.uuid }" folderid="${frow.uuid }" foldername="${frow.fname }">${frow.fname }${frow.noread1 }</li>
	              	</c:forEach>
	              	</c:if>
	              	
	
	              	<!-- 标签 -->
	              	<c:if test="${labelList!= null && fn:length(labelList) > 0}">
	              	<li id="mytag" onclick="changePlus('mlabel');"><img id="mlabelImg" src="${ctx }/resources/ckfinder/skins/kama/images/ckfplus.gif"/><spring:message code="mail.label.myLabel"/>${labelBox }</li>
	              	<c:forEach items="${labelList }" var="lrow">
	<%--               	<li id="mlabel" class="mlabel liveData" style="display:none;"   onclick="mailLink('mail_label','我的标签','${ctx}/mail/mail_box_list.action?rel=15&ctype=${lrow.uuid }&pageNo=0&&mtype=0');"> --%>
	              	<li id="mlabel" class="mlabel liveData" style="display:none;" name_="${lrow.fname }" url="/basicdata/dyview/view_show?viewUuid=2f5b414a-175e-4754-871b-e233e6c5dff8&status_=${lrow.uuid }" labelid="${lrow.uuid }" labelname="${lrow.fname }" labelcolor="${lrow.color}">
	              		<span style="cursor: pointer;background-color: ${lrow.color };display:block;width:10px;height:10px;border-radius: 4px 4px 4px 4px;float: left;margin: 8px 5px 0 20px;"></span><span style="color:${lrow.color }; ">${lrow.fname }${lrow.noread1 }</span>
	              	</li>
	              	</c:forEach>
	              	</c:if>
	<!--              	<li onclick="mailLink('other_mail','其他邮件','${ctx}/mail/mail_other.action');"><img src="${ctx }/resources/pt/img/plus.gif"/>其他邮件</li>-->
	<!--              	<li >日历</li>-->
              	</ul>
              </div>


<script src="${ctx}/resources/jquery/jquery.js"></script>
<!-- jQuery UI -->
<script src="${ctx}/resources/jqueryui/js/jquery-ui.js"></script>

<script src="${ctx}/pt/mail/js/jquery-powerFloat-min.js" type="text/javascript"></script>

<script type="text/javascript" src="${ctx}/resources/pt/mail/global_zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>

<script src="${ctx}/pt/mail/js/mail.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/resources/timers/jquery.timers.min.js"></script>
<script type="text/javascript">
$(function(){
	$(".mail_div  ul li").click(function(){
		$(".mail_div  ul li").removeClass('active');
		if($(this).attr("id")!="mytag"&&$(this).attr("id")!="othermail"&&$(this).attr("id")!="myfile"){
			$(this).addClass('active');
		}
	});
	$(".writeMail").click(function(){
		$.ajax({
			type : "POST",
			url : ctx+"/mail/findMailUser.action",
			data : [],
			dataType : "text",
			success : function callback(result) {
				if(result == 'false'){
					oAlert("未配置对应的邮件用户");
				} else {
					window.open(ctx+'/mail/writeMail.action');
				}
			}
		});
	});
});
</script>
</div>
</body>
</html>