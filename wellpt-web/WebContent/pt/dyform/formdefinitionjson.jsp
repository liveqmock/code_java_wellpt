<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>表单定义json</title>
</head>
<body>
{
	<!--主表IdEntity属性-->
    "uuid": "0e46407a-d196-4997-afb0-e270cf62720e",
	"createTime":"",
	"creator":"UT001",
	"modifyTime":"",
	"modifier":"UT002",
	"recVer":"95",
	<!--主表基本属性-->
	"name":"userform_ssxx_ztdj",
	"id":"userform_ssxx_ztdj",
	"applyTo":"WORK_TASK_SUB_1",
	"descname":"商事登记_主体基本信息",
	"tableNum":"001",
	"formDisplay":"2",
	"showTableModel":"商事登记_主体基本信息显示单",
	"showTableModelId":"f0348a75-676a-4239-8a47-07804ecb1c63",
	"printTemplate":"NOTICE",
	"printTemplateName":"公告",
	"fileName":"",
	"desp":"",
	"htmlPath":"/wellpt-web/pt/upload/1386152117767//商事主体基本信息.html",
	"version":"1.0",
	"moduleId":"DATAEXCHANGE",
	"moduleName":"数据交换",
	"formSign":"1",
	<!--定义模板-->
	"html":"",
	
	<!--主表字段定义-->
	"field":{
		"ZTZT":{
			<!--主表IdEntity属性-->
		    "uuid": "03ed6183-8626-407b-a67a-e9d5627b0c5d",
			"createTime":"04-12月-13 06.15.24.020000 下午",
			"creator":"U0000000001",
			"modifyTime":"04-5月 -14 07.40.57.547000 下午",
			"modifier":"U0010000001",
			"recVer":"95",
			<!--字段基本属性-->
			"applyTo":"",
			"fieldName":"ZTZT",
			"descname":"主体状态",
			"entityName":"userform_ssxx_ztdj",
			"indexed":"0",
			"showed":"0",
			"sorted":"0",
			"sysType":"",
			"length":"30",
			"defaultValue":"",
			"isHide":"1",
			"defaultValueWay":"1",
			"floatSet":"",
			"setUrlOnlyRead":"",
			"jsFunction":"",
			"inputType":"1",
			"relationDataTextTwo":"",
			"relationDataValueTwo?":"",
			"relationDataDefiantion":"",
			"fieldCheckRules":"",
			"relationDataTwoSql":"",
			"relationDataText":"",
			"relationDataValue":"",
			"relationDataSql":"",
			"relationDataShowMethod":"",
			"relationDataShowType":"",
			<!--控件类型-->
			"type":"1",
			"uploadFileType":"",
			"isGetZhengWen":"",
			"dataShow":"1",
			"data":"",
			"serviceName":"",
			"methodName":"",
			"ctrlField":"",
			"optionDataSource":"",
			"textAlign":"",
			"fontWidth":"",
			"fontHight":"",
			"fontSize":"12",
			"fontColor":"",
			"textareaRow":"",
			"textareaCol":"",
			"designatedId":"",
			"designatedType":"",
			"isOverride":"",
			"isSaveDb":"",
			"unEditDesignatedId":"",
			"unEditIsSaveDb":"" 
			
			
		},
		"JYHW":{
			<!--主表IdEntity属性-->
		    "uuid": "62ec30e0-5d0c-4821-bfc9-3d846b5947be",
			"createTime":"04-12月-13 06.15.24.020000 下午",
			"creator":"U0000000001",
			"modifyTime":"04-5月 -14 07.40.57.547000 下午",
			"modifier":"U0010000001",
			"recVer":"95",
			<!--字段基本属性-->
			"applyTo":"",
			"fieldName":"JYHW",
			"descname":"经营范围",
			"entityName":"userform_ssxx_ztdj",
			"indexed":"0",
			"showed":"0",
			"sorted":"0",
			"sysType":"",
			"length":"30",
			"defaultValue":"",
			"isHide":"1",
			"defaultValueWay":"1",
			"floatSet":"",
			"setUrlOnlyRead":"",
			"jsFunction":"",
			"inputType":"1",
			"relationDataTextTwo":"",
			"relationDataValueTwo?":"",
			"relationDataDefiantion":"",
			"fieldCheckRules":"",
			"relationDataTwoSql":"",
			"relationDataText":"",
			"relationDataValue":"",
			"relationDataSql":"",
			"relationDataShowMethod":"",
			"relationDataShowType":"",
			<!--控件类型-->
			"type":"1",
			"uploadFileType":"",
			"isGetZhengWen":"",
			"dataShow":"1",
			"data":"",
			"serviceName":"",
			"methodName":"",
			"ctrlField":"",
			"optionDataSource":"",
			"textAlign":"",
			"fontWidth":"",
			"fontHight":"",
			"fontSize":"12",
			"fontColor":"",
			"textareaRow":"",
			"textareaCol":"",
			"designatedId":"",
			"designatedType":"",
			"isOverride":"",
			"isSaveDb":"",
			"unEditDesignatedId":"",
			"unEditIsSaveDb":"" 
			
			
		}
		
		
	},
	<!-- 从表 -->
	"subform":{
		"userform_ssxx_jycs":{
			<!-- 从表属性 -->
			<!--主表IdEntity属性-->
		    "uuid": "76df79ba-d271-4073-abe2-f1d628cea297",
			"createTime":"04-12月-13 06.15.24.020000 下午",
			"creator":"U0000000001",
			"modifyTime":"04-5月 -14 07.40.57.547000 下午",
			"modifier":"U0010000001",
			"recVer":"95",
			<!-- 从表基本属性 -->
			"mainFormUuid":"0e46407a-d196-4997-afb0-e270cf62720e",
			"editMode":"1",
			"hideButtons":"1",
			"tableTitle":"经营场所",
			"tableOpen":"2",
			"groupShowTitle":"",
			"isGroupShowTitle":"",
			"subrRelationDataDefiantion":"",
			"subformApplyTableId":"",
			"subFormUuid":"9559fa83-2836-441c-8ea6-cddb35bf8b33",
			"id":"000", 
			<!-- 从表字段定义 -->
			"field":{
				"JYCS":{
					"uuid": "c2fd5a3b-9da6-467a-ae97-ff5f2acd3a7d",
					"createTime":"04-5月 -14 07.40.57.707000 下午",
					"creator":"U0010000001",
					"modifyTime":"04-5月 -14 07.40.57.707000 下午",
					"modifier":"U0010000001",
					"recVer":"0",
					"srcFieldUuid":"",
					"destFieldUuid":"509c5d7e-00ba-4d0a-9489-cb43952a4a15",
					"subColHidden":"",
					"subColEdit":"",
					"fieldWidth":"",
					"fieldOrder":"0",
					"headerName":"经营场所"
				}
			}
			
		},
		"userform_ssxx_zzjg":{
			<!-- 从表属性 -->
			<!--主表IdEntity属性-->
		    "uuid": "7d26959b-6b1a-46c2-b3cd-87b035bad8db",
			"createTime":"04-12月-13 06.15.24.020000 下午",
			"creator":"U0000000001",
			"modifyTime":"04-5月 -14 07.40.57.547000 下午",
			"modifier":"U0010000001",
			"recVer":"95",
			<!-- 从表基本属性 -->
			"mainFormUuid":"0e46407a-d196-4997-afb0-e270cf62720e",
			"editMode":"1",
			"hideButtons":"1",
			"tableTitle":"组织机构信息",
			"tableOpen":"2",
			"groupShowTitle":"",
			"isGroupShowTitle":"",
			"subrRelationDataDefiantion":"",
			"subformApplyTableId":"",
			"subFormUuid":"33fb6090-5f07-4724-a831-814c0d278f3d",
			"id":"002", 
			<!-- 从表字段定义 -->
			"field":{
				"XM":{
					"uuid": "1e8522c8-5583-4820-9e04-cd3ebc95cbb4",
					"createTime":"04-5月 -14 07.40.57.707000 下午",
					"creator":"U0010000001",
					"modifyTime":"04-5月 -14 07.40.57.707000 下午",
					"modifier":"U0010000001",
					"recVer":"8",
					"srcFieldUuid":"",
					"destFieldUuid":"968be804-cb83-4f79-92f9-eb904ab57b04",
					"subColHidden":"",
					"subColEdit":"",
					"fieldWidth":"",
					"fieldOrder":"0",
					"headerName":"姓名"
				},
				"JW":{
					"uuid": "db5f7f89-ce1e-45e7-ae14-bb4dd547a85d",
					"createTime":"04-5月 -14 07.40.57.707000 下午",
					"creator":"U0010000001",
					"modifyTime":"04-5月 -14 07.40.57.707000 下午",
					"modifier":"U0010000001",
					"recVer":"8",
					"srcFieldUuid":"",
					"destFieldUuid":"c3d6950a-7ab7-4aae-b402-cd59f469d4a9",
					"subColHidden":"",
					"subColEdit":"",
					"fieldWidth":"",
					"fieldOrder":"1",
					"headerName":"职务"
				}
				
			}
			
		}
		
	}
	
	
}

 
</body>
</html>