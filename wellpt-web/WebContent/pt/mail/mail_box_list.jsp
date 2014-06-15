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
<link href="${ctx}/pt/mail/js/powerFloat.css" type="text/css" rel="stylesheet" />
<style>


</style> 

<script src="${ctx}/resources/jquery/jquery.js"></script>
<!-- jQuery UI -->
<script src="${ctx}/resources/jqueryui/js/jquery-ui.js"></script>
<script src="${ctx}/resources/bootstrap/js/bootstrap.js"></script>
<script src="${ctx}/resources/bootstrap/js/bootstrap-dropdown.js"></script>
<script src="${ctx}/pt/mail/js/jquery-powerFloat-min.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/common/jquery.alerts.js"/>

<script src="${ctx}/resources/jqgrid/js/i18n/grid.locale-cn.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/mail/global_zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
<script src="${ctx}/pt/mail/js/mail.js" type="text/javascript"></script>
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








</script>
</head>
<body style="width: 100%; height: 100%; padding: 0px; margin: 0px;">
<center><label style="color: red;">${msg }</label></center>
<c:if test="${rel=='8' }">
<table>
<tr>

<c:forEach items="${myfolders }" var="mfs">
<c:if test="${mfs.status=='0' }">
<td>
<c:if test="${mfs.uuid==ctype }">
<span style="border:1px solid blue;cursor: pointer;"
		onclick="window.location.href='${ctx}/mail/mail_box_list.action?rel=${rel }&ctype=${mfs.uuid }&pageNo=${mypage.pageNo}&mtype=${mtype}';">${mfs.fname }</span>
</c:if>
<c:if test="${mfs.uuid!=ctype }">
<span style="cursor: pointer;"
		onclick="window.location.href='${ctx}/mail/mail_box_list.action?rel=${rel }&ctype=${mfs.uuid }&pageNo=${mypage.pageNo}&mtype=${mtype}';">${mfs.fname }</span>
</c:if>
</td>
</c:if>
</c:forEach>
</tr>
</table>
</c:if>

<div>(<spring:message code="mail.label.total1"/>${mypage.totalCount }<spring:message code="mail.label.total2"/>${mypage.noread }<spring:message code="mail.label.total3"/>)</div>

<input type="hidden" id="contextPath" value="${ctx}"></input>
<input type="hidden" id="rel" name="rel" value="${rel}"></input>
<div id="div1" style="float:left; position:relative; margin:0 0 0 0; height:30px;">
<c:if test="${rel=='6' }">
<button onclick="history.go(-1);"><spring:message code="mail.btn.return"/></button>
</c:if>
<c:if test="${rel!='3' }">
<button onclick="moveMailBox('${rel }','${mypage.pageNo}','${mtype}','3','${mailname }')"><spring:message code="mail.btn.delete"/></button>
</c:if>
<button onclick="deleMailBox('${rel }','${mypage.pageNo}','${mtype}','${mailname }')"><spring:message code="mail.btn.cdelete"/></button>
<c:if test="${rel!='4' }">
<button
	onclick="toSendOtherMailBox('1','${rel }','${mypage.pageNo}','${mtype}');"><spring:message code="mail.btn.sendother"/></button>
</c:if>
<c:if test="${rel=='4' }">
<button onclick="toSendOtherGMailBox('${rel }','${mypage.pageNo}','${mtype}','4');" ><spring:message code="mail.btn.sendother"/></button>
		<button onclick="toSendOtherGMailBox('${rel }','${mypage.pageNo}','${mtype}','3');" ><spring:message code="mail.btn.newMailSend"/></button>
</c:if>
<c:if test="${rel=='3' }">
<button
	onclick="clearMailBox('${rel }','${mypage.pageNo}','${mtype}','${mailname }');"><spring:message code="mail.btn.clear"/></button>
