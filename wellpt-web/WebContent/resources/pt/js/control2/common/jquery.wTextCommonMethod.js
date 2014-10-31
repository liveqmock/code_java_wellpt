/**
input文本类型属性的公共方法
(主要是ReadOnly及Edtiable的控制)
*/
;
(function($){
	
	$.wTextCommonMethod={
			 
			 get$InputElem:function(){
				 if(this.$editableElem == null){
					 return $([]);
				 }else{
					 return this.$editableElem;
				 }
				
			 },
			  
			 
			
			/*设值到标签中*/
			 setValue2LabelElem:function(){
				 if(this.$labelElem == null){
					 return;
				 }
				 this.$labelElem.html(this.value);
			 },
			 
			 /*设置到可编辑元素中*/
			 setValue2EditableElem:function(){
				 if(this.$editableElem == null){
					 return;
				 }
				 this.$editableElem.val(this.value); 
			 },

			 
			//set............................................................//
		     
			//设值,值为真实值
			 setValue:function(value, isRender){
				 if(value == null){
					 return;
				 }
				 this.value = value;
				 if(!this.options.isHide ){//该控件被隐藏时则不进行渲染
					 this.render(isRender);//将值渲染到页面元素上
				 }
				// this.set
				 if( this.culateByFormula)
				 this.culateByFormula();//根据运算公式计算 
				 this.invoke("afterSetValue",  this.value);
			 } ,
			 
			 isValueMap:function(){
				 return false;
			 },
	};
	
})(jQuery);