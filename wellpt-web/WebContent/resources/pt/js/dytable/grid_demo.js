$(function() {
	
	 
	/*$("#abc").dytable({
		data : result.data,
		isFile2swf:true,
		enableSignature:true,//是否签名
		setReadOnly:false,//是否设置所有字段只读，true表示设置,false表示不设置
		supportDown:"2",//2表示防止下载 1表示支持下载 不设置表示默认支持下载
		btnSubmit : 'save',
		beforeSubmit : submit,
		buttons:[{"text": "选择完成任务","method":function(){alert(1);},"subtableMapping":"dy_form_id_report_evaluate"},
		         {"text": "选择未完成任务","method":function(){alert(2);},"subtableMapping":"dy_form_id_report_evaluate"},
		         {"text": "选择未成任务","method":function(){alert(3);},"subtableMapping":"dy_form_id_report_plan"},
		         {"text": "选择未完务","method":function(){alert(4);},"subtableMapping":"dy_form_id_report_plan"},
		         ],
		open: function(){
		}
	});
*/
	 
	 
	 jQuery("#list4").jqGrid(  
			 { 
				 url:"server.php",
				 datatype: "local", 
				 height: 250, 
				 colNames:['Inv No','Date', 'Client', 'Amount','Tax','Total','Notes'], 
				 colModel:[ {name:'id',index:'id', width:60, sorttype:"int"}, 
				            {name:'invdate',index:'invdate', width:90, sorttype:"date"}, 
				            {name:'name',index:'name', width:100},
				            {name:'amount',index:'amount', width:80, align:"right",sorttype:"float"}, 
				            {name:'tax',index:'tax', width:80, align:"right",sorttype:"float"}, 
				            {name:'total',index:'total', width:80,align:"right",sorttype:"float"}, 
				            {name:'note',index:'note', width:150, sortable:false} 
				           ], 
				 multiselect: true, 
				 caption: "Manipulating Array Data" 
			 }); 
	 var mydata = [ 
	                {id:"1",invdate:"2007-10-01",name:"test",note:"note",amount:"200.00",tax:"10.00",total:"210.00"},
	                {id:"2",invdate:"2007-10-02",name:"test2",note:"note2",amount:"300.00",tax:"20.00",total:"320.00"}, 
	                {id:"3",invdate:"2007-09-01",name:"test3",note:"note3",amount:"400.00",tax:"30.00",total:"430.00"}, 
	                {id:"4",invdate:"2007-10-04",name:"test",note:"note",amount:"200.00",tax:"10.00",total:"210.00"}, 
	                {id:"5",invdate:"2007-10-05",name:"test2",note:"note2",amount:"300.00",tax:"20.00",total:"320.00"}, 
	                {id:"6",invdate:"2007-09-06",name:"test3",note:"note3",amount:"400.00",tax:"30.00",total:"430.00"}, 
	                {id:"7",invdate:"2007-10-04",name:"test",note:"note",amount:"200.00",tax:"10.00",total:"210.00"}, 
	                {id:"8",invdate:"2007-10-03",name:"test2",note:"note2",amount:"300.00",tax:"20.00",total:"320.00"}, 
	                {id:"9",invdate:"2007-09-01",name:"test3",note:"note3",amount:"400.00",tax:"30.00",total:"430.00"} ]; 
	 for(var i=0;i<=mydata.length;i++) 
		 jQuery("#list4").jqGrid('addRowData',i+1,mydata[i]);
	 
	 
	 
	  colModel = [
			            {name:'id',index:'id', width:90, sorttype:"int", editable: true   }, 
			            {name:'name',index:'name', width:150,editable: true,editoptions:{size:"20",maxlength:"30"}}, 
			            {name:'stock',index:'stock', width:60, editable: true,edittype:"checkbox",editoptions: {value:"Yes:No"}}, 
			            {name:'ship',index:'ship', width:90, editable: false,edittype:"select",editoptions:{value:"FE:FedEx;IN:InTime;TN:TNT;AR:ARAMEX"  }}, 
			            {name:'note',index:'note', width:200, sortable:false,editable: true,edittype:"textarea",  editoptions:{rows:"2",cols:"10"}} ,
			            {name:'fileupload',index:'fileupload', width:300, sortable:false,editable: true,edittype:"fileupload",
			            	 editoptions:{
			            		dataInit:function(elem){
			            			 
			            		}
			            }
			            		/*custom_element:
				            		function(value, options){
				            		 	
				            			 var el = document.createElement("div");
				            			  el.type="text";
				            			  el.value = value;
				            			  //$(el).attr("id", "container_" + options.id);
				            			  var ctlID = "ctl_" + options.id;
				            			 
				            			  var fileupload = new WellFileUpload(ctlID);
				            			  var files = [];
				            			  if(WellFileUpload.files[ctlID]){
				            				  files = WellFileUpload.files[ctlID];
				            			  }
				            			
				            			 var fileList = files.slice(0); 
				      					  fileupload.init(false,   $(el),  false , true,  fileList);

				            			  return el;
				            		}, 
				            	custom_value:function(elem, operation, value){
				            		//console.log("custom_value:" + elem);
				            		//console.log("custom_value:" + operation);
				            		//console.log("custom_value:" + value);
				            		//  if(operation === 'get') {
				            		//       return $(elem).val();
				            		//    } else if(operation === 'set') {
				            		//       $('input',elem).val(value);
				            		 //   }
				            		
				            		
				            		
				            	}
				            }, 
				            formatter:function(cellvalue, options, rowObject){
				            	 
				            	 var el = document.createElement("div");
				            	 el.innerHTML="<div></div>"; 
				            	 var ctlID = "ctl_" + options.rowId + "_" + options.colModel.name;
		            			 
		            			  var fileupload = new WellFileUpload(ctlID);
		            			    
		            			  var files = [];
		            			  if(WellFileUpload.files[ctlID]){
		            				  files = WellFileUpload.files[ctlID];
		            			  }else{
		            				  files = cellvalue;
		            			  }
		            			
		            			 var fileList = files.slice(0); 
		      					  fileupload.init(false,  $( $(el).find("div")[0]),  false , true,  fileList);
		            			 
				            	 
				            	return $(el).html();
				            }*/
			            } 
			           ];
	 
	 var lastsel2 ;
	 jQuery("#rowed5").jqGrid(
			 {
				 url:null, 
				 datatype: "local", 
				 height: 250, 
				 colNames:['ID Number','Name', 'Stock', 'Ship via','Notes', "附件"], 
				 colModel: colModel, 
				 cellEdit:true,
				 cellsubmit:'clientArray',
				 onSelectRow: function(id){
				        	   if(id && id!==lastsel2){ 
				        		   //jQuery('#rowed5').jqGrid('restoreRow',lastsel2); 
				        		   jQuery('#rowed5').jqGrid('editRow',id,true); lastsel2=id; 
				        		  } 
				  }, 
				
				 caption: "Input Types" ,
					 editurl: null
			}); 
	 
	 
	  mydata2 = [ 
	                 {id:"12345",name:"Desktop Computer",note:"note",stock:"Yes",ship:"FedEx", fileupload:[
	                        { fileID:"1394787da8b54a9787e1869a20f687d7", fileName:"WELL_PT_动态表单接口调用V1.0.docx"},
	                        { fileID:"97f891f214fb4bee8ba12a02d57e57bf", fileName:"desktop.ini"}
	                	 	
	                	 	]}, 
	                 {id:"23456",name:"Laptop",note:"Long text ",stock:"Yes",ship:"InTime", fileupload:[]}, 
	                 {id:"34567",name:"LCD Monitor",note:"note3",stock:"Yes",ship:"TNT", fileupload:[]}, 
	                 {id:"45678",name:"Speakers",note:"note",stock:"No",ship:"ARAMEX", fileupload:[]}, 
	                 {id:"56789",name:"Laser Printer",note:"note2",stock:"Yes",ship:"FedEx", fileupload:[]}, 
	                 {id:"67890",name:"Play Station",note:"note3",stock:"No", ship:"FedEx", fileupload:[]}, 
	                 {id:"76543",name:"Mobile Telephone",note:"note",stock:"Yes",ship:"ARAMEX", fileupload:[]}, 
	                 {id:"87654",name:"Server",note:"note2",stock:"Yes",ship:"TNT", fileupload:[]}, 
	                 {id:"98765",name:"Matrix Printer",note:"note3",stock:"No", ship:"FedEx", fileupload:[]} ]; 
	 
	 for(var i=0;i < mydata2.length;i++) 
		 jQuery("#rowed5").jqGrid('addRowData',mydata2[i].id,mydata2[i]);
 
});