</c:if>
<button onclick="readMailBox('${rel }','0','${mypage.pageNo}','${mtype}');"><spring:message code="mail.label.setAllRead"/></button>
<button onclick="readMailBox('${rel }','1','${mypage.pageNo}','${mtype}');"><spring:message code="mail.label.setAllNoRead"/></button>
</div>

<div class="btn-group">  
  <button class="btn"><spring:message code="mail.label.setTo"/></button>  
  <button class="btn dropdown-toggle" data-toggle="dropdown">  
    <span class="caret"></span>  
  </button>  
  <ul class="dropdown-menu">  
   <li onmouseover="setSelThis(this);" onmouseout="setSelThis2(this);" style="cursor: pointer;" onclick="readMailBox('${rel }','2','${mypage.pageNo}','${mtype}');"><spring:message code="mail.label.hasReadMail"/></li>
            <li onmouseover="setSelThis(this);" onmouseout="setSelThis2(this);" style="cursor: pointer;" onclick="readMailBox('${rel }','4','${mypage.pageNo}','${mtype}');"><spring:message code="mail.label.xingbiao"/></li>
            <li onmouseover="setSelThis(this);" onmouseout="setSelThis2(this);" style="cursor: pointer; " onclick="readMailBox('${rel }','5','${mypage.pageNo}','${mtype}');"><spring:message code="mail.label.noxingbiao"/></li>
             <li >---------------------</li>
            <c:forEach items="${labelList}" var="mf2">
            <c:if test="${mf2.uuid!=ctype&&mf2.fname!=mailname }">
            <li onmouseover="setSelThis(this);" onmouseout="setSelThis3(this,'${mf2.color }');" style="cursor: pointer;background-color: ${mf2.color };color:white" onclick="setLabel('${mf2.uuid }','${mf2.fname}','${mf2.color}','1');"><c:out value="${mf2.fname}"/></li>
            </c:if>
            </c:forEach>
             <li >---------------------</li>
            <li  onmouseover="setSelThis(this,'${row.uuid}');" onmouseout="setSelThis2(this);" style="cursor: pointer;" onclick="openMailListLabel('${rel}','${mypage.pageNo}','${mtype}','${mailname }');"><spring:message code="mail.label.addLabel"/></li>
  </ul>  
</div>  

<div class="btn-group">  
  <button class="btn"><spring:message code="mail.label.moveTo"/></button>  
  <button class="btn dropdown-toggle" data-toggle="dropdown">  
    <span class="caret"></span>  
  </button>  
  <ul class="dropdown-menu">  
   			<c:if test="${rel!='0'&&inbox!='1' }" >
        	<li onmouseover="setSelThis(this);" onmouseout="setSelThis2(this);"  style="cursor: pointer;" onclick="moveMailBox('${rel }','${mypage.pageNo}','${mtype }','0','${mailname }');"><spring:message code="mail.label.receiveMailBox"/></li>
        	</c:if>
        	<c:if test="${rel!='4'&&group!='1' }" >
            <li onmouseover="setSelThis(this);" onmouseout="setSelThis2(this);" style="cursor: pointer;" onclick="moveMailBox('${rel }','${mypage.pageNo}','${mtype }','4','${mailname }');"><spring:message code="mail.label.groupBox"/></li>
             </c:if>
        	<c:if test="${rel!='1'&&fasong!='1' }" >
            <li  onmouseover="setSelThis(this);" onmouseout="setSelThis2(this);" style="cursor: pointer;" onclick="moveMailBox('${rel }','${mypage.pageNo}','${mtype }','1','${mailname }');"><spring:message code="mail.label.hasSendBox"/></li>
            </c:if>
            <c:if test="${rel!='2'&&caogao!='1' }" >
            <li  onmouseover="setSelThis(this);" onmouseout="setSelThis2(this);" style="cursor: pointer;" onclick="moveMailBox('${rel }','${mypage.pageNo}','${mtype }','2','${mailname }');"><spring:message code="mail.label.caogaoBox"/></li>
             </c:if>
            <c:if test="${rel!='3'&&del!='1' }" >
            <li  onmouseover="setSelThis(this);" onmouseout="setSelThis2(this);" style="cursor: pointer;" onclick="moveMailBox('${rel }','${mypage.pageNo}','${mtype }','3','${mailname }');"><spring:message code="mail.label.deleteBox"/></li>
             </c:if>
              <li >---------------------</li>
            <c:forEach items="${myfolders}" var="mf">
            <c:if test="${mf.uuid!=ctype&&mf.fname!=mailname }">
            <li onmouseover="setSelThis(this);" onmouseout="setSelThis2(this);" style="cursor: pointer;" onclick="moveMailBox('${rel}','${mypage.pageNo}','${mtype }','${mf.uuid }','${mailname }');">${mf.fname}</li>
            </c:if>
            </c:forEach>
            <c:forEach items="${otherList}" var="mo">
            <c:if test="${mo.uuid!=ctype&&mo.username!=mailname }">
            <li onmouseover="setSelThis(this);" onmouseout="setSelThis2(this);" style="cursor: pointer;" onclick="moveMailBox2('${rel}','${mypage.pageNo}','${mtype }','6','${mailname }','${mo.username }');">${mo.username}</li>
            </c:if>
            </c:forEach>
             <li >---------------------</li>
            <li  onmouseover="setSelThis(this,'${row.uuid}');" onmouseout="setSelThis2(this);" style="cursor: pointer;"  onclick="openMailListFolder('${rel}','${mypage.pageNo}','${mtype}','${mailname }');"><spring:message code="mail.label.addFolder"/></li>
  </ul>  
