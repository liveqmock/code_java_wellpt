<%@ page contentType="text/html;charset=UTF-8"%> 
<%@ include file="/pt/common/taglibs.jsp"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Inbox Mail List</title>
<%@ include file="/pt/common/meta.jsp"%>

<script type="text/javascript">

function scheduleOnload(){
	var hei=0;
		$(".scheduletd").each(function (){
			var hei=($(window).height()-89)/5;
			$(this).height(hei+"px");
		});
}


//添加个人日程
function addPersonSchedulewuzq(ldate,stype,rel,mtype){
	var scheduleName=$('#scheduleName').val();
	if(scheduleName==''){
		alert('日程名称不能为空!');
		$('#scheduleName').focus();
		return false;
	}
	var address=$('#address').val();
	if(address==''){
		alert('地点不能为空!');
		$('#address').focus();
		return false;
	}
	var startDate=$('#startDate').val();
	if(startDate==''){
		alert('开始日期不能为空!');
		$('#startDate').focus();
		return false;
	}
	
	var quan=$("#quan").attr("checked")=='checked'?"1":"0";
	var jieshu=$("#jieshu").attr("checked")=='checked'?"1":"0";
	var startTime,startTime2,endDate,endTime,endTime2;
	if(quan==0){
	startTime=$('#startTime').val();
	startTime2=$('#startTime2').val();
	if(startTime==''||startTime2==''){
		alert('开始时间 不能为空!');
		$('#startTime').focus();
		return false;
	}
	}
	if(jieshu==1){
	endDate=$('#endDate').val();
	if(endDate==''){
		alert('结束日期不能为空!');
		$('#endDate').focus();
		return false;
	}
	}
	if(quan==0&&jieshu==1){
	endTime=$('#endTime').val();
	endTime2=$('#endTime2').val();
	if(endTime==''||endTime2==''){
		alert('结束时间不能为空!');
		$('#endTime').focus();
		return false;
	}
	}
	
	
	var color=$("#mycolor").val();
	
	
	
	
	var leaderNames=$('#leaderNames').val();
	if(leaderNames==''){
		alert('责任人不能为空!');
		$('#leaderNames').focus();
		return false;
	}
	var leaderIds=$('#leaderIds').val();
	
	var isView="";
	$(".isView").each(function (){
			if($(this).attr("checked")=="checked"){
				isView=$(this).val();
			}
		});
	var pleases=$('#pleases').val();
	var views=$('#views').val();
	var pleaseIds=$('#pleaseIds').val();
	var viewIds=$('#viewIds').val();
	var creators=$('#creators').val();
	var creatorIds=$('#creatorIds').val();
	var status=$('#status').val();
	var uuid=$('#uuid').val();
	var tip=$('#tip').val();
	var tipDate=$('#tipDate').val();
	var tipTime=$('#tipTime').val();
	var tipTime2=$('#tipTime2').val();
	var repeat=$("#repeat").val();
	$.ajax({
		type : "POST",
		url : contextPath+"/schedule/schedule_add.action",
		data : "creatorIds="+creatorIds+"&creators="+creators+"&tip="+tip+"&tipTime2="+tipTime2+"&startTime2="+startTime2+"&endTime2="+endTime2+"&tipDate="+tipDate+"&tipTime="+tipTime+"&repeat="+repeat+"&color="+color+"&uuid="+uuid+"&status="+status+"&scheduleName="+scheduleName+"&address="+address+"&dstartDate="+startDate+"&startTime="+startTime+"&dendDate="+endDate+"&endTime="+endTime+"&isView="+isView+"&leaderNames="+leaderNames+"&leaderIds="+leaderIds+"&pleases="+pleases+"&views="+views+"&pleaseIds="+pleaseIds+"&viewIds="+viewIds,
		dataType : "text",
		success : function callback(result) {
			completeSchedule(ldate,stype,rel,mtype);
		}
	});
	}
	


</script>
</head>
<body  style="width:100%; height:100%;padding:0px; margin:0px;" onload="scheduleOnload();">

	
	<div id="toolbar">
		<table width="100%"><tr><td align="left">
