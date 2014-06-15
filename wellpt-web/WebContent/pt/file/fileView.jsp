<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
request.setAttribute("basepath",basePath);
%>
<%@ include file="/pt/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>
  		<spring:message code="fileManager.label.documentPreviewInterface" />  
    </title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="${basepath}resources/jquery/jquery.js"></script>
			<script type="text/javascript" src="${basepath}resources/pt/js/global.js"></script>
	<script type="text/javascript" src="${basepath}resources/pt/js/file/file_view.js"></script>
	<script type="text/javascript">
	alert(ctx);
	function addMouseWheelListener() {

		var flash = $FlexPaper('documentViewer');

		var ua = navigator.userAgent;

		console.log(ua.indexOf("Firefox"));

		if (ua.indexOf("Firefox") > -1) {

			flash.addEventListener('DOMMouseScroll', onWheelZoom, false);

		} else if (ua.indexOf("MSIE") == -1) {

			flash.addEventListener('mousewheel', onWheelZoom, false);

		} else {

			flash.attachEvent('onmousewheel', onWheelZoom);

		}

		flash.attachEvent('onmousewheel', onWheelZoom);

	}

	function onWheelZoom(event) {

		var app = $FlexPaper('documentViewer');

		if (app) {

			var o = {
				delta :event.wheelDelta
			};

			$FlexPaper('documentViewer').jsWheelZoom(o);

		}

	}
</script>
  </head>
  
  <body>
   <!--     <div style="margin-top:530px;margin-left:720px;">
      <a href="${basepath}file/fileAction!list.do">查看文件列表</a>
      </div>-->
      <div style="position:absolute;left:10px;top:10px;">
	        <a id="viewerPlaceHolder" style="width:1024PX;height:768PX;display:block"></a>
	        
	        <script type="text/javascript"> 
				var fp = new FlexPaperViewer(	
						 'FlexPaperViewer',
						 'viewerPlaceHolder', { config : {
						 SwfFile : '${basepath}upload/testswf.swf',
						 Scale : 0.99, 
						 ZoomTransition : 'easeOut',
						 ZoomTime : 0.5,
						 ZoomInterval : 0.2,
						 FitPageOnLoad : true,
						 FitWidthOnLoad : false,
						 FullScreenAsMaxWindow : false,
						 ProgressiveLoading : false,
						 MinZoomSize : 0.2,
						 MaxZoomSize : 5,
						 SearchMatchAll : false,
						 InitViewMode : 'SinglePage',
						 
						 ViewModeToolsVisible : true,
						 ZoomToolsVisible : true,
						 NavToolsVisible : true,
						 CursorToolsVisible : true,
						 SearchToolsVisible : true,
  						
  						 localeChain: 'en_US'
						 }});
	        </script>
	   	  
        </div>
  </body>
</html>