</div> 








<!--<button onclick="readMailBox('2','2','${mypage.pageNo}','${mtype}');">标记为已读</button>-->
<!--<button onclick="readMailBox('2','3','${mypage.pageNo}','${mtype}');">标记为未读</button>-->
<div id="div4" style="float:right; position:relative; margin:0 0 0 0;width:200px;  height:30px;">
<c:out value="${mypage.pageNo }"></c:out>/<c:out
	value="${mypage.totalPage }"></c:out><spring:message code="mail.label.page"/> <c:if
	test="${mypage.pageNo<=mypage.totalPage&&mypage.pageNo>1 }">
	<a
		href="${ctx}/mail/mail_box_list.action?rel=${rel }&pageNo=${mypage.pageNo-1}&mtype=${mtype}&mailname=${mailname }"><spring:message code="mail.label.prePage"/></a>
</c:if> <c:if test="${mypage.pageNo<mypage.totalPage }">
	<a
		href="${ctx}/mail/mail_box_list.action?rel=${rel }&pageNo=${mypage.pageNo+1}&mtype=${mtype}&mailname=${mailname }"><spring:message code="mail.label.nextPage"/></a>
</c:if>
<c:if test="${mypage.totalPage>1 }">
	<a
		href="#" onclick="openPage();"><spring:message code="mail.label.go"/></a>
		<div id="divpage" style="display: none">
		<spring:message code="mail.label.goto"/><select id="toPage"><c:forEach begin="1" end="${ mypage.totalPage}" step="1" var="prow">
		<c:if test="${prow==mypage.pageNo }">
		<option value="${prow }" selected="selected">${prow }</option>
		</c:if>
		<c:if test="${prow!=mypage.pageNo }">
		<option value="${prow }">${prow }</option>
		</c:if>
		</c:forEach></select><spring:message code="mail.label.page"/>
		<button onclick="toPage('${rel }','${mtype}','${mailname }');"><spring:message code="mail.btn.confirm"/></button>
		</div>
</c:if>

</div>



<div id="addFolder"></div>

