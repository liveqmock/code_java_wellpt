var data = {};
(function($) {
	
	/*
	 * SUBFORM CLASS DEFINITION ======================
	 */
	var SubForm4Group = function(element, options) {
		this.$element = $(element);
		this.options = $.extend({}, $.fn["wsubForm4Group"].defaults, options,
				this.$element.data());
	};

	SubForm4Group.prototype = {
		constructor : SubForm4Group,
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
			 
			
			var fields = this.options.formDefinition.sortFieldsOfSubform(formUuid, false);
		 
			for(var i in fields){
				var field = fields[i];
				jqGridColNames.push(field.displayName);
				var model =  this.getJqGridColModel(fields[field.name]); 
				model.editable =  this.isFieldEditable(field);//可编辑与否 
				model.sortable = (typeof field.sortable) == "undefined" ? false: field.sortable;//是否支持点击排序
				model.controlable = (field.controlable && field.controlable == dySubFormFieldCtl.control)? true:false;
				model.hidden = this.isFieldHidden(field);
				
				if((typeof field.width) != "undefined" &&  field.width.length > 0 ){//列宽度
					model.width =  field.width;
				}
				jqGridColModels.push(model); 
			}
			var expandColumn = "";//分组列名,以第一个不隐藏的列做为分组列名
			for(var i = 0; i < jqGridColModels.length; i ++){
				var hidden = jqGridColModels[i].hidden;
				var name =  jqGridColModels[i].name;
				if(typeof hidden == "undefined" || hidden == dySubFormFieldShow.show ){
					expandColumn = name;
					break;
				}
			}
			 
			 var subBtnId = "operateBtn" +   formUuid;//操作按钮的id
			 var btnAddId = "btn_add_" + formUuid; 
			 var btnEditId = "btn_edit_" + formUuid; 
			 var btnDelId = "btn_del_" +  formUuid; 
			 var btnRefreshId = "btn_refresh_" +  formUuid; 
			 var btnAddSubId = "btn_add_sub_" + formUuid; 
			 
			 
			// var options =  _paranentthis.cache.get.call(_paranentthis,cacheType.options);
			 var options = this.options;
			 if(options.readOnly ){//只读
				 //TODO here set every field control to readonly 
			 } else{//非只读
				 if(subform.hideButtons == dySubFormHideButtons.show){//显示操作按钮，及其事件
					 //对从表的数据进行编辑时，有两种展示方式，一种是直接在jqgrid对表单数据进行编辑,一种是弹出到新窗口再对数据进行编辑
					
					 var btnAddElem = '<button  id="' + btnAddId +   '"   >' + dybtn.add  +'</input>' ;
					 var btnEditElem =  '<button  id="' + btnEditId +   '"   >' + dybtn.edit  +'</input>' ;
					 var btnDelElem = '<button id="' + btnDelId +   '" >' + dybtn.del  +'</button>' ;
					 var btnRefreshElem = '<button id="' + btnRefreshId +   '" >' + '分组刷新'  +'</button>' ;
					 var strbtn1 = '<span id="'+subBtnId+'">';
					 var strbtn2 = '</span>';
					 var strSpace =  '&nbsp;&nbsp;&nbsp;&nbsp;';
					 if(subform.editMode == dySubFormEdittype.newWin){//在新窗口编辑
						  //而对于在窗口中编辑表单数据的需要“编辑“的按钮
						 jqGridCaption += strbtn1 + strSpace + btnAddElem + strSpace  + strSpace + btnEditElem  + strSpace +btnDelElem +btnRefreshElem+ strbtn2;
						 
						 _paranentthis.$("#" + btnAddId).live("click", function(){//定义操作事件--添加行 
							 try{
								 _this.editSubformRowDataInDialog( subform, {});
							 }catch(e){
								console.log(e);
							 }
							 return false;
						 });
						 
						 _paranentthis.$("#" + btnRefreshId).live("click", function(){//定义操作事件--刷新行 
							 try{
									//分组刷新..
								 var formUuid=_this.options.formUuid;
									//获得表单数据
									var formdatas=_this.collectSubformData();
									$('#'+formUuid).jqGrid("clearGridData");
									//重新填充
									_this.fillFormData(formdatas);
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
						 jqGridCaption += strbtn1 + strSpace + btnAddElem+ strSpace  + strSpace + btnDelElem+btnRefreshElem + strbtn2; 
						 
						 _paranentthis.$("#" + btnAddId).live("click", function(){//定义操作事件--添加行 
							 try{
								 _this.addSubformRowDataInJqGrid(formUuid);
							 }catch(e){
									console.log(e);
							 }
							 return false;
						 }); 
						 
						 _paranentthis.$("#" + btnRefreshId).live("click", function(){//定义操作事件--刷新行 
							 try{
								//分组刷新..
								 var formUuid=_this.options.formUuid;
									//获得表单数据
									var formdatas=_this.collectSubformData();
									$('#'+formUuid).jqGrid("clearGridData");
									//重新填充
									_this.fillFormData(formdatas);
									
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
				 data: [], 
				 datatype: "local", 
				 height: 'auto', 
				 rownumbers:true,
				 rowList: [1000],
				 rowNum: 1000,
				 autowidth:true,
				 shrinkToFit: true,
				colNames:jqGridColNames,
				colModel:jqGridColModels, 
				 grouping:true, 
		 		 groupingView : {
		 			 groupField : [_this.options.groupField], 
		 			 groupColumnShow: [_this.options.groupColumnShow]
		 		 },  
			   
			   	afterEditCell:function(){},
			   	loadComplete:function(){},
			   	caption:"<span class='collapsesubform' id='openDiv'></span>" +  jqGridCaption,
			   	gridComplete:function(){
			   		console.log("subform load gridComplete  ");
			   	},
			   	afterInsertRow:function( rowId, rowdata, rowelem){
			   		if(rowdata["newRow"]==true){
			   			_this.initRowControls(formUuid,rowId,rowdata);
			   		}
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
			   		//如果去掉下面的代码，第一次点击后，在光标移开之后没办法再还原为label状态
			   		var dom = control.getCtlElement()[0];
			   		//TODOradio和checkbox需要特殊处理
			   		if(control.getAllOptions().commonProperty.inputMode==dyFormInputMode.checkbox||
			   				control.getAllOptions().commonProperty.inputMode==dyFormInputMode.radio){
			   			return ;
			   		}
			   		
			   		if(!_this.options.readOnly){
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
			
		},
		
		/**
		 * 为从表添加行数据
		 * @param formUuid
		 * @param data 数据格式：{name1:value1,name2: value2…} name为在colModel中指定的名称
		 */
		//initializeControlFromRemoteFlag:false, 
		addRowData: function(formUuid, data){  
			var t = formUuid;
			if(arguments.length == 1){
				formUuid = this.options.formUuid;
				data = t;
			}
			if(typeof data["id"] == "undefined"){//调用方没有生成行id,这里也为dataUuid
				console.log("新数据");
				data["id"] = this.createUuid();  
				data["newRow"] = true; //添加新增的行，
			}
			// level:"0", parent:"", isLeaf:false, expanded:true, loaded:true
			if( typeof data["level"] == "undefined"){
				  data["level"] = "0"; 
			}
			
			if( typeof data["parent_uuid"] == "undefined"){
				  data["parent_uuid"] = ""; 
			}
			 
			if( typeof data["isLeaf"] == "undefined"){
				  data["isLeaf"] = true; 
			}
			if( typeof data["expanded"] == "undefined"){
				  data["expanded"] = true; 
			}
			if( typeof data["loaded"] == "undefined"){
				  data["loaded"] = true; 
			}
			 
			this.options.$paranentelement.$("#" + formUuid).jqGrid('addChildNode',data["id"],data["parent_uuid"], data);//jqgrid的行ID与Id值一样
			this.updateSeqNo(formUuid,data["id"]);//更新从表的序号
			
			
			 
		},
		
		/**
		 * 更新序号
		 * @param formUuid
		 * @param parentNode
		 */
		updateSeqNo:function(formUuid,rowid){
				var obj = this.options.$paranentelement.$("#" + formUuid).jqGrid("getRowData");
				var i=obj.length-1;
				this.options.$paranentelement.$("#" + formUuid).jqGrid("setCell", rowid, "seqNO", i + 1 ); 
		},
		
		/**
		 * 填充从表数据.
		 * @param formUuid
		 * @param formDatas
		 */
		fillFormData: function(formDatas){
			for(var i =0; i < formDatas.length;  i++){
				var formData = formDatas[i];
				formData["id"] = formData.uuid;
				this.addRowData(this.options.formUuid, formData);
				
			}
			
			var groupfield=this.options.groupField;
			var formUuid=this.options.formUuid;
			
			//分组刷新..
			$('#'+formUuid).jqGrid('groupingGroupBy',groupfield, {
				   groupOrder : [this.options.groupOrder],
				   groupColumnShow: [this.options.groupColumnShow],
				   groupCollapse: this.options.groupCollapse
				  }); 
			
			
		
			 colModel=$('#'+formUuid).jqGrid('getGridParam','colModel');
			 var subFormDefinition=this.options.subformDefinition;
			 //初始化控件 并赋值
			 var subformctl=this;
			 var _this=this;
			 //例如
			 //
			   var obj = $('#'+formUuid).jqGrid("getRowData");
			   for(var i =0; i < formDatas.length;  i++){
				   var rowdata=formDatas[i];
			  
			    jQuery(obj).each(function(){
			    	if(rowdata.id!=this.id){
			    		return ;
			    	}
			     var rowId=this.id;
			 
			   	for(var j = 0 ; j < colModel.length; j ++){
					var model = colModel[j];
					var fieldName = model.name;
					if(!subformctl.isControlFieldInSubform(fieldName) ){
						continue;
					}
					var id = subformctl.getCellId(rowId, fieldName);
				
					var fieldDefinition = subFormDefinition.fields[fieldName]; 
					 if(typeof fieldDefinition == "undefined"){
						 continue;
					 }
					
					var cellValue = rowdata[fieldName];
					 
					$.ControlManager.createControl(id, fieldDefinition, subFormDefinition); 
					var control = $.ControlManager.getControl(id);
					subformctl.setValue(control, cellValue, rowId);
					control.setPos(dyControlPos.subForm);
					
					var isvaluemap=control.isValueMap();
					var groupvalue='';
					if(isvaluemap){
						groupvalue=control.getDisplayValue();
					}else{
						groupvalue=cellValue;
					}
					
					if(fieldName==groupfield){
						var groupspan={};
						if($('#'+rowId).prev().is('tr')){
							if($('#'+rowId).prev().attr('id').indexOf('ghead')>'-1'){
									//如果是分组收起的，获得分组图标后面的span，再设值。
									 groupspan=$('#'+rowId).prev().find('span').eq(1);
										var spanid=groupspan.attr("id");
									    if(typeof spanid != "undefined" && spanid.indexOf('ghead')>'-1'){
									   	 if(groupspan.html()!=groupvalue){
												if(groupvalue!=''&&groupvalue!=undefined){
													groupspan.html(groupvalue);
												}else{
													groupspan.html('&nbsp');
												}
											}
									    }
							}
						}
					}
					if(control==undefined) return;
					//设置分组验证id
					control.setValidateGroup({groupName:fieldName, groupUsed:"uniqueGroup"});
					
					
					
					subformctl.addControlUrl(control,rowId);
					
					_this.setBlurPartOfSwitch(control, model);
			   	}
			});
			    
			   }
			var i=0;
			   var obj = $('#'+formUuid).jqGrid("getRowData");
			   jQuery(obj).each(function(){
			     var rowId=this.id;
			     _this.options.$paranentelement.$("#" + formUuid).jqGrid("setCell", rowId, "seqNO", i + 1 ); 
				i++;
			   });
		}		
		
	};
	
	/*
	 * SUBFORM PLUGIN DEFINITION =========================
	 */
	$.fn.wsubForm4Group = function(option) {
		var method = false;
		var args = null;
		if (arguments.length == 2) {
			method = true;
			args = arguments[1];
		}
		
		if (typeof option == 'string') {
			if(option === 'getObject'){ //通过getObject来获取实例
				var $this = $(this);
				var data = $this.data('wsubForm4Group');
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
					data = $this.data('wsubForm4Group'), 
					options = typeof option == 'object'
							&& option;
					if (!data) {
						 data = new SubForm4Group(this,options);
						 var datacopy={};
						 var data1=$.extend(datacopy,data);
						 var extenddata=$.extend(data,$.wSubFormMethod);
						 var data2=$.extend(extenddata,data1);
						 data2.init();
						 $this.data('wsubForm4Group',data2 );
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

	$.fn.wsubForm4Group.Constructor = SubForm4Group;

	$.fn.wsubForm4Group.defaults = {
			$paranentelement:{},
			formDefinition:'',
			subformDefinition:'',
			readOnly:false,
			mainformDataUuid:'',
			formUuid:'',
			groupField:'',
			groupOrder : 'asc',
			groupColumnShow: true,
			groupCollapse:false
	};
	
})(jQuery);