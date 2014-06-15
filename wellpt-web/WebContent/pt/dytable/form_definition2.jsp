<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<title id="title"></title>
<script type="text/javascript"
	src="${ctx}/resources/jquery/jquery.js"></script>
<script type="text/javascript"
		src="${ctx}/resources/bootstrap/js/bootstrap.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/utils/ajaxfileupload.js"></script>
<script type="text/javascript" src="${ctx}/resources/utils/json2.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/pt/js/dytable/dytable_constant.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/validate/js/jquery.validate.js"></script>
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/jqueryui/css/base/jquery-ui.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/jqgrid/css/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css" media="screen"
	href="${ctx}/resources/jqgrid/themes/ui.multiselect.css" />

<script type="text/javascript"
		src="${ctx}/resources/jqueryui/js/jquery-ui.js"></script>
<script src="${ctx}/resources/jqgrid/js/src/i18n/grid.locale-cn.js"
	type="text/javascript"></script>
<script type="text/javascript">
	$.jgrid.no_legacy_api = true;
	$.jgrid.useJSON = true;
</script>
<script src="${ctx}/resources/jqgrid/js/jquery.jqGrid.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/jBox/jquery.jBox.src.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/jBox/i18n/jquery.jBox-zh-CN.js"
	type="text/javascript"></script>
<link href="${ctx}/resources/jBox/Skins/Blue/jbox.css" rel="stylesheet"
	type="text/css" />
<!-- Bootstrap -->
<link rel="stylesheet"
	href="${ctx}/resources/bootstrap/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/pt/css/custom-admin-style.css" />
	
<link rel="stylesheet"
	href="${ctx}/resources/ztree/css/zTreeStyle/zTreeStyle.css"
	type="text/css" />
<link rel="stylesheet" type="text/css" media="screen"
	href="${ctx}/resources/pt/css/form.css" />
<script type="text/javascript"
	src="${ctx}/resources/ztree/js/jquery.ztree.js"></script>

<style type="text/css">
body,table,input,select {
	font-size: 12px
}

#fs3 legend {
    margin-bottom: 0;
}
.ui-button { font-weight: normal; }
.ui-jqgrid .ui-icon-asc { margin-left: 1px; }
#uploadFile {
	width: 185px;
}

</style>
</head>

<body style="font-size: 12px;">
<input type="hidden" id="flag" name="flag" value="${flag}">
<div class="div_body">
	<div class="form_header">
		<!--标题-->
		<div class="form_title">
			<h2 id="title_h2"></h2>
			<div class="form_close" title="关闭"></div>
		</div>
		<div id="toolbar" class="form_toolbar">
			<div class="form_operate">
				<button id="btn_form_save" name="btn_form_save">保存</button>
				<button id="btn_form_save_new" name="btn_form_save_new">保存新版本</button>
				
			</div>
		</div>
	</div>
	<div class="form_content">
		<div class="tabbable" >
			<ul class="nav nav-tabs">
				<li class="active"><a href="#tab1" data-toggle="tab">基本属性</a></li>
				<li><a href="#tab2" data-toggle="tab">表单设计</a></li>
				<c:if test="${flag == 1}" >
				<li><a href="#tab3" data-toggle="tab">字段设置</a></li>
				</c:if>
				<c:if test="${flag == 2}" >
				<li><a href="#tab3" data-toggle="tab">预览</a></li>
				</c:if>
				<li><a href="#tab4" data-toggle="tab">帮助</a></li>
			</ul>
			<div class="tab-content">
				<div class="tab-pane active" id="tab1" style="height:250px;">
					<div class="dytable_form">
						<div class="post-sign">
							<div class="post-detail">
							<form id="mainForm" name="mainForm" style="margin: 0px;">
								<input type="hidden" name="formUuid" id="formUuid" value="${uuid}" />
								<input type="hidden" name="version" id="version" value="" /> 
								<input type="hidden" name="isUp" id="isUp" value="" /> 
								<input type="hidden" name="htmlPath" id="htmlPath" value="" /> 
								<input type="hidden" name="tempHtmlPath" id="tempHtmlPath" value="" />
						<table>
							<tr class="odd">
								<td class="Label" style="width:20%">名称</td>
								<td class="value" style="width:30%"><input type="text" name="mainTableCnName"
									id="mainTableCnName" value="" /></td>
								<td class="Label" style="width:20%">所属模块</td>
								<td class="value" style="width:30%"><input type="text" name="moduleName"
									id="moduleName" value="" />
									<input type="hidden" class="w100"
						name="moduleId" id="moduleId" value="" />
								</td>
							</tr>
							<tr>
								<td class="Label" style="width:20%">ID</td>
								<td class="value" style="width:30%"><input type="text" name="tableId"
									id="tableId" value="" /></td>
								<td class="Label" style="width:20%">应用于</td>
								<td class="value" style="width:30%"><input type="text" class="w100" name="applyToName2"
									id="applyToName2" value="" />
									<input type="hidden" class="w100"
						name="applyTo2" id="applyTo2" value="" /></td>
									
