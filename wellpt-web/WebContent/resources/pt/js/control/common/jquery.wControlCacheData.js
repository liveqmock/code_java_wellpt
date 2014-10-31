
/**
 *控件初始化数据缓存类 。 包括数据字典等的一些值的缓存获取. 
 */
var controlCacheData={};

var getControlCacheData=function(inputModel,cacheCode){
	/**
	 * 字典编码数据缓存
	 * (radio/checkbox/combobox)
	 */
	if(inputModel==dyFormInputMode.radio||inputModel==dyFormInputMode.checkbox||inputModel==dyFormInputMode.selectMutilFase){
		if(controlCacheData['dataDict'+cacheCode]!=undefined){
			return controlCacheData['dataDict'+cacheCode];
		}
		var dictcode=cacheCode;
		JDS.call({
	       	 service:"dataDictionaryService.getDataDictionariesByType",
	       	 data:[dictcode],
	       	 async: false,
				success:function(result){
					var datas = result.data;
					controlCacheData['dataDict'+dictcode]=datas;
				},
				error:function(jqXHR){
				}
			});
	}
	return controlCacheData['dataDict'+cacheCode];
};
