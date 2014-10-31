<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<title>视图自定义</title>
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/jqueryui/css/base/jquery-ui.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/jqgrid/css/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/layout/jquery.layout.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/ztree/css/zTreeStyle/zTreeStyle.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/pt/css/custom-admin-style.css" />
<style type="text/css">
.fontColorCss {
	background-color: #000000;
    display: block;
    float: left;
    height: 11px;
    margin-left: 10px;
    margin-top: 12px;
    width: 11px;
}
.subcolorCss {
   background: url("${ctx}/resources/theme/images/v1_icon3.png") no-repeat scroll 0 -263px transparent;
    cursor: pointer;
    float: left;
    height: 20px;
    margin-left: 4px;
    margin-top: 8px;
    position: relative;
    width: 20px;
}

.colors {
    background: none repeat scroll 0 0 #FFFFFF;
    border: 1px solid #DDDDDD;
    position: absolute;
    top: 18px;
    width: 58px;
}

.selectcolor {
    display: block;
    float: left;
    height: 15px;
    margin: 2px;
    width: 15px;
}

.selectTable input{
	width:50%;
}
.selectTable select{
	width:50%;
}
</style>
</head>
<body>
		<div class="ui-layout-west">
			<div>
				<div class="btn-group btn-group-top">
					<div class="query-fields">
						<div style="float: left;margin-top: 3px;width: 85px;">
							<select id="select_query" name="select_query" onchange="selectQuery(this)" style="width:100%;"></select>
						</div>
						<input id="query_keyWord"/>
						<button id="btn_query" type="button" class="btn">查询</button>
					</div>
					<button id="btn_add" type="button" class="btn">新增</button>
					<button id="btn_delAll" type="button" class="btn">删除</button>
				</div>
				<table id="list"></table>
				<div id="pager"></div>
			</div>
		</div>
		<div class="ui-layout-center">
			<div>
				<form action="" id="column_form">
					<input type="hidden" id="uuid" name="uuid">
					<input type="hidden" id="createTime" name="createTime"> 
					<div class="tabs">
						<ul>
							<li><a href="#tabs-1">基本属性</a></li>
							<li><a href="#tabs-2">列定义</a></li>
							<li><a href="#tabs-4">查询定义</a></li>
							<li><a href="#tabs-5">按钮定义</a></li>
							<li><a href="#tabs-6">行样式定义</a></li>
						</ul>					
						<div id="tabs-1">
							<table style="width: 100%;">
								<tr>
									<td>
										视图名称
									</td>
									<td >
										<input type="text" id="viewName" name="viewName" style="width:100%">
									</td>
								</tr>
								<tr>
									<td>
										ID
									</td>
									<td>
										<input type="text" id="viewId" name="viewId" style="width:100%">
									</td>
								</tr>
								<tr>
									<td>
										编号
									</td>
									<td>
										<input type="text" id="code" name="code" style="width:100%">
									</td>
								</tr>
								<tr>
									<td>分类<input type="hidden" id="cateName" name="cateName" />
									</td>
									<td><select id="cateUuid" name="cateUuid" style="width:100%;"></select>
									</td>
								</tr>
								<tr>
									<td>
										选择数据源
									</td>
									<td>
									<input type="text" class="w100" name="tableDefinitionText"
							id="tableDefinitionText" value="" style="width:100%"/><input type="hidden" class="w100"
							name="tableDefinitionId" id="tableDefinitionId" value=""/>
							<input type="hidden" name="tableDefinitionName" id="tableDefinitionName" value=""/>
							<input type="hidden" name="formuuid" id="formuuid" value=""/>
									</td>
								</tr>
								<tr>
									<td>默认搜索条件</td>
									<td><input type="text" id="defaultCondition" name="defaultCondition" style="width:100%"/></td>
								</tr>
								<tr>
									<td>行数据点击事件</td>
									<td><input type="text" id="clickEvent" name="clickEvent" style="width:100%;"/></td>
								</tr>
								<tr>
									<td>行数据点击跳转的URL</td>
									<td><input type="text" id="url" name="url" style="width:100%;"/></td>
								</tr>
								<tr>
									<td class="align-top">引入的js</td>
									<td><textarea id="jsSrc" name="jsSrc" style="width:100%;"></textarea></td>
								</tr>
								<tr>
									<td>复选框</td>
									<td><input type="checkbox" id="showCheckBox" name="showCheckBox"/>显示,复选框值&nbsp;<input type="text" id="checkKey" name="checkKey" style="width: 170px"/>[注：不定义取UUID]</td>
								</tr>
								<tr>
									<td>已阅未阅</td>
									<td><input type="checkbox" id="isRead" name="isRead"/>开启,状态取值&nbsp;<input type="text" id="readKey" name="readKey" style="width: 170px"/>[注：不定义取UUID]</td>
								</tr>
								<tr>
									<td><label>分页</label></td>
									<td><input id="isPaging" name="isPaging" type="checkbox" value="" />开启,每页页数&nbsp;<input id="pageNum" name="pageNum" type="text" style="width: 170px"/></td>
								</tr>
	<!-- 							<tr><td>按钮显示</td><td><select id="lineType" name="lineType"><option value="第二行">第二行</option><option value="第一行">第一行</option><option value="头部">头部</option></select></td><td></td></tr> -->
								<tr>
									<td></td>
									<td><input type="checkbox" id="showTitle" name="showTitle"/>显示标题</td>
								</tr>
								<tr>
									<td>按钮</td>
									<td><input type="checkbox" id="buttonPlace" name="buttonPlace"/>头尾部显示</td>
								</tr>
								<tr>
									<td>数据导出模版</td>
									<td><input type="text" id="dataModuleName" name="dataModuleName" />
										<input type="hidden" id="dataModuleId" name="dataModuleId" />
									</td>
								</tr>
								<tr>
									<td>特殊列值计算</td>
									<td>
										<input type="checkbox" id="specialField" name="specialField"/>
									</td>
								</tr>
								<tr class="specialFieldTemp" style="display:none;">
									<td>计算调用的js方法</td>
									<td>调用方法：<input type="text" id="specialFieldMethod" name="specialFieldMethod"/></td>
								</tr>
								<tr class="specialFieldTemp" style="display:none;">
									<td></td>
									<td>请求参数：<input type="text" id="requestParamName" name="requestParamName"/>
												  <input type="hidden" id="requestParamId" name="requestParamId"/>	
									</td>
								</tr>
								<tr class="specialFieldTemp" style="display:none;">
									<td></td>
									<td>输出参数：<input type="text" id="responseParamName" name="responseParamName"/>
												  <input type="hidden" id="responseParamId" name="responseParamId"/>	
									</td>
								</tr>
							</table>
						</div>
						<div id = "tabs-2">
							<div class="btn-group">
									<button id="btn_add_column" type="button" class="btn">新增列</button>
									<button id="btn_del_column" type="button" class="btn">删除列</button>
									<button id="btn_up_column" type="button" class="btn">上移列</button>
									<button id="btn_down_column" type="button" class="btn">下移列</button>
							</div>
							<table id="column_list"></table>
							<div id="btn_pager"></div>
						</div>
						<div id = "tabs-4">
							<table>
