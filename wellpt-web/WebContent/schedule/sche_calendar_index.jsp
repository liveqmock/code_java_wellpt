<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>calendar</title>
	<%@ include file="/pt/common/meta.jsp"%>

<body>
	<link type="text/css" rel="stylesheet" href="${ctx}/resources/theme/css/scheMonth.css" />
	<link type="text/css" rel="stylesheet" href="${ctx}/resources/ztree/css/zTreeStyle/zTreeStyle.css" />
	<link type="text/css" rel="stylesheet" href="${ctx}/resources/ztree/css/zTreeStyle/zTreeOutlookMenu.css" />
	<link type="text/css" rel="stylesheet" href="${ctx}/resources/fileupload/jquery.fileupload-ui.css" />
	<link type="text/css" rel="stylesheet" href="${ctx}/resources/fileupload/fileupload.css" />
	
	<div class="left_menu_div" >
		<ul class="nav nav-tabs">
			<li class="active"><a href="#"><spring:message code="schedule.label.scheNavigation" /></a></li>
		</ul>
		<div class="left_menu_content">
			<ul id="calendar_tree" class="ztree" ></ul>
		</div>
		<!-- <div id="annex_div" style="width: 100px;height: 100px;background-color: silver;"></div> -->
		<!-- <input id="annex_ipt" type="button" value="subbmit" />
		<input id="fileupload_schedule_ipt" type="file" /> -->
	</div>
	<div class="right_content_div">
		
	</div>
	
	<script type="text/javascript" src="${ctx}/resources/jquery/jquery.js"></script>
	<script type="text/javascript" src="${ctx}/resources/pt/js/org/unit/jquery.unit.js"></script>
	<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
	<script type="text/javascript" src="${ctx}/resources/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="${ctx}/resources/schedule/global_zh_CN.js"></script>
	<script type="text/javascript" src="${ctx}/resources/ztree/js/jquery.ztree.js"></script>
		
	<script type="text/javascript" src="${ctx}/resources/pt/js/fileupload/well.fileupload.constant.js"></script>
	<script type="text/javascript" src="${ctx}/resources/pt/js/fileupload/well.fileupload.js"></script>
	<script type="text/javascript" src="${ctx}/resources/pt/js/fileupload/well.fileupload4Icon.js"></script>
		
		
	
	<script type="text/javascript">
		var treeObj ;
		$(document).ready(function(){
			$(function(){
			var setting = {
				view:{
					showLine: false,
					showIcon: false,
					selectedMulti: false,
					dblClickExpand: false,
					addDiyDom : addDiyDom
				},
				data: {
					simpleData: {
						enable: true
					}
				},
				async:{
					enable : true,
					contentType : "application/json",
					url : ctx + "/json/data/services",
					otherParam : {
						"serviceName" : "scheduleTagService",
						"methodName" : "getCalendarTree"
					},
					type : "POST"
				},
				callback:{
					beforeClick: beforeClick,
					onAsyncSuccess: zTreeOnAsyncSuccess
				}
			};
		
			var zNodes =[
				{id:1,pId:0,name:scheglobal.myCalendar,isParent:true,open:true },
				{id:3,pId:0,name:scheglobal.createCalendar,isParent:true},
				{id:2,pId:0,name:scheglobal.myAttention,isParent:true,open:true}
			];
			// 初始化导航树 
			treeObj = $.fn.zTree.init($("#calendar_tree"), setting);
			
			/* // 显示右侧日历 
			$.ajax({
				type : "POST",
				data:{},
				url : ctx + "/schedule/month/view",
				dataType : "text",
				success : function callback(result) {
					$(".right_content_div").html(result);
				}
			}); */

			
			// 测试附件点击事件 
			$("#annex_ipt").click(function(){
				var content ="";
				$.ajax({
					type : "POST",
					async:false,
					data:{},
					url : ctx + "/schedule/schedule/single",
					dataType : "text",
					success : function callback(result) {
						content = result;
					}
				});

				var json = 
				{
					title:scheglobal.addSchedule,  /*****标题******/
					autoOpen: true,  /*****初始化之后，是否立即显示对话框******/
					modal: true,     /*****是否模式对话框******/
					closeOnEscape: true, /***当按 Esc之后，是否应该关闭对话框****/
					draggable: true, /*****是否允许拖动******/  
					resizable: true, /*****是否可以调整对话框的大小******/  
					stack : false,   /*****对话框是否叠在其他对话框之上******/
					height: 600, /*****弹出框高度******/
					width: 680,   /*****弹出框宽度******/
					content: content,/*****内容******/
					//open：事件,
					buttons: {
						"确定":addSchedule
					}
				};
				showDialog(json);
			});
			/*  debugger;
			var schedule_fileupload_id = 'fileupload_schedule_ipt'; 
			var schedule_fileupload = new WellFileUpload(schedule_fileupload_id);
			schedule_fileupload.init(false,  $('#annex_div'),  false, true , []);
			$('#annex_ipt').click(function(){
					 alert('请查看日志');
			 });   
			 debugger; */
			
			});
		});
		
		// 重写树节点的添加 
		function addDiyDom(treeId, treeNode) {
			var spaceWidth = 5;
			var switchObj = $("#" + treeNode.tId + "_switch"),
			icoObj = $("#" + treeNode.tId + "_ico");
			switchObj.remove();
			icoObj.before(switchObj);

			if (treeNode.level > 1) {
				var spaceStr = "<span style='display: inline-block;width:" + (spaceWidth * treeNode.level)+ "px'></span>";
				switchObj.before(spaceStr);
			}
		}
		
		// 节点单击事件 
		function beforeClick(treeId,treeNode){
			// 请求参数 
			var reqData={
					"dateStr":$("#queryDate").val(),
					"ctype":treeNode.id,
					"viewType":$("#viewType").val()
				};
			$.ajax({
				type : "POST",
				data:reqData,
				url : ctx + "/schedule/month/view",
				dataType : "text",
				success : function callback(result) {
					$(".right_content_div").html(result);
					return true;
				},
				error:function (msg){
					return false;
				}
			});
			
		}
		
		// 异步加载成功后执行函数 
		function zTreeOnAsyncSuccess(event, treeId, treeNode, msg){
			// 针对根进行异步加载时，选中并触发第一个节点的点击事件 
			if(treeNode==null){
				var nodes = treeObj.getNodes();
				if (nodes.length>0) {
					treeObj.selectNode(nodes[0]);
					beforeClick(treeId,nodes[0]);
					for(var i=0;i<nodes.length;i++){
						treeObj.expandNode(nodes[i],null,false,true,false);
					}
				}
			}
		}
	</script>
	

</body>
</html>