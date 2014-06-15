(function() {
    CKEDITOR.dialog.add("formfile", 
    function(editor) {
    	var Id = "";
        return {
            title: "从表插入",
            minWidth: 750,
            minHeight:500,
            contents: [{
                id: "tab1",
                label: "label",
                title: "title",
                expand: true,
                padding: 0,
                elements: [
					{
						id: 'selectForm',
						type: 'text',
						style: 'width: 98%;height:40px;margin:0 5px;',
						onClick:function () {
							var d = this.getDialog();
							var Obj =  d.getContentElement('tab1','selectForm');
							 Id = Obj.getInputElement().$.id;
							 $("#"+Id).after("<input type='hidden' name='_"+Id+"' id='_"+Id+"' value='' />");
							
							var setting = {
									async : {
										otherParam : {
											"serviceName" : "formDefinitionService",
											"methodName" : "getFullTree",
											"data":[""]
											
										}
									},
									check : {
										enable : false
									},
									callback : {
										onClick: function(event, treeId, treeNode) {
											$("#fieldCfgTable").append("<tr name=tr-td><td>test</td><td name=fieldMapping></td></tr>");
												$("#"+Id).val(treeNode.name);
												$('#_'+Id).val(treeNode.id);
												//加载数据
												$.ajaxSettings.async = false;
												$.getJSON(contextPath + "/dytable/field_list.action?tableId=" + treeNode.id,function (data){
													var selectHTML = "<select name=fieldSelect class='formfileSelect'>";
													var $table= $("#fieldCfgTable tbody");
													$("#fieldCfgTable .tr-td").remove();
													for(var index=0;index<data.length;index++) {
														 selectHTML += "<option value='"+data[index].value+"' name='"+data[index].label+"'>" + data[index].label + "</option>";
								                    	 var vTr= '<tr name=tr-td class=tr-td ><td style="border: 1px solid #000000;"><input type="text" name="inputField" value="'+data[index].label+'"/></td><td style="border: 1px solid #000000;" class=fieldMapping name=fieldMapping></td></tr>';
								                    	 $table.append(vTr);
													}
													selectHTML += "</select>";
													$(".fieldMapping").each(function() {
														$(this).html(selectHTML);
													});
													$("#"+Id).comboTree("hide");
												});
										},
										onAsyncSuccess:function(event, treeId, treeNode, msg) {
											var nodes = $.fn.zTree.getZTreeObj(treeId).getNodesByParam('id','',null);
											if(nodes && nodes.length > 0){
												$('#'+Id).val(nodes[0].name);
											}else{
												$('#'+Id).val();
											}
										}
									}
								};
							var i = 0;
							while(i == 0){
								$("#"+Id).comboTree({
									labelField : Id,
									valueField : "_"+Id,
									treeSetting : setting
								});
								i++;
							}
							$("#fieldCfgTable .tr-td").unbind("click");
							$("#fieldCfgTable .tr-td").die().live("click",function() {
								if(	$(this).css("background-color") == "rgb(75, 154, 210)") {
									$(this).css("background-color","");
								}else {
									$(this).css("background-color","rgb(75, 154, 210)");
								}
							});
						},
						commit: function (editor) {
						},
					},
					{
						 type: "vbox",
		                    padding: 0,
		                    children: [{
		                        type: "hbox",
		                        widths: ["62px", "62px","62px","62px"],
		                        children: [{	
		                        	id:'button_add',
		    						label : '添加行',
		                        	type:'button',
		                        	onClick:function () {
		                        		var $table= $("#fieldCfgTable tbody");
		                        		var vTr2 = $("#fieldCfgTable .tr-td").eq(0).clone(true);
		                        		vTr2.find("input").each(function(){
		                        			$(this).val("");
		                        		});
		                        		$table.append(vTr2);
		                        	}
		                        },
		                        {	id:'button_reduce',
		                        	label : '删除行',
		                        	type:'button',
		                        	onClick:function () {
		                        		$("#fieldCfgTable .tr-td").each(function() {
		                        			var t = $(this);
		                        			if(t.css("background-color") == "rgb(75, 154, 210)") {
		                        				t.remove();
		                        			}
		                        		});
		                        	}
		                        },
		                        {	id:'button_up',
		    						label : '上移行',
		                        	type:'button',
		                        	onClick:function () {
		                        			$("#fieldCfgTable .tr-td").each(function() {
		                            			if($(this).css("background-color") == "rgb(75, 154, 210)") {
		                            				var prevStr = $(this).prev().html();
		                            				if($(this).prev().find("th").html() == "标题") {
		                            					alert("已经是第一行！");
		                            					return;
		                            				}else {
		                            					$(this).prev().remove();
		                                				prevStr = "<tr class='tr-td' name='tr-td'>"+prevStr+"</tr>";
		                                				$(this).after(prevStr);
		                            				}
		                            			}
		                            		});
		                        		
		                        	}
		                        },
		                        {	id:'button_down',
		                        	label : '下移行',
		                        	type:'button',
		                        	onClick:function () {
		                        		$("#fieldCfgTable .tr-td").each(function() {
		                        			if($(this).css("background-color") == "rgb(75, 154, 210)") {
		                        				var prevStr = $(this).next().html();
		                        				if(prevStr == null) {
		                        					alert("已经是最后一行！");
		                        					return;
		                        				}else {
		                        					$(this).next().remove();
		                            				prevStr = "<tr class='tr-td' name='tr-td'>"+prevStr+"</tr>";
		                            				$(this).before(prevStr);
		                        				}
		                        			}
		                        		});
		                        	}
		                        }]
		                    }]
					},
					{	id:'table_html',
                    	type:'html',
                    	style: 'width: 100%;',
                    	html:'<div class="tableDiv"><table style="width:98%;" id="fieldCfgTable"><tr class="th"><td style="background-color: #0F599C;border: 1px solid #0F599C;border-radius: 0 0 0 0;color: #FFFFFF;padding: 7px 0 7px 20px;display: table-cell;" class="label">标题</td><td style="background-color: #0F599C;border: 1px solid #0F599C;border-radius: 0 0 0 0;color: #FFFFFF;padding: 7px 0 7px 20px;display: table-cell;">对应字段</td></tr></table><div>'
                    }, 
                     
				
                ]
            }],
            onOk: function() {
            	var num = Math.ceil(Math.random()*10000);
            	var ckTable = '<table width="100%" border="1" tableType="2" editType="1" tableId="'+$("#_"+Id).val()+'" id="'+num+'"><tr>';
        		var selectArr = $("#fieldCfgTable").find("select[name=fieldSelect]");
        		var inputArr = $("#fieldCfgTable").find("input[name=inputField]");
        		
        		var appendHtml = "";
        		for(var i=0;i<selectArr.length;i++){
        			appendHtml += "<th>"+$(inputArr[i]).val()+"</th>";
        		}
        		ckTable += appendHtml + '</tr></table>';
        		
				editor.insertHtml(ckTable);
            }
        };
    });
})();