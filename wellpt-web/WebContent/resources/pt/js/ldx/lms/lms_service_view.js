var File = {};
$(function() {
	/**
	 * 固定资产/低值易耗品报废申请/不同主体固资/计量器具/委外校准申请/资产调拨
	 */
	File.searchLabfile = function(id){
		$.ajax({
			async : false,
			url : ctx + "/lms/search/labfile",
			type:"POST",
			dataType:"json",
			data:{
				"labfileNo":$("#labfile_no").val()
			},
			success : function(res) {
				var arr = eval(res);
				oAlert2(arr.length);
			}
		});
	};
	/**
	 * 固定资产更新
	 */
	File.updateLabdileGDZC = function(){
		JDS.call({
			service : "lMSService.updateLabfile",
			data : [$("#labfile_no").val(),$("#labfile_state").val(),$("#labfile_scrappedno").val()],
			async : false,
			success : function() {
				oAlert2("保存成功");
			}
		});
	};
	/**
	 * 不同主体固定资产出售更新
	 */
	File.updateLabdileBTZT = function(){
		JDS.call({
			service : "lMSService.updateLabfileBTZT",
			data : [$("#labfile_no").val(),
			        $("#labfile_assetsno").val(),
			        $("#labfile_mainhave").val(),
			        $("#labfile_address").val()],
			async : false,
			success : function() {
				oAlert2("保存成功");
			}
		});
	};
	/**
	 * 计量器具领用
	 */
	File.searchLabfile = function(id){
		$.ajax({
			async : false,
			url : ctx + "/lms/search/labfile",
			type:"POST",
			dataType:"json",
			data:{
				"labfileNo":$("#labfile_no").val()
			},
			success : function(res) {
				var arr = eval(res);
				oAlert2(arr.length);
			}
		});
	};
	
	File.updateMeter = function(){
		JDS.call({
			service : "lMSService.updateMeterAccess",
			data : [$("#labfile_no").val(),$("#labfile_address").val(),$("#labfile_claim").val()],
			async : false,
			success : function() {
				oAlert2("保存成功");
			}
		});
	};
	
	/**
	 * 校准计划通知单查询
	 */
	File.searchJZJH = function(){
		$.ajax({
			async : false,
			url : ctx + "/lms/search/jzjh",
			type:"POST",
			dataType:"json",
			data:{
				"labfile_havedate":$("#labfile_havedate").val()
			},
			success : function(res) {
				var arr = eval(res);
				oAlert2(arr.length);
			}
		});
	};
});