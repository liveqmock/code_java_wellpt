<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>calendar</title>
	<%@ include file="/pt/common/meta.jsp"%>

</head>
<body>
	<!-- 引入css文件 -->
	<link type="text/css" rel="stylesheet" href="${ctx}/resources/theme/css/scheMonth.css" />
	<link type="text/css" rel="stylesheet" href="${ctx}/resources/ztree/css/zTreeStyle/zTreeStyle.css" />
	<link type="text/css" rel="stylesheet" href="${ctx}/resources/ztree/css/zTreeStyle/zTreeOutlookMenu.css" />
	<link type="text/css" rel="stylesheet" href="${ctx}/resources/fileupload/jquery.fileupload-ui.css" />
	<link type="text/css" rel="stylesheet" href="${ctx}/resources/fileupload/fileupload.css" />

	<div class="left_menu_div" >
		<ul class="nav nav-tabs">
			<li class="active"><a href="#"><spring:message code="hrManager.label.scheNavigation" /></a></li>
		</ul>
		<div class="left_menu_content">
			<ul id="hr_tree" class="ztree" ></ul>
		</div>
		<!-- <div id="annex_div" style="width: 100px;height: 100px;background-color: silver;"></div> -->
		<!-- <input id="annex_ipt" type="button" value="subbmit" /> -->
		<!-- <input id="fileupload_schedule_ipt" type="file" /> -->
	</div>
	<div class="right_content_div">
		
	</div>


	<!-- 引入js页面 -->
	<script type="text/javascript" src="${ctx}/resources/jquery/jquery.js"></script>
	<script type="text/javascript" src="${ctx}/resources/pt/js/org/unit/jquery.unit.js"></script>
	<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
	<script type="text/javascript" src="${ctx}/resources/My97DatePicker/WdatePicker.js"></script>
	<!-- 国际化文件 -->
	<script type="text/javascript" src="${ctx}/resources/schedule/global_zh_CN.js"></script>
	<script type="text/javascript" src="${ctx}/resources/ztree/js/jquery.ztree.js"></script>
	
	<script type="text/javascript" src="${ctx}/resources/fileupload/js/jquery.fileupload.js"></script>	
	<script type="text/javascript" src="${ctx}/resources/fileupload/js/jquery.iframe-transport.js"></script>	
	<script type="text/javascript" src="${ctx}/resources/fileupload/js/jquery.fileupload-process.js"></script>	
	<script type="text/javascript" src="${ctx}/resources/fileupload/js/jquery.fileupload-fp.js"></script>	
	<script type="text/javascript" src="${ctx}/resources/fileupload/js/jquery.fileupload-ui.js"></script>	
	<script type="text/javascript" src="${ctx}/resources/fileupload/js/uuid.js"></script>	
	<script type="text/javascript">

		$(document).ready(function(){
			//
			var setting = {
				view:{
					//设置 zTree 是否显示节点之间的连线。默认值：true
					showLine: false,
					//设置 zTree 是否显示节点的图标。默认值：true
					showIcon: false,
					//设置是否允许同时选中多个节点。默认值：true
					selectedMulti: false,
					//双击节点时，是否自动展开父节点的标识.默认值: true
					dblClickExpand: false,
					//用于在节点上固定显示用户自定义控件
					//1. 大数据量的节点加载请注意：在 addDiyDom 中针对每个节点 查找 DOM 对象并且添加新 DOM 控件，肯定会影响初始化性能；如果不是必须使用，建议不使用此功能
					//2. 属于高级应用，使用时请确保对 zTree 比较了解。
					//默认值：null
					addDiyDom:addDiyDom
				},
				data: {
					simpleData: {
						enable: true
					}
				},
				/* async:{
					
					enable : true,
					contentType : "application/json",
					//Ajax 获取数据的 URL 地址。[setting.async.enable = true 时生效]默认值：""
					url : ctx + "/json/data/services",
					//
					otherParam : {
						"serviceName" : "scheduleTagService",
						"methodName" : "getCalendarTree"
					},
					type : "POST"
				},
				*/
				//回调函数
				callback:{
					beforeClick: beforeClick
				} 
			};
	
			/* var zNodes =[
							{id:1,pId:0,name:global.myCalendar,isParent:true,open:true },
							{id:3,pId:0,name:global.createCalendar,isParent:true},
							{id:2,pId:0,name:global.myAttention,isParent:true,open:true}
						]; */
			
			//树形数据
			var zNodes =[
			      { id:1,name:"人事档案",isParent:true, open:true,children: []},
					
				  { id:2,name:"人员管理", open:false,
						children: [
							{id:21, name:"员工入职",
								children: [
									{ name:"leaf node 111"},
									{ name:"leaf node 114"}
								]},
							{id:22, name:"员工转正",
								children: [
									{ name:"leaf node 121"},
									{ name:"leaf node 124"}
								]},
							{id:23, name:"员工调动"},
							{id:24, name:"员工离职"}
						]},
						
				 { id:3,name:"奖惩管理", open:false,
					children: [
						{id:31, name:"pNode",
							children: [
								{ name:"leaf node 113"},
								{ name:"leaf node 114"}
							]}
					]},
				{ id:4,name:"合同管理", open:false,
					children: [
						{id:41, name:"pNode 11",
							children: [
								{ name:"leaf node 113"},
								{ name:"leaf node 114"}
							]}
					]},
								
				{ id:5,name:"培训管理", open:false,
					children: [
						{id:51, name:"pNode 11",
							children: [
								{ name:"leaf node 113"},
								{ name:"leaf node 114"}
							]}
						
					]}
				]
			// 子节点单击事件 
			function beforeClick(treeId,treeNode){
				if(treeNode.isParent){
					return false;
				}else{
					reqData={
							"ctype":treeNode.getParentNode().id,
							"ctype":treeNode.id
						};
					$.ajax({
						type : "POST",
						data:reqData,
						url : ctx + "/app/hr/treeNode_click",
						dataType : "text",
						success : function callback(result) {
							$(".right_content_div").html(result);
						}
					});
					return true;
				}
			}
			
			// 初始化导航树 
			$.fn.zTree.init($("#hr_tree"), setting, zNodes);
			
			// 异步加载节点数据 
			$.ajax({
				type : "POST",
				data:{},
				url : ctx + "/app/hr/personal_records_index",
				dataType : "text",
				success : function callback(result) {
					$(".right_content_div").html(result);
				}
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
		
		
	</script>
</body>
</html>