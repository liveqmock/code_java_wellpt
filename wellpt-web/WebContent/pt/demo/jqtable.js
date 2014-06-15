$(function() {
	//加载数据例子 
	$("#tableid").jqGrid('setGridParam', { url: "ctx + '/common/jqgrid/query?queryType=user'"}, 
			{postData: ''}).trigger('reloadGrid');

});
