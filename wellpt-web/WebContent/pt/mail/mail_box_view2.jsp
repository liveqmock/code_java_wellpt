<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${mail.subject}</title>
<%@ include file="/pt/common/meta.jsp"%>

<script src="${ctx}/resources/jquery/jquery.js"></script>
<!-- jQuery UI -->
<script src="${ctx}/resources/jqueryui/js/jquery-ui.js"></script>
<link href="${ctx}/resources/theme/css/wellnewoa.css" rel="stylesheet" />
<link href="${ctx}/resources/jqgrid/themes/ui.jqgrid.css" rel="stylesheet" />
<link type="text/css" rel="stylesheet" href="${ctx}/resources/jBox/Skins/Blue/jbox.css"/>
<link href="${ctx}/resources/jqgrid/themes/redmond/jquery-ui-1.8.2.custom.css" rel="stylesheet" />
<link href="${ctx}/pt/mail/js/powerFloat.css" type="text/css" rel="stylesheet" />
<script type="text/javascript"
	src="${ctx}/resources/ckeditor4.1/ckeditor.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/pt/js/common/jquery.ckeditor.js"></script>
<script src="${ctx}/pt/mail/js/jquery-powerFloat-min.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/resources/pt/mail/global_zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
<script src="${ctx}/pt/mail/js/mail.js" type="text/javascript"></script>
<script src="${ctx}/resources/pt/js/common/jquery.alerts.js" type="text/javascript"></script>
<script src="${ctx}/resources/My97DatePicker/WdatePicker.js"
		type="text/javascript"></script>
<script src="${ctx}/resources/jqgrid/js/i18n/grid.locale-cn.js"></script>
	<script src="${ctx}/resources/jqgrid/js/jquery.jqGrid.min.js"></script>
	<script src="${ctx}/resources/jBox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript"
		src="${ctx}/resources/pt/js/common/jquery.cmsWindown.js"></script>
<script type="text/javascript" src="${ctx}/resources/timers/jquery.timers.min.js"></script>
<script type="text/javascript">
$(function() {
	$("#markSelectBox").powerFloat({
        width: 117,
        eventType: "click",
        target: ".qmpanel_shadow",
        showCall: function() {
            $(".menu_item").click(function() {
                $("#markSelectTo").text($(this).text());
                $.powerFloat.hide();
            });
        }
    });	
$("#markMoveBox").powerFloat({
        width: 117,
        eventType: "click",
        target: ".qmpanel_shadow2",
        showCall: function() {
            $(".menu_item").click(function() {
                $("markMoveTo").text($(this).text());
                $.powerFloat.hide();
            });
        }
    });	
$("#markSelectBox2").powerFloat({
    width: 117,
    eventType: "click",
    target: ".qmpanel_shadow",
    showCall: function() {
        $(".menu_item").click(function() {
            $("#markSelectTo2").text($(this).text());
            $.powerFloat.hide();
        });
    }
});	
$("#markMoveBox2").powerFloat({
    width: 117,
    eventType: "click",
    target: ".qmpanel_shadow2",
    showCall: function() {
        $(".menu_item").click(function() {
            $("markMoveTo2").text($(this).text());
            $.powerFloat.hide();
        });
    }
});	
});
function trim(str){ //删除左右两端的空格
    return str.replace(/(^\s*)|(\s*$)/g, "");
}
</script>
<style>
body {
    background-color: #586973;
    color: #666666;
    font-family: "Microsoft YaHei";
    font-size: 12px;
    margin: 0;
    padding: 0;
    text-align: left;
}
.mail_css_bg{
	background: url("${ctx}/resources/theme/images/v1_bd3.png") repeat-y scroll 100% 0 #FFFFFF;
    border-left: 1px solid #FFFFFF;
    margin: 10px auto 0;
    width: 76%;
    font-family: "Microsoft YaHei";
}
.mail_css {
    width: 99.5%;
    min-height: 650px;
}
.body_foot {
    background: url("${ctx}/resources/theme/images/v1_bg_bottom.png") repeat-x scroll 0 0 transparent;
    height: 33px;
    margin: 0 auto;
    width: 76%;
}
.form_header {
	background: url("${ctx}/resources/theme/images/v1_icon.png") repeat-x scroll 0 -53px transparent;
}
.form_header .form_title {
    border-top: 1px solid #FFFFFF;
    height: 40px;
    margin: 0 auto;
}
.form_header .form_title h2 {
	color: #FFFFFF;
    float: left;
    font-family: "Microsoft YaHei";
    font-size: 18px;
    font-weight: normal;
    margin: 0px;
    padding: 8px 0 0 15px;
    line-height: 20px;
}
.form_header .form_title h2 img {
    margin-bottom: -2px;
}
.form_toolbar {
	background: none repeat scroll 0 0 #F4F8FA;
    padding: 8px;
    border-bottom: 1px solid #DDDDDD;
    text-align: right;
}
.form_operate {
    margin: 0 auto;
}
.form_operate button:hover{
	color: #ff7200;
}

