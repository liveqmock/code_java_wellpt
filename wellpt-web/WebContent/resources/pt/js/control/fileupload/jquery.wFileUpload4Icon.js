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
	 * FILEUPLOAD4ICON CLASS DEFINITION ======================
	 */
	var FileUpload4Icon = function(element, options) {
		this.$element = $(element);
		this.options = $.extend({}, $.fn["wfileUpload4Icon"].defaults, options,
				this.$element.data());
	};

	FileUpload4Icon.prototype = {
		constructor : FileUpload4Icon,
		init : function() {
			
			$.ControlUtil.setCommonCtrAttr(this.$element,this.options);
			this.$element.attr("id",this.$element.attr("name"));
			//在这里处理 列表式的附件
			 var setReadOnly=false;
			 if(this.options.columnProperty.showType==2){
				 setReadOnly=true;
			 }
			 
			 var allowUpload=this.options.allowUpload;
			 var allowDownload=this.options.allowDownload;
			 var allowDelete=this.options.allowDelete;

			 
			this.$element.hide();
			var id = this.$element.attr('id');//字段名称
			 
			var uploaddivhtml="<div id='_fileupload"+id+"'></div>";
			this.$element.after(uploaddivhtml);
			var $attachtr = $("#_fileupload"+id);
			
			 //创建上传控件
			var elementID = WellFileUpload.getCtlID4Dytable(this.options.mainTableName, id, 0);
			this.fileuploadobj = new WellFileUpload4Icon(elementID,iconFileControlStyle.OnlyIcon);
			//初始化上传控件
			if(this.getDataUuid()!=''&&this.getDataUuid()!=undefined){
				this.fileuploadobj.initWithLoadFilesFromFileSystem(allowUpload,//是否则有上传的权限 
				allowDownload,//是否具有下载的权限 
				$attachtr,//存放该附件的容器 
				this.options.enableSignature,//是否签名
				this.getDataUuid(),
				id
				);	
			}else{
				this.fileuploadobj.init(allowUpload,//是否则有上传的权限 
						allowDownload,//是否具有下载的权限 
						$attachtr,//存放该附件的容器 
						this.options.enableSignature,//是否签名
						[]);	
			}
		},
	};
	
	/*
	 * FILEUPLOAD4ICON PLUGIN DEFINITION =========================
	 */
	$.fn.wfileUpload4Icon = function(option) {
		var method = false;
		var args = null;
		if (arguments.length == 2) {
			method = true;
			args = arguments[1];
		}
		
		if (typeof option == 'string') {
			if(option === 'getObject'){ //通过getObject来获取实例
				var $this = $(this);
				var data = $this.data('wfileUpload4Icon');
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
					data = $this.data('wfileUpload4Icon'), 
					options = typeof option == 'object'
							&& option;
					if (!data) {
						
						data = new FileUpload4Icon(this,options);
						 var data1=$.extend(data,$.wFileUploadMethod);
						 data1.init();
						 $this.data('wfileUpload4Icon',data1);
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

	$.fn.wfileUpload4Icon.Constructor = FileUpload4Icon;
	
	$.fn.wfileUpload4Icon.defaults = {
			columnProperty:columnProperty,//字段属性
			commonProperty:commonProperty,//公共属性
	        isHide:false,//是否隐藏
	        mainTableName:'',
	        formSign:'',
	        enableSignature:"",
         	allowUpload:true,//允许上传
            allowDownload:true,//允许下载
            allowDelete:true,//允许删除
            mutiselect:true//是否多选
	};
	
})(jQuery);