var Ldx = {};
$(function() {
	$(".ldxCheckBox").each(function(){
		var ck = $(this).attr("ck");
		if(ck==1){
			$(this).attr("checked",true);
		}else if(ck==0){
			$(this).attr("checked",false);
		}
	});
	Ldx.NewProduct = function(formUuid) {
		var url = ctx+"/ldx/new_product?formUid="+formUuid+"&moduleid=d775a2cb-6bc5-4792-9ddc-3cd5ea8c1b74&wid=201441213014582";
		window.open(url);
	};
	Ldx.EditProduct = function(formUuid) {
		 $(".checkeds").each(function(){
			var $this = $(this);
			if($this.attr("checked")){
				var dataUuid = $this.val();
				var url = ctx+"/ldx/product?formUid="+formUuid+"&dataUid="+dataUuid+"&showSubTable=true&readOnly=true&moduleid=d775a2cb-6bc5-4792-9ddc-3cd5ea8c1b74&wid=201441213014582";
				window.open(url);
			}
		 });
	};
	 Ldx.DelProduct = function(formUuid) {
		 $(".checkeds").each(function(){
			var $this = $(this);
			if($this.attr("checked")){
				var dataUuid = $this.val();
				JDS.call({
					async:false,
					service : "formDataService.delete",
					data : [formUuid,dataUuid],
					success : function(result) {
						oAlert("删除成功",function (){
							location.reload(); 
						});
					}
				});
			} 
		 });
	 };
	 
	 Ldx.ExportProduct = function(formUuid) {
		 var checkes = new Array();
		 $(".checkeds").each(function(){
			var $this = $(this);
			if($this.attr("checked")){
				checkes.push($this.val());
			}
		 });
		 var url = ctx+"/ldx/export?checkes="+checkes+"&formUuid="+formUuid;
			window.open(url);
	 };
	 
	 Ldx.uploadPicture = function(formUuid) {
		 
	 }
});