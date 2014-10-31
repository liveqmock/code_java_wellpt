var File = {};
$(function() {
	// 灯具ADCP审核表审批流程
	File.show_djadcpshb = function() {
		window.open(ctx + "/workflow/work/new/ZZGC-DJADCPSHBSPLC");
	};
	// 灯具可行性分析报告审批流程
	File.show_djkxxfxbg = function() {
		window.open(ctx + "/workflow/work/new/ZZGC-DJKXXFXBGSPLC");
	};
	// 灯具PDCP审核表审批流程
	File.show_djpdcpshb = function() {
		window.open(ctx + "/workflow/work/new/ZZGC-DJPDCPSHBSPLC");
	};
	// 灯具项目申请
	File.show_DJXMSQDSPLC = function() {
		window.open(ctx + "/workflow/work/new/ZZGC-DJXMSQDSPLC");
	};
	// LED替换灯项目申请单
	File.show_LEDTHDXMSQD = function() {
		window.open(ctx + "/workflow/work/new/ZZGC_LEDTHDXMSQD");
	};
	// EP申请单审批流程
	File.show_EPSQDSPLC = function() {
		window.open(ctx + "/workflow/work/new/ZZGC_EPSQDSPLC");
	};
	// 工艺评审表审批流程
	File.show_EPGYPSBSPLC = function() {
		window.open(ctx + "/workflow/work/new/ZZGC-EPGYPSBSPLC");
	};
});
function create_form(lcId){
	window.open(ctx + "/workflow/work/new/"+lcId);
}
/** 流程状态对应值 * */
/** 00 草稿 * */
/** 01 待审核 * */
/** 11 发布 * */
/** 02 不通过 * */
/** 12 归档 * */

function upVersion(add) {
	if (confirm("是否将该数据升版？")) {
		var temp = $(".checkeds:checked");
		if (temp.length == 1) {
			var bh = "", bb = "", zt = "",formuuid = "";
			var datas = readParam();
			datas.forEach(function(obj) {
				bh = obj.bH;
				bb = obj.bB;
				zt = obj.zT;
				formuuid = obj.formUuid;
			});
			if (zt != "发布") {
				oAlert("该条数据非发布状态无法升版！");
				return false;
			} else {
				window.open(ctx + "/versionUp" + add + "?bh=" + bh + "&bb="
						+ bb + "&form_uuid=" + formuuid, "_blank");
			}
		} else {
			if (temp.length == 0) {
				oAlert("请选择一条要升版的数据！");
			}
			if (temp.length > 1) {
				oAlert("升版的数据只能选择一条，请从新选择！");
			}
		}
	}
}

function deleteData(){
	if (confirm("是否删除选中数据？")) {
		var tmp = $(".checkeds:checked");
		if(tmp.length == 0){
			oAlert("请至少选中一条数据！");
			return false;
		}
		var datas = readParam();
		var uuid = "", form_uuid = "", zt = "",falg = true;
		datas.forEach(function(obj) {
			zt = obj.zT;
			if(zt != "待审核" && zt != "审核不通过"){
				oAlert("状态不是待审核和不通过不能删除，请重新选择！");
				falg = false;
			}
			form_uuid = obj.formUuid;
			if(tmp.length == 1){  // uuid
				if(typeof(obj.uUID) == 'undefined'){
					uuid = obj.uuid;
				}else{
					uuid = obj.uUID; 
				}
			}else if(tmp.length > 1){
				if(typeof(obj.uUID) == 'undefined'){
					uuid += obj.uuid + ";";
				}else{
					uuid += obj.uUID + ";";
				}
			}
		});
		if(falg){
			$.ajax({
				type : "post",
				data : {
					"form_uuid" : form_uuid,
					"uuid" : uuid
				},
				async : false,
				url : ctx + "/versionUp/dropTest",
				success : function(result) {
					alert(result);	
					window.location.reload();
				}
			});
		}
	}
}

function copyData(add){
	if(confim("是否复制该数据？")){
		var temp = $(".checkeds:checked");
		if(temp.length == 1){
			var form_uuid = "",
			    uuid = "";
			var datas = readParam();
			datas.forEach(function(obj){
				form_uuid = obj.formUuid;    //表单uuid
				uuid      = obj.uuid;        //uuid
			});
			$.ajax({
				type : "post",
				data : {
					    "formuuid" : form_uuid,
					    "uuid"     : uuid
				},
				async : false,
				url : ctx + "/versionUpgrade/checkVersion",
				success : function(result) {
					if(result != "成功"){
						alert(result);
					}else{
						window.open(ctx + "/copyData" + add + "?formuuid=" + form_uuid + "&uuid=" + uuid);
					}						
				}
			})
			
		}else{
			if(temp.length == 0){
				oAlert("请选择一条数据进行克隆！");
				return false;
			}
			if(temp.length > 1){
				oAlert("只能选择一条数据进行克隆！");
			}
		}
	}
}

// 灯具项目申请数据升版
function up_djxmsq() {

	if (confirm("是否将该数据升版？")) {
		var temp = $(".checkeds:checked");
		if (temp.length == 1) {
			var bh = "", bb = "", zt = "";
			var datas = readParam();
			datas.forEach(function(obj) {
				bh = obj.bH;
				bb = obj.bB;
				zt = obj.zT;
			});
			if (zt != "发布") {
				oAlert("该条数据非发布状态无法升版！");
				return false;
			} else {
				window.open(ctx + "/versionUp/uv_djxmsqdsplc" + "?bh=" + bh
						+ "&bb=" + bb, "_blank");
			}
		} else {
			if (temp.length == 0) {
				oAlert("请选择一条要升版的数据！");
				return false;
			}
			if (temp.length > 1) {
				oAlert("升版的数据只能选择一条，请从新选择！");
			}
		}
	}
}