<table width="100%">

	<tr>
		<td align="left" width="5%"><input type="checkbox" id="mycheckbox"
			onclick="selectall();" /></td>
		<td align="left" width="7%"><spring:message code="mail.label.hasOrNoRead"/></td>
		<td align="left" width="20%">
		 <c:if test="${rel!='6' }">
		<spring:message code="mail.label.toPerson"/>
		</c:if>
		 <c:if test="${rel=='6' }">
		<spring:message code="mail.label.from"/>
		</c:if>
		</td>
		<td align="left" width="32%"><spring:message code="mail.label.title"/></td>
		<td align="left" width="15%"><spring:message code="mail.label.mlabel"/></td>
		<td align="left" width="15%"><a href="#" onclick="sort('${sort }','${rel }','${mypage.pageNo}','${mtype}','${mailname }');"><spring:message code="mail.label.date"/>
		<c:if test="${sort=='desc' }">
		<img style="cursor: pointer;border: 0;"  src="${ctx }/pt/mail/js/icon-sort-desc.gif"></img>
		</c:if>
		<c:if test="${sort!='desc' }">
		<img style="cursor: pointer;border: 0;" src="${ctx }/pt/mail/js/icon-sort-asc.gif"></img>
		</c:if>
		</a>
		</td>
		<td align="left" width="5%"></td>
	</tr>
	<!--			<tr>-->
	<!--			<td align="center" colspan="4">(系统将自动清理来信时间在30天前的已删除邮件)</td>-->
	<!--			</tr>-->

	<c:forEach items="${mypage.result }" var="row">

		<tr>
			<td><input type="checkbox" class="subcheckbox"
				name="subcheckbox" value="${row.uuid }" /></td>
			<td><c:if test="${row.isRead==0}"><spring:message code="mail.label.noRead"/></c:if><c:if
				test="${row.isRead==1}"><spring:message code="mail.label.hasRead"/></c:if></td>
				 <c:if test="${rel!='6' }">
		<td title="${row.to }">
		<c:choose>
				<c:when test="${fn:length(row.to)>20 }">
					<c:out value="${fn:substring(row.to,0,20) }..." />
				</c:when>
				<c:otherwise>
					<c:out value="${row.to }" />
				</c:otherwise>
			</c:choose>
			</td>
			</c:if>
		<c:if test="${rel=='6' }">
		<td title="${row.from }">
		<c:out value="${row.from}" />
		</td>
		</c:if>
		
			
			<td>
			<c:if test="${(rel!='4'&&rel!='2'&&rel!='6')||(rel=='2'&&row.dingshi=='1') }">
			<c:if test="${row.groupName!=null&&row.groupName!=''&&rel!='1' }">
			<a href="${ctx}/mail/view_group_mail.action?uuid=${row.uuid }&rel=${rel }&pageNo=${mypage.pageNo}&mtype=${mtype}&allUuid=${row.allUuid}">
			<font color="${row.scolor }">
			
			<c:choose>
				<c:when test="${row.subLength>28 }">
					<c:out value="${fn:substring(row.subject,0,28) }..." />
					
				</c:when>
				<c:otherwise>
					<c:out value="${row.subject }" />
					<c:if test="${row.jtext!=''&&row.jtext!=null }">-</c:if>
				<font color="gray">
				<c:if test="${fn:length(row.jtext)>(28-row.subLength) }">
				<c:out value="${fn:substring(row.jtext,0,28-row.subLength) }..." />
				</c:if>
				<c:if test="${fn:length(row.jtext)<=(28-row.subLength) }">
				<c:out value="${fn:substring(row.jtext,0,28-row.subLength) }" />
				</c:if>
				
				</font>
				</c:otherwise>
			</c:choose> 
			</font>
			</a>
			</c:if>
			<c:if test="${row.groupName==''||row.groupName==null||rel=='1' }">
			<a href="${ctx}/mail/mailbox_view2.action?rel=${rel }&mailname=${mailname }&uuid=${row.uuid}&pageNo=${mypage.pageNo}&mtype=${mtype }&allUuid=${row.allUuid}">
			<font color="${row.scolor }">
			<c:choose>
				<c:when test="${row.subLength>28 }">
					<c:out value="${fn:substring(row.subject,0,28) }..." />
					
				</c:when>
				<c:otherwise>
					<c:out value="${row.subject }" />
					<c:if test="${row.jtext!=''&&row.jtext!=null }">-</c:if>
				<font color="gray">
				<c:if test="${fn:length(row.jtext)>(28-row.subLength) }">
				<c:out value="${fn:substring(row.jtext,0,28-row.subLength) }..." />
				</c:if>
				<c:if test="${fn:length(row.jtext)<=(28-row.subLength) }">
				<c:out value="${fn:substring(row.jtext,0,28-row.subLength) }" />
				</c:if>
				
				</font>
				</c:otherwise>
			</c:choose> 
			</font></a>
			</c:if>
			
			</c:if>
			<c:if test="${rel=='4' }">
			<a href="${ctx}/mail/view_group_mail.action?uuid=${row.uuid }&rel=${rel }&pageNo=${mypage.pageNo}&mtype=${mtype}&allUuid=${row.allUuid}">
			<font color="${row.scolor }">
			<c:choose>
				<c:when test="${row.subLength>28 }">
					<c:out value="${fn:substring(row.subject,0,28) }..." />
					
				</c:when>
				<c:otherwise>
					<c:out value="${row.subject }" />
					<c:if test="${row.jtext!=''&&row.jtext!=null }">-</c:if>
				<font color="gray">
				<c:if test="${fn:length(row.jtext)>(28-row.subLength) }">
				<c:out value="${fn:substring(row.jtext,0,28-row.subLength) }..." />
				</c:if>
				<c:if test="${fn:length(row.jtext)<=(28-row.subLength) }">
				<c:out value="${fn:substring(row.jtext,0,28-row.subLength) }" />
				</c:if>
				</font>
				</c:otherwise>
			</c:choose> 
			</font></a>
			</c:if>
			<c:if test="${(rel=='2'||rel=='6')&&row.dingshi!='1' }">
			<a href="${ctx}/mail/mailbox_view.action?rel=${rel }&mailname=0&uuid=${row.uuid}&pageNo=${mypage.pageNo}&mtype=${mtype }" >
			<font color="${row.scolor }">
			<c:choose>
				<c:when test="${row.subLength>28 }">
					<c:out value="${fn:substring(row.subject,0,28) }..." />
					
				</c:when>
				<c:otherwise>
					<c:out value="${row.subject }" />
					<c:if test="${row.jtext!=''&&row.jtext!=null }">-</c:if>
				<font color="gray">
				<c:if test="${fn:length(row.jtext)>(28-row.subLength) }">
				<c:out value="${fn:substring(row.jtext,0,28-row.subLength) }..." />
				</c:if>
				<c:if test="${fn:length(row.jtext)<=(28-row.subLength) }">
				<c:out value="${fn:substring(row.jtext,0,28-row.subLength) }" />
				</c:if>
				</font>
				</c:otherwise>
			</c:choose> 
			</font></a>
			</c:if>
			</td>
