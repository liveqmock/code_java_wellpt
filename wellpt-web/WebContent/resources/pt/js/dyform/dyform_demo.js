$(function() {
	
	 
	var formDefinition = loadFormDefinition($("#formUuid").val());
	
	
	$("#abc").dyform(formDefinition,
			{
				isFile2swf:true,
				//enableSignature:true,//是否签名
				readOnly:false,//是否设置所有字段只读，true表示设置,false表示不设置
				supportDown:"2",//2表示防止下载 1表示支持下载 不设置表示默认支持下载
				displayFormModel:false,//是否用单据展示
				success:function(){
					console.log("表单解析完毕");
				},
				error:function(){
					console.log("表单解析失败");
				}
			}
			
	);
	
	//在这里直接用parseForm接口代码 
	
	console.log("开始填充数据");
	
	var dataUuid = $("#dataUuid").val();
	if(typeof dataUuid != "undefinition" && dataUuid.length > 0){
		var formDatas = loadFormData($("#formUuid").val(), dataUuid); 
		$("#abc").dyform("fillFormData", formDatas, function(){
			console.log("数据填充完成");
		});
	}
	
	
/*	if(formDefinition.name == "userform_demo"){
		var data = { status:"正常", 
				aa:"2014-5-30 16:10:23",
				test:"2014-5-30 12:10:23"};
		$("#abc").dyform("addRowData", "642f277c-96ed-4ba3-b3ba-625c253fa95e", data); 
		
		data = { status:"正常", 
				aa:"2014-5-30 16:12:23",
				test:"2014-5-30 11:12:23"};
		$("#abc").dyform("addRowData", "642f277c-96ed-4ba3-b3ba-625c253fa95e", data); 
		
		data = { status:"正常", 
				test1:"2014-5-30 16:12:23",
				count:10
		};
		$("#abc").dyform("addRowData", "6c67703f-c360-48d0-a092-2ca482203d9b", data); 
	}*/
	
	 
	var validateForm = undefined;
	$("#validate").bind("click", function(){
		validateForm   = 	$("#abc").dyform("validateForm");
		console.log("valid:" + validateForm); 
	});
	
	
	
	
	var formData = undefined;
	
	$("#collectFormData").click(function(){
		if(validateForm ==  undefined){
			alert("请先验证数据");
			return;
		}else if(validateForm == false){
			alert("验证失败");
			return;
		}
		
		formData = $("#abc").dyform("collectFormData");
		if(typeof formData != "undefined"){
			console.log(JSON.stringify(formData));
		}
	});
	
	$("#save").click(function(){
		if(validateForm ==  undefined){
			alert("请先验证数据");
			return;
		}else if(validateForm == false){
			alert("验证失败");
			return;
		}else if(formData ==  undefined){
			alert("请先收集数据");
			return;
		}
		var url = ctx + "/dyformdata/saveFormData";
		$.ajax({
			url:url,
			type:"POST",
			data:  JSON.stringify(formData),
			dataType:'json',
			contentType:'application/json',
			success:function (result){
				 if(result.success == "true" || result.success == true){
					  alert("数据保存成功dataUuid=" + result.data);
					  var dataUuid =  result.data;
					  var formUuid = $("#formUuid").val();
					  var url = ctx + '/dyform/demo?formUuid=' + formUuid + "&dataUuid=" + dataUuid; 
						 
					  window.location.href = url;
	       			
       		   }else{
       			   alert("数据保存失败");
       		   }
			},
			error:function(data){
				 alert("数据保存失败");
			}
		});
		
		
		
	});
	
	
/*	var topicjson={
			   "response": [
			          {
			              "id": "1",
			              "elementName": "Grouping",
			              "url":"http://www.baidu.com",
			              level:"0", parent:"", isLeaf:false, expanded:false, loaded:true
			          },
			          {
			              "id": "1_1",
			              "elementName": "Simple Grouping",
			              "url":"http://www.baidu.com",
			              level:"1", parent:"1", isLeaf:true, expanded:false, loaded:true
			          },
			          {
			              "id": "1_2",
			              "elementName": "May be some other grouping",
			              "url":"http://www.baidu.com",
			              level:"1", parent:"1", isLeaf:true, expanded:false, loaded:true
			          },
			          {
			              "id": "2",
			              "elementName": "CustomFormater",
			              "url":"http://www.sina.com",
			              level:"0", parent:"", isLeaf:false, expanded:true, loaded:true
			          },
			          {
			              "id": "2_1",
			              "elementName": "Image Formatter",
			              "url":"http://www.sohu.com",
			              level:"1", parent:"2", isLeaf:true, expanded:false, loaded:true
			          },
			          {
			              "id": "2_2",
			              "elementName": "Anchor Formatter",
			              "url":"http://www.sohu.com",
			              level:"1", parent:"2", isLeaf:true, expanded:false, loaded:true
			          }
			      ]
			   };

			// $('<table id="list2"></table>').appendTo('#topics');
			var lastsel;
			var grid = jQuery("#cde");
			grid.jqGrid({
			   datastr: topicjson,
			   datatype: "jsonstring",
			   height: "auto",
			   loadui: "disable",
			   colNames: ["id","Items","url"],
			   colModel: [
			       {name: "id",width:200, hidden:false, key:true},
			       {name: "elementName", editable:true,width:250, resizable: false},
			       {name: "url",width:1, editable:true,hidden:false,width:250}
			   ],
			   treeGrid: true,
			   treeGridModel: "adjacency",
			   ExpandColumn: "elementName",
			   treeIcons: {leaf:'ui-icon-document-b'},
			   caption: "jqGrid Demos",
			   //autowidth: true,
			   rowNum: 10000,
			   //ExpandColClick: true,
			   jsonReader: {
			       repeatitems: false,
			       root: "response"
			   },
			   onSelectRow: function(id){ 
			    if(id && id!==lastsel){ 
			    grid.jqGrid('restoreRow',lastsel); 
			    grid.jqGrid('editRow',id,true); 
			    lastsel=id; 
			    } 
			   }
			});
			
			
			$("#addSubform").click(function(){
		 
				  var ret = {"error":"","total":1,"page":1,"records":1,"rows":[ {
		              "id": "1_3",
		              "elementName": "test Formatter",
		              "url":"http://www.voa.com",
		              level:"1", parent:"1", isLeaf:true, expanded:false, loaded:true
		          }]};
                $("#cde" ).jqGrid ('addChildNode', ret.rows[0].id, ret.rows[0].parent, ret.rows[0]);
			});
			$("#querySubform").click(function(){
				var datas  = $("#cde").jqGrid("getRowData");
				console.log(JSON.stringify(datas));
			});*/
});

