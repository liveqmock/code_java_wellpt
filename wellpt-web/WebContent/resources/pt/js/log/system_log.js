$(function() {
	var fromLineNumber = -1;
	var $log = $("#system_log");
	// 每1秒执行
	$("body").everyTime("1s", function() {
		$.ajax({
			type : "GET",
			url : ctx + "/log/system/get",
			data : {
				fromLineNumber : fromLineNumber
			},
			contentType : "application/json",
			dataType : "json",
			async : false,
			success : function(data, statusText, jqXHR) {
				$.each(data, function(i) {
					fromLineNumber = this.lineNumber;
					$log.append(this.content + "\n");
					$log.scrollTop($log[0].scrollHeight - $log.height());
				});
			}
		});
	});
});