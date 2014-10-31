var Entrust = {};
var userDetails = SpringSecurityUtils.getUserDetails();
$(function() {
	if(userDetails.userId != "U0010000001") {
		$("#CONSIGNOR_NAME").val(userDetails.userName);
		$("#CONSIGNOR").val(userDetails.userId);
		$("#CONSIGNOR_NAME").after("<span>"+userDetails.userName+"</span>");
		$("#CONSIGNOR_NAME").hide();
	}
	var form_selector = "#duty_agent_form";
	var uuid = $("#uuid").val();
	var bean = {
			"uuid" : null,
			"consignor" : null,
			"trustee" : null,
			"businessType" : null,
			"content" : null,
			"condition" : null,
			"status" : null,
			"code" : null,
			"fromTime" : null,
			"toTime" : null,
			"consignorName" : null,
			"trusteeName" : null,
			"formatedFromTime" : null,
			"formatedToTime" : null,
			"displayContent":null
		};
	//获得Bean
	if(uuid != undefined && uuid != "" && uuid != null) {
		JDS.call({
			service : "dutyAgentService.getBean",
			data : uuid,
			success : function(result) {
				bean = result.data;
				$("#CONSIGNOR_NAME").val(bean.consignorName);
				$("#CONSIGNOR").val(bean.consignor);
				$("#TRUSTEE_NAME").val(bean.trusteeName);
				$("#TRUSTEE").val(bean.trustee);
				$("#CODE").val(bean.code);
				$("#CONDITION").val(bean.condition);
				$("#DISPLAY_CONTENT").val(bean.displayContent);
				$("#CONTENT").val(bean.content);
				$("#STATUS").val(bean.status);
				$("#FROM_TIME").val(bean.formatedFromTime);
				$("#TO_TIME").val(bean.formatedToTime);
				$('input[type=radio][name=STATUS][value=' + bean.status + ']').attr('checked',true);
				//设置业务类型的值
				$("#BUSINESS_NAME").comboTree("initValue", bean.businessType);
				// 设置树形下拉框的值
				$("#DISPLAY_CONTENT").comboTree("initValue", bean.content);
			}
		});
	}
	
	// 委托人
	$("#CONSIGNOR_NAME").click(function(e) {
		$.unit.open({
			title : "选择人员",
			labelField : "CONSIGNOR_NAME",
			valueField : "CONSIGNOR",
			selectType : 4
		});
	});
	// 受托人
	$("#TRUSTEE_NAME").click(function(e) {
		$.unit.open({
			title : "选择人员",
			labelField : "TRUSTEE_NAME",
			valueField : "TRUSTEE",
			selectType : 4
		});
	});

	// 委托内容
	var setting = {
		async : {
			otherParam : {
				"serviceName" : "dutyAgentService",
				"methodName" : "getContentAsTreeAsync",
				"data" : "workflow"
			}
		},
		check : {
			enable : true,
			chkStyle:"checkbox",
			chkboxType : { Y : "s", N : "ps" }
		}
	};
	$("#DISPLAY_CONTENT").comboTree({
		labelField : "DISPLAY_CONTENT",
		valueField : "CONTENT",
		treeSetting : setting,
		initService : "dutyAgentService.getKeyValuePair",
		initServiceParam : [ "workflow" ]
	});
	
	var setting2 = {
			async : {
				otherParam : {
					"serviceName" : "dataDictionaryService",
					"methodName" : "getFromTypeAsTreeAsync",
					"data":"BUSINESS_TYPE"
				}
			},
			check : {
				enable : false
			},
			callback : {
				onClick: treeNodeOnClick,
			}
			
	};
	
	//表调用的回调函数
	function treeNodeOnClick(event, treeId, treeNode) {
		
		$("#BUSINESS_NAME").val(treeNode.name);
		$("#BUSINESS_TYPE").val(treeNode.data);
		$("#BUSINESS_NAME").comboTree("hide");
	}
	
	$("#BUSINESS_NAME").comboTree({
		labelField: "BUSINESS_NAME",
		valueField: "BUSINESS_TYPE",
		treeSetting : setting2,
		initService : "dataDictionaryService.getKeyValuePair",
		initServiceParam : [ "BUSINESS_TYPE" ],
		width: 220,
		height: 220
	});

	// 保存用户信息
	$("#btn_save").click(function() {
		//?没有编号
		bean.uuid = uuid;
		bean.consignor = $("#CONSIGNOR").val();
		bean.trustee = $("#TRUSTEE").val();
		bean.businessType = $("#BUSINESS_TYPE").val();
		bean.content = $("#CONTENT").val();
		bean.condition = $("#CONDITION").val();
		bean.status = $("input[type=radio][name=STATUS]:checked").val();
		bean.code = $("#CODE").val();
		bean.fromTime = $("#FROM_TIME").val() + ":00";
		bean.toTime = $("#TO_TIME").val() + ":00";
		bean.formatedFromTime = $("#FROM_TIME").val();
		bean.formatedToTime = $("#FROM_TIME").val();
		bean.consignorName = $("#CONSIGNOR_NAME").val();
		bean.trusteeName = $("#TRUSTEE_NAME").val();
		bean.displayContent = $("#DISPLAY_CONTENT").val();
		JDS.call({
			service : "dutyAgentService.saveBean",
			data : bean,
			success : function(result) {
				oAlert("保存成功!");
			}
		});
	});
	Entrust.del = function() {
		var element = $(this);
		var arrayObj = readParam();
		var uuids = new Array();
		for (var i=0;i<=arrayObj.length-1;i++) { 
			var uuid = arrayObj[i]["uuid"];
			uuids.push(uuid);
		}
		if (uuids.length == 0) {
			oAlert("请先选择要删除的记录!");
		}else {
			if (confirm("确定要删除所选记录吗?")) {
				JDS.call({
					service : "dutyAgentService.removeAll",
					data : [uuids],
					success : function(result) {
						oAlert("删除成功!");
						refreshWindow(element);
					}
				});
			}
		}
	};
});

//获取url参数
function readParam(){
	var arrayObj = new Array();
	$(".checkeds:checked").each(function(i){
		var s = new Object(); 
		var index = $(this).parent().parent(".dataTr").attr("src").indexOf("?");
		var search = $(this).parent().parent(".dataTr").attr("src").substr(index);
		var searchArray = search.replace("?", "").split("&");
		for(var i=0;i<searchArray.length;i++){
			var paraArray = searchArray[i].split("=");
			var key = paraArray[0];
			var value = paraArray[1];
			s[key] = value;
		}
		arrayObj.push(s);
	});
	return arrayObj;
}
