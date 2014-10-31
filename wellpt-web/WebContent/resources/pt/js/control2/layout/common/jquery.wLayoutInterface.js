;
(function($) {
	$.wLayoutInterface = {
		/**
		 * 初始化方法
		 */
		init:function(){
		
		},
		/**
		 * 隐藏
		 */
		hide:function(){
			
		},
		/**
		 * 显示
		 */
		show:function(){
			
		},
		/**
		 * 隐藏标题
		 */
		hideDisplayName:function(){
			
		},
		/**
		 * 显示标题
		 */
		showDisplayName:function(){
			
		},
		/**
		 * 设置标题
		 */
		setDisplayName:function(title){
			
		},
		/**
		 * 是否激活
		 * @param isActive  true:激活,反之为false
		 */
		active:function(isActive){
			
		},

		   //bind函数，桥接
	     bind:function(eventname,event){  
	    	 if(typeof this.options.events == "undefined"){
	    		 this.options.events = {};
	    	 }
	    	 this.options.events[eventname] = event; 
    		return this;
	     },
		 
		 //unbind函数，桥接
	     unbind:function(eventname){
	    	 if(typeof this.options.events == "undefined"){
	    		 return this;
	    	 }
	    	 delete this.options.events[eventname] = event;  
	    		return this;
	     },
	     
	     /*在设置之后角发该事件*/
		 invoke:function(method){
			 if(typeof this.options.events == "undefined"){
	    		 return this;
	    	 }
			 if(this.options.events[method]){
				 this.options.events[method].apply(this,$.makeArray(arguments).slice(1));
			 }
		 }
		
		
		
	};

	
})(jQuery);