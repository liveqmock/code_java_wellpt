/**
 * 从表操作的一些方法
 * 增删行、更新行操作等
 */
$(function(){
	
	$.wFileUploadMethod={
			
			getValue:function(){
				var files = WellFileUpload.files[this.getFielctlID()];
				 if(files){
					return files;
				 }else{
					return  [];
				 }
			},
			
			getFielctlID:function(){
				var id = this.$element.attr('id');//字段名称
				return  WellFileUpload.getCtlID4Dytable(this.options.mainTableName, id, 0);
			},
			 
			 getAllOptions:function(){
			    	 return this.options;
			     } ,  
			     
			 getRule:function(){
				 return $.ControlUtil.getCheckRules(this.options);
			 } ,
			 
			 getMessage:function(){
				 return $.ControlUtil.getCheckMsg(this.options);
			 } ,
			 
			 isValueMap:function(){
				 return false;
			 },
			 
			 setValue:function(value){
				 
				if(typeof value == "undefined" || value == null ){
					var columnName=this.options.columnProperty.columnName;
					var files=WellFileUpload.loadFilesFormDb(this.getDataUuid() , columnName);
					console.log(JSON.cStringify(files));
					this.fileuploadobj.addFiles(files,true);
				}else{
					this.fileuploadobj.addFiles(value, false);
				}
			 
			 } ,
			 setTextFile2SWF:function(enable){
				 
				 WellFileUpload.file2swf = enable;
			 }, 
			 
			 enableSignature:function(enable){
				 
					if(enable){
						 this.fileuploadobj.signature  = signature.enable;
					}else{
						 this.fileuploadobj.signature  = signature.disable;
					}
				  
			 }, 
			 
			 setDataUuid:function(datauuid){
				 this.options.columnProperty.dataUuid=datauuid;
			 },
			 
			 getDataUuid:function(){
				 return this.options.columnProperty.dataUuid;
			 },
			 
			 setPos:function(pos){
				 this.options.columnProperty.pos=pos;
			 },
			 
			 getPos:function(pos){
				 return this.options.columnProperty.pos;
			 },
		       
			 getFormDefinition:function(){
				 return this.options.columnProperty.formDefinition;
			 },	
			 
			 //设置可编辑
			 setEditable:function(){
				 this.options.readOnly = false; 
				 this.$element.siblings("div[id^='_fileupload']").find(".upload_div").show();
				 this.$element.siblings("div[id^='_fileupload']").find(".delete_Div").show();
				  
			 } ,
			 //设置只读
			 setReadOnly:function(){
				 this.options.readOnly = true; 
				 this.$element.siblings("div[id^='_fileupload']").find(".upload_div").hide();
				 this.$element.siblings("div[id^='_fileupload']").find(".delete_Div").hide();
			 } ,
			 
			 
			 
		     /**
		      * 获得控件名
		      * @returns
		      */
		     getCtlName:function(){
		    	 var id = this.$element.attr('id');//字段名称
				 return  WellFileUpload.getCtlID4Dytable(this.options.mainTableName, id, 0);
		     },
		     
		     getCtlElement:function(){
		    	 return this.$element;
		     },
			 
		     //bind函数，桥接
		     bind:function(eventname,event){
		    	this.$element.bind(eventname,event);
		    	return this;
		     },
		     
		     
		     validate:function(){
		    	 return true;
			 },
			 
			 //unbind函数，桥接
		     unbind:function(eventname){
		    	this.$element.unbind(eventname);
		    	return this;
		     },
		    //一些其他method ---------------------
			
		     /**
		      * 添加url超连接事件
		      */
		     addUrlClickEvent:function(urlClickEvent){
		 
		     },
		     
			 //显示为lablel
			 setDisplayAsLabel:function(){
				 this.setReadOnly();
			 } ,
		     
		     /**
	    	 * 设置需要分组验证的标识
	    	 */
	    	setValidateGroup:function(group){
	    		var element = this.$element;
	    		element.attr(group.groupUsed, group.groupName);
	    	},
	};
	
});