<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Inbox Mail List</title>
<%@ include file="/pt/common/meta.jsp"%>

<script src="${ctx}/resources/jquery/jquery.js"></script>
<!-- jQuery UI -->
<script src="${ctx}/resources/jqueryui/js/jquery-ui.js"></script>
<script type="text/javascript"
		src="${ctx}/resources/pt/js/org/unit/jquery.unit.js"></script>
	<script src="${ctx}/resources/schedule/schedule.js" type="text/javascript"></script>
	
	<script type="text/javascript">
	
	</script>
</head>
<body style="width: 100%; height: 100%; padding: 0px; margin: 0px;">

<input type="hidden" id="contextPath" value="${ctx}"></input>
<div id="toolbar">
<table width="100%">
	<tr>
		<td align="left"><!--		<button onclick="openLayer('${now2 }' ,'wuzq','test_con3');" >+</button>-->
		<!--		<input type="text" size="7" name="firstday" value="${firstday }"/>-->
		<button
			onclick="window.location.href='${ctx}/schedule/person_schedule2.action?ldate=${now }&stype=0&mtype=${mtype }';">今天</button>
		</td>
		<td align="center"><a href="#" onclick="to('1','${mtype}');"><</a> <label
			id="lldate"> ${day.display2Day }</label> <input type="hidden"
			size="7" id="ldate" name="ldate" value="${ldate }" /> <a href="#"
			onclick="to('2','${mtype}');">></a></td>
		<td align="right">
		<button 
			onclick="window.location.href='${ctx}/schedule/person_schedule.action?ldate=${ldate }&mtype=${mtype }';">月</button>
		<button style="color: blue;"
			onclick="window.location.href='${ctx}/schedule/person_schedule2.action?ldate=${ldate }&mtype=${mtype }';">日</button>
		</td>
	</tr>
