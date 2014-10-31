/**
 * 查询刷卡记录(ygskxx)
 * @param PI_PERNR 员工编号
 * @param PI_BEGDA 开始时间
 * @param PI_ENDDA 结束时间
 */
function qSKJL(PI_PERNR, PI_BEGDA, PI_ENDDA) {
	var params = '{"PI_PERNR":'+PI_PERNR+',"PI_BEGDA":'+PI_BEGDA+',"PI_ENDDA":'+PI_ENDDA+'}';
	$.ajax({
		type : "post",
		data : {
			"saps" : "sapConnectConfig",// 空取默认配置
			"functionName" : "ZHRI0003",
			"jsonParams" : params
		},
		async : true,
		url : ctx + "/basicdata/sap/executeRFC",
		success : function(result) {
			if (result.success) {
				var data = jQuery.parseJSON(result.data);
				var rows = data.PT_TEVEN.row;
				//alert(rows.length);
				var jsonObj = {};
				jsonObj.Rows = rows;
				grid_skjl.loadData(jsonObj);
				/**
				var table = document.getElementById("ID_A");
				for ( var i = 0; i < rows.length; i++) {
					var rowdata = rows[i];
					var row = table.insertRow();
					var cell=row.insertCell(0);
					cell.innerHTML = i;
					cell=row.insertCell(1);
					cell.innerHTML = rowdata.DDTEXT;
					cell=row.insertCell(2);
					cell.innerHTML = rowdata.LDATE;
					cell=row.insertCell(3);
					cell.innerHTML = rowdata.LTIME;
				}
				console.log(JSON.stringify(data));
				*/
			} else {
				alert(result.msg);
			}
		}
	});
};

/**
 * 查询考勤异常
 * @param PI_PERNR 员工编号
 * @param PI_BEGDA 开始时间
 * @param PI_ENDDA 结束时间
 */
function qKQYC(PI_PERNR, PI_BEGDA, PI_ENDDA) {
	var params = '{"PI_PERNR":'+PI_PERNR+',"PI_BEGDA":'+PI_BEGDA+',"PI_ENDDA":'+PI_ENDDA+'}';
	$.ajax({
		type : "post",
		data : {
			"saps" : "sapConnectConfig",// 空取默认配置
			"functionName" : "ZHRI0080",
			"jsonParams" : params
		},
		async : true,
		url : ctx + "/basicdata/sap/executeRFC",
		success : function(result) {
			if (result.success) {
				var data = jQuery.parseJSON(result.data);
				var rows = data.PT_RPFAUSG.row;
				//alert(rows.length);
				var jsonObj = {};
				jsonObj.Rows = rows;
				grid_kqyc.loadData(jsonObj);
				/**
				var table = document.getElementById("ID_B");
				for ( var i = 0; i < rows.length; i++) {
					var rowdata = rows[i];
					var row = table.insertRow();
					var cell=row.insertCell(0);
					cell.innerHTML = i;
					cell=row.insertCell(1);
					cell.innerHTML = rowdata.ERROR;
					cell=row.insertCell(2);
					cell.innerHTML = rowdata.ETEXT;
					cell=row.insertCell(3);
					cell.innerHTML = rowdata.KURZT;
					cell=row.insertCell(4);
					cell.innerHTML = rowdata.LDATE;
				}
				console.log(JSON.stringify(data));
				*/
			} else {
				alert(result.msg);
			}
		}
	});
};

/**
 * 自助查询请假调休定额查询接口
 * @param PI_PERNR 员工编号
 */
function qTXDE(PI_PERNR) {
	var params = '{"PI_PERNR":'+PI_PERNR+'}';
	$.ajax({
		type : "post",
		data : {
			"saps" : "sapConnectConfig",// 空取默认配置
			"functionName" : "ZHRI0042",
			"jsonParams" : params
		},
		async : true,
		url : ctx + "/basicdata/sap/executeRFC",
		success : function(result) {
			if (result.success) {
				var data = jQuery.parseJSON(result.data);
				var recordName = data._recordName_[0];
				
				var rows = data[recordName].row;
				//alert(rows.length);
				var result = rows[0].RESULT;
				$("#RESULT").val(result);
			} else {
				alert(result.msg);
			}
		}
	});
};
/**
 * 请假信息
 * @param PI_PERNR
 * @param PI_GJAHR
 * @param PI_MONAT
 */
function qQJXX(PI_PERNR,PI_GJAHR,PI_MONAT) {
	var params = '{"PI_PERNR":'+PI_PERNR+',"PI_GJAHR":'+PI_GJAHR+',"PI_MONAT":'+PI_MONAT+'}';
	$.ajax({
		type : "post",
		data : {
			"saps" : "sapConnectConfig",// 空取默认配置
			"functionName" : "ZHRI000901",
			"jsonParams" : params
		},
		async : true,
		url : ctx + "/basicdata/sap/executeRFC",
		success : function(result) {
			if (result.success) {
				var data = jQuery.parseJSON(result.data);
				var recordName = data._recordName_[0];
				var rows = data[recordName].row;
				var jsonObj = {};
				jsonObj.Rows = rows;
				grid_qjxx.loadData(jsonObj);
			} else {
				alert(result.msg);
			}
		}
	});
};
/**
 * 外出出差信息
 * @param PI_PERNR
 * @param PI_GJAHR
 * @param PI_MONAT
 */