<!--			label-->
			<td id="${row.uuid }label">
			<c:if test="${row.labelList!=null }" >
			<c:forEach items="${row.labelList}" var="lbl">
			<span id="${row.uuid }${lbl.labelUuid }" style="background-color: ${lbl.labelColor};color:white" ><a onclick="window.location.href='${ctx}/mail/mail_box_list.action?rel=15&ctype=${lbl.labelUuid }&pageNo=0&&mtype=0';"><c:out value="${lbl.labelName }"/></a><a href="#" onclick="setLabel2('${row.uuid }','${lbl.labelUuid }','2');">删除</a></span>
			</c:forEach>
			</c:if>
			</td>
			
			<td onclick="">
			<c:if test="${(rel!='4'&&rel!='2'&&rel!='6')||(rel=='2'&&row.dingshi=='1') }">
			<c:if test="${row.groupName!=null&&row.groupName!=''&&rel!='1' }">
			<a href="${ctx}/mail/view_group_mail.action?uuid=${row.uuid }&rel=${rel }&pageNo=${mypage.pageNo}&mtype=${mtype}&allUuid=${row.allUuid}">${row.sendDate}</a>
			</c:if>
			<c:if test="${row.groupName==''||row.groupName==null||rel=='1' }">
			<a href="${ctx}/mail/mailbox_view2.action?rel=${rel }&mailname=${mailname }&uuid=${row.uuid}&pageNo=${mypage.pageNo}&mtype=${mtype }&allUuid=${row.allUuid}">${row.sendDate}</a>
			</c:if>
