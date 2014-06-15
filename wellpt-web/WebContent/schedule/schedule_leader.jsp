<%@ page contentType="text/html;charset=UTF-8"%> 
<%@ include file="/pt/common/taglibs.jsp"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Inbox Mail List</title>
<%@ include file="/pt/common/meta.jsp"%>

<script src="${ctx}/resources/jquery/jquery.js"></script>
<!-- jQuery UI -->
<script src="${ctx}/resources/jqueryui/js/jquery-ui.js"></script>
<c:if test="${addCss!='yes'}">
<script type="text/javascript" src="${ctx}/resources/pt/js/common/jquery.alerts.js"></script>
</c:if>
<script type="text/javascript"
		src="${ctx}/resources/pt/js/org/unit/jquery.unit.js"></script>
	<script type="text/javascript">
	$("#leaderNames").click(function() {
		alert('2332');
$.unit.open({
	 	labelField : "leaderNames",
		valueField : "leaderIds",
		selectType : 4
});
});
	
	function openIframe(param){
		var url="";
		$.jBox.open('iframe:http://www.baidu.com','百度一下',800,350
				,{buttons:{'关闭':true},loaded:function (obj){
					var ifr = $.jBox.getIframe();
					var a = obj;
					$.each($(this).attr('param'),function (key,val){
						alert('key:' + key + ';val:' + val);
					});
				},param1:param});
	}
	</script>	

</head>
<body style="width:100%; height:100%;padding:0px; margin:0px;">
<!--	<div id="container">-->
	<input type="hidden" id="contextPath" value="${ctx}"></input>
	
	
	<div id="test_con3">
<div id="tab3" style="overflow:auto;width:auto;height:auto;background:#fee;">
<div id="tabtop3">
<!--<div id="tabtop-L3"  onmousedown="StartDrag(this);"   onmouseup="StopDrag(this);" onmousemove="Drag(this);"><strong style="color:red;font-size:20px">领导日程</strong></div>-->
<!--//<div id="tabtop-R3" onclick="closeLayer()"><strong>[关闭]</strong></div>-->
</div>
<div id="tabcontent3" >
<input type="hidden" id="status" name="status" value="1" />
<input type="hidden" id="uuid" name="uuid" />
<table>
<tr>
<td align="left" width="20%">日程名称:</td>
<td><input type="text" id="scheduleName" name="scheduleName" size="50"/></td>
</tr>
<tr>
<td align="left" width="20%">地点:</td>
<td><input type="text" id="address" name="address" size="50"/></td>
</tr>
<tr>
<td align="left" width="20%">时间:</td>
<td>
<input type="radio" name="radiobutton" onclick="changeTime(1);" value="radiobutton" checked="checked"/>整天 
<input type="radio" name="radiobutton" onclick="changeTime(2);" value="radiobutton" />上午
<input type="radio" name="radiobutton" onclick="changeTime(3);" value="radiobutton" />下午
<input type="radio" name="radiobutton" onclick="changeTime(4);" value="radiobutton" />晚上<br/>
<input type="text" size="7" id="startDate" name="startDate" value="2013-02-20"/><input type="text" id="startTime" name="startTime" size="2" value="08:00"/>-<input type="text" id="endDate" name="endDate" size="7" value="2013-02-22"/><input id="endTime" name="endTime" type="text" size="2" value="17:30"/>
</td>
</tr>
<tr>
<td align="left" width="20%">去向类型:</td>
<td>
<input type="radio" id="scheduleType" class="scheduleType"  name="scheduleType" value="1" checked="checked"/>在岗
<input type="radio" id="scheduleType" class="scheduleType" name="scheduleType" value="2" />公休
<input type="radio" id="scheduleType" class="scheduleType" name="scheduleType" value="3" />出差
<input type="radio" id="scheduleType" class="scheduleType" name="scheduleType" value="4" />请假
<input type="radio" id="scheduleType" class="scheduleType" name="scheduleType" value="5" />开会
<input type="radio" id="scheduleType" class="scheduleType" name="scheduleType" value="6" />外出工作
<input type="radio" id="scheduleType" class="scheduleType" name="scheduleType" value="7" />学习培训
<input type="radio" id="scheduleType" class="scheduleType" name="scheduleType" value="8" />待定
</td>
</tr>
<tr>
<td align="left" width="20%">颜色:</td>
<td>
<input type="radio" id="mycolor" class="mycolor"  name="mycolor" value="" checked="checked" />无
<input type="radio" id="mycolor" class="mycolor" name="mycolor" value="red" />红色
<input type="radio" id="mycolor" class="mycolor" name="mycolor" value="blue" />蓝色
<input type="radio" id="mycolor" class="mycolor" name="mycolor" value="gray" />灰色
</td>
</tr>
<tr>
<td align="left" width="20%"><input type="checkbox" id="isHint" name="isHint" /> 设置提醒:</td>
<td>提醒对象:<input type="checkbox" id="hintCreator" name="hintCreator"/> 创建人:
<input type="checkbox" id="hintPlease" name="hintPlease"/> 被邀请人:
<input type="checkbox" id="hintView" name="hintView"/> 查看人:<br/>
<div><div align="left">提醒方式:<input type="checkbox" id="onlineMessage" name="onlineMessage"/> 在线消息:
<input type="checkbox" id="mail" name="mail"/> 邮件:
<input type="checkbox" id="mobil" name="mobil"/> 手机短信:</div><div align="right">提前<input type="text" size="1" id="hintDay" name="hintDay" value="0"/>天<input type="text" size="1" id="hintHour" name="hintHour" value="0"/>小时<input type="text" id="hintMinute" name="hintMinute"  size="1" value="30"/>分</div>
</div>
</td>
</tr>

<tr>

<td align="left" width="20%">领导:</td>
<td><input type="text" name="leaderNames" id="leaderNames" size="20" /><input size="20" type="text" name="leaderIds" id="leaderIds"/><button onclick="openUser();">选择</button></td>
</tr>
<tr>
<td align="left" width="20%">被邀请人:</td>
<td><input  type="text" name="pleases"  id="pleases"  size="20"/><input  type="text" name="pleaseIds"  id="pleaseIds"  /></td>
</tr>
<tr>
<td align="left" width="20%">是否允许他人查看:</td>
<td><input type="radio" id="isView" class="isView" name="isView" value="1" checked="checked"/>允许部分人查看
<input type="radio" id="isView" name="isView" class="isView" value="2" />公开
<input type="radio" id="isView" name="isView" class="isView" value="3" />不允许</td>
</tr>
<tr>
<td align="left" width="20%">查看人:</td>
<td><input  type="text" id="views" name="views"  size="20"/><input  type="text" id="viewIds" name="viewIds"  size="50"/></td>
</tr>
</table>
<div align="center">
<table><tr><td>
<button onclick="addLeaderSchedule('${now}','1');">确定</button></td><td>
<button id="delsche" style="display: none" onclick="deleteLeaderSchedule('${now}','1');">删除</button></td><td>
<button onclick="return closeLayer();">取消</button></td><td>
</td></tr></table>
</div>

<!--</div>-->
</div>
</div>


	</div>
</body>
</html>