function qWCCCXX(PI_PERNR,PI_GJAHR,PI_MONAT) {
	var params = '{"PI_PERNR":'+PI_PERNR+',"PI_GJAHR":'+PI_GJAHR+',"PI_MONAT":'+PI_MONAT+'}';
	$.ajax({
		type : "post",
		data : {
			"saps" : "sapConnectConfig",// 空取默认配置
			"functionName" : "ZHRI000902",
			"jsonParams" : params
		},
		async : true,
		url : ctx + "/basicdata/sap/executeRFC",
		success : function(result) {
			if (result.success) {
				var data = jQuery.parseJSON(result.data);
				var recordName = data._recordName_[0];
				var rows = data[recordName].row;
				var jsonObj = {};
				jsonObj.Rows = rows;
				grid_wcccxx.loadData(jsonObj);
			} else {
				alert(result.msg);
			}
		}
	});
};

/**
 * 月度考勤汇总
 * @param PI_PERNR
 * @param PI_GJAHR
 * @param PI_MONAT
 */
function qYGKQHZ(PT_PERNR,NF,YF) {
	var params = '{"PT_PERNR":{"row":[{"PERNR": "' + PT_PERNR + '"}]},"PI_YEAR":'+NF+',"PI_MONTH":'+YF+'}';
	$.ajax({
		type : "post",
		//timeout:16 * 1000,
		data : {
			"saps" : "sapConnectConfig",// 空取默认配置
			"functionName" : "ZHRI0077",
			"jsonParams" : params
		},
		async : true,
		url : ctx + "/basicdata/sap/executeRFC",
		success : function(result) {
			if (result.success) {
				var data = jQuery.parseJSON(result.data);
				var rows = data.PT_ZHRS0103.row;
				var jsonObj = {};
				jsonObj.Rows = rows;
				grid_ygkqhz.loadData(jsonObj);
			} else {
				alert(result.msg);
			}
		}
	});
};

/**
 * 月度考勤汇总
 * @param PI_PERNR
 * @param PI_GJAHR
 * @param PI_MONAT
 */
function qBMKQHZ(PT_DEPID,NF,YF) {
	var params = '{"PT_DEPID":{"row":[{"OBJID": "' + PT_DEPID + '"}]},"PI_YEAR":"'+NF+'","PI_MONTH":"'+YF+'"}';
	$.ajax({
		type : "post",
		//timeout:16 * 1000,
		data : {
			"saps" : "sapConnectConfig",// 空取默认配置
			"functionName" : "ZHRI0077",
			"jsonParams" : params
		},
		async : true,
		url : ctx + "/basicdata/sap/executeRFC",
		success : function(result) {
			if (result.success) {
				var data = jQuery.parseJSON(result.data);
				var rows = data.PT_ZHRS0103.row;
				var jsonObj = {};
				jsonObj.Rows = rows;
				grid_bmkqhz.loadData(jsonObj);
			} else {
				alert(result.msg);
			}
		}
	});
};

/**
 * 部门培训信息
 * @param PI_PERNR
 * @param PI_GJAHR
 * @param PI_MONAT
 */
function qBMPXXX(PT_DEPID,NF,YF) {
	var params = '{"PT_DEPID":{"row":[{"OBJID": "' + PT_DEPID + '"}]},"PI_YEAR":"'+NF+'","PI_MONTH":"'+YF+'"}';
	$.ajax({
		type : "post",
		//timeout:16 * 1000,
		data : {
			"saps" : "sapConnectConfig",// 空取默认配置
			"functionName" : "ZHRI0073",
			"jsonParams" : params
		},
		async : true,
		url : ctx + "/basicdata/sap/executeRFC",
		success : function(result) {
			if (result.success) {
				var data = jQuery.parseJSON(result.data);
				var rows = data.PT_ZHRS0104.row;
				var jsonObj = {};
				jsonObj.Rows = rows;
				grid_bmpxxx.loadData(jsonObj);
			} else {
				alert("查询出错");
			}
		}
	});
};

/**
 * 个人培训信息
 * @param PI_PERNR
 * @param PI_GJAHR
 * @param PI_MONAT
 */
function qYGPXXX(PT_PERNR,NF,YF) {
	var params = '{"PT_PERNR":{"row":[{"PERNR": "' + PT_PERNR + '"}]},"PI_YEAR":'+NF+',"PI_MONTH":'+YF+'}';
	$.ajax({
		type : "post",
		//timeout:16 * 1000,
		data : {
			"saps" : "sapConnectConfig",// 空取默认配置
			"functionName" : "ZHRI0073",
			"jsonParams" : params
		},
		async : true,
		url : ctx + "/basicdata/sap/executeRFC",
		success : function(result) {
			if (result.success) {
				var data = jQuery.parseJSON(result.data);
				var rows = data.PT_ZHRS0104.row;
				var jsonObj = {};
				jsonObj.Rows = rows;
				grid_ygpxxx.loadData(jsonObj);
			} else {
				alert("查询出错");
			}
		}
	});
};