<!-- 								<td width="15%" class="label">选择模板</td> -->
<!-- 								<td width="35%"><input type="file" name="uploadFile" -->
<!-- 									id="uploadFile" /> <a class="easyui-linkbutton" -->
<!-- 									iconCls="icon-ok" href="javascript:void(0)" id="uploadBtn" -->
<!-- 									data-options="plain:true">上传</a></td> -->
							</tr>
							<tr class="odd">
								<td class="Label" style="width:20%">数据库表名</td>
								<td class="value" style="width:30%"><input type="text" name="mainTableEnName"
									id="mainTableEnName" value="" /></td>
								<td class="Label" style="width:20%">打印模板</td>
								<td class="value" style="width:30%"><input type="text" class="w100" name="getPrintTemplateName"
									id="getPrintTemplateName" value="" />
									<input type="hidden" class="w100"
						name="getPrintTemplateId" id="getPrintTemplateId" value="" /></td>
							</tr>
							<tr>
								<td class="Label" style="width:20%">编号</td>
								<td class="value" style="width:30%"><input type="text" name="tableNum"
									id="tableNum" value="" /></td>
								<c:if test="${flag == 1}" >
								<td class="Label" style="width:20%">显示单据模板</td>
								<td class="value" style="width:30%"><input type="text" name="showTableModel"
									id="showTableModel" value="" />
									<input type="hidden" name="showTableModelId"
									id="showTableModelId" value="" />
								</td>
								</c:if>
								<c:if test="${flag == 2}" >
								<td class="Label" style="width:20%">编辑单据模板</td>
								<td class="value" style="width:30%"><input type="text" name="showTableModel"
									id="showTableModel" value="" />
									<input type="hidden" name="showTableModelId"
									id="showTableModelId" value="" />
								</td>
								</c:if>
								<tr class="odd">
									<td class="Label" style="width:20%">是否启用签名</td>
									<td class="value" style="width:30%">
										<span id="formSign">
											<input type='radio' name="formSign" id='formSign_1' value='1' style='margin-top: 11px;' checked='checked'></input><label for='formSign_1' style='margin-top: 8px;'>不启用</label>
											<input type='radio' name="formSign" id='formSign_2' value='2' style='margin-top: 11px;'></input><label for='formSign_2' style='margin-top: 8px;'>启用</label>
											<input type='hidden' id='isGetZhengWen' />
										</span>
									</td>
								</tr>
									<input type="hidden" name="formDisplay"
									id="formDisplay" value="" />
<!-- 									<input type="radio" name="formDisplay" id="formDisplay" value="1"><label for="formDisplay_1">显示表单定义</label></input> -->
<!-- 									<input type="radio" name="formDisplay" id="formDisplay" value="2"><label for="formDisplay_2">编辑表单定义</label></input> -->
<!-- 									<select name="formDisplay" id="formDisplay"> -->
<!-- 										<option value="1">显示表单定义</option> -->
<!-- 										<option value="2">编辑表单定义</option> -->
<!-- 									</select> -->
							</tr>
<!-- 							<tr class="odd"> -->
<!-- 								<td class="Label" style="width:20%">关联表单</td> -->
<!-- 								<td class="value" style="width:30%"> -->
<!-- 									<input type="text" name="relationFormNames" id="relationFormNames" /> -->
<!-- 									<input type="text" name="relationFormIds" id="relationFormIds" style="display: none;"/> -->
<!-- 								</td> -->
<!-- 								<td class="Label" style="width:20%">是否展示详细</td> -->
<!-- 								<td class="value" style="width:30%"> -->
<!-- 									<input type="radio" name="showDetail" class="showDetail" id="showDetail_true" value=true/>是 -->
<!-- 									<input type="radio" name="showDetail" class="showDetail" id="showDetail_false" value=false checked="checked"/>否 -->
<!-- 								</td> -->
<!-- 							</tr> -->
						</table>
					</form>
							</div>
						</div>
					</div>		
				</div>
				<div class="tab-pane" id="tab2">
					<div class="dytable_form">
						<div class="post-sign">
							<div class="post-detail">
					<table>
					<tr class="title">
						<td class="Label" colspan="4">上传本地html模版</td>
					</tr>
					<tr class="odd">
						<td class="Label">选择模板</td>
						<td><input type="file" name="uploadFile"
									id="uploadFile" style="float: none"/> <a class="easyui-linkbutton"
									iconCls="icon-ok" href="javascript:void(0)" id="uploadBtn"
									data-options="plain:true">上传</a></td>
						
					</tr>
					<tr>
					<td class="Label">样例模板</td>
						<td><a style="cursor: pointer;" id="downTemp">下载</a></td>
					</tr>
					<tr class="title">
						<td class="Label" colspan="4">Web编辑</td>
					</tr>
					<tr>
						
					</tr>
					</table>
					<fieldset id="fs3" >
						<legend></legend>
						<textarea id="moduleText"  name="moduleText"></textarea>
					</fieldset>
							</div>
						</div>
					</div>
				</div>
				<div class="tab-pane" id="tab3">
					<fieldset id="fs2" display: none;>
						<div id="moduleDiv"></div>
					</fieldset>
				</div>
				<div class="tab-pane" id="tab4" style="height:250px;">
					<div style="margin-left: 50px;">&nbsp;&nbsp;本地表单模版编辑使用说明：<br>
					1、用户可以先在界面布局上传本地html模板中下载一份样例模板；<br>
					2、参考模板中的表单进行表单设计；<br>
				    3、设置表单域时，遵循标题+输入域的输入方式；
					</div>
					<div style="margin-left: 50px;"> &nbsp;&nbsp; Web表单编辑使用说明:<br>
					1、界面布局提供了web编辑方式，用户可以在编辑器中编辑自己需要的表单；<br>
					2、使用编辑器的工具栏直接插入表单、复选框、单选框、单行文本、多行文本、按钮等表单元素，编辑器也支持源码插入表单；<br>
					3、可以使用编辑器插入从表；<br>
					4、编辑完成表单后，点击编辑器工具栏上的预览按钮，就可以到字段设置页面进行字段设置；<br>
					</div>
					<div style="margin-left: 50px;">&nbsp;&nbsp;备注:如果一个表单域需要设置成附件格式，则该域的id必须设置成fileupload</div>
				</div>
			</div>
		</div>
	</div>


	<div id="attrCfgDiv" title='属性设置'>
		<div>
			<input type="hidden" name="formEleType" id="formEleType" value="" />
			<input type="hidden" name="formEleId" id="formEleId" value="" />
			<div class="dytable_form">
				<div class="post-sign">
					<div class="post-detail">
			<table>
				<!--1 -->
				<tr class="odd">
					<td class="Label">应用于</td>
					<td class="value"><input type="text" class="w100" name="applyToName"
						id="applyToName" value="" /><input type="hidden" class="w100"
						name="applyTo" id="applyTo" value="" /></td>
				</tr>
				<!--2 -->
				<tr>
					<td class="Label">字段名</td>
					<td class="value"><input type="text"
						name="colEnName" id="colEnName" value="" /> 