<!-- 							<tr> -->
<!-- 									<td width="150px"><label>是否显示查询</label></td> -->
<!-- 									<td width="30px"><input class="full-width" id="selectShow" name="selectShow" type="checkbox" -->
<!-- 										value="" /></td> -->
<!-- 									<td></td> -->
<!-- 								</tr> -->
								<tr>
									<td width="150px"><label>按字段查询</label></td>
									<td width="30px"><input class="full-width" id="forFieldSelect" name="forFieldSelect" type="checkbox"
										/></td>
									<td></td>
								</tr>
							</table>
							<div id="selectFieldDiv"  style="display:none">
								<div class="btn-group">
									<button id="btn_add_field" type="button" class="btn">新增字段</button>
									<button id="btn_del_field" type="button" class="btn">删除字段</button>
								</div>	
								<table id="selectField"></table>
							</div>
							<table>	
								
								<tr>
									<td width="150px"><label>按条件查询</label></td>
									<td width="30px"><input class="full-width" id="forCondition" name="forCondition" type="checkbox"
										/></td>
									<td></td>
								</tr>
							</table>
							<div id="a"  style="display:none">
								<div class="btn-group">
									<button id="btn_add_cond" type="button" class="btn">新增条件</button>
									<button id="btn_del_cond" type="button" class="btn">删除条件</button>
								</div>	
								<table id="selectCond"></table>
							</div>
							<table id="select_list">
								<tr>	
									<td width="150px"><label>按关键字查询</label></td>
									<td width="30px"><input class="full-width" id="forKeySelect" name="forKeySelect" type="checkbox"
										value="" />
									</td>
									<td></td>
								</tr>
							</table>
							<div id="btn_pager"></div>
						</div>
						<div id = "tabs-5">
							<div class="btn-group">
								<button id="btn_add_btn" type="button" class="btn">新增</button>
								<button id="btn_del_btn" type="button" class="btn">删除</button>
							</div>
							<table border="1" id="customButton">
								<tr id="customButton_title">
									<td><input type="checkbox" class="customButton_allCheck"/></td>
									<td>按钮名称</td>
									<td>按钮位置</td>
									<td>按钮组别</td>
									<td>资源</td>
									<td>按钮事件</td>
								</tr>