<!--			<a href="${ctx}/mail/mailbox_view2.action?rel=${rel }&mailname=0uuid=${row.uuid}&pageNo=${mypage.pageNo}&mtype=${mtype }">${row.sendDate}</a>-->
			</c:if>
			<c:if test="${rel=='4' }">
			<a href="${ctx}/mail/view_group_mail.action?uuid=${row.uuid }&rel=${rel }&pageNo=${mypage.pageNo}&mtype=${mtype}&allUuid=${row.allUuid}" >${row.sendDate }</a>
			</c:if>
			<c:if test="${(rel=='2'||rel=='6')&&row.dingshi!='1' }">
			<a href="${ctx}/mail/mailbox_view.action?rel=${rel }&mailname=0&uuid=${row.uuid}&pageNo=${mypage.pageNo}&mtype=${mtype }" >${row.sendDate }</a>
			</c:if>
			</td>
			<td>
			<c:if test="${row.star=='1' }">
			<img onclick="setStar(this,'${row.uuid}');" src="${ctx}/pt/mail/js/star-on.png" alt="<spring:message code="mail.label.xingbiao"/>" />
			</c:if>
			<c:if test="${row.star!='1' }">
			<img onclick="setStar(this,'${row.uuid}');" src="${ctx}/pt/mail/js/star-off.png" alt="<spring:message code="mail.label.xingbiao"/>" />
			</c:if>
			</td>
		</tr>

	</c:forEach>
</table>

<div><spring:message code="mail.label.select"/>：<a href="${ctx}/mail/mail_box_list.action?rel=${rel }&pageNo=1&mtype=0"><spring:message code="mail.label.all"/></a>-<a
	href="${ctx}/mail/mail_box_list.action?rel=${rel }&pageNo=1&mtype=1"><spring:message code="mail.label.hasRead"/></a>-<a
	href="${ctx}/mail/mail_box_list.action?rel=${rel }&pageNo=1&mtype=2"><spring:message code="mail.label.noRead"/></a></div>
	
	
	

<div id="div1" style="float:left; position:relative; margin:0 0 0 0; height:30px;">
<c:if test="${rel=='6' }">
<button onclick="history.go(-1);"><spring:message code="mail.btn.return"/> </button>
</c:if>
<c:if test="${rel!='3' }">
<button onclick="moveMailBox('${rel }','${mypage.pageNo}','${mtype}','3','${mailname }')"><spring:message code="mail.btn.delete"/></button>
</c:if>
<button onclick="deleMailBox('${rel }','${mypage.pageNo}','${mtype}','${mailname }')"><spring:message code="mail.btn.cdelete"/></button>
<c:if test="${rel!='4' }">
<button
	onclick="toSendOtherMailBox('1','${rel }','${mypage.pageNo}','${mtype}');"><spring:message code="mail.btn.sendother"/></button>
</c:if>
<c:if test="${rel=='4' }">
<button onclick="toSendOtherGMailBox('${rel }','${mypage.pageNo}','${mtype}','4');" ><spring:message code="mail.btn.sendother"/></button>
		<button onclick="toSendOtherGMailBox('${rel }','${mypage.pageNo}','${mtype}','3');" ><spring:message code="mail.btn.newMailSend"/></button>