<!-- 						<span style="color: #bbb">(注:该字段只能在画表单时指定,此处不能修改)</span></td> -->
				</tr>
				<!--3 -->
				<tr class="odd">
					<td class="Label">标题</td>
					<td class="value"><input type="text" class="w100" name="colCnName"
						id="colCnName" value="" /></td>
				</tr>
				<!--4字体大小及颜色-->
				<tr>
					<td class="Label">样式</td>
					<td class="value">
					<div class="tr_td_div_left">
						<select name="fontSize" id="fontSize" style="display: block;float: left;width: 112px;">
							<option value="12" style="font-size:12px;">12</option>
							<option value="8" style="font-size:8px;">8</option>
							<option value="9" style="font-size:9px;">9</option>
							<option value="10" style="font-size:10px;">10</option>
							<option value="11" style="font-size:11px;">11</option>
							<option value="14" style="font-size:14px;">14</option>
							<option value="16" style="font-size:16px;">16</option>
							<option value="18" style="font-size:18px;">18</option>
							<option value="20" style="font-size:20px;">20</option>
							<option value="22" style="font-size:22px;">22</option>
							<option value="24" style="font-size:24px;">24</option>
							<option value="26" style="font-size:26px;">26</option>
							<option value="28" style="font-size:28px;">28</option>
							<option value="36" style="font-size:36px;">36</option>
							<option value="48" style="font-size:48px;">48</option>
							<option value="72" style="font-size:72px;">72</option>
						</select>
					</div>
					<div class="tr_td_div_left">
						<span id="fontcolor" class="fontColorCss" ></span>
						<input type="hidden" value="" id="fontColor">
						<div class="subcolorCss" id="subcolor" >
						<div style="display: none;" class="colors">
							<span onclick="selColor('black');" style="background-color:black;" class="selectcolor"></span>
							<span onclick="selColor('red');" style="background-color:red " class="selectcolor"></span>
							<span onclick="selColor('blue');" style="background-color:blue " class="selectcolor"></span>
							<span onclick="selColor('orange');" style="background-color:orange " class="selectcolor"></span>
							<span onclick="selColor('green');" style="background-color:green " class="selectcolor"></span>
							<span onclick="selColor('yellow');" style="background-color:yellow " class="selectcolor"></span>
							<span onclick="selColor('purple');" style="background-color:purple " class="selectcolor"></span>
							<span onclick="selColor('silver');" style="background-color:silver " class="selectcolor"></span>
							<span onclick="selColor('tan');" style="background-color:tan " class="selectcolor"></span>
							<span onclick="selColor('maroon');" style="background-color:maroon;" class="selectcolor"></span>
							<span onclick="selColor('olive');" style="background-color:olive;" class="selectcolor"></span>
							<span onclick="selColor('gray');" style="background-color:gray;" class="selectcolor"></span>
							<span onclick="selColor('lime');" style="background-color:lime;" class="selectcolor"></span>
						</div>
						</div>
					</div>
					<div class="tr_td_div_left">
						<span style="float: left;padding-top: 9px;margin-right: 3px;">宽度:</span><input type="text" name="fontWidth" id="fontWidth" style="width: 25px;float: left;"/>
					</div>
					<div class="tr_td_div_left">
						<span style="float: left;padding-top: 9px;margin-right: 3px;">高度:</span><input type="text" name="fontHight" id="fontHight" style="width: 25px;float: left;"/>
					</div>
					<div class="tr_td_div_left">
						<select name="textAlign" id="textAlign"  style="width: 80px;">
							<option value="">请选择</option>
							<option value="center">居中</option>
							<option value="left">靠左</option>
							<option value="right">靠右</option>
						</select>
					</div>
					<div class="tr_td_div_left" style="padding-top: 9px;">
						<input type="checkbox" value="2" name="isHide" id="isHide" /><label for="isHide">隐藏</label>
					</div>
					<div class="tr_td_div_left" style="padding-top: 9px;">
						<input type="checkbox" value="2" name="dataShow" id="dataShow" /><label for="dataShow">隐藏输入框(直接显示文本)</label>
					</div>
					</td>
				</tr>