<!--		<button onclick="openLayer('${now2 }' ,'wuzq','test_con3');" >+</button>-->
<!--		<input type="text" size="7" name="firstday" value="${firstday }"/>-->
		<button onclick="window.location.href='${ctx}/cms/schedule/person_schedule.action?ldate=${now3 }&stype=0&mtype=${mtype }';"  >今天</button>
		</td>
		<td align="center">
		<a  href="#" onclick="window.location.href='${ctx}/cms/schedule/person_schedule.action?ldate=${ldate }&stype=1&mtype=${mtype }'" ><</a>
		<input type="text" size="7" name="ldate" value="${lldate }"/>
		<a href="#" href="#" onclick="window.location.href='${ctx}/cms/schedule/person_schedule.action?ldate=${ldate }&stype=2&mtype=${mtype }'"  >></a>
		</td>
		<td align="right">
		 <button style="color: blue;" onclick="window.location.href='${ctx}/cms/schedule/person_schedule.action?ldate=${ldate }&mtype=${mtype }';" >月</button>
		 <button onclick="window.location.href='${ctx}/cms/schedule/person_schedule2.action?mtype=${mtype }';" >日</button>
		</td></tr></table>
	</div>
	<table width="100%" style="height:100%"   border="2" >
		<tr>
		<div style="border: 2;border-width: 2"></div>
		<td width="14%">
		星期一
		</td>
		<td width="14%">
		星期二
		</td>
		<td width="14%">
		星期三
		</td>
		<td width="14%">
		星期四
		</td>
		<td width="14%">
		星期五
		</td>
		<td width="14%" bgcolor="gray">
		星期六
		</td>
		<td width="14%"  bgcolor="gray">
		星期日
		</td>
		</tr>
		
		<tr >
		
		<c:forEach items="${days }" var="row" >
			<c:if test="${row.weekend==1}">
			<td class="scheduletd" valign="top" height="${100/(totalday/7) }" bgcolor="gray" onmouseover="showorno('${row.sdate }','one','1');" onmouseout="showorno('${row.sdate }','one','2');">
			</c:if>
			<c:if test="${row.weekend!=1}">
			<td class="scheduletd" valign="top" height="${100/(totalday/7) }" onmouseover="showorno('${row.sdate }','one','1');" onmouseout="showorno('${row.sdate }','one','2');">
			</c:if>
			<div  style="width:100%;overflow:hidden;font-size: 12px;"  >
			<c:if test="${row.today==1}">
			<table width="100%" bgcolor="blue"><tr  valign="top"><td align="left" width="60%">
			<c:out value="${row.displayDay }"/>
			</td>
			<td align="right">
			<c:out value="${row.chinaDay }"/>
			</td>
			</tr></table>
			</c:if>
			
			<c:if test="${row.today!=1}">
			<table width="100%"><tr valign="top"><td align="left" width="60%">
			<c:out value="${row.displayDay }"/> <c:out value="${row.today}"/>
			</td>
			<td align="right">
			<c:out value="${row.chinaDay }"/>
			</td>
			</tr></table>
			</c:if>
			<table>
			
			<c:forEach items="${sche[row.sdate] }" var="row2">
			<tr>
			<c:if test="${row.today==1}">
			<td bgcolor="blue">
			<img src="${ctx }/resources/schedule/img/star-on.png"  />
			<a href="#" onclick="openLayerPerson('${row2.isComplete }','${userno }','${row.sdate }','${row.sdate }one','test_con3','${row2.uuid }','${row2.creator }','${row2.scheduleName }','${row2.address }','${row2.dstartDate }','${row2.startTime }','${row2.dendDate }','${row2.endTime }','${row2.isView }','${row2.status }','${row2.leaderNames }','${row2.leaderIds }','${row2.pleases }','${row2.pleaseIds }','${row2.views }','${row2.viewIds }','${row2.color}','${row2.tip }','${row2.tipDate }','${row2.tipTime }','${row2.repeat }','${row2.startTime2 }','${row2.endTime2 }','${row2.tipTime2 }','${row2.creators }','${row2.creatorIds }');">
			<font color="${row2.color }" >
			<c:if test="${row2.isComplete=='1' }">
			<strike>
			<c:if test="${row2.startTime=='' }">全天 </c:if><c:if test="${row2.startTime!='' }"><c:out value="${row2.startTime }"/>:<c:out value="${row2.startTime2 }"/> </c:if> 
			<c:choose><c:when test="${fn:length(row2.address)>3 }"><c:out value="${fn:substring(row2.address,0,3) }.."/></c:when><c:otherwise><c:out value="${row2.address }"/></c:otherwise></c:choose> 
			<c:choose><c:when test="${fn:length(row2.scheduleName)>3 }"><c:out value="${fn:substring(row2.scheduleName,0,3) }.."/></c:when><c:otherwise><c:out value="${row2.scheduleName }"/></c:otherwise></c:choose>
			<c:if test="${mtype=='1'||mtype=='2' }">
			<c:choose><c:when test="${fn:length(row2.leaderNames)>5 }"><c:out value="${fn:substring(row2.leaderNames,0,5) }.."/></c:when><c:otherwise><c:out value="${row2.leaderNames }"/></c:otherwise></c:choose>
			</c:if>
			</strike>
			</c:if>
			
			<c:if test="${row2.isComplete!='1' }">
			<c:if test="${row2.startTime=='' }">全天 </c:if><c:if test="${row2.startTime!='' }"><c:out value="${row2.startTime }"/>:<c:out value="${row2.startTime2 }"/> </c:if> 
			<c:choose><c:when test="${fn:length(row2.address)>3 }"><c:out value="${fn:substring(row2.address,0,3) }.."/></c:when><c:otherwise><c:out value="${row2.address }"/></c:otherwise></c:choose> 
			<c:choose><c:when test="${fn:length(row2.scheduleName)>3 }"><c:out value="${fn:substring(row2.scheduleName,0,3) }.."/></c:when><c:otherwise><c:out value="${row2.scheduleName }"/></c:otherwise></c:choose>
			<c:if test="${mtype=='1'||mtype=='2' }">
			<c:choose><c:when test="${fn:length(row2.leaderNames)>5 }"><c:out value="${fn:substring(row2.leaderNames,0,5) }.."/></c:when><c:otherwise><c:out value="${row2.leaderNames }"/></c:otherwise></c:choose>
			</c:if>
			</c:if>
			</font></a>
			</td>
			</c:if>
			<c:if test="${row.today!='1'}">
			<td >
			<img src="${ctx }/resources/schedule/img/star-on.png"  />
			<a href="#" onclick="openLayerPerson('${row2.isComplete }','${userno }','${row.sdate }','${row.sdate }one','test_con3','${row2.uuid }','${row2.creator }','${row2.scheduleName }','${row2.address }','${row2.dstartDate }','${row2.startTime }','${row2.dendDate }','${row2.endTime }','${row2.isView }','${row2.status }','${row2.leaderNames }','${row2.leaderIds }','${row2.pleases }','${row2.pleaseIds }','${row2.views }','${row2.viewIds }','${row2.color}','${row2.tip }','${row2.tipDate }','${row2.tipTime }','${row2.repeat }','${row2.startTime2 }','${row2.endTime2 }','${row2.tipTime2 }','${row2.creators }','${row2.creatorIds }');">
			<font color="${row2.color }">
			<c:if test="${row2.isComplete=='1' }">
			<strike>
			<c:if test="${row2.startTime=='' }">全天 </c:if><c:if test="${row2.startTime!='' }"><c:out value="${row2.startTime }"/>:<c:out value="${row2.startTime2 }"/> </c:if>
			<c:choose><c:when test="${fn:length(row2.address)>3 }"><c:out value="${fn:substring(row2.address,0,3) }.."/></c:when><c:otherwise><c:out value="${row2.address }"/></c:otherwise></c:choose> 
			<c:choose><c:when test="${fn:length(row2.scheduleName)>3 }"><c:out value="${fn:substring(row2.scheduleName,0,3) }.."/></c:when><c:otherwise><c:out value="${row2.scheduleName }"/></c:otherwise></c:choose>
			<c:if test="${mtype=='1'||mtype=='2' }">
			<c:choose><c:when test="${fn:length(row2.leaderNames)>6 }"><c:out value="${fn:substring(row2.leaderNames,0,6) }.."/></c:when><c:otherwise><c:out value="${row2.leaderNames }"/></c:otherwise></c:choose>
			</c:if>
			</strike>
			</c:if>
			<c:if test="${row2.isComplete!='1' }">
			<c:if test="${row2.startTime=='' }">全天 </c:if><c:if test="${row2.startTime!='' }"><c:out value="${row2.startTime }"/>:<c:out value="${row2.startTime2 }"/> </c:if>
			<c:choose><c:when test="${fn:length(row2.address)>3 }"><c:out value="${fn:substring(row2.address,0,3) }.."/></c:when><c:otherwise><c:out value="${row2.address }"/></c:otherwise></c:choose> 
			<c:choose><c:when test="${fn:length(row2.scheduleName)>3 }"><c:out value="${fn:substring(row2.scheduleName,0,3) }.."/></c:when><c:otherwise><c:out value="${row2.scheduleName }"/></c:otherwise></c:choose>
			<c:if test="${mtype=='1'||mtype=='2' }">
			<c:choose><c:when test="${fn:length(row2.leaderNames)>6 }"><c:out value="${fn:substring(row2.leaderNames,0,6) }.."/></c:when><c:otherwise><c:out value="${row2.leaderNames }"/></c:otherwise></c:choose>
			</c:if>
			</c:if>
			</font></a>
			</td>
			</c:if>
			
			</tr>
			</c:forEach>
			<tr>
			<td>
			<c:if test="${row.scheSize>2 }">
			 共有${row.scheSize }条日程,<a href="#" onclick="window.location.href='${ctx}/cms/schedule/person_schedule2.action?ldate=${row.sdate }&mtype=${mtype }';">查看</a>
			</c:if>
			</td>
			</tr>
			</table>
			<div id="${row.sdate }one" style="display: none;"><font size="1"><a href="#" onclick="openLayer('${row.sdate }' ,'${row.sdate }one','test_con3');">新建事件...</a></font></div>
			</div>
			</td>
			
			<c:if test="${row.count%7==0 }">
			</tr>
			<tr>
			</c:if>
		</c:forEach>
		</tr>
	</table>	
		
			
	
	<div id="test_con3" style="display:none">