</c:if>
<c:if test="${rel=='3' }">
<button
	onclick="clearMailBox('${rel }','${mypage.pageNo}','${mtype}','${mailname }');"><spring:message code="mail.btn.clear"/></button>
</c:if>
<button onclick="readMailBox('${rel }','0','${mypage.pageNo}','${mtype}');"><spring:message code="mail.label.setAllRead"/></button>
<button onclick="readMailBox('${rel }','1','${mypage.pageNo}','${mtype}');"><spring:message code="mail.label.setAllNoRead"/> </button>
</div>

<div class="btn-group">  
  <button class="btn"><spring:message code="mail.label.setTo"/></button>  
  <button class="btn dropdown-toggle" data-toggle="dropdown">  
    <span class="caret"></span>  
  </button>  
  <ul class="dropdown-menu">  
   <li onmouseover="setSelThis(this);" onmouseout="setSelThis2(this);" style="cursor: pointer;" onclick="readMailBox('${rel }','2','${mypage.pageNo}','${mtype}');"><spring:message code="mail.label.hasReadMail"/></li>
            <li onmouseover="setSelThis(this);" onmouseout="setSelThis2(this);" style="cursor: pointer;" onclick="readMailBox('${rel }','4','${mypage.pageNo}','${mtype}');"><spring:message code="mail.label.xingbiao"/></li>
            <li onmouseover="setSelThis(this);" onmouseout="setSelThis2(this);" style="cursor: pointer; " onclick="readMailBox('${rel }','5','${mypage.pageNo}','${mtype}');"><spring:message code="mail.label.noxingbiao"/></li>
             <li >---------------------</li>
            <c:forEach items="${labelList}" var="mf2">
            <c:if test="${mf2.uuid!=ctype&&mf2.fname!=mailname }">
            <li onmouseover="setSelThis(this);" onmouseout="setSelThis3(this,'${mf2.color }');" style="cursor: pointer;background-color: ${mf2.color };color:white" onclick="setLabel('${mf2.uuid }','${mf2.fname}','${mf2.color}','1');"><c:out value="${mf2.fname}"/></li>
            </c:if>
            </c:forEach>
             <li >---------------------</li>
            <li  onmouseover="setSelThis(this,'${row.uuid}');" onmouseout="setSelThis2(this);" style="cursor: pointer;" onclick="openMailListLabel('${rel}','${mypage.pageNo}','${mtype}','${mailname }');"><spring:message code="mail.label.addLabel"/></li>
  </ul>  
</div>  