/**
 * QLB 20141023 表單升版
 */
function formVersionUp(add) {
	if (confirm("是否将该数据升版？")) {
		var tmp = $(".checkeds:checked");
		var bh   = "",      //编号
		    bb   = "",      //版本
		    zt   = "",      //状态
		    uuid = "",      //uuid
		    form_uuid = ""; //form_uuid

		if (tmp.length == 1) {   // 选中一条数据
			var datas = readParam();
			datas.forEach(function(obj) {
				uuid = obj.uuid;   // uuid
				zt   = obj.zT;     // 状态
				bh   = obj.bH;     // 编号
				bb   = obj.bB;     // 版本
				form_uuid = obj.formUuid;  //form_uuid
			});
			if (zt != "发布") { // 升版只针对状态为“发布”的表单
				oAlert("升版的数据状态只能是‘发布’，请重新选择！");
			}else{
				$.ajax({
					type : "post",
					data : {
						"bh" : bh,
						"bb" : bb,
						"form_uuid" : form_uuid
					},
					async : false,
					url : ctx + "/versionUpgrade/checkVersion",
					success : function(result) {
						if(result != "成功"){
							alert(result);
						}else{
							window.open(ctx + "/versionUpgrade" + add + "?bh=" + bh + "&bb=" + bb);
						}						
					}
				});
			}			
		} else {                // 多选或不选则报错
			if (tmp.length == 0) {
				oAlert("请选择一条要升版的数据！");
			}
			if (tmp.length > 1) {
				oAlert("升版的数据只能选择一条，请重新选择！");
			}
		}
	}
}

/**
 * QLB 20141024 表单归档
 */
function formArchive(add) {
	if (confirm("是否将该数据归档？")) {
		var tmp = $(".checkeds:checked");
		var zt   = "",      // 状态
			bh   = "",      // 编号
			bb   = "",      // 版本
			uuid = "",      // uuid
			form_uuid = "", // form_uuid
			symbol = "";    // 标识

		if (tmp.length >= 1) { // 选中一条或多条数据
			var datas = readParam();
			datas.forEach(function(obj) {
				if ( obj.zT !== "发布") {
					symbol = "X";
					oAlert("归档数据的状态只能为‘发布’状态，请重新选择！");
				}else{
					uuid += obj.uuid + ';';        // uuid
					bh   += obj.bH + ';';          // 编号
					bb   += obj.bB + ';';          // 版本
					if(form_uuid=="")
						form_uuid = obj.formUuid;  //form_uuid
				}
			});
			if(symbol !== "X"){
				$.ajax({
					type : "post",
					data : {
						"uuid" : uuid,
						"bh"   : bh,
						"bb"   : bb,
						"form_uuid" : form_uuid
					},
					async : false,
					url : ctx + "/versionUpgrade" + add,
					success : function(result) {
						alert(result);
						window.location.reload();  //刷新页面
					}
				});
			}
		} else {         //不选则报错
			if (tmp.length == 0) {
				oAlert("请选择一条要恢复的数据！");
			}
		}
	}
}


/**
 * QLB 20141024 表单恢复
 */
function formRecover(add) {
	if (confirm("是否将数据恢复？")) {
		var tmp = $(".checkeds:checked");
		var zt   = "",           // 状态
			bh   = "",           // 编号
			bb   = "",           // 版本	
			uuid = "",           // uuid
			form_uuid = "",      // form_uuid
			symbol = "";         // 标识

		if (tmp.length == 1) {   // 只能选中一条数据进行恢复
			var datas = readParam();
			datas.forEach(function(obj) {
				if (obj.zT !== "归档") {
					symbol = "X";
					oAlert("恢复数据的状态只能为‘归档’状态，请重新选择！");
				}else{
					uuid += obj.uuid + ';';        // uuid
					bh   += obj.bH + ';';          // 编号
					bb   += obj.bB + ';';          // 版本
					if(form_uuid=="")
						form_uuid = obj.formUuid;  // form_uuid
				}				
			});
			if(symbol !=="X" ){
				$.ajax({
					type : "post",
					data : {
						"uuid" : uuid,
						"bh"   : bh,
						"bb"   : bb,
						"form_uuid" : form_uuid
					},
					async : false,
					url : ctx + "/versionUpgrade" + add,
					success : function(result) {
						oAlert(result);
						window.location.reload();  //刷新页面
					}
				});
			}
		} else { // 多选或不选则报错
			if (tmp.length == 0) {
				oAlert("请选择一条要恢复的数据！");
			}
			if (tmp.length > 1) {
				oAlert("一次只能恢复一条记录，请重选！");
			}
		}
	}
}


function readParam() {
	var arrayObj = new Array();
	$(".checkeds:checked").each(function(i) {
		var s = new Object();
		var jsonstr = $(this).parent().parent(".dataTr").attr("jsonstr");
		var jsonObj = eval("(" + urldecode(jsonstr) + ")");
		arrayObj.push(jsonObj);
	});
	return arrayObj;
}