<div id="tab3" style="overflow:auto;width:auto;height:auto;background:#fee;">
<div id="tabtop3">
<div id="tabtop-L3"  onmousedown="StartDrag(this);"   onmouseup="StopDrag(this);" onmousemove="Drag(this);"><strong style="color:red;font-size:20px">新建事件</strong></div></div>

</div>
<div id="tabcontent3" >
<input type="hidden" id="status" name="status" value="1" />
<input type="hidden" id="uuid" name="uuid" />
<table>
<tr>
<td align="left" width="20%">主题:</td>
<td ><input type="text" size="50" id="scheduleName" name="scheduleName" size="70"/></td>
</tr>
<tr>
<td align="left" width="20%">地点:</td>
<td><input type="text" id="address" name="address" size="50"/></td>
</tr>
<tr>
<td align="left" width="20%">开始:</td>
<td>
<div  style="float:left; position:relative; margin:0 0 0 0; width:100px; height:30px;">
<input type="text" size="10" id="startDate" name="startDate"  onclick="WdatePicker();" /> 
</div>
<div id="dstartTime" style="display:none;float:left; position:relative; margin:0 0 0 0; width:50%; height:30px;">
<select id="startTime" name="startTime"><option value="">--</option>
<option value="0">0</option><option value="1">1</option><option value="2">2</option><option value="3">3</option><option value="4">4</option><option value="5">5</option><option value="6">6</option><option value="7">7</option><option value="8">8</option><option value="9">9</option><option value="10">10</option><option value="11">11</option>
<option value="12">12</option><option value="13">13</option><option value="14">14</option><option value="15">15</option><option value="16">16</option><option value="17">17</option><option value="18">18</option><option value="19">19</option><option value="20">20</option><option value="21">21</option><option value="22">22</option><option value="23">23</option>
</select>
:<select id="startTime2" name="startTime2"><option value="">--</option>
<option value="00">00</option><option value="10">10</option><option value="20">20</option><option value="30">30</option><option value="40">40</option><option value="50">50</option>
</select>
</div>
</td>
</tr>
<tr style="display:none" id="trjieshu">
<td align="left" width="20%">结束:</td>
<td>
<div  style="float:left; position:relative; margin:0 0 0 0; width:100px; height:30px;">
<input  type="text" size="10" id="endDate" name="endDate"  onclick="WdatePicker();" />
</div>
<div id="dendTime"  style="display:none;float:left; position:relative; margin:0 0 0 0; width:50%; height:30px;">
<select id="endTime" name="endTime"><option value="">--</option>
<option value="0">0</option><option value="1">1</option><option value="2">2</option><option value="3">3</option><option value="4">4</option><option value="5">5</option><option value="6">6</option><option value="7">7</option><option value="8">8</option><option value="9">9</option><option value="10">10</option><option value="11">11</option>
<option value="12">12</option><option value="13">13</option><option value="14">14</option><option value="15">15</option><option value="16">16</option><option value="17">17</option><option value="18">18</option><option value="19">19</option><option value="20">20</option><option value="21">21</option><option value="22">22</option><option value="23">23</option>
</select>
:<select id="endTime2" name="endTime2"><option value="">--</option>
<option value="00">00</option><option value="10">10</option><option value="20">20</option><option value="30">30</option><option value="40">40</option><option value="50">50</option>
</select>
 </div>
