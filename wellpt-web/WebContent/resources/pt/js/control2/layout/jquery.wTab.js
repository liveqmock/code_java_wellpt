/**
 * 页签布局控件
 */
;
(function($) {
	/*
	 * Tab CLASS DEFINITION ======================
	 */
	var Tab = function($placeHolder/*占位符*/, options) {
		//this.$element = $(element);
		this.options = $.extend({}, $.fn["wTab"].defaults, options);
		this.$placeHolder = $placeHolder;
	};
	
	Tab.prototype = {
		constructor : Tab,
		initSelf:function(){
			
		}
	};
	
 
	
	/*
	 * Tab PLUGIN DEFINITION =========================
	 */
	$.fn.wTab = function(option) {
		var method = false;
		var args = null;
		if (arguments.length == 2) {
			method = true;
			args = arguments[1];
		}
	 
		if (typeof option == 'string') {
			if(option === 'getObject'){ //通过getObject来获取实例
				var $this = $(this);
				data = $this.data('wTab');
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
					data = $this.data('wTab'), 
					options = typeof option == 'object'
							&& option;
					if (!data) {
						 data = new Tab($(this), options); 
						 $.extend(data, $.wLayoutInterface);  
						 data.init();
						 $this.data('wTab',data );
					}
					
					if (typeof option == 'string') {
						if (method == true && args != null) {
							return data[option](args);
						} else {
							return data[option]();
						}
					}else{
						return data;
					}
				});
	};

	$.fn.wTab.Constructor = Tab;

	$.fn.wTab.defaults = {
		name:"",//页签组编码
		displayName:"",//
		subTabs:{}
	};
})(jQuery);