<!-- 				<tr class="odd"> -->
<!-- 					<td class="Label">字段展示</td> -->
<!-- 					<td class="value"> -->
<!-- 						<input type="radio" name="dataShow" id="dataShow_1" value="1"></input><label for="dataShow_1">直接展示</label> -->
<!-- 						<input type="radio" name="dataShow" id="dataShow_2" value="2"></input><label for="dataShow_2">隐藏input框</label> -->
<!-- 					</td> -->
<!-- 				</tr> -->
				<!--5 --><!--6 -->
				<tr class="odd">
					<td class="Label">字段类型</td>
					<td class="value"><select name="dataType" id="dataType"
						style="width: 33%;float: left;margin-right: 5px;padding: 4px;">
							<option value="1">字符串</option>
							<option value="16">&nbsp;&nbsp;大字段类型</option>
							<option disabled=true>日期时间</option>
							<option value="2">&nbsp;&nbsp;日期</option>
							<option value="7">&nbsp;&nbsp;日期时间(到分)</option>
							<option value="8">&nbsp;&nbsp;日期时间(到秒)</option>
							<option value="10">&nbsp;&nbsp;时间(到分)</option>
							<option value="11">&nbsp;&nbsp;时间(到秒)</option>
							<option disabled=true>数值</option>
							<option value="13">&nbsp;&nbsp;整数</option>
							<option value="14">&nbsp;&nbsp;长整数</option>
							<option value="15">&nbsp;&nbsp;浮点数</option>
						</select>
						<span id="floatSetSpan" style="display: none;">
							<span>计算结果保留<input type="text" value="0" id="floatSet" name="floatSet" style="width:20px; float: none;">位小数</span>
						</span>
					</td>
				</tr>
				<!--7 字段默认值-->
				<tr  >
					<td class="Label">字段值</td>
					<td class="value">
					<input type="radio" value="1" name="defaultValueWay" class="defaultValueWay" id="defaultValueWay1" checked=true/><label for="defaultValueWay1">用户输入</label>
					<input type="radio" value="2" name="defaultValueWay" class="defaultValueWay" id="defaultValueWay2"/><label for="defaultValueWay2">JS公式</label>
					<input type="radio" value="3" name="defaultValueWay" class="defaultValueWay" id="defaultValueWay3"/><label for="defaultValueWay3">创建时计算</label>
					<input type="radio" value="4" name="defaultValueWay" class="defaultValueWay" id="defaultValueWay4"/><label for="defaultValueWay4">显示时计算</label>
					<input type="radio" value=5 name="defaultValueWay" class="defaultValueWay" id="defaultValueWay5"/><label for="defaultValueWay5">二维码</label>
					<input type="radio" value="6" name="defaultValueWay" class="defaultValueWay" id="defaultValueWay6"/><label for="defaultValueWay6">条形码</label>
					<!-- 关联文档的配合未加入 -->
					<input type="radio" value="7" name="defaultValueWay" class="defaultValueWay" id="defaultValueWay7"/><label for="defaultValueWay7">关联文档</label>
					<div style="position: relative;">
					<input type="text" style="" class="w100" name="defaultValue" id="defaultValue"/>
					<div class='relationClass' style="display: none;">
<!-- 						数据范围： -->
<!-- 						<input type="radio" value="1" name="relationDataType" class="relationDataType" id="relationDataType1" checked="checked"><label for="relationDataType1">实体类表</label> -->
<!-- 						<input type="radio" value="4" name="relationDataType" class="relationDataType" id="relationDataType2"><label for="relationDataType2">动态表单</label></br> -->
<!-- 						<input type="radio" value="3" name="relationDataType" class="relationDataType" id="relationDataType3"><label for="relationDataType3">模块数据</label></br> -->
						视图设置：
						<input style="width: 84%;" id="relationDataText" type="text" name="relationDataText"></br>
						<input id="relationDataValue" type="hidden" name="relationDataValue">
						约束条件：
						<input style="width: 84%;" id="relationDataSql" type="text" name="relationDataSql"><br/>