.form_operate button {
    background: url("/wellpt-web/resources/theme/images/v1_icon.png") no-repeat scroll 0 -165px transparent;
    border: 1px solid #DEE1E2;
    color: #0F599C;
    cursor: pointer;
    font-family: "Microsoft YaHei";
    font-size: 12px;
    margin-left: 2px;
    padding: 0 3px;
}
.post-detail table {
    border-bottom: 1px solid #FFFFFF;
    border-collapse: collapse;
    border-top: 1px solid #FFFFFF;
}
.post-detail-tr {
    background: none repeat scroll 0 0 #F7F7F7;
}
.post-detail .Label {
    text-indent: 10px;
    width: 50px;
}
.odd.post-detail-tr  td {
    padding: 8px 0;
}
.fjiteam {
    float: left;
    margin: 0 5px;
}
.fjiteam a{
    color:#0F599C;
    text-decoration: none;
}
.fjiteam a:hover{
	color: #ff7200;
}
.entryContent {
    padding: 30px;
    border-top: 1px solid #DDDDDD;
}
.mail_resource {
    background: none repeat scroll 0 0 #F7F7F7;
    padding: 10px;
}
.mail_resource_item {
    margin-top: 2px;
}
.form_header .form_close {
    background: url("${ctx}/resources/theme/images/v1_close_btn.png") no-repeat scroll 0 0 transparent;
    cursor: pointer;
    float: right;
    height: 32px;
    margin: 4px;
    width: 32px;
}
.form_header .form_close:hover{
	background-color: #0d589b;
}
</style>
</head>
<body style="width: 100%; height: 100%; padding: 0px; margin: 0px;" onload="mailView2Load();">
<div class="mail_css_bg">
<div class="mail_css">
<input type="hidden" id="listView" name="listView" value="${listView}"/>
<input type="hidden" id="allUuid" name="allUuid" value="${allUuid}"/>
<input type="hidden" id="nextUuid" name="nextUuid" value="${nextUuid}"/>
<input type="hidden"  value="${rel }" name="rel" id="rel"/>
<input type="hidden"  value="${mailname }" name="mailname" id="mailname"/>
<input type="hidden"  value="${mail.sendStatus }" name="sendStatus" id="sendStatus"/>
<center><label  id="msg" style="color: red;">${msg }</label></center>
<div class="form_header">
		<!--标题-->
		<div class="form_title">
			<h2>
				${mail.subject }
				<c:if test="${mail.star=='1' }">
					<img id="star" onclick="setStar(this,'${mail.uuid}');" src="${ctx}/pt/mail/js/star-on.png" alt="<spring:message code="mail.label.xingbiao"/>" />
				</c:if>
				<c:if test="${mail.star!='1' }">
					<img id="star" onclick="setStar(this,'${mail.uuid}');" src="${ctx}/pt/mail/js/star-off.png" alt="<spring:message code="mail.label.xingbiao"/>" />
				</c:if>
			</h2>
			<div onclick="returnWindow();window.close();" title="关闭" class="form_close"></div>
		</div>
		<div id="toolbar" class="form_toolbar">
			<div class="form_operate">
				<c:if test="${rel!='3' }">
