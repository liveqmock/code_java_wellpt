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
	 * UNIT CLASS DEFINITION ======================
	 */
	var Unit = function($placeHolder, options) {  
		this.options = $.extend({}, $.fn["wunit"].defaults, options);
		this.value = "";
		this.$editableElem = null;
		this.$labelElem = null;
		this.$placeHolder = $placeHolder;
	};

	Unit.prototype = {
		constructor : Unit  
	};
	
	$.Unit = {
			createEditableElem:function(){ 
				 if(this.$editableElem != null){//创建可编辑框
					 return ;
				 }
				 
				 var options = this.options;
				 var ctlName =  this.getCtlName();
				 var editableElem = document.createElement("input");
				 editableElem.setAttribute("class", this.editableClass);
				 editableElem.setAttribute("name", ctlName);
				 editableElem.setAttribute("id", ctlName);
				 editableElem.setAttribute("type", "text");
				 
				 $( editableElem).css(this.getTextInputCss());
				 
				 this.$placeHolder.after($(editableElem));
				 this.$editableElem = this.$placeHolder.next("." + this.editableClass); 
				 this.$editableElem.mouseover(function(){
					$(this).attr("title", $(this).val());
				 });
				 
				 this.$editableElem.addClass("input-people");//css in wellnewoa.css
				/* if(this.options.commonProperty.inputMode==dyFormOrgSelectType.orgSelectStaff){
					
				 }else{
					 this.$editableElem.addClass("input-search");//css in wellnewoa.css
				 }*/
				
				 var labelField = this.getCtlName();
				 var inputMode=this.options.commonProperty.inputMode;
				 var _this = this;
				 var ismultiselect=this.options.mutiSelect;
				 var showUnitType=this.options.showUnitType;
				 var filterCondition=this.options.filterCondition;
				 if(dyFormOrgSelectType.orgSelectAddress == inputMode) {//单位通讯录 
					 this.$editableElem.click(function(){
						 	if(!_this.options.readOnly){
						 		if(_this.isSelection()){
						 			return;
						 		}
								$.unit.open({
									title : "组织选择",
									labelField:labelField, 
									type : 'Unit',
									commonType : 2,
									multiple:ismultiselect,
									showType :showUnitType,
									filterCondition:filterCondition,
									ok:function(){ 
										$("#"+labelField).focus();
										$("#"+labelField).blur();
										_this.setToRealDisplayColumn( );
										_this.setValue2Object( );
										_this.afterUnitChoose();
									}
								});
						 	}
						});
				 }else{
					 this.$editableElem.click(function(){
						 	if(!_this.options.readOnly){
						 		if(_this.isSelection()){
						 			return;
						 		}
								$.unit.open({
									title : "组织选择",
									labelField:labelField, 
									selectType : _this.getSelectTypeByinputMode(inputMode),
									type : _this.getTypeByinputMode(inputMode),
									multiple:ismultiselect,
									showType :showUnitType,
									filterCondition:filterCondition,
									ok:function(){ 
										$("#"+labelField).focus();
										$("#"+labelField).blur();
										_this.setToRealDisplayColumn( );
										_this.setValue2Object( );
										_this.afterUnitChoose();
									}
								});
						 	}
						 });
					 
				 }
				 
			 },
			 
			//通过类型返回type
				getTypeByinputMode:function(inputMode){
					var type="MyUnit";
					 if(dyFormOrgSelectType.orgSelectAddress == inputMode) {//单位通讯录
						 type="Unit";
					}else if(dyFormOrgSelectType.orgSelectStaff == inputMode){//组织选择框(仅选择组织人员)
						 type="MyUnit";
					}else if(dyFormOrgSelectType.orgSelectDepartment == inputMode){//组织选择框(仅选择组织部门)
						 type="Dept";
					}else if(dyFormOrgSelectType.orgSelect == inputMode){//组织选择框(仅选择组织人员)
						 type="MyUnit";
					}else if(dyFormOrgSelectType.orgSelectStaDep == inputMode){//组织选择框（部门+人员）
						type="MyUnit";
					}else if(dyFormOrgSelectType.orgSelectJob == inputMode){//组织选择框(职位)
						type="Job";
					}else if(dyFormOrgSelectType.orgSelectPublicGroup == inputMode){//组织选择框(公共群组)
						type="PublicGroup";
					}else if(dyFormOrgSelectType.orgSelectMyDept == inputMode){//组织选择框(我的部门)
						type="MyDept";
					}else if(dyFormOrgSelectType.orgSelectMyParentDept == inputMode){//组织选择框(上级部门)
						type="MyParentDept";
					}else if(dyFormOrgSelectType.orgSelectMyUnit == inputMode){//组织选择框(部门+职位+人员)
						type="MyUnit";
					}
					return type;
				},
				
				getSelectTypeByinputMode:function(inputMode){
					var selectType=1;
					 if(dyFormOrgSelectType.orgSelectAddress == inputMode) {//单位通讯录
						  selectType=1;
					}else if(dyFormOrgSelectType.orgSelectStaff == inputMode){//组织选择框(仅选择组织人员)
						 selectType=4;
					}else if(dyFormOrgSelectType.orgSelectDepartment == inputMode){//组织选择框(仅选择组织部门)
						selectType=2;
					}else if(dyFormOrgSelectType.orgSelect == inputMode){//组织选择框(仅选择组织人员)
						selectType=1;
					}else if(dyFormOrgSelectType.orgSelectStaDep == inputMode){//组织选择框（部门+人员）
						selectType=6;
					}else if(dyFormOrgSelectType.orgSelectJob == inputMode){//组织选择框(职位)
						selectType=32;
					}else if(dyFormOrgSelectType.orgSelectPublicGroup == inputMode){//组织选择框(公共群组)
						selectType=1;
					}else if(dyFormOrgSelectType.orgSelectMyDept == inputMode){//组织选择框(我的部门)
						selectType=1;
					}else if(dyFormOrgSelectType.orgSelectMyParentDept == inputMode){//组织选择框(上级部门)
						selectType=1;
					}else if(dyFormOrgSelectType.orgSelectMyUnit == inputMode){//组织选择框(部门+职位+人员)
						selectType=1;
					}
					return selectType;
				},
				
			 
			 /*设值到标签中*/
			 setValue2LabelElem:function(){
				 if(this.$labelElem == null){
					 return;
				 } 
				 this.$labelElem.html(this.getDisplayValue());
			 },
			 
			 /*设置到可编辑元素中*/
			 setValue2EditableElem:function(){ 
				 if(this.$editableElem == null){
					 return;
				 } 
				this.$editableElem.attr("hiddenvalue", this.getValue());
				
				this.$editableElem.val(this.getDisplayValue());
				 
			 },
			 
			 
			 isValueMap:function(){
				 return true;
			 } ,
			 
			 setValueByMap:function(valuemap){
				 this.value = valuemap;
				 if(!this.options.isHide){//该控件被隐藏时则不进行渲染
					 this.render();//将值渲染到页面元素上
				 } 
				 this.setToRealDisplayColumn();//设置到真实值，显示值字段上 
			 },
			 
			  
			
			//设值,值为真实值
			 setValue:function(value){
				 var options = this.options;
				if(value.length > 0){
					 var value1= null;
					if(value.indexOf(",") != -1){//值用,隔开 
						value1=value.split(",");
					}else{//值用;隔开
						value1=value.split(";");
					}
					var valueMap = {};
					 
					 for(var i=0;i<value1.length;i++){
						 valueMap[value1[i]] = value1[i];
					 }
					 this.setValueByMap(JSON.cStringify(valueMap));
				}
			 } ,
			 
			//将值分别从labelfield和valuefield中取出放到内存中的value对象中
			 setValue2Object:function(){ 
				 var realValue = this.$editableElem.attr("hiddenvalue"); 
				 if(realValue.length == 0){
					 this.setValueByMap("");
					 return;
				 }
				
				 var displayValue = this.$editableElem.val();
				 var values = realValue.split(';');
				 var displayvalue=displayValue.split(';');
				 if(values.length!=displayvalue.length){
					 throw new Error('隐藏值和显示值长度不一致!');
				 }
				 var v = {};
				 for ( var i = 0; i < values.length; i++) {
					 v[values[i]]=displayvalue[i];
				} 
				 this.setValueByMap( JSON.cStringify(v));
			 },
			 /*选择完组织之后 */
			 afterUnitChoose:function(){//控件自定义事件
				 var eventName = "afterUnitChoose";
				 var _this = this;
				 var result = {};
				 if(typeof loadPersonInfo != "undefined"){
					var userIds = _this.getValue();
					if(typeof userIds == "undefined" || userIds == null || userIds.length == 0 ){ 
					}else  if(userIds.indexOf(";") != -1){
						
					}else if($.trim(userIds).length > 0){
						result = loadPersonInfo(_this.getValue()); 
						this.invoke("afterOrgInfo", result);
					} 
				 }
				 this.bind(eventName, function(result){
					//console.log(JSON.cStringify(SpringSecurityUtils.getCurrentUserName()));
					console.log(JSON.cStringify(result));
					
					 for(var i in result){
						 if(typeof i == "string"){
							 if(typeof console != "undefined"){
								 console.log(i + "--->" + result[i]);
							 }
							 var control = $.ControlManager.getCtl(i);
							 if(typeof control != "undefined" && control != null){
								 control.setValue(result[i]);
							 }
						 }
					 }
				 }, true);
				 this.invoke(eventName, result);
			 }
		  
	};
	
	/*
	 * UNIT PLUGIN DEFINITION =========================
	 */
	$.fn.wunit = function(option) {
		var method = false;
		var args = null;
		if (arguments.length == 2) {
			method = true;
			args = arguments[1];
		}
		
		if (typeof option == 'string') {
			if(option === 'getObject'){ //通过getObject来获取实例
				var $this = $(this);
				data = $this.data('wunit');
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
					data = $this.data('wunit'),
					options = typeof option == 'object'
							&& option;
					if (!data) {
						 data = new Unit($(this),options);  
						$.extend(data,$.wControlInterface); 
						 $.extend(data,$.wTextCommonMethod);
						 $.extend(data,$.Unit);
						 data.init();
						 $this.data('wunit',data );
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

	$.fn.wunit.Constructor = Unit;

	$.fn.wunit.defaults = {
			columnProperty:columnProperty,//字段属性
			commonProperty:commonProperty,//公共属性
	        readOnly:false,
	        disabled:false,
	        isHide:false,//是否隐藏
	        mutiSelect:true,
	        showUnitType:true,
	        filterCondition:null
	        
	};
	
})(jQuery);