$(function() {
	// 初使化
	JDS.call({
		service : 'workTaskCancelService.getData',
		data : [$("#formUuid").val(), $("#dataUuid").val()],
		success : function(result) {
			// init('abc', data, 'save', submit);
			$("#abc").dytable({
				data : result.data.formAndDataBean,
				btnSubmit : 'save',
				buttons:{text:"选择未完成任务",method:choseMission ,subtableMapping:"dy_form_id_task_cancel_detail"},
				beforeSubmit : submit,
				open:function(){
					 
				}
			});
		},
		error : function(xhr, textStatus, errorThrown) {
		}
	});
	$('#closebtn').click(function(){
		if(!confirm('是否要保存编辑内容，是则保存并关闭，否则不保存并关闭')){
			window.close();
		}else{
			$('#save').click();
		}
	});
	 $('#submitbtn').click(function(){
		 var rootFormData = $("#abc").dytable("formData");
		 var workFormBean = {}; 
			workFormBean.formUuid=$("#formUuid").val(); 
			workFormBean.rootFormDataBean = rootFormData;
			if(rootFormData.formDatas[1].recordList.length==0){
				 $.jBox.info('请添加或者选择明细', dymsg.tipTitle);
				 return;
			}
			JDS.call({
				service : 'workTaskCancelService.saveData',
				data : workFormBean,
				success : function(result) {
					 
					//$.jBox.info(dymsg.saveOk, dymsg.tipTitle);
					 try{
						 window.opener.location.reload();
						 window.close();
					 }catch (e) {
						// TODO: handle exception
					}
				},
				error : function(xhr, textStatus, errorThrown) {
				}
			});
		
	 });

	function submit() { 
	}
});
var task_name1; 
var target1;
var begin_time1;
var end_time1;
var count1;
var type1;
var uuid1;
function choseMission(){
	var subid=$("#abc" ).dytable("getIdInfo","dy_form_id_task_cancel_detail"); 
	var u1=$("#abc").dytable("getSubFieldInfo",{uuid:$("#formUuid").val(),fieldMappingName:"dy_work_task_cancel_detail_uuid"}); 
	var n1=$("#abc").dytable("getSubFieldInfo",{uuid:$("#formUuid").val(),fieldMappingName:"dy_work_task_cancel_detail_name"}); 
	var t1=$("#abc").dytable("getSubFieldInfo",{uuid:$("#formUuid").val(),fieldMappingName:"dy_work_task_cancel_detail_target"});
	var b1=$("#abc").dytable("getSubFieldInfo",{uuid:$("#formUuid").val(),fieldMappingName:"dy_work_task_cancel_detail_begin_time"});
	var e1=$("#abc").dytable("getSubFieldInfo",{uuid:$("#formUuid").val(),fieldMappingName:"dy_work_task_cancel_detail_end_time"});
	var c1=$("#abc").dytable("getSubFieldInfo",{uuid:$("#formUuid").val(),fieldMappingName:"dy_work_task_cancel_detail_count"});
	var ty1=$("#abc").dytable("getSubFieldInfo",{uuid:$("#formUuid").val(),fieldMappingName:"dy_work_task_cancel_detail_type"});
	task_name1=n1[0]; 
	target1=t1[0];
	begin_time1=b1[0];
	end_time1=e1[0];
	count1=c1[0];
	type1=ty1[0]; 
	uuid1=u1[0];
	var taskarray= window.showModalDialog(ctx+"/worktask/task/list_unfinished.action?fromtype=cancel",null,"dialogWidth=800px;dialogHeight=600px");
	 
	for(var i=0;i<taskarray.length;i++){  
		var str="{"+type1+":'"+taskarray[i].task_type+"',"+task_name1+":'"+taskarray[i].task_name+"',"+target1
		+":'"+taskarray[i].target+"',"+begin_time1+":'"+taskarray[i].plan_begin_time+"',"+end_time1+":'"+taskarray[i].plan_end_time+"',"+count1+":'"+taskarray[i].plan_work_count+"',"+uuid1+":'"+taskarray[i].task_uuid+"'}";
 
		 var obj=eval('('+str+')'); 
		
	 jQuery("#"+subid).jqGrid('addRowData',99,obj);
		 
	}
}