<%-- 				<button onclick="moveInMailBoxCms('${rel }','${pageNo}','${mtype}','${mail.uuid }','3','${mailname }','${ctype }')"><spring:message code="mail.btn.delete"/></button> --%>
				<button onclick="moveInMailBoxCms('${mail.uuid }','3')"><spring:message code="mail.btn.delete"/></button>
				</c:if>
				
				<button  onclick="deleInMailBoxCms('${mail.uuid }')"><spring:message code="mail.btn.cdelete"/></button>
<%-- 				<button  onclick="deleInMailBox('${rel }','${pageNo}','${mtype}','${mail.uuid }','${mailname }','${ctype }')"><spring:message code="mail.btn.cdelete"/></button> --%>
				<c:if test="${rel=='0'||rel=='6' }">
				<button onclick="window.location.href='${ctx}/mail/toReplyMail.action?rel=${rel }&pageNo=${pageNo }&mtype=${mtype }&uuid=${mail.uuid }&rtype=0&mailname=${mailname}';"><spring:message code="mail.btn.reply"/></button>
				</c:if>
				<c:if test="${rel=='0' }">
					<button onclick="window.location.href='${ctx}/mail/toReplyMail.action?rel=${rel }&pageNo=${pageNo }&mtype=${mtype }&uuid=${mail.uuid }&rtype=1&mailname=${mailname}';"><spring:message code="mail.btn.replyAll"/></button>
				</c:if>
				<c:if test="${mtype=='9' }">
				<button  onclick="toSendOther2InMailBox('${rel }','${pageNo}','${mtype}','${mail.uuid }','${mailname }');" ><spring:message code="mail.btn.reEdit"/></button>
				</c:if>
				<c:if test="${rel=='1' }">
				<button style="display: none" onclick="toSendOther2InMailBox('${rel }','${pageNo}','${mtype}','${mail.uuid }','${mailname }');" ><spring:message code="mail.btn.reEdit"/></button>
				<button id="che1" onclick="chehuiMail('${mail.createTime }','${mail.uuid }','${mail.to }','${mail.cc }','${mail.bcc }','${mailconfig }','${rel }','${pageNo}','${mtype}','${mailname }');" ><spring:message code="mail.btn.chehui"/></button>
				<button  onclick="toSendBCInMailBox('${rel }','${pageNo}','${mtype}','${mail.uuid }','${mailname }');" ><spring:message code="mail.btn.bcsend"/></button>
				</c:if>
				<button onclick="toSendOtherInMailBox('${rel }','${pageNo}','${mtype}','${mail.uuid }','${mailname }');" ><spring:message code="mail.btn.sendother"/></button>
<%-- 				<button id="btn_close" onclick="window.opener=null;window.close();" type="button"><spring:message code="mail.btn.close"/></button> --%>
			</div>
		</div>
	</div>

<div class="form_content">
		 <!-- 动态表单 -->
		<div id="dyform"><div class="dytable_form">
			<div class="post-sign">
				<div class="post-detail">
					<table edittype="1" id="001" tabletype="1" width="100%">
						<tbody>
							<tr class="odd post-detail-tr">  
					        	<td class="Label"><label><spring:message code="mail.label.tfrom"/>:</label></td>
					        	<td ><c:out value="${mail.from}" /></td>
					        </tr>
					        <tr class="post-detail-tr">
					        	<td class="Label"><label><spring:message code="mail.label.date"/>:</label></td>
					        	<td><c:out value="${mail.sendDate}" /></td>
					        </tr>
					        <tr class="odd post-detail-tr">
					        	<td class="Label"><label><spring:message code="mail.label.to"/>:</label></td>
					        	<td><c:out value="${mail.to}" /></td>
					        </tr>
					        <c:if test="${mail.cc!='' }">
						        <tr class="post-detail-tr">
									<td class="Label"><label><spring:message code="mail.label.cc"/>:</label></td>
						        	<td><c:out value="${mail.cc}" /> </td>
						        </tr>  
					        </c:if>
					        <c:if test="${mail.bcc!=''&&mail.bcc!=';'&&mail.bcc!=null }">
						        <tr class="post-detail-tr odd">  
									<td class="Label"><label><spring:message code="mail.label.bcc"/>:</label></td>
						        	<td><c:out value="${mail.bcc}" /></td>
						        </tr>
					        </c:if>
					        <c:if test="${mail.attachment!=''&&mail.attachment!=null }">
						        <tr class="post-detail-tr">  
									<td class="Label"><label><spring:message code="mail.label.file"/>:</label></td>
						        	<td>
						        	<div ><div class="fjiteam">${mail.fileNum }个</div><c:forEach items="${mail.attachmentParts}" var="stat">
							                <div class="fjiteam">
							                	<a href="${ctx}/mail/downLoad_file.action?fileMid=${stat.filemid}&filename=${stat.filename }"><c:out value="${stat.filename }"/></a><!-- (<c:out value="${stat.length }"/>) -->