<!-- 						展示格式： -->
<!-- 						<input id="relationDataShowType1" type="radio" name="relationDataShowType" value="table"  checked="checked"><label for="relationDataShowType1">表格</label> -->
<!-- 						<input id="relationDataShowType2" type="radio" name="relationDataShowType" value="list"><label for="relationDataShowType2">列表</label> -->
					</div>
					<div class="hongdy">
						<ul class="hongdy_ul">
							<li class="noChose">日期时间</li>
								<li class="isChose">当前日期(2000-01-01)</li>
								<li class="isChose">当前日期(2000年1月1日)</li>
								<li class="isChose">当前日期(2000年)</li>
								<li class="isChose">当前日期(2000年1月)</li>
								<li class="isChose">当前日期(1月1日)</li>
								<li class="isChose">当前日期(星期一)</li>
								<li class="isChose">当前年份(2000)</li>
								<li class="isChose">当前时间(12:00)</li>
								<li class="isChose">当前时间(12:00:00)</li>
								<li class="isChose">当前日期时间(2000-01-01 12:00)</li>
								<li class="isChose">当前日期时间(2000-01-01 12:00:00)</li>
							<li  class="noChose">当前用户</li>
								<li class="isChose">当前用户ID</li>
								<li class="isChose">当前用户姓名</li>
								<li class="isChose">当前用户部门(长名称)</li>
								<li class="isChose">当前用户部门(短名称)</li>
							<li  class="noChose">创建人</li>
								<li class="isChose">创建人ID</li>
								<li class="isChose">创建人姓名</li>
								<li class="isChose">创建人部门(长名称)</li>
								<li class="isChose">创建人部门(短名称)</li>
							<li  class="noChose">流水号</li>
							<li  class="noChose">来自SQL语句查询</li>
						</ul>
					</div>
					</div>
					</td>
				</tr>
				<!--8 -->
				<tr id="dataTypeDetailTr" style="display: none;">
					<td class="Label" id="dataTypeDetailLabel"></td>
					<td class="value" id="dataTypeDetailOption"></td>
				</tr>
				<tr id="optionDataSourceTr" style="display: none;" class="odd">
					<td class="Label">备选项来源</td>
					<td class="value" id="optionDataSourceTd"><input type="radio"
						name="optionDataSource" id="optionDataSource_1" value="1" /><label
						for="optionDataSource_1">常量</label> <input type="radio"
						name="optionDataSource" id="optionDataSource_2" value="2" /><label
						for="optionDataSource_2">取字典</label></td>
				</tr>
				<tr id="optionsTr" style="display: none;">
					<td id="optionsTd_1" class="Label">备选项</td>
					<td class="value" id="optionsTd_2"></td>
				</tr>
				<tr id="ctrlFieldTr" style="display:none;">
					<td class="Label">控制显示\隐藏字段</td>
					<td class="value">
						<input type="text" id="ctrlFieldName" value="" name="ctrlFieldName" />
						<input type="hidden" id="ctrlField"  value="" name="ctrlField" />					
					</td>
				</tr>
				<tr>
					<td class="Label">只读状态下设置跳转url</td>
					<td class="value"><input type="text" id="setUrlOnlyRead" name="setUrlOnlyRead" /></td>
				</tr>
				<tr class="odd" id="inputDataTypetr">
					<td class="Label">输入样式</td>
					<td class="value">
						<select name="inputDataType" id="inputDataType" style="width: 33%;float: left;margin-right: 5px;padding: 4px;">
							<option value="1">普通表单输入</option>
							<option value="2">富文本编辑</option>
							<option value="16">树形下拉框</option>
							<option value="26">弹出框</option>
							<option value="27">XML</option>
							<option value="3" disabled=true>附件</option>
							<option value="4">&nbsp;&nbsp;图标显示</option>
							<option value="5">&nbsp;&nbsp;图标显示(含正文)</option>
							<option value="6">&nbsp;&nbsp;列表显示(不含正文)</option>
							<option value="7">可编辑流水号</option>
							<option value="29">不可编辑流水号</option>
							<option value="8" disabled=true>组织选择</option>
							<option value="9">&nbsp;&nbsp;人员</option>
							<option value="10">&nbsp;&nbsp;部门</option>
							<option value="11">&nbsp;&nbsp;部门+人员</option>
							<option value="28">&nbsp;&nbsp;单位通讯录</option>
							<option value="12" disabled=true>资源选择</option>
							<option value="13">&nbsp;&nbsp;会议室</option>
							<option value="14">&nbsp;&nbsp;车辆</option>
							<option value="15">&nbsp;&nbsp;司机</option>
						</select>
						<div id="inputDataTypeValue" style="display: none;clear: both;">
							<input type="text"  id="relationDataShowType" style="width: 71%;" name="relationDataShowType"/>
							<span id="inputDataTypeValueInfo" style="line-height: 35px;"></span>
						</div>
<!-- 						<div id="fileSign" style="display: none;margin-top: 7px;"> -->
<!-- 							<span style="display: block;float: left;margin-top: 2px;">是否启用附件签名</span> -->
<!-- 							<input type="radio" value="1" id="useFileSign1" name="useFileSign" checked="checked"><label for="useFileSign1">启用</label> -->
<!-- 							<input type="radio" value="2" id="useFileSign2" name="useFileSign" checked="checked"><label for="useFileSign2">不启用</label> -->
<!-- 						</div> -->
						
					</td>
				</tr>
				<tr  id="definitiontr">
					<td class="Label">关联数据选择</td>
					<td class="value">
						<div class="definitionDiv">
<!-- 							<div> -->
<!-- 							数据范围：<input type="radio" value="2" name="relationDataTypeTwo" class="relationDataTypeTwo" id="relationDataTypeTwo1"  checked="checked"><label for="relationDataTypeTwo1">实体类表</label> -->
<!-- 									 <input type="radio" value="4" name="relationDataTypeTwo" class="relationDataTypeTwo" id="relationDataTypeTwo2"><label for="relationDataTypeTwo2">动态表单</label> -->
<!-- 									 <input type="radio" value="3" name="relationDataTypeTwo" class="relationDataTypeTwo" id="relationDataTypeTwo3"><label for="relationDataTypeTwo3">模块数据</label> -->
<!-- 							</div> -->
							<div>
							视图设置：<input style="width: 41%;" id="relationDataTextTwo" type="text" name="relationDataTextTwo">
									 <input id="relationDataValueTwo" type="hidden" name="relationDataValueTwo">
<!-- 									 <input id="relationDataUuid" type="hidden" name="relationDataUuid"> -->
							</div>
							<div>
								展示方式：
								<input id="relationDataShowMethod1" type="radio" name="relationDataShowMethod" value="direct"><label for="relationDataShowMethod1">直接展示</label>
								<input id="relationDataShowMethod2" type="radio" name="relationDataShowMethod" value="indirect"><label for="relationDataShowMethod2">视图展示</label>
							</div>
							<div>
							约束条件：
									 <input style="width: 41%;" id="relationDataTwoSql" type="text" name="relationDataTwoSql">
							</div>
							<div>
							添加映射关系：<select style="width: 20%;" id="sqlField" name="sqlField"></select>(数据库字段)--
										 <select style="width: 20%;" id="formField" name="formField"></select>(字段名称)
										 <input type="checkbox" id="searchCheck" value="yes"/>查 
										 <button class="addRelationField">添加</button>
							</div>
						</div>
						<input type="text" name="relationDataDefiantion" id="relationDataDefiantion" style="display: none;"/>
						<div class="definitionDiv" style="margin-bottom: 5px;">
							<table class="definitiontrtable">
								<tr class="definitiontitle" style="background:#F7F7F7;">
									<td>字段标题</td>
									<td>字段名</td>
									<td>表单字段</td>
									<td style="display: none;">字段名</td>
									<td>是否作为查询字段</td>
									<td>操作</td>
								</tr>
