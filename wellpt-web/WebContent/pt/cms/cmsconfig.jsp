<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>



<title>网站内容页面编辑</title>
<link href="${ctx}/resources/gridster/jquery.gridster.css"
	type="text/css" rel="stylesheet" />
<link href="${ctx}/resources/gridster/reset.css" type="text/css" rel="stylesheet" />
<link rel="stylesheet" href="${ctx}/resources/jqueryui/css/base/jquery-ui.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/resources/pt/css/custom-admin-style.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/resources/pt/css/custom-admin-style.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/resources/pt/css/cmsconfig.css" />
<script src="${ctx}/resources/jquery/jquery.js"></script>
<script src="${ctx}/resources/jqueryui/js/jquery-ui.js"></script>
<script src="${ctx}/resources/utils/json2.js"></script>
<script src="${ctx}/resources/gridster/jquery.dragresize.js"></script>
<script src="${ctx}/resources/pt/js/global.js"></script>
</head>
<body>
	<div id="main">
		<div id="widgetWrap">
			<table>
				<tr>
					<td><input name="save" value="保存页面" id="save" type="button" />
					</td>
				</tr>
				<tr>
					<td><input name="view" value="页面预览" id="view" type="button" />
					</td>
				</tr>
				<tr>
					<td><input id="size" type="text" style="width: 60px;display: none;"/></td>
				</tr>
				<tr>
					<td><input name="listsize" value="设置尺寸" id="savesize" type="button" /></td>
				</tr>
				<tr>
					<td>
						<div id="ifgoWidgets">
							<!-- widget容器 -->
						</div>
					</td>
					<!--	<td>
					<div id="showHide">隐藏</div>
				</td>-->
				</tr>
			</table>
		</div>
		<div class="center">
			<div id="widgetlist" class="out"></div>

		</div>
		<div class="clear"></div>
	</div>

	<div id="dialog" class="setProp" title="设置属性">
		<div>
			<table>
				<tr>
					<td><label>名称</label></td>
					<td><input id="title" name="title" type="text" /></td>
				</tr>
				<tr>
					<td><label>更多样式</label></td>
					<td><select id="moreStyle" name="moreStyle">
							<option value="more">默认</option>
							<option value="more1">样式一</option>
							<option value="more2">样式二</option>
							<option value="more3">样式三</option>
							<option value="more4">样式四</option>
					</select></td>
				</tr>
				<tr>
					<td><label>视图行数(启用则无分页效果)</label></td>
					<td><input id="rows" name="rows" type="text" /></td>
				</tr>
				<tr>
					<td><label>文本参数</label></td>
					<td><input id="para" name="para" type="text" /></td>
				</tr>
				<tr>
					<td><label>窗口主题</label></td>
					<td><select id="theme" name="theme"></select></td>
				</tr>
				<tr>
					<td><label>窗口id</label></td>
					<td><input id="wid" name="wid" type="text" /></td>
				</tr>
				<tr>
					<td></td>
					<td>
						<input id="showTitle" name="showTitle" type="checkbox" />显示名称
						<input id="showMore" name="showMore" type="checkbox" />显示more</br>
						<input id="openSearch" name="openSearch" type="checkbox" />启用搜索
						<input id="showBorder" name="showBorder" type="checkbox" />显示border</br>
						<input id="showNum" name="showNum" type="checkbox" />显示数量
					</td>
				</tr>
			</table>
		</div>
	</div>
	<div id="dialog2" class="setSize" title="设置页面宽度">
		<div>
			<input id="size2" />
		</div>
	</div>
	<script src="${ctx}/resources/pt/js/cms/cmsconfig.js"></script>
</body>
</html>