<%-- 								<c:forEach var="resources" items="${resources}"> --%>
<!-- 								<tr id="customButton_content"> -->
<%-- 								<td><input id="${resources.code}_custom" type="checkbox"  class="button_check" value="${resources.code}" name="${resources.name}"/></td> --%>
<!-- 								<td> -->
<%-- 									${resources.name} --%>
<!-- 								</td> -->
<!-- 								<td> -->
<%-- 									<input type="checkbox" id="${resources.code}_place1" class="button_place" value="头部"/>头部&nbsp; --%>
<%-- 									<input type="checkbox" id="${resources.code}_place2" class="button_place" value="第一行"/>第一行&nbsp; --%>
<%-- 									<input type="checkbox" id="${resources.code}_place3" class="button_place" value="第二行"/>第二行 --%>
<!-- 								</td> -->
<!-- 								<td> -->
<%-- 									<select id="${resources.code}_buttonGroup" class="buttonGroup" style="width:70px;"></select> --%>
<!-- 								</td> -->
<!-- 								<td> -->
<%-- 									<textarea id="${resources.code}_jsContent" class="button_jscontent" rows="2" cols="20" style="width:160px;"></textarea> --%>
<!-- 								</td> -->
<!-- 								</tr>		 -->
<%-- 								</c:forEach> --%>
										
							</table>
						</div>
						<div id = "tabs-6">
							<div class="btn-group">
									<button id="newButton_css" type="button" class="btn">新增</button>
									<button id="delButton_css" type="button" class="btn">删除</button>
							</div>
							<table id="columnTableCss">
							</table>
						</div>
					</div>
					<div class="btn-group btn-group-bottom">
						<button id="btn_save" type="button" class="btn">保存</button>
						<button id="btn_del" type="button" class="btn">删除</button>
						<button id="btn_url" type="button" class="btn" onclick="var uuid = $('#uuid').val();if(uuid==''){alert('请先保存视图信息');}else{window.open('${ctx}/basicdata/view/view_show?viewUuid='+uuid+'&openBy=definition');}">预览</button>
					</div>
				</form>
					<div>
	<!-- 		<input id="current_form_url_prefix" type="hidden" -->
	<%-- 			value="${ctx}/basicdata/dyview/view_show"> <a --%>
	<!-- 			id="current_form_url" href="" target="_blank" style="display: none">查看当前视图</a> -->
					</div>
			</div>
		</div>
	
	<div id="columnCssSet" title="样式设置" style="display:none;">
		<table>
			<tr>
				<td><label>字体是否加粗:</label></td>
				<td>
					<input type="radio" name="fontWide" id="fontWide_1" value="1"></input><label for="fontWide_1">是</label>
					<input type="radio" name="fontWide" id="fontWide_2" value="2"></input><label for="fontWide_2">否</label>
				</td>
			</tr>
			<tr>
				<td><label>字体颜色:</label></td>
				<td>
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
				</td>
			</tr>
			
		</table>
	</div>
	
	<div style="display: none">
		<div id="select_fieldtype_choose">
		<table class="selectTable">
		    <tr>
				<td style="width: 25%;">查询类型:</td>
				<td style="width: 75%;">
					<select id="selectType" name="selectType">
						<option value="null">--请选择--</option>
						<option value="TEXT">文本框</option>
						<option value="DATE">日期</option>
						<option value="ORG">组织选择框</option>
						<option value="SELECT">下拉框</option>
						<option value="RADIO">单选框</option>
						<option value="CHECKBOX">复选框</option>
						<option value="DIAlOG">弹出框</option>
					</select>
				</td>
			</tr>
			<tr id="isArea_tr" style="display:">
				<td style="width: 25%;">
					区域显示:
				</td>
				<td style="width: 75%;">
					<input id="isArea" type="checkbox" name="isArea">
				</td>
			</tr>
			<tr id="isExact_tr" style="display:">
				<td style="width: 25%;">
					精确查询:
				</td>
				<td style="width: 75%;">
					<input id="isExact" type="checkbox" name="isExact">
					<div id="exactValueDiv" style="display:none;">
					<select id="exactValue" name ="exactValue">
						<option value="1">等于</option>
						<option value="2">包含</option>
						<option value="3">不等于</option>
						<option value="4">不包含</option>
					</select>
					</div>
				</td>
			</tr>
			<tr id="isLike_tr" style="display:">
				<td style="width: 25%;">
					模糊查询:
				</td>
				<td style="width: 75%;">
					<input id="isLike" type="checkbox" name="isLike">
				</td>
			</tr>
			<tr id="default_tr" style="display:">
				<td style="width: 25%;">默认值:</td>
				<td style="width: 75%;">
					<input type="text" class="w100" name="defaultValue" id="defaultValue" />
				</td>
			</tr>
			<tr id="date_tr" style="display:none">
				<td class="Label" style="width: 25%;">日期格式:</td>
				<td class="value" style="width: 75%;">
					<select id="contentFormat" name="contentFormat">
						<option value="1">日期(2000-01-01)</option>
						<option value="2">日期(2000年1月1日)</option>
						<option value="3" >日期(2000年)</option>
						<option value="4" >日期(2000年1月)</option>
						<option value="5">日期(1月1日)</option>
						<option value="7">年份(2000)</option> 
						<option value="8">时间(12)</option>
						<option value="9">时间到分(12:00)</option>
						<option value="10">时间到秒(12:00:00)</option>
						<option value="11">日期到时 (2000-01-01 12)</option>
						<option value="12">日期到分 (2000-01-01 12:00)</option>
						<option value="13">日期到秒 (2000-01-01 12:00:00)</option>
					</select>
					 
				</td>
			</tr>
			<tr id="org_tr" style="display:none">
				<td class="Label" style="width: 25%;">输入类型选择:</td>
				<td class="value" style="width: 75%;">
					<select id="orgInputMode" name="orgInputMode" class="w100">
					 	<option value="">--请选择--</option>
						<option value="9">组织选择框（人员）</option>
						<option value="10">组织选择框（部门）</option>
						<option value="11">组织选择框（部门+人员）</option>
						<option value="28">组织选择框 (单位通讯录)</option>
					</select>
				
				</td>
			</tr> 
			<tr id="backup_data_tr" style="display:none">
				<td style="width: 25%;">备选项来源:</td>
				<td style="width: 75%;">
				<input id="optionDataSource_1" type="radio" value="0" name="optionDataSource"/>
				<label for="optionDataSource_1">常量</label>
				<input id="optionDataSource_2" type="radio" value="1" name="optionDataSource"/>
				<label for="optionDataSource_2">取字典</label>
				<input id="optionDataSource_3" type="radio" value="2" name="optionDataSource"/>
				<label for="optionDataSource_3">取数据源</label>
				</td>
			</tr>
			<tr id="backup_set_tr" style="display:none">
				<td  class="Label" style="width: 25%;">备选项设置:</td>
				<td class="value" style="width: 75%;"><textarea id="optdata" name="optdata" style="width:100%;"></textarea></td>
			</tr>
			<tr id='dicCode_tr' style="display:none">
				<td  class="Label" style="width: 25%;">字典编码:</td>
				<td class="value" style="width: 75%;">
				<input id="dictName" class="w100" type="text" value="--请选择--" name="dictName">
				<input id="dictCode" type="hidden" class="w100" type="text"  name="dictCode">
				<ul id="datadict_tree" class="ztree"></ul>
				</td>
			</tr> 
			<tr id='dataSource_tr' style="display:none">
				<td  class="Label" style="width: 25%;">数据源:</td>
				<td class="value" style="width: 75%;">
				<input type="text" class="w100" name="dataSourceName"
							id="dataSourceName" value="" style="width:100%"/>
							<input type="hidden" class="w100"
							name="dataSourceId" id="dataSourceId" value=""/>
				
				</td>
			</tr> 
			<tr id="select_name_column_tr" style="display:none">
				<td  class="Label" style="width: 25%;">选项名称列:</td>
				<td class="value" style="width: 75%;">
				<select id="selectNameColumn" name="selectNameColumn" class="w100">
				</select>