<!--								<tr class="definitioncontentiteam"> -->
<!-- 									<td>字段标题</td> -->
<!-- 									<td>字段名</td> -->
<!-- 									<td>表单字段</td> -->
<!-- 									<td>是否作为查询字段</td> -->
<!-- 								</tr> -->
							</table>
						</div>
					</td>
				</tr>
				<tr class="odd" >
					<td class="Label">校验</td>
					<td class="value"><input type="checkbox" name="checkRule" id="checkRule_1"
						value="1" /><label for="checkRule_1">不允许为空</label> <input
						type="checkbox" name="checkRule" id="checkRule_2" value="2" /><label
						for="checkRule_2">必须是URL</label> <input type="checkbox"
						name="checkRule" id="checkRule_3" value="3" /><label
						for="checkRule_3">必须是邮箱地址</label> <input type="checkbox"
						name="checkRule" id="checkRule_4" value="4" /><label
						for="checkRule_4">必须是身份证</label> <input type="checkbox"
						name="checkRule" id="checkRule_5" value="5"/><label for="checkRule_5">要求唯一</label></td>
				</tr>
				<tr id="uniqueCheckRuleTr" style="display: none;">
					<td class="Label">校验规则</td>
					<td class="value"><textarea id='uniqueCheckRule' rows="2" cols="50"></textarea>
					</td>
				</tr>