<div class="btn-group">  
  <button class="btn"><spring:message code="mail.label.moveTo"/></button>  
  <button class="btn dropdown-toggle" data-toggle="dropdown">  
    <span class="caret"></span>  
  </button>  
  <ul class="dropdown-menu">  
   			<c:if test="${rel!='0'&&inbox!='1' }" >
        	<li onmouseover="setSelThis(this);" onmouseout="setSelThis2(this);"  style="cursor: pointer;" onclick="moveMailBox('${rel }','${mypage.pageNo}','${mtype }','0','${mailname }');"><spring:message code="mail.label.receiveMailBox"/></li>
        	</c:if>
        	<c:if test="${rel!='4'&&group!='1' }" >
            <li onmouseover="setSelThis(this);" onmouseout="setSelThis2(this);" style="cursor: pointer;" onclick="moveMailBox('${rel }','${mypage.pageNo}','${mtype }','4','${mailname }');"><spring:message code="mail.label.groupBox"/></li>
             </c:if>
        	<c:if test="${rel!='1'&&fasong!='1' }" >
            <li  onmouseover="setSelThis(this);" onmouseout="setSelThis2(this);" style="cursor: pointer;" onclick="moveMailBox('${rel }','${mypage.pageNo}','${mtype }','1','${mailname }');"><spring:message code="mail.label.hasSendBox"/></li>
            </c:if>
            <c:if test="${rel!='2'&&caogao!='1' }" >
            <li  onmouseover="setSelThis(this);" onmouseout="setSelThis2(this);" style="cursor: pointer;" onclick="moveMailBox('${rel }','${mypage.pageNo}','${mtype }','2','${mailname }');"><spring:message code="mail.label.caogaoBox"/></li>
             </c:if>
            <c:if test="${rel!='3'&&del!='1' }" >
            <li  onmouseover="setSelThis(this);" onmouseout="setSelThis2(this);" style="cursor: pointer;" onclick="moveMailBox('${rel }','${mypage.pageNo}','${mtype }','3','${mailname }');"><spring:message code="mail.label.deleteBox"/></li>
             </c:if>
              <li >---------------------</li>
            <c:forEach items="${myfolders}" var="mf">
            <c:if test="${mf.uuid!=ctype&&mf.fname!=mailname }">
            <li onmouseover="setSelThis(this);" onmouseout="setSelThis2(this);" style="cursor: pointer;" onclick="moveMailBox('${rel}','${mypage.pageNo}','${mtype }','${mf.uuid }','${mailname }');">${mf.fname}</li>
            </c:if>
            </c:forEach>
            <c:forEach items="${otherList}" var="mo">
            <c:if test="${mo.uuid!=ctype&&mo.username!=mailname }">
            <li onmouseover="setSelThis(this);" onmouseout="setSelThis2(this);" style="cursor: pointer;" onclick="moveMailBox2('${rel}','${mypage.pageNo}','${mtype }','6','${mailname }','${mo.username }');">${mo.username}</li>
            </c:if>
            </c:forEach>
             <li >---------------------</li>
            <li  onmouseover="setSelThis(this,'${row.uuid}');" onmouseout="setSelThis2(this);" style="cursor: pointer;"  onclick="openMailListFolder('${rel}','${mypage.pageNo}','${mtype}','${mailname }');"><spring:message code="mail.label.addFolder"/></li>
  </ul>  
</div> 







<!--<button onclick="readMailBox('2','2','${mypage.pageNo}','${mtype}');">标记为已读</button>-->
<!--<button onclick="readMailBox('2','3','${mypage.pageNo}','${mtype}');">标记为未读</button>-->
<div id="div4" style="float:right; position:relative; margin:0 0 0 0;width:200px;  height:30px;">
<c:out value="${mypage.pageNo }"></c:out>/<c:out
	value="${mypage.totalPage }"></c:out><spring:message code="mail.label.page"/> <c:if
	test="${mypage.pageNo<=mypage.totalPage&&mypage.pageNo>1 }">
	<a
		href="${ctx}/mail/mail_box_list.action?rel=${rel }&pageNo=${mypage.pageNo-1}&mtype=${mtype}&mailname=${mailname }"><spring:message code="mail.label.prePage"/></a>
</c:if> <c:if test="${mypage.pageNo<mypage.totalPage }">
	<a
		href="${ctx}/mail/mail_box_list.action?rel=${rel }&pageNo=${mypage.pageNo+1}&mtype=${mtype}&mailname=${mailname }"><spring:message code="mail.label.nextPage"/></a>
</c:if>
<c:if test="${mypage.totalPage>1 }">
	<a
		href="#" onclick="openPage();"><spring:message code="mail.label.go"/></a>
		<div id="divpage" style="display: none">
		<spring:message code="mail.label.goto"/><select id="toPage"><c:forEach begin="1" end="${ mypage.totalPage}" step="1" var="prow">
		<c:if test="${prow==mypage.pageNo }">
		<option value="${prow }" selected="selected">${prow }</option>
		</c:if>
		<c:if test="${prow!=mypage.pageNo }">
		<option value="${prow }">${prow }</option>
		</c:if>
		</c:forEach></select><spring:message code="mail.label.page"/>
		<button onclick="toPage('${rel }','${mtype}','${mailname }');"><spring:message code="mail.btn.confirm"/></button>
		</div>
</c:if>

</div>






<script type="text/javascript"> 

</script>
</body>
</html>