</table>
</div>
<table width="100%" style="height: 100%" border="2">
	<tr valign="top">
		<td width="25%" valign="top">
		<table>
			<tr>
				<td height="200"><font size="7"><strong><label
					id="displayDay"><c:out value="${day.displayDay }" /></label></strong></font><br />
				<label id="display2Day"><c:out value="${day.display2Day }" /></label><br />
				<label id="fullChinaDay"><c:out value="${day.fullChinaDay }" /></label>
				</td>
			</tr>
			<tr>
				<td height="600" valign="top"><a><</a><label>${lldate }</label><a>></a>
				<table>
					<tr>
						<td>日</td>
						<td>一</td>
						<td>二</td>
						<td>三</td>
						<td>四</td>
						<td>五</td>
						<td>六</td>
					</tr>
					<tr>
						<c:forEach items="${days }" var="row">

							<c:if test="${row.sdate==ldate }">
								<td bgcolor="blue">
							</c:if>
							<c:if test="${row.sdate!=ldate }">
								<td>
							</c:if>
							<c:if test="${row.inMonth==0 }">
								<font color="black">
							</c:if>
							<c:if test="${row.inMonth!=0 }">
								<font color="gray">
							</c:if>
							<span 
								onclick="changeDay('${row.displayDay}','${row.sdate }','${row.display2Day }','${row.fullChinaDay }');"><c:out
								value="${row.displayDay }"></c:out></span>
							</font>
							</font>
							<c:if test="${row.count%7==0 }">
					</tr>
					<tr>
						</c:if>
						</td>
						</c:forEach>
					</tr>
				</table>
				</td>
			</tr>
		</table>

		</td>
		<td width="75%">
		<table id="mytb" width="100%">
			<c:forEach items="${days }" var="row1">

				<c:if test="${row1.sdate==ldate }">
					<tr valign="top" id="${row1.sdate }one" style="display: block">
				</c:if>
				<c:if test="${row1.sdate!=ldate }">
					<tr valign="top" id="${row1.sdate }one" style="display: none">
				</c:if>

				<td><c:out value="${row1.display2Day }"></c:out></td>
				</tr>
				<c:if test="${row1.sdate==ldate }">
					<tr valign="top" id="${row1.sdate }two" style="display: block">
				</c:if>
				<c:if test="${row1.sdate!=ldate }">
					<tr valign="top" id="${row1.sdate }two" style="display: none">
				</c:if>
				<td><c:forEach items="${sche[row1.sdate] }" var="row2">
					<table width="100%">
						<tr width="15%">
							<td><a href="#"
								onclick="openLayerPerson('${row2.isComplete }','${userno }','${row1.sdate }','${row1.sdate }four','test_con3','${row2.uuid }','${row2.creator }','${row2.scheduleName }','${row2.address }','${row2.dstartDate }','${row2.startTime }','${row2.dendDate }','${row2.endTime }','${row2.isView }','${row2.status }','${row2.leaderNames }','${row2.leaderIds }','${row2.pleases }','${row2.pleaseIds }','${row2.views }','${row2.viewIds }','${row2.color}','${row2.tip }','${row2.tipDate }','${row2.tipTime }','${row2.repeat }','${row2.startTime2 }','${row2.endTime2 }','${row2.tipTime2 }','${row2.creators }','${row2.creatorIds }');">
							<font color="${row2.color }">
							<c:if test="${row2.isComplete=='1' }">
							<strike>
							<c:if test="${row2.startTime=='' }">全天 </c:if><c:if test="${row2.startTime!='' }"><c:out value="${row2.startTime }"/>:<c:out value="${row2.startTime2 }"/> </c:if>
							</strike>
							</c:if>
							<c:if test="${row2.isComplete!='1' }">
							<c:if test="${row2.startTime=='' }">全天 </c:if><c:if test="${row2.startTime!='' }"><c:out value="${row2.startTime }"/>:<c:out value="${row2.startTime2 }"/> </c:if>
							</c:if>
							</font> </a></td>
							<td width="20%"><a href="#"
								onclick="openLayerPerson('${row2.isComplete }','${userno }','${row1.sdate }','${row1.sdate }four','test_con3','${row2.uuid }','${row2.creator }','${row2.scheduleName }','${row2.address }','${row2.dstartDate }','${row2.startTime }','${row2.dendDate }','${row2.endTime }','${row2.isView }','${row2.status }','${row2.leaderNames }','${row2.leaderIds }','${row2.pleases }','${row2.pleaseIds }','${row2.views }','${row2.viewIds }','${row2.color}','${row2.tip }','${row2.tipDate }','${row2.tipTime }','${row2.repeat }','${row2.startTime2 }','${row2.endTime2 }','${row2.tipTime2 }','${row2.creators }','${row2.creatorIds }');">
							<font color="${row2.color }">
							<c:if test="${row2.isComplete=='1' }">
							<strike>
							<c:choose>
								<c:when test="${fn:length(row2.address)>20 }">
									<c:out value="${fn:substring(row2.address,0,20) }..." />
								</c:when>
								<c:otherwise>
									<c:out value="${row2.address }" />
								</c:otherwise>
							</c:choose>
							</strike>
							</c:if>
							<c:if test="${row2.isComplete!='1' }">
							<c:choose>
								<c:when test="${fn:length(row2.address)>20 }">
									<c:out value="${fn:substring(row2.address,0,20) }..." />
								</c:when>
								<c:otherwise>
									<c:out value="${row2.address }" />
								</c:otherwise>
							</c:choose>
							</c:if>
							</font> </a></td>
							<td width="25%"><a href="#"
								onclick="openLayerPerson('${row2.isComplete }','${userno }','${row1.sdate }','${row1.sdate }four','test_con3','${row2.uuid }','${row2.creator }','${row2.scheduleName }','${row2.address }','${row2.dstartDate }','${row2.startTime }','${row2.dendDate }','${row2.endTime }','${row2.isView }','${row2.status }','${row2.leaderNames }','${row2.leaderIds }','${row2.pleases }','${row2.pleaseIds }','${row2.views }','${row2.viewIds }','${row2.color}','${row2.tip }','${row2.tipDate }','${row2.tipTime }','${row2.repeat }','${row2.startTime2 }','${row2.endTime2 }','${row2.tipTime2 }','${row2.creators }','${row2.creatorIds }');">
							<font color="${row2.color }">
							<c:if test="${row2.isComplete=='1' }">
							<strike>
							<c:choose>
								<c:when test="${fn:length(row2.scheduleName)>10 }">
									<c:out value="${fn:substring(row2.scheduleName,0,10) }..." />
								</c:when>
								<c:otherwise>
									<c:out value="${row2.scheduleName }" />
								</c:otherwise>
							</c:choose>
							</strike>
							</c:if>
							<c:if test="${row2.isComplete!='1' }">
							<c:choose>
								<c:when test="${fn:length(row2.scheduleName)>10 }">
									<c:out value="${fn:substring(row2.scheduleName,0,10) }..." />
								</c:when>
								<c:otherwise>
									<c:out value="${row2.scheduleName }" />
								</c:otherwise>
							</c:choose>
							</c:if>
							</font> </a></td>
							<td width="20%"><a href="#"
								onclick="openLayerPerson('${row2.isComplete }','${userno }','${row1.sdate }','${row1.sdate }four','test_con3','${row2.uuid }','${row2.creator }','${row2.scheduleName }','${row2.address }','${row2.dstartDate }','${row2.startTime }','${row2.dendDate }','${row2.endTime }','${row2.isView }','${row2.status }','${row2.leaderNames }','${row2.leaderIds }','${row2.pleases }','${row2.pleaseIds }','${row2.views }','${row2.viewIds }','${row2.color}','${row2.tip }','${row2.tipDate }','${row2.tipTime }','${row2.repeat }','${row2.startTime2 }','${row2.endTime2 }','${row2.tipTime2 }','${row2.creators }','${row2.creatorIds }');">
							<font color="${row2.color }">
							<c:if test="${row2.isComplete=='1' }">
							<strike>
							<c:choose>
								<c:when test="${fn:length(row2.leaderNames)>20 }">
									<c:out value="${fn:substring(row2.leaderNames,0,20) }..." />
								</c:when>
								<c:otherwise>
									<c:out value="${row2.leaderNames }" />
								</c:otherwise>
							</c:choose>
							</strike>
							</c:if>
							<c:if test="${row2.isComplete!='1' }">
							<c:choose>
								<c:when test="${fn:length(row2.leaderNames)>20 }">
									<c:out value="${fn:substring(row2.leaderNames,0,20) }..." />
								</c:when>
								<c:otherwise>
									<c:out value="${row2.leaderNames }" />
								</c:otherwise>
							</c:choose>
							</c:if>
							</font> </a></td>
							<td width="20%"><a href="#"
								onclick="openLayerPerson('${row2.isComplete }','${userno }','${row1.sdate }','${row1.sdate }four','test_con3','${row2.uuid }','${row2.creator }','${row2.scheduleName }','${row2.address }','${row2.dstartDate }','${row2.startTime }','${row2.dendDate }','${row2.endTime }','${row2.isView }','${row2.status }','${row2.leaderNames }','${row2.leaderIds }','${row2.pleases }','${row2.pleaseIds }','${row2.views }','${row2.viewIds }','${row2.color}','${row2.tip }','${row2.tipDate }','${row2.tipTime }','${row2.repeat }','${row2.startTime2 }','${row2.endTime2 }','${row2.tipTime2 }','${row2.creators }','${row2.creatorIds }');">
							<font color="${row2.color }">
							<c:if test="${row2.isComplete=='1' }">
							<strike>
							<c:choose>
								<c:when test="${fn:length(row2.allNames)>20 }">
									<c:out value="${fn:substring(row2.allNames,0,20) }..." />
								</c:when>
								<c:otherwise>
									<c:out value="${row2.allNames }" />
								</c:otherwise>
							</c:choose>
							</strike>
							</c:if>
							<c:if test="${row2.isComplete!='1' }">
							<c:choose>
								<c:when test="${fn:length(row2.allNames)>20 }">
									<c:out value="${fn:substring(row2.allNames,0,20) }..." />
								</c:when>
								<c:otherwise>
									<c:out value="${row2.allNames }" />
								</c:otherwise>
							</c:choose>
							</c:if>
							</font> </a></td>
						</tr>
					</table>
				</c:forEach>
				<div style="width: 100%; overflow: hidden;">
				<c:if test="${row1.sdate==ldate }">
				<div id="${row1.sdate }four" class="visi">
				</c:if>
				<c:if test="${row1.sdate!=ldate }">
				<div id="${row1.sdate }four">
				</c:if>
				<font size="1"><a href="#"
					onclick="openLayer('${row1.sdate }' ,'${row1.sdate }four','test_con3');">新建事件...</a></font></div>
				</div>
				</td>
				</tr>

			</c:forEach>

		</table>
		</td>
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
<span class="selectcolor" style="background-color:black;" onclick="selColor('balck');">&nbsp;&nbsp;&nbsp;&nbsp;</span>
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
<table>
	<tr>
	<td>
<button style="display: none" id="editsche"  onclick="invalid();">编辑</button></td>
		<td>
		<button id="addsche" onclick="addPersonSchedule('${ldate}','0','2','${mtype }');">确定</button>
		</td>
		<td>
		<button id="delsche" style="display: none"
			onclick="deletePersonSchedule('${ldate}','0','2','${mtype }');">删除</button>
		</td>
		<td>
		<button id="compsche" style="display: none"
			onclick="addPersonSchedulewuzq('${ldate}','0','2','${mtype }');">完成</button>
		</td>
		<td>
		<button onclick="closeLayer();">取消</button>
		</td>
		<td></td>
	</tr>
</table>
</div>

</div>
</div>
</div>



</body>
</html>