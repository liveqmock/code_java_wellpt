;
(function($) {
	var columnProperty={
			//控件字段属性
			applyTo:null,//应用于
			columnName:null,//字段定义  fieldname
			displayName:null,//描述名称  descname
			dbDataType:'',//字段类型  datatype type
			indexed:null,//是否索引
			showed:null,//是否界面表格显示
			sorted:null,//是否排序
			sysType:null,//系统定义类型，包括三种（0：系统默认，1：管理员常量定义，2：表单添加后自定义）
			length:null,//长度
			showType:'1',//显示类型 1,2,3,4 datashow
			defaultValue:null,//默认值
			valueCreateMethod:'1',//默认值创建方式 1用户输入
			onlyreadUrl:null,//只读状态下设置跳转的url
	};
	
	//控件公共属性
	var commonProperty={
			inputMode:null,//输入样式 控件类型 inputDataType
			fieldCheckRules:null,
			fontSize:null,//字段的大小
			fontColor:null,//字段的颜色
			ctlWidth:null,//宽度
			ctlHight:null,//高度
			textAlign:null,//对齐方式
			
	};	
	
	/*
	 * SERIALNUMBER CLASS DEFINITION ======================
	 */
	var SerialNumber = function(element, options) {
		this.$element = $(element);
		this.options = $.extend({}, $.fn["wserialNumber"].defaults, options,
				this.$element.data());
	};

	SerialNumber.prototype = {
		constructor : SerialNumber,

		//默认参数初始化
		initSelf:function(){
			
			var inputMode=this.options.commonProperty.inputMode;
			 this.$element.attr("id",this.$element.attr("name"));
			 this.$element.attr("designatedId",this.options.designatedId);	//流水号定义:指定的流水号ID
			 this.$element.attr("designatedType",this.options.designatedType);//可编辑流水号定义:指定的流水号TYPE
			 this.$element.attr("isOverride",this.options.isOverride);	//可编辑流水号定义:是否覆盖指针
			 this.$element.attr("isSaveDb",this.options.isSaveDb);	//流水号定义：是否保存进数据库
			 //设置文本的css样式
			 this.setTextInputCss();
			 if(inputMode==dyFormInputMode.serialNumber){
				 this.$element.addClass("input-search");//css in wellnewoa.css
			 }

			 var formUuid=this.options.formUuid;
			 var element=this.$element;
			 var val=this.$element.val();
			 var options=this.options;
			 if(dyFormInputMode.serialNumber == inputMode) {//可编辑流水号
					var designatedId_ = options.designatedId;
					var designatedType_ = options.designatedType;
					var isOverride_ = options.isOverride;
					this.$element.live("click",function() {
						getEditableSerialNumber(designatedId_,designatedType_,parseInt(isOverride_),formUuid,options.columnProperty.columnName,element.attr("id"));
						element.val(snValue);
					});
					//根据show类型展示
					 this.dispalyByShowType();
			 }else{
				 var unEditDesignatedId = options.designatedId;
					var unEditIsSaveDb = options.isSaveDb;
					if(val != "") {
						this.setValue(val);
					}else {
					 
						 var notEditIsSaveDb = unEditIsSaveDb == "0"? true:false;
						 console.log("取流水号:[" + unEditDesignatedId + "]unEditIsSaveDb:[" + notEditIsSaveDb + "]formUuid:[" + formUuid + "]id:["+this.options.columnProperty.columnName+"]");
						var snValue = getNotEditableSerialNumberForDytable(unEditDesignatedId,notEditIsSaveDb,formUuid , this.options.columnProperty.columnName);
						 console.log(snValue);
						this.setValue(snValue);
					}
					this.setDisplayAsLabel();
			 }
			 if(this.getValue()==""||this.getValue()==undefined){
		         //设置默认值
				 this.setDefaultValue(options.columnProperty.defaultValue);
			 }
			  this.addMustMark();
		},
	     
	};
	
	/*
	 * SERIALNUMBER PLUGIN DEFINITION =========================
	 */
	$.fn.wserialNumber = function(option) {
		var method = false;
		var args = null;
		if (arguments.length == 2) {
			method = true;
			args = arguments[1];
		}
		
		if (typeof option == 'string') {
			if(option === 'getObject'){ //通过getObject来获取实例
				var $this = $(this);
				data = $this.data('wserialNumber');
                if(data){
                    return data; //返回实例对象
                }else{
                    throw new Error('This object is not available');
                }
            }
		}
		
		return this
				.each(function() {
					var $this = $(this),
					data = $this.data('wserialNumber'), 
					options = typeof option == 'object'
							&& option;
					if (!data) {
						 data = new SerialNumber(this,options);
						 var datacopy={};
						 var data1=$.extend(datacopy,data);
						 var extenddata=$.extend(data,$.wControlInterface);
						 var data2=$.extend(extenddata,data1);
						 var data3=$.extend(data2,$.wTextCommonMethod);
						 data3.init();
						 $this.data('wserialNumber',data3 );
					}
					if (typeof option == 'string') {
						if (method == true && args != null) {
							return data[option](args);
						} else {
							return data[option]();
						}
						
						
					}
					
				});
	};

	$.fn.wserialNumber.Constructor = SerialNumber;

	$.fn.wserialNumber.defaults = {
			columnProperty:columnProperty,//字段属性
			commonProperty:commonProperty,//公共属性
			
			//控件私有属性
			isHide:false,//是否隐藏
			disabled:false,
	        readOnly:false,
	        designatedId:"",
	        isShowAsLabel:false,
			designatedType:"",
			isOverride:"",
			isSaveDb:"",
			formUuid:""
			
	};
	
})(jQuery);