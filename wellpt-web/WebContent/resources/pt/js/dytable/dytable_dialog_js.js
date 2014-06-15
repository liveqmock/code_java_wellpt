function testJs(value,jqGridId,id,name,val,iRow,iCol){
	$.ajax({
		async:false,
		url : ctx + value.relationDataShowType,
		success : function(data) {
			var json = new Object(); 
	        json.content = data;
	        json.title = "自定义弹出框";
	        json.height= 600;
	        json.width= 800;
	        showDialog(json);
		}
	});
}

function chooseName(value,jqGridId,id,name,val,iRow,iCol) {
	$.ajax({
		async:false,
		url : ctx + "/psi/stock_alert",
		success : function(data) {
			var json = new Object(); 
	        json.content = data;
	        json.title = "选择名称";
	        json.height= 600;
	        json.width= 800;
	        json.buttons = {
	        		"确定":function sure() {
	        			alert(1111);
	        		}
	        }
	        showDialog(json);
		}
	});
}