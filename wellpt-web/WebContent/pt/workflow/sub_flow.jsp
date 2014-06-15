<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/pt/common/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${ctx}/resources/jquery/jquery.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/artDialog/jquery.artDialog.js?skin=default"></script>
<script type="text/javascript"
	src="${ctx}/resources/artDialog/plugins/iframeTools.js"></script>
<script type="text/javascript"
	src="${ctx}/pt/workflow/js/jquery.workflow.js"></script>
<script type="text/javascript" src="${ctx}/pt/workflow/js/property.js"></script>
<link href="${ctx}/pt/workflow/css/Dialog.css" rel="stylesheet"
	type="text/css">
<script type="text/javascript">
<!--
	InitProperty("新流程属性", true);
// -->
</script>
<script type="text/javascript">
	$(function() {
		//onload事件
		SubFlowLoadEvent();
		//bInitPageLabel();
		//键盘事件
		$(document).keypress(function(e) {
			var keyCode = e.which;
			if (keyCode == 13) {
				$("#ok").click();
			}
			if (keyCode == 27) {
				//window.close();
				$.dialog.data("goProperty", null);
				$.dialog.close();
			}
		});
		//确定事件
		$("#ok").click(function(e) {
			SubFlowOKEvent();
		});
		//取消事件
		$("#cancel").click(function(e) {
			//window.close();
			$.dialog.data("goProperty", null);
			$.dialog.close();
		});
		//选择子流程
		$("#subFlow").click(function(e) {
			bSubFlowActions(1, null, e);
		});
		// 增加子流程
		$("#newFlowAdd").click(function(e){
			var reValCallback = function() {
				var lsReturn = $.dialog.data("newFlowReVal");
				$.dialog.data("newFlowReVal", null);
				if (lsReturn == null) {
					return;
				}
				var laValue = lsReturn.name + "|" + lsReturn.value + "|" + lsReturn.conditionName
				+ "|" + lsReturn.conditionValue + "|" + lsReturn.creatName + "|" + lsReturn.creatValue
				+ "|" + lsReturn.isMerge + "|" + lsReturn.isWait + "|" + lsReturn.isShare + "|" + lsReturn.toTaskName + "|" + lsReturn.toTaskId
				+ "|" + lsReturn.copyFields + "|" + lsReturn.returnFields;
				var laName = lsReturn.name + "|" + lsReturn.conditionName + "|" + lsReturn.creatName
				+ "|" + (lsReturn.isMerge == "1" ? "是" : "否") + "|" + (lsReturn.isWait == "1" ? "是" : "否")
				+ "|" + (lsReturn.isShare == "1" ? "是" : "否") + "|" + lsReturn.toTaskName ;
				bAddEntry(goForm.DNewFlows, laName, laValue);
			};
			sGetNewFlow(null, reValCallback);
		});
		// 编辑子流程
		$("#newFlowEdit").click(function(e){
			var lsValue = sGetAllEntryValue(goForm.DNewFlows, true);
			if (lsValue == null || lsValue == "") {
				return false;
			}
			var lsValues = lsValue.split("|");
			// 转化为对象参数
			var lsArgs = {};
			lsArgs.name = lsValues[0];
			lsArgs.value = lsValues[1];
			lsArgs.conditionName = lsValues[2];
			lsArgs.conditionValue = lsValues[3];
			lsArgs.creatName = lsValues[4];
			lsArgs.creatValue = lsValues[5];
			lsArgs.isMerge = lsValues[6];
			lsArgs.isWait = lsValues[7];
			lsArgs.isShare = lsValues[8];
			lsArgs.toTaskName = lsValues[9];
			lsArgs.toTaskId = lsValues[10];
			lsArgs.copyFields = lsValues[11];
			lsArgs.returnFields = lsValues[12];
			var reValCallback = function() {
				var lsReturn = $.dialog.data("newFlowReVal");
				$.dialog.data("newFlowReVal", null);
				if (lsReturn == null) {
					return;
				}
				var laValue = lsReturn.name + "|" + lsReturn.value + "|" + lsReturn.conditionName
				+ "|" + lsReturn.conditionValue + "|" + lsReturn.creatName + "|" + lsReturn.creatValue
				+ "|" + lsReturn.isMerge + "|" + lsReturn.isWait + "|" + lsReturn.isShare + "|" + lsReturn.toTaskName 
				+ "|" + lsReturn.toTaskId + "|" + lsReturn.copyFields + "|" + lsReturn.returnFields;
				var laName = lsReturn.name + "|" + lsReturn.conditionName + "|" + lsReturn.creatName
				+ "|" + (lsReturn.isMerge == "1" ? "是" : "否") + "|" + (lsReturn.isWait == "1" ? "是" : "否")
				+ "|" + (lsReturn.isShare == "1" ? "是" : "否") + "|" + lsReturn.toTaskName ;
				bEditEntry(goForm.DNewFlows, laName, laValue);
			};
			sGetNewFlow(lsArgs, reValCallback);
		});
		// 删除子流程
		$("#newFlowDelete").click(function(e){
			bMoveEntry(goForm.DNewFlows);
		});
		function sGetNewFlow(psValue, reValCallback) {
			var laArg = new Array();
			laArg.value = (psValue == null) ? "" : psValue;
			laArg.opener = opener;
			// return opener.vOpenModal_("remark.htm", laArg, 400, 180);
			var liWidth = 500;
			var liHeight = 380;
			openDialog("flow/select.htm", "新流程", liWidth, liHeight, "newFlowLaArg", laArg,
					reValCallback);
		}
	});