</td>
</tr>
<tr>
<td align="left" width="20%"></td>
<td>
<input type="checkbox"" id="quan" name="quan" onclick="quantian();" checked="checked" />全天
<input type="checkbox"" id="jieshu" name="jieshu" onclick="jieshu();"  />结束时间
</td>
</tr>

<tr>
<td align="left" width="20%">事件颜色:</td>
<td>
<input type="hidden" id="mycolor" name="mycolor" value="black"/>
<span id="showcolor" style="background-color:black;">&nbsp;&nbsp;&nbsp;&nbsp;</span>
<span >&nbsp;|&nbsp;</span>
<span class="selectcolor" style="background-color:black;" onclick="selColor('black');">&nbsp;&nbsp;&nbsp;&nbsp;</span>
<span class="selectcolor" style="background-color:red " onclick="selColor('red');">&nbsp;&nbsp;&nbsp;&nbsp;</span>
<span class="selectcolor" style="background-color:blue " onclick="selColor('blue');">&nbsp;&nbsp;&nbsp;&nbsp;</span>
<span class="selectcolor" style="background-color:orange " onclick="selColor('orange');">&nbsp;&nbsp;&nbsp;&nbsp;</span>
<span class="selectcolor" style="background-color:green " onclick="selColor('green');">&nbsp;&nbsp;&nbsp;&nbsp;</span>
<span class="selectcolor" style="background-color:yellow " onclick="selColor('yellow');">&nbsp;&nbsp;&nbsp;&nbsp;</span>
<span class="selectcolor" style="background-color:purple " onclick="selColor('purple');">&nbsp;&nbsp;&nbsp;&nbsp;</span>
<span class="selectcolor" style="background-color:silver " onclick="selColor('silver');">&nbsp;&nbsp;&nbsp;&nbsp;</span>
<span class="selectcolor" style="background-color:tan " onclick="selColor('tan');">&nbsp;&nbsp;&nbsp;&nbsp;</span>
<span class="selectcolor" style="background-color:maroon;" onclick="selColor('maroon');">&nbsp;&nbsp;&nbsp;&nbsp;</span>
<span class="selectcolor" style="background-color:olive;" onclick="selColor('olive');">&nbsp;&nbsp;&nbsp;&nbsp;</span>
<span class="selectcolor" style="background-color:gray;" onclick="selColor('gray');">&nbsp;&nbsp;&nbsp;&nbsp;</span>
<span class="selectcolor" style="background-color:lime;" onclick="selColor('lime');">&nbsp;&nbsp;&nbsp;&nbsp;</span>
</td>
</tr>

