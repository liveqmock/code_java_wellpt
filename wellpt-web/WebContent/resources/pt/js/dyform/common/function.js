/**
 * 从数据库中加载表单定义信息
 */
var loadFormDefinition = function(uuid){
	if(uuid == "" || uuid == null || typeof uuid == "undefined" || uuid == "undefined"){
		return new MainFormClass();
	}
	var definitionObj  = null;
	$.ajax({
		url : contextPath + "/dyform/getFormDefinition",
		cache : false,
		async : false,//同步完成
		type : "POST",
		data : {formUuid: uuid} ,
		dataType : "json",
		success : function(data) { 
			definitionObj = data; 
		}, 
		error: function(){//加载定义失败
			oAlert("表单定义加载失败,请重试"); 
			//window.close();//加载失败时关闭当前窗口,用户需重新点击
			definitionObj = null;
		}
	});
	return definitionObj;
};

/**
 * 根据指定的dataUuid从指定的formUuid表单中获取表单数据
 * */
var loadFormData = function(formUuid, dataUuid){
	if(formUuid == "" || formUuid == null 
			|| typeof dataUuid == "undefined" || dataUuid == "undefined"){//未初始化
		throw new Error("formUuid or dataUuid is not initialized");
	}
	
	var formDatas  = null;
	$.ajax({
		url : contextPath + "/dyformdata/getFormData",
		cache : false,
		async : false,//同步完成
		type : "POST",
		data : {formUuid: formUuid, dataUuid: dataUuid} ,
		dataType : "json",
		success : function(result) { 
			 if(result.success == "true" || result.success == true){ 
				  formDatas =  result.data;  
  		   }else{
  			   alert("数据获取失败");
  		   }
		}, 
		error: function(){//加载定义失败
			 alert("数据获取失败"); 
			 formDatas  = null;
		}
	});
	return formDatas;
};


/**
 * 控件参数继承时需要将父类属性显示.
 * eg. this.toJSON = toJSON;
 */
var toJSON = function()
{
	/*
	var tmp = {};

	for(var key in this)
	{
		if(typeof this[key] !== 'function')
			tmp[key] = this[key];
	}
	return tmp;
	*/
	  var result = Object.create(this);
		for(var key in result) {
			result[key] = result[key];
		}
		return result;
	 
};