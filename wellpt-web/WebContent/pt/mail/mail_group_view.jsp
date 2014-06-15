<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${mail.subject }</title>
<%@ include file="/pt/common/meta.jsp"%>
<script src="${ctx}/resources/jquery/jquery.js"></script>
	<!-- jQuery UI -->
	<script src="${ctx}/resources/jqueryui/js/jquery-ui.js"></script>
<script type="text/javascript" src="${ctx}/resources/ckeditor/ckeditor.js"></script>
<script src="${ctx}/pt/mail/js/jquery-powerFloat-min.js" type="text/javascript"></script>
<link href="${ctx}/resources/theme/css/wellnewoa.css" rel="stylesheet" />
<link href="${ctx}/pt/mail/js/powerFloat.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/resources/pt/mail/global_zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
<script src="${ctx}/pt/mail/js/mail.js" type="text/javascript"></script>
<script src="${ctx}/resources/pt/js/common/jquery.alerts.js" type="text/javascript"></script>
<script type="text/javascript"
		src="${ctx}/resources/pt/js/common/jquery.cmsWindown.js"></script>
<script type="text/javascript" src="${ctx}/resources/timers/jquery.timers.min.js"></script>
<script  type="text/javascript">
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
});



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
table {
    border-collapse: collapse;
    width:100%;
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
.form_toolbar button {
    background: url("/wellpt-web/resources/theme/images/v1_icon.png") no-repeat scroll 0 -165px transparent;
    border: 1px solid #DEE1E2;
    color: #0F599C;
    cursor: pointer;
    font-family: "Microsoft YaHei";
    font-size: 12px;
    margin-left: 2px;
    padding: 0 3px;
}
.form_toolbar button:hover{
	color: #ff7200;
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
.post-detail-tr {
    background: none repeat scroll 0 0 #F7F7F7;
}
.post-detail-tr .left_td {
    text-indent: 10px;
    width: 50px;
}
.post-detail-tr  .post-detail-tr-td {
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
#frmTitle #topLink {
margin-bottom: 5px;
    color: #0F599C;
}
#frmTitle #topLink:hover {
    color: #ff7200;
}
.showlogTable {
    display: block;
    margin: 0 auto;
    width: 94%;
}
.showlog_title_tr {
    background: none repeat scroll 0 0 #0F599C;
    border: 1px solid #0F599C;
    color: #FFFFFF;
    font-size: 12px;
}
.showlog_title_tr td {
    padding: 8px;
}
.showlog_content_tr td {
    border: 1px solid;
    padding: 8px;
}
.showlog_content_tr a {
    color: #0F599C;
    text-decoration: none;
}

.showlog_content_tr a:hover {
    color: #ff7200;
}

.showlog_content_tr div {
    margin: 3px 0;
}
.form_boot{
	margin-top: 10px;
}
</style>
</head>
<body>
<div class="mail_css_bg">
<div class="mail_css">
<input type="hidden" id="listView" name="listView" value="${listView}"/>
<input type="hidden" id="allUuid" name="allUuid" value="${allUuid}"/>
<input type="hidden" id="nextUuid" name="nextUuid" value="${nextUuid}"/>
<input type="hidden"  value="${rel }" name="rel" id="rel"/>
	<div class="form_header">
	<div class="form_title">
			<h2>
				${mail.subject }
				<c:if test="${mail.star=='1' }">
			<img id="star" onclick="setStar(this,'${mail.uuid}');" src="${ctx}/pt/mail/js/star-on.png" alt="星标邮件" />
			</c:if>
			<c:if test="${mail.star!='1' }">
			<img id="star" onclick="setStar(this,'${mail.uuid}');" src="${ctx}/pt/mail/js/star-off.png" alt="星标邮件" />
			</c:if>
			
			</h2>
			<div onclick="window.close();" title="关闭" class="form_close"></div>
		</div>
	<div id="toolbar" class="form_toolbar">
		<button style="display: none;" onclick="window.location.href='${ctx}/mail/mail_box_list.action?rel=4&pageNo=${pageNo}&mtype=${mtype}&mailname=0';return false;"><spring:message code="mail.btn.return"/></button>
		<%-- <button onclick="history.go(-1);return false;"><spring:message code="mail.btn.return"/></button> --%>
		<button onclick="toReplyGInMailBox('${rel }','${pageNo}','${mtype}','5','${mail.uuid }');" ><spring:message code="mail.btn.reply"/></button>
		<button onclick="toSendOtherGInMailBox('${rel }','${pageNo}','${mtype}','4','${mail.uuid }');" ><spring:message code="mail.btn.sendother"/></button>
		<button onclick="toSendOtherGInMailBox('${rel }','${pageNo}','${mtype}','3','${mail.uuid }');" ><spring:message code="mail.btn.newMailSend"/></button>
		<button onclick="moveInMailBoxCms('${mail.uuid }','3')"><spring:message code="mail.btn.delete"/></button>
		<button  onclick="deleInMailBoxCms('${mail.uuid }')"><spring:message code="mail.btn.cdelete"/></button>
<%-- 		<button onclick="moveInMailBox('4','${pageNo}','${mtype}','${mail.uuid }','0')"><spring:message code="mail.btn.delete"/></button> --%>
<%-- 		<button onclick="deleInMailBox('4','${pageNo}','${mtype}','${mail.uuid }','0')"><spring:message code="mail.btn.cdelete"/></button> --%>
		
<!--		<button onclick="readInMailBox('2','${pageNo}','${mtype}','${mail.uuid }');">标记为已读</button>-->
<!--		<button onclick="readInMailBox('3','${pageNo}','${mtype}','${mail.uuid }');">标记为未读</button>-->
		
		
	</div>
	
	</div>
	
	<div style="display:none;float:left; position:relative; margin:0 0 0 0; width:600px; height:30px;" >
<div style=" margin: 2px 0pt 0pt 2px;">
<div id="markSelectBox"
	style="border-width: 1px 2px 2px 1px; border: 1px solid #DCE0E1; cursor: pointer; width: 100px; padding: 1px 1px 1px 2px; background: white; float: left;"
	class="bd">
<div
	style="width: 16px; height: 18px; overflow: hidden; text-align: center; float: right;"
	class="attbg"><img align="middle" style="margin: 3px 0pt 0pt;"
	src="http://rescdn.qqmail.com/zh_CN/htmledition/images/webqqshow_on.gif" />
</div>
<div
	style="padding-left: 3px ! important; line-height: 16px; height: 18px;"
	class="txtflow" id="markSelectTo"><spring:message code="mail.label.setTo"/></div>
</div>
</div>
</div>
<div class="form_content">
	<form name="form" action="${ctx}/mail/replyGroupMail.action"  method="post">
<table >
  
  
   <tr >
    <td id="frmTitle">
	<table>  
<!-- 		<tr> -->
<%--         	<td width="100%" colspan="2"><b><c:out value="${mail.subject }" /></b> --%>
<%--         	<c:if test="${mail.star=='1' }"> --%>
<%-- 			<img id="star" onclick="setStar(this,'${mail.uuid}');" src="${ctx}/pt/mail/js/star-on.png" alt="星标邮件" /> --%>
<%-- 			</c:if> --%>
<%-- 			<c:if test="${mail.star!='1' }"> --%>
<%-- 			<img id="star" onclick="setStar(this,'${mail.uuid}');" src="${ctx}/pt/mail/js/star-off.png" alt="星标邮件" /> --%>
<%-- 			</c:if> --%>
<!--         	</td> -->
<!--         </tr> -->
		<tr class="post-detail-tr">  
        	<td class="post-detail-tr-td left_td"><spring:message code="mail.label.tfrom"/>：</td>
        	<td  class="post-detail-tr-td right_td"><c:out value="${mail.from}" /></td>
        </tr>
		<tr class="post-detail-tr">  
        	<td  class="post-detail-tr-td left_td"><spring:message code="mail.label.to"/>：</td>
        	<td  class="post-detail-tr-td right_td"><c:out value="${to}" /></td>
        </tr>
        <tr class="post-detail-tr">
        	<td  class="post-detail-tr-td left_td"><spring:message code="mail.label.date"/>：</td>
        	<td  class="post-detail-tr-td right_td"><c:out value="${mail.sendDate}" /></td>
        </tr>
        
        <c:if test="${cc!=''&&cc!=null }">
        <tr class="post-detail-tr" id="mcc" >
			<td  class="post-detail-tr-td left_td"><spring:message code="mail.label.cc"/>：</td>
        	<td  class="post-detail-tr-td right_td"><c:out value="${cc}"/> </td>
        </tr>  
        </c:if>
        
        <c:if test="${bcc!=''&&bcc!=null }">
        <tr class="post-detail-tr" id="mcc" >
			<td  class="post-detail-tr-td left_td"><spring:message code="mail.label.bcc"/>：</td>
        	<td  class="post-detail-tr-td right_td"><c:out value="${bcc}"/> </td>
        </tr>  
        </c:if>
        <tr class="post-detail-tr" style="display: none">
        <td  class="post-detail-tr-td left_td"></td>
        <td id="addCc"><a href="#" onclick="addCc();"><spring:message code="mail.label.addcc"/></a>-</td><td style="display: none" id="delCc" ><a href="#" onclick="delCc();"><spring:message code="mail.label.delcc"/></a></td>
       </tr>
       <c:if test="${mail.attachment!=''&&mail.attachment!=null }">
        <tr class="post-detail-tr">  
			<td  class="post-detail-tr-td left_td"><spring:message code="mail.label.file"/>：</td>
        	<td  class="post-detail-tr-td right_td">
        	<div ><div  class="fjiteam">${mail.fileNum }个 </div><c:forEach items="${mail.attachmentParts}" var="stat">
							                <div  class="fjiteam">
							                	<a href="${ctx}/mail/downLoad_file.action?fileMid=${stat.filemid}&filename=${stat.filename }"><c:out value="${stat.filename }"/></a>
							                	<a href="${ctx}/mail/downLoad_file.action?fileMid=${stat.filemid}&filename=${stat.filename }">
													<spring:message code="mail.btn.download"/>
												</a>
							                </div>
						        		</c:forEach> </div>
<!--        		<div>-->
<!--        		<c:forEach items="${mail.attachmentParts}" var="stat">-->
<!--	                <div>-->
<!--	                	<a href="${ctx}/mail/downLoad_file.action?fileMid=${stat.filemid}&filename=${stat.filename }"><c:out value="${stat.filename }"/></a>&nbsp;(<c:out value="${stat.length }"/>)&nbsp;-->
<!--	                	<a href="${ctx}/mail/downLoad_file.action?fileMid=${stat.filemid}&filename=${stat.filename }">-->
<!--							<spring:message code="mail.btn.download"/>-->
<!--						</a>-->
<!--	                </div>-->
<!--        		</c:forEach>-->
<!--        		</div>-->
        	</td>
        </tr>
        </c:if>
         <tr>
<%--         	<td align="right" valign="top"><spring:message code="mail.label.text"/>：</td> --%>
        	<td colspan="2"><div  style="OVERFLOW-Y: auto; OVERFLOW-X:hidden;height:auto;" class="entryContent">
					        			${mail.text}
					        		</div></td>
        </tr>
<!--         <tr style="display: none;">  -->
<!--        	<td valign="top" align="right">回复：</td>-->
<!--        	<td align="left">-->
<!--        	-->
<!--        	<input type="hidden" name="mid" value="${mail.mid }"/>-->
<!--        	<input type="hidden" name="subject" value="${mail.subject }"/>-->
<!--        	<input type="hidden" name="groupName" value="${mail.groupName }"/>-->
<!--        	<input type="hidden" name="to" value="${to }"/>-->
<!--        	<input type="hidden" name="cc" value="${cc }"/>-->
<!--        	<input type="hidden" name="bcc" value="${bcc }"/>-->
<!--        	<input type="hidden" name="from" value="${from }"/>-->
<!--        	<textarea  id="text" name="text" rows="10" cols="50" ></textarea><button id="fasong" type="submit" onclick="return checkG('1');;">发表</button>-->
<!--        	-->
<!--        	</td>-->
<!--        </tr>-->
        

        
        <tr>
        <td  colspan="2">
        <table class="showlogTable">
        <thead>
       <tr>  
        	<td colspan="4" >
        	<div id="topLink"><span style="cursor: pointer;" onclick="switchLogBar()"   id=switchPoint title=关闭/打开>
        	<spring:message code="mail.label.genzonglog"/>
        	</span></div>
        	</td>
        </tr></thead>
        <tbody id="showlog" style="display:none;">
        <tr class="showlog_title_tr">
        <td width="15%"><spring:message code="mail.label.date"/></td>
        <td width="25%"><spring:message code="mail.label.tfrom"/></td>
        <td width="40%"><spring:message code="mail.label.toPerson"/></td>
        <td width="20%"><spring:message code="mail.label.file"/></td>
        </tr>
        <c:if test="${fn:length(mailList)>0 }">
        <c:forEach items="${mailList}" var="st">
        <tr class="showlog_content_tr">  
        	<td valign="top">
        	<c:out value="${st.sendDate}" />
        	</td>
        	<td valign="top">
        	<c:out value="${st.from}" />
        	</td>
        	<td valign="top">
        	<c:out value="${st.to}" /><c:out value="${st.cc}" /><c:out value="${st.bcc}" />
        	</td>
        	<td valign="top">
        	<c:if test="${fn:length(st.attachmentParts)>0}"> 
        		<c:forEach items="${st.attachmentParts}" var="stat">
	                <div>
	                	<a href="${ctx}/mail/downLoad_file.action?fileMid=${stat.filemid}&filename=${stat.filename }"><c:out value="${stat.filename }"/></a>
<%-- 	                	<a href="${ctx}/mail/downLoad_file.action?fileMid=${stat.filemid}&filename=${stat.filename }"> --%>
<%-- 							<spring:message code="mail.btn.download"/> --%>
<!-- 						</a> -->
	                </div>
        		</c:forEach>
        		</c:if>
        	</td>
        </tr>
        </c:forEach>
        </c:if>
        
        <c:if test="${fn:length(mailList)<=0 }">
        <tr>  
        	<td valign="top">
        	<c:out value="${mail.sendDate}" />
        	</td>
        	<td valign="top">
        	<c:out value="${mail.from}" />
        	</td>
        	<td valign="top">
        	<c:out value="${mail.to}" /><c:out value="${mail.cc}" /><c:out value="${bcc}" />
        	</td>
        	<td valign="top">
        	<c:if test="${fn:length(mail.attachmentParts)>0}"> 
        	<c:forEach items="${mail.attachmentParts}" var="stat">
	                <div>
	                	<a href="${ctx}/mail/downLoad_file.action?fileMid=${stat.filemid}&filename=${stat.filename }"><c:out value="${stat.filename }"/></a>&nbsp;(<c:out value="${stat.length }"/>)&nbsp;
	                	<a href="${ctx}/mail/downLoad_file.action?fileMid=${stat.filemid}&filename=${stat.filename }">
							<spring:message code="mail.btn.download"/>
						</a>
	                </div>
        		</c:forEach>
        		</c:if>
        	</td>
        </tr>
        
        </c:if>
        </tbody>
        </table>
        </td>
        </tr>
        
        </table>
        
        </td>
   
       
       
       
<!--      <td rowspan="2" width="1%" valign="middle" scope="col" >-->
<!--		<div id="topLink" style="position:absolute;"><span  onclick="switchSysBar()"   id=switchPoint title=关闭/打开><img  src="${ctx}/pt/mail/js/right.gif" name="barImg" id="barImg"/></span></div>-->
<!--	</td>-->
<!--    <td id="rightf" valign="top" scope="col" id="rightf" style="display: none;">-->
<!--    <div style="height:500px;border:1px solid blue;OVERFLOW-Y: auto; OVERFLOW-X:hidden;" align="left">-->
<!--    <ul id="treeContactor" class="ztree" >-->
<!--    <c:forEach items="${hlist }" var="hrow">-->
<!--    <li title="${hrow.operation }">${hrow.operation }</li>-->
<!--    </c:forEach>-->
<!--    </ul>-->
<!--    </div>-->
<!--    </td>-->
  </tr>
  
</table>  
        
        
         
<!--        <tr>  -->
<!--            <td colspan="2"><input type="button" value="回复" onclick="reply()"></input><input type="button" value="回复全部" onclick="replyAll()"></input></td>  -->
<!--        </tr>  -->
    </form>
    </div>
   <div class="form_header form_boot">
	<div id="toolbar" class="form_toolbar">
		<button style="display: none;" onclick="window.location.href='${ctx}/mail/mail_box_list.action?rel=4&pageNo=${pageNo}&mtype=${mtype}&mailname=0';return false;"><spring:message code="mail.btn.return"/></button>
<%-- 		<button onclick="history.go(-1);return false;"><spring:message code="mail.btn.return"/></button> --%>
		<button onclick="toReplyGInMailBox('${rel }','${pageNo}','${mtype}','5','${mail.uuid }');" ><spring:message code="mail.btn.reply"/></button>
		<button onclick="toSendOtherGInMailBox('${rel }','${pageNo}','${mtype}','4','${mail.uuid }');" ><spring:message code="mail.btn.sendother"/></button>
		<button onclick="toSendOtherGInMailBox('${rel }','${pageNo}','${mtype}','3','${mail.uuid }');" ><spring:message code="mail.btn.newMailSend"/></button>
		<button onclick="moveInMailBoxCms('${mail.uuid }','3')"><spring:message code="mail.btn.delete"/></button>
		<button  onclick="deleInMailBoxCms('${mail.uuid }')"><spring:message code="mail.btn.cdelete"/></button>
<%-- 		<button onclick="moveInMailBox('4','${pageNo}','${mtype}','${mail.uuid }','0')"><spring:message code="mail.btn.delete"/></button> --%>
<%-- 		<button onclick="deleInMailBox('4','${pageNo}','${mtype}','${mail.uuid }','0')"><spring:message code="mail.btn.cdelete"/></button> --%>
		
<!--		<button onclick="readInMailBox('2','${pageNo}','${mtype}','${mail.uuid }');">标记为已读</button>-->
<!--		<button onclick="readInMailBox('3','${pageNo}','${mtype}','${mail.uuid }');">标记为未读</button>-->
		
		
	</div>
	
	</div>
	<div style="display:none;float:left; position:relative; margin:0 0 0 0; width:600px; height:30px;" >
<div style=" margin: 2px 0pt 0pt 2px;">
<div id="markSelectBox2"
	style="border-width: 1px 2px 2px 1px; border: 1px solid #DCE0E1; cursor: pointer; width: 100px; padding: 1px 1px 1px 2px; background: white; float: left;"
	class="bd">
<div
	style="width: 16px; height: 18px; overflow: hidden; text-align: center; float: right;"
	class="attbg"><img align="middle" style="margin: 3px 0pt 0pt;"
	src="http://rescdn.qqmail.com/zh_CN/htmledition/images/webqqshow_on.gif" />
</div>
<div
	style="padding-left: 3px ! important; line-height: 16px; height: 18px;"
	class="txtflow" id="markSelectTo2"><spring:message code="mail.label.setTo"/></div>
</div>
</div>
</div>
	
	
	<div class="qmpanel_shadow" style="display:none; position:absolute;background-color:#fff">
	<div class="menu_base">
    	<div class="menu_bd bd">
        	<div class="menu_item" onmouseover="setSelThis(this);" onmouseout="setSelThis2(this);" style="cursor: pointer;" onclick="readInMailBox('${rel }','2','${pageNo}','${mtype}','${mail.uuid }','${mailname }','${ctype }');"><spring:message code="mail.label.hasReadMail"/></div>
            <div class="menu_item" onmouseover="setSelThis(this);" onmouseout="setSelThis2(this);" style="cursor: pointer;" onclick="readInMailBox('${rel }','3','${pageNo}','${mtype}','${mail.uuid }','${mailname }','${ctype }');"><spring:message code="mail.label.noReadMail"/></div>
            <div class="menu_item_nofun"><div style="background:#ccc; padding-top:1px; margin-top: 5px;"></div></div>
            <div class="menu_item" onmouseover="setSelThis(this);" onmouseout="setSelThis2(this);" style="cursor: pointer;" onclick="readInMailBox('${rel }','4','${pageNo}','${mtype}','${mail.uuid }','${mailname }','${ctype }');"><spring:message code="mail.label.xingbiao"/></div>
            <div class="menu_item" onmouseover="setSelThis(this);" onmouseout="setSelThis2(this);" style="cursor: pointer;" onclick="readInMailBox('${rel }','5','${pageNo}','${mtype}','${mail.uuid }','${mailname }','${ctype }');"><spring:message code="mail.label.noxingbiao"/></div>
        </div>
    </div>
</div>
</div>
</div>
<div class="body_foot"></div>
</body>

</html>