<!-- 				<tr> -->
<!-- 					<td class="Label">JS函数定义</td> -->
<!-- 					<td class="value"> -->
<!-- 						<textarea name="jsFunction" id="jsFunction" rows="2" cols="100"></textarea> -->
<!-- 					</td> -->
<!-- 				</tr> -->
			</table>
			
		</div>
		</div>
			</div>
			</div>
	</div>

	<div id="tableInfoCfgDiv" title='表信息设置'>
			<div class="dytable_form">
				<div class="post-sign">
					<div class="post-detail">
			<table>
				<tr class="odd">
					<td class="Label">从表:</td>
					<td class="value"><input type="hidden" name="tableDefinitionId"
						id="tableDefinitionId" /> <input id="tableDefinitionText"
						type="text" readonly="readonly" value=""/>
					</td>
				</tr>
				<tr>
					<td class="Label">编辑模式:</td>
					<td class="value"><input name="editType" type="radio" class="w100"
						id="editType_1" value="1" checked="checked" />单元格 <input
						name="editType" type="radio" class="w100" id="editType_2"
						value="2" />新窗口</td>
				</tr>
				<tr>
					<td class="Label">显示操作按钮:</td>
					<td class="value"><input name="hideButtons" type="radio" class="w100"
						id="hideButtons_1" value="1" checked="checked" style="float:none;margin-top: -4px;"/>是 <input
						name="hideButtons" type="radio" class="w100" id="hideButtons_2"
						value="2"  style="float:none;margin-top: -4px;"/>否
					</td>
				</tr>
				<tr class="odd">
					<td class="Label">从表显示名称:</td>
					<td class="value"><input type="text" id="tableTitle" name="tableTitle"/></td>
				</tr>
				<tr>
					<td class="Label">表单是否默认展开:</td>
					<td class="value">
						<input name="tableOpen" type="radio" class="w100"
						id="tableOpen_1" value="1" checked="checked" style="float:none;margin-top: -4px;"/>是 <input
						name="tableOpen" type="radio" class="w100" id="tableOpen_2"
						value="2"  style="float:none;margin-top: -4px;"/>否
					</td>
				</tr>
				<tr>
					<td class="Label">从表数据分组展示:</td>
					<td class="value">
						<input name="isGroupShowTitle" type="radio" class="w100"
						id="isGroupShowTitle_1" value="1" checked="checked" style="float:none;margin-top: -4px;"/>是 <input
						name="isGroupShowTitle" type="radio" class="w100" id="isGroupShowTitle_2"
						value="2"  style="float:none;margin-top: -4px;"/>否
					</td>
				</tr>
				<tr>
					<td class="Label">分组展示的标题:</td>
					<td>
						<select style="width: 27%;" id="groupShowTitle" name="groupShowTitle"></select>
					</td>
				</tr>
				<tr>
					<td class="Label">从表映射关系:</td>
					<td class="value">
					<div id="subformApply">
						<div>
						<input type="hidden" id="subformApplyTableId" />
						从表数据来源:
						<input id="subformApplyTableIdText" type="text" readonly="readonly" />
						</div>
						<div>
						添加映射关系:
						<select style="width: 27%;" id="subformApplyCol" name="subformApplyCol"></select>-->
						PARENT_ID
						<button class="addSubRelationField">添加</button>
						</div>						
					</div>
					</td>
				</tr>
				<tr>
					<td></td>
					<td>
						<input type="text" name="subrRelationDataDefiantion" id="subrRelationDataDefiantion" style="display: none;"/>
						<div class="definitionDiv" style="margin-bottom: 5px;">
							<table class="subDefinitiontrtable">
								<tr class="definitiontitle" style="background:#F7F7F7;">
									<td width="20%" >主表字段标题</td>
									<td width="20%" >主表字段名</td>
									<td width="20%" >来源表字段名</td>
									<td width="15%" >操作</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
			</table>
			<table id="fieldCfgTable" >
				<tr class="title" >
					<td>标题</td>
					<td>对应字段</td>
					<td>从表数据来源</td>
					<td>字段是否隐藏</td>
					<td>字段是否可编辑</td>
					<td>字段宽度</td>
				</tr>
			</table>
				</div>
			</div>
		</div>
	</div>

	<div id="menuContent" class="menuContent"
		style="display: none; position: absolute; z-index: 9999; background-color: #fff; overflow: scroll; border: 1px solid #c5dbec;">
		<ul id="tableTree" class="ztree"
			style="margin-top: 0; height: 300px;"></ul>
	</div>
	<div id="menuContent2" class="menuContent"
		style="display: none; position: absolute; z-index: 9999; background-color: #fff; overflow: scroll; border: 1px solid #c5dbec;">
		<ul id="tableTree2" class="ztree"
			style="margin-top: 0; height: 300px;"></ul>
	</div>
	<!-- <textarea rows="10" cols="60" id="showJSON"></textarea> -->
	
	<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/common/jquery.comboTree.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/ckeditor4.1/ckeditor.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/common/jquery.alerts.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/dytable/dytable_definition2.js"></script>
	
	<script type="text/javascript">
	function autoWith(){
		var div_body_width = $(window).width() * 0.76;
		$(".form_header").css("width", div_body_width-5);
	 	$(".div_body").css("width", div_body_width);
		
	}
	autoWith();
	$(window).resize(function(e) {
		// 调整自适应表单宽度
		autoWith();
	});
	$("#downTemp").click(function() {
		location.href='${ctx}/dytable/downloadTemp.action';
	});
	var validator = $("#mainForm").validate({
		rules : {
			tableId : {
				required : true,
				remote : {
					url : "${ctx}/common/validate/check/exists",
					type : "POST",
					data : {
					uuid:function() {
						return $('#formUuid').val();
					},
					checkType : "formDefinition",
					fieldName : "id",
					fieldValue : function() {
									return $('#tableId').val();
								}
					   }
					}
			},
			mainTableCnName : {
				required : true,
				remote : {
				url : "${ctx}/common/validate/check/exists",
				type : "POST",
				data : {
				uuid:function() {
					return $('#formUuid').val();
				},
				checkType : "formDefinition",
				fieldName : "descname",
				fieldValue : function() {
								return $('#mainTableCnName').val();
							}
				   }
				}
			},
			mainTableEnName : {
				required : true,
				remote : {
				url : "${ctx}/common/validate/check/exists",
				type : "POST",
				data : {
				uuid:function() {
					return $('#formUuid').val();
				},
				checkType : "formDefinition",
				fieldName : "name",
				fieldValue : function() {
								return $('#mainTableEnName').val();
							}
				   }
				}
			},
		},
		messages : {
			tableId:{
				required : "ID不能为空！",
					remote : "该ID已存在!"
			},
			mainTableCnName : {
				required : "表单名称不能为空!",
				remote : "该表单名称已存在!"
			},
			mainTableEnName : {
				required : "数据库名称不能为空!",
				remote : "该数据库名称已存在!"
			}
		}
	});
		$(".btn").button();
		var setting = {
			async : {
				otherParam : {
					"serviceName" : "dataDictionaryService",
					"methodName" : "getFromTypeAsTreeAsync",
					"data" : "DY_FORM_FIELD_MAPPING"
				}
			}
		};
		$("#applyToName").comboTree({
			labelField : "applyToName",
			valueField : "applyTo",
			width:200,
			height:300,
			treeSetting : setting
		});
		$("#attrCfgDiv").dialog({
			autoOpen: false,
			height: 750,
			width: 970,
			modal: true
		});
		$("#tableInfoCfgDiv").dialog({
			autoOpen: false,
			height: 600,
			width: 850,
			modal: true
		});
		
		
		var setting2 = {
				async : {
					otherParam : {
						"serviceName" : "dataDictionaryService",
						"methodName" : "getFromTypeAsTreeAsync",
						"data" : "DY_FORM_ID_MAPPING"
					}
				}
			};
			$("#applyToName2").comboTree({
				labelField : "applyToName2",
				valueField : "applyTo2",
				width:200,
				height:250,
				treeSetting : setting2,
				initServiceParam:["DY_FORM_ID_MAPPING"]
			});
			
			var setting3 = {
					async : {
						otherParam : {
							"serviceName" : "dataDictionaryService",
							"methodName" : "getFromTypeAsTreeAsync",
							"data" : "MODULE_ID"
						}
					},
					check : {
						enable : false
					},
					callback : {
						onClick: treeNodeOnClick,
					}
			
				};
				$("#moduleName").comboTree({
					labelField : "moduleName",
					valueField : "moduleId",
					width:150,
					height:250,
					treeSetting : setting3,
					initServiceParam:["MODULE_ID"]
				});	
				
				function treeNodeOnClick(event, treeId, treeNode) {
					$("#moduleName").val(treeNode.name);
					$("#moduleId").val(treeNode.data);
					$("#moduleName").comboTree("hide");
				}
				
				var setting4 = {
						async : {
							otherParam : {
								"serviceName" : "formDefinitionService",
								"methodName" : "getPrintTemplates",
							}
						},
						callback : {
							onClick:treeNodeOnClick2,
							onCheck:treeNodeOnCheck2
						}
					};
					$("#getPrintTemplateName").comboTree({
						autoInitValue:false,
						labelField : "getPrintTemplateName",
						valueField : "getPrintTemplateId",
						treeSetting : setting4,
					});	
				
				function treeNodeOnClick2(event, treeId, treeNode) {
					$("#getPrintTemplateName").val(treeNode.name);
					$("#getPrintTemplateId").val(treeNode.id);
				}
				function treeNodeOnCheck2(event, treeId, treeNode) {
					// 设置值
					var zTree = $.fn.zTree.getZTreeObj(treeId);
					var checkNodes = zTree.getCheckedNodes(true);
					var path = "";
					var value = "";
					for ( var index = 0; index < checkNodes.length; index++) {
						var checkNode = checkNodes[index];
							if (path == "") {
								path = getAbsolutePath(checkNode);
							} else {
								path = path + ";" + getAbsolutePath(checkNode);
							}
							if (value == "") {
								value = checkNode.data.id;
							} else {
								value = value + ";" + checkNode.data.id;
							}
					}
					$("#getPrintTemplateName").val(path);
					
					$("#getPrintTemplateId").val(value);
				}
					var setting5 = {
							async : {
								otherParam : {
									"serviceName" : "formDefinitionService",
									"methodName" : "getShowTableModels",
									"data":1
								}
							},
							callback : {
								onClick:treeNodeOnClick5,
								onCheck:treeNodeOnCheck5
							}
						};
						$("#showTableModel").comboTree({
							autoInitValue:false,
							labelField : "showTableModel",
							valueField : "showTableModelId",
							treeSetting : setting5,
						});
			
				function treeNodeOnClick5(event, treeId, treeNode) {
					$("#showTableModel").val(treeNode.name);
					$("#showTableModelId").val(treeNode.id);
				}
				
				function treeNodeOnCheck5(event, treeId, treeNode) {
					// 设置值
					var zTree = $.fn.zTree.getZTreeObj(treeId);
					var checkNodes = zTree.getCheckedNodes(true);
					var path = "";
					var value = "";
					for ( var index = 0; index < checkNodes.length; index++) {
						var checkNode = checkNodes[index];
							if (path == "") {
								path = getAbsolutePath(checkNode);
							} else {
								path = path + ";" + getAbsolutePath(checkNode);
							}
							if (value == "") {
								value = checkNode.id;
							} else {
								value = value + ";" + checkNode.id;
							}
					}
					$("#showTableModel").val(path);
					$("#showTableModelId").val(value);
				}
				
				// 获取树结点的绝对路径
				function getAbsolutePath(treeNode) {
					var path = treeNode.name;
					var parentNode = treeNode.getParentNode();
					while (parentNode != null) {
						path = parentNode.name + "/" + path;
						parentNode = parentNode.getParentNode();
					}
					return path;
				}
				// 动态表单单据关闭
				$(".form_header .form_close").click(function(e){window.close();});
				$(".nav li").eq(1).click(function(){
					$(".cke_contents").css("height","700px");
				});
				
				$('#subcolor').live("click",function(){
					if($(".colors").css("display")=="block"){
						$(".colors").css("display","none");
					}else if($(".colors").css("display")=="none"){
						$(".colors").css("display","block");
					}
					
				});
				function selColor(color){
					 $("#scolor").val(color);
				     $("#subject").css("color",color);
				     $("#fontcolor").css("background-color",color);
				     $("#fontColor").val(color);
				}
				
				
				var ctrlFieldSetting = {
						async : {
							otherParam : {
								"serviceName" : "formDefinitionService",
								"methodName" : "getFieldByFormUuid",
								"data":$("#formUuid").val()
							}
						},
						callback : {
							onClick:treeNodeOnClickForctrlFieldSetting,
							onCheck:treeNodeOnCheckForctrlFieldSetting
						}
				};
					$("#ctrlFieldName").comboTree({
						labelField : "ctrlFieldName",
						valueField : "ctrlField",
						treeSetting : ctrlFieldSetting,
						width: 220,
						height: 220
					});
					
					function treeNodeOnClickForctrlFieldSetting(event, treeId, treeNode) {
						$("#ctrlFieldName").val(treeNode.name);
						$("#ctrlField").val(treeNode.id + ","+ treeNode.name);
					}
					
					function treeNodeOnCheckForctrlFieldSetting(event, treeId, treeNode) {
						// 设置值
						var zTree = $.fn.zTree.getZTreeObj(treeId);
						var checkNodes = zTree.getCheckedNodes(true);
						var path = "";
						var value = "";
						for ( var index = 0; index < checkNodes.length; index++) {
							var checkNode = checkNodes[index];
								if (path == "") {
									path = getAbsolutePath(checkNode);
								} else {
									path = path + ";" + getAbsolutePath(checkNode);
								}
								if (value == "") {
									value = checkNode.id;
								} else {
									value = value + ";" + checkNode.id;
								}
						}
						$("#ctrlFieldName").val(path);
						$("#ctrlField").val(value + "," + path);
					}
					
					// 获取树结点的绝对路径
					function getAbsolutePath(treeNode) {
						var path = treeNode.name;
						var parentNode = treeNode.getParentNode();
						while (parentNode != null) {
							path = parentNode.name + "/" + path;
							parentNode = parentNode.getParentNode();
						}
						return path;
					}
	</script>
	</div>
	<div class="body_foot"></div>
</body>
</html>
