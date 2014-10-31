<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="/pt/common/taglibs.jsp"%>
<!DOCTYPE  html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title><spring:message code="schedule.label.scheNavigation" /></title>
	<%@ include file="/pt/common/meta.jsp"%>
</head>

<body>
	<div>
		<div id="toolbar">
			<span class="today" style="margin-left: 15px;"><spring:message code="schedule.label.setting" /></span>
		</div>
		<div class="settingBlock">
			<div class="scheSettingSeparate">
				<span>
					<spring:message code="schedule.label.myCalendar" />：
				</span>
			</div>
			<div class="scheSettingBlock">
				<table>
					<thead>
						<tr>
							<th><spring:message code="schedule.label.name" /></th>
							
							<th><spring:message code="schedule.label.projectParticipant" /></th>
							<th><spring:message code="schedule.info.sec" /></th>
							<th><spring:message code="schedule.label.allowSomeoneView" /></th>
							<th style="width: 80px;"><spring:message code="schedule.label.operate" /></th>
						</tr>
					</thead>
					<c:forEach items="${userTags }" var="tag" varStatus="varStatu">
						<tr>
							<td style="background-color: ${tag.color}">${tag.name }</td>
							
							<td>${tag.participantNames }</td>
							<td>${tag.secretaryNames }</td>
							<td>
								<c:choose>
									<c:when test="${1 eq tag.isView}">${tag.viewNames }</c:when>
									<c:when test="${2 eq tag.isView}"><spring:message code="schedule.label.public" /></c:when>
									<c:when test="${3 eq tag.isView}"><spring:message code="schedule.label.notAllow" /></c:when>
								</c:choose>
							</td>
							<td>
								<c:if test="${not varStatu.first}">
									<a class="iconmoveup" href="#" title="上移"  onclick="changeTagSort('${tag.uuid}','${userTags[varStatu.index-1].uuid }');"></a>
								</c:if>
								<c:if test="${not varStatu.last}">
									<a class="iconmovedown" href="#" title="下移"  onclick="changeTagSort('${tag.uuid}','${userTags[varStatu.count].uuid }');"></a>
								</c:if>
								<c:if test="${tag.userId eq userId }">
									<a class="editgroup" href="#" title="修改"
										onclick="editTag('${tag.uuid}','${tag.name}','${tag.color}','${tag.sort}',
											'${tag.participantIds}','${tag.participantNames}','${tag.secretaryIds}','${tag.secretaryNames}',
											'${tag.userId }','${tag.userName }','${tag.isView}','${tag.viewIds}','${tag.viewNames}');"></a>
									<a class="delgroup" href="#" title="删除" 
										onclick="deleteTag('${tag.uuid}');"></a>
								</c:if>
								
							</td>
						</tr>
					</c:forEach>
				</table>
				<button id=addCalendar  class="btn" onclick="editTag('','','','','','','','','','','','','');"><spring:message code="schedule.btn.addCalendar" /></button>
			</div>
			
			<div class="scheSettingSeparate">
				<span>
					<spring:message code="schedule.label.myAttention"/>：
				</span>
			</div>
			<div class="scheSettingBlock">
				<table>
					<thead>
						<tr>
							<th><spring:message code="schedule.label.personName" /></td>
							<th><spring:message code="schedule.label.attentionCalendar" /></td>
						</tr>
					</thead>
					<c:forEach items="${attentionMap }" var="atten">
						<tr>
							<td>${atten.value[0].attentionName}</td>
							<td>
								<c:forEach items="${atten.value}" var="attention">
									<span>${ attention.tagName}</span>
									<a  href="#" title="删除" onclick="delAttention('${attention.uuid}')" >
										<i class="icon-remove"></i>
									</a>
								</c:forEach>
							</td>
						</tr>
					</c:forEach>
				</table>
				<button id="setAttention" class="btn" ><spring:message code="schedule.btn.addAttention" /></button>
			</div>
			
			<div class="scheSettingSeparate">
				<span>
					<spring:message code="schedule.label.alwaysAddr" />：
				</span>
			</div>
			<div class="scheSettingBlock">
				<table>
					<thead>
						<tr>
							<th style="width:10px;"><spring:message code="schedule.label.serialNo" /></th>
							<th><spring:message code="schedule.info.address" /></th>
							<th style="width:80px;"><spring:message code="schedule.label.operate" /></th>
						</tr>
					</thead>
					<c:forEach items="${userAddrs }" var="addr" varStatus="varStatu">
						<tr>
							<td>${varStatu.count }</td>
							<td>${ addr.content}</td>
							<td>
								<a class="editgroup" href="#" title="修改" onclick="editAlwaysAddr('${addr.uuid}','${userId }','${addr.content }','${addr.setNo }');"></a>
								<a class="delgroup" href="#" title="删除" onclick="delScheduleSetting('${addr.uuid}');"></a>
							</td>
						</tr>
					</c:forEach>
				</table>
				<button id=addAddr  class="btn" onclick="editAlwaysAddr('','${userId}','','${fn:length(userAddrs) + 1}');">
					<spring:message code="schedule.btn.addAlwaysAddr" />
				</button>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="${ctx}/resources/jquery/jquery.js"></script>
	<script type="text/javascript" src="${ctx}/resources/jqueryui/js/jquery-ui.js"></script>
	<script type="text/javascript" src="${ctx}/resources/pt/js/org/unit/jquery.unit.js"></script>
	<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
	<script type="text/javascript" src="${ctx}/resources/schedule/sche.js"></script>
	<script type="text/javascript">
		
		// 点击添加关注选择人员
		 $("#setAttention").click(function(){
				$.unit.open({
					labelField:"",
					valueField:"",
					selectType : 4,
					multiple:false,
					afterSelect:attentionSet
				});
			}); 
		
		// 选择关注人员后显示其对当前用户公开的日历 
		function attentionSet(person){
			var userTag = [];
			$.ajax({
				type : "POST",
				async:false,
				data:{"personId":person["id"]},
				dataType:'text',
				url : ctx+"/schedule/tag/share_tag.action",
				success : function callback(result) {
					userTag = eval(result) ;
				}
			});
			if(userTag.length<1){
				alert("没有对您公开的日历！");
			}else{
				var content ="<div class='scheSettingBlock'><table><thead><tr><th>"
					+person["name"] +"  "+global.myVisibleCalendar
					+"<input id='attentionUserId' type='hidden' value='"+person["id"]+"'/></th></tr></thead></thead><tbody>";
				for(var key in userTag){
					content+="<tr class='dialog_tr'><td><input class='calendar_chkbx' type='checkbox'  value='"+userTag[key].uuid+"'>"
						+userTag[key].name+"</td></tr>";
				}
				content+="</tbody></table>";
				var json = 
				{
					title:global.checkAttentionCalendar,  //标题
					autoOpen: true,  //初始化之后，是否立即显示对话框
					modal: true,     //是否模式对话框
					closeOnEscape: true, //当按 Esc之后，是否应该关闭对话框
					draggable: true, //是否允许拖动
					resizable: true, //是否可以调整对话框的大小
					stack : false,   //对话框是否叠在其他对话框之上
					height: 400, //弹出框高度
					width: 250,   //弹出框宽度
					content: content,//内容
					//open：事件,
					buttons: {
						"确定":addAttention
					}
				};
				showDialog(json);
			}
		} 
		
		// 添加关注 
		function addAttention(){
			var calendarIds = "";
			$(".calendar_chkbx:checked").each(function(){
				calendarIds+=$(this).val()+";";
			});
			
			$.ajax({
				type : "POST",
				url : ctx+"/schedule/add_attention",
				dataType : "text",
				data:{"personId":$("#attentionUserId").val(),"calendarIds":calendarIds},
				success : function callback(result) {
					closeDialog();
					refreshSchedule(result);
				}
			});
		}
		
		// 删除关注 
		function delAttention(attentionId){
			if(confirm(global.deleteConfirm)){
				$.ajax({
					type : "POST",
					url : ctx+"/schedule/del_attention",
					dataType : "text",
					data:{"uuid":attentionId},
					success : function callback(result) {
						refreshSchedule(result);
					}
				});
			}
		}
		
		// 打开修改常用地址的dialog 
		function editAlwaysAddr(uuid,userId,content,setNo){
			var title = global.editAlwaysAddr;
			if(uuid==''){
				title = global.addAlwaysAddr;
			}
			var dialogContent = "<input id='settingId' type='hidden' value='" + uuid + "'/>"
				+ "<input id='settingUserId' type='hidden' value='" +userId + "'/>"
				+ "<input id='settingNo' type='hidden' value='" +setNo + "'/>"
				+ "<input id='settingContent' type='text' size='100' style='margin:7px;width:90%;' value='" + content + "' />";
			var json = 
			{
				title:title,  //标题
				autoOpen: true,  //初始化之后，是否立即显示对话框
				modal: true,     //是否模式对话框
				closeOnEscape: true, //当按 Esc之后，是否应该关闭对话框
				draggable: true, //是否允许拖动
				resizable: true, //是否可以调整对话框的大小
				stack : true,   //对话框是否叠在其他对话框之上
				height: 150, //弹出框高度
				width: 500,   //弹出框宽度
				content: dialogContent,//内容
				//open：事件,
				buttons: {
					"确定":editAddrSetting
				}
			};
			showDialog(json);
		}
		
		// 修改常用地址 
		function editAddrSetting(){
			var reqData = {};
			reqData.uuid=$("#settingId").val();
			reqData.userId=$("#settingUserId").val();
			reqData.setNo=$("#settingNo").val();
			reqData.content=$("#settingContent").val();
			
			reqData.setType="addr";
			
			$.ajax({
				type : "POST",
				url : ctx+"/schedule/setting/save",
				dataType : "text",
				data: reqData,
				success : function callback(result) {
					closeDialog();
					refreshSchedule(result);
				}
			});
		}
		
		// 删除日历设置 
		function delScheduleSetting(uuid){
			if(confirm(global.deleteConfirm)){
				$.ajax({
					type : "POST",
					url : ctx+"/schedule/setting/delete",
					dataType : "text",
					data: {"uuid":uuid},
					success : function callback(result) {
						closeDialog();
						refreshSchedule(result);
					}
				});
			}
		}
		
		function changeTagSort(tagId,anotherTagId){
			$.ajax({
				type : "POST",
				url : ctx+"/schedule/setting/tagsort",
				dataType : "text",
				data: {"tagId":tagId,"anotherTagId":anotherTagId},
				success : function callback(result) {
					refreshSchedule(result);
				}
			});
		}
	</script>
</body>
</html>