<%-- 							                	<a href="${ctx}/mail/downLoad_file.action?fileMid=${stat.filemid}&filename=${stat.filename }"> --%>
<%-- 													<spring:message code="mail.btn.download"/> --%>
<!-- 												</a> -->
							                </div>
						        		</c:forEach> </div>
						        	</td>
						        </tr>
					         </c:if>
					         <c:if test="${mail.dingshi=='1' }">
						         <tr class="post-detail-tr odd">
						        	 <td  class="Label" colspan="2" style="color: gray;"><spring:message code="mail.label.dingshi1"/><span id="showsdate"> ${mail.nymdhs }</span> <spring:message code="mail.label.dingshi2"/><a style="display: none;" href="#" onclick="openSdLayer('${mail.uuid}','addFolder','test_con33');">修改时间</a></td>
						         </tr>
					         </c:if>
					       
					        
<!-- 					        <tr class="post-detail-tr content_text"> -->
<%-- 					        <td class="Label" valign="top"><spring:message code="mail.label.text"/>:</td> --%>
<!-- 					        	<td> -->
<!-- 					        		<div  style="OVERFLOW-Y: auto; OVERFLOW-X:hidden;height:auto;" class="entryContent"> -->
<%-- 					        			${mail.text} --%>
<!-- 					        		</div> -->
<!-- 					        	</td> -->
<!-- 					        </tr>   -->
						</tbody>
					</table>
					<div  style="OVERFLOW-Y: auto; OVERFLOW-X:hidden;height:auto;" class="entryContent">
					        			${mail.text}
					        		</div></div>
				</div>
			</div>
		</div>
	</div>

<div class="mail_content"></div>
<div id="addFolder"></div>
<div class="mail_form">
   <div class="post-sign">	
	<div class="post-detail">
<table width="100%" >  
    </table>
</div></div></div>
<div class="qmpanel_shadow" style="display:none; position:absolute;background-color:#fff">
	<div class="menu_base">
    	<div class="menu_bd bd">
        	<div class="menu_item" onmouseover="setSelThis(this);" onmouseout="setSelThis2(this);" style="cursor: pointer;" onclick="readInMailBox('${rel }','2','${pageNo}','${mtype}','${mail.uuid }','${mailname }','${ctype }');"><spring:message code="mail.label.hasReadMail"/></div>
            <div class="menu_item" onmouseover="setSelThis(this);" onmouseout="setSelThis2(this);" style="cursor: pointer;" onclick="readInMailBox('${rel }','3','${pageNo}','${mtype}','${mail.uuid }','${mailname }','${ctype }');"><spring:message code="mail.label.noReadMail"/></div>
        	<div class="menu_item_nofun"><div style="background:#ccc; padding-top:1px; margin-top: 5px;"></div></div>
            <div class="menu_item" onmouseover="setSelThis(this);" onmouseout="setSelThis2(this);" style="cursor: pointer;" onclick="readInMailBox('${rel }','4','${pageNo}','${mtype}','${mail.uuid }','${mailname }','${ctype }');"><spring:message code="mail.label.xingbiao"/></div>
            <div class="menu_item" onmouseover="setSelThis(this);" onmouseout="setSelThis2(this);" style="cursor: pointer;" onclick="readInMailBox('${rel }','5','${pageNo}','${mtype}','${mail.uuid }','${mailname }','${ctype }');"><spring:message code="mail.label.noxingbiao"/></div>
            <c:forEach items="${labelList}" var="mf2">
            <c:if test="${mf2.uuid!=ctype&&mf2.fname!=mailname }">
            <div class="menu_item" onmouseover="setSelThis(this);" onmouseout="setSelThis2(this);" style="cursor: pointer;" onclick="setLabel('${mf2.uuid }','1');">${mf2.fname}</div>
            </c:if>
            </c:forEach>
            <div class="menu_item_nofun"><div style="background:#ccc; padding-top:1px; margin-top: 5px;"></div></div>
            <div  onmouseover="setSelThis(this,'${row.uuid}');" onmouseout="setSelThis2(this);" style="cursor: pointer;" class="menu_item" onclick="openMailListLabelIn('${rel}','${mypage.pageNo}','${mtype}','${mailname }','${mail.uuid }','1');"><spring:message code="mail.label.addLabel"/></div>
        </div>
    </div>
