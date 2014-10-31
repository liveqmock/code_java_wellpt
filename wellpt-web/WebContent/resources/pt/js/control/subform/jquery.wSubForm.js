;
(function($) {
	
	/*
	 * SUBFORM CLASS DEFINITION ======================
	 */
	var SubForm = function(element, options) {
		this.$element = $(element);
		this.options = $.extend({}, $.fn["wsubForm"].defaults, options,
				this.$element.data());
	};

	SubForm.prototype = {
		constructor : SubForm,
		init:function(){
			var _this=this;
			this.$element.hide();
			var _paranentthis=this.options.$paranentelement;
			var _tableelement=this.$element.next();
			//初始化
			var formUuid =this.options.formUuid; 
			//获取从表定义
			var subform = this.options.formDefinition.subforms[formUuid];
			if(subform == null){//在定义中找不到该表单 
				_tableelement.remove();//找不到定义就直接删除
				return true;
			}
			
			_tableelement.attr("id", formUuid);//从表的table 的id属性值为formuuid
			var subFormDefinition = this.options.subformDefinition;//加载从表对应的那张表的完整定义
			if(subFormDefinition == null){
				_tableelement.remove();//找不到定义就直接删除
				return true;
			}
			
			
			
			
			/*
			 *从表以jqGrid来展示
			 */
			//从表的标题
			var jqGridCaption = subform.displayName;
			
			//收集从表列信息
			var jqGridColNames = [];//从表列标题 
			var jqGridColModels = [];//各列的配置信息
			
			//行ID,dataUuid
			jqGridColNames.push("id");
			jqGridColModels.push({name:'id',index:'id',hidden:true});
			jqGridColNames.push("parent_uuid");
			jqGridColModels.push({name:'parent_uuid',index:'parent_uuid',hidden:true});
			//序号
			jqGridColNames.push("序号");
			jqGridColModels.push({name:'seqNO',index:'seqNO',width:'25px', align:"center", sortable:false});
			
			
			
			
			
			//var fields = subform.fields;
		  	var fields = this.options.formDefinition.sortFieldsOfSubform(formUuid, false);
			for(var i in fields){
				var field = fields[i];
				jqGridColNames.push(field.displayName);
				var model =  this.getJqGridColModel(fields[field.name]); 
				model.editable = this.isFieldEditable(field);
				model.sortable = (typeof field.sortable) == "undefined" ? false: field.sortable;//是否支持点击排序
				model.hidden = this.isFieldHidden(field);
				model.controlable = (field.controlable && field.controlable == dySubFormFieldCtl.control)? true:false;
				 
				if((typeof field.width) != "undefined" &&  field.width.length > 0 ){//列宽度
					model.width =  field.width;
				}
				 
				jqGridColModels.push(model); 
			}
			
			

			var expandColumn = "";//分组列名,以第一个不隐藏的列做为分组列名
			for(var i = 0; i < jqGridColModels.length; i ++){
				
				var hidden = jqGridColModels[i].hidden;
				var name =  jqGridColModels[i].name;
				if(typeof hidden == "undefined" || hidden == false){
					expandColumn = name;
					break;
				}
			}
			
			 	
			 var subBtnId = "operateBtn" +   formUuid;//操作按钮的id
			 var btnAddId = "btn_add_" + formUuid; 
			 var btnEditId = "btn_edit_" + formUuid; 
			 var btnDelId = "btn_del_" +  formUuid; 
			 var btnAddSubId = "btn_add_sub_" + formUuid; 
			 
			// var options =  _paranentthis.cache.get.call(_paranentthis,cacheType.options);
			 var options = this.options;
			 if(options.readOnly ){//只读
				 //TODO here set every field control to readonly 
			 } else{//非只读
				 if(subform.hideButtons == dySubFormHideButtons.show){//显示操作按钮，及其事件
					 
					 //对从表的数据进行编辑时，有两种展示方式，一种是直接在jqgrid对表单数据进行编辑,一种是弹出到新窗口再对数据进行编辑
					
					 var btnAddElem = '<button  id="' + btnAddId +   '"   >' + dybtn.add  +'</input>' ;
					 var btnAddSubElem = '<button  id="' + btnAddSubId +   '"   >' + dybtn.addSub  +'</input>' ;
					 var btnEditElem =  '<button  id="' + btnEditId +   '"   >' + dybtn.edit  +'</input>' ;
					 var btnDelElem = '<button id="' + btnDelId +   '" >' + dybtn.del  +'</button>' ;
					 var strbtn1 = '<span id="'+subBtnId+'">';
					 var strbtn2 = '</span>';
					 var strSpace =  '&nbsp;&nbsp;&nbsp;&nbsp;';
					 if(subform.editMode == dySubFormEdittype.newWin){//在新窗口编辑
						  //而对于在窗口中编辑表单数据的需要“编辑“的按钮
						 jqGridCaption += strbtn1 + strSpace + btnAddElem + strSpace + btnAddSubElem + strSpace + btnEditElem  + strSpace +btnDelElem + strbtn2;
						 
						 _paranentthis.$("#" + btnAddId).live("click", function(){//定义操作事件--添加行 
							 try{
								 _this.editSubformRowDataInDialog( subform, {});
							 }catch(e){
								console.log(e);
							 }
							 return false;
						 });
						 
						 
						 _paranentthis.$("#" + btnAddSubId).live("click", function(){//定义操作事件--添加子行 
							  
							 try{
								 var selectedRowId = $(this).attr("selectedrowid"); 
								if(typeof selectedRowId == "undefined" || selectedRowId == null || selectedRowId.lengh == 0){  
									oAlert(dymsg.selectRecordModErr);
									return false;
								}
								var rowData = _this.getRowData(formUuid, selectedRowId);
								 
								_this.editSubformRowDataInDialog( subform, {}, rowData);
							 }catch(e){
									console.log(e);
							 }
							 return false;
						 });
						 
						 _paranentthis.$("#" + btnEditId).live("click", function(){//定义操作事件--编辑行 
							 try{
								  
								 var selectedRowId = $(this).attr("selectedrowid"); 
									//var ids = _paranentthis.$("#" + subform.formUuid).jqGrid('getGridParam','selarrrow');
									if(typeof selectedRowId == "undefined" || selectedRowId == null || selectedRowId.lengh == 0){  
										oAlert(dymsg.selectRecordModErr);
										return false;
									}
									var rowData = _this.getRowData(formUuid, selectedRowId);
									 
								
									_this.editSubformRowDataInDialog( subform, rowData); 
							 }catch(e){
									console.log(e);
							 }
							 return false;
						 });
						 
					 }else{//直接在jqgrid编辑
						//对于直接在jqgrid对表单数据进行编辑的方式，不需要“编辑“的按钮,直接在jqgrid中编辑即可
						 jqGridCaption += strbtn1 + strSpace + btnAddElem+ strSpace + btnAddSubElem  + strSpace + btnDelElem + strbtn2; 
						 
						 _paranentthis.$("#" + btnAddId).live("click", function(){//定义操作事件--添加行 
							 try{
								 _this.addSubformRowDataInJqGrid(formUuid);
							 }catch(e){
									console.log(e);
							 }
							 return false;
						 }); 
						 
						 
						 _paranentthis.$("#" + btnAddSubId).live("click", function(){//定义操作事件--添加子行 
							  
							 try{
								 var selectedRowId = $(this).attr("selectedrowid"); 
								if(typeof selectedRowId == "undefined" || selectedRowId == null || selectedRowId.lengh == 0){  
									oAlert(dymsg.selectRecordModErr);
									return false;
								}
								var rowData = _this.getRowData(formUuid, selectedRowId);
								 
								_this.addSubformRowDataInJqGrid(formUuid, rowData);
							 }catch(e){
									console.log(e);
							 }
							 return false;
						 });
					 }
					  
					 _paranentthis.$("#" + btnDelId).live("click", function(){//定义操作事件--删除行 
						 try{
							 var selectedRowId = $(this).attr("selectedrowid");
							 
								//var ids = _paranentthis.$("#" + subform.formUuid).jqGrid('getGridParam','selarrrow');
								if(typeof selectedRowId == "undefined" || selectedRowId == null || selectedRowId.lengh == 0){  
									oAlert(dymsg.selectRecordModErr);
									return false;
								}
							 _this.deleteSubformRowDataEvent(formUuid, selectedRowId);
						 }catch(e){
								console.log(e);
						 }
						 
						 return false;
					 });
				 }
			 }
			  
			 
			//展示jqGrid
			 _tableelement.jqGrid({
				//beforeEditCell:function(){},
				datatype:'jsonstring',
				datastr:[],
		        autowidth: true,  
		        //autoheight: true, 
				colNames:jqGridColNames,
				colModel:jqGridColModels,
				 ExpandColumn: expandColumn,//分组列名 
				 treeGrid: true,
				  treeGridModel: "adjacency",
				 // treeIcons: {leaf:'ui-icon-document-b'},
				   loadui: "disable",
				   rowNum: 10000, 
			    jsonReader: {
				       repeatitems: false,
				       root: "response"
				   },
			   	//afterEditCell:function(){},
			   	loadComplete:function(){},
			   	caption:"<span class='collapsesubform' id='openDiv'></span>" +  jqGridCaption,
			   	gridComplete:function(){
			   		// console.log("subform load gridComplete  ");
			   	},
			   	afterInsertRow:function( rowId, rowdata, rowelem){
			   		_this.initRowControls(formUuid,rowId,rowdata);
			   	},
			   	onCellSelect:function(rowId, iCol,  cellcontent, e){
			   		if((options.readOnly ) //调用者要求只读
			   				|| subform.editMode == dySubFormEdittype.newWin//编辑直接在窗口中
			   				|| subform.hideButtons != dySubFormHideButtons.show//在定义中配置了不让编辑
			   		){
			   			return;
			   		}
			   		var colModels = $(this).jqGrid('getGridParam','colModel'); 
			   		var colModel = colModels[iCol];
			   		var fieldName = colModel.name;
			   		if(!_this.isControlFieldInSubform(fieldName) ){
						return;
					}
			   		 
			   		if(!colModel.editable){//列不可编辑
			   			return;
			   		}
			   		
			   		var id = _this.getCellId(rowId, fieldName);
			   		var control = $.ControlManager.getControl(id);
			   		
			   		if(control == undefined){
			   			return;
			   		}
			   		control.setEditable();
			   		
			   		//radio和checkbox需要特殊处理
			   		if(control.getAllOptions().commonProperty.inputMode==dyFormInputMode.checkbox||
			   				control.getAllOptions().commonProperty.inputMode==dyFormInputMode.radio){
			   			return ;
			   		}
			   		
			   	//如果去掉下面的代码，第一次点击后，在光标移开之后没办法再还原为label状态
			   		if(!_this.options.readOnly){//非只读状态
			   			var dom = control.getCtlElement()[0];
			   			dom.focus();
				   		var hadBeenFocused = dom.getAttribute("focused"); 
				   		if(typeof hadBeenFocused == "undefined" || hadBeenFocused == null){
				   			dom.setAttribute("focused", true); 
				   			dom.blur(); 
				   			dom.focus();
				   		}
			   		}
			   	}, 
			   	onSelectRow: function(id){
			   		_paranentthis.$("#" + btnAddSubId).attr("selectedRowId", id);
			   		_paranentthis.$("#" + btnEditId).attr("selectedRowId", id);
			   		_paranentthis.$("#" + btnDelId).attr("selectedRowId", id);
				}
			 
			});
			 
			  
			 this.definiteCollapse(subform.tableOpen);
			 
			 this.resetGridWidth();
			
		}
	};
	
	/*
	 * SUBFORM PLUGIN DEFINITION =========================
	 */
	$.fn.wsubForm = function(option) {
		var method = false;
		var args = null;
		if (arguments.length == 2) {
			method = true;
			args = arguments[1];
		}
		
		if (typeof option == 'string') {
			if(option === 'getObject'){ //通过getObject来获取实例
				var $this = $(this);
				var data = $this.data('wsubForm');
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
					data = $this.data('wsubForm'), 
					options = typeof option == 'object'
							&& option;
					if (!data) {
						 data = new SubForm(this,options);
						 var data1=$.extend(data,$.wSubFormMethod);
						 data1.init();
						 $this.data('wsubForm',data1 );
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

	$.fn.wsubForm.Constructor = SubForm;

	$.fn.wsubForm.defaults = {
			$paranentelement:{},
			formDefinition:'',
			subformDefinition:'',
			readOnly:false,
			mainformDataUuid:'',
			formUuid:''
	};
	
})(jQuery);