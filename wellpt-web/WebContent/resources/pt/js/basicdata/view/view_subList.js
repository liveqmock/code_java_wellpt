$(function() {
	$(".subTableDate").parents(".view_content_list").hide();
	$(".subTableDate").each(function(){
		var dataId = $(this).attr("dataid");
		var tableId = $(this).attr("tableid");
		var fieldName = $(this).text();
		var this_ = $(this);
//		var data_ = new Object();
//		data_.formUid = tableId;
//		data_.dataUid = dataId;
//		data_.fieldName = fieldName;
//		alert(JSON.stringify(data_));
		JDS.call({
			async : false,
			service : "formDataService.getSubFormFieldValue",
			data : [tableId,dataId,fieldName],
			success:function(result) {
				this_.text(result.data);
			}
		});
	});
	$(".subTableDate").parents(".view_content_list").show();
});