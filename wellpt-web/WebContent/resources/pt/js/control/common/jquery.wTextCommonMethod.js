/**
input文本类型属性的公共方法
(主要是ReadOnly及Edtiable的控制)
*/

$(function(){
	
	$.wTextCommonMethod={
			
			 //只读，文本框不置灰，不可编辑
			 setReadOnly:function(isreadonly){
				 $.ControlUtil.setReadOnly(this.$element,isreadonly);
				 this.options.readOnly=isreadonly;
				 if(this.hideOperator)this.hideOperator();
			 } ,
			 
			 isReadOnly:function(){
				 return this.options.readOnly;
			 },
			 
			 //设置可编辑
			 setEditable:function(){ 
				 this.setReadOnly(false);
				 this.setEnable(true);
				 this.setDisplayAsCtl();
				 if(this.showOperator)this.showOperator();
			 },
			 
			 /**
			  * 返回是否可编辑(由readOnly和disabled判断)
			  * @returns {Boolean}
			  */
			 isEditable:function(){
				 if(this.options.readOnly&&this.options.disabled){
					 return false;
				 }else{
					 return true;
				 }
			 }	
			
	};
	
});