<!-- 				<input type="text" class="w100" name="selectNameColumn" -->
<!-- 							id="selectNameColumn"/> -->
				</td>
			</tr>
			<tr id="select_value_column_tr" style="display:none">
				<td  class="Label" style="width: 25%;">选项名称值:</td>
				<td class="value" style="width: 75%;">
				<select id="selectValueColumn" name="selectValueColumn" class="w100">
				</select>
<!-- 				<input type="text" class="w100" name="selectValueColumn" -->
<!-- 							id="selectValueColumn"/> -->
				</td>
			</tr>
			<tr id="dialog_tr" style="display:none">
				<td style="width: 25%;">弹出框:<td>
				<td style="width: 75%;"><input type="text" /></td>
			</tr>	
		</table>
		</div>
	</div>
	<div id="columnValue" title="列值设置">
		<table>
			<tr>
				
				<td><label>基本列值设置</label></td>
				<td width="50"><input id="columnBasic" name="columnBasic" type="checkbox"
									/></td>
				<td><div id="columnValue_basic" style="display:none"><select id="setColumnValue_basic" name="setColumnValue_basic"><option></option></select></div></td>
			</tr>
			<tr>
				<td><label>高级列值设置</label></td>
				<td width="50"><input id="columnSenior" name="columnSenior" type="checkbox"
									/></td>
				<td><div id="columnValue_senior" style="display:none"><textarea id="setColumnValue_senior" style="overflow:visible;width:400px;height:200px;" maxlength="250"/></textarea></div></td>
			</tr>
			<tr id="data_dict" style="display:none;">
				<td><label>数据字典</label></td>
				<td width="50"></td>
				<td>
					<input type="text" class="w100" name="dataDictName" id="dataDictName" />
					<input type="hidden" class="w100" name="dataDictValue" id="dataDictValue" />
				</td>
			</tr>
		</table>
	</div>
	<div style="display: none">
		<div id="acl_role_choose">
		<table>
		    <tr>
				<td>实体类</td>
				<td>
					<input type="text" class="w100" name="aclEntityClass" id="aclEntityClass" />
				</td>
			</tr>
			<tr>
				<td>选择角色</td>
				<td>
					<input type="text" class="w100" name="aclRoleName" id="aclRoleName" />
					<input type="hidden" class="w100" name="aclRoleValue" id="aclRoleValue" value="" />
				</td>
			</tr>
		</table>
		</div>
	</div>
	<div id="dlg_choose_button"></div>
	<script type="text/javascript" src="${ctx}/resources/jquery/jquery.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/jqueryui/js/jquery-ui.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/jqgrid/js/i18n/grid.locale-cn.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/jqgrid/js/jquery.jqGrid.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/layout/jquery.layout.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/form/jquery.form.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/ztree/js/jquery.ztree.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/common/jquery.dytableTree.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/common/jquery.comboTree.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/global.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/org/unit/jquery.unit.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/common/jquery.popupTreeWindow.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/common/jquery.popupWindow.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/system_admin.js"></script>
	<script type="text/javascript" src="${ctx}/resources/pt/js/control/common/jquery.wControlConfigUtil.js"></script>	
		<script type="text/javascript"
	src="${ctx}/resources/pt/js/basicdata/view/view_constant.js"></script>	
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/basicdata/view/view_definition.js"></script>
	<script type="text/javascript" src="${ctx}/resources/pt/js/common/jquery.popupTreeWindow.js"></script>
</body>
</html>