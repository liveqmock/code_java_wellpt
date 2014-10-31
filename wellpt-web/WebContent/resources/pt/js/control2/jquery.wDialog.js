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
	 * DIALOG CLASS DEFINITION ======================
	 */
	var Dialog = function($placeHolder, options) { 
		this.options = $.extend({}, $.fn["wdialog"].defaults, options);
		this.value = "";
		this.$editableElem = null;
		this.$labelElem = null;
		this.$placeHolder = $placeHolder; 
	};

	Dialog.prototype = {
		constructor : Dialog
	};
	
	$.Dialog = {
			
			createEditableElem:function(){
				 if(this.$editableElem != null){//创建可编辑框
					 return ;
				 }
				 var options = this.options;
				 var ctlName =  this.getCtlName();
				 var editableElem = document.createElement("input");
				 editableElem.setAttribute("class", this.editableClass);
				 editableElem.setAttribute("name", ctlName);
				 editableElem.setAttribute("type", "text");
				 
				 $( editableElem).css(this.getTextInputCss());
				 
				 this.$placeHolder.after($(editableElem));
				 this.$editableElem = this.$placeHolder.next("." + this.editableClass);
				 this.$editableElem.addClass("input-search");//css in wellnewoa.css
				 var _this = this;
				 
				 
				 var _relationDataDefiantion=this.options.relationDataDefiantion;//格式为:{"sqlTitle":"\u5355\u4f4d\u540d\u79f0","sqlField":"title","formTitle":"\u540d\u79f0","formField":"mc","search":"no"}|{"sqlTitle":"\u7ec4\u7ec7\u673a\u6784\u4ee3\u7801","sqlField":"reservedText1","formTitle":"\u4ee3\u7801","formField":"dm","search":"no"}
				 if($.trim(_relationDataDefiantion).length == 0){
					 alert("请添加弹出框控件的字段映射关系:" + ctlName);
					 return;
				 }
				 //alert(_relationDataDefiantion + "----" + ctlName);
				 var relationFieldMappingstrArray = _relationDataDefiantion.split("|");
				 var relationFieldMappingArray = [];
				 for(var i = 0; i < relationFieldMappingstrArray.length; i++){
					 relationFieldMappingArray.push(JSON.parse(relationFieldMappingstrArray[i]));
				 }
				 
				 var _relationDataValueTwo = this.options.relationDataValueTwo;//数据来源id
				 
				 var _relationDataTwoSql = this.options.relationDataTwoSql;
				 
				 
				 var _this=this;
				 
				 if(typeof options.columnProperty.relativeMethod == "undefined" || options.columnProperty.relativeMethod == dyRelativeMethod.DIALOG){
					 this.create$DialogElement(relationFieldMappingArray, _relationDataValueTwo, _relationDataTwoSql);//创建搜索框 
				 }else{ 
					 this.create$SearchElement(relationFieldMappingArray, _relationDataValueTwo, _relationDataTwoSql, this.options.pageSize);//创建搜索框
				 }
				 this.$editableElem.bind("paste", function(){//粘贴事件 
					 var _dom = this;
						window.setTimeout(function(){
							_this.setValue( $(_dom).val(), false);
						}, 100); 
				 });
				 this.$editableElem.bind("keyup", function(){//输入事件
				    	_this.setValue( $(this).val(), false);
				 });
				 this.$editableElem.bind("change", function(){//输入事件
				    	_this.setValue( $(this).val(), false);
				 });
			 },
		  
			
			 
			bind:function(event, callback){
				this.options[event] = callback;
			},
			
			/**
			 * 创建搜索框
			 * @param relationFieldMapping 字段关系映射, 成员为字符串
			 *  @param dataSourceId  数据源id, 
			 *   @param contrainSql 数据源sql约束条件, 
			 */
			create$SearchElement:function(relationFieldMapping, dataSourceId, contrainSql, pageSize){ 
				var _this = this;
				 this.$editableElem.addClass("input-search");//css in wellnewoa.css 
				 	//取得输入框JQuery对象 
					var $searchInput = this.$editableElem;
					//关闭浏览器提供给输入框的自动完成 
					$searchInput.attr('autocomplete','off'); 
					//创建自动完成的下拉列表，用于显示服务器返回的数据,插入在搜索按钮的后面，等显示的时候再调整位置 
					var $autocomplete = $('<div class="autocomplete"></div>').hide().insertAfter($searchInput);
					
					//清空下拉列表的内容并且隐藏下拉列表区 
					var clear = function(){ 
						$autocomplete.empty().hide();
					};
					
					var isInAutoCompleteDiv = false;
					$autocomplete.mouseover( function(evt){ 
						isInAutoCompleteDiv = true;
					});
					$autocomplete.mouseout( function(evt){
						//debugger; 
						var _this = evt.currentTarget;
						var rect = _this.getBoundingClientRect(); 
						 
						isInAutoCompleteDiv = false;  
						 
						 var inDiv = (evt.clientX >= rect.left && evt.clientX <= rect.right); 
						 inDiv = inDiv &&  (evt.clientY >=  rect.top && evt.clientY <= rect.bottom); 
						 if(!inDiv){
							 setTimeout(clear,350);
						 }
						 
					});
					
					//注册事件，当输入框失去焦点的时候清空下拉列表并隐藏 
					$searchInput.blur(function(evt){ 
						if(isInAutoCompleteDiv){
						}else{
							setTimeout(clear,350);
						}
						isInAutoCompleteDiv = false;
						_this.clearAllSession();//先清空所有的请求会话
					});
					
					
					//设置下拉项的高亮背景 
					var setCheckedItem = function(_this){ 
						//$(_this).parent().removeClass('highlight'); 
						clearCheckedItem(_this);
						$(_this).addClass('highlight');
					};
					
					//设置下拉项的高亮背景 
					var getCheckedItem = function(){ 
						return $autocomplete.find (".highlight");
					};
					
					var moveCheckedItem2Next = function(){
						if($autocomplete.find (".highlight")[0] == $autocomplete.find ("tr:last")[0]){
							return;
						}
						 
						var $dom = getCheckedItem();
						if($dom.size() == 0){
							setCheckedItem($autocomplete.find("tr").first());
							return;
						}
						var dom = $dom[0];
						clearCheckedItem($(dom));
						
						setCheckedItem($(dom).next());
						
					}
					var moveCheckedItem2Prev = function(){
						if($autocomplete.find (".highlight")[0] == $autocomplete.find ("tr:first")[0]){
							return;
						}
						var dom = $autocomplete.find (".highlight")[0];
						clearCheckedItem($(dom));
						
						setCheckedItem($(dom).prev()); 
					}
					
					
					//清空下拉项的高亮背景 
					var clearCheckedItem = function(_this){
						$(_this).siblings().removeClass('highlight'); 
						//$(_this).addClass('highlight');
					};
					
					var selectItem = function(mappingFields ){
						var $checkedTr = $autocomplete.find (".highlight");
						var $checkedTd = $checkedTr.find("td");
						var fieldsKeyValue = {};
						$checkedTd.each(function(){
							fieldsKeyValue[$(this).attr("fieldname").toLowerCase()] = $(this).find("div").first().html();
						});
						 
						_this.setValue2Controls(mappingFields, fieldsKeyValue);
					}
					
					var sqlFields = [];
					for(var i  = 0; i <  relationFieldMapping.length; i++){
						sqlFields.push(relationFieldMapping[i].sqlField);
					}
					
					//约束条件relationDataTwoSql
					var contraint = {};
					var relationDataTwoSql =  this.options.relationDataTwoSql;
					
					 
					
					var ajax_request = function(){
						contraint = _this.getContraint();
						_this.clearAllSession();//先清空所有的请求会话
						var currentSessionId = _this.createSession();//创建新的会话 
						$searchInput.addClass("input-waiting");  
						 
						//ajax服务端通信 
						$.ajax({
							'url':contextPath + '/dyform/autoComplete', //服务器的地址 
							//{"sqlTitle":"\u5355\u4f4d\u540d\u79f0","sqlField":"title","formTitle":"\u540d\u79f0","formField":"mc","search":"no"}|{"sqlTitle":"\u7ec4\u7ec7\u673a\u6784\u4ee3\u7801","sqlField":"reservedText1","formTitle":"\u4ee3\u7801","formField":"dm","search":"no"}
							
							'data':{
								'searchText':$searchInput.val(), 
								'fields':JSON.cStringify(sqlFields), 
								'dataSourceId': dataSourceId, 
								contraint:JSON.cStringify(contraint),
								pageSize:typeof pageSize == "undefined" ? "300": pageSize
							}, //参数 
							'dataType':'json', //返回数据类型 
							'type':'POST', //请求类型 
							'success':function(data){ 
								$searchInput.removeClass("input-waiting");
								if(_this.isSessionTimeout(currentSessionId)){//会话已被取消
									return;
								}
								clear();
								_this.invoke("beforeAutoCompleteShow", data);
								if(data.length) {
									$autocomplete.append("<table></table>");
									//遍历data，添加到自动完成区 
									$.each(data, function(index,term) {
										if(typeof term == "string"){
											//创建li标签,添加到下拉列表中
											$('<tr></tr>').html("<td>" + term + "</td>").appendTo($autocomplete.find("table")).addClass('clickable').hover(function(){
											    
												setCheckedItem(this);//设置高亮
											},function(){
												//setCheckedItem(this);
												//clearCheckedItem(this);//清空高亮
											}) .click(function(){
												clear();//清空下拉项
											});
										}else{
											var content = ""; 
											for(var i in term){ 
													var isHide = _this.isFieldHide(relationFieldMapping, i); 
													var width = _this.getFieldWidth(relationFieldMapping, i);
													content += "<td fieldName='" + i + "' style='" +   (isHide ? "display:none;": "")  +  "' title='"+ term[i] +"' ><div style='width:" + width + "px;" +"'>" + term[i] + "</div></td>";
											} 
												//创建li标签,添加到下拉列表中
												$('<tr></tr>').html(content).appendTo($autocomplete.find("table")).addClass('clickable').hover(function(){ 
													setCheckedItem(this);//设置高亮
												},function(){
													clearCheckedItem(this);//清空高亮
												}) .click(function(){
													selectItem(relationFieldMapping);
													clear();//清空下拉项
												});
										}
									});//事件注册完毕 
									//设置下拉列表的位置，然后显示下拉列表 
									var ypos = $searchInput.position().top; 
									var xpos = $searchInput.position().left;
									 
									//$autocomplete.css("width", $autocomplete.find("table").css("width"))
									
									var width = $autocomplete.width();
									//console.log(width);
									$autocomplete.css('width',$searchInput.width());
									 
									
									$autocomplete.css({'position':'absolute',
										'left':(xpos - 4) + "px",
										'top': (ypos + 35) + "px",
										'z-index':9999, 
										"overflow-x": "auto",
										"overflow-y": "auto", 
										"height": 300 + "px",
										"border": "1px solid #c5dbec",
									"background-color": "#fff",
									"text-align": "left"
										
									
									});
									 
									//显示下拉列表 
									$autocomplete.show(); 
								}
							 
								if(_this.options.afterAutoCompleteShow){
								  
					        		_this.options.afterAutoCompleteShow.call(_this);
					        		 
								}
							} 
						}); 
					}; 
					
					//timeout的ID 
					var timeoutid = null; 
					//点击事件
					$searchInput.click(function(){  
						 
						if(_this.isReadOnly()){//只读时不可搜索
							return;
						}
						clear();//清空下拉项 
						clearTimeout(timeoutid); 
						timeoutid = setTimeout(ajax_request,100); 
					});
					
					//改变事件
					$searchInput.change(function(){  
						var fieldsKeyValue = {};
						for(var i=0;i<relationFieldMapping.length;i++){
							fieldsKeyValue[relationFieldMapping[i]["formField"].toLowerCase()] = "";
						}
						_this.setValue2Controls(relationFieldMapping, fieldsKeyValue);
					});
					
					
					//对输入框进行事件注册 
					$searchInput.keyup(function(event) { 
						if(_this.isReadOnly()){//只读时不可搜索
							return;
						}
						//字母数字，退格，空格 
						if(event.keyCode > 40 || event.keyCode == 8 || event.keyCode ==32) { 
							//首先删除下拉列表中的信息 
							clear();//清空下拉项 
							clearTimeout(timeoutid);  
							timeoutid = setTimeout(ajax_request,100); 
						} 
						else if(event.keyCode == 38){
							//上 
							//CheckedItem = -1 代表鼠标离开 
							moveCheckedItem2Prev();
							event.preventDefault(); 
						} 
						else if(event.keyCode == 40) { 
							//下 
							moveCheckedItem2Next();
							event.preventDefault(); 
						} 
					}).keypress(function(event){ 
						//enter键 
						if(event.keyCode == 13) {
							//selectItem(relationFieldMapping);
							//clear();
							//event.preventDefault(); 
							getCheckedItem().trigger("click");
						} 
					}).keydown(function(event){ 
						//esc键 
						if(event.keyCode == 27 ) { 
							clear();
							event.preventDefault(); 
						} 
					}); 
					//注册窗口大小改变的事件，重新调整下拉列表的位置 
					$(window).resize(function() { 
						var ypos = $searchInput.position().top; 
						var xpos = $searchInput.position().left; 
						$autocomplete.css('width',$searchInput.css('width')); 
						$autocomplete.css({'position':'absolute','left':xpos + "px",'top':ypos +"px"}); 
					}); 
				  
			},
			
			isFieldHide:function(mappingFields, fieldName){
				for(var i  = 0; i <  mappingFields.length; i++){
					var tempObj = mappingFields[i];
					 var sqlField = tempObj.sqlField ;
					 var formField = tempObj.formField ;
					 var isHide = tempObj.isHide;
					 if(fieldName.toLowerCase() == sqlField.toLowerCase()){
						  if(typeof isHide == "undefined"){
							  return false;
						  }else{
							  return isHide;
						  }
						  
					 }
				}
				return false;
					 
			},
			getFieldWidth:function(mappingFields, fieldName){
				for(var i  = 0; i <  mappingFields.length; i++){
					var tempObj = mappingFields[i];
					 var sqlField = tempObj.sqlField ;
					 var formField = tempObj.formField ;
					 var width = tempObj.width;
					 if(fieldName.toLowerCase() == sqlField.toLowerCase()){
						  if(typeof width == "undefined" || $.trim(width).length == 0){
							  break;
						  }else{
							  return width;
						  }
						  
					 }
				}
				 return "";
					 
			},
			
			isSessionTimeout:function(sessionId){ 
				if(typeof jqueryWDialogSession[sessionId] == "undefined"){
					return true;
				}else{
					return false;
				}
			},
			createSession:function(){
				var sessionId = new UUID().id.toLowerCase(); 
				jqueryWDialogSession[sessionId] = sessionId;
				return sessionId;
			},
			clearAllSession:function(){
				jqueryWDialogSession = {}; 
			},
			getContraint:function(){
				var _this = this;
				var relationDataTwoSql =  this.options.relationDataTwoSql;
				var contraint = {};
				if(typeof relationDataTwoSql != "undefined" && $.trim(relationDataTwoSql).length > 0){
					var fieldvalues = relationDataTwoSql.split(";");
					for(var i = 0; i < fieldvalues.length; i++){
						var fieldValue = fieldvalues[i].split("=");
						if(fieldValue.length != 2){
							continue;
						}
						var field = fieldValue[0];
						var valueField = fieldValue[1];
						
						if(_this.getPos()==dyControlPos.subForm){//在从表中
							/*var datauuid=this.getDataUuid();
							if(issubformdialog){
								control = $.ControlManager.getCtl(datauuid+'____'+formField); 
								paramsObj[datauuid+'____'+formField] =  value ;
							}else{
								control = $.ControlManager.getCtl(datauuid+'___'+formField); 
								paramsObj[datauuid+'___'+formField] =  value ;
							}
							if(typeof control == "undefined" || control == null){
								continue;
							}
							control.setValue(value);*/
						}else{
							var control = $.ControlManager.getCtl(valueField);
							if(typeof control == "undefined" || control == null){//找不到控件
								continue;
							}
							
							
							var value = control.getValue();
							if(typeof value == "undefined" || value == null || $.trim(value).length == 0){//没有设值
								continue;
							}
							
							//sqlFields.push(field);
							
							contraint[field] = value; 
						}
						
						
						
					}
				}
				
				return contraint;
			},
			
			
			create$DialogElement:function (relationFieldMappingArray, _relationDataValueTwo, _relationDataTwoSql){
				if(this.getPos()==dyControlPos.subForm && this.options.destType != dyControlPos.mainForm){
					alert(this.getCtlName + "-" + "不宜将目标为从表的弹出框控件做为从表的控件");
					return;
				}
				
				
				
				var _this = this;
				 this.$editableElem.unbind("click");
				    this.$editableElem.click(function(){
						//获得要查询的字段
						var str = "";
						for(var j=0;j<relationFieldMappingArray.length;j++){
							if(relationFieldMappingArray[j] != "") {
								var tempObj =relationFieldMappingArray[j];
								str += ','+tempObj.sqlField;
							}
						}
						
						for(var i = 0; i < relationFieldMappingArray.length; i++){//去掉下划线
			        		var fields = relationFieldMappingArray[i];
			        		fields.sqlField = fields.sqlField.replace("_","");
			        		fields.sqlField = fields.sqlField.replace("_","");
			        		fields.sqlField = fields.sqlField.replace("_","");
			        	}
						
						
						var path = "";
						if(_relationDataValueTwo!= undefined) {
							path = "/basicdata/view/view_show?viewUuid="+_relationDataValueTwo+"&currentPage=1&openBy=dytable&relationDataDefiantion="+str;
						}
						
						/*if(_relationDataTwoSql!=undefined&&_relationDataTwoSql!=""){
							var reg = /\${([^}]*)}/g;
							var fields =  data.formula.match(reg);
							for(var i in fields){
								console.log(fields[i]);
							}
							path += "&whereSql="+_relationDataTwoSql;
						}*/
						
						var contraint = _this.getContraint();
						if($.wControlInterface.objectLength(contraint) > 0){
							path += "&whereSql="+ JSON.cStringify(contraint);
						}else{
							
						}
						
						 
						var title = "选择(" + _this.options.columnProperty.displayName +")";
						if(path.indexOf("${")>-1){
							var json = new Object();
							json.content = "没有相应条件的数据";
					        json.title = title;
					        json.height= 600;
					        json.width= 800;
					        showdialog(json);
						}else{
							 
							$.ajax({
								async:false,
								cache:false,
								url : ctx + path,
								success : function(data) {
									var json = new Object();
									json.content = "<div class='dnrw' style='width:99%;'>" + data +  " </div>";
							        json.title = title;
							        json.height= 600;
							        json.width= 800;
							        var $checkeds = $(json.content).find(".checkeds");
							        if($checkeds.size() > 0){//在视图配置成可以多选时,要把确认按钮显示出来
							        	   json.buttons={
									        	"确定":	function(){
									        		
									        		var datas = _this.collectData();//收集数据  
									        		if(datas.length == 0){
								        				alert("请选择一行数据");
								        				return;
								        			}
									        		
									        		 
									        	  if(_this.options.destType == dyControlPos.subForm){//从表
									        			_this.addRowData(relationFieldMappingArray, datas);//添加到从表中  
									        		}else{
									        			_this.setValue2Controls(relationFieldMappingArray, datas[0]);  
									        		}
									        		$("#dialogModule").dialog( "close" );
									        	}
										    };
								     }
							        
							        showDialog(json);
							        
							        var $checkeds = $(".checkeds");
							        
							        if(_this.options.selectType != dySelectType.multiple || _this.options.destType != dyControlPos.subForm){
							        	//设置为单选,(目标类型为从表且为单选,目标类型为主表)
							        	$checkeds.click(function(){
							        		if($(this).attr("checked") == "checked"){//选中一个，则把其他的设置为非选中
							        			$checkeds.attr("checked",false);
							        			$(this).attr("checked",true); 
							        		}
							        	});
							        }
							        
							        $(".dataTr").unbind("dblclick");
							        $(".dataTr").live("dblclick",function(){
							        	var jsonstr = $(this).attr("jsonstr");
							        	var jsonObj = eval("(" + urldecode(jsonstr) + ")");
							        	var datas = [];
							        	datas.push(jsonObj);
							        	 if(_this.options.destType == dyControlPos.subForm){//从表
						        			_this.addRowData(relationFieldMappingArray, datas);//添加到从表中  
						        		}else{
						        			_this.setValue2Controls(relationFieldMappingArray, jsonObj);  
						        		}
							        	
							        	 
							        	$("#dialogModule").dialog( "close" );
							        	
									});
								}
							});
						}
					});
			},
			/***
			 * 收集选中行的数据
			 */
			collectData:function(){
				var $checkeds = $("#dialogModule").find(".checkeds:checked");
				var viewDatas = [];
				$checkeds.each(function(){
					if($(this).parent().parent().is(".dataTr")){
						var $tr = $(this).parent().parent();
						var jsonstr = $tr.attr("jsonstr");
			        	var jsonObj = eval("(" + urldecode(jsonstr) + ")");
			        	 
			        	viewDatas.push(jsonObj);
					}
				});
				return viewDatas;
			},
			
			/**
			 * 将数据添加到从表中
			 */
			addRowData:function(mappingFields, datas){
				var $form = this.$placeHolder.parents("form");
				var destFormId = this.options.destSubform;
				var paramsObjs = [];
				for(var index = 0 ; index < datas.length; index ++){
					var fieldsKeyValue = datas[index]; 
					var fieldsKeyValueObj = {};
					for(var i in fieldsKeyValue){
						fieldsKeyValueObj[i.toLowerCase()] = fieldsKeyValue[i];
					}
					fieldsKeyValue = fieldsKeyValueObj;
					var _this = this; 
					var paramsObj = {};
					
					for(var i  = 0; i <  mappingFields.length; i++){
						var tempObj = mappingFields[i];
						 var sqlField = tempObj.sqlField;
						 var formField = tempObj.formField;
						 var value =  fieldsKeyValue[sqlField.toLowerCase()]; 
						 if(typeof value == "undefined"){
							 value = "";
						 }
						 paramsObj[formField] = value; 
					}
					paramsObjs.push(paramsObj); 
					$form.dyform("addRowData",  destFormId, paramsObj);//添加到从表中
				}
				
				//alert(JSON.cStringify(paramsObjs));
				
				
				if(_this.options.afterDialogSelect){
	        		_this.options.afterDialogSelect.call(this, this.getCtlName(), paramsObjs, datas);
				}
			},
			
			/**
			 * 将值设置到各映射字段中
			 */
			setValue2Controls:function(mappingFields, fieldsKeyValue){
				var issubformdialog=false; 
				 if(this.getCtlName().indexOf('____'+this.options.columnProperty.columnName)>0){
					 issubformdialog=true;
				 }else if(this.getCtlName().indexOf('___'+this.options.columnProperty.columnName)>0){
					 issubformdialog=false;
				 }
				var fieldsKeyValueObj = {};
				for(var i in fieldsKeyValue){
					fieldsKeyValueObj[i.toLowerCase()] = fieldsKeyValue[i];
				}
				fieldsKeyValue = fieldsKeyValueObj;
				var _this = this; 
				var paramsObj = {};
				
				for(var i  = 0; i <  mappingFields.length; i++){
					var tempObj = mappingFields[i];
					 var sqlField = tempObj.sqlField ;
					 var formField = tempObj.formField ;
					 var value =  fieldsKeyValue[sqlField.toLowerCase()]; 
					 if(typeof value == "undefined"){
						 value = "";
					 }
					var control={};
					if(_this.getPos()==dyControlPos.subForm){
						var datauuid=_this.getDataUuid();
						if(issubformdialog){
							control = $.ControlManager.getCtl(datauuid+'____'+formField); 
							paramsObj[datauuid+'____'+formField] =  value ;
						}else{
							control = $.ControlManager.getCtl(datauuid+'___'+formField); 
							paramsObj[datauuid+'___'+formField] =  value ;
						}
						if(typeof control == "undefined" || control == null){
							continue;
						}
						control.setValue(value);
					}else{
						control = $.ControlManager.getCtl(formField);
						//alert(control.getCtlName() + "---" + value);
						 if(control==undefined){//设置自定义映射
						    	$("#"+formField).val(value);
						  }else{
					    	if(typeof control == "undefined" || typeof control.setValue == "undefined"){
								continue;
							}
							 
							control.setValue(value);
							 if("cbzxfzrid" == control.getCtlName()){
								// alert(111);
							 }
							 
							 paramsObj[formField] = value;
						  }
					}
				}
				
				if(_this.options.afterDialogSelect){
	        		_this.options.afterDialogSelect.call(this, this.getCtlName(), paramsObj, fieldsKeyValue);
				}
			}
	};
	
	/*
	 * DIALOG PLUGIN DEFINITION =========================
	 */
	$.fn.wdialog = function(option) {
		var method = false;
		var args = null;
		if (arguments.length == 2) {
			method = true;
			args = arguments[1];
		}
		
		if (typeof option == 'string') {
			if(option === 'getObject'){ //通过getObject来获取实例
				var $this = $(this);
				data = $this.data('wdialog');
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
					data = $this.data('wdialog'), 
					options = typeof option == 'object'
							&& option;
					if (!data) {
						 data = new Dialog($(this),options);
						 
						 $.extend(data,$.wControlInterface);
				 
						 $.extend(data,$.wTextCommonMethod);
						 $.extend(data,$.Dialog);
						 data.init();
						 $this.data('wdialog',data );
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
	
	
	

	$.fn.wdialog.Constructor = Dialog;

	$.fn.wdialog.defaults = {
			columnProperty:columnProperty,//字段属性
			commonProperty:commonProperty,//公共属性
	        readOnly:false,
	        isShowAsLabel:false,
	        disabled:false,
	        isHide:false,//是否隐藏
	    	relationDataTextTwo: "",		 	
			relationDataValueTwo: "", 		 	
			relationDataTwoSql: "", 	
			relationDataDefiantion:"", 	
			relationDataShowMethod: "", 	
			relationDataShowType: "",
			afterDialogSelect: function(){}
	};
	
})(jQuery);
 var jqueryWDialogSession = {};
