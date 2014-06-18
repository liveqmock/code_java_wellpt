;
(function($) {
	$.ControlUtil = {
			/**
			 * 设置控件的各种属性
			 * @param elment
			 * @param options
			 */
			setCtrAttr:function(elment,options){
				var inputMode=options.commonProperty.inputMode;
				//控件特殊属性设置
				 if(inputMode==dyFormInputMode.text
						 ||inputMode==dyFormInputMode.number
						 ||inputMode==dyFormInputMode.selectMutilFase){
					 if(options.inputMode==dyFormInputMode.number){
						 elment.attr("decimal",options.decimal);	//小数位
						 elment.attr("negative",options.negative);	//是否允许负数
					 }
					 elment.attr("id",elment.attr("name"));
					 this.setCommonProperty(elment,options);
				 }else if(inputMode==dyFormInputMode.textArea||inputMode==dyFormInputMode.ckedit){
					 this.setCommonProperty(elment,options);
				 }else if(inputMode==dyFormInputMode.textArea||inputMode==dyFormInputMode.date){
					 elment.attr("contentFormat",options.contentFormat);
					 this.setCommonProperty(elment,options);
				 }else if(inputMode==dyFormInputMode.selectMutilFase){
					 this.setCommonProperty(elment,options);
					 elment.attr("optiondatasource",options.optiondatasource);	//radio,checkbox,select供选项来源 1.常量 2.字典
				 }else if(inputMode==dyFormInputMode.radio){
					 elment.attr("ctrlfield",options.ctrlfield);	//radio,checkbox,select供选项来源 1.常量 2.字典
					 elment.attr("optiondatasource",options.optiondatasource);	//radio,checkbox,select供选项来源 1.常量 2.字典
					 
				 }else if(inputMode==dyFormInputMode.checkbox){
					 elment.attr("ctrlfield",options.ctrlfield);	//checkbox、radio 控制隐藏字段
					 elment.attr("optiondatasource",options.optiondatasource);	//radio,checkbox,select供选项来源 1.常量 2.字典
					 
				 }else if(inputMode==dyFormInputMode.serialNumber||inputMode==dyFormInputMode.unEditSerialNumber){
					 elment.attr("id",elment.attr("name"));
					 elment.attr("designatedId",options.designatedId);	//流水号定义:指定的流水号ID
					 elment.attr("designatedType",options.designatedType);//可编辑流水号定义:指定的流水号TYPE
					 elment.attr("isOverride",options.isOverride);	//可编辑流水号定义:是否覆盖指针
					 elment.attr("isSaveDb",options.isSaveDb);	//流水号定义：是否保存进数据库
					 this.setCommonProperty(elment,options);
					 if(inputMode==dyFormInputMode.serialNumber){
						 elment.addClass("input-search");//css in wellnewoa.css
					 }
				 }else if(inputMode==dyFormInputMode.textBody){
					 
				 }else if(inputMode==dyFormInputMode.fileUpload){
					 elment.attr("uploadfiletype",options.uploadfiletype);	//附件上传样式
					 elment.attr("isgetzhengwen",options.isgetzhengwen);//附件上传是否正文
				 }else if(inputMode==dyFormInputMode.treeSelect){
					 elment.attr("id",elment.attr("name"));
					// elment.attr("data",options.data);	//树形下拉框调用的服务层的方法的请求参数
					 elment.attr("servicename",options.servicename);	//树形下拉框调用的服务层
					// elment.attr("methodname",options.methodname);	 //树形下拉框调用的服务层的方法
					 elment.addClass("input-tier");//css in wellnewoa.css
					 this.setCommonProperty(elment,options);
					 
				 }else if(inputMode==dyFormOrgSelectType.orgSelectAddress ||inputMode==dyFormOrgSelectType.orgSelectStaDep
							||inputMode==dyFormOrgSelectType.orgSelectDepartment||inputMode==dyFormOrgSelectType.orgSelectStaff){
					 elment.attr("id",elment.attr("name"));
					 if(inputMode==dyFormOrgSelectType.orgSelectStaff){
						 elment.addClass("input-people");//css in wellnewoa.css
					 }else{
						 elment.addClass("input-search");//css in wellnewoa.css
					 }
				 }else if(inputMode==dyFormInputMode.dialog){
					 elment.attr("id",elment.attr("name"));
					 elment.attr("relationdatatwosql",options.relationdatatwosql);	//关联数据类型
					 elment.attr("relationdatatext",options.relationdatatext);	//关联数据源显示值
					 elment.attr("relationdatavalue",options.relationdatavalue);	//关联数据源隐藏值
					 elment.attr("relationdatasql",options.relationdatasql);	//关联数据约束条件
					 elment.attr("relationdatashowmethod",options.relationdatashowmethod);	//关联数据的展示方式
					 elment.attr("relationdatashowtype",options.relationdatashowtype);	//关联数据显示方式
					 elment.attr("relationDataDefiantion",options.relationDataDefiantion);	//关联数据显示方式
					 elment.addClass("input-search");//css in wellnewoa.css
					 
				 }else if(inputMode==dyFormInputMode.viewdisplay){
					 elment.attr("relationdatatext",options.relationdatatext);	//关联数据源显示值
					 elment.attr("relationdatavalue",options.relationdatavalue);	//关联数据源隐藏值
					 elment.attr("relationdatasql",options.relationdatasql);	//关联数据约束条件
				 }else if(inputMode==dyFormInputMode.accessory3||inputMode==dyFormInputMode.accessory1){
					 elment.attr("id",elment.attr("name"));
				 }
				 
				 //公共属性设置
				 elment.attr("applyTo",options.columnProperty.applyTo);	//应用于
				 elment.attr("columnName",options.columnProperty.columnName);	//字段定义
				 elment.attr("displayName",options.columnProperty.displayName);	//描述名称
				 elment.attr("dbDataType",options.columnProperty.dbDataType);	//字段类型?
				 elment.attr("indexed",options.columnProperty.indexed);	//是否索引
				 elment.attr("showed",options.columnProperty.showed);//是否界面表格显示
				 elment.attr("sorted",options.columnProperty.sorted);	//是否排序
				 elment.attr("sysType",options.columnProperty.sysType);	//系统定义类型，包括三种（0：系统默认，1：管理员常量定义，2：表单添加后自定义）
				 elment.attr("length",options.columnProperty.length);	//字段长度
				 elment.attr("isHide",options.columnProperty.isHide);	//是否隐藏
				 elment.attr("showType",options.columnProperty.showType);	 //是否隐藏
				 elment.attr("defaultValue",options.columnProperty.defaultValue);	//默认值
				 elment.attr("valueCreateMethod",options.columnProperty.valueCreateMethod);	//默认值创建方式 1用户输入
				 elment.attr("onlyreadUrl",options.columnProperty.onlyreadUrl);	//只读状态下设置跳转的url
				 
				 elment.attr("inputMode",options.commonProperty.inputMode);	//输入方式
				 elment.attr("fieldCheckRules",options.commonProperty.fieldCheckRules);	//校验规则
				 
				 //是否隐藏
				 if(options.columnProperty.ishide=='true'){
					 elment.hide(); 
				 }else{
					 elment.show();
				 }
				 
			},
			
			/**
			 * 根据showtype展现
			 */
			dispalyByShowType:function(elment,options){
				var showType=options.columnProperty.showType;
				 if(showType==dyshowType.showAsLabel){
					 this.setIsDisplayAsLabel(elment,options,true);
				 }else if(showType==dyshowType.readonly){
					 this.setReadOnly(elment,true);
				 }else if(showType==dyshowType.disabled){
					 this.setEnable(elment,false);
				 }else if(showType==dyshowType.hide){
					 this.setVisible(elment,false);
				 }
			},
			
			/**
			 * 设置是否可用（html属性为disabled）
			 */
			setEnable:function(elment,isenable){
				 if(isenable){
					 elment.removeAttr("disabled");
	                }
	                else
	                {
	                  elment.attr("disabled","disabled");   
	                }
			},
			
			/**
			 * 设置是否可用（html属性为readonly）
			 */
			setReadOnly:function(elment,isreadonly){
				 if(isreadonly){
					  elment.attr("readonly","readonly");    
	                }
	                else
	                {
	                  elment.removeAttr("readonly");
	                }
			},
			
			/**
			 * 设置是否可用（html属性为readonly）
			 */
			setVisible:function(elment,isvisible){
				if(isvisible){
					elment.show();
					//label也需要隐藏
					 if(elment.parent().is("tr[class='field']")){
						 elment.parent().show();
					 }else if(elment.parent().parent().is("tr[class='field']")){
						 elment.parent().parent().show();
					 }else if(elment.parent().parent().parent().is("tr[class='field']")){
						 elment.parent().parent().parent().show();
					 }
				 }else{
					 elment.hide(); 
					//label也需要隐藏
					 if(elment.parent().is("tr[class='field']")){
						 elment.parent().hide();
					 }else if(elment.parent().parent().is("tr[class='field']")){
						 elment.parent().parent().hide();
					 }else if(elment.parent().parent().parent().is("tr[class='field']")){
						 elment.parent().parent().parent().hide();
					 }
				 }
			},
			
			
			 /**
			  * setIsDisplayAsLabel的逆向操作
			  * @param elment
			  * @param options
			  * @param islabelshow
			  */
			 setDisplayAsCtl:function(elment,options){
				 elment.show();
				 if( elment.next().is('span')){
					 elment.next().remove();
				 }
				 options.isShowAsLabel=true;
			 },
			
			
			/**
			 * 设置控件作为标签显示
			 */
			setIsDisplayAsLabel:function(elment,options,islabelshow){
				if(islabelshow){
				 //只显示为label.
					 var val=elment.val();
					 elment.hide();
					if(!val) {
						elment.after("<span>"+val+"</span>");
					}else {
						if(val == "" || val == null) {
							elment.after("<span></span>");
						}else {
							this.setSpanStyle(elment,options.commonProperty.textAlign,options.commonProperty.fontSize,options.commonProperty.fontColor,options.commonProperty.ctlWidth,options.commonProperty.ctlHight,val);
						}
					}
					options.isShowAsLabel=true;
				 }else{
					 options.isShowAsLabel=false;
				 }
				
				
				var onlyreadUrl=options.columnProperty.onlyreadUrl;
				if(!(onlyreadUrl==""||onlyreadUrl==undefined)){
					//var expandFieldValue = $("#expand_field").val();
					var expandFieldValue="";//这个变量作用？先注释掉了
					elment.next("span").css("cursor","pointer");
					elment.next("span").css("color","#0000FF");
					elment.next("span").bind("click",{a:onlyreadUrl,b:expandFieldValue},function(event) {
						var url = event.data.a;
						/*var tempValue = event.data.b;
						var tempValueArray = tempValue.split(",");
						for(var k=0;k<tempValueArray.length;k++) {
							var tempValues = tempValueArray[k].split(":");
							if(url.indexOf(tempValues[0]) > -1 ) {
								url = url.replace("${", "").replace("}", "").replace(tempValues[0],tempValues[1]);
							}
						}*/
						window.open(ctx+url);
					});
				}
				

			},
			
			
			/**
			 * 设置必输
			 * 返回设置后的规则.
			 */
			setRequired:function(isrequire,options){
				 var checkruleisempty;
				 var fieldCheckRules=options.commonProperty.fieldCheckRules;
					 if(fieldCheckRules==""||fieldCheckRules==undefined){
						 checkruleisempty=true;
					 }else{
						 checkruleisempty=false;
					 };
					 if(checkruleisempty){
						 if(isrequire){
							 fieldCheckRules="[{value:'1',label:'字段必输'}]";
						 }
					 }else{
						 var jsonarray = eval('('+fieldCheckRules+')');
						 //增加元素
						 if(isrequire){
							 var isaddrequire=true;
							 for(var k=0;k<jsonarray.length;k++){
								 if(dyCheckRule.notNull == jsonarray[k].value){
									 isaddrequire=false;
									 break;
								 }
							 }
							 if(isaddrequire){
								 jsonarray.push({value:'1', label:"字段必输"});
							 }
						 }
						 var checkrules=[];
						 for(var k=0;k<jsonarray.length;k++){
							 if(dyCheckRule.notNull == jsonarray[k].value&&!isrequire){
								 continue;
							 }
							 checkrules.push({value:jsonarray[k].value, label:jsonarray[k].label});
						 }
						 fieldCheckRules=JSON.stringify(checkrules); 
					 }
					 options.commonProperty.fieldCheckRules=fieldCheckRules;
			 },
			
			
			 /**
			  * eg.
			  * email: {
	    		required: "请输入Email地址",
		        email: "请输入正确的email地址"
	         },*/
			 getCheckMsg:function(options){
				 var fieldCheckRules= options.commonProperty.fieldCheckRules;
				 if(fieldCheckRules==""||fieldCheckRules==undefined){
					 return null;
				 }
				 var rules = eval(fieldCheckRules);
				 var msgmap={};
					for(var k=0;k<rules.length;k++){
						if(dyCheckRule.notNull == rules[k].value){//非空
							msgmap["required"]=rules[k].label;
						}else if(dyCheckRule.url == rules[k].value){//url
							msgmap["url"]=rules[k].label;
						}else if(dyCheckRule.email == rules[k].value){//email
							msgmap["email"]=rules[k].label;
						}else if(dyCheckRule.idCard == rules[k].value){//身份证
							msgmap["idCard"]=rules[k].label;
						}else if(dyCheckRule.unique == rules[k].value){//唯一校验
							msgmap["unique"]=rules[k].label;
						}else if(dyCheckRule.tel == rules[k].value){//唯一校验
							msgmap["isPhone"]=rules[k].label;
						}else if(dyCheckRule.mobilePhone == rules[k].value){//唯一校验
							msgmap["isMobile"]=rules[k].label;
						}
					}
				 return JSON.stringify(msgmap);
			 },
			 
			 /**
		      * eg.
		      *    email: {
				    required: true,
				    email: true
				   },
			 }
		      */
		 getCheckRules:function(options){
			 var fieldCheckRules= options.commonProperty.fieldCheckRules;
			 if(fieldCheckRules==""||fieldCheckRules==undefined){
				 return null;
			 }
			 var rulemap={};
			 var rules = eval(fieldCheckRules);
				for(var k=0;k<rules.length;k++){
					if(dyCheckRule.notNull == rules[k].value){//非空
						rulemap["required"]=true;
					}else if(dyCheckRule.url == rules[k].value){//url
						rulemap["url"]=true;
					}else if(dyCheckRule.email == rules[k].value){//email
						rulemap["email"]=true;
					}else if(dyCheckRule.idCard == rules[k].value){//身份证
						rulemap["idCard"]=true;
					}else if(dyCheckRule.unique == rules[k].value){//唯一校验
						rulemap["unique"]=true;
					}else if(dyCheckRule.tel == rules[k].value){//固定电话
						rulemap["isPhone"]=true;
					}else if(dyCheckRule.mobilePhone == rules[k].value){//手机
						rulemap["isMobile"]=true;
					}
				}
			 return JSON.stringify(rulemap);
		 } ,
		 
		 isRequired:function(options){
			 var fieldCheckRules= options.commonProperty.fieldCheckRules;
			 if(fieldCheckRules==""||fieldCheckRules==undefined){
				 return false;
			 }
			 //往校验规则添加required
			 var checkrules = eval('('+fieldCheckRules+')');
			 var isexistrequired=false;
			 for(var k=0;k<checkrules.length;k++){
				 if(dyCheckRule.notNull == checkrules[k].value){
					 isexistrequired=true;
					 break;
				 };
			 };
			 return isexistrequired;
		 },
		 
		 
			/**
			 * 设置只读状态下的span的样式
			 * @param elment
			 * @param textAlign
			 * @param fontSize
			 * @param fontColor
			 * @param ctlWidth
			 * @param ctlHight
			 * @param val
			 */
		 setSpanStyle:function (elment,textAlign,fontSize,fontColor,ctlWidth,ctlHight,val){
			 var spanstyle=this.getStyleHtmlByParams(textAlign,fontSize,fontColor,ctlWidth,ctlHight);
			 elment.after("<span "+spanstyle+">"+val+"</span>");
			},
			
		
		/**
		 * 控件设值(如果是显示为lable，则label也得更新)
		 * @param elment
		 * @param options
		 */
		 setValue:function(elment,options,value){
			  elment.val(value);
	    	  if(options.isShowAsLabel==true){
	    		  elment.next().html(value);
	    	  }
		 },
			
			/**
			 * 通过参数获得初始化的样式。
			 * @param textAlign
			 * @param fontSize
			 * @param fontColor
			 * @param ctlWidth
			 * @param ctlHight
			 * @returns {String}
			 */
		  getStyleHtmlByParams:function(textAlign,fontSize,fontColor,ctlWidth,ctlHight){
				var cssTemp = "style = '";
				if(textAlign == "center") {
					cssTemp += "text-align:center;";
				}else if(textAlign == "left") {
					cssTemp += "text-align:left;";
				}else if(textAlign == "right") {
					cssTemp += "text-align:right;";
				}
				if(fontSize != null && fontSize != "") {
					cssTemp += "font-size:"+ fontSize+"px;";
				}
				if(fontColor != null && fontColor != "") {
					cssTemp += "color:"+ fontColor+";";
				}
				if(ctlWidth != null && ctlWidth != "") {
					cssTemp += "width:"+ ctlWidth+"px;";
				}
				if(ctlHight != null && ctlHight != "") {
					cssTemp += "height:"+ ctlHight+"px;";
				}
				cssTemp += "'";
				return cssTemp;
			},
         
     	/**
     	 * 设置控件公共属性
     	 * @param elment
     	 * @param options
     	 */
     	 setCommonProperty:function(elment,options){
    		elment.val(options.columnProperty.defaultValue);
    		elment.attr("value",options.columnProperty.defaultValue);
			 elment.attr("textAlign",options.commonProperty.textAlign);	//对齐方式
			 elment.attr("fontSize",options.commonProperty.fontSize);	//字段的大小
			 elment.attr("fontColor",options.commonProperty.fontColor);	//字段的颜色
			 elment.attr("ctlWidth",options.commonProperty.ctlWidth);	//宽度
			 elment.attr("ctlHight",options.commonProperty.ctlHight); //高度
    		 //宽、高
    		 elment.css({
        			"width": options.commonProperty.ctlWidth+"px",
        			"height": options.commonProperty.ctlHight+"px",
        			"text-align":options.commonProperty.textAlign,
        			"font-size":options.commonProperty.fontSize+"px",
        			"color":options.commonProperty.fontColor,
        		   });
    		//只读属性
    		 if(options.readOnly){
    			 elment.attr("readonly","readonly");    
                }
                else
                {
                	elment.removeAttr("readonly");
                }
    	}
     	
		 
	};
	

	

	
})(jQuery);