/**
 * 控件管理类，主要负责控件的动态创建以及控件的实例对象获取。
 */
;
(function($) {
	$.ControlManager = {
			
			/**
			 * 控件创建
			 * @param column colunm属性列表 
			 */
			createControl:function(column){
				var fieldcode=column.name;
				var inputMode=column.inputMode;
				var type=column.type;
		        if($("input[name='"+fieldcode+"']").length>0){
					$("input[name='"+fieldcode+"']").hide();
					var  appendhtml=getHtmlStrByInputDataType(inputMode,fieldcode,'_');
					 $("input[name='"+fieldcode+"']").after(appendhtml.html);
					 initDyControl(type,appendhtml.formfieldcode,column);
				}
			},
				
			/**
			 * 根据name返回控件对象。
			 * @param name
			 * @returns
			 */
			getControl:function(name){
				var parenttype="";
				var inputMode="";
			    if($("input[name='"+name+"']").size()>0){
					parenttype="_";
					inputMode=$("input[name='"+name+"']").next().attr("inputMode");
				}
				 if(inputMode==dyFormInputMode.textArea||inputMode==dyFormInputMode.ckedit){
					 realcontrolname="#"+parenttype+"area"+name;
				 }else if(inputMode==dyFormInputMode.selectMutilFase){
					 realcontrolname="select[name='"+parenttype+"comb"+name+"']";
				 }else {
					 realcontrolname="input[name='"+parenttype+"input"+name+"']";
				 }
				if(inputMode==''||inputMode==undefined){
					//alert(name+"控件未定义!");
					return undefined;
				}
				console.log(realcontrolname);
				var obj=$(realcontrolname);
				var getmethod="getObject";
					if(inputMode==dyFormInputMode.text){
						return obj.wtextInput(getmethod);
					}else if(inputMode==dyFormInputMode.checkbox){
						return obj.wcheckBox(getmethod);
					}else if(inputMode==dyFormInputMode.radio){
						return obj.wradio(getmethod);
					}else if(inputMode==dyFormInputMode.textArea){
						return obj.wtextArea(getmethod);
					}else if(inputMode==dyFormInputMode.selectMutilFase){
						return obj.wcomboBox(getmethod);
					}else if(inputMode==dyFormInputMode.ckedit){
						return obj.wckeditor(getmethod);
					}else if(inputMode==dyFormInputMode.treeSelect){
						return obj.wcomboTree(getmethod);
					}else if(inputMode==dyFormInputMode.date){
						return obj.wdatePicker(getmethod);
					}else if(inputMode==dyFormInputMode.number){
						return obj.wnumberInput(getmethod);
					}else if(inputMode==dyFormInputMode.serialNumber||inputMode==dyFormInputMode.unEditSerialNumber){
						return obj.wserialNumber(getmethod);
					}else if(inputMode==dyFormInputMode.viewdisplay){
						return obj.wviewDisplay(getmethod);
					}else if(inputMode==dyFormInputMode.dialog){
						return obj.wdialog(getmethod);
					}else if(inputMode==dyFormInputMode.accessory3){
						return obj.wfileUpload(getmethod);
					}else if(inputMode==dyFormInputMode.accessory1){
						return obj.wfileUpload4Icon(getmethod);
					}else if(inputMode==dyFormOrgSelectType.orgSelectAddress ||inputMode==dyFormOrgSelectType.orgSelectStaDep
							||inputMode==dyFormOrgSelectType.orgSelectDepartment||inputMode==dyFormOrgSelectType.orgSelectStaff){
						return obj.wunit(getmethod);
					}
			}
	};
	
	/**
	控件初始化
	*/
	function initDyControl(type,name,column){
		var columnProperty={
				//控件字段属性
				applyTo:column.applyTo,//应用于
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
				dictCode:column.dictCode
		});
		}else if(inputMode==dyFormInputMode.number){
			//numbertext
			$("input[name='"+name+"']").wnumberInput({
				columnProperty:columnProperty,
		    	commonProperty:commonProperty,
				decimal:column.decimal,
				negative:column.negative
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
		    	commonProperty:commonProperty
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
				mutiSelect:column.mutiSelect
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
			if(column.fileUploadConfig!=''||column.fileUploadConfig!=undefined){
				  fileUploadConfig=eval('('+column.fileUploadConfig+')');
			}
		    $("input[name='"+name+"']").wfileUpload({
		    	columnProperty:columnProperty,
		    	commonProperty:commonProperty,	
		    	allowUpload:column.allowUpload,//允许上传
	            allowDownload:column.allowDownload,//允许下载
	            allowDelete:column.allowDelete,//允许删除
	            mutiselect:column.mutiselect//是否多选
				});
		}else if(inputMode==dyFormInputMode.accessory1){
			if(column.fileUploadConfig!=''||column.fileUploadConfig!=undefined){
				  fileUploadConfig=eval('('+column.fileUploadConfig+')');
			}
		    $("input[name='"+name+"']").wfileUpload4Icon({
		    	columnProperty:columnProperty,
		    	commonProperty:commonProperty,	
		    	allowUpload:column.allowUpload,//允许上传
	            allowDownload:column.allowDownload,//允许下载
	            allowDelete:column.allowDelete,//允许删除
	            mutiselect:column.mutiselect//是否多选
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
			  formfieldcode=elmenttype+"area"+fieldcode;
			  appendhtml.html = '<textarea type="text" id="'+formfieldcode+'" name="'+formfieldcode+'  "></textarea>';
			  appendhtml.formfieldcode=formfieldcode;
		 }else if(inputMode==dyFormInputMode.selectMutilFase){
			 formfieldcode=elmenttype+"comb"+fieldcode;
			 appendhtml.formfieldcode=formfieldcode;
			 appendhtml.html  = '<select name="'+formfieldcode+'" type="text" > </select>';
		 }else {
			 formfieldcode=elmenttype+"input"+fieldcode;
			 appendhtml.formfieldcode=formfieldcode;
			 appendhtml.html  = '<input name="'+formfieldcode+'"  type="text" ></input>';
		 }
		 return appendhtml;
	}
	
})(jQuery);
