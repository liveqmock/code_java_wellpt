/**
 * 控件管理类，主要负责控件的动态创建以及控件的实例对象获取。
 */
;
(function($) {
	$.ControlManager = {
			
			 
			
			
			/**
			 * 控件创建,控件的id保存于占位符的name中
			 * @param $placeHolder  该控件的占位符
			 * @param column  控件配置
			 * @param formDefinition
			 */
			createCtl:function($placeHolder, column,formDefinition){ 
				 
				if(typeof $placeHolder == "undefined"  || $placeHolder == null|| $placeHolder.size() == 0){
					console.error("placeholder not defined");
					return null;
				}
				
				var ctlName = $placeHolder.attr("name"); 
				if(typeof ctlName == "undefined" || ctlName.length == 0){
					console.error("placeholer must have a name property ,used to save the control id");
					return null;
				}
				
				
				
				$placeHolder.hide();
				var ctlType = "";
				
					//var  appendhtml=getHtmlStrByInputDataType(inputMode,fieldcode,'_');
					// $("input[name='"+fieldcode+"']").after(appendhtml.html);
					// initDyControl(ctlName,appendhtml.formfieldcode,column,formDefinition);
				
						var columnProperty={
								//控件字段属性
								applyTo:column.applyTo,//应用于
								controlName:ctlName,//控件名称，由外面指定 
								columnName:column.name,//字段定义  fieldname
								displayName:column.displayName,//描述名称  descname
								dbDataType:column.dbDataType,//字段类型  datatype type
								indexed:column.indexed,//是否索引
								showed:column.showed,//是否界面表格显示
								sorted:column.sorted,//是否排序
								sysType:column.sysType,//系统定义类型，包括三种（0：系统默认，1：管理员常量定义，2：表单添加后自定义）
								length:column.length,//长度
								isHide:column.isHide,//是否隐藏
								showType:column.showType,//显示类型 1,2,3,4 datashow
								defaultValue:column.defaultValue,//默认值
								valueCreateMethod:column.valueCreateMethod,//默认值创建方式 1用户输入
								onlyreadUrl:column.onlyreadUrl,//只读状态下设置跳转的url
								realDisplay: column.realDisplay,
								formDefinition:formDefinition,
								pos:column.pos,
								data:column.data,
								dataUuid:column.dataUuid,
								relativeMethod:column.relativeMethod
						};
						 
						//控件公共属性
						var commonProperty={
								inputMode:column.inputMode,//输入样式 控件类型 inputDataType
								fieldCheckRules:column.fieldCheckRules,
								fontSize:column.fontSize,//字段的大小
								fontColor:column.fontColor,//字段的颜色
								ctlWidth:column.ctlWidth,//宽度
								ctlHight:column.ctlHight,//高度
								textAlign:column.textAlign//对齐方式
						};	
						var inputMode=commonProperty.inputMode; 
						//radio
						if(inputMode==dyFormInputMode.radio){
							$placeHolder.wradio({
								columnProperty:columnProperty,
						    	commonProperty:commonProperty,
								optionDataSource:column.optionDataSource, //备选项来源1:常量,2:字段
								optionSet:column.optionSet,
								dictCode:column.dictCode
							});
							ctlType = "wradio";
						//checkbox
						}else if(inputMode==dyFormInputMode.checkbox){
							$placeHolder.wcheckBox({
								columnProperty:columnProperty,
						    	commonProperty:commonProperty,
								ctrlField:column.ctrlField,
								optionDataSource:column.optionDataSource, //备选项来源1:常量,2:字段
								optionSet:column.optionSet,
								dictCode:column.dictCode,
								
								selectMode:column.selectMode,//选择模式，单选1，多选2
								singleCheckContent :column.singleCheckContent,//单选 选中内容
								singleUnCheckContent:column.singleUnCheckContent//单选 取消选中内容
						});
							ctlType = "wcheckBox";
						}else if(inputMode==dyFormInputMode.number){
							//numbertext
							$placeHolder.wnumberInput({
								columnProperty:columnProperty,
						    	commonProperty:commonProperty,
								decimal:column.decimal,
								negative:column.negative,
								operator:column.operator
								});
						
							ctlType = "wnumberInput";
						}else if(inputMode==dyFormInputMode.text){
							//text
							$placeHolder.wtextInput({
						    	columnProperty:columnProperty,
						    	commonProperty:commonProperty
								});
							ctlType = "wtextInput";
						}else if(inputMode==dyFormOrgSelectType.orgSelectAddress ||inputMode==dyFormOrgSelectType.orgSelectStaDep
								||inputMode==dyFormOrgSelectType.orgSelectDepartment||inputMode==dyFormOrgSelectType.orgSelectStaff
								||inputMode==dyFormOrgSelectType.orgSelectJob||inputMode==dyFormOrgSelectType.orgSelectPublicGroup
								||inputMode==dyFormOrgSelectType.orgSelectMyDept||inputMode==dyFormOrgSelectType.orgSelectMyParentDept
								||inputMode==dyFormOrgSelectType.orgSelectMyUnit){
							$placeHolder.wunit({
								columnProperty:columnProperty,
						    	commonProperty:commonProperty,
						    	mutiSelect:column.mutiSelect,
						    	showUnitType:column.showUnitType, //是否显示分类
						    	filterCondition:column.filterCondition //过滤条件
								});
							ctlType = "wunit";
						} 
						else if(inputMode==dyFormInputMode.serialNumber||inputMode==dyFormInputMode.unEditSerialNumber){
							//serialNumber	
							$placeHolder.wserialNumber({
								columnProperty:columnProperty,
						    	commonProperty:commonProperty,
								designatedId:column.designatedId,
								designatedType:column.designatedType,
								isOverride:column.isOverride,
								isSaveDb:column.isSaveDb,
								formUuid:formDefinition.uuid
								});
							ctlType = "wserialNumber";
						}else if(inputMode==dyFormInputMode.date){
							$placeHolder.wdatePicker({
								columnProperty:columnProperty,
						    	commonProperty:commonProperty,
						    	contentFormat:column.contentFormat
								});
							ctlType = "wdatePicker";
						}else if(inputMode==dyFormInputMode.textArea){
							$placeHolder.wtextArea({
						    	columnProperty:columnProperty,
						    	commonProperty:commonProperty
								});
							ctlType = "wtextArea";
						//富文本
						}else if(inputMode==dyFormInputMode.ckedit){
							$placeHolder.wckeditor({
						    	columnProperty:columnProperty,
						    	commonProperty:commonProperty,
								indent: true,
					            breakBeforeOpen: false,
					            breakAfterOpen: false,
					            breakBeforeClose: false,
					            breakAfterClose: false
						 });
							ctlType = "wckeditor";
						//combobox    
						}else if(inputMode==dyFormInputMode.selectMutilFase){
							$placeHolder.wcomboBox( {
						    	columnProperty:columnProperty,
						    	commonProperty:commonProperty,
								optionDataSource:column.optionDataSource, //备选项来源1:常量,2:字段
								optionSet:column.optionSet,
								dictCode:column.dictCode
								});
							ctlType = "wcomboBox";
						}else if(inputMode==dyFormInputMode.treeSelect){
							 
							$placeHolder.wcomboTree({
						    	columnProperty:columnProperty,
						    	commonProperty:commonProperty,
						    	serviceName:column.serviceName,
						    	treeWidth: column.treeWidth,
								treeHeight: column.treeHeight,
								mutiSelect:column.mutiSelect,
								realDisplay:column.realDisplay
								});
							ctlType = "wcomboTree";
						//视图展示    
						}else if(inputMode==dyFormInputMode.viewdisplay){
							$placeHolder.wviewDisplay({
						    	columnProperty:columnProperty,
						    	commonProperty:commonProperty,
						    	relationDataText : column.relationDataText, 		 	
								relationDataValue : column.relationDataValue,		 	
								relationDataSql : column.relationDataSql		
								});
							ctlType = "wviewDisplay";
						}else if(inputMode==dyFormInputMode.accessory3){ 
							$placeHolder.wfileUpload({
						    	columnProperty:columnProperty,
						    	commonProperty:commonProperty,	
						    	allowUpload:column.allowUpload,//允许上传
					            allowDownload:column.allowDownload,//允许下载
					            allowDelete:column.allowDelete,//允许删除
					            mutiselect:column.mutiselect,//是否多选
					            enableSignature:columnProperty.formDefinition.enableSignature == signature.enable
								});
							ctlType = "wfileUpload";
						}else if(inputMode==dyFormInputMode.accessoryImg){
							$placeHolder.wfileUpload4Image({
						    	columnProperty:columnProperty,
						    	commonProperty:commonProperty,	
						    	allowUpload:column.allowUpload,//允许上传
					            allowDownload:column.allowDownload,//允许下载
					            allowDelete:column.allowDelete,//允许删除
					            mutiselect:column.mutiselect,//是否多选
					            enableSignature:columnProperty.formDefinition.enableSignature == signature.enable
								});
							ctlType = "wfileUpload4Image";
						}else if(inputMode==dyFormInputMode.accessory1){
							$placeHolder.wfileUpload4Icon({
						    	columnProperty:columnProperty,
						    	commonProperty:commonProperty,	
						    	allowUpload:column.allowUpload,//允许上传
					            allowDownload:column.allowDownload,//允许下载
					            allowDelete:column.allowDelete,//允许删除
					            mutiselect:column.mutiselect,//是否多选
					            enableSignature:columnProperty.formDefinition.enableSignature == signature.enable
								});
							ctlType = "wfileUpload4Icon";
						//弹出对话框    
						}else if(inputMode==dyFormInputMode.dialog){
							 
							$placeHolder.wdialog({
						    	columnProperty:columnProperty,
						    	commonProperty:commonProperty,
						    	relationDataTextTwo: column.relationDataTextTwo,		 	
								relationDataValueTwo:column.relationDataValueTwo, 		 	
								relationDataTwoSql: column.relationDataTwoSql, 	
								relationDataDefiantion: column.relationDataDefiantion, 	
								relationDataShowMethod: column.relationDataShowType, 	
								relationDataShowType: column.relationDataShowType,
								selectType:column.selectType,
								destType:column.destType,
								destSubform: column.destSubform,
								pageSize:column.pageSize
								});
							ctlType = "wdialog";
						}else if(inputMode==dyFormInputMode.timeEmploy){
							 
							$placeHolder.wtimeEmploy({
						    	columnProperty:columnProperty,
						    	commonProperty:commonProperty, 
						    	resCode: column.resCode	
								});
							ctlType = "wtimeEmploy";
						}else if(inputMode==dyFormInputMode.embedded){
							 
							$placeHolder.wembedded({
						    	columnProperty:columnProperty,
						    	commonProperty:commonProperty
						    	 
								});
							ctlType = "wembedded";
						}else if(inputMode==dyFormInputMode.job){
							 
							$placeHolder.wjobSelect({
						    	columnProperty:columnProperty,
						    	commonProperty:commonProperty,
						    	realDisplay:column.realDisplay,
						    	optionSet:column.optionSet
								});
							ctlType = "wjobSelect";
						}else{ 
							console.error(ctlName + "--> unknown inputMode[" + inputMode + "]");
							return;
						}
						
						//注册公式
						if(column.formula)
							$.ControlManager.registFormula(column.formula);
						
						$.ControlManager[ctlName] = {"$placeHolder":$placeHolder, "ctlType":ctlType};
			},
			
			
			/**
			 * createSubFormControl
			 * 创建从表控件
			 */
			createSubFormControl:function($tableelement,parentelement,formDefinition, subformDefinition){
				var formUuid=$tableelement.attr('formUuid'); 
				var subformConfig=formDefinition.subforms[formUuid]; //从表配置
				if(typeof subformConfig == "undefined" || subformConfig == null){
					throw new Error(" cann't find subform[" + formUuid + "] in form definition json package]"); 
				}
				
				
				//var subformDefinition=loadFormDefinition(formUuid); //从表字段定义
				if(typeof subformDefinition == "undefined" || subformDefinition == null){
					subformDefinition=loadFormDefinition(formUuid);
					if(typeof subformDefinition == "undefined" || subformDefinition == null){
						throw new Error(" cann't find formUuid[" + formUuid + " in db]"); 
					} 
				}
				parentelement.cache.put.call(parentelement, cacheType.formDefinition, subformDefinition);
				 
				
				var groupColumnShow=true;
				if(subformConfig.isGroupColumnShow=='2'){
					groupColumnShow=false;
				}
				 
				if(subformConfig.isGroupShowTitle==dySubFormGroupShow.show){
					$tableelement.before('<input  id=_inputsubform_'+formUuid+' isGroupShowTitle='+dySubFormGroupShow.show+'>');
					$('#_inputsubform_'+formUuid).wsubForm4Group({
						$paranentelement:parentelement,
						formDefinition:formDefinition,
						subformDefinition:subformDefinition,
						readOnly:false,
						mainformdataUuid:parentelement.getDataUuid(),
						formUuid:formUuid,
						groupField:subformConfig.groupShowTitle,
						groupOrder : 'asc',
						groupColumnShow: groupColumnShow,
						autoWidth:subformConfig.autoWidth,
						$parent:$tableelement.parent()
					});
				}else{
					$tableelement.before('<input  id=_inputsubform_'+formUuid+' isGroupShowTitle='+dySubFormGroupShow.notShow+'>');
					$('#_inputsubform_'+formUuid).wsubForm({
						$paranentelement:parentelement,
						formDefinition:formDefinition,
						subformDefinition:subformDefinition,
						readOnly:false,
						mainformdataUuid:parentelement.getDataUuid(),
						formUuid:formUuid,
						autoWidth:subformConfig.autoWidth ,
						$parent:$tableelement.parent()
					});
				}
				//alert($.ControlManager.getSubFormControl("9559fa83-2836-441c-8ea6-cddb35bf8b33").options.formUuid);
			},
			
			/**
			 * 获得从表控件(根据isGroupShowTitle属性区分.)
			 * @param formUuid
			 * @returns
			 */
			 getSubFormControl:function(formUuid){
				 var ctl={};
				 var isGroupShowTitle="";
				 if($('#_inputsubform_'+formUuid).size()>0){
					  isGroupShowTitle=$('#_inputsubform_'+formUuid).attr("isGroupShowTitle");
				 }else{
					 return undefined;
				 }
				 if(isGroupShowTitle==dySubFormGroupShow.show){
					 ctl=$('#_inputsubform_'+formUuid).wsubForm4Group("getObject");
				 }else{
					 ctl=$('#_inputsubform_'+formUuid).wsubForm("getObject");
				 }
				 return ctl;
			},
			
			 
			
			/**
			 * 根据name返回控件对象。
			 * @param name
			 * @returns
			 */
			getCtl:function(ctlName){
				var ctlMapInfo = $.ControlManager[ctlName];// {"$placeHolder":$placeHolder, "ctlType":ctlType};
				if(typeof ctlMapInfo == "undefined" || ctlMapInfo == null){
					//console.error("cann't find the control,  whose name   is " + ctlName);
					return null;
				}
				var obj = ctlMapInfo.$placeHolder;
				var getmethod="getObject";
				return obj[ctlMapInfo.ctlType](getmethod); 
			},
			
			getControlFormulas:function(){
				if(typeof controlFormulas == "undefined"){
					controlFormulas = {};
				}
				return controlFormulas;
			},
			
			//注册公式
			registFormula :function(formula){
				 
				var formulas = $.ControlManager.getControlFormulas();
				if(typeof formulas == "undefined" || formulas == null ){
					formulas = {};
				}
				
				if(typeof formula  == "undefined" || formula == null || $.trim(formula).length == 0){
					return;
				} 
				if(typeof formula == "string"){ 
					try{
						formula = eval("(" + formula + ")");
					}catch(e){
						console.log(formula  + "公式注册失败");
						return;
					} 
				}
				 
				var triggerElements = formula.triggerElements;//触发公式的元素
				var formulaFun = formula.formula;//公式
				
				//非法公式
				if(typeof triggerElements == "undefined" || typeof formulaFun == "undefined"){
					if(typeof formula == "string"){
						console.error("非法的公式:" + formula);
					}else{
						console.error("存在非法的公式");
					}
					
					return;
				}
				
				for(var i = 0; i < triggerElements.length;i ++){
					var triggerElementFieldName = triggerElements[i];
					var fieldFormulas = [];
					for(var fn in formulas){
						if(fn == triggerElementFieldName){
							fieldFormulas = formulas[fn];
						}
					}
					fieldFormulas.push(formulaFun);
					formulas[triggerElementFieldName] = fieldFormulas;
				}
			},
			//运算公式
			culateByFormula:function(triggerControl){
				var fieldName = triggerControl.getCtlName();
				var allformulas = $.ControlManager.getControlFormulas();
				if(triggerControl.getPos() == dyControlPos.subForm){ 
					fieldName = triggerControl.options.columnProperty.columnName
					fieldName = triggerControl.getFormDefinition().outerId + ":" + fieldName;
				}
				
				var formulas = allformulas[fieldName];//获取公式
				if(typeof formulas == "undefined"){
					return;
				}
				 
				//运行公式
				for(var i = 0 ; i < formulas.length; i ++){
					try{
						formulas[i].call(triggerControl);
					}catch(e){
						console.error(e);
					}
					
				}
				//	control.registFormula(allformulas[name]);
			}
			
	};
	
	/**
	控件初始化
	*/
	function initDyControl(ctlName,name,column,formDefinition){
		
		var columnProperty={
				//控件字段属性
				applyTo:column.applyTo,//应用于
				controlName:ctlName,//控件名称，由外面指定 
				columnName:column.name,//字段定义  fieldname
				displayName:column.displayName,//描述名称  descname
				dbDataType:column.dbDataType,//字段类型  datatype type
				indexed:column.indexed,//是否索引
				showed:column.showed,//是否界面表格显示
				sorted:column.sorted,//是否排序
				sysType:column.sysType,//系统定义类型，包括三种（0：系统默认，1：管理员常量定义，2：表单添加后自定义）
				length:column.length,//长度
				isHide:column.isHide,//是否隐藏
				showType:column.showType,//显示类型 1,2,3,4 datashow
				defaultValue:column.defaultValue,//默认值
				valueCreateMethod:column.valueCreateMethod,//默认值创建方式 1用户输入
				onlyreadUrl:column.onlyreadUrl,//只读状态下设置跳转的url
				realDisplay: column.realDisplay,
				formDefinition:formDefinition
		};
		//控件公共属性
		var commonProperty={
				inputMode:column.inputMode,//输入样式 控件类型 inputDataType
				fieldCheckRules:column.fieldCheckRules,
				fontSize:column.fontSize,//字段的大小
				fontColor:column.fontColor,//字段的颜色
				ctlWidth:column.ctlWidth,//宽度
				ctlHight:column.ctlHight,//高度
				textAlign:column.textAlign//对齐方式
		};	
		var inputMode=commonProperty.inputMode;
		//radio
		if(inputMode==dyFormInputMode.radio){
			$("input[name='"+name+"']").wradio({
				columnProperty:columnProperty,
		    	commonProperty:commonProperty,
				optionDataSource:column.optionDataSource, //备选项来源1:常量,2:字段
				optionSet:column.optionSet,
				dictCode:column.dictCode
			});
		//checkbox
		}else if(inputMode==dyFormInputMode.checkbox){
			$("input[name='"+name+"']").wcheckBox({
				columnProperty:columnProperty,
		    	commonProperty:commonProperty,
				ctrlField:column.ctrlField,
				optionDataSource:column.optionDataSource, //备选项来源1:常量,2:字段
				optionSet:column.optionSet,
				dictCode:column.dictCode,
				
				selectMode:column.selectMode,//选择模式，单选1，多选2
				singleCheckContent :column.singleCheckContent,//单选 选中内容
				singleUnCheckContent:column.singleUnCheckContent//单选 取消选中内容
		});
		}else if(inputMode==dyFormInputMode.number){
			//numbertext
			$("input[name='"+name+"']").wnumberInput({
				columnProperty:columnProperty,
		    	commonProperty:commonProperty,
				decimal:column.decimal,
				negative:column.negative,
				operator:column.operator
				});
		//text
		}else if(inputMode==dyFormInputMode.text){
		    $("input[name='"+name+"']").wtextInput({
		    	columnProperty:columnProperty,
		    	commonProperty:commonProperty
				});
		}else if(inputMode==dyFormOrgSelectType.orgSelectAddress ||inputMode==dyFormOrgSelectType.orgSelectStaDep
				||inputMode==dyFormOrgSelectType.orgSelectDepartment||inputMode==dyFormOrgSelectType.orgSelectStaff){
			$("input[name='"+name+"']").wunit({
				columnProperty:columnProperty,
		    	commonProperty:commonProperty,
		    	mutiSelect:column.mutiSelect //是否多选
				});
		}
		//serialNumber	
		else if(inputMode==dyFormInputMode.serialNumber||inputMode==dyFormInputMode.unEditSerialNumber){
			$("input[name='"+name+"']").wserialNumber({
				columnProperty:columnProperty,
		    	commonProperty:commonProperty,
				designatedId:column.designatedId,
				designatedType:column.designatedType,
				isOverride:column.isOverride,
				isSaveDb:column.isSaveDb,
				formUuid:formDefinition.uuid
				});
		}else if(inputMode==dyFormInputMode.date){
			$("input[name='"+name+"']").wdatePicker({
				columnProperty:columnProperty,
		    	commonProperty:commonProperty,
		    	contentFormat:column.contentFormat
				});
		}else if(inputMode==dyFormInputMode.textArea){
		    $("#"+name).wtextArea({
		    	columnProperty:columnProperty,
		    	commonProperty:commonProperty
				});
		//富文本
		}else if(inputMode==dyFormInputMode.ckedit){
		    $("#"+name).wckeditor({
		    	columnProperty:columnProperty,
		    	commonProperty:commonProperty,
				indent: true,
	            breakBeforeOpen: false,
	            breakAfterOpen: false,
	            breakBeforeClose: false,
	            breakAfterClose: false
		 });
		//combobox    
		}else if(inputMode==dyFormInputMode.selectMutilFase){
		    $("select[name='"+name+"']").wcomboBox({
		    	columnProperty:columnProperty,
		    	commonProperty:commonProperty,
				optionDataSource:column.optionDataSource, //备选项来源1:常量,2:字段
				optionSet:column.optionSet,
				dictCode:column.dictCode
				});
		}else if(inputMode==dyFormInputMode.treeSelect){
			 
		    $("input[name='"+name+"']").wcomboTree({
		    	columnProperty:columnProperty,
		    	commonProperty:commonProperty,
		    	serviceName:column.serviceName,
		    	treeWidth: column.treeWidth,
				treeHeight: column.treeHeight,
				mutiSelect:column.mutiSelect,
				realDisplay:column.realDisplay
				});
		//视图展示    
		}else if(inputMode==dyFormInputMode.viewdisplay){
		    $("input[name='"+name+"']").wviewDisplay({
		    	columnProperty:columnProperty,
		    	commonProperty:commonProperty,
		    	relationDataText : column.relationDataText, 		 	
				relationDataValue : column.relationDataValue,		 	
				relationDataSql : column.relationDataSql		
				});
		}else if(inputMode==dyFormInputMode.accessory3){ 
		    $("input[name='"+name+"']").wfileUpload({
		    	columnProperty:columnProperty,
		    	commonProperty:commonProperty,	
		    	allowUpload:column.allowUpload,//允许上传
	            allowDownload:column.allowDownload,//允许下载
	            allowDelete:column.allowDelete,//允许删除
	            mutiselect:column.mutiselect,//是否多选
	            enableSignature:columnProperty.formDefinition.enableSignature == signature.enable
				});
		}else if(inputMode==dyFormInputMode.accessoryImg){
		    $("input[name='"+name+"']").wfileUpload4Image({
		    	columnProperty:columnProperty,
		    	commonProperty:commonProperty,	
		    	allowUpload:column.allowUpload,//允许上传
	            allowDownload:column.allowDownload,//允许下载
	            allowDelete:column.allowDelete,//允许删除
	            mutiselect:column.mutiselect,//是否多选
	            enableSignature:columnProperty.formDefinition.enableSignature
				});
		}else if(inputMode==dyFormInputMode.accessory1){
		    $("input[name='"+name+"']").wfileUpload4Icon({
		    	columnProperty:columnProperty,
		    	commonProperty:commonProperty,	
		    	allowUpload:column.allowUpload,//允许上传
	            allowDownload:column.allowDownload,//允许下载
	            allowDelete:column.allowDelete,//允许删除
	            mutiselect:column.mutiselect,//是否多选
	            enableSignature:columnProperty.formDefinition.enableSignature
				});
		//弹出对话框    
		}else if(inputMode==dyFormInputMode.dialog){
		    $("input[name='"+name+"']").wdialog({
		    	columnProperty:columnProperty,
		    	commonProperty:commonProperty,
		    	relationDataTextTwo: column.relationDataTextTwo,		 	
				relationDataValueTwo:column.relationDataValueTwo, 		 	
				relationDataTwoSql: column.relationDataTwoSql, 	
				relationDataDefiantion: column.relationDataDefiantion, 	
				relationDataShowMethod: column.relationDataShowType, 	
				relationDataShowType: column.relationDataShowType	
				});
		}
	}
	
	/**
	 * 通过输入类型获得拼装的html
	 */
	function getHtmlStrByInputDataType(inputMode,fieldcode,elmenttype){
		var appendhtml={};
		 if(inputMode==dyFormInputMode.textArea||inputMode==dyFormInputMode.ckedit){
			  formfieldcode=elmenttype+"textarea"+fieldcode;
			  appendhtml.html = '<textarea type="text" id="'+formfieldcode+'" name="'+formfieldcode+'"></textarea>';
			  appendhtml.formfieldcode=formfieldcode;
		 }else if(inputMode==dyFormInputMode.selectMutilFase){
			 formfieldcode=elmenttype+"select"+fieldcode;
			 appendhtml.formfieldcode=formfieldcode;
			 appendhtml.html  = '<select name="'+formfieldcode+'" type="text" > </select>';
		 } else {
			 formfieldcode=elmenttype+"input"+fieldcode;
			 appendhtml.formfieldcode=formfieldcode;
			 appendhtml.html  = '<input name="'+formfieldcode+'"  type="text" />';
		 }
		 return appendhtml;
	}
	
	
	
})(jQuery);
