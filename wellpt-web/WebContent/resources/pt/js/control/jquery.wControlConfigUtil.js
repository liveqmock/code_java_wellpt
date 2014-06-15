;
(function($) {
	$.ControlConfigUtil = {
		  	/**
	     	 * 控件初始化设置
	     	 */
	     	ctlPropertyComInitSet:function (field){
	     		//初始化应用于
	     		this.initApplyToTree($("#applyTo"));
	     		$("#applyTo").wcomboTree("getObject").setValue(field.applyTo);
	     		$("#name").val(field.name);
	     		$("#displayName").val(field.displayName);
	     		$("#dbDataType").val(field.dbDataType);
	     		selColor(field.fontColor);
	     		$("#fontSize").val(field.fontSize);
	     		$("#length").val(field.length);
	     		$("#ctlWidth").val(field.ctlWidth);
	     		$("#ctlHight").val(field.ctlHight);
	     		$("#textAlign").val(field.textAlign);
	     		$("#defaultValue").val(field.defaultValue);
	     		$("#onlyreadUrl").val(field.onlyreadUrl); 
	     		//设置约束条件
	     		if((typeof field.fieldCheckRules) != "undefined" 
	     				&& field.fieldCheckRules != null){
	     			var fieldCheckRules =  field.fieldCheckRules;
	     			for(var i = 0; i < fieldCheckRules.length ; i++){
	     				var fieldCheckRule = fieldCheckRules[i];
	     				$("input[name='fieldCheckRules'][value='" + fieldCheckRule.value + "']").each(function(){
	     					this.checked = true;
	     				});
	     			}
	     		}
	     		$("input[name='showType'][value='" + field.showType + "']").each(function(){
	     			this.checked = true;
	     		});
	     		//如果field已存在，则name不可编辑
	     		if(field.name!=""&&field.name!=undefined){
	     			$("#name").attr("readonly","readonly");
	     		}else{
	     			$("#name").removeAttr("readonly");
	     		}
	     		
	     	},
	     	
	     	
	     	/**
			 * 控件属性编辑对话框onshow
			 * @param _editor
			 * @param _this
			 * @param ctlname
			 */
			ctlEditDialogonShow:function(_editor,_this,ctlname){
				if(_editor==undefined){
	            	return ;
	            }
	         	if(_editor.focusedDom != null && (typeof _editor.focusedDom != "undefined")){
	         		//通过双击ckeditor中的从表元素,表示修改从表属性,则不需要做焦点位置判断
	         		return;
	         	}
	         	//判断光标焦点位置是不是在从表中，若在其他从表中则不允许插入新的从表
	         	var selection = _editor.getSelection();
	         	 var selected_ranges = selection.getRanges(); // getting ranges
	         	 var node = selected_ranges[0].startContainer; // selecting the starting node
	              
	         	  var parents = node.getParents(true);
	         	  var parentsLength = parents.length;
	         	  if(parentsLength > 3){
		    			  var fieldClazzElement = null; 
		        		  for(var i = parentsLength - 1; i > -1 ; i --){
		        			  var parent = parents[i];
		        			  if(parent.type != 3 && parent.getName() == "tr"){
		        				  fieldClazzElement = parent; //获取包括焦点的最内层的tr,该tr的class将被设置成"field"
		        			  }else if(parent.type != 3 ){
		        				  valueClassElement = parent; 
		        			  } 
		        		  }
		        		  if(fieldClazzElement == null){
		        			  _this.hide();
		        			  alert("请将您的光标移至布局表格中!!");
		        			  return;
		        		  }else{
		        			  editor.fieldClazzElement = fieldClazzElement;
		        		  }
	         	  }else{ 
	         		 _this.hide();
	         		  alert("在插件"+ctlname+"控件之前，请先添加布局表格!!");
	         	  }
	         },
	     	
	         
	         /**
	 		 * 控件属性编辑对话框ok事件
	 		 * @param ctl
	 		 * @param containerID
	 		 * @returns {Boolean}
	 		 */
	 		ctlEditDialogOnOk:function(ctl,containerID){
	 			var checkpass=ctl.collectFormAndFillCkeditor();  
	         	if(!checkpass){
	         		return false;
	         	} 
	         	ctl.exitDialog(); 
	            	window.setTimeout(function(){ 
	         		 $("#"+containerID).empty();//重新初始化属性窗口 
	 			 }, 100);
	            	return true;
	 		},
	 		
	 		/**
	 		 * 控件属性编辑对话框退出事件
	 		 * @param ctl
	 		 * @param containerID
	 		 */
	 		ctlEditDialogOnCancel:function(ctl,containerID){
	 			if( (typeof ctl.exitDialog) != "undefined"){
	 				ctl.exitDialog(); 
	         		window.setTimeout(function(){ 
	            		 $("#"+containerID).empty();//重新初始化属性窗口 
	 				 }, 100);
	         	}
	 		},
	 		
	 		 /**
			  * 初始化applytotree
			  */
			initApplyToTree:function(elment){
				 elment.wcomboTree({
			 		 treewidth: 300,
			 		 treeheight: 220,
			 		 serviceName:"dataDictionaryService.getFromTypeAsTreeAsync('DY_FORM_FIELD_MAPPING')",
			 		 initService : "dataDictionaryService.getKeyValuePair",
			 		 initServiceParam : [ "DY_FORM_FIELD_MAPPING" ],
			 		 mutiselect:true,
			 		 isinitTreeById:true
			 	 }); 
				 elment.addClass("input-tier");
			 },
			 
			/**
			 * 收集控件一些公共属性
			 * @param field
			 */
			collectFormCtlComProperty:function(field){
				var checkpass=true;
				field.name = $("#name").val();
				field.displayName = $("#displayName").val();
				if(field.name==""||field.name==undefined){
					alert("字段编码不能为空!");
					return false;
				}
				if(field.displayName==""||field.displayName==undefined){
					alert("显示名称不能为空!");
					return false;
				}
				field.valueCreateMethod = "1";
				field.applyTo=$("#applyTo").wcomboTree("getObject").getValue();
				field.fontColor = $("#fontColor").val();
				field.fontSize = $("#fontSize").val();
				field.ctlWidth = $("#ctlWidth").val();
				field.ctlHight = $("#ctlHight").val();
				field.textAlign = $("#textAlign").val();
				field.showType = $("input[name='showType']:checked").val();
				field.defaultValue = $("#defaultValue").val();
				field.onlyreadUrl = $("#onlyreadUrl").val(); 
				if($("#length").size()>0){
					field.length = $("#length").val(); 
				}
				if($("#dbDataType").size()>0){
					field.dbDataType = $("#dbDataType").val(); 
				}
				$("input[name='fieldCheckRules']:checked").each(function(){
					if(this.checked){
						field.fieldCheckRules.push({value:$(this).val(), label:$(this).next().text()});
					}
				});
				return checkpass;
			},

	     	
			/**
			 * 初始化数据字典字树
			 */
			initDictCode:function(){
				 var ctrlFieldSetting = {
						 async : {
						 otherParam : {
						 "serviceName" : "dataDictionaryService",
						 "methodName" : "getAsTreeAsyncForControl",
						 }
						 },
						 check: {
								enable: false
							},
						 callback : {
							 onClick:treeNodeOnClickForctrlFieldSetting,
							 beforeClick: zTreeBeforeClick
						 }
						 };
						 $("#dictName").comboTree({
							 labelField : "dictName",
							 valueField : "dictCode",
							 treeSetting : ctrlFieldSetting,
							 width: 220,
							 height: 220
						 });
						 function treeNodeOnClickForctrlFieldSetting(event, treeId, treeNode) {
							 $("#dictName").val(getAbsolutePath(treeNode));
							 $("#dictCode").val(treeNode.data+':'+getAbsolutePath(treeNode));
						 }
						 function zTreeBeforeClick(treeId, treeNode, clickFlag) {
							    return treeNode.isParent;//当是父节点 返回false 不让选取
							}
							// 获取树结点的绝对路径
						function getAbsolutePath(treeNode) {
								var path = treeNode.name;
								var parentNode = treeNode.getParentNode();
								while (parentNode != null) {
									path = parentNode.name + "/" + path;
									parentNode = parentNode.getParentNode();
								}
								return path;
							}
						$("#dictName").addClass("input-tier");
			},
			
			
			
			
};
	

	
})(jQuery);