<tr>
<td align="left" width="20%">负责人:</td>
<td><input type="text" name="leaderNames" readonly="readonly" id="leaderNames" size="50" onclick="openUser('leaderNames','leaderIds');" /><input  type="hidden" name="leaderIds" id="leaderIds"/></td>
</tr>
<tr>
<td align="left" width="20%">参与人:</td>
<td><input  type="text" name="pleases"  id="pleases" readonly="readonly"  size="50" onclick="openUser('pleases','pleaseIds');"/><input  type="hidden" name="pleaseIds"  id="pleaseIds"  /></td>
</tr>
<tr>
<td align="left" width="20%">创建者:</td>
<td><input  type="text" name="creators"  id="creators" value="${creators }" readonly="readonly"  size="50" onclick="openUser('creators','creatorIds');"/><input  type="hidden" value="${creatorIds }" name="creatorIds"  id="creatorIds"  /></td>
</tr>
<tr>
<td align="left" width="20%">允许他人查阅:</td>
<td>
<input type="radio" id="isView" name="isView" class="isView" value="3" checked="checked" onclick="showView(this);"/>不允许
<input type="radio" id="isView" name="isView" class="isView" value="2"  onclick="showView(this);"/>公开
<input type="radio" id="isView" class="isView" name="isView" value="1" onclick="showView(this);" />允许部分人查看
</td>
</tr>
<tr id="showview" style="display: none">
<td align="left" width="20%">查看人:</td>
<td><input  type="text" id="views" name="views"  readonly="readonly" size="50" onclick="openUser('views','viewIds');"/><input  type="hidden" id="viewIds" name="viewIds"  /></td>
</tr>
<tr>
<td align="left" width="20%">提醒:</td>
<td>
<table>
<tr>
<td>


