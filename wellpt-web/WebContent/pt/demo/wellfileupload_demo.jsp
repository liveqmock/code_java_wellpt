<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>


<!DOCTYPE html>
<html lang="en">
<head>
 <c:set var="ctx" value="${pageContext.request.contextPath}" />
 
 <%-- <link rel="stylesheet" href="${ctx}/resources/theme/css/wellnewoa.css  " type="text/css" /> --%>
 <c:set var="ctx" value="${pageContext.request.contextPath}" />
<link rel="stylesheet" href="${ctx}/resources/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css" />
<link rel="stylesheet" href="${ctx}/resources/pt/css/jquery-ui-1.8.21.custom.css" type="text/css" />
<link rel="stylesheet" type="text/css" href="${ctx}/resources/jqueryui/css/base/jquery-ui.css" />
<link href="${ctx}/resources/jBox/Skins/Blue/jbox.css" rel="stylesheet" type="text/css" /> 

<link rel="stylesheet" type="text/css" media="screen"
href="${ctx}/resources/jqgrid/themes/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css" media="screen"
href="${ctx}/resources/jqgrid/themes/ui.multiselect.css" /> 
<script type="text/javascript" src="${ctx}/resources/jquery/jquery.js"></script>
<script type="text/javascript" src="${ctx}/resources/utils/json2.js"></script>
<script type="text/javascript" src="${ctx}/resources/bootstrap/js/bootstrap.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/control/common/jquery.wControlUtil.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/control/common/jquery.wabstractCtl.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/control/common/jquery.wControlInterface.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/control/common/jquery.wTextCommonMethod.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/control/subform/jquery.wSubFormMethod.js"></script>
 
 <script type="text/javascript" src="${ctx}/resources/pt/js/dyform/common/dyform_constant.js"></script>
 
 
 <link rel="stylesheet"
	href="${ctx}/resources/bootstrap/css/bootstrap.min.css" />
 <link rel="stylesheet" type="text/css" 
		href="${ctx}/resources/theme/css/wellnewoa.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/fileupload/jquery.fileupload-ui.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/fileupload/fileupload.css" />
	
<link rel="stylesheet" type="text/css" media="screen"
	href="${ctx}/resources/pt/css/form.css" />
	
<script type="text/javascript" src="${ctx}/resources/jquery/jquery.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/pt/js/fileupload/well.fileupload.constant.js"></script>

<script type="text/javascript"
	src="${ctx}/resources/utils/ajaxfileupload.src.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/utils/ajaxfileupload.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/fileupload/js/jquery.fileupload.js"></script>


<script type="text/javascript"
	src="${ctx}/resources/pt/js/fileupload/well.fileupload.js"></script>
	

<script type="text/javascript" src="${ctx}/resources/pt/js/control/jquery.wRelation.js"></script>
	
	


<title>统一上传附件 示例</title>
 
</head>
<body id="explainBody">
	 
	<input type="hidden" id="text" value='请选择未完成工作' >
	<input type="hidden" id="subtable" value="001">
	<button id="btn_fileupload_ctlid_multiple_demo" type="button" value="提交多文件上传控件的文件信息">提交多文件上传控件的文件信息</button>
	<button id="btn_fileupload_ctlid_single_demo" type="button" value="提交单文件上传控件的文件信息">提交单文件上传控件的文件信息</button>
 	
	<!-- 自定义表单 -->
	<form action="">
		<label>统一上传控件示例</label> <input type="text" id="creator" name="creator" />
	</form>
	<!-- 动态表单 -->
	<div>
		简单多文件上传
		<div id="abc">
			 
		</div>
	</div>
	
	<br/>
	<div>
		简单单文件上传
		<div id="abcd">
			 
		</div>
	</div>
	
	<div>
		<div id="relativedropdiv">
			 
		</div>
	</div>
	
	
	<div>
		<input id="current_form_url_prefix" type="hidden"
			value="${ctx}/dytable/demo"> <a
			id="current_form_url" href="" target="_blank" style="display: none">查看当前动态表单</a>
	</div>
				 
<%@ include file="/pt/dytable/form_js.jsp"%>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/dytable/grid_demo.js"></script>
		<script type="text/javascript">
 
 $(function(){

		//创建上传控件
		 var ctlID = "fileupload_ctlid_multiple_demo";
		 var fileupload = new WellFileUpload(ctlID);

		//初始化上传控件
		//fileupload.initWithLoadFilesFromFileSystem(false,  $attachContainer,  formSign == "2", true, idValue, fieldName);
		 //初始化上传控件
			fileupload.init(false,  $("#abc"),  false, true, []);
		 
		 $("#btn_"  + ctlID).click(function(){
			 alert("请查看日志");
			 console.log(JSON.stringify(WellFileUpload.files[ctlID]));
		 });
		 
			//创建上传控件
			var ctlID2 = "fileupload_ctlid_single_demo"; 
			fileupload = new WellFileUpload(ctlID2);

			//初始化上传控件
			//fileupload.initWithLoadFilesFromFileSystem(false,  $attachContainer,  formSign == "2", true, idValue, fieldName);
			 //初始化上传控件
				fileupload.init(false,  $("#abcd"),  false, false, []);
			 
			 
			 
				 
				 $("#btn_"  + ctlID2).click(function(){
					 alert("请查看日志");
					 console.log(JSON.stringify(WellFileUpload.files[ctlID2]));
				 }); 
				 
				 var columnProperty={
							//控件字段属性
							applyTo:"test",//应用于
							controlName:"test",//控件名称，由外面指定 
							columnName:"test",//字段定义  fieldname
							displayName:"displayName",//描述名称  descname
							dbDataType:"1",//字段类型  datatype type
							indexed:"1",//是否索引
							showed:"",//是否界面表格显示
							sorted:"",//是否排序
							sysType:"2",//系统定义类型，包括三种（0：系统默认，1：管理员常量定义，2：表单添加后自定义）
							length:255,//长度
							//isHide:column.isHide,//是否隐藏
							showType:"1",//显示类型 1,2,3,4 datashow
							defaultValue:"2",//默认值
							valueCreateMethod:"1",//默认值创建方式 1用户输入
							onlyreadUrl:"",//只读状态下设置跳转的url
							realDisplay: {},
							formDefinition:{}
					};
					//控件公共属性
					var commonProperty={
							inputMode:20,//输入样式 控件类型 inputDataType
							fieldCheckRules:{},
							fontSize:"12",//字段的大小
							fontColor:"red",//字段的颜色
							ctlWidth:"20",//宽度
							ctlHight:"12",//高度
							textAlign:"left"//对齐方式
					};	
				 
				 $("#relativedropdiv").wrelation({
				    	columnProperty:columnProperty,
				    	commonProperty:commonProperty,
				    	relationDataTextTwo: "",		 	
						relationDataValueTwo:"f2a6385f-7177-4a7d-8aff-8e1b4ca114ee", 		 	
						relationDataTwoSql: "", 	
						relationDataDefiantion:  "{\"sqlTitle\":\"组织机构代码\",\"sqlField\":\"reservedText5\",\"formTitle\":\"单位编码\",\"formField\":\"dwbm\",\"search\":\"no\"}|{\"sqlTitle\":\"单位名称\",\"sqlField\":\"title\",\"formTitle\":\"unit\",\"formField\":\"unit\",\"search\":\"no\"}",
						relationDataShowMethod: '2', 	
						relationDataShowType: ""	
					});
		 
 });
 
 </script>
</body>
</html>
