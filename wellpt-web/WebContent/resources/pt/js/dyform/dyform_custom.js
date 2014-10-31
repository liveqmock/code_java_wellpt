/**
 * 从表主表必填域设置值(分主表和从表)
 * 		var fieldArr = ["a","b","c",...];
 * 例子  主表：setRequiredAndUnRequired("",fieldArr,_this,true);
 * 例子  从表：setRequiredAndUnRequired("uf_ldx_dfdddd",fieldArr,_this,true);
 */
function setRequiredAndUnRequired(mainOrSub,fieldArray,_thisTemp,isRequired){
	var _this = _thisTemp;
	if(mainOrSub == ""){//主表情况
		if(fieldArray != null && fieldArray != ""){
			for(var i=0;i<fieldArray.length;i++){
				_this.getControl(fieldArray[i]).setRequired(isRequired);
			}
			
		}
	}else{//从表情况
		
		if(fieldArray != null && fieldArray != ""){
			var formUuid = _this.getFormUuid(mainOrSub); 
			var ctl = _this.getSubformControl(formUuid);//获得从表的控件
			var forminfo = {};
			forminfo.id = mainOrSub;
			//搜集从表的数据
			var allRowData = _this.getAllRowData(forminfo);//获取所有的数据
			if(fieldArray != null && fieldArray != "" 
				&& allRowData != null && allRowData.length > 0){
				for(var i=0;i<allRowData.length;i++){
					for(var j=0;j<fieldArray.length;j++){
						_this.getControl(fieldArray[j],allRowData[i].id).setRequired(isRequired);
					}
				}
			}
		}
	}
}


// 金额大写
function uppercaseMoney(currencyDigits){
	// Constants:
	var MAXIMUM_NUMBER = 99999999999.99;
	// Predefine the radix characters and currency symbols for output:
	var CN_ZERO = "零";
	var CN_ONE = "壹";
	var CN_TWO = "贰";
	var CN_THREE = "叁";
	var CN_FOUR = "肆";
	var CN_FIVE = "伍";
	var CN_SIX = "陆";
	var CN_SEVEN = "柒";
	var CN_EIGHT = "捌";
	var CN_NINE = "玖";
	var CN_TEN = "拾";
	var CN_HUNDRED = "佰";
	var CN_THOUSAND = "仟";
	var CN_TEN_THOUSAND = "万";
	var CN_HUNDRED_MILLION = "亿";
	var CN_DOLLAR = "元";
	var CN_TEN_CENT = "角";
	var CN_CENT = "分";
	var CN_INTEGER = "整";
	
	// Variables:
	var integral; // Represent integral part of digit number.
	var decimal; // Represent decimal part of digit number.
	var outputCharacters; // The output result.
	var parts;
	var digits, radices, bigRadices, decimals;
	var zeroCount;
	var i, p, d;
	var quotient, modulus;
	
	// Validate input string:
	currencyDigits = currencyDigits.toString();
	if (currencyDigits == "") {
		return "";
	}
	if (currencyDigits.match(/[^,.\d]/) != null) {
		return "";
	}
	if ((currencyDigits).match(/^((\d{1,3}(,\d{3})*(.((\d{3},)*\d{1,3}))?)|(\d+(.\d+)?))$/) == null) {
		return "";
	}
	
	// Normalize the format of input digits:
	currencyDigits = currencyDigits.replace(/,/g, ""); // Remove comma delimiters.
	currencyDigits = currencyDigits.replace(/^0+/, ""); // Trim zeros at the beginning.
	// Assert the number is not greater than the maximum number.
	if (Number(currencyDigits) > MAXIMUM_NUMBER) {
		return "";
	}
	
	// Process the coversion from currency digits to characters:
	// Separate integral and decimal parts before processing coversion:
	parts = currencyDigits.split(".");
	if (parts.length > 1) {
		integral = parts[0];
		decimal = parts[1];
		// Cut down redundant decimal digits that are after the second.
		decimal = decimal.substr(0, 2);
	}else {
		integral = parts[0];
		decimal = "";
	}
	// Prepare the characters corresponding to the digits:
	digits = new Array(CN_ZERO, CN_ONE, CN_TWO, CN_THREE, CN_FOUR, CN_FIVE, CN_SIX, CN_SEVEN, CN_EIGHT, CN_NINE);
	radices = new Array("", CN_TEN, CN_HUNDRED, CN_THOUSAND);
	bigRadices = new Array("", CN_TEN_THOUSAND, CN_HUNDRED_MILLION);
	decimals = new Array(CN_TEN_CENT, CN_CENT);
	// Start processing:
	outputCharacters = "";
	// Process integral part if it is larger than 0:
	if (Number(integral) > 0) {
		zeroCount = 0;
		for (i = 0; i < integral.length; i++) {
			p = integral.length - i - 1;
			d = integral.substr(i, 1);
			quotient = p / 4;
			modulus = p % 4;
			if (d == "0") {
				zeroCount++;
			}else {
				if (zeroCount > 0) {
					outputCharacters += digits[0];
				}
				zeroCount = 0;
				outputCharacters += digits[Number(d)] + radices[modulus];
			}
			if (modulus == 0 && zeroCount < 4) {
				outputCharacters += bigRadices[quotient];
			}
		}
		outputCharacters += CN_DOLLAR;
	}
	// Process decimal part if there is:
	if (decimal != "") {
		for (i = 0; i < decimal.length; i++) {
			d = decimal.substr(i, 1);
			if (d != "0") {
				outputCharacters += digits[Number(d)] + decimals[i];
			}
		}
	}
	// Confirm and return the final output string:
	if (outputCharacters == "") {
		outputCharacters = CN_ZERO + CN_DOLLAR;
	}
	if (decimal == "") {
		outputCharacters += CN_INTEGER;
	}
	return outputCharacters;

}

// 调整字符串为固定长度(字符串超过指定长度则截取字符串为指定长度,否则用指定字符填充使其达到指定长度)
function fitlength(str,length,filler){
	var len = str.length;
	if(len>length){
		return str.subString(len-length-1,len-1);
	}else{
		for(var i=0;i<length-len;i++){
			str = filler + str;
		}
		return str;
	}
}
/**
 * 判断两个日期的大小,
 * @param sDate 格式为:2014-08-12
 * @param eDate 格式为:2014-05-12
 * @returns {Boolean}
 */
function sDateAndeDateCompare(sDate,eDate,sDesc,eDesc){
	 var _sDate = new Date(sDate.replace(/\-/g, "\/"));
	  var _eDate = new Date(eDate.replace(/\-/g, "\/"));
	  if(_sDate > _eDate){
		  alert(sDesc + "不能大于" + eDesc + "!");
		  return false;
	  }
}

/**
 * 判断两个日期的大小,
 * @param sDate 格式为:2014-08-12   2012/12/14 new Date(2012/12/14 )
 * @param eDate 格式为:2014-05-12
 * @returns {Boolean}
 */
function sDateAndeDateCompare(sDate,eDate,tip){
	 var _sDate = new Date(sDate.replace(/\-/g, "\/"));
	  var _eDate = new Date(eDate.replace(/\-/g, "\/"));
	  if(_sDate > _eDate){
		  alert(tip);
		  return false;
	  }
}

/**
 * 供应商Code 根据不同的类型设置必填域
 */
function SupplierCodeSetRequired(gysdlyy,
		dlyypgjgsm,zsjxgyy,zsjlb,xgqzsj,xghzsj,
		tyyy,tysj,tysjyysm,hfyy,hfsj,hfsjyysm,_this){
	_this.getControl("gysdlyy").setRequired(gysdlyy);
	_this.getControl("dlyypgjgsm").setRequired(dlyypgjgsm);
	
	_this.getControl("zsjxgyy").setRequired(zsjxgyy);//主数据修改原因
	_this.getControl("zsjlb").setRequired(zsjlb);//
	_this.getControl("xgqzsj").setRequired(xgqzsj);//
	_this.getControl("xghzsj").setRequired(xghzsj);//
	
	_this.getControl("tyyy").setRequired(tyyy);//
	_this.getControl("tysj").setRequired(tysj);//
	_this.getControl("tysjyysm").setRequired(tysjyysm);//
	
	_this.getControl("hfyy").setRequired(hfyy);//
	_this.getControl("hfsj").setRequired(hfsj);//
	_this.getControl("hfsjyysm").setRequired(hfsjyysm);//
}

/**
 * 交期赋值封装函数
 */
function jqfzCallFunction(_this,buttionName,childFormId){
	
	_this.$(buttionName).click(function(){
		
		var formUuid = _this.getFormUuid(childFormId); 
		var ctl = _this.getSubformControl(formUuid);
		var forminfo = {};
		forminfo.id = childFormId;
		var allRowData = _this.getAllRowData(forminfo);//获取所有的数据
		if(allRowData != null && allRowData.length >1){
			var theFirstRowData = allRowData[0];
			var hfjqDate = theFirstRowData.hfjq;//回复交期
			var jhjqDate = theFirstRowData.jhjq;//交互绝期
			
			for(var i=0;i<allRowData.length;i++){
				var uuid = allRowData[i+1].uuid;//数据uuid
				var id = allRowData[i+1].id;//数据id
				var json = {
						"hfjq":hfjqDate,
						"jhjq":jhjqDate,
						"id":uuid, 
						"uuid":id}
				_this.updateRowData(childFormId, json);//更新行数据
			}
		}
	});
}

//---------qlb----------------------------------------------------------
/**
 * 根据物料ID获取产品名称/目标区域/客户/备料BOM单 -- EP申请单
 * QLB  20141015 
 * @param id
 * @param name
 * @param formId
 * @param rowdata
 * @param _this
 * @returns
 */
function getCPID_EP(id,name,formId,rowdata,_this){
	//根据产品ID，通过 ajax 获取 --- 产品名称/目标区域/客户
	$.ajax({
	   type     : "post",
	   async    : false,
	   dataType : "json",
	   url      : ctx + "/defaultValue/queryDataToEP?cpid=" + id, 
	   success  : function(result){
	   var arr = eval(result);
	   var charter = arr[0].charter;	
	   if(charter.length == "0"){
		   var json = {"cpid":id,"cpmc":name,"id":rowdata.id,"uuid":rowdata.uuid};
           _this.updateRowData(formId,json);
	   }else{
		   var json = {"cpid":id,"cpmc":name,"mbqy":charter[0][1],"kh":charter[0][2],"id":rowdata.id,"uuid":rowdata.uuid};
           _this.updateRowData(formId,json);	
	   }	    
	}
    });	
	
	//根据产品ID，通过 ajax 到CRM中获取E-BOM(对应到EP申请表的'备料BOM单')
	$.ajax({
		type     : "post",
		async    : false,
		dataType : "jsonp",  // 跨域设置
		url      : "http://pm-test.leedarson.com/index/index/lcpservice", 
		data:{
			"json": JSON.stringify({
				'apiServiceName' : 'index.index.getEBomIdByProductItemNo',
				'productItemNo'  : id
			})
		},
		jsonp:'jsonpCallback',
		success  : function(result){
			if( result.bomId != null ){
				var json = {"blbomd":"<a href=# onclick='jumpToPDM(\"" + result.bomId + "\")'>" + result.bomId + "</a>","id":rowdata.id,"uuid":rowdata.uuid};
                _this.updateRowData(formId,json);
			}            		 
		}
	 });
}

function jsonpCallback() //回调函数
{
	//return '123456';
	console.log(data);
    alert(data.message); 
}

/**
 * 通过E-BOM转到PDM的BOM详细页面
 * QLB  20141016
 * @param blbomd
 * @returns
 */
function jumpToPDM(blbomd)
{   
	//定义url变量，存放默认地址
	var url = "http://pm-test.leedarson.com/develop/bom/detail?id=";	
	url = url + blbomd;
	window.open(url); //在新的页面中打开
}

//---------huangwy----------------------------------------------------------
function setFlowNameAndUserId(gdzclb,dqgs,fields,_this){
	var result = [];
	var gdzclbValue = _this.getControl(gdzclb).getValue();
	var dqgsValue = _this.getControl(dqgs).getValue();
	if(gdzclbValue == ''||dqgsValue == ''){
		_this.setFieldValueByFieldName(fields[0],'');
		_this.setFieldValueByFieldName(fields[2],'');
		_this.setFieldValueByFieldName(fields[4],'');
		   
		_this.setFieldValueByFieldName(fields[1],'');
		_this.setFieldValueByFieldName(fields[3],'');
		_this.setFieldValueByFieldName(fields[5],'');
	}else {
			
		if(gdzclbValue=="行政类(厦门/四川)"){
			
			if(dqgsValue == "厦门"){
				result.push({id:'U0010000027',name:'叶志武'});
				result.push({id:'U0010000444',name:'陈清埔'});
				result.push({id:'U0010000006',name:'郑连勇'});
			}else if(dqgsValue == "长泰"){
				
			}else if(dqgsValue=="四川鼎吉"){
				result.push({id:'U0010001867',name:'李佑红'});
				result.push({id:'U0010001865',name:'杜亮'});
				result.push({id:'U0010001838',name:'郑登龙'});
			}else if(dqgsValue=="四川联恺"){
				result.push({id:'U0010001867',name:'李佑红'});
				result.push({id:'U0010001865',name:'杜亮'});
				result.push({id:'U0010001838',name:'郑登龙'});
			}
		}else if(gdzclbValue=="行政类(长泰办公设备等)"){
			
			if(dqgsValue == "厦门"){
				
			}else if(dqgsValue == "长泰"){
				result.push({id:'U0010001152',name:'郑康伟'});
				result.push({id:'U0010001774',name:'黄鸿瑜'});
				result.push({id:'U0010001410',name:'邹国春'});
			}else if(dqgsValue == "四川鼎吉"){
				
			}else if(dqgsValue == "四川联恺"){
				
			}
	   }else if(gdzclbValue=="行政类(长泰公共能源等)"){
		   
		   if(dqgsValue == "厦门"){
				
		   }else if(dqgsValue == "长泰"){
				result.push({id:'U0010001517',name:'戴艺云'});
				result.push({id:'U0010001774',name:'黄鸿瑜'});
				result.push({id:'U0010001410',name:'邹国春'});
		   }else if(dqgsValue == "四川鼎吉"){
				
		   }else if(dqgsValue == "四川联恺"){
				
		   }
	   }else if(gdzclbValue=="运输设备"){
		   
	      if(dqgsValue=="厦门"){
				result.push({id:'U0010000027',name:'叶志武'});
				result.push({id:'U0010000444',name:'陈清埔'});
				result.push({id:'U0010000006',name:'郑连勇'});
	      }else if(dqgsValue=="长泰"){
				result.push({id:'U0010001152',name:'郑康伟'});
				result.push({id:'U0010001774',name:'黄鸿瑜'});
				result.push({id:'U0010001410',name:'邹国春'});
	      }else if(dqgsValue=="四川鼎吉"){
				result.push({id:'U0010001867',name:'李佑红'});
				result.push({id:'U0010001865',name:'杜亮'});
				result.push({id:'U0010001838',name:'郑登龙'});
	      }else if(dqgsValue=="四川联恺"){
				result.push({id:'U0010001867',name:'李佑红'});
				result.push({id:'U0010001865',name:'杜亮'});
				result.push({id:'U0010001838',name:'郑登龙'});
	      }
	   }else if(gdzclbValue=="机器设备"){
		   
	      if(dqgsValue=="厦门"){
				result.push({id:'U0010001280',name:'江茂辉'});
				result.push({id:'U0010001280',name:'江茂辉'});
				result.push({id:'U0010000123',name:'汤茂平'});
	      }else if(dqgsValue=="长泰"){
				result.push({id:'U0010001280',name:'江茂辉'});
				result.push({id:'U0010001280',name:'江茂辉'});
				result.push({id:'U0010000123',name:'汤茂平'});
	      }else if(dqgsValue=="四川鼎吉"){
				result.push({id:'U0010001867',name:'李佑红'});
				result.push({id:'U0010001865',name:'杜亮'});
				result.push({id:'U0010001838',name:'郑登龙'});
	      }else if(dqgsValue=="四川联恺"){
				result.push({id:'U0010001867',name:'李佑红'});
				result.push({id:'U0010001865',name:'杜亮'});
				result.push({id:'U0010001838',name:'郑登龙'});
	      }
	   }else if(gdzclbValue=="计量器具"){
		   
	      if(dqgsValue=="厦门"){
				result.push({id:'U0010001280',name:'江茂辉'});
				result.push({id:'U0010001280',name:'江茂辉'});
				result.push({id:'U0010000123',name:'汤茂平'});
	      }else if(dqgsValue=="长泰"){
				result.push({id:'U0010001280',name:'江茂辉'});
				result.push({id:'U0010001280',name:'江茂辉'});
				result.push({id:'U0010000123',name:'汤茂平'});
	      }else if(dqgsValue=="四川鼎吉"){
				result.push({id:'U0010001867',name:'李佑红'});
				result.push({id:'U0010001865',name:'杜亮'});
				result.push({id:'U0010001838',name:'郑登龙'});
	       }else if(dqgsValue=="四川联恺"){
				result.push({id:'U0010001867',name:'李佑红'});
				result.push({id:'U0010001865',name:'杜亮'});
				result.push({id:'U0010001838',name:'郑登龙'});
	       } 
	   }else if(gdzclbValue=="信息类"){
		   
	      if(dqgsValue=="厦门"){
				result.push({id:'U0010000383',name:'吴艳兰'});
				result.push({id:'',name:''});
				result.push({id:'U0010000005',name:'林友钦'});
	      }else if(dqgsValue=="长泰"){
				result.push({id:'U0010000383',name:'吴艳兰'});
				result.push({id:'',name:''});
				result.push({id:'U0010000005',name:'林友钦'});
	      }else if(dqgsValue=="四川鼎吉"){
				result.push({id:'U0010001835',name:'黄洪'});
				result.push({id:'',name:''});
				result.push({id:'U0010000005',name:'林友钦'});
	      }else if(dqgsValue=="四川联恺"){
				result.push({id:'U0010001835',name:'黄洪'});
				result.push({id:'',name:''});
				result.push({id:'U0010000005',name:'林友钦'});
	      } 
	   }
		if(result.length==0){
			_this.setFieldValueByFieldName(fields[0],'');
			_this.setFieldValueByFieldName(fields[2],'');
			_this.setFieldValueByFieldName(fields[4],'');
			   
			_this.setFieldValueByFieldName(fields[1],'');
			_this.setFieldValueByFieldName(fields[3],'');
			_this.setFieldValueByFieldName(fields[5],'');
			oAlert(dqgsValue + '的不能选择' + gdzclbValue);
		}else{
			_this.setFieldValueByFieldName(fields[0],result[0].name);
			_this.setFieldValueByFieldName(fields[2],result[1].name);
			_this.setFieldValueByFieldName(fields[4],result[2].name);
			   
			_this.setFieldValueByFieldName(fields[1],result[0].id);
			_this.setFieldValueByFieldName(fields[3],result[1].id);
			_this.setFieldValueByFieldName(fields[5],result[2].id);
		}
	}
}

function setNumberNameAndUserId(gsdm,fields,cthis){
	var _this = cthis;
	var gsdmValue = _this.getFieldValueByFieldName(gsdm);//公司代码
	var flowUser = [];
	if(gsdmValue=="1000"){
		flowUser.push({id:'U0010001542',name:'刘立华'});
		flowUser.push({id:'U0010001792',name:'黄爱娥'});
	}else if(gsdmValue=="2000"){
		flowUser.push({id:'U0010000827',name:'周惠娟'});
		flowUser.push({id:'U0010000493',name:'孙雅羡'});
	}else if(gsdmValue == "3000"){
		flowUser.push({id:'U0010000507',name:'洪荔红'});
		flowUser.push({id:'U0010000376',name:'叶红'});
   }else if(gsdmValue=="4000"){
		flowUser.push({id:'U0010000284',name:'何玲鹏'});
		flowUser.push({id:'U0010000294',name:'周家平'});
	}else if(gsdmValue=="4600"){
		flowUser.push({id:'U0010001787',name:'林育华'});
		flowUser.push({id:'U0010000294',name:'周家平'});
	}else if(gsdmValue=="5000"){
		flowUser.push({id:'U0010000359',name:'林丽真'});
		flowUser.push({id:'U0010000303',name:'林春金'});
   }else if(gsdmValue=="5200"){
		flowUser.push({id:'U0010000507',name:'洪荔红'});
		flowUser.push({id:'U0010000376',name:'叶红'});
   }else if(gsdmValue=="6000"){
		flowUser.push({id:'U0010001927',name:'邓小龙'});
		flowUser.push({id:'U0010000282',name:'邱燕华'});
   }else if(gsdmValue=="6500"){
		flowUser.push({id:'U0010001927',name:'邓小龙'});
		flowUser.push({id:'U0010000282',name:'邱燕华'});
   }else if(gsdmValue=="7000"){
		flowUser.push({id:'U0010000507',name:'洪荔红'});
		flowUser.push({id:'U0010001414',name:'杨晨'});
	}else if(gsdmValue=="7200"){
		flowUser.push({id:'U0010000507',name:'洪荔红'});
		flowUser.push({id:'U0010001414',name:'杨晨'});
	}else if(gsdmValue=="7300"){
		flowUser.push({id:'U0010000507',name:'洪荔红'});
		flowUser.push({id:'U0010001414',name:'杨晨'});
   }else{
		flowUser.push({id:'',name:''});
		flowUser.push({id:'',name:''});
   }
	if(fields.length=2){
		_this.setFieldValueByFieldName(fields[0],flowUser[0].name);
		_this.setFieldValueByFieldName(fields[1],flowUser[0].id);
	}else if(fields.length=4){
		_this.setFieldValueByFieldName(fields[0],flowUser[0].name);
		_this.setFieldValueByFieldName(fields[2],flowUser[1].name);
	   
		_this.setFieldValueByFieldName(fields[1],flowUser[0].id);
		_this.setFieldValueByFieldName(fields[3],flowUser[1].id);
	}
	
}
 
//---------huangwy-------------------------------------------------------
//-----------hehe-------------------------------------------------------------------



/**
 * 获取sap的ZHRI0060表数据(///工资等级查询（转正流程）)
 * @param id	员工编号
 * @param fun	获取sap数据后要执行的回调函数
 */
function getHR_ZHRI002902(id,fun){	
	var params='{"PI_PERNR":"'+id+'"}';					
	$.ajax({
		type : "post",
		data : {
			"saps":"sapConnectConfig",
			"functionName" : "ZHRI002902",
			"jsonParams" : params,
		},
		url : ctx + "/basicdata/sap/executeRFC",
		success : function(result) {
			if (result.success) {
				//alert(result.data);
				//return;
								
				if(typeof fun==='function'){					
					fun(eval('(' + result.data + ')'));
				}			
										
			} else {
				alert(result.msg);
			}
		}				
	});			
}


/**
 * 获取sap的ZHRI0060表数据(//获取入职日期，计划转正日期)
 * @param id	员工编号
 * @param fun	获取sap数据后要执行的回调函数
 */
function getHR_ZHRI0060(id,fun){	
	var params='{"PI_PERNR":"'+id+'"}';					
	$.ajax({
		type : "post",
		data : {
			"saps":"sapConnectConfig",
			"functionName" : "ZHRI0060",
			"jsonParams" : params,
		},
		url : ctx + "/basicdata/sap/executeRFC",
		success : function(result) {
			if (result.success) {
				//alert(result.data);
				//return;
								
				if(typeof fun==='function'){					
					fun(eval('(' + result.data + ')'));
				}			
										
			} else {
				alert(result.msg);
			}
		}				
	});			
}

/**
 * 获取sap的ZHRI001004_02表数据
 * @param id	员工编号
 * @param fun	获取sap数据后要执行的回调函数
 */
function getHR_ZHRI001004_02(id,fun){	
	var params='{"PI_PERNR":"'+id+'"}';					
	$.ajax({
		type : "post",
		data : {
			"saps":"sapConnectConfig",
			"functionName" : "ZHRI001004_02",
			"jsonParams" : params,
		},
		url : ctx + "/basicdata/sap/executeRFC",
		success : function(result) {
			if (result.success) {
				//alert(result.data);
				//return;
								
				if(typeof fun==='function'){					
					fun(eval('(' + result.data + ')'));
				}			
										
			} else {
				alert(result.msg);
			}
		}				
	});			
}

/**
 * 获取sap的HR_ZHRI001004表数据
 * @param id	员工编号
 * @param fun	获取sap数据后要执行的回调函数
 */
function getHR_ZHRI001004(id,fun){	
	var params='{"PI_PERNR":"'+id+'"}';					
	$.ajax({
		type : "post",
		data : {
			"saps":"sapConnectConfig",
			"functionName" : "ZHRI001004",
			"jsonParams" : params,
		},
		url : ctx + "/basicdata/sap/executeRFC",
		success : function(result) {
			if (result.success) {
				//alert(result.data);
				//return;
								
				if(typeof fun==='function'){					
					fun(eval('(' + result.data + ')'));
				}			
										
			} else {
				alert(result.msg);
			}
		}				
	});			
}

/**
 * 获取sap的HR_YGLDHTWHJK表数据
 * @param id	员工编号
 * @param begda	开始时间
 * @param ctedt	结束时间
 * @param fun	获取sap数据后要执行的回调函数
 */
function getHR_YGLDHTWHJK(id,begda,ctedt,fun){	
	var params='{"PERNR":"'+id+'","MOD":"R","BEGDA":"'+begda+'","ENDDA":"99991231","CTEDT":"'+ctedt+'","CTTYP":"01"}';					
	$.ajax({
		type : "post",
		data : {
			"saps":"sapConnectConfig",
			"functionName" : "ZHRI0006",
			"jsonParams" : params,
		},
		url : ctx + "/basicdata/sap/executeRFC",
		success : function(result) {
			if (result.success) {
				//alert(result.data);
				//return;
								
				if(typeof fun==='function'){					
					fun(eval('(' + result.data + ')'));
				}			
										
			} else {
				alert(result.msg);
			}
		}				
	});			
}

/**
 * 获取sap的HR_ZHRI001012表数据
 * @param id	员工编号
 * @param fun	获取sap数据后要执行的回调函数
 */
function getHR_ZHRI001012(id,fun){	
	var params='{"PI_PERNR":"'+id+'"}';					
	$.ajax({
		type : "post",
		data : {
			"saps":"sapConnectConfig",
			"functionName" : "ZHRI001012",
			"jsonParams" : params,
		},
		url : ctx + "/basicdata/sap/executeRFC",
		success : function(result) {
			if (result.success) {
				//alert(result.data);
				//return;
								
				if(typeof fun==='function'){					
					fun(eval('(' + result.data + ')'));
				}			
										
			} else {
				alert(result.msg);
			}
		}				
	});			
}

/**
 * 获取sap的HR_ZHRI003201表数据
 * @param id	员工编号
 * @param fun	获取sap数据后要执行的回调函数
 */
function getHR_ZHRI003201(id,fun){	
	var params='{"PI_PERNR":"'+id+'"}';					
	$.ajax({
		type : "post",
		data : {
			"saps":"sapConnectConfig",
			"functionName" : "ZHRI003201",
			"jsonParams" : params,
		},
		url : ctx + "/basicdata/sap/executeRFC",
		success : function(result) {
			if (result.success) {
				//alert(result.data);
				//return;
								
				if(typeof fun==='function'){					
					fun(eval('(' + result.data + ')'));
				}			
										
			} else {
				alert(result.msg);
			}
		}				
	});			
}
/**
 * 根据物料ID获取产品名称/目标区域/客户 -- EP申请单
 * QLB  20141015 
 * @param id
 * @param name
 * @param formId
 * @param rowdata
 * @param _this
 * @returns
 */
function getCPID_EP(id,name,formId,rowdata,_this){
	$.ajax({
	type     : "post",
	async    : false,
	dataType : "json",
	url      : ctx + "/defaultValue/queryDataToEP?cpid=" + id, 
	success  : function(result){
	var arr = eval(result);
	var charter = arr[0].charter;		
	if(charter.length == "0"){
		//alert("该产品ID没有指定的项目，请重新选择！")
		var json = {"cpid":id,"cpmc":name,"id":rowdata.id,"uuid":rowdata.uuid};
		_this.updateRowData(formId,json);	
	}else{
		var json = {"cpid":id,"cpmc":name,"mbqy":charter[0][1],"kh":charter[0][2],"id":rowdata.id,"uuid":rowdata.uuid};
		_this.updateRowData(formId,json);	 
	}
	}
    });	
}

function sumOfColumn(column, formObj){ 
	 
	var subformColumn = column.split(":");
	if(subformColumn.length == 0){
		return;
	}
	
	var formId = subformColumn[0];
	var fieldName = subformColumn[1];
	 
	//获取从表所有的行数据 
	var formdata = formObj.collectSubformData( formId)
 
	if(typeof formdata == "undefined" || formdata.length == 0){
		return 0;
	}
	
	var sum = 0;
	for(var i = 0; i < formdata.length; i++){
		var id = formdata[i]["uuid"];
		var control = $.ControlManager.getCtl($.wSubFormMethod.getCellId(id, fieldName)); 
		sum += parseNumber(control.getValue())
	}
	return sum;
	
}
//-------------hehe-----------------------------------------------------------------------

$(function(){$.dyform.extend({  
	
	/**
	 * 对于想在表单上面做扩展，非二次开发的，可直接在该js文件中写代码.
	 * 1、各表单的扩展js须在以表单对应的formId为名的函数下.所以有做扩展的表单都会有这样一个函数
	 * 2、在以formId为名的函数名中可以直接调用表单中的各属性和函数
	 * 3、下面以"测试单据1"的表单为例子
	 **/
	uf_test1:function(){
		//以表单的formId(大小写敏感)做为函数名,这里以"测试单据1"的表单做为例子
		//在表单初始化完成后，表单会主动调用该函数.
		var _this = this; 
		this.$("#setValue4selectest").click(function(){
			alert("字段selectest的值为:" + _this.getFieldValueByFieldName("selectest"));
			_this.setFieldValueByFieldName("testtest", _this.getFieldValueByFieldName("selectest"));
			 
		});
		
		this.$("#testbtn").click(function(){
			alert(_this.getFieldValueByFieldName("testtest"));		
		}); 		
	},
	
	userform_test_1:function(){
		
		var _this = this;
		
		this.$("#btn_hiden").click(function(){
			 _this.$("#tb_hiden").hide();
		});

		this.$("#btn_show").click(function(){
			 _this.$("#tb_hiden").show();
		});		
		
		//单选框事件  对于隐藏的字段，如nunber1，需在源码中为nunber1增加ID，具体参见测试表单1
		var control =_this.getControl("sfyc");
		_this.$("input[name='"+control.getCtlName()+"'][value='1']").change(function(){
			 _this.$("#tb_hiden").hide();
			 _this.$("#number1").hide();

	   	   });
		_this.$("input[name='"+control.getCtlName()+"'][value='0']").change(function(){
		         _this.$("#tb_hiden").show();
			 _this.$("#number1").show();

	   	   });
		
		//复选框事件
		var checkbox =_this.getControl("ck1");
		_this.$("input[name='"+checkbox.getCtlName()+"'][value='1']").change(function(){
			 _this.$("#tb_hiden").hide();
	   	   });
		_this.$("input[name='"+checkbox.getCtlName()+"'][value='0']").change(function(){
			 _this.$("#tb_hiden").show();
	   	   });
			 
			 
		//下拉框事件	
		var select =_this.getControl("checkbox");
		_this.$("select[name='"+select.getCtlName()+"']").change(function(){
				 _this.$("#tb_hiden").hide();
	   	   });
		
		//合计
		this.$("#btn_sum").click(function(){
			var number1=parseInt(_this.getFieldValueByFieldName("number1"));
			var number2=parseInt(_this.getFieldValueByFieldName("number2"));
			var sum=number1+number2;
			_this.setFieldValueByFieldName("number_sum", sum);
		}); 

               //sap函数调用测试
	this.$("#btn_sap").click(function(){
		var param=_this.getFieldValueByFieldName("number1");
		//var param="abc";
	    var params='{"PI_MATNR":"'+param+'","PI_WERKS":"efg","PI_TAB":{"MATNR":"1"},"PT_TAB":{"row":[{"MATNR":"1","WERKS":"2"},{"MATNR":"3","WERKS":"4"}]}}';
			$.ajax({
				type : "post",
				data : {
					"saps":"",
					"functionName" : "ZFM_TEMPLATE",
					"jsonParams" : params
				},
				async : false,
				url : ctx + "/basicdata/sap/executeRFC",
				success : function(result) {
					if (result.success) {
						// 保存成功刷新列表
						//转成对象
						var jsonobj = eval('(' + result.data + ')');
						//获得当个值
						console.log(jsonobj.PO_WERKS);
						//获得构造体消息字段值
						console.log(jsonobj.PO_RETURN.MESSAGE);
						//获得table 第二行的 MATNR字段值
						console.log(jsonobj.PT_TAB.row[0].MESSAGE);
						
						_this.setFieldValueByFieldName("rfcrs",result.data);
					} else {
						alert(result.msg);
					}
				}
			});
		});
		
	},

	// ERP_业务管理_订单评审与签发申请表 
    userform_ywgl_ddpsyqfsqb:function(){

        var _this = this;

        // 清除信息 
        _this.$("#btn_qcxx").click(function(){
			var  options =  _this.cache.get.call(_this, cacheType.options);
			_this.dyform(options);
		});
        // 订单导入
        this.$("#btn_dddr").click(function(){	
            var param1 = _this.getFieldValueByFieldName("xsddh");
	        var params = '{"PI_VBELN":"'+param1+'","PI_VKORG":"","PI_KUNNR":""}';
			$.ajax({
				type : "post",
				data : {
					"saps":"sapConnectConfig",
					"functionName" : "ZOA_SO_EVA_READ",
					"jsonParams" : params
				},
				async : false,
				url : ctx + "/basicdata/sap/executeRFC",
				success : function(result) {
					if (result.success) {
						// 保存成功刷新列表
						//转成对象
						var jsonobj = eval('(' + result.data + ')');
						//获得当个值
						//console.log(jsonobj.PT_ZSDS0029);
						//获得构造体消息字段值
						//console.log(jsonobj.PO_RETURN.MESSAGE);
						//获得table 第二行的 MATNR字段值
						//console.log(jsonobj.PT_TAB.row[0].MESSAGE);
						if(jsonobj.PT_VBAK1.row.length>0){
							_this.setFieldValueByFieldName("xszz",jsonobj.PT_VBAK1.row[0].VKORG);
							_this.setFieldValueByFieldName("sch",jsonobj.PT_VBAK1.row[0].BSTKD);
							_this.setFieldValueByFieldName("SPART",jsonobj.PT_VBAK1.row[0].SPART);
							_this.setFieldValueByFieldName("KUNNR",jsonobj.PT_VBAK1.row[0].KUNNR);
							_this.setFieldValueByFieldName("VTEXT",jsonobj.PT_VBAK1.row[0].VTEXT);
							_this.setFieldValueByFieldName("AUART",jsonobj.PT_VBAK1.row[0].AUART);
							_this.setFieldValueByFieldName("ZETTYP",jsonobj.PT_VBAK1.row[0].ZETTYP);
							_this.setFieldValueByFieldName("EDATU1",jsonobj.PT_VBAK1.row[0].EDATU);
							_this.setFieldValueByFieldName("khjc",jsonobj.PT_VBAK1.row[0].SORT1);
							_this.setFieldValueByFieldName("crmno",jsonobj.PT_VBAK1.row[0].CRMLN);
							_this.setFieldValueByFieldName("dgyy",jsonobj.PT_VBAK1.row[0].AUGRU);
							_this.setFieldValueByFieldName("dgyy2",jsonobj.PT_VBAK1.row[0].BEZEI);
							_this.setFieldValueByFieldName("xsddscyq",jsonobj.PT_VBAK1.row[0].ZSCYQ);	
							_this.setFieldValueByFieldName("ddlxbz",jsonobj.PT_VBAK1.row[0].ABRVW);
							_this.setFieldValueByFieldName("khdqjb",jsonobj.PT_VBAK1.row[0].ZCUCLS);
							// 从表数据添加 
							for(var i=0;i<jsonobj.PT_VBAK1.row.length;i++){
								_this.addRowData("uf_erp_ywgl_ddpsyqfsqxx", {
									"hxmh":jsonobj.PT_VBAK1.row[i].POSNR, "cpid":jsonobj.PT_VBAK1.row[i].MATNR,
									"cp":jsonobj.PT_VBAK1.row[i].ZTEXT,"sl":jsonobj.PT_VBAK1.row[i].ZMENG,"gc":jsonobj.PT_VBAK1.row[i].WERKS,
									"cjp":jsonobj.PT_VBAK1.row[i].ZYQDT,"khwlbh":jsonobj.PT_VBAK1.row[i].KDMAT,"bzxxbbh":jsonobj.PT_VBAK1.row[i].ZPAK_NO});
							}
							params = "{PI_VKORG:'"+jsonobj.PT_VBAK1.row[0].VKORG+"',PI_KUNNR:'"+jsonobj.PT_VBAK1.row[0].KUNNR+"'}";
							$.ajax({
								type : "post",
								data : {
									"saps":"sapConnectConfig",
									"functionName" : "ZSDI0008",
									"jsonParams" : params
								},
								async : false,
								url : ctx + "/basicdata/sap/executeRFC",
								success : function(scdresult) {
									if (scdresult.success) {
										var scdjsonobj = eval('(' + scdresult.data + ')');
										if(scdjsonobj.PT_RETURN.rows>0){
											_this.setFieldValueByFieldName("xyed",scdjsonobj.PT_ZSDS0029.row[0].KLIMK);
											_this.setFieldValueByFieldName("yyed",scdjsonobj.PT_ZSDS0029.row[0].OBLIG);
											_this.setFieldValueByFieldName("yced",scdjsonobj.PT_ZSDS0029.row[0].EXCESS);
											//给从表添加行,并给从表设值
											//_this.addRowData("uf_erp_ywgl_ddqfxx", {"hxmh":"1", "cpid":"2"});
											//_this.setFieldValueByFieldName("yyed",result.data);
										}                     
									} else {
										oAlert2(result.msg);
									}
								}
							});
						}else{
							oAlert2(jsonobj.PO_RETURN.MESSAGE);
						}
					} else {
						oAlert2(result.msg);
					}
				}
			});


		});
        
        // 解除订单评审状态
        this.$("#btn_jcddpszt").click(function(){	
            var param1 = _this.getFieldValueByFieldName("xsddh");
            if(param1==''){
            	oAlert2("请输入订单号！");
            }else{
		        var params = '{"PI_VBELN":"' + param1 + '"}';
				$.ajax({
					type : "post",
					data : {
						"saps":"sapConnectConfig",
						"functionName" : "ZSDI0001",
						"jsonParams" : params
					},
					async : false,
					url : ctx + "/basicdata/sap/executeRFC",
					dataType : "json",
					success : function(result) {
						var obj = eval("(" + result.data + ")");
						oAlert2(obj.PT_RETURN.row[0].MESSAGE);
					}
				});
            }
        });
	},
        
	/**
	 *LDX_人事管理_调动申请表
	*/
	userform_ldx_rsgl_ddsqb:function(){
		_this = this;
		
		//----------------------------
		//单选框事件  对于隐藏的字段，如nunber1，需在源码中为nunber1增加ID，具体参见测试表单1
		_this.$("#kbmddlx").hide();
		var control =_this.getControl("ddlx");
		_this.$("input[name='"+control.getCtlName()+"'][value='1']").change(function(){
			 _this.$("#kbmddlx").hide();

	   	   });
		_this.$("input[name='"+control.getCtlName()+"'][value='2']").change(function(){
		      _this.$("#kbmddlx").show();

	   	   });	
		//----------------------------
		
		//表单onload时，设置默认值
		var userInfo = SpringSecurityUtils.getUserDetails();
		this.setFieldValueByFieldName("gh",userInfo.employeeNumber);
		this.setFieldValueByFieldName("xm",userInfo.userName);
		
		var loadLeve = function(jsonobj){					
			if(jsonobj==null){
				alert("没有找到对应的数据");
				return;
			}
			
			var pt = "PT_BAPIHRS0001";
			if(jsonobj[pt].rows==="0"){
				alert("没有找到对应的数据");
				return;
			}
			var data = jsonobj[pt].row[0];
			
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("WERKS",data.WERKS);	
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("BTRTL",data.BTRTL);
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("PERSK",data.BTRTL);
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("SHORT3",data.BTRTL);
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("BUTXT",data.BTRTL);
			
			//姓名
			_this.setFieldValueByFieldName("xm",data.ENAME);
			//部门
			_this.setFieldValueByFieldName("szbm",data.ORGTX);
			//岗位
			_this.setFieldValueByFieldName("cyzw",data.PLSTX);	
			//人事子范围
			_this.setFieldValueByFieldName("rszfw",data.BTRTX);
			//员工子组
			_this.setFieldValueByFieldName("ygzz",data.PTEXT);
			//所在公司
			_this.setFieldValueByFieldName("szgs",data.NAME1);
			//员工组
			_this.setFieldValueByFieldName("ygz",data.PGTXT);
		};
		getHR_ZHRI001004(_this.getFieldValueByFieldName("gh"),loadLeve);
		
		//员工工号文本框
		this.$("input[name='gh']").click(function(){
			$.unit.open({
				title : "选择人员",
				labelField : "ygxm",
				valueField : "ygbh",
				type : "MyDept",
				multiple:false, 
				afterSelect : function(retVal) {
					_this.setFieldValueByFieldName("gh",retVal.employeeNumber);
					_this.setFieldValueByFieldName("xm",retVal.name);							
					getHR_ZHRI001004(_this.getFieldValueByFieldName("gh"),loadLeve);
				},
				selectType : 4	
			});
		});
		
		//员工工号文本框
		this.$("input[name='gh']").focus(function(){
			this.blur();
		});	
		
		//新公司下拉框
		_this.$("select[name='xgs']").change(function(){
			_this.$("input[name='xgsview']").trigger("click");
	   	});
		
		/*//新公司下拉框选择后弹出视图选择后
		this.getControl("xgsview").bind("afterDialogSelect",function(element,ctlName, data,fieldsKeyValue){
			//data,选择后返回的json数据  
			data = jsonKeytoUcase(data);
			_this.setFieldValueByFieldName("xrszfw",data.BTRTL);
		});*/
		
	},   	

   uf_ddj_cwl_thd:function(){
		var _this = this;
		
		//金额大写
		_this.$("#btn_jedx").click(function(){
			_this.setFieldValueByFieldName("jedx",uppercaseMoney(_this.getFieldValueByFieldName("hj")));
		});	
		
		//注册公式
		$.ControlManager.registFormula(
				{
					   triggerElements:["hj"],//字段名数组,这些元素变化会触发公式
				       formula:function(){//this指向当前控件  
				    	   _this.setFieldValueByFieldName("jedx",uppercaseMoney(_this.getFieldValueByFieldName("hj")));
				       }
				}
		);
		
		//注册公式
		$.ControlManager.registFormula(
				{
				       triggerElements:["uf_ddj_cwl_thxxxx:je"],//字段名数组,这些元素变化会触发公式
				       formula:function(){//this指向当前控件  
				    	   _this.getControl("hj").setValue(sumOfColumn("uf_ddj_cwl_thxxxx:je", _this));
				       }
				}
		);
		
		//注册公式
		$.ControlManager.registFormula(
				{
				       triggerElements:["uf_ddj_cwl_thxxxx:chsl", "uf_ddj_cwl_thxxxx:dj"],//字段名数组,这些元素变化会触发公式
				       formula:function(){//this指向当前控件  
				    	   //_this.getControl("hj").setValue(sumOfColumn("uf_ddj_cwl_thxxxx:je", _this));
				    	   var rowId = this.getDataUuid();
				    	   var chslctl = _this.getControl( "chsl", rowId);
							var djctl = _this.getControl( "dj", rowId);
							_this.getControl("je", rowId).setValue(parseNumber(chslctl.getValue())*parseNumber(djctl.getValue()));
				    	   
				       }
				}
		);
	},
	
	userform_cwgl_fksq:function(){
		var _this = this;
		// 计算两个输入框的和,并设值给其他字段
		var control1 =_this.getControl("bhsje");
		var control2 =_this.getControl("se");
		var resultInput="xxje";
		_this.$("input[name='"+control1.getCtlName()+"']").change(function(){
			var number1 = Number(_this.$("input[name='"+control1.getCtlName()+"']").val());
			var number2 = Number(_this.$("input[name='"+control2.getCtlName()+"']").val());
			_this.setFieldValueByFieldName(resultInput,number1+number2);
	   	});
		_this.$("input[name='"+control2.getCtlName()+"']").change(function(){
			var number1 = Number(_this.$("input[name='"+control1.getCtlName()+"']").val());
			var number2 = Number(_this.$("input[name='"+control2.getCtlName()+"']").val());
			_this.setFieldValueByFieldName(resultInput,number1+number2);
	   	});
		//金额大写
		_this.$("#btn_dxje").click(function(){
			_this.setFieldValueByFieldName("dxje",uppercaseMoney(_this.getFieldValueByFieldName("xxje")));
		});		
		
	},
	
	
	
	
	
	uf_ddj_cwl_thxxxx:function(){
		var _this = this;
		// 计算两个输入框的乘积
		var control1 =_this.getControl("chsl");
		var control2 =_this.getControl("dj");
		var resultInput="je";
		_this.$("input[name='"+control1.getCtlName()+"']").change(function(){
			var number1 = Number(_this.$("input[name='"+control1.getCtlName()+"']").val());
			var number2 = Number(_this.$("input[name='"+control2.getCtlName()+"']").val());
			_this.setFieldValueByFieldName(resultInput,number1*number2);
	   	});
		_this.$("input[name='"+control2.getCtlName()+"']").change(function(){
			var number1 = Number(_this.$("input[name='"+control1.getCtlName()+"']").val());
			var number2 = Number(_this.$("input[name='"+control2.getCtlName()+"']").val());
			_this.setFieldValueByFieldName(resultInput,number1*number2);
	   	});	
	},
	
	uf_scgl_ycgsfysqpzn:function(){//异常工时费用申请品质内  
		var _this = this;
		// 计算两个输入框的乘积
		var control1 =_this.getControl("hjgs");
		var control2 =_this.getControl("gsfl");
		var resultInput="ycgsfy";
		_this.$("input[name='"+control1.getCtlName()+"']").change(function(){
			var number1 = Number(_this.$("input[name='"+control1.getCtlName()+"']").val());
			var number2 = Number(_this.$("input[name='"+control2.getCtlName()+"']").val());
			_this.setFieldValueByFieldName(resultInput,number1*number2);
	   	});
		_this.$("input[name='"+control2.getCtlName()+"']").change(function(){
			var number1 = Number(_this.$("input[name='"+control1.getCtlName()+"']").val());
			var number2 = Number(_this.$("input[name='"+control2.getCtlName()+"']").val());
			_this.setFieldValueByFieldName(resultInput,number1*number2);
	   	});	
	},
	


	uf_ddj_cwl_shd:function(){
		var _this = this;
				
		//金额大写
		_this.$("#btn_jedx").click(function(){
			_this.setFieldValueByFieldName("jedx",uppercaseMoney(_this.getFieldValueByFieldName("hj")));
		});
		
	},
	
	uf_ddj_cwl_fkd:function(){
		var _this = this;
				
		//金额大写
		_this.$("#btn_jedx").click(function(){
			_this.setFieldValueByFieldName("jedx",uppercaseMoney(_this.getFieldValueByFieldName("je")));
		});
		
	},
 
   
    uf_dj_cwl_jksq:function(){
	var _this = this;
			
	//金额大写
	_this.$("#btn_jedx").click(function(){
		_this.setFieldValueByFieldName("jedx",uppercaseMoney(_this.getFieldValueByFieldName("xjjkje")));
	});
	
    },	

    uf_ddj_cwl_jkdqy:function(){
    	var _this = this;
    			
    	//金额大写
    	_this.$("#btn_jedx").click(function(){
    		_this.setFieldValueByFieldName("jedx",uppercaseMoney(_this.getFieldValueByFieldName("jkje")));
    	});
    	
        },
        
        
        uf_dj_cwl_gzfksq:function(){
    		var _this = this;
    		
    		//清除信息
    		_this.$("#btn_qcxx").click(function(){
    			var  options =  _this.cache.get.call(_this, cacheType.options);
    			_this.dyform(options);
    		});
    				
    		//金额大写
    		_this.$("#btn_jedx").click(function(){
    			_this.setFieldValueByFieldName("jedx",uppercaseMoney(_this.getFieldValueByFieldName("sqje")));
    		});
    		
    	},
    	
    	/**
    	 * ERP_计划管理_交期变更申请表 
    	 */
    	uf_erp_jhgl_jqbgsqb:function(){
    		var _this = this;
    		var childFormId = "uf_jhgl_jqbgsqb";
    		
    		_this.$("#btn_dr").click(function(){
    			var vbeln = _this.getFieldValueByFieldName("xsdd");						
    			var params='{"PI_VBELN":"'+vbeln+'"}';	
    			$.ajax({
    				type : "post",
    				data : {
    					"saps":"sapConnectConfig",
    					"functionName" : "ZOA_SO_HIS_READ",
    					"jsonParams" : params,
    				},
    				async : false,
    				url : ctx + "/basicdata/sap/executeRFC",
    				success : function(result) {
    					if (result.success) {
    						// 保存成功刷新列表
    						//转成对象
    						var jsonobj = eval('(' + result.data + ')');
    						if(jsonobj.PT_VBAK3.row.length<1){
    							alert(jsonobj.PO_RETURN.MESSAGE);
    						}else{
    							// 删除已有从表行
    							var childRow = _this.getAllRowData({id:childFormId});
    							for(var i=0;i<childRow.length;i++){
    								_this.deleteRowData(childFormId,childRow[i].id);
    							}
    							// 导入订单数据 
    							var rowobj = jsonobj.PT_VBAK3.row[0];
    							_this.setFieldValueByFieldName("khmc",rowobj.SORTL);
    							for(var i=0;i<jsonobj.PT_VBAK3.row.length;i++){
    								rowobj = jsonobj.PT_VBAK3.row[i];
    								var rowjson = {};
    								rowjson.hxmh = rowobj.POSNR;
    								rowjson.cpid = rowobj.MATNR;
    								rowjson.cpmc = rowobj.ARKTX;
    								rowjson.sl = rowobj.KWMENG;
    								//rowjson.jhrq = rowobj.EDATU;
    								//rowjson.gqjg = rowobj.ZKBETR;
    								rowjson.gqcjq = rowobj.ZBPDATE;
    								rowjson.gqyhrq = rowobj.ZEGDATE;//更后船交期
    								rowjson.gqzgrq = rowobj.GLTRS;//更后装柜日期
    								rowjson.gqwgrq = rowobj.EDATU;
    								rowjson.scgc = rowobj.WERKS;
    								_this.addRowData(childFormId,rowjson);
    							}
    						}
    					} else {
    						alert(result.msg);
    					}
    				}
    			});
    		});
    		//清除信息
    		_this.$("#btn_qcxx").click(function(){
    			var  options =  _this.cache.get.call(_this, cacheType.options);
    			_this.dyform(options);
    		});
    		
    		//交期变更  更新完工日期赋值按钮实现
    		_this.$("#btn_ghwgrqfz").click(function(){
    			
    			var formUuid = _this.getFormUuid(childFormId); 
    			var ctl = _this.getSubformControl(formUuid);
    			var forminfo = {};
    			forminfo.id = childFormId;
    			var allRowData = _this.getAllRowData(forminfo);//获取所有的数据
    			if(allRowData != null && allRowData.length >1){
    				var theFirstRowData = allRowData[0];
    				var finishDate = theFirstRowData.ghwgrq;//得到第一条更后完工数据的日期
    				
    				for(var i=0;i<allRowData.length;i++){
    					var uuid = allRowData[i+1].uuid;//数据uuid
    					var id = allRowData[i+1].id;//数据id
    					var json = {
    							"ghwgrq":finishDate,
    							"id":uuid, 
    							"uuid":id}
    					_this.updateRowData(childFormId, json);//更新行数据
    				}
    			}
    			
    		});
    		
    	},
  
    	/**
    	 * A类订单变更申请
    	 * */
    	uf_erp_ywgl_alddbgsq:function(){
    		var _this = this;
    		
    		//清除信息
    		_this.$("#btn_qcxx").click(function(){
    			var  options =  _this.cache.get.call(_this, cacheType.options);
    			_this.dyform(options);
    		});
    		
    		//导入供应商编号
    		this.$("#btn_dddr").click(function(){
    			var vbeln = _this.getFieldValueByFieldName("xsddh");						
    			var params='{"PI_VBELN":"'+vbeln+'"}';	
    			$.ajax({
    				type : "post",
    				data : {
    					"saps":"sapConnectConfig",
    					"functionName" : "ZOA_SO_HIS_READ",
    					"jsonParams" : params,
    				},
    				async : false,
    				url : ctx + "/basicdata/sap/executeRFC",
    				success : function(result) {
    					if (result.success) {
    						// 保存成功刷新列表
    						//转成对象
    						var jsonobj = eval('(' + result.data + ')');
    						if(jsonobj.PT_VBAK3.row.length<1){
    							alert(jsonobj.PO_RETURN.MESSAGE);
    						}else{
    							var rowobj = jsonobj.PT_VBAK3.row[0];
    							_this.setFieldValueByFieldName("khjc",rowobj.SORTL);
    							_this.setFieldValueByFieldName("xszz",rowobj.VKORG);
    							for(var i=0;i<jsonobj.PT_VBAK3.row.length;i++){
    								rowobj = jsonobj.PT_VBAK3.row[i];
    								var rowjson = {};
    								rowjson.hxmh = rowobj.POSNR;
    								rowjson.cpid = rowobj.MATNR;
    								rowjson.cpms = rowobj.ARKTX;
    								rowjson.ddsl = rowobj.KWMENG;
    								rowjson.jhrq = rowobj.EDATU;
    								rowjson.gqjg = rowobj.ZKBETR;
    								rowjson.gqcjq = rowobj.ZBPDATE;
    								rowjson.gqyhrq = rowobj.ZEGDATE;
    								rowjson.gqzgrq = rowobj.GLTRS;
    								rowjson.gqwgrq = rowobj.EDATU;
    								_this.addRowData("uf_erp_ywgl_alddbgsqxx",rowjson);
    							}
    						}
    					} else {
    						alert(result.msg);
    					}
    				}
    			});
    		});	
    		
    	},
    	
    	/**
    	 * ERP_业务管理_B类订单变更申请
    	 * */
    	uf_ywgl_blddbgsq:function(){
    		var _this = this;
    		
    		//清除信息
    		_this.$("#btn_qcxx").click(function(){
    			var  options =  _this.cache.get.call(_this, cacheType.options);
    			_this.dyform(options);
    		});
    		
    		//订单导入
    		this.$("#btn_dddr").click(function(){
    			//销售订单号
    			var vbeln = _this.getFieldValueByFieldName("xsddh");						
    			var params='{"PI_VBELN":"'+vbeln+'"}';	
    			
    			$.ajax({
    				type : "post",
    				data : {
    					"saps":"sapConnectConfig",
    					"functionName" : "ZOA_SO_HIS_READ",
    					"jsonParams" : params,
    				},
    				async : false,
    				url : ctx + "/basicdata/sap/executeRFC",
    				success : function(result) {
    					if (result.success) {
    						// 保存成功刷新列表
    						//转成对象
    						var jsonobj = eval('(' + result.data + ')');
    						if(jsonobj.PT_VBAK3.row.length<1){
    							alert(jsonobj.PO_RETURN.MESSAGE);
    						}else{
    							var rowobj = jsonobj.PT_VBAK3.row[0];
    							_this.setFieldValueByFieldName("khjc",rowobj.SORTL);
    							_this.setFieldValueByFieldName("xszz",rowobj.VKORG);
    							for(var i=0;i<jsonobj.PT_VBAK3.row.length;i++){
    								rowobj = jsonobj.PT_VBAK3.row[i];
    								var gqsl = rowobj.KWMENG;
    								if(gqsl!=''&&gqsl.indexOf(".")!=-1){
    									gqsl = gqsl.substring(0,gqsl.indexOf("."));
    								}
    								var rowjson = {};
    								rowjson.hxmh = rowobj.POSNR;
    								rowjson.cpid = rowobj.MATNR;
    								rowjson.cpms = rowobj.ARKTX;
    								rowjson.gqsl = gqsl;
    								rowjson.jhrq = rowobj.EDATU;
    								rowjson.gqjg = rowobj.ZKBETR;
    								rowjson.gqcjq = rowobj.ZBPDATE;
    								rowjson.gqyhrq = rowobj.ZEGDATE;
    								rowjson.gqzgrq = rowobj.GLTRS;
    								rowjson.gqwgrq = rowobj.EDATU;
    								_this.addRowData("uf_ywgl_blddbgnr",rowjson);
    							}
    						}
    					} else {
    						alert(result.msg);
    					}
    				}
    			});
    		});
    	},
    	
    	/**
    	 * ERP_业务管理_B类订单变更申请
    	 * */
    	uf_ywgl_blddbgsq:function(){
    		var _this = this;
    		//控制隐藏
    		var control =_this.getControl("sxbg1");
    		control.bind("afterSetValue", function(value){
    			_this.uf_ywgl_blddbgsq_receiverChange();
    	   	   }, true);
    		
    		this.uf_ywgl_blddbgsq_receiverChange();
    		//清除信息
    		_this.$("#btn_qcxx").click(function(){
    			var  options =  _this.cache.get.call(_this, cacheType.options);
    			_this.dyform(options);
    		});
    		
    		//订单导入
    		this.$("#btn_dddr").click(function(){
    			//销售订单号
    			var vbeln = _this.getFieldValueByFieldName("xsddh");						
    			var params='{"PI_VBELN":"'+vbeln+'"}';	
    			
    			$.ajax({
    				type : "post",
    				data : {
    					"saps":"sapConnectConfig",
    					"functionName" : "ZOA_SO_HIS_READ",
    					"jsonParams" : params,
    				},
    				async : false,
    				url : ctx + "/basicdata/sap/executeRFC",
    				success : function(result) {
    					if (result.success) {
    						// 保存成功刷新列表
    						//转成对象
    						var jsonobj = eval('(' + result.data + ')');
    						if(jsonobj.PT_VBAK3.row.length<1){
    							alert(jsonobj.PO_RETURN.MESSAGE);
    						}else{
    							var rowobj = jsonobj.PT_VBAK3.row[0];
    							_this.setFieldValueByFieldName("khjc",rowobj.SORTL);
    							_this.setFieldValueByFieldName("xszz",rowobj.VKORG);
    							for(var i=0;i<jsonobj.PT_VBAK3.row.length;i++){
    								rowobj = jsonobj.PT_VBAK3.row[i];
    								var gqsl = rowobj.KWMENG;
    								if(gqsl!=''&&gqsl.indexOf(".")!=-1){
    									gqsl = gqsl.substring(0,gqsl.indexOf("."));
    								}
    								var rowjson = {};
    								rowjson.hxmh = rowobj.POSNR;
    								rowjson.cpid = rowobj.MATNR;
    								rowjson.cpms = rowobj.ARKTX;
    								rowjson.gqsl = gqsl;
    								rowjson.jhrq = rowobj.EDATU;
    								rowjson.gqjg = rowobj.ZKBETR;
    								rowjson.gqcjq = rowobj.ZBPDATE;
    								rowjson.gqyhrq = rowobj.ZEGDATE;
    								rowjson.gqzgrq = rowobj.GLTRS;
    								rowjson.gqwgrq = rowobj.EDATU;
    								_this.addRowData("uf_ywgl_blddbgnr",rowjson);
    							}
    						}
    					} else {
    						alert(result.msg);
    					}
    				}
    			});
    		});
    	},  	

    	uf_ywgl_blddbgsq_receiverChange:function(){
    		var _this = this;
    		var control =_this.getControl("sxbg1");
    		var value =  control.getValue();
	   		 if(value == null || value.length == 0){
	   		    _this.$("#sxbg2").show();
	   		 }else{
			    _this.$("#sxbg2").hide();
			}
    	},
    	
    	/**
    	 * 内销订单评审与签发申请表
    	 * */
    	uf_erp_ywgl_nxddpsyqfsq:function(){
    		var _this = this;
    		
    		//清除信息
    		_this.$("#btn_qcxx").click(function(){
    			var  options =  _this.cache.get.call(_this, cacheType.options);
    			_this.dyform(options);
    		});
    		
    		//订单导入
    		this.$("#dddr").click(function(){
    			//销售订单号
    			var vbeln = _this.getFieldValueByFieldName("xsddh");						
    			var params='{"PI_VBELN":"'+vbeln+'"}';	
    			
    			$.ajax({
    				type : "post",
    				data : {
    					"saps":"sapConnectConfig",
    					"functionName" : "ZOA_SO_EVA_READ",
    					"jsonParams" : params,
    				},
    				async : false,
    				url : ctx + "/basicdata/sap/executeRFC",
    				success : function(result) {
    					if (result.success) {
    						alert(result.data);
    						return;
    						// 保存成功刷新列表
    						//转成对象
    						var jsonobj = eval('(' + result.data + ')');
    						
    						_this.setFieldValueByFieldName("rfcrs",result.data);
    					} else {
    						alert(result.msg);
    					}
    				}
    			});
    		});	
    		
    	},
    	//产品认证申请表
    	uf_leedarson_cprzsqb:function(){
    		var _this = this;
    						
    		//单选框事件  对于隐藏的字段，如nunber1，需在源码中为nunber1增加ID，具体参见测试表单1 
    		//alert(2);
    		var control =_this.getControl("sqztywmc");
    		control.bind("afterSetValue", function(value){
    			_this.uf_leedarson_cprzsqb_receiverChange();
    	   	   }, true);
    		
    		this.uf_leedarson_cprzsqb_receiverChange();
    		
    	},   	

    	uf_leedarson_cprzsqb_receiverChange:function(){
    		var _this = this;
    		var control =_this.getControl("sqztywmc");
    		var value =  control.getValue();
	   		 if(value == null || value.length == 0){
	   		_this.$("#gsmc").hide();
	   	    	_this.$("#gsdz").hide();
	   	    	_this.$("#qtsqzt").hide();
	   		 }else if(value == "5"){ 
				_this.$("#gsmc").show();
			        _this.$("#gsdz").show();
			}else if(value == "4"){
				_this.$("#qtsqzt").show();
			}else{
			_this.$("#gsmc").hide();
	   	    	_this.$("#gsdz").hide();
	   	    	_this.$("#qtsqzt").hide();
			}
    	},
  
    	uf_hdx_cwl_hkd:function(){
    		var _this = this;
    				
    		//金额大写
    		_this.$("#btn_jedx").click(function(){
    			_this.setFieldValueByFieldName("jedx",uppercaseMoney(_this.getFieldValueByFieldName("hkzje")));
    		});
    		
    	},
    	uf_dj_cwl_clfksq:function(){
    		var _this = this;
      				
      		//金额大写
      		_this.$("#btn_jedx").click(function(){
      			_this.setFieldValueByFieldName("jedx",uppercaseMoney(_this.getFieldValueByFieldName("sqje")));
      		});
      		
      	},
    	
    	uf_ddj_cwl_fybxdqy:function(){
    		var _this = this;
    						
    		//单选框事件  对于隐藏的字段，如nunber1，需在源码中为nunber1增加ID，具体参见测试表单1 
    		//alert(2);
    		var control =_this.getControl("sklx");
    		control.bind("afterSetValue", function(value){
    			_this.uf_ddj_cwl_fybxdqy_receiverChange();
    	   	   }, true);
    		
    		this.uf_ddj_cwl_fybxdqy_receiverChange();
    		
    	},   	
    	/***
    	 * 收款人变化时处理函数 
    	 */
    	uf_ddj_cwl_fybxdqy_receiverChange:function(){
    		var _this = this;
    		var control =_this.getControl("sklx");
    		var value =  control.getValue();
	   		 if(value == null || value.length == 0){ 
	   			_this.$("#bbskr").hide();
	   	    	_this.$("#zh").hide();
	   	    	_this.$("#wqyskdw").hide();
	   		 }else if(value == "1"){ 
				  _this.$("#bbskr").show();
			      _this.$("#zh").show();
			      _this.$("#wqyskdw").hide();
			}else{
				 _this.$("#bbskr").hide();
	    		  _this.$("#zh").hide();
	    		  _this.$("#wqyskdw").show();
			} 
   		
    	},

       	//组织及编制变更申请单
    	uf_rsgl_zzjbzbgsqd:function(){
    		var _this = this;

    		var control =_this.getControl("sqyy");
    		control.bind("afterSetValue", function(value){
    			_this.uf_rsgl_zzjbzbgsqd_receiverChange();
    	   	   }, true);
    		
    		this.uf_rsgl_zzjbzbgsqd_receiverChange();
    		
    	},   	

    	uf_rsgl_zzjbzbgsqd_receiverChange:function(){
    		var _this = this;
    		var control =_this.getControl("sqyy");
    		var value =  control.getValue();
	   		 if(value == null || value.length == 0){
	 	   		_this.$("#zzbgxx").hide();
	   	    	_this.$("#zwbgxx").hide();
	   		 }else if(value == "组织变更"){ 
				_this.$("#zzbgxx").show();
			    _this.$("#zwbgxx").hide();
			 }else if(value == "编制变更"){
			    _this.$("#zzbgxx").hide();
	   	    	_this.$("#zwbgxx").show();
			 }else{
			    _this.$("#zzbgxx").show();
	   	    	_this.$("#zwbgxx").show();
			 }
    	},


    	//文件分发
    	uf_tylc_wjff:function(){
    		var _this = this;
    						
    		var control =_this.getControl("wjzt");
    		_this.$("input[name='"+control.getCtlName()+"'][value='1']").change(function(){
  		      _this.$("#skfxsj").show();

    	   	   });
    		_this.$("input[name='"+control.getCtlName()+"'][value='2']").change(function(){
    		  _this.$("#skfxsj").hide();
    		      
    	   	   });		
    	},
    	
    	//无发票报销申请表
    	uf_ddj_cwl_wfpbxsqb:function(){
    		var _this = this;
    		var control =_this.getControl("sklx");
    		control.bind("afterSetValue", function(value){
    			_this.uf_ddj_cwl_wfpbxsqb_receiverChange();
    	   	   }, true);    		
    		this.uf_ddj_cwl_wfpbxsqb_receiverChange();
    		//从表数量合计
    		$.ControlManager.registFormula(
    				{
    					triggerElements:["uf_hdx_cwl_kpsqbxxxx:je", "xkpzje"],//字段名数组,这些元素变化会触发公式
    					formula:function(){//this指向当前控件 
    						// var xkpzjeCtl = _this.getControl("xkpzje");//取得控件
    						//  var xkpzjeVal = xkpzjeCtl.getValue();//取得控件的值   
    						//xkpzjeVal = (xkpzjeVal == ""? 0: parseFloat(xkpzjeVal));
    						_this.getControl("jezj").setValue(sumOfColumn("uf_hdx_cwl_kpsqbxxxx:je", _this));
    					}
    				}
    			);  		
    	},  
    	
    	//质量退货申请
    	uf_ddj_cwl_thsqb:function(){
    		var _this = this;
    	$.ControlManager.registFormula(
    			{
    				triggerElements:["uf_ddj_cwl_thsqxxxx:zlwtsl", "uf_ddj_cwl_thsqxxxx:pssl", "uf_ddj_cwl_thsqxxxx:hdsl", "uf_ddj_cwl_thsqxxxx:sjdhsl"],//字段名数组,这些元素变化会触发公式
    				formula:function(){//this指向当前控件 
    					_this.getControl("hjzlwtsl").setValue(sumOfColumn("uf_ddj_cwl_thsqxxxx:zlwtsl", _this));
    					_this.getControl("hjpssl").setValue(sumOfColumn("uf_ddj_cwl_thsqxxxx:pssl", _this));
    					_this.getControl("hjhdsl").setValue(sumOfColumn("uf_ddj_cwl_thsqxxxx:hdsl", _this));
    					_this.getControl("hjsjdhsl").setValue(sumOfColumn("uf_ddj_cwl_thsqxxxx:sjdhsl", _this));
    				}
    			}
    		);
    	}, 
    	
    	//现金折扣申请表
    	uf_erp_ywgl_xjzksqd:function(){
    		var _this = this;
    		$.ControlManager.registFormula(
    			{
    				triggerElements:["uf_erp_ywgl_xjzksqddtbg:ysje", "uf_erp_ywgl_xjzksqddtbg:zkhysje"],//字段名数组,这些元素变化会触发公式
    				formula:function(){//this指向当前控件 
    					_this.getControl("ysje").setValue(sumOfColumn("uf_erp_ywgl_xjzksqddtbg:ysje", _this));
    					_this.getControl("zkhysje").setValue(sumOfColumn("uf_erp_ywgl_xjzksqddtbg:zkhysje", _this));
    				}
    			}
    		);
		//注册公式--从表列项求和给从表字段设值
		$.ControlManager.registFormula(
				{
				       triggerElements:["uf_erp_ywgl_xjzksqddtbg:ysje", "uf_erp_ywgl_xjzksqddtbg:pzzkl"],//字段名数组,这些元素变化会触发公式
				       formula:function(){//this指向当前控件  
				    	   var rowId = this.getDataUuid();
				    	   var ysjectl = _this.getControl( "ysje", rowId);
					   var pzzklctl = _this.getControl( "pzzkl", rowId);
					   _this.getControl("zkhysje", rowId).setValue(parseNumber(ysjectl.getValue())*parseNumber(pzzklctl.getValue()));
				    	   
				       }
				}
		);
        	}, 
        	//礼品请购单
	uf_leedarson_xzgl_lpqgd:function(){
		var _this = this;
		$.ControlManager.registFormula(
				{
					triggerElements:["uf_leedarson_xzgl_qgxx:sl", "uf_leedarson_xzgl_qgxx:dg"],//字段名数组,这些元素变化会触发公式
							       formula:function(){//this指向当前控件  
							    	   //_this.getControl("hj").setValue(sumOfColumn("uf_ddj_cwl_thxxxx:je", _this));
							    	   var rowId = this.getDataUuid();
							    	   var chslctl = _this.getControl( "sl", rowId);
										var djctl = _this.getControl( "dg", rowId);
										_this.getControl("hj", rowId).setValue(parseNumber(chslctl.getValue())*parseNumber(djctl.getValue()));
							    	   
							       }
							}
					);
    	}, 
            	
 //DJ_人事类_转正申请表(鼎吉)
  uf_rsl_zzsqb:function(){
		var _this = this;
		//注册公式--从表列项求和给从表字段设值
		$.ControlManager.registFormula(
				{
				       triggerElements:["jbgz", "zwgz", "qtjt"],//字段名数组,这些元素变化会触发公式
				       formula:function(){//this指向当前控件  
				    	//   var rowId = this.getDataUuid();
				    	   var jbgzctl = _this.getControl( "jbgz");
					   var zwgzctl = _this.getControl( "zwgz");
                                           var qtjtctl = _this.getControl( "qtjt");
					   _this.getControl("hj").setValue(parseNumber(jbgzctl.getValue())+parseNumber(zwgzctl.getValue())+parseNumber(qtjtctl.getValue()));
				    	   
				       }
				}
		);
    	}, 
    	
 //加工申请单
    	uf_ddj_jhgl_jgsqd:function(){
    		var _this = this;
			//注册公式
    		$.ControlManager.registFormula(
    				{
    				       triggerElements:["uf_ddj_jhgl_jgsqxx:sl", "uf_ddj_jhgl_jgsqxx:dj"],//字段名数组,这些元素变化会触发公式
    				       formula:function(){//this指向当前控件  
    				    	   //_this.getControl("hj").setValue(sumOfColumn("uf_ddj_cwl_thxxxx:je", _this));
    				    	   var rowId = this.getDataUuid();
    				    	   var chslctl = _this.getControl( "sl", rowId);
    							var djctl = _this.getControl( "dj", rowId);
    							_this.getControl("hj", rowId).setValue(parseNumber(chslctl.getValue())*parseNumber(djctl.getValue()));
    				    	   
    				       }
    				}
    		);
    			//注册公式--从表列项求和并给主表字段设值
    		$.ControlManager.registFormula(
    				{
    				       triggerElements:["uf_ddj_jhgl_jgsqxx:hj"],//字段名数组,这些元素变化会触发公式
    				       formula:function(){//this指向当前控件  
    				    	   _this.getControl("jehj").setValue(sumOfColumn("uf_ddj_jhgl_jgsqxx:hj", _this));
    				       }
    				}
    		);
        	}, 
        	
        	
 //HDX_国内业务类_区域（光源）补货单(发货申请)
	uf_hdx_gnywl_qygybhd:function(){
		var _this = this;
        $.ControlManager.registFormula(
    			{
    				triggerElements:["uf_hdx_gnywl_qygybhxx1:fhsl", "uf_hdx_gnywl_qygybhxx1:je"],//字段名数组,这些元素变化会触发公式
    				formula:function(){//this指向当前控件 
    					_this.getControl("hj").setValue(sumOfColumn("uf_hdx_gnywl_qygybhxx1:fhsl", _this));
    					_this.getControl("hjje").setValue(sumOfColumn("uf_hdx_gnywl_qygybhxx1:je", _this));
    				}
    			}
    		);
		//注册公式--从表列项求和给从表字段设值
		$.ControlManager.registFormula(
				{
				       triggerElements:["uf_hdx_gnywl_qygybhxx1:fhsl", "uf_hdx_gnywl_qygybhxx1:dj"],//字段名数组,这些元素变化会触发公式
				       formula:function(){//this指向当前控件  
				    	   var rowId = this.getDataUuid();
				    	   var fhslctl = _this.getControl( "fhsl", rowId);
					   var djctl = _this.getControl( "dj", rowId);
					   _this.getControl("je", rowId).setValue(parseNumber(fhslctl.getValue())*parseNumber(djctl.getValue()));
				    	   
				       }
				}
		);
    	},

 	
    	/***
    	 * 收款人变化时处理函数 
    	 */
    	uf_ddj_cwl_wfpbxsqb_receiverChange:function(){
    		var _this = this;
    		var control =_this.getControl("sklx");
    		var value =  control.getValue();
	   		 if(value == null || value.length == 0){ 
	   			_this.$("#bbskr").hide();
	   	    	_this.$("#zh").hide();
	   	    	_this.$("#wqyskdw").hide();
	   		 }else if(value == "1"){ 
				  _this.$("#bbskr").show();
			      _this.$("#zh").show();
			      _this.$("#wqyskdw").hide();
			}else{
				 _this.$("#bbskr").hide();
	    		  _this.$("#zh").hide();
	    		  _this.$("#wqyskdw").show();
			} 
   		
    	},
   	
    	
    	
   //零星请购单 	
    	uf_ddj_cggl_lxqgd:function(){

            var _this = this;
  
    		this.getControl("cbzx").bind("afterDialogSelect", function(){
    			_this.getControl("cbzxdm").$editableElem.css("ime-mode", "disabled");//禁用输入法
    			_this.getControl("cbzxdm").$editableElem.keydown(function(){ 
    				return false;
    			});
    			_this.getControl("cbzxdm").setReadOnly(false);    						
    		});		
            this.$("#cbzx").click(function(){	
            //var param1=_this.getFieldValueByFieldName("xsddh");
	        //var params='{"PI_VBELN":"'+param1+'","PI_VKORG":"'+param2+'","PI_KUNNR":"'+param3+'"}';
			$.ajax({
				type : "post",
				data : {
					"saps":"sapConnectConfig",
					"functionName" : "ERP_ZHRI0050",
					"jsonParams" : params
				},
				async : false,
				url : ctx + "/basicdata/sap/executeRFC",
				success : function(result) {
					if (result.success) {
						//_this.setFieldValueByFieldName("xyed",jsonobj.PT_ZSDS0029.row[0].KLIMK);
						//_this.setFieldValueByFieldName("yyed",jsonobj.PT_ZSDS0029.row[0].OBLIG);
						//_this.setFieldValueByFieldName("yced",jsonobj.PT_ZSDS0029.row[0].EXCESS);
						var jsonobj = eval('(' + result.data + ')');
						_this.setFieldValueByFieldName("cbzxdm",result.data);                       
					} else {
						alert(result.msg);
					}
				}
			});
		});
            $.ControlManager.registFormula(
    				{
    				       triggerElements:["uf_ddj_cggl_lxqgdsqxx:hj"],//字段名数组,这些元素变化会触发公式
    				       formula:function(){//this指向当前控件  
    				    	   _this.getControl("jehj").setValue(sumOfColumn("uf_ddj_cggl_lxqgdsqxx:hj", _this));
    				       }
    				}
    		);
    			//注册公式
    		$.ControlManager.registFormula(
    				{
    				       triggerElements:["uf_ddj_cggl_lxqgdsqxx:sl", "uf_ddj_cggl_lxqgdsqxx:djqr"],//字段名数组,这些元素变化会触发公式
    				       formula:function(){//this指向当前控件  
    				    	   //_this.getControl("hj").setValue(sumOfColumn("uf_ddj_cwl_thxxxx:je", _this));
    				    	   var rowId = this.getDataUuid();
    				    	   var chslctl = _this.getControl( "sl", rowId);
    							var djctl = _this.getControl( "djqr", rowId);
    							_this.getControl("hj", rowId).setValue(parseNumber(chslctl.getValue())*parseNumber(djctl.getValue()));
    				    	   
    				       }
    				}
    		);        		
	},
    		
	//表单的id号：uf_meeting_anpi
	uf_meeting_anpi:function(){
		//获得时间占用资源控件
		var control =this.getControl("meeting_addr");
		control.getDisplayValue = function(){
				if(this.value == null || $.trim(this.value).length == 0){
					return "";
				}
				var val = eval("(" + this.value + ")");
				if(typeof val.uuid == "undefined" || $.trim(val.uuid ).length == 0){
					return "";
				}
				return val.employName;
		};
		control.render();//重新渲染
		var _this =this;
		//控件绑定事件
		control.bind("afterResourceSelect",function(ctlName, data){
			//测试代码：alert(data.employName);
			//获取控件对象
			//var controlCtl = _this.$("input[name='" + control.getCtlName() + "']");
			//controlCtl.val(data.employName);
			//获取控件开始时间
			var beginTimeCtl = _this.getControl("meeting_start_time");
			//设置控件的开始时间
			beginTimeCtl.setValue(data.beginTime);
			//获取控件结束时间
			var endTimeCtl = _this.getControl("meeting_end_time");
			//设置控件的结束时间
			endTimeCtl.setValue(data.endTime);
		});
		
	},
	
	
	/**
	 *LDX_人事管理_人员增补申请表
	*/
	uf_ldx_rsgl_ryzbsqb:function(){
		var _this = this;
		//---------------------------
		//单选框事件  对于隐藏的字段，如nunber1，需在源码中为nunber1增加ID，具体参见测试表单1
		_this.$("#ryzbxxgr").hide();
		_this.$("#ryzbxxfgr").hide();
		var control =_this.getControl("zbrylb");
		_this.$("input[name='"+control.getCtlName()+"'][value='1']").change(function(){
			 _this.$("#ryzbxxfgr").hide();
			 _this.$("#ryzbxxgr").show();

	   	   });
		_this.$("input[name='"+control.getCtlName()+"'][value='2']").change(function(){
			_this.$("#ryzbxxgr").hide(); 
			_this.$("#ryzbxxfgr").show();

	   	   });
		
		var control = this.getControl("ryqd_tck");
		
		//删除人才按钮
		this.$("#delete_person").click(function(){
			
			alert("ok");
		});
		
		//是否扩大编制  绑定事件
		_this.getControl("sfkdbz").bind("click",function(){
			var isExtend = _this.getControl("sfkdbz").getValue();
			if(isExtend == "1"){
				//1 -->否
				
			}
			if(isExtend == "4"){
				//4 -->是
				//是否扩大编制单选按钮选择“是”时，需弹出组织及编制变更申请流程
				var formFlowUuid = "62ca37c1-47b4-4ab8-a082-23936cd91b1f";
				var url = ctx + '/workflow/work/new?flowDefUuid=' + formFlowUuid; 
				window.open(url);
			}
		});
		//增补人员类别选择“临时工”，时需弹出临时工招聘申请表流程。
		_this.getControl("zbrylb").bind("click",function(){
			var extendPersonType = _this.getControl("zbrylb").getValue();
			//alert(extendPersonType);
			if(extendPersonType == "1"){
				//临时工整补申请表
				var formFlowUuid = "b53d03d4-1c74-4b37-831f-327ed090df9a";
				var url = ctx + '/workflow/work/new?flowDefUuid=' + formFlowUuid; 
				window.open(url);
			}
			
		});
		
		//添加人才按钮		
		this.$("#person_list").click(function(){
			var viewUuid = 'a555513d-42a3-4c7c-b9fc-a2d029019b28';
			path = "/basicdata/dyview/view_show?viewUuid="+ viewUuid +"&currentPage=1&openBy=dytable";
			//弹出框标题
			var title = "选择合适人才";
			//发起请求，获得视图的数据，弹出框
			$.ajax({
				async:false,
				cache:false,
				url : ctx + path,
				success:function(data){
					var json = new Object(); 
					json.content = "<div class='dnrw' style='width:99%;'>" +data+"</div>";
			        json.title = title;
			        json.height= 600;
			        json.width= 800;
			        showDialog(json);
			        
			        $(".dataTr").die().live("dblclick",function(){
			        	var paramsObj = new Object();
			        	//点击行，返回的jsonstr
			        	var jsonstr = $(this).attr("jsonstr");
			        	var jsonObj = eval("(" + urldecode(jsonstr) + ")");
			        	
			        	oConfirm("确定要添加该人才吗？", function() {
				        	
				        	var _formuuid = jsonObj.formUuid;
							//简历状态
							var hire_resume_jlzt = jsonObj.hireResumeJlzt;
							//性别
							var hire_resume_xbxsz = jsonObj.hireResumeXbXsz;
							//姓名
							var hire_resume_xm = jsonObj.hireResumeXm;
							//学校
							var uf_hire_resume_jybj_xx = jsonObj.ufHireResumeJybjXx;
							//专业
							var uf_hire_resume_jybj_zy = jsonObj.ufHireResumeJybjZy;
							//申请人编号
							var hire_resume_sqrbh = jsonObj.hireresumesqrbh;
							
							var uuid = jsonObj.uUID;
							var changeData = {
											  "uuid":uuid,
											  "id":uuid,
											  "hire_resume_jlzt":hire_resume_jlzt,
											  "hire_resume_xbxsz":hire_resume_xbxsz,
											  "hire_resume_xm":hire_resume_xm,
											  "uf_hire_resume_jybj_xx":uf_hire_resume_jybj_xx,
											  "uf_hire_resume_jybj_zy":uf_hire_resume_jybj_zy,
											  "hire_resume_sqrbh":hire_resume_sqrbh
											 };
							
							_this.addRowDataByFormUuid(_formuuid,changeData);
							
							oAlert("添加成功！",function(){
    							//关闭对话框
	    						closeDialog();
	    						
    						});
			        	});
			        	
			        	
			        });
				}
				
			});
		});
		
		//_this.options.afterDialogSelect.call(this, this.getCtlName(), paramsObj, fieldsKeyValue);
		
		
		
		
		/*control.bind("afterDialogSelect",function(element,ctlName, data,fieldsKeyValue){
			var _element = element;
			var _ctlName = ctlName;
			var _data = data;
			var _fieldsKeyValue = fieldsKeyValue;
			
			var _formuuid = _data.formuuid;
			//简历状态
			var hire_resume_jlzt = _data.hireresumejlzt;
			//性别
			var hire_resume_xbxsz = _data.hireresumexbxsz;
			//姓名
			var hire_resume_xm = _data.hireresumexm;
			//学校
			var uf_hire_resume_jybj_xx = _data.ufhireresumejybjxx;
			//专业
			var uf_hire_resume_jybj_zy = _data.ufhireresumejybjzy;
			//申请人编号
			var hire_resume_sqrbh = _data.hireresumesqrbh;
			
			var uuid = _data.uuid;
			var changeData = {
							  "uuid":uuid,
							  "id":uuid,
							  "hire_resume_jlzt":hire_resume_jlzt,
							  "hire_resume_xbxsz":hire_resume_xbxsz,
							  "hire_resume_xm":hire_resume_xm,
							  "uf_hire_resume_jybj_xx":uf_hire_resume_jybj_xx,
							  "uf_hire_resume_jybj_zy":uf_hire_resume_jybj_zy,
							  "hire_resume_sqrbh":hire_resume_sqrbh
							 };
			
			_this.addRowDataByFormUuid(_formuuid,changeData);
			
		});*/
		//---------------------------
		
		
		//表单onload时，设置默认值
		var userInfo = SpringSecurityUtils.getUserDetails();
		var val = _this.getFieldValueByFieldName("WERKS");
		if(typeof val != 'undefined' && $.trim(val).length > 0){
			//return;
		}else{
			this.setFieldValueByFieldName("ygbh",userInfo.employeeNumber);
			this.setFieldValueByFieldName("ygxm",userInfo.userName);
		}
			
		var loadLeve = function(jsonobj){					
			if(jsonobj==null){
				alert("没有找到对应的数据");
				return;
			}
			
			var pt = "PT_BAPIHRS0001";
			if(jsonobj[pt].rows==="0"){
				//alert("没有找到对应的数据");
				return;
			}
			var data = jsonobj[pt].row[0];
			if(typeof val != 'undefined' && $.trim(val).length > 0){
				 
				return;
			}
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("WERKS",data.WERKS);
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("BTRTL",data.BTRTL);
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("PERSK",data.PERSK);
			//员工姓名
			_this.setFieldValueByFieldName("ygxm",data.ENAME);
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("SHORT3",data.SHORT3);
		};
		getHR_ZHRI001004(_this.getFieldValueByFieldName("ygbh"),loadLeve);
						
		//员工编号文本框
		this.$("input[name='ygbh']").click(function(){
			$.unit.open({
				title : "选择人员",
				labelField : "ygxm",
				valueField : "ygbh",
				type : "MyDept",
				multiple:false, 
				afterSelect : function(retVal) {
					_this.setFieldValueByFieldName("ygbh",retVal.employeeNumber);
					_this.setFieldValueByFieldName("ygxm",retVal.name);							
					getHR_ZHRI001004(_this.getFieldValueByFieldName("ygbh"),loadLeve);
				},
				selectType : 4	
			});
		});
		
		//员工编号文本框
		this.$("input[name='ygbh']").focus(function(){
			this.blur();
		});
		
		//----------------定义动态表格事件-----------------------
		var formId = "uf_ldx_rsgl_ryzbsqxx";
		var formUuid = _this.getFormUuid(formId); 
		var ctl = _this.getSubformControl(formUuid);
						
		ctl.bind("afterInsertRow", function(rowdata){					
			
			//var type = _this.getControl("zbrylb").getValue();取真实值
			//var type = _this.getControl("zbrylb").getDisplayValue();
								
			_this.getControl("zwbm", rowdata.id ).bind("blur", function(){						
				if(this.value != ""){	
					var type = _this.getControl("zbrylb").getDisplayValue();
					if(type=="非工人"){
						getHR_ZHRI001703(_this.getFieldValueByFieldName("zwbm"),function(jsonobj){
							
						});
						getHR_ZHRI001701(_this.getFieldValueByFieldName("zwbm"),function(jsonobj){
							var pt = "PO_ZHRS0025";
							var data = jsonobj[pt];	
							_this.setFieldValueByFieldName("ZZZWMC",data.ZZZWMC);									
						});
					}else{
						getHR_ZHRI001703(this.value,function(jsonobj){
							
						});
						getHR_ZHRI001701(this.value,function(jsonobj){	
							var pt = "PO_ZHRS0025";
							var data = jsonobj[pt];										
							var rowData = ctl.getRowData(ctl.selectedRowId);						
							rowData.zwmc = data.ZZZWMC;		
							_this.updateRowData(formId, rowData);
						});
					}
				}
			});
		});
	},
	
	//表单中说明性文档链接
	uf_leedarson_xzgl_dhywsqb:function(){
		this.$("#feeStandardBtn").click(function(){
			//window.location.href = contextPath + "/resources/pt/help/doc/feeStandard.doc";
			//费用参考标准.doc
			var path =  "/resources/pt/help/ldx/费用参考标准.doc" ; 
			path = urlencode(urlencode(path));
			window.location =  contextPath + "/repository/file/mongo/file?path=" + path;
		});
	},
	// 
	uf_ldx_clkfgl_bzclkfsqd:function(){
		var _this = this;
		/*_this.$(".ui-jqgrid-title").append("<button id='btn_queryChioce' >选择</button>");
		_this.$("#btn_queryChioce").click(function(){
			alert(241);
		});
		JDS.call({
			async:false,
			service:"sapConnect.getDictWlzms",
			data:[],
			success:function(result) {
				alert(result);
			},
			error:function(result) {
				alert("保存失败");
			}
		});*/

		var control =_this.getControl("wlzms");
		_this.$("input[name='"+control.getCtlName()+"']").change(function(){
			
		});
	},
	/**
	 * Leedarson_固定资产_计量器具领用表
	 */
	uf_leedarson_gdzc_jlqjlyb: function(){
		var _this = this;

		this.$(".editableClass[name$=___glbh]").live("change", function(e){
			
			var val = $(this).val();
			
			var formId = "uf_leedarson_gdzc_jlqjlyxx";
			var formUuid = _this.getFormUuid(formId); 
			var ctl = _this.getSubformControl(formUuid);
			$.ajax({
				async : false,
				url : ctx + "/lms/search/labfile",
				type:"POST",
				dataType:"json",
				data:{
					"labfileNo":val
				},
				success : function(res) {
					var arr = eval(res);
					if(arr.length==1){
						var labMainhave = '' ;
						if('1'==arr[0].labfileMainhave){
							labMainhave='{"1":"厦门光电"}';
						}else if('2'==arr[0].labfileMainhave){
							labMainhave='{"2":"立明光电"}';
						}else if('3'==arr[0].labfileMainhave){
							labMainhave='{"3":"绿色照明"}';
						}else if('4'==arr[0].labfileMainhave){
							labMainhave='{"4":"电光源"}';
						}else if('5'==arr[0].labfileMainhave){
							labMainhave='{"5":"海德信"}';
						}else if('6'==arr[0].labfileMainhave){
							labMainhave='{"6":"四川鼎吉"}';
						}else if('7'==arr[0].labfileMainhave){
							labMainhave='{"7":"四川联恺"}';
						}else if('8'==arr[0].labfileMainhave){
							labMainhave='{"8":"绿天光电"}';
						}else if('9'==arr[0].labfileMainhave){
							labMainhave='{"9":"其它"}';
						}
						var json = {"mc":arr[0].labfileName,"xhgg":arr[0].labfileSpec,"glbh":arr[0].labfileNo, 
								"sccj":arr[0].supName,"ccbh":arr[0].labfileDefno ,"gdzch":arr[0].labfileAssetsno,
								"ztgs":labMainhave,"cbzx":arr[0].costName,"id":ctl.selectedRowId, "uuid":ctl.selectedRowId  };
						
						_this.updateRowData(formId, json);
					}
				}
			});
		});
	},
	


	/**
	 * Leedarson_固定资产_资产转卖申请表(不同主体) 
	 */
	uf_leedarson_gdzc_zczmsqb:function(){
		var _this = this;
		this.getControl("csfcbzxmc").bind("afterDialogSelect", function(){
			_this.getControl("csfcbzxfzr").$editableElem.css("ime-mode", "disabled");//禁用输入法
			_this.getControl("csfcbzxfzr").$editableElem.keydown(function(){ 
				return false;			
			});
			_this.getControl("csfcbzxdm").$editableElem.css("ime-mode", "disabled");//禁用输入法
			_this.getControl("csfcbzxdm").$editableElem.keydown(function(){ 
				return false;				
			});			
			_this.getControl("csfcbzxfzr").setDisplayAsCtl();
			_this.getControl("csfcbzxfzr").setReadOnly(false);
			_this.getControl("csfcbzxdm").setReadOnly(false);
			_this.getControl("csfcbzxfzr").bind("afterAutoCompleteShow", function(){
				if(_this.getControl("csfcbzxfzr").$editableElem.next(".autocomplete").find("tr").size() == 1){
					_this.getControl("csfcbzxfzr").$editableElem.next(".autocomplete").find("tr").first().addClass("highlight");
					_this.getControl("csfcbzxfzr").$editableElem.next(".autocomplete").find("tr").first().trigger("click");
				}					
			});
			_this.getControl("csfcbzxfzr").$editableElem.trigger("click");		
			_this.getControl("csfcbzxfzr").setReadOnly(false);
								
		});
		this.getControl("jsfcbzxmc").bind("afterDialogSelect", function(){
			_this.getControl("jsfcbzxfzr").$editableElem.css("ime-mode", "disabled");//禁用输入法
			_this.getControl("jsfcbzxfzr").$editableElem.keydown(function(){ 
				return false;			
			});
			_this.getControl("jsfcbzxdm").$editableElem.css("ime-mode", "disabled");//禁用输入法
			_this.getControl("jsfcbzxdm").$editableElem.keydown(function(){ 
				return false;				
			});			
			_this.getControl("jsfcbzxfzr").setDisplayAsCtl();
			_this.getControl("jsfcbzxfzr").setReadOnly(false);
			_this.getControl("jsfcbzxdm").setReadOnly(false);
			_this.getControl("jsfcbzxfzr").bind("afterAutoCompleteShow", function(){
				if(_this.getControl("jsfcbzxfzr").$editableElem.next(".autocomplete").find("tr").size() == 1){
					_this.getControl("jsfcbzxfzr").$editableElem.next(".autocomplete").find("tr").first().addClass("highlight");
					_this.getControl("jsfcbzxfzr").$editableElem.next(".autocomplete").find("tr").first().trigger("click");
				}					
			});
			_this.getControl("jsfcbzxfzr").$editableElem.trigger("click");		
			_this.getControl("jsfcbzxfzr").setReadOnly(false);
								
		});
		
		this.$(".editableClass[name$=___glbh]").live("change", function(e){
			
			var val = $(this).val();
			
			var formId = "uf_leedarson_gdzc_zczmxx";
			var formUuid = _this.getFormUuid(formId); 
			var ctl = _this.getSubformControl(formUuid);
			$.ajax({
				async : false,
				url : ctx + "/lms/search/labfile",
				type:"POST",
				dataType:"json",
				data:{
					"labfileNo":val
				},
				success : function(res) {
					var arr = eval(res);
					if(arr.length==1){
						var yzcqyrq = arr[0].labfileUsrdate;
						if(yzcqyrq!=''){
							yzcqyrq = yzcqyrq.substr(0,10);
						}
						var json = {"mc":arr[0].labfileName,"ggxh":arr[0].labfileSpec,"sccj":arr[0].supName,"glbh":arr[0].labfileNo, 
								"ccbh":arr[0].labfileDefno ,"csfsapzcbm":arr[0].labfileAssetsno,"yzcqyrq":yzcqyrq,
								"id":ctl.selectedRowId, "uuid":ctl.selectedRowId  };
					 
						_this.updateRowData(formId, json);
					}
				}
			});
		});

		// 固定资产类别
		this.getControl("gdzclb").bind("change",function(){
			var fields = ['dcfgkzy','dcfgkzyid','dcfgkbmjl','dcfgkbmjlid','dcfgkbmld','dcfgkbmldid'];
			//给表单的隐藏域设置流程的姓名和用户id
			setFlowNameAndUserId("gdzclb","csfzcsyd",fields,_this);
			
			var fields2 = ['drfgkzy','drfgkzyid','drfgkbmjl','drfgkbmjlid','drfgkbmld','drfgkbmldid'];
			//给表单的隐藏域设置流程的姓名和用户id
			setFlowNameAndUserId("gdzclb","jsfzcsyd",fields2,_this);
		});
		
		// 出售方资产使用地
		this.getControl("csfzcsyd").bind("change",function(){
			var fields = ['dcfgkzy','dcfgkzyid','dcfgkbmjl','dcfgkbmjlid','dcfgkbmld','dcfgkbmldid'];
			//给表单的隐藏域设置流程的姓名和用户id
			setFlowNameAndUserId("gdzclb","csfzcsyd",fields,_this);
			
		});
		
		// 接收方资产使用地
		this.getControl("jsfzcsyd").bind("change",function(){
			
			var fields = ['drfgkzy','drfgkzyid','drfgkbmjl','drfgkbmjlid','drfgkbmld','drfgkbmldid'];
			//给表单的隐藏域设置流程的姓名和用户id
			setFlowNameAndUserId("gdzclb","jsfzcsyd",fields,_this);
		});
		
//		// 主体归属 
//		this.getControl("csfztgs").bind("afterDialogSelect",function(e){
//			
//			var fields = ['dcfgdzczy','dcfgdzczyid'];
//			setNumberNameAndUserId('csfgsdm',fields,_this);
//		});
        // 主体归属 
		this.getControl("csfztgs").bind("afterDialogSelect",function(e){
			_this.getControl("csfcbzxmc").setValue('');	
			_this.getControl("csfcbzxdm").setValue('');	
			_this.getControl("csfcbzxfzr").setValue('');	
			_this.getControl("csfcbzxfzrid").setValue('');
			var fields = ['dcfgdzczy','dcfgdzczyid'];
			setNumberNameAndUserId('csfgsdm',fields,_this);
		});		
		// 主体归属 
		this.getControl("csfztgs").bind("change",function(e){
			_this.getControl("csfcbzxmc").setValue('');	
			_this.getControl("csfcbzxdm").setValue('');	
			_this.getControl("csfcbzxfzr").setValue('');	
			_this.getControl("csfcbzxfzrid").setValue('');
			var fields = ['dcfgdzczy','dcfgdzczyid'];
			setNumberNameAndUserId('csfgsdm',fields,_this);
		});
		
//		// 主体归属 
//		this.getControl("jsfztgs").bind("afterDialogSelect",function(e){
//			
//			var fields = ['drfgdzczy','drfgdzczyid'];
//			setNumberNameAndUserId('jsfgsdm',fields,_this);
//		});
        // 主体归属 
		this.getControl("jsfztgs").bind("afterDialogSelect",function(e){			
			_this.getControl("jsfcbzxmc").setValue('');				
			_this.getControl("jsfcbzxdm").setValue('');				
			_this.getControl("jsfcbzxfzr").setValue('');			
			_this.getControl("jsfcbzxfzrid").setValue('');
			var fields = ['drfgdzczy','drfgdzczyid'];
			setNumberNameAndUserId('jsfgsdm',fields,_this);
		});		
		// 主体归属 
		this.getControl("jsfztgs").bind("change",function(e){
			_this.getControl("jsfcbzxmc").setValue('');	
			_this.getControl("jsfcbzxdm").setValue('');	
			_this.getControl("jsfcbzxfzr").setValue('');
			_this.getControl("jsfcbzxfzrid").setValue('');
			var fields = ['drfgdzczy','drfgdzczyid'];
			setNumberNameAndUserId('jsfgsdm',fields,_this);
		});
		
		this.getControl("jsfztgs").bind("beforeAutoCompleteShow",function(data){
			if(typeof data == "undefined" || data.length == 0){
				return;
			}
		 
			 var csfztgs = _this.getControl("csfztgs").getValue();
			 var index = -1;
			 for(var i = 0; i < data.length; i ++){
				 var zt = data[i]["BUTXT"]
				 if(zt == csfztgs){
					 index = i;
					 break;
				 }
			 }
			 if(index != -1){
				 data.splice(index, 1);
			 }
		});
		this.getControl("csfztgs").bind("beforeAutoCompleteShow",function(data){
			if(typeof data == "undefined" || data.length == 0){
				return;
			}
		 
			 var csfztgs = _this.getControl("jsfztgs").getValue();
			 var index = -1;
			 for(var i = 0; i < data.length; i ++){
				 var zt = data[i]["BUTXT"]
				 if(zt == csfztgs){
					 index = i;
					 break;
				 }
			 }
			 if(index != -1){
				 data.splice(index, 1);
			 }
		});
		
		 
	},
	
	/**
	 * Leedarson_固定资产_低值易耗品报废申请表
	 */ 
	uf_leedarson_dzyhpbfsqb:function(){
		var _this = this;
		this.getControl("cbzx").bind("afterDialogSelect", function(){
			_this.getControl("cbzxfzr").$editableElem.css("ime-mode", "disabled");//禁用输入法
			_this.getControl("cbzxfzr").$editableElem.keydown(function(){ 
				return false;			
			});
			_this.getControl("cbzxdm").$editableElem.css("ime-mode", "disabled");//禁用输入法
			_this.getControl("cbzxdm").$editableElem.keydown(function(){ 
				return false;				
			});			
			_this.getControl("cbzxfzr").setDisplayAsCtl();
			_this.getControl("cbzxfzr").setReadOnly(false);
			_this.getControl("cbzxdm").setReadOnly(false);
			_this.getControl("cbzxfzr").bind("afterAutoCompleteShow", function(){
				if(_this.getControl("cbzxfzr").$editableElem.next(".autocomplete").find("tr").size() == 1){
					_this.getControl("cbzxfzr").$editableElem.next(".autocomplete").find("tr").first().addClass("highlight");
					_this.getControl("cbzxfzr").$editableElem.next(".autocomplete").find("tr").first().trigger("click");
				}					
			});
			_this.getControl("cbzxfzr").$editableElem.trigger("click");		
			_this.getControl("cbzxfzr").setReadOnly(false);
								
		});
		
		
		this.$(".editableClass[name$=___glbh]").live("change", function(e){
			
			var val = $(this).val();
			
			var formId = "uf_leedarson_dzyhpbfsqbfxx";
			var formUuid = _this.getFormUuid(formId); 
			var ctl = _this.getSubformControl(formUuid);
			$.ajax({
				async : false,
				url : ctx + "/lms/search/labfile",
				type:"POST",
				dataType:"json",
				data:{
					"labfileNo":val
				},
				success : function(res) {
					var arr = eval(res);
					if(arr.length==1){
						var qyrq = arr[0].labfileUsrdate;
						if(qyrq!=''){
							qyrq = qyrq.substr(0,10);
						}
						var json = {"mc":arr[0].labfileName,"glbh":arr[0].labfileNo,"sccj":arr[0].supName, "xhgg":arr[0].labfileSpec,
								"ccbh":arr[0].labfileDefno ,"syr":arr[0].labfileClaim,"qyrq":qyrq,"id":ctl.selectedRowId, "uuid":ctl.selectedRowId };
						_this.updateRowData(formId, json);
					}
				}
			});
		});
		
		this.getControl("gszt").bind("afterDialogSelect",function(e){
			_this.getControl("cbzx").setValue('');	
			_this.getControl("cbzxfzr").setValue('');	
			_this.getControl("cbzxdm").setValue('');	
			_this.getControl("gsdm").setValue('');	
			_this.getControl("cbzxfzrid").setValue('');	
		});
		
		// 主体归属 
		this.getControl("gszt").bind("change",function(e){
			_this.getControl("cbzx").setValue('');	
			_this.getControl("cbzxfzr").setValue('');	
			_this.getControl("cbzxdm").setValue('');	
			_this.getControl("gsdm").setValue('');	
			_this.getControl("cbzxfzrid").setValue('');	
		});

	},
	/**
	 * Leedarson_固定资产_固定资产报废申请表 
	 */
	uf_leedarson_gdzc_gdzcbfsqb:function(){
		var _this = this;
		
		//注册公式--从表列项求和并给主表字段设值
		$.ControlManager.registFormula(
				{
				       triggerElements:["uf_leedarson_gdzc_gdzcbfxx:yz"],//字段名数组,这些元素变化会触发公式
				       formula:function(){//this指向当前控件  
				    	   _this.getControl("yzze").setValue(sumOfColumn("uf_leedarson_gdzc_gdzcbfxx:yz", _this));
				       }
				}
		);

		this.getControl("cbzxmc").bind("afterDialogSelect", function(){
			_this.getControl("cbzxfzr").$editableElem.css("ime-mode", "disabled");//禁用输入法
			_this.getControl("cbzxfzr").$editableElem.keydown(function(){ 
				return false;			
			});
			 
			//_this.getControl("cbzxdm").$editableElem[0].onfocus = function(){document.body.style.imeMode = 'disabled'};
			_this.getControl("cbzxdm").$editableElem.css("ime-mode", "disabled");//禁用输入法
			_this.getControl("cbzxdm").$editableElem.keydown(function(){
				 
				return false;				
			});			
		 		
			_this.getControl("cbzxfzr").setDisplayAsCtl();
			_this.getControl("cbzxfzr").setReadOnly(false);
			_this.getControl("cbzxdm").setReadOnly(false);
			_this.getControl("cbzxfzr").bind("afterAutoCompleteShow", function(){
				if(_this.getControl("cbzxfzr").$editableElem.next(".autocomplete").find("tr").size() == 1){
					_this.getControl("cbzxfzr").$editableElem.next(".autocomplete").find("tr").first().addClass("highlight");
					_this.getControl("cbzxfzr").$editableElem.next(".autocomplete").find("tr").first().trigger("click");
				}
			});
			_this.getControl("cbzxfzr").$editableElem.trigger("click");		
			_this.getControl("cbzxfzr").setReadOnly(false);
								
		});
		// 主体归属 
		this.getControl("zcssztdw").bind("afterDialogSelect",function(e){
			_this.getControl("cbzxmc").setValue('');	
			_this.getControl("cbzxdm").setValue('');
			_this.getControl("cbzxfzr").setValue('');	
			_this.getControl("cbzxfzrid").setValue('');	
            _this.getControl("gsdm").setValue('');
			var fields = ['gdzczy','gdzczyid'];
			setNumberNameAndUserId('gsdm',fields,_this);
		});
		
		// 主体归属 
		this.getControl("zcssztdw").bind("change",function(e){
			_this.getControl("cbzxmc").setValue('');	
			_this.getControl("cbzxdm").setValue('');	
			_this.getControl("cbzxfzr").setValue('');
			_this.getControl("cbzxfzrid").setValue('');	
            _this.getControl("gsdm").setValue('');
			var fields = ['gdzczy','gdzczyid'];
			setNumberNameAndUserId('gsdm',fields,_this);
		});

		this.$(".editableClass[name$=___glbh]").live("change", function(e){
			
			var val = $(this).val();
			
			var formId = "uf_leedarson_gdzc_gdzcbfxx";
			var formUuid = _this.getFormUuid(formId); 
			var ctl = _this.getSubformControl(formUuid);
			$.ajax({
				async : false,
				url : ctx + "/lms/search/labfile",
				type:"POST",
				dataType:"json",
				data:{
					"labfileNo":val
				},
				success : function(res) {
					var arr = eval(res);
					if(arr.length==1){
						var qyrq = arr[0].labfileUsrdate;
						if(qyrq!=''){
							qyrq = qyrq.substr(0,10);
						}
						var json = {"mc":arr[0].labfileName,"ggxh":arr[0].labfileSpec,"sccj":arr[0].supName, "sapzcbh":arr[0].labfileAssetsno,
								"glbh":arr[0].labfileNo ,"qyrq":qyrq,"id":ctl.selectedRowId, "uuid":ctl.selectedRowId };
						_this.updateRowData(formId, json);
					}
				}
			});
		});

		// 固定资产类别
		this.getControl("zclb").bind("change",function(){
			var fields = ['gkzy','gkzyid','gkbmjl','gkbmjlid','gkbmfgld','gkbmfgldid'];
			//给表单的隐藏域设置流程的姓名和用户id
			setFlowNameAndUserId("zclb","dqgs",fields,_this);
			
		});
		
		//地区(公司)
		this.getControl("dqgs").bind("change",function(){
			var fields = ['gkzy','gkzyid','gkbmjl','gkbmjlid','gkbmfgld','gkbmfgldid'];
			//给表单的隐藏域设置流程的姓名和用户id
			setFlowNameAndUserId('zclb','dqgs',fields,_this);
		});
		
		/*固资专员判断*/
//		this.getControl("zcssztdw").bind("afterDialogSelect",function(e){
//			
//			var fields = ['gdzczy','gdzczyid'];
//			setNumberNameAndUserId('gsdm',fields,_this);
//		});
	},
	
	/**
	 * Leedarson_固定资产_计量器具送检单
	 */
	uf_leedarson_gdzc_jlqjsjd:function(){
		var _this = this;

		this.$(".editableClass[name$=___qgdh]").live("change", function(e){
			
			var val = $(this).val();
			
			var formId = "uf_leedarson_gdzc_jlqjsdtbg";
			var formUuid = _this.getFormUuid(formId); 
			var ctl = _this.getSubformControl(formUuid);
			// 查询通过流程的零星请购申请信息表单
			$.ajax({
				async : false,
				url : ctx + "/special/dyform/queryall",
				type:"POST",
				dataType:"json",
				data:{
					"formId":"uf_leedarson_ledqgdlxcg","columns":"uuid,lsh_gzsqdh,sqr,sszt,cbzxmc,cbzxdm,cbzxfzr,lxr","condition":"flow_status = '1' and lsh_gzsqdh = '" + $(this).val() + "'",
					"childFromId":"uf_leedarson_ledqglxcgxx","childColumns":"uuid,pm,ggpp"
				},
				success : function(res) {
					var jsonObj = eval(res);
					if(jsonObj.length==1){
						ctl.delRowData(ctl.selectedRowId);
						var arr = jsonObj[0].childAllList;
						if(arr!=null){
							for(var i=0;i<arr.length;i++){
								var json = {"qgdh":jsonObj[0].lshGzsqdh,"sqr":jsonObj[0].sQR,"ztgs":jsonObj[0].sSZT,
										"cbzxmc":jsonObj[0].cBZXMC, "cbzxdm":jsonObj[0].cBZXDM,"cbzxfzr":jsonObj[0].cBZXFZR,
										"jkry":jsonObj[0].lXR,"mc":arr[i].pM,"xhgg":arr[i].gGPP,"id":arr[i].uUID, "uuid":arr[i].uUID };
								_this.addRowDataByFormUuid(formUuid,json);
							}
						}
					}
				}
			});
			// 查询通过流程的固定资产购置表单
			$.ajax({
				async : false,
				url : ctx + "/special/dyform/queryall",
				type:"POST",
				dataType:"json",
				data:{
					"formId":"uf_leedarson_xzgl_gdzcgzsqb","columns":"uuid,lsh_gzsqdh,sqr,ztgs,cbzxmc,cbzxdm,cbzxfzr,ysry",
					"condition":"flow_status = '1' and lsh_gzsqdh = '" + $(this).val() + "'",
					"childFromId":"uf_leedarson_xzgl_gdzcxbjjl","childColumns":"uuid,sapgdzcbh,gzmc,ggxh,zzcs"
				},
				success : function(res) {
					var jsonObj = eval(res);
					if(jsonObj.length==1){
						var arr = jsonObj[0].childAllList;
						if(arr!=null){
							for(var i=0;i<arr.length;i++){
								var json = {"qgdh":jsonObj[0].lshGzsqdh,"sqr":jsonObj[0].sQR,"ztgs":jsonObj[0].zTGS,
										"cbzxmc":jsonObj[0].cBZXMC, "cbzxdm":jsonObj[0].cBZXDM,"cbzxfzr":jsonObj[0].cBZXFZR,
										"jkry":jsonObj[0].ySRY,"gdzch":arr[i].sAPGDZCBH,"mc":arr[i].gZMC,"xhgg":arr[i].gGXH,
										"sccj":arr[i].zZCS,"id":arr[i].uUID, "uuid":arr[i].uUID };
								_this.addRowDataByFormUuid(formUuid,json);
							}
							ctl.delRowData(ctl.selectedRowId);
						}
					}
				}
			});
		}); 
	},

	/**
	 * LDX_材料开发管理_材料测试申请单
	 */
	uf_ldx_clkfgl_clcssqd:function(){
		var _this = this;
		this.getControl("jcbh").bind("change",function(){
			var jcbh = $(this).val();
			
			var formId = "uf_leedarson_gdzc_jlqjsdtbg";
			var formUuid = _this.getFormUuid(formId); 
			var ctl = _this.getSubformControl(formUuid);
			
			$.ajax({
				async : false,
				url : ctx + "/special/dyform/queryall",
				type:"POST",
				dataType:"json",
				data:{
					"formId":"uf_ldx_clkfgl_bzclkfsqd","columns":"","condition":"lsh_bzclkfsq = '" + jcbh + "'",
					"childFromId":"uf_leedarson_ledqglxcgxx","childColumns":"uuid,pm,ggpp"
				},
				success : function(res) {
					var jsonObj = eval(res);
					if(jsonObj.length==1){
						ctl.delRowData(ctl.selectedRowId);
						var arr = jsonObj[0].childAllList;
						if(arr!=null){
							for(var i=0;i<arr.length;i++){
								var json = {"qgdh":jsonObj[0].lshGzsqdh,"sqr":jsonObj[0].sQR,"ztgs":jsonObj[0].sSZT,
										"cbzxmc":jsonObj[0].cBZXMC, "cbzxdm":jsonObj[0].cBZXDM,"cbzxfzr":jsonObj[0].cBZXFZR,
										"jkry":jsonObj[0].lXR,"mc":arr[i].pM,"xhgg":arr[i].gGPP,"id":arr[i].uUID, "uuid":arr[i].uUID };
								_this.addRowDataByFormUuid(formUuid,json);
							}
						}
					}
				}
			});
		});
		
	},
	
	// 测试异常通知单
	uf_ldx_pbgl_csyctzd:function(){
		var _this = this;
		var wtdhIptC = _this.getControl("wtdh");
		var zbIptC = _this.getControl("zb");
		this.$("input[name='" + wtdhIptC.getCtlName() +"'],input[name='"+zbIptC.getCtlName()+"']").change(function(){
			var wtdh = _this.$("input[name='" + wtdhIptC.getCtlName() +"']").val();
			var zb = _this.$("input[name='" + zbIptC.getCtlName() +"']").val();
			if(wtdh!=''&&zb!=''){
				$.ajax({
					async : false,
					url : ctx + "/lms/search/error",
					type:"post",
					dataType:"json",
					data:{
						"tstorder_no":wtdh,"tstorder2_clsfrom":zb
					},
					success : function(res) {
						var arr = eval(res);
						if(arr.length==1){
							_this.setFieldValueByFieldName("xsddh",arr[0][0]);
							_this.setFieldValueByFieldName("cpid",arr[0][1]);
							_this.setFieldValueByFieldName("ggxh",arr[0][2]);
							_this.setFieldValueByFieldName("csmd",arr[0][3]);
							_this.setFieldValueByFieldName("syr",arr[0][4]);
							if(arr[0][5] != null){
								var wtrq = new Date(arr[0][5].time);
								var wtrqStr = wtrq.getFullYear() + "-" + (wtrq.getMonth()+1) + "-" + wtrq.getDate();
								_this.setFieldValueByFieldName("wtrq",wtrqStr);
							}
						}
					}
				});
			}
			
			//测试异常通知单  B1100058   组别  A   
			if(wtdh!= null){
				$.ajax({
					async : false,
					url : ctx + "/lms/search/queryTsttypeName",
					type:"post",
					dataType:"json",
					data:{
						"tstorder_no":wtdh
					},
					success : function(res) {
						var arr = eval(res);
						var cs = _this.getControl("csxm").getCtlElement();
						if(arr.length >= 1){
							for(var i=0;i<arr.length;i++){
								cs.html("<option value='" + arr[i].tsttype_name + "'>" + arr[i].tsttype_name + "</option>");
							}
						}
					}
				});
			}
		});
	},
	
	uf_ldx_pbgl_xzjhtzd:function(){
		var _this = this;
		var formId = "uf_ldx_pbgl_xzjhtzddtbg";
		var formUuid = _this.getFormUuid(formId); 
		var ctl = _this.getSubformControl(formUuid);
			$.ajax({
				async : false,
				url : ctx + "/lms/search/jzjh",
				type:"POST",
				dataType:"json",
				data:{
					"labfile_havedate":"2014/09/21"
				},
				success : function(res) {
					var arr = eval(res);
					for(var i=0;i<arr.length;i++){
						var json = {"mc":arr[i].labfileName,"glbh":arr[i].labfileNo,"xhgg":arr[i].labfileSpec,
								"ccbh":arr[i].labfileDefno, "xzrq":arr[i].labfileCheckdate,"yxqz":arr[i].labfileHavedate,
								"xzfs":arr[i].labfileCheck,"bz":arr[i].labfileRem,"cbzxdm":arr[i].labfileCostno,
								"cbzxmc":arr[i].costName,"cbzxfzr":arr[i].labfileUsr,"jkry":arr[i].labfileInterusr,
								"syr":arr[i].labfileClaim,"azdd":arr[i].labfileAddress,"id":arr[i].labfileNo, "uuid":arr[i].labfileNo };
						_this.addRowDataByFormUuid(formUuid,json);
					}
				}
			});
		/*this.$(".editableClass[name$=___glbh]").live("change", function(e){
			
			var val = $(this).val();
			
			var formId = "uf_ldx_pbgl_xzjhtzddtbg";
			var formUuid = _this.getFormUuid(formId); 
			var ctl = _this.getSubformControl(formUuid);
			
			$.ajax({
				async : false,
				url : ctx + "/lms/search/labfile",
				type:"POST",
				dataType:"json",
				data:{
					"labfileNo":val
				},
				success : function(res) {
					var arr = eval(res);
					if(arr.length==1){
						var json = {"mc":arr[0].labfileName,"glbh":arr[0].labfileNo,"ccbh":arr[0].labfileProveno, "id":ctl.selectedRowId, "uuid":ctl.selectedRowId };
					 
						_this.updateRowData(formId, json);
					}
				}
			});
		});*/
	},
	
	/**
	 * Leedarson_固定资产_固定资产转移单
	 */ 
	uf_leedarson_gdzc_gdzczyd:function(){
		var _this = this;
		this.getControl("dcfcbzx").bind("afterDialogSelect", function(){
			_this.getControl("dcfcbzxfzr").$editableElem.css("ime-mode", "disabled");//禁用输入法
			_this.getControl("dcfcbzxfzr").$editableElem.keydown(function(){ 
				return false;			
			});
			_this.getControl("dcfcbzxdm").$editableElem.css("ime-mode", "disabled");//禁用输入法
			_this.getControl("dcfcbzxdm").$editableElem.keydown(function(){ 
				return false;				
			});			
			_this.getControl("dcfcbzxfzr").setDisplayAsCtl();
			_this.getControl("dcfcbzxfzr").setReadOnly(false);
			_this.getControl("dcfcbzxdm").setReadOnly(false);
			_this.getControl("dcfcbzxfzr").bind("afterAutoCompleteShow", function(){
				if(_this.getControl("dcfcbzxfzr").$editableElem.next(".autocomplete").find("tr").size() == 1){
					_this.getControl("dcfcbzxfzr").$editableElem.next(".autocomplete").find("tr").first().addClass("highlight");
					_this.getControl("dcfcbzxfzr").$editableElem.next(".autocomplete").find("tr").first().trigger("click");
				}					
			});
			_this.getControl("dcfcbzxfzr").$editableElem.trigger("click");		
			_this.getControl("dcfcbzxfzr").setReadOnly(false);
								
		});
		this.getControl("drfcbzx").bind("afterDialogSelect", function(){
			_this.getControl("drfcbzxfzr").$editableElem.css("ime-mode", "disabled");//禁用输入法
			_this.getControl("drfcbzxfzr").$editableElem.keydown(function(){ 
				return false;			
			});
			_this.getControl("drfcbzxdm").$editableElem.css("ime-mode", "disabled");//禁用输入法
			_this.getControl("drfcbzxdm").$editableElem.keydown(function(){ 
				return false;				
			});			
			_this.getControl("drfcbzxfzr").setDisplayAsCtl();
			_this.getControl("drfcbzxfzr").setReadOnly(false);
			_this.getControl("drfcbzxdm").setReadOnly(false);
			_this.getControl("drfcbzxfzr").bind("afterAutoCompleteShow", function(){
				if(_this.getControl("drfcbzxfzr").$editableElem.next(".autocomplete").find("tr").size() == 1){
					_this.getControl("drfcbzxfzr").$editableElem.next(".autocomplete").find("tr").first().addClass("highlight");
					_this.getControl("drfcbzxfzr").$editableElem.next(".autocomplete").find("tr").first().trigger("click");
				}					
			});
			_this.getControl("drfcbzxfzr").$editableElem.trigger("click");		
			_this.getControl("drfcbzxfzr").setReadOnly(false);
								
		});
		

		// 固定资产类别
		this.getControl("gdzclb").bind("change",function(){
			var fields = ['dcfgkbmzczy','dcfgkbmzczyid','dcfgkbmjl','dcfgkbmjlid','dcfgkbmld','dcfgkbmldid'];
			//给表单的隐藏域设置流程的姓名和用户id
			setFlowNameAndUserId("gdzclb","dcfsydd",fields,_this);
			
			var fields2 = ['drfgkbmzczy','drfgkbmzczyid','drfgkbmjl','drfgkbmjlid','drfgkbmld','drfgkbmldid'];
			//给表单的隐藏域设置流程的姓名和用户id
			setFlowNameAndUserId("gdzclb","drfsydd",fields2,_this);
		});
		
		// 调出方使用地点(公司)
		this.getControl("dcfsydd").bind("change",function(){
			var fields = ['dcfgkbmzczy','dcfgkbmzczyid','dcfgkbmjl','dcfgkbmjlid','dcfgkbmld','dcfgkbmldid'];
			//给表单的隐藏域设置流程的姓名和用户id
			setFlowNameAndUserId("gdzclb","dcfsydd",fields,_this);
			
		});
		
		// 调入方使用地点(公司)
		this.getControl("drfsydd").bind("change",function(){
			
			var fields = ['drfgkbmzczy','drfgkbmzczyid','drfgkbmjl','drfgkbmjlid','drfgkbmld','drfgkbmldid'];
			//给表单的隐藏域设置流程的姓名和用户id
			setFlowNameAndUserId("gdzclb","drfsydd",fields,_this);
		});
		
		// 主体归属 
		this.getControl("dcfzt").bind("afterDialogSelect",function(e){
			_this.getControl("dcfcbzx").setValue('');	
			_this.getControl("dcfcbzxdm").setValue('');	
			_this.getControl("dcfcbzxfzr").setValue('');	
			_this.getControl("dcfztgsdm").setValue('');	
			_this.getControl("dcfcbzxfzrid").setValue('');	
			_this.getControl("drfcbzx").setValue('');	
			_this.getControl("drfcbzxdm").setValue('');	
			_this.getControl("drfcbzxfzr").setValue('');	
			_this.getControl("drfztgsdm").setValue('');	
			_this.getControl("drfcbzxfzrid").setValue('');		
			var fields = ['gdzczy','gdzczyid'];
			setNumberNameAndUserId('dcfztgsdm',fields,_this);
		});
		
		// 主体归属 
		this.getControl("dcfzt").bind("change",function(e){
			_this.getControl("dcfcbzx").setValue('');	
			_this.getControl("dcfcbzxdm").setValue('');	
			_this.getControl("dcfcbzxfzr").setValue('');	
			_this.getControl("dcfztgsdm").setValue('');	
			_this.getControl("dcfcbzxfzrid").setValue('');	
			_this.getControl("drfcbzx").setValue('');	
			_this.getControl("drfcbzxdm").setValue('');	
			_this.getControl("drfcbzxfzr").setValue('');	
			_this.getControl("drfztgsdm").setValue('');	
			_this.getControl("drfcbzxfzrid").setValue('');
			var fields = ['gdzczy','gdzczyid'];
			setNumberNameAndUserId('dcfztgsdm',fields,_this);
		});
		// 主体归属 
		this.getControl("drfzt").bind("afterDialogSelect",function(e){
			_this.getControl("drfcbzx").setValue('');	
			_this.getControl("drfcbzxdm").setValue('');	
			_this.getControl("drfcbzxfzr").setValue('');	
			_this.getControl("drfztgsdm").setValue('');	
			_this.getControl("drfcbzxfzrid").setValue('');	
			_this.getControl("dcfcbzx").setValue('');	
			_this.getControl("dcfcbzxdm").setValue('');	
			_this.getControl("dcfcbzxfzr").setValue('');	
			_this.getControl("dcfztgsdm").setValue('');	
			_this.getControl("dcfcbzxfzrid").setValue('');	
			var fields = ['gdzczy','gdzczyid'];
			setNumberNameAndUserId('drfztgsdm',fields,_this);
		});
		
		// 主体归属 
		this.getControl("drfzt").bind("change",function(e){
			_this.getControl("drfcbzx").setValue('');	
			_this.getControl("drfcbzxdm").setValue('');	
			_this.getControl("drfcbzxfzr").setValue('');	
			_this.getControl("drfztgsdm").setValue('');	
			_this.getControl("drfcbzxfzrid").setValue('');	
			_this.getControl("dcfcbzx").setValue('');	
			_this.getControl("dcfcbzxdm").setValue('');	
			_this.getControl("dcfcbzxfzr").setValue('');	
			_this.getControl("dcfztgsdm").setValue('');	
			_this.getControl("dcfcbzxfzrid").setValue('');
			var fields = ['gdzczy','gdzczyid'];
			setNumberNameAndUserId('drfztgsdm',fields,_this);
		});
		
		
		// 主体归属 
//		this.getControl("drfzt").bind("afterDialogSelect",function(e){
//			
//			var fields = ['gdzczy','gdzczyid'];
//			setNumberNameAndUserId('drfztgsdm',fields,_this);
//		});
		
		// 输入管理编号查询数据 
		this.$(".editableClass[name$=___glbh]").live("change", function(e){
			
			var val = $(this).val();
			
			var formId = "uf_leedarson_gdzc_gdzczmxx";
			var formUuid = _this.getFormUuid(formId); 
			var ctl = _this.getSubformControl(formUuid);
			$.ajax({
				async : false,
				url : ctx + "/lms/search/labfile",
				type:"POST",
				dataType:"json",
				data:{
					"labfileNo":val
				},
				success : function(res) {
					var arr = eval(res);
					if(arr.length==1){
						var yzcqyrq = arr[0].labfileUsrdate;
						if(yzcqyrq != ''){
							yzcqyrq = yzcqyrq.substr(0,10);
						}
						var json = {"mc":arr[0].labfileName,"ggxh":arr[0].labfileSpec,"dcfsapzcbm":arr[0].labfileAssetsno,"yzcqyrq":yzcqyrq,
								"glbh":arr[0].labfileNo ,"sccj":arr[0].supName,"ccbh":arr[0].labfileDefno,"id":ctl.selectedRowId, "uuid":ctl.selectedRowId };
						_this.updateRowData(formId, json);
					}
				}
			});
		});
	},
	
	
	/**
	 * 模具报废申请单成本中心
	 */
	uf_leedarson_gdzc_mjbfsqb:function(){
		var _this = this;
		this.getControl("cbzxmc").bind("afterDialogSelect", function(){
			_this.getControl("cbzxfzr").$editableElem.css("ime-mode", "disabled");//禁用输入法
			_this.getControl("cbzxfzr").$editableElem.keydown(function(){ 
				return false;
			});
			_this.getControl("cbzxdm").$editableElem.css("ime-mode", "disabled");//禁用输入法
			_this.getControl("cbzxdm").$editableElem.keydown(function(){ 
				return false;				
			});
			_this.getControl("cbzxfzr").setDisplayAsCtl();
			_this.getControl("cbzxfzr").setReadOnly(false);
			_this.getControl("cbzxdm").setReadOnly(false);
			_this.getControl("cbzxfzr").bind("afterAutoCompleteShow", function(){
				if(_this.getControl("cbzxfzr").$editableElem.next(".autocomplete").find("tr").size() == 1){
					_this.getControl("cbzxfzr").$editableElem.next(".autocomplete").find("tr").first().addClass("highlight");
					//alert(_this.getControl("cbzxfzr").$editableElem.next(".autocomplete").find("tr").size());
					_this.getControl("cbzxfzr").$editableElem.next(".autocomplete").find("tr").first().trigger("click");
				}
			});
			_this.getControl("cbzxfzr").$editableElem.trigger("click");		
		});
		
		this.getControl("zcssztdw").bind("afterDialogSelect",function(e){
			_this.getControl("cbzxmc").setValue('');	
			_this.getControl("cbzxfzr").setValue('');	
			_this.getControl("cbzxdm").setValue('');	
			_this.getControl("cbzxfzrid").setValue('');	
			_this.getControl("zcssztdwid").setValue('');	
		});
		
		// 主体归属 
		this.getControl("zcssztdw").bind("change",function(e){
			_this.getControl("cbzxmc").setValue('');	
			_this.getControl("cbzxfzr").setValue('');	
			_this.getControl("cbzxdm").setValue('');	
			_this.getControl("cbzxfzrid").setValue('');	
			_this.getControl("zcssztdwid").setValue('');		
		});
	},
	
	/**
	 * 固定资产工程改造申请成本中心
	 */
	uf_leedarson_gdzc_gcgzsq:function(){
		var _this = this;
		this.getControl("sycbzx").bind("afterDialogSelect", function(){
			_this.getControl("cbzxfzr").$editableElem.css("ime-mode", "disabled");//禁用输入法
			_this.getControl("cbzxfzr").$editableElem.keydown(function(){ 
				return false;
			});
			_this.getControl("cbzxdm").$editableElem.css("ime-mode", "disabled");//禁用输入法
			_this.getControl("cbzxdm").$editableElem.keydown(function(){ 
				return false;				
			});
			_this.getControl("cbzxfzr").setDisplayAsCtl();
			_this.getControl("cbzxfzr").setReadOnly(false);
			_this.getControl("cbzxdm").setReadOnly(false);
			_this.getControl("cbzxfzr").bind("afterAutoCompleteShow", function(){
				if(_this.getControl("cbzxfzr").$editableElem.next(".autocomplete").find("tr").size() == 1){
					_this.getControl("cbzxfzr").$editableElem.next(".autocomplete").find("tr").first().addClass("highlight");
					//alert(_this.getControl("cbzxfzr").$editableElem.next(".autocomplete").find("tr").size());
					_this.getControl("cbzxfzr").$editableElem.next(".autocomplete").find("tr").first().trigger("click");
				}
				
				/*window.setTimeout(function(){
					$autocomplete.find("tr").first().trigger("click");
				}, 600);*/
			});
			_this.getControl("cbzxfzr").$editableElem.trigger("click");			
			//alert(_this.getControl("cbzxfzr").$editableElem.next(".autocomplete").find("tr").size());			
		});
		
		this.getControl("ztgs").bind("afterDialogSelect",function(e){
			_this.getControl("sycbzx").setValue('');	
			_this.getControl("cbzxfzr").setValue('');	
			_this.getControl("cbzxdm").setValue('');	
			_this.getControl("gsdm").setValue('');	
			_this.getControl("cbzxfzrid").setValue('');
			var fields = ['gdzczy','gdzczyid'];
			setNumberNameAndUserId('gsdm',fields,_this);
		});
		
		// 主体归属 
		this.getControl("ztgs").bind("change",function(e){
			_this.getControl("sycbzx").setValue('');	
			_this.getControl("cbzxfzr").setValue('');	
			_this.getControl("cbzxdm").setValue('');	
			_this.getControl("gsdm").setValue('');	
			_this.getControl("cbzxfzrid").setValue('');
			/*固资专员判断*/
			var fields = ['gdzczy','gdzczyid'];
			setNumberNameAndUserId('gsdm',fields,_this);
		});
		
		// 固定资产类别
		this.getControl("zclb").bind("change",function(){
			var fields = ['gkzy','gkzyid','gkbmjl','gkbmjlid','gkbmld','gkbmldid'];
			//给表单的隐藏域设置流程的姓名和用户id
			setFlowNameAndUserId("zclb","dqgs",fields,_this);
			
		});
		
		//地区(公司)
		this.getControl("dqgs").bind("change",function(){
			var fields = ['gkzy','gkzyid','gkbmjl','gkbmjlid','gkbmld','gkbmldid'];
			//给表单的隐藏域设置流程的姓名和用户id
			setFlowNameAndUserId('zclb','dqgs',fields,_this);
		});
		
		/*固资专员判断*/
//		this.getControl("ztgs").bind("afterDialogSelect",function(e){
//			
//			var fields = ['gdzczy','gdzczyid'];
//			setNumberNameAndUserId('gsdm',fields,_this);
//		});
	},
	/**
	 * Leedarson_固定资产_固定资产租用申请 
	 */
	uf_leedarson_gdzc_gdzczysq:function(){
		var _this = this;
		this.getControl("sycbzx").bind("afterDialogSelect", function(){
			_this.getControl("cbzxfzr").$editableElem.css("ime-mode", "disabled");//禁用输入法
			_this.getControl("cbzxfzr").$editableElem.keydown(function(){ 
				return false;
			});
			_this.getControl("cbzxdm").$editableElem.css("ime-mode", "disabled");//禁用输入法
			_this.getControl("cbzxdm").$editableElem.keydown(function(){ 
				return false;				
			});
			_this.getControl("cbzxfzr").setDisplayAsCtl();
			_this.getControl("cbzxfzr").setReadOnly(false);
			_this.getControl("cbzxdm").setReadOnly(false);		
		});
		
		// 主体归属 
		this.getControl("ztgs").bind("afterDialogSelect",function(e){
			_this.getControl("sycbzx").setValue('');	
			_this.getControl("cbzxdm").setValue('');	
			_this.getControl("cbzxfzr").setValue('');
			_this.getControl("gsdm").setValue('');
			_this.getControl("cbzxfzrid").setValue('');	

		});
		
		// 主体归属 
		this.getControl("ztgs").bind("change",function(e){
			_this.getControl("sycbzx").setValue('');	
			_this.getControl("cbzxdm").setValue('');	
			_this.getControl("cbzxfzr").setValue('');
			_this.getControl("gsdm").setValue('');
			_this.getControl("cbzxfzrid").setValue('');	

		});
		
		
		// 固定资产类别
		this.getControl("gdzclb").bind("change",function(){
			var fields = ['gkzy','gkzyid','gkbmjl','gkbmjlid','gkbmfgld','gkbmfgldid'];
			//给表单的隐藏域设置流程的姓名和用户id
			setFlowNameAndUserId("gdzclb","dqgs",fields,_this);
			
		});
		
		// 地区(公司)
		this.getControl("dqgs").bind("change",function(){
			var fields = ['gkzy','gkzyid','gkbmjl','gkbmjlid','gkbmfgld','gkbmfgldid'];
			//给表单的隐藏域设置流程的姓名和用户id
			setFlowNameAndUserId('gdzclb','dqgs',fields,_this);
		});
	},
	/**
	 * Leedarson_固定资产_固定资产验收报告
	 */
	uf_leedarson_gdzc_gdzcysbg:function(){
		var _this = this;
		this.getControl("sycbzx").bind("afterDialogSelect", function(){	
			_this.getControl("cbzxfzr").$editableElem.css("ime-mode", "disabled");//禁用输入法
			_this.getControl("cbzxfzr").$editableElem.keydown(function(){ 
				return false;
			});
			_this.getControl("cbzxdm").$editableElem.css("ime-mode", "disabled");//禁用输入法
			_this.getControl("cbzxdm").$editableElem.keydown(function(){ 
				return false;				
			});
			_this.getControl("cbzxfzr").setDisplayAsCtl();
			_this.getControl("cbzxfzr").setReadOnly(false);
			_this.getControl("cbzxdm").setReadOnly(false);
			_this.getControl("cbzxfzr").bind("afterAutoCompleteShow", function(){
				if(_this.getControl("cbzxfzr").$editableElem.next(".autocomplete").find("tr").size() == 1){
					_this.getControl("cbzxfzr").$editableElem.next(".autocomplete").find("tr").first().addClass("highlight");
					_this.getControl("cbzxfzr").$editableElem.next(".autocomplete").find("tr").first().trigger("click");
				}
			});
			_this.getControl("cbzxfzr").$editableElem.trigger("click");					
		});
		
		// 主体归属 
		this.getControl("ztgs").bind("afterDialogSelect",function(e){
			_this.getControl("sycbzx").setValue('');	
			_this.getControl("cbzxdm").setValue('');	
			_this.getControl("cbzxfzr").setValue('');
			_this.getControl("gsdm").setValue('');
			_this.getControl("cbzxfzrid").setValue('');	

		});
		
		// 主体归属 
		this.getControl("ztgs").bind("change",function(e){
			_this.getControl("sycbzx").setValue('');	
			_this.getControl("cbzxdm").setValue('');	
			_this.getControl("cbzxfzr").setValue('');
			_this.getControl("gsdm").setValue('');
			_this.getControl("cbzxfzrid").setValue('');	

		});
		
		// 固定资产类别
		this.getControl("gdzclb").bind("change",function(){
			var fields = ['gkzy','gkzyid','gkbmjl','gkbmjlid','gkbmld','gkbmldid'];
			//给表单的隐藏域设置流程的姓名和用户id
			setFlowNameAndUserId("gdzclb","sydd",fields,_this);			
		});
		
		// 地区(公司)
		this.getControl("sydd").bind("change",function(){
			var fields = ['gkzy','gkzyid','gkbmjl','gkbmjlid','gkbmld','gkbmldid'];
			//给表单的隐藏域设置流程的姓名和用户id
			setFlowNameAndUserId('gdzclb','sydd',fields,_this);
		});
		
	},
	/**
	 * Leedarson_固定资产_固定资产维修改造申请
	 */
	uf_leedarson_gdzc_wxgzsq:function(){
		var _this = this;
		this.getControl("sycbzx").bind("afterDialogSelect", function(){
			_this.getControl("cbzxfzr").$editableElem.css("ime-mode", "disabled");//禁用输入法
			_this.getControl("cbzxfzr").$editableElem.keydown(function(){ 
				return false;
			});
			_this.getControl("cbzxdm").$editableElem.css("ime-mode", "disabled");//禁用输入法
			_this.getControl("cbzxdm").$editableElem.keydown(function(){ 
				return false;				
			});
			_this.getControl("cbzxfzr").setDisplayAsCtl();
			_this.getControl("cbzxfzr").setReadOnly(false);
			_this.getControl("cbzxdm").setReadOnly(false);
			_this.getControl("cbzxfzr").bind("afterAutoCompleteShow", function(){
				if(_this.getControl("cbzxfzr").$editableElem.next(".autocomplete").find("tr").size() == 1){
					_this.getControl("cbzxfzr").$editableElem.next(".autocomplete").find("tr").first().addClass("highlight");
					_this.getControl("cbzxfzr").$editableElem.next(".autocomplete").find("tr").first().trigger("click");
				}
			});
			_this.getControl("cbzxfzr").$editableElem.trigger("click");
					
		});
		
		// 主体归属 
		this.getControl("ztgs").bind("afterDialogSelect",function(e){
			_this.getControl("sycbzx").setValue('');	
			_this.getControl("cbzxdm").setValue('');	
			_this.getControl("cbzxfzr").setValue('');	
			_this.getControl("cbzxfzrid").setValue('');
			_this.getControl("gsdm").setValue('');
			var fields = ['gdzczy','gdzczyid'];
			setNumberNameAndUserId('gsdm',fields,_this);
		});
		
		// 主体归属 
		this.getControl("ztgs").bind("change",function(e){
			_this.getControl("sycbzx").setValue('');	
			_this.getControl("cbzxdm").setValue('');	
			_this.getControl("cbzxfzr").setValue('');
			_this.getControl("cbzxfzrid").setValue('');
			_this.getControl("gsdm").setValue('');
			var fields = ['gdzczy','gdzczyid'];
			setNumberNameAndUserId('gsdm',fields,_this);
		});
		

		// 固定资产类别
		this.getControl("gdzclb").bind("change",function(){
			var fields = ['gkzy','gkzyid','gkbmjl','gkbmjlid','gkbmld','gkbmldid'];
			//给表单的隐藏域设置流程的姓名和用户id
			setFlowNameAndUserId("gdzclb","dq",fields,_this);
		});
		// 地区(公司)
		
		this.getControl("dq").bind("change",function(){
			var fields = ['gkzy','gkzyid','gkbmjl','gkbmjlid','gkbmld','gkbmldid'];
			//给表单的隐藏域设置流程的姓名和用户id
			setFlowNameAndUserId('gdzclb','dq',fields,_this);
		});
		
		// 根据主体归属判断固定资产专员 
//		this.getControl("ztgs").bind("afterDialogSelect",function(e){
//			
//			var fields = ['gdzczy','gdzczyid'];
//			setNumberNameAndUserId('gsdm',fields,_this);
//		});
	},
	
	
	/**
	 * CFL包材样品申请委托单
	 */
	uf_leedarson_cflbcypsqwtd:function(){
		var _this = this;
		this.getControl("cbzxmc").bind("afterDialogSelect", function(){
			_this.getControl("cbzxfzr").$editableElem.keydown(function(){ 
				return false;
			});
			_this.getControl("cbzxdm").$editableElem.keydown(function(){ 
				return false;				
			});
			_this.getControl("cbzxfzr").setDisplayAsCtl();
			_this.getControl("cbzxfzr").setReadOnly(false);
			_this.getControl("cbzxdm").setReadOnly(false);
			_this.getControl("cbzxfzr").bind("afterAutoCompleteShow", function(){
				if(_this.getControl("cbzxfzr").$editableElem.next(".autocomplete").find("tr").size() == 1){
					_this.getControl("cbzxfzr").$editableElem.next(".autocomplete").find("tr").first().addClass("highlight");
					//alert(_this.getControl("cbzxfzr").$editableElem.next(".autocomplete").find("tr").size());
					_this.getControl("cbzxfzr").$editableElem.next(".autocomplete").find("tr").first().trigger("click");
				}
				
				/*window.setTimeout(function(){
					$autocomplete.find("tr").first().trigger("click");
				}, 600);*/
			});
			_this.getControl("cbzxfzr").$editableElem.trigger("click");
			
			
			//alert(_this.getControl("cbzxfzr").$editableElem.next(".autocomplete").find("tr").size());
			
			
		});
	},
	/**
	 * Leedarson_行政管理_固定资产对外出售申请
	 */
	uf_leedarson_xzgl_gzdwcssq:function(){
		var _this = this;
		// 固定资产类别
		this.getControl("gdzclb").bind("change",function(){
			var fields = ['gkzy','gkzyid','gkbmjl','gkbmjlid','gkbmfgld','gkbmfgldid'];
			//给表单的隐藏域设置流程的姓名和用户id
			setFlowNameAndUserId("gdzclb","dqgs",fields,_this);
			
		});
		
		//地区(公司)
		this.getControl("dqgs").bind("change",function(){
			var fields = ['gkzy','gkzyid','gkbmjl','gkbmjlid','gkbmfgld','gkbmfgldid'];
			//给表单的隐藏域设置流程的姓名和用户id
			setFlowNameAndUserId('gdzclb','dqgs',fields,_this);
		});
		
		/*固资专员判断*/
		this.getControl("ztgs").bind("afterDialogSelect",function(e){
			
			var fields = ['gdzczy','gdzczyid','cwbfzr','cwbfzrid'];
			setNumberNameAndUserId('gsdm',fields,_this);
		});
		
		this.getControl("sycbzx").bind("afterDialogSelect", function(){
			_this.getControl("cbzxfzr").$editableElem.css("ime-mode", "disabled");//禁用输入法
			_this.getControl("cbzxfzr").$editableElem.keydown(function(){ 
				return false;
			});
			_this.getControl("cbzxdm").$editableElem.css("ime-mode", "disabled");//禁用输入法
			_this.getControl("cbzxdm").$editableElem.keydown(function(){ 
				return false;				
			});
			_this.getControl("cbzxfzr").setDisplayAsCtl();
			_this.getControl("cbzxfzr").setReadOnly(false);
			_this.getControl("cbzxdm").setReadOnly(false);
			_this.getControl("cbzxfzr").bind("afterAutoCompleteShow", function(){
				if(_this.getControl("cbzxfzr").$editableElem.next(".autocomplete").find("tr").size() == 1){
					_this.getControl("cbzxfzr").$editableElem.next(".autocomplete").find("tr").first().addClass("highlight");
					//alert(_this.getControl("cbzxfzr").$editableElem.next(".autocomplete").find("tr").size());
					_this.getControl("cbzxfzr").$editableElem.next(".autocomplete").find("tr").first().trigger("click");
				}
				
				/*window.setTimeout(function(){
					$autocomplete.find("tr").first().trigger("click");
				}, 600);*/
			});
			_this.getControl("cbzxfzr").$editableElem.trigger("click");			
		});
		// 主体归属 
		this.getControl("ztgs").bind("afterDialogSelect",function(e){
			_this.getControl("sycbzx").setValue('');	
			_this.getControl("cbzxdm").setValue('');	
			_this.getControl("cbzxfzr").setValue('');	
			_this.getControl("cbzxfzrid").setValue('');	
			_this.getControl("gsdm").setValue('');	
		});
		
		// 主体归属 
		this.getControl("ztgs").bind("change",function(e){
			_this.getControl("sycbzx").setValue('');	
			_this.getControl("cbzxdm").setValue('');	
			_this.getControl("cbzxfzr").setValue('');
			_this.getControl("cbzxfzrid").setValue('');
			_this.getControl("gsdm").setValue('');	
	
		});
		
	},

	/**
	 * 工程设施维修申请成本中心
	 */
	uf_leedarson_xzgl_gcsswxsqb:function(){
		var _this = this;
		this.getControl("cbzx").bind("afterDialogSelect", function(){
			_this.getControl("cbzxfzr").$editableElem.keydown(function(){ 
				return false;
			});
			_this.getControl("cbzxdm").$editableElem.keydown(function(){ 
				return false;				
			});
			_this.getControl("cbzxfzr").setDisplayAsCtl();
			_this.getControl("cbzxfzr").setReadOnly(false);
			_this.getControl("cbzxdm").setReadOnly(false);
			_this.getControl("cbzxfzr").bind("afterAutoCompleteShow", function(){
				if(_this.getControl("cbzxfzr").$editableElem.next(".autocomplete").find("tr").size() == 1){
					_this.getControl("cbzxfzr").$editableElem.next(".autocomplete").find("tr").first().addClass("highlight");
					//alert(_this.getControl("cbzxfzr").$editableElem.next(".autocomplete").find("tr").size());
					_this.getControl("cbzxfzr").$editableElem.next(".autocomplete").find("tr").first().trigger("click");
				}
				
				/*window.setTimeout(function(){
					$autocomplete.find("tr").first().trigger("click");
				}, 600);*/
			});
			_this.getControl("cbzxfzr").$editableElem.trigger("click");
			
			
			//alert(_this.getControl("cbzxfzr").$editableElem.next(".autocomplete").find("tr").size());
			
			
		});
	},
	/**
	 * 行政采购申请（总部区域）成本中心
	 */
	uf_leedarson_xzgl_qgd:function(){
		var _this = this;
		this.getControl("cbzxmc").bind("afterDialogSelect", function(){
			_this.getControl("cbzxdm").$editableElem.keydown(function(){ 
				return false;				
			});

			_this.getControl("cbzxdm").setReadOnly(false);

		});
	},
	/**
	 * Leedarson_信息管理_IT耗材领用申请表
	 */
	uf_leedarson_xxgl_ithclysqb:function(){
		var _this = this;
		this.getControl("sscbzx").bind("afterDialogSelect", function(){
			_this.getControl("cbzxdm").$editableElem.css("ime-mode", "disabled");//禁用输入法
			_this.getControl("cbzxdm").$editableElem.keydown(function(){ 
				return false;				
			});
//			_this.getControl("cbzxfzr").setDisplayAsCtl();
//			_this.getControl("cbzxfzr").setReadOnly(false);
			_this.getControl("cbzxdm").setReadOnly(false);
//			_this.getControl("cbzxfzr").bind("afterAutoCompleteShow", function(){
//				if(_this.getControl("cbzxfzr").$editableElem.next(".autocomplete").find("tr").size() == 1){
//					_this.getControl("cbzxfzr").$editableElem.next(".autocomplete").find("tr").first().addClass("highlight");
//					//alert(_this.getControl("cbzxfzr").$editableElem.next(".autocomplete").find("tr").size());
//					_this.getControl("cbzxfzr").$editableElem.next(".autocomplete").find("tr").first().trigger("click");
//				}
//				
//				/*window.setTimeout(function(){
//					$autocomplete.find("tr").first().trigger("click");
//				}, 600);*/
//			});
//			_this.getControl("cbzxfzr").$editableElem.trigger("click");	
		});
		this.getControl("ztgs").bind("afterDialogSelect",function(e){
			_this.getControl("sscbzx").setValue('');	
			_this.getControl("cbzxdm").setValue('');	
		});
		// 主体归属 
		this.getControl("ztgs").bind("change",function(e){
			_this.getControl("sscbzx").setValue('');	
			_this.getControl("cbzxdm").setValue('');		
		});
		
		
	},
	
	/**
	 * IT固定资产验收报告成本中心
	 */
	uf_leedarson_xxgl_itgdzcys:function(){
		var _this = this;
		this.getControl("cbzx").bind("afterDialogSelect", function(){
			_this.getControl("cbzxfzr").$editableElem.keydown(function(){ 
				return false;
			});
			_this.getControl("cbzxdm").$editableElem.keydown(function(){ 
				return false;				
			});
			_this.getControl("cbzxfzr").setDisplayAsCtl();
			_this.getControl("cbzxfzr").setReadOnly(false);
			_this.getControl("cbzxdm").setReadOnly(false);
			_this.getControl("cbzxfzr").bind("afterAutoCompleteShow", function(){
				if(_this.getControl("cbzxfzr").$editableElem.next(".autocomplete").find("tr").size() == 1){
					_this.getControl("cbzxfzr").$editableElem.next(".autocomplete").find("tr").first().addClass("highlight");
					//alert(_this.getControl("cbzxfzr").$editableElem.next(".autocomplete").find("tr").size());
					_this.getControl("cbzxfzr").$editableElem.next(".autocomplete").find("tr").first().trigger("click");
				}
				
				/*window.setTimeout(function(){
					$autocomplete.find("tr").first().trigger("click");
				}, 600);*/
			});
			_this.getControl("cbzxfzr").$editableElem.trigger("click");
			
			
			//alert(_this.getControl("cbzxfzr").$editableElem.next(".autocomplete").find("tr").size());
			
			
		});
	},
	/**
	 * 请购验收单（生产零星请购）
	 */
	uf_leedarson_ledqgdlxcg:function(){
		var _this = this;
		this.getControl("cbzxmc").bind("afterDialogSelect", function(){
			_this.getControl("cbzxfzr").$editableElem.keydown(function(){ 
				return false;
			});
			_this.getControl("cbzxdm").$editableElem.keydown(function(){ 
				return false;				
			});
			_this.getControl("cbzxfzr").setDisplayAsCtl();
			_this.getControl("cbzxfzr").setReadOnly(false);
			_this.getControl("cbzxdm").setReadOnly(false);
			_this.getControl("cbzxfzr").bind("afterAutoCompleteShow", function(){
				if(_this.getControl("cbzxfzr").$editableElem.next(".autocomplete").find("tr").size() == 1){
					_this.getControl("cbzxfzr").$editableElem.next(".autocomplete").find("tr").first().addClass("highlight");
					//alert(_this.getControl("cbzxfzr").$editableElem.next(".autocomplete").find("tr").size());
					_this.getControl("cbzxfzr").$editableElem.next(".autocomplete").find("tr").first().trigger("click");
				}
				
				/*window.setTimeout(function(){
					$autocomplete.find("tr").first().trigger("click");
				}, 600);*/
			});
			_this.getControl("cbzxfzr").$editableElem.trigger("click");
			
			
			//alert(_this.getControl("cbzxfzr").$editableElem.next(".autocomplete").find("tr").size());
			
			
		});
	},
	
	/**
	 * 未送检通知单*/
	uf_leedarson_gdzc_wsjtzd:function(){
		var _this = this;
		this.getControl("cbzx").bind("afterDialogSelect", function(){
			_this.getControl("cbzxfzr").$editableElem.css("ime-mode", "disabled");//禁用输入法
			_this.getControl("cbzxfzr").$editableElem.keydown(function(){ 
				return false;				
			});
			_this.getControl("cbzxdm").$editableElem.css("ime-mode", "disabled");//禁用输入法
			_this.getControl("cbzxdm").$editableElem.keydown(function(){ 
				return false;				
			});
			
			_this.getControl("cbzxfzr").setDisplayAsCtl();
			_this.getControl("cbzxfzr").setReadOnly(false);
			_this.getControl("cbzxdm").setReadOnly(false);
			_this.getControl("cbzxfzr").bind("afterAutoCompleteShow", function(){
				if(_this.getControl("cbzxfzr").$editableElem.next(".autocomplete").find("tr").size() == 1){
					_this.getControl("cbzxfzr").$editableElem.next(".autocomplete").find("tr").first().addClass("highlight");
					//alert(_this.getControl("cbzxfzr").$editableElem.next(".autocomplete").find("tr").size());
					_this.getControl("cbzxfzr").$editableElem.next(".autocomplete").find("tr").first().trigger("click");
				}				
				/*window.setTimeout(function(){
					$autocomplete.find("tr").first().trigger("click");
				}, 600);*/
			});
			_this.getControl("cbzxfzr").$editableElem.trigger("click");
			
			_this.getControl("cbzxfzr").setReadOnly(false);
			//alert(_this.getControl("cbzxfzr").$editableElem.next(".autocomplete").find("tr").size());						
		});
		
		this.getControl("ztdw").bind("afterDialogSelect",function(e){
			_this.getControl("cbzx").setValue('');	
			_this.getControl("cbzxfzr").setValue('');	
			_this.getControl("cbzxdm").setValue('');	
			_this.getControl("cbzxfzrid").setValue('');	
			_this.getControl("ztdwid").setValue('');	
		});
		
		// 主体归属 
		this.getControl("ztdw").bind("change",function(e){
			_this.getControl("cbzx").setValue('');	
			_this.getControl("cbzxfzr").setValue('');	
			_this.getControl("cbzxdm").setValue('');	
			_this.getControl("cbzxfzrid").setValue('');	
			_this.getControl("ztdwid").setValue('');	
		});
		
		
	},
	
	/**
	 * 固资申请成本中心与合计
	 * */
	uf_leedarson_xzgl_gdzcgzsqb:function(){
		var _this = this;
		
		// 计算两个输入框的和,并设值给其他字段
		var control1 =_this.getControl("qgsl");
		var control2 =_this.getControl("jhdj");
		var resultInput="jhzje";
		_this.$("#but_hj").bind("click",function(){
			var number1 = Number(_this.$("input[name='"+control1.getCtlName()+"']").val());
			var number2 = Number(_this.$("input[name='"+control2.getCtlName()+"']").val());
			_this.setFieldValueByFieldName(resultInput,number1*number2);
		});
		
		this.getControl("cbzxmc").bind("afterDialogSelect", function(){
			_this.getControl("cbzxfzr").$editableElem.css("ime-mode", "disabled");//禁用输入法
			_this.getControl("cbzxfzr").$editableElem.keydown(function(){ 
				return false;				
			});
			_this.getControl("cbzxdm").$editableElem.css("ime-mode", "disabled");//禁用输入法
			_this.getControl("cbzxdm").$editableElem.keydown(function(){ 
				return false;				
			});		
			_this.getControl("cbzxfzr").setDisplayAsCtl();
			_this.getControl("cbzxfzr").setReadOnly(false);
			_this.getControl("cbzxdm").setReadOnly(false);
			_this.getControl("cbzxfzr").bind("afterAutoCompleteShow", function(){
				if(_this.getControl("cbzxfzr").$editableElem.next(".autocomplete").find("tr").size() == 1){
					_this.getControl("cbzxfzr").$editableElem.next(".autocomplete").find("tr").first().addClass("highlight");
					//alert(_this.getControl("cbzxfzr").$editableElem.next(".autocomplete").find("tr").size());
					_this.getControl("cbzxfzr").$editableElem.next(".autocomplete").find("tr").first().trigger("click");
				}				
				/*window.setTimeout(function(){
					$autocomplete.find("tr").first().trigger("click");
				}, 600);*/
			});
			_this.getControl("cbzxfzr").$editableElem.trigger("click");
			
			_this.getControl("cbzxfzr").setReadOnly(false);
			//alert(_this.getControl("cbzxfzr").$editableElem.next(".autocomplete").find("tr").size());						
		});

		this.getControl("ztgs").bind("afterDialogSelect",function(e){
			_this.getControl("cbzxmc").setValue('');	
			_this.getControl("cbzxdm").setValue('');	
			_this.getControl("cbzxfzr").setValue('');
            _this.getControl("cbzxfzrid").setValue('');          
            /*固资专员判断*/
			var fields = ['gdzczy','gdzczyid'];
			setNumberNameAndUserId('gsdm',fields,_this);
	
		});
		
		// 主体归属 
		this.getControl("ztgs").bind("change",function(e){
			_this.getControl("cbzxmc").setValue('');	
			_this.getControl("cbzxdm").setValue('');	
			_this.getControl("cbzxfzr").setValue('');
            _this.getControl("cbzxfzrid").setValue('');
           /*固资专员判断*/
			var fields = ['gdzczy','gdzczyid'];
			setNumberNameAndUserId('gsdm',fields,_this);
		});
		

		// 固定资产类别
		this.getControl("gdzclb").bind("change",function(){
			var fields = ['gkzy','gkzyid','gkbmjl','gkbmjlid','gkbmfgld','gkbmfgldid'];
			//给表单的隐藏域设置流程的姓名和用户id
			setFlowNameAndUserId("gdzclb","dqgs",fields,_this);
			
		});
		
		//地区(公司) 
		this.getControl("dqgs").bind("change",function(){
			var fields = ['gkzy','gkzyid','gkbmjl','gkbmjlid','gkbmfgld','gkbmfgldid'];
			//给表单的隐藏域设置流程的姓名和用户id
			setFlowNameAndUserId('gdzclb','dqgs',fields,_this);
		});
		
//		/*固资专员判断*/
//		this.getControl("ztgs").bind("afterDialogSelect",function(e){			
//			var fields = ['gdzczy','gdzczyid'];
//			setNumberNameAndUserId('gsdm',fields,_this);
//		});
	},
	
	/**
	 * ERP_物料管理_采购价格录入申请
	 * */
	uf_erp_wlgl_cgjglrsq:function(){
		var _this = this;
		
		//sap价格查询
		this.$("#btn_sapjgcx").click(function(){
			var allData = _this.collectSubformData("uf_erp_wlgl_cgjglrsq_dtbg");
						
 			var obj = {};
			obj.PT_ZMMS0043={};
			obj.PT_ZMMS0043.row=[];
			var elements = obj.PT_ZMMS0043.row;			
			for(var i=0;i<allData.length;i++){
				var data = allData[i];
				var temp = {};
				temp.EKORG = data.cgzz;
				temp.WERKS = data.gc;
				temp.LIFNR = data.gysh;
				temp.MATNR = data.wlbm;
				temp.ESOKZ = data.xxlb;			
				elements.push(temp);
			}
			
			$.ajax({
				type : "post",
				data : {
					"saps":"sapConnectConfig",
					"functionName" : "ZMMI0021",					
					"jsonParams" : JSON.stringify(obj)
				},
				async : false,
				url : ctx + "/basicdata/sap/executeRFC",
				success : function(result) {
					if (result.success) {
						//转成对象
						var obj = eval('(' + result.data + ')');
						if(obj.PT_ZMMS0043.rows == "0") return false;
						
						var elements = obj.PT_ZMMS0043.row;						
						for(var i=0;i<elements.length;i++){							
							var data = allData[i];						 
							data.wlmc = elements[i].MAKTX;
	                        data.gysmc = elements[i].SORTL;
	                        data.sapjj = elements[i].NETPR;
	                        data.sapdw = elements[i].PEINH;
	                        _this.updateRowData("uf_erp_wlgl_cgjglrsq_dtbg",data);
						}
					} else {
						alert(result.msg);
					}
				}
			});
		});
		
		//SRM查询：SRM_CGBJCXJK
		this.$("#btn_sapjgcx").click(function(){
			//todo
		});
	
	},
	
	/**
	 * 供应商Code登录/停用/恢复/修改申请表
	 * */
	uf_leedarson_gyscodesqb:function(){
		var _this = this;
//		
//		/**
//		 * 供应商Code 根据不同的类型设置必填域
//		 */
//		function SupplierCodeSetRequired(gysdlyy,
//				dlyypgjgsm,zsjxgyy,zsjlb,xgqzsj,xghzsj,
//				tyyy,tysj,tysjyysm,hfyy,hfsj,hfsjyysm,_this){
//			_this.getControl("gysdlyy").setRequired(gysdlyy);
//			_this.getControl("dlyypgjgsm").setRequired(dlyypgjgsm);
//			
//			_this.getControl("zsjxgyy").setRequired(zsjxgyy);//主数据修改原因
//			_this.getControl("zsjlb").setRequired(zsjlb);//
//			_this.getControl("xgqzsj").setRequired(xgqzsj);//
//			_this.getControl("xghzsj").setRequired(xghzsj);//
//			
//			_this.getControl("tyyy").setRequired(tyyy);//
//			_this.getControl("tysj").setRequired(tysj);//
//			_this.getControl("tysjyysm").setRequired(tysjyysm);//
//			
//			_this.getControl("hfyy").setRequired(hfyy);//
//			_this.getControl("hfsj").setRequired(hfsj);//
//			_this.getControl("hfsjyysm").setRequired(hfsjyysm);//
//		}

		
		var sqlx = _this.getControl("sqlx");//申请类型
		sqlx.bind("change",function(){
			var sqlxValue = sqlx.getValue();
			//{"1":"创建","2":"变更","3":"停用","4":"恢复","5":"扩充"}
			if(sqlxValue == "1"){
				SupplierCodeSetRequired(true,true,false,false,false,false,false,false,false,false,false,false,_this);
			}else if(sqlxValue == "2"){
				SupplierCodeSetRequired(false,false,true,true,true,true,false,false,false,false,false,false,_this);
			}else if(sqlxValue == "3"){
				SupplierCodeSetRequired(false,false,false,false,false,false,true,true,true,false,false,false,_this);
			}else if(sqlxValue == "4"){
				SupplierCodeSetRequired(false,false,false,false,false,false,false,false,false,true,true,true,_this);
			}else if(sqlxValue == "5"){
				SupplierCodeSetRequired(false,false,false,false,false,false,false,false,false,false,false,false,_this);
				//如果为扩充，则都不限
			}
		});
		
		//导入供应商编号
		this.$("#btn_drgysbm").click(function(){
			var linfnr = _this.getFieldValueByFieldName("gysbm");						
			var params='{"PI_LIFNR":"'+linfnr+'"}';	
			
			$.ajax({
				type : "post",
				data : {
					"saps":"sapConnectConfig",
					"functionName" : "ZMMI001501",
					"jsonParams" : params
				},
				async : false,
				url : ctx + "/basicdata/sap/executeRFC",
				success : function(result) {
					if (result.success) {
						//转成对象
						var obj = eval('(' + result.data + ')');
						if(obj.PT_ZMMS003301.rows == "0") return false;
						       
						var element = obj.PT_ZMMS003301.row[0];							
						_this.setFieldValueByFieldName("gysqc",element.NAME1);
						_this.setFieldValueByFieldName("gysjc",element.SORTL);
						_this.setFieldValueByFieldName("lxryyzz",element.STCD2);
						_this.setFieldValueByFieldName("dz",element.STRAS);
						_this.setFieldValueByFieldName("dh",element.TELF1);
						_this.setFieldValueByFieldName("cz",element.TELFX);
						_this.setFieldValueByFieldName("gysbm",element.LIFNR);
						_this.setFieldValueByFieldName("cgzz",element.EKORG);
						_this.setFieldValueByFieldName("zhz",element.KTOKK);
						_this.setFieldValueByFieldName("gszt",element.BUKRS);
						_this.setFieldValueByFieldName("fkfs",element.ZTERM);
						_this.setFieldValueByFieldName("yyzz",element.STCD1);
						_this.setFieldValueByFieldName("swdjzh",element.STCD3);
						_this.setFieldValueByFieldName("gj",element.LAND1);//国家
						_this.setFieldValueByFieldName("yhdm",element.BANKL);//银行代码
						
						_this.setFieldValueByFieldName("yhzh",element.BANKN);
						_this.setFieldValueByFieldName("tykm",element.AKONT);
						_this.setFieldValueByFieldName("jybb",element.WAERS);
						_this.setFieldValueByFieldName("pxm",element.ZUAWA);
						_this.setFieldValueByFieldName("lxr",element.VERKF);
						_this.setFieldValueByFieldName("lxdh",element.TELF2);
													
					} else {
						alert(result.msg);
					}
				}
			});
		});			
	
	},

	// ERP_业务管理_订单签发申请表 
	uf_erp_ywgl_ddqfsqb:function(){

        var _this = this;

        // 清除信息
        _this.$("#btn_qcxx").click(function(){
			var  options =  _this.cache.get.call(_this, cacheType.options);
			_this.dyform(options);
		});
        
        this.$("#btn_dddr").click(function(){	
            var param1=_this.getFieldValueByFieldName("xsddh");
	        var params='{"PI_VBELN":"'+param1+'"}';
			$.ajax({
				type : "post",
				data : {
					"saps":"sapConnectConfig",
					"functionName" : "ZOA_SO_EVA_READ",
					"jsonParams" : params
				},
				async : false,
				url : ctx + "/basicdata/sap/executeRFC",
				success : function(result) {
					if (result.success) {
						// 保存成功刷新列表
						//转成对象
						var jsonobj = eval('(' + result.data + ')');
						//获得当个值
						//console.log(jsonobj.PT_ZSDS0029);
						//获得构造体消息字段值
						//console.log(jsonobj.PO_RETURN.MESSAGE);
						//获得table 第二行的 MATNR字段值
						//console.log(jsonobj.PT_TAB.row[0].MESSAGE);
						if(jsonobj.PT_VBAK1.row.length>0){
							_this.setFieldValueByFieldName("xszz",jsonobj.PT_VBAK1.row[0].VKORG);
							_this.setFieldValueByFieldName("sch",jsonobj.PT_VBAK1.row[0].BSTKD);
							_this.setFieldValueByFieldName("SPART",jsonobj.PT_VBAK1.row[0].SPART);
							_this.setFieldValueByFieldName("KUNNR",jsonobj.PT_VBAK1.row[0].KUNNR);
							_this.setFieldValueByFieldName("VTEXT",jsonobj.PT_VBAK1.row[0].VTEXT);
							_this.setFieldValueByFieldName("AUART",jsonobj.PT_VBAK1.row[0].AUART);
							_this.setFieldValueByFieldName("ZETTYP",jsonobj.PT_VBAK1.row[0].ZETTYP);
							_this.setFieldValueByFieldName("EDATU1",jsonobj.PT_VBAK1.row[0].EDATU);
							_this.setFieldValueByFieldName("khjc",jsonobj.PT_VBAK1.row[0].SORT1);
							_this.setFieldValueByFieldName("crmno",jsonobj.PT_VBAK1.row[0].CRMLN);
							_this.setFieldValueByFieldName("dgyy",jsonobj.PT_VBAK1.row[0].AUGRU);
							_this.setFieldValueByFieldName("dgyy2",jsonobj.PT_VBAK1.row[0].BEZEI);
							_this.setFieldValueByFieldName("xsddscyq",jsonobj.PT_VBAK1.row[0].ZSCYQ);	
							_this.setFieldValueByFieldName("ddlxbz",jsonobj.PT_VBAK1.row[0].ABRVW);
							_this.setFieldValueByFieldName("khdqjb",jsonobj.PT_VBAK1.row[0].ZCUCLS);
							// 从表数据添加 
							for(var i=0;i<jsonobj.PT_VBAK1.row.length;i++){
								_this.addRowData("uf_erp_ywgl_ddqfxx", {
									"hxmh":jsonobj.PT_VBAK1.row[i].POSNR, "cpid":jsonobj.PT_VBAK1.row[i].MATNR,
									"cp":jsonobj.PT_VBAK1.row[i].ZTEXT,"sl":jsonobj.PT_VBAK1.row[i].ZMENG,"gc":jsonobj.PT_VBAK1.row[i].WERKS,
									"cjp":jsonobj.PT_VBAK1.row[i].ZYQDT,"khwlbh":jsonobj.PT_VBAK1.row[i].KDMAT,"bzxxbbh":jsonobj.PT_VBAK1.row[i].ZPAK_NO});
							}
							params = "{VKORG:'"+jsonobj.PT_VBAK1.row[0].VKORG+"',KUNNR:'"+jsonobj.PT_VBAK1.row[0].KUNNR+"'}";
							$.ajax({
								type : "post",
								data : {
									"saps":"sapConnectConfig",
									"functionName" : "ZSDI0008",
									"jsonParams" : params
								},
								async : false,
								url : ctx + "/basicdata/sap/executeRFC",
								success : function(scdresult) {
									if (scdresult.success) {
										var scdjsonobj = eval('(' + scdresult.data + ')');
										if(scdjsonobj.PT_RETURN.rows>0){
											_this.setFieldValueByFieldName("xyed",scdjsonobj.PT_RETURN.row[0].KLIMK);
											_this.setFieldValueByFieldName("yyed",scdjsonobj.PT_RETURN.row[0].OBLIG);
											_this.setFieldValueByFieldName("yced",scdjsonobj.PT_RETURN.row[0].EXCESS);
											//给从表添加行,并给从表设值
											//_this.addRowData("uf_erp_ywgl_ddqfxx", {"hxmh":"1", "cpid":"2"});
											//_this.setFieldValueByFieldName("yyed",result.data);
										}                     
									} else {
										oAlert2(result.msg);
									}
								}
							});
							
						}else{
							oAlert2(jsonobj.PO_RETURN.MESSAGE);
						}
					} else {
						oAlert2(result.msg);
					}
				}
			});

		});
        // 解除订单评审状态
        this.$("#btn_jcddpszt").click(function(){	
            var param1 = _this.getFieldValueByFieldName("xsddh");
            if(param1==''){
            	oAlert2("请输入订单号！");
            }else{
		        var params = '{"PI_VBELN":"' + param1 + '"}';
				$.ajax({
					type : "post",
					data : {
						"saps":"sapConnectConfig",
						"functionName" : "ZSDI0001",
						"jsonParams" : params
					},
					async : false,
					url : ctx + "/basicdata/sap/executeRFC",
					dataType : "json",
					success : function(result) {
						var obj = eval("(" + result.data + ")");
						oAlert2(obj.PT_RETURN.row[0].MESSAGE);
					}
				});
            }
        });
	},
	
	// LED小批量试产报告
	uf_leedarson_zzgc_ledxplsc:function(){
		var _this = this;
		var jhhC =_this.getControl("jhh");
		_this.$("input[name='"+jhhC.getCtlName()+"']").change(function(){
			var aufnr = fitlength($(this).val(),12,"0");
			var sql = "select matnr,dwerk from AFPO where AUFNR = '" + aufnr + "' ";
			$.ajax({
				type : "post",
				data : {
					"dataSourceId":"SAP",
					"sql" : sql
				},
				async : false,
				url : ctx + "/special/dyform/sap",
				success : function(result) {
					var arr = eval(result);
					if(arr.length>=1){
						_this.setFieldValueByFieldName("cpID",arr[0].mATNR);
						_this.setFieldValueByFieldName("jgc",arr[0].dWERK);
					}
				}
			});
		});
		var xpjdC = _this.getControl("xpjd");
		_this.$("select[name='"+xpjdC.getCtlName()+"']").change(function(){
			var xpjdV = $(this).val();
			if(xpjdV == 'PP'){
				_this.setFieldValueByFieldName("bz2","直通率>93%，收率99%");
				_this.setFieldValueByFieldName("bz3",">85%");
			}else if(xpjdV == "首批"){
				_this.setFieldValueByFieldName("bz2","直通率>99.5%");
				_this.setFieldValueByFieldName("bz3","99%");
			}else{
				_this.setFieldValueByFieldName("bz2","");
				_this.setFieldValueByFieldName("bz3","");
			}
		});
		//计算
        $.ControlManager.registFormula(
				{
				    triggerElements: ["uf_zzgc_ledxplscxx:trs", "uf_zzgc_ledxplscxx:bls"], //字段名数组,这些元素变化会触发公式
				    formula: function () {//this指向当前控件  
				        //_this.getControl("hj").setValue(sumOfColumn("uf_ddj_cwl_thxxxx:je", _this));
				        var rowId = this.getDataUuid();
				        var chslctl = _this.getControl("trs", rowId);
				        var djctl = _this.getControl("bls", rowId);
				        var ztlxs = 1.00 - (parseNumber(djctl.getValue())/parseNumber(chslctl.getValue())); //直通率小数
				        var ztldx = (ztlxs.toFixed(2)) * 100;
				        if (ztldx != "-Infinity") {
				            _this.getControl("ztl", rowId).setValue(ztldx + "%");
				        } 
				    }
				}
		);
		
		
	},
	
	// 领退料单
	uf_leedarson_scgl_ltld:function(){
		var _this = this;

		this.$(".editableClass[name$=___wlID]").live("change", function(e){
			
			var matnr = $(this).val();
			var nufnr = _this.getFieldValueByFieldName("scddh");
			var formId = "uf_leedarson_scgl_ltldxx";
			var formUuid = _this.getFormUuid(formId); 
			var ctl = _this.getSubformControl(formUuid);
			// 物料id、生产订单号、物料描述、单位
			var sql = "select r.matnr,r.aufnr,m.maktx,a.meins from resb r,makt m,mara a " +
					"where m.matnr=r.matnr and r.matnr=a.matnr and r.matnr='"+matnr+
					"' and r.aufnr = '"+ nufnr+"'";
			$.ajax({
				type : "post",
				data : {
					"dataSourceId":"SAP",
					"sql" : sql
				},
				async : false,
				url : ctx + "/special/dyform/sap",
				success : function(result) {
					var arr = eval(result);
					ctl.delRowData(ctl.selectedRowId);
					if(arr.length>0){
						for(var i=0;i<arr.length;i++){
							_this.addRowData("uf_leedarson_scgl_ltldxx", {
								"wlID":arr[i].mATNR, "wlms":arr[i].mAKTX,
								"dw":arr[i].mEINS});
						}
					}
				}
			});
		});
	},
	
	// 异常工时费用申请单
	uf_scgl_ycgsfysqpzn:function(){
		var _this = this;

		this.$(".editableClass[name$=___wlid]").live("change", function(e){
			// 从表ID
			var formId = "uf_scgl_ycgsfysqpznxx";
			// 从表Uuid
			var formUuid = _this.getFormUuid(formId); 
			// 从表操作控件 
			var ctl = _this.getSubformControl(formUuid);
			var rowData = ctl.getRowData(ctl.selectedRowId);
			// 物料id、生产订单号、物料描述、单位
			var sql = " select m.matnr,m.maktx,a.meins,w.zplp1 from makt m,mara a,mbew w " +
					"where m.matnr = a.matnr and m.matnr=w.matnr and m.matnr='"+rowData.wlid+"'";
			$.ajax({
				type : "post",
				data : {
					"dataSourceId":"SAP",
					"sql" : sql
				},
				async : false,
				url : ctx + "/special/dyform/sap",
				success : function(result) {
					var arr = eval(result);
					if(arr.length==1){
						var json = {"wlid":arr[0].mATNR, "wlmc":arr[0].mAKTX, "dw":arr[0].mEINS,
							"cldj":arr[0].zPLP1,"id":ctl.selectedRowId, "uuid":ctl.selectedRowId};
			
						_this.updateRowData(formId, json);
					}
				}
			});
		});
	},

	// 成品例行试验&RoHS测试&寿命跟踪计划表
	uf_pbgl_cplxsyyrohscsysmgz:function(){
		var _this = this;
		
		// 委托单号输入后查询数据
		var wtdhC =_this.getControl("wtdh");
		_this.$("input[name='"+wtdhC.getCtlName()+"']").change(function(){
			$.ajax({
				data : {
					"tstorder_no": $(this).val() 
				},
				async : false,
				url : ctx + "/lms/search/queryOrderByNO",
				success : function(result) {
					var arr = eval(result);
					if(arr.length>1){
						var sjrq = new Date(arr[0][1].time);
						var sjrqStr = sjrq.getFullYear() + "-" + (sjrq.getMonth()+1) + "-" + sjrq.getDate();
						_this.setFieldValueByFieldName("sjrq",sjrqStr);
						_this.setFieldValueByFieldName("csdy",arr[0][2]);
						_this.setFieldValueByFieldName("gygys",arr[0][3]);
						_this.setFieldValueByFieldName("csmd",arr[0][4]);
						_this.setFieldValueByFieldName("csjg",arr[0][5]);
					}
				}
			});

		});
	},
	
	// 成品例行试验&RoHS测试异常单
	uf_pbgl_cplxsyyrohscsycd:function(){
		var _this = this;
		var wtdhC =_this.getControl("wtdh");
		_this.$("input[name='"+wtdhC.getCtlName()+"']").change(function(){
			$.ajax({
				type : "get",
				data : {
					"tstorder_no": $(this).val() 
				},
				async : false,
				url : ctx + "/lms/search/queryOrderByError",
				success : function(result) {
					var arr = eval(result);
					if(arr.length==1){
						_this.setFieldValueByFieldName("wtdh",arr[0][0]);
						_this.setFieldValueByFieldName("kh",arr[0][1]);
						_this.setFieldValueByFieldName("xsddh",arr[0][2]);
						_this.setFieldValueByFieldName("cpid",arr[0][3]);
						_this.setFieldValueByFieldName("xh",arr[0][4]);
						_this.setFieldValueByFieldName("csmd",arr[0][5]);
					}
				}
			});
		});
	},
	
	//客户超额表单:客户信息查询
	uf_erp_ywgl_khcechsqd:function(){
		 var _this = this;
		 //alert(_this.getFieldValueByFieldName);

		 
         this.$("#khxxcx").click(function(){	
        	var param=_this.getFieldValueByFieldName("accountid");
     		var relParam1=_this.getControl("xszz").getValue();
         	
     		if(param != null && param !="" && typeof param != "undifined"
     			&& relParam1 != null && relParam1 != "" && relParam1 != "undifined"
     		){
	     	    var params="{'PI_KUNNR':'"+param+"','PI_VKORG':'"+relParam1+"','PI_VTWEG':'10','PI_KKBER':'1000','PI_SPART':'00','PI_CMFRE':'20130808'}";
				$.ajax({
					type : "post",
					data : {
						"saps":"sapConnectConfig",
						"functionName" : "ZSDI0008",
						"jsonParams" : params
					},
					async : false,
					url : ctx + "/basicdata/sap/executeRFC",
					success : function(result) {
						if (result.success) {
							//_this.setFieldValueByFieldName("xyed",jsonobj.PT_ZSDS0029.row[0].KLIMK);
							//_this.setFieldValueByFieldName("yyed",jsonobj.PT_ZSDS0029.row[0].OBLIG);
							//_this.setFieldValueByFieldName("yced",jsonobj.PT_ZSDS0029.row[0].EXCESS);
							var jsonobj = eval('(' + result.data + ')');
							
							_this.setFieldValueByFieldName("khbm",jsonobj.PT_ZSDS0029.row[0].SORTL);
							_this.setFieldValueByFieldName("fktj1",jsonobj.PT_ZSDS0029.row[0].ZTERM);
							_this.setFieldValueByFieldName("fktj2",jsonobj.PT_ZSDS0029.row[0].VTEXT);
							_this.setFieldValueByFieldName("xyed",jsonobj.PT_ZSDS0029.row[0].KLIMK);
							_this.setFieldValueByFieldName("yyed",jsonobj.PT_ZSDS0029.row[0].OBLIG);
							_this.setFieldValueByFieldName("ycje",jsonobj.PT_ZSDS0029.row[0].EXCESS);
							
							_this.setFieldValueByFieldName("xdspcstj",jsonobj.PT_ZSDS0029.row[0].APLZL);
							                      
						} else {
							alert(result.msg);
						}
					}
					
				});
			
     		}
		});

			//this.$(".editableClass[name$=___wxjhdh]").live("change", function(e){
            //var val = $(this).val();
         this.$("#jhdxxcx").click(function(){
        	 var sqdate = _this.getFieldValueByFieldName("sqrq");
        	 var allData = _this.collectSubformData("uf_erp_ywgl_khcechsqddtbg");
				
  			var obj = {};
  			obj.PI_ZCEDAT=sqdate;
 			obj.PT_ZSDS0030={};
 			obj.PT_ZSDS0030.row=[];
 			var elements = obj.PT_ZSDS0030.row;			
 			for(var i=0;i<allData.length;i++){
 				var data = allData[i];
 				var temp = {};
 				temp.VBELN = data.wxjhdh;		
 				elements.push(temp);
 			}
 			
 			//alert(JSON.stringify(obj));
 			/*
				var sqdate = _this.getFieldValueByFieldName("sqrq");
				var formId = "uf_erp_ywgl_khcechsqddtbg";
				var formUuid = _this.getFormUuid(formId); 
				var ctl = _this.getSubformControl(formUuid);
				var rowData = ctl.getRowData(ctl.selectedRowId);
				var params = {};
				params["VBELN"] = rowData.wxjhdh;
					var paramArray = [];				
					paramArray.push(params);
				var par={};
					par["PI_ZCEDAT"] = sqdate;
					var res = {};
					res.row = paramArray;
					par["PT_ZSDS0030"] = res;
					alert(JSON.stringify(par));
					*/
				$.ajax({
	 				type : "post",
	 				data : {
	 					"saps":"sapConnectConfig",
	 					"functionName" : "ZSDI0009",
	 					//"jsonParams" : JSON.stringify(par)
	 					"jsonParams" : JSON.stringify(obj)
	 				},
	 				
	 				async : false,
	 				url : ctx + "/basicdata/sap/executeRFC",
	 				success : function(result) {
	 					if (result.success) {
	 						//alert(result.data);
	 						//转成对象
							var obj = eval('(' + result.data + ')');
							if(obj.PT_ZSDS0030.rows == "0") return false;
							
							var elements = obj.PT_ZSDS0030.row;						
							for(var i=0;i<elements.length;i++){							
								var data = allData[i];		
								
								data.jhrq = elements[i].LFDAT;
		                        data.chje = elements[i].KBETR;
		                        data.bz = elements[i].WAERK;
		                        
		                        _this.updateRowData("uf_erp_ywgl_khcechsqddtbg",data);
							}
	 						
	 						/*var jsonobj = eval('(' + result.data + ')');	 						
	 						var json = {"jhrq":jsonobj.PT_ZSDS0030.row[0].LFDAT,"chje":jsonobj.PT_ZSDS0030.row[0].KBETR,
	 								"bz":jsonobj.PT_ZSDS0030.row[0].WAERK,"id":ctl.selectedRowId, "uuid":ctl.selectedRowId};
							_this.updateRowData(formId, json);*/
	 					} else {
	 						alert(result.msg);
	 					}
	 				}
	 			});

			});

	},
	/**
	 * 材料开发补录
	 * shizy
	 * */
		uf_clkfgl_clkfglmkbd:function(){
			var _this = this;
			var num = 1;
			
			var formId = "uf_clkfgl_clkfglgysxx";
			var formUuid = _this.getFormUuid(formId); 
			var ctl = _this.getSubformControl(formUuid);
			var hNo = $("#No").val();
			//添加按钮响应放法
			ctl.bind("afterInsertRow", function(rowdata){
				var json = {"jcbh":hNo+(num++),"id":rowdata.id,"uuid":rowdata.id};
				_this.updateRowData(formId, json);
			});
			
			/*this.$("#btn_add_a390210c-8005-4c9d-88af-c01fbf789644").click(function(){
				var json = {"jcbh":"1231231","id":ctl.selectedRowId,"uuid":ctl.selectedRowId};
				_this.updateRowData(formId, json);
			})*/
		},
		/*
			 * shizy
			 * */
		uf_scgl_clblgkbbl:function(){
			var _this = this;
			var zdbkjhh = $("#zdjhh").val();

			_this.setFieldValueByFieldName("zdbkjhh",zdbkjhh);
		},
		/*灯具技术规格书从表默认参数赋值
			 * shizy
			 * */
		uf_djjsggs:function(){
			var _this = this;

			if(this.isCreate()){
				$.ajax({
	 				type : "post",
	 				data : {
	 					"tableName" : "ldx_default_djjsggs1"
	 				},
	 				dataType:"json",
	 				async : false,
	 				url : ctx + "/defaultValue/getDefaultData",
	 				success : function(result) {
	 					var arr = eval(result);
	 					var mrz1 = arr[0].dtbg1;
	 					var mrz2 = arr[0].dtbg2;
	 					var mrz3 = arr[0].dtbg3;
	 					var mrz4 = arr[0].dtbg4;
	 					
	 					var val = new Array();
	 					
	 					var formId = "uf_djjsggsdtbg1";
	 					var formUuid = _this.getFormUuid(formId); 
	 					var ctl = _this.getSubformControl(formUuid);
 						for(var i=0;i<mrz1.length;i++){
 							val[i] = {"cszbp":mrz1[i][1],"dwu":mrz1[i][2],"bzkzfw":mrz1[i][3],"form_uuid":formUuid};
 						}
	 					ctl.fillFormData(val);
	 						
	 					var val2 = new Array();
	 					var formId2 = "uf_djjsggsdtbg2";
	 					var formUuid2 = _this.getFormUuid(formId2); 
	 					var ctl2 = _this.getSubformControl(formUuid2);
 						for(var i=0;i<mrz2.length;i++){
 							val2[i] = {"cszb":mrz2[i][1],"dw":mrz2[i][2],"bz":mrz2[i][3],"form_uuid":formUuid2};
 						}
 						ctl2.fillFormData(val2);
	 					
 						var val3 = new Array();
	 					var formId3 = "uf_djjsggsdtbg3";
	 					var formUuid3 = _this.getFormUuid(formId3); 
	 					var ctl3 = _this.getSubformControl(formUuid3);
 						for(var i=0;i<mrz3.length;i++){
 							val3[i] = {"cszb":mrz3[i][1],"dw":mrz3[i][2],"bz":mrz3[i][3],"form_uuid":formUuid3};
 						}
 						ctl3.fillFormData(val3);
	 					
 						var val4 = new Array();
	 					var formId4 = "uf_djjsggsdtbg4";
	 					var formUuid4 = _this.getFormUuid(formId4); 
	 					var ctl4 = _this.getSubformControl(formUuid4);
 						for(var i=0;i<mrz4.length;i++){
 							val4[i] = {"cszb":mrz4[i][1],"tz1":mrz4[i][2],"tz2":mrz4[i][3],"tz3":mrz4[i][4],"bz":mrz4[i][5],"form_uuid":formUuid4};
 						}
 						ctl4.fillFormData(val4);
	 				}
	 			});
				var bh = $("span[name=bh]").text();
				if(bh == ""){
					$.ajax({
		 				type : "post",
		 				data : {
		 					"noHead" : "F1555",
		 					"formName" : "uf_djjsggs"
		 				},
		 				async : false,
		 				url : ctx + "/pageForValues/getOrderNo",
		 				success : function(result) {
							_this.setFieldValueByFieldName("bh",result);
		 				}
					});
				}
			}
		},
		/*工艺评审表
			 * shizy
			 * */
		uf_leedarson_thdepgypsb:function(){
			var _this = this;
			
			var formId = "uf_gypsbdtbd";
			var formUuid = _this.getFormUuid(formId); 
			var ctl = _this.getSubformControl(formUuid);
			if(this.isCreate()){
				$("select[name='xpjd']").change(function(){
					
					var cplx = $("select[name='cplx']").val();
					var xpjd = $("select[name='xpjd']").val();
					if(cplx!=""){
						var fromtable = "";
						if(cplx.indexOf("1")>=0&&xpjd.indexOf("1")>=0)fromtable="TEP";
						if(cplx.indexOf("1")>=0&&xpjd.indexOf("2")>=0)fromtable="TES";
						if(cplx.indexOf("2")>=0&&xpjd.indexOf("1")>=0)fromtable="DEP";
						if(cplx.indexOf("2")>=0&&xpjd.indexOf("2")>=0)fromtable="DES";
						if(cplx.indexOf("3")>=0&&xpjd.indexOf("1")>=0)fromtable="ZEP";
						if(cplx.indexOf("3")>=0&&xpjd.indexOf("2")>=0)fromtable="ZES";
						$.ajax({
			 				type : "post",
			 				data : {
			 					"tableName" : "LDX_DEFAULT_GYPS",
			 					"whereSql" : " fromtable='"+fromtable+"'"
			 				},
			 				dataType:"json",
			 				async : false,
			 				url : ctx + "/pageForValues/getDefaultForEP",
			 				success : function(result) {
			 					var arr = eval(result);
			 					
			 					var val = new Array();
			 					for(var i=0;i<arr.length;i++){
			 						val[i] = {"pslx":arr[i][1],"psxm":arr[i][2],"psnr":arr[i][3],"form_uuid":formUuid};
	//			 					_this.addRowData(formId,json);
			 					}
			 					ctl.fillFormData(val);
			 					var rowData = _this.collectSubformData(formId);
			 					var blryid = $("input[name=blryid]").val();
			 					for(var i=0;i<rowData.length;i++){
			 						_this.getControl("zrr", rowData[i].id ).bind("show", function(){
			 							$.unit.open({
			 								title : "选择人员",
			 								valueField : "zrr",
			 								type : "MyDept",
			 								multiple:false, 
			 								afterSelect : function(retVal) {	
			 									if(blryid.indexOf(retVal.id)<0){
				 									if(blryid != ""){
				 										blryid += ";"+retVal.id;
				 										_this.setFieldValueByFieldName("blryid",blryid);
				 									}else{
				 										blryid = retVal.id;
				 										_this.setFieldValueByFieldName("blryid",blryid);
				 									}
			 									}
			 									var json = {"zrr":retVal.name,"id":ctl.selectedRowId,"uuid":ctl.selectedRowId};
			 		 							_this.updateRowData(formId, json);
			 								},
			 								selectType : 4	
			 							});
			 						}, true);
			 					}
			 				}
						});
					}else{
						$("select[name='xpjd']").get(0).value="";
						oAlert("产品类型为空");
					}
				});
				$("select[name='cplx']").change(function(){
					var cplx = $("select[name='cplx']").val();
					var xpjd = $("select[name='xpjd']").val();
					if(xpjd!=""){
						var fromtable = "";
						if(cplx.indexOf("1")>=0&&xpjd.indexOf("1")>=0)fromtable="TEP";
						if(cplx.indexOf("1")>=0&&xpjd.indexOf("2")>=0)fromtable="TES";
						if(cplx.indexOf("2")>=0&&xpjd.indexOf("1")>=0)fromtable="DEP";
						if(cplx.indexOf("2")>=0&&xpjd.indexOf("2")>=0)fromtable="DES";
						if(cplx.indexOf("3")>=0&&xpjd.indexOf("1")>=0)fromtable="ZEP";
						if(cplx.indexOf("3")>=0&&xpjd.indexOf("2")>=0)fromtable="ZES";
						$.ajax({
			 				type : "post",
			 				data : {
			 					"tableName" : "LDX_DEFAULT_GYPS",
			 					"whereSql" : " fromtable='"+fromtable+"'"
			 				},
			 				dataType:"json",
			 				async : false,
			 				url : ctx + "/pageForValues/getDefaultForEP",
			 				success : function(result) {
			 					var arr = eval(result);
			 					
			 					var val = new Array();
			 					for(var i=0;i<arr.length;i++){
			 						val[i] = {"pslx":arr[i][1],"psxm":arr[i][2],"psnr":arr[i][3],"form_uuid":formUuid};
	//			 					_this.addRowData(formId,json);
			 					}
			 					ctl.fillFormData(val);
			 					var rowData = _this.collectSubformData(formId);
			 					var blryid = $("input[name=blryid]").val();
			 					for(var i=0;i<rowData.length;i++){
			 						_this.getControl("zrr", rowData[i].id ).bind("show", function(){
			 							$.unit.open({
			 								title : "选择人员",
			 								valueField : "zrr",
			 								type : "MyDept",
			 								multiple:false, 
			 								afterSelect : function(retVal) {	
			 									if(blryid.indexOf(retVal.id)<0){
				 									if(blryid != ""){
				 										blryid += ";"+retVal.id;
				 										_this.setFieldValueByFieldName("blryid",blryid);
				 									}else{
				 										blryid = retVal.id;
				 										_this.setFieldValueByFieldName("blryid",blryid);
				 									}
			 									}
			 									var json = {"zrr":retVal.name,"id":ctl.selectedRowId,"uuid":ctl.selectedRowId};
			 		 							_this.updateRowData(formId, json);
			 								},
			 								selectType : 4	
			 							});
			 						}, true);
			 					}
			 				}
						});
					}
				});
				var bh = $("span[name=bh]").text();
				if(bh == ""){
					$.ajax({
		 				type : "post",
		 				data : {
		 					"noHead" : "P1617",
		 					"formName" : "uf_leedarson_thdepgypsb"
		 				},
		 				async : false,
		 				url : ctx + "/pageForValues/getOrderNo",
		 				success : function(result) {
							_this.setFieldValueByFieldName("bh",result);
		 				}
					});
				}
			}
		},
		/*灯具各阶段设计评审表
			 * shizy
			 * */
		uf_leedarson_djsjpsb:function(){
			var _this = this;
			
			$("input[name='xmdh']").change(function(){
				$.ajax({
	 				type : "post",
	 				data : {
	 					"xmdh" : $("input[name='xmdh']").val()
	 				},
	 				dataType:"json",
	 				async : false,
	 				url : ctx + "/pageForValues/getCharterToDJSJPS",
	 				success : function(result) {
	 					var arr = eval(result);
	 					if(arr.length==1){
	 						var cpdl = eval(arr[0][0]);
	 						var value = "";
	 						for(var key in cpdl){
	 							value = cpdl[key];
	 						}
		 					_this.setFieldValueByFieldName("cpdl",value);
		 					_this.setFieldValueByFieldName("cpxl",arr[0][1]);
		 					_this.setFieldValueByFieldName("cpxh",arr[0][2]);
		 					_this.setFieldValueByFieldName("xmms",arr[0][3]);
	 					}else{
	 						alert("项目单取值错误");
	 					}
	 				}
				});
			});
			
			var bh = $("span[name=bh]").text();
			if(bh == ""){
				$.ajax({
	 				type : "post",
	 				data : {
	 					"noHead" : "F1238",
	 					"formName" : "uf_leedarson_djsjpsb"
	 				},
	 				async : false,
	 				url : ctx + "/pageForValues/getOrderNo",
	 				success : function(result) {
						_this.setFieldValueByFieldName("bh",result);
	 				}
				});
			}
		},
		/*灯具产品可行性分析报告
			 * shizy
			 * */
		uf_djcpkxxfxbg:function(){
			var _this = this;
			$("input[name='xmdh']").change(function(){
				$.ajax({
	 				type : "post",
	 				data : {
	 					"xmdh" : $("input[name='xmdh']").val()
	 				},
	 				dataType:"json",
	 				async : false,
	 				url : ctx + "/pageForValues/queryCharterToAR",
	 				success : function(result) {
	 					var arr = eval(result);
	 					var charter = arr[0].charter;
	 					var cpid = arr[0].cpid;
	 					var cpidvalue = "";
 						for(var i=0;i<cpid.length;i++){
 							cpidvalue += cpid[i];
 							if(i<cpid.length-1)cpidvalue += ",";
 						}
	 					if(arr.length==1){
	 						var cpdl = eval(charter[0][0]);
	 						var value = "";
	 						for(var key in cpdl){
	 							value = cpdl[key];
	 						}
	 						var xmlx = eval(charter[0][12]);
	 						for(var key in cpdl){
	 							xmlx = cpdl[key];
	 						}
		 					_this.setFieldValueByFieldName("cpdl",value);
		 					_this.setFieldValueByFieldName("cpxl",charter[0][1]);
		 					_this.setFieldValueByFieldName("cpxh",charter[0][2]);
		 					_this.setFieldValueByFieldName("mbbomcb",charter[0][4]);
		 					_this.setFieldValueByFieldName("sjpg11",charter[0][5]);
		 					_this.setFieldValueByFieldName("sjpg21",charter[0][6]);
		 					_this.setFieldValueByFieldName("sjpg41",charter[0][7]);
		 					_this.setFieldValueByFieldName("sjpg51",charter[0][8]);
		 					_this.setFieldValueByFieldName("sjpg61",charter[0][9]);
		 					_this.setFieldValueByFieldName("yxj",charter[0][10]);
		 					_this.setFieldValueByFieldName("xmlx",xmlx);
		 					_this.setFieldValueByFieldName("cpid",cpidvalue == "null"?"":cpidvalue);
	 					}else if(arr.length==0){
	 						oAlert("项目单取值为空");
	 					}else{
	 						oAlert("项目单取值错误");
	 					}
	 				}
				});
			});
			
			$("input[name='ygbomcb']").focus(function(){
				  var val_1 = $("input[name='gyygcb']").val(),
				  val_2 = $("input[name='pcbygcb']").val(),
				  val_3 = $("input[name='qdygcb']").val(),
				  val_4 = $("input[name='rjygcb']").val(),
				  val_5 = $("input[name='jgygcb']").val();
				  
				  if(val_1==""){
					  oAlert("光源预估成本值为空");
				  }else if(val_2==""){
					  oAlert("PCB预估成本值为空");
				  }else if(val_3==""){
					  oAlert("驱动预估成本值为空");
				  }else if(val_4==""){
					  oAlert("软件预估成本值为空");
				  }else if(val_5==""){
					  oAlert("结构预估成本值为空");
				  }else{
					  _this.setFieldValueByFieldName("ygbomcb",eval(val_1)+eval(val_2)+eval(val_3)+eval(val_4)+eval(val_5));
				  }
			 });
			
			var bh = $("span[name=bh]").text();
			if(bh == ""){
				$.ajax({
	 				type : "post",
	 				data : {
	 					"noHead" : "F1239",
	 					"formName" : "uf_djcpkxxfxbg"
	 				},
	 				async : false,
	 				url : ctx + "/pageForValues/getOrderNo",
	 				success : function(result) {
						_this.setFieldValueByFieldName("bh",result);
	 				}
				});
			}
		},
		/*灯具PDCP审核默认值设置
		 * shizy
		 * */
		uf_leedarson_djpdcpshb:function(){
			var _this = this;
			
			//if(this.getOptional().first){ 
			//}
			var formId = "uf_djpdcpshbdtbd";
			if(this.isCreate()){
				var formUuid = _this.getFormUuid(formId); 
				var ctl = _this.getSubformControl(formUuid);
				$.ajax({
	 				type : "post",
	 				data : {
	 					"tableName" : "ldx_default_djjsggs1",
	 					"whereSql" : " formtable='PDCP'"
	 				},
	 				dataType:"json",
	 				async : false,
	 				url : ctx + "/defaultValue/getDefaultPDCP",
	 				success : function(result) {
	 					var arr = eval(result);
	 					var val = new Array();
	 					for(var i=0;i<arr.length;i++){
	 						val[i]= {"jcx":arr[i][1],"form_uuid":formUuid};
	 					}
	 					ctl.fillFormData(val);
	 				}
				});
				//"form_uuid":"b373bfbf-2a70-494d-a1d4-682d50c12e85","jcx":"1、对该产品的参数信息是否有疑议。或需进一步确认？"
				var bh = $("span[name=bh]").text();
				if(bh == ""){
					$.ajax({
		 				type : "post",
		 				data : {
		 					"noHead" : "F1246",
		 					"formName" : "uf_leedarson_djpdcpshb"
		 				},
		 				async : false,
		 				url : ctx + "/pageForValues/getOrderNo",
		 				success : function(result) {
							_this.setFieldValueByFieldName("bh",result);
		 				}
					});
				}
			}
			/*_this.formParseComplete = function(){
				var rowData = _this.collectSubformData(formId);
				$.ajax({
	 				type : "post",
	 				data : {
	 					"tableName" : "ldx_default_djjsggs1",
	 					"whereSql" : " formtable='PDCP'"
	 				},
	 				dataType:"json",
	 				async : false,
	 				url : ctx + "/defaultValue/getDefaultPDCP",
	 				success : function(result) {
	 					var arr = eval(result);
	 					
	 					for(var i=0;i<arr.length;i++){
	 						var json = {"jcx":arr[i][1],"id":rowData[i].id,"uuid":rowData[i].uuid};
								_this.updateRowData(formId, json);
	 					}
	 				}
				});
			}*/
			
			/*项目单号查询*/
			$("input[name='xmdh']").change(function(){
				$.ajax({
					type : "post",
					data : {
						"xmdh" : $("input[name='xmdh']").val()
	 				},
	 				async : false,
	 				dataType:"json",
	 				url : ctx + "/defaultValue/queryDataToPDCP",
	 				success : function(result) {
	 					var arr = eval(result);
	 					
 						var charter = arr[0].charter;
 						var kxxbg = arr[0].kxxbg;
 						var cpid = arr[0].cpid;
 						var cpidvalue = "";
 						for(var i=0;i<cpid.length;i++){
 							cpidvalue += cpid[i];
 							if(i<cpid.length-1)cpidvalue += ",";
 						}
 						if(charter.length==1&&kxxbg.length==1){
 							var cpdl = eval(charter[0][2]);
	 						var value = "";
	 						for(var key in cpdl){
	 							value = cpdl[key];
	 						}
	 						_this.setFieldValueByFieldName("cpdl",value);
	 						_this.setFieldValueByFieldName("cpxh",charter[0][1]);
	 						_this.setFieldValueByFieldName("xlm",charter[0][0]);
	 						_this.setFieldValueByFieldName("cpms",cpidvalue);

	 						var newRowData = _this.collectSubformData(formId);
	 						
	 						for(var i=0;i<newRowData.length;i++){
	 	 						if(newRowData[i].jcx.indexOf("采用IC方案")>=0){
	 	 							//采用IC方案
//	 	 							var json = {"jg":arr[0][1],"id":rowData[i].id,"uuid":rowData[i].uuid};
//	 								_this.updateRowData(formId, json);
	 	 						}else if(newRowData[i].jcx.indexOf("目标手样提供日期")>=0){//目标手样提供日期
	 	 							var json = {"jg":charter[0][3],"id":newRowData[i].id,"uuid":newRowData[i].uuid};
	 								_this.updateRowData(formId, json);
	 	 						}else if(newRowData[i].jcx.indexOf("目标可量产时间")>=0){//目标可量产时间
	 	 							var json = {"jg":charter[0][4],"id":newRowData[i].id,"uuid":newRowData[i].uuid};
	 								_this.updateRowData(formId, json);
	 	 						}else if(newRowData[i].jcx.indexOf("评估后手样提供时间")>=0){//评估后手样提供时间
	 	 							var json = {"jg":kxxbg[0][0],"id":newRowData[i].id,"uuid":newRowData[i].uuid};
	 								_this.updateRowData(formId, json);
	 	 						}else if(newRowData[i].jcx.indexOf("评估后可量产时间")>=0){//评估后可量产时间
	 	 							var json = {"jg":kxxbg[0][1],"id":newRowData[i].id,"uuid":newRowData[i].uuid};
	 								_this.updateRowData(formId, json);
	 	 						}else if(newRowData[i].jcx.indexOf("目标售价")>=0){//目标售价（$)
	 	 							var json = {"jg":charter[0][5],"id":newRowData[i].id,"uuid":newRowData[i].uuid};
	 								_this.updateRowData(formId, json);
	 	 						}else if(newRowData[i].jcx.indexOf("预计毛利率")>=0){//预计毛利率（%）
	 	 							var json = {"jg":charter[0][6],"id":newRowData[i].id,"uuid":newRowData[i].uuid};
	 								_this.updateRowData(formId, json);
	 	 						}else if(newRowData[i].jcx.indexOf("目标BOM成本")>=0){//目标BOM成本(未税RMB）
	 	 							var json = {"jg":charter[0][7],"id":newRowData[i].id,"uuid":newRowData[i].uuid};
	 								_this.updateRowData(formId, json);
	 	 						}else if(newRowData[i].jcx.indexOf("评估后BOM成本")>=0){//评估后BOM成本（未税RMB)
	 	 							var json = {"jg":kxxbg[0][2],"id":newRowData[i].id,"uuid":newRowData[i].uuid};
	 								_this.updateRowData(formId, json);
	 	 						}
	 	 					}
 						}else{
 							alert("获取相关数据出错!");
 						}
	 				}
				});
			});
		},
		/**
		 * 灯具ADCP审核默认值设置
		 * QLB   20141013
		 */
		uf_leedarson_djadcpshb:function(){
			var _this = this;
			
			var formId = "uf_djadcpshbdtbd";
			if(this.isCreate()){
				var formUuid = _this.getFormUuid(formId); 
				var ctl = _this.getSubformControl(formUuid);
				$.ajax({
	 				type : "post",
	 				data : {
	 					"tableName" : "ldx_default_djjsggs1",
	 					"whereSql" : " formtable='ADCP'"
	 				},
	 				async : false,
	 				url : ctx + "/defaultValue/getDefaultADCP",
	 				success : function(result) {
	 					var arr = eval(result);
	 					
	 					var val = new Array();
	 					for(var i=0;i<arr.length;i++){
	 						val[i]= {"jcx":arr[i][1],"form_uuid":formUuid};
	 					}
	 					ctl.fillFormData(val);
	 				}
				});
				//"form_uuid":"b373bfbf-2a70-494d-a1d4-682d50c12e85","jcx":"1、对该产品的参数信息是否有疑议。或需进一步确认？"
				var bh = $("span[name=bh]").text();
				if(bh == ""){
					$.ajax({
		 				type : "post",
		 				data : {
		 					"noHead" : "F1245",
		 					"formName" : "uf_leedarson_djadcpshb"
		 				},
		 				async : false,
		 				url : ctx + "/pageForValues/getOrderNo",
		 				success : function(result) {
							_this.setFieldValueByFieldName("bh",result);
		 				}
					});
				}
			}	
			
			//根据项目单号获取数据
			$("input[name=xmdh]").change(function(){
				$.ajax({
					type   : 'post',
					data   : {"xmdh" : $("input[name=xmdh]").val()},
					async  : false,
					url    : ctx + "/defaultValue/queryDataToADCP",
					success: function(result){
						var arr = eval(result);   //将返回的结果变成数组
												
						var rowData = _this.collectSubformData(formId);
						var charter = arr[0].charter;
						var cpid = arr[0].cpid;
 						var cpidvalue = "";
 						for(var i=0;i<cpid.length;i++){
 							cpidvalue += cpid[i];
 							if(i<cpid.length-1)cpidvalue += ",";
 						}
						if(charter.length==1){
							//获取产品大类，其返回值是json
							var arr1 = eval(charter[0][2]);
							var temp = "";
							for(var x in arr1){
								temp=arr1[x];
							}
							
							_this.setFieldValueByFieldName("cpdl",temp);          //产品大类
	 						_this.setFieldValueByFieldName("cpxh",charter[0][1]); //产品型号
	 						_this.setFieldValueByFieldName("xlm",charter[0][0]);  //系列名
	 						_this.setFieldValueByFieldName("cpms",cpidvalue);     //产品描述
							
	 						for(var i=0;i<rowData.length;i++){
	 	 						if(rowData[i].seqNO=="7"){	     //目标售价
	 	 							var json = {"zxzk":charter[0][4],"id":rowData[i].id,"uuid":rowData[i].uuid};
	 								_this.updateRowData(formId, json);
	 	 						}else if(rowData[i].seqNO=="8"){//预计毛利率
	 	 							var json = {"zxzk":charter[0][5],"id":rowData[i].id,"uuid":rowData[i].uuid};
	 								_this.updateRowData(formId, json);
	 	 						}else if(rowData[i].seqNO=="9"){//目标BOM成本(未税RMB）
	 	 							var json = {"zxzk":charter[0][6],"id":rowData[i].id,"uuid":rowData[i].uuid};
	 								_this.updateRowData(formId, json);	 	 						
	 	 						}else if(rowData[i].seqNO=="11"){//目标证书取得时间
	 	 							var json = {"zxzk":charter[0][3],"id":rowData[i].id,"uuid":rowData[i].uuid};
	 								_this.updateRowData(formId, json);	 
	 	 						}
	 	 					}
 						}else{
 							alert("获取相关数据出错!");
						}
					}
				});
			});
		},
		
		/**
		 * EP申请单
		 * QLB   20141014
		 */
		uf_leedarson_epsqd:function(){
			_this = this;	
			var formId = "uf_epsqd";  //EP申请单动态表单
			var formUuid = _this.getFormUuid(formId); 
			var ctl = _this.getSubformControl(formUuid);			
			var rowDataUuid;
									
			//添加按钮响应放法
			ctl.bind("afterInsertRow", function(rowdata){
				_this.getControl("cpid",rowdata.id).bind("show",function(){
					$.product.open({
						labelField : 'cpmc',
						valueField : 'cpid',
						afterSelect : function(retVal) {
							getCPID_EP(retVal.id,retVal.name,formId,rowdata,_this);
						}
					});
			    },true);
			},true);	
			
			var bh = $("span[name=bh]").text();
			if(bh == ""){
				$.ajax({
	 				type : "post",
	 				data : {
	 					"noHead" : "D1206",
	 					"formName" : "uf_leedarson_epsqd"
	 				},
	 				async : false,
	 				url : ctx + "/pageForValues/getOrderNo",
	 				success : function(result) {
						_this.setFieldValueByFieldName("bh",result);
	 				}
				});
			}
		},
		
		//客户验货标准解读跟踪清单信息补录
		uf_pbgl_khyhbzjdgzqdbl:function(){
			var _this = this;
			var jdsx = $("#jdsx").val();
			var xh = $("#xh").val();
			var khz = $("#khz").val();
			var ksrq = $("#ksrq").val();
			var wcrq = $("#wcrq").val();
			var jdry = $("#jdry").val();

			_this.setFieldValueByFieldName("xh",xh);
			_this.setFieldValueByFieldName("khz",khz);
			_this.setFieldValueByFieldName("jdry",jdry);
			_this.setFieldValueByFieldName("jdksrq",ksrq);
			_this.setFieldValueByFieldName("jdwcrq",wcrq);
			_this.setFieldValueByFieldName("bzjdsx",jdsx+'天');

			$("select[name='sfxydzxdbz']").change(function(){
				var sel = this;
				var val = $("select[name='sfxydzxdbz']").val();
				if(val=='是'){
					var bzqdrq = $("input[name='bzqdrq']").val();
					var bztjfxrq = $("input[name='bztjfxrq']").val();
					if(bzqdrq!=''&&bztjfxrq!=''){
						$.ajax({
							async : false,
							url : ctx + "/materials/setTime",
							type:"POST",
							dataType:"json",
							data:{
								"qdrq":bzqdrq,
								"fxrq":bztjfxrq
							},
							success : function(res) {
								var arr = res;
								_this.setFieldValueByFieldName("bzzdsx",arr+'天');
							}
						});
					}else{
						$("select[name='sfxydzxdbz']").find("option").eq(0).attr("selected","selected");
						oAlert2("确定日期或发行日期为空，请填入日期后选择是否需要定制/修订标准！");
					}
				}else{
					_this.setFieldValueByFieldName("bzzdsx",$("#jdsx").val()+'天');
				}
			});
		},
	/**
	 * Leedarson_固定资产_委外校准申请单 
	 */
	uf_leedarson_gdzc_wwjzsqd:function(){
		var _this = this;
		
		this.$(".editableClass[name$=___glbh]").live("change", function(e){
			
			var val = $(this).val();
			
			var formId = "uf_leedarson_gdzc_wwjzsjxx";
			var formUuid = _this.getFormUuid(formId); 
			var ctl = _this.getSubformControl(formUuid);
			$.ajax({
				async : false,
				url : ctx + "/lms/search/labfile",
				type:"POST",
				dataType:"json",
				data:{
					"labfileNo":val
				},
				success : function(res) {
					var arr = eval(res);
					if(arr.length==1){
						var dqyf = arr[0].labfileHavedate;
						if(dqyf!=''){
							dqyf = dqyf.substr(0,10);
						}
						var json = {"mc":arr[0].labfileName,"xhgg":arr[0].labfileSpec,"dqyf":dqyf,
								"glbh":arr[0].labfileNo ,"ccbh":arr[0].labfileDefno,"id":ctl.selectedRowId, "uuid":ctl.selectedRowId };
						_this.updateRowData(formId, json);
					}
				}
			});
		});
		// 委外校准申请单成本中心
		this.getControl("cbzx").bind("afterDialogSelect", function(){
			_this.getControl("cbzxfzr").$editableElem.css("ime-mode", "disabled");//禁用输入法
			_this.getControl("cbzxfzr").$editableElem.keydown(function(){ 
				return false;				
			});
			_this.getControl("cbzxdm").$editableElem.css("ime-mode", "disabled");//禁用输入法
			_this.getControl("cbzxdm").$editableElem.keydown(function(){ 
				return false;				
			});
			
			_this.getControl("cbzxfzr").setDisplayAsCtl();
			_this.getControl("cbzxfzr").setReadOnly(false);
			_this.getControl("cbzxdm").setReadOnly(false);
			_this.getControl("cbzxfzr").bind("afterAutoCompleteShow", function(){
				if(_this.getControl("cbzxfzr").$editableElem.next(".autocomplete").find("tr").size() == 1){
					_this.getControl("cbzxfzr").$editableElem.next(".autocomplete").find("tr").first().addClass("highlight");
					//alert(_this.getControl("cbzxfzr").$editableElem.next(".autocomplete").find("tr").size());
					_this.getControl("cbzxfzr").$editableElem.next(".autocomplete").find("tr").first().trigger("click");
				}				
				/*window.setTimeout(function(){
					$autocomplete.find("tr").first().trigger("click");
				}, 600);*/
			});
			_this.getControl("cbzxfzr").$editableElem.trigger("click");
			
			_this.getControl("cbzxfzr").setReadOnly(false);
			//alert(_this.getControl("cbzxfzr").$editableElem.next(".autocomplete").find("tr").size());						
		});
		this.getControl("ztgs").bind("afterDialogSelect",function(e){
			_this.getControl("cbzx").setValue('');	
			_this.getControl("cbzxdm").setValue('');	
			_this.getControl("cbzxfzr").setValue('');	
			_this.getControl("cbzxfzrid").setValue('');	
			_this.getControl("ztdwid").setValue('');	
		});
		
		// 主体归属 
		this.getControl("ztgs").bind("change",function(e){
			_this.getControl("cbzx").setValue('');	
			_this.getControl("cbzxdm").setValue('');	
			_this.getControl("cbzxfzr").setValue('');	
			_this.getControl("cbzxfzrid").setValue('');	
			_this.getControl("ztdwid").setValue('');	
		});
	},
	
	// 固定资产，计量器具维修单
	uf_leedarson_gdzc_jlqjwxd:function(){
		var _this = this;
		var glbhC =_this.getControl("glbh");
		_this.$("input[name='"+glbhC.getCtlName()+"']").change(function(){
			$.ajax({
				async : false,
				url : ctx + "/lms/search/labfile",
				type:"POST",
				dataType:"json",
				data:{
					"labfileNo":$(this).val()
				},
				success : function(res) {
					var arr = eval(res);
					if(arr.length==1){
						_this.setFieldValueByFieldName("jlqjmc",arr[0].labfileName);
						_this.setFieldValueByFieldName("xhgg",arr[0].labfileSpec);
						_this.setFieldValueByFieldName("syry",arr[0].labfileClaim);
						
					}
				}
			});
		});
		// 计量器具维修单申请中心
		this.getControl("cbzxmc").bind("afterDialogSelect", function(){
			_this.getControl("cbzxfzr").$editableElem.css("ime-mode", "disabled");//禁用输入法
			_this.getControl("cbzxfzr").$editableElem.keydown(function(){ 
				return false;
			});
			_this.getControl("cbzxdm").$editableElem.css("ime-mode", "disabled");//禁用输入法
			_this.getControl("cbzxdm").$editableElem.keydown(function(){ 
				return false;				
			});
			_this.getControl("cbzxfzr").setDisplayAsCtl();
			_this.getControl("cbzxfzr").setReadOnly(false);
			_this.getControl("cbzxdm").setReadOnly(false);
			_this.getControl("cbzxfzr").bind("afterAutoCompleteShow", function(){
				if(_this.getControl("cbzxfzr").$editableElem.next(".autocomplete").find("tr").size() == 1){
					_this.getControl("cbzxfzr").$editableElem.next(".autocomplete").find("tr").first().addClass("highlight");
					//alert(_this.getControl("cbzxfzr").$editableElem.next(".autocomplete").find("tr").size());
					_this.getControl("cbzxfzr").$editableElem.next(".autocomplete").find("tr").first().trigger("click");
				}
				
				/*window.setTimeout(function(){
					$autocomplete.find("tr").first().trigger("click");
				}, 600);*/
			});
			_this.getControl("cbzxfzr").$editableElem.trigger("click");	
		});
		
		this.getControl("ztdw").bind("afterDialogSelect",function(e){
			_this.getControl("cbzxmc").setValue('');	
			_this.getControl("cbzxfzr").setValue('');	
			_this.getControl("cbzxdm").setValue('');	
			_this.getControl("cbzxfzrid").setValue('');	
			_this.getControl("ztdwid").setValue('');	
		});
		
		// 主体归属 
		this.getControl("ztdw").bind("change",function(e){
			_this.getControl("cbzxmc").setValue('');	
			_this.getControl("cbzxfzr").setValue('');	
			_this.getControl("cbzxdm").setValue('');	
			_this.getControl("cbzxfzrid").setValue('');	
			_this.getControl("ztdwid").setValue('');	
		});
	},
	
	// 外部委托测试报价单
	uf_pbgl_wbwtcsbjd:function(){
		var _this = this;
		this.$(".editableClass[name$=___fy]").live("change", function(e){
			// 合计金额 
			var hj = _this.getFieldValueByFieldName("hj");
			_this.setFieldValueByFieldName("dx1",uppercaseMoney(hj));
			// 税额
			var se = Math.round(hj*6)/100;
			_this.setFieldValueByFieldName("se",se);
			_this.setFieldValueByFieldName("dx2",uppercaseMoney(se));
			// 价税合计
			var jshj = hj + se;
			_this.setFieldValueByFieldName("jshj",jshj);
			_this.setFieldValueByFieldName("dx3",uppercaseMoney(jshj));
		});
	},

	//材料开发申请单：是否送检
		 uf_ldx_clkfgl_clkfsqd:function(){
			  
				var _this = this;
				
				this.$("input[name='wlid']").click(function(){	
					var inspect2 = _this.getFieldValueByFieldName("wlid"); 
					if(inspect2 == ""){
						$("input[name=sfsj]:eq(0)").attr("checked","true");
					}else{ 
						$("input[name=sfsj]:eq(1)").attr("checked","true");
					}
				});
				
				
				var control2 = this.getControl("wlid");
				//获取控件的点击事件
				
				control2.bind("afterDialogSelect",function(temp_this,paramsId, paramsObj,jsonObj){
					var _temp_this = temp_this;//点击的对象
					var _ctlName = paramsId;//控件名称
					var _data = paramsObj;//控件返回数据
					var _temp = jsonObj;
					//点击变否
					$("input[name=sfsj]:eq(1)").attr("checked","true");
					
					  var dataUuid = _this.getFieldValueByFieldName("wlid");
					if(typeof dataUuid != "undefined" && $.trim(dataUuid).length != 0){ 
						$.ajax({
							async : false,
							url : ctx + "/dyformatl/getProductInfo",
							type:"POST",
							dataType:"json",
							data:{
								"formUuid":_this.getFormUuid(),
								"wlId":'00000000'+control2.getValue()
							},
							success : function(res) {
								var arr = res.data;
								_this.setFieldValueByFieldName("cpmc",arr.cPMC);
								_this.setFieldValueByFieldName("cpID",arr.cPID);
								_this.setFieldValueByFieldName("ICgy",arr.iCGY);
								_this.setFieldValueByFieldName("srjxh",arr.sRJXH);
							},
						});	
					};
					$.ajax({
						type : "post",
						data : {
							"dataSourceId":"SAP",
							"sql" : "select mstae from mara where matnr='00000000"+dataUuid+"'"
						},
						async : false,
						url : ctx + "/special/dyform/sap",
						success : function(result) {
							var arr = eval(result);
							if(arr.length==1){
								_this.setFieldValueByFieldName("IDzt",arr[0].mSTAE);
							}
						}
					});
				});
		 },
		
		 /*
		  * 客户索赔费用评估表更改
		  */
			uf_leedarson_pbgl_khspfypgb:function(){
			 //alert(69);
				    var _this = this;
			
				   _this.$(".editableClass[name$=___xsddh],.editableClass[name$=___hxmh]").live("change", function(e){
					
					var formId = "uf_pbgl_khspfypgb_dd";
					var formUuid = _this.getFormUuid(formId); 
					var ctl = _this.getSubformControl(formUuid);
					var rowData = ctl.getRowData(ctl.selectedRowId);
					
					
					var kdauf = rowData.xsddh;
					var kdpos = rowData.hxmh;
					$.ajax({
						type : "post",
						data : {
							"dataSourceId":"SAP",
							"sql" : "select distinct  a.dwerk, a.matnr, b.maktx, " +
									" (case when h.vprsv ='S' then h.stprs/h.peinh  when h.vprsv='V'" +
									" then h.verpr/h.peinh end ) vprsv ,(f.kwmeng/f.netwr) kwmeng ," +
									"f.waerk from AFPO a inner join MAKT b on  a.matnr = b.matnr " +
									"  inner join VBAP f on a.kdauf=f.vbeln " +
									"and a.kdpos=f.posnr and a.kdauf=f.vbeln  inner join VBAK g on a.kdauf=g.vbeln " +
									"inner join EBEWH h on f.matnr=h.matnr and a.kdauf=g.vbeln where a.kdauf = " +
									"'"+kdauf+"' and a.kdpos= '"+kdpos+"'"
								
						},
						async : false,
						url : ctx + "/special/dyform/sap",
						success : function(result) {
							
							var arr = eval('(' + result + ')');
							if(arr.length==1){
								var json = {"cpid":arr[0].mATNR,"scgc":arr[0].dWERK,"cpmc":arr[0].mAKTX,
										"chrq":arr[0].wadatIst,"chsl":arr[0].lFIMG,
										"bb":arr[0].wAERK,"dwcbjg":arr[0].vPRSV, "dj":arr[0].kWMENG,
										"id":ctl.selectedRowId, "uuid":ctl.selectedRowId};
								_this.updateRowData(formId, json);
							}
						}
					});
					var kdauf = rowData.xsddh;
					var kdpos = rowData.hxmh;
					$.ajax({
						type : "post",
						data : {
							"dataSourceId":"SAP",
							"sql" :"select sum(b.lfimg)lfimg  from lips b inner join AFPO a on " +
									" a.kdauf=b.vgbel inner join LIKP c on b.vbeln=c.vbeln where " +
									"a.kdauf ='"+kdauf+"' and a.kdpos= '"+kdpos+"'"
								
						},
						async : false,
						url : ctx + "/special/dyform/sap",
						success : function(res) {
							
							var arr1 = eval('(' + res + ')');
							if(arr1.length==1){
								var json = {"chsl":arr1[0].lFIMG,
										"id":ctl.selectedRowId, "uuid":ctl.selectedRowId};
								_this.updateRowData(formId, json);
							}
						}
					});
			
					var kdauf = rowData.xsddh;
					var kdpos = rowData.hxmh;
					$.ajax({
						type : "post",
						data : {
							"dataSourceId":"SAP",
							"sql" :"select  listagg(e.gstrp ,',') within group(order by e.gstrp ) gSTRP, listagg(a.wadat_ist,',') " +
									"within group(order by a.wadat_ist) wADARIST " +
									"from LIKP a inner join LIPS b on b.vbeln=a.vbeln inner join AFPO c on " +
									"c.kdauf=b.vgbel and c.kdpos=b.vgpos inner join AFKO e on c.aufnr=e.aufnr " +
									"where c.kdauf ='"+kdauf+"' and c.kdpos= '"+kdpos+"'"
								
						},
						async : false,
						url : ctx + "/special/dyform/sap",
						success : function(re) {
							var arr2 = eval('(' + re + ')');
							if(arr2.length==1){
								var json = {"chrq":arr2[0].wADARIST,"scrq":arr2[0].gSTRP,
										"id":ctl.selectedRowId, "uuid":ctl.selectedRowId};
								_this.updateRowData(formId, json);
							}
						}
					});
			
				});
			},
		

	/**
	 *办件申请单
	 */
	uf_xzsp_bjsq:function(){

	},
	
	/**
	 * LDX_生产管理_出差申请单(长泰)
	 */
	uf_scgl_ccsqd:function(){
		var _this = this;
		//---------------------------------------------------------------------------------
		//表单onload时，设置默认值
		var userInfo = SpringSecurityUtils.getUserDetails();
		var val = _this.getFieldValueByFieldName("WERKS");
		if(typeof val != 'undefined' && $.trim(val).length > 0){
			//return;
		}else{
			this.setFieldValueByFieldName("ygbh",userInfo.employeeNumber);
			this.setFieldValueByFieldName("xm",userInfo.userName);
		}
			
		var loadLeve = function(jsonobj){					
			if(jsonobj==null){
				alert("没有找到对应的数据");
				return;
			}
			
			var pt = "PT_BAPIHRS0001";
			if(jsonobj[pt].rows==="0"){
				//alert("没有找到对应的数据");
				return;
			}
			var data = jsonobj[pt].row[0];
			if(typeof val != 'undefined' && $.trim(val).length > 0){
				 
				return;
			}
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("WERKS",data.WERKS);
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("BTRTL",data.BTRTL);
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("SHORT3",data.SHORT3);
			//姓名
			_this.setFieldValueByFieldName("xm",data.ENAME);
			//部门
			_this.setFieldValueByFieldName("bm",data.ORGTX);
			//职位
			_this.setFieldValueByFieldName("zw",data.PLSTX);
			//员工子组
			_this.setFieldValueByFieldName("ygzz",data.PTEXT);
			
		};
		getHR_ZHRI001004(_this.getFieldValueByFieldName("ygbh"),loadLeve);
		
		//员工编号文本框
		this.$("input[name='ygbh']").click(function(){
			$.unit.open({
				title : "选择人员",
				labelField : "xm",
				valueField : "ygbh",
				type : "MyDept",
				multiple:false, 
				afterSelect : function(retVal) {
					_this.setFieldValueByFieldName("ygbh",retVal.employeeNumber);
					_this.setFieldValueByFieldName("xm",retVal.name);							
					getHR_ZHRI001004(_this.getFieldValueByFieldName("ygbh"),loadLeve);
				},
				selectType : 4	
			});
		});
		
		//员工编号文本框
		this.$("input[name='ygbh']").focus(function(){
			this.blur();
		});	
		//------------------------------------------------------------------------------------
		
		var formId = "uf_scgl_ccxx";
		var formUuid = _this.getFormUuid(formId); 
		var ctl = _this.getSubformControl(formUuid);
		//var hNo = $("#No").val();
		var rowDataUuid;
		//添加按钮响应放法
		ctl.bind("afterInsertRow", function(rowdata){
			//---------------------------------------------
			
					var updateRow = function(jsonobj){
						if(jsonobj==null){
							alert("没有找到对应的数据");
							return;
						}
						
						var pt = "RETURN_INFO";
						var data = jsonobj[pt];						
						var rowData = ctl.getRowData(ctl.selectedRowId);						
						rowData.ygbh = data.PERNR;
						rowData.xm = '{"":"'+data.ENAME+'"}';
						rowData.gw = data.PLSTX;
						rowData.bm = '{"":"'+data.ORGTX+'"}';
						
						_this.updateRowData(formId, rowData);
					};
					
					_this.getControl("ygbh", rowdata.id ).bind("focus", function(){
						$.unit.open({
							title : "选择人员",
							type : "MyDept",
							multiple:false, 
							afterSelect : function(retVal) {							
								getHR_YGLDHTWHJK(retVal.employeeNumber,"","",updateRow);
							},
							selectType : 4	
						});
						
					});
				
			//-------------------------------------------------
			
			var _rowDate = rowdata;
			rowDataUuid = rowdata.id;
			
			var controlNkssj = _this.getControl("nkssj",rowDataUuid);
			var controlNjssj = _this.getControl("njssj",rowDataUuid);
			var ygbh = _this.getControl("ygbh",rowDataUuid);
			controlNkssj.bind("afterSetValue",function(){
				var rowData = ctl.getRowData(ctl.selectedRowId);
				if(rowData.nkssj != "" && rowData.njssj != "" && rowData.ygbh != ""){
					$.ajax({
						async : true,
						url : ctx + "/ehr/validate?njssj=" + rowData.njssj + "&nkssj=" 
						+ rowData.nkssj + "&ygbh=" + rowData.ygbh + "&psCode=" + "1" + "&psBigType=" + ""
						+ "&psType=" + "12",
						success:function(data) {
							var arr = eval(data);
							if(arr[0] != "ok"){
								oAlert(arr[0]);
							}else{
								var json = {"ygbh":rowData.ygbh,
										"ts":arr[1],"xss":arr[2],
										"id":ctl.selectedRowId, 
										"uuid":ctl.selectedRowId};
								_this.updateRowData(formId, json);
							}
							
						}
					});
					
				}
				
			}, true);
			
			controlNjssj.bind("afterSetValue",function(){
				var rowData = ctl.getRowData(ctl.selectedRowId);
				if(rowData.nkssj != "" && rowData.njssj != "" && rowData.ygbh != ""){
					$.ajax({
						async : true,
						url : ctx + "/ehr/validate?njssj=" + rowData.njssj + "&nkssj="
						+ rowData.nkssj + "&ygbh=" + rowData.ygbh + "&psCode=" + "1" + "&psBigType=" + ""
						+ "&psType=" + "12",
						success:function(data) {
							var arr = eval(data);
							if(arr[0] != "ok"){
								oAlert(arr[0]);
							}else{
								var json = {"ygbh":rowData.ygbh,
										"ts":arr[1],"xss":arr[2],
										"id":ctl.selectedRowId, 
										"uuid":ctl.selectedRowId};
								_this.updateRowData(formId, json);
							}
						}
					});
					
				}
				
			}, true);
			
			ygbh.bind("change",function(){
				var rowData = ctl.getRowData(ctl.selectedRowId);
				if(rowData.nkssj != "" && rowData.njssj != "" && rowData.ygbh != ""){
					$.ajax({
						async : true,
						url : ctx + "/ehr/validate?njssj=" + rowData.njssj + "&nkssj="
						+ rowData.nkssj + "&ygbh=" + rowData.ygbh + "&psCode=" + "1" + "&psBigType=" + ""
						+ "&psType=" + "12",
						success:function(data) {
							var arr = eval(data);
							if(arr[0] != "ok"){
								oAlert(arr[0]);
							}else{
								var json = {"ygbh":rowData.ygbh,
										"ts":arr[1],"xss":arr[2],
										"id":ctl.selectedRowId, 
										"uuid":ctl.selectedRowId};
								_this.updateRowData(formId, json);
							}
						}
					});
					
				}
			});
			
				
		});
			
		
	},
	
	
	/**
	 * LDX_生产管理_加班申请单(长泰) (1.0)
	 */
	uf_scgl_jbsqd:function(){
		//====================================================================
		
		_this = this;	
		
		//表单onload时，设置默认值
		var userInfo = SpringSecurityUtils.getUserDetails();
		var val = _this.getFieldValueByFieldName("WERKS");
		if(typeof val != 'undefined' && $.trim(val).length > 0){
			//return;
		}else{
			this.setFieldValueByFieldName("ygbh",userInfo.employeeNumber);
			this.setFieldValueByFieldName("xm",userInfo.userName);
		}
		
		var loadLeve = function(jsonobj){					
			if(jsonobj==null){
				alert("没有找到对应的数据");
				return;
			}
			
			var pt = "PT_BAPIHRS0001";
			if(jsonobj[pt].rows==="0"){
				alert("没有找到对应的数据");
				return;
			}
			var data = jsonobj[pt].row[0];
			
			if(typeof val != 'undefined' && $.trim(val).length > 0){
				 
				return;
			}
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("WERKS",data.WERKS);
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("BTRTL",data.BTRTL);
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("SHORT3",data.SHORT3);
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("BTRTX",data.SHORT3);
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("NAME1",data.SHORT3);					
			//姓名
			_this.setFieldValueByFieldName("xm",data.ENAME);
			//部门
			_this.setFieldValueByFieldName("bm",data.ORGTX);
			//职位
			_this.setFieldValueByFieldName("zw",data.PLSTX);
			//员工子组
			_this.setFieldValueByFieldName("ygzz",data.PTEXT);
			
		};
		getHR_ZHRI001004(_this.getFieldValueByFieldName("ygbh"),loadLeve);
		
		//员工编号文本框
		this.$("input[name='ygbh']").click(function(){
			$.unit.open({
				title : "选择人员",
				labelField : "xm",
				valueField : "ygbh",
				type : "MyDept",
				multiple:false, 
				afterSelect : function(retVal) {
					_this.setFieldValueByFieldName("ygbh",retVal.employeeNumber);
					_this.setFieldValueByFieldName("xm",retVal.name);							
					getHR_ZHRI001004(_this.getFieldValueByFieldName("ygbh"),loadLeve);
				},
				selectType : 4	
			});
		});
		
		//员工编号文本框
		this.$("input[name='ygbh']").focus(function(){
			this.blur();
		});	
		
		//=====================================================================
		
		var formId = "uf_scgl_jbxx";//加班申请单动态表格uf_scgl_jbxx
		var formUuid = _this.getFormUuid(formId); 
		var ctl = _this.getSubformControl(formUuid);
		//var hNo = $("#No").val();
		var rowDataUuid;
		//添加按钮响应放法
		ctl.bind("afterInsertRow", function(rowdata){
			
			//====================================================
			var updateRow = function(jsonobj){
				if(jsonobj==null){
					alert("没有找到对应的数据");
					return;
				}
				
				var pt = "RETURN_INFO";
				var data = jsonobj[pt];						
				var rowData = ctl.getRowData(ctl.selectedRowId);						
				rowData.ygbh = data.PERNR;
				rowData.xm = data.ENAME;
				rowData.gw = data.PLSTX;
				rowData.bm = data.ORGTX;
				
				_this.updateRowData(formId, rowData);
			};
			
			_this.getControl("ygbh", rowdata.id ).bind("focus", function(){
				$.unit.open({
					title : "选择人员",
					type : "MyDept",
					multiple:false, 
					afterSelect : function(retVal) {							
						getHR_YGLDHTWHJK(retVal.employeeNumber,"","",updateRow);
					},
					selectType : 4	
				});
				
			});
			
			//====================================================
			
			var _rowDate = rowdata;
			rowDataUuid = rowdata.id;
			var controlNkssj = _this.getControl("jhjbkssj",rowDataUuid);//计划加班开始时间
			var controlNjssj = _this.getControl("jhjbjssj",rowDataUuid);//计划加班结束时间
			var ygbh = _this.getControl("ygbh",rowDataUuid);//员工编号
			var jblx = _this.getControl("jblx",rowDataUuid);//加班类型
			controlNkssj.bind("afterSetValue",function(){
				var rowData = ctl.getRowData(ctl.selectedRowId);
				
				var temp = ""; 
				var type_qj = eval('(' +rowData.jblx + ')');
				
				for(var x in type_qj){
					temp = x;
				}
				
				if(rowData.jhjbkssj != "" && rowData.jhjbjssj != "" && rowData.ygbh != "" && typeof temp != "undefined"){
					$.ajax({
						async : true,
						url : ctx + "/ehr/validate?nkssj=" + rowData.jhjbkssj
						+ "&njssj=" + rowData.jhjbjssj + "&ygbh=" + rowData.ygbh
						+ "&psCode=" + "0" + "&psBigType=" + ""
						+ "&psType=" + temp,
						success:function(data) {
							var arr = eval(data);
							if(arr[0] != "ok"){
								oAlert(arr[0]);
							}else{
								var json = {"ygbh":rowData.ygbh,
										"xss":arr[2],
										"id":ctl.selectedRowId, 
										"uuid":ctl.selectedRowId};
								_this.updateRowData(formId, json);
							}
							
						}
					});
					
				}
				
			}, true);
			
			controlNjssj.bind("afterSetValue",function(){
				var rowData = ctl.getRowData(ctl.selectedRowId);
				
				var temp; 
				var type_qj = eval('(' +rowData.jblx + ')');
				
				for(var x in type_qj){
					temp = x;
				}
				if(rowData.jhjbkssj != "" && rowData.jhjbjssj != "" && rowData.ygbh != ""  && typeof temp != "undefined"){
					$.ajax({
						async : true,
						url : ctx + "/ehr/validate?nkssj=" + rowData.jhjbkssj + "&njssj="
						+ rowData.jhjbjssj + "&ygbh=" + rowData.ygbh + "&psCode=" + "0" + "&psBigType=" + ""
						+ "&psType=" + temp,
						success:function(data) {
							var arr = eval(data);
							if(arr[0] != "ok"){
								oAlert(arr[0]);
							}else{
								var json = {"ygbh":rowData.ygbh,
										"xss":arr[2],
										"id":ctl.selectedRowId, 
										"uuid":ctl.selectedRowId};
								_this.updateRowData(formId, json);
							}
							
						}
					});
					
				}
				
			}, true);
			
			ygbh.bind("change",function(){
				var rowData = ctl.getRowData(ctl.selectedRowId);
				
				var temp; 
				var type_qj = eval('(' +rowData.jblx + ')');
				
				for(var x in type_qj){
					temp = x;
				}
				
				if(rowData.jhjbkssj != "" && rowData.jhjbjssj != "" && rowData.ygbh != ""  && typeof temp != "undefined"){
					$.ajax({
						async : true,
						url : ctx + "/ehr/validate?njssj=" + rowData.jhjbjssj + "&nkssj="
						+ rowData.jhjbkssj + "&ygbh=" + rowData.ygbh + "&psCode=" + "0" + "&psBigType=" + ""
						+ "&psType=" + temp,
						success:function(data) {
							var arr = eval(data);
							if(arr[0] != "ok"){
								oAlert(arr[0]);
							}else{
								var json = {"ygbh":rowData.ygbh,
										"xss":arr[2],
										"id":ctl.selectedRowId, 
										"uuid":ctl.selectedRowId};
								_this.updateRowData(formId, json);
							}
						}
					});
					
				}
			});
			
			jblx.bind("change",function(){
				var rowData = ctl.getRowData(ctl.selectedRowId);
				var temp; 
				var type_qj = eval('(' +rowData.jblx + ')');
				
				for(var x in type_qj){
					temp = x;
				}
				
				if(rowData.jhjbkssj != "" && rowData.jhjbjssj != "" && rowData.ygbh != "" && typeof temp != "undefined"){
					$.ajax({
						async : true,
						url : ctx + "/ehr/validate?njssj=" + rowData.jhjbjssj + "&nkssj="
						+ rowData.jhjbkssj + "&ygbh=" + rowData.ygbh + "&psCode=" + "0" + "&psBigType=" + ""
						+ "&psType=" + temp,
						success:function(data) {
							var arr = eval(data);
							if(arr[0] != "ok"){
								oAlert(arr[0]);
							}else{
								var json = {"ygbh":rowData.ygbh,
										"xss":arr[2],
										"id":ctl.selectedRowId, 
										"uuid":ctl.selectedRowId};
								_this.updateRowData(formId, json);
							}
						}
					});
					
				}
				
			});
			
		});
	},
	
	/**
	 * LDX_生产管理_公出申请单(长泰)
	 */
	uf_scgl_gcsqd:function(){//	uf_scgl_gcsqd
		
		//====================================================================
		
		_this = this;
		
		//表单onload时，设置默认值
		var userInfo = SpringSecurityUtils.getUserDetails();
		var val = _this.getFieldValueByFieldName("WERKS");
		if(typeof val != 'undefined' && $.trim(val).length > 0){
			//return;
		}else{
			this.setFieldValueByFieldName("ygbh",userInfo.employeeNumber);
			this.setFieldValueByFieldName("xm",userInfo.userName);
		}
			
		var loadLeve = function(jsonobj){					
			if(jsonobj==null){
				alert("没有找到对应的数据");
				return;
			}
			
			var pt = "PT_BAPIHRS0001";
			if(jsonobj[pt].rows==="0"){
				alert("没有找到对应的数据");
				return;
			}
			var data = jsonobj[pt].row[0];
			if(typeof val != 'undefined' && $.trim(val).length > 0){
				 
				return;
			}
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("WERKS",data.WERKS);
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("BTRTL",data.BTRTL);
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("SHORT3",data.SHORT3);
			//姓名
			_this.setFieldValueByFieldName("ygxm",data.ENAME);
			//部门
			_this.setFieldValueByFieldName("bm",data.ORGTX);
			//岗位
			_this.setFieldValueByFieldName("zw",data.PLSTX);
			//员工子组
			_this.setFieldValueByFieldName("ygzz",data.PTEXT);			
			
		};
		getHR_ZHRI001004(_this.getFieldValueByFieldName("ygbh"),loadLeve);
		
		//员工工号文本框
		this.$("input[name='ygbh']").click(function(){
			$.unit.open({
				title : "选择人员",
				labelField : "ygxm",
				valueField : "ygbh",
				type : "MyDept",
				multiple:false, 
				afterSelect : function(retVal) {
					_this.setFieldValueByFieldName("ygbh",retVal.employeeNumber);
					_this.setFieldValueByFieldName("xm",retVal.name);							
					getHR_ZHRI001004(_this.getFieldValueByFieldName("ygbh"),loadLeve);
				},
				selectType : 4	
			});
		});
		
		//员工工号文本框
		this.$("input[name='ygbh']").focus(function(){
			this.blur();
		});		
		
		//======================================================================
		
		var controlNkssj = _this.getControl("nkssj");//计划加班开始时间
		var controlNjssj = _this.getControl("njssj");//计划加班结束时间
		var ygbh = _this.getControl("ygbh");//员工编号
		controlNkssj.bind("afterSetValue",function(){
			//var rowData = ctl.getRowData(ctl.selectedRowId);
			var _nkssj = controlNkssj.getValue("nkssj");
			var _njssj = controlNjssj.getValue("njssj");
			var _ygbh = ygbh.getValue("ygbh");
			if(_nkssj != ""&&_njssj != "" && _ygbh != ""){
				$.ajax({
					async : true,
					url : ctx + "/ehr/validate?nkssj=" + _nkssj
					+ "&njssj=" + _njssj + "&ygbh=" + _ygbh
					+ "&psCode=" + "1" + "&psBigType=" + ""
					+ "&psType=" + "13",
					success:function(data) {
						var arr = eval(data);
						if(arr[0] != "ok"){
							oAlert(arr[0]);
						}else{
							var tsControl = _this.getControl("ts");//天数
							var xssControl = _this.getControl("xss");//小时数
							tsControl.setValue(arr[1]);
							xssControl.setValue(arr[2]);
						}
						
					}
				});
				
			}
			
		}, true);
		
		controlNjssj.bind("afterSetValue",function(){
			var _nkssj = controlNkssj.getValue("nkssj");
			var _njssj = controlNjssj.getValue("njssj");
			var _ygbh = ygbh.getValue("ygbh");
			if(_nkssj != ""&&_njssj != "" && _ygbh != ""){
				$.ajax({
					async : true,
					url : ctx + "/ehr/validate?nkssj=" + _nkssj + "&njssj="
					+ _njssj + "&ygbh=" + _ygbh + "&psCode=" + "1" + "&psBigType=" + ""
					+ "&psType=" + "13",
					success:function(data) {
						var arr = eval(data);
						if(arr[0] != "ok"){
							oAlert(arr[0]);
						}else{
							var tsControl = _this.getControl("ts");//天数
							var xssControl = _this.getControl("xss");//小时数
							tsControl.setValue(arr[1]);
							xssControl.setValue(arr[2]);
						}
						
					}
				});
			}
		}, true);
		
	},
	
	/**
	 * 请销假申请单（长泰）	userform_scgl_qjsqd/userform_scgl_qjsqd
	 */
	userform_scgl_qjsqd:function(){
		
		//===============================================================================

		_this = this;
		
		//表单onload时，设置默认值
		var userInfo = SpringSecurityUtils.getUserDetails();
		this.setFieldValueByFieldName("gh",userInfo.employeeNumber);
		this.setFieldValueByFieldName("qjr",userInfo.userName);
		
		var loadLeve = function(jsonobj){					
			if(jsonobj==null){
				alert("没有找到对应的数据");
				return;
			}
			
			var pt = "PT_BAPIHRS0001";
			if(jsonobj[pt].rows==="0"){
				alert("没有找到对应的数据");
				return;
			}
			var data = jsonobj[pt].row[0];
								
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("WERKS",data.WERKS);					
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("PERSK",data.PERSK);
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("BTRTL",data.BTRTL);
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("SHORT3",data.SHORT3);
			//姓名
			_this.setFieldValueByFieldName("qjr",data.ENAME);
			//部门
			_this.setFieldValueByFieldName("bm",data.ORGTX);
			//岗位
			_this.setFieldValueByFieldName("qjrzw",data.PLSTX);	
			//人事子范围
			_this.setFieldValueByFieldName("Range",data.BTRTX);
			
		};
		getHR_ZHRI001004(_this.getFieldValueByFieldName("gh"),loadLeve);
		
		//员工工号文本框
		this.$("input[name='gh']").click(function(){
			$.unit.open({
				title : "选择人员",
				labelField : "ygxm",
				valueField : "ygbh",
				type : "MyDept",
				multiple:false, 
				afterSelect : function(retVal) {
					_this.setFieldValueByFieldName("gh",retVal.employeeNumber);
					_this.setFieldValueByFieldName("qjr",retVal.name);							
					getHR_ZHRI001004(_this.getFieldValueByFieldName("gh"),loadLeve);
				},
				selectType : 4	
			});
		});
		
		//员工工号文本框
		this.$("input[name='gh']").focus(function(){
			this.blur();
		});	
		
		//===============================================================================
		
		var formId = "userform_scgl_qjxx";
		var formUuid = _this.getFormUuid(formId); 
		var ctl = _this.getSubformControl(formUuid);
		//var hNo = $("#No").val();
		var rowDataUuid;
		
		var txdecxValue = 0.0;
		
		//调休定额 =============================start=======================================
		//调休定额按钮查询
		var controlGhValue = _this.getControl("gh").getValue();//工号值
		$.ajax({
			async : true,
			url : ctx + "/ehr/txdecx?controlGhValue=" + controlGhValue,
			success:function(data) {
				var arr = eval(data);
				txdecxValue = parseFloat(arr);
				_this.getControl("txdecx").setValue(arr);//工号值
			}
		});
		this.$("#ehrtxdecx").click(function(){
			//调休定额按钮查询
			var controlGhValue2 = _this.getControl("gh").getValue();//工号值
			$.ajax({
				async : true,
				url : ctx + "/ehr/txdecx?controlGhValue=" + controlGhValue2,
				success:function(data) {
					var arr = eval(data);
					txdecxValue = parseFloat(arr);
					_this.getControl("txdecx").setValue(arr);//工号值
				}
			});
		});
		
		//调休定额 =============================start=======================================
		
		
		//添加按钮响应放法
		ctl.bind("afterInsertRow", function(rowdata){
			var _rowDate = rowdata;
			rowDataUuid = rowdata.id;
			
			var controlNkssj = _this.getControl("StartTime",rowDataUuid);
			var controlNjssj = _this.getControl("EndTime",rowDataUuid);
			var ygbh=_this.getFieldValueByFieldName("gh");
			controlNkssj.bind("afterSetValue",function(){
				var rowData = ctl.getRowData(ctl.selectedRowId);
				
				//Type_QJ: "{"1":"事假"}"  '(' + re + ')'
				var temp; 
				var type_qj = eval('(' +rowData.Type_QJ + ')');
				
				for(var x in type_qj){
					temp = x;
				}
				if(rowData.StartTime != "" && rowData.EndTime != "" && rowData.gh != ""){
					$.ajax({
						async : true,
						url : ctx + "/ehr/validate?njssj=" + rowData.EndTime + "&nkssj=" 
						+ rowData.StartTime + "&ygbh=" + ygbh + "&psCode=" + "1" + "&psBigType=" + ""
						+ "&psType=" + temp,
						success:function(data) {
							var arr = eval(data);
							if(arr[0] != "ok"){
								oAlert(arr[0]);
							}else{
								var json = {
										"TS":arr[1],"XSS":arr[2],
										"id":ctl.selectedRowId, 
										"uuid":ctl.selectedRowId};
								_this.updateRowData(formId, json);
								
								//计算总天数和总小时数
								var allSubFormData = _this.collectSubformData("userform_scgl_qjxx");
								if(allSubFormData != null){
									var totalDay = 0;//天数
									var totalHour = 0;//小时数
									var totalTXJHour = 0;//调休假小时数
									for(var i=0;i<allSubFormData.length;i++){
										var temp = allSubFormData[i];
										var vacationType = eval('(' +temp.Type_QJ + ')');
										var vacationTypeRel = vacationType["11"];//获得的值为事假，或调休假等
										if(vacationTypeRel == "调休假"){
											totalTXJHour = totalTXJHour + parseFloat(temp.XSS);
										}
										totalDay = totalDay + parseFloat(temp.TS);
										totalHour = totalHour + parseFloat(temp.XSS);
									}
									_this.getControl("qjts").setValue(totalDay);
									_this.getControl("qjxss").setValue(totalHour);
									if(txdecxValue < totalTXJHour){
										oAlert("请假小时数大于调休定额数！");
									}
								}
							}
							
						}
					});
					
				}
				
			}, true);
			
			controlNjssj.bind("afterSetValue",function(){
				var rowData = ctl.getRowData(ctl.selectedRowId);
				var temp; 
				var type_qj = eval('(' +rowData.Type_QJ + ')');
				
				for(var x in type_qj){
					temp = x;
				}
				if(rowData.StartTime != "" && rowData.EndTime != "" && rowData.gh != ""){
					$.ajax({
						async : true,
						url : ctx + "/ehr/validate?njssj=" + rowData.EndTime + "&nkssj=" 
						+ rowData.StartTime + "&ygbh=" + ygbh + "&psCode=" + "1" + "&psBigType=" + ""
						+ "&psType=" + temp,
						success:function(data) {
							var arr = eval(data);
							if(arr[0] != "ok"){
								oAlert(arr[0]);
							}else{
								var json = {
										"TS":arr[1],"XSS":arr[2],
										"id":ctl.selectedRowId, 
										"uuid":ctl.selectedRowId};
								_this.updateRowData(formId, json);
								
								//计算总天数和总小时数
								var allSubFormData = _this.collectSubformData("userform_scgl_qjxx");
								if(allSubFormData != null){
									var totalDay = 0;//天数
									var totalHour = 0;//小时数
									var totalTXJHour = 0;//调休假小时数
									for(var i=0;i<allSubFormData.length;i++){
										var temp = allSubFormData[i];
										var vacationType = eval('(' +temp.Type_QJ + ')');
										var vacationTypeRel = vacationType["11"];//获得的值为事假，或调休假等
										if(vacationTypeRel == "调休假"){
											totalTXJHour = totalTXJHour + parseFloat(temp.XSS);
										}
										totalDay = totalDay + parseFloat(temp.TS);
										totalHour = totalHour + parseFloat(temp.XSS);
									}
									_this.getControl("qjts").setValue(totalDay);
									_this.getControl("qjxss").setValue(totalHour);
									if(txdecxValue < totalTXJHour){
										oAlert("请假小时数大于调休定额数！");
									}
								}
							}
						}
					});
					
				}
				
			}, true);
			var ygbh = _this.getControl("gh");//员工编号 add by HeShi 20141018
			ygbh.bind("change",function(){
				var rowData = ctl.getRowData(ctl.selectedRowId);
				var temp; 
				var type_qj = eval('(' +rowData.Type_QJ + ')');
				
				for(var x in type_qj){
					temp = x;
				}
				if(rowData.StartTime != "" && rowData.EndTime != "" && rowData.gh != ""){
					$.ajax({
						async : true,
						url : ctx + "/ehr/validate?njssj=" + rowData.EndTime + "&nkssj=" 
						+ rowData.StartTime + "&ygbh=" + ygbh + "&psCode=" + "1" + "&psBigType=" + ""
						+ "&psType=" + temp,
						success:function(data) {
							var arr = eval(data);
							if(arr[0] != "ok"){
								oAlert(arr[0]);
							}else{
								var json = {
										"TS":arr[1],"XSS":arr[2],
										"id":ctl.selectedRowId, 
										"uuid":ctl.selectedRowId};
								_this.updateRowData(formId, json);
								
								//计算总天数和总小时数
								var allSubFormData = _this.collectSubformData("userform_scgl_qjxx");
								if(allSubFormData != null){
									var totalDay = 0;//天数
									var totalHour = 0;//小时数
									var totalTXJHour = 0;//调休假小时数
									for(var i=0;i<allSubFormData.length;i++){
										var temp = allSubFormData[i];
										var vacationType = eval('(' +temp.Type_QJ + ')');
										var vacationTypeRel = vacationType["11"];//获得的值为事假，或调休假等
										if(vacationTypeRel == "调休假"){
											totalTXJHour = totalTXJHour + parseFloat(temp.XSS);
										}
										totalDay = totalDay + parseFloat(temp.TS);
										totalHour = totalHour + parseFloat(temp.XSS);
									}
									_this.getControl("qjts").setValue(totalDay);
									_this.getControl("qjxss").setValue(totalHour);
									if(txdecxValue < totalTXJHour){
										oAlert("请假小时数大于调休定额数！");
									}
								}
							}
						}
					});
					
				}
			});
			
		});
	},
	
	/**
	 * LDX_生产管理_考勤补卡单(长泰)
	 */
	uf_scgl_kqbkd:function(){
		//==========================================================================
		_this = this;
		
		//表单onload时，设置默认值
		var userInfo = SpringSecurityUtils.getUserDetails();
		var val = _this.getFieldValueByFieldName("WERKS");
		if(typeof val != 'undefined' && $.trim(val).length > 0){
			//return;
		}else{
			this.setFieldValueByFieldName("yggh",userInfo.employeeNumber);
			this.setFieldValueByFieldName("ygxm",userInfo.userName);
		}
		
		var loadLeve = function(jsonobj){					
			if(jsonobj==null){
				//alert("没有找到对应的数据");
				return;
			}
			
			var pt = "PT_BAPIHRS0001";
			if(jsonobj[pt].rows==="0"){
				//alert("没有找到对应的数据");
				return;
			}
			var data = jsonobj[pt].row[0];

			if(typeof val != 'undefined' && $.trim(val).length > 0){
				return;
			}
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("WERKS",data.WERKS);
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("BTRTL",data.BTRTL);
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("PERSK",data.PERSK);
			//员工姓名
			_this.setFieldValueByFieldName("ygxm",data.ENAME);
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("SHORT3",data.SHORT3);
		};
		getHR_ZHRI001004(_this.getFieldValueByFieldName("yggh"),loadLeve);
						
		//员工工号文本框
		this.$("input[name='yggh']").click(function(){
			$.unit.open({
				title : "选择人员",
				labelField : "ygxm",
				valueField : "yggh",
				type : "MyDept",
				multiple:false, 
				afterSelect : function(retVal) {
					_this.setFieldValueByFieldName("yggh",retVal.employeeNumber);
					_this.setFieldValueByFieldName("ygxm",retVal.name);							
					getHR_ZHRI001004(_this.getFieldValueByFieldName("yggh"),loadLeve);
					
					
					//重新获取考勤卡号
					//获取工号
					var attendenceNumber = _this.getControl("yggh").getValue();
					$.ajax({
						async : true,
						dataType:"text",
						url : ctx + "/ehr/kqbkh?attendenceNumber=" + attendenceNumber,
						success:function(data) {
							
							_this.getControl("kqkh").setValue(data);//工号值
						},
						error:function(msg){
							//alert(msg);
						}
					});
				},
				selectType : 4	
			});
			
			
			
		});
		
		//员工工号文本框
		this.$("input[name='yggh']").focus(function(){
			this.blur();
		});
		
		//获取工号
		var attendenceNumber = _this.getControl("yggh").getValue();
		$.ajax({
			async : true,
			dataType:"text",
			url : ctx + "/ehr/kqbkh?attendenceNumber=" + attendenceNumber,
			success:function(data) {
				
				_this.getControl("kqkh").setValue(data);//工号值
			},
			error:function(msg){
				alert(msg);
			}
		});
		//========================================================================
		
	},
	/**
	 * Leedarson_采购管理_设备易损件采购申请
	 * */
	uf_leedarson_cggl_sbysjcgsq:function(){
		var _this = this;
		
		//搜索
		this.$("#btn_ss").click(function(){
			var plwrk = _this.getFieldValueByFieldName("jhgc");
			var maktx = _this.getFieldValueByFieldName("wlms");						
			var params='{"PI_PLWRK":"'+plwrk+'","PI_MAKTX":"'+maktx+'"}';	
			
			$.ajax({
				type : "post",
				data : {
					"saps":"sapConnectConfig",
					"functionName" : "ZPPI0010",
					"jsonParams" : params,
				},
				async : false,
				url : ctx + "/basicdata/sap/executeRFC",
				success : function(result) {
					if (result.success) {
						//alert(result.data);
						//return;
						// 保存成功刷新列表
						//转成对象
    					var obj = eval('(' + result.data + ')');							
    					if(obj.PT_ZPPS0021.rows == "0") return false;
						var elements = obj.PT_ZPPS0021.row;	
						
						for(var i=0;i<elements.length;i++){
							var data = {};								
							data.gc = elements[i].PLWRK;
							data.jhddh = elements[i].PLNUM;
							data.ddrq = elements[i].PSTTR;								
							data.wlid = elements[i].MATNR;
							data.wlms = elements[i].MAKTX;
							data.ddsl = elements[i].GSMNG;
							data.dw = elements[i].MEINS;
						
							//alert(JSON.stringify(data));
							_this.addRowData("uf_cggl_sbysjcgsq_dtbd", data);
						}				
												
					} else {
						alert(result.msg);
					}
				}
			});
		});			
	
	},
	/**
	 *LDX_人事管理_员工晋升表单
	*/
	uf_rsgl_ygjsbd:function(){
		_this = this;
						
		var loadLeve = function(jsonobj){					
			if(jsonobj==null){
				alert("没有找到对应的数据");
				return;
			}
			
			var pt = "PT_BAPIHRS0001";
			if(jsonobj[pt].rows==="0"){
				alert("没有找到对应的数据");
				return;
			}
			var data = jsonobj[pt].row[0];
			
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("WERKS",data.WERKS);	
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("BTRTL",data.BTRTL);
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("PERSK",data.PERSK);	
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("SHORT3",data.SHORT3);
			
			//姓名
			_this.setFieldValueByFieldName("xm",data.SNAME);
			//部门
			_this.setFieldValueByFieldName("bm",data.ORGTX);
			//岗位
			_this.setFieldValueByFieldName("zw",data.PLSTX);	
			//人事子范围
			_this.setFieldValueByFieldName("rszfw",data.BTRTX);
			//员工子组
			_this.setFieldValueByFieldName("ygzz",data.PTEXT);
			//人事范围
			_this.setFieldValueByFieldName("rsfw",data.BUTXT);
		};
		
		var loadLeve_x = function(jsonobj){					
			if(jsonobj==null){
				alert("没有找到对应的数据");
				return;
			}
			
			var pt = "PT_ZHRS0023";
			if(jsonobj[pt].rows==="0"){
				alert("没有找到对应的数据");
				return;
			}
			var data = jsonobj[pt].row[0];										
			//学历
			_this.setFieldValueByFieldName("xl",data.ZXLMS);
		};
		
		//晋升人员文本框
		this.$("input[name='jsry']").click(function(){
			$.unit.open({
				title : "选择晋升人员",
				labelField : "ygxm",
				valueField : "ygbh",
				type : "MyDept",
				multiple:false, 
				afterSelect : function(retVal) {
					_this.setFieldValueByFieldName("jsry",retVal.employeeNumber);						
					getHR_ZHRI001004(_this.getFieldValueByFieldName("jsry"),loadLeve);
					getHR_ZHRI001012(_this.getFieldValueByFieldName("jsry"),loadLeve_x);
				},
				selectType : 4	
			});
		});
		
		//晋升人员文本框
		this.$("input[name='jsry']").focus(function(){
			this.blur();
		});		
	},
	/**
	 *LDX_人事管理_转正评估申请表	uf_rsgl_zzpgsqb
	*/
	uf_rsgl_zzpgsqb:function(){
		_this = this;
		
		//表单onload时，设置默认值
		var userInfo = SpringSecurityUtils.getUserDetails();
		this.setFieldValueByFieldName("gh",userInfo.employeeNumber);
		this.setFieldValueByFieldName("xm",userInfo.userName);
		
		var loadLeve = function(jsonobj){					
			if(jsonobj==null){
				alert("没有找到对应的数据");
				return;
			}
			
			var pt = "PT_BAPIHRS0001";
			if(jsonobj[pt].rows==="0"){
				//alert("没有找到对应的数据");
				return;
			}
			var data = jsonobj[pt].row[0];
			
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("WERKS",data.WERKS);
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("BTRTL",data.BTRTL);
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("PERSK",data.PERSK);
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("SHORT3",data.SHORT3);
			
			//姓名
			_this.setFieldValueByFieldName("xm",data.ENAME);
			//部门
			_this.setFieldValueByFieldName("bm",data.ORGTX);
			//职位
			_this.setFieldValueByFieldName("zw",data.PLSTX);
			//人事范围
			_this.setFieldValueByFieldName("rsfw",data.BUTXT);
			//人事子范围
			_this.setFieldValueByFieldName("rszfw",data.BTRTX);
		};
		
		var loadLeve2 = function(jsonobj){					
			if(jsonobj==null){
				//alert("没有找到对应的数据");
				return;
			}
			//转正评估申请表中的计划转正日期设置值
			
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("jhzzrq",jsonobj.PO_TERMN);
			_this.setFieldValueByFieldName("rzrq",jsonobj.PO_BEGDA);
			
		};
		
		var loadLeve3 = function(jsonobj){					
			if(jsonobj==null){
				//alert("没有找到对应的数据");
				return;
			}
			
			var pt = "PT_PA0008";
			if(jsonobj[pt].rows==="0"){
				//alert("没有找到对应的数据");
				return;
			}
			var data = jsonobj[pt].row[0];			
			//---------------------
			_this.setFieldValueByFieldName("TRFAR",data.TRFAR);
			_this.setFieldValueByFieldName("TRFGB",data.TRFGB);
			_this.setFieldValueByFieldName("TRFGR",data.TRFGR);
			_this.setFieldValueByFieldName("TRFST",data.TRFST);
			
		};
		
		getHR_ZHRI001004(_this.getFieldValueByFieldName("gh"),loadLeve);
		getHR_ZHRI0060(_this.getFieldValueByFieldName("gh"),loadLeve2);
		getHR_ZHRI002902(_this.getFieldValueByFieldName("gh"),loadLeve3);
		
		
		//员工工号文本框
		this.$("input[name='gh']").click(function(){
			$.unit.open({
				title : "选择人员",
				labelField : "ygxm",
				valueField : "yggh",
				type : "MyDept",
				multiple:false, 
				afterSelect : function(retVal) {
					_this.setFieldValueByFieldName("gh",retVal.employeeNumber);
					_this.setFieldValueByFieldName("xm",retVal.name);							
					getHR_ZHRI001004(_this.getFieldValueByFieldName("gh"),loadLeve);
					getHR_ZHRI0060(_this.getFieldValueByFieldName("gh"),loadLeve2);
					getHR_ZHRI002902(_this.getFieldValueByFieldName("gh"),loadLeve3);
				},
				selectType : 4	
			});
		});
		
		//员工工号文本框
		this.$("input[name='gh']").focus(function(){
			this.blur();
		});
	},

	/**
	 * 员工录用审批表（总部）
	 */
	uf_ldx_rsgl_yglyspb:function(){
		_this = this;
		
		//表单onload时，设置默认值
		var userInfo = SpringSecurityUtils.getUserDetails();
		this.setFieldValueByFieldName("txrbh",userInfo.employeeNumber);
		this.setFieldValueByFieldName("txrxm",userInfo.userName);
		
		var loadLeve = function(jsonobj){					
			if(jsonobj==null){
				alert("没有找到对应的数据");
				return;
			}
			
			var pt = "PT_BAPIHRS0001";
			if(jsonobj[pt].rows==="0"){
				//alert("没有找到对应的数据");
				return;
			}
			var data = jsonobj[pt].row[0];
								
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("ORGTX",data.ORGTX);	
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("BTRTX",data.BTRTX);
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("PLSTX",data.PLSTX);	
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("BTRTL",data.BTRTL);
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("PTEXT",data.PTEXT);	
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("WERKS",data.WERKS);
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("SHORT3",data.SHORT3);	
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("NAME1",data.NAME1);
			
			//填写人姓名
			_this.setFieldValueByFieldName("txrxm",data.SNAME);
			
		};
		getHR_ZHRI001004(_this.getFieldValueByFieldName("txrbh"),loadLeve);
		
		//填写人编号文本框
		this.$("input[name='txrbh']").click(function(){
			$.unit.open({
				title : "选择人员",
				labelField : "ygxm",
				valueField : "ygbh",
				type : "MyDept",
				multiple:false, 
				afterSelect : function(retVal) {
					_this.setFieldValueByFieldName("txrbh",retVal.employeeNumber);
					_this.setFieldValueByFieldName("txrxm",retVal.name);							
					getHR_ZHRI001004(_this.getFieldValueByFieldName("txrbh"),loadLeve);
				},
				selectType : 4	
			});
		});
		
		//填写人编号文本框
		this.$("input[name='txrbh']").focus(function(){
			this.blur();
		});	
		
		var loadLeve_yp = function(jsonobj){					
			if(jsonobj==null){
				alert("没有找到对应的数据");
				return;
			}
			
			var pt = "PT_PB0002";
			
			var pt2 = "PT_PB0022";
			
			var pt3 = "PT_PB0001";
			
			if(jsonobj[pt].rows==="0"){
				//alert("没有找到对应的数据");
				return;
			}
			var data = jsonobj[pt].row[0];
			
			var data2 = jsonobj[pt2].row[0];
			
			var data3 = jsonobj[pt3].row[0];
								
			
			//人事范围
			_this.setFieldValueByFieldName("rsfw",data3.WERKS);
			//人事子范围
			_this.setFieldValueByFieldName("rszfw",data3.BTRTL);
			
			//姓名
			_this.setFieldValueByFieldName("x",data.NACHN);
			//姓名
			_this.setFieldValueByFieldName("m",data.VORNA);
			//英文名
			_this.setFieldValueByFieldName("ywm",data.NAME2);
			//性别
			_this.setFieldValueByFieldName("xb",data.GESCH);
			//出生日期
			_this.setFieldValueByFieldName("csrq",data.GBDAT);
			//籍贯
			_this.setFieldValueByFieldName("jg",data.GBORT);
			//学历
			_this.setFieldValueByFieldName("xl",data2.STEXT);
			//婚姻状况
			_this.setFieldValueByFieldName("hyzk",data.FAMST);
			//专业
			_this.setFieldValueByFieldName("zy",data2.ZZYMC);
			//国籍
			_this.setFieldValueByFieldName("gj",data.NATIO);
			
		};
		
		//应聘人编号文本框
		this.$("input[name='yprybm']").change(function(){
			
			getHR_ZHRI003201(this.value,loadLeve_yp);
		    
		});
	},
	/**
	 *员工录用审批表（专项工作组）
	*/
	uf_ldx_rsgl_yglyspbzxgzz:function(){
		_this = this;
		
		//表单onload时，设置默认值
		var userInfo = SpringSecurityUtils.getUserDetails();
		this.setFieldValueByFieldName("txrbh",userInfo.employeeNumber);
		this.setFieldValueByFieldName("txrxm",userInfo.userName);
		
		var loadLeve = function(jsonobj){					
			if(jsonobj==null){
				alert("没有找到对应的数据");
				return;
			}
			
			var pt = "PT_BAPIHRS0001";
			if(jsonobj[pt].rows==="0"){
				alert("没有找到对应的数据");
				return;
			}
			var data = jsonobj[pt].row[0];
								
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("ORGTX",data.ORGTX);	
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("BTRTX",data.BTRTX);
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("PLSTX",data.PLSTX);	
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("BTRTL",data.BTRTL);
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("PTEXT",data.PTEXT);	
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("WERKS",data.WERKS);
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("SHORT3",data.SHORT3);	
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("NAME1",data.NAME1);
			
			//填写人姓名
			_this.setFieldValueByFieldName("txrxm",data.SNAME);
			
		};
		getHR_ZHRI001004(_this.getFieldValueByFieldName("txrbh"),loadLeve);
		
		//填写人编号文本框
		this.$("input[name='txrbh']").click(function(){
			$.unit.open({
				title : "选择人员",
				labelField : "ygxm",
				valueField : "ygbh",
				type : "MyDept",
				multiple:false, 
				afterSelect : function(retVal) {
					_this.setFieldValueByFieldName("txrbh",retVal.employeeNumber);
					_this.setFieldValueByFieldName("txrxm",retVal.name);							
					getHR_ZHRI001004(_this.getFieldValueByFieldName("txrbh"),loadLeve);
				},
				selectType : 4	
			});
		});
		
		//填写人编号文本框
		this.$("input[name='txrbh']").focus(function(){
			this.blur();
		});	
		
		var loadLeve_yp = function(jsonobj){					
			if(jsonobj==null){
				alert("没有找到对应的数据");
				return;
			}
			
			var pt = "PT_PB0002";
			
			var pt2 = "PT_PB0022";
			
			if(jsonobj[pt].rows==="0"){
				//alert("没有找到对应的数据");
				return;
			}
			var data = jsonobj[pt].row[0];
			
			var data2 = jsonobj[pt2].row[0];
								
			//姓名
			_this.setFieldValueByFieldName("x",data.NACHN);
			//姓名
			_this.setFieldValueByFieldName("m",data.VORNA);
			//英文名
			_this.setFieldValueByFieldName("ywm",data.NAME2);
			//性别
			_this.setFieldValueByFieldName("xb",data.GESCH);
			//出生日期
			_this.setFieldValueByFieldName("csrq",data.GBDAT);
			//籍贯
			_this.setFieldValueByFieldName("jg",data.GBORT);
			//学历
			_this.setFieldValueByFieldName("xl",data2.STEXT);
			//婚姻状况
			_this.setFieldValueByFieldName("hyzk",data.FAMST);
			//专业
			_this.setFieldValueByFieldName("zy",data2.ZZYMC);
			//国籍
			_this.setFieldValueByFieldName("gj",data.NATIO);
		};
		
		//应聘人编号文本框
		this.$("input[name='yprybm']").change(function(){
			
				getHR_ZHRI003201(this.value,loadLeve_yp);
		    
		});
	},
	
	/**
	 *LDX_人事管理_劳动合同续签审批表
	*/
	uf_rsgl_ldhtxqspb:function(){
		_this = this;	
						
		var loadLeve = function(jsonobj){					
			if(jsonobj==null){
				alert("没有找到对应的数据");
				return;
			}
			
			var pt = "PT_BAPIHRS0001";
			if(jsonobj[pt].rows==="0"){
				alert("没有找到对应的数据");
				return;
			}
			var data = jsonobj[pt].row[0];
			
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("WERKS",data.WERKS);
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("BTRTX",data.BTRTX);
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("PERSK",data.PERSK);
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("SNAME",data.SNAME);
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("SHORT3",data.SHORT3);
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("BTRTL",data.BTRTL);
			
		};
		
		//----------------定义动态表格事件-----------------------
		var formId = "uf_rsgl_ldhtxqxx";
		var formUuid = _this.getFormUuid(formId); 
		var ctl = _this.getSubformControl(formUuid);
						
		ctl.bind("afterInsertRow", function(rowdata){
			
			var updateRow = function(jsonobj){
				if(jsonobj==null){
					alert("没有找到对应的数据");
					return;
				}
				
				var pt = "RETURN_INFO";
				var data = jsonobj[pt];						
				var rowData = ctl.getRowData(ctl.selectedRowId);
				
				//Domino表单中隐藏域
				_this.setFieldValueByFieldName("PLSTX",data.PLSTX);
				//Domino表单中隐藏域
				_this.setFieldValueByFieldName("ORGTX",data.ORGTX);							
				rowData.ygbh = data.PERNR;
				rowData.ygxm = data.ENAME;
				rowData.cyzw = data.PLSTX;
				rowData.ssbm = data.ORGTX;
				rowData.yksrq = data.BEGDA;
				rowData.yjzrq = data.CTEDT;											
				
				_this.updateRowData(formId, rowData);
			};
			
			_this.getControl("ygbh", rowdata.id ).bind("focus", function(){
				$.unit.open({
					title : "选择人员",
					type : "MyDept",
					multiple:false, 
					afterSelect : function(retVal) {							
						getHR_YGLDHTWHJK(retVal.employeeNumber,"","",updateRow);
						getHR_ZHRI001004(retVal.employeeNumber,loadLeve);								
					},
					selectType : 4	
				});
				
			});
		});
		
						
	},
	/**
	 *LDX_人事管理_劳动合同续签审批表(专项工作组) (1.0)
	*/
	uf_rsgl_ldhtxqspb_zxgzz:function(){
		_this = this;
		var userInfo = SpringSecurityUtils.getUserDetails();
		this.setFieldValueByFieldName("zwdj",userInfo.majorJob.dutyLevel);
		var loadLeve = function(jsonobj){					
			if(jsonobj==null){
				alert("没有找到对应的数据");
				return;
			}
			
			var pt = "PT_BAPIHRS0001";
			if(jsonobj[pt].rows==="0"){
				alert("没有找到对应的数据");
				return;
			}
			var data = jsonobj[pt].row[0];
			
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("WERKS",data.WERKS);
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("BTRTX",data.BTRTX);
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("PERSK",data.PERSK);
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("SNAME",data.SNAME);
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("SHORT3",data.SHORT3);
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("BTRTL",data.BTRTL);
			
		};
		
		//----------------定义动态表格事件-----------------------
		var formId = "uf_rsgl_ldhtxqzxxx";
		var formUuid = _this.getFormUuid(formId); 
		var ctl = _this.getSubformControl(formUuid);
						
		ctl.bind("afterInsertRow", function(rowdata){
			
			var updateRow = function(jsonobj){
				if(jsonobj==null){
					alert("没有找到对应的数据");
					return;
				}
				
				var pt = "RETURN_INFO";
				var data = jsonobj[pt];						
				var rowData = ctl.getRowData(ctl.selectedRowId);
				
				//Domino表单中隐藏域
				_this.setFieldValueByFieldName("PLSTX",data.PLSTX);
				//Domino表单中隐藏域
				_this.setFieldValueByFieldName("ORGTX",data.ORGTX);							
				rowData.ygbh = data.PERNR;
				rowData.ygxm = data.ENAME;
				rowData.cyzw = data.PLSTX;
				rowData.ssbm = data.ORGTX;
				rowData.yksrq = data.BEGDA;
				rowData.yjzrq = data.CTEDT;											
				
				_this.updateRowData(formId, rowData);
			};
			
			_this.getControl("ygbh", rowdata.id ).bind("click", function(){
				$.unit.open({
					title : "选择人员",
					type : "MyUnit",
					multiple:false, 
					afterSelect : function(retVal) {							
						getHR_YGLDHTWHJK(retVal.employeeNumber,"","",updateRow);
						getHR_ZHRI001004(retVal.employeeNumber,loadLeve);								
					},
					selectType : 4	
				});
				
			});
		});
	},
	
	/**
	 * LDX_人事管理_离职申请表(专项工作组)
	*/
	uf_rsgl_lzsqb_zxgzz:function(){
		_this = this;
		
		//表单onload时，设置默认值
		var userInfo = SpringSecurityUtils.getUserDetails();
		var val = _this.getFieldValueByFieldName("WERKS");
		if(typeof val != 'undefined' && $.trim(val).length > 0){
			//return;
		}else{
			this.setFieldValueByFieldName("ygbh",userInfo.employeeNumber);
			this.setFieldValueByFieldName("xm",userInfo.userName);
		}
			
		var loadLeve = function(jsonobj){					
			if(jsonobj==null){
				alert("没有找到对应的数据");
				return;
			}
			
			var pt = "PT_BAPIHRS0001";//PT_BAPIHRS0001
			if(jsonobj[pt].rows==="0"){
				//alert("没有找到对应的数据");
				return;
			}
			var data = jsonobj[pt].row[0];

			if(typeof val != 'undefined' && $.trim(val).length > 0){
				 
				return;
			}		
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("PLANS",data.PLANS);
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("BTRTL",data.BTRTL);
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("WERKS",data.PERSK);
			
			//姓名
			_this.setFieldValueByFieldName("xm",data.ENAME);
			//部门
			_this.setFieldValueByFieldName("bm",data.ORGTX);
			//职位
			_this.setFieldValueByFieldName("zw",data.PLSTX);
			//人事子范围
			_this.setFieldValueByFieldName("rszfw",data.BTRTX);
			//员工子组
			_this.setFieldValueByFieldName("ygzz",data.PTEXT);
			//职级/星级
			_this.setFieldValueByFieldName("zjxj",data.SHORT3);
			//人事范围
			_this.setFieldValueByFieldName("rsfw",data.NAME1);
			//关键岗位级别
			_this.setFieldValueByFieldName("gjgwjb",data.ZZGJGWJB);
		};
		getHR_ZHRI001004(_this.getFieldValueByFieldName("ygbh"),loadLeve);
						
		//员工编号文本框
		this.$("input[name='ygbh']").click(function(){
			$.unit.open({
				title : "选择人员",
				labelField : "ygxm",
				valueField : "yggh",
				type : "MyDept",
				multiple:false, 
				afterSelect : function(retVal) {
					_this.setFieldValueByFieldName("ygbh",retVal.employeeNumber);
					_this.setFieldValueByFieldName("xm",retVal.name);							
					getHR_ZHRI001004(_this.getFieldValueByFieldName("ygbh"),loadLeve);
				},
				selectType : 4	
			});
		});
		
		//员工编号文本框
		this.$("input[name='ygbh']").focus(function(){
			this.blur();
		});
	},
	
	/**
	 * LDX_人事管理_离职申请表
	*/
	uf_rsgl_lzsqb:function(){
		_this = this;
		
		//表单onload时，设置默认值
		var userInfo = SpringSecurityUtils.getUserDetails();
		var val = _this.getFieldValueByFieldName("WERKS");
		if(typeof val != 'undefined' && $.trim(val).length > 0){
			//return;
		}else{
			this.setFieldValueByFieldName("ygbh",userInfo.employeeNumber);
			this.setFieldValueByFieldName("xm",userInfo.userName);
		}
			
		var loadLeve = function(jsonobj){					
			if(jsonobj==null){
				alert("没有找到对应的数据");
				return;
			}
			
			var pt = "PT_BAPIHRS0001";
			if(jsonobj[pt].rows==="0"){
				//alert("没有找到对应的数据");
				return;
			}
			var data = jsonobj[pt].row[0];

			if(typeof val != 'undefined' && $.trim(val).length > 0){
				 
				return;
			}		
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("PLANS",data.WERKS);
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("BTRTL",data.BTRTL);
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("WERKS",data.PERSK);
			
			//姓名
			_this.setFieldValueByFieldName("xm",data.ENAME);
			//部门
			_this.setFieldValueByFieldName("bm",data.ORGTX);
			//职位
			_this.setFieldValueByFieldName("zw",data.PLSTX);
			//人事子范围
			_this.setFieldValueByFieldName("rszfw",data.BTRTX);
			//员工子组
			_this.setFieldValueByFieldName("ygzz",data.PTEXT);
			//职级/星级
			_this.setFieldValueByFieldName("zjxj",data.SHORT3);
			//人事范围
			_this.setFieldValueByFieldName("rsfw",data.NAME1);
			//关键岗位级别
			_this.setFieldValueByFieldName("gjgwjb",data.ZZGJGWJB);
		};
		
		var loadLeve2 = function(jsonobj){					
			if(jsonobj==null){
				alert("没有找到对应的数据");
				return;
			}
			
			if(jsonobj.PO_BEGDA != ""){
				//Domino表单中隐藏域
				_this.setFieldValueByFieldName("rzrq",jsonobj.PO_BEGDA);
			}
			
			
		};
		
		getHR_ZHRI001004(_this.getFieldValueByFieldName("ygbh"),loadLeve);
		//入职日期查询
		getHR_ZHRI0060(_this.getFieldValueByFieldName("ygbh"),loadLeve2);				
		//员工编号文本框
		this.$("input[name='ygbh']").click(function(){
			$.unit.open({
				title : "选择人员",
				labelField : "ygxm",
				valueField : "yggh",
				type : "MyDept",
				multiple:false, 
				afterSelect : function(retVal) {
					_this.setFieldValueByFieldName("ygbh",retVal.employeeNumber);
					_this.setFieldValueByFieldName("xm",retVal.name);							
					getHR_ZHRI001004_02(_this.getFieldValueByFieldName("ygbh"),loadLeve);
					getHR_ZHRI0060(_this.getFieldValueByFieldName("ygbh"),loadLeve2);
				},
				selectType : 4	
			});
		});
		
		//员工编号文本框
		this.$("input[name='ygbh']").focus(function(){
			this.blur();
		});
	},
	/**
	 *LDX_人事管理_招聘面试安排表
	*/
	uf_ldx_rsgl_zpmsapb:function(){
		_this = this;
						
		//表单onload时，设置默认值
		var userInfo = SpringSecurityUtils.getUserDetails();
		this.setFieldValueByFieldName("sqr",userInfo.userName);
		this.setFieldValueByFieldName("sqrbh",userInfo.employeeNumber);
		
		var loadLeve = function(jsonobj){					
			if(jsonobj==null){
				alert("没有找到对应的数据");
				return;
			}
			
			var pt = "PT_BAPIHRS0001";
			if(jsonobj[pt].rows==="0"){
				alert("没有找到对应的数据");
				return;
			}
			var data = jsonobj[pt].row[0];
			
			//申请人
			_this.setFieldValueByFieldName("sqr",data.ENAME);
			//申请人姓名
			_this.setFieldValueByFieldName("sqrxm",data.ENAME);
			//职位
			_this.setFieldValueByFieldName("zw",data.PLSTX);
		};				
		getHR_ZHRI001004(_this.getFieldValueByFieldName("sqrbh"),loadLeve);
		
		//申请人文本框
		this.$("input[name='sqr']").click(function(){
			$.unit.open({
				title : "选择人员",
				labelField : "ygxm",
				valueField : "yggh",
				type : "MyDept",
				multiple:false, 
				afterSelect : function(retVal) {
					_this.setFieldValueByFieldName("sqrbh",retVal.employeeNumber);						
					getHR_ZHRI001004(_this.getFieldValueByFieldName("sqrbh"),loadLeve);
				},
				selectType : 4	
			});
		});
		
		//申请人文本框
		this.$("input[name='sqr']").focus(function(){
			this.blur();
		});
		
		//职位编码文本框
		this.$("input[name='zwbm']").focus(function(){
			_this.$("input[name='zpmsapbview']").trigger("click");
	   	});
		
		//简历查询后填充对应的字段
		var loadLeve_jl = function(jsonobj){					
			if(jsonobj==null){
				alert("没有找到对应的数据");
				return;
			}
			
			var pt = "PT_PB0002";
			if(jsonobj[pt].rows==="0"){
				alert("没有找到对应的数据");
				return;
			}
			var data = jsonobj[pt].row[0];
			
			//申请人编号
			_this.setFieldValueByFieldName("PERNR",data.PERNR);
			//姓
			_this.setFieldValueByFieldName("xmdx",data.NACHN);
			//名 
			_this.setFieldValueByFieldName("xmdm",data.VORNA);
			//<隐藏域>
			//_this.setFieldValueByFieldName("NAME2",data.NAME2);
			//性别
			_this.setFieldValueByFieldName("xb",data.GESCH);
			//出生年月
			_this.setFieldValueByFieldName("csny",data.GBDAT);
			//籍贯
			_this.setFieldValueByFieldName("jg",data.GBORT);
			//<隐藏域>
			//_this.setFieldValueByFieldName("STEXT",data.STEXT);
			//毕业院校
			_this.setFieldValueByFieldName("byyx",data.INSTI);
			//离职原因
			_this.setFieldValueByFieldName("lzyy",data.ZLZYY);
			//联系电话
			_this.setFieldValueByFieldName("lxdh",data.USRID);
		};	
		//申请人简历查询
		this.$("#btn_sqrjlcx").click(function(){
			getHR_ZHRI003201(_this.getFieldValueByFieldName("sqrbh"),loadLeve_jl);
		});
		
		/**
		 * SAP回写
		 */
		this.$("#hiresap").click(function(){
			
			var PI_APLNO = _this.getControl("sqrbh").getValue();//形象气质
			var PI_BEGDA = _this.getControl("ylmssj").getValue();//一轮面试时间
			var PI_ENDDA  = "99991231";
			var PI_ZZXXQZ = _this.getControl("xxqz").getValue();//形象气质
			var PI_ZZGTBDNL = _this.getControl("bdgt").getValue();//计划加班开始时间 5
			var PI_ZZZWPPD = _this.getControl("jlppd").getValue();//计划加班开始时间 6
			
			var PI_ZZLJLWYBNL = _this.getControl("ljlwnl").getValue();//计划加班开始时间
			var PI_ZZLJSWNL = _this.getControl("ljsw").getValue();//逻辑思维能力
			var PI_ZZZRXYGZXT = _this.getControl("zrx").getValue();//计划加班开始时间
			var PI_ZZPYQLYKSX = _this.getControl("ksx").getValue();//计划加班开始时间
			var PI_ZZJHZZNL = _this.getControl("syx").getValue();//文化及性格适应性
			var PI_ZZWHJXGSYX = _this.getControl("jhzznl").getValue();//计划组织能力
			
			var PI_ZZTDJSYS = _this.getControl("tdjsys").getValue();//计划加班开始时间
			var PI_ZZCXXXYS = _this.getControl("cxxxys").getValue();//计划加班开始时间
			var PI_ZZCJDXYS = _this.getControl("cjdxys").getValue();//成就导向意识
			var PI_ZZCSZHPY = _this.getControl("zhpy").getValue();//综合评语
			var PI_ZZGZJN = _this.getControl("jnzsppd").getValue();//工作技能
			var PI_ZZGZHJSYX = _this.getControl("gzhjdsyx").getValue();//工作环境适应性
			
			var PI_ZZGLFSSYX = _this.getControl("ldglfsdsyx").getValue();//领导管理方式的适应性
			var PI_ZZGRFZMZD = _this.getControl("grfzxqdmzd").getValue();//个人发展需求的满足度
			var PI_ZZFSZHPY = _this.getControl("fszhpy").getValue();//复试综合评语
			var PI_ZZZSZHPY = _this.getControl("zszhpy").getValue();//终始综合评语
			var PI_ZZQWXZ = _this.getControl("qwxz").getValue();//期望薪资
			
			var params = "{PI_APLNO:'" + PI_APLNO + "',PI_BEGDA:'" + PI_BEGDA 
			+ "',PI_ENDDA:'" + PI_ENDDA + "',PI_ZZXXQZ:'" + PI_ZZXXQZ
			+ "',PI_ZZGTBDNL:'" + PI_ZZGTBDNL + "',PI_ZZZWPPD:'" + PI_ZZZWPPD 
			
			+ "',PI_ZZLJLWYBNL:'" + PI_ZZLJLWYBNL + "',PI_ZZLJSWNL:'" + PI_ZZLJSWNL 
			+ "',PI_ZZZRXYGZXT:'" + PI_ZZZRXYGZXT + "',PI_ZZPYQLYKSX:'" + PI_ZZPYQLYKSX 
			+ "',PI_ZZJHZZNL:'" + PI_ZZJHZZNL + "',PI_ZZWHJXGSYX:'" + PI_ZZWHJXGSYX 
			
			+ "',PI_ZZTDJSYS:'" + PI_ZZTDJSYS + "',PI_ZZCXXXYS:'" + PI_ZZCXXXYS
			+ "',PI_ZZCJDXYS:'" + PI_ZZCJDXYS + "',PI_ZZCSZHPY:'" + PI_ZZCSZHPY 
			+ "',PI_ZZGZJN:'" + PI_ZZGZJN + "',PI_ZZGZHJSYX:'" + PI_ZZGZHJSYX + 
			
			+ "',PI_ZZGLFSSYX:'" + PI_ZZGLFSSYX + "',PI_ZZGRFZMZD:'" + PI_ZZGRFZMZD 
			+ "',PI_ZZFSZHPY:'" + PI_ZZFSZHPY + "',PI_ZZZSZHPY:'" + PI_ZZZSZHPY + 
			+ "',PI_ZZQWXZ:'" + PI_ZZQWXZ +
			"'}"; 
			$.ajax({
				type : "post",
				data : {
					"saps":"sapConnectConfig",
					"functionName" : "ZHRI0021",
					"jsonParams" : params,
				},
				url : ctx + "/basicdata/sap/executeRFC",
				success : function(result) {
					var _result = result;//
					var _data = result.data;//
					if (result.success) {
						var evalData = eval('(' + result.data + ')');
						var row = evalData.PT_RETURN.row;
						if(row.length >0){
							
							for(var i=0;i<row.length;i++){
								if(row[i].TYPE == "S"){
									alert(row[i].MESSAGE);
								}else{
									alert(row[i].MESSAGE);
								}
							}
						}
					}else{
						alert(result.msg);
					}
				}				
			});	
			
		});
		
	},
	/**
	 *LDX_人事管理_员工录用审批表
	*/
	uf_rsgl_yglyspb_dj:function(){
		_this = this;
		
		//表单onload时，设置默认值
		var userInfo = SpringSecurityUtils.getUserDetails();
		this.setFieldValueByFieldName("txrbh",userInfo.employeeNumber);
		this.setFieldValueByFieldName("txrxm",userInfo.userName);
		
		var loadLeve = function(jsonobj){					
			if(jsonobj==null){
				//alert("没有找到对应的数据");
				return;
			}
			
			var pt = "PT_BAPIHRS0001";
			if(jsonobj[pt].rows==="0"){
				alert("没有找到对应的数据");
				return;
			}
			var data = jsonobj[pt].row[0];
								
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("ORGTX",data.ORGTX);	
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("BTRTX",data.BTRTX);
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("PLSTX",data.PLSTX);	
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("BTRTL",data.BTRTL);
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("PTEXT",data.PTEXT);	
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("WERKS",data.WERKS);
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("SHORT3",data.SHORT3);	
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("NAME1",data.NAME1);
			
			//填写人姓名
			_this.setFieldValueByFieldName("txrxm",data.SNAME);
			
		};
		getHR_ZHRI001004(_this.getFieldValueByFieldName("txrbh"),loadLeve);
		
		//填写人编号文本框
		this.$("input[name='txrbh']").click(function(){
			$.unit.open({
				title : "选择人员",
				labelField : "ygxm",
				valueField : "ygbh",
				type : "MyDept",
				multiple:false, 
				afterSelect : function(retVal) {
					_this.setFieldValueByFieldName("txrbh",retVal.employeeNumber);
					_this.setFieldValueByFieldName("txrxm",retVal.name);							
					getHR_ZHRI001004(_this.getFieldValueByFieldName("txrbh"),loadLeve);
				},
				selectType : 4	
			});
		});
		
		//填写人编号文本框
		this.$("input[name='txrbh']").focus(function(){
			this.blur();
		});	
		
		var loadLeve_yp = function(jsonobj){					
			if(jsonobj==null){
				//alert("没有找到对应的数据");
				return;
			}
			var pt = "PT_PB0002";
			
			var pt2 = "PT_PB0022";
			
			if(jsonobj[pt].rows==="0"){
				//alert("没有找到对应的数据");
				return;
			}
			var data = jsonobj[pt].row[0];
			
			var data2 = jsonobj[pt2].row[0];
								
			//姓名
			_this.setFieldValueByFieldName("x",data.NACHN);
			//姓名
			_this.setFieldValueByFieldName("m",data.VORNA);
			//英文名
			_this.setFieldValueByFieldName("ywm",data.NAME2);
			//性别
			_this.setFieldValueByFieldName("xb",data.GESCH);
			//出生日期
			_this.setFieldValueByFieldName("csrq",data.GBDAT);
			//籍贯
			_this.setFieldValueByFieldName("jg",data.GBORT);
			//学历
			_this.setFieldValueByFieldName("xl",data2.STEXT);
			//婚姻状况
			_this.setFieldValueByFieldName("hyzk",data.FAMST);
			//专业
			_this.setFieldValueByFieldName("zy",data2.ZZYMC);
		};
		
		//应聘人编号文本框
		this.$("input[name='yprybm']").keydown(function(){
			if(event.keyCode == 13){
				getHR_ZHRI003201(this.value,loadLeve_yp);
		    }
		});
	},
	
	/**
	 *LDX_培训管理_培训课程报名表
	*/
	uf_pxgl_pxkcbmb:function(){
		_this = this;
						
		var loadLeve = function(jsonobj){					
			if(jsonobj==null){
				alert("没有找到对应的数据");
				return;
			}
			
			var pt = "PT_BAPIHRS0001";
			if(jsonobj[pt].rows==="0"){
				alert("没有找到对应的数据");
				return;
			}
			var data = jsonobj[pt].row[0];
								
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("WERKS",data.WERKS);
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("BTRTL",data.BTRTL);
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("PERSK",data.PERSK);
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("SHORT3",data.SHORT3);
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("SNAME",data.SNAME);
			
		};
						
		//员工编号文本框
		this.$("input[name='ygbh']").click(function(){
			$.unit.open({
				title : "选择人员",
				labelField : "ygxm",
				valueField : "yggh",
				type : "MyDept",
				multiple:false, 
				afterSelect : function(retVal) {
					_this.setFieldValueByFieldName("ygbh",retVal.employeeNumber);							
					getHR_ZHRI001004(_this.getFieldValueByFieldName("ygbh"),loadLeve);
				},
				selectType : 4	
			});
		});
		
		//员工编号文本框
		this.$("input[name='ygbh']").focus(function(){
			this.blur();
		});
	},
	/**
	 * LDX_人事管理_离职申请表(国际)
	*/
	uf_rsgl_lzsqb_gj:function(){
		_this = this;
		
		//表单onload时，设置默认值
		var userInfo = SpringSecurityUtils.getUserDetails();
		var val = _this.getFieldValueByFieldName("WERKS");
		if(typeof val != 'undefined' && $.trim(val).length > 0){
			//return;
		}else{
			this.setFieldValueByFieldName("ygbh",userInfo.employeeNumber);
			this.setFieldValueByFieldName("xm",userInfo.userName);
		}
			
		var loadLeve = function(jsonobj){					
			if(jsonobj==null){
				alert("没有找到对应的数据");
				return;
			}
			
			var pt = "PT_BAPIHRS0001";
			if(jsonobj[pt].rows==="0"){
				alert("没有找到对应的数据");
				return;
			}
			var data = jsonobj[pt].row[0];
			if(typeof val != 'undefined' && $.trim(val).length > 0){
				 
				return;
			}			
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("PLANS",data.WERKS);
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("BTRTL",data.BTRTL);
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("WERKS",data.PERSK);
			
			//姓名
			_this.setFieldValueByFieldName("xm",data.ENAME);
			//部门
			_this.setFieldValueByFieldName("bm",data.ORGTX);
			//职位
			_this.setFieldValueByFieldName("zw",data.PLSTX);
			//人事子范围
			_this.setFieldValueByFieldName("rszfw",data.BTRTX);
			//员工子组
			_this.setFieldValueByFieldName("ygzz",data.PTEXT);
			//职级/星级
			_this.setFieldValueByFieldName("zjxj",data.SHORT3);
			//人事范围
			_this.setFieldValueByFieldName("rsfw",data.NAME1);
			//关键岗位级别
			_this.setFieldValueByFieldName("gjgwjb",data.ZZGJGWJB);
		};
		getHR_ZHRI001004(_this.getFieldValueByFieldName("ygbh"),loadLeve);
						
		//员工编号文本框
		this.$("input[name='ygbh']").click(function(){
			$.unit.open({
				title : "选择人员",
				labelField : "ygxm",
				valueField : "yggh",
				type : "MyDept",
				multiple:false, 
				afterSelect : function(retVal) {
					_this.setFieldValueByFieldName("ygbh",retVal.employeeNumber);
					_this.setFieldValueByFieldName("xm",retVal.name);							
					getHR_ZHRI001004(_this.getFieldValueByFieldName("ygbh"),loadLeve);
				},
				selectType : 4	
			});
		});
		
		//员工编号文本框
		this.$("input[name='ygbh']").focus(function(){
			this.blur();
		});
	},
	
	/**
	 *LDX_培训管理_导师制培训信息
	*/
	uf_pxgl_dszpxxx:function(){
		_this = this;
		
		//员工工号				
		var loadLeve = function(jsonobj){					
			if(jsonobj==null){
				alert("没有找到对应的数据");
				return;
			}
			
			var pt = "PT_BAPIHRS0001";
			if(jsonobj[pt].rows==="0"){
				alert("没有找到对应的数据");
				return;
			}
			var data = jsonobj[pt].row[0];
								
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("WERKS",data.WERKS);					
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("PERSK",data.PERSK);
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("BTRTL",data.BTRTL);
			//员工姓名
			_this.setFieldValueByFieldName("ygxm",data.ENAME);
			
		};
		
		//导师工号
		var loadLeve_d = function(jsonobj){					
			if(jsonobj==null){
				alert("没有找到对应的数据");
				return;
			}
			
			var pt = "PT_BAPIHRS0001";
			if(jsonobj[pt].rows==="0"){
				alert("没有找到对应的数据");
				return;
			}
			var data = jsonobj[pt].row[0];
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("WERKS",data.WERKS);					
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("PERSK",data.PERSK);
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("BTRTL",data.BTRTL);
			//导师姓名
			_this.setFieldValueByFieldName("dsxm",data.ENAME);
			
		};
		
		//员工工号文本框
		this.$("input[name='yggh']").click(function(){
			$.unit.open({
				title : "选择人员",
				type : "MyDept",
				multiple:false, 
				afterSelect : function(retVal) {
					_this.setFieldValueByFieldName("yggh",retVal.employeeNumber);					
					getHR_ZHRI001004(_this.getFieldValueByFieldName("yggh"),loadLeve);
				},
				selectType : 4	
			});
		});
		
		//员工工号文本框
		this.$("input[name='yggh']").focus(function(){
			this.blur();
		});	
		
		
		//导师工号文本框
		this.$("input[name='dsgh']").click(function(){
			$.unit.open({
				title : "选择导师",
				type : "MyDept",
				multiple:false, 
				afterSelect : function(retVal) {
					_this.setFieldValueByFieldName("dsgh",retVal.employeeNumber);					
					getHR_ZHRI001004(_this.getFieldValueByFieldName("dsgh"),loadLeve_d);
				},
				selectType : 4	
			});
		});
		
		//导师工号文本框
		this.$("input[name='dsgh']").focus(function(){
			this.blur();
		});
		
	},
	
	/**
	 *  LDX_人事管理_加班申请单(总部)
	 */
	uf_rsgl_jbsqd:function(){
		var _this = this;	
		//表单onload时，设置默认值
		var userInfo = SpringSecurityUtils.getUserDetails();
		var val = _this.getFieldValueByFieldName("SQ_WERKS");
		if(typeof val != 'undefined' && $.trim(val).length > 0){
			//return;
		}else{
			this.setFieldValueByFieldName("ygbh",userInfo.employeeNumber);
			this.setFieldValueByFieldName("xm",userInfo.userName);
		}
		
		var loadLeve = function(jsonobj){					
			if(jsonobj==null){
				alert("没有找到对应的数据");
				return;
			}
			
			var pt = "PT_BAPIHRS0001";
			if(jsonobj[pt].rows==="0"){
				//alert("没有找到对应的数据");
				return;
			}
			var data = jsonobj[pt].row[0];
			
			if(typeof val != 'undefined' && $.trim(val).length > 0){
				return;
			}	
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("SQ_BTRTX",data.BTRTX);
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("SQ_BTRTL",data.BTRTL);
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("SQ_WERKS",data.WERKS);
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("SQ_SHORT3",data.SHORT3);
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("SQ_NAME1",data.NAME1);
			//姓名
			_this.setFieldValueByFieldName("xm",data.ENAME);
			//部门
			_this.setFieldValueByFieldName("bm",data.ORGTX);
			//申请人岗位
			_this.setFieldValueByFieldName("gw",data.PLSTX);
			//员工子组
			_this.setFieldValueByFieldName("ygzz",data.PTEXT);
			
		};
		getHR_ZHRI001004(_this.getFieldValueByFieldName("ygbh"),loadLeve);
		
		//员工编号文本框
		this.$("input[name='ygbh']").click(function(){
			$.unit.open({
				title : "选择人员",
				labelField : "xm",
				valueField : "ygbh",
				type : "MyDept",
				multiple:false, 
				afterSelect : function(retVal) {
					_this.setFieldValueByFieldName("ygbh",retVal.employeeNumber);
					_this.setFieldValueByFieldName("xm",retVal.name);		

					
					getHR_ZHRI001004(_this.getFieldValueByFieldName("ygbh"),loadLeve);


				},
				selectType : 4	
			});
		});
		
		//员工编号文本框
		this.$("input[name='ygbh']").focus(function(){
			this.blur();
		});	
		
		//----------------定义动态表格事件-----------------------
		var formId = "uf_rsgl_jbxx";
		var formUuid = _this.getFormUuid(formId); 
		var ctl = _this.getSubformControl(formUuid);
						
		ctl.bind("afterInsertRow", function(rowdata){
			
			var updateRow = function(jsonobj){
				if(jsonobj==null){
					alert("没有找到对应的数据");
					return;
				}
				
				var pt = "RETURN_INFO";
				var data = jsonobj[pt];						
				var rowData = ctl.getRowData(ctl.selectedRowId);			
			
				rowData.ygbh = data.PERNR;
				rowData.xm = data.ENAME;
				rowData.gw = data.PLSTX;
				rowData.bm = data.ORGTX;
				
				_this.updateRowData(formId, rowData);
			};
			
			_this.getControl("ygbh", rowdata.id ).bind("focus", function(){
				$.unit.open({
					title : "选择人员",
					type : "MyDept",
					multiple:false, 
					afterSelect : function(retVal) {				

			
						getHR_YGLDHTWHJK(retVal.employeeNumber,"","",updateRow);


					},
					selectType : 4	
				});
				
			});
		});
		
						
	},
	
	/**
	 * LDX_人事管理_出差申请单(总部)
	 */
	uf_rsgl_ccsqd:function(){
		_this = this;	
		
		//表单onload时，设置默认值
		var userInfo = SpringSecurityUtils.getUserDetails();
		var val = _this.getFieldValueByFieldName("WERKS");
		if(typeof val != 'undefined' && $.trim(val).length > 0){
			//return;
		}else{
			this.setFieldValueByFieldName("ygbh",userInfo.employeeNumber);
			this.setFieldValueByFieldName("xm",userInfo.userName);
		}
		
		var loadLeve = function(jsonobj){					
			if(jsonobj==null){
				alert("没有找到对应的数据");
				return;
			}
			
			var pt = "PT_BAPIHRS0001";
			if(jsonobj[pt].rows==="0"){
				//alert("没有找到对应的数据");
				return;
			}
			
			var data = jsonobj[pt].row[0];
			if(typeof val != 'undefined' && $.trim(val).length > 0){
				return;
			}
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("WERKS",data.WERKS);
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("BTRTL",data.BTRTL);
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("SHORT3",data.SHORT3);
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("BTRTX",data.BTRTX);
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("NAME1",data.NAME1);
			
			//姓名
			_this.setFieldValueByFieldName("xm",data.ENAME);
			//部门
			_this.setFieldValueByFieldName("bm",data.ORGTX);
			//职位
			_this.setFieldValueByFieldName("gw",data.PLSTX);
			//员工子组
			_this.setFieldValueByFieldName("ygzz",data.PTEXT);
			
		};
		getHR_ZHRI001004(_this.getFieldValueByFieldName("ygbh"),loadLeve);
		
		//员工编号文本框
		this.$("input[name='ygbh']").click(function(){
			$.unit.open({
				title : "选择人员",
				labelField : "xm",
				valueField : "ygbh",
				type : "MyDept",
				multiple:false, 
				afterSelect : function(retVal) {
					_this.setFieldValueByFieldName("ygbh",retVal.employeeNumber);
					_this.setFieldValueByFieldName("xm",retVal.name);							
					getHR_ZHRI001004(_this.getFieldValueByFieldName("ygbh"),loadLeve);
				},
				selectType : 4	
			});
		});
		
		//员工编号文本框
		this.$("input[name='ygbh']").focus(function(){
			this.blur();
		});	
		
		//----------------定义动态表格事件-----------------------
		var formId = "uf_rsgl_ccxx";
		var formUuid = _this.getFormUuid(formId); 
		var ctl = _this.getSubformControl(formUuid);
						
		ctl.bind("afterInsertRow", function(rowdata){
			
			var updateRow = function(jsonobj){
				if(jsonobj==null){
					alert("没有找到对应的数据");
					return;
				}
				
				var pt = "RETURN_INFO";
				var data = jsonobj[pt];						
				var rowData = ctl.getRowData(ctl.selectedRowId);						
				rowData.ygbh = data.PERNR;
				rowData.xm = '{"":"'+data.ENAME+'"}';
				rowData.gw = data.PLSTX;
				rowData.bm = '{"":"'+data.ORGTX+'"}';
				
				_this.updateRowData(formId, rowData);
			};
			
			_this.getControl("ygbh", rowdata.id ).bind("focus", function(){
				$.unit.open({
					title : "选择人员",
					type : "MyDept",
					multiple:false, 
					afterSelect : function(retVal) {							
						getHR_YGLDHTWHJK(retVal.employeeNumber,"","",updateRow);
					},
					selectType : 4	
				});
				
			});
		});
		
		var sfxyydjphjd = _this.getControl("sfxyydjphjd");
		sfxyydjphjd.bind("change",function(){
			var sfxyydjphjd = _this.getControl("sfxyydjphjd").getValue();
			if(sfxyydjphjd == "是"){
				var fieldArr = ["sfzh","fybmsszt"];
				setRequiredAndUnRequired("uf_rsgl_ccydxx",fieldArr,_this,true);
			}
			if(sfxyydjphjd == "否"){
				var fieldArr = ["zhbmzg"];
				setRequiredAndUnRequired("",fieldArr,_this,true);
			}
		});
		
						
	},
	
	/**
	 *LDX_人事管理_员工奖惩表单
	*/
	uf_rsgl_ygjcbd:function(){
		_this = this;
		
		//表单onload时，设置默认值
		var userInfo = SpringSecurityUtils.getUserDetails();
		var val = _this.getFieldValueByFieldName("WERKS");
		if(typeof val != 'undefined' && $.trim(val).length > 0){
			//return;
		}else{
			//this.setFieldValueByFieldName("gh",userInfo.employeeNumber);
			//this.setFieldValueByFieldName("xm",userInfo.userName);
		}
		var loadLeve = function(jsonobj){					
			if(jsonobj==null){
				alert("没有找到对应的数据");
				return;
			}
			
			var pt = "PT_BAPIHRS0001";
			if(jsonobj[pt].rows==="0"){
				//alert("没有找到对应的数据");
				return;
			}
			var data = jsonobj[pt].row[0];
			
			if(typeof val != 'undefined' && $.trim(val).length > 0){
				return;
			}
			
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("WERKS",data.WERKS);	
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("BTRTL",data.BTRTL);
			//姓名
			_this.setFieldValueByFieldName("xm",data.ENAME);
			//部门
			_this.setFieldValueByFieldName("bm",data.ORGTX);
			//岗位
			_this.setFieldValueByFieldName("zw2",data.PLSTX);	
			//人事子范围
			_this.setFieldValueByFieldName("rszfw",data.BTRTX);
			//员工子组
			_this.setFieldValueByFieldName("ygzz",data.PTEXT);
			//设置用户userid
			_this.setFieldValueByFieldName("userid",userInfo.userId);
		};
		//getHR_ZHRI001004(_this.getFieldValueByFieldName("gh"),loadLeve);
		
		//员工工号文本框
		this.$("input[name='gh']").click(function(){
			$.unit.open({
				title : "选择人员",
				labelField : "ygxm",
				valueField : "ygbh",
				type : "MyDept",
				multiple:false, 
				afterSelect : function(retVal) {
					_this.setFieldValueByFieldName("gh",retVal.employeeNumber);
					_this.setFieldValueByFieldName("xm",retVal.name);							
					getHR_ZHRI001004(_this.getFieldValueByFieldName("gh"),loadLeve);
				},
				selectType : 4	
			});
		});
		
		//员工工号文本框
		this.$("input[name='gh']").focus(function(){
			this.blur();
		});		
	},
	
	/**
	 *LDX_人事管理_请假申请单(总部)
	*/
	userform_rsgl_qjsqd:function(){
		_this = this;
		
		//表单onload时，设置默认值
		var userInfo = SpringSecurityUtils.getUserDetails();
		var val = _this.getFieldValueByFieldName("WERKS");
		if(typeof val != 'undefined' && $.trim(val).length > 0){
			//return;
		}else{
			this.setFieldValueByFieldName("gh",userInfo.employeeNumber);
			this.setFieldValueByFieldName("qjr",userInfo.userName);
		}
		
		var loadLeve = function(jsonobj){					
			if(jsonobj==null){
				alert("没有找到对应的数据");
				return;
			}
			
			var pt = "PT_BAPIHRS0001";
			if(jsonobj[pt].rows==="0"){
				//alert("没有找到对应的数据");
				return;
			}
			var data = jsonobj[pt].row[0];
			
			if(typeof val != 'undefined' && $.trim(val).length > 0){
				return;
			}
			
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("WERKS",data.WERKS);					
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("PERSK",data.PERSK);
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("BTRTL",data.BTRTL);
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("SHORT3",data.SHORT3);
			//姓名
			_this.setFieldValueByFieldName("qjr",data.ENAME);
			//部门
			_this.setFieldValueByFieldName("bm",data.ORGTX);
			//岗位
			_this.setFieldValueByFieldName("zw",data.PLSTX);	
			//人事子范围
			_this.setFieldValueByFieldName("Range",data.BTRTX);
			
		};
		getHR_ZHRI001004(_this.getFieldValueByFieldName("gh"),loadLeve);
		
		//员工工号文本框
		this.$("input[name='gh']").click(function(){
			$.unit.open({
				title : "选择人员",
				labelField : "ygxm",
				valueField : "ygbh",
				type : "MyDept",
				multiple:false, 
				afterSelect : function(retVal) {
					_this.setFieldValueByFieldName("gh",retVal.employeeNumber);
					_this.setFieldValueByFieldName("qjr",retVal.name);							
					getHR_ZHRI001004(_this.getFieldValueByFieldName("gh"),loadLeve);
				},
				selectType : 4	
			});
		});
		
		//员工工号文本框
		this.$("input[name='gh']").focus(function(){
			this.blur();
		});		
	},
	
	/**
	 * LDX_人事管理_考勤补卡单(总部)
	 */
	uf_rsgl_kqbkd:function(){
		_this = this;
		
		//表单onload时，设置默认值
		var userInfo = SpringSecurityUtils.getUserDetails();
		var val = _this.getFieldValueByFieldName("WERKS");
		if(typeof val != 'undefined' && $.trim(val).length > 0){
			//return;
		}else{
			this.setFieldValueByFieldName("yggh",userInfo.employeeNumber);
			this.setFieldValueByFieldName("ygxm",userInfo.userName);
		}
		var loadLeve = function(jsonobj){					
			if(jsonobj==null){
				alert("没有找到对应的数据");
				return;
			}
			
			var pt = "PT_BAPIHRS0001";
			if(jsonobj[pt].rows==="0"){
				//alert("没有找到对应的数据");
				return;
			}
			var data = jsonobj[pt].row[0];
			if(typeof val != 'undefined' && $.trim(val).length > 0){
				 
				return;
			}
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("WERKS",data.WERKS);
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("BTRTL",data.BTRTL);
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("PERSK",data.PERSK);
			//员工姓名
			_this.setFieldValueByFieldName("ygxm",data.ENAME);
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("SHORT3",data.SHORT3);
		};
		getHR_ZHRI001004(_this.getFieldValueByFieldName("yggh"),loadLeve);
		
		//员工工号文本框
		this.$("input[name='yggh']").click(function(){
			$.unit.open({
				title : "选择人员",
				labelField : "ygxm",
				valueField : "yggh",
				type : "MyDept",
				multiple:false, 
				afterSelect : function(retVal) {
					_this.setFieldValueByFieldName("yggh",retVal.employeeNumber);
					_this.setFieldValueByFieldName("ygxm",retVal.name);							
					getHR_ZHRI001004(_this.getFieldValueByFieldName("yggh"),loadLeve);
				},
				selectType : 4	
			});
		});
		
		//员工工号文本框
		this.$("input[name='yggh']").focus(function(){
			this.blur();
		});		
	}
	,
	/**
	 * IT耗材领用登记成本中心
	 */
	uf_x5_xzgl_wpsq:function(){
		var _this = this;
		this.getControl("sscbzx").bind("afterDialogSelect", function(){

			_this.getControl("cbzxdm").$editableElem.keydown(function(){ 
				return false;				
			});
			_this.getControl("cbzxdm").setReadOnly(false);					
		});
	},
	
	// 生产管理_策略备库申请单
	uf_scgl_clbksqd:function(){
		var _this = this;
		var schedule_fileupload_id = 'fileupload_schedule_ipt'; 
		var schedule_fileupload = new WellFileUpload(schedule_fileupload_id);
		schedule_fileupload.init(false,  $('#fileupload_td'),  false, false , []);
		_this.$(".editableClass[name$=___cgsqdh],.editableClass[name$=___cghth]").live("change", function(e){
			// 从表id
			var formId = "uf_scgl_clbksqdxx";
			// 从表formUuid
			var formUuid = _this.getFormUuid(formId); 
			// 从表操作控件
			var ctl = _this.getSubformControl(formUuid);
			// 从表选中行数据
			var rowData = ctl.getRowData(ctl.selectedRowId);
			if(rowData.cgsqdh != '' && rowData.cghth != ''){
				var sql = "select a.eindt htjq,b.eindt zxhqjq from eket a,ekes b where a.ebeln = b.ebeln"+
					" and a.ebelp = b.ebelp and a.ebeln = '" + rowData.cgsqdh 
					+ "' and a.ebelp = '" + rowData.cghth + "'";
				
				$.ajax({
					type : "post",
					data : {
						"dataSourceId":"SAP",
						"sql" :sql
					},
					async : false,
					url : ctx + "/special/dyform/sap",
					success : function(re) {
						var arr = eval('(' + re + ')');
						
					}
				});
		
			}
		});
		// 
		this.$("#drexcel").click(function(){
			_this.$("#upload").trigger("click");
		});
		
		this.$("#upload").change(function(){
			
		});
		
	},
	
	/**
	 * Leedarson_生产管理_风险备库申请单 
	 */
	uf_scgl_fxbksqd:function(){
		
		var _this = this;
		
		this.$("#dcexcel").click(function(){
			var formUuid = _this.getFormUuid();
			var dataUuid = _this.getDataUuid();
			if(dataUuid==''||dataUuid==undefined){
				oAlert2("请先保存！");
			}else{
				window.location.href = ctx + "/special/dyform/standbyExport?formUuid=" +
					formUuid + "&dataUuid=" + dataUuid;
			}
		});
		

	},
	/**
	 * Leedarson_业务管理_CFL样品签发申请 (1.0)
	 */
	uf_leedarson_ywgl_cflypqfsq:function(){
		
		var _this = this;
		var childFormId = "uf_ywgl_cflypqfsq";//CFL样品签发申请 动态表格
		$("#chaxun").bind("click",function(){
			//crmid
			var crmidValue = _this.getControl("crmid").getValue();
			
			if(crmidValue == "" || crmidValue == null || typeof crmidValue == "undifined"  ){
				oAlert("CRM AccountID不能为空！");
			}else{
				//var _customer_code = _this.getControl("customer_code");
				var _potential_customer_code = _this.getControl("khdm");//客户代码
				var _customer_potential_rank = _this.getControl("khqljb");//客户潜力级别
				
				if(crmidValue != null && typeof crmidValue != "undifined"){
					$.ajax({
						async : true,
						url : ctx + "/sample/issue?crmidValue=" + crmidValue, 
						success:function(data) {
							var arr = eval(data);
							if(arr.length > 0){
								var customer_code = arr[0].customer_code;
								var potential_customer_code = arr[0].potential_customer_code;
								var customer_potential_rank = arr[0].customer_potential_rank;
								//_customer_code.setValue(customer_code);
								_potential_customer_code.setValue(customer_code);
								_customer_potential_rank.setValue(customer_potential_rank);
							}
						
						}
					});
				}
			}
			
			
		});
		//交期赋值按钮功能实现
		jqfzCallFunction(_this,"#jqfz",childFormId);		
	},
	
	/**
	 * Leedarson_业务管理_LED样品签发申请 (1.0)
	 */


	uf_ywgl_ledypqfsq:function(){
		var _this = this;
		var childFormId = "uf_ywgl_ledypqfsqxx";//LED样品签发申请 动态表格
		$("#chaxun").bind("click",function(){
			//crmid
			var crmidValue = _this.getControl("crmid").getValue();
			
			if(crmidValue == "" || crmidValue == null || typeof crmidValue == "undifined"  ){
				oAlert("CRM AccountID不能为空！");
			}else{
				//var _customer_code = _this.getControl("customer_code");
				var _potential_customer_code = _this.getControl("khdm");//客户代码
				var _customer_potential_rank = _this.getControl("khqljb");//客户潜力级别
				
				if(crmidValue != null && typeof crmidValue != "undifined"){
					$.ajax({
						async : true,
						url : ctx + "/sample/issue?crmidValue=" + crmidValue, 
						success:function(data) {
							var arr = eval(data);
							if(arr.length > 0){
								var customer_code = arr[0].customer_code;
								var potential_customer_code = arr[0].potential_customer_code;
								var customer_potential_rank = arr[0].customer_potential_rank;
								//_customer_code.setValue(customer_code);
								_potential_customer_code.setValue(customer_code);
								_customer_potential_rank.setValue(customer_potential_rank);
							}
						
						}
					});
				}
			}
		});
		
		//交期赋值按钮功能实现
		jqfzCallFunction(_this,"#jqfz",childFormId);
		
	
	},
	/**
	 * LF样品单签发申请
	 */
	uf_leedarson_ywgl_lfypqfsq:function(){
		var _this = this;
		$("#chaxun").bind("click",function(){
			//crmid
			var crmidValue = _this.getControl("crmid").getValue();
			
			if(crmidValue == "" || crmidValue == null || typeof crmidValue == "undifined"  ){
				oAlert("CRM AccountID不能为空！");
			}else{
				//var _customer_code = _this.getControl("customer_code");
				var _potential_customer_code = _this.getControl("khdm");//客户代码
				var _customer_potential_rank = _this.getControl("khqljb");//客户潜力级别
				
				if(crmidValue != null && typeof crmidValue != "undifined"){
					$.ajax({
						async : true,
						url : ctx + "/sample/issue?crmidValue=" + crmidValue, 
						success:function(data) {
							var arr = eval(data);
							if(arr.length > 0){
								var customer_code = arr[0].customer_code;
								var potential_customer_code = arr[0].potential_customer_code;
								var customer_potential_rank = arr[0].customer_potential_rank;
								//_customer_code.setValue(customer_code);
								_potential_customer_code.setValue(customer_code);
								_customer_potential_rank.setValue(customer_potential_rank);
							}
						
						}
					});
				}
			}
			
			
		});
		var childFormId = "uf_ldx_ywgl_lfypqfsqxx";//LF样品签发申请 动态表格
		//交期赋值按钮功能实现
		jqfzCallFunction(_this,"#jqfz",childFormId);
	},
	
	
	
	/**
	 * 对外邮件地址申请
	 */
	uf_leedarson_xxgl_dwyjdzsq:function(){
		//获取用户的信息
		var userInfo = SpringSecurityUtils.getUserDetails();
		var _this = this;
		var formId = "uf_xxgldwyjdzsq_dtbd";//对外邮箱申请动态表格
		var formUuid = _this.getFormUuid(formId); 
		var ctl = _this.getSubformControl(formUuid);
		
		ctl.bind("afterInsertRow", function(rowdata){
			var rowDataUuid = rowdata.id;
			var controlYjdz = _this.getControl("yjdz",rowDataUuid);
			//emial控件值改变时触发的事件
			controlYjdz.bind("change",function(){
				
				var email = controlYjdz.getValue();//获得email的值
				$.ajax({
					async : true,
					dataType:"text",
					url : ctx + "/ehr/dwyx?email=" + email,
					success:function(data) {
						var _data = data;
						if(data == "0"){
							oAlert("验证成功!");
						}else if(data == "1"){
							oAlert("邮件地址错误!");
						}else if(data == "2"){
							oAlert("免费邮箱域不能添加!");
						}else if(data == "3"){
							oAlert("邮箱已添加!");
						}else if(data == "4"){
							oAlert("数据库出错!");
						}else if(data == "5"){
							oAlert("参数为空!");
						}
					},
					error:function(msg){
						alert(msg);
					}
				});
			});
			
		});
	},
	/**
	 * Leedarson_信息管理_信息服务单
	 */
	uf_leedarson_xxgl_xxfwd:function(){
		var tableSelector = "user_select_table";
		var _this = this;
		this.$("#btn_xzzhhx").click(function(){
			var allRowData = _this.getAllRowData({"id":"uf_leedarson_xxgl_xxfwdxx"});
			if(typeof allRowData == "undefined" || allRowData.length <= 0){
				return false;
			}
			var params = {};
			for(var i=0;i<allRowData.length;i++){
				var rowData = allRowData[i];
				params.ygbh = rowData.gh;
				params.xm = encodeURIComponent(rowData.xm);
				params.systype = rowData.xtlx;
				params.account =  encodeURIComponent(rowData.xzdzh);
				if(params.account == "" || params.systype == "{}"){
					oAlert("请填写系统类型和新增帐号！");
					return false;
				};
				$.ajax({
					async : false,
					url : ctx + "/sample/account",
					data : params,
					success:function(data) {
						oAlert("新增账户回写成功！");
					},
					erroe:function(data){
						oAlert("新增账户回写失败！");
					}
				});
			}
		});
		
		this.$("#btn_hqygxx").click(function() {
			var xm = _this.getControl("xzzhhxxm").getValue();
			$.ajax({
				async : true,
				url : ctx + "/sample/getUsers?xm=" + urlencode(urlencode(xm)),
				success : function(data) {
					if(data == null || data.length <= 0 ){
						oAlert("该用户信息不存在！");
						return false;
					};
					if(data.length == 1){
						var row = data[0];
						var rowdata = {};
						rowdata.xm = row.name;
						rowdata.gh = row.yggh;
						_this.addRowData("uf_leedarson_xxgl_xxfwdxx",rowdata);
						return true;
					};
					var html = "<div class='dialog_form_content' style='line-height: 4px;'><table width='96%'>"
					+ "<tr>" + "<td width='4%'/><td width='10%'>序号</td>" + "<td width='18%'>姓名</td>" + "<td width='18%'>工号</td>"
					+ "<td width='25%'>应聘部门</td>" + "<td width='25%'>应聘岗位</td></tr>";
					for ( var i = 0; i < data.length; i++) {
						html = html + "<tr class='odd'><td/>";
						html = html + "<td><input type='radio' name='person' value='" + i + "'></td>";
						html = html + "<td>" + data[i].name + "</td>";
						html = html + "<td>" + data[i].yggh + "</td>";
						html = html + "<td>" + data[i].department + "</td>";
						html = html + "<td>" + data[i].job + "</td>";
						html = html + "</tr>";
					}
					html = html + "</table></div>";
					json = {
						title : "人员选择",
						closeOnEscape : true,
						draggable : true,
						resizable : true,
						height : 320,
						width : 620,
						content : html,
						buttons: {
							"确定":function(){
								var sel = $("input[name='person']:checked").val();
								// alert(JSON.stringify(data[sel]));
								var row = data[sel];
								var rowdata = {};
								rowdata.xm = row.name;
								rowdata.gh = row.yggh;
								_this.addRowData("uf_leedarson_xxgl_xxfwdxx",rowdata);
								closeDialog();
							}
						},
						defaultBtnName : "关闭"
					};
					showDialog(json);
				},
				error : function(result) {
					oAlert("获取人员信息出错！");
				}
			});
		});
		
		//姓名调取部门,职位
		if(_this.getFieldValueByFieldName("xm")=='{}'){
		    var userInfo = SpringSecurityUtils.getUserDetails();
			_this.getControl("xm").setValue(userInfo.userName);//姓名
			_this.getControl("gw").setValue(userInfo.jobName);//职位名称
			_this.getControl("llrbm").setValue(userInfo.departmentPath);//部门名称
		}
		this.getControl("xm").bind("afterOrgInfo", function(result){
			console.log(JSON.stringify(result));
			_this.getControl("gw").setValue(result.mainJobName);
			var dep = {};
			dep[result.mainDepartmentId] = result.mainDepartmentFullPath; 
			_this.setFieldValueByFieldName("llrbm", JSON.stringify(dep));			
		}, true);
	},
	//名片印制申请单
	uf_leedarson_xzgl_mpyz:function(){
		var _this = this;
		var control =_this.getControl("mplx");
		control.bind("afterSetValue", function(value){
			_this.uf_leedarson_xzgl_mpyz_receiverChange();
	   	   }, true);
	   	this.uf_leedarson_xzgl_mpyz_receiverChange();   
	},
	//灯具项目申请单（Fixture Charter）
	uf_leedarson_djxmsqdfc:function(){
		var _this = this;
						
		//单选框事件  对于隐藏的字段，如nunber1，需在源码中为nunber1增加ID，具体参见测试表单1 
		//alert(2);
		var control =_this.getControl("cpdl");
		control.bind("afterSetValue", function(value){
			_this.uf_leedarson_djxmsqdfc_receiverChange();
	   	   }, true);
		
		this.uf_leedarson_djxmsqdfc_receiverChange();
		$("select[name=ddzk]").change(function(){
			var ddzk = $("select[name=ddzk]").val();
			if(ddzk=="1"){
				_this.setFieldValueByFieldName("yxj","重要紧急");
			}else if(ddzk=="2"){
				_this.setFieldValueByFieldName("yxj","优先");
			}else if(ddzk=="3"){
				_this.setFieldValueByFieldName("yxj","一般");
			}
		});
	},
	uf_leedarson_xzgl_mpyz_receiverChange:function(){
		var _this = this;
		var control =_this.getControl("mplx");
		var lsPrivate =  control.getValue();
   		 if(lsPrivate == null || lsPrivate.length == 0){
   			//lsPrivate = "0";
   			return;
   		 }
   		 var imgDiv = "";
   		 
   		 switch(lsPrivate){
   		 case "1":
   		 	imgDiv = "ID_TypeA";
   		 	break;
   		 case "2":
   		 	imgDiv = "ID_TypeB";
   		 	break;
   		case "3":
   		 	imgDiv = "ID_TypeC";
   		 	break;
   		case "4":
   		 	imgDiv = "ID_TypeD";
   		 	break;
   		case "5":
   			imgDiv = "ID_TypeE";
   		 	break;
   		case "6":
   			imgDiv = "ID_TypeF"; 
   		 	break;
   		case "7":
   			imgDiv = "ID_TypeG";
   		 
   		 	break;
   		case "8":
   			imgDiv = "ID_TypeH"; 
   		 	break;
   		 default:
   			 break;
   		 } 
   		 
   		_this.$("tr[id^='ID_Type']").hide();
   		_this.$("#" + imgDiv).find("img").each(function(){
   			var src = $(this).attr("src");
   			src = src.replace("..", contextPath);
   			$(this).attr("src", src); 
   			$(this).css("max-width", "600px");
   			$(this).css("max-height", "500px");
   		});
   		_this.$("#" + imgDiv).show();
   		
   	    
	},
	uf_leedarson_djxmsqdfc_receiverChange:function(){
		var _this = this;
		var control =_this.getControl("cpdl");
		var value =  control.getValue();
   		 if(value == null || value.length == 0){
   			_this.$("#tsdsjghb").hide();
   	    	_this.$("#mbdsjghb").hide();
   	    	_this.$("#xddsjghb").hide();
   		 }else if(value == "1"){ 
   			_this.$("#tsdsjghb").show();
   			_this.$("#mbdsjghb").hide();
   	    	_this.$("#xddsjghb").hide();
		 }else if(value == "2"){
			_this.$("#tsdsjghb").hide();
   	    	_this.$("#mbdsjghb").hide();
   	    	_this.$("#xddsjghb").show();
		 }else if(value == "3"){
			_this.$("#tsdsjghb").hide();
	   	    _this.$("#mbdsjghb").show();
	   	    _this.$("#xddsjghb").hide();
		 }else{
			_this.$("#tsdsjghb").hide();
	   	    _this.$("#mbdsjghb").hide();
	   	    _this.$("#xddsjghb").hide();
		 }
	},
	
	/**
	 * LDX_人事管理_公出申请单(总部)
	 */
	uf_rsgl_gcsqd:function(){

		var _this = this;
		
		//表单onload时，设置默认值
		var userInfo = SpringSecurityUtils.getUserDetails();
		var val = _this.getFieldValueByFieldName("WERKS");
		if(typeof val != 'undefined' && $.trim(val).length > 0){
			//return;
		}else{
			this.setFieldValueByFieldName("ygbh",userInfo.employeeNumber);
			this.setFieldValueByFieldName("xm",userInfo.userName);
		}
			
		var loadLeve = function(jsonobj){					
			if(jsonobj==null){
				alert("没有找到对应的数据");
				return;
			}
			
			var pt = "PT_BAPIHRS0001";
			if(jsonobj[pt].rows==="0"){
				alert("没有找到对应的数据");
				return;
			}
			var data = jsonobj[pt].row[0];
			if(typeof val != 'undefined' && $.trim(val).length > 0){
				 
				return;
			}
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("WERKS",data.WERKS);
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("BTRTL",data.BTRTL);
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("SHORT3",data.SHORT3);
			//姓名
			_this.setFieldValueByFieldName("ygxm",data.ENAME);
			//部门
			_this.setFieldValueByFieldName("bm",data.ORGTX);
			//岗位
			_this.setFieldValueByFieldName("ccrzw",data.PLSTX);
			//员工子组
			_this.setFieldValueByFieldName("ygzz",data.PTEXT);			
			
		};
		getHR_ZHRI001004(_this.getFieldValueByFieldName("ygbh"),loadLeve);
		
		//员工工号文本框
		this.$("input[name='ygbh']").click(function(){
			$.unit.open({
				title : "选择人员",
				labelField : "ygxm",
				valueField : "ygbh",
				type : "MyDept",
				multiple:false, 
				afterSelect : function(retVal) {
					_this.setFieldValueByFieldName("ygbh",retVal.employeeNumber);
					_this.setFieldValueByFieldName("xm",retVal.name);							
					getHR_ZHRI001004(_this.getFieldValueByFieldName("ygbh"),loadLeve);
				},
				selectType : 4	
			});
		});
		
		//员工工号文本框
		this.$("input[name='ygbh']").focus(function(){
			this.blur();
		});		
		
	},
	
	/**
	 * Leedarson_人事管理_人员到岗通知单 (1.0)
	 */
	uf_leedarson_rsgl_rydgtzd:function(){
		var _this = this;
		this.$("#xzzhhx").click(function(){
			var allRowData = _this.getAllRowData({"id":"uf_leedarson_rsgl_rydgtzddt"});
			if(typeof allRowData == "undefined" || allRowData.length <= 0){
				return false;
			}
			var params = {};
			var xm = _this.getControl("xm").getValue();
			var ygbh = _this.getControl("ygbh").getValue();
			for(var i = 0; i < allRowData.length; i++){
				var rowData = allRowData[i];
				params.ygbh = ygbh;
				params.xm = encodeURIComponent(xm);
				params.systype = rowData.xtlx;
				params.account =  encodeURIComponent(rowData.xzdzh);
				if(params.account == "" || params.systype == "{}"){
					oAlert("请填写系统类型和新增帐号！");
					return false;
				};
				$.ajax({
					async : false,
					url : ctx + "/sample/account",
					data : params,
					success:function(data) {
						oAlert("新增账户回写成功！");
					},
					erroe:function(data){
						oAlert("新增账户回写失败！");
					}
				});
			}
		});
		
		this.$("#hqygbh").click(function() {
			var xm = _this.getControl("xm").getValue();
			$.ajax({
				async : true,
				url : ctx + "/sample/getUsers?xm=" + urlencode(urlencode(xm)),
				success : function(data) {
					if(data == null || data.length <= 0 ){
						oAlert("该用户信息不存在！");
						return false;
					};
					if(data.length == 1){
						var row = data[0];
						_this.getControl("xm").setValue(row.name);
						_this.getControl("ygbh").setValue(row.yggh);
						return true;
					};
					var html = "<div class='dialog_form_content' style='line-height: 4px;'><table width='96%'>"
					+ "<tr>" + "<td width='4%'/><td width='10%'>序号</td>" + "<td width='18%'>姓名</td>" + "<td width='18%'>工号</td>"
					+ "<td width='25%'>应聘部门</td>" + "<td width='25%'>应聘岗位</td></tr>";
					for ( var i = 0; i < data.length; i++) {
						html = html + "<tr class='odd'><td/>";
						html = html + "<td><input type='radio' name='person' value='" + i + "'></td>";
						html = html + "<td>" + data[i].name + "</td>";
						html = html + "<td>" + data[i].yggh + "</td>";
						html = html + "<td>" + data[i].department + "</td>";
						html = html + "<td>" + data[i].job + "</td>";
						html = html + "</tr>";
					}
					html = html + "</table></div>";
					json = {
						title : "人员选择",
						closeOnEscape : true,
						draggable : true,
						resizable : true,
						height : 320,
						width : 620,
						content : html,
						buttons: {
							"确定":function(){
								var sel = $("input[name='person']:checked").val();
								// alert(JSON.stringify(data[sel]));
								var row = data[sel];
								_this.getControl("xm").setValue(row.name);
								_this.getControl("ygbh").setValue(row.yggh);
								closeDialog();
							}
						},
						defaultBtnName : "关闭"
					};
					showDialog(json);
				},
				error : function(result) {
					oAlert("获取人员信息出错！");
				}
			});
		});
	},
	
	/**
	 * 信息管理_OA问题反馈
	 * add by heshi 20141023
	 */
	uf_leedarson_xxgl_oawtfk:function(){
		//员工工号文本框
		var _this = this;
		this.$("input[name='xm']").click(function(){
			$.unit.open({
				title : "选择人员",
				labelField : "xm",
				valueField : "",
				type : "MyUnit",
				multiple:false, 
				afterSelect : function(retVal) {
					_this.setFieldValueByFieldName("xm",retVal.name);							
					$.ajax({
					   type     : "post",
					   async    : false,
					   dataType : "json",
					   url      : ctx + "/hrsupport/getUserDetail?id=" + retVal.id, 
					   success  : function(result){
						   _this.setFieldValueByFieldName("ssbm",result[0].dep);
						   _this.setFieldValueByFieldName("sszw",result[0].job);
						}
				    });	
				},
				selectType : 4	
			});
		});
	},
	
	uf_ldx_url_bdmb:function(){
		if(WorkFlow == null){
			return;
		}
		var autoSubmit = $("input[name=auto_submit]", "#wf_form").val();
		if (StringUtils.isNotBlank(autoSubmit) && "true" === autoSubmit) {
			return;
		}
		var _this = this;
		var workData = WorkFlow.getWorkData();
		if (workData != null && StringUtils.isNotBlank(WorkFlow.getTaskInstUuid())
				&& workData.formUuid == "93cb7a2c-7e95-440a-8c59-d0213bb9da89" && workData.isFirstTaskNode == "true") {
			var dyformSelector = WorkFlow.getDyformSelector();
			var urlValue = _this.getFieldValueByFieldName("item_url");
			var wp = WorkFlow.getWorkProcess();
			if (StringUtils.isNotBlank(urlValue) && wp != null && wp["previous"] != null) {
				var href = ctx
						+ "/cms/cmspage/readPage?uuid=efda735d-7fcf-4911-8d80-cabaa7f49198&ContentArea=33608e1a-a6f1-4a46-b48e-aab5cc5e51dc&url="
						+ urlValue;
				window.location.href = href;
			}
		}
	},
	/**
	 * LDX_薪资管理_福利金申请单 (1.0)
	 */
	uf_xzgl_fljsqd:function(){
		_this = this;
		//表单onload时，设置默认值
		var userInfo = SpringSecurityUtils.getUserDetails();
		var val = _this.getFieldValueByFieldName("WERKS");
		if(typeof val != 'undefined' && $.trim(val).length > 0){
			//return;
		}else{
			this.setFieldValueByFieldName("sqrgh",userInfo.employeeNumber);
			//this.setFieldValueByFieldName("ygxm",userInfo.userName);
		}
		
		var loadLeve = function(jsonobj){					
			if(jsonobj==null){
				//alert("没有找到对应的数据");
				return;
			}
			
			var pt = "PT_BAPIHRS0001";
			if(jsonobj[pt].rows==="0"){
				//alert("没有找到对应的数据");
				return;
			}
			var data = jsonobj[pt].row[0];

			if(typeof val != 'undefined' && $.trim(val).length > 0){
				return;
			}
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("WERKS",data.WERKS);
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("BTRTL",data.BTRTL);
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("PERSK",data.PERSK);
			//员工姓名
			_this.setFieldValueByFieldName("ygxm",data.ENAME);
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("SHORT3",data.SHORT3);
		};
		
		var loadLeve2 = function(jsonobj){					
			if(jsonobj==null){
				//alert("没有找到对应的数据");
				return;
			}
			//转正评估申请表中的计划转正日期设置值
			
			//Domino表单中隐藏域
			_this.setFieldValueByFieldName("jhzzrq",jsonobj.PO_TERMN);
			_this.setFieldValueByFieldName("rzrq",jsonobj.PO_BEGDA);
			
		};
		
		
		getHR_ZHRI001004(userInfo.employeeNumber,loadLeve);
		getHR_ZHRI0060(userInfo.employeeNumber,loadLeve2);
		
		//员工工号文本框
		this.$("input[name='yggh']").click(function(){
			$.unit.open({
				title : "选择人员",
				labelField : "ygxm",
				valueField : "yggh",
				type : "MyDept",
				multiple:false, 
				afterSelect : function(retVal) {
					_this.setFieldValueByFieldName("yggh",retVal.employeeNumber);
					_this.setFieldValueByFieldName("ygxm",retVal.name);							
					getHR_ZHRI001004(userInfo.employeeNumber,loadLeve);
					getHR_ZHRI0060(userInfo.employeeNumber,loadLeve2);
				},
				selectType : 4	
			});
		});
	},
	
	/**
	 * Leedarson_采购管理_设备委外维修申请 (1.0)
	 */
	uf_leedarson_cggl_sbwwwxsq:function(){
		var _this = this;
		var formId = "uf_cggl_sbwwwxsq_dtbd";//Leedarson_采购管理_设备委外维修申请(动态表单) (1.0)
		var formUuid = _this.getFormUuid(formId); 
		var ctl = _this.getSubformControl(formUuid);
		
		ctl.bind("afterInsertRow", function(rowdata){
			rowDataUuid = rowdata.id;
			var jhrq = _this.getControl("jhrq",rowDataUuid);//交货日期
			var wxddksrq = _this.getControl("wxddksrq",rowDataUuid);//维修订单开始日期
			
			jhrq.bind("afterSetValue",function(){
				var jhrqValue = jhrq.getValue();
				var wxddksrqValue = wxddksrq.getValue();
				if(jhrqValue != null && jhrqValue != "" && wxddksrqValue != null && wxddksrqValue != "" ){
					sDateAndeDateCompare(wxddksrqValue,jhrqValue,"维修订单开始日期","交货日期");
				}
				
			},true);
			
			wxddksrq.bind("afterSetValue",function(){
				var jhrqValue = jhrq.getValue();
				var wxddksrqValue = wxddksrq.getValue();
				if(jhrqValue != null && jhrqValue != "" && wxddksrqValue != null && wxddksrqValue != "" ){
					sDateAndeDateCompare(wxddksrqValue,jhrqValue,"维修订单开始日期","交货日期");
				}
			},true);
			
		});
	},
	
	uf_test1111:function(){
		//sDateAndeDateCompare("2014/11/12","2014/08/13","开始日期不能答应接受日期");
	},
	
	//工作交办
	userform_tylc_gzjb:function(){
		var _this = this;
		if(_this.isCreate()){
			var userInfo = SpringSecurityUtils.getUserDetails();
			 var userObj = {};
			 userObj[userInfo.userId] = userInfo.userName;
			_this.setFieldValueByFieldName("xm", JSON.stringify(userObj));//姓名
			_this.getControl("jbrzw").setValue(userInfo.jobName);//职位名称
			_this.getControl("jbrbm").setValue(userInfo.departmentPath);//部门名称
		}
		this.getControl("xm").bind("afterOrgInfo", function(result){
			console.log(JSON.stringify(result));
			_this.getControl("jbrzw").setValue(result.mainJobName);
			var dep = {};
			dep[result.mainDepartmentId] = result.mainDepartmentFullPath; 
			_this.setFieldValueByFieldName("jbrbm", JSON.stringify(dep));			
		}, true);		
	},
	//内销请求事项审批表
	uf_xzl_qqsxspb:function(){
		var _this = this;
		if(_this.getFieldValueByFieldName("xm")=='{}'){
			var userInfo = SpringSecurityUtils.getUserDetails();
			_this.getControl("xm").setValue(userInfo.userName);//姓名
			_this.getControl("csrzw").setValue(userInfo.jobName);//职位名称
			_this.getControl("csbm").setValue(userInfo.departmentPath);//部门名称
		}
		this.getControl("xm").bind("afterOrgInfo", function(result){
			console.log(JSON.stringify(result));
			_this.getControl("csrzw").setValue(result.mainJobName);
			var dep = {};
			dep[result.mainDepartmentId] = result.mainDepartmentFullPath; 
			_this.setFieldValueByFieldName("csbm", JSON.stringify(dep));			
		}, true);		
	},
	
	
	//工作确认
	userform_tylc_gzqr:function(){
		var _this = this;
		if(_this.getFieldValueByFieldName("xm")=='{}'){
		var userInfo = SpringSecurityUtils.getUserDetails();
		_this.getControl("xm").setValue(userInfo.userName);//姓名
		_this.getControl("tdrzw").setValue(userInfo.jobName);//职位名称
		_this.getControl("tdrbm").setValue(userInfo.departmentPath);//部门名称
		}
		this.getControl("xm").bind("afterOrgInfo", function(result){
			console.log(JSON.stringify(result));
			_this.getControl("tdrzw").setValue(result.mainJobName);
			var dep = {};
			dep[result.mainDepartmentId] = result.mainDepartmentFullPath; 
			_this.setFieldValueByFieldName("tdrbm", JSON.stringify(dep));			
		}, true);		
	},
	//借款申请
	 uf_dj_cwl_jksq:function(){
			var _this = this;		
			//金额大写
			_this.$("#btn_jedx").click(function(){
				_this.setFieldValueByFieldName("jedx",uppercaseMoney(_this.getFieldValueByFieldName("xjjkje")));
			});
			//人员姓名调取部门，职位
			if(_this.getFieldValueByFieldName("xm")=='{}'){
			var userInfo = SpringSecurityUtils.getUserDetails();
			_this.getControl("xm").setValue(userInfo.userName);//姓名
			_this.getControl("ssgw").setValue(userInfo.jobName);//职位名称
			_this.getControl("ssbm").setValue(userInfo.departmentPath);//部门名称
			}
			this.getControl("xm").bind("afterOrgInfo", function(result){
				console.log(JSON.stringify(result));
				_this.getControl("ssgw").setValue(result.mainJobName);
				var dep = {};
				dep[result.mainDepartmentId] = result.mainDepartmentFullPath; 
				_this.setFieldValueByFieldName("ssbm", JSON.stringify(dep));			
			}, true);
	},
	//Leedarson_固定资产_固定资产借用申请表（固定资产借用单）
	uf_leedarson_gdzc_gdzcjysqb:function(){
		var _this = this;
		this.getControl("syr").bind("afterOrgInfo", function(result){
			console.log(JSON.stringify(result));
		_this.getControl("syrgw").setValue(result.mainDepartmentFullPath+'/'+result.mainJobName);			
		}, true);
		this.getControl("yzrr").bind("afterOrgInfo", function(result){
			console.log(JSON.stringify(result));
			_this.getControl("yzrrgw").setValue(result.mainDepartmentFullPath+'/'+result.mainJobName);		
		}, true);
	},

	
	
	/**
	 * C类订单变更申请
	 */
	uf_ywgl_clddbgsq:function(){
		//交期变更  更后船交期赋值按钮实现
		var _this = this;
		var childFormId = "uf_ywgl_clddbgnr";
		_this.$("#ghcjffz").click(function(){
			
			var formUuid = _this.getFormUuid(childFormId); 
			var ctl = _this.getSubformControl(formUuid);
			var forminfo = {};
			forminfo.id = childFormId;
			var allRowData = _this.getAllRowData(forminfo);//获取所有的数据
			if(allRowData != null && allRowData.length >1){
				var theFirstRowData = allRowData[0];
				var finishDate = theFirstRowData.ghcjq;//得到第一条更后完工数据的日期
				
				for(var i=0;i<allRowData.length;i++){
					var uuid = allRowData[i+1].uuid;//数据uuid
					var id = allRowData[i+1].id;//数据id
					var json = {
							"ghcjq":finishDate,
							"id":id, 
							"uuid":uuid}
					_this.updateRowData(childFormId, json);//更新行数据
				}
			}
			
		});
		//C类订单变更日期  更后验货日期按钮实现
		_this.$("#ghyhjqfz").click(function(){
			
			var formUuid = _this.getFormUuid(childFormId); 
			var ctl = _this.getSubformControl(formUuid);
			var forminfo = {};
			forminfo.id = childFormId;
			var allRowData = _this.getAllRowData(forminfo);//获取所有的数据
			if(allRowData != null && allRowData.length >1){
				var theFirstRowData = allRowData[0];
				var finishDate = theFirstRowData.ghyhrq;//得到第一条更后完工数据的日期
				
				for(var i=0;i<allRowData.length;i++){
					var uuid = allRowData[i+1].uuid;//数据uuid
					var id = allRowData[i+1].id;//数据id
					var json = {
							"ghyhrq":finishDate,
							"id":id, 
							"uuid":uuid}
					_this.updateRowData(childFormId, json);//更新行数据
				}
			}
			
		});
	},
	//班车乘坐申请单
	uf_leedarson_xzgl_bcczsq:function(){
		var _this = this;
						
		var control =_this.getControl("sqzl");
		control.bind("afterSetValue", function(value){
			_this.uf_leedarson_xzgl_bcczsq_receiverChange();
	   	   }, true);
		
		this.uf_leedarson_xzgl_bcczsq_receiverChange();
		
	}, 
	uf_leedarson_xzgl_bcczsq_receiverChange:function(){
		var _this = this;
		var control =_this.getControl("sqzl");
		var value =  control.getValue();
   		 if(value == null || value.length == 0){
   		    _this.$("#gdccsj").hide();
   	    	_this.$("#lsccsj").hide();
   		 }else if(value == "1"){ 
			_this.$("#gdccsj").show();
			_this.$("#lsccsj").hide();
		}else{
		    _this.$("#gdccsj").hide();
   	    	_this.$("#lsccsj").show();

		}
	},
	//LED智能灯技术规格书新:根据电压类型隐藏
	uf_ledzndjsggsx:function(){
		var _this = this;
						
		//单选框事件  对于隐藏的字段，如nunber1，需在源码中为nunber1增加ID，具体参见测试表单1 
		//alert(2);
		var control1 =_this.getControl("dylx");
		control1.bind("afterSetValue", function(value){
			_this.uf_ledzndjsggsx_receiverChange1();
	   	   }, true);
		
		this.uf_ledzndjsggsx_receiverChange1();
		
		var control2 =_this.getControl("tgtslx");
		control2.bind("afterSetValue", function(value){
			_this.uf_ledzndjsggsx_receiverChange2();
	   	   }, true);
		
		this.uf_ledzndjsggsx_receiverChange2();
	},   	

	uf_ledzndjsggsx_receiverChange1:function(){
		var _this = this;
		var control =_this.getControl("dylx");
		var value =  control.getValue();
   		 if(value == null || value.length == 0){
   		_this.$("#dycy").show();
   	    	_this.$("#dyky").hide();
   		 }else if(value == "1"){ 
			_this.$("#dycy").show();
		        _this.$("#dyky").hide();
		}else{
			_this.$("#dyky").show();
                            _this.$("#dycy").hide();
		}
	},
	uf_ledzndjsggsx_receiverChange2:function(){
		var _this = this;
		var control =_this.getControl("tgtslx");
		var value =  control.getValue();
   		 if(value == null || value.length == 0){
   		_this.$("#tg").show();
   	    	_this.$("#tgts").hide();
   	    	_this.$("#tgts1").hide();
   		 }else if(value == "1"){ 
			_this.$("#tg").show();
		        _this.$("#tgts").hide();
		        _this.$("#tgts1").hide();
		}else{
			_this.$("#tgts").show();
			_this.$("#tgts1").show();
                            _this.$("#tg").hide();
		}
	},
	
	/**
	 * LDX_人事管理_职位申请书 (1.0)
	 */
	uf_ldx_rsgl_zwsqs:function(){
		var _this = this;
		_this.$("#hxsap1").bind("click",function(){
			var params = {};
			params.PI_BEGDA = _this.getControl("sqsj").getValue().replace(new RegExp("-", 'g'), "");
			params.PI_ENDDA = "99991231";
			params.PI_WERKS = _this.getControl("rsfw").getValue();
			params.PI_APTYP = _this.getControl("sqrfw").getValue();
			params.PI_APGRP = _this.getControl("sqrz").getValue();
			params.PI_BTRTL = _this.getControl("rszfw").getValue();
			params.PI_OBJID = _this.getControl("sqzwbh").getValue();
			params.PI_NACHN = _this.getControl("x").getValue();
			params.PI_VORNA = _this.getControl("m").getValue();
			params.PI_NAME2 = _this.getControl("ywm").getValue();
			params.PI_GESC2 = _this.getControl("xb").getValue();
			params.PI_GBDAT = _this.getControl("csrq").getValue().replace(new RegExp("-", 'g'), "");
			params.PI_GBORT = _this.getControl("jg").getValue();
			params.PI_NATIO = _this.getControl("gj").getValue();
			params.PI_FATXT = _this.getControl("hyzk").getValue();
			// alert(JSON.stringify(params));
			$.ajax({
				type : "post",
				data : {
					"saps":"sapConnectConfig",
					"functionName" : "ZHRI002702",
					"jsonParams" : JSON.stringify(params)
				},
				url : ctx + "/basicdata/sap/executeRFC",
				success : function(result) {
					if (result.success) {
						var data = jQuery.parseJSON(result.data);
						var row = data.PT_RETURN.row;
						_this.getControl("ypbm").setValue(data.PO_PERNR);
						alert(row[0].MESSAGE);
					}
				},
				error : function(result){
					alert("操作失败");
				}
			});
		});
		
		_this.$("#hxsap2").bind("click",function(){
		    var piicnum = _this.getControl("zjhm").getValue();
		    var pigbdat = _this.getControl("csrq").getValue();
		    
		    if(pigbdat.indexOf("-")>0) {  pigbdat = pigbdat.replace(/-/g,'');
		          pigbdat = pigbdat.substr(2,6);
		    }
		       
		    if ((piicnum.length != 15)&&(piicnum.length != 18)){
		         alert("身份证号码输入有误，请检查！");
		         return false;
		    }       
		    if (piicnum.length == 15){  
		        var icnumtemp = piicnum.substr(6,6);
		    }        
		    if (piicnum.length == 18){
		        var icnumtemp = piicnum.substr(8,6);
		    }
		      
		    if (pigbdat!= icnumtemp){ 
		        alert("出生日期与身份证上对应不上，请检查！");
		        return false;
		    }
			var params = {};
			params.PI_APLNO = _this.getControl("ypbm").getValue();
			params.PI_BEGDA = _this.getControl("csrq").getValue().replace(new RegExp("-", 'g'), "");
			params.PI_ENDDA = "99991231";
			params.PI_ICNUM = piicnum;
			// alert(JSON.stringify(params));
			$.ajax({
				type : "post",
				data : {
					"saps":"sapConnectConfig",
					"functionName" : "ZHRI002716",
					"jsonParams" : JSON.stringify(params)
				},
				url : ctx + "/basicdata/sap/executeRFC",
				success : function(result) {
					if (result.success) {
						var data = jQuery.parseJSON(result.data);
						var row = data.PT_RETURN.row;
						alert(row[0].MESSAGE);
					}
				},
				error : function(result){
					alert("操作失败");
				}
			});
		});
		
		_this.$("#hxsap3").bind("click",function(){
			// alert("#hxsap3");
			var allRowData = _this.getAllRowData({"id":"uf_ldx_rsgl_zwsqstxxx"});
			if(typeof allRowData == "undefined" || allRowData.length <= 0){
				return false;
			}
			var params = {};
			params.PI_APLNO = _this.getControl("ypbm").getValue();
			params.PI_BEGDA = _this.getControl("sqsj").getValue().replace(new RegExp("-", 'g'), "");
			params.PI_ENDDA = "99991231";
			for(var i = 0 ; i < allRowData.length ; i++){
				var rowData = allRowData[i];
				params.PI_USRID_LONG = rowData.txhm;
				params.PI_SUBTY = rowData.txlxz;
				$.ajax({
					type : "post",
					data : {
						"saps":"sapConnectConfig",
						"functionName" : "ZHRI002705",
						"jsonParams" : JSON.stringify(params)
					},
					url : ctx + "/basicdata/sap/executeRFC",
					success : function(result) {
						if (result.success) {
							var data = jQuery.parseJSON(result.data);
							var row = data.PT_RETURN.row;
							alert(row[0].MESSAGE);
						}
					},
					error : function(result){
						alert("操作失败");
					}
				});
			}
		});
		
		_this.$("#hxsap4").bind("click",function(){
			// alert("#hxsap4");return false;
			var allRowData = _this.getAllRowData({"id":"uf_ldx_rsgl_zwsqsdzxx"});
			if(typeof allRowData == "undefined" || allRowData.length <= 0){
				return false;
			}
			var params = {};
			params.PI_APLNO = _this.getControl("ypbm").getValue();
			params.PI_BEGDA = _this.getControl("sqsj").getValue().replace(new RegExp("-", 'g'), "");
			params.PI_ENDDA = "99991231";
			for(var i = 0 ; i < allRowData.length ; i++){
				var rowData = allRowData[i];
				params.PI_SUBTY = rowData.dzlxz;
				params.PI_LAND1 = rowData.gjz;
				params.PI_STATE = rowData.sfz;
				params.PI_LOCAT = rowData.xxdz;
				params.PI_PSTLZ = rowData.yzbm;
				$.ajax({
					type : "post",
					data : {
						"saps":"sapConnectConfig",
						"functionName" : "ZHRI002706",
						"jsonParams" : JSON.stringify(params)
					},
					url : ctx + "/basicdata/sap/executeRFC",
					success : function(result) {
						if (result.success) {
							var data = jQuery.parseJSON(result.data);
							var row = data.PT_RETURN.row;
							alert(row[0].MESSAGE);
						}
					},
					error : function(result){
						alert("操作失败");
					}
				});
			}
		});
		
		_this.$("#hxsap5").bind("click",function(){
			// alert("#hxsap4");return false;
			var allRowData = _this.getAllRowData({"id":"uf_ldx_rsgl_zwsqsjtcyxx"});
			if(typeof allRowData == "undefined" || allRowData.length <= 0){
				return false;
			}
			var params = {};
			params.PI_APLNO = _this.getControl("ypbm").getValue();
			params.PI_BEGDA = _this.getControl("sqsj").getValue().replace(new RegExp("-", 'g'), "");
			params.PI_ENDDA = "99991231";
			for(var i = 0 ; i < allRowData.length ; i++){
				var rowData = allRowData[i];
				params.PI_SUBTY = rowData.cylxz;
				params.PI_FANAM = rowData.x;
				params.PI_FAVOR = rowData.m;
				params.PI_GESC2 = rowData.xbz;
				params.PI_TELNR = rowData.tx;
				params.PI_ZZFJSJ = rowData.fjsj;
				params.PI_LAND1 = "CN";
				$.ajax({
					type : "post",
					data : {
						"saps":"sapConnectConfig",
						"functionName" : "ZHRI002717",
						"jsonParams" : JSON.stringify(params)
					},
					url : ctx + "/basicdata/sap/executeRFC",
					success : function(result) {
						if (result.success) {
							var data = jQuery.parseJSON(result.data);
							var row = data.PT_RETURN.row;
							alert(row[0].MESSAGE);
						}
					},
					error : function(result){
						alert("操作失败");
					}
				});
			}
		});
		
		_this.$("#hxsap6").bind("click",function(){
			// alert("#hxsap4");return false;
			var allRowData = _this.getAllRowData({"id":"uf_ldx_rsgl_zwsqsjyxx"});
			if(typeof allRowData == "undefined" || allRowData.length <= 0){
				return false;
			}
			var params = {};
			params.PI_APLNO = _this.getControl("ypbm").getValue();
			for(var i = 0 ; i < allRowData.length ; i++){
				var rowData = allRowData[i];
				params.PI_SUBTY = rowData.jydjz;
				params.PI_SLART = rowData.jydjz;
				params.PI_BEGDA = rowData.rxsj;
				params.PI_ENDDA = rowData.bysj;
				params.PI_AUSBI = rowData.jxxsz;
				params.PI_INSTI = rowData.xxmc;
				params.PI_SLAND = "CN";
				params.PI_ZZYMC = rowData.zy;
				params.PI_ZXL = rowData.xlz;
				params.PI_ZXW = rowData.xwz;
				params.PI_ZJXXS = rowData.zsbh;
				params.PI_SLABS = "01";
				$.ajax({
					type : "post",
					data : {
						"saps":"sapConnectConfig",
						"functionName" : "ZHRI002708",
						"jsonParams" : JSON.stringify(params)
					},
					url : ctx + "/basicdata/sap/executeRFC",
					success : function(result) {
						if (result.success) {
							var data = jQuery.parseJSON(result.data);
							var row = data.PT_RETURN.row;
							alert(row[0].MESSAGE);
						}
					},
					error : function(result){
						alert("操作失败");
					}
				});
			}
		});
		
		_this.$("#hxsap7").bind("click",function(){
			// alert("#hxsap4");return false;
			var allRowData = _this.getAllRowData({"id":"uf_ldx_rsgl_zwsqszgzsxx"});
			if(typeof allRowData == "undefined" || allRowData.length <= 0){
				return false;
			}
			var params = {};
			params.PI_APLNO = _this.getControl("ypbm").getValue();
			for(var i = 0 ; i < allRowData.length ; i++){
				var rowData = allRowData[i];
				params.PI_BEGDA = rowData.yxqxks;
				params.PI_ENDDA = rowData.yxqxjs;
				params.PI_ZZZGZS = rowData.zgzsmc;
				$.ajax({
					type : "post",
					data : {
						"saps":"sapConnectConfig",
						"functionName" : "ZHRI002715",
						"jsonParams" : JSON.stringify(params)
					},
					url : ctx + "/basicdata/sap/executeRFC",
					success : function(result) {
						if (result.success) {
							var data = jQuery.parseJSON(result.data);
							var row = data.PT_RETURN.row;
							alert(row[0].MESSAGE);
						}
					},
					error : function(result){
						alert("操作失败");
					}
				});
			}
		});
		
		_this.$("#hxsap8").bind("click",function(){
			// alert("#hxsap4");return false;
			var allRowData = _this.getAllRowData({"id":"uf_ldx_rsgl_zwsqsgzjl"});
			if(typeof allRowData == "undefined" || allRowData.length <= 0){
				return false;
			}
			var params = {};
			params.PI_APLNO = _this.getControl("ypbm").getValue();
			for(var i = 0 ; i < allRowData.length ; i++){
				var rowData = allRowData[i];
				params.PI_BEGDA = rowData.rzsj;
				params.PI_ENDDA = rowData.lzsj;
				params.PI_ZQYMC = rowData.qymc;
				params.PI_ZRZBMGW = rowData.szbm;
				params.PI_ZZHY = rowData.szhy;
				params.PI_ZQYXZ = rowData.qyxzz;
				params.PI_ZZJZGXM = rowData.zjzgxm;
				params.PI_ZZJZGLXFS = rowData.zjzglxfs;
				params.PI_ZLZYY = rowData.lzyy;
				$.ajax({
					type : "post",
					data : {
						"saps":"sapConnectConfig",
						"functionName" : "ZHRI002710",
						"jsonParams" : JSON.stringify(params)
					},
					url : ctx + "/basicdata/sap/executeRFC",
					success : function(result) {
						if (result.success) {
							var data = jQuery.parseJSON(result.data);
							var row = data.PT_RETURN.row;
							alert(row[0].MESSAGE);
						}
					},
					error : function(result){
						alert("操作失败");
					}
				});
			}
		});
		
		_this.$("#hxsap9").bind("click",function(){
			// alert("#hxsap4");return false;
			var allRowData = _this.getAllRowData({"id":"uf_ldx_rsgl_zwsqszgzsxx"});
			if(typeof allRowData == "undefined" || allRowData.length <= 0){
				return false;
			}
			var params = {};
			params.PI_APLNO = _this.getControl("ypbm").getValue();
			for(var i = 0 ; i < allRowData.length ; i++){
				var rowData = allRowData[i];
				params.PI_BEGDA = rowData.cjsj;
				params.PI_ENDDA2 = "99991231";
				params.PI_ZNAME = rowData.pxkc;
				params.PI_ZPOSITION = rowData.pxdd;
				params.PI_ZGRADE = rowData.khcj;
				$.ajax({
					type : "post",
					data : {
						"saps":"sapConnectConfig",
						"functionName" : "ZHRI002711",
						"jsonParams" : JSON.stringify(params)
					},
					url : ctx + "/basicdata/sap/executeRFC",
					success : function(result) {
						if (result.success) {
							var data = jQuery.parseJSON(result.data);
							var row = data.PT_RETURN.row;
							alert(row[0].MESSAGE);
						}
					},
					error : function(result){
						alert("操作失败");
					}
				});
			}
		});
		
		_this.$("#hxsap10").bind("click",function(){
			var params = {};
			params.PI_APLNO = _this.getControl("ypbm").getValue();
			params.PI_BEGDA = _this.getControl("sqsj").getValue().replace(new RegExp("-", 'g'), "");
			params.PI_ENDDA = "99991231";
			params.PI_ZZFXRQ = _this.getControl("fxrq").getValue();
			params.PI_ZZJBGZ = _this.getControl("jbgz").getValue();
			params.PI_ZZJJ = _this.getControl("jj").getValue();
			params.PI_ZZSYJT = _this.getControl("syjt").getValue();
			params.PI_ZZJTZF = _this.getControl("jtzf").getValue();
			params.PI_ZZQT = _this.getControl("qt").getValue();
			params.PI_ZZHJ = _this.getControl("hj").getValue();
			$.ajax({
				type : "post",
				data : {
					"saps":"sapConnectConfig",
					"functionName" : "ZHRI002712",
					"jsonParams" : JSON.stringify(params)
				},
				url : ctx + "/basicdata/sap/executeRFC",
				success : function(result) {
					if (result.success) {
						var data = jQuery.parseJSON(result.data);
						var row = data.PT_RETURN.row;
						_this.getControl("ypbm").setValue(data.PO_PERNR);
						alert(row[0].MESSAGE);
					}
				},
				error : function(result){
					alert("操作失败");
				}
			});
		});
	},
	
	
	});
});