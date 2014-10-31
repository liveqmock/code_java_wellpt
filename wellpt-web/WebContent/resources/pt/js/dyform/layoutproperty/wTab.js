/**
 * 页签分组,每个页签均属于一个分组
 */
var WTaClass = function(){
		this.name = "";
		this.displayName = "";
		this.subTabs = {};
		this.toJSON = toJSON;
   };
   
   WTaClass.prototype = new LayOut();


var WSubTabClass = function(){
		this.name = "";//布局唯一标识
		this.displayName = "";//布局标题
		this.isActive = false;//是否激活 
		this.isHide = false;//是否隐藏 
		this.toJSON = toJSON;
   };
   
   //WSubTabClass.prototype=new LayOut();