<select id="tip" name="tip" onchange="tipChange();" style="height:20px;">
<option value="1" selected="selected">不提醒</option>
<option value="2">前一天</option>
<option value="3">同一天</option>
<option value="4">指定时间</option>
</select>

</td>
<td>
<div id="dtipDate"  style="display:none; height:20px;">
<input type="text" size="10"  id="tipDate" name="tipDate" value="${now }" onclick="WdatePicker();"/>
</div>
</td>
<td>
<div id="dtipTime"  style="display:none; height:20px;">
<select id="tipTime" name="tipTime"><option value="">--</option>
<option value="0">0</option><option value="1">1</option><option value="2">2</option><option value="3">3</option><option value="4">4</option><option value="5">5</option><option value="6">6</option><option value="7">7</option><option value="8">8</option><option value="9">9</option><option value="10">10</option><option value="11">11</option>
<option value="12">12</option><option value="13">13</option><option value="14">14</option><option value="15">15</option><option value="16">16</option><option value="17">17</option><option value="18">18</option><option value="19">19</option><option value="20">20</option><option value="21">21</option><option value="22">22</option><option value="23">23</option>
</select>
:<select id="tipTime2" name="tipTime2"><option value="">--</option>
<option value="00">00</option><option value="10">10</option><option value="20">20</option><option value="30">30</option><option value="40">40</option><option value="50">50</option>
</select>
</div>
</td>
</tr>
</table>
</td>
</tr>
<tr>
<td align="left" width="20%">重复:</td>
<td>
<select id="repeat" name="repeat">
<option value="1" selected="selected">不重复</option>
<option value="2">每日</option>
<option value="3">每工作日</option>
<option value="4">每周</option>
<option value="5">每月</option>
<option value="6">每年</option>
</select>
</td>
</tr>
</table>
<div align="center">
<table><tr>
<td>
<button style="display: none" id="editsche"  onclick="invalid();">编辑</button></td>
<td>
<button  id="addsche" onclick="addPersonSchedule('${ldate}','0','1','${mtype }');">确定</button></td><td>
<button id="delsche" style="display: none" onclick="deletePersonSchedule('${ldate}','0','1','${mtype }');">删除</button></td>
<td>
<button id="compsche" style="display: none" onclick="addPersonSchedulewuzq('${ldate}','0','1','${mtype }');">完成</button></td>
<td>
<button onclick="closeLayer();">取消</button></td><td>
</td></tr></table>
</div>

</div>
</div>
<script src="${ctx}/resources/jquery/jquery.js"></script>
<!-- jQuery UI -->
<script src="${ctx}/resources/jqueryui/js/jquery-ui.js"></script>
<script src="${ctx}/resources/My97DatePicker/WdatePicker.js"
		type="text/javascript"></script>
<script type="text/javascript"
		src="${ctx}/resources/pt/js/org/unit/jquery.unit.js"></script>
		
	<script src="${ctx}/resources/schedule/schedule.js" type="text/javascript"></script>
	<input type="hidden" id="contextPath" value="${ctx}"></input>
</body>
</html>