</script>
</head>

<body style="overflow-x: hidden; overflow-y: auto;">
	<form>
		<table cellpadding="0" cellspacing="0" border="0" style="width: 100%;">
			<tr style="height: 30">
				<td width="50" align=right>名称：</td>
				<td width="205"><input NAME="name" VALUE=""
					CLASS="Border_Inset" STYLE="width: 100%"></td>
				<td width="50" align=right>ID：</td>
				<td width="100"><input NAME="id" VALUE="" CLASS="Border_Inset"
					STYLE="width: 100%"></td>
				<td align="right" width="180"><input type="button" id="ok"
					value="  确定  " class="BORDER_BUTTON">&nbsp;&nbsp;<input
					type="button" id="cancel" class="BORDER_BUTTON" value="  取消  "></td>
				<td width="15">&nbsp;</td>
			</tr>
		</table>
		<div id="ID_PageLabel" class="PageLabel"
			style="padding: 10; width: 570; height: 300px;">
			<div id=ID_PageContent style="display: none;" Label="子流程">
				<fieldset style="width: 100%; height: 100%">
					<legend style="color: black;">子流程</legend>
					<table cellpadding="3" cellspacing="5" border="0" width="100%"
						style="height: 100%">
						<tr style="height: 25px">
							<td>
								<table>
									<thead>
										<tr>
											<td>流程名称|</td>
											<td>触发条件|</td>
											<td>创建实例的字段|</td>
											<td>合并等待|</td>
											<td>共享|</td>
											<td>提交环节</td>
										</tr>
									</thead>
								</table>
							</td>
						</tr>
						<tr style="height: 200px">
							<td><select id="DNewFlows" name="DNewFlows" size="6"
								style="width: 98%; height: 100%"></select>&nbsp;</td>
							<td align="center" width="15%"><input type="button"
								id="newFlowAdd" value=" 增加 " class="BORDER_BUTTON" /> <input
								type="button" id="newFlowEdit" value=" 编辑 "
								class="BORDER_BUTTON" /> <input type="button"
								id="newFlowDelete" value=" 删除 " class="BORDER_BUTTON" /></td>
						</tr>
					</table>
				</fieldset>
			</div>
		</div>
	</form>
</body>
</html>