</div>
<div class="qmpanel_shadow2" style="display:none; position:absolute;">
	<div class="menu_base">
    	<div class="menu_bd bd">
    		<c:if test="${rel!='0'||mail.status!='0' }" >
        	<div class="menu_item" onmouseover="setSelThis(this);" onmouseout="setSelThis2(this);"  style="cursor: pointer;" onclick="moveInMailBox('${rel }','${pageNo}','${mtype }','${mail.uuid }','0','${mailname }','${ctype }');"><spring:message code="mail.label.receiveMailBox"/></div>
        	</c:if>
        	<c:if test="${rel!='4'||mail.status!='4' }" >
            <div class="menu_item" onmouseover="setSelThis(this);" onmouseout="setSelThis2(this);" style="cursor: pointer;" onclick="moveInMailBox('${rel }','${pageNo}','${mtype }','${mail.uuid }','4','${mailname }','${ctype }');"><spring:message code="mail.label.groupBox"/></div>
             </c:if>
        	<c:if test="${rel!='1'||mail.status!='1' }" >
            <div class="menu_item" onmouseover="setSelThis(this);" onmouseout="setSelThis2(this);" style="cursor: pointer;" onclick="moveInMailBox('${rel }','${pageNo}','${mtype }','${mail.uuid }','1','${mailname }','${ctype }');"><spring:message code="mail.label.hasSendBox"/> </div>
            </c:if>
            <c:if test="${rel!='2'||mail.status!='2' }" >
            <div class="menu_item" onmouseover="setSelThis(this);" onmouseout="setSelThis2(this);" style="cursor: pointer;" onclick="moveInMailBox('${rel }','${pageNo}','${mtype }','${mail.uuid }','2','${mailname }','${ctype }');"><spring:message code="mail.label.caogaoBox"/></div>
             </c:if>
            <c:if test="${rel!='3'||mail.status!='3' }" >
            <div class="menu_item" onmouseover="setSelThis(this);" onmouseout="setSelThis2(this);" style="cursor: pointer;" onclick="moveInMailBox('${rel }','${pageNo}','${mtype }','${mail.uuid }','3','${mailname }','${ctype }');"><spring:message code="mail.label.deleteBox"/></div>
             </c:if>
            <c:forEach items="${myfolders}" var="mf">
            <c:if test="${mf.uuid!=ctype&&mf.fname!=mailname }">
            <div class="menu_item" onmouseover="setSelThis(this);" onmouseout="setSelThis2(this);" style="cursor: pointer;" onclick="moveInMailBox('${rel }','${pageNo}','${mtype }','${mail.uuid }','${mf.uuid }','${mailname }','${ctype }');">${mf.fname}</div>
            </c:if>
            </c:forEach>
            <div class="menu_item_nofun"><div style="background:#ccc; padding-top:1px; margin-top: 5px;"></div></div>
            <div  onmouseover="setSelThis(this,'${row.uuid}');" onmouseout="setSelThis2(this);" style="cursor: pointer;" class="menu_item" onclick="openMailListFolderIn('${rel}','${mypage.pageNo}','${mtype}','${mailname }','${mail.uuid }','1');"><spring:message code="mail.label.addFolder"/></div>
        </div>
    </div>
</div>
</div>
</div>
</div>
<div class="body_foot"></div>
</body>

</html>