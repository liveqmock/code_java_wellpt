var XZSP_BTNS = {};

$(function() {
	var xzsp_view = "/resources/app/js/xzsp/xzsp_view.js";
	
	// 项目接件事件处理
	XZSP_BTNS.receive = function(obj){
		 var object = $(obj).parents(".viewContent").find("input[class=checkeds]:checked");
		 if(object.length==0) {
			 oAlert("请先选择一笔项目！");
			 return false;
		 }
		 if(object.length!=1) {
			 oAlert("只能选择一笔项目接件！");
			 return false;
		 }
		 var projectUuid = $(object).val();
		 $.getScript(ctx + xzsp_view, function() {
			 XZSP.workflow.getMattersByLibId.call(this, 'MATTERS_LIB', projectUuid, 'CMS_GOIN');
		 });
	};
	
	// 网上申报项目接件事件处理
	XZSP_BTNS.receive2 = function(obj){
		 var object = $(obj).parents(".viewContent").find("input[class=checkeds]:checked");
//		 if(object.length==0) {
//			 oAlert("请先选择一笔项目！");
//			 return false;
//		 }
//		 if(object.length!=1) {
//			 oAlert("只能选择一笔项目接件！");
//			 return false;
//		 }
		 var projectUuid = $(object).val();
		 $.getScript(ctx + xzsp_view, function() {
			 XZSP.workflow.getMattersByLibId.call(this, 'MATTERS_LIB', projectUuid, 'CMS_GOIN','2');
		 });
	};
	
});